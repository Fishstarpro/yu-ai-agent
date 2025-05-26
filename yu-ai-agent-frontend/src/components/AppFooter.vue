<template>
  <footer class="app-footer">
    <div class="footer-content">
      <div class="footer-main">
        <div class="footer-section">
          <h3 class="footer-title">AI智能助手</h3>
          <p class="footer-desc">专业的AI对话平台，提供智能化的交互体验</p>
        </div>
        
        <div class="footer-section">
          <h4 class="section-title">产品服务</h4>
          <ul class="footer-links">
            <li><a href="/love-app">AI恋爱大师</a></li>
            <li><a href="/manus-app">AI超级智能体</a></li>
          </ul>
        </div>
        
        <div class="footer-section">
          <h4 class="section-title">关于我们</h4>
          <ul class="footer-links">
            <li><a href="#" @click.prevent>用户协议</a></li>
            <li><a href="#" @click.prevent>隐私政策</a></li>
            <li><a href="#" @click.prevent>联系我们</a></li>
          </ul>
        </div>
        
        <div class="footer-section">
          <h4 class="section-title">联系方式</h4>
          <div class="contact-info">
            <p>管理员：fishstar</p>
            <p>邮箱：yushenxiansheng@163.com</p>
          </div>
        </div>
      </div>
      
      <div class="footer-bottom">
        <div class="copyright">
          <p>&copy; {{ currentYear }} AI智能助手平台. 保留所有权利.</p>
          <p>网站管理者：fishstar | 技术支持：Vue3 + Element Plus</p>
        </div>
      </div>
    </div>
  </footer>
</template>

<script>
import { computed } from 'vue'

export default {
  name: 'AppFooter',
  setup() {
    const currentYear = computed(() => new Date().getFullYear())
    
    return {
      currentYear
    }
  }
}
</script>

<style scoped>
.app-footer {
  background: var(--bg-card);
  border-top: 1px solid var(--border-light);
  margin-top: auto;
}

.footer-content {
  max-width: 1200px;
  margin: 0 auto;
  padding: var(--spacing-xxl) var(--spacing-md) var(--spacing-lg);
}

.footer-main {
  display: grid;
  grid-template-columns: 2fr 1fr 1fr 1fr;
  gap: var(--spacing-xl);
  margin-bottom: var(--spacing-xl);
}

.footer-section {
  display: flex;
  flex-direction: column;
}

.footer-title {
  font-size: var(--font-size-xl);
  font-weight: 600;
  color: var(--text-primary);
  margin-bottom: var(--spacing-sm);
}

.footer-desc {
  color: var(--text-secondary);
  font-size: var(--font-size-sm);
  line-height: 1.6;
}

.section-title {
  font-size: var(--font-size-base);
  font-weight: 500;
  color: var(--text-primary);
  margin-bottom: var(--spacing-md);
}

.footer-links {
  list-style: none;
  padding: 0;
}

.footer-links li {
  margin-bottom: var(--spacing-sm);
}

.footer-links a {
  color: var(--text-secondary);
  text-decoration: none;
  font-size: var(--font-size-sm);
  transition: color var(--transition-fast);
}

.footer-links a:hover {
  color: var(--primary-color);
}

.contact-info p {
  color: var(--text-secondary);
  font-size: var(--font-size-sm);
  margin-bottom: var(--spacing-xs);
}

.footer-bottom {
  padding-top: var(--spacing-lg);
  border-top: 1px solid var(--border-light);
}

.copyright {
  text-align: center;
}

.copyright p {
  color: var(--text-tertiary);
  font-size: var(--font-size-xs);
  margin-bottom: var(--spacing-xs);
}

/* 响应式设计 */
@media (max-width: 768px) {
  .footer-main {
    grid-template-columns: 1fr 1fr;
    gap: var(--spacing-lg);
  }
}

@media (max-width: 480px) {
  .footer-main {
    grid-template-columns: 1fr;
    gap: var(--spacing-md);
  }
  
  .footer-content {
    padding: var(--spacing-lg) var(--spacing-md);
  }
}
</style> 