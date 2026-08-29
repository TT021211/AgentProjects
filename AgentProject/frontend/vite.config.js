import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig({
  plugins: [vue()],
  server: {
    // 监听所有本机地址，避免 Windows 上 localhost 只解析到 IPv6 回环地址。
    host: '0.0.0.0',
    port: 5173,
    proxy: {
      '/api': 'http://localhost:8082'
    }
  }
})
