<template>
  <div>
    <my-card search title="搜索条件">
      <n-form inline label-placement="left">
        <n-form-item label="打印类型">
          <n-select
            v-model:value="searchForm.createBy"
            class="w-180px"
            :options="[
              { value: '0', label: '轴模板' },
              { value: '1', label: '箱模板' },
              { value: '2', label: '托模板' },
              { value: '3', label: '原料模板' },
              { value: '4', label: '报工模板' },
              { value: '5', label: '废丝模板' }
            ]"
          ></n-select>
        </n-form-item>
        <n-form-item label="模板类型">
          <n-select v-model:value="searchForm.templateCode" class="w-180px" :options="printTypeList"></n-select>
        </n-form-item>
        <n-form-item label="所属客户">
          <n-select v-model:value="searchForm.clientId" class="w-180px" :options="saleUserList"></n-select>
        </n-form-item>
        <n-form-item>
          <component :is="useSearchBtn(search, reset)"></component>
        </n-form-item>
      </n-form>
    </my-card>
    <my-card title="模板列表">
      <template #right>
        <div>
          <component :is="useAddBtn(add, 'small', '新增')"></component>
        </div>
      </template>
      <n-data-table :columns="columns" :data="data"></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="init"></my-pagination>
    </my-card>
    <edit-dialog
      :id="tempid"
      v-model:show="show"
      :client-options="saleUserList"
      :print-type-list="printTypeList"
      :user-list="userList"
      @init="init"
    ></edit-dialog>
    <my-dialog v-model:show="addTemplate" title="添加打印模板" @submit="addTemplateWay" @cancel="addTemplate = false">
      <template #content>
        <div class="w-full h-full">
          <n-form label-placement="left" label-width="100">
            <n-form-item label="模板类型">
              <n-select
                v-model:value="addForm.templateCode"
                :options="printTypeList"
                placeholder="请选择打印模板"
              ></n-select>
            </n-form-item>
          </n-form>
        </div>
      </template>
    </my-dialog>
  </div>
</template>

