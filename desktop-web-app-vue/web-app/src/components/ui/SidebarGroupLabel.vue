<script setup lang="ts">
import { ref, defineProps, useAttrs } from "vue";

const props = defineProps<{
  className?: string;
  asChild?: boolean;
}>();

const attrs = useAttrs();
const divRef = ref<HTMLElement | null>(null);
</script>

<template>
  <component
      :is="props.asChild ? 'slot' : 'div'"
      ref="divRef"
      data-sidebar="group-label"
      :class="[
      'duration-200 flex h-8 shrink-0 items-center rounded-md px-2 text-xs font-medium text-sidebar-foreground/70 outline-none ring-sidebar-ring transition-[margin,opa] ease-linear focus-visible:ring-2 [&>svg]:size-4 [&>svg]:shrink-0',
      'group-data-[collapsible=icon]:-mt-8 group-data-[collapsible=icon]:opacity-0',
      props.className
    ]"
      v-bind="attrs"
  >
    <slot />
  </component>
</template>