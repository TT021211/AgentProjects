<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item style="width: 180px" label="产品分类">
          <n-tree-select v-model:value="searchForm.productTypeId" :options="typeOptions" placeholder="请输入产品分类" />
        </n-form-item>
        <n-form-item label="产品名称">
          <n-input v-model:value="searchForm.productName" placeholder="请输入产品名称" />
        </n-form-item>
        <n-form-item label="产品规格">
          <n-input v-model:value="searchForm.specification" placeholder="请输入产品规格" />
        </n-form-item>
        <n-form-item label="批号">
          <n-input v-model:value="searchForm.batchNumber" placeholder="请输入产品规格" />
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
    <my-card title="产品列表">
      <template #right>
        <div class="flex-center">
          <n-button class="ml-5px" type="warning" quaternary @click="uploadShow = true">
            <icon-uil:export class="mr-4px text-20px" />
            导入
          </n-button>
          <cx-columns v-model:columns="columns" size="medium"></cx-columns>
          <n-button class="ml-5px" type="primary" @click="openDialog">
            <icon-ic-round-plus class="mr-4px text-20px" />
            新增产品
          </n-button>
        </div>
      </template>
      <n-data-table ellipsis :loading="loading" :columns="columns" :data="data"></n-data-table>
      <!-- <n-pagination
        :page="searchForm.pageNum"
        :item-count="searchForm.total"
        :page-size="searchForm.pageSize"
        style="margin-top: 20px; float: right"
        @update:page="changePage"
      /> -->
      <my-pagination v-model:search-form="searchForm" @init="init"></my-pagination>
    </my-card>
    <my-dialog
      :title="addForm.id ? '编辑产品' : '新增产品'"
      width="750px"
      :show="showDialog"
      @cancel="cancel"
      @submit="submit"
    >
      <template #content>
        <div>
          <n-form ref="addFormRef" :rules="rules" :model="addForm" label-placement="left" label-width="auto">
            <n-grid :cols="24" :x-gap="20">
              <n-form-item-grid-item label="产品分类" :span="12" path="productTypeName">
                <n-tree-select
                  v-model:value="addForm.productTypeId"
                  :options="typeOptions"
                  placeholder="请输入产品分类"
                  @update:value="
                    (value, item: any) => {
                      console.log(value, item);
                      addForm.productTypeName = item.label;
                    }
                  "
                />
              </n-form-item-grid-item>
              <n-form-item-grid-item label="产品型号" :span="12" path="productSize">
                <n-select
                  v-model:value="addForm.productSize"
                  filterable
                  placeholder="请选择主型号"
                  :options="dictionaryOptions"
                  @update:value="dictionaryHandleChange"
                />
              </n-form-item-grid-item>
              <n-form-item-grid-item label="产品名称" :span="24" path="productTypeId">
                <n-input v-model:value="addForm.productName" placeholder="请输入产品名称" />
              </n-form-item-grid-item>
              <n-form-item-grid-item label="产品规格" :span="24" path="specification">
                <n-input v-model:value="addForm.specification" placeholder="请输入产品规格" />
              </n-form-item-grid-item>
              <n-form-item-grid-item label="产品批号" :span="24" path="batchNumber">
                <n-input v-model:value="addForm.batchNumber" placeholder="请输入产品批号" />
              </n-form-item-grid-item>
              <n-form-item-grid-item label="产品轴型" :span="12" path="axialType">
                <n-input v-model:value="addForm.axialType" placeholder="请输入产品轴型" />
              </n-form-item-grid-item>
              <n-form-item-grid-item label="产品系数" :span="12" path="coefficient">
                <n-input v-model:value="addForm.coefficient" placeholder="请输入产品系数" />
              </n-form-item-grid-item>
              <n-form-item-grid-item label="物料代码" :span="12">
                <n-input v-model:value="addForm.materialCode" placeholder="请输入物料代码" />
              </n-form-item-grid-item>
              <n-form-item-grid-item label="重量单位" :span="12" path="weightMeasureName">
                <n-select
                  v-model:value="addForm.weightMeasureId"
                  :options="unitOptions"
                  placeholder="请输入重量单位"
                  @update:value="
                    (val, item:any) => {
                      addForm.weightMeasureName = item.label;
                    }
                  "
                />
              </n-form-item-grid-item>
              <n-form-item-grid-item label="备注" :span="24">
                <n-input v-model:value="addForm.remark" type="textarea" placeholder="请输入输入备注" />
              </n-form-item-grid-item>
            </n-grid>
          </n-form>
        </div>
      </template>
    </my-dialog>
    <my-dialog
      :show="uploadShow"
      :title="'导入产品信息'"
      width="500px"
      @cancel="uploadShow = false"
      @submit="submitUpload"
    >
      <template #content>
        <div>
          <n-form ref="formModel" style="width: 300px" label-placement="left" label-align="left" :label-width="80">
            <n-form-item label="上传文件">
              <n-upload
                :show-file-list="true"
                :style="{ width: '220px' }"
                :default-upload="true"
                :max="1"
                @change="changeUpload"
              >
                <n-button :style="{ width: '220px' }">选择文件</n-button>
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
  name: 'ListMange'
});
import type { Ref } from 'vue';
import { ref, onMounted, getCurrentInstance } from 'vue';
import { useMessage } from 'naive-ui';
import type { DataTableColumns, FormInst } from 'naive-ui';
import { useLoading, useWarning, useBoolean } from '@/hooks';
import { listToTree, deepClone, createRequiredFormRule } from '@/utils';
import { getAllClassifyList } from '@/service/api/md/classify';
import { getAllUnitList } from '@/service/api/md/unit';
import { getProductList, deleteProduct, addProduct, editProduct, addProductUpload } from '@/service/api/md/list';
const { loading, startLoading, endLoading } = useLoading();
const warning = useWarning();
const message = useMessage();
const { bool: showDialog, setFalse: closeDialog, setTrue: openDialog } = useBoolean();
const addFormRef = ref<FormInst | null>(null);
const routeOptions = ref<{ label: string; value: number }[]>([]);

