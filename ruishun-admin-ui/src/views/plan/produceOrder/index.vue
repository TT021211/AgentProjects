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
        <n-form-item label="规格">
          <n-input v-model:value="searchForm.productSpecification" type="text" placeholder="请输入规格" />
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
        :scroll-x="2200"
        :loading="loading"
        :columns="columns"
        :data="data"
      ></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="init()"></my-pagination>
    </my-card>
    <my-dialog v-model:show="showDialog" title="排产计划" width="1000px" @cancel="cancel" @submit="submit">
      <template #content>
        <div>
          <n-form style="width: 900px" :model="formModel" label-placement="left" label-width="auto">
            <n-grid :cols="3" :x-gap="20">
              <n-form-item-grid-item label="订单编号:">
                <n-space>{{ formModel.orderCode }}</n-space>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="交付时间:">
                <n-space>{{ formModel.payTime }}</n-space>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="重量:">
                <n-space>{{ formModel.onlyReadWeight }}</n-space>
              </n-form-item-grid-item>
            </n-grid>
            <n-tabs v-model:value="craftName" type="line" animated @click="getCraftName">
              <n-tab-pane
                v-for="(itemRoute, index) in routeNameList"
                :key="itemRoute.id"
                :name="itemRoute.name"
                :tab="itemRoute.name"
              >
                <n-form
                  :ref="
                    el => {
                      refList[index] = el;
                    }
                  "
                  style="width: 900px"
                  :rules="itemRoute.Rules"
                  :model="itemRoute"
                  label-placement="left"
                  label-width="70px"
                >
                  <n-grid v-if="craftName !== '拉丝'" :cols="3" :x-gap="20">
                    <n-form-item-grid-item label="机台:" path="equipmentId">
                      <!-- <n-input
                      v-model:value="enamelCoverData.equipmentId"
                      placeholder="请输入机台"
                      :style="{ width: formItemWidth }"
                    ></n-input> -->
                      <n-select
                        v-model:value="itemRoute.equipmentId"
                        :options="drillingCrewoptions"
                        :style="{ width: formItemWidth }"
                        placeholder="请选择机台"
                        :disabled="getDetailComplete"
                        filterable
                        @update:value="(value: number, option: planWorkOrder.SelectMixedOption)=>{
														itemRoute.equipmentCode = option.label;
														itemRoute.deviceId = option.deviceId
														if (itemRoute.name !== '拉丝') {
															const index: number = rowsWorkbenchEnamellingList.findIndex(f => f.id === value);
															if (index === -1) return;
															const axleNumberList = rowsWorkbenchEnamellingList[index].axleNumber.split(/[，,]/g);
															locationOptions = [];
															itemRoute.axleNumber = null;
															itemRoute.axleNumberId = null;
															axleNumberList.forEach((j: any,idx) => {
																locationOptions.push({
																	value: idx,
																	label: j
																});
															})
														}
														if(itemRoute.name === '绞线'){
															getStrandedwire({pageSize:999,equipmentCode: option.label,statusList:[0,1]}).then(res=>{
																jxDetailData = [];
																endLoading();
																getDetailComplete = false;
																if (res.code === 200) {
																	jxDetailData = res.rows.map(n => {
																		n.code = n.strandedwireCode;
																		return n;
																	});
																}
															})
														}
													}"
                      ></n-select>
                    </n-form-item-grid-item>
                    <n-form-item-grid-item v-if="craftName !== '绞线'" label="位置:" path="axleNumberId">
                      <n-select
                        v-model:value="itemRoute.axleNumberId"
                        :style="{ width: formItemWidth }"
                        placeholder="请选择位置"
                        :options="locationOptions"
                        :disabled="getDetailComplete"
                        multiple
                        filterable
                        @update-value="(_val, item: any) => {
														itemRoute.axleNumber = ''
														// 多选
														item.forEach(n=>{
															itemRoute.axleNumber += n.label
															itemRoute.axleNumber += ','
														})
														itemRoute.axleNumber = itemRoute.axleNumber.substring(0,itemRoute.axleNumber.length-1)
														itemRoute.axleNumber = itemRoute.axleNumber.length === 0 ? null:itemRoute.axleNumber
														// console.log(itemRoute.axleNumber)
														// 单选
														// itemRoute.axleNumber = item.label;
														// getDetailComplete = true;
														startLoading();
														if (itemRoute.equipmentId === null || itemRoute.axleNumber === null) return;
														const params = { equipmentId: itemRoute.equipmentId, axleNumber: itemRoute.axleNumber };
														console.log(itemRoute.axleNumber,'aaaa')
														getDetailDataById(params);
													}"
                      ></n-select>
                    </n-form-item-grid-item>
                  </n-grid>

                  <n-grid v-else :cols="2" :x-gap="20">
                    <n-form-item-grid-item label="机台:" path="equipmentIdList">
                      <n-select
                        v-model:value="itemRoute.equipmentIdList"
                        :options="drillingCrewoptions"
                        :style="{ width: '220px' }"
                        placeholder="请选择机台"
                        :disabled="getDetailComplete"
                        filterable
                        multiple
                        @update:value="(value: number, option: any)=>{
														itemRoute.equipmentIdList = value
														itemRoute.equipmentCode = option.label;
														itemRoute.deviceId = option.map(n=>n.deviceId).join(',');
													}"
                      ></n-select>
                    </n-form-item-grid-item>
                  </n-grid>
                  <n-form-item v-show="itemRoute.equipmentId && itemRoute.axleNumber">
                    <div style="overflow: auto; max-height: 350px">
                      <n-data-table
                        v-if="craftName === '退火'"
                        :loading="loading"
                        :columns="detailColumns"
                        :data="thDetailData"
                      ></n-data-table>
                      <n-data-table
                        v-if="craftName === '镀锡'"
                        :loading="loading"
                        :columns="detailColumns"
                        :data="dxDetailData"
                      ></n-data-table>
                      <n-data-table
                        v-if="craftName === '镀锡并丝'"
                        :loading="loading"
                        :columns="detailColumns"
                        :data="bsDetailData"
                      ></n-data-table>
                    </div>
                  </n-form-item>
                  <n-form-item v-if="craftName === '绞线' && itemRoute.equipmentId">
                    <div style="overflow: auto; max-height: 350px">
                      <n-data-table :loading="loading" :columns="detailColumns" :data="jxDetailData"></n-data-table>
                    </div>
                  </n-form-item>
                  <n-grid :cols="3" :x-gap="20">
                    <n-form-item-grid-item label="关联产品:" path="productId">
                      <n-select
                        v-model:value="itemRoute.productId"
                        :options="productOptions"
                        filterable
                        :disabled="itemRoute.disabled"
                        :style="{ width: formItemWidth }"
                        placeholder="请选择关联的产品"
                        @update:value="
													(val, item: any) => {
														itemRoute.batNumber = item.batchNumber;
														itemRoute.model = item.productSize;
														itemRoute.specification = item.specification;
													}
												"
                      />
                    </n-form-item-grid-item>
                    <n-form-item-grid-item v-if="itemRoute.productId !== null" label="批号:" path="productId">
                      <n-input
                        v-model:value="itemRoute.batNumber"
                        :disabled="true"
                        placeholder="请选择关联产品"
                        :style="{ width: formItemWidth }"
                      ></n-input>
                    </n-form-item-grid-item>
                    <n-form-item-grid-item v-if="itemRoute.productId !== null" label="规格:" path="productId">
                      <n-input
                        v-model:value="itemRoute.specification"
                        :disabled="true"
                        placeholder="请选择关联产品"
                        :style="{ width: formItemWidth }"
                      ></n-input>
                    </n-form-item-grid-item>
                    <n-form-item-grid-item label="开始时间:" path="startTime">
                      <n-date-picker
                        v-model:formatted-value="itemRoute.startTime"
                        type="datetime"
                        :style="{ width: formItemWidth }"
                      />
                    </n-form-item-grid-item>
                    <n-form-item-grid-item label="结束时间:" path="endTime">
                      <n-date-picker
                        v-model:formatted-value="itemRoute.endTime"
                        type="datetime"
                        :style="{ width: formItemWidth }"
                      />
                    </n-form-item-grid-item>
                    <n-form-item-grid-item label="线盘:" path="wireDisc">
                      <n-select
                        v-model:value="itemRoute.wireDiscId"
                        :style="{ width: formItemWidth }"
                        placeholder="请选择线盘"
                        filterable
                        :options="accessoryListOptions"
                        @update-value="(val, item: any) => {
														itemRoute.wireDisc = item.label
													}"
                      ></n-select>
                    </n-form-item-grid-item>
                    <!-- <n-form-item-grid-item label="线盘数量" path="wireDiscNumber">
                  <n-input-number
                    v-model:value="enamelCoverData.wireDiscNumber"
                    :style="{ width: formItemWidth }"
                    placeholder="请输入线盘数量"
                  />
                </n-form-item-grid-item> -->
                    <n-form-item-grid-item label="盘数:">
                      <n-input-number
                        v-model:value="itemRoute.discNumber"
                        :style="{ width: formItemWidth }"
                        placeholder="请输入盘数"
                      />
                    </n-form-item-grid-item>
                    <n-form-item-grid-item label="标准:" path="standardValue">
                      <n-select
                        v-model:value="itemRoute.standardValue"
                        :style="{ width: formItemWidth }"
                        placeholder="请选择标准"
                        filterable
                        :options="standardList"
                        @update-value="(val, item: any) => {
														itemRoute.standardValue = val
														itemRoute.standardName = item.label;
													}"
                      ></n-select>
                    </n-form-item-grid-item>
                    <n-form-item-grid-item label="技术要求:">
                      <n-input
                        v-model:value="itemRoute.artt6"
                        placeholder="请输入特殊要求"
                        :style="{ width: formItemWidth }"
                      ></n-input>
                    </n-form-item-grid-item>
                    <n-form-item-grid-item label="备注:">
                      <n-input
                        v-model:value="itemRoute.remark"
                        placeholder="请输入备注"
                        :style="{ width: formItemWidth }"
                      ></n-input>
                    </n-form-item-grid-item>
                  </n-grid>
                </n-form>
              </n-tab-pane>
            </n-tabs>
          </n-form>
        </div>
      </template>
    </my-dialog>
  </div>
