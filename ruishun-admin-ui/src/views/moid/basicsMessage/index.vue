<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item label="模具编号">
          <n-input v-model:value="searchForm.moldCode" type="text" placeholder="请输入模具编号" />
        </n-form-item>
        <n-form-item label="模具型号">
          <n-input v-model:value="searchForm.moldSpec" type="text" placeholder="请输入模具型号" />
        </n-form-item>
        <n-form-item label="模具孔径">
          <n-input v-model:value="searchForm.moldAperture" type="text" placeholder="请输入模具孔径" />
        </n-form-item>
        <n-form-item label="材质">
          <n-input v-model:value="searchForm.moldMaterial" type="text" placeholder="请输入材质" />
        </n-form-item>
        <n-form-item label="存放位置">
          <n-input v-model:value="searchForm.moldArea" type="text" placeholder="请输入存放位置" />
        </n-form-item>
        <n-form-item>
          <component :is="useSearchBtn(search, reset)"></component>
        </n-form-item>
      </n-form>
    </my-card>
    <my-card title="模具基础信息台账列表">
      <template #right>
        <div>
          <n-button size="small" type="info" style="margin-right: 10px" @click="uploadShow = true">
            <icon-mdi-import />
            导入模具
          </n-button>
          <n-button size="small" type="info" style="margin-right: 10px" @click="addOrEdit(0)">
            <icon-mdi-add />
            新增模具
          </n-button>
          <CxColumns v-model:columns="columns" size="small" />
        </div>
      </template>
      <n-data-table :loading="loading" :columns="columns" :data="data"></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="init"></my-pagination>
    </my-card>

    <my-dialog :show="show" width="700px" :title="title" @cancel="cancel" @submit="submit">
      <template #content>
        <div>
          <n-form
            ref="formModel"
            style="width: 600px"
            label-placement="left"
            label-align="left"
            :label-width="80"
            :model="addForm"
            :rules="rules"
          >
            <n-grid x-gap="12" :cols="2">
              <n-form-item-grid-item label="模具类别" path="classificationId">
                <n-select
                  v-model:value="addForm.classificationId"
                  placeholder="请选择模具类别"
                  class="w-180px"
                  :options="moidOptions"
                  :style="{ width: formItemWidth }"
                  @update:value="handleUpdateValue"
                ></n-select>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="型号" path="moldSpec">
                <n-input
                  v-model:value="addForm.moldSpec"
                  placeholder="请输入型号"
                  :style="{ width: formItemWidth }"
                  :disabled="true"
                ></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="模具编号" path="moldCode">
                <n-input
                  v-model:value="addForm.moldCode"
                  placeholder="请输入模具编号"
                  :style="{ width: formItemWidth }"
                />
              </n-form-item-grid-item>
              <n-form-item-grid-item label="材质" path="moldMaterial">
                <n-input
                  v-model:value="addForm.moldMaterial"
                  placeholder="请输入材质"
                  :style="{ width: formItemWidth }"
                ></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="模具孔径" path="moldAperture">
                <n-input-number
                  v-model:value="addForm.moldAperture"
                  placeholder="请输入模具孔径"
                  :style="{ width: formItemWidth }"
                />
              </n-form-item-grid-item>
              <n-form-item-grid-item label="模具数量" path="moldNum">
                <n-input-number
                  v-model:value="addForm.moldNum"
                  placeholder="请输入模具数量"
                  :style="{ width: formItemWidth }"
                />
              </n-form-item-grid-item>
              <n-form-item-grid-item label="存放位置" path="moldArea">
                <n-input
                  v-model:value="addForm.moldArea"
                  placeholder="请输入存放位置"
                  :style="{ width: formItemWidth }"
                ></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="公差" path="engineeringTolerance">
                <n-input-number
                  v-model:value="addForm.engineeringTolerance"
                  placeholder="请输入公差"
                  :style="{ width: formItemWidth }"
                />
              </n-form-item-grid-item>

              <n-form-item-grid-item label="所属客户" path="moldSupplierId">
                <n-select
                  v-model:value="addForm.moldSupplierId"
                  placeholder="请选择所属客户"
                  class="w-180px"
                  :options="SupplierList"
                  :style="{ width: formItemWidth }"
                ></n-select>
              </n-form-item-grid-item>
            </n-grid>
          </n-form>
        </div>
      </template>
    </my-dialog>

    <my-dialog :show="uploadShow" :title="'导入模具'" width="700px" @cancel="uploadShow = false" @submit="submitUpload">
      <template #content>
        <div>
          <n-form ref="formModel" style="width: 600px" label-placement="left" label-align="left" :label-width="130">
            <n-form-item label="上传文件">
              <n-upload
                :show-file-list="true"
                :style="{ width: formItemWidth }"
                :default-upload="true"
                :max="1"
                @change="changeUpload"
              >
                <n-button :style="{ width: formItemWidth }">选择文件</n-button>
              </n-upload>
            </n-form-item>
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
import { useMessage, useDialog } from 'naive-ui';
import { createRequiredFormRule } from '@/utils';
import { useLoading } from '~/src/hooks';
import { useSearchBtn } from '~/src/hooks/common/useBtn';
import {
  getMoldLedgerList,
  addMoldLedger,
  ediMoldLedger,
  deleteMoldLedger,
  addMoldLedgerUpload
} from '~/src/service/api/moid/basicsMessage/index';
import { getMoidList2 } from '~/src/service/api/moid/sortManagement/index';
import { getmoldSupplierList } from '~/src/service/api/moid/supplier/index';
const searchForm = ref<basicsMessage.searchForm>({
  moldCode: '',
  moldMaterial: '',
  moldSpec: '',
  moldArea: '',
  moldAperture: '',
  pageNum: 1,
  pageSize: 10,
  total: 0
});