const { proxy } = getCurrentInstance() as any;
const { product_model_standard } = proxy.useDict('product_model_standard');
// 读取字典值
// const { md_batch_number } = proxy.useDict('md_batch_number');
const dictionaryOptions = product_model_standard;
// const batchNumberOptions = md_batch_number;
const uploadFormData = ref<any | null>(null);
const addForm = ref<List.AddForm>({
  processRouteName: '',
  productTypeId: '',
  productSize: null,
  productSizeInternational: '',
  processRouteId: '',
  specification: '',
  productName: '',
  weightMeasureId: null,
  remark: '',
  ifEnable: 1,
  batchNumber: null,
  axialType: '',
  coefficient: '',
  materialCode: ''
});
const rules = {
  productTypeId: createRequiredFormRule('请选择产品类型'),
  productSize: createRequiredFormRule('请输入规格型号'),
  specification: createRequiredFormRule('请输入规格型号'),
  productName: createRequiredFormRule('请输入产品名称'),
  weightMeasureId: createRequiredFormRule('请输入重量单位'),
  batchNumber: createRequiredFormRule('请输入产品批号'),
  axialType: createRequiredFormRule('请输入产品轴型'),
  coefficient: createRequiredFormRule('请输入产品系数')
};
function cancel() {
  closeDialog();
  addForm.value = {
    processRouteName: '',
    productTypeId: '',
    productName: '',
    productSize: null,
    productSizeInternational: '',
    specification: '',
    processRouteId: '',
    weightMeasureId: null,
    remark: '',
    ifEnable: 1,
    batchNumber: null,
    axialType: '',
    coefficient: '',
    materialCode: ''
  };
}
// 导入
const uploadShow = ref<boolean>(false);
function changeUpload(files) {
  // eslint-disable-next-line no-console
  console.log('files ==> ', files);
  if (files.fileList.length === 0) {
    uploadFormData.value = null;
    return;
  }
  uploadFormData.value = files.file;
  // eslint-disable-next-line no-console
  console.log('uploadFormData.value ==> ', uploadFormData.value);
}
function submitUpload() {
  if (uploadFormData.value === null) {
    message.warning('请选择文件进行上传');
    return;
  }
  addProductUpload(uploadFormData.value, {
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  }).then(res => {
    if (res.code === 200) {
      message.success('上传成功');
    }
  });
}
async function submit() {
  if (!addFormRef.value) return;
  addFormRef.value.validate(async errors => {
    if (!errors) {
      routeOptions.value.forEach(item => {
        if (item.value === parseInt(addForm.value.processRouteId as string, 10)) {
          addForm.value.processRouteName = item.label;
        }
      });
      if (!addForm.value.id) {
        await addProduct(addForm.value).then(res => {
          if (res.code === 200) {
            message.success('新增成功');
            cancel();
            init();
          }
        });
      } else {
        await editProduct(addForm.value).then(res => {
          if (res.code === 200) {
            message.success('修改成功');
            cancel();
            init();
          }
        });
      }
    }
  });
}
const searchForm = ref<List.SearchForm>({
  productTypeId: '',
  productName: '',
  specification: '',
  batchNumber: '',
  pageNum: 1,
  pageSize: 10,
  total: 0
});
function dictionaryHandleChange(val, item) {
  addForm.value.productSize = item.label;
  addForm.value.productSizeInternational = val;
}

