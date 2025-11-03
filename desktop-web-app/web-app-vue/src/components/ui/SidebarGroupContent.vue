<script setup lang="ts">
import { ref, defineProps, useAttrs } from 'vue'

const props = defineProps<{
  className?: string
}>()

const attrs = useAttrs()
const divRef = ref<HTMLDivElement | null>(null)
</script>

<template>
  <div
    ref="divRef"
    data-sidebar="group-content"
    :class="['w-full text-sm', props.className]"
    v-bind="attrs"
  >
    <slot />
  </div>
</template>
