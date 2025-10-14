<template>
  <form @submit.prevent="onSubmit">
    <slot />
  </form>
</template>

<script setup lang="ts">
interface Emits {
  (e: 'submit', event: Event): void
}

const emit = defineEmits<Emits>()

function onSubmit(event: Event) {
  emit('submit', event)
}
</script>