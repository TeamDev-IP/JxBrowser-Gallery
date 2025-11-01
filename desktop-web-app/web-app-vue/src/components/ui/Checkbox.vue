
<template>
  <button
      type="button"
      role="checkbox"
      :aria-checked="modelValue"
      :disabled="disabled"
      :class="checkboxClass"
      @click="toggle"
  >
    <svg
        v-if="modelValue"
        class="h-4 w-4 text-current"
        fill="none"
        stroke="currentColor"
        viewBox="0 0 24 24"
    >
      <path
          stroke-linecap="round"
          stroke-linejoin="round"
          stroke-width="3"
          d="M5 13l4 4L19 7"
      />
    </svg>
    <span
        v-else-if="indeterminate"
        class="h-0.5 w-3 bg-current"
    />
  </button>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { cn } from '@/lib/utils'

interface Props {
  modelValue?: boolean
  disabled?: boolean
  indeterminate?: boolean
}

interface Emits {
  (e: 'update:modelValue', value: boolean): void
}

const props = defineProps<Props>()
const emit = defineEmits<Emits>()

const checkboxClass = computed(() => {
  return cn(
      'peer h-4 w-4 shrink-0 rounded-sm border border-primary',
      'ring-offset-background focus-visible:outline-none focus-visible:ring-2',
      'focus-visible:ring-ring focus-visible:ring-offset-2',
      'disabled:cursor-not-allowed disabled:opacity-50',
      'flex items-center justify-center',
      {
        'bg-primary text-primary-foreground': props.modelValue || props.indeterminate
      }
  )
})

function toggle() {
  if (!props.disabled) {
    emit('update:modelValue', !props.modelValue)
  }
}
</script>
