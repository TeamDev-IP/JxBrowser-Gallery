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
    data-sidebar="group"
    :class="['relative flex w-full min-w-0 flex-col p-2', props.className]"
    v-bind="attrs"
  >
    <slot />
  </div>
</template>
