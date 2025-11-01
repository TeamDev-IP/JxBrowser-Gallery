<template>
  <div
      :class="['flex flex-col space-y-2 text-center sm:text-left', className]"
  >
    <slot />
  </div>
</template>

<script setup lang="ts">
const props = defineProps<{ className?: string }>();
const className = props.className ?? "";
</script>
