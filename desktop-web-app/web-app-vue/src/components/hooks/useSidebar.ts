/*
 * Copyright 2026, TeamDev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
import {computed, inject, InjectionKey, provide, ref} from 'vue'

export type SidebarState = 'expanded' | 'collapsed'

export type SidebarContext = {
  state: SidebarState
  open: boolean
  setOpen: (open: boolean) => void
  openMobile: boolean
  setOpenMobile: (open: boolean) => void
  isMobile: boolean
  toggleSidebar: () => void
}

export const SIDEBAR_KEY: InjectionKey<SidebarContext> = Symbol('SidebarContext')

export function provideSidebar(isMobile: boolean) {
  const open = ref(false)
  const openMobile = ref(false)
  const state = computed<SidebarState>(() => (open.value ? 'expanded' : 'collapsed'))

  const setOpen = (val: boolean) => (open.value = val)
  const setOpenMobile = (val: boolean) => (openMobile.value = val)
  const toggleSidebar = () => (open.value = !open.value)

  provide(SIDEBAR_KEY, {
    get state() {
      return state.value
    },
    get open() {
      return open.value
    },
    setOpen,
    get openMobile() {
      return openMobile.value
    },
    setOpenMobile,
    isMobile,
    toggleSidebar,
  })
}

export function useSidebar(): SidebarContext {
  const context = inject(SIDEBAR_KEY)
  if (!context) {
    throw new Error('useSidebar must be used within a SidebarProvider')
  }
  return context
}
