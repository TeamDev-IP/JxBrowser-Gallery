/*
 *  Copyright (c) 2025 TeamDev
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */

import {TwoFactorAuthentication} from "@/gen/account_pb.ts";

/**
 * The two-factor authentication methods as human-readable strings.
 */
export type TfaMethod = "Email" | "SMS" | "Passkey"

const emailTfa: TfaMethod = "Email";
const smsTfa: TfaMethod = "SMS";
const passkeyTfa: TfaMethod = "Passkey";

/**
 * Converts a {@link TwoFactorAuthentication} to a TfaMethod.
 */
function fromTfa(value: TwoFactorAuthentication): TfaMethod {
    if (value === TwoFactorAuthentication.EMAIL) {
        return emailTfa;
    } else if (value === TwoFactorAuthentication.SMS) {
        return smsTfa;
    } else if (value === TwoFactorAuthentication.PASS_KEY) {
        return passkeyTfa;
    } else {
        throw new TypeError("Incorrect two-factor authentication.");
    }
}

/**
 * Converts a TwoFactorAuthentication to a {@link TfaMethod}.
 */
function toTfa(value: TfaMethod): TwoFactorAuthentication {
    if (value === emailTfa) {
        return TwoFactorAuthentication.EMAIL;
    } else if (value === smsTfa) {
        return TwoFactorAuthentication.SMS;
    } else {
        return TwoFactorAuthentication.PASS_KEY;
    }
}

export {
    emailTfa,
    smsTfa,
    passkeyTfa,
    fromTfa,
    toTfa
};