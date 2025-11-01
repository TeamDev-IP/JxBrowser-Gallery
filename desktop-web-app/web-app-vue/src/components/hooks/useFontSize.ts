import { inject } from "vue";
import {FontSizeContext, FontSizeContextKey} from "@/utils/fontsize-context.ts";

export function useFontSize(): FontSizeContext {
    const context = inject<FontSizeContext>(FontSizeContextKey);
    if (!context) throw new Error("useFontSize must be used within a FontSizeProvider");
    return context;
}
