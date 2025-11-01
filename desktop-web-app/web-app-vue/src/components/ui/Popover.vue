<template>
  <div class="relative inline-block">
    <slot />
  </div>
</template>

<script setup lang="ts">
import { ref, provide, watch, defineProps, defineEmits } from 'vue'

interface Props {
  open?: boolean
}

const props = defineProps<Props>()
const emit = defineEmits<{ (e: 'update:open', value: boolean): void }>()

const isOpen = ref(props.open ?? false)

watch(() => props.open, val => {
  isOpen.value = val ?? false
})
watch(isOpen, val => {
  emit('update:open', val)
})

provide('popoverOpen', isOpen)
provide('togglePopover', () => {
  isOpen.value = !isOpen.value
})
</script>
