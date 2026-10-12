import axios from 'axios'
import { ElMessage } from 'element-plus'
import { getErrorMessage } from '@/utils/format'
const http = axios.create({ baseURL: import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080', timeout: 10000 })
http.interceptors.response.use((response) => response, (error) => { ElMessage.error(getErrorMessage(error)); return Promise.reject(error) })
export default http
