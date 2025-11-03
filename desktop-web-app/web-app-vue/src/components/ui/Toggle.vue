<template>
  <button
    type="button"
    :aria-pressed="modelValue"
    :disabled="disabled"
    :class="toggleClass"
    @click="toggle"
  >
    <slot />
  </button>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { cn } from '@/lib/utils'

interface Props {
  modelValue?: boolean
  disabled?: boolean
  variant?: 'default' | 'outline'
  size?: 'default' | 'sm' | 'lg'
}

interface Emits {
  (e: 'update:modelValue', value: boolean): void
}

const props = withDefaults(defineProps<Props>(), {
  variant: 'default',
  size: 'default',
})

const emit = defineEmits<Emits>()

const toggleClass = computed(() => {
  return cn(
    'inline-flex items-center justify-center rounded-md text-sm font-medium',
    'ring-offset-background transition-colors hover:bg-muted hover:text-muted-foreground',
    'focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2',
    'disabled:pointer-events-none disabled:opacity-50',
    {
      'bg-transparent': !props.modelValue && props.variant === 'default',
      'bg-accent text-accent-foreground': props.modelValue && props.variant === 'default',
      'border border-input bg-transparent hover:bg-accent hover:text-accent-foreground':
        props.variant === 'outline',
      'h-10 px-3': props.size === 'default',
      'h-9 px-2.5': props.size === 'sm',
      'h-11 px-5': props.size === 'lg',
    }
  )
})

function toggle() {
  if (!props.disabled) {
    emit('update:modelValue', !props.modelValue)
  }
}
</script>
