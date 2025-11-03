<script setup lang="ts">
import { provide } from 'vue'
import { tooltipContextKey } from '@/utils/tooltipcontext.ts'

const props = defineProps<{ delay?: number }>()

provide(tooltipContextKey, { delay: props.delay ?? 0 })
</script>

<template>
  <slot />
</template>
