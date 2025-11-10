<!--
  Copyright (c) 2025 TeamDev

  Permission is hereby granted, free of charge, to any person obtaining a copy
  of this software and associated documentation files (the "Software"), to deal
  in the Software without restriction, including without limitation the rights
  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
  copies of the Software, and to permit persons to whom the Software is
  furnished to do so, subject to the following conditions:

  The above copyright notice and this permission notice shall be included in all
  copies or substantial portions of the Software.

  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
  SOFTWARE.
-->

<template>
  <router-link :to="url" class="nav-item" :class="{ active: isActive }" @click="$emit('select')">
    <component :is="icon" class="icon" />
    <span class="label">{{ type }}</span>
  </router-link>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useRoute } from 'vue-router'
import { LucideIcon } from 'lucide-vue-next'

/**
 * Available navigation items.
 */
export type NavigationItemType = 'Account' | 'General' | 'Appearance' | 'Notifications'

interface Props {
  icon: LucideIcon
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
  padding: 0.3rem 0.75rem;
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
  width: 18px;
}

.label {
  font-weight: 500;
}
</style>
