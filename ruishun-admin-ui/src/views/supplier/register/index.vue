<template>
  <div>
    <my-card title="搜索">
      <n-form inline>
        <n-form-item label="供应商全称">
          <n-input v-model:value="searchForm.supplierName" placeholder="请输入供应商全称" />
        </n-form-item>
        <n-form-item label="名称">
          <n-input v-model:value="searchForm.name" placeholder="请输入名称" />
        </n-form-item>
        <n-form-item>
          <n-button type="primary" class="mr-10px" @click="handleSearch">
            <icon-ic-round-search class="mr-4px text-20px" />
            搜索
          </n-button>
          <n-button @click="handleReset">
            <icon-ic-round-refresh class="mr-4px text-20px" />
            重置
          </n-button>
        </n-form-item>
      </n-form>
    </my-card>

    <my-card title="联系人列表">
      <template #right>
        <div class="flex-center">
          <n-button type="info" size="small" class="mr-10px" @click="addOrEdit(0)">
            <icon-mdi-add />
            新增
          </n-button>
          <CxColumns v-model:columns="columns" />
        </div>
      </template>
      <n-data-table
        :data="data"
        :loading="loading"
        :columns="columns"
        :scroll-x="dataTableConfig.scrollWidth(columns)"
      ></n-data-table>
    </my-card>

    <my-dialog
      v-model:show="show"
      :title="addOrEditForm.id ? '编辑联系人' : '新增联系人'"
      width="900px"
      @cancel="show = false"
      @submit="submit"
    >
      <template #content>
        <div>
          <n-form
            ref="formModel"
            style="width: 800px"
            :rules="rules"
            :model="addOrEditForm"
            label-placement="left"
            label-width="120px"
          >
            <n-grid :cols="2" :x-gap="20">
              <n-form-item-grid-item label="供应商全称" path="supplierName">
                <n-input v-model:value="addOrEditForm.supplierName" placeholder="请输入供应商全称" />
              </n-form-item-grid-item>
              <n-form-item-grid-item label="名称" path="name">
                <n-input v-model:value="addOrEditForm.name" placeholder="请输入联系人名称" />
              </n-form-item-grid-item>
              <n-form-item-grid-item label="职位" path="position">
                <n-input v-model:value="addOrEditForm.position" placeholder="请输入联系人职位" />
              </n-form-item-grid-item>
              <n-form-item-grid-item label="电话">
                <n-input v-model:value="addOrEditForm.phone" placeholder="请输入职务" />
              </n-form-item-grid-item>
              <n-form-item-grid-item label="邮箱">
                <n-input v-model:value="addOrEditForm.email" placeholder="请输入邮箱" />
              </n-form-item-grid-item>
              <n-form-item-grid-item label="地址">
                <n-input v-model:value="addOrEditForm.address" placeholder="请输入联系人详情" />
              </n-form-item-grid-item>
            </n-grid>
          </n-form>
        </div>
      </template>
    </my-dialog>
  </div>
</template>

<script setup lang="tsx">
import type { Ref } from 'vue';
import { ref, onMounted } from 'vue';
import type { FormInst, DataTableColumns } from 'naive-ui';
// import { useDialog } from 'naive-ui';
import { createRequiredFormRule } from '@/utils';
import { dataTableConfig } from '@/config/dataTableConfig';
import { useResetSearch } from '~/src/utils/common/searchReset';
import { useLoading } from '~/src/hooks/index';

const linkmanList = ref<Array<any>>([
  {
    id: 1,
    supplierName: '余江县银瑞贸易有限公司',
    name: '银瑞',
    address: '江西省鹰潭市',
    bank: '中国银行',
    product: '五金配件',
    totalTax: '增值税',
    taxRate: '13%'
  },
  {
    id: 2,
    supplierName: '苏州金菱电气自动化有限公司',
    name: '金菱',
    address: '江苏省苏州市',
    bank: '中国建设银行',
    product: '汇川变频器',
    totalTax: '印花税,增值税',
    taxRate: '13%'
  },
  {
    id: 3,
    supplierName: '江西德位精密模具有限责任公司',
    name: '德位精密模具',
    address: '江西省鹰潭市',
    bank: '中国银行',
    product: '拉丝模具、镀锡模、绞线模',
    totalTax: '印花税,土地使用税,车船使用税',
    taxRate: '13%'
  },
  {
    id: 4,
    supplierName: '上海申辰线缆设备有限公司',
    name: '申辰线缆',
    address: '上海市杨浦区',
    bank: '浦发银行',
    product: '单孔模具、I型模具维修',
    totalTax: '增值税,土地使用税',
    taxRate: '13%'
  },
  {
    id: 5,
    supplierName: '鹰潭市秉昭贸易有限公司',
    name: '秉昭',
    address: '江西省鹰潭市',
    bank: '中国工商银行',
    product: '5寸、8寸、9寸线轴',
    totalTax: '印花税,土地使用税',
    taxRate: '13%'
  },
  {
    id: 6,
    supplierName: '天长市天连金属制品有限公司',
    name: '天连金属',
    address: '安徽省天长市',
    bank: '中国银行',
    product: '拉丝模具租赁、钻石镀锡模',
    totalTax: '印花税,土地使用税,车船使用税',
    taxRate: '13%'
  },

  {
    id: 7,
    supplierName: '江西荣欣新材料有限公司',
    name: '荣欣',
    address: '江西省鹰潭市',
    bank: '‌中国建设银行',
    product: '5寸、8寸、9寸线轴',
    totalTax: '增值税普通发票',
    taxRate: '9%'
  },
  {
    id: 8,
    supplierName: '常州常华光电塑胶有限公司',
    name: '华光电塑胶',
    address: '江苏省常州市',
    bank: '‌中国建设银行',
    product: '5寸、8寸、9寸线轴',
    totalTax: '400#线盘',
    taxRate: '13%'
  },
  {
    id: 9,
    supplierName: '江西卓裕彩印有限公司',
    name: '卓裕彩印',
    address: '江西省鹰潭市',
    bank: '‌中国建设银行',
    product: '合格证、标签',
    totalTax: '增值税普通发票',
    taxRate: '6%'
  },
  {
    id: 10,
    supplierName: '常州鼎天电工机械有限公司',
    name: '鼎天',
    address: '江苏省常州市',
    bank: '‌中国银行',
    product: 'PCL设备维修',
    totalTax: '增值税普通发票',
    taxRate: '13%'
  },
  {
    id: 11,
    supplierName: '太仓苏润润滑油有限公司',
    name: '苏润',
    address: '江苏省苏州市',
    bank: '‌中国建设银行',
    product: '助焊剂',
    totalTax: '增值税普通发票',
    taxRate: '9%'
  }
]);

