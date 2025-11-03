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