</template>

<script setup lang="tsx">
defineOptions({
  name: 'ProduceOrder'
});
import type { Ref } from 'vue';
import { ref, onMounted } from 'vue';
import { useMessage } from 'naive-ui';
import type { DataTableColumns } from 'naive-ui';
import { createRequiredFormRule } from '@/utils';
// import { getWorkbenchWiredrawingList } from '@/service/api/md/workbench/wiredrawing';
import { getAccessoryList } from '@/service/api/md/accessory';
import { getPlantList } from '@/service/api/md/plant/plantList';
import { getDictDataList } from '@/service/api/system/dictData';
import { useSearchBtn } from '~/src/hooks/common/useBtn';
import { useLoading } from '~/src/hooks';
import {
  getWorkOrderArrange,
  cancellation,
  getListByStrandedwireId,
  getListByDoublewireId,
  getListByTinningId,
  getListByAnnealingId,
  sendToRegistrationIds
} from '~/src/service/api/plan/productOrder';
import {
  addEnamellingWorkorder,
  addTinningWorkorder,
  addWiredrawingWorkorder,
  addWireWorkorder,
  addStrandedwire
} from '~/src/service/api/plan/enamellingWorkOrder';
import { formatDate } from '~/src/utils/form/rule';
import { getPlanStatusName, getStatusName } from '~/src/utils/common/workOrder';
// 拉丝
import { getWorkbenchWiredrawingList } from '~/src/service/api/md/workbench/wiredrawing';
// 镀锡
import { getWorkbenchEnamellingList } from '~/src/service/api/md/workbench/enamelling';
// 退火
import { getWorkbenchEnamellingList as getWorkAnneal } from '~/src/service/api/md/workbench/workAnneal';
// 镀锡并丝
import { getWorkbenchEnamellingList as getWorkFilament } from '~/src/service/api/md/workbench/workFilament';
// 绞线
import { getWorkbenchEnamellingList as getWorkStrandedWire } from '~/src/service/api/md/workbench/workStrandedWire';
import { getProductByProductSize } from '~/src/service/api/md/list';
import { querySaleOrderById } from '~/src/service/api/sale/order';
import { getStrandedwire } from '~/src/service/api/plan/planStrandedWire';
const enamelCoverData = ref<planWorkOrder.enamelCoverData>({
  equipmentCode: '',
  equipmentId: null,
  equipmentIdList: null,
  axleNumber: null,
  wireDisc: null,
  plantName: null,
  startTime: null,
  endTime: null,
  discNumber: null,
  remark: '',
  weight: null,
  plantNumber: null,
  wireDiscNumber: null,
  standardValue: null,
  standardName: '',
  wireDiscId: null,
  plantNameList: null,
  artt5: null,
  status: null,
  productId: null,
  model: '',
  specification: '',
  artt6: ''
});

