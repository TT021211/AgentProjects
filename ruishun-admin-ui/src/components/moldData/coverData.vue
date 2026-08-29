<template>
  <div class="cover">
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item label="套模编号">
          <n-input v-model:value="searchForm.nestingName" type="text" placeholder="请输入套模编号" />
        </n-form-item>
        <n-form-item label="套模范围">
          <n-input v-model:value="searchForm.nestingScope" type="text" placeholder="请输入套模范围" />
        </n-form-item>
        <n-form-item label="存放位置">
          <n-input v-model:value="searchForm.nestingArea" type="text" placeholder="请输入存放位置" />
        </n-form-item>
        <n-form-item label="当前在用规格">
          <n-input v-model:value="searchForm.nestingSpec" type="text" placeholder="请输入当前在用规格" />
        </n-form-item>
        <n-form-item>
          <component :is="useSearchBtn(search, reset)"></component>
        </n-form-item>
      </n-form>
    </my-card>
    <my-card title="套模列表">
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
import { ref, onMounted } from 'vue';
import type { Ref } from 'vue';
import type { DataTableColumns } from 'naive-ui';
import { useMessage } from 'naive-ui';
import { useSearchBtn } from '~/src/hooks/common/useBtn';
import { useLoading } from '~/src/hooks';
import { getMoldNestingList2 } from '~/src/service/api/moid/drawing/index';
const searchForm = ref<{
  pageNum: number;
  pageSize: number;
  total: number;
  nestingName: string;
  nestingScope: string;
  nestingArea: string;
  nestingSpec: string;
}>({
  pageNum: 1,
  pageSize: 10,
  total: 0,
  nestingName: '',
  nestingScope: '',
  nestingArea: '',
  nestingSpec: ''
});

const { loading, startLoading, endLoading } = useLoading();
const message = useMessage();
const checkedRowKeys = ref<Array<string | number>>([]);
const rowKey = (row: any) => row.nestingMoldId;

const data = ref<drawing.viewData[]>([]);

const columns: Ref<DataTableColumns<drawing.viewData>> = ref([
  {
    type: 'selection',
    multiple: false
  },
  {
    title: '序号',
    key: 'index',
    align: 'center',
    width: 70,
    render: (_row, index) => (searchForm.value.pageNum - 1) * searchForm.value.pageSize + index + 1
  },
  {
    title: '套模编号',
    key: 'nestingName',
    align: 'center',
    width: 100
  },
  {
    title: '套模范围',
    key: 'nestingScope',
    align: 'center',
    width: 100
  },
  {
    title: '存放位置',
    key: 'nestingArea',
    align: 'center',
    width: 100
  },
  {
    title: '当前在用规格',
    key: 'nestingSpec',
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
    message.warning('请选择套模');
    return;
  }
  const parsms = data.value.find(item => item.nestingMoldId === checkedRowKeys.value[0]);
  if (parsms) {
    emit('submit', parsms);
  }
}

function search() {
  searchForm.value.pageNum = 1;
  init();
  console.log('search');
}
function reset() {
  searchForm.value = {
    pageNum: 1,
    pageSize: 10,
    total: 0,
    nestingName: '',
    nestingScope: '',
    nestingArea: '',
    nestingSpec: ''
  };
  init();
  console.log('reset');
}
function init() {
  startLoading();
  getMoldNestingList2(searchForm.value).then(res => {
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
