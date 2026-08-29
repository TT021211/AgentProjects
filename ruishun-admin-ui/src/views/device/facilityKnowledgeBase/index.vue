<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item label="设备编码">
          <n-input v-model:value="searchForm.equipmentCode" placeholder="请输入设备编码"></n-input>
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
import { ChatboxEllipsesOutline } from '@vicons/ionicons5';
import { getKnowledgeRepairReportList, deleteKnowledgeByIds } from '@/service/api/device/repairForm';
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
  // eslint-disable-next-line no-console
  console.log(row);
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
function previewImages(imageUrl: string) {
  imageList.value = imageUrl.split(/,/);
}
const rowKey = (row: any) => row.id;
const columns: Ref<DataTableColumns<DataType>> = ref([
  {
    type: 'selection'
  },
  {
    title: '序号',
    key: 'index',
    align: 'center',
    render: (_row, index) => (searchForm.value.pageNum - 1) * searchForm.value.pageSize + index + 1
  },
  {
    title: '设备编码',
    align: 'center',
    key: 'equipmentCode'
  },
  {
    title: '故障类型',
    align: 'center',
    key: 'breakdownName'
  },
  {
    title: '故障描述',
    align: 'center',
    key: 'breakdownDescription'
  },
  {
    title: '故障图片',
    align: 'center',
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
    key: 'applyTime'
  },
  {
    title: '报修人',
    align: 'center',
    key: 'applyBy'
  },
  {
    title: '维修时间',
    align: 'center',
    key: 'repairTime'
  },
  {
    title: '维修人',
    align: 'center',
    key: 'repairBy'
  },
  {
    title: '维修方法',
    align: 'center',
    key: 'repairMethod'
  },
  {
    title: '审批人',
    align: 'center',
    key: 'approveBy'
  },
  {
    title: '操作',
    align: 'center',
    width: '200',
    key: 'actions',
    render: row => {
      return [
        <n-button color="#756CF2" size="tiny" class="mr" onClick={() => handleInfo(row)}>
          <ChatboxEllipsesOutline class="mr-3px text-15px w-13px" />
          评论
        </n-button>,
        useDelBtn(() => {
          deleteKnowledgeByIds(row).then(res => {
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

function init() {
  startLoading();
  data.value = [];
  getKnowledgeRepairReportList(searchForm.value).then(res => {
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