const productOptions = ref<any[]>([]);
// 查询各个工艺的产品
function getProduct(str) {
  getProductByProductSize(str).then(res => {
    productOptions.value = [];
    res.data.forEach((item: any) => {
      productOptions.value.push({
        label: `${item.productName} (${item.batchNumber})`,
        batchNumber: item.batchNumber,
        productSize: item.productSize,
        specification: item.specification.split(/[,*]/)[0],
        value: item.id
      });
    });
  });
}
// const lsFormModel = ref<FormInst | null>(null);
const refList: any = ref([]);
const craftName = ref('拉丝');
const range = ref<[number, number] | null>(null);

const formItemWidth = ref<string>('240px');

const message = useMessage();
const routeNameList: any = ref([]);

const { loading, startLoading, endLoading } = useLoading();

const selectProductionId = ref<number>(-1);

// const drawBenchOptions = ref<planWorkOrder.SelectMixedOption[]>([]);

const standardList = ref<planWorkOrder.SelectMixedOption[]>([]);

// const standardItem = ref<planWorkOrder.SelectMixedOption[]>([]);

const plantListOptions = ref<planWorkOrder.SelectMixedOption[]>([]);

const accessoryListOptions = ref<planWorkOrder.SelectMixedOption[]>([]);

const orderTypeList = ref<{ label: string; value: number }[]>([
  { label: '客户订单', value: 0 },
  { label: '备库订单', value: 1 }
]);

