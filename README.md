# Yu AI Agent - 智能AI助手平台

<div align="center">
  <h1>🤖 Yu AI Agent</h1>
  <p>基于Spring Boot + Vue 3构建的现代化AI智能助手平台</p>

  ![Java](https://img.shields.io/badge/Java-21-ED8B00?style=flat-square&logo=openjdk&logoColor=white)
  ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.5-6DB33F?style=flat-square&logo=spring&logoColor=white)
  ![Vue.js](https://img.shields.io/badge/Vue.js-3.3.4-4FC08D?style=flat-square&logo=vue.js)
  ![Element Plus](https://img.shields.io/badge/Element%20Plus-2.3.9-409EFF?style=flat-square&logo=element)
  ![Docker](https://img.shields.io/badge/Docker-支持-2496ED?style=flat-square&logo=docker&logoColor=white)
  ![License](https://img.shields.io/badge/License-MIT-green?style=flat-square)
</div>

## 📖 项目介绍

Yu AI Agent 是一个功能强大的AI智能助手平台，集成了多种AI模型和工具，提供智能对话、情感咨询、超级智能体等功能。项目采用前后端分离架构，支持实时流式对话，具有良好的扩展性和用户体验。

### 🎯 核心功能

- **🎭 AI恋爱大师** - 专业的情感咨询助手，提供恋爱建议和情感支持
- **🧠 AI超级智能体** - 具备强大推理能力的多步骤问题解决助手
- **💬 实时对话** - 基于SSE技术的流式对话体验
- **🔧 工具集成** - 支持MCP协议，可扩展各种AI工具
- **💾 对话记忆** - 持久化对话历史，支持上下文连续对话
- **📊 API文档** - 集成Knife4j，提供完整的API文档

## ✨ 技术特性

- 🚀 **现代化架构** - Spring Boot 3 + Vue 3 + Vite
- 🤖 **多模型支持** - 阿里云百炼、Ollama等多种AI模型
- 🔄 **流式响应** - Server-Sent Events实时流式对话
- 🛠️ **工具扩展** - MCP协议支持，可接入各种AI工具
- 📱 **响应式设计** - 完美适配桌面端和移动端
- 🐳 **容器化部署** - Docker支持，一键部署
- 📚 **RAG支持** - 文档检索增强生成
- 🔐 **安全可靠** - 完善的错误处理和安全机制

## 🛠️ 技术栈

### 后端技术栈
- **核心框架**: Spring Boot 3.4.5
- **Java版本**: JDK 21
- **AI框架**: Spring AI 1.0.0-M6、LangChain4j
- **AI模型**: 阿里云百炼DashScope、Ollama
- **数据库**: MySQL 8.0
- **文档工具**: Knife4j (Swagger)
- **工具库**: Hutool、Jackson、Jsoup
- **构建工具**: Maven 3.9

### 前端技术栈
- **核心框架**: Vue 3.3.4
- **构建工具**: Vite 4.4.9
- **UI组件**: Element Plus 2.3.9
- **路由管理**: Vue Router 4.2.4
- **HTTP客户端**: Axios 1.4.0
- **开发工具**: TypeScript、ESLint

### 基础设施
- **容器化**: Docker + Docker Compose
- **Web服务器**: Nginx
- **数据库**: MySQL 8.0
- **缓存**: 内存缓存
- **协议支持**: MCP (Model Context Protocol)

## 📦 快速开始

### 环境要求

- **Java**: JDK 21+
- **Node.js**: 16.0.0+
- **MySQL**: 8.0+
- **Maven**: 3.6+
- **Docker**: 20.10+ (可选)

### 1. 克隆项目

```bash
git clone https://github.com/Fishstarpro/yu-ai-agent.git
cd yu-ai-agent
```

### 2. 数据库初始化

```bash
# 创建数据库
mysql -u root -p < sql/create.sql
```

### 3. 后端启动

```bash
# 配置application-local.yml中的数据库连接和AI模型API密钥
# 启动后端服务
mvn spring-boot:run
```

后端服务将在 `http://localhost:8112` 启动

### 4. 前端启动

```bash
cd yu-ai-agent-frontend

# 安装依赖
npm install

# 启动开发服务器
npm run dev
```

前端服务将在 `http://localhost:3000` 启动

### 5. 访问应用

- **前端界面**: http://localhost:3000
- **API文档**: http://localhost:8112/api/doc.html
- **健康检查**: http://localhost:8112/api/health

## 🐳 Docker部署

### 使用Docker Compose（推荐）

```bash
# 创建docker-compose.yml文件
version: '3.8'
services:
  mysql:
    image: mysql:8.0
    environment:
      MYSQL_ROOT_PASSWORD: your_password
      MYSQL_DATABASE: yu_ai_agent
    ports:
      - "3306:3306"
    volumes:
      - ./sql/create.sql:/docker-entrypoint-initdb.d/create.sql

  backend:
    build: .
    ports:
      - "8112:8112"
    depends_on:
      - mysql
    environment:
      SPRING_PROFILES_ACTIVE: prod

  frontend:
    build: ./yu-ai-agent-frontend
    ports:
      - "80:80"
    depends_on:
      - backend

# 启动所有服务
docker-compose up -d
```

### 单独构建镜像

```bash
# 构建后端镜像
docker build -t yu-ai-agent-backend .

# 构建前端镜像
cd yu-ai-agent-frontend
docker build -t yu-ai-agent-frontend .

# 运行容器
docker run -d -p 8112:8112 yu-ai-agent-backend
docker run -d -p 80:80 yu-ai-agent-frontend
```

## 🏗️ 项目结构

```
yu-ai-agent/
├── src/main/java/com/yxc/yuaiagent/
│   ├── agent/                 # AI智能体实现
│   ├── app/                   # 应用层服务
│   ├── controller/            # REST API控制器
│   ├── config/                # 配置类
│   ├── tools/                 # AI工具实现
│   ├── rag/                   # RAG检索增强
│   ├── chatmemory/            # 对话记忆管理
│   └── YuAiAgentApplication.java
├── src/main/resources/
│   ├── application.yml        # 应用配置
│   └── document/              # RAG文档库
├── yu-ai-agent-frontend/      # 前端项目
│   ├── src/
│   │   ├── components/        # Vue组件
│   │   ├── views/             # 页面视图
│   │   ├── router/            # 路由配置
│   │   └── utils/             # 工具函数
│   ├── public/                # 静态资源
│   └── dist/                  # 构建输出
├── sql/                       # 数据库脚本
├── yu-image-search-mcp/       # MCP图像搜索工具
├── Dockerfile                 # 后端Docker配置
├── docker-compose.yml         # Docker编排配置
└── README.md                  # 项目文档
```

## 🔧 配置说明

### 后端配置

在 `src/main/resources/application-local.yml` 中配置：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/yu_ai_agent
    username: your_username
    password: your_password
  
  ai:
    dashscope:
      api-key: your_dashscope_api_key
    ollama:
      base-url: http://localhost:11434
```

### 前端配置

在 `yu-ai-agent-frontend/vite.config.js` 中配置API代理：

```javascript
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

## 📚 API文档

项目集成了Knife4j，提供完整的API文档：

- **开发环境**: http://localhost:8112/api/doc.html

### 主要API接口

| 接口 | 方法 | 描述 |
|------|------|------|
| `/api/health` | GET | 健康检查 |
| `/api/ai/love_app/chat/sse` | GET | AI恋爱大师流式对话 |
| `/api/ai/manus/chat` | GET | AI超级智能体对话 |

## 🤝 贡献指南

1. Fork 本仓库
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 打开 Pull Request

### 开发规范

- 后端遵循Spring Boot最佳实践
- 前端使用Vue 3 Composition API
- 代码注释使用中文
- 提交信息遵循 Conventional Commits

## 🐛 问题反馈

如果您在使用过程中遇到问题，请通过以下方式反馈：

1. 查看 [Issues](../../issues) 是否已有相关问题
2. 创建新的 Issue 并详细描述问题
3. 提供复现步骤和环境信息

## 📄 许可证

本项目采用 MIT 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情。

## 🙏 致谢

感谢以下开源项目和服务：

- [Spring Boot](https://spring.io/projects/spring-boot) - 企业级Java应用框架
- [Spring AI](https://spring.io/projects/spring-ai) - AI应用开发框架
- [Vue.js](https://vuejs.org/) - 渐进式JavaScript框架
- [Element Plus](https://element-plus.org/) - Vue 3组件库
- [阿里云百炼](https://dashscope.aliyun.com/) - AI模型服务
- [LangChain4j](https://github.com/langchain4j/langchain4j) - Java AI应用框架

## 📞 联系方式

- 项目维护者: fishstar
- 邮箱: yushenxiansheng@163.com
- 项目地址: https://github.com/Fishstarpro/yu-ai-agent

---

<div align="center">
  <p>如果这个项目对您有帮助，请给它一个 ⭐️</p>
  <p>Made with ❤️ by fishstar</p>
</div> 