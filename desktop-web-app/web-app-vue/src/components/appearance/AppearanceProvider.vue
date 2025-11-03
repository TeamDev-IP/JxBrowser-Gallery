<script setup lang="ts">
import ThemeProvider from '@/components/appearance/ThemeProvider.vue'
import FontSizeProvider from '@/components/appearance/FontSizeProvider.vue'
</script>

<template>
  <ThemeProvider>
    <FontSizeProvider>
      <slot />
    </FontSizeProvider>
  </ThemeProvider>
</template>
