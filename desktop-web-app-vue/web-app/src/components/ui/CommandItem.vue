<template>
  <div
      class="cursor-pointer px-2 py-1.5 text-sm hover:bg-gray-100 rounded-sm flex justify-between items-center"
      @click="$emit('select', key)"
  >
    <slot />
  </div>
</template>

<script setup lang="ts">
import { defineProps } from "vue"

const props = defineProps<{ key: string | number }>()
const { key } = props
</script>