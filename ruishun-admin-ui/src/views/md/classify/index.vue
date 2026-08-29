<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item label="分类名称">
          <n-tree-select v-model:value="searchForm.id" class="w-180px" :options="options" placeholder="分类名称" />
        </n-form-item>
        <n-form-item label="分类编码">
          <n-input v-model:value="searchForm.typeCode" placeholder="分类编码" />
        </n-form-item>
        <n-form-item label="是否启用">
          <n-select
            v-model:value="searchForm.ifEnable"
            class="w-180px"
            :options="[
              { label: '是', value: 1 },
              { label: '否', value: 0 }
            ]"
          />
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
    <my-card title="分类列表">
      <template #right>
        <div class="flex-center">
          <cx-columns v-model:columns="columns" size="small"></cx-columns>
          <n-button
            size="small"
            class="ml-5px"
            type="primary"
            @click="
              () => {
                open();
              }
            "
          >
            <icon-ic-round-plus class="mr-4px text-20px" />
            新增主分类
          </n-button>
        </div>
      </template>
      <n-data-table :loading="loading" :row-key="rowKey" :data="data" :columns="columns"></n-data-table>
    </my-card>
    <my-dialog :show="show" :title="addForm.id ? '编辑分类' : '新增分类'" @cancel="cancel" @submit="submit">
      <template #content>
        <div>
          <n-form ref="formRef" label-placement="left" :model="addForm" :rules="rules">
            <n-form-item
              v-if="(addForm.parentTypeId || addChildFlag) && !editFlag"
              label="父级分类"
              path="parentTypeId"
            >
              <n-select v-model:value="addForm.parentTypeId" disabled class="w-180px" :options="options" />
            </n-form-item>
            <n-form-item label="分类名称" path="typeName">
              <n-input v-model:value="addForm.typeName" placeholder="分类名称" />
            </n-form-item>

            <n-form-item label="分类编码" path="typeCode">
              <n-input v-model:value="addForm.typeCode" placeholder="分类编码" />
            </n-form-item>
            <n-form-item label="是否启用" path="ifEnable">
              <n-select
                v-model:value="addForm.ifEnable"
                class="w-180px"
                :options="[
                  { label: '是', value: 1 },
                  { label: '否', value: 0 }
                ]"
              />
            </n-form-item>
          </n-form>
        </div>
      </template>
    </my-dialog>
  </div>
</template>

<script setup lang="tsx">
defineOptions({
  name: 'ClassifyManage'
});
import type { Ref } from 'vue';
import { ref, onMounted } from 'vue';
import { useMessage } from 'naive-ui';
import type { DataTableColumns, FormInst } from 'naive-ui';
import { listToTree, deepClone, createRequiredFormRule } from '@/utils';
import {
  getAllClassifyList,
  deleteClassify,
  // getClassifyTree,
  addClassifyWay,
  editClassifyWay
} from '@/service/api/md/classify';
import { useLoading, useWarning, useBoolean } from '~/src/hooks';

const options = ref<{ label: string; value: number | string; children?: { label: string; value: number }[] }[]>([]);
const addChildFlag = ref<boolean>(false);
const formRef = ref<FormInst | null>(null);
const { bool: show, setTrue: open, setFalse: close } = useBoolean();
const warning = useWarning();
const { loading, startLoading, endLoading } = useLoading();
const message = useMessage();
const searchForm = ref<Type.searchForm>({
  id: null,
  typeCode: '',
  ifEnable: null,
  pageNum: 1,
  pageSize: 10,
  total: 0
});
function handleSearch() {
  init();
}
function handleReset() {
  searchForm.value = {
    id: null,
    typeCode: '',
    ifEnable: null,
    pageNum: 1,
    pageSize: 10,
    total: 0
  };
  init();
}
const addForm = ref<Type.addForm>({
  typeName: '',
  typeCode: '',
  parentTypeId: null,
  ifEnable: 1
});
const rules = {
  typeName: createRequiredFormRule('分类名称不能为空'),
  typeCode: createRequiredFormRule('分类编码不能为空'),
  parentTypeId: createRequiredFormRule('父级分类不能为空'),
  ifEnable: createRequiredFormRule('请选择是否启用')
};

