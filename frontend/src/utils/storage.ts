import type { User } from '@/types/user'
const KEY = 'lab-auth-user'
export const getStoredUser = (): User | null => { const value = localStorage.getItem(KEY); return value ? JSON.parse(value) as User : null }
export const setStoredUser = (user: User) => localStorage.setItem(KEY, JSON.stringify(user))
export const clearStoredUser = () => localStorage.removeItem(KEY)
