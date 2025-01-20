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

import {Separator} from "@/components/ui/separator.tsx";
import {Combobox} from "@/components/combobox.tsx";
import {GreenSwitch} from "@/components/green-switch.tsx";
import {useEffect, useRef, useState} from "react";
import {GeneralSchema, Language} from "@/gen/general_pb.ts";
import {getGeneral, setGeneral} from "@/rpc/app-preferences-service.ts";
import {create} from "@bufbuild/protobuf";
import {
    checkForUpdatesFromStorage,
    englishLanguage,
    frenchLanguage,
    germanLanguage,
    languageFromStorage,
    LanguageOption,
    launchAtStartupFromStorage,
    saveCheckForUpdatesInStorage,
    saveLanguageInStorage,
    saveLaunchAtStartupInStorage
} from "@/storage/general.ts";

const languages: LanguageOption[] = [
    englishLanguage,
    germanLanguage,
    frenchLanguage
]

function languageString(value: Language): LanguageOption {
    if (value === Language.ENGLISH) {
        return englishLanguage;
    } else if (value === Language.GERMAN) {
        return germanLanguage;
    } else if (value === Language.FRENCH) {
        return frenchLanguage;
    } else {
        throw new TypeError("Incorrect language.");
    }
}

function languageEnum(value: LanguageOption): Language {
    if (value === englishLanguage) {
        return Language.ENGLISH;
    } else if (value === germanLanguage) {
        return Language.GERMAN;
    } else {
        return Language.FRENCH;
    }
}

export function General() {
    const [launchAtStartupPref, setLaunchAtStartupPref] =
        useState<boolean>(launchAtStartupFromStorage());
    const [languagePref, setLanguagePref] =
        useState<LanguageOption>(languageFromStorage());
    const [checkForUpdatesPref, setCheckForUpdatesPref] =
        useState<boolean>(checkForUpdatesFromStorage());
    const isInitialized = useRef(false);

    useEffect(() => {
        getGeneral(generalPrefs => {
            setLaunchAtStartupPref(generalPrefs.launchAtStartup);
            setLanguagePref(languageString(generalPrefs.language));
            setCheckForUpdatesPref(generalPrefs.checkForUpdates);

            saveLaunchAtStartupInStorage(generalPrefs.launchAtStartup);
            saveCheckForUpdatesInStorage(generalPrefs.checkForUpdates);
            saveLanguageInStorage(languageString(generalPrefs.language))
            isInitialized.current = true;
        });
    }, []);

    useEffect(() => {
        if (!isInitialized.current) {
            return;
        }
        const newGeneralPrefs = create(GeneralSchema, {
            launchAtStartup: launchAtStartupPref,
            language: languageEnum(languagePref),
            checkForUpdates: checkForUpdatesPref
        });
        saveLanguageInStorage(languagePref);
        saveCheckForUpdatesInStorage(checkForUpdatesPref);
        saveLaunchAtStartupInStorage(launchAtStartupPref);
        setGeneral(newGeneralPrefs);
    }, [launchAtStartupPref, languagePref, checkForUpdatesPref]);

    return (
        <div className="space-y-4">
            <h1 className="text-2xl font-semibold">General</h1>
            <Separator className="my-4 h-[1px] w-full"/>
            <div className="w-full inline-flex items-center justify-between py-1">
                <div className="pr-8">
                    <p className="text-sm">Launch at startup</p>
                    <p className="text-xs text-muted-foreground">
                        Enable this option to automatically start the application when your device
                        boots up.
                    </p>
                </div>
                <GreenSwitch isChecked={launchAtStartupPref} onChange={setLaunchAtStartupPref}/>
            </div>
            <div className="w-full inline-flex items-center space-y-2 justify-between py-1">
                <div className="pr-8">
                    <p className="text-sm">Language</p>
                    <p className="text-xs text-muted-foreground">
                        Choose the language for the application’s interface.
                    </p>
                </div>
                <Combobox options={languages} currentOption={languagePref}
                          onSelect={value => {
                              setLanguagePref(value as LanguageOption);
                          }}/>
            </div>
            <div className="w-full inline-flex items-center justify-between py-1">
                <div className="pr-8">
                    <p className="text-sm">Check for updates automatically</p>
                    <p className="text-xs text-muted-foreground">
                        Allow to check for updates in the background.
                    </p>
                </div>
                <GreenSwitch isChecked={checkForUpdatesPref} onChange={setCheckForUpdatesPref}/>
            </div>
        </div>
    );
}