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
  <Sidebar class="!h-auto" collapsible="none" side="left">
    <SidebarContent class="p-4">
      <SidebarGroup />
      <SidebarGroupContent>
        <SidebarMenu>
          <NavigationItem
            type="Account"
            url="/"
            :icon="SquareUser"
            :isSelected="currentNavigation === 'Account'"
            @select="updateNavigation('Account')"
          />
        </SidebarMenu>
      </SidebarGroupContent>

      <SidebarGroupLabel class="pt-3">Preferences</SidebarGroupLabel>

      <SidebarGroupContent>
        <SidebarMenu>
          <NavigationItem
            type="General"
            url="/prefs/general"
            :icon="Settings"
            :isSelected="currentNavigation === 'General'"
            @select="updateNavigation('General')"
          />
          <NavigationItem
            type="Appearance"
            url="/prefs/appearance"
            :icon="SunMoon"
            :isSelected="currentNavigation === 'Appearance'"
            @select="updateNavigation('Appearance')"
          />
          <NavigationItem
            type="Notifications"
            url="/prefs/notifications"
            :icon="Bell"
            :isSelected="currentNavigation === 'Notifications'"
            @select="updateNavigation('Notifications')"
          />
        </SidebarMenu>
      </SidebarGroupContent>

      <SidebarGroup />
    </SidebarContent>
  </Sidebar>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import NavigationItem, { NavigationItemType } from '@/components/navigation/NavigationItem.vue'
import { Bell, Settings, SquareUser, SunMoon } from 'lucide-vue-next'
import Sidebar from '@/components/ui/Sidebar.vue'
import SidebarMenu from '@/components/ui/SidebarMenu.vue'
import SidebarGroupContent from '@/components/ui/SidebarGroupContent.vue'
import SidebarGroup from '@/components/ui/SidebarGroup.vue'
import SidebarContent from '@/components/ui/SidebarContent.vue'
import SidebarGroupLabel from '@/components/ui/SidebarGroupLabel.vue'

const currentNavigation = ref<NavigationItemType>('Account')

function updateNavigation(navigation: NavigationItemType) {
  currentNavigation.value = navigation
}
</script>
