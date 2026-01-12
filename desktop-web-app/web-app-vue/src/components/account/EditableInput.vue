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

<script setup lang="ts">
import { ref, watch, computed } from 'vue'
import Input from '@/components/ui/Input.vue'

interface Props {
  title: string
  modelValue: string
  isEmail?: boolean
}

const props = defineProps<Props>()
const emit = defineEmits<{
  (e: 'update:modelValue', value: string): void
}>()

const localValue = ref(props.modelValue)
const isValid = ref(true)

watch(
  () => props.modelValue,
  (val) => {
    localValue.value = val
  }
)

const inputType = computed(() => (props.isEmail ? 'email' : 'text'))

const inputClasses = computed(() => {
  return [
    'w-full text-sm focus-visible:border-control-select transition-colors',
    !isValid.value ? 'bg-red-300 focus-visible:border-red-500' : '',
  ]
})

watch(localValue, (val) => {
  if (props.isEmail) {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
    isValid.value = emailRegex.test(val)
  }
})

function handleBlur() {
  if (!isValid.value) {
    localValue.value = props.modelValue
    isValid.value = true
    return
  }
  emit('update:modelValue', localValue.value)
}
</script>

<template>
  <div class="w-full items-center space-y-2">
    <div class="w-full sm:flex items-center space-y-1 justify-between">
      <p class="text-sm">{{ title }}</p>
      <div class="xs:w-full sm:w-[50%] lg:w-[30%]">
        <Input
          v-model="localValue"
          :type="inputType"
          :class="inputClasses.join(' ')"
          @blur="handleBlur"
        />
        <span v-if="!isValid" class="text-xs text-red-500">
          Please enter a valid email address.
        </span>
      </div>
    </div>
  </div>
</template>
