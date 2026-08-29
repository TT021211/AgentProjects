<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item label="工单状态：">
          <n-select
            v-model:value="searchForm.status"
            placeholder="请选择工单状态"
            class="w-180px"
            :options="workOrderStatus"
          ></n-select>
        </n-form-item>
        <n-form-item label="机台">
          <n-select
            placeholder="请选择机台"
            :options="machineList"
            :filterable="true"
            class="w-160px"
            @update-value="(val, item: any) => {
            searchForm.equipmentCode = item.label;
          }"
          ></n-select>
        </n-form-item>
        <n-form-item label="订单类型">
          <n-select
            v-model:value="searchForm.orderType"
            placeholder="请选择订单类型"
            :options="orderTypeList"
            class="w-160px"
          ></n-select>
        </n-form-item>

        <n-form-item label="生产工单号">
          <n-input v-model:value="searchForm.workorderCode" type="text" placeholder="请输入生产工单号" />
        </n-form-item>

        <n-form-item label="产品型号">
          <n-input v-model:value="searchForm.model" type="text" placeholder="请输入产品型号" />
        </n-form-item>

        <n-form-item>
          <component
            :is="
              useSearchBtn(
                () => {
                  search();
                },
                () => {
                  reset();
                }
              )
            "
          />
        </n-form-item>
      </n-form>
      <n-form inline>
        <n-form-item label="时间">
          <n-date-picker v-model:value="range" type="datetimerange" clearable />
        </n-form-item>

        <n-form-item label="规格">
          <n-input v-model:value="searchForm.specification" type="text" placeholder="请输入规格" />
        </n-form-item>
      </n-form>
    </my-card>
    <my-card :title="title">
      <template #right>
        <div>
          <CxColumns v-model:columns="columns" size="small" />
        </div>
      </template>
      <n-data-table
        :max-height="400"
        :scroll-x="2400"
        :loading="loading"
        :columns="columns"
        :data="data"
      ></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="init()"></my-pagination>
    </my-card>

    <my-dialog
      v-model:show="showDialog"
      :title="craftTitle"
      width="1000px"
      @cancel="(showDialog = false), (formModel.productId = null)"
      @submit="submit"
    >
      <template #content>
        <div>
          <n-form style="width: 900px" :model="formModel" label-placement="left" label-width="90px">
            <n-grid :cols="2" :x-gap="20">
              <n-form-item-grid-item label="订单编号: ">
                <n-space>{{ formModel.orderCode }}</n-space>
              </n-form-item-grid-item>
              <n-form-item-grid-item v-if="formModel.productId !== null" label="型号: ">
                <n-space>{{ formModel.model }}</n-space>
              </n-form-item-grid-item>
              <n-form-item-grid-item v-if="formModel.productId !== null" label="规格: ">
                <n-space>{{ formModel.specification }}</n-space>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="重量: ">
                <n-space>{{ formModel.onlyReadWeight }}</n-space>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="绞线机台: ">
                <n-space>{{ formModel.qbEquipmentCode }}</n-space>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="位置: ">
                <n-space>{{ formModel.axleNumber }}</n-space>
              </n-form-item-grid-item>
            </n-grid>

            <div class="divider"></div>

            <n-form
              ref="lsFormModel"
              style="width: 900px"
              :model="drawBenchData"
              :rules="rules"
              label-placement="left"
              label-width="90px"
            >
              <n-grid :cols="2" :x-gap="20">
                <n-form-item-grid-item v-if="craftTitle !== '拉丝排产'" label="机台" path="equipmentId">
                  <n-space>
                    <!-- <n-input
                      v-model:value="enamelCoverData.equipmentId"
                      placeholder="请输入机台"
                      :style="{ width: formItemWidth }"
                    ></n-input> -->
                    <n-select
                      v-model:value="drawBenchData.equipmentId"
                      :options="drawBenchOptions"
                      :style="{ width: formItemWidth }"
                      placeholder="请选择机台"
                      :disabled="getDetailComplete"
                      filterable
                      @update:value="handleUpdateValue"
                    ></n-select>
                  </n-space>
                </n-form-item-grid-item>
                <n-form-item-grid-item v-else label="机台" path="equipmentIdList">
                  <n-space>
                    <!-- <n-input
                      v-model:value="enamelCoverData.equipmentId"
                      placeholder="请输入机台"
                      :style="{ width: formItemWidth }"
                    ></n-input> -->
                    <n-select
                      v-model:value="drawBenchData.equipmentIdList"
                      :options="drawBenchOptions"
                      :style="{ width: formItemWidth }"
                      placeholder="请选择机台"
                      :disabled="getDetailComplete"
                      filterable
                      multiple
                      @update:value="handleUpdateValue"
                    ></n-select>
                  </n-space>
                </n-form-item-grid-item>
                <n-form-item-grid-item v-show="craftTitle !== '拉丝排产'" label="位置">
                  <n-space vertical>
                    <!-- <n-input
                      v-model:value="axleNumberId"
                      placeholder="请输入位置"
                      :style="{ width: formItemWidth }"
                    ></n-input> -->
                    <n-select
                      v-model:value="axleNumberId"
                      :style="{ width: formItemWidth }"
                      placeholder="请选择位置"
                      :options="locationOptions"
                      :disabled="getDetailComplete"
                      filterable
                      @update-value="(val, item: any) => {
												drawBenchData.axleNumber = item.label;
												getDetailData()
											}"
                    ></n-select>
                  </n-space>
                </n-form-item-grid-item>
              </n-grid>
              <n-form-item v-show="drawBenchData.equipmentId && drawBenchData.axleNumber">
                <div style="overflow: auto; max-height: 350px">
                  <n-data-table :loading="loading" :columns="detailColumns" :data="detailData"></n-data-table>
                </div>
              </n-form-item>
              <n-grid :cols="2" :x-gap="20">
                <!-- <n-form-item-grid-item label="绞线机台">
                  <n-space>{{}}</n-space>
                  <n-input
                    v-model:value="formModel.qbEquipmentCode"
                    :style="{ width: formItemWidth }"
                    :disabled="true"
                  ></n-input>
                </n-form-item-grid-item> -->

                <n-form-item-grid-item label="开始时间" path="startTime">
                  <n-date-picker
                    v-model:formatted-value="drawBenchData.startTime"
                    type="datetime"
                    :style="{ width: formItemWidth }"
                  />
                </n-form-item-grid-item>
                <n-form-item-grid-item label="结束时间" path="endTime">
                  <n-date-picker
                    v-model:formatted-value="drawBenchData.endTime"
                    type="datetime"
                    :style="{ width: formItemWidth }"
                  />
                </n-form-item-grid-item>
                <n-form-item-grid-item label="关联产品">
                  <n-select
                    v-model:value="formModel.productId"
                    :options="productOptions"
                    filterable
                    :style="{ width: formItemWidth }"
                    placeholder="请选择关联的产品"
                    @update:value="
                      (val, item: any) => {
                        formModel.model = item.productSize;
                        formModel.specification = item.specification;
                      }
                    "
                  />
                </n-form-item-grid-item>
                <n-form-item-grid-item label="线盘" path="wireDisc">
                  <n-space vertical>
                    <n-select
                      v-model:value="drawBenchData.wireDiscId"
                      :style="{ width: formItemWidth }"
                      placeholder="请选择线盘"
                      filterable
                      :options="accessoryListOptions"
                      @update-value="(val, item: any) => {
												drawBenchData.wireDisc = item.label
												if(craftTitle === '拉丝排产') drawBenchData.artt6 = item.label
											}"
                    ></n-select>
                  </n-space>
                </n-form-item-grid-item>
                <n-form-item-grid-item label="盘数">
                  <n-input-number
                    v-model:value="drawBenchData.discNumber"
                    placeholder="请输入盘数"
                    :style="{ width: formItemWidth }"
                  />
                </n-form-item-grid-item>
                <n-form-item-grid-item label="特殊要求">
                  <n-input
                    v-model:value="drawBenchData.remark"
                    placeholder="请输入特殊要求"
                    :style="{ width: formItemWidth }"
                  ></n-input>
                </n-form-item-grid-item>

                <!-- <n-form-item-grid-item label="排产重量" path="weight">
          <n-input-number v-model:value="drawBenchData.weight" placeholder="请输入排产重量"
            :style="{ width: formItemWidth }" />
        </n-form-item-grid-item> -->
              </n-grid>
            </n-form>
          </n-form>
        </div>
      </template>
    </my-dialog>
    <my-dialog v-model:show="editShow" title="编辑" width="1200px" @cancel="editShow = false" @submit="submitEdit">
      <template #content>
        <div>
          <n-form
            ref="qbFormModel"
            style="width: 1100px"
            :rules="qbRules"
            :model="editData"
            label-placement="left"
            label-width="80px"
          >
            <n-grid :cols="2" :x-gap="20">
              <n-form-item-grid-item label="绞线机台" path="equipmentId">
                <n-space>
                  <n-select
                    v-model:value="editData.equipmentId"
                    :options="machineList"
                    :style="{ width: formItemWidth }"
                    placeholder="请选择机台"
                    :disabled="getDetailComplete || editData.attr1 === 'Y'"
                    filterable
                    @update:value="handleUpdateValue"
                  ></n-select>
                </n-space>
              </n-form-item-grid-item>
              <!-- <n-form-item-grid-item label="位置" path="axleNumber">
                <n-space vertical>
                  <n-select
                    v-model:value="axleNumberId"
                    :style="{ width: formItemWidth }"
                    placeholder="请选择位置"
                    :options="locationOptions"
                    :disabled="getDetailComplete"
                    multiple
                    filterable
                    @update-value="(val, item: any) => {
                        editData.axleNumber = null;
                        let axleNumber = '';
                        item.forEach(ele => {
                          axleNumber += ele.label + ','
                        })
                        if (axleNumber) {
                          editData.axleNumber = axleNumber.slice(0, -1)
                          getDetailList()
                        }
                      }"
                  ></n-select>
                </n-space>
              </n-form-item-grid-item> -->
            </n-grid>
            <n-form-item v-show="editData.equipmentId">
              <div style="overflow: auto; max-height: 350px">
                <n-data-table :loading="rowsLoading" :columns="detailColumns" :data="detailData"></n-data-table>
              </div>
            </n-form-item>
            <n-grid :cols="2" :x-gap="20">
              <n-form-item-grid-item label="线盘" path="wireDisc">
                <n-space vertical>
                  <n-select
                    v-model:value="editData.wireDisc"
                    :style="{ width: formItemWidth }"
                    placeholder="请选择线盘"
                    filterable
                    :disabled="editData.attr1 === 'Y'"
                    :options="accessoryListOptions"
                    @update-value="(val, item: any) => {
                        editData.wireDisc = item.label
                      }"
                  ></n-select>
                </n-space>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="盘数" path="discNumber">
                <n-input-number
                  v-model:value="editData.discNumber"
                  :style="{ width: formItemWidth }"
                  placeholder="请输入盘数"
                  :disabled="editData.attr1 === 'Y'"
                />
              </n-form-item-grid-item>
              <n-form-item-grid-item label="开始时间" path="startTime">
                <n-date-picker
                  v-model:formatted-value="editData.startTime"
                  value-format="yyyy-MM-dd"
                  type="date"
                  :disabled="editData.attr1 === 'Y'"
                  :style="{ width: formItemWidth }"
                />
              </n-form-item-grid-item>
              <n-form-item-grid-item label="结束时间" path="endTime">
                <n-date-picker
                  v-model:formatted-value="editData.endTime"
                  value-format="yyyy-MM-dd"
                  type="date"
                  :disabled="editData.attr1 === 'Y'"
                  :style="{ width: formItemWidth }"
                />
              </n-form-item-grid-item>
              <n-form-item-grid-item label="标准" path="standardValue">
                <n-space vertical>
                  <n-select
                    v-model:value="editData.standardValue"
                    :style="{ width: formItemWidth }"
                    placeholder="请选择标准"
                    filterable
                    :disabled="editData.attr1 === 'Y'"
                    :options="standardList"
                    @update-value="(val, item: any) => {
                        editData.standardName = item.label;
                      }"
                  ></n-select>
                </n-space>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="技术要求">
                <n-input
                  v-model:value="editData.artt6"
                  placeholder="请输入技术要求"
                  :style="{ width: formItemWidth }"
                  :disabled="editData.attr1 === 'Y'"
                ></n-input>
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
  name: 'PaintJobOrder'
});
import type { Ref } from 'vue';
import { ref, onMounted } from 'vue';
import { useDialog, useMessage } from 'naive-ui';
import type { DataTableColumns, FormInst } from 'naive-ui';
import { createRequiredFormRule } from '@/utils';
import { useSearchBtn } from '~/src/hooks/common/useBtn';
import { useLoading } from '~/src/hooks';
import {
  addEnamellingWorkorder,
  addTinningWorkorder,
  addWiredrawingWorkorder,
  addWireWorkorder,
  addStrandedwire
} from '~/src/service/api/plan/enamellingWorkOrder';
import { formatDate } from '~/src/utils/form/rule';
import { getStatusName } from '~/src/utils/common/workOrder';
import { getStrandedwire, putStrandedwire } from '~/src/service/api/plan/planStrandedWire';
// 拉丝
// import { getWorkbenchWiredrawingList } from '~/src/service/api/md/workbench/wiredrawing';
// 镀锡
// import { getWorkbenchEnamellingList } from '~/src/service/api/md/workbench/enamelling';
// 退火
// import { getWorkbenchEnamellingList as getWorkAnneal } from '~/src/service/api/md/workbench/workAnneal';
// 并丝
// import { getWorkbenchEnamellingList as getWorkFilament } from '~/src/service/api/md/workbench/workFilament';
// 绞线
import { getWorkbenchEnamellingList as getWorkStrandedWire } from '~/src/service/api/md/workbench/workStrandedWire';
import {
  getListByAnnealingId,
  getListByDoublewireId,
  getListByStrandedwireId,
  getListByTinningId
} from '~/src/service/api/plan/productOrder';
import { strandedwireWorkorderComplete } from '~/src/service/api/produre/workOrderArrange';
import { getDictDataList } from '~/src/service/api/system/dictData';
import { getAccessoryList } from '~/src/service/api/md/accessory';
// import { getProductByProductSize } from '~/src/service/api/md/list';
const { loading, startLoading, endLoading } = useLoading();

