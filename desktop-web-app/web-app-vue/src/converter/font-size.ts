/*
 * Copyright (c) 2025 TeamDev
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
import { FontSize } from '@/gen/prefs_pb.ts'

export type FontSizeOption = 'Small' | 'Default' | 'Large'

export const smallFontSize: FontSizeOption = 'Small'
export const defaultFontSize: FontSizeOption = 'Default'
export const largeFontSize: FontSizeOption = 'Large'

/**
 * Converts {@link FontSize} to FontSizeOption.
 */
export function fromFontSize(value: FontSize): FontSizeOption {
  switch (value) {
    case FontSize.SMALL:
      return smallFontSize
    case FontSize.DEFAULT:
      return defaultFontSize
    case FontSize.LARGE:
      return largeFontSize
    default:
      throw new TypeError('Incorrect font size value')
  }
}

/**
 * Converts FontSizeOption to {@link FontSize}.
 */
export function toFontSize(value: FontSizeOption): FontSize {
  switch (value) {
    case smallFontSize:
      return FontSize.SMALL
    case defaultFontSize:
      return FontSize.DEFAULT
    case largeFontSize:
      return FontSize.LARGE
    default:
      return FontSize.DEFAULT
  }
}
