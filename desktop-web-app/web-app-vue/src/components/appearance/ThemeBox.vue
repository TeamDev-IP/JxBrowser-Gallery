<script setup lang="ts">
import type { FunctionalComponent } from 'vue'

interface Props {
  title: string
  icon: FunctionalComponent
  isSelected: boolean
  onSelect: () => void
}

const props = defineProps<Props>()
</script>

<template>
  <div
    :class="[
      'cursor-pointer rounded-lg p-2 items-center border-2 flex w-full justify-center gap-x-2 select-none transition-colors',
      props.isSelected ? 'border-control-select' : 'hover:border-control-hover',
    ]"
    @click="props.onSelect"
  >
    <component :is="props.icon" class="text-right" />
    <span class="justify-end text-center text-sm">{{ props.title }}</span>
  </div>
</template>
