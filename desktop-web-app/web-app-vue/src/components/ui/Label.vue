<template>
  <label
      :class="labelClass"
      v-bind="$attrs"
  >
    <slot />
  </label>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { cn } from '@/lib/utils'

interface Props {
  error?: boolean
  required?: boolean
}

const props = defineProps<Props>()

const labelClass = computed(() => {
  return cn(
      'text-sm font-medium leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70',
      {
        'text-destructive': props.error
      }
  )
})
</script>
