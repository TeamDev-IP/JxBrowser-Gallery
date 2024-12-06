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

import {Label} from "@/components/ui/label.tsx";
import {Separator} from "@/components/ui/separator.tsx";
import {BackgroundSelector} from "@/components/background-selector.tsx";
import {Slider} from "@/components/ui/slider.tsx";
import {cn} from "@/lib/utils.ts";

export function Appearance() {
    return (
        <div className="space-y-4">
            <h1 className="text-2xl font-semibold">Appearance</h1>
            <Separator className="my-4 h-[2px] w-full"/>
            <div className="items-center flex space-y-2 flex-wrap gap-4">
                <div>
                    <p>Theme</p>
                    <p className="text-sm text-muted-foreground">
                        Select a color theme for the app: light, dark, or match your system
                        settings.
                    </p>
                </div>
                <div className="flex flex-wrap gap-x-12 items-center">
                    <div>
                        <div
                            className="items-center rounded-md border-2 border-muted p-1 bg-popover hover:bg-accent hover:border-accent hover:text-accent-foreground">
                            <div className="space-y-2 rounded-sm bg-[#ecedef] p-2">
                                <div className="space-y-2 rounded-md bg-white p-2 shadow-sm">
                                    <div className="h-2 w-[80px] rounded-lg bg-[#ecedef]"/>
                                    <div className="h-2 w-[100px] rounded-lg bg-[#ecedef]"/>
                                </div>
                                <div
                                    className="flex items-center space-x-2 rounded-md bg-white p-2 shadow-sm">
                                    <div className="h-4 w-4 rounded-full bg-[#ecedef]"/>
                                    <div className="h-2 w-[100px] rounded-lg bg-[#ecedef]"/>
                                </div>
                                <div
                                    className="flex items-center space-x-2 rounded-md bg-white p-2 shadow-sm">
                                    <div className="h-4 w-4 rounded-full bg-[#ecedef]"/>
                                    <div className="h-2 w-[100px] rounded-lg bg-[#ecedef]"/>
                                </div>
                            </div>
                        </div>
                        <span className="block w-full p-2 text-center font-normal">
                      Light
                    </span>
                    </div>

                    <div>
                        <div
                            className="items-center rounded-md border-2 border-muted bg-popover p-1 hover:bg-accent hover:text-accent-foreground">
                            <div className="space-y-2 rounded-sm bg-slate-950 p-2">
                                <div className="space-y-2 rounded-md bg-slate-800 p-2 shadow-sm">
                                    <div className="h-2 w-[80px] rounded-lg bg-slate-400"/>
                                    <div className="h-2 w-[100px] rounded-lg bg-slate-400"/>
                                </div>
                                <div
                                    className="flex items-center space-x-2 rounded-md bg-slate-800 p-2 shadow-sm">
                                    <div className="h-4 w-4 rounded-full bg-slate-400"/>
                                    <div className="h-2 w-[100px] rounded-lg bg-slate-400"/>
                                </div>
                                <div
                                    className="flex items-center space-x-2 rounded-md bg-slate-800 p-2 shadow-sm">
                                    <div className="h-4 w-4 rounded-full bg-slate-400"/>
                                    <div className="h-2 w-[100px] rounded-lg bg-slate-400"/>
                                </div>
                            </div>
                        </div>
                        <span className="block w-full p-2 text-center font-normal">
                      Dark
                    </span>
                    </div>
                    <div>
                        <div
                            className="flex items-center rounded-md border-2 border-muted bg-popover p-1 hover:bg-accent hover:border-accent hover:text-accent-foreground">
                            {/* Left Side: Light Theme */}
                            <div className="flex-1 space-y-2 rounded-sm bg-[#ecedef] p-2">
                                <div className="space-y-2 rounded-md bg-white p-2 shadow-sm">
                                    <div className="h-2 w-[20px] rounded-lg bg-[#ecedef]"/>
                                    <div className="h-2 w-[22px] rounded-lg bg-[#ecedef]"/>
                                </div>
                                <div
                                    className="flex items-center space-x-2 rounded-md bg-white p-2 shadow-sm">
                                    <div className="h-4 w-4 rounded-full bg-[#ecedef]"/>
                                    <div className="h-2 w-[22px] rounded-lg bg-[#ecedef]"/>
                                </div>
                                <div
                                    className="flex items-center space-x-2 rounded-md bg-white p-2 shadow-sm">
                                    <div className="h-4 w-4 rounded-full bg-[#ecedef]"/>
                                    <div className="h-2 w-[22px] rounded-lg bg-[#ecedef]"/>
                                </div>
                                {/*<span className="block w-full p-2 text-center font-normal">Light</span>*/}
                            </div>

                            {/* Right Side: Dark Theme */}
                            <div className="flex-1 space-y-2 rounded-sm bg-slate-950 p-2">
                                <div className="space-y-2 rounded-md bg-slate-800 p-2 shadow-sm">
                                    <div className="h-2 w-[20px] rounded-lg bg-slate-400"/>
                                    <div className="h-2 w-[22px] rounded-lg bg-slate-400"/>
                                </div>
                                <div
                                    className="flex items-center space-x-2 rounded-md bg-slate-800 p-2 shadow-sm">
                                    <div className="h-4 w-4 rounded-full bg-slate-400"/>
                                    <div className="h-2 w-[22px] rounded-lg bg-slate-400"/>
                                </div>
                                <div
                                    className="flex items-center space-x-2 rounded-md bg-slate-800 p-2 shadow-sm">
                                    <div className="h-4 w-4 rounded-full bg-slate-400"/>
                                    <div className="h-2 w-[22px] rounded-lg bg-slate-400"/>
                                </div>
                                {/*<span*/}
                                {/*    className="block w-full p-2 text-center font-normal text-white">Dark</span>*/}
                            </div>
                        </div>
                        <span className="block w-full p-2 text-center font-normal">
                      Auto
                    </span>
                    </div>
                </div>
            </div>
            <div className="w-full items-center space-y-2">
                <div>
                    <p>Default background color</p>
                    <p className="text-sm text-muted-foreground">
                        Select the default background color for the application.
                    </p>
                </div>
                <BackgroundSelector/>
            </div>
            <div className="w-full flex flex-wrap items-center space-y-2 justify-between">
                <div>
                    <p>Font size</p>
                    <p className="text-sm text-muted-foreground">
                        Adjust the size of the text in the application for better readability.
                    </p>
                </div>
                <div className="inline-flex space-x-4 justify-between w-[30%]">
                    <Slider defaultValue={[18]} max={40} min={14} step={2}
                            className={cn("w-[90%]")}/>
                    <Label>18</Label>
                </div>
            </div>
        </div>
    )
}