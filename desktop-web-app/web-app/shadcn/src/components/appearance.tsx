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
import {Combobox, Option} from "@/components/combobox.tsx";

const fonts: Option[] = [
    {
        value: "small",
        label: "Small",
    },
    {
        value: "default",
        label: "Default",
    },
    {
        value: "large",
        label: "Large",
    },
]

export function Appearance() {
    const {theme} = useTheme()

    const [isLight, setIsLight] = useState(theme === "light");
    const [isDark, setIsDark] = useState(theme === "dark");
    const [isSystem, setIsSystem] = useState(theme === "system");

    function adjustFontSize(factor: number) {
        const style = document.documentElement.style;
        style.setProperty('--font-size-sm', `${0.875 * factor}rem`);
        style.setProperty('--font-size-xs', `${0.75 * factor}rem`);
        style.setProperty('--font-size-lg', `${1.125 * factor}rem`);
        style.setProperty('--font-size-2xl', `${1.5 * factor}rem`);
    }

    return (
        <div className="space-y-4">
            <h1 className="text-2xl font-semibold">Appearance</h1>
            <Separator className="my-4 h-[1px] w-full"/>
            <div className="items-center flex-wrap space-y-4 py-1">
                <div>
                    <p className="text-sm">Theme</p>
                    <p className="text-xs text-muted-foreground text-gray-500">
                        The color theme for the application: light, dark, or match your
                        system&nbsp;settings.
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
                <Combobox options={fonts} onClick={(value)=> {
                    if (value==="small") {
                        adjustFontSize(0.8);
                    }else if(value === "default") {
                        adjustFontSize(1.0);
                    } else {
                        adjustFontSize(1.2);
                    }
                }} defaultOption={fonts[1].value}/>
            </div>
        </div>
    )
}
