<!--
  Copyright 2026, TeamDev

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
