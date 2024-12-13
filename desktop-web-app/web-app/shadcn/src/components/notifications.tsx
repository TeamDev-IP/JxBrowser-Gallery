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
import {GreenSwitch} from "@/components/green-switch.tsx";

export function Notifications() {
    return (
        <div className="space-y-4">
            <h1 className="text-2xl font-semibold">Notifications</h1>
            <Separator className="my-4 h-[1px] w-full"/>
            <div className="w-full inline-flex items-center justify-between">
                <div className={"w-[30%] md:w-[50%] lg:w-[60%]"}>
                    <p>Email notifications</p>
                    <p className="text-sm text-muted-foreground">
                        Receive an email digest for unread notifications. Notifications will be
                        grouped together and sent based on their urgency.
                    </p>
                </div>
                <GreenSwitch/>
            </div>
            <div className="w-full inline-flex items-center justify-between">
                <div className={"w-[30%] md:w-[50%] lg:w-[60%]"}>
                    <p>Desktop notifications</p>
                    <p className="text-sm text-muted-foreground">
                        Receive personal notifications on the desktop.
                    </p>
                </div>
                <GreenSwitch/>
            </div>
        </div>
    );
}