const title = ref<string>('绞线工单-全部');

const detailData = ref<paintJobOrder.tabelList[]>([]);
const getDetailComplete = ref<boolean>(false);
const detailColumns: Ref<DataTableColumns<paintJobOrder.tabelList>> = ref([
  {
    title: '工单号',
    align: 'center',
    width: 180,
    key: 'code'
  },
  {
    title: '机台',
    align: 'center',
    width: 100,
    key: 'equipmentCode'
  },
  {
    title: '位置',
    align: 'center',
    width: 100,
    key: 'axleNumber'
  },
  // {
  //   title: '油漆',
  //   width: 100,
  //   key: 'plantName'
  // },
  {
    title: '型号',
    align: 'center',
    width: 100,
    key: 'model'
  },
  {
    title: '产品规格',
    align: 'center',
    width: 100,
    key: 'specification'
  },
  {
    title: '创建时间',
    align: 'center',
    key: 'createTime',
    width: 180
  },
  {
    title: '重量(kg)',
    align: 'center',
    width: 100,
    key: 'weight'
  },

  {
    title: '盘数',
    align: 'center',
    width: 100,
    key: 'discNumber'
  },
  {
    title: '开始时间',
    align: 'center',
    width: 180,
    key: 'startTime'
  },
  {
    title: '结束时间',
    align: 'center',
    width: 180,
    key: 'endTime'
  },
  {
    title: '技术要求',
    align: 'center',
    width: 100,
    key: 'artt6',
    ellipsis: {
      tooltip: true
    }
  },
  {
    title: '备注',
    align: 'center',
    width: 100,
    key: 'remark',
    ellipsis: {
      tooltip: true
    }
  },
  {
    title: '状态',
    align: 'center',
    width: 100,
    key: 'status',
    render: row => {
      const statusInfo = getStatusName(row.status);
      return <n-tag type={statusInfo.bgColor}>{statusInfo.statusName}</n-tag>;
    }
  }
]);
const drawBenchData = ref<planWorkOrder.drawBenchData>({
  wireDiscId: null,
  wireDisc: null, // 线盘
  equipmentCode: '',
  equipmentId: null,
  startTime: null,
  endTime: null,
  axleNumber: null,
  discNumber: null,
  remark: '',
  equipmentIdList: null,
  weight: null,
  enamellingEquipmentId: null,
  enamellingEquipmentCode: null,
  artt5: null,
  artt6: null,
  status: null
});
const craftTitle = ref('');
const productOptions = ref<any[]>([]);

