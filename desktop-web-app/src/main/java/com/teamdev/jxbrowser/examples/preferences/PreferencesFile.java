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

package com.teamdev.jxbrowser.examples.preferences;

import com.google.gson.Gson;
import com.teamdev.jxbrowser.examples.AppDetails;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * A utility for reading from and writing to the preferences file.
 */
public final class PreferencesFile {

    static final File APP_PREFERENCES_FILE =
            new File(AppDetails.INSTANCE.appResourcesDir()
                                        .resolve("preferences.json")
                                        .toString());

    private PreferencesFile() {

    }

    /**
     * Reads {@link Preferences} from the preferences file.
     */
    public static Preferences read() {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(APP_PREFERENCES_FILE)) {
            return gson.fromJson(reader, Preferences.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Writes updated {@link Preferences} to the preferences file.
     */
    public static void write(Preferences preferences) {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(APP_PREFERENCES_FILE)) {
            gson.toJson(preferences, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
