<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item label="车间">
          <n-select
            v-model:value="searchForm.location"
            placeholder="请选择车间"
            :options="locationList"
            class="w-160px"
          ></n-select>
        </n-form-item>
        <n-form-item>
          <component :is="useSearchBtn(search, resetThen)"></component>
        </n-form-item>
      </n-form>
    </my-card>
    <n-card :bordered="false" class="h-full rounded-8px shadow-sm">
      <n-grid cols="s:1 m:2 l:4" responsive="screen" :x-gap="16" :y-gap="16">
        <n-grid-item v-for="item in cardData" :key="item.id">
          <gradient-bg class="h-100px" :start-color="item.colors[0]" :end-color="item.colors[1]">
            <h3 class="text-16px">{{ item.title }}</h3>
            <div class="flex justify-between pt-12px">
              <svg-icon :icon="item.icon" class="text-32px" />
              <count-to
                :prefix="item.unit"
                :start-value="1"
                :end-value="item.value"
                class="text-30px text-white dark:text-dark"
              />
            </div>
          </gradient-bg>
        </n-grid-item>
      </n-grid>

      <n-divider title-placement="center" style="font-size: 18px; margin: 30px 0">设备信息</n-divider>

      <n-grid cols="s:1 m:2 l:4" responsive="screen" :x-gap="16" :y-gap="16">
        <n-grid-item v-for="item in deviceDataList" :key="item.code">
          <div class="device-card" :class="getStatus(item.status).className">
            <div>设备编码 : {{ item.code }}</div>
            <div>状态 : {{ getStatus(item.status).text }}</div>
            <div>拉丝速度 : {{ item.speed }}</div>
            <div>当前重量 : {{ item.nowWeight }}</div>
          </div>
        </n-grid-item>
      </n-grid>
    </n-card>
  </div>
</template>

<script setup lang="tsx">
import { ref, onMounted, onUnmounted, reactive } from 'vue';
import mqtt from 'mqtt';
import { getWorkbenchWiredrawingList } from '@/service/api/md/workbench/wiredrawing';
import { selectAllWorkbenchEnamellingList } from '@/service/api/md/workbench/enamelling';
import { useResetSearch } from '~/src/utils/common/searchReset';
import { useSearchBtn } from '~/src/hooks/common/useBtn';
import { serviceEnv } from '~/.env-config';
import { GradientBg } from './components';
const { searchForm, reset } = useResetSearch({
  location: null
});

interface CardData {
  id: string;
  title: string;
  value: number;
  unit: string;
  colors: [string, string];
  icon: string;
}

type device = {
  code: string;
  status: number;
  speed: string;
  nowWeight: string;
  process: string;
};

const deviceAllList = ref<Array<device>>([]);

const locationList = ref<Array<{ label: string; value: string }>>([
  {
    label: '全部',
    value: '全部'
  }
]);

const deviceDataList = ref<Array<device>>([]);

const cardData = reactive<CardData[]>([
  {
    id: 'visit',
    title: '设备总数',
    value: 0,
    unit: '',
    colors: ['#ec4786', '#b955a4'],
    icon: 'ant-design:bar-chart-outlined'
  },
  {
    id: 'amount',
    title: '运行中',
    value: 96,
    unit: '$',
    colors: ['#865ec0', '#5144b4'],
    icon: 'ant-design:money-collect-outlined'
  },
  {
    id: 'download',
    title: '待机',
    value: 60,
    unit: '',
    colors: ['#56cdf3', '#719de3'],
    icon: 'carbon:document-download'
  },
  {
    id: 'trade',
    title: '故障',
    value: 4,
    unit: '',
    colors: ['#fcbc25', '#f68057'],
    icon: 'ant-design:trademark-circle-outlined'
  }
]);

// MQTT 配置
const mqttBrokerUrl = `${serviceEnv.dev.wsUrl}`; // 可以换成你自己的 broker 地址
const topic = 'ls-data';
// 定义状态
// 创建 MQTT 客户端实例
let client: mqtt.MqttClient;

const clientId = `mgttx_cc213498`;