// 工单状态
const workOrderStatus = [
  { label: '全部', value: undefined },
  { label: '已排产', value: 0 },
  { label: '生产中', value: 1 },
  { label: '已完工', value: 2 },
  { label: '已作废', value: 3 }
];
const searchForm = ref<paintJobOrder.searchForm>({
  status: undefined,
  pageNum: 1,
  pageSize: 10,
  total: 0,
  equipmentId: null,
  orderType: null,
  model: '',
  workorderCode: '',
  specification: '',
  equipmentCode: ''
});

const formModel = ref<{
  qbEquipmentCode: string | null;
  orderCode: string | number | null; // 订单编号
  productModel: string | null; // 产品型号
  productSpecification: string | number | null; // 产品规格
  orderType: string | number | null; // 订单类型
  customerName: string | null; // 客户
  workorderCode: string | null; // 生产工单
  workorderId: number | null;
  model: string | null; // 产品型号
  specification: string | null; // 产品规格
  measureId: number | null;
  measureName: string | null;
  productId: string | null;
  onlyReadWeight: string | number | null; // 只读重量
  axleNumber: string | null;
}>({
  qbEquipmentCode: '',
  orderCode: null, // 订单编号
  productModel: null, // 产品型号
  productSpecification: null, // 产品规格
  orderType: null, // 订单类型
  customerName: null, // 客户
  workorderCode: null, // 生产工单
  workorderId: null,
  model: null, // 产品型号
  specification: null, // 产品规格
  measureId: null,
  measureName: null,
  productId: null,
  onlyReadWeight: null,
  axleNumber: null
});

