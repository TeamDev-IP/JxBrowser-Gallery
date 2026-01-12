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
  <Teleport to="body">
    <transition
      enter-active-class="transition ease-out duration-150"
      enter-from-class="opacity-0 scale-95"
      enter-to-class="opacity-100 scale-100"
      leave-active-class="transition ease-in duration-100"
      leave-from-class="opacity-100 scale-100"
      leave-to-class="opacity-0 scale-95"
    >
      <div
        v-if="popoverOpen"
        ref="contentRef"
        class="z-50 w-72 rounded-md border bg-popover p-4 text-popover-foreground shadow-md outline-none fixed"
        :style="contentStyle"
        @click.stop
      >
        <slot />
      </div>
    </transition>
  </Teleport>
</template>

<script setup lang="ts">
import {inject, nextTick, onBeforeUnmount, onMounted, ref, watch} from 'vue'

const props = defineProps<{
  align?: 'left' | 'center' | 'right'
  sideOffset?: number
}>()

const popoverOpen = inject('popoverOpen')
if (!popoverOpen) throw new Error('PopoverContent must be used inside Popover')

const triggerRef = ref<HTMLElement | null>(null)
const contentRef = ref<HTMLElement | null>(null)
const contentStyle = ref<Record<string, string>>({})

function findTrigger(): HTMLElement | null {
  return document.querySelector('[data-popover-trigger]') as HTMLElement | null
}

async function updatePosition() {
  await nextTick()

  const trigger = triggerRef.value
  const content = contentRef.value
  if (!trigger || !content) return

  const rect = trigger.getBoundingClientRect()
  const scrollY = window.scrollY
  const scrollX = window.scrollX
  const sideOffset = props.sideOffset ?? 4

  let left = rect.left + scrollX
  if (props.align === 'center')
    left = rect.left + scrollX + rect.width / 2 - content.offsetWidth / 2
  if (props.align === 'right') left = rect.right + scrollX - content.offsetWidth

  contentStyle.value = {
    top: `${rect.bottom + sideOffset + scrollY}px`,
    left: `${left}px`,
    width: `${rect.width}px`,
  }
}

watch(popoverOpen, async (val) => {
  if (val) {
    await updatePosition()
  }
})

onMounted(() => {
  triggerRef.value = findTrigger()
  window.addEventListener('resize', updatePosition)
  window.addEventListener('scroll', updatePosition)
})

onBeforeUnmount(() => {
  window.removeEventListener('resize', updatePosition)
  window.removeEventListener('scroll', updatePosition)
})
</script>
