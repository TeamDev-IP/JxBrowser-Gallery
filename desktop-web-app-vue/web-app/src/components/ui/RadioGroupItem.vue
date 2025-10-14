
<template>
  <div class="flex items-center space-x-2">
    <button
        type="button"
        role="radio"
        :aria-checked="isChecked"
        :class="radioClass"
        :disabled="disabled"
        @click="select"
    >
      <span v-if="isChecked" class="flex items-center justify-center">
        <span class="h-2 w-2 rounded-full bg-current" />
      </span>
    </button>
    <label
        v-if="label || $slots.default"
        :for="id"
        class="text-sm font-medium leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70 cursor-pointer"
        @click="select"
    >
      <slot>{{ label }}</slot>
    </label>
  </div>
</template>

<script setup lang="ts">
import { inject, computed } from 'vue'
import { cn } from '@/lib/utils'

interface Props {
  value: string | number
  id?: string
  label?: string
  disabled?: boolean
}

const props = defineProps<Props>()

const radioGroup = inject<{
  modelValue?: string | number
  name?: string
  updateValue: (value: string | number) => void
}>('radioGroup')

const isChecked = computed(() => {
  return radioGroup?.modelValue === props.value
})

const radioClass = computed(() => {
  return cn(
      'aspect-square h-4 w-4 rounded-full border border-primary text-primary',
      'ring-offset-background focus:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2',
      'disabled:cursor-not-allowed disabled:opacity-50'
  )
})

function select() {
  if (!props.disabled) {
    radioGroup?.updateValue(props.value)
  }
}
</script>