const rules = {
  wireDisc: createRequiredFormRule('请选择线盘'),
  equipmentIdList: createRequiredFormRule('请选择机台'),
  equipmentId: createRequiredFormRule('请选择机台'),
  startTime: createRequiredFormRule('请选择开始时间'),
  endTime: createRequiredFormRule('请选择结束时间')
};
const lsFormModel = ref<FormInst | null>(null);
const drawBenchOptions = ref<planWorkOrder.SelectMixedOption[]>([]);
const message = useMessage();
const formItemWidth = ref<string>('240px');
const range = ref<[number, number] | null>(null);
const showDialog = ref<boolean>(false);
// const drawBenchOptionsData = ref<any[]>([]);
const machineList = ref<any[]>([]);
const orderTypeList = ref<{ label: string; value: number }[]>([
  { label: '客户订单', value: 0 },
  { label: '备库订单', value: 1 }
]);

const data = ref<paintJobOrder.tabelList[]>([]);
const axleNumberId = ref<any>(null);
const locationOptions = ref<planWorkOrder.SelectMixedOption[]>([]);

// 编辑
const editShow = ref<boolean>(false);
const qbFormModel = ref<FormInst | null>(null);
const editData = ref<paintJobOrder.tabelList | any>({});
const rowsLoading = ref<boolean>(false);
const accessoryListOptions = ref<planWorkOrder.SelectMixedOption[]>([]);
const standardList = ref<planWorkOrder.SelectMixedOption[]>([]);
const qbRules = {
  equipmentId: createRequiredFormRule('请选择机台'),
  axleNumber: createRequiredFormRule('请选择位置'),
  discNumber: createRequiredFormRule('请输入盘数'),
  wireDisc: createRequiredFormRule('请选择线盘'),
  startTime: createRequiredFormRule('请选择开始时间'),
  endTime: createRequiredFormRule('请选择结束时间')
};
function edit(row: paintJobOrder.tabelList) {
  editData.value = row;
  editShow.value = true;
  getDetailList();
}
// 查询绞线工单
function getDetailList() {
  getDetailComplete.value = true;
  startLoading();
  detailData.value = [];
  if (editData.value.equipmentId === null) return;
  const params = { equipmentCode: editData.value.equipmentCode, statusList: [0, 1] };
  getStrandedwire(params).then(res => {
    endLoading();
    getDetailComplete.value = false;
    if (res.code === 200) {
      detailData.value = res.rows.map(n => {
        n.code = n.strandedwireCode;
        return n;
      });
    }
  });
}
// 编辑提交
function submitEdit() {
  qbFormModel.value?.validate(errors => {
    if (!errors) {
      putStrandedwire(editData.value).then(res => {
        if (res.code === 200) {
          init();
          editShow.value = false;
          message.success('修改成功');
        }
      });
    }
  });
}
// 根据位置查询工单
function getDetailData() {
  getDetailComplete.value = true;
  startLoading();
  if (drawBenchData.value.equipmentId === null || drawBenchData.value.axleNumber === null) return;
  const params = { equipmentId: drawBenchData.value.equipmentId, axleNumber: drawBenchData.value.axleNumber };
  getDetailDataById(params);
}
// 分开查询各个机台
function getDetailDataById(params): void {
  detailData.value = [];
  switch (craftTitle.value) {
    case '退火排产':
      getListByAnnealingId(params).then(res => {
        endLoading();
        getDetailComplete.value = false;
        if (res.code === 200) {
          detailData.value = res.data.map(n => {
            n.code = n.annealingCode;
            return n;
          });
        }
      });
      break;
    case '镀锡排产':
      getListByTinningId(params).then(res => {
        endLoading();
        getDetailComplete.value = false;
        if (res.code === 200) {
          detailData.value = res.data.map(n => {
            n.code = n.tinningCode;
            return n;
          });
        }
      });
      break;
    case '并丝排产':
      getListByDoublewireId(params).then(res => {
        endLoading();
        getDetailComplete.value = false;
        if (res.code === 200) {
          detailData.value = res.data.map(n => {
            n.code = n.doublewireCode;
            return n;
          });
        }
      });
      break;
    case '绞线排产':
      getListByStrandedwireId(params).then(res => {
        endLoading();
        getDetailComplete.value = false;
        if (res.code === 200) {
          detailData.value = res.data.map(n => {
            n.code = n.strandedwireCode;
            return n;
          });
        }
      });
      break;
    default:
      break;
  }
}

