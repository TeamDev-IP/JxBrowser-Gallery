<template>
  <div role="radiogroup" class="space-y-2">
    <slot />
  </div>
</template>

<script setup lang="ts">
import { provide } from 'vue'

interface Props {
  modelValue?: string | number
  name?: string
}

interface Emits {
  (e: 'update:modelValue', value: string | number): void
}

const props = defineProps<Props>()
const emit = defineEmits<Emits>()

function updateValue(value: string | number) {
  emit('update:modelValue', value)
}

provide('radioGroup', {
  modelValue: props.modelValue,
  name: props.name,
  updateValue
})
</script>