<template>
  <div ref="selectRef" class="relative">
    <button type="button" :class="selectClass" :disabled="disabled" @click="toggle">
      <span class="block truncate">
        {{ selectedLabel || placeholder }}
      </span>
      <svg
        class="ml-2 h-4 w-4 shrink-0 opacity-50"
        fill="none"
        stroke="currentColor"
        viewBox="0 0 24 24"
      >
        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 9l-7 7-7-7" />
      </svg>
    </button>

    <Transition name="select">
      <div
        v-if="isOpen"
        class="absolute z-50 mt-1 w-full rounded-md border bg-popover text-popover-foreground shadow-md"
      >
        <div class="max-h-60 overflow-auto p-1">
          <div
            v-for="option in options"
            :key="option.value"
            :class="[
              'relative flex cursor-pointer select-none items-center rounded-sm py-1.5 pl-8 pr-2 text-sm outline-none',
              'hover:bg-accent hover:text-accent-foreground',
              { 'bg-accent text-accent-foreground': modelValue === option.value },
            ]"
            @click="selectOption(option)"
          >
            <span
              v-if="modelValue === option.value"
              class="absolute left-2 flex h-3.5 w-3.5 items-center justify-center"
            >
              <svg class="h-4 w-4" fill="currentColor" viewBox="0 0 20 20">
                <path
                  fill-rule="evenodd"
                  d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z"
                  clip-rule="evenodd"
                />
              </svg>
            </span>
            <span>{{ option.label }}</span>
          </div>
        </div>
      </div>
    </Transition>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, onUnmounted } from 'vue'
import { cn } from '@/lib/utils'

interface Option {
  label: string
  value: string | number
}

interface Props {
  modelValue?: string | number
  options?: Option[]
  placeholder?: string
  disabled?: boolean
}

interface Emits {
  (e: 'update:modelValue', value: string | number): void
}

const props = withDefaults(defineProps<Props>(), {
  options: () => [],
  placeholder: 'Select an option...',
})

const emit = defineEmits<Emits>()

const isOpen = ref(false)
const selectRef = ref<HTMLElement>()

const selectClass = computed(() => {
  return cn(
    'flex h-10 w-full items-center justify-between rounded-md border border-input',
    'bg-background px-3 py-2 text-sm ring-offset-background',
    'placeholder:text-muted-foreground',
    'focus:outline-none focus:ring-2 focus:ring-ring focus:ring-offset-2',
    'disabled:cursor-not-allowed disabled:opacity-50'
  )
})

const selectedLabel = computed(() => {
  const option = props.options.find((opt) => opt.value === props.modelValue)
  return option?.label || ''
})

function toggle() {
  if (!props.disabled) {
    isOpen.value = !isOpen.value
  }
}

function selectOption(option: Option) {
  emit('update:modelValue', option.value)
  isOpen.value = false
}

function handleClickOutside(event: MouseEvent) {
  if (!selectRef.value) return

  const target = event.target as Node
  if (!selectRef.value.contains(target)) {
    isOpen.value = false
  }
}

onMounted(() => {
  document.addEventListener('click', handleClickOutside)
})

onUnmounted(() => {
  document.removeEventListener('click', handleClickOutside)
})
</script>

<style scoped>
.select-enter-active,
.select-leave-active {
  transition:
    opacity 0.15s ease,
    transform 0.15s ease;
}

.select-enter-from,
.select-leave-to {
  opacity: 0;
  transform: translateY(-4px);
}
</style>
