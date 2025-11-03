<template>
  <textarea
    :value="modelValue"
    :class="textareaClass"
    :rows="rows"
    :disabled="disabled"
    :placeholder="placeholder"
    v-bind="$attrs"
    @input="onInput"
  />
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { cn } from '@/lib/utils'

interface Props {
  modelValue?: string
  disabled?: boolean
  placeholder?: string
  rows?: number
  error?: boolean
}

interface Emits {
  (e: 'update:modelValue', value: string): void
}

const props = withDefaults(defineProps<Props>(), {
  rows: 3,
})

const emit = defineEmits<Emits>()

const textareaClass = computed(() => {
  return cn(
    'flex min-h-[80px] w-full rounded-md border border-input bg-background px-3 py-2 text-sm',
    'ring-offset-background placeholder:text-muted-foreground',
    'focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2',
    'disabled:cursor-not-allowed disabled:opacity-50',
    'resize-none',
    {
      'border-destructive focus-visible:ring-destructive': props.error,
    }
  )
})

function onInput(event: Event) {
  const target = event.target as HTMLTextAreaElement
  emit('update:modelValue', target.value)
}
</script>
