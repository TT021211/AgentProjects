<template>
  <div>
    <my-card search title="搜索条件">
      <n-form inline>
        <n-form-item label="规则名称">
          <n-input v-model:value="searchForm.ruleName" placeholder="请输入规则名称"></n-input>
        </n-form-item>
        <n-form-item label="规则编码">
          <n-input v-model:value="searchForm.ruleCode" placeholder="请输入规则编码"></n-input>
        </n-form-item>
        <n-form-item label="启用状态">
          <n-select
            v-model:value="searchForm.enableFlag"
            class="w-180px"
            :options="[
              { label: '启用', value: 'Y' },
              { label: '禁用', value: 'N' }
            ]"
          ></n-select>
        </n-form-item>
        <n-form-item>
          <component :is="useSearchBtn(search, reset)"></component>
        </n-form-item>
      </n-form>
    </my-card>
    <my-card title="规则列表">
      <template #right>
        <div>
          <NButton type="error" :disabled="!selectList.length" size="small" class="mr-5px" @click="delectAll">
            <NIcon size="15" :component="TrashOutline" class="mr-5px" />
            删除
          </NButton>
          <component
            :is="
              useAddBtn(
                () => {
                  addFlog = true;
                },
                undefined,
                '添加编码规则'
              )
            "
          ></component>
          <cx-columns v-model:columns="columns" size="small"></cx-columns>
        </div>
      </template>
      <n-data-table
        :row-key="row => row.ruleId"
        :data="data"
        :columns="columns"
        :loading="loading"
        @update:checked-row-keys="handleSelect"
      ></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="init"></my-pagination>
    </my-card>
    <my-dialog
      v-model:show="addFlog"
      width="400px"
      :title="addAutoCode.ruleId ? '修改编码规则' : '添加编码规则'"
      @submit="submit"
      @cancel="cancel"
    >
      <template #content>
        <div>
          <n-form ref="formRef" label-placement="left" :model="addAutoCode" :rules="rules" :label-width="80">
            <n-form-item label="规则编码" path="ruleCode">
              <n-input v-model:value="addAutoCode.ruleCode" placeholder="请输入规则编码"></n-input>
            </n-form-item>
            <n-form-item label="规则名称" path="ruleName">
              <n-input v-model:value="addAutoCode.ruleName" placeholder="请输入规则名称"></n-input>
            </n-form-item>

            <n-form-item label="最大长度">
              <n-input-number
                v-model:value="addAutoCode.maxLength"
                placeholder="请输入最大长度"
                :min="0"
              ></n-input-number>
            </n-form-item>
            <n-form-item label="是否补齐">
              <n-radio-group v-model:value="addAutoCode.isPadded" name="radiogroup">
                <n-radio value="Y">是</n-radio>
                <n-radio value="N">否</n-radio>
              </n-radio-group>
            </n-form-item>
            <n-form-item label="启用状态">
              <n-radio-group v-model:value="addAutoCode.enableFlag" name="radiogroup">
                <n-radio value="Y">是</n-radio>
                <n-radio value="N">否</n-radio>
              </n-radio-group>
              <!-- <n-select
              v-model:value="addAutoCode.enableFlag"
              class="w-180px"
              :options="[
                { label: '启用', value: 'Y' },
                { label: '禁用', value: 'N' }
              ]"
            ></n-select> -->
            </n-form-item>
            <n-form-item label="描述">
              <n-input v-model:value="addAutoCode.remark" type="textarea" placeholder="请输入描述"></n-input>
            </n-form-item>
          </n-form>
        </div>
      </template>
    </my-dialog>
  </div>
</template>

