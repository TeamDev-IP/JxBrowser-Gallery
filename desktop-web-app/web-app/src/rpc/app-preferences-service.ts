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

import {createGrpcWebTransport} from "@connectrpc/connect-web";
import {createCallbackClient} from "@connectrpc/connect";
import {Account, ProfilePicture} from "@/gen/account_pb.ts";
import {AppPreferencesService} from "@/gen/app_preferences_pb.ts";
import {General} from "@/gen/general_pb.ts";
import {Appearance} from "@/gen/appearance_pb.ts";
import {Notifications} from "@/gen/notifications_pb.ts";

/**
 * A port for RPC communication obtained from the server side via
 * the JxBrowser's Java-Js bridge.
 */
declare const rpcPort: Number;

const transport = createGrpcWebTransport({
    baseUrl: `http://localhost:${rpcPort}`,
});
const appPreferencesClient = createCallbackClient(AppPreferencesService, transport);

/**
 * Sends a request for the account info.
 *
 * @param callback a callback that is invoked when the account info has been received
 */
function getAccount(callback?: (account: Account) => void) {
    appPreferencesClient.getAccount({}, (_err, res) => {
        callback && callback(res);
    });
}

/**
 * Sends a request to update the account info.
 *
 * @param newAccount the updated account info
 * @param callback a callback that is invoked when the account has been updated
 */
function setAccount(newAccount: Account, callback?: () => void) {
    appPreferencesClient.setAccount(newAccount, (_err) => {
        callback && callback();
    });
}

/**
 * Sends a request to update the user's profile picture.
 *
 * @param newProfilePicture the updated picture
 * @param callback a callback that is invoked when the picture has been updated
 */
function setProfilePicture(newProfilePicture: ProfilePicture, callback?: () => void) {
    appPreferencesClient.setProfilePicture(newProfilePicture, (_err) => {
        callback && callback();
    });
}

/**
 * Sends a request for the user's profile picture.
 *
 * @param callback a callback that is invoked when the account info has been received
 */
function getProfilePicture(callback?: (src: Uint8Array) => void) {
    appPreferencesClient.getProfilePicture({}, (_err, res) => {
        callback && callback(res.content);
    });
}

/**
 * Sends a request to update the general preferences.
 *
 * @param newGeneral the updated preferences
 * @param callback a callback that is invoked when the preferences have been updated
 */
function setGeneral(newGeneral: General, callback?: () => void) {
    appPreferencesClient.setGeneral(newGeneral, (_err) => {
        callback && callback();
    });
}

/**
 * Sends a request for the general preferences.
 *
 * @param callback a callback that is invoked when the preferences have been received
 */
function getGeneral(callback?: (general: General) => void) {
    appPreferencesClient.getGeneral({}, (_err, res) => {
        callback && callback(res);
    });
}

/**
 * Sends a request to update the appearance preferences.
 *
 * @param newAppearance the updated preferences
 * @param callback a callback that is invoked when the preferences have been updated
 */
function setAppearance(newAppearance: Appearance, callback?: () => void) {
    appPreferencesClient.setAppearance(newAppearance, (_err) => {
        callback && callback();
    });
}

/**
 * Sends a request for the appearance preferences.
 *
 * @param callback a callback that is invoked when the preferences have been received
 */
function getAppearance(callback?: (appearancePrefs: Appearance) => void) {
    appPreferencesClient.getAppearance({}, (_err, res) => {
        callback && callback(res);
    });
}

/**
 * Sends a request to update the notification preferences.
 *
 * @param newNotifications the updated preferences
 * @param callback a callback that is invoked when the preferences have been updated
 */
function setNotifications(newNotifications: Notifications, callback?: () => void) {
    appPreferencesClient.setNotifications(newNotifications, (_err) => {
        callback && callback();
    });
}

/**
 * Sends a request for the notification preferences.
 *
 * @param callback a callback that is invoked when the preferences have been received
 */
function getNotifications(callback?: (notificationPrefs: Notifications) => void) {
    appPreferencesClient.getNotifications({}, (_err, res) => {
        callback && callback(res);
    });
}

export {
    getAccount,
    setAccount,
    setProfilePicture,
    getProfilePicture,
    setGeneral,
    getGeneral,
    setAppearance,
    getAppearance,
    setNotifications,
    getNotifications
};
