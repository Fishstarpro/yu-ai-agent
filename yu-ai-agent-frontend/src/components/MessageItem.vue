<template>
  <div class="message-item" :class="{ 'message-right': isUser, 'step-message': isStepMessage }">
    <div class="avatar-container">
      <div class="avatar" :class="{ 'user-avatar': isUser, 'ai-avatar': !isUser, 'love-ai': aiType === 'love', 'manus-ai': aiType === 'manus' }">
        <el-avatar :size="40" v-if="isUser">
          <el-icon><User /></el-icon>
        </el-avatar>
        <el-avatar :size="40" v-else-if="aiType === 'love'" class="love-avatar">
          <el-icon><Heart /></el-icon>
        </el-avatar>
        <el-avatar :size="40" v-else class="manus-avatar">
          <el-icon><Monitor /></el-icon>
        </el-avatar>
      </div>
      <div class="avatar-label">{{ getAvatarLabel() }}</div>
    </div>
    <div class="message-content">
      <div class="message-bubble" :class="{ 
        'step-bubble': isStepMessage,
        'user-bubble': isUser,
        'love-bubble': !isUser && aiType === 'love',
        'manus-bubble': !isUser && aiType === 'manus'
      }">
        <div v-if="!loading" class="message-text">
          <div v-if="isStepMessage" class="step-indicator">
            <el-icon><Lightning /></el-icon> 
            <span>AI思考步骤</span>
          </div>
          <p>{{ content }}</p>
        </div>
        <div v-else class="loading-container">
          <div class="typing-indicator">
            <span></span>
            <span></span>
            <span></span>
          </div>
          <span class="loading-text">{{ getLoadingText() }}</span>
        </div>
      </div>
      <div class="message-time">{{ formatTime(timestamp) }}</div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'MessageItem',
  props: {
    content: {
      type: String,
      default: ''
    },
    isUser: {
      type: Boolean,
      default: false
    },
    timestamp: {
      type: Number,
      default: () => Date.now()
    },
    loading: {
      type: Boolean,
      default: false
    },
    isStepMessage: {
      type: Boolean,
      default: false
    },
    aiType: {
      type: String,
      default: 'manus', // 'love' 或 'manus'
      validator: value => ['love', 'manus'].includes(value)
    }
  },
  methods: {
    formatTime(timestamp) {
      const date = new Date(timestamp)
      return `${date.getHours().toString().padStart(2, '0')}:${date.getMinutes().toString().padStart(2, '0')}`
    },
    getAvatarLabel() {
      if (this.isUser) return '我'
      return this.aiType === 'love' ? '恋爱大师' : '智能体'
    },
    getLoadingText() {
      if (this.aiType === 'love') return '恋爱大师正在思考...'
      return '智能体正在分析...'
    }
  }
}
</script>

<style scoped>
.message-item {
  display: flex;
  margin-bottom: var(--spacing-lg);
  align-items: flex-start;
  gap: var(--spacing-md);
}

.message-right {
  flex-direction: row-reverse;
}

.avatar-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: var(--spacing-xs);
  min-width: 60px;
}

.avatar {
  position: relative;
}

.user-avatar .el-avatar {
  background: linear-gradient(135deg, var(--primary-color), var(--primary-light));
  color: white;
}

