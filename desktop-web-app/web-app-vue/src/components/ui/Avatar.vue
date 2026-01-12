<!--
  Copyright 2026, TeamDev

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

<template>
  <div :class="['relative flex h-10 w-10 shrink-0 overflow-hidden rounded-full', className]">
    <img
      v-if="showImage"
      :src="src"
      :alt="alt"
      :class="['aspect-square h-full w-full object-cover', imageClass]"
      @error="handleError"
    />
    <div
      v-else
      :class="[
        'flex h-full w-full items-center justify-center rounded-full bg-muted text-sm font-medium',
        fallbackClass,
      ]"
    >
      <slot>{{ fallback }}</slot>
    </div>
  </div>
</template>

<script setup lang="ts">
import {defineProps, ref, watch} from 'vue'

interface Props {
  className?: string
  src?: string
  alt?: string
  fallback?: string
  imageClass?: string
  fallbackClass?: string
}

const props = defineProps<Props>()

const showImage = ref(!!props.src)

watch(
  () => props.src,
  (newSrc) => {
    showImage.value = !!newSrc
  }
)

function handleError() {
  console.warn('Avatar image failed to load, showing fallback.')
  showImage.value = false
}
</script>
