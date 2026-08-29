<template>
  <div class="archives">
    <div class="content">
      <div class="top">
        <div class="title">
          <div class="text">客户信息</div>
          <div class="tool">
            <img src="../../../../assets/img/close.png" alt="" class="close" @click="close" />
          </div>
        </div>

        <div class="tool-box">
          <div>
            <n-button size="small" type="info" @click="save('保存成功')">
              <icon-mdi-export class="mr-5px" />
              保存
            </n-button>

            <n-button size="small" type="warning" class="ml-10px" @click="isEdit = false">
              <icon-mdi-edit class="mr-5px" />
              编辑
            </n-button>

            <n-button size="small" type="error" class="ml-10px" @click="deleteInfo">
              <icon-mdi-delete class="mr-5px" />
              清空
            </n-button>
          </div>
        </div>

        <div class="form-widgets">
          <div class="label">客户档案</div>
          <div class="sep-line">
            <div class="sep-bg-wrapper">
              <div class="sep-bg"></div>
            </div>
            <div class="sep-label"></div>
          </div>
        </div>
      </div>

      <div class="center">
        <div>
          <n-form ref="formRef" label-placement="top" inline>
            <n-grid :cols="2">
              <n-form-item-grid-item :span="1" label="客户简称" path="clientNick" style="width: 95%">
                <n-input placeholder="请输入客户简称" :disabled="true" />
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="客户编码" path="clientCode" style="width: 95%">
                <n-input placeholder="请输入客户编码" :disabled="true" />
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="开户银行" path="bankAccount" style="width: 95%">
                <n-input v-model:value="addForm.bankAccount" placeholder="请输入开户银行" :disabled="isEdit" />
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="统一信用社代码" path="creditCode" style="width: 95%">
                <n-input
                  v-model:value="addForm.creditCode"
                  placeholder="请输入统一信用社代码"
                  :disabled="isEdit"
                ></n-input>
              </n-form-item-grid-item>
              <!-- <n-form-item-grid-item :span="1" label="联系人邮箱" style="width: 95%">
                <n-input placeholder="请输入联系人邮箱" :disabled="isEdit"></n-input>
              </n-form-item-grid-item> -->
              <n-form-item-grid-item :span="1" label="公司成立时间" style="width: 95%">
                <n-input
                  v-model:value="addForm.foundingTime"
                  placeholder="请输入公司成立时间"
                  :disabled="isEdit"
                ></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="注册资金" style="width: 95%">
                <n-input
                  v-model:value="addForm.registeredCapital"
                  placeholder="请输入注册资金"
                  :disabled="isEdit"
                ></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="经营范围" style="width: 95%">
                <n-input
                  v-model:value="addForm.businessScope"
                  placeholder="请输入经营范围"
                  :disabled="isEdit"
                ></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="终端/经销商" style="width: 95%">
                <n-select
                  v-model:value="addForm.ifDistributor"
                  :options="[
                    { label: '终端', value: 0 },
                    { label: '经销商', value: 1 }
                  ]"
                  placeholder="请选择"
                  :disabled="isEdit"
                ></n-select>
              </n-form-item-grid-item>

              <n-form-item-grid-item :span="1" label="价格模式" style="width: 95%">
                <n-select
                  v-model:value="addForm.pricingModel"
                  :options="[
                    { label: '报价', value: '报价' },
                    { label: '固定', value: '固定' },
                    { label: '其他', value: '其他' }
                  ]"
                  placeholder="请选择"
                  :disabled="isEdit"
                ></n-select>
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="需求产品">
                <n-input
                  v-model:value="addForm.products"
                  placeholder="请输入需求产品"
                  style="width: 95%"
                  :disabled="isEdit"
                ></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="月需求量">
                <n-input
                  v-model:value="addForm.productsNum"
                  placeholder="请输入月需求量"
                  style="width: 95%"
                  :disabled="isEdit"
                ></n-input>
              </n-form-item-grid-item>
            </n-grid>
          </n-form>
        </div>

        <div class="btn-box">
          <div
            v-for="item in otherContenList"
            :key="item.id"
            class="btn"
            :class="activeId === item.id ? 'active' : ''"
            @click="selectContent(item.id)"
          >
            {{ item.label }}
          </div>
        </div>
        <my-card v-show="activeId === 1" title="联系人信息">
          <n-data-table :loading="recordLoading" :data="data" :columns="columns"></n-data-table>
        </my-card>
        <my-card v-show="activeId === 2" title="跟进记录列表">
          <n-data-table :loading="recordLoading" :data="recordData" :columns="recordColumns"></n-data-table>
        </my-card>
      </div>
    </div>
  </div>
