# MES Agent 接入设计

## 目标

AgentProject 首期连接本地 MySQL 数据库 `rsmes_cloud`，为 Agent 提供生产工单事实查询、生产知识检索和可追溯的会话上下文。当前只实现只读查询和知识快照索引，不会修改 MES 数据。

## 数据边界

```text
实时生产事实  -> ruishun-cloud MES HTTP API
API不可用时    -> MES JdbcTemplate 固定只读 SQL降级
SOP/工艺/质量规则 -> Ollama Embedding -> Qdrant RAG
复杂计算       -> 后续排产/产能业务服务
业务写操作     -> 后续审批工作流 -> MES API
LLM            -> 意图理解、工具编排、结果解释
```

不能把向量库作为实时生产数据的唯一来源。工单状态、报工数量和设备状态必须以 MES 查询或经过明确标注的同步快照为准，回答中应携带查询时间。

## 当前已实现

### MES API 数据源

- API优先开关：`MES_API_ENABLED`，默认开启
- MES服务地址：`MES_API_BASE_URL`，默认 `http://localhost:9977`
- WMS服务地址：`WMS_API_BASE_URL`，默认 `http://localhost:9213`
- 内部来源头：`MES_API_FROM_SOURCE`，默认 `inner`
- MES工单接口：`/mesProductWorkorder/getMesWorkOrdersData`、`/mesProductWorkorder/list`
- WMS仓库接口：`/wms/warehouse/getlist`
- API响应兼容 ruishun-cloud 的 `AjaxResult` 和 `TableDataInfo` 包装结构

AgentProject 当前不是 ruishun-cloud 的 Spring Cloud/Nacos 服务，不能直接注入 `RemoteMesService` 或 `RemoteWmsService` 这两个 OpenFeign 接口。这里通过 `RestTemplate` 调用同一组服务 HTTP API；未来将 AgentProject 注册到 Nacos 后，可以把适配器替换为 OpenFeign，业务层不需要变化。

### MES 数据库降级

- 数据库：MySQL `rsmes_cloud`
- 默认地址：`192.168.10.109:3306`
- 配置开关：`MES_DB_ENABLED`
- 账号：`MES_DB_USERNAME`
- 密码：`MES_DB_PASSWORD`
- 访问方式：API优先；可选独立 `DataSource` + `JdbcTemplate` 降级
- 写入保护：MES 数据源没有 JPA 实体和写操作，只允许服务内固定查询

MES 运行时配置的真实密码不写入仓库。如果数据库实际运行在本机回环地址，请将 `MES_DB_URL` 改为 `jdbc:mysql://localhost:3306/rsmes_cloud...`。

### 首期生产表

API优先读取生产工单接口，数据库降级时读取 `mes_product_workorder`，使用字段：

```text
id, workorder_code, order_code, customer_name,
product_model, product_specification, status, weight,
create_time, update_time, is_deleted
```

状态含义来自 MES Mapper：`1=排产中`、`2=生产中`、`3=完工`。未知状态原样展示为 `状态N`，不由模型猜测含义。

### Agent 上下文

`agent_conversation` 保存会话主信息，`agent_conversation_message` 保存消息。每轮最多向模型提供最近 10 条消息，避免上下文无限增长。客户端需要保存响应中的 `conversationId`，下一轮请求带回该字段。

上下文故障不会阻断生产查询，服务会降级为空历史上下文。当前尚未接入登录和数据权限，因此不能作为多租户生产环境直接使用。

## RAG 策略

### 适合索引

- SOP 和设备操作指导
- 工艺路线、工艺参数和质量标准
- 异常处理规范
- 生产日报和人工确认的生产快照

### 不适合只依赖索引

- 当前工单状态
- 当前产能和实时设备状态
- 实时库存和报工数量
- 排产可行性计算

可通过 `POST /api/mes/production/index-knowledge` 手动把当前生产概览和最近工单生成知识片段。API优先时快照来自 ruishun-cloud；该接口是显式触发的，避免定时任务重复写入大量相同向量。正式版本需要增加文档 ID、版本、有效期、权限和去重策略。

## 后续排产边界

```text
Agent提取订单、交期和约束
  -> SchedulingService调用规则/优化算法
  -> 返回多个可行方案和风险
  -> 用户确认或审批
  -> MES API发布计划
  -> 审计记录和飞书通知
```

LLM 不直接生成最终排产结果，也不直接执行 MES 写操作。

## 配置示例

```powershell
$env:MES_API_ENABLED="true"
$env:MES_API_BASE_URL="http://localhost:9977"
$env:WMS_API_BASE_URL="http://localhost:9213"
$env:MES_DB_URL="jdbc:mysql://192.168.10.109:3306/rsmes_cloud?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai&useSSL=false&allowPublicKeyRetrieval=true"
$env:MES_DB_USERNAME="root"
$env:MES_DB_PASSWORD="数据库密码"
mvn spring-boot:run
```

启动后先访问 `GET /api/mes/production/overview` 验证连接，再触发知识快照索引。

## 变更记录

| 2026-08-29 | 增加 rsmes_cloud MES 只读接入、生产工单查询、会话上下文和生产快照索引设计 |
