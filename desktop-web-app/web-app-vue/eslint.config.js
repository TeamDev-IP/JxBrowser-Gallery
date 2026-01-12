/*
 * Copyright 2026, TeamDev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
import { defineConfig } from 'eslint-define-config'
import vue from 'eslint-plugin-vue'
import tsPlugin from '@typescript-eslint/eslint-plugin'
import vueParser from 'vue-eslint-parser'
import prettier from 'eslint-plugin-prettier'

export default defineConfig([
  {
    ignores: ['dist', 'src/gen', 'tailwind.config.cjs'],
    languageOptions: {
      parser: vueParser,
      parserOptions: {
        parser: '@typescript-eslint/parser',
        ecmaVersion: 'latest',
        sourceType: 'module',
        extraFileExtensions: ['.vue', '.yaml'],
      },
    },
    plugins: {
      vue,
      '@typescript-eslint': tsPlugin,
      prettier,
    },
    rules: {
      ...tsPlugin.configs.recommended.rules,
      ...vue.configs.recommended.rules,

      '@typescript-eslint/no-empty-object-type': 'off',
      '@typescript-eslint/no-explicit-any': 'off',

      'vue/multi-word-component-names': 'off',
      'vue/no-parsing-error': 'off',

      'eol-last': ['error', 'always'],

      'prettier/prettier': [
        'error',
        {
          singleQuote: true,
          semi: false,
          bracketSpacing: true,
          trailingComma: 'es5',
          printWidth: 100,
        },
      ],
    },
    files: ['**/*.{ts,vue}'],
  },
])
