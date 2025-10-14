<script setup lang="ts">
import { ref } from 'vue'
import { Command as CmdkPrimitive } from 'cmdk'
import { Search } from 'lucide-vue-next'
import { cn } from '@/lib/utils'

defineProps<{ className?: string }>()

const inputRef = ref<HTMLInputElement>()
</script>

<template>
  <div class="flex items-center border-b px-3" data-cmdk-input-wrapper="">
    <Search class="mr-2 h-4 w-4 shrink-0 opacity-50" />
    <CmdkPrimitive.Input
        ref="inputRef"
        :class="cn('flex h-11 w-full rounded-md bg-transparent py-3 text-sm outline-none placeholder:text-muted-foreground disabled:cursor-not-allowed disabled:opacity-50', className)"
        v-bind="$attrs"
    />
  </div>
</template>