# Yu AI Agent Frontend

<div align="center">
  <h1>🤖 Yu AI智能助手前端项目</h1>
  <p>基于Vue 3构建的现代化AI智能助手平台</p>
  
  ![Vue.js](https://img.shields.io/badge/Vue.js-3.3.4-4FC08D?style=flat-square&logo=vue.js)
  ![Element Plus](https://img.shields.io/badge/Element%20Plus-2.3.9-409EFF?style=flat-square&logo=element)
  ![Vite](https://img.shields.io/badge/Vite-4.4.9-646CFF?style=flat-square&logo=vite)
  ![License](https://img.shields.io/badge/License-MIT-green?style=flat-square)
</div>

## 📖 项目介绍

Yu AI Agent Frontend 是一个现代化的AI智能助手前端平台，提供直观友好的用户界面，支持多种AI助手应用。项目采用Vue 3 + Element Plus构建，具有响应式设计和优秀的用户体验。

### 🎯 主要功能

- **AI恋爱大师** - 专业的恋爱话题助手，提供情感咨询和聊天陪伴
- **AI超级智能体** - 全能型AI助手，具备强大的推理能力和多步骤问题解决能力
- **实时对话** - 基于SSE技术的实时流式对话体验
- **响应式设计** - 适配各种设备屏幕尺寸

## ✨ 功能特性

- 🚀 **现代化技术栈** - Vue 3 + Composition API + Vite
- 💬 **实时对话** - Server-Sent Events (SSE) 流式响应
- 🎨 **优雅UI** - Element Plus组件库，现代化设计风格
- 📱 **响应式设计** - 完美适配桌面端和移动端
- ⚡ **高性能** - Vite构建工具，快速开发和构建
- 🐳 **容器化部署** - Docker支持，一键部署
- 🔧 **开发友好** - 热重载、代理配置、TypeScript支持

## 🛠️ 技术栈

### 核心框架
- **Vue 3** - 渐进式JavaScript框架
- **Vue Router 4** - 官方路由管理器
- **Vite** - 下一代前端构建工具

### UI组件库
- **Element Plus** - 基于Vue 3的组件库
- **Element Plus Icons** - 图标库

### 工具库
- **Axios** - HTTP客户端
- **UUID** - 唯一标识符生成

### 开发工具
- **Cross-env** - 跨平台环境变量设置
- **Terser** - JavaScript压缩工具
- **Rimraf** - 跨平台删除工具

## 📦 安装与使用

### 环境要求

- Node.js >= 16.0.0
- npm >= 8.0.0

### 快速开始

```bash
# 克隆项目
git clone <repository-url>
cd yu-ai-agent-frontend

# 安装依赖
npm install

# 启动开发服务器
npm run dev
```

访问 `http://localhost:3000` 查看应用。

### 可用脚本

```bash
# 开发环境启动
npm run dev

# 生产环境构建
npm run build

# 生产环境构建（带环境变量）
npm run build:prod

# 预览构建结果
npm run serve
npm run preview

# 清理构建文件
npm run clean          # Unix/Linux/macOS
npm run clean:win      # Windows

# 完整部署流程
npm run deploy

# 构建测试
npm run test:build
```

## 🏗️ 项目结构

```
yu-ai-agent-frontend/
├── public/                 # 静态资源
├── src/
│   ├── assets/            # 项目资源文件
│   ├── components/        # 公共组件
│   ├── router/           # 路由配置
│   ├── utils/            # 工具函数
│   ├── views/            # 页面组件
│   │   ├── Home.vue      # 首页
│   │   ├── LoveApp.vue   # AI恋爱大师
│   │   └── ManusApp.vue  # AI超级智能体
│   ├── App.vue           # 根组件
│   └── main.js           # 应用入口
├── dist/                 # 构建输出目录
├── .dockerignore         # Docker忽略文件
├── .gitignore           # Git忽略文件
├── Dockerfile           # Docker配置
├── nginx.conf           # Nginx配置
├── package.json         # 项目配置
├── test-build.js        # 构建测试脚本
├── vite.config.js       # Vite配置
└── README.md           # 项目说明
```

## 🚀 部署指南

### 开发环境配置

开发环境下，API请求会通过Vite代理转发到后端服务：

```javascript
// vite.config.js
server: {
  port: 3000,
  proxy: {
    "/api": {
      target: "http://localhost:8112",
      changeOrigin: true,
    },
  },
}
```

### 生产环境部署

#### 方式一：传统部署

```bash
# 1. 构建项目
npm run deploy

# 2. 将dist目录部署到Web服务器
# 配置Nginx代理API请求到后端服务
```

#### 方式二：Docker部署

```bash
# 构建Docker镜像
docker build -t yu-ai-agent-frontend .

# 运行容器
docker run -d -p 80:80 yu-ai-agent-frontend
```

#### 方式三：Docker Compose

```yaml
version: '3.8'
services:
  frontend:
    build: .
    ports:
      - "80:80"
    depends_on:
      - backend
```

### Nginx配置

项目包含了生产环境的Nginx配置文件 `nginx.conf`，主要功能：

- 静态文件服务
- API代理转发
- Gzip压缩
- 缓存策略
- SPA路由支持

### 健康检查

部署完成后，可以通过以下方式检查服务状态：

- 访问 `/health.html` 查看服务健康状态
- 运行 `npm run test:build` 验证构建结果

## 🔧 配置说明

### 环境变量

项目支持通过环境变量进行配置：

- `NODE_ENV` - 运行环境（development/production）
- `VITE_API_BASE_URL` - API基础URL（可选）

### API配置

- **开发环境**: API请求代理到 `http://localhost:8112/api`
- **生产环境**: API请求使用相对路径 `/api`，通过Nginx代理

## 🤝 贡献指南

1. Fork 本仓库
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 打开 Pull Request

## 📝 开发规范

- 使用 Vue 3 Composition API
- 遵循 ESLint 代码规范
- 组件命名采用 PascalCase
- 文件命名采用 kebab-case
- 提交信息遵循 Conventional Commits

## 🐛 问题反馈

如果您在使用过程中遇到问题，请通过以下方式反馈：

1. 查看 [Issues](../../issues) 是否已有相关问题
2. 创建新的 Issue 并详细描述问题
3. 提供复现步骤和环境信息

## 📄 许可证

本项目采用 MIT 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情。

## 🙏 致谢

感谢以下开源项目：

- [Vue.js](https://vuejs.org/) - 渐进式JavaScript框架
- [Element Plus](https://element-plus.org/) - Vue 3组件库
- [Vite](https://vitejs.dev/) - 下一代前端构建工具

---

<div align="center">
  <p>如果这个项目对您有帮助，请给它一个 ⭐️</p>
</div>