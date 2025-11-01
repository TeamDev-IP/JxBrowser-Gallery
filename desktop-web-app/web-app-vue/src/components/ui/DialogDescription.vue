<script setup lang="ts">
import { ref } from 'vue'
import { cn } from '@/lib/utils'

const props = defineProps<{
  className?: string
}>()

const descRef = ref<HTMLElement>()
</script>

<template>
  <p ref="descRef" :class="cn('text-sm text-muted-foreground', props.className)">
    <slot />
  </p>
</template>
