# FlowPilot 企业运营智能体原型

FlowPilot 是一个面向企业协作场景的本地优先 Agent 原型。它以 Spring Boot 为后端、MySQL 为业务数据库、Qdrant 为向量数据库、Vue 为前端，并通过 Ollama 调用本机的 Qwen3:8B。

当前原型包含：

- 销售、采购、生产、仓储四个角色入口
- 产品、库存、销售订单演示数据
- 本地模型对话和规则降级机制
- 库存、订单、运营概览接口
- Qdrant知识库索引和检索接口
- rsmes_cloud MES生产工单只读查询和生产快照索引
- Agent会话上下文持久化
- 飞书事件订阅入口及Webhook适配器骨架
- 移动端可用的Vue工作台

## 环境要求

- JDK 8或更高版本（当前项目使用Spring Boot 2.7，兼容Java 8）
- Maven 3.8+
- Node.js 18+
- Docker Desktop + WSL2（Windows下运行Qdrant和可选MySQL）
- Ollama，并已下载模型 `qwen3:8b`
- 用于向量检索的Embedding模型，推荐 `bge-m3`

## 启动基础服务

Windows推荐先安装Docker Desktop，并在安装时启用WSL2后端。管理员PowerShell中可以执行：

```powershell
wsl --install
wsl --update
```

执行后如果系统要求重启，请先重启，再打开Docker Desktop，确认Settings中的 **Use the WSL 2 based engine** 已启用。然后在项目根目录执行：

```bash
docker compose up -d mysql qdrant
ollama pull qwen3:8b
ollama pull bge-m3
```

验证Qdrant：

```powershell
Invoke-RestMethod http://localhost:6333/collections
```

Qdrant管理页面：`http://localhost:6333/dashboard`

当前项目使用Qdrant的REST接口，不需要在Windows本机单独安装Java或Python Qdrant客户端。Qdrant数据保存在Docker命名卷中，容器重启后不会因为项目目录挂载问题丢失。

如果本机MySQL已经运行，可以不启动Compose中的MySQL，并通过环境变量修改连接信息：

```bash
DB_USERNAME=root root=root mvn spring-boot:run
```

## 启动后端

```bash
mvn spring-boot:run
```

后端地址：`http://localhost:8082`

健康检查：`http://localhost:8082/actuator/health`

## 启动前端

```bash
cd frontend
npm install
npm run dev
```

前端地址：`http://localhost:5173`

## API示例

查询概览：

```bash
curl http://localhost:8082/api/dashboard/summary
```

销售角色提问：

```bash
curl -X POST http://localhost:8082/api/agent/chat ^
  -H "Content-Type: application/json" ^
  -d "{\"role\":\"SALES\",\"message\":\"客户A订购产品P-1001 1000件，要求10天内交付，能否按时完成？\"}"
```

索引知识库文本：

```bash
curl -X POST http://localhost:8082/api/knowledge/index ^
  -H "Content-Type: application/json" ^
  -d "{\"content\":\"采购申请金额超过50000元时，需要采购负责人和财务共同审批。\"}"
```

## 配置说明

配置文件是 `src/main/resources/application.yml`，也可以使用环境变量覆盖：

- `DB_URL`、`DB_USERNAME`、`DB_PASSWORD`
- `OLLAMA_BASE_URL`、`OLLAMA_CHAT_MODEL`、`OLLAMA_EMBEDDING_MODEL`
- `QDRANT_BASE_URL`、`QDRANT_COLLECTION`、`QDRANT_EMBEDDING_DIMENSION`
- `FEISHU_WEBHOOK_URL`
- `MES_DB_ENABLED`、`MES_DB_URL`、`MES_DB_USERNAME`、`MES_DB_PASSWORD`
- `MES_API_ENABLED`、`MES_API_BASE_URL`、`WMS_API_BASE_URL`、`MES_API_FROM_SOURCE`

Qdrant向量维度必须和Embedding模型一致。`bge-m3`默认使用1024维；如果更换Embedding模型，必须同步修改 `QDRANT_EMBEDDING_DIMENSION`。

MES默认优先调用 ruishun-cloud API：MES服务为 `http://localhost:9977`，WMS仓储服务为 `http://localhost:9213`。启用前请设置 `MES_API_ENABLED=true`。数据库直连仍可作为降级方案，通过 `MES_DB_ENABLED=true` 开启。如果数据库在本机，请将 `MES_DB_URL` 改为 `localhost:3306/rsmes_cloud`。

## 目录结构

```text
src/main/java/com/agententerprise
├── controller       HTTP接口和飞书回调
├── config           CORS、数据初始化和HTTP客户端
├── domain           MySQL业务实体
├── dto              接口输入输出对象
├── repository       Spring Data JPA仓储
└── service          Agent、模型、向量库和业务服务
frontend             Vue工作台
docs                 架构和开发说明
docker-compose.yml   MySQL和Qdrant本地服务
```

查询MES生产概览：

```bash
curl http://localhost:8082/api/mes/production/overview
```

手动索引MES生产快照：

```bash
curl -X POST http://localhost:8082/api/mes/production/index-knowledge
```

查询WMS仓库：

```bash
curl http://localhost:8082/api/mes/warehouses
```

## 当前原型边界

目前的写操作只生成建议，不会自动创建采购单、修改订单或执行出库。正式产品需要继续补充登录认证、飞书用户映射、数据权限、工具幂等、审批状态机、模型评估和操作审计。

详细设计见：

- [架构说明](docs/architecture.md)
- [当前项目流程图](docs/flowchart.md)
- [业务流程图](docs/business-flowchart.md)
- [开发路线](docs/roadmap.md)
- [MES Agent接入设计](docs/mes-agent-architecture.md)
- [接口文档](docs/enterpriseagentplatform-api.md)