const columns: Ref<DataTableColumns<paintJobOrder.tabelList>> = ref([
  {
    title: '序号',
    key: 'index',
    align: 'center',
    width: 70,
    render: (_row, index) => (searchForm.value.pageNum - 1) * searchForm.value.pageSize + index + 1
  },
  {
    title: '创建时间',
    align: 'center',
    width: 180,
    key: 'createTime'
  },
  {
    title: '订单类型',
    width: 100,
    key: 'orderType',
    render: row => {
      return <n-space>{row.orderType === '0' ? '客户订单' : '备库订单'}</n-space>;
    }
  },
  {
    title: '客户',
    align: 'center',
    width: 100,
    key: 'customerName'
  },
  // {
  //   title: '销售订单编号',
  //   width: 120,
  //   key: 'orderCode'
  // },
  // {
  //   title: '生产工单号',
  //   width: 100,
  //   key: 'workorderCode'
  // },
  {
    title: '绞线工单号',
    align: 'center',
    width: 180,
    key: 'strandedwireCode'
  },
  {
    title: '绞线机台',
    align: 'center',
    width: 100,
    key: 'equipmentCode'
  },
  {
    title: '完成度',
    align: 'center',
    width: 100,
    key: 'degreeOfCompletion',
    render: (row: any) => {
      return (
        <n-progress
          type="line"
          percentage={row.degreeOfCompletion * 100}
          indicator-placement="inside"
          processing
        ></n-progress>
      );
    }
  },
  {
    title: '位置',
    align: 'center',
    width: 100,
    key: 'axleNumber'
  },
  {
    title: '线盘',
    align: 'center',
    width: 80,
    key: 'wireDisc'
  },
  // {
  //   title: '锡块',
  //   width: 100,
  //   key: 'plantName'
  // },
  {
    title: '型号',
    align: 'center',
    width: 100,
    key: 'model'
  },
  {
    title: '产品规格',
    align: 'center',
    width: 100,
    key: 'specification'
  },

  {
    title: '重量(kg)',
    width: 100,
    align: 'center',
    key: 'weight'
  },
  {
    title: '已完成重量',
    align: 'center',
    width: 100,
    key: 'wightCompletion'
  },
  {
    title: '盘数',
    align: 'center',
    width: 100,
    key: 'discNumber'
  },
  {
    title: '开始时间',
    align: 'center',
    width: 180,
    key: 'startTime'
  },
  {
    title: '结束时间',
    align: 'center',
    width: 180,
    key: 'endTime'
  },
  // {
  //   title: '关联工艺路线',
  //   width: 180,
  //   key: 'artt5'
  // },
  {
    title: '技术要求',
    align: 'center',
    width: 100,
    key: 'artt6',
    ellipsis: {
      tooltip: true
    }
  },
  {
    title: '备注',
    align: 'center',
    width: 100,
    key: 'remark',
    ellipsis: {
      tooltip: true
    }
  },
  {
    title: '状态',
    key: 'status',
    fixed: 'right',
    width: 80,
    render: row => {
      const statusInfo = getStatusName(row.status);
      return <n-tag type={statusInfo.bgColor}>{statusInfo.statusName}</n-tag>;
    }
  },
  {
    title: '操作',
    key: 'action',
    fixed: 'right',
    align: 'center',
    titleAlign: 'center',
    width: 200,
    render: row => {
      // const name = row.artt5.split('-');
      // const routeName = name[name.length - 1];
      return (
        <div>
          {/* <n-button
            type="info"
            onClick={() => drawbenchScheduling(row, routeName)}
            v-show={row.attr1 === 'N' && row.status === '0'}
          >
            {routeName}排产
          </n-button>
          <span>&nbsp;&nbsp;&nbsp;&nbsp;</span> */}
          <n-button type="warning" onClick={() => edit(row)} v-show={row.status === '0'}>
            编辑
          </n-button>
          <span>&nbsp;&nbsp;&nbsp;&nbsp;</span>
          <n-button type="success" v-show={row.status === '1'} onClick={() => completeWorkOrder(row.id)}>
            工单完工
          </n-button>
        </div>
      );
    }
  }
]);
const dialog = useDialog();
function completeWorkOrder(id: number) {
  const d = dialog.warning({
    title: '提示',
    content: '确认要工单完工吗?',
    positiveText: '确认',
    negativeText: '取消',
    onPositiveClick: () => {
      d.loading = true;
      return new Promise(resolve => {
        sleep2(id).then((res: any) => {
          if (res.code === 200) {
            message.success('工单完工成功');
            init();
            resolve(res);
          }
        });
      });
    }
  });
}
function sleep2(id: number): any {
  return new Promise(resolve => {
    strandedwireWorkorderComplete(id, '2').then(res => {
      resolve(res);
    });
  });
}
const putList = ref({
  attr1: ''
});
// 选择机台
function handleUpdateValue(_value: number, option: planWorkOrder.SelectMixedOption) {
  drawBenchData.value.equipmentCode = option.label;
  editData.value = option;
  detailData.value = [];
  getDetailList();
}
// 查询各个工艺的产品
// function getProduct(str) {
//   getProductByProductSize(str).then(res => {
//     productOptions.value = [];
//     res.data.forEach((item: any) => {
//       productOptions.value.push({
//         label: `${item.specification} (${item.productSize})`,
//         productSize: item.productSize,
//         specification: item.specification.split(',')[0],
//         value: item.id
//       });
//     });
//   });
// }
// 查询机台
// function selBrenchList(func) {
//   drawBenchOptions.value = [];
//   rowsWorkbenchEnamellingList.value = [];
//   func({ pageSize: 999 }).then(res => {
//     rowsWorkbenchEnamellingList.value = res.rows;
//     res.rows.forEach((item: { equipmentCode: any; id: any }) => {
//       drawBenchOptions.value.push({
//         label: item.equipmentCode,
//         value: item.id
//       });
//     });
//   });
// }
// 查询工艺机台
// function selCraftMachine(type) {
//   switch (type) {
//     case 1:
//       selBrenchList(getWorkbenchWiredrawingList);
//       getProduct('?productSize=LTX');
//       break;
//     case 2:
//       selBrenchList(getWorkAnneal);
//       getProduct('?productSize=LTX');
//       break;
//     case 3:
//       selBrenchList(getWorkbenchEnamellingList);
//       getProduct('?productSize=DXX');
//       break;
//     case 4:
//       selBrenchList(getWorkFilament);
//       getProduct('?productSize=DXBS&productSize=LTBS');
//       break;
//     case 5:
//       selBrenchList(getWorkStrandedWire);
//       getProduct('?productSize=DXJX&productSize=LTJX');
//       break;
//     default:
//       break;
//   }
// }
// function drawbenchScheduling(row: paintJobOrder.tabelList, craft) {
//   putList.value = { ...row };
//   craftTitle.value = `${craft}排产`;
//   switch (craft) {
//     case '拉丝':
//       selCraftMachine(1);
//       break;
//     case '退火':
//       selCraftMachine(2);
//       break;
//     case '镀锡':
//       selCraftMachine(3);
//       break;
//     case '并丝':
//       selCraftMachine(4);
//       break;
//     case '绞线':
//       selCraftMachine(5);
//       break;
//     default:
//       break;
//   }
//   showDialog.value = true;
//   for (const key in drawBenchData.value) {
//     if (drawBenchData.value !== null) {
//       drawBenchData.value[key] = null;
//     }
//   }

