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
import {GuidingLine} from "@/components/guiding-line.tsx";

const languages: Option[] = [
    {
        value: "en",
        label: "English",
    },
    {
        value: "ge",
        label: "German",
    },
    {
        value: "fr",
        label: "French",
    },
    {
        value: "ua",
        label: "Ukrainian",
    },
]

export function General() {
    return (
        <div className="space-y-4">
            <h1 className="text-2xl font-semibold">General</h1>
            <Separator className="my-4 h-[1px] w-full"/>
            <div className="w-full inline-flex items-center justify-between py-1">
                <div className="pr-8">
                    <p className="text-sm">Launch at startup</p>
                    <p className="text-xs text-muted-foreground">
                        Enable this option to automatically start the application when your device
                        boots up.
                    </p>
                </div>
                <GreenSwitch/>
            </div>
            <div className="w-full inline-flex items-center space-y-2 justify-between py-1">
                <div className="pr-8">
                    <p className="text-sm">Language</p>
                    <p className="text-xs text-muted-foreground">
                        Choose the language for the application’s interface.
                    </p>
                </div>
                <Combobox options={languages} defaultOption={"English"}/>
            </div>
            <div className="w-full inline-flex items-center justify-between py-1">
                <div className="pr-8">
                    <p className="text-sm">Check for updates automatically</p>
                    <p className="text-xs text-muted-foreground">
                        Allow to check for updates in the background.
                    </p>
                </div>
                <GreenSwitch/>
            </div>
        </div>
    );
}