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

import com.google.protobuf.ByteString;
import com.google.protobuf.Empty;
import com.teamdev.jxbrowser.preferences.Preferences.Account;
import com.teamdev.jxbrowser.preferences.Preferences.Appearance;
import com.teamdev.jxbrowser.preferences.Preferences.General;
import com.teamdev.jxbrowser.preferences.Preferences.Notifications;
import com.teamdev.jxbrowser.preferences.Preferences.ProfilePicture;
import com.teamdev.jxbrowser.preferences.PreferencesServiceGrpc.PreferencesServiceImplBase;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.nio.file.Files;

import static com.teamdev.jxbrowser.examples.preferences.PreferencesFile.PREFERENCES_FILE;
import static com.teamdev.jxbrowser.preferences.Preferences.FontSize.DEFAULT;
import static com.teamdev.jxbrowser.preferences.Preferences.Theme.SYSTEM;
import static com.teamdev.jxbrowser.preferences.Preferences.TwoFactorAuthentication.EMAIL;

/**
 * A gRPC service for reading and updating preferences.
 */
public final class PreferencesService extends PreferencesServiceImplBase {

    private final Preferences appPreferences;

    public PreferencesService() {
        if (PREFERENCES_FILE.exists()) {
            appPreferences = PreferencesFile.read();
        } else {
            appPreferences = initPreferences();
        }
    }

    @Override
    public void getAccount(Empty request, StreamObserver<Account> responseObserver) {
        responseObserver.onNext(appPreferences.account());
        responseObserver.onCompleted();
    }

    @Override
    public void setAccount(Account request, StreamObserver<Empty> responseObserver) {
        appPreferences.account(request);
        PreferencesFile.write(appPreferences);
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

    @Override
    public void setProfilePicture(ProfilePicture request, StreamObserver<Empty> responseObserver) {
        byte[] content = request.getContent().toByteArray();
        appPreferences.profilePicture(content);
        PreferencesFile.write(appPreferences);
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

    @Override
    public void getProfilePicture(Empty request, StreamObserver<ProfilePicture> responseObserver) {
        responseObserver.onNext(ProfilePicture.newBuilder()
                .setContent(ByteString.copyFrom(appPreferences.profilePicture()))
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void setGeneral(General request, StreamObserver<Empty> responseObserver) {
        appPreferences.general(request);
        PreferencesFile.write(appPreferences);
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

    @Override
    public void getGeneral(Empty request, StreamObserver<General> responseObserver) {
        responseObserver.onNext(appPreferences.general());
        responseObserver.onCompleted();
    }

    @Override
    public void setAppearance(Appearance request, StreamObserver<Empty> responseObserver) {
        appPreferences.appearance(request);
        PreferencesFile.write(appPreferences);
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

    @Override
    public void getAppearance(Empty request, StreamObserver<Appearance> responseObserver) {
        responseObserver.onNext(appPreferences.appearance());
        responseObserver.onCompleted();
    }

    @Override
    public void setNotifications(Notifications request, StreamObserver<Empty> responseObserver) {
        appPreferences.notifications(request);
        PreferencesFile.write(appPreferences);
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

    @Override
    public void getNotifications(Empty request, StreamObserver<Notifications> responseObserver) {
        responseObserver.onNext(appPreferences.notifications());
        responseObserver.onCompleted();
    }

    private Preferences initPreferences() {
        final Preferences appPreferences;
        try {
            Files.createFile(PREFERENCES_FILE.toPath());
            Account user = Account.newBuilder()
                    .setFullName("John Doe")
                    .setEmail("john.doe@mail.com")
                    .setBiometricAuthentication(false)
                    .setTwoFactorAuthentication(EMAIL)
                    .build();
            Appearance appearance = Appearance.newBuilder()
                    .setFontSize(DEFAULT)
                    .setTheme(SYSTEM)
                    .build();
            appPreferences = new Preferences();
            appPreferences.account(user);
            appPreferences.appearance(appearance);
            PreferencesFile.write(appPreferences);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return appPreferences;
    }

}
