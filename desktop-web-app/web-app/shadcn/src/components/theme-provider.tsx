import React, {createContext, useContext, useEffect, useState} from "react"
import {getAppearance} from "@/rpc/app-preferences-service.ts";
import {Theme} from "@/gen/appearance_pb.ts";

export type ThemeOption = "dark" | "light" | "system"

type ThemeProviderProps = {
    children: React.ReactNode
    defaultTheme?: ThemeOption
    storageKey?: string
}

type ThemeProviderState = {
    theme: ThemeOption
    setTheme: (theme: ThemeOption) => void
}

const initialState: ThemeProviderState = {
    theme: "system",
    setTheme: () => null,
}

const ThemeProviderContext = createContext<ThemeProviderState>(initialState)

export function themeOption(value: Theme): ThemeOption {
    if (value === Theme.LIGHT) {
        return "light";
    } else if (value === Theme.DARK) {
        return "dark";
    } else if (value === Theme.SYSTEM) {
        return "system";
    } else {
        throw new TypeError("Incorrect two-factor authentication.");
    }
}

export function themeEnum(value: ThemeOption): Theme {
    if (value === "light") {
        return Theme.LIGHT;
    } else if (value === "dark") {
        return Theme.DARK;
    } else {
        return Theme.SYSTEM;
    }
}

export function ThemeProvider({
                                  children,
                                  defaultTheme = "system",
                                  storageKey = "vite-ui-theme",
                                  ...props
                              }: ThemeProviderProps) {
    const [theme, setTheme] = useState<ThemeOption>(
        () => (localStorage.getItem(storageKey) as ThemeOption) || defaultTheme)

    useEffect(() => {
        getAppearance(appearancePrefs => {
            setTheme(themeOption(appearancePrefs.theme));
            localStorage.setItem(storageKey, themeOption(appearancePrefs.theme));
        });
    }, []);
    useEffect(() => {
        const root = window.document.documentElement
        root.classList.remove("light", "dark")
        if (theme === "system") {
            const systemTheme = window.matchMedia("(prefers-color-scheme: dark)")
                .matches
                ? "dark"
                : "light"

            root.classList.add(systemTheme)
            return
        }
        root.classList.add(theme)
    }, [theme])

    const value = {
        theme,
        setTheme: (theme: ThemeOption) => {
            localStorage.setItem(storageKey, theme);
            setTheme(theme);
        },
    }

    return (
        <ThemeProviderContext.Provider {...props} value={value}>
            {children}
        </ThemeProviderContext.Provider>
    )
}

export const useTheme = () => {
    const context = useContext(ThemeProviderContext)

    if (context === undefined)
        throw new Error("useTheme must be used within a ThemeProvider")

    return context
}
