import { ref, onMounted, onUnmounted, computed } from 'vue'

/**
 * Breakpoints based on Tailwind CSS defaults
 */
export const MOBILE_BREAKPOINT = 768 // md breakpoint

export function useMobile(breakpoint: number = MOBILE_BREAKPOINT) {
  const windowWidth = ref(0)

  /**
   * Check if current viewport is mobile
   */
  const isMobile = computed(() => windowWidth.value < breakpoint)

  /**
   * Check if current viewport is tablet
   */
  const isTablet = computed(() => windowWidth.value >= breakpoint && windowWidth.value < 1024)

  /**
   * Check if current viewport is desktop
   */
  const isDesktop = computed(() => windowWidth.value >= 1024)

  /**
   * Get current device type
   */
  const deviceType = computed(() => {
    if (isMobile.value) return 'mobile'
    if (isTablet.value) return 'tablet'
    return 'desktop'
  })

  /**
   * Update window width
   */
  const updateWidth = () => {
    windowWidth.value = window.innerWidth
  }

  /**
   * Check if device has touch capability
   */
  const isTouchDevice = computed(() => {
    return 'ontouchstart' in window || navigator.maxTouchPoints > 0
  })

  /**
   * Get viewport orientation
   */
  const orientation = computed(() => {
    return windowWidth.value > window.innerHeight ? 'landscape' : 'portrait'
  })

  onMounted(() => {
    updateWidth()
    window.addEventListener('resize', updateWidth)
  })

  onUnmounted(() => {
    window.removeEventListener('resize', updateWidth)
  })

  return {
    isMobile,
    isTablet,
    isDesktop,
    deviceType,
    isTouchDevice,
    orientation,
    windowWidth,
  }
}

/**
 * Extended version with more features
 */
export function useResponsive() {
  const { isMobile, isTablet, isDesktop, deviceType, isTouchDevice, orientation, windowWidth } =
    useMobile()

  /**
   * Breakpoint helpers
   */
  const breakpoints = {
    xs: 0, // Extra small devices
    sm: 640, // Small devices
    md: 768, // Medium devices (tablets)
    lg: 1024, // Large devices (desktops)
    xl: 1280, // Extra large devices
    '2xl': 1536, // 2X Extra large devices
  }

  /**
   * Check if viewport is at least the specified breakpoint
   */
  const isAtLeast = (breakpoint: keyof typeof breakpoints) => {
    return computed(() => windowWidth.value >= breakpoints[breakpoint])
  }

  /**
   * Check if viewport is at most the specified breakpoint
   */
  const isAtMost = (breakpoint: keyof typeof breakpoints) => {
    return computed(() => windowWidth.value < breakpoints[breakpoint])
  }

  /**
   * Check if viewport is between two breakpoints
   */
  const isBetween = (min: keyof typeof breakpoints, max: keyof typeof breakpoints) => {
    return computed(
      () => windowWidth.value >= breakpoints[min] && windowWidth.value < breakpoints[max]
    )
  }

  /**
   * Get current breakpoint name
   */
  const currentBreakpoint = computed(() => {
    if (windowWidth.value < breakpoints.sm) return 'xs'
    if (windowWidth.value < breakpoints.md) return 'sm'
    if (windowWidth.value < breakpoints.lg) return 'md'
    if (windowWidth.value < breakpoints.xl) return 'lg'
    if (windowWidth.value < breakpoints['2xl']) return 'xl'
    return '2xl'
  })

  return {
    isMobile,
    isTablet,
    isDesktop,
    deviceType,
    isTouchDevice,
    orientation,
    windowWidth,
    breakpoints,
    isAtLeast,
    isAtMost,
    isBetween,
    currentBreakpoint,
  }
}

/**
 * Hook for detecting mobile device by user agent
 */
export function useDeviceDetection() {
  const userAgent = navigator.userAgent.toLowerCase()

  const isIOS = computed(() => {
    return /iphone|ipad|ipod/.test(userAgent)
  })

  const isAndroid = computed(() => {
    return /android/.test(userAgent)
  })

  const isMobileDevice = computed(() => {
    return isIOS.value || isAndroid.value || /mobile/.test(userAgent)
  })

  const isTabletDevice = computed(() => {
    return /ipad|android(?!.*mobile)/.test(userAgent)
  })

  const browser = computed(() => {
    if (/firefox/.test(userAgent)) return 'firefox'
    if (/chrome/.test(userAgent)) return 'chrome'
    if (/safari/.test(userAgent)) return 'safari'
    if (/edge/.test(userAgent)) return 'edge'
    if (/msie|trident/.test(userAgent)) return 'ie'
    return 'unknown'
  })

  const os = computed(() => {
    if (isIOS.value) return 'ios'
    if (isAndroid.value) return 'android'
    if (/windows/.test(userAgent)) return 'windows'
    if (/mac/.test(userAgent)) return 'macos'
    if (/linux/.test(userAgent)) return 'linux'
    return 'unknown'
  })

  return {
    isIOS,
    isAndroid,
    isMobileDevice,
    isTabletDevice,
    browser,
    os,
    userAgent,
  }
}

/**
 * Hook for detecting screen features
 */
export function useScreenFeatures() {
  const pixelRatio = computed(() => window.devicePixelRatio || 1)

  const isRetina = computed(() => pixelRatio.value > 1)

  const colorScheme = computed(() => {
    if (window.matchMedia('(prefers-color-scheme: dark)').matches) {
      return 'dark'
    }
    return 'light'
  })

  const reducedMotion = computed(() => {
    return window.matchMedia('(prefers-reduced-motion: reduce)').matches
  })

  const highContrast = computed(() => {
    return window.matchMedia('(prefers-contrast: high)').matches
  })

  return {
    pixelRatio,
    isRetina,
    colorScheme,
    reducedMotion,
    highContrast,
  }
}
