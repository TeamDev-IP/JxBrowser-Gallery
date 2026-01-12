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
  <component
    :is="asChild ? 'slot' : 'button'"
    ref="buttonRef"
    data-sidebar="menu-button"
    :data-size="size"
    :data-active="isActive"
    :class="classes"
    v-bind="attrs"
  >
    <slot />
  </component>

  <Tooltip v-if="tooltip">
    <TooltipTrigger asChild>
      <component
        :is="asChild ? 'slot' : 'button'"
        ref="buttonRef"
        data-sidebar="menu-button"
        :data-size="size"
        :data-active="isActive"
        :class="classes"
        v-bind="attrs"
      >
        <slot />
      </component>
    </TooltipTrigger>

    <TooltipContent v-if="showTooltip" side="right" align="center" v-bind="tooltipProps">
      <template v-if="typeof tooltip === 'string'">
        {{ tooltip }}
      </template>
      <template v-else>
        <slot name="tooltip" />
      </template>
    </TooltipContent>
  </Tooltip>
</template>

<script setup lang="ts">
import {computed, ref, useAttrs} from 'vue'
import {cn} from '@/lib/utils'
import {useSidebar} from '@/components/hooks/useSidebar.ts'
import {cva} from 'class-variance-authority'
import TooltipContent from '@/components/ui/TooltipContent.vue'
import TooltipTrigger from '@/components/ui/TooltipTrigger.vue'
import Tooltip from '@/components/ui/Tooltip.vue'

export const sidebarMenuButtonVariants = cva(
  'peer/menu-button flex w-full items-center gap-2 overflow-hidden rounded-md p-2 text-left text-sm outline-none ring-sidebar-ring transition-[width,height,padding] hover:bg-sidebar-hover hover:text-sidebar-hover-foreground focus-visible:ring-2 active:bg-sidebar-accent active:text-sidebar-accent-foreground disabled:pointer-events-none disabled:opacity-50 group-has-[[data-sidebar=menu-action]]/menu-item:pr-8 aria-disabled:pointer-events-none aria-disabled:opacity-50 data-[active=true]:bg-sidebar-accent data-[active=true]:text-sidebar-accent-foreground data-[state=open]:hover:bg-sidebar-hover data-[state=open]:hover:text-sidebar-accent-foreground group-data-[collapsible=icon]:!size-8 group-data-[collapsible=icon]:!p-2 [&>span:last-child]:truncate [&>svg]:size-4 [&>svg]:shrink-0',
  {
    variants: {
      variant: {
        default: 'hover:bg-sidebar-hover hover:text-sidebar-hover-foreground',
        outline:
          'bg-background shadow-[0_0_0_1px_hsl(var(--sidebar-border))] hover:bg-sidebar-hover hover:text-sidebar-hover-foreground hover:shadow-[0_0_0_1px_hsl(var(--sidebar-accent))]',
      },
      size: {
        default: 'h-8 text-sm',
        sm: 'h-7 text-xs',
        lg: 'h-12 text-sm group-data-[collapsible=icon]:!p-0',
      },
    },
    defaultVariants: {
      variant: 'default',
      size: 'default',
    },
  }
)

export type SidebarMenuButtonVariant = 'default' | 'outline'
export type SidebarMenuButtonSize = 'default' | 'sm' | 'lg'

interface Props {
  asChild?: boolean
  isActive?: boolean
  tooltip?: string | Record<string, any>
  variant?: SidebarMenuButtonVariant
  size?: SidebarMenuButtonSize
  className?: string
}

const props = withDefaults(defineProps<Props>(), {
  asChild: false,
  isActive: false,
  variant: 'default',
  size: 'default',
})

const attrs = useAttrs()
const buttonRef = ref<HTMLButtonElement | null>(null)
const { isMobile, state } = useSidebar()

const classes = computed(() =>
  cn(
    sidebarMenuButtonVariants({
      variant: props.variant,
      size: props.size,
    }),
    props.className
  )
)

const tooltipProps = computed(() => {
  if (typeof props.tooltip === 'string') return { children: props.tooltip }
  return props.tooltip || {}
})

const showTooltip = computed(() => {
  return props.tooltip && state === 'collapsed' && !isMobile
})
</script>
