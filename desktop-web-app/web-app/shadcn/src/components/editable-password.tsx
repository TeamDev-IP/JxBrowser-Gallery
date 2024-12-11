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
import {Input} from "@/components/ui/input.tsx";
import {Label} from "@/components/ui/label.tsx";
import {Toggle} from "@/components/ui/toggle.tsx";
import {Check, Eye, EyeOff, PenLine, X} from "lucide-react";

interface Props {
    defaultValue: string
}

export function EditablePassword({defaultValue}: Props) {
    const [editable, setEditable] = useState(false);

    const [value, setValue] = useState(defaultValue)
    const [visible, setVisible] = useState(false)

    return (
        <div className="w-full items-center space-y-2">
            <p>Password</p>
            <div className="w-full flex items-center space-x-4 justify-between">
                {
                    editable ?
                        <Input autoFocus={true} id={"password"} disabled={!editable}
                               defaultValue={value}/>
                        :
                        <Label className={"font-bold text-muted-foreground"}>{
                            visible ? value : [...value].map(() => '*')
                        }</Label>
                }
                {editable ?
                    <div className={"flex space-x-4"}>
                        <Toggle id={"confirm-change"} onClick={() => {
                            setValue(document.getElementById("password")?.value)
                            setEditable(false)
                        }}
                                className="bg-green-500 hover:data-[state=on]:bg-green-700 hover:data-[state=off]:bg-green-700 data-[state=on]:bg-green-500">
                            <Check/>
                        </Toggle>
                        <Toggle id={"confirm-change"} onClick={() => {
                            setEditable(false)
                        }}
                                className="bg-red-500 hover:data-[state=on]:bg-red-700 hover:data-[state=off]:bg-red-700 data-[state=on]:bg-red-500">
                            <X/>
                        </Toggle>
                    </div>
                    :
                    <div className={"flex space-x-4"}>
                        {
                            visible ?
                                <Toggle onClick={() => {
                                    setVisible(false);
                                }} className={"bg-accent"}>
                                    <Eye className="right-4 top-4 z-10"/>
                                </Toggle> :
                                <Toggle onClick={() => {
                                    setVisible(true);
                                }} className={"bg-accent"}>
                                    <EyeOff className="right-4 top-4 z-10"/>
                                </Toggle>
                        }

                        <Toggle onClick={() => {
                            setEditable(true);
                        }} className="bg-accent">
                            <PenLine/>
                        </Toggle>
                    </div>
                }
            </div>
        </div>
    );
}
