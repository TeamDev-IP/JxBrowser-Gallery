<template>
  <div class="space-y-4">
    <h1 class="text-2xl font-semibold">Appearance</h1>
    <Separator class="my-4 h-[1px] w-full"/>

    <div class="items-center flex-wrap space-y-4 py-1">
      <div>
        <p class="text-sm">Theme</p>
        <p class="text-xs text-muted-foreground text-gray-500">
          The color theme for the application: light, dark, or match your system settings.
        </p>
      </div>

      <div class="flex flex-col sm:flex-row gap-x-2 gap-y-2 justify-center items-center">
        <ThemeBox
            title="Light"
            :isSelected="theme === lightTheme"
            :onSelect="() => updateTheme(lightTheme)"
            :icon="Sun"
        />
        <ThemeBox
            title="Dark"
            :isSelected="theme === darkTheme"
            :onSelect="() => updateTheme(darkTheme)"
            :icon="Moon"
        />
        <ThemeBox
            title="System"
            :isSelected="theme === systemTheme"
            :onSelect="() => updateTheme(systemTheme)"
            :icon="Laptop"
        />
      </div>
    </div>

    <div class="w-full flex items-center space-y-2 justify-between py-2">
      <div class="pr-8">
        <p class="text-sm">Font size</p>
        <p class="text-xs text-muted-foreground text-gray-500">
          Adjust the size of the text in the application for better readability.
        </p>
      </div>

      <Combobox
          v-model:currentOption="fontSize"
          :options="fontSizes"
      />
    </div>
  </div>
</template>

<script setup lang="ts">
import {onMounted} from "vue"
import Separator from "@/components/ui/Separator.vue"
import ThemeBox from "@/components/appearance/ThemeBox.vue"
import Combobox from "@/components/ui/common/Combobox.vue"
import {Sun, Moon, Laptop} from "lucide-vue-next"

import {create} from "@bufbuild/protobuf"
import {AppearanceSchema} from "@/gen/prefs_pb.ts"

import {
  lightTheme,
  darkTheme,
  systemTheme,
  fromTheme,
  toTheme,
  ThemeOption,
} from "@/converter/theme.ts"

import {
  smallFontSize,
  defaultFontSize,
  largeFontSize,
  fromFontSize,
  toFontSize,
  FontSizeOption,
} from "@/converter/font-size.ts"
import { prefsClient } from "@shared/rpc/prefs-client";
import {useTheme} from "@/components/hooks/useTheme.ts";
import {useFontSize} from "@/components/hooks/useFontSize.ts";


const {theme, setTheme} = useTheme()
const {fontSize, setFontSize} = useFontSize()
const fontSizes: FontSizeOption[] = [smallFontSize, defaultFontSize, largeFontSize]

onMounted(async () => {
  try {
    const appearance = await prefsClient.getAppearance({})
    const t = fromTheme(appearance.theme)
    const f = fromFontSize(appearance.fontSize)
    setTheme(t)
    setFontSize(f)
  } catch (err) {
    console.error("Failed to load appearance:", err)
  }
})

function onUpdateAppearance({
                              newTheme = theme.value,
                              newFontSize = fontSize.value,
                            }: {
  newTheme?: ThemeOption;
  newFontSize?: FontSizeOption;
}) {
  const newAppearance = create(AppearanceSchema, {
    theme: toTheme(newTheme),
    fontSize: toFontSize(newFontSize),
  });
  prefsClient.setAppearance(newAppearance);
}

function updateTheme(t: ThemeOption) {
  console.log(t)
  onUpdateAppearance({newTheme: t})
  setTheme(t)
}
</script>
