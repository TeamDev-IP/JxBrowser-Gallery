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

<script setup lang="ts">
import { inject } from 'vue'
import { tooltipContextKey } from './TooltipProvider.vue'

const context = inject<{ tooltipOpen: any }>(tooltipContextKey)
if (!context) throw new Error('TooltipTrigger must be used inside a Tooltip')

const openTooltip = () => (context.tooltipOpen.value = true)
const closeTooltip = () => (context.tooltipOpen.value = false)
</script>

<template>
  <div @mouseenter="openTooltip" @mouseleave="closeTooltip">
    <slot />
  </div>
</template>
