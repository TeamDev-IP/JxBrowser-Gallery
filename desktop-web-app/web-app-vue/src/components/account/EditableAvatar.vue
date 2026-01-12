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

<script setup lang="ts">
import { ref } from 'vue'
import Avatar from '@/components/ui/Avatar.vue'
import { Upload } from 'lucide-vue-next'

interface Props {
  pictureDataUri: string
  fallback: string
  onChange: (file: File) => void
}

const props = defineProps<Props>()
const fileInputRef = ref<HTMLInputElement | null>(null)

const triggerFileInput = () => {
  fileInputRef.value?.click()
}

const handleFileChange = (event: Event) => {
  const target = event.target as HTMLInputElement
  const file = target.files?.[0]
  if (file) props.onChange(file)
}
</script>

<template>
  <div class="w-full flex items-center justify-between">
    <p class="text-sm">Profile picture</p>

    <div
      class="relative flex items-center justify-center group cursor-pointer"
      @click="triggerFileInput"
    >
      <Avatar
        class="transition-opacity duration-200 object-cover group-hover:opacity-60"
        :src="pictureDataUri"
        :fallback="fallback"
      />

      <input
        ref="fileInputRef"
        type="file"
        accept="image/*"
        class="hidden"
        @change="handleFileChange"
      />

      <div
        class="absolute inset-0 flex items-center justify-center text-primary text-sm font-medium opacity-0 transition-opacity group-hover:opacity-100"
      >
        <Upload class="w-4 h-4" />
      </div>
    </div>
  </div>
</template>