<script setup lang="tsx">
import { ref, onMounted } from 'vue';
import type { Ref } from 'vue';
import { useRouter } from 'vue-router';
import type { DataTableColumns } from 'naive-ui';
import { useMessage } from 'naive-ui';
import {
  getPrintTemplateList,
  deletePrintTemplate,
  copyPrintTemplate,
  addPrintTemplate,
  updatePrintTemplate
} from '@/service/api/md/printTemplate';
import { getDictDataList } from '@/service/api/system/dictData';
import { getAllUser, getUserList } from '@/service/api/sale/userManage';
import { useDelBtn, useEditBtn, useInfoBtn, useSearchBtn, useAddBtn } from '~/src/hooks/common/useBtn';
import { fetchUserList } from '~/src/service';
import EditDialog from './component/EditDialog.vue';
// 获取vue的实力proxy
// const { proxy } = getCurrentInstance() as any;
// const { print_type } = proxy.useDict('print_type');
// const obj = proxy.useDict('print_type');
// console.log('obj ==> ', obj);
const printTypeList = ref<{ label: string; value: string }[]>([]);
const userList = ref<{ label: string; value: string }[]>([]);
const saleUserList: any = ref([]);
const router = useRouter();
const show = ref<boolean>(false);
const tempid = ref<number | string>('');
const searchForm = ref({
  pageNum: 1,
  pageSize: 10,
  total: 0,
  templateCode: null,
  clientId: null,
  createBy: null
});
const message = useMessage();
const addTemplate = ref<boolean>(false);
function add() {
  addTemplate.value = true;
  // router.push({ name: 'hiprint' });
}
const addForm = ref({
  templateCode: null
});
function addTemplateWay() {
  addPrintTemplate(addForm.value).then(res => {
    if (res.code === 200) {
      router.push({ name: 'hiprint', query: { templateId: res.data.templateId.toString() } });
    }
  });
  // router.push({ name: 'hiprint', query: { templateCode: addForm.value.templateCode } });
}
const data = ref<PrintTemplate.Data[]>([]);
const columns: Ref<DataTableColumns<PrintTemplate.Data>> = ref([
  {
    title: '模板类型',
    titleAlign: 'center',
    align: 'center',
    width: 170,
    key: 'createBy',
    render(row) {
      switch (row.createBy) {
        case '0':
          return <span>{'轴模板'}</span>;
        case '1':
          return <span>{'箱模板'}</span>;
        case '2':
          return <span>{'托模板'}</span>;
        case '3':
          return <span>{'原料模板'}</span>;
        case '4':
          return <span>{'报工模板'}</span>;
        case '5':
          return <span>{'废丝模板'}</span>;
        default:
          return <span>{row.createBy}</span>;
      }
    }
  },
  {
    title: '模板名称',
    titleAlign: 'center',
    align: 'center',
    width: 170,
    key: 'templateName'
  },
  // {
  //   title: '字典值',
  //   align:'center',
  //   width:170,
  //   key: 'templateCode'
  // },
  {
    title: '所属客户',
    titleAlign: 'center',
    align: 'center',
    width: 180,
    key: 'clientNick'
  },
  {
    title: '所属用户',
    titleAlign: 'center',
    align: 'center',
    width: 120,
    key: 'userNickName'
  },
  {
    title: '创建时间',
    titleAlign: 'center',
    align: 'center',
    width: 180,
    key: 'createTime'
  },
  {
    title: '是否为默认模板',
    titleAlign: 'center',
    align: 'center',
    width: 130,
    key: 'status',
    render: row => {
      return (
        <my-switch
          v-model:value={row.status}
          checked={'1'}
          unchecked={'0'}
          onChecked={val => {
            updateStatus(val, row);
          }}
          onUnchecked={val => {
            updateStatus(val, row);
          }}
        ></my-switch>
      );
    }
  },
  {
    title: '操作',
    key: 'action',
    titleAlign: 'center',
    align: 'center',
    width: '300',
    render: _row => {
      return [
        useInfoBtn(
          () => {
            router.push({ name: 'hiprint', query: { templateId: _row.templateId } });
          },
          'tiny',
          '设计'
        ),
        useEditBtn(
          () => {
            show.value = true;
            tempid.value = _row.templateId as string;
          },
          'tiny',
          '修改'
        ),
        useInfoBtn(
          () => {
            copyPrintTemplate({ templateId: _row.templateId }).then(res => {
              if (res.code === 200) {
                init();
                window.$message?.success('复制成功');
              }
            });
          },
          'tiny',
          '复制',
          '#546e7a'
        ),
        useDelBtn(
          () => {
            deletePrintTemplate(_row.templateId as string).then(res => {
              if (res.code === 200) {
                init();
                window.$message?.success('删除成功');
              }
            });
          },
          'tiny',
          '删除'
        )
      ];
    }
  }
]);

function updateStatus(val: string, row: PrintTemplate.Data) {
  row.status = val;
  updatePrintTemplate(row).then(res => {
    if (res.code === 200) {
      message.success('状态修改成功');
    }
  });
}

function search() {
  searchForm.value.pageNum = 1;
  init();
}
function reset() {
  searchForm.value = {
    pageNum: 1,
    pageSize: 10,
    total: 0,
    templateCode: null,
    clientId: null,
    createBy: null
  };
  init();
}
function init() {
  getPrintTemplateList(searchForm.value).then(res => {
    data.value = res.rows;
    searchForm.value.total = res.total;
  });
}
const clisentOptions = ref<{ label: string; value: string }[]>([]);
function getClientList() {
  getAllUser().then(res => {
    if (res.code === 200) {
      res.data.forEach(item => {
        clisentOptions.value.push({
          label: item.clientName,
          value: item.id,
          ...item
        });
      });
    }
  });
}

async function getList() {
  const userData = await fetchUserList({ params: { pageSize: 999 } });
  userData.rows.forEach(item => {
    userList.value.push({ label: item.userName, value: item.userId });
  });
  getDictDataList({ dictType: 'print_type', pageSize: 999 }).then(res => {
    if (res.code === 200) {
      res.rows.forEach(item => {
        printTypeList.value.push({ label: item.dictLabel, value: item.dictValue });
      });
    }
  });
  getUserList({ pageSize: 999 }).then(res => {
    if (res.code === 200) {
      res.rows.forEach(item => {
        saleUserList.value.push({ label: item.clientNick, value: item.id });
      });
    }
  });
}

onMounted(() => {
  getClientList();
  init();
  getList();
});
</script>

<style scoped></style>
