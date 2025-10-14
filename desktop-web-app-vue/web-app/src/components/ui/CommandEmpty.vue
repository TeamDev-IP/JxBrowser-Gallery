<script setup lang="ts">
import { ref } from 'vue'
import { Command as CmdkPrimitive } from 'cmdk'

const refEl = ref<HTMLElement>()
</script>

<template>
  <CmdkPrimitive.Empty ref="refEl" class="py-6 text-center text-sm" v-bind="$attrs">
    <slot />
  </CmdkPrimitive.Empty>
</template>