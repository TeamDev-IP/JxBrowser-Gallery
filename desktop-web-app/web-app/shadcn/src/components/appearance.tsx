/*
 *  Copyright (c) 2025 TeamDev
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
import {useEffect, useState} from "react";
import {Laptop, Moon, Sun} from "lucide-react";
import {ThemeBox} from "@/components/theme-box.tsx";
import {Combobox} from "@/components/combobox.tsx";
import {AppearanceSchema, FontSize, Theme} from "@/gen/appearance_pb.ts";
import {getAppearance, setAppearance} from "@/rpc/app-preferences-service.ts";
import {create} from "@bufbuild/protobuf";
import {themeEnum, themeOption, useTheme} from "@/components/theme-provider.tsx";
import {
    defaultFontSize,
    FontSizeOption,
    largeFontSize,
    saveFontSizeInStorage,
    smallFontSize
} from "@/storage/appearance.ts";
import {useFontSize} from "@/components/font-size-provider.tsx";

const fontsSizes: FontSizeOption[] = [
    smallFontSize,
    defaultFontSize,
    largeFontSize
]

function fromFontSize(value: FontSize): FontSizeOption {
    if (value === FontSize.SMALL) {
        return "Small";
    } else if (value === FontSize.DEFAULT) {
        return "Default";
    } else if (value === FontSize.LARGE) {
        return "Large";
    } else {
        throw new TypeError("Incorrect font size.");
    }
}

function toFontSize(value: FontSizeOption): FontSize {
    if (value === smallFontSize) {
        return FontSize.SMALL;
    } else if (value === defaultFontSize) {
        return FontSize.DEFAULT;
    } else {
        return FontSize.LARGE;
    }
}

export function Appearance() {
    const {theme, setTheme} = useTheme();
    const {fontSize, setFontSize} = useFontSize();

    const [uiTheme, setUiTheme] = useState<Theme>(themeEnum(theme));
    const [uiFontSize, setUiFontSize] = useState<FontSizeOption>(fontSize);

    useEffect(() => {
        getAppearance(appearance => {
            setUiTheme(appearance.theme);
            setTheme(themeOption(appearance.theme));
            setFontSize(fromFontSize(appearance.fontSize));
            saveFontSizeInStorage(fromFontSize((appearance.fontSize)));
        });
    }, []);

    useEffect(() => {
        const newAppearance = create(AppearanceSchema, {
            theme: uiTheme,
            fontSize: toFontSize(uiFontSize)
        });
        setAppearance(newAppearance, () => {
            console.log("setAppearance " + uiFontSize)
            setTheme(themeOption(uiTheme));
            setFontSize(uiFontSize);
        });
    }, [uiTheme, uiFontSize]);

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
                    <ThemeBox title="Light" theme="light" isSelected={uiTheme === Theme.LIGHT}
                              onClick={() => {
                                  setUiTheme(Theme.LIGHT);
                              }} icon={Sun}/>
                    <ThemeBox title="Dark" theme="dark" isSelected={uiTheme === Theme.DARK}
                              onClick={() => {
                                  setUiTheme(Theme.DARK);
                              }} icon={Moon}/>
                    <ThemeBox title="System" theme="system" isSelected={uiTheme === Theme.SYSTEM}
                              onClick={() => {
                                  setUiTheme(Theme.SYSTEM);
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
                <Combobox options={fontsSizes} onSelect={(value) => {
                    setUiFontSize(value as FontSizeOption);
                }} currentOption={fontSize}/>
            </div>
        </div>
    )
}
