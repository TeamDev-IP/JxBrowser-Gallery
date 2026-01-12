<!--
  Copyright 2026, TeamDev

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

<template>
  <div class="space-y-4">
    <h1 class="text-2xl font-semibold">General</h1>
    <Separator class="my-4 h-[1px] w-full" />
    <div class="w-full inline-flex items-center justify-between py-1">
      <div class="pr-8">
        <p class="text-sm">Launch at startup</p>
        <p class="text-xs text-muted-foreground">
          Enable this option to automatically start the application when your device boots up.
        </p>
      </div>
      <PreferenceSwitch :isChecked="launchAtStartup" :onChange="onLaunchAtStartupChange" />
    </div>

    <div class="w-full inline-flex items-center space-y-2 justify-between py-1">
      <div class="pr-8">
        <p class="text-sm">Language</p>
        <p class="text-xs text-muted-foreground">
          Choose the language for the application’s interface.
        </p>
      </div>
      <Combobox
        v-model:currentOption="language"
        :options="languages"
        @update:currentOption="onSelectLanguage"
      />
    </div>

    <div class="w-full inline-flex items-center justify-between py-1">
      <div class="pr-8">
        <p class="text-sm">Check for updates automatically</p>
        <p class="text-xs text-muted-foreground">Allow to check for updates in the background.</p>
      </div>
      <PreferenceSwitch :isChecked="checkForUpdates" :onChange="onCheckForUpdatesChange" />
    </div>
  </div>
</template>

<script setup lang="ts">
import {onMounted, ref} from 'vue'
import Separator from '@/components/ui/Separator.vue'
import Combobox from '@/components/ui/common/Combobox.vue'
import PreferenceSwitch from '@/components/ui/common/PreferenceSwitch.vue'
import {GeneralSchema} from '@/gen/prefs_pb'
import {create} from '@bufbuild/protobuf'
import {
  englishLanguage,
  frenchLanguage,
  fromLanguage,
  germanLanguage,
  LanguageOption,
  toLanguage,
} from '@/converter/language'
import {prefsStorage} from '@/storage/prefs-storage'
import {prefsClient} from '@shared/rpc/prefs-client'

const languages: LanguageOption[] = [englishLanguage, germanLanguage, frenchLanguage]
const launchAtStartup = ref(prefsStorage.launchAtStartupEnabled())
const language = ref<LanguageOption>(prefsStorage.language())
const checkForUpdates = ref(prefsStorage.checkForUpdatesEnabled())

onMounted(async () => {
  const general = await prefsClient.getGeneral({})
  launchAtStartup.value = general.launchAtStartup
  language.value = fromLanguage(general.language)
  checkForUpdates.value = general.checkForUpdates

  prefsStorage.saveLaunchAtStartup(general.launchAtStartup)
  prefsStorage.saveLanguage(language.value)
  prefsStorage.saveCheckForUpdates(general.checkForUpdates)
})

function onUpdateGeneral({
  newLanguage = language.value,
  newLaunchAtStartup = launchAtStartup.value,
  newCheckForUpdates = checkForUpdates.value,
}: {
  newLanguage?: LanguageOption
  newLaunchAtStartup?: boolean
  newCheckForUpdates?: boolean
}) {
  const newGeneral = create(GeneralSchema, {
    launchAtStartup: newLaunchAtStartup,
    language: toLanguage(newLanguage),
    checkForUpdates: newCheckForUpdates,
  })
  prefsClient.setGeneral(newGeneral)
}

function onLaunchAtStartupChange(checked: boolean) {
  launchAtStartup.value = checked
  prefsStorage.saveLaunchAtStartup(checked)
  onUpdateGeneral({ newLaunchAtStartup: checked })
}

function onCheckForUpdatesChange(checked: boolean) {
  checkForUpdates.value = checked
  prefsStorage.saveCheckForUpdates(checked)
  onUpdateGeneral({ newCheckForUpdates: checked })
}

function onSelectLanguage(value: string) {
  const newLanguage = value as LanguageOption
  onUpdateGeneral({ newLanguage })
  prefsStorage.saveLanguage(newLanguage)
  language.value = newLanguage
}
</script>
