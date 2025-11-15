# Web UI 项目说明

## 项目简介
基于 Vue 3 + Vue Router + Axios 的前端项目，对接后端 Spring Boot 系统用户管理模块。

## 技术栈
- **Vue 3.2** - 渐进式 JavaScript 框架
- **Vue Router 4** - 官方路由管理器
- **Axios** - HTTP 请求库
- **Vue CLI 5** - 项目脚手架

## 项目结构
```
web-ui/
├── public/              # 静态资源
├── src/
│   ├── api/            # API 接口定义
│   │   └── user.js     # 用户相关接口
│   ├── assets/         # 资源文件（图片、样式等）
│   ├── components/     # 公共组件
│   ├── config/         # 配置文件
│   │   └── index.js    # 全局配置（环境变量）
│   ├── router/         # 路由配置
│   │   └── index.js    # 路由定义
│   ├── utils/          # 工具函数
│   │   └── request.js  # Axios 封装
│   ├── views/          # 页面组件
│   │   ├── Home.vue    # 首页（用户列表示例）
│   │   └── About.vue   # 关于页面
│   ├── App.vue         # 根组件
│   └── main.js         # 入口文件
├── .env                # 通用环境变量
├── .env.development    # 开发环境变量
├── .env.production     # 生产环境变量
├── vue.config.js       # Vue CLI 配置
└── package.json        # 项目依赖

```

## 环境配置

### 开发环境 (.env.development)
- API 地址：`http://localhost:8080/api`
- 启用 Mock 数据
- 开发服务器端口：8081

### 生产环境 (.env.production)
- API 地址：`https://api.example.com`
- 禁用 Mock 数据
- 启用代码分割和优化

## 快速开始

### 安装依赖
```bash
npm install
```

### 开发模式
```bash
npm run dev
```
访问：http://localhost:8081

### 生产构建
```bash
npm run build
```
构建产物在 `dist/` 目录

### 代码检查
```bash
npm run lint
```

## API 接口说明

### 后端接口规范
- 基础路径：`/system/user`
- 返回格式：`{ code: 200, msg: '操作成功', data: {...} }`
- 状态码：200 表示成功，其他表示失败

### 用户接口列表
| 接口 | 方法 | 说明 |
|------|------|------|
| `/system/user/list` | GET | 获取用户列表（不分页） |
| `/system/user/page` | GET | 分页查询用户列表 |
| `/system/user/{id}` | GET | 获取用户详情 |
| `/system/user` | POST | 新增用户 |
| `/system/user` | PUT | 修改用户 |
| `/system/user/{ids}` | DELETE | 删除用户 |
| `/system/user/saveOrUpdate` | POST | 保存或更新用户 |

## 核心功能

### 1. 路由管理
- 使用 Vue Router 4
- 懒加载路由组件
- HTML5 History 模式

### 2. HTTP 请求
- Axios 统一封装
- 请求/响应拦截器
- 自动添加 Token
- 统一错误处理

### 3. 环境配置
- 多环境配置支持
- 开发/生产环境自动切换
- API 代理配置（解决跨域）

### 4. 构建优化
- 生产环境代码分割
- 第三方库单独打包
- 公共代码提取
- 关闭 Source Map

## 开发规范

### 命名规范
- 组件名：大驼峰（PascalCase）
- 文件名：大驼峰（PascalCase.vue）
- 方法名：小驼峰（camelCase）
- 常量名：全大写下划线（UPPER_CASE）

### 代码注释
- 文件头部添加功能说明
- 复杂逻辑添加注释
- API 接口添加参数和返回值说明

## 待清理项
- `src/components/HelloWorld.vue` - 未使用的示例组件，可手动删除

## 常见问题

### 1. 跨域问题
开发环境通过 `vue.config.js` 中的 proxy 配置解决。

### 2. Token 管理
Token 存储在 localStorage，请求时自动添加到 Authorization 头。

### 3. 环境变量不生效
修改 `.env.*` 文件后需要重启开发服务器。

## 后续扩展建议
- [ ] 添加 Vuex/Pinia 状态管理
- [ ] 集成 Element Plus UI 组件库
- [ ] 添加用户登录/登出功能
- [ ] 完善错误提示（Toast/Message）
- [ ] 添加请求 Loading 效果
- [ ] 添加路由权限控制
