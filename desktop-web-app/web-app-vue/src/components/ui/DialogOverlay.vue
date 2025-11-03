<script setup lang="ts">
import { ref } from 'vue'
import { cn } from '@/lib/utils'

const props = defineProps<{
  className?: string
}>()

const overlayRef = ref<HTMLElement>()
</script>

<template>
  <div
    ref="overlayRef"
    :class="
      cn(
        'fixed inset-0 z-50 bg-black/80 data-[state=open]:animate-in data-[state=closed]:animate-out data-[state=closed]:fade-out-0 data-[state=open]:fade-in-0',
        props.className
      )
    "
  >
    <slot />
  </div>
</template>
