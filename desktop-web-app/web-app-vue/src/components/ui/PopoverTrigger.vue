<template>
  <div data-popover-trigger @click="togglePopover">
    <slot />
  </div>
</template>

<script setup lang="ts">
import { inject } from "vue"

const togglePopover = inject("togglePopover") as () => void
if (!togglePopover) throw new Error("PopoverTrigger must be used inside Popover")
</script>
