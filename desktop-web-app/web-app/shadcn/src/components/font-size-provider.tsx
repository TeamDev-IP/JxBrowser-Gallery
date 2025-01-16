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

import React, {createContext, useContext, useEffect, useState} from "react"
import {getAppearance} from "@/rpc/app-preferences-service.ts";
import {FontSize} from "@/gen/appearance_pb.ts";
import {
    defaultFontSize,
    fontSizeFromStorage,
    FontSizeOption,
    saveFontSizeInStorage,
    smallFontSize
} from "@/storage/appearance.ts";

type FontSizeProviderProps = {
    children: React.ReactNode
}

type FontSizeProviderState = {
    fontSize: FontSizeOption
    setFontSize: (theme: FontSizeOption) => void
}

const initialState: FontSizeProviderState = {
    fontSize: defaultFontSize,
    setFontSize: () => null,
}

const FontSizeProviderContext = createContext<FontSizeProviderState>(initialState);

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

export function FontSizeProvider({
                                     children,
                                     ...props
                                 }: FontSizeProviderProps) {
    const [fontSize, setFontSize] = useState<FontSizeOption>(fontSizeFromStorage());

    useEffect(() => {
        getAppearance(appearancePrefs => {
            setFontSize(fromFontSize(appearancePrefs.fontSize));
            saveFontSizeInStorage(fromFontSize(appearancePrefs.fontSize));
        });
    }, []);
    useEffect(() => {
        const adjustFontSize = (scaleFactor: number) => {
            const style = document.documentElement.style;
            style.setProperty('--font-size-sm', `${0.875 * scaleFactor}rem`);
            style.setProperty('--font-size-xs', `${0.75 * scaleFactor}rem`);
            style.setProperty('--font-size-lg', `${1.125 * scaleFactor}rem`);
            style.setProperty('--font-size-2xl', `${1.5 * scaleFactor}rem`);
        }
        if (fontSize === smallFontSize) {
            adjustFontSize(0.8);
        } else if (fontSize === defaultFontSize) {
            adjustFontSize(1.0);
        } else {
            adjustFontSize(1.2);
        }
    }, [fontSize])

    const value = {
        fontSize,
        setFontSize: (fontSize: FontSizeOption) => {
            saveFontSizeInStorage(fontSize);
            setFontSize(fontSize);
        },
    }

    return (
        <FontSizeProviderContext.Provider {...props} value={value}>
            {children}
        </FontSizeProviderContext.Provider>
    )
}

export const useFontSize = () => {
    const context = useContext(FontSizeProviderContext)

    if (context === undefined)
        throw new Error("useFontSize must be used within a FontSizeProvider")

    return context
}
