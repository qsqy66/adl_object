<script setup lang="ts">
import { useRoute, useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
const route = useRoute(); const router = useRouter(); const auth = useAuthStore()
const menus = [{ path: '/', label: '首页' }, { path: '/devices', label: '设备管理' }, { path: '/labs', label: '实验室管理' }, { path: '/categories', label: '设备分类' }, { path: '/users', label: '用户管理' }]
function logout() { auth.logout(); router.push('/login') }
</script>
<template><el-container class="app-shell"><el-aside width="220px" class="sidebar"><div class="brand">实验室设备管理</div><el-menu :default-active="route.path" router background-color="#132238" text-color="#d6e2f0" active-text-color="#fff"><el-menu-item v-for="item in menus" :key="item.path" :index="item.path">{{ item.label }}</el-menu-item></el-menu></el-aside><el-container><el-header class="main-header"><el-breadcrumb><el-breadcrumb-item>实验室设备管理系统</el-breadcrumb-item><el-breadcrumb-item>{{ menus.find(item => item.path === route.path)?.label }}</el-breadcrumb-item></el-breadcrumb><el-dropdown><span>{{ auth.user?.userName || '用户' }} ▾</span><template #dropdown><el-dropdown-menu><el-dropdown-item @click="logout">退出登录</el-dropdown-item></el-dropdown-menu></template></el-dropdown></el-header><el-main><router-view /></el-main></el-container></el-container></template>