// 添加用户名和密码配置
const options = {
  clean: true, // 保留会话
  connectTimeout: 4000, // 超时时间
  reconnectPeriod: 1000, // 重连时间间隔
  username: 'rsmqtt',
  password: 'rsmqtt',
  clientId
};

const isConnect = true;

const connectToMQTT = () => {
  // 连接到 MQTT broker
  if (!isConnect) return;
  client = mqtt.connect(mqttBrokerUrl, options);
  // 连接成功后的回调
  client.on('connect', () => {
    client.subscribe(topic, err => {
      if (err) {
        // eslint-disable-next-line no-console
        console.error('Failed to subscribe:', err);
      } else {
        // eslint-disable-next-line no-console
        console.log(`Subscribed to topic: ${topic}`);
      }
    });
  });

  // 监听收到的消息
  client.on('message', (_topic1, message) => {
    // 这里有可能拿到的数据格式是Uint8Array格式，可以直接用toString转成字符串
    const res = JSON.parse(message.toString());
    if (res.data) {
      res.data.forEach(item => {
        const index = deviceAllList.value.findIndex(val => item.code === val.code);
        if (index > -1) {
          deviceAllList.value[index].status = 0;
          deviceAllList.value[index].speed = item.speed;
          deviceAllList.value[index].nowWeight = item.weight;
        }
      });
      // const amountList = deviceAllList.value.filter(item => item.status === 0)
      //  deviceAllList.value.sort((a,b) => a.status - b.status)
      // cardData[1].value =amountList.length;
    }
  });

  // 监听连接错误
  client.on('error', error => {
    // eslint-disable-next-line no-console
    console.error('MQTT connection error:', error);
  });
};

function getStatus(status: number) {
  switch (status) {
    case 0:
      return {
        text: '运行中',
        className: 'amount-color'
      };
    case 1:
      return {
        text: '待机',
        className: 'download-color'
      };
    case 2:
      return {
        text: '故障',
        className: 'trade-color'
      };
    default:
      return {
        text: '运行中',
        className: 'amount-color'
      };
  }
}

async function getList() {
  await getWorkbenchWiredrawingList({ pageSize: 999999 }).then(res => {
    if (res.code === 200) {
      res.rows.forEach(item => {
        deviceEach(item);
      });
    }
  });
  await selectAllWorkbenchEnamellingList({}).then(res => {
    if (res.code === 200) {
      res.data.forEach(item => {
        deviceEach(item);
      });
    }
  });
  // cardData[0].value = deviceAllList.value.length;
  cardData[0].value = 160;
}

function deviceEach(item: any) {
  if (locationList.value.findIndex(ele => ele.value === item.process) === -1) {
    locationList.value.push({
      label: `${item.process}车间`,
      value: item.process
    });
  }
  deviceAllList.value.push({
    code: item.equipmentCode,
    status: 1,
    speed: '0',
    nowWeight: '0',
    process: item.process
  });
}

// function getRandom(min: number, max: number) {
//   return Math.floor(Math.random() * (max - min + 1) + min);
// }

function search() {
  init();
}

function resetThen() {
  reset();
  init();
}

function init() {
  if (!searchForm.value.location) {
    deviceDataList.value = [];
    return;
  }
  if (searchForm.value.location === '全部') {
    deviceDataList.value = deviceAllList.value.map(item => item);
    return;
  }
  deviceDataList.value = deviceAllList.value.filter(item => item.process === searchForm.value.location);
}

// 组件挂载时连接 MQTT
onMounted(() => {
  connectToMQTT();
  init();
  getList();
});

// 组件卸载时断开 MQTT 连接
onUnmounted(() => {
  if (client) {
    client.end();
  }
});
</script>

<style scoped>
/* 你的样式 */

.device-card {
  color: #fff;
  border-radius: 8px;
  padding: 10px;
  box-sizing: border-box;
  font-size: 16px;
}

.amount-color {
  background: linear-gradient(45deg, #865ec0, #5144b4);
}

.download-color {
  background: linear-gradient(45deg, #56cdf3, #719de3);
}

.trade-color {
  background: linear-gradient(45deg, #fcbc25, #f68057);
}
</style>
