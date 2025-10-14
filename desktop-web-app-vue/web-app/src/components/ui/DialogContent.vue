<script setup lang="ts">
import { ref } from 'vue'
import DialogPortal from './DialogPortal.vue'
import DialogOverlay from './DialogOverlay.vue'
import { cn } from '@/lib/utils'

const props = defineProps<{
  className?: string
}>()

const open = ref(true)
const contentRef = ref<HTMLElement>()
</script>

<template>
  <DialogPortal>
    <DialogOverlay />
    <div
        ref="contentRef"
        :class="cn(
        'fixed left-[50%] top-[50%] z-50 grid w-full max-w-lg translate-x-[-50%] translate-y-[-50%] gap-4 border bg-background p-6 shadow-lg duration-200 data-[state=open]:animate-in data-[state=closed]:animate-out data-[state=closed]:fade-out-0 data-[state=open]:fade-in-0 data-[state=closed]:zoom-out-95 data-[state=open]:zoom-in-95 data-[state=closed]:slide-out-to-left-1/2 data-[state=closed]:slide-out-to-top-[48%] data-[state=open]:slide-in-from-left-1/2 data-[state=open]:slide-in-from-top-[48%] sm:rounded-lg',
        props.className
      )"
    >
      <slot />
      <button
          class="absolute right-4 top-4 rounded-sm opacity-70 ring-offset-background transition-opacity hover:opacity-100 focus:outline-none focus:ring-2 focus:ring-ring focus:ring-offset-2 disabled:pointer-events-none data-[state=open]:bg-accent data-[state=open]:text-muted-foreground"
          @click="open = false"
      >
        <X class="h-4 w-4" />
        <span class="sr-only">Close</span>
      </button>
    </div>
  </DialogPortal>
</template>