<template>
  <div class="h-full">
    <my-card title="搜索条件" search>
      <n-form ref="formRef" inline label-width="auto" :model="formValue" size="medium">
        <n-form-item label="系统模块">
          <n-input v-model:value="formValue.title" placeholder="请输入系统模块" />
        </n-form-item>
        <n-form-item label="操作人员">
          <n-input v-model:value="formValue.operName" placeholder="请输入操作人员" />
        </n-form-item>
        <n-form-item label="类型">
          <n-select v-model:value="formValue.businessType" :options="businessTypeSelect.options" style="width: 200px" />
        </n-form-item>
        <n-form-item label="状态">
          <n-select v-model:value="formValue.status" :options="statusSelect.options" style="width: 200px" />
        </n-form-item>
        <n-form-item label="操作时间">
          <n-date-picker v-model:value="daterange" type="daterange" :format="format" />
        </n-form-item>

        <n-form-item>
          <n-button type="primary" class="mr-10px" @click="handleValidateClick">
            <icon-ic-round-search class="mr-4px text-20px" />
            搜索
          </n-button>
          <n-button @click="reset">
            <icon-ic-round-refresh class="mr-4px text-20px" />
            重置
          </n-button>
        </n-form-item>
      </n-form>
    </my-card>
    <my-card title="列表">
      <template #right>
        <div class="flex-center">
          <cx-columns v-model:columns="columns" size="small"></cx-columns>
          <n-button
            v-hasPermi="['system:operlog:remove']"
            type="error"
            size="small"
            class="mr-5px ml-5px"
            :disabled="isBacthDelete"
            @click="
              showModal = true;
              flag = 0;
            "
          >
            <icon-ic-round-delete class="mr-4px text-20px" />
            删除
          </n-button>
          <n-button
            v-hasPermi="['system:operlog:remove']"
            type="warning"
            size="small"
            class="mr-5px"
            @click="
              showModal = true;
              flag = 1;
            "
          >
            <icon-ic-round-delete class="mr-4px text-20px" />
            清空
          </n-button>
          <n-button
            v-hasPermi="['system:operlog:export']"
            size="small"
            class="mr-5px"
            type="primary"
            color="#2cc2d9"
            @click="handleExport"
          >
            <icon-tdesign-arrow-right-up-circle class="mr-4px text-20px" />
            导出
          </n-button>
          <n-button size="small" class="mr-5px" type="primary" @click="getOperaData">
            <icon-mdi-refresh class="mr-4px text-16px" :class="{ 'animate-spin': loading }" />
            刷新表格
          </n-button>
        </div>
      </template>
      <div>
        <n-data-table
          :columns="columns"
          :data="operaData"
          :loading="loading"
          :row-key="rowKey"
          @update:checked-row-keys="handleCheck"
        />
        <n-pagination
          :page="page"
          :page-size="formValue.pageSize"
          :page-sizes="[10, 20, 50, 100]"
          show-size-picker
          :item-count="total"
          style="margin-top: 20px; float: right"
          @update:page="changePage"
          @update-page-size="changePageSize"
        >
          <template #prefix="{ itemCount }">共 {{ itemCount }} 条</template>
        </n-pagination>
      </div>

      <n-modal
        v-model:show="showModal"
        :mask-closable="false"
        preset="dialog"
        title="用户退出"
        :content="`你真的要${flag ? '清空' : '删除'}这些记录吗？`"
        positive-text="确认"
        negative-text="算了"
        @positive-click="onPositiveClick"
      />
      <n-drawer v-model:show="show" :width="502">
        <n-drawer-content title="操作日志详情" closable>
          <n-form ref="formRef" :label-width="80" :model="form">
            <n-grid :cols="24" :x-gap="18">
              <!-- <n-form-item-grid-item :span="12" label="操作模块">
                    {{ form.title }} / {{ typeFormat(form) }}
                  </n-form-item-grid-item> -->
              <n-form-item-grid-item :span="12" label="请求地址">{{ form.operUrl }}</n-form-item-grid-item>
              <n-form-item-grid-item :span="12" label="登录信息">
                {{ form.operName }} / {{ form.operIp }}
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="12" label="请求方式">{{ form.requestMethod }}</n-form-item-grid-item>
              <n-form-item-grid-item :span="12" label="操作方法">{{ form.method }}</n-form-item-grid-item>
              <n-form-item-grid-item v-if="form.operParam" :span="24" label="请求参数">
                {{ form.operParam }}
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="12" label="返回参数">{{ form.jsonResult }}</n-form-item-grid-item>
              <n-form-item-grid-item :span="12" label="操作状态">
                <div v-if="form.status === 0">正常</div>
                <div v-else-if="form.status === 1">失败</div>
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="12" label="消耗时间">{{ form.costTime }}毫秒</n-form-item-grid-item>
              <n-form-item-grid-item :span="12" label="操作时间">
                {{ form.operTime }}
              </n-form-item-grid-item>
              <n-form-item-grid-item v-if="form.status === 1" :span="12" label="操作时间">
                {{ form.errorMsg }}
              </n-form-item-grid-item>
            </n-grid>
          </n-form>
        </n-drawer-content>
      </n-drawer>
    </my-card>
  </div>
