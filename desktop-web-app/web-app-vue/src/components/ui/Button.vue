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
  <component
    :is="tag"
    :type="tag === 'button' ? type : undefined"
    :disabled="disabled || loading"
    :class="buttonClass"
    v-bind="$attrs"
  >
    <span v-if="loading" class="mr-2 h-4 w-4 animate-spin">
      <svg class="h-full w-full" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
        <circle
          class="opacity-25"
          cx="12"
          cy="12"
          r="10"
          stroke="currentColor"
          stroke-width="4"
        ></circle>
        <path
          class="opacity-75"
          fill="currentColor"
          d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"
        ></path>
      </svg>
    </span>
    <slot />
  </component>
</template>

<script setup lang="ts">
import { computed } from 'vue'

interface Props {
  variant?: 'default' | 'destructive' | 'outline' | 'secondary' | 'ghost' | 'link'
  size?: 'default' | 'sm' | 'lg' | 'icon'
  disabled?: boolean
  loading?: boolean
  type?: 'button' | 'submit' | 'reset'
  asChild?: boolean
}

const props = withDefaults(defineProps<Props>(), {
  variant: 'default',
  size: 'default',
  type: 'button',
  asChild: false,
})

const tag = computed(() => (props.asChild ? 'slot' : 'button'))

const buttonClass = computed(
  () => `
  inline-flex items-center justify-center rounded-md text-sm font-medium
  ${props.variant === 'default' ? 'bg-primary text-white' : ''}
  ${props.size === 'sm' ? 'h-9 px-3' : 'h-10 px-4'}
`
)
</script>
