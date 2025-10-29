import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import { viteSingleFile } from 'vite-plugin-singlefile'
import path from 'path'

export default defineConfig(({ mode }) => ({
  plugins: [
    vue(),
    mode === 'production' ? viteSingleFile() : undefined
  ].filter(Boolean),
  resolve: {
    alias: {
      '@': path.resolve(__dirname, './src'),
      '@shared': path.resolve(__dirname, '../shared'),
    }
  },
  optimizeDeps: {
    include: ["@connectrpc/connect-web", "@connectrpc/connect"]
  },
  build: {
    outDir: 'dist',
    assetsDir: 'assets',
    emptyOutDir: true,
  rollupOptions: {
      external: ["@connectrpc/connect-web", "@connectrpc/connect"],
    }
  },
  server: {
    port: 5175,
    strictPort: true
  }
}))