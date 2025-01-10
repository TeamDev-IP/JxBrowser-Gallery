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
import {useEffect, useState} from "react";
import {
    getAccount,
    getProfilePicture,
    setAccount,
    setProfilePicture
} from "@/rpc/app-preferences-service.ts";
import {AccountSchema, ProfilePictureSchema, TwoFactorAuthentication} from "@/gen/account_pb.ts";
import {create} from "@bufbuild/protobuf";

const authentications: string[] = [
    twoFactorAuthenticationString(TwoFactorAuthentication.EMAIL),
    twoFactorAuthenticationString(TwoFactorAuthentication.SMS),
    twoFactorAuthenticationString(TwoFactorAuthentication.PASS_KEY),
]

type UpdateAccountData = {
    fullName?: string;
    email?: string;
    twoFactorAuthentication?: TwoFactorAuthentication;
    biometricAuthentication?: boolean
};

function twoFactorAuthenticationString(value: TwoFactorAuthentication): string {
    if (value === TwoFactorAuthentication.EMAIL) {
        return "Email";
    } else if (value === TwoFactorAuthentication.SMS) {
        return "SMS";
    } else if (value === TwoFactorAuthentication.PASS_KEY) {
        return "Passkey";
    } else {
        throw new TypeError("Incorrect two-factor authentication.");
    }
}

export function UserAccount() {
    const [userProfilePicture, setUserProfilePicture] = useState<string>("");
    const [userFullName, setUserFullName] = useState<string>("");
    const [userEmail, setUserEmail] = useState<string>("");
    const [userTwoFactorAuthentication, setUserTwoFactorAuthentication] =
        useState<TwoFactorAuthentication>(TwoFactorAuthentication.EMAIL);
    const [userBiometricAuthentication, setUserBiometricAuthentication] = useState<boolean>(false);

    useEffect(() => {
        getAccount(account => {
            setUserEmail(account.email);
            setUserFullName(account.fullName);
            setUserTwoFactorAuthentication(account.twoFactorAuthentication);
            setUserBiometricAuthentication(account.biometricAuthentication);
        });
        getProfilePicture(contentBytes => {
            const base64String = btoa(
                String.fromCharCode(...contentBytes)
            );
            const pictureDataUri = `data:image/png;base64,${base64String}`;
            setUserProfilePicture(pictureDataUri);
        })
    }, []);

    const updateAccountData = ({
                                   fullName = userFullName,
                                   email = userEmail,
                                   twoFactorAuthentication = userTwoFactorAuthentication,
                                   biometricAuthentication = userBiometricAuthentication
                               }: UpdateAccountData) => {
        const newAccount = create(AccountSchema, {
            fullName,
            email,
            twoFactorAuthentication,
            biometricAuthentication
        });
        setAccount(newAccount);
    };

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
                        const base64String = btoa(
                            String.fromCharCode(...newProfilePicture.content)
                        );
                        const pictureDataUri = `data:image/png;base64,${base64String}`;
                        setUserProfilePicture(pictureDataUri);
                    })
                };
                reader.readAsArrayBuffer(file);
            }}
                            pictureSrc={userProfilePicture}
                            fallback={userFullName.split(" ").map(it => it[0]).join("")}/>
            <EditableLabel title={"Email"} type={EditableLabelType.EMAIL}
                           onChange={(value) => {
                               setUserEmail(value);
                               updateAccountData({email: value});
                           }}
                           defaultValue={userEmail} id={"email"}/>
            <EditableLabel title={"Full name"} type={EditableLabelType.TEXT}
                           defaultValue={userFullName}
                           onChange={(value) => {
                               setUserFullName(value);
                               updateAccountData({fullName: value});
                           }
                           } id={"fullname"}/>
            <GuidingLine/>
            <div className="w-full inline-flex items-center space-y-2 justify-between py-1">
                <div className="pr-8">
                    <p className="text-sm">Two-factor authentication</p>
                    <p className="text-xs text-muted-foreground text-gray-500">
                        Select an extra layer of security by requiring a code when logging in.
                    </p>
                </div>
                <Combobox onSelect={value => {
                    if (twoFactorAuthenticationString(TwoFactorAuthentication.EMAIL) === value) {
                        updateAccountData({twoFactorAuthentication: TwoFactorAuthentication.EMAIL});
                        setUserTwoFactorAuthentication(TwoFactorAuthentication.EMAIL);
                    } else if (twoFactorAuthenticationString(TwoFactorAuthentication.SMS) === value) {
                        updateAccountData({twoFactorAuthentication: TwoFactorAuthentication.SMS});
                        setUserTwoFactorAuthentication(TwoFactorAuthentication.SMS);
                    } else {
                        updateAccountData({twoFactorAuthentication: TwoFactorAuthentication.PASS_KEY});
                        setUserTwoFactorAuthentication(TwoFactorAuthentication.PASS_KEY);
                    }
                }} options={authentications}
                          currentOption={twoFactorAuthenticationString(userTwoFactorAuthentication)}/>
            </div>
            <div className="w-full inline-flex items-center justify-between py-1">
                <div className="pr-8">
                    <p className="text-sm">Biometric authentication</p>
                    <p className="text-xs text-muted-foreground text-gray-500">
                        Allow authentication via fingerprints or Face ID.
                    </p>
                </div>
                <GreenSwitch onChange={checked => {
                    setUserBiometricAuthentication(checked);
                    updateAccountData({biometricAuthentication: checked});
                }} isChecked={userBiometricAuthentication}/>
            </div>
        </div>
    )
}
