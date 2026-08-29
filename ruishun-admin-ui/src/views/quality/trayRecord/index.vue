<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item label="产品名称">
          <n-input v-model:value="searchForm.productName" type="text" placeholder="请输入型号" />
        </n-form-item>
        <!-- <n-form-item label="采购单号">
          <n-input v-model:value="searchForm.purchaseOrder" type="text" placeholder="请输入采购单号" />
        </n-form-item> -->
        <n-form-item>
          <component :is="useSearchBtn(search, reset)"></component>
        </n-form-item>
      </n-form>
    </my-card>
    <my-card title="原料检列表">
      <template #right>
        <div>
          <CxColumns v-model:columns="columns" size="small" />
        </div>
      </template>
      <n-data-table
        :loading="loading"
        :columns="columns"
        :max-height="dataTableConfig.maxHeight"
        :scroll-x="dataTableConfig.scrollWidth(columns)"
        :data="data"
      ></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="init"></my-pagination>
    </my-card>
  </div>
</template>

<script setup lang="tsx">
import type { Ref } from 'vue';
import { ref, onMounted } from 'vue';
import { useMessage, useDialog } from 'naive-ui';
import type { DataTableColumns } from 'naive-ui';
import { dataTableConfig } from '@/config/dataTableConfig';
import { useLoading } from '~/src/hooks';
import { useSearchBtn } from '~/src/hooks/common/useBtn';
import { geMaterialRecordList, deleteQcWireDiscRecord } from '~/src/service/api/quality/trayRecord/index';
import { getQcInspectionItems } from '~/src/service/api/quality/qualityInspectionItem/index';
import { getQcDefect } from '~/src/service/api/quality/qcDefect/index';
const searchForm = ref<trayRecord.searchForm>({
  pageNum: 1,
  pageSize: 10,
  total: 0,
  productName: ''
});

const itemTypeId = ref<number>(4); // 4 : 质检检测项分类为线盘的分类id
const defectClassificationId = ref<number>(4); // 4 : 缺陷项设置分类为线盘的分类id
// const title = ref<string>('新增线盘检记录');
const message = useMessage();
const dialog = useDialog();
const { loading, startLoading, endLoading } = useLoading();
const inspectionList = ref<{ label: string; value: number }[]>([]);
const defectList = ref<{ label: string; value: number }[]>([]);
const data = ref<trayRecord.TableList[]>([]);

const columns: Ref<DataTableColumns<trayRecord.TableList>> = ref([
  {
    title: '序号',
    key: 'index',
    align: 'center',
    width: 70,
    render: (_row, index) => (searchForm.value.pageNum - 1) * searchForm.value.pageSize + index + 1
  },
  {
    title: '检测项名称',
    key: 'inspectionItemName',
    align: 'center',
    width: 100
  },
  {
    title: '检测分类',
    key: 'classificationName',
    align: 'center',
    width: 100
  },
  {
    title: '产品名称',
    key: 'productName',
    align: 'center',
    width: 100
  },
  {
    title: '产品规格',
    key: 'productSpecification',
    align: 'center',
    width: 100
  },
  {
    title: '重量(kg)',
    key: 'weight',
    align: 'center',
    width: 80
  },
  // {
  //   title: '采购订单',
  //   key: 'purchaseOrder',
  //   align: 'center',
  //   width: 100
  // },
  {
    title: '批号',
    key: 'batchNumber',
    align: 'center',
    width: 120
  },
  {
    title: '料号',
    key: 'itemNumber',
    align: 'center',
    width: 120
  },
  {
    title: '质检时间',
    key: 'createTime',
    align: 'center',
    width: 180
  },
  {
    title: '质检人',
    key: 'createBy',
    align: 'center',
    width: 100
  },

  {
    title: '检测结果',
    key: 'status',
    align: 'center',
    width: 100,
    render: row => (
      <n-tag type={row.status === '0' ? 'success' : 'error'}>{row.status === '0' ? '合格' : '不合格'}</n-tag>
    )
  },
  {
    title: '缺陷项',
    key: 'defectName',
    align: 'center',
    width: 100
  },
  {
    title: '操作',
    key: '',
    align: 'center',
    width: 100,
    render: row => {
      return (
        <n-space justify={'center'}>
          <n-button
            type="warning"
            size={'small'}
            onClick={() => {
              handleDeleteItemType(row.id);
            }}
          >
            <icon-mdi-delete />
            删除
          </n-button>
        </n-space>
      );
    }
  }
]);

const sleep = id =>
  new Promise(resolve => {
    deleteQcWireDiscRecord(id).then(res => {
      resolve(res);
    });
  });

function handleDeleteItemType(id: number): void {
  const d = dialog.warning({
    title: '删除',
    content: '确认要删除吗?',
    positiveText: '确认',
    negativeText: '取消',
    onPositiveClick: () => {
      d.loading = true;
      return new Promise(resolve => {
        sleep(id).then((res: any) => {
          if (res.code === 200) {
            message.success('删除成功');
            init();
            resolve(res);
          }
        });
      });
    }
  });
}

function search() {
  init();
}
function reset() {
  searchForm.value = {
    pageNum: 1,
    pageSize: 10,
    total: 0,
    productName: ''
  };
  init();
}

function getList() {
  getQcInspectionItems({ pageSize: 999, itemTypeId: itemTypeId.value }).then(res => {
    if (res.code === 200) {
      res.rows.forEach(item => {
        inspectionList.value.push({
          label: item.detectionName,
          value: item.id
        });
      });
    }
  });

  getQcDefect({ pageSize: 999, defectClassificationId: defectClassificationId.value }).then(res => {
    if (res.code === 200) {
      res.rows.forEach(item => {
        defectList.value.push({
          label: item.defectName,
          value: item.defectId
        });
      });
    }
  });
}

function init() {
  startLoading();
  geMaterialRecordList(searchForm.value).then(res => {
    endLoading();
    if (res.code === 200) {
      data.value = res.rows;
      searchForm.value.total = res.total;
    }
  });
}
onMounted(() => {
  init();
  getList();
});
</script>

<style scoped>
:deep(.n-space) {
  justify-content: center !important;
}

:deep(.n-data-table .n-data-table-th .n-data-table-th__title-wrapper) {
  text-align: center;
}
</style>
