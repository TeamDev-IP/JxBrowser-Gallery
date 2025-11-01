
<template>
  <tr
      :class="[
      'border-b transition-colors hover:bg-muted/50 data-[state=selected]:bg-muted',
      clickable ? 'cursor-pointer' : ''
    ]"
      @click="onClick"
  >
    <slot />
  </tr>
</template>

<script setup lang="ts">
interface Props {
  clickable?: boolean
}

interface Emits {
  (e: 'click', event: MouseEvent): void
}

defineProps<Props>()
const emit = defineEmits<Emits>()

function onClick(event: MouseEvent) {
  emit('click', event)
}
</script>