</template>

<script lang="tsx" setup>
import { ref } from 'vue';
import type { Ref } from 'vue';
import { NButton, useMessage, NTag } from 'naive-ui';
import type { DataTableColumns, DataTableRowKey } from 'naive-ui';
// import { ReorderThreeOutline, Sync } from '@vicons/ionicons5';
import { operaBusinessLabels, operaStatusLabels } from '@/constants';
import { operaList, delOperlog, cleanOperlog } from '@/service';
import { useLoading } from '@/hooks';
import { formatDate, addDateRange } from '@/utils';
import { download } from '@/service/request/helpers';

const operaData = ref();
const format = 'yyyy-MM-dd hh:mm:ss';
const message = useMessage();
const rowKey = row => row.operId;
const showModal = ref(false);
const isBacthDelete = ref(true);
const form = ref();
const flag = ref(1);
const delIds = ref();
const total = ref();
const page = ref(1);
const showExport = ref(false);
const daterange = ref();
const detailId = ref();
const show = ref(false);
const formValue = ref({
  pageNum: 1,
  pageSize: 10,
  title: undefined,
  operName: undefined,
  businessType: undefined,
  status: undefined
});
const statusSelect = ref({
  options: [
    {
      label: '成功',
      value: 0
    },
    {
      label: '失败',
      value: 1
    }
  ]
});
const businessTypeSelect = ref({
  options: [
    {
      label: '新增',
      value: 1
    },
    {
      label: '删除',
      value: 3
    },
    {
      label: '修改',
      value: 2
    },
    {
      label: '授权',
      value: 4
    },
    {
      label: '导出',
      value: 5
    },
    {
      label: '退出',
      value: 7
    },
    {
      label: '清空数据',
      value: 9
    },
    {
      label: '其他',
      value: 0
    }
  ]
});
const columns: Ref<DataTableColumns<OperaManagement.OperaType>> = ref([
  {
    type: 'selection',
    align: 'center'
  },
  {
    title: '日志编号',
    key: 'operId',
    align: 'center'
  },
  {
    title: '系统模块',
    key: 'title',
    align: 'center'
  },
  {
    title: '操作类型',
    key: 'businessType',
    render: row => {
      if (row.businessType) {
        const tagTypes: Record<OperaManagement.OperaBusinessTypeKey, NaiveUI.ThemeColor> = {
          1: 'success',
          2: 'warning',
          3: 'error',
          4: 'info',
          5: 'primary',
          6: 'info',
          7: 'error',
          9: 'success'
        };

        return <NTag type={tagTypes[row.businessType]}>{operaBusinessLabels[row.businessType]}</NTag>;
      }
      return <span></span>;
    },
    align: 'center'
  },
  {
    title: '请求方式',
    key: 'requestMethod',
    align: 'center'
  },
  {
    title: '操作人员',
    key: 'operName',
    align: 'center'
  },
  {
    title: '主机',
    key: 'operIp',
    align: 'center'
  },
  {
    title: '操作状态',
    key: 'status',
    render: row => {
      if (row.status.toString()) {
        const tagTypes: Record<OperaManagement.OperaStatusKey, NaiveUI.ThemeColor> = {
          0: 'success',
          1: 'error'
        };

        return <NTag type={tagTypes[row.status]}>{operaStatusLabels[row.status]}</NTag>;
      }
      return <span></span>;
    },
    align: 'center'
  },
  {
    title: '消耗时间',
    key: 'costTime',
    align: 'center'
  },
  {
    title: '操作日期',
    key: 'operTime',
    align: 'center',
    defaultSortOrder: 'ascend',
    sorter: 'default'
  },
  {
    title: '操作',
    key: 'action',
    align: 'center',
    render: row => {
      return (
        <n-space justify={'center'}>
          <n-button
            size={'tiny'}
            type="primary"
            onClick={() => {
              show.value = true;
              detailId.value = row.operId;
              form.value = row;
            }}
          >
            <icon-tdesign-file-import class="mr-2px text-15px " />
            详情
          </n-button>
        </n-space>
      );
    }
  }
]) as Ref<DataTableColumns<OperaManagement.OperaType>>;

