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
  <div :class="badgeClass">
    <slot />
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { cn } from '@/lib/utils'

interface Props {
  variant?: 'default' | 'secondary' | 'destructive' | 'outline'
}

const props = withDefaults(defineProps<Props>(), {
  variant: 'default',
})

const badgeClass = computed(() => {
  return cn(
    'inline-flex items-center rounded-full border px-2.5 py-0.5 text-xs font-semibold',
    'transition-colors focus:outline-none focus:ring-2 focus:ring-ring focus:ring-offset-2',
    {
      'border-transparent bg-primary text-primary-foreground hover:bg-primary/80':
        props.variant === 'default',
      'border-transparent bg-secondary text-secondary-foreground hover:bg-secondary/80':
        props.variant === 'secondary',
      'border-transparent bg-destructive text-destructive-foreground hover:bg-destructive/80':
        props.variant === 'destructive',
      'text-foreground': props.variant === 'outline',
    }
  )
})
</script>
