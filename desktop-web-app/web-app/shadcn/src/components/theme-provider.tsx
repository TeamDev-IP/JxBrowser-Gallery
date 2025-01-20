import React, {createContext, useContext, useEffect, useState} from "react"
import {getAppearance} from "@/rpc/app-preferences-service.ts";
import {Theme} from "@/gen/appearance_pb.ts";
import {
    darkTheme,
    lightTheme,
    saveThemeInStorage,
    systemTheme,
    themeFromStorage,
    ThemeOption
} from "@/storage/theme.ts";

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

export function themeOption(value: Theme): ThemeOption {
    if (value === Theme.LIGHT) {
        return lightTheme;
    } else if (value === Theme.DARK) {
        return darkTheme;
    } else if (value === Theme.SYSTEM) {
        return systemTheme;
    } else {
        throw new TypeError("Incorrect two-factor authentication.");
    }
}

export function themeEnum(value: ThemeOption): Theme {
    if (value === lightTheme) {
        return Theme.LIGHT;
    } else if (value === darkTheme) {
        return Theme.DARK;
    } else {
        return Theme.SYSTEM;
    }
}

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
            setTheme(themeOption(appearancePrefs.theme));
            saveThemeInStorage(themeOption(appearancePrefs.theme));
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
