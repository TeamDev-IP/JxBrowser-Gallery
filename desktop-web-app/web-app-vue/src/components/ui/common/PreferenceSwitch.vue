<template>
  <Switch
    v-model="localValue"
    :class="cn('hover:opacity-70', localValue ? 'bg-control-select' : 'bg-neutral-200')"
  />
</template>

<script setup lang="ts">
import { ref, watch } from 'vue'
import { cn } from '@/lib/utils'
import Switch from '@/components/ui/Switch.vue'

const props = defineProps<{
  isChecked: boolean
  onChange: (value: boolean) => void
}>()

const localValue = ref(props.isChecked)

watch(
  () => props.isChecked,
  (val) => {
    localValue.value = val
  }
)

watch(localValue, (val) => {
  props.onChange(val)
})
</script>
