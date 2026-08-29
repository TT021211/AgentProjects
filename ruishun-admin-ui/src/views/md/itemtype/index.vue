<template>
  <div class="h-full">
    <my-card title="搜索条件" search>
      <n-form
        ref="formRef"
        inline
        :model="formValue"
        :rules="rules"
        label-placement="top"
        label-align="center"
        label-width="auto"
        require-mark-placement="right-hanging"
      >
        <n-form-item label="分类名称" path="itemtype.itemTypeName">
          <n-input v-model:value="formValue.itemTypeName" placeholder="请输入分类名称" />
        </n-form-item>
        <n-form-item label="是否启用" path="itemtype.ifEnable">
          <n-select
            v-model:value="formValue.ifEnable"
            class="w-180px"
            placeholder="选择是或否"
            :options="sourceOption"
            clearable
          />
        </n-form-item>
        <n-form-item>
          <n-button type="primary" class="mr-10px" @click="handleSearchClick">
            <icon-ic-round-search class="mr-4px text-20px" />
            搜索
          </n-button>
          <n-button @click="handleResetClick">
            <icon-ic-round-refresh class="mr-4px text-20px" />
            重置
          </n-button>
        </n-form-item>
      </n-form>
    </my-card>
    <my-card title="物料列表">
      <template #right>
        <div class="flex-center">
          <n-space>
            <n-switch v-model:value="expandedSwitchshow" class="mr-5px" @update:value="handleSwitchChange">
              <template #checked>展开</template>
              <template #unchecked>折叠</template>
            </n-switch>
          </n-space>
          <cx-columns v-model:columns="columns" size="small"></cx-columns>
          <n-button class="ml-5px" type="primary" size="small" attr-type="button" @click="handleMainTypeAdd">
            添加主分类
          </n-button>
        </div>
      </template>

      <n-data-table
        :loading="loading"
        :columns="columns"
        :data="tableData"
        :expanded-row-keys="expandedRows"
        :on-update:expanded-row-keys="changeExpandedRow"
        striped
      />
      <action-modal
        v-model:visible="visible"
        :type="modalType"
        :parent-type-id="parentTypeId"
        :parent-form-value="formValue"
        :edit-data="editData"
        @refresh-table-data="handleSearchClick"
      />
    </my-card>
  </div>
</template>

<script lang="tsx" setup>
import type { Ref } from 'vue';
import { ref } from 'vue';
import { type DataTableColumns } from 'naive-ui';
import { fetchItemTypeList, deleteItemType, updateItemType } from '@/service';
import { useBoolean, useLoading } from '@/hooks';
import ActionModal from './components/action-modal.vue';
import type { ModalType } from './components/action-modal.vue';
const { loading, startLoading, endLoading } = useLoading(false);
const { bool: visible, setTrue: openModal } = useBoolean();

let allRowKeysArr: string[] = [];
const expandedRows = ref(['200']);
const expandedSwitchshow = ref(true);
const parentTypeId = ref(0);

async function getTableData(params: object) {
  startLoading();
  const data = await fetchItemTypeList(params);
  if (data) {
    setTimeout(() => {
      setTableData(data.data);
      endLoading();
      allRowKeysArr = data.allParentIDArr;
      expandedRows.value = data.allParentIDArr;
    }, 100);
  }
}

const tableData = ref<ApiItemTypeManagement.ItemType[]>([]);
function setTableData(data: ApiItemTypeManagement.ItemType[]) {
  tableData.value = data;
}

const formValue = ref({ itemTypeName: '', enableFlag: null, ifEnable: null });

const sourceOption = [
  {
    label: `是`,
    value: 1
  },
  {
    label: `否`,
    value: 0
  }
];

const rules = {
  itemtype: {
    // name: {
    //   required: false,
    //   message: '请输入分类名称',
    //   trigger: 'blur'
    // },
    // enable_flag: {
    //   required: false,
    //   message: '请选择是否启用',
    //   trigger: ['input', 'blur']
    // }
  }
};

// const labelStyle = reactive({
//   backgroundColor: '#F8F8F8',
//   border: '1px solid #E0E0E6',
//   borderRight: 'none',
//   marginRight: '-2px',
//   padding: '0 15px',
//   width: '140px'
// });