<script setup lang="tsx">
import { onMounted, ref } from 'vue';
import type { Ref } from 'vue';
import { useRouter } from 'vue-router';
import type { DataTableColumns, FormInst, DataTableRowKey } from 'naive-ui';
import { useMessage } from 'naive-ui';
import { TrashOutline } from '@vicons/ionicons5';
import {
  fetchAutoCodeRuleList,
  addAutoCodeRule,
  deleteAutoCodeRule,
  editAutoCodeRule
} from '@/service/api/system/autoCodeRule';
import { useLoading } from '~/src/hooks';
import { deepClone } from '~/src/utils';
import { useSearchBtn, useAddBtn, useDelBtn, useEditBtn } from '~/src/hooks/common/useBtn';
const router = useRouter();
const selectList = ref<DataTableRowKey[]>([]);
const addFlog = ref<boolean>(false);
const message = useMessage();
const { loading, startLoading, endLoading } = useLoading();
const searchForm = ref({
  ruleName: '',
  ruleCode: '',
  enableFlag: null,
  pageNum: 1,
  pageSize: 10,
  total: 0
});
function search() {
  searchForm.value.pageNum = 1;
  init();
}
const formRef = ref<FormInst | null>(null);
const rules = {
  ruleCode: {
    required: true,
    message: '请输入规则编码',
    trigger: ['input', 'blur']
  },
  ruleName: {
    required: true,
    message: '请输入规则名称',
    trigger: ['input', 'blur']
  }
};
function handleSelect(value: DataTableRowKey[]) {
  selectList.value = value;
}
function delectAll() {
  deleteAutoCodeRule(selectList.value).then(res => {
    if (res.code === 200) {
      message.success('删除成功');
      init();
    }
  });
}
const addAutoCode = ref<columnType>({
  createBy: '',
  createTime: '',
  enableFlag: '',
  isPadded: '',
  maxLength: 0,
  paddedChar: '',
  paddedMethod: '',
  remark: '',
  ruleCode: '',
  ruleDesc: '',
  ruleId: null,
  ruleName: '',
  updateBy: '',
  updateTime: ''
});
function cancel() {
  addFlog.value = false;
  addAutoCode.value = {
    createBy: '',
    createTime: '',
    enableFlag: '',
    isPadded: '',
    maxLength: 0,
    paddedChar: '',
    paddedMethod: '',
    remark: '',
    ruleCode: '',
    ruleDesc: '',
    ruleId: null,
    ruleName: '',
    updateBy: '',
    updateTime: ''
  };
}
function submit() {
  formRef.value?.validate(errors => {
    if (!errors) {
      if (addAutoCode.value.ruleId) {
        editAutoCodeRule(addAutoCode.value).then(res => {
          if (res.code === 200) {
            message.success('修改成功');
            init();
            cancel();
          }
        });
      } else {
        addAutoCodeRule(addAutoCode.value).then(res => {
          if (res.code === 200) {
            message.success('新增成功');
            init();
            cancel();
          }
        });
      }
    }
  });
}
function reset() {
  searchForm.value.ruleName = '';
  searchForm.value.ruleCode = '';
  searchForm.value.enableFlag = null;
  searchForm.value.pageNum = 1;
  searchForm.value.pageSize = 10;
  searchForm.value.total = 0;
  init();
}
type columnType = {
  createBy: string;
  createTime: string;
  enableFlag: string;
  isPadded: string;
  maxLength: number;
  paddedChar: null | string;
  paddedMethod: string;
  remark: null | string;
  ruleCode: string;
  ruleDesc: null | string;
  ruleId: number | null;
  ruleName: string;
  updateBy: null | string;
  updateTime: null | string;
};
const data = ref<columnType[]>([]);
const columns: Ref<DataTableColumns<columnType>> = ref([
  {
    type: 'selection'
  },
  {
    title: '规则编码',
    key: 'ruleCode',
    align: 'center',
    render: row => {
      return (
        <n-gradient-text
          style="cursor: pointer;"
          type="info"
          onClick={() => {
            router.push({ name: 'system_autoCodeDetail', query: { ruleId: row.ruleId } });
            // router.push({ name: 'system_autoCodeDetail', query: { ruleId: row.ruleId } });
          }}
        >
          {row.ruleCode}
        </n-gradient-text>
      );
    }
  },
  {
    title: '规则名称',
    key: 'ruleName',
    align: 'center'
  },
  {
    title: '最大长度',
    key: 'maxLength',
    align: 'center'
  },
  {
    title: '是否填充',
    key: 'isPadded',
    align: 'center',
    render: row => {
      return row.isPadded === 'Y' ? <n-tag type="success">是</n-tag> : <n-tag type="error">否</n-tag>;
    }
  },

  {
    title: '是否启用',
    key: 'enableFlag',
    align: 'center',
    render: row => {
      return (
        <n-tag type={row.enableFlag === 'Y' ? 'success' : 'error'}>{row.enableFlag === 'Y' ? '启用' : '禁用'}</n-tag>
      );
    }
  },
  {
    title: '备注',
    key: 'remark',
    align: 'center'
  },
  {
    title: '创建时间',
    key: 'createTime',
    align: 'center'
  },
  {
    title: '操作',
    key: 'action',
    render: row => {
      return [
        useEditBtn(() => {
          addAutoCode.value = deepClone(row);
          addFlog.value = true;
        }),
        useDelBtn(() => {
          deleteAutoCodeRule(row.ruleId).then(res => {
            if (res.code === 200) {
              message.success('删除成功');
              init();
            }
          });
        })
      ];
    }
  }
]);
function init() {
  startLoading();
  fetchAutoCodeRuleList(searchForm.value).then(res => {
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
