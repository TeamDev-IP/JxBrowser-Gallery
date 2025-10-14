<script setup lang="ts">
import {ref, computed, onMounted} from "vue";
import Combobox from "@/components/ui/common/Combobox.vue";
import PreferenceSwitch from "@/components/ui/common/PreferenceSwitch.vue";
import EditableAvatar from "@/components/account/EditableAvatar.vue";
import EditableInput from "@/components/account/EditableInput.vue";
import GuidingLine from "@/components/ui/common/GuidingLine.vue";

import {AccountSchema, ProfilePictureSchema} from "@/gen/prefs_pb.ts";
import {create} from "@bufbuild/protobuf";
import {imageToDataUri} from "@/converter/image.ts";
import {
  emailTwoFA,
  smsTwoFA,
  passkeyTwoFA,
  fromTfa,
  toTfa,
  TwoFAMethod,
} from "@/converter/two-fa-method.ts";
import {prefsStorage} from "@/storage/prefs-storage.ts";
import {prefsClient} from "../../../rpc/prefs-client.ts";

const profilePictureDataUri = ref<string>("");
const fullName = ref<string>("");
const email = ref<string>("");
const twoFactorAuthentication = ref<TwoFAMethod>(prefsStorage.twoFA());
const biometricAuthentication = ref<boolean>(
    prefsStorage.biometricAuthenticationEnabled()
);

const initials = computed(() =>
    fullName.value.split(" ").map((i) => i[0]).join("")
);

const authentications: TwoFAMethod[] = [emailTwoFA, smsTwoFA, passkeyTwoFA];

onMounted(async () => {
  try {
    const account = await prefsClient.getAccount({});
    email.value = account.email;
    fullName.value = account.fullName;

    const tfaMethod = fromTfa(account.twoFactorAuthentication);
    twoFactorAuthentication.value = tfaMethod;
    biometricAuthentication.value = account.biometricAuthentication;

    prefsStorage.saveTwoFA(tfaMethod);
    prefsStorage.saveBiometricAuthentication(account.biometricAuthentication);

    await loadProfilePicture();
  } catch (e) {
    console.error("getAccount failed:", e);
  }
});

async function loadProfilePicture() {
  try {
    const profilePicture = await prefsClient.getProfilePicture({});

    if (!profilePicture?.content || profilePicture.content.length === 0) {
      profilePictureDataUri.value = initials.value;
      return;
    }

    profilePictureDataUri.value = imageToDataUri(profilePicture.content);
  } catch (err) {
    profilePictureDataUri.value = initials.value;
    console.error(err)
  }
}

function onUpdateAccount({
                           newFullName = fullName.value,
                           newEmail = email.value,
                           newTwoFactorAuthentication = twoFactorAuthentication.value,
                           newBiometricAuthentication = biometricAuthentication.value
                         }: {
  newFullName?: string
  newEmail?: string
  newTwoFactorAuthentication?: TwoFAMethod
  newBiometricAuthentication?: boolean
}) {
  const newAccount = create(AccountSchema, {
    fullName: newFullName,
    email: newEmail,
    twoFactorAuthentication: toTfa(newTwoFactorAuthentication),
    biometricAuthentication: newBiometricAuthentication
  })

  prefsClient.setAccount(newAccount)
  prefsStorage.saveTwoFA(newTwoFactorAuthentication)
  prefsStorage.saveBiometricAuthentication(newBiometricAuthentication)
}

function updateAccount({
                         newFullName = fullName.value,
                         newEmail = email.value,
                         newTwoFactorAuthentication = twoFactorAuthentication.value,
                         newBiometricAuthentication = biometricAuthentication.value,
                       }: {
  newFullName?: string;
  newEmail?: string;
  newTwoFactorAuthentication?: TwoFAMethod;
  newBiometricAuthentication?: boolean;
}) {
  const newAccount = create(AccountSchema, {
    fullName: newFullName,
    email: newEmail,
    twoFactorAuthentication: toTfa(newTwoFactorAuthentication),
    biometricAuthentication: newBiometricAuthentication,
  });

  prefsClient.setAccount(newAccount);
  prefsStorage.saveTwoFA(newTwoFactorAuthentication);
  prefsStorage.saveBiometricAuthentication(newBiometricAuthentication);
}

function updateBiometricAuthentication(checked: boolean) {
  biometricAuthentication.value = checked;
  updateAccount({newBiometricAuthentication: checked});
}

function onChangeAvatar(file: File) {
  const reader = new FileReader();
  reader.onload = () => {
    const newProfilePicture = create(ProfilePictureSchema, {
      content: new Uint8Array(reader.result as ArrayBuffer),
    });
    prefsClient.setProfilePicture(newProfilePicture);
    profilePictureDataUri.value = imageToDataUri(newProfilePicture.content);
  };
  reader.readAsArrayBuffer(file);
}
</script>

<template>
  <div class="space-y-4">
    <h1 class="text-2xl font-semibold">Account</h1>
    <GuidingLine/>

    <EditableAvatar
        :pictureDataUri="profilePictureDataUri"
        :fallback="initials"
        :onChange="onChangeAvatar"
    />

    <EditableInput
        v-model:modelValue="email"
        title="Email"
        :isEmail="true"
        @update:modelValue="(value: string) => {
        onUpdateAccount({ newEmail: value });
        email = value;
    }"
    />

    <EditableInput
        v-model:modelValue="fullName"
        title="Full name"
        :isEmail="false"
        @update:modelValue="(value: string) => {
        onUpdateAccount({ newFullName: value });
        fullName = value;
    }"
    />

    <GuidingLine/>

    <div class="w-full inline-flex items-center space-y-2 justify-between py-1">
      <div class="pr-8">
        <p class="text-sm">Two-factor authentication</p>
        <p class="text-xs text-muted-foreground text-gray-500">
          Select an extra layer of security by requiring a code when logging in.
        </p>
      </div>
      <Combobox
          v-model:currentOption="twoFactorAuthentication"
          :options="authentications"
      />
    </div>

    <!-- Biometric authentication -->
    <div class="w-full inline-flex items-center justify-between py-1">
      <div class="pr-8">
        <p class="text-sm">Biometric authentication</p>
        <p class="text-xs text-muted-foreground text-gray-500">
          Allow authentication via fingerprints or Face ID.
        </p>
      </div>
      <PreferenceSwitch
          :isChecked="biometricAuthentication"
          :onChange="updateBiometricAuthentication"
      />
    </div>
  </div>
</template>

