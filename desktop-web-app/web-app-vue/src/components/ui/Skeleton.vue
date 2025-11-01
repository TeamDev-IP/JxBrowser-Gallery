<template>
  <div :class="skeletonClass" />
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { cn } from '@/lib/utils'

interface Props {
  class?: string
}

const props = defineProps<Props>()

const skeletonClass = computed(() => {
  return cn('animate-pulse rounded-md bg-muted', props.class)
})
</script>
