<template>
  <div class="app-container">
    <transition name="fade" mode="out-in">
      <router-view />
    </transition>
    <app-footer v-if="showFooter" />
  </div>
</template>

<script>
import { computed } from 'vue'
import { useRoute } from 'vue-router'
import AppFooter from './components/AppFooter.vue'

export default {
  name: 'App',
  components: {
    AppFooter
  },
  setup() {
    const route = useRoute()
    
    // 只在主页显示页脚
    const showFooter = computed(() => {
      return route.path === '/'
    })
    
    return {
      showFooter
    }
  }
}
</script>

<style>
.app-container {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

/* 路由过渡动画 */
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style> 