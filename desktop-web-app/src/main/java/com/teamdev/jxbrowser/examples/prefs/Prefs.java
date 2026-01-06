/*
 *  Copyright 2026, TeamDev
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

package com.teamdev.jxbrowser.examples.prefs;

import com.teamdev.jxbrowser.prefs.Prefs.Account;
import com.teamdev.jxbrowser.prefs.Prefs.Appearance;
import com.teamdev.jxbrowser.prefs.Prefs.General;
import com.teamdev.jxbrowser.prefs.Prefs.Notifications;

/**
 * The application preferences.
 */
public final class Prefs {

    private Account account;
    private General general;
    private Appearance appearance;
    private Notifications notifications;
    private byte[] profilePicture;

    public byte[] profilePicture() {
        return profilePicture;
    }

    public void profilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Account account() {
        return account;
    }

    public void account(Account account) {
        this.account = account;
    }

    public General general() {
        return general;
    }

    public void general(General general) {
        this.general = general;
    }

    public Appearance appearance() {
        return appearance;
    }

    public void appearance(Appearance appearance) {
        this.appearance = appearance;
    }

    public Notifications notifications() {
        return notifications;
    }

    public void notifications(Notifications notifications) {
        this.notifications = notifications;
    }
}
