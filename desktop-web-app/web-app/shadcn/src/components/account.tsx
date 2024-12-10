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

import {Avatar, AvatarFallback, AvatarImage} from "@/components/ui/avatar.tsx";
import {Eye, Pencil, PenLine} from "lucide-react";
import {Separator} from "@/components/ui/separator.tsx";
import {Input} from "@/components/ui/input.tsx";
import {Toggle} from "@/components/ui/toggle.tsx";
import {Combobox, Option} from "@/components/combobox.tsx";
import {cn} from "@/lib/utils.ts";
import {GreenSwitch} from "@/components/green-switch.tsx";

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
            <div className="w-full flex justify-center items-center">
                <div
                    className="w-[150px] h-[150px] flex relative justify-center items-center group">
                    <Avatar
                        className="group-hover:opacity-50 transition-colors duration-200 w-[150px] h-[150px] object-cover">
                        <AvatarImage width={420} height={420} src="https://github.com/shadcn.png"/>
                        <AvatarFallback>JD</AvatarFallback>
                    </Avatar>
                    <div className={cn(
                        "absolute inset-0 flex items-center justify-center text-white text-sm font-medium opacity-0 transition-opacity group-hover:opacity-100"
                    )}
                    >
                        <Pencil/>
                    </div>
                </div>
            </div>
            <div className="w-full items-center space-y-2">
                <p>Email</p>
                <div className="w-full flex items-center space-x-4 justify-between ">
                    <Input disabled={false} value={"john@mail.com"}/>
                    <Toggle className={"bg-accent"}>
                        <PenLine/>
                    </Toggle>
                </div>
            </div>
            <div className="w-full items-center space-y-2">
                <p>Full name</p>
                <div className="w-full flex items-center space-x-4 justify-between ">
                    <Input disabled value={"John Doe"}/>
                    <Toggle className={"bg-accent"}>
                        <PenLine/>
                    </Toggle>
                </div>
            </div>
            <div className="w-full items-center space-y-2">
                <p>Password</p>
                <div className="w-full flex items-center justify-between ">
                    <div className="flex w-full space-x-4">
                        <Input type={"password"} className="relative flex" disabled
                               value={"password"}/>
                        <Toggle>
                            <Eye className="right-4 top-4 z-10"/>
                        </Toggle>
                        <Toggle className={"bg-accent"}>
                            <PenLine/>
                        </Toggle>
                    </div>
                </div>
            </div>
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
