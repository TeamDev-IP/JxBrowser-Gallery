import SidebarProvider from "@/components/ui/SidebarProvider.vue";
import type { SidebarContext  } from "@/components/ui/sidebar-types";

export const sidebarContext: SidebarContext = (SidebarProvider as any).context;
