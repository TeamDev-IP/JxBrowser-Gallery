import { ref, computed, provide, inject, InjectionKey } from "vue";

export type SidebarState = "expanded" | "collapsed";

export type SidebarContext = {
  state: SidebarState;
  open: boolean;
  setOpen: (open: boolean) => void;
  openMobile: boolean;
  setOpenMobile: (open: boolean) => void;
  isMobile: boolean;
  toggleSidebar: () => void;
};

export const SIDEBAR_KEY: InjectionKey<SidebarContext> = Symbol("SidebarContext");

export function provideSidebar(isMobile: boolean) {
  const open = ref(false);
  const openMobile = ref(false);
  const state = computed<SidebarState>(() =>
    open.value ? "expanded" : "collapsed"
  );

  const setOpen = (val: boolean) => (open.value = val);
  const setOpenMobile = (val: boolean) => (openMobile.value = val);
  const toggleSidebar = () => (open.value = !open.value);

  provide(SIDEBAR_KEY, {
    get state() {
      return state.value;
    },
    get open() {
      return open.value;
    },
    setOpen,
    get openMobile() {
      return openMobile.value;
    },
    setOpenMobile,
    isMobile,
    toggleSidebar,
  });
}

export function useSidebar(): SidebarContext {
  const context = inject(SIDEBAR_KEY);
  if (!context) {
    throw new Error("useSidebar must be used within a SidebarProvider");
  }
  return context;
}