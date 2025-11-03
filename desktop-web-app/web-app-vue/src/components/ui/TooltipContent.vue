<script setup lang="ts">
import { inject, computed, defineProps } from 'vue'
import { tooltipContextKey } from './TooltipProvider.vue'
import { cn } from '@/lib/utils'

const props = defineProps<{
  side?: 'top' | 'bottom' | 'left' | 'right'
  sideOffset?: number
  className?: string
}>()

const context = inject<{ tooltipOpen: any }>(tooltipContextKey)
if (!context) throw new Error('TooltipContent must be used inside a Tooltip')

const sideOffset = computed(() => props.sideOffset ?? 4)

const contentClasses = computed(() =>
  cn(
    'z-50 overflow-hidden rounded-md border bg-popover px-3 py-1.5 text-sm text-popover-foreground shadow-md animate-in fade-in-0 zoom-in-95 data-[state=closed]:animate-out data-[state=closed]:fade-out-0 data-[state=closed]:zoom-out-95 data-[side=bottom]:slide-in-from-top-2 data-[side=left]:slide-in-from-right-2 data-[side=right]:slide-in-from-left-2 data-[side=top]:slide-in-from-bottom-2',
    props.className
  )
)
</script>

<template>
  <transition name="fade">
    <div
      v-if="context.tooltipOpen"
      :class="contentClasses"
      :data-side="props.side ?? 'top'"
      :style="{ margin: `${sideOffset}px` }"
    >
      <slot />
    </div>
  </transition>
</template>

<style>
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.2s;
}
.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>
