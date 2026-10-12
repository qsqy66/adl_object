import http from './http'
import type { Lab } from '@/types/lab'
export const getLabs = () => http.get<Lab[]>('/lab').then(r => r.data)
export const getLabById = (id: number) => http.get<Lab>(`/lab/${id}`).then(r => r.data)
export const createLab = (data: Lab) => http.post<Lab>('/lab', data).then(r => r.data)
export const updateLab = (id: number, data: Lab) => http.put<boolean>(`/lab/${id}`, data).then(r => r.data)
export const deleteLab = (id: number) => http.delete<boolean>(`/lab/${id}`).then(r => r.data)
