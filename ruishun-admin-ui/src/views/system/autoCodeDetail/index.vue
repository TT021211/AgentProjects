<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline label-placement="left">
        <n-form-item label="编码组成">
          <n-input v-model:value="searchForm.partCode" placeholder="请输入编码组成"></n-input>
        </n-form-item>
        <n-form-item label="组成名称">
          <n-input v-model:value="searchForm.partName" placeholder="请输入组成名称"></n-input>
        </n-form-item>
        <n-form-item>
          <component :is="useSearchBtn(search, reset)"></component>
        </n-form-item>
      </n-form>
    </my-card>
    <my-card title="搜索列表">
      <template #right>
        <div>
          <component :is="useBackBtn()"></component>
          <component
            :is="
              useAddBtn(() => {
                addFlag = true;
              })
            "
          ></component>
          <n-button type="error" :disabled="!rowChecked.length" size="small" class="mr-5px" @click="delectAll">
            <n-icon size="15" :component="TrashOutline" class="mr-5px" />
            删除
          </n-button>
          <cx-columns v-model:columns="columns" size="small"></cx-columns>
        </div>
      </template>
      <n-data-table
        :row-key="row => row.partId"
        :loading="loading"
        :data="data"
        :columns="columns"
        @update:checked-row-keys="changeSelect"
      ></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="init"></my-pagination>
    </my-card>
    <my-dialog v-model:show="addFlag" title="添加规则组成" @cancel="cancel" @submit="submit">
      <template #content>
        <div>
          <n-form ref="formRef" :model="addForm" :rules="rules" label-placement="left" label-width="100">
            <n-form-item label="分段编码" path="partCode">
              <n-input v-model:value="addForm.partCode" placeholder="请输入分段编码"></n-input>
            </n-form-item>
            <n-form-item label="分段名称" path="partName">
              <n-input v-model:value="addForm.partName" placeholder="请输入分段名称"></n-input>
            </n-form-item>
            <n-form-item label="分段序号" path="partIndex">
              <n-input-number v-model:value="addForm.partIndex" placeholder="请输入分段序号"></n-input-number>
            </n-form-item>
            <n-form-item label="分段长度">
              <n-input-number v-model:value="addForm.partLength" placeholder="请输入分段长度"></n-input-number>
            </n-form-item>
            <n-form-item label="分段类型">
              <n-select v-model:value="addForm.partType" :options="addOPtion" placeholder="请选择分段类型"></n-select>
            </n-form-item>
            <n-form-item v-if="addForm.partType == 'NOWDATE'" label="日期时间格式">
              <n-input v-model:value="addForm.dateFormat" placeholder="请输入日期时间格式" />
            </n-form-item>
            <n-form-item v-if="addForm.partType == 'INPUTCHAR'" label="输入字符">
              <n-input v-model:value="addForm.inputCharacter" placeholder="请填写输入字符" />
            </n-form-item>
            <n-form-item v-if="addForm.partType == 'FIXCHAR'" label="固定字符">
              <n-input v-model:value="addForm.fixCharacter" placeholder="请填写固定字符" />
            </n-form-item>
            <n-form-item v-if="addForm.partType == 'SERIALNO'" label="起始流水号">
              <n-input-number v-model:value="addForm.seriaStartNo" placeholder="请填写起始流水号" />
            </n-form-item>
            <n-form-item v-if="addForm.partType == 'SERIALNO'" label="流水号步长">
              <n-input-number v-model:value="addForm.seriaStep" placeholder="请填写流水号步长" />
            </n-form-item>
            <n-form-item v-if="addForm.partType == 'SERIALNO'" label="是否循环">
              <n-radio-group v-model:value="addForm.cycleFlag">
                <n-radio value="Y">是</n-radio>
                <n-radio value="N">否</n-radio>
              </n-radio-group>
            </n-form-item>
            <n-form-item
              v-if="addForm.partType == 'SERIALNO' && addForm.cycleFlag == 'Y'"
              label="循环方式"
              prop="cycleMethod"
            >
              <n-select
                v-model:value="addForm.cycleMethod"
                :options="flowOption"
                placeholder="请选择循环方式"
              ></n-select>
            </n-form-item>
            <n-form-item label="备注">
              <n-input v-model:value="addForm.remark" type="textarea" placeholder="请输入备注"></n-input>
            </n-form-item>
          </n-form>
        </div>
      </template>
    </my-dialog>
  </div>
</template>

<script setup lang="tsx">
import type { Ref } from 'vue';
import { onMounted, ref } from 'vue';
import { useRoute } from 'vue-router';
import type { DataTableColumns, DataTableRowKey, FormInst } from 'naive-ui';
import { useMessage } from 'naive-ui';
import { TrashOutline } from '@vicons/ionicons5';
import {
  getAutoCodeDetail,
  deleteAutoCodeDetail,
  addAutoCodeDetail,
  getOptionList,
  updateAutoCodeDetail
} from '@/service/api/system/autoCodeDetail';
import { useLoading } from '~/src/hooks';
import { deepClone, createRequiredFormRule } from '~/src/utils';
import { useSearchBtn, useAddBtn, useEditBtn, useDelBtn, useBackBtn } from '~/src/hooks/common/useBtn';

