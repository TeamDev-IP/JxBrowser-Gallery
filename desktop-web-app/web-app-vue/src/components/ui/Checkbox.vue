<!--
  Copyright (c) 2025 TeamDev

  Permission is hereby granted, free of charge, to any person obtaining a copy
  of this software and associated documentation files (the "Software"), to deal
  in the Software without restriction, including without limitation the rights
  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
  copies of the Software, and to permit persons to whom the Software is
  furnished to do so, subject to the following conditions:

  The above copyright notice and this permission notice shall be included in all
  copies or substantial portions of the Software.

  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
  SOFTWARE.
-->

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
      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="3" d="M5 13l4 4L19 7" />
    </svg>
    <span v-else-if="indeterminate" class="h-0.5 w-3 bg-current" />
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
      'bg-primary text-primary-foreground': props.modelValue || props.indeterminate,
    }
  )
})

function toggle() {
  if (!props.disabled) {
    emit('update:modelValue', !props.modelValue)
  }
}
</script>