//   for (const key in formModel.value) {
//     if (row[key] !== null && key !== 'productId') {
//       formModel.value[key] = row[key];
//     }
//   }
//   formModel.value.onlyReadWeight = row.weight;
//   formModel.value.qbEquipmentCode = row.equipmentCode;
//   drawBenchData.value.enamellingEquipmentCode = row.equipmentCode;
//   drawBenchData.value.enamellingEquipmentId = row.equipmentId;
//   drawBenchData.value.weight = row.weight;
//   const routeList = row.artt5.split('-');
//   routeList.pop();
//   drawBenchData.value.artt5 = routeList.join('-');
//   drawBenchData.value.status = row.status;
//   getDetailComplete.value = false;
//   axleNumberId.value = null;

//   const spec = Number(row.specification);
//   drawBenchOptions.value = [];
//   drawBenchOptionsData.value.forEach(item => {
//     const upper = item.productionUpperLimit;
//     const lower = item.productionLowerLimit;
//     if (spec) {
//       if ((spec < upper || spec === upper) && (spec > lower || spec === lower)) {
//         drawBenchOptions.value.push({
//           label: item.equipmentCode,
//           value: item.id
//         });
//       }
//     }
//   });
// }

function submit() {
  if (!lsFormModel.value) return;
  lsFormModel.value.validate(async errors => {
    if (!errors) {
      if (drawBenchData.value.discNumber !== null) {
        if (drawBenchData.value.discNumber < 0 || drawBenchData.value.discNumber === 0) {
          message.warning('盘数不能小于或等于0');
          return;
        }
      }

      if (drawBenchData.value.startTime && drawBenchData.value.endTime) {
        const startTime = new Date(drawBenchData.value.startTime).getTime();
        const endTime = new Date(drawBenchData.value.endTime).getTime();
        if (startTime > endTime) {
          message.warning('开始时间不能大于结束时间');
          return;
        }
      }

      if (drawBenchData.value.equipmentIdList !== null) {
        drawBenchData.value.equipmentCode = '';
        drawBenchData.value.equipmentId = '';
        drawBenchData.value.equipmentIdList.forEach(item => {
          const index = drawBenchOptions.value.findIndex(ele => ele.value === item);
          drawBenchData.value.equipmentCode += `${drawBenchOptions.value[index].label},`;
          drawBenchData.value.equipmentId += `${item},`;
        });
        drawBenchData.value.equipmentCode = drawBenchData.value.equipmentCode.slice(0, -1);
        drawBenchData.value.equipmentId = drawBenchData.value.equipmentId.slice(0, -1);
      }
      switch (craftTitle.value) {
        case '拉丝排产':
          submitRepetitiom(drawBenchData, formModel, addWiredrawingWorkorder);
          break;
        case '退火排产':
          submitRepetitiom(drawBenchData, formModel, addEnamellingWorkorder);
          break;
        case '镀锡排产':
          submitRepetitiom(drawBenchData, formModel, addTinningWorkorder);
          break;
        case '并丝排产':
          submitRepetitiom(drawBenchData, formModel, addWireWorkorder);
          break;
        case '绞线排产':
          submitRepetitiom(drawBenchData, formModel, addStrandedwire);
          break;
        default:
          break;
      }
    }
  });
}

