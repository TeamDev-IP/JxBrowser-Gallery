/*
 *  Copyright (c) 2024 TeamDev
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

import {Separator} from "@/components/ui/separator.tsx";
import {Combobox, Option} from "@/components/combobox.tsx";
import {GreenSwitch} from "@/components/green-switch.tsx";
import {EditableAvatar} from "@/components/editable-avatar.tsx";
import {EditableLabel} from "@/components/editable-label.tsx";
import {EditablePassword} from "@/components/editable-password.tsx";

const authentications: Option[] = [
    {
        value: "email",
        label: "Email",
    },
    {
        value: "sms",
        label: "SMS",
    },
    {
        value: "passkey",
        label: "Passkey",
    },
]

export function Account() {
    return (
        <div className="space-y-4">
            <h1 className="text-2xl font-semibold">Account</h1>
            <Separator className="my-4 h-[1px] w-full"/>
            <EditableAvatar/>
            <EditableLabel title={"Email"} defaultValue={"johndoe@mail.com"} id={"email"}/>
            <EditableLabel title={"Full name"} defaultValue={"John Doe"} id={"fullname"}/>
            <EditablePassword defaultValue={"Test123$"}/>
            <div className="w-full inline-flex items-center space-y-2 justify-between">
                <div className="w-[70%]">
                    <p>Two-factor authentication</p>
                    <p className="text-sm text-muted-foreground">
                        Select an extra layer of security by requiring a code when logging in.
                    </p>
                </div>
                <Combobox options={authentications} defaultOption={"Email"}/>
            </div>
            <div className="w-full inline-flex items-center justify-between">
                <div>
                    <p>Biometric authentication</p>
                    <p className="text-sm text-muted-foreground">
                        Allow authentication via fingerprints or Face ID.
                    </p>
                </div>
                <GreenSwitch/>
            </div>
        </div>
    )
}
