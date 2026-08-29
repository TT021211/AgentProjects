<script setup>
import { computed, onMounted, ref } from 'vue'

const roles = [
  { key: 'SALES', label: '销售', short: 'SA', color: 'coral', description: '订单、客户与交付评估' },
  { key: 'PROCUREMENT', label: '采购', short: 'PR', color: 'amber', description: '供应商、缺料与采购申请' },
  { key: 'PRODUCTION', label: '生产', short: 'PD', color: 'blue', description: '产能、排程与生产异常' },
  { key: 'WAREHOUSE', label: '仓储', short: 'WH', color: 'green', description: '库存、库位与出入库' }
]

const activeRole = ref(roles[0])
const message = ref('')
const loading = ref(false)
const error = ref('')
const reply = ref(null)
const conversationId = ref(null)
const summary = ref({ productCount: 0, inventoryRecordCount: 0, orderCount: 0, lowStockCount: 0 })
const inventory = ref([])
const orders = ref([])
const warehouses = ref([])
const mesProduction = ref({
  dataAvailable: false,
  message: '正在读取MES生产数据...',
  totalWorkOrders: 0,
  schedulingWorkOrders: 0,
  producingWorkOrders: 0,
  completedWorkOrders: 0,
  recentWorkOrders: []
})

const roleGreeting = computed(() => `我是${activeRole.value.label}智能体，可以帮你处理${activeRole.value.description}。`)

async function loadDashboard() {
  try {
    const [summaryResponse, inventoryResponse, ordersResponse, mesResponse, warehousesResponse] = await Promise.all([
      fetch('/api/dashboard/summary'),
      fetch('/api/inventory'),
      fetch('/api/orders'),
      fetch('/api/mes/production/overview'),
      fetch('/api/mes/warehouses')
    ])
    if (!summaryResponse.ok || !inventoryResponse.ok || !ordersResponse.ok) throw new Error('dashboard request failed')
    summary.value = await summaryResponse.json()
    inventory.value = await inventoryResponse.json()
    orders.value = await ordersResponse.json()
    mesProduction.value = mesResponse.ok
      ? await mesResponse.json()
      : { ...mesProduction.value, dataAvailable: false, message: 'MES生产接口暂不可用。' }
    warehouses.value = warehousesResponse.ok ? await warehousesResponse.json() : []
  } catch (err) {
    error.value = '无法连接后端，请先启动Spring Boot服务。'
  }
}

function mesStatusLabel(status) {
  if (status === 1) return '排产中'
  if (status === 2) return '生产中'
  if (status === 3) return '完工'
  return status == null ? '未知' : `状态${status}`
}

function formatMesTime(value) {
  return value ? value.replace('T', ' ').slice(0, 16) : '-'
}

async function askAgent(text = message.value) {
  if (!text.trim() || loading.value) return
  loading.value = true
  error.value = ''
  try {
    const response = await fetch('/api/agent/chat', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ role: activeRole.value.key, message: text, conversationId: conversationId.value })
    })
    if (!response.ok) throw new Error('request failed')
    reply.value = await response.json()
    conversationId.value = reply.value.conversationId
    message.value = ''
  } catch (err) {
    error.value = 'Agent请求失败，请检查后端、MySQL和Ollama服务。'
  } finally {
    loading.value = false
  }
}

function chooseRole(role) {
  activeRole.value = role
  reply.value = null
  conversationId.value = null
}

onMounted(loadDashboard)
</script>

