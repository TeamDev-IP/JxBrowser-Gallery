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

import {PreferenceSwitch} from "@/components/ui/common/preference-switch.tsx";
import {useEffect, useState} from "react";
import {
    desktopNotificationsKeyFromStorage,
    emailNotificationsFromStorage,
    saveDesktopNotificationsKeyInStorage,
    saveEmailNotificationsInStorage
} from "@/storage/notifications.ts";
import {preferencesClient} from "@/rpc/preference-client.ts";
import {create} from "@bufbuild/protobuf";
import {NotificationsSchema} from "@/gen/preferences_pb.ts";

/**
 * A component that allows managing the notification preferences.
 *
 * @constructor
 */
export function Notifications() {
    const [desktopEnabled, setDesktopEnabled] =
        useState<boolean>(desktopNotificationsKeyFromStorage());
    const [emailEnabled, setEmailEnabled] =
        useState<boolean>(emailNotificationsFromStorage());

    useEffect(() => {
        (async () => {
            const notifications = await preferencesClient.getNotifications({});
            setEmailEnabled(notifications.emailEnabled);
            setDesktopEnabled(notifications.desktopEnabled);
            saveEmailNotificationsInStorage(notifications.emailEnabled);
            saveDesktopNotificationsKeyInStorage(notifications.desktopEnabled);
        })();
    });

    const onUpdateNotifications = ({
                                       newDesktopEnabled = desktopEnabled,
                                       newEmailEnabled = emailEnabled,
                                   }: {
        newDesktopEnabled?: boolean;
        newEmailEnabled?: boolean,
    }) => {
        const newNotifications = create(NotificationsSchema, {
            emailEnabled: newEmailEnabled,
            desktopEnabled: newDesktopEnabled,
        });
        preferencesClient.setNotifications(newNotifications);
    };

    return (
        <div className="space-y-4">
            <h1 className="text-2xl font-semibold">Notifications</h1>
            <Separator className="my-4 h-[1px] w-full"/>
            <div className="w-full inline-flex items-center justify-between py-1">
                <div className="pr-8">
                    <p className="text-sm">Email notifications</p>
                    <p className="text-xs text-muted-foreground">
                        Receive an email digest for unread notifications. Notifications will be
                        grouped together and sent based on their urgency.
                    </p>
                </div>
                <PreferenceSwitch onChange={checked => {
                    onUpdateNotifications({newEmailEnabled: checked});
                    saveEmailNotificationsInStorage(checked);
                    setEmailEnabled(checked);
                }} isChecked={emailEnabled}/>
            </div>
            <div className="w-full inline-flex items-center justify-between py-1">
                <div className="pr-8">
                    <p className="text-sm">Desktop notifications</p>
                    <p className="text-xs text-muted-foreground">
                        Receive personal notifications on the desktop.
                    </p>
                </div>
                <PreferenceSwitch onChange={checked => {
                    onUpdateNotifications({newDesktopEnabled: checked});
                    saveDesktopNotificationsKeyInStorage(checked);
                    setDesktopEnabled(checked);
                }} isChecked={desktopEnabled}/>
            </div>
        </div>
    );
}
