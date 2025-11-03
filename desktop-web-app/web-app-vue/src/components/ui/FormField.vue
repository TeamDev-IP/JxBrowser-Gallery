<template>
  <div class="space-y-2">
    <Label v-if="label" :for="id" :required="required" :error="!!error">
      {{ label }}
    </Label>
    <slot :id="id" :error="!!error" />
    <p v-if="description" class="text-sm text-muted-foreground">
      {{ description }}
    </p>
    <p v-if="error" class="text-sm font-medium text-destructive">
      {{ error }}
    </p>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import Label from './Label.vue'

interface Props {
  label?: string
  description?: string
  error?: string
  required?: boolean
  name?: string
}

const props = defineProps<Props>()

const id = computed(() => (props.name ? `field-${props.name}` : undefined))
</script>
