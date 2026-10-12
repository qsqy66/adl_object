import { computed, ref } from 'vue'
import { defineStore } from 'pinia'
import type { LoginRequest, User } from '@/types/user'
import * as userApi from '@/api/user'
import { clearStoredUser, getStoredUser, setStoredUser } from '@/utils/storage'
export const useAuthStore = defineStore('auth', () => { const user = ref<User | null>(getStoredUser()); const isLoggedIn = computed(() => user.value !== null); async function login(data: LoginRequest) { user.value = await userApi.login(data); setStoredUser(user.value) }; function logout() { user.value = null; clearStoredUser() }; return { user, isLoggedIn, login, logout } })
