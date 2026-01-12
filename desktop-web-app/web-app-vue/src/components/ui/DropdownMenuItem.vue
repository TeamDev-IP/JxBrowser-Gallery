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
  <div :class="itemClass" @click="onClick">
    <slot />
  </div>
</template>

<script setup lang="ts">
import {computed} from 'vue'
import {cn} from '@/lib/utils'

interface Props {
  disabled?: boolean
  destructive?: boolean
}

interface Emits {
  (e: 'click', event: MouseEvent): void
}

const props = defineProps<Props>()
const emit = defineEmits<Emits>()

const itemClass = computed(() => {
  return cn(
    'relative flex cursor-pointer select-none items-center rounded-sm px-2 py-1.5 text-sm outline-none',
    'transition-colors hover:bg-accent hover:text-accent-foreground',
    'focus:bg-accent focus:text-accent-foreground',
    {
      'opacity-50 pointer-events-none': props.disabled,
      'text-destructive hover:text-destructive': props.destructive,
    }
  )
})

function onClick(event: MouseEvent) {
  if (!props.disabled) {
    emit('click', event)
  }
}
</script>
