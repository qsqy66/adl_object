import http from './http'

export interface ChatMessage {
  role: 'user' | 'assistant'
  content: string
}

export interface ChatResponse {
  reply: string
  changed: boolean
}

export const chat = (messages: ChatMessage[]) =>
  http.post<ChatResponse>('/ai/chat', { messages }).then(response => response.data)