async function submit() {
  if (!formRef.value) return;
  formRef.value.validate(error => {
    if (!error) {
      if (addForm.value.id) {
        editClassifyWay(addForm.value).then(res => {
          if (res.code === 200) {
            message.success('编辑成功');
            cancel();
            init();
          }
        });
      } else {
        addClassifyWay(addForm.value)
          .then(res => {
            if (res.code === 200) {
              message.success('新增成功');
              cancel();
              init();
            }
          })
          .catch(() => {
            message.error('新增失败');
          });
      }
    }
  });

  // await getClassifyTree().then(res => {
  //   if (res.code === 200) {
  //     console.log(res.data, 'ccc');
  //     options.value.splice(0, options.value.length);
  //     res.data.forEach((item: any) => {
  //       options.value.push({
  //         label: item.lable,
  //         value: item.id
  //       });
  //     });
  //   }
  // });
  addChildFlag.value = false;
}
const columns: Ref<DataTableColumns<Type.DataForm>> = ref([
  {
    title: '分类名称',
    key: 'typeName'
  },
  {
    title: '分类编码',
    key: 'typeCode'
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
            row.ifEnable = val;
            editClassifyWay(row).then(res => {
              if (res.code === 200) {
                message.success(res.msg);
                init();
              }
            });
          }}
          onUnchecked={val => {
            row.ifEnable = val;
            editClassifyWay(row).then(res => {
              if (res.code === 200) {
                message.success(res.msg);
                init();
              }
            });
          }}
        />
      );
      // return <n-tag type={row.ifEnable === 1 ? 'success' : 'error'}>{row.ifEnable === 1 ? '是' : '否'}</n-tag>;
    }
  },
  {
    title: '操作',
    key: 'action',
    width: 260,
    render(row) {
      return (
        <div>
          <n-button size="tiny" type="info" class="mr" onClick={() => handleEdit(row)}>
            <icon-tdesign-edit class="mr-1px text-15px " />
            编辑
          </n-button>
          <n-button size="tiny" type="success" class="mr" onClick={() => handleAddChild(row)}>
            <icon-ic-round-plus class="mr-1px text-15px " />
            新增子类
          </n-button>
          <n-button
            size="tiny"
            type="error"
            onClick={() => {
              warning.warn(() => {
                deleteClassify(row.id).then(res => {
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
const editFlag = ref<boolean>(false);
function handleEdit(row: any) {
  editFlag.value = true;
  addForm.value = deepClone(row);
  open();
}
function cancel() {
  editFlag.value = false;
  close();
  addForm.value = {
    typeName: '',
    typeCode: '',
    parentTypeId: null,
    ifEnable: 1
  };
  addChildFlag.value = false;
}
function handleAddChild(row: any) {
  // addForm.value = deepClone(row);
  addChildFlag.value = true;
  addForm.value.parentTypeId = row.id;
  open();
}
const data = ref<Type.DataForm[]>([]);
function rowKey(row: any) {
  return row.id;
}
function init() {
  startLoading();
  // getClassifyList(searchForm.value).then(res => {
  //   data.value = listToTree(res.rows, 'id', 'parentTypeId');
  //   console.log(data.value);
  //   searchForm.value.total = res.total;
  //   endLoading();
  // });
  getAllClassifyList(searchForm.value).then(res => {
    const list: any[] = [];
    res.data.forEach(item => {
      list.push({
        id: item.id,
        parentTypeId: item.parentTypeId,
        key: item.id,
        label: item.typeName
      });
    });
    data.value = listToTree(res.data, 'id', 'parentTypeId');
    // data.value.forEach(item => {
    //   options.value.push({
    //     label: item.typeName,
    //     value: item.id
    //   });
    // });
    searchForm.value.total = res.total;
    options.value = listToTree(list, 'id', 'parentTypeId');
    // getClassifyTree().then(response => {
    //   if (response.code === 200) {
    //     options.value.splice(0, options.value.length);
    //     response.data.forEach((item: any) => {
    //       options.value.push({
    //         label: item.lable,
    //         value: item.id,
    //         children: item.children
    //       });
    //     });
    //   }
    // });
    getAllClassifyList({ pageNum: 1, pageSize: 100 }).then(response => {
      const tempList: any[] = [];
      response.data.forEach(item => {
        tempList.push({
          id: item.id,
          parentTypeId: item.parentTypeId,
          key: item.id,
          label: item.typeName
        });
      });
      //   const temp = listToTree(tempList, 'id', 'parentTypeId');
      //   console.log(data.value, 'cc');
      //   temp.forEach((item: any) => {
      //     options.value.push({
      //       label: item.typeName,
      //       value: item.id
      //     });
      //   });
      options.value = listToTree(tempList, 'id', 'parentTypeId');
    });
    endLoading();
  });
}
onMounted(() => {
  init();
});
</script>

<style scoped lang="scss"></style>