function submitRepetitiom(submitForModel: any, obj: any, func: any) {
  func({
    ...submitForModel.value,
    ...obj.value,
    isDeleted: 0,
    version: 0,
    degreeOfCompletion: 0,
    wightCompletion: 0,
    attr1: 'N',
    sort: 0,
    insertStatus: 0
  }).then(res => {
    if (res.code === 200) {
      message.success('排产成功');
      putList.value.attr1 = 'Y';
      putStrandedwire({ ...putList.value }).then(rs => {
        if (rs.code === 200) {
          init();
          showDialog.value = false;
        }
      });
    } else {
      message.error('排产失败');
    }
  });
}
function search() {
  if (searchForm.value.status !== undefined) {
    title.value = `绞线工单-${workOrderStatus[searchForm.value.status].label}`;
  } else {
    title.value = `绞线工单-全部`;
  }
  if (range.value !== null) {
    searchForm.value['params[beginTime]'] = formatDate(new Date(range.value[0]), 'yyyy-MM-dd');
    searchForm.value['params[endTime]'] = formatDate(new Date(range.value[1]), 'yyyy-MM-dd');
  } else {
    searchForm.value['params[beginTime]'] = null;
    searchForm.value['params[endTime]'] = null;
  }
  // 不需要机台id查询
  init();
}

