<script setup lang="ts">
import {ref, watch, computed} from 'vue'
import Input from '@/components/ui/Input.vue'

interface Props {
  title: string
  modelValue: string
  isEmail?: boolean
}

const props = defineProps<Props>()
const emit = defineEmits<{
  (e: 'update:modelValue', value: string): void
}>()

const localValue = ref(props.modelValue)
const isValid = ref(true)

watch(() => props.modelValue, (val) => {
  localValue.value = val
})

const inputType = computed(() => props.isEmail ? 'email' : 'text')

const inputClasses = computed(() => {
  return [
    'w-full text-sm focus-visible:border-control-select transition-colors',
    !isValid.value ? 'bg-red-300 focus-visible:border-red-500' : ''
  ]
})

watch(localValue, (val) => {
  if (props.isEmail) {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
    isValid.value = emailRegex.test(val)
  }
})

function handleBlur() {
  if (!isValid.value) {
    localValue.value = props.modelValue
    isValid.value = true
    return
  }
  emit('update:modelValue', localValue.value)
}
</script>

<template>
  <div class="w-full items-center space-y-2">
    <div class="w-full sm:flex items-center space-y-1 justify-between">
      <p class="text-sm">{{ title }}</p>
      <div class="xs:w-full sm:w-[50%] lg:w-[30%]">
        <Input
            v-model="localValue"
            :type="inputType"
            :class="inputClasses.join(' ')"
            @blur="handleBlur"
        />
        <span v-if="!isValid" class="text-xs text-red-500">
          Please enter a valid email address.
        </span>
      </div>
    </div>
  </div>
</template>
