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

import { Language } from '@/gen/prefs_pb'

/**
 * Represents supported application languages as human-readable strings.
 *
 * @see https://en.wikipedia.org/wiki/Language_localisation — About software localization.
 * @see https://developer.mozilla.org/en-US/docs/Mozilla/Add-ons/WebExtensions/Internationalization — MDN on internationalization (i18n).
 * @see https://protobuf.dev/programming-guides/proto3/#enum — Protocol Buffers enum reference.
 */
export type LanguageOption = 'English' | 'German' | 'French'

export const englishLanguage: LanguageOption = 'English'
export const germanLanguage: LanguageOption = 'German'
export const frenchLanguage: LanguageOption = 'French'

/**
 * Converts protobuf {@link Language} enum to a human-readable {@link LanguageOption}.
 *
 * @param value - Enum value from the generated protobuf `Language`.
 * @returns Corresponding {@link LanguageOption} string.
 *
 * @throws {TypeError} If the language is not recognized.
 *
 * @see https://developers.google.com/protocol-buffers/docs/reference/overview — Protobuf overview.
 */
export function fromLanguage(value: Language): LanguageOption {
  switch (value) {
    case Language.ENGLISH:
      return englishLanguage
    case Language.GERMAN:
      return germanLanguage
    case Language.FRENCH:
      return frenchLanguage
    default:
      throw new TypeError('Incorrect language.')
  }
}

/**
 * Converts a human-readable {@link LanguageOption} to the protobuf {@link Language} enum.
 *
 * @param value - The selected language option.
 * @returns Corresponding {@link Language} enum value.
 *
 * @throws {TypeError} If the language option is invalid.
 *
 * @see https://www.typescriptlang.org/docs/handbook/enums.html — TypeScript enums documentation.
 */
export function toLanguage(value: LanguageOption): Language {
  switch (value) {
    case englishLanguage:
      return Language.ENGLISH
    case germanLanguage:
      return Language.GERMAN
    case frenchLanguage:
      return Language.FRENCH
    default:
      throw new TypeError('Incorrect language option.')
  }
}