const title = ref<string>('计划工单-全部');
// 工单状态
const workOrderStatus = [
  { label: '全部', value: undefined },
  { label: '未排产', value: 0 },
  { label: '排产中', value: 1 },
  { label: '生产中', value: 2 },
  { label: '已完成', value: 3 },
  { label: '已作废', value: 4 }
];

const searchForm = ref<planWorkOrder.searchForm>({
  status: undefined,
  pageNum: 1,
  pageSize: 10,
  total: 0,
  orderType: null,
  model: '',
  workorderCode: '',
  productSpecification: ''
});

const showDialog = ref<boolean>(false);
const getDetailComplete = ref<boolean>(false);

// const isGetWiredrawingList = ref<boolean>(false);

const data = ref<planWorkOrder.planWorkOrderList[]>([]);

const drillingCrewoptions = ref<planWorkOrder.SelectMixedOption[]>([]);
const axleNumberId = ref<string | null>(null);
const rowsWorkbenchEnamellingList = ref<any[]>([]);
// 是否请求完成
const isRequestAccomplish = ref<boolean>(true);

const rowsData = ref<any>({});

const columns: Ref<DataTableColumns<planWorkOrder.planWorkOrderList>> = ref([
  {
    title: '序号',
    key: 'index',
    align: 'center',
    width: 70,
    render: (_row, index) => (searchForm.value.pageNum - 1) * searchForm.value.pageSize + index + 1
  },
  {
    title: '创建时间',
    width: 180,
    align: 'center',
    key: 'createTime'
  },
  {
    title: '订单类型',
    key: 'orderType',
    width: 100,
    render: row => {
      return <n-space>{row.orderType === '0' ? '客户订单' : '备库订单'}</n-space>;
    }
  },
  {
    title: '客户',
    key: 'customerName',
    align: 'center',
    width: 100
  },
  {
    title: '订单编号',
    key: 'orderCode',
    align: 'center',
    width: 280
  },
  {
    title: '生产工单',
    key: 'workorderCode',
    align: 'center',
    width: 180
  },
  {
    title: '产品型号',
    key: 'productModel',
    align: 'center',
    width: 100
  },
  {
    title: '规格',
    key: 'productSpecification',
    align: 'center',
    width: 100,
    ellipsis: {
      tooltip: true
    }
  },
  {
    title: '重量',
    align: 'center',
    key: 'weight',
    width: 100
  },
  {
    title: '关联工艺路线',
    align: 'center',
    key: 'routingName',
    width: 240
  },
  // {
  //   title: '重量',
  //   align: 'center',
  //   key: 'weight',
  //   width: 100
  // },
  {
    title: '交付时间',
    align: 'center',
    key: 'payTime',
    width: 180
  },
  {
    title: '状态',
    key: 'status',
    width: 80,
    fixed: 'right',
    render: row => {
      const statusInfo = getPlanStatusName(row.status);
      return <n-tag type={statusInfo.bgColor}>{statusInfo.statusName}</n-tag>;
    }
  },
  {
    title: '操作',
    key: 'action',
    fixed: 'right',
    width: 180,
    render: row => {
      if (row.status === '4') return '已作废,不可操作';
      return (
        <n-space>
          <n-button type="info" v-show={row.status === '0'} onClick={() => changeShowDialog(row)}>
            排产
          </n-button>
          <n-popconfirm onPositiveClick={() => handleDeleteItemType(row.id)}>
            {{
              default: () => '确认作废吗>',
              trigger: () => (
                <n-button type="warning" v-show={row.status === '1' || row.status === '0'}>
                  作废
                </n-button>
              )
            }}
          </n-popconfirm>
        </n-space>
      );
    }
  }
]);

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
    title: '特殊要求',
    align: 'center',
    width: 100,
    key: 'remark'
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