function handleSearch() {
  init();
}
function handleReset() {
  searchForm.value = {
    productTypeId: '',
    productName: '',
    specification: '',
    batchNumber: '',
    pageNum: 1,
    pageSize: 10,
    total: 0
  };
  init();
}
const columns: Ref<DataTableColumns<List.DataForm>> = ref([
  {
    title: '序号',
    key: 'index',
    align: 'center',
    width: 60,
    render: (_row, index) => (searchForm.value.pageNum - 1) * searchForm.value.pageSize + index + 1
  },
  {
    title: '产品分类',
    key: 'productTypeName',
    align: 'center',
    width: 80,
    ellipsis: {
      tooltip: true
    }
  },
  {
    title: '产品名称',
    key: 'productName',
    align: 'center',
    width: 160,
    ellipsis: {
      tooltip: true
    }
  },
  {
    title: '产品编码',
    ellipsis: {
      tooltip: true
    },
    key: 'productCode',
    align: 'center',
    width: 150
  },
  {
    title: '产品型号',
    ellipsis: {
      tooltip: true
    },
    key: 'productSize',
    align: 'center',
    width: 120
  },
  {
    title: '国际型号',
    ellipsis: {
      tooltip: true
    },
    key: 'productSizeInternational',
    align: 'center',
    width: 120
  },
  {
    title: '产品规格',
    ellipsis: {
      tooltip: true
    },
    key: 'specification',
    align: 'center',
    width: 120
  },
  {
    title: '产品批号',
    ellipsis: {
      tooltip: true
    },
    key: 'batchNumber',
    align: 'center',
    width: 120
  },
  {
    title: '产品轴型',
    ellipsis: {
      tooltip: true
    },
    key: 'axialType',
    align: 'center',
    width: 80
  },
  {
    title: '产品系数',
    ellipsis: {
      tooltip: true
    },
    key: 'coefficient',
    align: 'center',
    width: 80
  },
  {
    title: '物料代码',
    ellipsis: {
      tooltip: true
    },
    key: 'materialCode',
    align: 'center',
    width: 140
  },
  {
    title: '单位',
    ellipsis: {
      tooltip: true
    },
    key: 'weightMeasureName',
    align: 'center',
    width: 80
  },
  // {
  //   title: '创建时间',
  //   key: 'createTime',
  //   width: 200
  // },
  {
    title: '备注',
    ellipsis: {
      tooltip: true
    },
    align: 'center',
    key: 'remark',
    width: 100
  },
  {
    title: '操作',
    key: 'action',
    align: 'center',
    fixed: 'right',
    width: 160,
    render(row) {
      return (
        <div>
          <n-button size="tiny" type="info" class="mr-5px" onClick={() => handleEdit(row)}>
            <icon-tdesign-edit class="mr-1px text-15px " />
            编辑
          </n-button>
          <n-button
            size="tiny"
            type="error"
            class="mr-5px"
            onClick={() => {
              warning.warn(() => {
                deleteProduct({ id: row.id }).then(res => {
                  if (res.code === 200) {
                    message.success('删除成功');
                    init();
                  } else {
                    message.error('删除失败');
                  }
                });
              });
            }}
          >
            <icon-tdesign-delete class="mr-1px text-15px " />
            删除
          </n-button>
        </div>
      );
    }
  }
]);
// function optionsToObject(options: { label: string; value: string }[]) {
//   const obj = {};
//   options.forEach(item => {
//     obj[item.value] = item.label;
//   });
//   return obj;
// }
const data = ref<List.DataForm[]>([]);
function handleEdit(row: List.DataForm) {
  addForm.value = deepClone(row);
  openDialog();
}

const typeOptions = ref<{ label: string; value: string }[]>([]);
const unitOptions = ref<{ label: string; value: string }[]>([]);
async function init() {
  // typeOptions.value.splice(0, typeOptions.value.length);
  startLoading();
  await getProductList(searchForm.value).then(res => {
    data.value = res.rows;
    searchForm.value.total = res.total;
  });

  endLoading();
}
onMounted(() => {
  init();
  // 获取单位Options
  getAllUnitList().then(res => {
    res.data.forEach(item => {
      unitOptions.value.push({
        label: item.measureName,
        value: item.id
      });
    });
  });
  // 获取类型Options
  getAllClassifyList({ pageNum: 1 }).then(res => {
    const list: any[] = [];
    res.data.forEach(item => {
      list.push({
        id: item.id,
        parentTypeId: item.parentTypeId,
        key: item.id,
        label: item.typeName
      });
    });
    typeOptions.value = listToTree(list, 'id', 'parentTypeId');
  });
});
</script>

<style lang="scss" scoped></style>
