<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline :model="searchForm">
        <n-form-item label="类型名称">
          <n-input v-model:value="searchForm.typeName" placeholder="请输入类型名称" />
        </n-form-item>
        <n-form-item label="类型编码">
          <n-select
            v-model:value="searchForm.typeCode"
            :options="codeOptions"
            :clearable="true"
            class="w-180px"
            placeholder="请选择类型编码"
          ></n-select>
        </n-form-item>
        <n-form-item>
          <n-button type="primary" class="mr-10px" @click="handleSearch">
            <icon-ic-round-search class="mr-4px text-20px" />
            搜索
          </n-button>
          <n-button @click="handleReset">
            <icon-ic-round-refresh class="mr-4px text-20px" />
            重置
          </n-button>
        </n-form-item>
      </n-form>
    </my-card>
    <my-card title="设备类型列表">
      <template #right>
        <div class="flex-center">
          <n-button type="primary" class="ml-5px" @click="add(1)">
            <icon-ic-round-plus class="mr-4px text-20px" />
            新增
          </n-button>
          &nbsp;
          <cx-columns v-model:columns="columns" size="medium"></cx-columns>
        </div>
      </template>
      <n-data-table :row-key="rowKey" :loading="loading" :columns="columns" :data="data"></n-data-table>
      <!-- <n-pagination
        v-model:page="searchForm.pageNum"
        v-model:page-size="searchForm.pageSize"
        style="margin-top: 20px; float: right"
        :item-count="searchForm.total"
        @update:page="changePage"
      ></n-pagination> -->
      <my-pagination v-model:search-form="searchForm" @init="getList"></my-pagination>
    </my-card>
    <my-dialog v-model:show="showDialog" :title="titleText" @cancel="cancel" @submit="addWorkshop">
      <template v-if="showData" #content>
        <div>
          <n-form ref="formRef" label-placement="left" label-width="100px" :model="addForm" :rules="rules">
            <n-form-item label="类型名称：" path="typeName">
              <n-input v-model:value="addForm.typeName" placeholder="请输入类型名称" />
            </n-form-item>
            <n-form-item label="类型编码：" path="typeCode">
              <n-select
                v-model:value="addForm.typeCode"
                :options="codeOptions"
                :clearable="true"
                class="w-180px"
                placeholder="请选择类型编码"
              ></n-select>
            </n-form-item>
            <n-form-item label="采集方式：">
              <n-input v-model:value="addForm.collectionType" placeholder="请输入采集方式" />
            </n-form-item>
            <n-form-item label="备注：">
              <n-input v-model:value="addForm.remark" type="textarea" placeholder="请输入备注" />
            </n-form-item>
          </n-form>
        </div>
      </template>
      <template v-else #content>
        <div>
          <n-form ref="formRef" label-placement="left" label-width="100px" :model="addForm" :rules="rulesReal">
            <n-form-item label="参数名：" path="realTimeName">
              <n-input v-model:value="addRealForm.realTimeName" placeholder="请输入参数名" />
            </n-form-item>
            <n-form-item label="参数编码：" path="realTimeCode">
              <n-input v-model:value="addRealForm.realTimeCode" placeholder="请输入参数编码" />
            </n-form-item>
            <n-form-item label="数值上限：" path="dataUpper">
              <n-input v-model:value="addRealForm.dataUpper" placeholder="请输入数值上限" />
            </n-form-item>
            <n-form-item label="数值下限：" path="dataLower">
              <n-input v-model:value="addRealForm.dataLower" placeholder="请输入数值下限" />
            </n-form-item>
            <n-form-item label="采集地址：" path="realTimeArea">
              <n-input v-model:value="addRealForm.realTimeArea" placeholder="请输入采集地址" />
            </n-form-item>
            <n-form-item label="端口：" path="port">
              <n-input v-model:value="addRealForm.port" placeholder="请输入端口" />
            </n-form-item>
            <n-form-item label="是否去重" path="port">
              <n-select
                v-model:value="addRealForm.dataSet"
                :options="[
                  { label: '是', value: '是' },
                  { label: '否', value: '否' }
                ]"
                class="w-160px"
                placeholder="请选择是否去重"
              ></n-select>
            </n-form-item>
          </n-form>
        </div>
      </template>
    </my-dialog>
    <my-dialog
      v-model:show="realTimeSet"
      sub-text="关闭"
      width="1200px"
      title="设备数采参数设置"
      :show-cancel="false"
      @cancel="closerealTime"
      @submit="closerealTime"
    >
      <template #content>
        <div class="test">
          <my-card title="设备数采参数列表">
            <template #right>
              <n-button size="small" type="primary" class="ml-5px" @click="add(0)">
                <icon-ic-round-plus class="mr-4px text-15px" />
                新增
              </n-button>
              &nbsp;
              <CxColumns v-model:columns="realTimeColumns" />
            </template>
            <n-data-table :loading="false" :columns="realTimeColumns" :data="realTimeData"></n-data-table>
            <my-pagination v-model:search-form="searchForm"></my-pagination>
          </my-card>
        </div>
      </template>
    </my-dialog>
  </div>