const formModel = ref<planWorkOrder.FormModel>({
  orderCode: null,
  productModel: null,
  productSpecification: null,
  payTime: null,
  orderType: null, // 订单类型
  customerName: null, // 客户
  workorderCode: null, // 生产工单
  workorderId: null,
  models: null, // 产品型号
  specifications: null, // 产品规格
  measureId: null,
  measureName: null,
  onlyReadWeight: null,
  residueWeight: null, // 剩余重量
  productIds: null //
});

function search(): void {
  if (range.value !== null) {
    searchForm.value['params[beginTime]'] = formatDate(new Date(range.value[0]), 'yyyy-MM-dd');
    searchForm.value['params[endTime]'] = formatDate(new Date(range.value[1]), 'yyyy-MM-dd');
  } else {
    searchForm.value['params[beginTime]'] = null;
    searchForm.value['params[endTime]'] = null;
  }
  if (searchForm.value.status !== undefined) {
    title.value = `生产工单-${workOrderStatus[searchForm.value.status].label}`;
  } else {
    title.value = `生产工单-全部`;
  }
  init();
}
function reset(): void {
  searchForm.value = {
    status: undefined,
    pageNum: 1,
    pageSize: 10,
    total: 0,
    orderType: null,
    model: '',
    workorderCode: '',
    productSpecification: ''
  };
  if (searchForm.value.status !== undefined) {
    title.value = `生产工单-${workOrderStatus[searchForm.value.status].label}`;
  } else {
    title.value = `生产工单-全部`;
  }
  range.value = null;
  search();
}
const locationOptions = ref<planWorkOrder.SelectMixedOption[]>([]);
const lsBrench: any = ref([]);
const thBrench: any = ref([]);
const dxBrench: any = ref([]);
const bsBrench: any = ref([]);
const jxBrench: any = ref([]);
const thDetailData = ref<paintJobOrder.tabelList[]>([]);
const dxDetailData = ref<paintJobOrder.tabelList[]>([]);
const bsDetailData = ref<paintJobOrder.tabelList[]>([]);
const jxDetailData = ref<paintJobOrder.tabelList[]>([]);
// 分开查询各个机台
function getDetailDataById(params): void {
  switch (craftName.value) {
    case '退火':
      thDetailData.value = [];
      getListByAnnealingId(params).then(res => {
        endLoading();
        getDetailComplete.value = false;
        if (res.code === 200) {
          thDetailData.value = res.data.map(n => {
            n.code = n.annealingCode;
            return n;
          });
        }
      });
      break;
    case '镀锡':
      dxDetailData.value = [];
      getListByTinningId(params).then(res => {
        endLoading();
        getDetailComplete.value = false;
        if (res.code === 200) {
          dxDetailData.value = res.data.map(n => {
            n.code = n.tinningCode;
            return n;
          });
        }
      });
      break;
    case '镀锡并丝':
      bsDetailData.value = [];
      getListByDoublewireId(params).then(res => {
        endLoading();
        getDetailComplete.value = false;
        if (res.code === 200) {
          bsDetailData.value = res.data.map(n => {
            n.code = n.doublewireCode;
            return n;
          });
        }
      });
      break;
    case '绞线':
      jxDetailData.value = [];
      getListByStrandedwireId(params).then(res => {
        endLoading();
        getDetailComplete.value = false;
        if (res.code === 200) {
          jxDetailData.value = res.data.map(n => {
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
// 查询工艺机台
function selCraftMachine(type) {
  drillingCrewoptions.value = [];
  rowsWorkbenchEnamellingList.value = [];
  const spec = Number(formModel.value.specifications);
  // 排产规格
  switch (type) {
    case 1:
      lsBrench.value = [];
      getWorkbenchWiredrawingList({ pageSize: 999 }).then(res => {
        rowsWorkbenchEnamellingList.value = res.rows;
        res.rows.forEach((item: any) => {
          const upper = item.productionUpperLimit;
          const lower = item.productionLowerLimit;
          if (spec) {
            if ((spec < upper || spec === upper) && (spec > lower || spec === lower)) {
              lsBrench.value.push({ label: item.equipmentCode, value: item.id, ...item });
              drillingCrewoptions.value.push({
                label: item.equipmentCode,
                value: item.id,
                deviceId: item.equipmentId
              });
            }
          }
        });
      });
      break;
    case 2:
      thBrench.value = [];
      getWorkAnneal({ pageSize: 999 }).then(res => {
        rowsWorkbenchEnamellingList.value = res.rows;
        res.rows.forEach((item: any) => {
          const upper = item.productionUpperLimit;
          const lower = item.productionLowerLimit;
          if (spec) {
            if ((spec < upper || spec === upper) && (spec > lower || spec === lower)) {
              thBrench.value.push({ label: item.equipmentCode, value: item.id, ...item });
              drillingCrewoptions.value.push({
                label: item.equipmentCode,
                value: item.id,
                deviceId: item.equipmentId
              });
            }
          }
        });
      });
      break;
    case 3:
      dxBrench.value = [];
      getWorkbenchEnamellingList({ pageSize: 999 }).then(res => {
        rowsWorkbenchEnamellingList.value = res.rows;
        res.rows.forEach((item: any) => {
          const upper = item.productionUpperLimit;
          const lower = item.productionLowerLimit;
          if (spec) {
            if ((spec < upper || spec === upper) && (spec > lower || spec === lower)) {
              dxBrench.value.push({ label: item.equipmentCode, value: item.id, ...item });
              drillingCrewoptions.value.push({
                label: item.equipmentCode,
                value: item.id,
                deviceId: item.equipmentId
              });
            }
          }
        });
      });
      break;
    case 4:
      bsBrench.value = [];
      getWorkFilament({ pageSize: 999 }).then(res => {
        rowsWorkbenchEnamellingList.value = res.rows;
        res.rows.forEach((item: any) => {
          const upper = item.productionUpperLimit;
          const lower = item.productionLowerLimit;
          if (spec) {
            if ((spec < upper || spec === upper) && (spec > lower || spec === lower)) {
              bsBrench.value.push({ label: item.equipmentCode, value: item.id, ...item });
              drillingCrewoptions.value.push({
                label: item.equipmentCode,
                value: item.id,
                deviceId: item.equipmentId
              });
            }
          }
        });
      });
      break;
    case 5:
      jxBrench.value = [];
      getWorkStrandedWire({ pageSize: 999 }).then(res => {
        rowsWorkbenchEnamellingList.value = res.rows;
        res.rows.forEach((item: any) => {
          const upper = item.productionUpperLimit;
          const lower = item.productionLowerLimit;
          if (spec) {
            if ((spec < upper || spec === upper) && (spec > lower || spec === lower)) {
              jxBrench.value.push({ label: item.equipmentCode, value: item.id, ...item });
              drillingCrewoptions.value.push({
                label: item.equipmentCode,
                value: item.id,
                deviceId: item.equipmentId
              });
            }
          }
        });
      });
      break;
    default:
      break;
  }
}
// 切换机台
function getCraftName() {
  switch (craftName.value) {
    case '拉丝':
      selCraftMachine(1);
      getProduct('?productSize=TR');
      break;
    case '退火':
      selCraftMachine(2);
      getProduct('?productSize=TR');
      break;
    case '镀锡':
      selCraftMachine(3);
      getProduct('?productSize=TXR');
      break;
    case '镀锡并丝':
      selCraftMachine(4);
      getProduct('?productSize=TXR&productSize=TR');
      break;
    case '绞线':
      selCraftMachine(5);
      getProduct('?productSize=TXJR&productSize=TJR');
      break;
    default:
      break;
  }
}
// 打开排产
function changeShowDialog(row: planWorkOrder.planWorkOrderList): void {
  querySaleOrderById({ id: row.orderId }).then(res => {
    for (const key in formModel.value) {
      if (row[key] !== null) {
        formModel.value[key] = row[key];
      }
    }
    getDetailComplete.value = false;
    formModel.value.workorderId = row.id;
    formModel.value.models = row.productModel;
    formModel.value.specifications = parseFloat(row.productSpecification);
    formModel.value.onlyReadWeight = row.weight;
    axleNumberId.value = null;
    rowsData.value = row;
    showDialog.value = true;
    for (const key in enamelCoverData.value) {
      if (enamelCoverData.value[key] !== null) {
        enamelCoverData.value[key] = null;
      }
    }
    selectProductionId.value = row.id;
    enamelCoverData.value.weight = row.weight;
    const routeList = row.routingName.split('-');
    routeList.pop();
    enamelCoverData.value.artt5 = routeList.join('-');
    enamelCoverData.value.status = row.status;
    enamelCoverData.value.standardValue = res.data.standardId.toString();
    enamelCoverData.value.standardName = res.data.standardName;
    enamelCoverData.value.remark = res.data.remark;
    enamelCoverData.value.artt6 = res.data.technicalRequirements;
    routeNameList.value = row.routingName.split('-').map((n: any, index) => {
      let Rules = {};
      if (n !== '拉丝') {
        Rules = {
          equipmentId: createRequiredFormRule('请选择机台'),
          axleNumberId: createRequiredFormRule('请选择位置'),
          wireDisc: createRequiredFormRule('请选择线盘'),
          startTime: createRequiredFormRule('请选择开始时间'),
          endTime: createRequiredFormRule('请选择结束时间'),
          productId: createRequiredFormRule('请选择关联产品')
        };
      } else {
        Rules = {
          equipmentIdList: createRequiredFormRule('请选择机台'),
          wireDisc: createRequiredFormRule('请选择线盘'),
          startTime: createRequiredFormRule('请选择开始时间'),
          endTime: createRequiredFormRule('请选择结束时间'),
          productId: createRequiredFormRule('请选择关联产品')
        };
      }
      return {
        id: index,
        name: n,
        disabled: false,
        axleNumberId: null,
        deviceId: '',
        message: {
          title: `${n}排产通知`,
          content: ''
        },
        Rules,
        ...enamelCoverData.value,
        spec: row.productModel
      };
    });
    routeNameList.value[routeNameList.value.length - 1].productId = row.productId;
    routeNameList.value[routeNameList.value.length - 1].model = row.productModel;
    routeNameList.value[routeNameList.value.length - 1].specification = row.productSpecification;
    routeNameList.value[routeNameList.value.length - 1].batNumber = row.attr1;
    routeNameList.value[routeNameList.value.length - 1].disabled = true;
    getCraftName();
  });
}

function handleDeleteItemType(id: number): void {
  cancellation(id).then(res => {
    if (res.code === 200) {
      message.success('作废成功');
      init();
    }
  });
}

// function isInteter(count: number | null, msg: string): boolean {
//   console.log('count ==> ', count);
//   console.log('msg ==> ', msg);
//   if (count === null) return false;
//   const inteters = count.toString().split('.');
//   if (inteters.length > 1) {
//     message.warning(`${msg}数量只能为整数`);
//     return false;
//   }
//   if (count < 0 || count === 0) {
//     message.warning(`${msg}不能小于或等于0`);
//     return false;
//   }
//   return true;
// }

function cancel(): void {
  showDialog.value = false;
  craftName.value = '拉丝';
}
async function submitRepetitiom(obj: any, func: any) {
  // console.log({ ...obj, ...formModel.value });
  isRequestAccomplish.value = false;
  const res = await func({
    ...obj,
    ...formModel.value,
    isDeleted: 0,
    version: 0,
    degreeOfCompletion: 0,
    wightCompletion: 0,
    attr1: 'N',
    sort: 0,
    insertStatus: 0
  });
  isRequestAccomplish.value = true;
  if (res.code === 200) {
    message.success('排产成功');
    craftName.value = '拉丝';
    init();
    showDialog.value = false;
  }
}
function vaildateForm(obj) {
  let flag = true;
  let timeStatus = true;
  if (!obj.wireDiscId || !obj.startTime || !obj.endTime || !obj.productId) {
    flag = false;
  }
  if (obj.startTime && obj.endTime) {
    const startTime = new Date(obj.startTime).getTime();
    const endTime = new Date(obj.endTime).getTime();
    if (startTime > endTime) {
      flag = false;
      timeStatus = false;
    }
  }
  return {
    flag,
    name: obj.name,
    timeStatus,
    ...obj
  };
}
function submit(): void {
  const allStatus: any = [];
  routeNameList.value.forEach(item => {
    const status = vaildateForm(item);
    if (!status.timeStatus) {
      message.warning(`${status.name}信息开始时间不能大于结束时间`);
      return;
    }
    if (!status.flag) {
      message.warning(`请将${status.name}工单信息填写完整！！！`);
      return;
    }
    allStatus.push(true);
  });
  if (allStatus.length === routeNameList.value.length) {
    const sendObj = {
      mesStrandedwireWorkorder: {},
      mesDoublewireWorkorder: {},
      mesTinningWorkorder: {},
      mesAnnealingWorkorder: {},
      mesWiredrawingWorkorder: {}
    };
    routeNameList.value.forEach(n => {
      switch (n.name) {
        case '拉丝':
          n.equipmentCode = '';
          n.equipmentId = '';
          n.equipmentIdList.forEach(k => {
            const idx = lsBrench.value.findIndex(ele => ele.value === k);
            n.equipmentCode += `${lsBrench.value[idx].label},`;
            n.equipmentId += `${k},`;
          });
          n.equipmentCode = n.equipmentCode.slice(0, -1);
          n.equipmentId = n.equipmentId.slice(0, -1);
          n.message.content = `排产机台:${n.equipmentCode},排产量:${n.weight}/kg`;
          sendObj.mesWiredrawingWorkorder = n;
          submitRepetitiom(n, addWiredrawingWorkorder);
          break;
        case '退火':
          n.message.content = `排产机台:${n.equipmentCode},排产量:${n.weight}/kg`;
          submitRepetitiom(n, addEnamellingWorkorder);
          sendObj.mesAnnealingWorkorder = n;
          break;
        case '镀锡':
          n.message.content = `排产机台:${n.equipmentCode},排产量:${n.weight}/kg`;
          sendObj.mesTinningWorkorder = n;
          submitRepetitiom(n, addTinningWorkorder);
          break;
        case '镀锡并丝':
          n.message.content = `排产机台:${n.equipmentCode},排产量:${n.weight}/kg`;
          sendObj.mesDoublewireWorkorder = n;
          submitRepetitiom(n, addWireWorkorder);
          break;
        case '绞线':
          n.message.content = `排产机台:${n.equipmentCode},排产量:${n.weight}/kg`;
          sendObj.mesStrandedwireWorkorder = n;
          submitRepetitiom(n, addStrandedwire);
          break;
        default:
          break;
      }
      return n;
    });
    sendToRegistrationIds(sendObj).then(res => {
      // eslint-disable-next-line no-console
      console.log(res, '11111111111111');
    });
  }

  //
}

function getList(): void {
  drillingCrewoptions.value = [];
  getWorkbenchEnamellingList({ pageSize: 999, isEnable: 1 }).then(res => {
    rowsWorkbenchEnamellingList.value = res.rows;
    res.rows.forEach((item: { equipmentCode: any; id: any; equipmentId: any }) => {
      drillingCrewoptions.value.push({
        label: item.equipmentCode,
        value: item.id,
        deviceId: item.equipmentId
      });
    });
  });

  getPlantList({ pageSize: 999 }).then(res => {
    res.rows.forEach((item: { plantName: any; id: any }) => {
      plantListOptions.value.push({
        label: item.plantName,
        value: item.id
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
          value: item.dictValue
        });
      });
    }
  });
}

// watch(showDialog, () => {
//   changeActive(1);
// });

function init(): void {
  startLoading();
  getWorkOrderArrange({ ...searchForm.value }).then(res => {
    if (res.code === 200) {
      searchForm.value.total = res.total;
      data.value = res.rows;
    }
    endLoading();
  });
}
onMounted(() => {
  init();
  getList();
});
</script>

<style scoped>
:deep(.n-form-item-label) {
  width: 100px !important;
}

.divider {
  height: 1px;
  background-color: #ddd;
  margin-bottom: 30px;
}
</style>