function changeStatus(row: ApiItemTypeManagement.ItemType, val: number) {
  row.ifEnable = val;
  updateItemType(row).then(res => {
    if (res.code === 200) {
      handleSearchClick();
    }
  });
}
const columns: Ref<DataTableColumns<ApiItemTypeManagement.ItemType>> = ref([
  {
    title: '分类',
    key: 'itemTypeName'
  },
  {
    title: '排序',
    key: 'orderNum',
    align: 'center'
  },
  {
    title: '分类编号',
    key: 'itemTypeCode',
    align: 'center'
  },
  {
    title: '是否启用',
    key: 'enableFlag',
    align: 'center',
    render: row => {
      // return <NTag type={tagTypes[row.ifEnable]}>{enableFlagLabels[row.ifEnable]}</NTag>;
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
        ></my-switch>
      );
    }
  },
  {
    title: '创建时间',
    key: 'createTime',
    align: 'center'
  },
  {
    title: '操作',
    key: 'actions',
    align: 'center',
    render: row => {
      return (
        <n-space justify={'center'}>
          <n-button attr-type="button" type="primary" size="small" onClick={() => handleRowAdd(row)}>
            <icon-mdi-add />
            新增
          </n-button>
          <n-button attr-type="button" size={'small'} onClick={() => handleEditTable(row)}>
            <icon-mdi-edit />
            编辑
          </n-button>
          <n-popconfirm onPositiveClick={() => handleDeleteItemType(row.id)}>
            {{
              default: () => '确认删除',
              trigger: () => (
                <n-button type="warning" size={'small'}>
                  <icon-mdi-delete />
                  删除
                </n-button>
              )
            }}
          </n-popconfirm>
        </n-space>
      );
    }
  }
]);

/** 对比两个数组是否相等，用于判断是否已完全折叠展开 */
function compareArr(a: string[], b: string[]) {
  const newA = new Set(a);
  const newB = new Set(b);
  if (a.length === b.length) {
    const differenceABSet = Array.from(new Set([...newA].filter(x => !newB.has(x))));
    // console.log('两数组差集：');
    // console.log(differenceABSet);
    if (differenceABSet.length === 0) {
      // console.log('两数组相等');
      return true;
    }
    // console.log('两数组不相等');
    return false;
  }
  return false;
}

function changeExpandedRow(rows: string[]) {
  if (!compareArr(allRowKeysArr, rows)) {
    expandedSwitchshow.value = false;
  } else {
    expandedSwitchshow.value = true;
  }
  expandedRows.value = rows;
}

function handleSwitchChange() {
  if (expandedSwitchshow.value) {
    // console.log('展开');
    expandedRows.value = allRowKeysArr;
    // console.log(allRowKeysArr);
  } else {
    // console.log('折叠');
    expandedRows.value = [];
  }
}

function handleSearchClick() {
  expandedSwitchshow.value = true;
  getTableData(formValue.value);
}

function handleResetClick() {
  formValue.value.itemTypeName = '';
  formValue.value.enableFlag = null;
  getTableData({});
}

/** 弹出新增窗口 */
function handleMainTypeAdd() {
  // const findItem = tableData.value.find(item => item.itemTypeId === rowId);
  // console.log(findItem);
  // if (findItem) {
  //   setEditData(findItem);
  // }
  parentTypeId.value = 0;
  setModalType('add');
  openModal();
}

/** 弹出新增窗口 */
function handleRowAdd(row: ApiItemTypeManagement.ItemType) {
  // const findItem = tableData.value.find(item => item.itemTypeId === rowId);
  // console.log(findItem);
  // if (findItem) {
  //   setEditData(findItem);
  // }
  parentTypeId.value = row.id;
  setModalType('add');
  openModal();
}

/** 弹出编辑窗口 */
function handleEditTable(row: ApiItemTypeManagement.ItemType) {
  // const findItem = tableData.value.find(item => item.itemTypeId === rowId);
  // console.log(findItem);
  // if (findItem) {
  //   setEditData(findItem);
  // }
  parentTypeId.value = row.parentTypeId;
  setEditData(row);
  setModalType('edit');
  openModal();
}
/** 弹窗编辑的内容 */
const editData = ref<ApiItemTypeManagement.ItemType | null>(null);
function setEditData(data: ApiItemTypeManagement.ItemType | null) {
  editData.value = data;
}

/** 设置弹窗内的数据操作类型（新增or编辑） */
const modalType = ref<ModalType>('add');
function setModalType(type: ModalType) {
  modalType.value = type;
}

/** 删除分类 */
async function handleDeleteItemType(id: number) {
  console.log(id);
  if (id > 0) {
    const rs = await deleteItemType(id);
    if (rs.code === 200) {
      window.$message?.success('删除成功');
    } else {
      window.$message?.error(rs.msg);
    }
    handleSearchClick();
  }
}

getTableData({});
</script>

<style>
.width110 {
  width: 110px;
}
</style>
