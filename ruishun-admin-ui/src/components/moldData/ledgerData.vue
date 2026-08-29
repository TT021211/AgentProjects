<template>
  <div class="cover">
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item label="模具分类">
          <n-input v-model:value="searchForm.classificationName" type="text" placeholder="请输入模具分类" />
        </n-form-item>
        <n-form-item label="型号">
          <n-input v-model:value="searchForm.classificationType" type="text" placeholder="请输入型号" />
        </n-form-item>
        <n-form-item label="分类编码">
          <n-input v-model:value="searchForm.classificationCode" type="text" placeholder="请输入分类编码" />
        </n-form-item>
        <n-form-item>
          <component :is="useSearchBtn(search, reset)"></component>
        </n-form-item>
      </n-form>
    </my-card>
    <my-card title="套模列表" style="max-height: 400px">
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
import { getMoidList } from '~/src/service/api/moid/sortManagement/index';

const searchForm = ref<sortManagement.searchForm>({
  classificationName: '',
  classificationType: '',
  classificationCode: '',
  pageNum: 1,
  pageSize: 10,
  total: 0
});

const { loading, startLoading, endLoading } = useLoading();
const message = useMessage();
const checkedRowKeys = ref<Array<string | number>>([]);
const rowKey = (row: any) => row.classificationId;

const data = ref<sortManagement.TableList[]>([]);

const columns: Ref<DataTableColumns<sortManagement.TableList>> = ref([
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
    title: '模具分类',
    key: 'classificationName',
    align: 'center',
    width: 100
  },
  {
    title: '型号',
    key: 'classificationType',
    align: 'center',
    width: 100
  },
  {
    title: '分类编码',
    key: 'classificationCode',
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
    message.warning('请选择模具');
    return;
  }
  const parsms = data.value.find(item => item.classificationId === checkedRowKeys.value[0]);
  if (parsms) {
    emit('submit', parsms);
  }
}
function search() {
  searchForm.value.pageNum = 1;
  init();
}
function reset() {
  searchForm.value = {
    classificationName: '',
    classificationType: '',
    classificationCode: '',
    pageNum: 1,
    pageSize: 10,
    total: 0
  };
  init();
}
function init() {
  startLoading();
  getMoidList(searchForm.value).then(res => {
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
