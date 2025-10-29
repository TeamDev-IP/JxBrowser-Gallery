import { FontSize } from "@/gen/prefs_pb.ts";

export type FontSizeOption = "Small" | "Default" | "Large";

export const smallFontSize: FontSizeOption = "Small";
export const defaultFontSize: FontSizeOption = "Default";
export const largeFontSize: FontSizeOption = "Large";

export function fromFontSize(value: FontSize): FontSizeOption {
    switch (value) {
        case FontSize.SMALL:
            return smallFontSize;
        case FontSize.DEFAULT:
            return defaultFontSize;
        case FontSize.LARGE:
            return largeFontSize;
        default:
            throw new TypeError("Incorrect font size value");
    }
}

/**
 * Конвертация FontSizeOption → enum FontSize
 */
export function toFontSize(value: FontSizeOption): FontSize {
    switch (value) {
        case smallFontSize:
            return FontSize.SMALL;
        case defaultFontSize:
            return FontSize.DEFAULT;
        case largeFontSize:
            return FontSize.LARGE;
        default: return FontSize.DEFAULT
    }
}