import http from './http'
import type { Device, DeviceForm } from '@/types/device'
export const getDevices = () => http.get<Device[]>('/device').then(r => r.data)
export const getDeviceById = (id: number) => http.get<Device>(`/device/${id}`).then(r => r.data)
export const createDevice = (data: DeviceForm) => http.post<Device>('/device', data).then(r => r.data)
export const updateDevice = (id: number, data: DeviceForm) => http.put<boolean>(`/device/${id}`, data).then(r => r.data)
export const deleteDevice = (id: number) => http.delete<boolean>(`/device/${id}`).then(r => r.data)
