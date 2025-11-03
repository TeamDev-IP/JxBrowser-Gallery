<script setup lang="ts">
import { ref } from 'vue'
import { cn } from '@/lib/utils'

const props = defineProps<{ className?: string }>()
const listRef = ref<HTMLElement>()
</script>

<template>
  <div
    ref="listRef"
    :class="cn('max-h-[300px] overflow-y-auto overflow-x-hidden', props.className)"
    v-bind="$attrs"
  >
    <slot />
  </div>
</template>
