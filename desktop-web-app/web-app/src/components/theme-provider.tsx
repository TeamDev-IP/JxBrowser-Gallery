import React, {createContext, useContext, useEffect, useState} from "react"
import {getAppearance} from "@/rpc/app-preferences-service.ts";
import {fromTheme, systemTheme, ThemeOption} from "@/components/converter/theme.ts";
import {saveThemeInStorage, themeFromStorage} from "@/storage/appearance.ts";

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
    theme: systemTheme,
    setTheme: () => null,
}

const ThemeProviderContext = createContext<ThemeProviderState>(initialState)

export function ThemeProvider({
                                  children,
                                  ...props
                              }: ThemeProviderProps) {
    const [theme, setTheme] = useState<ThemeOption>(themeFromStorage());

    const setRootTheme = (theme: ThemeOption) => {
        const root = window.document.documentElement;
        root.classList.remove("light", "dark");
        if (theme === "system") {
            const systemTheme = window.matchMedia("(prefers-color-scheme: dark)")
                .matches
                ? "dark"
                : "light";

            root.classList.add(systemTheme);
            return;
        }
        root.classList.add(theme);
    }
    setRootTheme(theme);
    useEffect(() => {
        getAppearance(appearancePrefs => {
            const theme = fromTheme(appearancePrefs.theme);
            setTheme(theme);
            saveThemeInStorage(theme);
        });
    }, []);
    useEffect(() => setRootTheme(theme), [theme]);

    const value = {
        theme,
        setTheme: (theme: ThemeOption) => {
            saveThemeInStorage(theme);
            setTheme(theme);
        },
    }

    return (
        <ThemeProviderContext.Provider {...props} value={value}>
            {children}
        </ThemeProviderContext.Provider>
    );
}

export const useTheme = () => {
    const context = useContext(ThemeProviderContext);

    if (context === undefined)
        throw new Error("useTheme must be used within a ThemeProvider");

    return context;
}
