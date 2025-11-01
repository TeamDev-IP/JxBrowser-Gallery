<script setup lang="ts">
import { cn } from '@/lib/utils'
defineProps<{ className?: string }>()
</script>

<template>
  <span :class="cn('ml-auto text-xs tracking-widest text-muted-foreground', className)" v-bind="$attrs">
    <slot />
  </span>
</template>
