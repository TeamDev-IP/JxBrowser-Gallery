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

import {GreenSwitch} from "@/components/green-switch.tsx";
import {useEffect, useRef, useState} from "react";
import {
    desktopNotificationsKeyFromStorage,
    emailNotificationsFromStorage,
    saveDesktopNotificationsKeyInStorage,
    saveEmailNotificationsInStorage
} from "@/storage/notifications.ts";
import {getNotifications, setNotifications} from "@/rpc/app-preferences-service.ts";
import {create} from "@bufbuild/protobuf";
import {NotificationsSchema} from "@/gen/notifications_pb.ts";

export function Notifications() {
    const [desktopEnabled, setDesktopEnabled] =
        useState<boolean>(desktopNotificationsKeyFromStorage());
    const [emailEnabled, setEmailEnabled] =
        useState<boolean>(emailNotificationsFromStorage());
    const isInitialized = useRef(false);

    useEffect(() => {
        getNotifications(notifications => {
            setEmailEnabled(notifications.emailEnabled);
            setDesktopEnabled(notifications.desktopEnabled);
            saveEmailNotificationsInStorage(notifications.emailEnabled);
            saveDesktopNotificationsKeyInStorage(notifications.desktopEnabled);
            isInitialized.current = true;
        })
    }, []);

    useEffect(() => {
        if (!isInitialized.current) {
            return;
        }
        const newNotificationsPrefs = create(NotificationsSchema, {
            emailEnabled,
            desktopEnabled,
        });
        setNotifications(newNotificationsPrefs);
        saveEmailNotificationsInStorage(emailEnabled);
        saveDesktopNotificationsKeyInStorage(desktopEnabled);
    }, [desktopEnabled, emailEnabled]);

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
                <GreenSwitch onChange={setEmailEnabled} isChecked={emailEnabled}/>
            </div>
            <div className="w-full inline-flex items-center justify-between py-1">
                <div className="pr-8">
                    <p className="text-sm">Desktop notifications</p>
                    <p className="text-xs text-muted-foreground">
                        Receive personal notifications on the desktop.
                    </p>
                </div>
                <GreenSwitch onChange={setDesktopEnabled} isChecked={desktopEnabled}/>
            </div>
        </div>
    );
}