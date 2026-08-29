-- AgentProject 会话上下文表。
-- 业务事实仍保存在 AgentProject 主库或 rsmes_cloud，本文件不修改 MES 表。

CREATE TABLE IF NOT EXISTS agent_conversation (
    conversation_id VARCHAR(64) NOT NULL COMMENT '会话UUID',
    role VARCHAR(32) NOT NULL COMMENT '当前角色',
    created_at DATETIME NOT NULL COMMENT '创建时间',
    updated_at DATETIME NOT NULL COMMENT '最近更新时间',
    PRIMARY KEY (conversation_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Agent会话主表';

CREATE TABLE IF NOT EXISTS agent_conversation_message (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '消息主键',
    conversation_id VARCHAR(64) NOT NULL COMMENT '所属会话ID',
    role VARCHAR(16) NOT NULL COMMENT '消息角色：user或assistant',
    content TEXT NOT NULL COMMENT '消息正文',
    created_at DATETIME NOT NULL COMMENT '消息时间',
    PRIMARY KEY (id),
    KEY idx_agent_message_conversation_time (conversation_id, created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Agent会话消息表';
