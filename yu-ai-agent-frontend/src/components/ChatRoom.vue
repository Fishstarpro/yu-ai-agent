<template>
  <div class="chat-room">
    <div class="chat-header">
      <h2>{{ title }}</h2>
      <div v-if="chatId" class="chat-id">会话ID: {{ chatId }}</div>
    </div>
    
    <div class="chat-messages" ref="messagesContainer">
      <template v-if="messages.length">
        <message-item 
          v-for="(msg, index) in messages" 
          :key="index"
          :content="msg.content"
          :isUser="msg.isUser"
          :timestamp="msg.timestamp"
          :loading="msg.loading"
          :isStepMessage="msg.isStepMessage"
          :aiType="aiType"
        />
      </template>
      <div v-else class="empty-message">
        <el-icon class="empty-icon animate-float"><ChatDotRound /></el-icon>
        <p class="animate-fadeInUp">发送消息开始聊天</p>
      </div>
    </div>
    
    <div class="chat-input">
      <el-input
        v-model="inputMessage"
        type="textarea"
        :rows="3"
        placeholder="请输入消息..."
        :disabled="loading"
        @keyup.enter.ctrl="sendMessage"
      />
      <el-button 
        type="primary" 
        :icon="paperPlane"
        :disabled="loading || !inputMessage.trim()" 
        @click="sendMessage">
        发送
      </el-button>
    </div>
  </div>
</template>

<script>
import { ref, onMounted, nextTick, watch } from 'vue'
import MessageItem from './MessageItem.vue'
import { v4 as uuidv4 } from 'uuid'

export default {
  name: 'ChatRoom',
  components: {
    MessageItem
  },
  props: {
    title: {
      type: String,
      default: '聊天室'
    },
    createSSEConnection: {
      type: Function,
      required: true
    },
    needChatId: {
      type: Boolean,
      default: false
    },
    aiType: {
      type: String,
      default: 'manus',
      validator: value => ['love', 'manus'].includes(value)
    }
  },
  setup(props) {
    const messages = ref([])
    const inputMessage = ref('')
    const loading = ref(false)
    const eventSource = ref(null)
    const chatId = ref(props.needChatId ? uuidv4() : '')
    const messagesContainer = ref(null)
    
    // 监听消息变化，滚动到底部
    watch(messages, () => {
      nextTick(() => {
        if (messagesContainer.value) {
          messagesContainer.value.scrollTop = messagesContainer.value.scrollHeight
        }
      })
    }, { deep: true })

    const sendMessage = async () => {
      if (!inputMessage.value.trim() || loading.value) return
      
      // 添加用户消息
      const userMessage = {
        content: inputMessage.value,
        isUser: true,
        timestamp: Date.now()
      }
      messages.value.push(userMessage)
      
      // 添加一条空的AI消息（带loading状态）
      const aiMessage = {
        content: '',
        isUser: false,
        timestamp: Date.now(),
        loading: true,
        isStepMessage: !props.needChatId // 智能体应用的消息标记为步骤消息
      }
      messages.value.push(aiMessage)
      
      loading.value = true
      
      try {
        // 关闭之前的连接
        if (eventSource.value) {
          eventSource.value.close()
        }
        
        // 创建新的SSE连接
        eventSource.value = props.needChatId 
          ? props.createSSEConnection(inputMessage.value, chatId.value)
          : props.createSSEConnection(inputMessage.value)
        
        let responseText = ''
        
        eventSource.value.onmessage = (event) => {
          if (event.data) {
            if (props.needChatId) {
              // 恋爱大师应用：累积显示内容
              responseText += event.data
              const aiMessageIndex = messages.value.length - 1
              messages.value[aiMessageIndex].content = responseText
              messages.value[aiMessageIndex].loading = false
            } else {
              // 智能体应用：每个步骤单独显示
              const aiMessageIndex = messages.value.length - 1
              
              // 如果当前消息还在loading状态，更新它
              if (messages.value[aiMessageIndex].loading) {
                messages.value[aiMessageIndex].content = event.data
                messages.value[aiMessageIndex].loading = false
              } else {
                // 如果当前消息已经完成，创建新的消息
                const newAiMessage = {
                  content: event.data,
                  isUser: false,
                  timestamp: Date.now(),
                  loading: false,
                  isStepMessage: true
                }
                messages.value.push(newAiMessage)
              }
            }
          }
        }
        
        eventSource.value.onerror = () => {
          // 关闭连接
          if (eventSource.value) {
            eventSource.value.close()
            eventSource.value = null
          }
          
          // 如果没有收到任何消息，添加错误提示
          const aiMessageIndex = messages.value.length - 1
          if (messages.value[aiMessageIndex] && messages.value[aiMessageIndex].loading) {
            messages.value[aiMessageIndex].content = '抱歉，发生了错误，请稍后再试。'
            messages.value[aiMessageIndex].loading = false
          }
          
          loading.value = false
        }
        
        eventSource.value.onopen = () => {
          console.log('SSE连接已打开')
        }
        
        // 清空输入框
        inputMessage.value = ''
      } catch (error) {
        console.error('发送消息出错:', error)
        // 更新AI消息为错误提示
        const aiMessageIndex = messages.value.length - 1
        if (messages.value[aiMessageIndex] && messages.value[aiMessageIndex].loading) {
          messages.value[aiMessageIndex].content = '抱歉，发生了错误，请稍后再试。'
          messages.value[aiMessageIndex].loading = false
        }
        loading.value = false
      }
    }
    
    onMounted(() => {
      // 在组件卸载时关闭SSE连接
      return () => {
        if (eventSource.value) {
          eventSource.value.close()
          eventSource.value = null
        }
      }
    })
    
    return {
      messages,
      inputMessage,
      loading,
      chatId,
      messagesContainer,
      sendMessage,
      paperPlane: 'Position'
    }
  }
}
</script>

