import type { Ref } from "vue";
import type { FontSizeOption } from "@/converter/font-size.ts";

export const FontSizeContextKey = Symbol("FontSizeContext");

export interface FontSizeContext {
    fontSize: Ref<FontSizeOption>;
    setFontSize: (fontSize: FontSizeOption) => void;
}