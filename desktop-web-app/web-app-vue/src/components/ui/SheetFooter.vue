<template>
  <div
      :class="[
      'flex flex-col-reverse sm:flex-row sm:justify-end sm:space-x-2',
      className,
    ]"
  >
    <slot />
  </div>
</template>

<script setup lang="ts">
const props = defineProps<{ className?: string }>();
const className = props.className ?? "";
</script>
