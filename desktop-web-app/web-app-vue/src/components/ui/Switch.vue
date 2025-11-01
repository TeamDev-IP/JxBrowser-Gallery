<template>
  <label
      :class="cn(
      'relative inline-flex h-6 w-11 shrink-0 cursor-pointer items-center rounded-full border-2 border-transparent transition-colors',
      'focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 focus-visible:ring-offset-background',
      disabled
        ? 'cursor-not-allowed opacity-50'
        : '',
      modelValue
        ? 'bg-primary'
        : 'bg-input',
      className
    )"
  >
    <input
        type="checkbox"
        class="sr-only peer"
        :checked="modelValue"
        :disabled="disabled"
        @change="$emit('update:modelValue', !modelValue)"
    />
    <span
        :class="cn(
        'pointer-events-none block h-5 w-5 rounded-full bg-background shadow-lg ring-0 transition-transform',
        modelValue ? 'translate-x-5' : 'translate-x-0'
      )"
    />
  </label>
</template>

<script setup lang="ts">
import { defineProps, defineEmits } from 'vue'
import { cn } from '@/lib/utils'

defineProps<{
  modelValue: boolean
  disabled?: boolean
  className?: string
}>()

defineEmits(['update:modelValue'])
</script>
