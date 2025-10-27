
<script setup lang="ts">
import { ref } from "vue"
import Avatar from "@/components/ui/Avatar.vue"
import { Upload } from "lucide-vue-next" // Vue-версия lucide-react

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
      <Avatar class="transition-opacity duration-200 object-cover group-hover:opacity-60"
              :src="pictureDataUri"
              :fallback="fallback" />

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