</template>

<script setup lang="tsx">
import { ref } from 'vue';
import type { Ref } from 'vue';
import type { FormInst, DataTableColumns } from 'naive-ui';
import { useDialog } from 'naive-ui';
const props = withDefaults(
  defineProps<{
    fromData: any;
  }>(),
  {
    fromData: {
      bankAccount: '',
      creditCode: '',
      foundingTime: null,
      registeredCapital: '',
      businessScope: '',
      ifDistributor: null,
      pricingModel: null,
      products: '',
      productsNum: ''
    }
  }
);

const addForm = ref(props.fromData);

const initForm = {
  bankAccount: '',
  creditCode: '',
  foundingTime: null,
  registeredCapital: '',
  businessScope: '',
  ifDistributor: null,
  pricingModel: null,
  products: '',
  productsNum: ''
};

type otherContent = {
  label: string;
  id: number;
};

const otherContenList = ref<Array<otherContent>>([
  { label: '联系人信息', id: 1 },
  { label: '跟进记录', id: 2 }
]);

const activeId = ref<number>(1);

const dialog = useDialog();

const formRef = ref<FormInst | null>(null);

const isEdit = ref<boolean>(true);

const recordLoading = ref<boolean>(false);

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
    title: '客户名称',
    key: 'clientName',
    align: 'center',
    width: 180,
    ellipsis: {
      tooltip: true
    }
  },
  {
    title: '客户编码',
    key: 'clientCode',
    align: 'center',
    width: 180,
    ellipsis: {
      tooltip: true
    }
  },
  {
    title: '联系人名称',
    key: 'personName',
    align: 'center',
    width: 120
  },
  {
    title: '联系人电话',
    key: 'personPhone',
    align: 'center',
    width: 120
  },
  {
    title: '部门',
    key: 'section',
    align: 'center',
    width: 120,
    render: row => {
      const sectionType = sectionText(row.section);
      const obj = {
        color: sectionType.color,
        textColor: sectionType.textColor,
        borderColor: sectionType.color
      };
      return <n-tag color={obj}>{sectionType.label}</n-tag>;
    }
  },
  {
    title: '职务',
    key: 'job',
    align: 'center',
    width: 120
  },
  {
    title: '微信号',
    key: 'wxNumber',
    align: 'center',
    width: 120
  },
  {
    title: '邮箱',
    key: 'mailbox',
    align: 'center',
    width: 160
  },
  {
    title: '联系人详情',
    key: 'detail',
    align: 'center',
    width: 160,
    ellipsis: {
      tooltip: true
    }
  },
  {
    title: '决策人',
    key: 'isDecision',
    align: 'center',
    width: 160
  },
  {
    title: '负责人',
    key: 'dutyBy',
    align: 'center',
    width: 120
  }
]);

const recordData = ref<interviewRecord.recordColumns[]>([]);

const recordColumns: Ref<DataTableColumns<interviewRecord.recordColumns>> = ref([
  {
    title: '客户名称',
    key: 'clientName',
    width: 200,
    align: 'center',
    ellipsis: {
      tooltip: true
    }
  },
  {
    title: '走访人',
    key: 'interviewName',
    width: 100,
    align: 'center'
  },
  {
    title: '走访时间',
    key: 'interviewTime',
    width: 180,
    align: 'center'
  },
  {
    title: '走访地址',
    key: 'interviewAddress',
    width: 180,
    align: 'center',
    ellipsis: {
      tooltip: true
    }
  },
  {
    title: '联系人',
    key: 'connectionName',
    width: 100,
    align: 'center'
  },
  {
    title: '走访描述',
    key: 'description',
    width: 120,
    align: 'center',
    ellipsis: {
      tooltip: true
    }
  },
  {
    title: '备注',
    key: 'remark',
    width: 120,
    align: 'center',
    ellipsis: {
      tooltip: true
    }
  }
]);

