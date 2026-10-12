import http from './http'
import type { LoginRequest, User, UserForm } from '@/types/user'
export const login = (data: LoginRequest) => http.post<User>('/user/login', data).then(r => r.data)
export const getUsers = () => http.get<User[]>('/user').then(r => r.data)
export const getUserById = (id: number) => http.get<User>(`/user/${id}`).then(r => r.data)
export const createUser = (data: UserForm) => http.post<User>('/user', data).then(r => r.data)
export const updateUser = (id: number, data: UserForm) => http.put<boolean>(`/user/${id}`, data).then(r => r.data)
export const deleteUser = (id: number) => http.delete<boolean>(`/user/${id}`).then(r => r.data)
