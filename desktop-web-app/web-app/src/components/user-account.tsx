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

import {Combobox} from "@/components/combobox.tsx";
import {GreenSwitch} from "@/components/green-switch.tsx";
import {EditableAvatar} from "@/components/editable-avatar.tsx";
import {EditableInput} from "@/components/editable-input.tsx";
import {GuidingLine} from "@/components/guiding-line.tsx";
import {useEffect, useRef, useState} from "react";
import {
    getAccount,
    getProfilePicture,
    setAccount,
    setProfilePicture
} from "@/rpc/app-preferences-service.ts";
import {AccountSchema, ProfilePictureSchema} from "@/gen/account_pb.ts";
import {create} from "@bufbuild/protobuf";
import {
    biometricAuthenticationFromStorage,
    saveBiometricAuthenticationInStorage,
    saveTfaInStorage,
    tfaFromStorage,
} from "@/storage/authentications.ts";
import {imageToDataUri} from "@/components/converter/image.ts";
import {
    emailTfa,
    fromTfa,
    passkeyTfa,
    smsTfa,
    TfaMethod,
    toTfa
} from "@/components/converter/tfa-method.ts";

/**
 * Available two-factor authentication methods.
 */
const authentications: TfaMethod[] = [
    emailTfa,
    smsTfa,
    passkeyTfa
];

/**
 * A component that allows managing the user's account.
 *
 * @constructor
 */
export function UserAccount() {
    const [profilePictureDataUri, setProfilePictureDataUri] = useState<string>("");
    const [fullName, setFullName] = useState<string>("");
    const [email, setEmail] = useState<string>("");
    const [twoFactorAuthentication, setTwoFactorAuthentication] =
        useState<TfaMethod>(tfaFromStorage());
    const [biometricAuthentication, setBiometricAuthentication] =
        useState<boolean>(biometricAuthenticationFromStorage());
    const isInitialized = useRef(false);

    useEffect(() => {
        getAccount(account => {
            setEmail(account.email);
            setFullName(account.fullName);

            const tfaMethod = fromTfa(account.twoFactorAuthentication);
            setTwoFactorAuthentication(tfaMethod);
            setBiometricAuthentication(account.biometricAuthentication);

            saveTfaInStorage(tfaMethod);
            saveBiometricAuthenticationInStorage(account.biometricAuthentication);
            isInitialized.current = true;
        });
        getProfilePicture(contentBytes => setProfilePictureDataUri(imageToDataUri(contentBytes)));
    }, []);

    useEffect(() => {
        if (!isInitialized.current) {
            return;
        }
        const newAccount = create(AccountSchema, {
            fullName,
            email,
            twoFactorAuthentication: toTfa(twoFactorAuthentication),
            biometricAuthentication
        });
        setAccount(newAccount);
        saveTfaInStorage(twoFactorAuthentication);
        saveBiometricAuthenticationInStorage(biometricAuthentication);
    }, [fullName, email, twoFactorAuthentication, biometricAuthentication]);

    return (
        <div className="space-y-4">
            <h1 className="text-2xl font-semibold">Account</h1>
            <GuidingLine/>
            <EditableAvatar onChange={file => {
                const reader = new FileReader();
                reader.onload = () => {
                    const newProfilePicture = create(ProfilePictureSchema, {
                        content: new Uint8Array(reader.result as ArrayBuffer)
                    });
                    setProfilePicture(newProfilePicture, () => {
                        setProfilePictureDataUri(imageToDataUri(newProfilePicture.content));
                    });
                };
                reader.readAsArrayBuffer(file);
            }} pictureDataUri={profilePictureDataUri}
                            fallback={fullName.split(" ").map(it => it[0]).join("")}/>
            <EditableInput title={"Email"} isEmail={true}
                           onChange={setEmail} value={email} id={"email"}/>
            <EditableInput title={"Full name"} isEmail={false}
                           value={fullName}
                           onChange={setFullName} id={"fullname"}/>
            <GuidingLine/>
            <div className="w-full inline-flex items-center space-y-2 justify-between py-1">
                <div className="pr-8">
                    <p className="text-sm">Two-factor authentication</p>
                    <p className="text-xs text-muted-foreground text-gray-500">
                        Select an extra layer of security by requiring a code when logging in.
                    </p>
                </div>
                <Combobox onSelect={value => {
                    setTwoFactorAuthentication(value as TfaMethod);
                }} options={authentications}
                          currentOption={twoFactorAuthentication}/>
            </div>
            <div className="w-full inline-flex items-center justify-between py-1">
                <div className="pr-8">
                    <p className="text-sm">Biometric authentication</p>
                    <p className="text-xs text-muted-foreground text-gray-500">
                        Allow authentication via fingerprints or Face ID.
                    </p>
                </div>
                <GreenSwitch onChange={setBiometricAuthentication}
                             isChecked={biometricAuthentication}/>
            </div>
        </div>
    );
}
