<template>
  <div class="home-container">
    <!-- 头部区域 -->
    <div class="hero-section">
      <div class="hero-content">
        <h1 class="hero-title">
          AI智能助手平台
        </h1>
        <p class="hero-subtitle">
          体验前沿AI技术，开启智能对话新时代
        </p>
        <div class="hero-features">
          <div class="feature-item">
            <el-icon class="feature-icon"><ChatDotRound /></el-icon>
            <span>智能对话</span>
          </div>
          <div class="feature-item">
            <el-icon class="feature-icon"><Lightning /></el-icon>
            <span>实时响应</span>
          </div>
          <div class="feature-item">
            <el-icon class="feature-icon"><Star /></el-icon>
            <span>专业服务</span>
          </div>
        </div>
      </div>
    </div>
    
    <!-- 应用卡片区域 -->
    <div class="apps-section">
      <div class="container">
        <h2 class="section-title">选择您的AI助手</h2>
        <div class="app-cards">
          <div 
            class="app-card" 
            @click="navigateTo('/love-app')"
          >
            <div class="card-icon">
              <el-icon><User /></el-icon>
            </div>
            <div class="card-content">
              <h3 class="card-title">AI恋爱大师</h3>
              <p>专业的恋爱话题助手，提供贴心的情感建议和聊天陪伴</p>
              <div class="card-features">
                <span class="feature-tag">情感咨询</span>
                <span class="feature-tag">聊天陪伴</span>
              </div>
            </div>
            <div class="card-arrow">
              <el-icon><ArrowRight /></el-icon>
            </div>
          </div>
          
          <div 
            class="app-card" 
            @click="navigateTo('/manus-app')"
          >
            <div class="card-icon">
              <el-icon><Monitor /></el-icon>
            </div>
            <div class="card-content">
              <h3 class="card-title">AI超级智能体</h3>
              <p>全能型AI助手，具备强大的推理能力和多步骤问题解决能力</p>
              <div class="card-features">
                <span class="feature-tag">智能推理</span>
                <span class="feature-tag">多步骤处理</span>
              </div>
            </div>
            <div class="card-arrow">
              <el-icon><ArrowRight /></el-icon>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { useRouter } from 'vue-router'

export default {
  name: 'Home',
  setup() {
    const router = useRouter()
    
    const navigateTo = (path) => {
      router.push(path)
    }
    
    return {
      navigateTo
    }
  }
}
</script>

<style scoped>
.home-container {
  min-height: 100vh;
  background: #ffffff;
}

/* 头部区域 */
.hero-section {
  padding: 80px 0 60px;
  text-align: center;
  background: #ffffff;
  border-bottom: 1px solid #f0f0f0;
}

.hero-content {
  max-width: 800px;
  margin: 0 auto;
  padding: 0 24px;
}

.hero-title {
  font-size: 48px;
  font-weight: 700;
  color: #1a1a1a;
  margin-bottom: 16px;
  line-height: 1.2;
}

.hero-subtitle {
  font-size: 18px;
  color: #666666;
  margin-bottom: 40px;
  line-height: 1.6;
}

.hero-features {
  display: flex;
  justify-content: center;
  gap: 32px;
  flex-wrap: wrap;
}

.feature-item {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 12px 20px;
  background: #f8f9fa;
  border-radius: 8px;
  border: 1px solid #e9ecef;
  transition: all 0.2s ease;
}

.feature-item:hover {
  background: #f1f3f4;
  border-color: #d0d7de;
}

.feature-icon {
  font-size: 20px;
  color: #4285f4;
}

.feature-item span {
  font-size: 14px;
  color: #333333;
  font-weight: 500;
}

/* 应用区域 */
.apps-section {
  padding: 60px 0;
  background: #fafbfc;
}

.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 24px;
}

.section-title {
  text-align: center;
  font-size: 32px;
  font-weight: 600;
  color: #1a1a1a;
  margin-bottom: 48px;
}

.app-cards {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(400px, 1fr));
  gap: 24px;
  max-width: 1000px;
  margin: 0 auto;
}

.app-card {
  background: #ffffff;
  border-radius: 12px;
  padding: 24px;
  cursor: pointer;
  transition: all 0.2s ease;
  border: 1px solid #e9ecef;
  display: flex;
  align-items: center;
  gap: 20px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.04);
}

.app-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.08);
  border-color: #d0d7de;
}

.card-icon {
  flex-shrink: 0;
  width: 64px;
  height: 64px;
  background: #f8f9fa;
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  border: 1px solid #e9ecef;
}

.card-icon .el-icon {
  font-size: 28px;
  color: #4285f4;
}

.card-content {
  flex: 1;
  text-align: left;
}

.card-content h3 {
  font-size: 20px;
  font-weight: 600;
  color: #1a1a1a;
  margin-bottom: 8px;
}

.card-content p {
  font-size: 14px;
  color: #666666;
  line-height: 1.5;
  margin-bottom: 12px;
}

.card-features {
  display: flex;
  gap: 8px;
  flex-wrap: wrap;
}

.feature-tag {
  background: #f1f3f4;
  color: #5f6368;
  padding: 4px 8px;
  border-radius: 4px;
  font-size: 12px;
  font-weight: 500;
}

.card-arrow {
  flex-shrink: 0;
  color: #9aa0a6;
  transition: all 0.2s ease;
}

.app-card:hover .card-arrow {
  color: #4285f4;
  transform: translateX(2px);
}

.card-arrow .el-icon {
  font-size: 18px;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .hero-title {
    font-size: 36px;
  }
  
  .hero-subtitle {
    font-size: 16px;
  }
  
  .hero-features {
    gap: 16px;
  }
  
  .app-cards {
    grid-template-columns: 1fr;
    gap: 16px;
  }
  
  .app-card {
    flex-direction: column;
    text-align: center;
    padding: 20px;
  }
  
  .card-content {
    text-align: center;
  }
  
  .card-arrow {
    transform: rotate(90deg);
  }
  
  .app-card:hover .card-arrow {
    transform: rotate(90deg) translateX(2px);
  }
}

@media (max-width: 480px) {
  .hero-section {
    padding: 60px 0 40px;
  }
  
  .hero-content {
    padding: 0 16px;
  }
  
  .hero-title {
    font-size: 28px;
  }
  
  .hero-features {
    flex-direction: column;
    gap: 12px;
  }
  
  .apps-section {
    padding: 40px 0;
  }
  
  .container {
    padding: 0 16px;
  }
  
  .section-title {
    font-size: 24px;
    margin-bottom: 32px;
  }
}
</style> 