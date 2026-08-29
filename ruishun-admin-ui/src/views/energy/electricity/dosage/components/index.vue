<template>
  <div>
    <div v-show="deviceNameList.length > 0" class="big-box" :style="{ height: height + 'px' }">
      <div class="box">
        <div class="device-box">
          <div v-for="item in deviceNameList" :key="item.electricDeviceName" class="device">
            <div class="w-100px" style="text-align: center; font-weight: bold">{{ item.electricDeviceName }}</div>
          </div>
        </div>

        <div v-for="(items, index) in deviceValueList" :key="index" class="device-box">
          <div v-for="(val, i) in items" :key="i" class="device">
            <div class="w-100px" style="text-align: center">{{ Number(val).toFixed(2) }}</div>
          </div>
        </div>

        <div class="time-box">
          <div v-for="item in timeList" :key="item.id" class="device">
            <div class="w-80px" style="text-align: center">{{ item.name }}</div>
          </div>
        </div>
      </div>
    </div>
    <div v-show="deviceNameList.length === 0" :style="{ height: height + 'px' }" class="flex-center">
      <n-empty description="暂无机台信息" size="huge"></n-empty>
    </div>
  </div>
</template>

<script setup lang="tsx">
import { ref, onMounted } from 'vue';

withDefaults(
  defineProps<{
    deviceNameList: Array<any>;
    deviceValueList: Array<any>;
    height: number;
  }>(),
  {
    deviceNameList: () => [],
    deviceValueList: () => [],
    height: 600
  }
);

const timeList = ref<{ name: string; id: number }[]>([]);

function init() {
  for (let index = 0; index < 24; index++) {
    timeList.value.push({
      name: `${index}~${index + 1}时`,
      id: index
    });
  }
  timeList.value.unshift({
    name: `今日合计`,
    id: 99
  });
  timeList.value.unshift({
    name: `单位/kwh`,
    id: 98
  });
}
onMounted(() => {
  init();
});
</script>

<style lang="scss" scoped>
.big-box {
  width: 100%;
  overflow: auto;
  transition: height 0.2s linear;
}

.box {
  position: relative;
}
.device-box {
  display: flex;
  margin-left: 80px;
}

.device {
  border: 1px solid #ddd;
  height: 40px;
  line-height: 40px;
}

.time-box {
  position: absolute;
  left: 0;
  top: 0;
}
</style>
