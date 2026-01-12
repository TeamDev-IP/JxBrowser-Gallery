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
import { defaultFontSize, FontSizeOption, fromFontSize } from '@/converter/font-size.ts'
import { prefsClient } from '@shared/rpc/prefs-client'
import { FontSizeContext, FontSizeContextKey } from '@/utils/fontsize-context.ts'

const fontSize = ref<FontSizeOption>(prefsStorage.fontSize() ?? defaultFontSize)

function setFontSize(newFontSize: FontSizeOption) {
  prefsStorage.saveFontSize(newFontSize)
  fontSize.value = newFontSize
}

onMounted(async () => {
  try {
    const appearance = await prefsClient.getAppearance({})
    const loadedFontSize = fromFontSize(appearance.fontSize)
    setFontSize(loadedFontSize)
  } catch (e) {
    console.error('Failed to load font size:', e)
  }
})

watch(
  fontSize,
  (val) => {
    const scaleFactor = val === 'Small' ? 0.8 : val === 'Default' ? 1.0 : 1.2
    const style = document.documentElement.style
    style.setProperty('--font-size-sm', `${0.875 * scaleFactor}rem`)
    style.setProperty('--font-size-xs', `${0.75 * scaleFactor}rem`)
    style.setProperty('--font-size-lg', `${1.125 * scaleFactor}rem`)
    style.setProperty('--font-size-2xl', `${1.5 * scaleFactor}rem`)
  },
  { immediate: true }
)

provide<FontSizeContext>(FontSizeContextKey, { fontSize, setFontSize })
</script>

<template>
  <slot />
</template>
