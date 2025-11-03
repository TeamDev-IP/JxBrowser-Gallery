import { Theme } from '@/gen/prefs_pb.ts'

export type ThemeOption = 'light' | 'dark' | 'system'

export const lightTheme: ThemeOption = 'light'
export const darkTheme: ThemeOption = 'dark'
export const systemTheme: ThemeOption = 'system'

export function fromTheme(value: Theme): ThemeOption {
  switch (value) {
    case Theme.LIGHT:
      return lightTheme
    case Theme.DARK:
      return darkTheme
    case Theme.SYSTEM:
      return systemTheme
    default:
      throw new TypeError('Incorrect theme value')
  }
}

export function toTheme(value: ThemeOption): Theme {
  switch (value) {
    case lightTheme:
      return Theme.LIGHT
    case darkTheme:
      return Theme.DARK
    case systemTheme:
      return Theme.SYSTEM
    default:
      return Theme.SYSTEM
  }
}
