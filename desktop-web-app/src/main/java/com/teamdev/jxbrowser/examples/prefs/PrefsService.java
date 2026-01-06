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

import com.google.protobuf.ByteString;
import com.google.protobuf.Empty;
import com.teamdev.jxbrowser.prefs.Prefs.Account;
import com.teamdev.jxbrowser.prefs.Prefs.Appearance;
import com.teamdev.jxbrowser.prefs.Prefs.General;
import com.teamdev.jxbrowser.prefs.Prefs.Notifications;
import com.teamdev.jxbrowser.prefs.Prefs.ProfilePicture;
import com.teamdev.jxbrowser.prefs.PrefsServiceGrpc.PrefsServiceImplBase;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.nio.file.Files;

import static com.teamdev.jxbrowser.examples.prefs.PrefsFile.PREFERENCES_FILE;
import static com.teamdev.jxbrowser.prefs.Prefs.FontSize.DEFAULT;
import static com.teamdev.jxbrowser.prefs.Prefs.Theme.SYSTEM;
import static com.teamdev.jxbrowser.prefs.Prefs.TwoFactorAuthentication.EMAIL;

/**
 * A gRPC service for reading and updating app preferences.
 */
public final class PrefsService extends PrefsServiceImplBase {

    private final Prefs appPrefs;

    public PrefsService() {
        if (PREFERENCES_FILE.exists()) {
            appPrefs = PrefsFile.read();
        } else {
            appPrefs = initPreferences();
        }
    }

    @Override
    public void getAccount(Empty request, StreamObserver<Account> responseObserver) {
        responseObserver.onNext(appPrefs.account());
        responseObserver.onCompleted();
    }

    @Override
    public void setAccount(Account request, StreamObserver<Empty> responseObserver) {
        appPrefs.account(request);
        PrefsFile.write(appPrefs);
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

    @Override
    public void setProfilePicture(ProfilePicture request, StreamObserver<Empty> responseObserver) {
        byte[] content = request.getContent().toByteArray();
        appPrefs.profilePicture(content);
        PrefsFile.write(appPrefs);
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

    @Override
    public void getProfilePicture(Empty request, StreamObserver<ProfilePicture> responseObserver) {
        responseObserver.onNext(ProfilePicture.newBuilder()
                .setContent(ByteString.copyFrom(appPrefs.profilePicture()))
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void setGeneral(General request, StreamObserver<Empty> responseObserver) {
        appPrefs.general(request);
        PrefsFile.write(appPrefs);
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

    @Override
    public void getGeneral(Empty request, StreamObserver<General> responseObserver) {
        responseObserver.onNext(appPrefs.general());
        responseObserver.onCompleted();
    }

    @Override
    public void setAppearance(Appearance request, StreamObserver<Empty> responseObserver) {
        appPrefs.appearance(request);
        PrefsFile.write(appPrefs);
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

    @Override
    public void getAppearance(Empty request, StreamObserver<Appearance> responseObserver) {
        responseObserver.onNext(appPrefs.appearance());
        responseObserver.onCompleted();
    }

    @Override
    public void setNotifications(Notifications request, StreamObserver<Empty> responseObserver) {
        appPrefs.notifications(request);
        PrefsFile.write(appPrefs);
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

    @Override
    public void getNotifications(Empty request, StreamObserver<Notifications> responseObserver) {
        responseObserver.onNext(appPrefs.notifications());
        responseObserver.onCompleted();
    }

    /**
     * Initializes the default preferences and writes them into the file.
     */
    private Prefs initPreferences() {
        final Prefs appPrefs;
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
            appPrefs = new Prefs();
            appPrefs.account(user);
            appPrefs.appearance(appearance);
            PrefsFile.write(appPrefs);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return appPrefs;
    }

}
