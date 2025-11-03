<script setup lang="ts">
import { ref } from 'vue'
import { cn } from '@/lib/utils'

const props = defineProps<{
  className?: string
}>()

const titleRef = ref<HTMLElement>()
</script>

<template>
  <h2
    ref="titleRef"
    :class="cn('text-lg font-semibold leading-none tracking-tight', props.className)"
  >
    <slot />
  </h2>
</template>
