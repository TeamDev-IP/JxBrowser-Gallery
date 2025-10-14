<script setup lang="ts">
import { ref, defineProps, useAttrs } from "vue";

const props = defineProps<{
  className?: string;
}>();

const attrs = useAttrs();
const ulRef = ref<HTMLUListElement | null>(null);
</script>

<template>
  <ul
      ref="ulRef"
      data-sidebar="menu"
      :class="['flex w-full min-w-0 flex-col gap-1', props.className]"
      v-bind="attrs"
  >
    <slot />
  </ul>
</template>