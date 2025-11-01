<template>
  <div
      :role="decorative ? 'presentation' : 'separator'"
      :aria-orientation="orientation"
      :class="[
      'shrink-0 bg-border',
      orientation === 'horizontal' ? 'h-[1px] w-full' : 'h-full w-[1px]',
      className
    ]"
  ></div>
</template>

<script setup lang="ts">
const props = withDefaults(defineProps<{
  className?: string
  orientation?: 'horizontal' | 'vertical'
  decorative?: boolean
}>(), {
  orientation: 'horizontal',
  decorative: true
})

const { className, orientation, decorative } = props
</script>
