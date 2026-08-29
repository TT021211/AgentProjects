<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item label="型号">
          <n-input v-model:value="searchForm.model" type="text" placeholder="请输入型号" />
        </n-form-item>
        <n-form-item label="规格">
          <n-input v-model:value="searchForm.spec" type="text" placeholder="请输入规格" />
        </n-form-item>
        <!-- <n-form-item label="调换前规格">
          <n-input v-model:value="searchForm.moldBeforSpec" type="text" placeholder="请输入调换前规格" />
        </n-form-item> -->
        <n-form-item label="调换后规格">
          <n-input v-model:value="searchForm.moldAfterSpec" type="text" placeholder="请输入调换后规格" />
        </n-form-item>
        <n-form-item>
          <component :is="useSearchBtn(search, reset)"></component>
        </n-form-item>
      </n-form>
    </my-card>
    <my-card title="换规单列表">
      <template #right>
        <div>
          <n-button size="small" type="info" style="margin-right: 10px" @click="addData">
            <icon-mdi-add />
            新增
          </n-button>
          <CxColumns v-model:columns="columns" size="small" />
        </div>
      </template>
      <n-data-table :loading="loading" :columns="columns" :data="data"></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="init"></my-pagination>
    </my-card>

    <my-dialog :show="show" width="700px" :title="'新增换规单'" @cancel="cancel" @submit="submit">
      <template #content>
        <div>
          <n-form
            ref="formModel"
            style="width: 600px"
            label-placement="left"
            label-align="left"
            :label-width="100"
            :model="addForm"
            :rules="rules"
          >
            <n-grid x-gap="12" :cols="2">
              <n-form-item-grid-item label="型号" path="model">
                <n-input
                  v-model:value="addForm.model"
                  placeholder="请输入型号"
                  :style="{ width: formItemWidth }"
                ></n-input>
              </n-form-item-grid-item>
              <!-- <n-form-item-grid-item label="规格" path="spec">
                <n-input v-model:value="addForm.spec" placeholder="请输入规格" :style="{ width: formItemWidth }" />
              </n-form-item-grid-item> -->
              <n-form-item-grid-item label="数量(kg)" path="quantity">
                <n-input-number
                  v-model:value="addForm.quantity"
                  placeholder="请输入数量(kg)"
                  :style="{ width: formItemWidth }"
                />
              </n-form-item-grid-item>
              <n-form-item-grid-item label="LS机台" path="lsDeviceId">
                <n-select
                  v-model:value="addForm.lsDeviceId"
                  placeholder="请选择机台"
                  class="w-180px"
                  :options="lsMachineList"
                  :style="{ width: formItemWidth }"
                ></n-select>
              </n-form-item-grid-item>
              <!-- <n-form-item-grid-item label="调换前规格" path="moldBeforeId">
                <n-select
                  v-model:value="addForm.moldBeforeId"
                  placeholder="请选择调换前规格"
                  class="w-180px"
                  :options="specList"
                  :style="{ width: formItemWidth }"
                ></n-select>
              </n-form-item-grid-item> -->
              <n-form-item-grid-item label="调换后规格" path="moldId">
                <n-select
                  v-model:value="addForm.moldId"
                  placeholder="请选择调换后规格"
                  class="w-180px"
                  :options="specList"
                  :style="{ width: formItemWidth }"
                ></n-select>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="开始时间" path="startTime">
                <n-date-picker
                  v-model:formatted-value="addForm.startTime"
                  type="datetime"
                  :style="{ width: formItemWidth }"
                />
              </n-form-item-grid-item>
              <n-form-item-grid-item label="结束时间" path="endTime">
                <n-date-picker
                  v-model:formatted-value="addForm.endTime"
                  type="datetime"
                  :style="{ width: formItemWidth }"
                />
              </n-form-item-grid-item>
              <n-form-item-grid-item label="QB机台" path="qbDeviceId">
                <n-select
                  v-model:value="addForm.qbDeviceId"
                  placeholder="请选择机台"
                  class="w-180px"
                  :options="qbMachineList"
                  :style="{ width: formItemWidth }"
                ></n-select>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="备注" path="remark">
                <n-input v-model:value="addForm.remark" placeholder="请输入备注" :style="{ width: formItemWidth }" />
              </n-form-item-grid-item>
              <n-form-item-grid-item label="下单日期" path="orderDate">
                <n-date-picker
                  v-model:formatted-value="addForm.orderDate"
                  type="datetime"
                  :style="{ width: formItemWidth }"
                />
              </n-form-item-grid-item>
            </n-grid>
          </n-form>
        </div>
      </template>
    </my-dialog>
  </div>
