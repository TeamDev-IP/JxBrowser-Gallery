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
  <Popover v-model:open="open">
    <PopoverTrigger>
      <Button
        variant="outline"
        role="combobox"
        size="sm"
        class="w-[130px] min-w-[120px] justify-between"
        :aria-expanded="String(open)"
        @click="toggleOpen"
      >
        {{ localCurrentOption }}
        <ChevronsUpDown class="h-4 opacity-50" />
      </Button>
    </PopoverTrigger>

    <PopoverContent class="w-[120px] p-0">
      <Command>
        <CommandList>
          <CommandGroup>
            <CommandItem
              v-for="option in options"
              :key="option"
              class="h-[30px]"
              @click="selectOption(option)"
            >
              {{ option }}
              <Check
                class="ml-auto"
                :class="localCurrentOption === option ? 'opacity-100' : 'opacity-0'"
              />
            </CommandItem>
          </CommandGroup>
        </CommandList>
      </Command>
    </PopoverContent>
  </Popover>
</template>

<script setup lang="ts">
import {ref, watch} from 'vue'
import Popover from '@/components/ui/Popover.vue'
import PopoverTrigger from '@/components/ui/PopoverTrigger.vue'
import PopoverContent from '@/components/ui/PopoverContent.vue'
import Button from '@/components/ui/Button.vue'
import {Check, ChevronsUpDown} from 'lucide-vue-next'
import Command from '@/components/ui/Command.vue'
import CommandGroup from '@/components/ui/CommandGroup.vue'
import CommandList from '@/components/ui/CommandList.vue'
import CommandItem from '@/components/ui/CommandItem.vue'

interface Props {
  options: string[]
  currentOption: string
}

const props = defineProps<Props>()
const emit = defineEmits<{
  (e: 'update:currentOption', value: string): void
  (e: 'update:open', value: boolean): void
}>()

const localCurrentOption = ref(props.currentOption)
watch(
  () => props.currentOption,
  (val) => {
    localCurrentOption.value = val
  }
)

const open = ref(false)
watch(open, (val) => emit('update:open', val))

function toggleOpen() {
  emit('update:open', !open.value)
}

function selectOption(value: string) {
  localCurrentOption.value = value
  emit('update:currentOption', value)
  emit('update:open', false)
}
</script>
