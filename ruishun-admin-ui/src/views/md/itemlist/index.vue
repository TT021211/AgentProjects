<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item label="物料名称">
          <n-input v-model:value="searchForm.itemName" placeholder="请输入物料名称" />
        </n-form-item>
        <n-form-item label="物料编码">
          <n-input v-model:value="searchForm.itemCode" placeholder="请输入物料名称" />
        </n-form-item>
        <n-form-item label="物料类型">
          <n-tree-select
            v-model:value="searchForm.itemTypeId"
            :options="OptionsData"
            label-field="itemTypeName"
            key-field="id"
            style="width: 180px"
          ></n-tree-select>
          <!-- <n-input v-model:value="searchForm.itemTypeId" placeholder="请输入物料类型" /> -->
        </n-form-item>
        <n-form-item label="单位" style="width: 180px">
          <n-select v-model:value="searchForm.numMeasureId" :options="primaryOptions" placeholder="请输入单位" />
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
    <my-card title="物料列表">
      <template #right>
        <div class="flex-center">
          <cx-columns v-model:columns="columns" size="small"></cx-columns>
          <n-button size="small" type="primary" class="mr-5px ml-5px" @click="setShow">
            <icon-ic-round-plus class="mr-4px text-20px" />
            新增
          </n-button>
          <n-button :loading="exportLoading" size="small" type="warning" @click="exportList">
            <icon-uil:export class="mr-4px text-20px" />
            导出
          </n-button>
        </div>
      </template>
      <n-data-table :loading="loading" :data="data" :columns="columns"></n-data-table>
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
      :title="addForm.id ? '编辑物料' : '新增物料'"
      :show="show"
      width="750px"
      @cancel="cancel"
      @submit="submit"
    >
      <template #content>
        <div>
          <n-form
            ref="addFormRef"
            label-placement="left"
            label-align="left"
            :label-width="100"
            :model="addForm"
            :rules="rules"
          >
            <n-grid :cols="24" :x-gap="20">
              <n-form-item-grid-item label="物料类型" :span="11" path="itemTypeName">
                <n-tree-select
                  v-model:value="addForm.itemTypeId"
                  style="width: 200px"
                  :options="OptionsData"
                  label-field="itemTypeName"
                  key-field="id"
                  @update:value="handleChange"
                ></n-tree-select>
                <!-- <n-input v-model:value="addForm.itemTypeId" placeholder="请输入物料类型" /> -->
              </n-form-item-grid-item>
              <n-form-item-grid-item label="物料名称" :span="11" path="itemName">
                <n-input v-model:value="addForm.itemName" placeholder="请输入物料名称" />
              </n-form-item-grid-item>

              <n-form-item-grid-item label="规格" :span="11" path="specification">
                <n-input v-model:value="addForm.specification" placeholder="请输入规格"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="重量单位" :span="11" path="weightMeasureId">
                <n-select
                  v-model:value="addForm.weightMeasureId"
                  class="w-200px"
                  :options="primaryOptions"
                  placeholder="请输入重量单位"
                  @update:value="weightHandleChange"
                />
              </n-form-item-grid-item>
              <n-form-item-grid-item label="工序名称" :span="11" path="workstageId">
                <n-select
                  v-model:value="addForm.workstageId"
                  class="w-200px"
                  :options="workstageOptions"
                  placeholder="请选择工序名称"
                  @update:value="workstageHandleChange"
                />
              </n-form-item-grid-item>
              <n-form-item-grid-item label="是否启用" :span="11">
                <n-switch v-model:value="addForm.ifEnable" :checked-value="1" :unchecked-value="0" />
              </n-form-item-grid-item>
              <n-form-item-grid-item label="备注" :span="25">
                <n-input v-model:value="addForm.remark" type="textarea" placeholder="请输入备注" />
              </n-form-item-grid-item>
            </n-grid>
          </n-form>
        </div>
      </template>
    </my-dialog>
  </div>
</template>

<script setup lang="tsx">
import type { Ref } from 'vue';
import { ref, onMounted } from 'vue';
import { useMessage } from 'naive-ui';
import type { DataTableColumns, FormInst } from 'naive-ui';
import { fetchItemTypeList } from '@/service';
import { createRequiredFormRule, deepClone } from '@/utils';
import { getAllUnit } from '@/service/api/md/unit/index';
import { getAllTechnologyList } from '@/service/api/technology/technologySetting/index';
import { getMaterialList, addMaterial, editMaterial, deleteMaterial } from '@/service/api/md/itemlist/index';
import { download } from '@/service/request/helpers';
import { useLoading, useWarning, useBoolean } from '~/src/hooks';

const message = useMessage();
const { bool: show, setTrue: setShow, setFalse: setHide } = useBoolean();
const { loading, startLoading, endLoading } = useLoading();
const dialog = useWarning();

