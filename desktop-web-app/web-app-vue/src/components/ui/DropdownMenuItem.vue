<template>
  <div
      :class="itemClass"
      @click="onClick"
  >
    <slot />
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { cn } from '@/lib/utils'

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
        'text-destructive hover:text-destructive': props.destructive
      }
  )
})

function onClick(event: MouseEvent) {
  if (!props.disabled) {
    emit('click', event)
  }
}
</script>