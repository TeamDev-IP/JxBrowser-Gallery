<template>
  <div ref="dropdownRef" class="relative inline-block">
    <div @click="toggle">
      <slot name="trigger">
        <Button>Menu</Button>
      </slot>
    </div>

    <Transition name="dropdown">
      <div
          v-if="isOpen"
          :class="dropdownClass"
      >
        <slot />
      </div>
    </Transition>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, onUnmounted } from 'vue'
import { cn } from '@/lib/utils'
import Button from './Button.vue'

interface Props {
  align?: 'start' | 'center' | 'end'
}

const props = withDefaults(defineProps<Props>(), {
  align: 'start'
})

const isOpen = ref(false)
const dropdownRef = ref<HTMLElement>()

const dropdownClass = computed(() => {
  return cn(
      'absolute z-50 min-w-[8rem] overflow-hidden rounded-md border bg-popover p-1 text-popover-foreground shadow-md',
      'animate-in fade-in-0 zoom-in-95',
      {
        'left-0': props.align === 'start',
        'left-1/2 -translate-x-1/2': props.align === 'center',
        'right-0': props.align === 'end'
      },
      'top-full mt-2'
  )
})

function toggle() {
  isOpen.value = !isOpen.value
}

function close() {
  isOpen.value = false
}

function handleClickOutside(event: MouseEvent) {
  if (!dropdownRef.value) return

  const target = event.target as Node
  if (!dropdownRef.value.contains(target)) {
    close()
  }
}

onMounted(() => {
  document.addEventListener('click', handleClickOutside)
})

onUnmounted(() => {
  document.removeEventListener('click', handleClickOutside)
})

defineExpose({ close })
</script>

<style scoped>
.dropdown-enter-active,
.dropdown-leave-active {
  transition: opacity 0.15s ease, transform 0.15s ease;
}

.dropdown-enter-from,
.dropdown-leave-to {
  opacity: 0;
  transform: translateY(-4px);
}
</style>