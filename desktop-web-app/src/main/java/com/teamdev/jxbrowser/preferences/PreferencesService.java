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

package com.teamdev.jxbrowser.preferences;

import com.google.protobuf.ByteString;
import com.google.protobuf.BytesValue;
import com.google.protobuf.Empty;
import com.teamdev.jxbrowser.preferences.AccountOuterClass.Account;
import com.teamdev.jxbrowser.preferences.AccountOuterClass.ProfilePicture;
import com.teamdev.jxbrowser.preferences.AppPreferencesServiceGrpc.AppPreferencesServiceImplBase;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.nio.file.Files;

import static com.teamdev.jxbrowser.preferences.AccountOuterClass.TwoFactorAuthentication.EMAIL;
import static com.teamdev.jxbrowser.preferences.PreferencesFile.APP_PREFERENCES_FILE;

public final class PreferencesService extends AppPreferencesServiceImplBase {

    private static final Preferences appPreferences;

    static {
        if (!APP_PREFERENCES_FILE.exists()) {
            try {
                Files.createFile(APP_PREFERENCES_FILE.toPath());
                Account user = Account.newBuilder()
                        .setFullName("John Doe")
                        .setEmail("john.doe@mail.com")
                        .setBiometricAuthentication(false)
                        .setTwoFactorAuthentication(EMAIL)
                        .build();
                appPreferences = new Preferences();
                appPreferences.account(user);
                PreferencesFile.write(appPreferences);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            appPreferences = PreferencesFile.read();
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
        responseObserver.onCompleted();
    }

    @Override
    public void setProfilePicture(ProfilePicture request, StreamObserver<Empty> responseObserver) {
        byte[] content = request.getContent().toByteArray();
        appPreferences.profilePicture(content);
        PreferencesFile.write(appPreferences);
        responseObserver.onCompleted();
    }

    @Override
    public void getProfilePicture(Empty request, StreamObserver<BytesValue> responseObserver) {
        responseObserver.onNext(BytesValue.newBuilder()
                .setValue(ByteString.copyFrom(appPreferences.profilePicture()))
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void setGeneral(GeneralOuterClass.General request, StreamObserver<Empty> responseObserver) {
        appPreferences.general(request);
        PreferencesFile.write(appPreferences);
        responseObserver.onCompleted();
    }

    @Override
    public void getGeneral(Empty request, StreamObserver<GeneralOuterClass.General> responseObserver) {
        responseObserver.onNext(appPreferences.general());
        responseObserver.onCompleted();
    }

    @Override
    public void setAppearance(AppearanceOuterClass.Appearance request, StreamObserver<Empty> responseObserver) {
        appPreferences.appearance(request);
        PreferencesFile.write(appPreferences);
        responseObserver.onCompleted();
    }

    @Override
    public void getAppearance(Empty request, StreamObserver<AppearanceOuterClass.Appearance> responseObserver) {
        responseObserver.onNext(appPreferences.appearance());
        responseObserver.onCompleted();
    }
}
