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
import {useState} from "react";
import {useTheme} from "@/components/theme-provider.tsx";
import {Laptop, Moon, Sun} from "lucide-react";
import {ThemeBox} from "@/components/theme-box.tsx";
import {Input} from "@/components/ui/input.tsx";

export function Appearance() {
    const {theme} = useTheme()

    const [isLight, setIsLight] = useState(theme === "light");
    const [isDark, setIsDark] = useState(theme === "dark");
    const [isSystem, setIsSystem] = useState(theme === "system");

    const [font, setFont] = useState(18);

    return (
        <div className="space-y-4">
            <h1 className="text-2xl font-semibold">Appearance</h1>
            <Separator className="my-4 h-[1px] w-full"/>
            <div className="items-center flex-wrap space-y-4">
                <div>
                    <p>Theme</p>
                    <p className="text-sm text-muted-foreground text-gray-500">
                        Select a color theme for the app: light, dark, or match your system
                        settings.
                    </p>
                </div>

                <div
                    className="flex flex-col md:flex-row sm:gap-x-4 md:gap-x-8 lg:gap-x-12 gap-y-4 justify-center items-center px-4">
                    <ThemeBox title="Light" theme="light" isSelected={isLight} onClick={() => {
                        setIsLight(true)
                        setIsDark(false)
                        setIsSystem(false)
                    }} icon={Sun}/>
                    <ThemeBox title="Dark" theme="dark" isSelected={isDark} onClick={() => {
                        setIsLight(false)
                        setIsDark(true)
                        setIsSystem(false)
                    }} icon={Moon}/>
                    <ThemeBox title="System" theme="system" isSelected={isSystem} onClick={() => {
                        setIsLight(false)
                        setIsDark(false)
                        setIsSystem(true)
                    }} icon={Laptop}/>
                </div>
            </div>
            <div className="w-full flex flex-wrap items-center space-y-2 justify-between">
                <div className="w-[30%] md:w-[50%] lg:w-[60%]">
                    <p>Font size</p>
                    <p className="text-sm text-muted-foreground text-gray-500">
                        Adjust the size of the text in the application for better readability.
                    </p>
                </div>
                <div className=" items-center gap-2">
                    <Input
                        type="number"
                        defaultValue={font}
                        min={14}
                        max={24}
                        step={2}
                        onChange={(e) => setFont(Number(e.target.value))}
                        className="text-center"
                        aria-label="Font size input"
                    />
                </div>
            </div>
        </div>
    )
}