const addForm = ref<basicsMessage.addForm>({
  classificationId: null,
  moldCode: '',
  moldSpec: '',
  moldMaterial: '',
  moldAperture: null,
  moldNum: null,
  moldArea: '',
  moldSupplierId: null,
  moldId: null,
  moldType: null,
  engineeringTolerance: null
});

const { loading, startLoading, endLoading } = useLoading();

const show = ref<boolean>(false);

const uploadShow = ref<boolean>(false);

const type = ref<number>(0); // 0 : 添加， 1 : 编辑
const formModel = ref<FormInst | null>(null);
const uploadFormData = ref<any | null>(null);
const message = useMessage();
const dialog = useDialog();
const addOrEditComplete = ref<boolean>(true);
const formItemWidth = ref<string>('220px');
const title = ref<string>('');

const requestList = ref<boolean>(false);

const moidList = ref<any[]>([]);

const moidOptions = ref<{ label: string; value: number }[]>([]);
const SupplierList = ref<{ label: string; value: number }[]>([]);

const params = {
  pageNum: 1,
  pageSize: 999,
  total: 0
};

const rules = {
  classificationId: createRequiredFormRule('请选择模具类别'),
  moldCode: createRequiredFormRule('请输入模具编号'),
  moldAperture: createRequiredFormRule('请输入模具孔径'),
  moldSupplierId: createRequiredFormRule('请选择所属客户'),
  moldNum: createRequiredFormRule('请输入模具数量'),
  engineeringTolerance: createRequiredFormRule('请输入公差')
};

const data = ref<basicsMessage.TableList[]>([
  {
    moldCode: '240928',
    moldType: '镀锡模',
    moldSpec: '出线模',
    moldMaterial: '高金',
    moldAperture: 0.178,
    moldNum: 1,
    moldArea: '200322',
    moldName: 'A1',
    engineeringTolerance: 0.03,
    moldSupplierName: '*****',
    status: '正常',
    supplierName: '',
    inOutStatus: '正常',
    nestingMoldId: 1,
    nowAperture: 1,
    createBy: '',
    createTime: '',
    updateTolerance: 1,
    moldId: 1,
    classificationId: 1,
    moldSupplierId: 1
  },
  {
    moldCode: '231028',
    moldType: '拉丝模',
    moldSpec: '出线模',
    moldMaterial: '高金',
    moldAperture: 0.158,
    moldNum: 1,
    moldArea: '200322',
    moldName: 'A2',
    engineeringTolerance: 0.01,
    moldSupplierName: '*****',
    status: '在用',
    supplierName: '',
    inOutStatus: '正常',
    nestingMoldId: 1,
    nowAperture: 1,
    createBy: '',
    createTime: '',
    updateTolerance: 1,
    moldId: 1,
    classificationId: 1,
    moldSupplierId: 1
  },
  {
    moldCode: '240618',
    moldType: '冷接模',
    moldSpec: '出线模',
    moldMaterial: '合金',
    moldAperture: 0.178,
    moldNum: 1,
    moldArea: '200322',
    moldName: '',
    engineeringTolerance: 0.03,
    moldSupplierName: '*****',
    status: '报废',
    supplierName: '',
    inOutStatus: '正常',
    nestingMoldId: 1,
    nowAperture: 1,
    createBy: '',
    createTime: '',
    updateTolerance: 1,
    moldId: 1,
    classificationId: 1,
    moldSupplierId: 1
  }
]);

const columns: Ref<DataTableColumns<basicsMessage.TableList>> = ref([
  {
    title: '模具编号',
    key: 'moldCode',
    align: 'center',
    width: 100
  },
  {
    title: '模具类别',
    key: 'moldType',
    align: 'center',
    width: 100
  },
  {
    title: '型号',
    key: 'moldSpec',
    align: 'center',
    width: 100
  },
  {
    title: '材质',
    key: 'moldMaterial',
    align: 'center',
    width: 100
  },
  {
    title: '模具孔径',
    key: 'moldAperture',
    align: 'center',
    width: 100
  },
  {
    title: '模具数量',
    key: 'moldNum',
    align: 'center',
    width: 100
  },
  {
    title: '存放位置',
    key: 'moldArea',
    align: 'center',
    width: 100
  },
  {
    title: '套模名称',
    key: 'moldName',
    align: 'center',
    width: 100
  },
  {
    title: '公差',
    key: 'engineeringTolerance',
    align: 'center',
    width: 100
  },
  {
    title: '供应商',
    key: 'moldSupplierName',
    align: 'center',
    width: 100
  },
  {
    title: '状态',
    key: 'status',
    align: 'center',
    width: 100,
    render: row => {
      return <n-tag>{row.status}</n-tag>;
    }
  },
  {
    title: '操作',
    key: 'actions',
    align: 'center',
    render: row => {
      return (
        <n-space justify={'center'}>
          <n-button
            attr-type="button"
            size={'small'}
            onClick={() => {
              addOrEdit(1, row);
            }}
          >
            <icon-mdi-edit />
            编辑
          </n-button>
          <n-button type="warning" size={'small'} onClick={() => handleDeleteItemType(row.moldId)}>
            <icon-mdi-delete />
            删除
          </n-button>
        </n-space>
      );
    }
  }
]);

