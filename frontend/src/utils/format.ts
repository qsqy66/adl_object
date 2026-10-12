import type { AxiosError } from 'axios'
export const formatDate = (value?: string | null) => value || '-'
export const formatCurrency = (value?: number | null) => value == null ? '-' : `¥${value.toLocaleString('zh-CN', { minimumFractionDigits: 2 })}`
export const formatNumber = (value?: number | null) => value == null ? '-' : value.toLocaleString('zh-CN')
export const getStatusTagType = (status: string) => ({ 正常: 'success', 维修中: 'warning', 报废: 'danger', 闲置: 'info' }[status] || 'info') as 'success' | 'warning' | 'danger' | 'info'
export const getErrorMessage = (error: unknown) => { const response = (error as AxiosError<{ message?: string }>).response; if (response?.status === 401) return '用户名或密码错误'; if (response?.status === 404) return '请求的资源不存在'; if (response?.status === 500) return '服务器发生错误'; return response?.data?.message || (error instanceof Error ? error.message : '请求失败，请稍后重试') }