</template>

<script setup lang="tsx">
import type { Ref } from 'vue';
import { onMounted, ref } from 'vue';
import { useMessage, useDialog } from 'naive-ui';
import type { DataTableColumns, FormInst } from 'naive-ui';
import { useLoading } from '~/src/hooks';
import { createRequiredFormRule, deepClone } from '~/src/utils';

const formRef = ref<FormInst | null>(null);
const dialog = useDialog();
const message = useMessage();
// const typeOptions = [
//   {
//     label: '拉丝机',
//     value: 'LSJ'
//   },
//   {
//     label: '漆包机',
//     value: 'QBJ'
//   },
//   {
//     label: '空压机',
//     value: 'KYJ'
//   }
// ];
// 搜索
type searchType = {
  typeName: string;
  typeCode: string | null;
  pageNum: number;
  pageSize: number;
  total: number;
};
const searchForm = ref<searchType>({
  typeName: '',
  typeCode: null,
  pageNum: 1,
  pageSize: 10,
  total: 7
});
function handleSearch() {
  console.log(searchForm.value);
  searchForm.value.pageNum = 1;
  getList();
}
function handleReset() {
  searchForm.value = {
    typeName: '',
    typeCode: null,
    pageNum: 1,
    pageSize: 10,
    total: 0
  };
  getList();
}
const codeOptions = [
  {
    value: '配电房',
    label: 'PDF'
  },
  {
    value: 'UV光解',
    label: 'UV'
  },
  {
    value: 'RTO',
    label: 'RTO'
  },
  {
    value: '空压机',
    label: 'KYJ'
  },
  {
    value: '称重机',
    label: 'CZ'
  },
  {
    value: '漆包机',
    label: 'QBJ'
  },
  {
    value: '拉丝机',
    label: 'LSJ'
  }
];
type addRealType = {
  id?: number;
  realTimeName: string;
  realTimeCode: string;
  dataUpper: string;
  dataLower: string;
  realTimeArea: string;
  port: string;
  dataSet: string | null;
};
const rulesReal = {
  realTimeName: createRequiredFormRule('请输入参数名'),
  realTimeCode: createRequiredFormRule('请选择参数编码'),
  realTimeArea: createRequiredFormRule('请选择采集地址'),
  port: createRequiredFormRule('请输入端口')
};
const addRealForm = ref<addRealType>({
  realTimeName: '',
  realTimeCode: '',
  dataUpper: '',
  dataLower: '',
  realTimeArea: '',
  port: '',
  dataSet: null
});

const realTimeSet = ref<boolean>(false);
function closerealTime() {
  realTimeSet.value = false;
}
function handleSet() {
  realTimeSet.value = true;
}
const realTimeData = ref<realTimeType[]>([
  {
    realTimeName: '拉丝速度',
    realTimeCode: 'LSSD',
    dataUpper: '50',
    dataLower: '30',
    realTimeArea: '192.168.1.10',
    port: 80,
    dataSet: '是'
  },
  {
    realTimeName: '当前长度',
    realTimeCode: 'DQCD',
    dataUpper: '50',
    dataLower: '30',
    realTimeArea: '192.168.1.37',
    port: 80,
    dataSet: '是'
  },
  {
    realTimeName: '当前重量',
    realTimeCode: 'DQZL',
    dataUpper: '50',
    dataLower: '30',
    realTimeArea: '192.168.1.26',
    port: 80,
    dataSet: '是'
  }
]);
const realTimeColumns: Ref<DataTableColumns<realTimeType>> = ref([
  {
    title: '序号',
    key: 'index',
    align: 'center',
    width: 120,
    render: (_row, index) => (searchForm.value.pageNum - 1) * searchForm.value.pageSize + index + 1
  },
  {
    title: '参数名',
    width: 120,
    key: 'realTimeName'
  },
  {
    title: '参数编码',
    width: 120,
    key: 'realTimeCode'
  },
  {
    title: '数值上限',
    width: 120,
    key: 'dataUpper'
  },
  {
    title: '数值下限',
    width: 120,
    key: 'dataLower'
  },
  {
    title: '采集地址',
    width: 120,
    key: 'realTimeArea'
  },
  {
    title: '端口',
    width: 80,
    key: 'port'
  },
  {
    title: '数据去重',
    align: 'center',
    width: 80,
    key: 'dataSet'
  },
  {
    title: '操作',
    titleAlign: 'center',
    fixed: 'right',
    width: 160,
    key: 'cz',
    render(row) {
      return (
        <div>
          <n-button onClick={() => handleEditTable(row, 0)} size="tiny" type="info" quaternary class="mr-5px">
            <icon-tdesign-edit class="mr-1px text-15px " />
            编辑
          </n-button>
          <n-button size="tiny" type="error" quaternary class="mr-5px">
            <icon-tdesign-delete class="mr-1px text-15px " />
            删除
          </n-button>
        </div>
      );
    }
  }
]);
type realTimeType = {
  realTimeName: string;
  realTimeCode: string;
  dataUpper: string;
  dataLower: string;
  realTimeArea: string;
  port: number;
  dataSet: string;
};

