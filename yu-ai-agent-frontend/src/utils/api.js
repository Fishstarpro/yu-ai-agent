import axios from 'axios'

// 根据环境变量设置 API 基础 URL
const API_BASE_URL = import.meta.env.PROD 
  ? '/api' // 生产环境使用相对路径，通过nginx代理到云端后端
  : 'http://localhost:8112/api' // 开发环境指向本地后端服务

// 创建axios实例
const request = axios.create({
  baseURL: API_BASE_URL,
  timeout: 30000
})

// 请求拦截器
request.interceptors.request.use(
  config => {
    return config
  },
  error => {
    return Promise.reject(error)
  }
)

// 响应拦截器
request.interceptors.response.use(
  response => {
    return response.data
  },
  error => {
    return Promise.reject(error)
  }
)

// 创建SSE连接 - 恋爱大师应用
export const createLoveAppSSE = (message, chatId) => {
  const baseUrl = import.meta.env.PROD ? '' : 'http://localhost:8112/api'
  const url = `${baseUrl}/api/ai/love_app/chat/sse?message=${encodeURIComponent(message)}&chatId=${encodeURIComponent(chatId)}`
  return new EventSource(url)
}

// 创建SSE连接 - 智能体应用
export const createManusAppSSE = (message) => {
  const baseUrl = import.meta.env.PROD ? '' : 'http://localhost:8112/api'
  const url = `${baseUrl}/api/ai/manus/chat?message=${encodeURIComponent(message)}`
  return new EventSource(url)
}

export default request 