const { loading, startLoading, endLoading } = useLoading(false);

// 搜索
function handleValidateClick() {
  getOperaData();
}

// 重置
function reset() {
  formValue.value.pageNum = 1;
  page.value = 1;
  daterange.value = null;
  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  const { pageNum, pageSize, ...rest } = formValue.value;
  for (const key in rest) {
    if (formValue.value[key] !== '') {
      formValue.value[key] = '';
    }
  }
  getOperaData();
}

// 获取用户信息
async function getOperaData() {
  startLoading();
  let dateArr;
  if (daterange.value) {
    dateArr = daterange.value.map(item => {
      return formatDate(new Date(item), format);
    });
  }
  await operaList({ params: addDateRange(formValue.value, dateArr, undefined) }).then(res => {
    operaData.value = res.rows;
    total.value = res.total;
    if (operaData.value) {
      setTimeout(() => {
        endLoading();
      }, 200);
    }
  });
}

// 确认删除
async function onPositiveClick() {
  if (!flag.value) {
    await delOperlog(delIds.value).then(() => {
      message.success('删除成功');
    });
  } else {
    cleanOperlog();
  }
  getOperaData();
}

// 改变页签
function changePage(pageNum) {
  formValue.value.pageNum = pageNum;
  page.value = pageNum;
  getOperaData();
}
function changePageSize(pageSize) {
  formValue.value.pageSize = pageSize;
  getOperaData();
}
// 导出数据
function handleExport() {
  showExport.value = true;
  download('system/logininfor/export', formValue.value, `operlog_${new Date().getTime()}.xlsx`, undefined)
    .then(() => {
      showExport.value = false;
    })
    .catch(() => {
      showExport.value = false;
    });
}
// 弹出确认框
function handleCheck(rowKeys: DataTableRowKey[]) {
  if (rowKeys.length > 0) {
    isBacthDelete.value = false;
    delIds.value = rowKeys;
  } else {
    isBacthDelete.value = true;
  }
}
// function typeFormat(row, column) {
//   return this.selectDictLabel(form, row.businessType);
// }
function init() {
  getOperaData();
}
init();
</script>
<style scoped>
.n-form {
  display: flex;
  flex-wrap: wrap;
}
.n-form .n-form-item-grid-item {
  color: aqua;
}
</style>