<template>
  <div class="app-shell">
    <aside class="sidebar">
      <div class="brand"><span class="brand-mark">F</span><span>FlowPilot</span></div>
      <div class="workspace-label">企业运营智能中枢</div>
      <nav>
        <a class="nav-item active"><span>◈</span> 工作台</a>
        <a class="nav-item"><span>⌁</span> 协作流程</a>
        <a class="nav-item"><span>▤</span> 知识库</a>
        <a class="nav-item"><span>⚙</span> 系统设置</a>
      </nav>
      <div class="sidebar-footer">
        <div class="local-status"><i></i><div><strong>本地智能引擎</strong><small>Qwen3:8B · 已连接</small></div></div>
        <div class="user-profile"><span class="avatar">演</span><div><strong>演示管理员</strong><small>平台管理员</small></div><span class="dots">···</span></div>
      </div>
    </aside>

    <main class="main-content">
      <header class="topbar">
        <div><p class="eyebrow">MONDAY, AUGUST 22, 2026</p><h1>早上好，演示管理员 <span>✦</span></h1></div>
        <div class="top-actions"><button class="icon-btn">⌕</button><button class="icon-btn notice">♧<b></b></button><button class="outline-btn">＋ 新建协作</button></div>
      </header>

      <section class="hero-grid">
        <div class="hero-card">
          <div class="hero-copy"><span class="pill">AI OPERATIONS</span><h2>让每个业务决策，<br /><em>都有智能协作者。</em></h2><p>连接销售、采购、生产与仓储，让信息流动起来，让团队专注于更重要的事。</p></div>
          <div class="orb"><span>✦</span><i></i><i></i><i></i></div>
          <div class="hero-lines"></div>
        </div>
         <div class="quick-card"><div class="section-title"><span>快速开始</span><small>常用场景</small></div><button @click="askAgent('客户A订购产品P-1001 1000件，要求10天内交付，能否按时完成？')"><span class="quick-icon coral-bg">↗</span><div><strong>订单交付评估</strong><small>库存 · 产能 · 缺料风险</small></div><b>→</b></button><button @click="askAgent('请帮我检查当前库存中低于安全库存的物料，并给出采购建议。')"><span class="quick-icon amber-bg">⌁</span><div><strong>库存健康检查</strong><small>安全库存 · 采购建议</small></div><b>→</b></button><button @click="askAgent('请总结最近订单，并标记需要生产和仓储协作的事项。')"><span class="quick-icon blue-bg">▣</span><div><strong>生成运营简报</strong><small>订单 · 生产 · 仓储</small></div><b>→</b></button><button @click="askAgent('请查询当前MES生产工单情况，告诉我正在生产和排产中的工单数量。')"><span class="quick-icon green-bg">⌁</span><div><strong>MES生产态势</strong><small>工单 · 进度 · 生产状态</small></div><b>→</b></button></div>
       </section>

      <section class="panel mes-panel">
        <div class="panel-heading">
          <div><span class="eyebrow">MES PRODUCTION PULSE</span><h3>生产现场脉搏</h3></div>
          <div class="mes-heading-actions"><span :class="['mes-connection', mesProduction.dataAvailable ? 'connected' : 'offline']"><i></i>{{ mesProduction.dataAvailable ? 'MES 已连接' : 'MES 未连接' }}</span><button class="refresh-btn" @click="loadDashboard">刷新 ↻</button></div>
        </div>
        <div v-if="mesProduction.dataAvailable" class="mes-content">
          <div class="mes-metrics">
            <div><small>工单总数</small><strong>{{ mesProduction.totalWorkOrders }}</strong></div>
            <div><small>排产中</small><strong>{{ mesProduction.schedulingWorkOrders }}</strong></div>
            <div><small>生产中</small><strong class="production-number">{{ mesProduction.producingWorkOrders }}</strong></div>
            <div><small>已完工</small><strong>{{ mesProduction.completedWorkOrders }}</strong></div>
          </div>
          <div class="mes-meta">数据源：{{ mesProduction.source }} <span>·</span> 查询时间：{{ formatMesTime(mesProduction.queriedAt) }}</div>
          <div class="mes-table">
            <div class="mes-table-row mes-table-head"><span>工单</span><span>产品</span><span>客户</span><span>状态</span><span>更新时间</span></div>
            <div v-for="workOrder in mesProduction.recentWorkOrders" :key="workOrder.id" class="mes-table-row"><span><b>{{ workOrder.workorderCode || '-' }}</b><small>{{ workOrder.orderCode || '无来源订单' }}</small></span><span>{{ workOrder.productModel || workOrder.productSpecification || '-' }}</span><span>{{ workOrder.customerName || '-' }}</span><span><i :class="['status-dot', workOrder.status === 2 ? 'production-dot' : '']"></i>{{ mesStatusLabel(workOrder.status) }}</span><span class="mes-time">{{ formatMesTime(workOrder.updateTime || workOrder.createTime) }}</span></div>
          </div>
          <div v-if="warehouses.length" class="warehouse-strip"><div class="warehouse-strip-title"><span>仓库目录</span><small>{{ warehouses.length }} 个 WMS 仓库</small></div><div class="warehouse-list"><div v-for="warehouse in warehouses" :key="warehouse.warehouseId" class="warehouse-item"><span class="warehouse-icon">WH</span><div><b>{{ warehouse.warehouseName || warehouse.warehouseCode || '-' }}</b><small>{{ warehouse.warehouseCode || '-' }} · {{ warehouse.location || '位置未维护' }}</small></div></div></div></div>
        </div>
        <div v-else class="mes-empty"><span class="mes-empty-icon">◎</span><div><strong>MES 数据暂未接入工作台</strong><p>{{ mesProduction.message || '请检查 MES_DB_ENABLED、数据库地址和密码配置。' }}</p><code>MES_DB_ENABLED=true</code></div><button class="outline-btn" @click="loadDashboard">重新检测</button></div>
      </section>

      <section class="metric-grid">
        <div class="metric-card"><span class="metric-icon coral-bg">▧</span><div><small>活跃产品</small><strong>{{ summary.productCount }}</strong></div><span class="metric-trend">+12%</span></div>
        <div class="metric-card"><span class="metric-icon blue-bg">▤</span><div><small>库存记录</small><strong>{{ summary.inventoryRecordCount }}</strong></div><span class="metric-trend">稳定</span></div>
        <div class="metric-card"><span class="metric-icon green-bg">↗</span><div><small>销售订单</small><strong>{{ summary.orderCount }}</strong></div><span class="metric-trend">本月</span></div>
        <div class="metric-card warning"><span class="metric-icon amber-bg">!</span><div><small>库存预警</small><strong>{{ summary.lowStockCount }}</strong></div><span class="metric-trend">需关注</span></div>
      </section>

      <section class="content-grid">
        <div class="panel agent-panel">
          <div class="panel-heading"><div><span class="eyebrow">ROLE AGENTS</span><h3>智能协作席</h3></div><span class="live-dot">● 在线</span></div>
          <div class="role-tabs"><button v-for="role in roles" :key="role.key" :class="['role-tab', { selected: activeRole.key === role.key }]" @click="chooseRole(role)"><span :class="['role-avatar', role.color]">{{ role.short }}</span><span>{{ role.label }}</span></button></div>
          <div class="chat-area">
            <div class="agent-message"><span :class="['role-avatar large', activeRole.color]">{{ activeRole.short }}</span><div><div class="message-meta"><strong>{{ activeRole.label }}智能体</strong><small>刚刚</small></div><p>{{ roleGreeting }}</p></div></div>
            <div v-if="reply" class="agent-message reply"><span class="role-avatar large system-avatar">✦</span><div><div class="message-meta"><strong>FlowPilot 分析结果</strong><small>{{ reply.model }}</small></div><p class="answer">{{ reply.answer }}</p><div class="chips"><span v-for="action in reply.suggestedActions" :key="action">{{ action }}</span></div></div></div>
            <div v-if="loading" class="typing"><i></i><i></i><i></i> 正在分析业务数据...</div>
          </div>
          <div class="prompt-box"><textarea v-model="message" :placeholder="`向${activeRole.label}智能体提问，例如：当前有哪些需要关注的事项？`" @keydown.enter.exact.prevent="askAgent()"></textarea><div class="prompt-footer"><span>⌘ Enter 发送 · 本地模型优先</span><button :disabled="loading || !message.trim()" @click="askAgent">{{ loading ? '分析中...' : '发送  →' }}</button></div></div>
        </div>

        <div class="panel activity-panel"><div class="panel-heading"><div><span class="eyebrow">DATA PULSE</span><h3>业务数据脉搏</h3></div><button class="more-btn">···</button></div><div class="table-title"><span>库存概览</span><button @click="loadDashboard">刷新 ↻</button></div><div class="data-table"><div class="table-row table-head"><span>产品</span><span>仓库</span><span>可用库存</span><span>状态</span></div><div v-for="item in inventory" :key="`${item.productCode}-${item.warehouseCode}`" class="table-row"><span><b>{{ item.productCode }}</b><small>{{ item.productName }}</small></span><span>{{ item.warehouseCode }}</span><span>{{ item.availableQuantity }} {{ item.productCode.startsWith('P') ? '台' : '件' }}</span><span><i :class="item.availableQuantity < 100 ? 'status-dot danger' : 'status-dot'"></i>{{ item.availableQuantity < 100 ? '关注' : '正常' }}</span></div></div><div class="table-title order-title"><span>最近订单</span><span class="muted">{{ orders.length }} 条记录</span></div><div class="orders-list"><div v-for="order in orders" :key="order.orderNo" class="order-row"><span class="order-badge">SO</span><div><b>{{ order.orderNo }}</b><small>{{ order.customerName }} · {{ order.productCode }} × {{ order.quantity }}</small></div><span class="order-status">{{ order.status }}</span></div></div></div>
      </section>
      <p v-if="error" class="error-banner">{{ error }}</p>
      <footer class="page-footer">FlowPilot Enterprise Agent Platform <span>·</span> Local-first prototype <span>·</span> v0.1.0</footer>
    </main>
  </div>
</template>
