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

import { Theme } from '@/gen/prefs_pb.ts'

/**
 * The theme options as human-readable strings.
 */
export type ThemeOption = 'light' | 'dark' | 'system'

export const lightTheme: ThemeOption = 'light'
export const darkTheme: ThemeOption = 'dark'
export const systemTheme: ThemeOption = 'system'

/**
 * Converts {@link Theme} to {@link ThemeOption}.
 */
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

/**
 * Converts {@link ThemeOption} to {@link Theme}.
 */
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