type addType = {
  id?: number;
  deivceType: string | null;
  typeName: string;
  typeCode: string | null;
  collectionType: string;
  remark: string;
};
const rules = {
  typeName: createRequiredFormRule('请输入类型名称'),
  typeCode: createRequiredFormRule('请选择类型编号')
};
const addForm = ref<addType>({
  deivceType: null,
  typeName: '',
  typeCode: null,
  collectionType: '',
  remark: ''
});
// const { bool: showDialog, setTrue: openDialog, setFalse: closeDialog } = useBoolean();

// function changePage(page: number) {
//   searchForm.value.pageNum = page;
//   getList();
// }id
const rowKey = (row: any) => row.id;
type columnsType = {
  id: number;
  typeName: string;
  typeCode: string;
  collectionType: string;
  remark: string;
  pName: string;
};
const { loading, startLoading, endLoading } = useLoading();
const data = ref<columnsType[]>([
  {
    id: 4,
    typeName: '空压机',
    typeCode: 'KYJ',
    collectionType: '汇川PLC',
    remark: '',
    pName: '空压机'
  },
  {
    id: 5,
    typeName: '称重机',
    typeCode: 'CZ',
    collectionType: 'RS232',
    remark: '',
    pName: '称重机'
  },
  {
    id: 6,
    typeName: '漆包机',
    typeCode: 'QBJ',
    collectionType: '西门子PLC',
    remark: '',
    pName: '漆包机'
  },
  {
    id: 7,
    typeName: '拉丝机',
    typeCode: 'LSJ',
    collectionType: '西门子PLC',
    remark: '',
    pName: '拉丝机'
  }
]);
function getList() {
  startLoading();
  endLoading();
}
const titleText = ref<string>('');
const showDialog = ref<boolean>(false);
const showData = ref<boolean>(false);
function cancel() {
  addForm.value = {
    deivceType: null,
    typeName: '',
    typeCode: null,
    collectionType: '',
    remark: ''
  };
  addRealForm.value = {
    realTimeName: '',
    realTimeCode: '',
    dataUpper: '',
    dataLower: '',
    realTimeArea: '',
    port: '',
    dataSet: null
  };
  showDialog.value = false;
  // closeDialog();
}
function handleEditTable(row: any, type) {
  showDialog.value = true;
  if (type) {
    showData.value = true;
    titleText.value = '编辑类型';
    addForm.value = deepClone(row);
  } else {
    showData.value = false;
    titleText.value = '编辑速采参数';
    addRealForm.value = deepClone(row);
  }
}
function add(type) {
  showDialog.value = true;
  if (type) {
    showData.value = true;
    titleText.value = '新增类型';
  } else {
    showData.value = false;
    titleText.value = '新增速采参数';
  }
}
// 确认新增/修改
function addWorkshop() {
  if (!formRef.value) return;
  formRef.value.validate(error => {
    if (!error) {
      if (addForm.value.id) {
        // editWorkshop(addForm.value).then(res => {
        //   if (res.code === 200) {
        //     message.success('编辑成功');
        //     getList();
        //   }
        // });
      } else {
        // add(addForm.value).then(res => {
        //   if (res.code === 200) {
        //     message.success('新增成功');
        //     getList();
        //   }
        // });
      }

      cancel();
    }
  });
}
const columns: Ref<DataTableColumns<columnsType>> = ref([
  {
    type: 'selection'
  },
  {
    title: '序号',
    key: 'index',
    align: 'center',
    render: (_row, index) => (searchForm.value.pageNum - 1) * searchForm.value.pageSize + index + 1
  },
  {
    title: '类型名称',
    key: 'typeName'
  },
  {
    title: '父类名称',
    key: 'pName'
  },
  {
    title: '类型编码',
    key: 'typeCode'
  },
  {
    title: '采集方式',
    key: 'collectionType'
  },
  {
    title: '备注',
    key: 'remark'
  },
  {
    title: '操作',
    key: 'action',
    titleAlign: 'center',
    width: 320,
    render(row: any) {
      return (
        <div>
          <n-button onClick={() => handleSet()} type="primary" size="small" class="mr-2">
            <icon-tdesign-edit class="mr-2" />
            数采参数设置
          </n-button>
          <n-button onClick={() => handleEditTable(row, 1)} type="primary" size="small" class="mr-2">
            <icon-tdesign-edit class="mr-2" />
            编辑
          </n-button>
          <n-button
            type="error"
            size="small"
            onClick={() => {
              dialog.warning({
                title: '警告',
                content: '此操作将删除该类型以及绑定的信息，确定删除吗？',
                style: { borderRadius: '10px' },
                maskClosable: false,
                closable: false,
                positiveText: '确定',
                negativeText: '取消',
                onPositiveClick: () => {
                  message.success('删除成功');
                },
                onNegativeClick: () => {
                  message.info('取消删除');
                }
              });
            }}
          >
            <icon-tdesign-delete class="mr-2" />
            删除
          </n-button>
        </div>
      );
    }
  }
]);
onMounted(() => {
  getList();
});
</script>

<style lang="scss" scoped></style>
