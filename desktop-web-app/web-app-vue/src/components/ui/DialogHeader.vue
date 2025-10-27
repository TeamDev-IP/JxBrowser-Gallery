<script setup lang="ts">
import { cn } from '@/lib/utils'

const props = defineProps<{
  className?: string
}>()
</script>

<template>
  <div :class="cn('flex flex-col space-y-1.5 text-center sm:text-left', props.className)">
    <slot />
  </div>
</template>