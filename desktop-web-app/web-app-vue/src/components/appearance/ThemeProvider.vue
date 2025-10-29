<script setup lang="ts">
import { ref, provide, onMounted, watch } from "vue";
import { prefsStorage } from "@/storage/prefs-storage.ts";
import { systemTheme, ThemeOption, fromTheme } from "@/converter/theme.ts";
import {ThemeContext, ThemeContextKey} from "@/utils/theme-context.ts";
import { prefsClient } from "@shared/rpc/prefs-client";

const theme = ref<ThemeOption>(prefsStorage.theme() ?? systemTheme);

function setTheme(newTheme: ThemeOption) {
  prefsStorage.saveTheme(newTheme);
  theme.value = newTheme;
}

onMounted(async () => {
  try {
    const appearance = await prefsClient.getAppearance({});
    const loadedTheme = fromTheme(appearance.theme);
    setTheme(loadedTheme);
  } catch (e) {
    console.error("Failed to load theme:", e);
  }
});

watch(theme, (val) => {
  const root = document.documentElement;
  root.classList.remove("light", "dark");
  if (val === "system") {
    root.classList.add(
        window.matchMedia("(prefers-color-scheme: dark)").matches ? "dark" : "light"
    );
  } else {
    root.classList.add(val);
  }
}, { immediate: true });

provide<ThemeContext>(ThemeContextKey, { theme, setTheme });
</script>

<template>
  <slot />
</template>