<script setup lang="ts">
import { inject } from "vue";
import { PanelLeft } from "lucide-vue-next";

const sidebar = inject("sidebar");
</script>

<template>
  <button
      class="h-7 w-7 flex items-center justify-center rounded-md hover:bg-sidebar-hover text-sidebar-foreground"
      @click="sidebar.toggleSidebar"
  >
    <PanelLeft />
    <span class="sr-only">Toggle Sidebar</span>
  </button>
</template>
