<script setup lang="ts">
import { cn } from '@/lib/utils'

const props = defineProps<{
  className?: string
}>()
</script>

<template>
  <div :class="cn('flex flex-col-reverse sm:flex-row sm:justify-end sm:space-x-2', props.className)">
    <slot />
  </div>
</template>