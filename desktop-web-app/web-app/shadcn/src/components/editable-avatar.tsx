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
import {cn} from "@/lib/utils.ts";
import {Upload} from "lucide-react";

export function EditableAvatar() {
    return (
        <div className="w-full items-center flex justify-between">
            <p className="text-sm">Profile picture</p>
            <div
                className="flex relative justify-center items-center group">
                <Avatar
                    className="group-hover:opacity-50 transition-colors duration-200 object-cover">
                    <AvatarImage src="https://github.com/shadcn.png"/>
                    <AvatarFallback>JD</AvatarFallback>
                </Avatar>
                <div className={cn(
                    "absolute inset-0 flex items-center justify-center text-white text-sm font-medium opacity-0 transition-opacity group-hover:opacity-100"
                )}>
                    <Upload/>
                </div>
            </div>
        </div>
    );
}
