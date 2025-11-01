<template>
  <input
      ref="inputRef"
      :type="type"
      v-bind="$attrs"
      :class="inputClass"
      :value="modelValue"
      @input="handleInput"
  />
</template>

<script setup lang="ts">
import { ref, computed, defineProps, defineEmits, defineExpose } from 'vue'

const props = defineProps<{
  type?: string
  class?: string
  modelValue?: string
}>()

const emit = defineEmits<{
  (e: 'update:modelValue', value: string): void
}>()

const inputRef = ref<HTMLInputElement | null>(null)

defineExpose({ inputRef })

function cn(...classes: (string | undefined | false)[]) {
  return classes.filter(Boolean).join(' ')
}

const type = computed(() => props.type ?? 'text')

const inputClass = computed(() =>
    cn(
        "flex h-8 w-full rounded-md border focus:border-control-select bg-background px-2 py-2 text-base ring-offset-background file:border-0 file:bg-transparent file:text-sm file:font-medium file:text-foreground placeholder:text-muted-foreground focus-visible:outline-none focus-visible:ring-ring focus-visible:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50 md:text-sm",
        props.class
    )
)

function handleInput(event: Event) {
  const target = event.target as HTMLInputElement | null
  if (!target) return
  emit('update:modelValue', target.value)
}
</script>
