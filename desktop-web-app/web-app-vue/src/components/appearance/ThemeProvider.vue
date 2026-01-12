<!--
  Copyright (c) 2025 TeamDev

  Permission is hereby granted, free of charge, to any person obtaining a copy
  of this software and associated documentation files (the "Software"), to deal
  in the Software without restriction, including without limitation the rights
  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
  copies of the Software, and to permit persons to whom the Software is
  furnished to do so, subject to the following conditions:

  The above copyright notice and this permission notice shall be included in all
  copies or substantial portions of the Software.

  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
  SOFTWARE.
-->

<script setup lang="ts">
import { ref, provide, onMounted, watch } from 'vue'
import { prefsStorage } from '@/storage/prefs-storage.ts'
import { systemTheme, ThemeOption, fromTheme } from '@/converter/theme.ts'
import { ThemeContext, ThemeContextKey } from '@/utils/theme-context.ts'
import { prefsClient } from '@shared/rpc/prefs-client'

const theme = ref<ThemeOption>(prefsStorage.theme() ?? systemTheme)

function setTheme(newTheme: ThemeOption) {
  prefsStorage.saveTheme(newTheme)
  theme.value = newTheme
}

onMounted(async () => {
  try {
    const appearance = await prefsClient.getAppearance({})
    const loadedTheme = fromTheme(appearance.theme)
    setTheme(loadedTheme)
  } catch (e) {
    console.error('Failed to load theme:', e)
  }
})

watch(
  theme,
  (val) => {
    const root = document.documentElement
    root.classList.remove('light', 'dark')
    if (val === 'system') {
      root.classList.add(
        window.matchMedia('(prefers-color-scheme: dark)').matches ? 'dark' : 'light'
      )
    } else {
      root.classList.add(val)
    }
  },
  { immediate: true }
)

provide<ThemeContext>(ThemeContextKey, { theme, setTheme })
</script>

<template>
  <slot />
</template>
