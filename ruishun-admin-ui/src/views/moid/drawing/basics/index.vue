<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item label="模具编号">
          <n-input v-model:value="searchForm.moldCode" type="text" placeholder="请输入模具编号" />
        </n-form-item>
        <n-form-item label="模具型号">
          <n-input v-model:value="searchForm.moldSpec" type="text" placeholder="请输入模具型号" />
        </n-form-item>
        <n-form-item label="材质">
          <n-input v-model:value="searchForm.moldMaterial" type="text" placeholder="请输入材质" />
        </n-form-item>
        <n-form-item label="存放位置">
          <n-input v-model:value="searchForm.moldArea" type="text" placeholder="请输入存放位置" />
        </n-form-item>
        <n-form-item>
          <component :is="useSearchBtn(search, reset)"></component>
        </n-form-item>
      </n-form>
    </my-card>
    <my-card title="模具台账基础信息" style="max-height: 401px; overflow: auto">
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
import { getMoldLedgerList2 } from '~/src/service/api/moid/drawing/index';
import { getMoidStatus } from '~/src/utils/common/moidFunc';
const searchForm = ref<basicsMessage.searchForm>({
  moldCode: '',
  moldMaterial: '',
  moldSpec: '',
  moldArea: '',
  pageNum: 1,
  pageSize: 10,
  total: 0
});

const { loading, startLoading, endLoading } = useLoading();
const message = useMessage();
const checkedRowKeys = ref<Array<string | number>>([]);
const rowKey = (row: any) => row.moldId;

const data = ref<basicsMessage.TableList[]>([]);

const columns: Ref<DataTableColumns<basicsMessage.TableList>> = ref([
  {
    type: 'selection'
  },
  {
    title: '序号',
    key: 'index',
    align: 'center',
    width: 70,
    render: (_row, index) => (searchForm.value.pageNum - 1) * searchForm.value.pageSize + index + 1
  },
  {
    title: '模具编号',
    key: 'moldCode',
    align: 'center',
    width: 100
  },
  {
    title: '模具类别',
    key: 'moldType',
    align: 'center',
    width: 100
  },
  {
    title: '型号',
    key: 'moldSpec',
    align: 'center',
    width: 100
  },
  {
    title: '材质',
    key: 'moldMaterial',
    align: 'center',
    width: 100
  },
  {
    title: '模具孔径',
    key: 'moldAperture',
    align: 'center',
    width: 100
  },
  {
    title: '模具数量',
    key: 'moldNum',
    align: 'center',
    width: 100
  },
  {
    title: '公差',
    key: 'engineeringTolerance',
    align: 'center',
    width: 100
  },
  {
    title: '存放位置',
    key: 'moldArea',
    align: 'center',
    width: 100
  },
  {
    title: '套模名称',
    key: 'moldName',
    align: 'center',
    width: 100
  },
  {
    title: '供应商',
    key: 'supplierName',
    align: 'center',
    width: 100
  },
  {
    title: '状态',
    key: 'status',
    align: 'center',
    render: row => {
      const obj = getMoidStatus(row.status);
      return <n-tag type={obj.statusType}>{obj.text}</n-tag>;
    }
  }
]);

const emit = defineEmits(['cancel', 'submit', 'update:show']);

defineExpose({
  checkedRowKeys,
  changeCheckedRowKeys
});

function changeCheckedRowKeys(checked: number[]) {
  checkedRowKeys.value = checked;
}

function cancel() {
  emit('cancel');
}
function handleSubmit() {
  if (checkedRowKeys.value.length === 0) {
    message.warning('请选择模具台账');
    return;
  }
  const itemList = data.value.filter(item => checkedRowKeys.value.findIndex(ele => ele === item.moldId) > -1);
  const params = {
    itemList,
    checkedRowKeys: checkedRowKeys.value
  };
  if (params) {
    emit('submit', params);
  }
}

function search() {
  init();
}
function reset() {
  searchForm.value = {
    moldCode: '',
    moldMaterial: '',
    moldSpec: '',
    moldArea: '',
    pageNum: 1,
    pageSize: 10,
    total: 0
  };
  init();
}

function init() {
  startLoading();
  getMoldLedgerList2(searchForm.value).then(res => {
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