</template>

<script setup lang="tsx">
defineOptions({
  name: 'BasicsMessage'
});
import type { Ref } from 'vue';
import { ref, onMounted } from 'vue';
import type { FormInst, DataTableColumns } from 'naive-ui';
import { useMessage } from 'naive-ui';
import { createRequiredFormRule } from '@/utils';
import { getWorkbenchWiredrawingList } from '@/service/api/md/workbench/wiredrawing';
import { getWorkbenchEnamellingList } from '@/service/api/md/workbench/enamelling';
import { useLoading } from '~/src/hooks';
import { useSearchBtn } from '~/src/hooks/common/useBtn';
import { getMoldLedgerList } from '~/src/service/api/moid/basicsMessage/index';
import { getMoldGaugeList, addMoldGaugeList } from '~/src/service/api/moid/gaugeChangeForm/index';
const searchForm = ref<gaugeChangeForm.searchForm>({
  model: '',
  spec: '',
  moldBeforSpec: '',
  moldAfterSpec: '',
  pageNum: 1,
  pageSize: 10,
  total: 0
});

const addForm = ref<gaugeChangeForm.addForm>({
  model: '',
  spec: '',
  quantity: null,
  lsDeviceId: null,
  qbDeviceId: null,
  startTime: null,
  endTime: null,
  remark: '',
  orderDate: null,
  moldBeforeId: null,
  moldId: null,
  moldBeforSpec: '',
  moldAfterSpec: ''
});

const { loading, startLoading, endLoading } = useLoading();

const show = ref<boolean>(false);

const formModel = ref<FormInst | null>(null);
const message = useMessage();
const addComplete = ref<boolean>(true);

const formItemWidth = ref<string>('220px');

const qbMachineList = ref<{ label: string; value: number }[]>([]);
const lsMachineList = ref<{ label: string; value: number }[]>([]);
const specList = ref<{ label: string; value: number }[]>([]);

const rules = {
  model: createRequiredFormRule('请输入型号'),
  quantity: createRequiredFormRule('请输入数量'),
  lsDeviceId: createRequiredFormRule('请选择LS机台'),
  // qbDeviceId: createRequiredFormRule('请选择QB机台'),
  // moldBeforeId: createRequiredFormRule('请选择调换前规格'),
  moldId: createRequiredFormRule('请选择调换后规格'),
  startTime: createRequiredFormRule('请选择开始时间'),
  endTime: createRequiredFormRule('请选择结束时间'),
  orderDate: createRequiredFormRule('请选择下单日期')
};

const data = ref<gaugeChangeForm.TableList[]>([
  {
    model: 'DX-2/145',
    moldAfterSpec: 0.12,
    quantity: 1000,
    lsName: '37',
    startDate: '2023-12-12',
    endDate: '2023-12-20',
    qbName: '24',
    remark: '',
    orderDate: '2023-12-12',
    moldGaugeId: 1,
    clientId: 1,
    lsdeviceid: 1,
    spec: '',
    moldBeforSpec: 1
  },
  {
    model: 'LS-2/145',
    moldAfterSpec: 0.07,
    quantity: 2000,
    lsName: '24',
    startDate: '2023-11-12',
    endDate: '2023-11-20',
    qbName: '11',
    remark: '',
    orderDate: '2023-11-12',
    moldGaugeId: 1,
    clientId: 1,
    lsdeviceid: 1,
    spec: '',
    moldBeforSpec: 1
  },
  {
    model: 'TH-2/145',
    moldAfterSpec: 0.12,
    quantity: 1000,
    lsName: '37',
    startDate: '2023-12-12',
    endDate: '2023-12-20',
    qbName: '24',
    remark: '',
    orderDate: '2023-12-12',
    moldGaugeId: 1,
    clientId: 1,
    lsdeviceid: 1,
    spec: '',
    moldBeforSpec: 1
  }
]);