const { searchForm, reset } = useResetSearch({
  pageNum: 1,
  pageSize: 10,
  total: 0,
  supplierName: '',
  name: ''
});

const addOrEditForm = ref({
  id: null,
  supplierName: '',
  name: '',
  position: '',
  phone: '',
  email: '',
  address: ''
});

const rules = {
  supplierName: createRequiredFormRule('请输入供应商全称'),
  name: createRequiredFormRule('请输入名称')
};

const formModel = ref<FormInst | null>(null);

const addOrEditType = ref<number>(0); // 0 : 添加， 1 : 编辑

const show = ref<boolean>(false);

const { loading, startLoading, endLoading } = useLoading();

const data = ref<contactPerson.TableList[]>([]);

const columns: Ref<DataTableColumns<contactPerson.TableList>> = ref([
  {
    title: '序号',
    key: 'index',
    align: 'center',
    render: (_row, index) => index + 1,
    width: 100
  },
  {
    title: '供应商全称',
    key: 'supplierName',
    align: 'center',
    width: 180,
    ellipsis: {
      tooltip: true
    }
  },
  {
    title: '简称',
    key: 'name',
    align: 'center',
    width: 180,
    ellipsis: {
      tooltip: true
    }
  },
  {
    title: '开户行',
    key: 'bank',
    align: 'center',
    width: 180,
    ellipsis: {
      tooltip: true
    }
  },
  {
    title: '地址',
    key: 'address',
    align: 'center',
    width: 120
  },
  {
    title: '主要供应产品',
    key: 'product',
    align: 'center',
    width: 120,
    ellipsis: {
      tooltip: true
    }
  },
  {
    title: '稅种',
    key: 'totalTax',
    align: 'center',
    width: 120,
    ellipsis: {
      tooltip: true
    }
  },
  {
    title: '税率',
    key: 'taxRate',
    align: 'center',
    width: 120
  },
  {
    title: '操作',
    key: 'actions',
    width: 180,
    align: 'center',
    fixed: 'right',
    render: row => {
      return (
        <n-space justify={'center'}>
          <n-button
            attr-type="button"
            size={'small'}
            type="warning"
            onClick={() => {
              addOrEdit(1, row);
            }}
          >
            <icon-mdi-edit />
            编辑
          </n-button>

          <n-button
            type="error"
            size={'small'}
            onClick={() => {
              console.log('1');
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

// function handleUpdateValue(val, option) {
//   console.log('val ==>', val);
//   addOrEditForm.value.clientName = option.clientName;
//   addOrEditForm.value.clientCode = option.clientCode;
// }

function addOrEdit(num: number, row?: contactPerson.TableList) {
  addOrEditType.value = num;
  show.value = true;
  addOrEditForm.value = {
    id: null,
    supplierName: '',
    name: '',
    position: '',
    phone: '',
    email: '',
    address: ''
  };
  if (num === 1) {
    if (!row) return;
    for (const key in addOrEditForm.value) {
      if (row[key] || row[key] === '0') {
        addOrEditForm.value[key] = row[key];
      }
    }
  }
}

// function handleDeleteItemType(id: number) {
//   const d = dialog.warning({
//     title: '删除',
//     content: '确认要删除吗?',
//     positiveText: '确认',
//     negativeText: '取消',
//     onPositiveClick: () => {
//       d.loading = true;
//       return new Promise(resolve => {
//         deleteCrmContactPerson(id).then(res => {
//           if (res.code === 200) {
//             init();
//             window.$message?.success('删除成功');
//           }
//           resolve(true);
//         });
//       });
//     }
//   });
// }

function submitSame(func: any, msg: string): void {
  func(addOrEditForm.value).then(res => {
    if (res.code === 200) {
      linkmanList.value.push(addOrEditForm.value);
      show.value = false;
      window.$message?.success(msg);
      init();
    }
  });
}

function add() {
  console.log('add');
  return new Promise(reslove => {
    reslove({ code: 200 });
  });
}

function edit() {
  console.log('edit');
}

function submit(): void {
  if (!formModel.value) return;
  formModel.value.validate(errors => {
    if (!errors) {
      if (addOrEditType.value === 0) {
        submitSame(add, '添加成功');
      } else {
        submitSame(edit, '编辑成功');
      }
    }
  });
}

function handleSearch() {
  init();
}
function handleReset() {
  reset();
  init();
}

function init() {
  startLoading();
  setTimeout(() => {
    // for (const key in addOrEditForm.value) {
    //   if (Object.hasOwn(addOrEditForm.value, key)) {
    //    if ()
    //   }
    // }
    data.value = linkmanList.value;
    endLoading();
  }, 300);
}

onMounted(() => {
  init();
});
</script>

<style lang="scss" scoped></style>
