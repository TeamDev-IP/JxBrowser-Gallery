<template>
  <div
      v-if="isActive"
      class="mt-2 ring-offset-background focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2"
  >
    <slot />
  </div>
</template>

<script setup lang="ts">
import { inject, computed } from 'vue'

interface Props {
  value: string
}

const props = defineProps<Props>()

const tabs = inject<{
  activeTab: { value: string }
}>('tabs')

const isActive = computed(() => tabs?.activeTab.value === props.value)
</script>