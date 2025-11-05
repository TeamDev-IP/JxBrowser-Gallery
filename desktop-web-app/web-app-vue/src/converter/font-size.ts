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

/**
 * The available font size options used in user preferences.
 */
export type FontSizeOption = 'Small' | 'Default' | 'Large'

export const smallFontSize: FontSizeOption = 'Small'
export const defaultFontSize: FontSizeOption = 'Default'
export const largeFontSize: FontSizeOption = 'Large'

/**
 * Converts a protobuf {@link FontSize} enum value into a human-readable {@link FontSizeOption}.
 *
 * @param value - The {@link FontSize} value from the protobuf definition.
 * @returns Corresponding {@link FontSizeOption} string.
 *
 * @see https://www.typescriptlang.org/docs/handbook/2/everyday-types.html#union-types — About union types in TypeScript.
 * @see https://protobuf.dev/programming-guides/encoding/ — Protocol Buffers encoding reference.
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
      throw new TypeError('Incorrect font size value.')
  }
}

/**
 * Converts a {@link FontSizeOption} string into the corresponding protobuf {@link FontSize} enum.
 *
 * @param value - A {@link FontSizeOption} string such as `"Small"` or `"Large"`.
 * @returns Corresponding {@link FontSize} enum value.
 *
 * @see https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Statements/switch — MDN: switch statement reference.
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