.love-avatar {
  background: linear-gradient(135deg, #ff6b9d, #ff8fab) !important;
  color: white !important;
}

.manus-avatar {
  background: linear-gradient(135deg, #667eea, #764ba2) !important;
  color: white !important;
}

.avatar-label {
  font-size: var(--font-size-xs);
  color: var(--text-tertiary);
  text-align: center;
  white-space: nowrap;
}

.message-content {
  display: flex;
  flex-direction: column;
  max-width: 70%;
  min-width: 0;
}

.message-bubble {
  padding: var(--spacing-md) var(--spacing-lg);
  border-radius: var(--radius-large);
  word-wrap: break-word;
  position: relative;
  box-shadow: var(--shadow-light);
  border: 1px solid var(--border-light);
  animation: messageSlideIn 0.4s cubic-bezier(0.175, 0.885, 0.32, 1.275);
  transform-origin: bottom left;
}

.message-right .message-bubble {
  transform-origin: bottom right;
}

@keyframes messageSlideIn {
  0% {
    opacity: 0;
    transform: scale(0.8) translateY(20px);
  }
  100% {
    opacity: 1;
    transform: scale(1) translateY(0);
  }
}

.user-bubble {
  background: linear-gradient(135deg, var(--primary-color), var(--primary-light));
  color: white;
  border: none;
}

.love-bubble {
  background: linear-gradient(135deg, #fff5f7, #ffe8ed);
  border-color: #ff6b9d;
  border-left: 4px solid #ff6b9d;
}

.manus-bubble {
  background: linear-gradient(135deg, #f8f9ff, #f0f2ff);
  border-color: #667eea;
  border-left: 4px solid #667eea;
}

.step-bubble {
  background: linear-gradient(135deg, #e8f4fd, #f0f9ff) !important;
  border-left: 4px solid var(--primary-color) !important;
  border-color: var(--primary-color) !important;
}

.step-indicator {
  display: flex;
  align-items: center;
  gap: var(--spacing-xs);
  margin-bottom: var(--spacing-sm);
  padding: var(--spacing-xs) var(--spacing-sm);
  background: linear-gradient(135deg, var(--primary-color), var(--primary-light));
  color: white;
  border-radius: var(--radius-small);
  font-size: var(--font-size-xs);
  width: fit-content;
  animation: glowPulse 2s infinite;
  box-shadow: 0 2px 8px rgba(64, 158, 255, 0.4);
}

.step-indicator .el-icon {
  animation: rotate 2s linear infinite;
}

@keyframes rotate {
  from { transform: rotate(0deg); }
  to { transform: rotate(360deg); }
}

.message-text p {
  margin: 0;
  line-height: 1.6;
  font-size: var(--font-size-base);
}

.message-time {
  font-size: var(--font-size-xs);
  color: var(--text-tertiary);
  margin-top: var(--spacing-xs);
  padding-left: var(--spacing-sm);
}

.message-right .message-time {
  text-align: right;
  padding-right: var(--spacing-sm);
  padding-left: 0;
}

.loading-container {
  display: flex;
  align-items: center;
  gap: var(--spacing-sm);
}

.typing-indicator {
  display: flex;
  gap: var(--spacing-xs);
  position: relative;
}

.typing-indicator span {
  width: 8px;
  height: 8px;
  background: linear-gradient(45deg, var(--primary-color), var(--primary-light));
  border-radius: 50%;
  animation: typingBounce 1.4s ease-in-out infinite both;
  box-shadow: 0 0 10px rgba(64, 158, 255, 0.5);
}

.typing-indicator span:nth-child(1) {
  animation-delay: -0.32s;
}

.typing-indicator span:nth-child(2) {
  animation-delay: -0.16s;
}

@keyframes typingBounce {
  0%, 80%, 100% {
    transform: scale(0.8) translateY(0);
    opacity: 0.5;
  }
  40% {
    transform: scale(1.2) translateY(-10px);
    opacity: 1;
  }
}

.loading-text {
  font-size: var(--font-size-sm);
  color: var(--text-secondary);
}

.step-message {
  margin-bottom: var(--spacing-sm);
}

@keyframes typingPulse {
  0%, 80%, 100% {
    transform: scale(0.6);
    opacity: 0.5;
  }
  40% {
    transform: scale(1);
    opacity: 1;
  }
}

/* 响应式设计 */
@media (max-width: 768px) {
  .message-content {
    max-width: 85%;
  }
  
  .message-bubble {
    padding: var(--spacing-sm) var(--spacing-md);
  }
  
  .avatar-container {
    min-width: 50px;
  }
  
  .avatar .el-avatar {
    width: 32px !important;
    height: 32px !important;
    font-size: 14px !important;
  }
}
</style> 