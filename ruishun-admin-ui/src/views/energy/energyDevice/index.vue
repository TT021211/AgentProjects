<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item label="设备编码">
          <n-input v-model:value="searchForm.electricDeviceCode"></n-input>
        </n-form-item>
        <n-form-item label="安装位置">
          <n-select
            v-model:value="searchForm.locationId"
            :options="locationList"
            class="w-180px"
            placeholder="请选择安装位置"
            @update-value="(val,item : any) => {
										searchForm.location = item.label;
								}"
          ></n-select>
        </n-form-item>
        <n-form-item>
          <component :is="useSearchBtn(search, reset)"></component>
        </n-form-item>
      </n-form>
    </my-card>
    <my-card title="耗能设备列表">
      <template #right>
        <div>
          <component
            :is="
              customBtn({
                fun: () => {
                  addForm = JSON.parse(JSON.stringify(addFormEmpty));
                  showDialogMachine = true;
                },
                text: '新增设备',
                type: 'primary',
                size: 'small',
                icon: 'AddOutline'
              })
            "
          ></component>
        </div>
      </template>
      <n-data-table
        :loading="loading"
        :data="data"
        :columns="columns"
        :max-height="dataTableConfig.maxHeight"
        :scroll-x="dataTableConfig.scrollWidth(columns)"
      ></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="init"></my-pagination>
    </my-card>
    <my-dialog
      v-model:show="showDialogMachine"
      :title="'新增设备'"
      width="800px"
      @cancel="showDialogMachine = false"
      @submit="submit"
    >
      <template #content>
        <div class="w-full">
          <n-form ref="formRef" :model="addForm" label-width="80" label-placement="left" :rules="rules">
            <n-grid :cols="2" :x-gap="20">
              <n-form-item-grid-item label="设备名称" path="electricDeviceName">
                <n-input v-model:value="addForm.electricDeviceName" placeholder="请输入设备名称"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="设备编码">
                <n-input v-model:value="addForm.electricDeviceCode" placeholder="请输入设备名称"></n-input>
              </n-form-item-grid-item>

              <n-form-item-grid-item label="设备状态">
                <n-input v-model:value="addForm.status" placeholder="请输入设备名称"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="安装日期">
                <n-date-picker v-model:value="addForm.installationDate" type="datetime" class="w-full"></n-date-picker>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="安装位置" path="locationId">
                <n-select
                  v-model:value="addForm.locationId"
                  :options="locationList"
                  class="w-full"
                  placeholder="请选择安装位置"
                  @update-value="(val,item : any) => {
										addForm.location = item.label;
								}"
                ></n-select>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="是否总表" path="isMain">
                <n-select
                  v-model:value="addForm.isMain"
                  :options="isGeneralTableList"
                  class="w-full"
                  placeholder="请选择是或否"
                ></n-select>
              </n-form-item-grid-item>
              <n-form-item-grid-item v-if="addForm.isMain === 0" label="所属总表">
                <n-select
                  v-model:value="addForm.parentId"
                  :options="parentGeneralTableList"
                  class="w-full"
                  placeholder="请选择所属总表"
                  :loading="parentLoading"
                  @update-show="getParentGeneralTableList"
                ></n-select>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="制造商">
                <n-input v-model:value="addForm.manufacturer" placeholder="请输入设备名称"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="备注">
                <n-input v-model:value="addForm.remark" type="textarea" placeholder="请输入设备名称"></n-input>
              </n-form-item-grid-item>
            </n-grid>
          </n-form>
        </div>
      </template>
    </my-dialog>
    <my-dialog
      v-model:show="showDialog"
      width="1200px"
      title="用电详情"
      :show-cancel="false"
      @cancel="showDialog = false"
      @submit="showDialog = false"
    >
      <template #content>
        <div class="w-full">
          <n-form inline label-placement="left">
            <n-form-item label="查询条件">
              <n-select
                v-model:value="temp"
                :options="pickerOptions"
                placeholder="请选择"
                class="w-180px"
                @update:value="changeType"
              ></n-select>
            </n-form-item>
            <n-form-item label="日期">
              <n-date-picker
                v-model:value="tempDate"
                :type="dateType"
                class="w-180px"
                @update:value="changeDate"
              ></n-date-picker>
            </n-form-item>
          </n-form>
          <div ref="ElectricRef" class="w-1100px h-500px"></div>
        </div>
      </template>
    </my-dialog>
    <full-loading v-model:show="showLoading"></full-loading>
  </div>
</template>

