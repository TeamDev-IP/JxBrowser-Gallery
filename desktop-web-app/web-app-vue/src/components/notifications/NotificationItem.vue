<template>
  <div class="flex items-center justify-between p-4 border border-gray-200 rounded-lg hover:bg-gray-50 transition-colors">
    <div class="flex items-center space-x-3">
      <span class="text-2xl">{{ icon }}</span>
      <div>
        <p class="font-medium">{{ label }}</p>
        <p class="text-sm text-gray-500">{{ description }}</p>
      </div>
    </div>

    <label class="relative inline-flex items-center cursor-pointer">
      <input
          type="checkbox"
          :checked="enabled"
          class="sr-only peer"
          @change="$emit('toggle')"
      />
      <div class="w-11 h-6 bg-gray-200 peer-focus:outline-none peer-focus:ring-4 peer-focus:ring-blue-300 rounded-full peer peer-checked:after:translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-[2px] after:left-[2px] after:bg-white after:border-gray-300 after:border after:rounded-full after:h-5 after:w-5 after:transition-all peer-checked:bg-blue-600"></div>
    </label>
  </div>
</template>

<script setup lang="ts">
interface Props {
  label: string
  description: string
  icon: string
  enabled: boolean
}

defineProps<Props>()
defineEmits<{
  (e: 'toggle'): void
}>()
</script>