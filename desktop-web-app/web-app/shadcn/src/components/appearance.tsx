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
import {Slider} from "@/components/ui/slider.tsx";
import {cn} from "@/lib/utils.ts";
import {AspectRatio} from "@/components/ui/aspect-ratio.tsx";
import {useState} from "react";

export function Appearance() {
    const [isLight, setIsLight] = useState(true);
    const [isDark, setIsDark] = useState(false);
    const [isSystem, setIsSystem] = useState(false);

    const [font, setFont] = useState(18);
    return (
        <div className="space-y-4">
            <h1 className="text-2xl font-semibold">Appearance</h1>
            <Separator className="my-4 h-[1px] w-full"/>
            <div className="items-center space-y-4 flex-wrap gap-4">
                <div>
                    <p>Theme</p>
                    <p className="text-sm text-muted-foreground">
                        Select a color theme for the app: light, dark, or match your system
                        settings.
                    </p>
                </div>

                <div
                    className="flex flex-wrap sm:gap-x-4 md:gap-x-8 lg:gap-x-12 justify-center items-center">
                    <div className={"sm:w-[20%] md:w-[15%] lg:w-[10%]"}>
                        <AspectRatio onClick={() => {
                            setIsLight(true)
                            setIsDark(false)
                            setIsSystem(false)
                        }} ratio={16 / 14}
                                     className={`rounded-3xl ${!isLight ? "hover:border-gray-400" : ""} bg-accent ${isLight ? "border-green-700" : ""} sm:border-spacing-4 md:border-spacing-6 lg:border-spacing-8 sm:border-4 md:border-8 lg:border-8`}>
                            <img alt={"Light"} src={"/images/light-theme.png"}
                                 className={"rounded-2xl"}></img>
                        </AspectRatio>
                        <span className="block w-full p-2 text-center font-normal">
                      Light
                    </span>
                    </div>

                    <div className={"sm:w-[20%] md:w-[15%] lg:w-[10%]"}>
                        <AspectRatio onClick={() => {
                            setIsLight(false)
                            setIsDark(true)
                            setIsSystem(false)
                        }} ratio={16 / 14}
                                     className={`rounded-3xl ${!isDark ? "hover:border-gray-400" : ""} bg-accent ${isDark ? "border-green-700" : ""} sm:border-spacing-4 md:border-spacing-6 lg:border-spacing-8 sm:border-4 md:border-8 lg:border-8`}>
                            <img alt={"Light"} src={"/images/dark-theme.png"}
                                 className={"rounded-2xl"}></img>
                        </AspectRatio>
                        <span className="block w-full p-2 text-center font-normal">
                              Dark
                            </span>
                    </div>

                    <div className={"sm:w-[20%] md:w-[15%] lg:w-[10%]"}>
                        <AspectRatio onClick={() => {
                            setIsLight(false)
                            setIsDark(false)
                            setIsSystem(true)
                        }} ratio={16 / 14}
                                     className={`rounded-3xl ${!isSystem ? "hover:border-gray-400" : ""} bg-accent sm:border-spacing-4 ${isSystem ? "border-green-700" : ""} md:border-spacing-6 lg:border-spacing-8 sm:border-4 md:border-8 lg:border-8`}>
                            <img alt={"Light"} src={"/images/system-theme.png"}
                                 className={"rounded-2xl"}></img>
                        </AspectRatio>
                        <span className="block w-full p-2 text-center font-normal">
                              System
                            </span>
                    </div>
                </div>
            </div>
            <div className="w-full flex flex-wrap items-center space-y-2 justify-between">
                <div>
                    <p>Font size</p>
                    <p className="text-sm text-muted-foreground">
                        Adjust the size of the text in the application for better readability.
                    </p>
                </div>
                <div className="inline-flex space-x-4 justify-between w-[30%]">
                    <Slider onValueChange={(e) => {
                        setFont(e[0])
                    }} defaultValue={[18]} max={40} min={14} step={2}
                            className={cn("w-[90%]")}/>
                    <Label>{font}</Label>
                </div>
            </div>
        </div>
    )
}