<style scoped>
.chat-room {
  display: flex;
  flex-direction: column;
  height: 100%;
  background: var(--bg-card);
  border-radius: var(--radius-large);
  box-shadow: var(--shadow-base);
  border: 1px solid var(--border-light);
  overflow: hidden;
}

.chat-header {
  padding: var(--spacing-lg);
  border-bottom: 1px solid var(--border-light);
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: var(--bg-primary);
  position: relative;
}

.chat-header::after {
  content: '';
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  height: 1px;
  background: linear-gradient(90deg, transparent, var(--border-light), transparent);
}

.chat-header h2 {
  font-size: var(--font-size-lg);
  font-weight: 600;
  color: var(--text-primary);
  margin: 0;
}

.chat-id {
  font-size: var(--font-size-xs);
  color: var(--text-tertiary);
  background: var(--bg-tertiary);
  padding: var(--spacing-xs) var(--spacing-sm);
  border-radius: var(--radius-small);
  border: 1px solid var(--border-light);
}

.chat-messages {
  flex: 1;
  overflow-y: auto;
  padding: var(--spacing-lg);
  background: var(--bg-secondary);
  position: relative;
}

.chat-messages::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: 
    radial-gradient(circle at 20% 20%, rgba(64, 158, 255, 0.02) 0%, transparent 50%),
    radial-gradient(circle at 80% 80%, rgba(64, 158, 255, 0.02) 0%, transparent 50%);
  pointer-events: none;
}

.empty-message {
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  color: var(--text-tertiary);
  position: relative;
  z-index: 1;
}

.empty-icon {
  font-size: 56px;
  margin-bottom: var(--spacing-lg);
  background: linear-gradient(135deg, var(--primary-color), var(--primary-light));
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  filter: drop-shadow(0 0 20px rgba(64, 158, 255, 0.5));
}

.empty-message p {
  font-size: var(--font-size-base);
  margin: 0;
  color: var(--text-secondary);
  opacity: 0;
  animation-fill-mode: forwards;
}

.chat-input {
  padding: var(--spacing-lg);
  border-top: 1px solid var(--border-light);
  display: flex;
  align-items: flex-end;
  gap: var(--spacing-md);
  background: var(--bg-primary);
  position: relative;
}

.chat-input::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 1px;
  background: linear-gradient(90deg, transparent, var(--border-light), transparent);
}

.chat-input :deep(.el-textarea) {
  flex: 1;
}

.chat-input :deep(.el-textarea__inner) {
  border-radius: var(--radius-base);
  border: 1px solid var(--border-base);
  transition: all var(--transition-fast);
  resize: none;
  font-family: inherit;
}

.chat-input :deep(.el-textarea__inner):focus {
  border-color: var(--primary-color);
  box-shadow: 0 0 0 2px rgba(64, 158, 255, 0.1);
}

.chat-input .el-button {
  height: 60px;
  padding: 0 var(--spacing-lg);
  border-radius: var(--radius-base);
  font-weight: 500;
  transition: all 0.3s cubic-bezier(0.175, 0.885, 0.32, 1.275);
  position: relative;
  overflow: hidden;
}

.chat-input .el-button::before {
  content: '';
  position: absolute;
  top: 50%;
  left: 50%;
  width: 0;
  height: 0;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.3);
  transform: translate(-50%, -50%);
  transition: width 0.6s, height 0.6s;
}

.chat-input .el-button:hover::before {
  width: 300px;
  height: 300px;
}

.chat-input .el-button:hover {
  transform: translateY(-2px) scale(1.05);
  box-shadow: 0 8px 25px rgba(64, 158, 255, 0.3);
}

.chat-input .el-button:active {
  transform: translateY(0) scale(0.98);
}

/* 响应式设计 */
@media (max-width: 768px) {
  .chat-header {
    padding: var(--spacing-md);
  }
  
  .chat-header h2 {
    font-size: var(--font-size-base);
  }
  
  .chat-messages {
    padding: var(--spacing-md);
  }
  
  .chat-input {
    padding: var(--spacing-md);
    flex-direction: column;
    align-items: stretch;
    gap: var(--spacing-sm);
  }
  
  .chat-input .el-button {
    height: 44px;
  }
  
  .empty-message .el-icon {
    font-size: 36px;
  }
}

@media (max-width: 480px) {
  .chat-header {
    flex-direction: column;
    gap: var(--spacing-sm);
    text-align: center;
  }
  
  .chat-id {
    order: -1;
  }
}
</style> 