<script setup lang="ts">
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, type FormInstance, type FormRules } from 'element-plus'
import { useAuthStore } from '@/stores/auth'
const router = useRouter(); const auth = useAuthStore(); const formRef = ref<FormInstance>(); const loading = ref(false)
const form = reactive({ username: '', password: '' }); const rules: FormRules = { username: [{ required: true, message: '请输入用户名或账号', trigger: 'blur' }], password: [{ required: true, message: '请输入密码', trigger: 'blur' }] }
async function submit() { await formRef.value?.validate(); loading.value = true; try { await auth.login(form); router.push('/') } catch { ElMessage.error('用户名或密码错误') } finally { loading.value = false } }
</script>
<template><div class="auth-page"><el-card class="auth-card"><h1 class="auth-title">实验室设备管理系统</h1><el-form ref="formRef" :model="form" :rules="rules" @submit.prevent="submit"><el-form-item prop="username"><el-input v-model="form.username" placeholder="用户名或账号" /></el-form-item><el-form-item prop="password"><el-input v-model="form.password" type="password" show-password placeholder="密码" @keyup.enter="submit" /></el-form-item><el-button type="primary" native-type="submit" :loading="loading" style="width:100%">登录</el-button></el-form></el-card></div></template>
