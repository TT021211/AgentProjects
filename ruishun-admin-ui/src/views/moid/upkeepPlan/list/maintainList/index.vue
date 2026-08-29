<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item label="项目名称">
          <n-input v-model:value="searchForm.maintainItemName" type="text" placeholder="请输入项目名称" />
        </n-form-item>
        <n-form-item label="完成标准">
          <n-input v-model:value="searchForm.maintainItemStandard" type="text" placeholder="请输入完成标准" />
        </n-form-item>
        <n-form-item>
          <component :is="useSearchBtn(search, reset)"></component>
        </n-form-item>
      </n-form>
    </my-card>
    <my-card title="保养项" style="max-height: 401px; overflow: auto">
      <n-data-table
        v-model:checked-row-keys="checkedRowKeys"
        :loading="loading"
        :columns="columns"
        :data="data"
        :row-key="rowKey"
      ></n-data-table>
    </my-card>
    <div class="box">
      <div>
        <my-pagination v-model:search-form="searchForm" @init="init"></my-pagination>
      </div>
      <div class="btn-box">
        <n-button size="small" type="info" style="margin-right: 10px" @click="handleSubmit">确认</n-button>
        <n-button size="small" type="info" style="margin-right: 10px" @click="cancel">取消</n-button>
      </div>
    </div>
  </div>
</template>

<script setup lang="tsx">
import type { Ref } from 'vue';
import { ref, onMounted } from 'vue';
import type { DataTableColumns } from 'naive-ui';
import { useMessage } from 'naive-ui';
import { useLoading } from '~/src/hooks';
import { useSearchBtn } from '~/src/hooks/common/useBtn';
import { getMoldMaintainItem2 } from '~/src/service/api/moid/upkeepPlan/index';

const searchForm = ref<upkeepPlan.setSearchFrom>({
  pageNum: 1,
  pageSize: 10,
  total: 0,
  maintainItemName: '',
  maintainItemStandard: ''
});

const { loading, startLoading, endLoading } = useLoading();
const message = useMessage();
const checkedRowKeys = ref<Array<string | number>>([]);
const rowKey = (row: any) => row.maintainItemId;

const data = ref<upkeepPlan.setTabelList[]>([]);
const columns: Ref<DataTableColumns<upkeepPlan.setTabelList>> = ref([
  {
    type: 'selection'
  },
  {
    title: '项目名称',
    key: 'maintainItemName',
    align: 'center',
    width: 100
  },
  {
    title: '项目内容',
    key: 'maintainItemContent',
    align: 'center',
    width: 100
  },
  {
    title: '完成标准',
    key: 'maintainItemStandard',
    align: 'center',
    width: 100
  },
  {
    title: '备注',
    key: 'maintainItemRemark',
    align: 'center',
    width: 100
  }
]);

const emit = defineEmits(['cancel', 'submit', 'update:show']);

function cancel() {
  emit('cancel');
}
function handleSubmit() {
  if (checkedRowKeys.value.length === 0) {
    message.warning('请选择保养项');
    return;
  }
  const itemList = data.value.filter(item => checkedRowKeys.value.findIndex(ele => ele === item.maintainItemId) > -1);
  if (itemList.length > 0) {
    emit('submit', itemList);
  }
}

function search() {
  init();
}
function reset() {
  searchForm.value = {
    pageNum: 1,
    pageSize: 10,
    total: 0,
    maintainItemName: '',
    maintainItemStandard: ''
  };
  init();
}

function init() {
  startLoading();
  getMoldMaintainItem2(searchForm.value).then(res => {
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

<style scoped>
button {
  padding: 0 20px;
}

.box {
  display: flex;
  flex-direction: column;
  margin-top: 20px;
}

.btn-box {
  display: flex;
  flex-direction: row-reverse;
  margin-top: 20px;
}
</style>
