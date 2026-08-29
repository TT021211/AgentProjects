<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item label="设备编码">
          <n-input v-model:value="searchForm.equipmentCode" placeholder="请输入设备编码"></n-input>
        </n-form-item>
        <n-form-item label="状态">
          <n-select
            v-model:value="searchForm.status"
            class="w-180px"
            :options="[
              { label: '全部', value: '' },
              { label: '维修中', value: '0' },
              { label: '审批中', value: '1' },
              { label: '已完成', value: '2' }
            ]"
          ></n-select>
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
    <my-card title="配件列表">
      <template #right>
        <div>
          <CxColumns v-model:columns="columns" size="small" />
        </div>
      </template>
      <n-data-table
        :data="data"
        :loading="loading"
        :row-key="rowKey"
        :columns="columns"
        scroll-x="1600"
        @update-checked-row-keys="handleCheck"
      ></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="init"></my-pagination>
    </my-card>
  </div>
</template>

<script setup lang="tsx">
import type { Ref } from 'vue';
import { ref, onMounted } from 'vue';
import type { DataTableColumns } from 'naive-ui';
import { useMessage } from 'naive-ui';
import { ArrowRedo } from '@vicons/ionicons5';
import { getRepairReportList, deleteRepairReport, addKnowledgeRepairReport } from '@/service/api/device/repairForm';
import { useSearchBtn, useDelBtn } from '~/src/hooks/common/useBtn';
import { useLoading } from '~/src/hooks/index';
const { loading, startLoading, endLoading } = useLoading();
const message = useMessage();

type searchType = {
  pageNum: number;
  pageSize: number;
  total: number;
  status: string;
  equipmentCode: string;
};

const data = ref<DataType[]>([]);
const searchForm = ref<searchType>({
  pageNum: 1,
  pageSize: 10,
  status: '',
  equipmentCode: '',
  total: 0
});
const statusMap = {
  0: {
    key: 'default',
    label: '确认到货'
  },
  1: {
    key: 'warning',
    label: '确认完成'
  },
  2: {
    key: 'success',
    label: '已到货'
  }
};
function getStatus(status: string) {
  switch (status) {
    case '0':
      return '维修中';
    case '1':
      return '审批中';
    case '2':
      return '已完成';
    default:
      return '';
  }
}
function handleSearch() {
  searchForm.value.pageNum = 1;
  init();
}
function handleReset() {
  searchForm.value = {
    pageNum: 1,
    pageSize: 10,
    status: '',
    equipmentCode: '',
    total: 0
  };
  init();
}
function handleInfo(row) {
  addKnowledgeRepairReport(row).then(res => {
    if (res.code === 200) {
      message.success('写入知识库成功');
      init();
    }
  });
}
type DataType = {
  id?: number;
  equipmentCode: string;
  breakdownName: string;
  breakdownDescription: string;
  breakdownImage: string;
  applyTime: string;
  applyBy: string;
  repairTime: string;
  repairBy: string;
  repairMethod: string;
  status: string;
  isKnowledge: number;
};
const imageList = ref<Array<string>>([]);
const rowKey = (row: any) => row.id;
const columns: Ref<DataTableColumns<DataType>> = ref([
  {
    type: 'selection'
  },
  {
    title: '序号',
    key: 'index',
    width: 100,
    align: 'center',
    render: (_row, index) => (searchForm.value.pageNum - 1) * searchForm.value.pageSize + index + 1
  },
  {
    title: '设备编码',
    align: 'center',
    width: 100,
    key: 'equipmentCode'
  },
  {
    title: '故障类型',
    align: 'center',
    width: 100,
    key: 'breakdownName'
  },
  {
    title: '故障描述',
    align: 'center',
    width: 100,
    key: 'breakdownDescription'
  },
  {
    title: '故障图片',
    align: 'center',
    width: 100,
    key: 'breakdownImage',
    ellipsis: {
      tooltip: true
    },
    render: row => {
      let images: Array<string> = [];
      if (row.breakdownImage) {
        images = row.breakdownImage.split(',');
      }
      if (images.length > 1) {
        images = images.slice(0, 1);
      }
      return (
        <n-image-group>
          <n-space>
            {images.map((item, index) => {
              const itemSrc = item.replace('http:', 'https:');
              return (
                <n-image
                  width="100"
                  src={itemSrc}
                  key={index}
                  preview-disabled={true}
                  onClick={() => {
                    previewImages(row.breakdownImage);
                  }}
                />
              );
            })}
          </n-space>
        </n-image-group>
      );
    }
  },
  {
    title: '报修时间',
    align: 'center',
    width: 100,
    key: 'applyTime'
  },
  {
    title: '报修人',
    align: 'center',
    width: 100,
    key: 'applyBy'
  },
  {
    title: '维修时间',
    align: 'center',
    width: 150,
    key: 'repairTime'
  },
  {
    title: '维修人',
    align: 'center',
    width: 150,
    key: 'repairBy'
  },
  {
    title: '维修方法',
    align: 'center',
    width: 100,
    key: 'repairMethod'
  },
  {
    title: '审批人',
    align: 'center',
    width: 100,
    key: 'approveBy'
  },
  {
    title: '状态',
    align: 'center',
    width: 100,
    key: 'status',
    render(row) {
      return <n-tag type={statusMap[row.status].key}>{getStatus(row.status)}</n-tag>;
    }
  },
  {
    title: '操作',
    align: 'center',
    width: 200,
    key: 'actions',
    render: row => {
      return [
        <n-button
          color="#85E05B"
          size="tiny"
          class="mr"
          v-show={row.status === '2' && row.isKnowledge === 0}
          onClick={() => handleInfo(row)}
        >
          <ArrowRedo class="mr-1px text-15px w-13px" />
          写入知识库
        </n-button>,
        useDelBtn(() => {
          deleteRepairReport(row).then(res => {
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
  checked.value = rowKeys;
};
function previewImages(imageUrl: string) {
  imageList.value = imageUrl.split(/,/);
}
function init() {
  startLoading();
  data.value = [];
  getRepairReportList(searchForm.value).then(res => {
    data.value = res.rows;
    searchForm.value.total = res.total;
  });
  endLoading();
}
onMounted(() => {
  init();
});
</script>

<style scoped></style>
