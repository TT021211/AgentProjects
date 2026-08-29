<template>
  <div class="h-full">
    <my-card title="搜索条件">
      <n-form ref="formRef" inline label-width="auto" :model="formValue" size="medium">
        <n-form-item label="登录地址">
          <n-input v-model:value="formValue.ipaddr" placeholder="请输入登录地址" />
        </n-form-item>
        <n-form-item label="用户名称">
          <n-input v-model:value="formValue.userName" placeholder="请输入操作人员" />
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
            v-hasPermi="['system:logininfor:remove']"
            type="error"
            class="mr-5px ml-5px"
            size="small"
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
            v-hasPermi="['system:logininfor:remove']"
            class="mr-5px"
            size="small"
            type="warning"
            @click="
              showModal = true;
              flag = 1;
            "
          >
            <icon-ic-round-delete class="mr-4px text-20px" />
            清空
          </n-button>
          <n-button
            v-hasPermi="['system:logininfor:export']"
            class="mr-5px"
            size="small"
            type="primary"
            color="#2cc2d9"
            @click="handleExport"
          >
            <icon-tdesign-arrow-right-up-circle class="mr-4px text-20px" />
            导出
          </n-button>
          <n-button class="mr-5px" size="small" type="primary" @click="getLoginData">
            <icon-mdi-refresh class="mr-4px text-16px" :class="{ 'animate-spin': loading }" />
            刷新表格
          </n-button>
        </div>
      </template>
      <div>
        <n-data-table
          :columns="columns"
          :data="loginData"
          :loading="loading"
          :row-key="rowKey"
          @update:checked-row-keys="handleCheck"
        />
        <n-pagination
          :page="page"
          :page-size="formValue.pageSize"
          :item-count="total"
          :page-sizes="[10, 20, 50, 100]"
          show-size-picker
          style="margin-top: 20px; float: right"
          @update:page="changePage"
          @update-page-size="changePageSize"
        >
          <template #prefix="{ itemCount }">共{{ itemCount }}条</template>
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
import { loginStatusLabels } from '@/constants';
import { loginList, delLogininfor, cleanLogininfor } from '@/service';
import { useLoading } from '@/hooks';
import { formatDate, addDateRange } from '@/utils';
import { download } from '@/service/request/helpers';

const loginData = ref();
const format = 'yyyy-MM-dd hh:mm:ss';
const message = useMessage();
const rowKey = row => row.infoId;
const showModal = ref(false);
const isBacthDelete = ref(true);
const form = ref();
const flag = ref(1);
const delIds = ref();
const showExport = ref(false);
const total = ref();
const page = ref(1);
const daterange = ref();
const show = ref(false);
const formValue = ref({
  pageNum: 1,
  pageSize: 6,
  ipaddr: undefined,
  userName: undefined,
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

const columns: Ref<DataTableColumns<LoginManagement.LoginType>> = ref([
  {
    type: 'selection',
    align: 'center'
  },
  {
    title: '访问编号',
    key: 'infoId',
    align: 'center'
  },
  {
    title: '用户名称',
    key: 'userName',
    align: 'center'
  },

  {
    title: '地址',
    key: 'ipaddr',
    align: 'center'
  },

  {
    title: '描述',
    key: 'msg',
    align: 'center'
  },
  {
    title: '登录状态',
    key: 'status',
    render: row => {
      if (row.status.toString()) {
        const tagTypes: Record<LoginManagement.LoginStatusKey, NaiveUI.ThemeColor> = {
          0: 'success',
          1: 'error'
        };

        return <NTag type={tagTypes[row.status]}>{loginStatusLabels[row.status]}</NTag>;
      }
      return <span></span>;
    },
    align: 'center'
  },

  {
    title: '访问时间',
    key: 'accessTime',
    align: 'center',
    defaultSortOrder: 'ascend',
    sorter: 'default'
  }
]) as Ref<DataTableColumns<LoginManagement.LoginType>>;

const { loading, startLoading, endLoading } = useLoading(false);
// const columnsRef = ref(columns);
// const { setTrue: openModal } = useBoolean();

// 搜索
function handleValidateClick() {
  getLoginData();
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
  getLoginData();
}

// 获取用户信息
async function getLoginData() {
  startLoading();
  let dateArr;
  if (daterange.value) {
    dateArr = daterange.value.map(item => {
      return formatDate(new Date(item), format);
    });
  }
  await loginList({ params: addDateRange(formValue.value, dateArr, undefined) }).then(res => {
    loginData.value = res.rows;
    total.value = res.total;
    if (loginData.value) {
      setTimeout(() => {
        endLoading();
      }, 200);
    }
  });
}

// 确认删除
async function onPositiveClick() {
  if (!flag.value) {
    await delLogininfor(delIds.value).then(() => {
      message.success('删除成功');
    });
  } else {
    cleanLogininfor();
  }
  getLoginData();
}

// 改变页签
function changePage(pageNum) {
  formValue.value.pageNum = pageNum;
  page.value = pageNum;
  getLoginData();
}
function changePageSize(pageSize) {
  formValue.value.pageSize = pageSize;
  getLoginData();
}
// 导出数据
function handleExport() {
  showExport.value = true;
  download('system/logininfor/export', formValue.value, `logininfor_${new Date().getTime()}.xlsx`, undefined)
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

function init() {
  getLoginData();
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
