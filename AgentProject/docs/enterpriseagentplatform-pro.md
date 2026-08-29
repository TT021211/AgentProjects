# enterprise-agent-platform 技术文档

## 项目概述

FlowPilot 是本地优先的企业 Agent 原型。后端使用 Spring Boot 2.7、Java 8、MySQL 和 Spring Data JPA；模型使用 Ollama；向量库使用 Qdrant；前端使用 Vue 和 Vite。当前增加了对本地 `rsmes_cloud` MySQL 的 MES 生产只读接入。

## 项目结构

```text
AgentProject/
├── src/main/java/com/agententerprise/
│   ├── config/       # CORS、演示数据、MES数据源
│   ├── controller/   # REST和飞书入口
│   ├── domain/       # AgentProject自己的JPA实体
│   ├── dto/          # 接口输入输出模型
│   ├── repository/   # AgentProject业务仓储
│   └── service/      # Agent、MES、模型、知识库业务
├── src/main/resources/application.yml
├── frontend/         # Vue工作台
├── docs/             # 架构、流程、路线和接口文档
├── docker-compose.yml
└── pom.xml
```

## 启动流程

```text
Spring Boot启动
  -> 创建AgentProject主数据源
  -> 创建MES独立数据源（不主动连接）
  -> JPA更新AgentProject本地表
  -> 初始化演示数据
  -> HTTP服务监听8082
```

MES数据源默认关闭，设置 `MES_DB_ENABLED=true` 后，首次调用 MES 服务时才连接数据库。

## 核心组件

| 组件 | 职责 |
|---|---|
| `AgentService` | 组装角色、会话、业务事实、RAG参考和模型回答 |
| `MesApiClient` | 调用 ruishun-mes 和 ruishun-wms HTTP API，解析统一响应包装 |
| `MesProductionService` | API优先查询生产事实，必要时使用固定只读SQL降级 |
| `ConversationContextService` | 保存和读取最近10条对话消息 |
| `KnowledgeService` | 文本分块、Embedding和Qdrant索引检索 |
| `OllamaClient` | 调用聊天模型和Embedding模型 |
| `QdrantVectorStore` | Qdrant REST客户端 |

## 接口调用规范

- Controller 只负责参数接收和服务调用。
- MES 查询只能由 `MesProductionService` 中的固定 SQL 执行。
- 不允许把用户输入拼接成 SQL、表名或排序字段。
- 实时业务事实优先于 RAG 结果。
- MES生产和WMS仓库优先通过 ruishun-cloud HTTP API 获取。
- 模型不可用时使用规则降级，数据不可用时必须明确告知。
- 写操作必须等后续权限、确认、审批、幂等和审计能力完成后再开放。

## 配置

| 环境变量 | 默认值 | 说明 |
|---|---|---|
| `DB_URL` | AgentProject本地MySQL | AgentProject自身业务库 |
| `MES_DB_ENABLED` | `false` | 是否启用MES查询 |
| `MES_DB_URL` | `192.168.10.109:3306/rsmes_cloud` | MES只读数据库 |
| `MES_DB_USERNAME` | `root` | MES数据库用户 |
| `MES_DB_PASSWORD` | 空 | MES数据库密码，不提交到仓库 |
| `MES_API_ENABLED` | `true` | 是否启用 ruishun-cloud API |
| `MES_API_BASE_URL` | `http://localhost:9977` | ruishun-mes地址 |
| `WMS_API_BASE_URL` | `http://localhost:9213` | ruishun-wms地址 |
| `MES_API_FROM_SOURCE` | `inner` | API请求来源头 |
| `OLLAMA_CHAT_MODEL` | `qwen3:8b` | 聊天模型 |
| `OLLAMA_EMBEDDING_MODEL` | `bge-m3` | Embedding模型 |
| `QDRANT_COLLECTION` | `enterprise_knowledge` | 向量集合 |

## 构建与部署

```powershell
mvn clean package
cd frontend
npm run build
```

本地基础服务：

```powershell
docker compose up -d mysql qdrant
ollama pull qwen3:8b
ollama pull bge-m3
```

## 已知限制

- MES API当前只接入生产工单概览和WMS仓库列表，设备、报工、质量、物料和工艺查询尚未接入。
- MES运行时配置来自原系统 Nacos，仓库内不能确认所有真实字段约束；AgentProject 使用的查询字段来自 MES Mapper。
- 当前没有认证、数据范围权限、RAG文档权限和多租户隔离。
- 生产快照索引是人工触发且没有去重，不适合高频自动同步。
- 排产目前只保留建议边界，尚未实现约束求解和 MES 写回。

## 变更记录

| 2026-08-29 | 增加 MES 接入、会话上下文、RAG快照索引的技术规范 |
