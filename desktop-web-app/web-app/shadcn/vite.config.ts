import path from "path"
import react from "@vitejs/plugin-react"
import {defineConfig} from "vite"
import {nodePolyfills} from "vite-plugin-node-polyfills";

export default defineConfig({
    plugins: [
        react(),
        nodePolyfills({
            include: ["buffer", "util", "process", "zlib", "https", "http", "http2", "stream"]
        })
    ],
    resolve: {
        alias: {
            "@": path.resolve(__dirname, "./src"),
        },
    },
    define: {
    }
})
