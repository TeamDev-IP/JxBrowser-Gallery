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

import {Popover, PopoverContent, PopoverTrigger} from "@/components/ui/popover.tsx";
import {Button} from "@/components/ui/button.tsx";
import {useState} from "react";
import {Check, ChevronsUpDown} from "lucide-react";
import {Command, CommandGroup, CommandItem, CommandList} from "@/components/ui/command.tsx";
import {cn} from "@/lib/utils.ts";

export declare type Option = {
    value: string,
    label: string
}

interface ComboboxProps {
    options: string[],
    defaultOption: string
    onSelect: (value: string) => void
}

export function Combobox({options, defaultOption, onSelect}: ComboboxProps) {
    const [open, setOpen] = useState(false)
    const [value, setValue] = useState(defaultOption)
    const [wasChanged, setWasChanged] = useState(false)

    return (
        <Popover open={open} onOpenChange={setOpen}>
            <PopoverTrigger className={"w-[130px]"} asChild>
                <Button
                    variant="outline"
                    role="combobox"
                    size="sm"
                    aria-expanded={open}
                    className="min-w-[120px] justify-between"
                >
                    {wasChanged ? value : defaultOption}
                    <ChevronsUpDown className="h-4 opacity-50"/>
                </Button>
            </PopoverTrigger>
            <PopoverContent className="w-[120px] p-0">
                <Command>
                    <CommandList>
                        <CommandGroup>
                            {options.map((it) => (
                                <CommandItem
                                    className={"h-[30px]"}
                                    key={it}
                                    value={it}
                                    onSelect={(currentValue) => {
                                        setValue(currentValue === value ? "" : currentValue)
                                        onSelect(currentValue)
                                        setWasChanged(true);
                                        setOpen(false)
                                    }}
                                >
                                    {it}
                                    <Check
                                        className={cn(
                                            "ml-auto",
                                            value === it ? "opacity-100" : "opacity-0"
                                        )}
                                    />
                                </CommandItem>
                            ))}
                        </CommandGroup>
                    </CommandList>
                </Command>
            </PopoverContent>
        </Popover>
    )
}