const rules = {
  partCode: createRequiredFormRule('请输入分段编码'),
  partName: createRequiredFormRule('请输入分段名称'),
  partIndex: createRequiredFormRule('请输入分段序号')
};
const formRef = ref<FormInst | null>(null);
const message = useMessage();
const addFlag = ref<boolean>(false);
const rowChecked = ref<DataTableRowKey[]>([]);
function changeSelect(rowKeys: DataTableRowKey[]) {
  rowChecked.value = rowKeys;
}
const addOPtion = ref<{ label: string; value: string }[]>([]);
const flowOption = ref<{ label: string; value: string }[]>([]);

const { loading, startLoading, endLoading } = useLoading();
const route = useRoute();
const searchForm = ref<{
  pageNum: number;
  pageSize: number;
  total: number;
  ruleId: null | number;
  partCode: string;
  partName: string;
}>({
  partCode: '',
  partName: '',
  pageNum: 1,
  pageSize: 10,
  total: 0,
  ruleId: null
});
// // eslint-disable-next-line no-invalid-this
// const test = this.$globalProperty.$msg;
function search() {
  // test.success('search');
  searchForm.value.pageNum = 1;
  init();
}
function reset() {
  searchForm.value = {
    partCode: '',
    partName: '',
    pageNum: 1,
    pageSize: 10,
    total: 0,
    ruleId: route.query.ruleId as unknown as number
  };
  init();
}
const addForm = ref<columnsType>({
  createBy: '',
  createTime: '',
  cycleFlag: null,
  cycleMethod: null,
  dateFormat: null,
  fixCharacter: '',
  inputCharacter: null,
  partCode: '',
  partId: 0,
  partIndex: 0,
  partLength: 0,
  partName: '',
  partType: null,
  remark: null,
  ruleId: null,
  seriaNowNo: null,
  seriaStartNo: null,
  seriaStep: null,
  updateBy: '',
  updateTime: null
});
type columnsType = {
  createBy: string;
  createTime: string;
  cycleFlag: null | string;
  cycleMethod: null | string;
  dateFormat: null | string;
  fixCharacter: string;
  inputCharacter: null | string;
  partCode: string;
  partId: number;
  partIndex: number;
  partLength: number;
  partName: string;
  partType: string | null;
  remark: null | string;
  ruleId: number | null;
  seriaNowNo: null | string;
  seriaStartNo: null | number;
  seriaStep: null | number;
  updateBy: string;
  updateTime: null | string;
};
const columns: Ref<DataTableColumns<columnsType>> = ref([
  { type: 'selection' },
  {
    title: '组成编码',
    key: 'partCode'
  },
  {
    title: '组成名称',
    key: 'partName'
  },
  {
    title: '分段序号',
    key: 'partIndex'
  },
  {
    title: '分段类型',
    key: 'partType'
  },
  {
    title: '分段长度',
    key: 'partLength'
  },
  {
    title: '备注',
    key: 'remark'
  },
  {
    title: '创建时间',
    key: 'createTime'
  },
  {
    title: '操作',
    key: 'action',
    fixed: 'right',
    width: 200,
    render: row => {
      return [
        useEditBtn(() => {
          addFlag.value = true;
          addForm.value = deepClone(row);
        }),
        useDelBtn(() => {
          deleteAutoCodeDetail(row.partId).then(res => {
            if (res.code === 200) {
              init();
            }
          });
        })
      ];
    }
  }
]);
const data = ref<columnsType[]>([]);
function init() {
  startLoading();
  // 请求接口
  getAutoCodeDetail(searchForm.value).then(res => {
    endLoading();
    if (res.code === 200) {
      data.value = res.rows;
      searchForm.value.total = res.total;
    }
  });
}

function cancel() {
  addFlag.value = false;
  addForm.value = {
    createBy: '',
    createTime: '',
    cycleFlag: null,
    cycleMethod: null,
    dateFormat: null,
    fixCharacter: '',
    inputCharacter: null,
    partCode: '',
    partId: 0,
    partIndex: 0,
    partLength: 0,
    partName: '',
    partType: null,
    remark: null,
    ruleId: null,
    seriaNowNo: null,
    seriaStartNo: null,
    seriaStep: null,
    updateBy: '',
    updateTime: null
  };
}
function submit() {
  addForm.value.ruleId = route.query.ruleId as unknown as number;
  formRef.value?.validate(error => {
    if (!error) {
      if (!addForm.value.partId) {
        addAutoCodeDetail(addForm.value).then(res => {
          if (res.code === 200) {
            message.success('添加成功');
            init();
            cancel();
          }
        });
      } else {
        updateAutoCodeDetail(addForm.value).then(res => {
          if (res.code === 200) {
            message.success('修改成功');
            init();
            cancel();
          }
        });
      }
    }
  });
}
function delectAll() {
  deleteAutoCodeDetail(rowChecked.value).then(res => {
    if (res.code === 200) {
      message.success('删除成功');
      init();
    }
  });
}

onMounted(() => {
  searchForm.value.ruleId = route.query.ruleId as unknown as number;
  init();
  getOptionList('sys_autocode_parttype').then(res => {
    res.data.forEach(item => {
      addOPtion.value.push({
        label: item.dictLabel,
        value: item.dictValue
      });
    });
  });
  getOptionList('sys_autocode_cyclemethod').then(res => {
    res.data.forEach(item => {
      flowOption.value.push({
        label: item.dictLabel,
        value: item.dictValue
      });
    });
  });
});
</script>

<style scoped lang="scss"></style>
