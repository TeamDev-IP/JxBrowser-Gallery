<script setup lang="ts">
import { ref } from 'vue'
import { Command as CmdkPrimitive } from 'cmdk'
import { cn } from '@/lib/utils'

defineProps<{ className?: string }>()
const sepRef = ref<HTMLElement>()
</script>

<template>
  <CmdkPrimitive.Separator ref="sepRef" :class="cn('-mx-1 h-px bg-border', className)" v-bind="$attrs" />
</template>
