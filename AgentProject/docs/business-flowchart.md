# 业务流程图

本文档从业务人员视角描述当前 FlowPilot 原型如何处理订单、库存、采购、生产和仓储协作。流程以当前已实现代码为准，AI 输出的是分析结果和建议，不会自动执行写操作。

## 业务协作总流程

```mermaid
flowchart TD
    START([业务人员进入工作台]) --> ROLE[选择业务角色]
    ROLE --> INPUT[输入业务问题或选择快捷场景]
    INPUT --> OBJECT{业务场景}

    OBJECT -->|订单交付评估| ORDER_INFO[提供客户、产品、数量和交付日期]
    OBJECT -->|库存健康检查| STOCK_INFO[查询库存和预留数量]
    OBJECT -->|运营简报| BRIEF_INFO[查看最近订单和库存概况]
    OBJECT -->|采购/生产/仓储协作| COLLAB_INFO[描述缺料、排产或出入库事项]

    ORDER_INFO --> FACTS
    STOCK_INFO --> FACTS
    BRIEF_INFO --> FACTS
    COLLAB_INFO --> FACTS

    FACTS[系统汇总业务事实]
    FACTS --> MYSQL_FACTS[(MySQL)]
    MYSQL_FACTS --> FACT_DETAIL[订单、库存、客户、产品和状态]
    FACTS --> MES_FACTS[(rsmes_cloud)]
    MES_FACTS --> MES_DETAIL[生产工单、状态和查询时间]
    FACTS --> KNOWLEDGE_FACTS[检索制度和流程知识]
    KNOWLEDGE_FACTS --> QDRANT[(Qdrant 知识库)]
    QDRANT --> KNOWLEDGE_RESULT[相关规则和参考内容]

    FACT_DETAIL --> ANALYZE[Agent 分析事实、风险和建议]
    MES_DETAIL --> ANALYZE
    KNOWLEDGE_RESULT --> ANALYZE
    ANALYZE --> MODEL{本地模型可用?}
    MODEL -->|是| MODEL_RESULT[生成中文分析结果]
    MODEL -->|否| RULE_RESULT[规则模式返回查询结果]
    MODEL_RESULT --> REVIEW
    RULE_RESULT --> REVIEW

    REVIEW[业务人员查看事实、风险和建议]
    REVIEW --> ACTION_TYPE{是否需要业务操作?}
    ACTION_TYPE -->|否| CLOSE[记录结论并结束本次分析]
    ACTION_TYPE -->|是| CONFIRM[人工确认操作内容、对象和数量]
    CONFIRM --> PROTOTYPE_LIMIT[当前原型停止在建议阶段]
    PROTOTYPE_LIMIT --> MANUAL[由人员在现有业务系统中手工执行]
    MANUAL --> CLOSE

    ORDER_INFO --> ORDER_PATH[交付评估建议]
    ORDER_PATH --> ORDER_RISK[依据订单、库存和知识库识别交付风险]
    ORDER_RISK --> REVIEW

    STOCK_INFO --> STOCK_PATH[库存健康建议]
    STOCK_PATH --> AVAILABLE[可用库存 = 总库存 - 已预留]
    AVAILABLE --> SAFETY{可用库存低于安全库存?}
    SAFETY -->|是| LOW_STOCK[产生库存预警]
    SAFETY -->|否| STOCK_OK[库存状态正常]
    LOW_STOCK --> REVIEW
    STOCK_OK --> REVIEW

    COLLAB_INFO --> COLLAB_PATH[生成协作建议]
    COLLAB_PATH --> COLLAB_TYPE{关键词识别事项}
    COLLAB_TYPE -->|采购/缺料/供应商| PROCUREMENT[采购申请草稿建议]
    COLLAB_TYPE -->|排产/生产/产能| PRODUCTION[生产计划和产能风险查询建议]
    COLLAB_TYPE -->|出库/入库/仓库| WAREHOUSE[仓储协作任务建议]
    COLLAB_TYPE -->|未识别| MORE[提示补充业务对象、数量和日期]
    PROCUREMENT --> REVIEW
    PRODUCTION --> REVIEW
    WAREHOUSE --> REVIEW
    MORE --> INPUT

    classDef actor fill:#e8f1fb,stroke:#599ce7,color:#141414
    classDef process fill:#eaf5ef,stroke:#3f9a66,color:#141414
    classDef decision fill:#f8f0e8,stroke:#d9943a,color:#141414
    classDef data fill:#fff4e5,stroke:#d9943a,color:#141414
    classDef result fill:#edf5f4,stroke:#4c9a8a,color:#141414
    classDef boundary fill:#f7ebee,stroke:#c85872,color:#141414

    class START,ROLE,INPUT,ORDER_INFO,STOCK_INFO,BRIEF_INFO,COLLAB_INFO actor
    class FACTS,FACT_DETAIL,KNOWLEDGE_FACTS,ANALYZE,AVAILABLE,COLLAB_PATH,ORDER_PATH,STOCK_PATH process
    class OBJECT,MODEL,ACTION_TYPE,SAFETY,COLLAB_TYPE decision
    class MYSQL_FACTS,QDRANT,KNOWLEDGE_RESULT data
    class MODEL_RESULT,RULE_RESULT,REVIEW,ORDER_RISK,LOW_STOCK,STOCK_OK,PROCUREMENT,PRODUCTION,WAREHOUSE,MORE,CLOSE result
    class CONFIRM,PROTOTYPE_LIMIT,MANUAL boundary
```

## 各场景说明

- 订单交付评估：系统读取最近订单和相关库存，把业务上下文交给 Agent，输出交付事实、风险和建议。当前没有独立的生产计划、BOM 或产能计算服务。
- 库存健康检查：工作台根据 `总库存 - 已预留库存` 计算可用库存，并用产品安全库存判断预警；Agent 对话则根据关键词读取库存并生成建议。
- 运营简报：读取最近 20 条订单和库存概况，由 Agent 总结需要销售、生产和仓储关注的事项。
- 采购协作：命中“采购、缺料、供应商”等关键词时，返回“生成采购申请草稿”的建议，不会保存采购单。
- 生产协作：命中“排产、生产、产能”等关键词时，会读取 `rsmes_cloud.mes_product_workorder` 的工单概览和最近工单；排产计算和写回仍未实现。
- 仓储协作：命中“出库、入库、仓库”等关键词时，返回仓储协作任务建议，不会自动执行出入库。

## 当前业务闭环边界

当前闭环是“提出问题 -> 汇总事实 -> AI 分析 -> 人工确认 -> 人工执行”，不是自动化交易闭环。正式业务流程还需要补充权限校验、审批状态、采购单和生产计划等业务对象，以及操作审计和幂等控制。
