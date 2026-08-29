<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item label="辅料类型">
          <n-tree-select
            v-model:value="searchForm.itemTypeId"
            :options="OptionsData"
            label-field="itemTypeName"
            key-field="id"
            style="width: 180px"
          ></n-tree-select>
          <!-- <n-input v-model:value="searchForm.itemTypeId" placeholder="请输入物料类型" /> -->
        </n-form-item>
        <n-form-item label="辅料型号">
          <n-input v-model:value="searchForm.model" placeholder="请输入辅料型号"></n-input>
        </n-form-item>
        <n-form-item>
          <component
            :is="
              useSearchBtn(
                () => {
                  handleSearch();
                },
                () => {
                  handleReset();
                }
              )
            "
          ></component>
        </n-form-item>
      </n-form>
    </my-card>
    <my-card title="辅料列表">
      <template #right>
        <div>
          <component
            :is="
              useAddBtn(() => {
                dialogFlag = true;
                flag = true;
              })
            "
            style="margin-right: 15px"
          ></component>
          <CxColumns v-model:columns="columns" size="small" />
        </div>
      </template>
      <n-data-table
        :data="data"
        :loading="loading"
        :row-key="rowKey"
        :columns="columns"
        @update-checked-row-keys="handleCheck"
      ></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="init"></my-pagination>
    </my-card>
    <my-dialog :title="flag ? '新增辅料' : '编辑辅料'" :show="dialogFlag" @cancel="handleCancel" @submit="handleSubmit">
      <template #content>
        <div>
          <n-form
            ref="addFormRef"
            label-placement="left"
            label-width="100px"
            label-align="left"
            :model="addForm"
            :rules="rules"
          >
            <n-form-item label="物料类型" path="itemTypeName">
              <n-tree-select
                v-model:value="addForm.itemTypeId"
                style="width: 180px"
                :options="OptionsData"
                label-field="itemTypeName"
                key-field="id"
                @update:value="handleChange"
              ></n-tree-select>
              <!-- <n-input v-model:value="addForm.itemTypeId" placeholder="请输入物料类型" /> -->
            </n-form-item>
            <n-form-item label="型号" :span="15" path="model">
              <n-input v-model:value="addForm.model" placeholder="请输入型号"></n-input>
            </n-form-item>
            <n-form-item label="主型号" :span="15" path="model">
              <n-select
                v-model:value="addForm.parentModel"
                filterable
                placeholder="请选择主型号"
                :options="typeOptions"
              />
            </n-form-item>
            <n-form-item label="重量" :span="15" path="weight">
              <n-input-number v-model:value="addForm.weight" placeholder="请输入重量"></n-input-number>
            </n-form-item>
            <n-form-item v-if="addForm.itemTypeName === '线盘'" label="满盘重量" :span="15" path="attr2">
              <n-input-number v-model:value="addForm.attr2" placeholder="请输入满盘重量"></n-input-number>
            </n-form-item>
            <n-form-item label="单位" :span="15" path="measureId">
              <n-select
                v-model:value="addForm.measureId"
                class="w-180px"
                :options="unitOptions"
                placeholder="请选择单位"
              />
            </n-form-item>
          </n-form>
        </div>
      </template>
    </my-dialog>
  </div>
</template>

<script setup lang="tsx">
import type { Ref } from 'vue';
import { ref, onMounted, getCurrentInstance } from 'vue';
import type { FormInst, DataTableColumns } from 'naive-ui';
import { useMessage } from 'naive-ui';
import { fetchItemTypeList } from '@/service';
import { getAccessoryList, deleteAccessory, editAccessory, addAccessory } from '@/service/api/md/accessory';
import { getAllUnit } from '@/service/api/md/unit/index';
import { useAddBtn, useEditBtn, useDelBtn, useSearchBtn } from '~/src/hooks/common/useBtn';
import { useLoading } from '~/src/hooks/index';
const { loading, startLoading, endLoading } = useLoading();
const message = useMessage();

const { proxy } = getCurrentInstance() as any;
const { wire_disc_parent } = proxy.useDict('wire_disc_parent');

const typeOptions = wire_disc_parent;

