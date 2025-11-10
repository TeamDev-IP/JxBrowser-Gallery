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
  <div
    v-if="collapsible === 'none'"
    ref="sidebarRef"
    :class="[
      'flex h-full w-[--sidebar-width] flex-col bg-sidebar text-sidebar-foreground',
      className,
    ]"
    v-bind="attrs"
  >
    <slot />
  </div>

  <!-- Case 2: Mobile sidebar (uses Sheet) -->
  <Sheet v-else-if="isMobile" v-model:open="openMobile" v-bind="attrs">
    <SheetContent
      data-sidebar="sidebar"
      data-mobile="true"
      class="w-[--sidebar-width] bg-sidebar p-0 text-sidebar-foreground [&>button]:hidden"
      :style="{ '--sidebar-width': SIDEBAR_WIDTH_MOBILE }"
      :side="side"
    >
      <div class="flex h-full w-full flex-col">
        <slot />
      </div>
    </SheetContent>
  </Sheet>

  <!-- Case 3: Desktop sidebar -->
  <div
    v-else
    ref="sidebarRef"
    class="group peer hidden md:block text-sidebar-foreground"
    :data-state="state"
    :data-collapsible="state === 'collapsed' ? collapsible : ''"
    :data-variant="variant"
    :data-side="side"
  >
    <!-- Spacer (sidebar gap) -->
    <div
      :class="[
        'duration-200 relative h-svh w-[--sidebar-width] bg-transparent transition-[width] ease-linear',
        'group-data-[collapsible=offcanvas]:w-0',
        'group-data-[side=right]:rotate-180',
        variant === 'floating' || variant === 'inset'
          ? 'group-data-[collapsible=icon]:w-[calc(var(--sidebar-width-icon)_+_theme(spacing.4))]'
          : 'group-data-[collapsible=icon]:w-[--sidebar-width-icon]',
      ]"
    />

    <!-- Actual sidebar -->
    <div
      :class="[
        'duration-200 fixed inset-y-0 z-10 hidden h-svh w-[--sidebar-width] transition-[left,right,width] ease-linear md:flex',
        side === 'left'
          ? 'left-0 group-data-[collapsible=offcanvas]:left-[calc(var(--sidebar-width)*-1)]'
          : 'right-0 group-data-[collapsible=offcanvas]:right-[calc(var(--sidebar-width)*-1)]',
        variant === 'floating' || variant === 'inset'
          ? 'p-2 group-data-[collapsible=icon]:w-[calc(var(--sidebar-width-icon)_+_theme(spacing.4)_+2px)]'
          : 'group-data-[collapsible=icon]:w-[--sidebar-width-icon] group-data-[side=left]:border-r group-data-[side=right]:border-l',
        className,
      ]"
      v-bind="attrs"
    >
      <div
        data-sidebar="sidebar"
        class="flex h-full w-full flex-col bg-sidebar group-data-[variant=floating]:rounded-lg group-data-[variant=floating]:border group-data-[variant=floating]:border-sidebar-border group-data-[variant=floating]:shadow"
      >
        <slot />
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, useAttrs } from 'vue'
import { useSidebar } from '@/components/hooks/useSidebar'
import Sheet from '@/components/ui/Sheet.vue'
import SheetContent from '@/components/ui/SheetContent.vue'

const props = defineProps<{
  side?: 'left' | 'right'
  variant?: 'sidebar' | 'floating' | 'inset'
  collapsible?: 'offcanvas' | 'icon' | 'none'
  className?: string
}>()

const attrs = useAttrs()
const sidebarRef = ref<HTMLDivElement | null>(null)

const { isMobile, state, openMobile } = useSidebar()

const SIDEBAR_WIDTH_MOBILE = '80vw'
const side = props.side ?? 'left'
const variant = props.variant ?? 'sidebar'
const collapsible = props.collapsible ?? 'offcanvas'
const className = props.className ?? ''
</script>