const addFormRef = ref<FormInst | null>(null);
const rules = {
  itemName: createRequiredFormRule('请输入物料名称'),
  itemTypeName: createRequiredFormRule('请输入物料类型'),
  workstageId: createRequiredFormRule('请选择工序'),
  specification: createRequiredFormRule('请输入规格')
};
const addForm = ref<Item.AddForm>({
  remark: '',
  itemName: '',
  itemTypeId: null,
  itemTypeName: '',
  workstageId: null,
  workstageName: '',
  specification: '',
  weightMeasureId: null,
  weightMeasureName: '',
  ifEnable: 1
});
function cancel() {
  setHide();
  addForm.value = {
    remark: '',
    itemName: '',
    itemTypeId: null,
    itemTypeName: '',
    workstageId: null,
    workstageName: '',
    weightMeasureId: null,
    weightMeasureName: '',
    ifEnable: 1
  };
}
async function changeStatus(row: any, val: number) {
  row.ifEnable = val;
  await editMaterial(row).then(res => {
    if (res.code === 200) {
      message.success(res.msg);
      cancel();
    }
  });
  init();
}
function handleChange(val, item) {
  addForm.value.itemTypeId = val;
  addForm.value.itemTypeName = item.itemTypeName;
}
function weightHandleChange(val, item) {
  addForm.value.weightMeasureId = val;
  addForm.value.weightMeasureName = item.label;
}
function workstageHandleChange(val, item) {
  addForm.value.workstageId = val;
  addForm.value.workstageName = item.label;
}
function submit() {
  if (!addFormRef.value) return;
  addFormRef.value.validate(errors => {
    if (!errors) {
      if (!addForm.value.id) {
        addMaterial(addForm.value).then(res => {
          if (res.code === 200) {
            message.success(res.msg);
            cancel();
            init();
          }
        });
      } else {
        editMaterial(addForm.value).then(res => {
          if (res.code === 200) {
            message.success(res.msg);
            cancel();
            init();
          }
        });
      }
    }
  });
}
const searchForm = ref<Item.SearchForm>({
  itemCode: '',
  itemName: '',
  itemTypeId: null,
  numMeasureId: null,
  pageNum: 1,
  pageSize: 10,
  total: 0
});
function handleSearch() {
  searchForm.value.pageNum = 1;
  init();
}
function handleReset() {
  searchForm.value = {
    itemCode: '',
    itemName: '',
    itemTypeId: null,
    numMeasureId: '',
    pageNum: 1,
    pageSize: 10,
    total: 0
  };
  init();
}
// function changePage(page: number) {
//   searchForm.value.pageNum = page;
//   init();
// }
const data = ref<Item.List[]>([]);
function init() {
  startLoading();
  getMaterialList(searchForm.value).then(res => {
    data.value = res.rows;
    searchForm.value.total = res.total;
    endLoading();
  });
}
const columns: Ref<DataTableColumns<Item.List>> = ref([
  {
    title: '序号',
    key: 'index',
    align: 'center',
    width: 60,
    render: (_row, index) => (searchForm.value.pageNum - 1) * searchForm.value.pageSize + index + 1
  },
  {
    title: '物料编码',
    key: 'itemCode',
    align: 'center'
  },
  {
    title: '物料名称',
    align: 'center',
    key: 'itemName'
  },
  {
    title: '类型名称',
    align: 'center',
    key: 'itemTypeName'
  },
  {
    title: '规格',
    align: 'center',
    key: 'specification'
  },
  {
    title: '工序名称',
    align: 'center',
    key: 'workstageName'
  },
  {
    title: '单位',
    align: 'center',
    key: 'weightMeasureName'
  },
  {
    title: '是否启用',
    key: 'ifEnable',
    render(row) {
      return (
        <my-switch
          v-model:value={row.ifEnable}
          checked={1}
          unchecked={0}
          onChecked={val => {
            changeStatus(row, val);
          }}
          onUnchecked={val => {
            changeStatus(row, val);
          }}
        />
      );
    }
  },
  {
    title: '备注',
    key: 'remark'
  },
  {
    title: '创建时间',
    key: 'createTime',
    width: 170
  },
  {
    title: '操作',
    key: 'action',
    render(row) {
      return (
        <div>
          <n-button
            type="primary"
            size="small"
            class="mr-10px"
            onClick={() => {
              addForm.value = deepClone(row);
              setShow();
            }}
          >
            <icon-tdesign-edit class="mr-1px text-15px " />
            编辑
          </n-button>
          <n-button
            type="error"
            size="small"
            onClick={() => {
              dialog.warn(() => {
                deleteMaterial(row.id).then(res => {
                  if (res.code === 200) {
                    message.success(res.msg);
                    init();
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
const workstageOptions = ref<any[]>([]);
const exportLoading = ref<boolean>(false);
function exportList() {
  exportLoading.value = true;
  download('/md/mdItem/export', searchForm.value, `itemlist_${new Date().getTime()}.xlsx`, undefined)
    .then(() => {
      exportLoading.value = false;
    })
    .catch(() => {
      exportLoading.value = false;
    });
}
const primaryOptions = ref<{ value: string; label: string }[]>([]);
const weightOptions = ref<{ value: string; label: string }[]>([]);
const OptionsData = ref<ApiItemTypeManagement.ItemType[]>([]);
function setTableData(list: ApiItemTypeManagement.ItemType[]) {
  OptionsData.value = list;
}
onMounted(async () => {
  const typeData = await fetchItemTypeList({});
  if (typeData) {
    setTimeout(() => {
      setTableData(typeData.data);
      // endLoading();
      // allRowKeysArr = typeData.allParentIDArr;
      // expandedRows.value = typeData.allParentIDArr;
    }, 100);
  }
  init();
  getAllTechnologyList().then(res => {
    if (res.code === 200) {
      workstageOptions.value = [];
      res.data.forEach((item: any) => {
        workstageOptions.value.push({
          label: item.workstageName,
          value: item.id
        });
      });
    }
  });
  getAllUnit().then(res => {
    primaryOptions.value = [];
    weightOptions.value = [];
    if (res.code === 200) {
      res.data.forEach((item: any) => {
        primaryOptions.value.push({
          label: item.measureName,
          value: item.id
        });

        weightOptions.value.push({
          label: item.measureName,
          value: item.id
        });
      });
    }
  });
});
</script>

<style scoped lang="scss"></style>
