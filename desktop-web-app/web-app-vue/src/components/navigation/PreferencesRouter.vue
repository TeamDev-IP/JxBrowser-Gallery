<!--
  Copyright 2026, TeamDev

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
  <div class="preferences-router h-full bg-gray-50 dark:bg-gray-900">
    <Transition
      :name="transitionName"
      mode="out-in"
      @before-enter="onBeforeEnter"
      @after-leave="onAfterLeave"
    >
      <router-view v-slot="{ Component, route }">
        <div :key="route.path" class="route-container">
          <KeepAlive :max="5">
            <component :is="Component" />
          </KeepAlive>
        </div>
      </router-view>
    </Transition>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, watch } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()

const isTransitioning = ref(false)
const transitionName = ref('slide-left')

const routeOrder = ['/general', '/account', '/appearance', '/notifications']

// Get route index
function getRouteIndex(path: string): number {
  const index = routeOrder.indexOf(path)
  return index === -1 ? 0 : index
}

// Previous route tracking
let previousRoutePath = route.path

// Watch route changes for transition direction
watch(
  () => route.path,
  (newPath, oldPath) => {
    const previousIndex = getRouteIndex(oldPath || previousRoutePath)
    const currentIndex = getRouteIndex(newPath)

    // Determine transition direction based on the route order.
    if (currentIndex > previousIndex) {
      transitionName.value = 'slide-left'
    } else if (currentIndex < previousIndex) {
      transitionName.value = 'slide-right'
    } else {
      transitionName.value = 'fade'
    }

    previousRoutePath = newPath
  }
)

// Transition lifecycle hooks
function onBeforeEnter() {
  isTransitioning.value = true
}

function onAfterLeave() {
  isTransitioning.value = false
}

// Computed property for loading state
const isLoading = computed(() => isTransitioning.value)

// Expose loading state if needed
defineExpose({
  isLoading,
})
</script>

<style scoped>
.preferences-router {
  position: relative;
  width: 100%;
  height: 100%;
}

.route-container {
  width: 100%;
  height: 100%;
  position: relative;
}

/* Slide Left Transition - Moving forward */
.slide-left-enter-active {
  animation: slideInFromRight 0.3s cubic-bezier(0.4, 0, 0.2, 1);
}

.slide-left-leave-active {
  animation: slideOutToLeft 0.3s cubic-bezier(0.4, 0, 0.2, 1);
}

@keyframes slideInFromRight {
  from {
    opacity: 0;
    transform: translateX(30px);
  }
  to {
    opacity: 1;
    transform: translateX(0);
  }
}

@keyframes slideOutToLeft {
  from {
    opacity: 1;
    transform: translateX(0);
  }
  to {
    opacity: 0;
    transform: translateX(-30px);
  }
}

/* Slide Right Transition - Moving backward */
.slide-right-enter-active {
  animation: slideInFromLeft 0.3s cubic-bezier(0.4, 0, 0.2, 1);
}

.slide-right-leave-active {
  animation: slideOutToRight 0.3s cubic-bezier(0.4, 0, 0.2, 1);
}

@keyframes slideInFromLeft {
  from {
    opacity: 0;
    transform: translateX(-30px);
  }
  to {
    opacity: 1;
    transform: translateX(0);
  }
}

@keyframes slideOutToRight {
  from {
    opacity: 1;
    transform: translateX(0);
  }
  to {
    opacity: 0;
    transform: translateX(30px);
  }
}

/* Fade Transition - Fallback */
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.25s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}

/* Scale transition alternative */
.scale-enter-active,
.scale-leave-active {
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
}

.scale-enter-from {
  opacity: 0;
  transform: scale(0.95);
}

.scale-leave-to {
  opacity: 0;
  transform: scale(1.05);
}

/* Loading overlay (optional) */
.preferences-router.loading::after {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(255, 255, 255, 0.5);
  backdrop-filter: blur(2px);
  z-index: 10;
  pointer-events: none;
}

/* Dark mode adjustments */
.dark .preferences-router.loading::after {
  background: rgba(0, 0, 0, 0.5);
}

/* Reduced motion preference */
@media (prefers-reduced-motion: reduce) {
  .slide-left-enter-active,
  .slide-left-leave-active,
  .slide-right-enter-active,
  .slide-right-leave-active {
    animation-duration: 0.01ms !important;
  }

  .fade-enter-active,
  .fade-leave-active {
    transition-duration: 0.01ms !important;
  }
}
</style>
