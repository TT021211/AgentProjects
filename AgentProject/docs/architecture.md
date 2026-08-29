# 架构说明

## 设计原则

FlowPilot采用“模型负责理解，系统负责计算和执行”的边界：

- LLM负责意图识别、参数提取、知识问答和结果解释
- Java业务服务负责库存、订单、日期和金额计算
- 角色策略负责控制可见数据和可调用工具
- 工作流负责多角色协作和人工确认
- MySQL保存业务事实，Qdrant保存可检索知识片段
- 飞书是协作入口，不是业务主数据库

## 请求链路

```text
飞书/网页请求
  -> AgentController
  -> AgentService识别角色和业务意图
  -> Business Service读取AgentProject MySQL
  -> MesProductionService优先调用ruishun-cloud MES API（命中生产问题时）
  -> API失败时按配置降级读取rsmes_cloud
  -> KnowledgeService检索Qdrant
  -> OllamaClient调用Qwen3:8B
  -> 返回事实、风险和建议
```

模型不可用时，`AgentService`会返回规则模式结果，保证业务查询接口仍可演示。后续接入真实工具时，所有写操作都应该经过权限校验、人工确认和审计记录。

## 本地服务

- MySQL：`localhost:3306`，保存组织、产品、库存和订单
- ruishun-mes API：`localhost:9977`，生产工单服务（由 `MES_API_ENABLED` 控制）
- ruishun-wms API：`localhost:9213`，仓库服务（由 `MES_API_ENABLED` 控制）
- rsmes_cloud：`192.168.10.109:3306`，只读保存MES生产数据（API失败时可由 `MES_DB_ENABLED` 降级）
- Qdrant：`localhost:6333`，保存知识库向量
- Ollama：`localhost:11434`，提供聊天和Embedding接口
- Spring Boot：`localhost:8082`
- Vue/Vite：`localhost:5173`

## 飞书接入边界

当前提供：

- `POST /api/feishu/events`：处理URL验证请求
- `FeishuClient`：通过自定义机器人Webhook发送文本

MES生产接入当前提供：

- 生产工单概览和最近工单查询
- 按工单号、状态筛选工单
- 手动生成生产快照知识文档
- Agent命中生产关键词时注入带查询时间的生产上下文
- Agent命中仓库、仓储或库位关键词时注入WMS仓库API上下文

正式接入飞书应用时，需要补充：

- App ID和App Secret管理
- `tenant_access_token`获取和缓存
- 请求签名校验
- 事件去重
- 飞书open_id到本地用户的映射
- 交互卡片和审批回调

本机开发接收飞书回调时，需要使用Cloudflare Tunnel或ngrok提供公网HTTPS地址。

MES当前通过API接入生产工单和WMS仓库，设备、报工、质量、物料和工艺查询尚未接入。