type searchType = {
  itemTypeId: string | null;
  model: string;
  pageNum: number;
  pageSize: number;
  total: number;
};
const OptionsData = ref<ApiItemTypeManagement.ItemType[]>([]);
const flag = ref<boolean>(true);
const data = ref<DataType[]>([]);
const searchForm = ref<searchType>({
  itemTypeId: null,
  model: '',
  pageNum: 1,
  pageSize: 10,
  total: 0
});
const rules = ref({});
type addFormType = {
  itemTypeId: string | null;
  itemTypeName: string;
  model: string;
  parentModelId: string | null;
  parentModel: string;
  weight: number;
  measureId: string | null;
  measureName: string;
  attr2: number;
};
const addForm = ref<addFormType>({
  itemTypeId: null,
  itemTypeName: '',
  model: '',
  parentModelId: null,
  parentModel: '',
  weight: 0,
  measureId: null,
  measureName: '',
  attr2: 0
});
const addFormRef = ref<FormInst | null>(null);
function handleSearch() {
  searchForm.value.pageNum = 1;
  init();
}
function handleReset() {
  searchForm.value = {
    itemTypeId: null,
    model: '',
    pageNum: 1,
    pageSize: 10,
    total: 0
  };
  init();
}
type DataType = {
  id?: number;
  itemTypeName: string;
  model: string;
  measureId: string | null;
  measureName: string;
};
const unitOptions = ref<{ value: string; label: string }[]>([]);
const rowKey = (row: any) => row.id;
const columns: Ref<DataTableColumns<DataType>> = ref([
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
    title: '辅料编码',
    align: 'center',
    key: 'attr1'
  },
  {
    title: '辅料类型',
    align: 'center',
    key: 'itemTypeName'
  },
  {
    title: '型号',
    align: 'center',
    key: 'model'
  },
  {
    title: '主型号',
    align: 'center',
    key: 'parentModel'
  },
  {
    title: '重量',
    align: 'center',
    key: 'weight'
  },
  {
    title: '单位',
    align: 'center',
    key: 'measureName'
  },
  {
    title: '操作',
    align: 'center',
    key: 'actions',
    width: 200,
    render: row => {
      return [
        useEditBtn(() => {
          handleEdit(row);
        }, 'tiny'),
        useDelBtn(() => {
          deleteAccessory(row).then(res => {
            if (res.code === 200) {
              message.success('删除成功');
              init();
            }
          });
        }, 'tiny')
      ];
    }
  }
]);

const checked = ref<(string | number)[]>([]);
const handleCheck = (rowKeys: (string | number)[]) => {
  console.log('rowKeys ==> ', rowKeys);

  checked.value = rowKeys;
};

function setTableData(list: ApiItemTypeManagement.ItemType[]) {
  OptionsData.value = list;
}
// 弹窗
const dialogFlag = ref<boolean>(false);
const handleCancel = () => {
  dialogFlag.value = false;
  addForm.value = {
    itemTypeId: null,
    itemTypeName: '',
    model: '',
    parentModelId: null,
    parentModel: '',
    weight: 0,
    measureId: null,
    measureName: '',
    attr2: 0
  };
};
async function handleSubmit() {
  await addFormRef.value?.validate(errors => {
    if (!errors) {
      if (flag.value) {
        addAccessory(addForm.value).then((res: any) => {
          if (res.code === 200) {
            message.success('添加成功');
            handleCancel();
            init();
          }
        });
      } else {
        editAccessory(addForm.value).then((res: any) => {
          if (res.code === 200) {
            message.success('修改成功');
            handleCancel();
            init();
          }
        });
      }
    }
  });
}
function handleEdit(row) {
  dialogFlag.value = true;
  flag.value = false;
  addForm.value = deepClone(row);
  if (row.itemTypeName === '线盘') {
    rules.value = {
      itemTypeId: [
        {
          required: true,
          message: '请选择辅料类型'
        }
      ],
      model: [
        {
          required: true,
          message: '请输入型号'
        }
      ],
      measureId: [
        {
          required: true,
          message: '请选择单位'
        }
      ],
      attr2: [
        {
          required: true,
          message: '请输入满盘重量'
        }
      ]
    };
  } else {
    rules.value = {
      itemTypeId: [
        {
          required: true,
          message: '请选择辅料类型'
        }
      ],
      model: [
        {
          required: true,
          message: '请输入型号'
        }
      ],
      measureId: [
        {
          required: true,
          message: '请选择单位'
        }
      ]
    };
  }
}
function handleChange(val, item) {
  addForm.value.itemTypeId = val;
  addForm.value.itemTypeName = item.itemTypeName;
  if (val === 24) {
    rules.value = {
      itemTypeId: [
        {
          required: true,
          message: '请选择辅料类型'
        }
      ],
      model: [
        {
          required: true,
          message: '请输入型号'
        }
      ],
      measureId: [
        {
          required: true,
          message: '请选择单位'
        }
      ],
      attr2: [
        {
          required: true,
          message: '请输入满盘重量'
        }
      ]
    };
  } else {
    rules.value = {
      itemTypeId: [
        {
          required: true,
          message: '请选择辅料类型'
        }
      ],
      model: [
        {
          required: true,
          message: '请输入型号'
        }
      ],
      measureId: [
        {
          required: true,
          message: '请选择单位'
        }
      ]
    };
  }
}
// 深度clone
function deepClone(obj: any) {
  return JSON.parse(JSON.stringify(obj));
}
function init() {
  startLoading();
  data.value = [];
  getAccessoryList(searchForm.value).then(res => {
    data.value = res.rows;
    searchForm.value.total = res.total;
  });
  endLoading();
}
onMounted(async () => {
  init();
  const typeData = await fetchItemTypeList({});
  if (typeData) {
    setTimeout(() => {
      setTableData(typeData.data);
    }, 100);
  }
  getAllUnit().then(res => {
    if (res.code === 200) {
      unitOptions.value = [];
      res.data.forEach((item: any) => {
        unitOptions.value.push({
          label: item.measureName,
          value: item.id
        });
      });
    }
  });
});
</script>

<style scoped></style>
