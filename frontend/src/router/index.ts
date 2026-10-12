import { createRouter, createWebHistory } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
import LoginView from '@/views/LoginView.vue'
import MainLayout from '@/layout/MainLayout.vue'
import DashboardView from '@/views/DashboardView.vue'
import DeviceListView from '@/views/device/DeviceListView.vue'
import LabListView from '@/views/lab/LabListView.vue'
import CategoryListView from '@/views/category/CategoryListView.vue'
import UserListView from '@/views/user/UserListView.vue'
const router = createRouter({ history: createWebHistory(), routes: [{ path: '/login', component: LoginView }, { path: '/', component: MainLayout, children: [{ path: '', component: DashboardView }, { path: 'devices', component: DeviceListView }, { path: 'labs', component: LabListView }, { path: 'categories', component: CategoryListView }, { path: 'users', component: UserListView }] }] })
router.beforeEach((to) => { const logged = useAuthStore().isLoggedIn; if (to.path !== '/login' && !logged) return '/login'; if (to.path === '/login' && logged) return '/'; return true })
export default router
