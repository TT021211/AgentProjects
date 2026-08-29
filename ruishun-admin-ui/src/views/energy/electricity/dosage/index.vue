<template>
  <div style="background-color: #fff">
    <my-card title="用电量">
      <template #right>
        <div style="display: flex; align-items: center">
          <n-space class="mr-10px">查询条件</n-space>
          <n-date-picker
            v-model:value="tempDate"
            type="date"
            class="w-180px mr-10px"
            @update:value="changeDate"
          ></n-date-picker>
          <n-select v-model:value="type" :options="typeList" class="w-180px" placeholder="请选择类型"></n-select>
          <n-button type="primary" size="small" class="ml-15px" @click="contentReduce(-50)">内容缩小</n-button>
          <n-button type="primary" size="small" class="ml-15px" @click="contentReduce(50)">内容放大</n-button>
        </div>
      </template>

      <div>
        <div ref="tabs" class="tabs" :style="{ minWidth: 120 * locationList.length }">
          <div
            v-for="(item, index) in locationList"
            :key="item.value"
            class="tab-pane"
            :style="{ minWidth: tabPaneWidth + 'px' }"
          >
            <span
              :class="tabsDefaultValue === item.value ? 'active' : ''"
              style="cursor: pointer"
              @click="changeTabPan(index, item.value)"
            >
              {{ item.label }}
            </span>
          </div>
          <div
            v-if="tabsDefaultValue || tabsDefaultValue === '0'"
            class="active-tabs-underline"
            :style="{
              width: underline.width + 'px',
              left: underline.left + 'px'
            }"
          ></div>
        </div>
      </div>
      <tables :device-name-list="deviceNameList" :device-value-list="deviceValueList" :height="dosageTableHeight" />
      <!-- <n-tabs
        ref="tabs"
        :default-value="tabsDefaultValue"
        justify-content="space-evenly"
        type="line"
        @update-value="changeTabs"
      >
        <n-tab-pane v-for="item in locationList" :key="item.value" :name="item.value" :tab="item.label">
          <tables :device-name-list="deviceNameList" :device-value-list="deviceValueList" />
        </n-tab-pane>
      </n-tabs> -->
      <div v-show="deviceNameList.length > 0" class="mt-1" style="font-size: 18px">
        当日车间总用电量(kwh) : {{ totalCount.toFixed(2) }}
      </div>

      <full-loading v-model:show="showLoading"></full-loading>
    </my-card>
  </div>
</template>

<script setup lang="tsx">
import { ref, onMounted, watch, onUnmounted } from 'vue';
import { getDictDataList } from '@/service/api/system/dictData';
import { getDeviceConsumptionValue, getGeneralTableData } from '@/service/api/ecm/dosate';
import FullLoading from '@/components/FullLoading.vue';
import { getEnergyDeviceList } from '@/service/api/ecm/energyDevice';
import { formatDate } from '~/src/utils/form/rule';
import tables from './components/index.vue';

const showLoading = ref<boolean>(false);

const type = ref<number>(0);

const deviceNameList = ref<any[]>([]);

const deviceValueList = ref<any[]>([]);

const typeList = ref<{ label: string; value: number }[]>([
  { label: '车间', value: 0 },
  { label: '总柜', value: 1 }
]);

const parentGeneralTableList = ref<Array<{ label: string; value: string }>>([]);

const tabPaneWidth = ref<number>(120);

const workshopList = ref<Array<{ label: string; value: string }>>([]);

const totalList = ref<any[]>([]);

const totalCount = ref<number>(0);

const defaultIndex = ref<number>(0);

const tabs = ref();

const timer = ref();

// 监听宽度变化
let resizeObserver: any = null;

const locationList = ref<Array<{ label: string; value: string }>>([]);

const tabsDefaultValue = ref<string>('');

const isGetEnergyDeviceList = ref<boolean>(false);

const tempDate = ref<any>(new Date().getTime());

const dosageTableHeight = ref<number>(Number(localStorage.getItem('dosageTableHeight')) || 600);

const searchForm = ref<{ location: string; date: string }>({
  location: '',
  date: formatDate(new Date(), 'yyyy-MM-dd')
});

const underline = ref({
  width: 80,
  left: 0
});

function contentReduce(num: number) {
  if (dosageTableHeight.value <= 200 && num < 0) {
    window.$message?.warning('不可再缩小');
    return;
  }
  if (dosageTableHeight.value >= 1000 && num > 0) {
    window.$message?.warning('不可再放大');
    return;
  }
  dosageTableHeight.value += num;
}

function changeDate() {
  searchForm.value.date = formatDate(new Date(tempDate.value), 'yyyy-MM-dd');
  init();
}

function changeTabPan(index: number, value: string) {
  if (tabsDefaultValue.value === value) return;
  tabsDefaultValue.value = value;
  defaultIndex.value = index;
  changeUnderlineMove(index);
  init();
}