<script setup lang="tsx">
import type { Ref } from 'vue';
import { ref, onMounted } from 'vue';
import { type DataTableColumns, type FormInst } from 'naive-ui';
import dayjs from 'dayjs';
import { useEcharts } from '@/composables';
// import type { ECOption } from '@/composables';
import FullLoading from '@/components/FullLoading.vue';
import { dataTableConfig } from '@/config/dataTableConfig';
import 'echarts/lib/component/dataZoom';
import { getMachineEnergyDetail } from '@/service/api/ecm/machineEnergy';
import { useSearchBtn, customBtn } from '@/hooks/common/useBtn';
import {
  getEnergyDeviceList,
  addEnergyDevice,
  delEnergyDevice,
  updateEnergyDevice
} from '@/service/api/ecm/energyDevice';
import { getDictDataList } from '@/service/api/system/dictData';
import { useLoading } from '~/src/hooks';
// import { format } from 'path';
const showLoading = ref<boolean>(false);
const temp = ref<string>('date');
const tempDate = ref<any>(new Date().getTime());
const locationList = ref<Array<{ label: string; value: string }>>([]);
const electId = ref<number | string>('');
const showDialog = ref(false);
const dateType = ref<any>('date');

const isGeneralTableList = ref<Array<{ label: string; value: number }>>([
  { label: '否', value: 0 },
  { label: '是', value: 1 }
]);

const parentGeneralTableList = ref<Array<{ label: string; value: number }>>([]);

const pickerOptions = [
  {
    label: '按天查询',
    value: 'date'
  },
  {
    label: '按月查询',
    value: 'month'
  },
  {
    label: '按年查询',
    value: 'year'
  }
];
function changeType(val: any) {
  // console.log(val);
  dateType.value = val;
  if (dateType.value && tempDate.value) {
    changeDate(tempDate.value);
  }
}
function getType(str: string) {
  switch (str) {
    case 'date':
      return 'YYYY-MM-DD';
    case 'month':
      return 'YYYY-MM';
    case 'year':
      return 'YYYY';
    default:
      return 'YYYY-MM-DD';
  }
}
// 获取24小时的数组以半个小时为分割
const getHours = (): string[] => {
  const hours: string[] = [];
  for (let i = 0; i <= 24; i += 0.5) {
    hours.push(`${i}时`);
  }
  return hours;
};
const getMonth = (): string[] => {
  const months: string[] = [];
  for (let i = 1; i <= 12; i += 1) {
    months.push(`${i}月`);
  }
  return months;
};
const getDay = (): string[] => {
  const days: string[] = [];
  for (let i = 1; i <= 31; i += 1) {
    days.push(`${i}日`);
  }
  return days;
};
function getYaxis(str: string) {
  switch (str) {
    case 'date':
      return getHours();
    case 'month':
      return getDay();
    case 'year':
      return getMonth();
    default:
      return getHours();
  }
}
const electricOption = ref({
  title: {
    text: `电表日能耗`,
    top: 'top',
    left: 'center'
  },
  toolbox: {
    show: true,
    feature: {
      saveAsImage: {}
      // magicType: { type: ['line', 'bar'] }
    }
  },
  tooltip: {
    trigger: 'axis'
  },
  legend: {
    data: ['电价', '能耗'],
    show: true,
    left: 'left',
    top: 'top'
  },
  xAxis: {
    type: 'category',
    data: getYaxis(dateType.value)
  },
  yAxis: [
    {
      name: '电价',
      type: 'value',
      axisLabel: {
        formatter: '{value} 元'
      },
      boundaryGap: false
    },
    {
      name: '能耗',
      type: 'value',
      axisLabel: {
        formatter: '{value} kwh'
      },
      boundaryGap: false
    }
  ],
  dataZoom: [
    { start: 0, end: 100 },
    {
      type: 'inside',
      start: 0,
      end: 100
    }
  ],
  series: [
    {
      name: '能耗',
      data: [],
      type: 'bar',
      label: {
        show: true,
        position: 'top'
      },
      yAxisIndex: 1,
      tooltip: {
        trigger: 'axis'
      }
    },
    {
      yAxisIndex: 0,
      name: '电价',
      data: [],
      type: 'line',
      // areaStyle: {},
      smooth: true,
      tooltip: {
        trigger: 'axis',
        axisPointer: {
          type: 'line'
        }
      }
    }
  ]
}) as any;
function changeDate(val: any) {
  showLoading.value = true;
  const formatString = getType(dateType.value);
  getMachineEnergyDetail(electId.value as string, dayjs(new Date(val)).format(formatString), dateType.value).then(
    res => {
      showLoading.value = false;
      // showDialog.value = true;
      // electData.value = res.data;
      if (!electricOption.value) return;
      // eslint-disable-next-line no-plusplus
      if (!electricOption.value.title) return;
      if (!electricOption.value.xAxis) return;
      electricOption.value.xAxis = {
        type: 'category',
        data: getYaxis(dateType.value)
      };
      electricOption.value.title = {
        text: `电表消耗`,
        top: 'top',
        left: 'center',
        subtext: `已经使用${res.data.consumptionValues[0]}(wh)`
      };
      electricOption.value.series = [
        {
          name: '能耗',
          showBackground: true,
          backgroundStyle: {
            color: 'rgba(216, 229, 247, 0.55)',
            borderRadius: [6, 6, 0, 0]
          },
          data: res.data.consumptionValues.slice(1).map(item => Number(item) / 1000),
          type: 'bar',
          showBackGround: true,
          yAxisIndex: 1,
          tooltip: {
            trigger: 'axis'
          }
        },
        {
          name: '电价',
          data: res.data.electricCharges.slice(1),
          type: 'line',
          yAxisIndex: 0,
          tooltip: {
            trigger: 'axis'
          },
          // areaStyle: {},
          smooth: true
        }
      ];
    }
  );
}
const formRef = ref<FormInst | null>(null);

