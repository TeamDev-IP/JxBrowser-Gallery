<template>
  <router-link
      :to="url"
      class="nav-item"
      :class="{ active: isActive }"
      @click="$emit('select')"
  >
    <component :is="icon" class="icon" />
    <span class="label">{{ type }}</span>
  </router-link>
</template>

<script setup lang="ts">
import {computed} from 'vue'
import { useRoute } from 'vue-router'
import {LucideIcon} from "lucide-vue-next";

/**
 * Available navigation items.
 */
export type NavigationItemType = "Account" | "General" | "Appearance" | "Notifications";

interface Props {
  icon: LucideIcon;
  type: string
  url: string
  isSelected: boolean
}

const props = defineProps<Props>()
const route = useRoute()


const isActive = computed(() => route.path === props.url)
</script>

<style scoped>
.nav-item {
  display: flex;
  align-items: center;
  padding: 0.75rem 1rem;
  border-radius: 0.5rem;
  text-decoration: none;
  color: #374151;
  transition: all 0.2s;
}

.nav-item:hover {
  background-color: #e5e7eb;
}

.nav-item.active {
  background-color: #3b82f6;
  color: white;
}

.icon {
  margin-right: 0.75rem;
  font-size: 1.25rem;
}

.label {
  font-weight: 500;
}
</style>