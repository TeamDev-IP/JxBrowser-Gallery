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

import { TwoFactorAuthentication } from '@/gen/prefs_pb.ts'

/**
 * Represents supported two-factor authentication methods as human-readable strings.
 *
 * Related documentation:
 * - General info about 2FA: https://en.wikipedia.org/wiki/Multi-factor_authentication
 * - Best practices for 2FA implementation: https://owasp.org/www-community/controls/Two-factor_authentication
 * - Protobuf enums: https://protobuf.dev/programming-guides/proto3/#enum
 */
export type TwoFAMethod = 'Email' | 'SMS' | 'Passkey'

export const emailTwoFA: TwoFAMethod = 'Email'
export const smsTwoFA: TwoFAMethod = 'SMS'
export const passkeyTwoFA: TwoFAMethod = 'Passkey'

/**
 * Converts protobuf {@link TwoFactorAuthentication} enum to a human-readable {@link TwoFAMethod}.
 *
 * @param value - Enum value from the generated protobuf `TwoFactorAuthentication`.
 * @returns Corresponding {@link TwoFAMethod} string.
 *
 * See:
 * - Protobuf reference: https://developers.google.com/protocol-buffers/docs/reference/overview
 */
export function fromTfa(value: TwoFactorAuthentication): TwoFAMethod {
  switch (value) {
    case TwoFactorAuthentication.EMAIL:
      return emailTwoFA
    case TwoFactorAuthentication.SMS:
      return smsTwoFA
    case TwoFactorAuthentication.PASS_KEY:
      return passkeyTwoFA
    default:
      throw new TypeError('Incorrect two-factor authentication.')
  }
}

/**
 * Converts a human-readable {@link TwoFAMethod} to the protobuf {@link TwoFactorAuthentication} enum.
 *
 * @param value - The selected 2FA method string.
 * @returns Corresponding {@link TwoFactorAuthentication} enum value.
 *
 * See:
 * - TypeScript enums: https://www.typescriptlang.org/docs/handbook/enums.html
 * - Mapping constants between client and backend: https://stackoverflow.com/questions/48006913/how-to-map-enum-values-between-client-and-server
 */
export function toTfa(value: TwoFAMethod): TwoFactorAuthentication {
  switch (value) {
    case emailTwoFA:
      return TwoFactorAuthentication.EMAIL
    case smsTwoFA:
      return TwoFactorAuthentication.SMS
    case passkeyTwoFA:
      return TwoFactorAuthentication.PASS_KEY
    default:
      throw new TypeError('Incorrect TwoFAMethod')
  }
}