const rules = {
  electricDeviceName: [
    {
      required: true,
      message: '请输入设备名称'
    }
  ],
  locationId: [
    {
      required: true,
      message: '请选择安装位置位置'
    }
  ],
  isMain: [
    {
      required: true,
      message: '请选择是或否'
    }
  ],
  parentId: [
    {
      required: true,
      message: '请选择所属总表'
    }
  ]
};
const parentLoading = ref<boolean>(false);
const { domRef: ElectricRef } = useEcharts(electricOption);
const showDialogMachine = ref(false);
const isLoading = ref(false);
const { loading, startLoading, endLoading } = useLoading();
// const parentId = ref<number | null>(null);
const searchForm = ref({
  electricDeviceCode: undefined,
  pageSize: 10,
  pageNum: 1,
  total: 0,
  location: null,
  locationId: null
});
const addFormEmpty = {
  electricDeviceName: '',
  electricDeviceCode: '',
  createTime: '',
  installationDate: undefined,
  status: undefined,
  location: '',
  manufacturer: '',
  remark: '',
  locationId: null,
  isMain: 0,
  parentId: null
};
const addForm = ref<Energy.energyDevice.AddForm>({
  electricDeviceName: '',
  electricDeviceCode: '',
  // createTime: '',
  installationDate: undefined,
  status: '',
  location: '',
  manufacturer: '',
  remark: '',
  locationId: null,
  isMain: 0,
  parentId: null
});
const data = ref<Energy.energyDevice.Columns[]>([]);
const columns: Ref<DataTableColumns<Energy.energyDevice.Columns>> = ref([
  {
    title: '设备名称',
    align: 'center',
    key: 'electricDeviceName',
    width: 120
  },
  {
    title: '设备编码',
    align: 'center',
    key: 'electricDeviceCode',
    width: 180
  },
  {
    title: '是否总表',
    align: 'center',
    key: '',
    width: 100,
    render: row => (
      <n-tag type={Number(row.isMain) === 1 ? 'success' : 'warning'}>{Number(row.isMain) === 1 ? '是' : '否'}</n-tag>
    )
  },
  // {
  //   title: '所属总表',
  //   key: 'parentName',
  //   width: 120
  // },
  {
    title: '安装位置',
    align: 'center',
    key: 'location',
    width: 140
  },
  {
    title: '制造商',
    align: 'center',
    key: 'manufacturer',
    width: 180
  },
  {
    title: '设备状态',
    align: 'center',
    key: 'status',
    width: 100
  },
  {
    title: '安装日期',
    align: 'center',
    key: 'installationDate',
    width: 180
  },
  {
    title: '备注',
    align: 'center',
    key: 'remark',
    width: 180
  },
  {
    title: '操作',
    align: 'center',
    key: 'action',
    fixed: 'right',
    width: 320,
    render: _row => {
      return [
        customBtn({
          fun: () => {
            delEnergyDevice(_row.electricDeviceId).then(res => {
              if (res.code !== 200) return;
              window.$message?.success('删除成功');
              init();
            });
          },
          text: '删除',
          type: 'error',
          size: 'small',
          icon: 'TrashOutline'
        }),
        customBtn({
          fun: async () => {
            showDialogMachine.value = true;
            addForm.value = { ..._row } as unknown as Energy.energyDevice.AddForm;
            addForm.value.locationId =
              locationList.value.find(item => item.label === addForm.value.location)?.value || null;
            addForm.value.installationDate = new Date(_row.installationDate as unknown as number) as unknown as number;
            addForm.value.isMain = Number(_row.isMain) === 1 ? Number(_row.isMain) : 0;
            await getParentGeneralTableList(true);
            addForm.value.parentId = Number(_row.parentId) || null;
          },
          text: '编辑',
          type: 'success',
          size: 'small',
          icon: 'CreateOutline'
        }),
        customBtn({
          fun: () => {
            if (isLoading.value) return;
            electId.value = _row.electricDeviceCode as string;
            const format = getType(temp.value);
            isLoading.value = true;
            getMachineEnergyDetail(electId.value, dayjs(new Date()).format(format), temp.value)
              .then(res => {
                // showDialog.value = true;
                // electData.value = res.data;
                isLoading.value = false;
                if (!electricOption.value) return;
                // eslint-disable-next-line no-plusplus
                if (!electricOption.value.title) return;
                electricOption.value.title = {
                  text: `每日电表消耗`,
                  top: 'top',
                  left: 'center',
                  subtext: `今日使用${res.data.consumptionValues[0] / 1000}(Kwh)`
                };
                electricOption.value.series = [
                  {
                    name: '能耗',
                    showBackground: true,
                    backgroundStyle: {
                      color: 'rgba(216, 229, 247, 0.55)',
                      borderRadius: [6, 6, 0, 0]
                    },
                    data: res.data.consumptionValues.slice(1).map(item => Number(item) / 1000),
                    type: 'bar',
                    showBackGround: true,
                    yAxisIndex: 1,
                    tooltip: {
                      trigger: 'axis'
                    }
                  },
                  {
                    name: '电价',
                    data: res.data.electricCharges.slice(1),
                    type: 'line',
                    yAxisIndex: 0,
                    tooltip: {
                      trigger: 'axis'
                    },
                    // areaStyle: {},
                    smooth: true
                  }
                ];
                // ElectricRef.value = useEcharts(electricOption);
                showDialog.value = true;
              })
              .catch(() => {
                isLoading.value = false;
              });
          },
          icon: 'EyeOutline',
          text: '查看电耗详情',
          size: 'small',
          type: 'primary'
        })
      ];
    }
  }
]);

