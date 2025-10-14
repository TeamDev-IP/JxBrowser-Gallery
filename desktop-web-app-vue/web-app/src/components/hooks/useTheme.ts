import { inject } from "vue";
import {ThemeContext, ThemeContextKey} from "@/utils/theme-context.ts";

export function useTheme(): ThemeContext {
    const context = inject<ThemeContext>(ThemeContextKey);
    if (!context) throw new Error("useTheme must be used within a ThemeProvider");
    return context;
}