function search() {
  init();
}
function reset() {
  searchForm.value = {
    moldCode: '',
    moldMaterial: '',
    moldSpec: '',
    moldArea: '',
    moldAperture: '',
    pageNum: 1,
    pageSize: 10,
    total: 0
  };
  init();
}

function getList(): void {
  if (requestList.value) return;
  requestList.value = true;
  getMoidList2(params).then(res => {
    if (res.code === 200) {
      moidList.value = res.rows;
      res.rows.forEach(item => {
        moidOptions.value.push({
          label: `${item.classificationName}+${item.classificationType}`,
          value: item.classificationId
        });
      });
    }
  });
  getmoldSupplierList(params).then(res => {
    if (res.code === 200) {
      res.rows.forEach(item => {
        SupplierList.value.push({
          label: item.supplierName,
          value: item.moldSupplierId
        });
      });
    }
  });
}

function changeUpload(files) {
  if (files.fileList.length === 0) {
    uploadFormData.value = null;
    return;
  }
  uploadFormData.value = files.file;
}

function addOrEdit(count: number, row?: basicsMessage.TableList): void {
  type.value = count;
  show.value = true;
  getList();
  if (count === 0) {
    title.value = '新增';
    for (const key in addForm.value) {
      if (addForm.value[key] !== null) {
        addForm.value[key] = null;
      }
    }
  } else {
    title.value = '编辑';
    for (const key in addForm.value) {
      if (row) {
        addForm.value[key] = row[key];
      }
    }
  }
}

function handleUpdateValue(value: number): void {
  const index = moidList.value.findIndex(item => item.classificationId === value);
  if (index > -1) {
    addForm.value.moldSpec = moidList.value[index].classificationType;
    addForm.value.moldType = moidList.value[index].classificationName;
  }
}

function cancel(): void {
  show.value = false;
}

function submitSame(msg: string, code: number): void {
  show.value = false;
  addOrEditComplete.value = true;
  addForm.value.moldSpec = null;
  if (code === 200) {
    message.success(msg);
    init();
  }
}

function submit(): void {
  if (!formModel.value) return;
  formModel.value.validate(errors => {
    if (!errors) {
      if (!addOrEditComplete.value) return;
      if (addForm.value.moldNum === null) return;
      if (addForm.value.moldNum < 0) {
        message.warning('模具数量不能小于0');
        return;
      }
      const item = addForm.value.moldNum.toString().split('.');
      if (item.length > 1) {
        message.warning('模具数量只能为整数');
        return;
      }
      addOrEditComplete.value = false;
      if (addForm.value.moldCode !== null) {
        addForm.value.moldCode = addForm.value.moldCode.trim();
      }
      if (type.value === 0) {
        addMoldLedger(addForm.value).then(res => {
          addOrEditComplete.value = true;
          if (res.code === 200) {
            submitSame('添加成功', res.code);
          }
        });
      } else {
        ediMoldLedger(addForm.value).then(res => {
          addOrEditComplete.value = true;
          if (res.code === 200) {
            submitSame('编辑成功', res.code);
          }
        });
      }
    }
  });
}

function submitUpload() {
  if (uploadFormData.value === null) {
    message.warning('请选择文件进行上传');
    return;
  }
  addMoldLedgerUpload(uploadFormData.value, {
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  }).then(res => {
    if (res.code === 200) {
      message.success('上传成功');
    }
  });
}

const sleep = id =>
  new Promise(resolve => {
    deleteMoldLedger(id).then(res => {
      resolve(res);
    });
  });

function handleDeleteItemType(id: number | null): void {
  const d = dialog.warning({
    title: '删除',
    content: '确认要删除吗?',
    positiveText: '确认',
    negativeText: '取消',
    onPositiveClick: () => {
      d.loading = true;
      return new Promise(resolve => {
        sleep(id).then((res: any) => {
          if (res.code === 200) {
            message.success('删除成功');
            init();
            resolve(res);
          }
        });
      });
    }
  });
}

function init() {
  startLoading();
  getMoldLedgerList(searchForm.value).then(res => {
    endLoading();
    if (res.code === 200) {
      data.value = res.rows;
      searchForm.value.total = res.total;
    }
  });
}
onMounted(() => {
  init();
});
</script>

<style scoped></style>
