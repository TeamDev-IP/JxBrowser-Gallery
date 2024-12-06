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

import {useState} from "react";

const colors = [
    { name: "White", value: "#ffffff" },
    { name: "Light Gray", value: "#f3f4f6" },
    { name: "Slate", value: "#64748b" },
    { name: "Blue", value: "#3b82f6" },
    { name: "Dark", value: "#1f2937" },
];

export function BackgroundSelector() {
    const [selectedColor, setSelectedColor] = useState(colors[0].value);
    return (
        <div className="py-2">
            <div className="flex flex-wrap gap-4">
                {colors.map((color) => (
                    <button
                        key={color.value}
                        onClick={() => setSelectedColor(color.value)}
                        className={`group w-24 h-24 rounded-md border-2 ${
                            selectedColor === color.value
                                ? "border-accent bg-accent/10"
                                : "border-muted"
                        } p-2 flex flex-col items-center justify-center hover:border-accent`}
                    >
                        <div
                            className="w-12 h-12 rounded-full"
                            style={{backgroundColor: color.value}}
                        />
                        <span
                            className="mt-2 text-sm font-medium text-muted-foreground group-hover:text-accent-foreground">
              {color.name}
            </span>
                    </button>
                ))}
            </div>
        </div>
    );
}