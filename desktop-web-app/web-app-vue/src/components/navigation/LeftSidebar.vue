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
import { ref } from "vue";
import NavigationItem, {NavigationItemType} from "@/components/navigation/NavigationItem.vue";
import { Bell, Settings, SquareUser, SunMoon } from "lucide-vue-next";
import Sidebar from "@/components/ui/Sidebar.vue";
import SidebarMenu from "@/components/ui/SidebarMenu.vue";
import SidebarGroupContent from "@/components/ui/SidebarGroupContent.vue";
import SidebarGroup from "@/components/ui/SidebarGroup.vue";
import SidebarContent from "@/components/ui/SidebarContent.vue";
import SidebarGroupLabel from "@/components/ui/SidebarGroupLabel.vue";

const currentNavigation = ref<NavigationItemType>("Account");

function updateNavigation(navigation: NavigationItemType) {
  currentNavigation.value = navigation;
}
</script>
