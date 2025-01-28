/*
 *  Copyright (c) 2025 TeamDev
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
import {Combobox} from "@/components/ui/common/combobox.tsx";
import {PreferenceSwitch} from "@/components/ui/common/preference-switch.tsx";
import {useEffect, useRef, useState} from "react";
import {GeneralSchema} from "@/gen/preferences_pb.ts";
import {getGeneral, setGeneral} from "@/rpc/preferences-service.ts";
import {create} from "@bufbuild/protobuf";
import {
    checkForUpdatesFromStorage,
    languageFromStorage,
    launchAtStartupFromStorage,
    saveCheckForUpdatesInStorage,
    saveLanguageInStorage,
    saveLaunchAtStartupInStorage
} from "@/storage/general.ts";
import {
    englishLanguage,
    frenchLanguage,
    fromLanguage,
    germanLanguage,
    LanguageOption,
    toLanguage
} from "@/converter/language.ts";

/**
 * Available language options.
 */
const languages: LanguageOption[] = [
    englishLanguage,
    germanLanguage,
    frenchLanguage
];

/**
 * A component that allows managing general application preferences.
 *
 * @constructor
 */
export function General() {
    const [launchAtStartup, setLaunchAtStartup] =
        useState<boolean>(launchAtStartupFromStorage());
    const [language, setLanguage] =
        useState<LanguageOption>(languageFromStorage());
    const [checkForUpdates, setCheckForUpdates] =
        useState<boolean>(checkForUpdatesFromStorage());
    const isInitialized = useRef(false);

    useEffect(() => {
        getGeneral(general => {
            const language = fromLanguage(general.language);

            setLaunchAtStartup(general.launchAtStartup);
            setLanguage(language);
            setCheckForUpdates(general.checkForUpdates);

            saveLaunchAtStartupInStorage(general.launchAtStartup);
            saveCheckForUpdatesInStorage(general.checkForUpdates);
            saveLanguageInStorage(language);
            isInitialized.current = true;
        });
    }, []);

    useEffect(() => {
        if (!isInitialized.current) {
            return;
        }
        const newGeneral = create(GeneralSchema, {
            launchAtStartup,
            language: toLanguage(language),
            checkForUpdates
        });
        saveLanguageInStorage(language);
        saveCheckForUpdatesInStorage(checkForUpdates);
        saveLaunchAtStartupInStorage(launchAtStartup);
        setGeneral(newGeneral);
    }, [launchAtStartup, language, checkForUpdates]);

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
                <PreferenceSwitch isChecked={launchAtStartup} onChange={setLaunchAtStartup}/>
            </div>
            <div className="w-full inline-flex items-center space-y-2 justify-between py-1">
                <div className="pr-8">
                    <p className="text-sm">Language</p>
                    <p className="text-xs text-muted-foreground">
                        Choose the language for the application’s interface.
                    </p>
                </div>
                <Combobox options={languages} currentOption={language}
                          onSelect={value => {
                              setLanguage(value as LanguageOption);
                          }}/>
            </div>
            <div className="w-full inline-flex items-center justify-between py-1">
                <div className="pr-8">
                    <p className="text-sm">Check for updates automatically</p>
                    <p className="text-xs text-muted-foreground">
                        Allow to check for updates in the background.
                    </p>
                </div>
                <PreferenceSwitch isChecked={checkForUpdates} onChange={setCheckForUpdates}/>
            </div>
        </div>
    );
}