const emit = defineEmits(['closeArchives', 'save']);

function deepClone(obj) {
  if (!obj) return obj;
  return JSON.parse(JSON.stringify(obj));
}

function close() {
  emit('closeArchives');
}

function save(tip) {
  // if (isEdit.value) return;
  // isEdit.value = true;
  emit('save', { value: addForm.value, tip });
  isEdit.value = true;
}

function deleteInfo() {
  const d = dialog.warning({
    title: '提示',
    content: '确认要清空吗?',
    positiveText: '确认',
    negativeText: '取消',
    onPositiveClick: () => {
      d.loading = true;
      addForm.value = deepClone(initForm);
      save('清空成功');
    }
  });
}

function selectContent(id: number) {
  if (activeId.value === id) return;
  activeId.value = id;
}

function sectionText(section: string) {
  switch (section) {
    case '0':
      return { label: '市场部', color: '#FF3838', textColor: '#fff' };
    case '1':
      return { label: '采购部', color: '#FFA400', textColor: '#fff' };
    case '2':
      return { label: '生产部', color: '#1FD95C', textColor: '#fff' };
    case '3':
      return { label: '人事部', color: '#9FCF00', textColor: '#fff' };
    case '4':
      return { label: '销售部', color: '#00C7FC', textColor: '#fff' };
    case '5':
      return { label: '行政部', color: '#4E61FF', textColor: '#fff' };
    case '6':
      return { label: '其他', color: '#FAFAFD', textColor: '#000' };
    default:
      return { label: '其他', color: '#FAFAFD', textColor: '#000' };
  }
}
</script>

<style lang="scss" scoped>
.archives {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.3);
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  z-index: 99;

  .content {
    width: 1000px;
    height: 90vh;
    background-color: #fff;
    box-sizing: border-box;
    border-radius: 20px;
    overflow: auto;
  }

  .top {
    position: sticky;
    top: 0;
    background-color: #fff;
    z-index: 9;
    .title {
      display: flex;
      justify-content: space-between;
      padding: 16px 20px 12px;
      border-bottom: 1px solid #ddd;
      .text {
        font-size: 20px;
      }

      .close {
        width: 20px;
        cursor: pointer;
      }
    }

    .label {
      color: rgb(19, 132, 255);
      font-size: 15px;
      margin-bottom: 8px;
    }

    .form-widgets {
      padding: 0 13px;
      margin-top: 10px;
    }

    .sep-line {
      height: 8px;
      position: relative;

      .sep-bg-wrapper {
        bottom: 0;
        left: 0;
        padding-left: 8px;
        position: absolute;
        right: 0;
        border-color: rgb(19, 132, 255);
      }

      .sep-bg {
        border-bottom: 4px solid;
        height: 0;
        opacity: 0.2;
        width: 100%;
        border-color: rgb(19, 132, 255);
      }

      .sep-label {
        border-radius: 0 8px;
        bottom: 0;
        left: 0;
        max-width: 81%;
        min-width: 8%;
        overflow: hidden;
        position: absolute;
        text-overflow: ellipsis;
        top: 0;
        white-space: nowrap;
        color: rgb(19, 132, 255);
        background-color: rgb(19, 132, 255);
      }
    }

    .tool-box {
      padding: 10px 20px;
      border-bottom: 1px solid #ddd;
    }
  }

  .center {
    padding: 0 13px;
    margin-top: 30px;

    .btn-box {
      display: flex;
      border-bottom: 1px solid #ddd;
      .btn {
        padding: 10px 20px;
        background-color: #f5f6f8;
        margin-right: 5px;
        border-radius: 10px 10px 0 0;
        border-bottom: 0px;
        border: 1px solid #ddd;
        cursor: pointer;
      }

      .active {
        color: #00b899;
        background-color: #fff;
      }
    }
  }
}
</style>
