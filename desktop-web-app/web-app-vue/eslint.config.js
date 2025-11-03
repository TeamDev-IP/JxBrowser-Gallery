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
        extraFileExtensions: ['.vue'],
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