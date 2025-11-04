<!--
  Copyright (c) 2025 TeamDev

  Permission is hereby granted, free of charge, to any person obtaining a copy
  of this software and associated documentation files (the "Software"), to deal
  in the Software without restriction, including without limitation the rights
  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
  copies of the Software, and to permit persons to whom the Software is
  furnished to do so, subject to the following conditions:

  The above copyright notice and this permission notice shall be included in all
  copies or substantial portions of the Software.

  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
  SOFTWARE.
-->

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import PreferenceSwitch from '@/components/ui/common/PreferenceSwitch.vue'
import { create } from '@bufbuild/protobuf'
import { NotificationsSchema } from '@/gen/prefs_pb.ts'
import { prefsStorage } from '@/storage/prefs-storage.ts'
import { prefsClient } from '@shared/rpc/prefs-client'
import Separator from '@/components/ui/Separator.vue'

const desktopEnabled = ref<boolean>(prefsStorage.desktopNotificationsEnabled())
const emailEnabled = ref<boolean>(prefsStorage.emailNotificationsEnabled())

onMounted(async () => {
  try {
    const notifications = await prefsClient.getNotifications({})
    emailEnabled.value = notifications.emailEnabled
    desktopEnabled.value = notifications.desktopEnabled

    prefsStorage.saveEmailNotifications(notifications.emailEnabled)
    prefsStorage.saveDesktopNotifications(notifications.desktopEnabled)
  } catch (e) {
    console.error('Failed to load notifications:', e)
  }
})

function onUpdateNotifications({
  newDesktopEnabled = desktopEnabled.value,
  newEmailEnabled = emailEnabled.value,
}: {
  newDesktopEnabled?: boolean
  newEmailEnabled?: boolean
}) {
  const newNotifications = create(NotificationsSchema, {
    emailEnabled: newEmailEnabled,
    desktopEnabled: newDesktopEnabled,
  })
  prefsClient.setNotifications(newNotifications)
}

function toggleEmail(checked: boolean) {
  onUpdateNotifications({ newEmailEnabled: checked })
  prefsStorage.saveEmailNotifications(checked)
  emailEnabled.value = checked
}

function toggleDesktop(checked: boolean) {
  onUpdateNotifications({ newDesktopEnabled: checked })
  prefsStorage.saveDesktopNotifications(checked)
  desktopEnabled.value = checked
}
</script>

<template>
  <div class="space-y-4">
    <h1 class="text-2xl font-semibold">Notifications</h1>
    <Separator class="my-4 h-[1px] w-full" />

    <div class="w-full inline-flex items-center justify-between py-1">
      <div class="pr-8">
        <p class="text-sm">Email notifications</p>
        <p class="text-xs text-muted-foreground">
          Receive an email digest for unread notifications. Notifications will be grouped together
          and sent based on their urgency.
        </p>
      </div>
      <PreferenceSwitch :isChecked="emailEnabled" @change="toggleEmail" />
    </div>

    <div class="w-full inline-flex items-center justify-between py-1">
      <div class="pr-8">
        <p class="text-sm">Desktop notifications</p>
        <p class="text-xs text-muted-foreground">Receive personal notifications on the desktop.</p>
      </div>
      <PreferenceSwitch :isChecked="desktopEnabled" @change="toggleDesktop" />
    </div>
  </div>
</template>
