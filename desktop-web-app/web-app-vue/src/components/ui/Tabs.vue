<template>
  <div class="w-full">
    <slot />
  </div>
</template>

<script setup lang="ts">
import { provide, ref, watch } from 'vue'

interface Props {
  modelValue?: string
  defaultValue?: string
}

interface Emits {
  (e: 'update:modelValue', value: string): void
}

const props = defineProps<Props>()
const emit = defineEmits<Emits>()

const activeTab = ref(props.modelValue || props.defaultValue || '')

watch(() => props.modelValue, (val) => {
  if (val) activeTab.value = val
})

function setActiveTab(value: string) {
  activeTab.value = value
  emit('update:modelValue', value)
}

provide('tabs', {
  activeTab,
  setActiveTab
})
</script>