function changeUnderlineMove(index: number) {
  let clientWidth = tabs.value?.clientWidth;
  if (clientWidth < tabPaneWidth.value * locationList.value.length) {
    clientWidth = tabPaneWidth.value * locationList.value.length;
  }
  const average = clientWidth / locationList.value.length;
  underline.value.left = average * (index + 1) - underline.value.width / 2 - average / 2;
}

function init() {
  const items = locationList.value.find(item => item.value === tabsDefaultValue.value);
  if (items) {
    searchForm.value.location = items.label;
  }
  showLoading.value = true;
  deviceValueList.value = [];
  deviceNameList.value = [];
  totalList.value = [];
  if (type.value === 0) {
    getDeviceConsumptionValue(searchForm.value).then(res => {
      sameInitAction(res);
    });
  } else {
    getGeneralTableData({ parentId: Number(tabsDefaultValue.value), date: searchForm.value.date }).then(res => {
      sameInitAction(res);
    });
  }
}

function sameInitAction(res) {
  showLoading.value = false;
  if (res.code === 200) {
    if (res.data.length === 0) return;
    deviceNameList.value = res.data;
    const length = deviceNameList.value[0].consumptionValues.length;
    for (let index = 0; index < length; index++) {
      const arr: any = [];
      deviceNameList.value.forEach(item => {
        arr.push(item.consumptionValues[index] / 1000);
        if (index === 0) {
          totalList.value.push(item.consumptionValues[index] / 1000);
        }
      });
      deviceValueList.value.push(arr);
    }
    totalCount.value = totalList.value.reduce((sum, item) => sum + (Number(item) || 0), 0);
  }
}

function sameMapAction(list) {
  locationList.value = list.map(item => item);
  changeTabPan(defaultIndex.value, locationList.value[defaultIndex.value]?.value);
}

function getList() {
  return new Promise(resolve => {
    getDictDataList({ dictType: 'energy_name', pageSize: 999 }).then(res => {
      if (res.code === 200) {
        res.rows.forEach(item => {
          workshopList.value.push({
            label: item.dictLabel,
            value: item.dictValue
          });
        });
        locationList.value = workshopList.value.map(item => item);
        changeTabPan(defaultIndex.value, locationList.value[defaultIndex.value]?.value);
      }
      resolve(true);
    });
  });
}

watch(
  () => type.value,
  () => {
    defaultIndex.value = 0;
    if (type.value === 0) {
      sameMapAction(workshopList.value);
    } else {
      locationList.value = parentGeneralTableList.value.map(item => item);
      if (isGetEnergyDeviceList.value) {
        sameMapAction(parentGeneralTableList.value);
      } else {
        getEnergyDeviceList({ isMain: 1 }).then(res => {
          if (res.code === 200) {
            isGetEnergyDeviceList.value = true;
            res.rows.forEach(item => {
              parentGeneralTableList.value.push({
                label: item.electricDeviceName,
                value: item.electricDeviceId
              });
            });
            sameMapAction(parentGeneralTableList.value);
          }
        });
      }
    }
  }
);

onMounted(async () => {
  await getList();
  // init();
  // window.addEventListener('resize', () => {
  //   if (timer.value) clearTimeout(timer.value);
  //   timer.value = setTimeout(() => {
  //     changeUnderlineMove(defaultIndex.value);
  //   }, 200);
  // });

  if (tabs.value) {
    // 创建 ResizeObserver 实例
    resizeObserver = new ResizeObserver(() => {
      if (timer.value) clearTimeout(timer.value);
      timer.value = setTimeout(() => {
        changeUnderlineMove(defaultIndex.value);
      }, 200);
    });

    // 开始监听目标元素
    resizeObserver.observe(tabs.value);
  }
});
// 组件卸载时停止监听
onUnmounted(() => {
  localStorage.setItem('dosageTableHeight', dosageTableHeight.value.toString());
  if (resizeObserver) {
    resizeObserver.disconnect();
  }
});
</script>

<style lang="scss" scoped>
.loading {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  z-index: 9999;
  color: #fff;
  flex-wrap: wrap;
}

:deep(.n-tabs-tab__label) {
  font-size: 18px !important;
}

.tabs {
  display: flex;
  text-align: center;
  position: relative;
  margin-bottom: 10px;
  border-bottom: 1px solid #ddd;
  overflow-x: auto;
}

.tab-pane {
  flex: 1;
  font-size: 18px;
  padding-bottom: 10px;
}

.active-tabs-underline {
  position: absolute;
  left: 0;
  height: 2px;
  background-color: #5b67ff;
  bottom: 0px;
  transition: left 0.15s linear;
}

.active {
  color: #5b67ff;
}
</style>
