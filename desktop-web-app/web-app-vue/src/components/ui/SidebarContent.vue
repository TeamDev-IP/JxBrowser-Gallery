<script setup lang="ts">
import { ref, defineProps, useAttrs } from "vue";

const props = defineProps<{
  className?: string;
}>();

const attrs = useAttrs();
const divRef = ref<HTMLDivElement | null>(null);
</script>

<template>
  <div
      ref="divRef"
      data-sidebar="content"
      :class="[
      'flex min-h-0 flex-1 flex-col gap-2 overflow-auto group-data-[collapsible=icon]:overflow-hidden',
      props.className
    ]"
      v-bind="attrs"
  >
    <slot />
  </div>
</template>