function getParentGeneralTableList(e) {
  if (!e) return;
  parentLoading.value = true;
  parentGeneralTableList.value = [];
  getEnergyDeviceList({ isMain: 1 }).then(res => {
    parentLoading.value = false;
    if (res.code === 200) {
      res.rows.forEach(item => {
        parentGeneralTableList.value.push({
          label: item.electricDeviceName,
          value: item.electricDeviceId
        });
      });
    }
  });
}

function submit() {
  if (addForm.value.isMain === 0) {
    rules.parentId = [
      {
        required: true,
        message: '请选择所属总表'
      }
    ];
  } else {
    rules.parentId = [];
  }
  formRef.value?.validate(errors => {
    if (errors) return;
    if (addForm.value.electricDeviceId) {
      updateEnergyDevice(addForm.value).then(res => {
        if (res.code !== 200) return;
        window.$message?.success('修改成功');
        showDialogMachine.value = false;
        init();
      });
    } else {
      addEnergyDevice(addForm.value).then(res => {
        if (res.code !== 200) return;
        window.$message?.success('新增成功');
        showDialogMachine.value = false;
        init();
      });
    }
  });
}

function getList() {
  getDictDataList({ dictType: 'energy_name', pageSize: 999 }).then(res => {
    if (res.code === 200) {
      res.rows.forEach(item => {
        locationList.value.push({ label: item.dictLabel, value: item.dictValue });
      });
    }
  });
}

function search() {
  searchForm.value.pageNum = 1;
  init();
}
function reset() {
  searchForm.value = {
    electricDeviceCode: undefined,
    pageSize: 10,
    pageNum: 1,
    total: 0,
    location: null,
    locationId: null
  };
  init();
}
function init() {
  startLoading();
  getEnergyDeviceList(searchForm.value).then(res => {
    endLoading();
    if (res.code !== 200) return;
    data.value = res.rows;
    searchForm.value.total = res.total;
  });
}

onMounted(() => {
  init();
  getList();
});
</script>

<style scoped></style>
