<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline label-placement="top">
        <n-form-item label="工序名称">
          <n-input v-model:value="searchForm.workstageName" placeholder="请输入工序名称" />
        </n-form-item>
        <n-form-item label="工序编码">
          <n-input v-model:value="searchForm.workstageCode" placeholder="请输入工序编码" />
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
    <my-card title="工序列表">
      <template #right>
        <div class="flex-center">
          <component
            :is="
              useAddBtn(() => {
                addTechnology();
              })
            "
          ></component>
          <cx-columns v-model:columns="columns" size="small"></cx-columns>
        </div>
      </template>
      <n-data-table :loading="loading" :data="data" :columns="columns"></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="init"></my-pagination>
    </my-card>
    <my-dialog v-model:show="flag" :title="!formData.id ? '新增工序' : '编辑工序'" @cancel="cancel" @submit="submit">
      <template #content>
        <div>
          <n-form ref="formRef" :model="formData" :rules="rules" label-placement="left" label-width="80px">
            <n-form-item label="工序名称" path="workstageName">
              <n-input v-model:value="formData.workstageName" placeholder="请输入工序名称" />
            </n-form-item>
            <n-form-item label="工序编码" path="workstageCode">
              <n-input v-model:value="formData.workstageCode" placeholder="请输入工序编码" />
            </n-form-item>
            <n-form-item label="工序备注" path="remark">
              <n-input
                v-model:value="formData.remark"
                :autosize="{
                  minRows: 2,
                  maxRows: 5
                }"
                type="textarea"
                placeholder="请输入工序描述"
              />
            </n-form-item>
            <n-form-item label="是否启用">
              <n-switch v-model:value="formData.isEnable" :checked-value="1" :unchecked-value="0" />
            </n-form-item>
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
import {
  getTechnologyList,
  fetchEditTechnology,
  fetchAddTechnology,
  fetchDeleteTechnology
} from '~/src/service/api/technology/technologySetting';
import { useAddBtn, useEditBtn, useDelBtn, useSearchBtn } from '~/src/hooks/common/useBtn';
import { useLoading } from '~/src/hooks/index';
import { deepClone } from '~/src/utils';

const message = useMessage();
const flag = ref<boolean>(false);
function addTechnology() {
  flag.value = true;
}
const { loading, startLoading, endLoading } = useLoading();
const data = ref<Tsetting.columns[]>([]);
const formData = ref<Tsetting.formData>({
  workstageName: '',
  workstageCode: '',
  isEnable: 0,
  remark: ''
});
const formRef = ref<FormInst | null>(null);
const rules = {
  workstageName: [{ required: true, message: '请输入工位名称', trigger: 'blur' }],
  workstageCode: [{ required: true, message: '请输入工位编码', trigger: 'blur' }],
  isEnable: [{ required: true, message: '请选择是否启用', trigger: 'blur' }],
  remark: [{ required: true, message: '请输入备注', trigger: 'blur' }]
};

const columns: Ref<DataTableColumns<Tsetting.columns>> = ref([
  {
    title: '工序名称',
    key: 'workstageName'
  },
  // {
  //   title: '父工序名称',
  //   key: 'parentWorkstageName'
  // },

  {
    title: '工序编码',
    key: 'workstageCode'
  },
  {
    title: '是否启用',
    key: 'isEnable',
    render: row => {
      return (
        <my-switch
          v-model:value={row.isEnable}
          checked={1}
          unchecked={0}
          onChecked={val => {
            editStatus(row, val);
          }}
          onUnchecked={val => {
            editStatus(row, val);
          }}
        ></my-switch>
      );
    }
  },
  {
    title: '备注',
    key: 'remark'
  },
  {
    title: '操作',
    key: 'actions',
    width: 200,
    render: row => {
      return [
        useEditBtn(() => {
          editTechnology(row);
        }),
        useDelBtn(() => {
          deleteTech(row);
        })
      ];
    }
  }
]);
function editTechnology(row: Tsetting.columns) {
  flag.value = true;
  formData.value = deepClone(row);
}
function deleteTech(row: Tsetting.columns) {
  fetchDeleteTechnology(row.id).then(res => {
    if (res.code === 200) {
      message.success('删除成功');
      init();
    } else {
      message.error(res.msg);
    }
  });
}
function editStatus(row: Tsetting.columns, val: number) {
  row.isEnable = val;
  fetchEditTechnology(row).then(res => {
    if (res.code === 200) {
      message.success('修改成功');
    } else {
      message.error('修改失败');
    }
  });
}
const searchForm = ref<Tsetting.searchForm>({
  workstageName: '',
  workstageCode: '',
  pageNum: 1,
  pageSize: 10,
  total: 0
});

async function init() {
  // 初始化
  startLoading();
  await getTechnologyList(searchForm.value).then(res => {
    if (res.code === 200) {
      data.value = res.rows;
      searchForm.value.total = res.total;
    }
  });
  endLoading();
}
function cancel() {
  flag.value = false;
  formData.value = {
    workstageName: '',
    workstageCode: '',
    isEnable: 0,
    remark: ''
  };
}
function handleSearch() {
  init();
}
function handleReset() {
  searchForm.value = {
    workstageName: '',
    workstageCode: '',
    pageNum: 1,
    pageSize: 10,
    total: 0
  };
  init();
}
function submit() {
  formRef.value?.validate(error => {
    if (!error) {
      if (formData.value.id) {
        fetchEditTechnology(formData.value).then(res => {
          if (res.code === 200) {
            message.success('编辑成功');
            flag.value = false;
            init();
            cancel();
          } else {
            message.error('编辑失败');
          }
        });
      } else {
        fetchAddTechnology(formData.value).then(res => {
          if (res.code === 200) {
            message.success('新增成功');
            flag.value = false;
            init();
            cancel();
          } else {
            message.error('新增失败');
          }
        });
      }
    }
  });
}
onMounted(() => {
  init();
});
</script>

<style lang="scss" scoped></style>
