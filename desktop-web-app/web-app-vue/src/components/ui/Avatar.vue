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
        :class="['flex h-full w-full items-center justify-center rounded-full bg-muted text-sm font-medium', fallbackClass]"
    >
      <slot>{{ fallback }}</slot>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, watch, defineProps } from "vue"

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

watch(() => props.src, (newSrc) => {
  showImage.value = !!newSrc
})

function handleError() {
  console.warn("Avatar image failed to load, showing fallback.")
  showImage.value = false
}
</script>
