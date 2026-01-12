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
  <button type="button" :class="triggerClass" :disabled="disabled" @click="onClick">
    <slot />
  </button>
</template>

<script setup lang="ts">
import { inject, computed } from 'vue'
import { cn } from '@/lib/utils'

interface Props {
  value: string
  disabled?: boolean
}

const props = defineProps<Props>()

const tabs = inject<{
  activeTab: { value: string }
  setActiveTab: (value: string) => void
}>('tabs')

const isActive = computed(() => tabs?.activeTab.value === props.value)

const triggerClass = computed(() => {
  return cn(
    'inline-flex items-center justify-center whitespace-nowrap rounded-sm px-3 py-1.5',
    'text-sm font-medium ring-offset-background transition-all',
    'focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2',
    'disabled:pointer-events-none disabled:opacity-50',
    {
      'bg-background text-foreground shadow-sm': isActive.value,
      'hover:bg-background/50': !isActive.value,
    }
  )
})

function onClick() {
  if (!props.disabled) {
    tabs?.setActiveTab(props.value)
  }
}
</script>
