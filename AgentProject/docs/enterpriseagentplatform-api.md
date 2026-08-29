# enterprise-agent-platform API

## 统一说明

- 基础地址：`http://localhost:8082`
- 返回格式：成功接口直接返回 JSON 对象或数组
- 当前没有登录认证和数据权限过滤，生产环境禁止直接开放写操作
- 参数校验失败返回 HTTP 400，响应为 `{ "message": "..." }`

## Agent

### `POST /api/agent/chat`

用途：根据角色、会话上下文、AgentProject 业务库、MES 生产事实和 Qdrant 知识库生成回答。

请求体：

| 字段 | 类型 | 必填 | 说明 |
|---|---|---|---|
| `message` | string | 是 | 用户问题，不能为空 |
| `role` | string | 否 | `SALES`、`PROCUREMENT`、`PRODUCTION`、`WAREHOUSE`、`ADMIN`，非法值降级为 `SALES` |
| `conversationId` | string | 否 | 已有会话ID，不传则创建新会话 |

响应字段：`conversationId`、`answer`、`role`、`model`、`localModelAvailable`、`sources[]`、`suggestedActions[]`。

## MES 生产

### `GET /api/mes/production/overview`

用途：优先调用 `ruishun-mes` 的生产工单API；API不可用且开启数据库降级时读取 `rsmes_cloud.mes_product_workorder`。

响应字段：

| 字段 | 类型 | 说明 |
|---|---|---|
| `dataAvailable` | boolean | MES数据是否读取成功 |
| `source` | string | 当前为 `rsmes_cloud` |
| `queriedAt` | string | 查询时间 |
| `message` | string | 不可用原因；成功时为空 |
| `totalWorkOrders` | number | 未删除工单总数 |
| `schedulingWorkOrders` | number | `status=1` 排产中数量 |
| `producingWorkOrders` | number | `status=2` 生产中数量 |
| `completedWorkOrders` | number | `status=3` 完工数量 |
| `recentWorkOrders` | object[] | 最近10条工单 |

`recentWorkOrders[]` 字段：`id`、`workorderCode`、`orderCode`、`customerName`、`productModel`、`productSpecification`、`status`、`weight`、`createTime`、`updateTime`。

### `GET /api/mes/production/workorders`

用途：查询 MES 生产工单。

查询参数：`workorderCode`（可选，工单号模糊匹配）、`status`（可选，`1=排产中`、`2=生产中`、`3=完工`）、`limit`（可选，默认20，服务端限制1至200）。

响应为工单对象数组，字段同 `recentWorkOrders[]`。MES不可用时返回空数组，原因请查询概览接口。

### `POST /api/mes/production/index-knowledge`

用途：把当前生产概览和最近工单转换为文本，调用现有 Embedding 和 Qdrant 服务索引。

响应：`{ "indexedChunks": number }`。副作用仅为写入 Qdrant，不写入 MES。重复调用会产生重复向量，正式版本需要增加快照去重和版本元数据。

### `GET /api/mes/warehouses`

用途：调用 `ruishun-wms` 的 `/wms/warehouse/getlist` 查询启用中的仓库，供工作台和 Agent 使用。

响应为仓库对象数组，每项字段为：`warehouseId`、`warehouseCode`、`warehouseName`、`location`、`charge`、`status`、`workstageName`。API未启用或调用失败时返回空数组。

## 现有运营接口

| 方法 | 路径 | 说明 |
|---|---|---|
| `GET` | `/api/dashboard/summary` | AgentProject 演示业务概览 |
| `GET` | `/api/inventory?keyword=` | AgentProject 演示库存查询 |
| `GET` | `/api/orders` | AgentProject 最近20条演示订单 |
| `POST` | `/api/knowledge/index` | 索引知识文本，body为 `{ "content": "..." }` |
| `POST` | `/api/feishu/events` | 飞书事件入口，目前只处理URL验证和接受事件 |

## 错误和安全边界

- `message` 为空返回 HTTP 400。
- MES未启用、连接失败或表结构不匹配时，生产概览返回 `dataAvailable=false`，Agent明确提示数据不可用。
- 当前所有 MES 接口均为只读，禁止通过 Agent 传入 SQL。
- 当前没有登录、组织范围权限和审批流，排产、报工、工单修改等写接口尚未实现。

## 变更记录

| 2026-08-29 | 增加 MES 生产查询和生产快照索引接口说明，补充 Agent 会话参数 |
