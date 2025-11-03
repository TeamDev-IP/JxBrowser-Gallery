<script setup lang="ts">
import { ref, provide } from 'vue'
import { tooltipContextKey } from './TooltipProvider.vue'

const tooltipOpen = ref(false)
provide(tooltipContextKey, { tooltipOpen })
</script>

<template>
  <div>
    <slot />
  </div>
</template>
