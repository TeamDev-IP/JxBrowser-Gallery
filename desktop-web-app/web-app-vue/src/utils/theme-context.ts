import type { Ref } from 'vue'
import type { ThemeOption } from '@/converter/theme.ts'

export const ThemeContextKey = Symbol('ThemeContext')

export interface ThemeContext {
  theme: Ref<ThemeOption>
  setTheme: (theme: ThemeOption) => void
}
