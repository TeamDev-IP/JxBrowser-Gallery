<script setup lang="ts">
import {ref, provide, onMounted, watch} from "vue";
import {prefsStorage} from "@/storage/prefs-storage.ts";
import {defaultFontSize, FontSizeOption, fromFontSize} from "@/converter/font-size.ts";
import { prefsClient } from "@shared/rpc/prefs-client";
import {FontSizeContext, FontSizeContextKey} from "@/utils/fontsize-context.ts";

const fontSize = ref<FontSizeOption>(prefsStorage.fontSize() ?? defaultFontSize);

function setFontSize(newFontSize: FontSizeOption) {
  prefsStorage.saveFontSize(newFontSize);
  fontSize.value = newFontSize;
}

onMounted(async () => {
  try {
    const appearance = await prefsClient.getAppearance({});
    const loadedFontSize = fromFontSize(appearance.fontSize);
    setFontSize(loadedFontSize);
  } catch (e) {
    console.error("Failed to load font size:", e);
  }
});

watch(fontSize, (val) => {
  const scaleFactor = val === "Small" ? 0.8 : val === "Default" ? 1.0 : 1.2;
  const style = document.documentElement.style;
  style.setProperty("--font-size-sm", `${0.875 * scaleFactor}rem`);
  style.setProperty("--font-size-xs", `${0.75 * scaleFactor}rem`);
  style.setProperty("--font-size-lg", `${1.125 * scaleFactor}rem`);
  style.setProperty("--font-size-2xl", `${1.5 * scaleFactor}rem`);
}, {immediate: true});

provide<FontSizeContext>(FontSizeContextKey, {fontSize, setFontSize});
</script>

<template>
  <slot/>
</template>