const columns: Ref<DataTableColumns<gaugeChangeForm.TableList>> = ref([
  {
    title: '型号',
    key: 'model',
    align: 'center',
    width: 100
  },
  {
    title: '规格',
    key: 'moldAfterSpec',
    align: 'center',
    width: 100
  },
  {
    title: '数量(kg)',
    key: 'quantity',
    align: 'center',
    width: 100
  },
  {
    title: 'LS机台',
    key: 'lsName',
    align: 'center',
    width: 100
  },
  // {
  //   title: '调换前规格',
  //   key: 'moldBeforSpec',
  //   align: 'center',
  //   width: 100
  // },
  {
    title: '调换后规格',
    key: 'moldAfterSpec',
    align: 'center',
    width: 100
  },
  {
    title: '开始时间',
    key: 'startDate',
    align: 'center',
    width: 100
  },
  {
    title: '结束时间',
    key: 'endDate',
    align: 'center',
    width: 100
  },
  {
    title: 'DX机台',
    key: 'qbName',
    align: 'center',
    width: 100
  },
  {
    title: '备注',
    key: 'remark',
    align: 'center',
    width: 100
  },
  {
    title: '下单日期',
    key: 'orderDate',
    align: 'center',
    width: 100
  }
]);

function getOptionsList(
  func: any,
  list: { label: string; value: number }[],
  params: {
    label: string;
    value: string;
    isEnable?: number;
  }
) {
  func({ pageSize: 999, isEnable: params.isEnable }).then(res => {
    if (res.code === 200) {
      res.rows.forEach(item => {
        list.push({
          label: item[params.label],
          value: item[params.value]
        });
      });
    }
  });
}

function getList(): void {
  getOptionsList(getWorkbenchWiredrawingList, lsMachineList.value, {
    label: 'equipmentCode',
    value: 'id',
    isEnable: 1
  });
  getOptionsList(getWorkbenchEnamellingList, qbMachineList.value, { label: 'equipmentCode', value: 'id', isEnable: 1 });
  getOptionsList(getMoldLedgerList, specList.value, { label: 'moldAperture', value: 'moldId' });
}

function search() {
  init();
}
function reset() {
  searchForm.value = {
    model: '',
    spec: '',
    moldBeforSpec: '',
    moldAfterSpec: '',
    pageNum: 1,
    pageSize: 10,
    total: 0
  };
  init();
}

function addData() {
  show.value = true;
  addForm.value = {
    model: '',
    spec: '',
    quantity: null,
    lsDeviceId: null,
    qbDeviceId: null,
    startTime: null,
    endTime: null,
    remark: '',
    orderDate: null,
    moldBeforeId: null,
    moldId: null,
    moldBeforSpec: '',
    moldAfterSpec: ''
  };
}

function cancel(): void {
  show.value = false;
}

function submit(): void {
  if (!addComplete.value) return;
  if (!formModel.value) return;
  formModel.value.validate(errors => {
    if (!errors) {
      if (addForm.value.quantity !== null && addForm.value.quantity < 0) {
        message.warning('数量不能小于0');
        return;
      }
      // if (addForm.value.moldId === addForm.value.moldBeforeId) {
      //   message.warning('调换前规格不能和调换后规格一致');
      //   return;
      // }
      if (addForm.value.startTime && addForm.value.endTime) {
        const startTime = new Date(addForm.value.startTime).getTime();
        const endTime = new Date(addForm.value.endTime).getTime();
        if (startTime >= endTime) {
          message.warning('开始时间不能大于或等于结束时间');
          return;
        }
      }
      // const index = specList.value.findIndex(item => item.value === addForm.value.moldBeforeId);
      // if (index > -1) {
      //   addForm.value.moldBeforSpec = specList.value[index].label;
      // }
      const i = specList.value.findIndex(item => item.value === addForm.value.moldId);
      if (i > -1) {
        addForm.value.moldAfterSpec = specList.value[i].label;
      }
      addComplete.value = false;
      addMoldGaugeList(addForm.value)
        .then(res => {
          addComplete.value = true;
          if (res.code === 200) {
            message.success('添加成功');
            show.value = false;
            init();
          }
        })
        .catch(() => {
          addComplete.value = true;
        });
    }
  });
}

function init() {
  startLoading();
  getMoldGaugeList(searchForm.value).then(res => {
    endLoading();
    if (res.code === 200) {
      data.value = res.rows;
      searchForm.value.total = res.total;
    }
  });
}
onMounted(() => {
  init();
  getList();
});
</script>

<style scoped></style>
