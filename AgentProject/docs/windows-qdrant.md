# Windows安装Qdrant

## 推荐方案

Windows环境使用Docker Desktop的WSL2后端运行Qdrant。Qdrant服务本身运行在Linux容器中，Spring Boot仍然通过Windows本机的 `localhost:6333` 访问。

当前电脑已检测到：

- Windows 10 Pro
- Docker命令尚未安装
- WSL状态需要通过系统命令确认

## 安装步骤

1. 使用管理员身份打开PowerShell。
2. 执行：

```powershell
wsl --install
wsl --update
```

3. 按系统提示重启电脑。
4. 安装Docker Desktop for Windows。
5. Docker Desktop中确认启用 **Use the WSL 2 based engine**。
6. 回到项目根目录执行：

```powershell
docker compose up -d qdrant
```

7. 检查服务：

```powershell
docker ps
Invoke-RestMethod http://localhost:6333/collections
```

## 项目配置

默认配置已经指向：

```text
QDRANT_BASE_URL=http://localhost:6333
QDRANT_COLLECTION=enterprise_knowledge
QDRANT_EMBEDDING_DIMENSION=1024
```

启动Spring Boot后，第一次索引知识库时会自动创建 `enterprise_knowledge` 集合。使用 `bge-m3` 时维度是1024；更换Embedding模型时必须同步修改维度配置。

## 常见问题

端口6333被占用时，可以修改 `docker-compose.yml`左侧端口，例如 `6335:6333`，同时设置：

```powershell
$env:QDRANT_BASE_URL="http://localhost:6335"
```

Docker未启动时，Spring Boot仍可以启动，但知识库索引和语义检索会降级为空结果，不影响MySQL业务查询和Qwen对话。
