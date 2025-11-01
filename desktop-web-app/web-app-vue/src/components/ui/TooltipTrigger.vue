<script setup lang="ts">
import { inject } from "vue";
import { tooltipContextKey } from "./TooltipProvider.vue";

const context = inject<{ tooltipOpen: any }>(tooltipContextKey);
if (!context) throw new Error("TooltipTrigger must be used inside a Tooltip");

const openTooltip = () => context.tooltipOpen.value = true;
const closeTooltip = () => context.tooltipOpen.value = false;
</script>

<template>
  <div @mouseenter="openTooltip" @mouseleave="closeTooltip">
    <slot />
  </div>
</template>
