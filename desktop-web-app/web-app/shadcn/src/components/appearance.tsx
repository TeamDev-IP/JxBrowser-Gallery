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
import {GuidingLine} from "@/components/guiding-line.tsx";

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
            <div className="items-center flex-wrap space-y-4 py-1">
                <div>
                    <p className="text-sm">Theme</p>
                    <p className="text-xs text-muted-foreground text-gray-500">
                        The color theme for the application: light, dark, or match your system&nbsp;settings.
                    </p>
                </div>

                <div
                    className="flex flex-col sm:flex-row gap-x-2 gap-y-2 justify-center items-center">
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
            <div className="w-full flex items-center space-y-2 justify-between py-2">
                <div className="pr-8">
                    <p className="text-sm">Font size</p>
                    <p className="text-xs text-muted-foreground text-gray-500">
                        Adjust the size of the text in the application for better readability.
                    </p>
                </div>
                <div className="items-center">
                    <Input
                        type="number"
                        defaultValue={font}
                        min={14}
                        max={24}
                        step={2}
                        onChange={(e) => setFont(Number(e.target.value))}
                        className="text-center w-[70px] text-sm"
                        aria-label="Font size input"
                    />
                </div>
            </div>
        </div>
    )
}