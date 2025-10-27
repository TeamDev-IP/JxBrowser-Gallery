<script setup lang="ts">
import { provide, ref, computed, onMounted, onUnmounted } from "vue";
import { useMobile } from "@/components/hooks/useMobile";
import TooltipProvider from "@/components/ui/TooltipProvider.vue";
import {SIDEBAR_KEY} from "@/components/hooks/useSidebar.ts";

export interface SidebarContext {
  state: "expanded" | "collapsed";
  open: boolean;
  setOpen: (open: boolean | ((val: boolean) => boolean)) => void;
  isMobile: boolean;
  openMobile: boolean;
  setOpenMobile: (v: boolean) => void;
  toggleSidebar: () => void;
}

const SIDEBAR_COOKIE_NAME = "sidebar:state";
const SIDEBAR_COOKIE_MAX_AGE = 60 * 60 * 24 * 7;
const SIDEBAR_KEYBOARD_SHORTCUT = "b";

const SIDEBAR_WIDTH = "16rem";
const SIDEBAR_WIDTH_ICON = "3rem";

const props = defineProps<{
  defaultOpen?: boolean;
  open?: boolean;
  onOpenChange?: (open: boolean) => void;
  className?: string;
  style?: Record<string, string | number>;
}>();

const emit = defineEmits(["update:open"]);

const mobile = useMobile();
const openMobile = ref(false);
const _open = ref(props.defaultOpen ?? true);
const open = computed(() => props.open ?? _open.value);

function setOpen(value: boolean | ((val: boolean) => boolean)) {
  const newValue = typeof value === "function" ? value(open.value) : value;
  if (props.onOpenChange) props.onOpenChange(newValue);
  else _open.value = newValue;
  emit("update:open", newValue);
  document.cookie = `${SIDEBAR_COOKIE_NAME}=${newValue}; path=/; max-age=${SIDEBAR_COOKIE_MAX_AGE}`;
}

function toggleSidebar() {
  if (mobile.isMobile.value) openMobile.value = !openMobile.value;
  else setOpen((v) => !v);
}

const state = computed<"expanded" | "collapsed">(() => (open.value ? "expanded" : "collapsed"));

provide(SIDEBAR_KEY, {
  get state() {
    return state.value;
  },
  get open() {
    return open.value;
  },
  setOpen,
  get isMobile() {
    return mobile.isMobile.value;
  },
  get openMobile() {
    return openMobile.value;
  },
  setOpenMobile: (v: boolean) => (openMobile.value = v),
  toggleSidebar,
});

onMounted(() => {
  const handleKey = (e: KeyboardEvent) => {
    if (e.key.toLowerCase() === SIDEBAR_KEYBOARD_SHORTCUT && (e.metaKey || e.ctrlKey)) {
      e.preventDefault();
      toggleSidebar();
    }
  };
  window.addEventListener("keydown", handleKey);
  onUnmounted(() => window.removeEventListener("keydown", handleKey));
});
</script>

<template>
  <TooltipProvider :delay="0">
    <div
        class="group/sidebar-wrapper flex min-h-svh w-full has-[[data-variant=inset]]:bg-sidebar"
        :class="props.className"
        :style="{
        '--sidebar-width': SIDEBAR_WIDTH,
        '--sidebar-width-icon': SIDEBAR_WIDTH_ICON,
        ...props.style
      }"
    >
      <slot />
    </div>
  </TooltipProvider>
</template>