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

import {Separator} from "@/components/ui/separator.tsx";
import {Combobox} from "@/components/combobox.tsx";
import {GreenSwitch} from "@/components/green-switch.tsx";
import {EditableAvatar} from "@/components/editable-avatar.tsx";
import {EditableLabel, EditableLabelType} from "@/components/editable-label.tsx";
import {GuidingLine} from "@/components/guiding-line.tsx";
import {useEffect, useRef, useState} from "react";
import {
    getAccount,
    getProfilePicture,
    setAccount,
    setProfilePicture
} from "@/rpc/app-preferences-service.ts";
import {AccountSchema, ProfilePictureSchema, TwoFactorAuthentication} from "@/gen/account_pb.ts";
import {create} from "@bufbuild/protobuf";
import {
    biometricAuthenticationFromStorage,
    saveBiometricAuthenticationInStorage,
    saveTfaInStorage,
    tfaEmail,
    tfaFromStorage,
    TfaMethod,
    tfaPasskey,
    tfaSms
} from "@/storage/authentications.ts";
import {imageToDataUri} from "@/components/converter/image.ts";

const authentications: TfaMethod[] = [
    tfaEmail,
    tfaSms,
    tfaPasskey
]

function tfaMethod(value: TwoFactorAuthentication): TfaMethod {
    if (value === TwoFactorAuthentication.EMAIL) {
        return tfaEmail;
    } else if (value === TwoFactorAuthentication.SMS) {
        return tfaSms;
    } else if (value === TwoFactorAuthentication.PASS_KEY) {
        return tfaPasskey;
    } else {
        throw new TypeError("Incorrect two-factor authentication.");
    }
}

function tfaEnum(value: TfaMethod): TwoFactorAuthentication {
    if (value === tfaEmail) {
        return TwoFactorAuthentication.EMAIL;
    } else if (value === tfaSms) {
        return TwoFactorAuthentication.SMS;
    } else {
        return TwoFactorAuthentication.PASS_KEY;
    }
}

export function UserAccount() {
    const [userProfilePicture, setUserProfilePicture] = useState<string>("");
    const [userFullName, setUserFullName] = useState<string>("");
    const [userEmail, setUserEmail] = useState<string>("");
    const [userTwoFactorAuthentication, setUserTwoFactorAuthentication] =
        useState<TfaMethod>(tfaFromStorage());
    const [userBiometricAuthentication, setUserBiometricAuthentication] =
        useState<boolean>(biometricAuthenticationFromStorage());
    const isInitialized = useRef(false);

    useEffect(() => {
        getAccount(account => {
            setUserEmail(account.email);
            setUserFullName(account.fullName);

            const tfaMethodValue = tfaMethod(account.twoFactorAuthentication);
            setUserTwoFactorAuthentication(tfaMethodValue);
            setUserBiometricAuthentication(account.biometricAuthentication);

            saveTfaInStorage(tfaMethodValue);
            saveBiometricAuthenticationInStorage(account.biometricAuthentication);
            isInitialized.current = true;
        });
        getProfilePicture(contentBytes => {
            setUserProfilePicture(imageToDataUri(contentBytes));
        });
    }, []);

    useEffect(() => {
        if (!isInitialized.current) {
            return;
        }
        const newAccount = create(AccountSchema, {
            fullName: userFullName,
            email: userEmail,
            twoFactorAuthentication: tfaEnum(userTwoFactorAuthentication),
            biometricAuthentication: userBiometricAuthentication
        });
        console.log("new ");
        setAccount(newAccount);
        saveTfaInStorage(userTwoFactorAuthentication);
        saveBiometricAuthenticationInStorage(userBiometricAuthentication);
    }, [userFullName, userEmail, userTwoFactorAuthentication, userBiometricAuthentication]);

    return (
        <div className="space-y-4">
            <h1 className="text-2xl font-semibold">Account</h1>
            <Separator className="my-2 h-[1px] w-full"/>
            <EditableAvatar onChange={file => {
                const reader = new FileReader();
                reader.onload = () => {
                    const newProfilePicture = create(ProfilePictureSchema, {
                        extension: file.name.split(".").pop(),
                        content: new Uint8Array(reader.result as ArrayBuffer)
                    });
                    setProfilePicture(newProfilePicture, () => {
                        setUserProfilePicture(imageToDataUri(newProfilePicture.content));
                    })
                };
                reader.readAsArrayBuffer(file);
            }} pictureSrc={userProfilePicture}
                            fallback={userFullName.split(" ").map(it => it[0]).join("")}/>
            <EditableLabel title={"Email"} type={EditableLabelType.EMAIL}
                           onChange={setUserEmail} defaultValue={userEmail} id={"email"}/>
            <EditableLabel title={"Full name"} type={EditableLabelType.TEXT}
                           defaultValue={userFullName}
                           onChange={setUserFullName} id={"fullname"}/>
            <GuidingLine/>
            <div className="w-full inline-flex items-center space-y-2 justify-between py-1">
                <div className="pr-8">
                    <p className="text-sm">Two-factor authentication</p>
                    <p className="text-xs text-muted-foreground text-gray-500">
                        Select an extra layer of security by requiring a code when logging in.
                    </p>
                </div>
                <Combobox onSelect={value => {
                    setUserTwoFactorAuthentication(value as TfaMethod);
                }} options={authentications}
                          currentOption={userTwoFactorAuthentication}/>
            </div>
            <div className="w-full inline-flex items-center justify-between py-1">
                <div className="pr-8">
                    <p className="text-sm">Biometric authentication</p>
                    <p className="text-xs text-muted-foreground text-gray-500">
                        Allow authentication via fingerprints or Face ID.
                    </p>
                </div>
                <GreenSwitch onChange={setUserBiometricAuthentication}
                             isChecked={userBiometricAuthentication}/>
            </div>
        </div>
    )
}
