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
  <input
    ref="inputRef"
    :type="type"
    v-bind="$attrs"
    :class="inputClass"
    :value="modelValue"
    @input="handleInput"
  />
</template>

<script setup lang="ts">
import { ref, computed, defineProps, defineEmits, defineExpose } from 'vue'

const props = defineProps<{
  type?: string
  class?: string
  modelValue?: string
}>()

const emit = defineEmits<{
  (e: 'update:modelValue', value: string): void
}>()

const inputRef = ref<HTMLInputElement | null>(null)

defineExpose({ inputRef })

function cn(...classes: (string | undefined | false)[]) {
  return classes.filter(Boolean).join(' ')
}

const type = computed(() => props.type ?? 'text')

const inputClass = computed(() =>
  cn(
    'flex h-8 w-full rounded-md border focus:border-control-select bg-background px-2 py-2 text-base ring-offset-background file:border-0 file:bg-transparent file:text-sm file:font-medium file:text-foreground placeholder:text-muted-foreground focus-visible:outline-none focus-visible:ring-ring focus-visible:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50 md:text-sm',
    props.class
  )
)

function handleInput(event: Event) {
  const target = event.target as HTMLInputElement | null
  if (!target) return
  emit('update:modelValue', target.value)
}
</script>
