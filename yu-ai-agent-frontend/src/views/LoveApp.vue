<template>
  <div class="love-app-container">
    <div class="page-header">
      <el-button @click="goBack" type="text">
        <el-icon><Back /></el-icon>
        返回
      </el-button>
      <h1>AI恋爱大师</h1>
    </div>
    
    <div class="chat-container">
      <chat-room 
        title="恋爱话题助手"
        :createSSEConnection="createLoveAppSSE"
        :needChatId="true"
        aiType="love"
      />
    </div>
  </div>
</template>

<script>
import { useRouter } from 'vue-router'
import ChatRoom from '../components/ChatRoom.vue'
import { createLoveAppSSE } from '../utils/api'

export default {
  name: 'LoveApp',
  components: {
    ChatRoom
  },
  setup() {
    const router = useRouter()
    
    const goBack = () => {
      router.push('/')
    }
    
    return {
      goBack,
      createLoveAppSSE
    }
  }
}
</script>

<style scoped>
.love-app-container {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  background: linear-gradient(135deg, #fff5f7 0%, var(--bg-secondary) 100%);
  padding: var(--spacing-lg);
}

.page-header {
  display: flex;
  align-items: center;
  margin-bottom: var(--spacing-lg);
  padding: var(--spacing-md) 0;
  position: relative;
}

.page-header::after {
  content: '';
  position: absolute;
  bottom: 0;
  left: 50%;
  transform: translateX(-50%);
  width: 60px;
  height: 2px;
  background: linear-gradient(90deg, transparent, #ff6b9d, transparent);
}

.page-header .el-button {
  background: var(--bg-card);
  border: 1px solid var(--border-light);
  color: var(--text-secondary);
  border-radius: var(--radius-base);
  padding: var(--spacing-sm) var(--spacing-md);
  transition: all var(--transition-fast);
}

.page-header .el-button:hover {
  background: var(--bg-tertiary);
  color: var(--text-primary);
  transform: translateY(-1px);
  box-shadow: var(--shadow-light);
}

.page-header h1 {
  margin: 0 auto;
  background: linear-gradient(135deg, #ff6b9d, #ff8fab);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  font-size: var(--font-size-xxl);
  font-weight: 600;
}

.chat-container {
  flex: 1;
  border-radius: var(--radius-large);
  overflow: hidden;
  animation: fadeInUp 0.6s ease-out;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .love-app-container {
    padding: var(--spacing-md);
  }
  
  .page-header h1 {
    font-size: var(--font-size-xl);
  }
}

@media (max-width: 480px) {
  .love-app-container {
    padding: var(--spacing-sm);
  }
  
  .page-header {
    margin-bottom: var(--spacing-md);
  }
}
</style> 