function reset() {
  searchForm.value = {
    status: undefined,
    pageNum: 1,
    pageSize: 10,
    total: 0,
    equipmentId: null,
    orderType: null,
    model: '',
    workorderCode: '',
    specification: '',
    equipmentCode: ''
  };
  if (searchForm.value.status !== undefined) {
    title.value = `绞线工单-${workOrderStatus[searchForm.value.status].label}`;
  } else {
    title.value = `绞线工单-全部`;
  }
  range.value = null;
  search();
}

function getList() {
  getWorkStrandedWire({ pageSize: 999 }).then(res => {
    res.rows.forEach((item: { equipmentCode: any; id: any }) => {
      machineList.value.push({
        label: item.equipmentCode,
        value: item.id,
        ...item
      });
    });
  });

  getAccessoryList({
    itemTypeId: 24,
    total: 2,
    pageSize: 999
  }).then(res => {
    res.rows.forEach((item: { model: any; id: any }) => {
      accessoryListOptions.value.push({
        label: item.model,
        value: item.id
      });
    });
  });

  getDictDataList({ pageSize: 999, dictType: 'technology_client_norm' }).then(res => {
    if (res.code === 200) {
      res.rows.forEach(item => {
        standardList.value.push({
          label: item.dictLabel,
          value: Number(item.dictValue)
        });
      });
    }
  });
}

function init() {
  startLoading();
  getStrandedwire({ ...searchForm.value }).then(res => {
    if (res.code === 200) {
      searchForm.value.total = res.total;
      data.value = res.rows;
    }
  });
  endLoading();
}
onMounted(() => {
  init();
  getList();
});
</script>

<style scoped>
:deep(.n-input__input-el) {
  color: #000 !important;
  padding-right: 40px;
}

.divider {
  height: 1px;
  background-color: #ddd;
  margin-bottom: 30px;
}
</style>
