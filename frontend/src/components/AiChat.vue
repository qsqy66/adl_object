<script setup lang="ts">
import { nextTick, ref } from 'vue'
import { ElMessage } from 'element-plus'
import * as aiApi from '@/api/ai'

const visible = ref(false)
const input = ref('')
const loading = ref(false)
const messages = ref<aiApi.ChatMessage[]>([
  { role: 'assistant', content: '你好，我可以帮你查询、添加或修改实验室设备数据。比如：添加一个位于教学楼 101 的实验室。' },
])
const listRef = ref<HTMLElement>()

async function scrollToBottom() {
  await nextTick()
  if (listRef.value) listRef.value.scrollTop = listRef.value.scrollHeight
}

async function send() {
  const content = input.value.trim()
  if (!content || loading.value) return
  messages.value.push({ role: 'user', content })
  input.value = ''
  loading.value = true
  await scrollToBottom()
  try {
    const result = await aiApi.chat(messages.value)
    messages.value.push({ role: 'assistant', content: result.reply })
  } catch {
    ElMessage.error('AI 请求失败，请检查 DeepSeek 配置或网络连接')
  } finally {
    loading.value = false
    await scrollToBottom()
  }
}

function clearMessages() {
  messages.value = [{ role: 'assistant', content: '对话已清空。你可以直接告诉我需要查询、添加或修改什么数据。' }]
}
</script>

<template>
  <div class="ai-chat">
    <el-button class="ai-chat-trigger" type="primary" circle @click="visible = !visible">AI</el-button>
    <el-card v-if="visible" class="ai-chat-panel" :body-style="{ padding: '0' }">
      <template #header>
        <div class="ai-chat-header">
          <span>AI 数据助手</span>
          <el-button link @click="clearMessages">清空</el-button>
        </div>
      </template>
      <div ref="listRef" class="ai-chat-list">
        <div v-for="(message, index) in messages" :key="index" class="ai-chat-message" :class="message.role">
          {{ message.content }}
        </div>
        <div v-if="loading" class="ai-chat-message assistant">正在处理...</div>
      </div>
      <div class="ai-chat-input">
        <el-input v-model="input" type="textarea" :rows="2" resize="none" placeholder="例如：把示波器状态改为维修中" @keydown.ctrl.enter="send" />
        <el-button type="primary" :loading="loading" @click="send">发送</el-button>
      </div>
    </el-card>
  </div>
</template>
