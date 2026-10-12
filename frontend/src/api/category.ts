import http from './http'
import type { DeviceCategory } from '@/types/category'
export const getCategories = () => http.get<DeviceCategory[]>('/category').then(r => r.data)
export const getCategoryById = (id: number) => http.get<DeviceCategory>(`/category/${id}`).then(r => r.data)
export const createCategory = (data: DeviceCategory) => http.post<DeviceCategory>('/category', data).then(r => r.data)
export const updateCategory = (id: number, data: DeviceCategory) => http.put<boolean>(`/category/${id}`, data).then(r => r.data)
export const deleteCategory = (id: number) => http.delete<boolean>(`/category/${id}`).then(r => r.data)
