<template>
  <n-modal v-model:show="modalVisible" preset="card" :title="title" class="w-700px">
    <n-form ref="formRef" label-placement="left" :label-width="80" :model="formModel" :rules="rules">
      <n-grid :cols="24" :x-gap="18">
        <n-form-item-grid-item :span="24" label="物料名称" path="itemName">
          <n-input v-model:value="formModel.itemName" />
        </n-form-item-grid-item>
        <n-form-item-grid-item :span="24" label="物料编号" path="itemCode">
          <n-input v-model:value="formModel.itemCode" />
        </n-form-item-grid-item>
        <n-form-item-grid-item :span="24" label="物料分类" path="itemTypeId">
          <n-select v-model:value="formModel.itemTypeId" :options="itemTypeOptions.options" />
        </n-form-item-grid-item>
        <n-form-item-grid-item v-if="formModel.itemId == undefined" :span="24" label="物料单位" path="unitOfMeasure">
          <n-input v-model:value="formModel.unitOfMeasure" />
        </n-form-item-grid-item>
        <n-form-item-grid-item :span="24" label="规格型号" path="specification">
          <n-input v-model:value="formModel.specification" />
        </n-form-item-grid-item>
        <n-form-item-grid-item :span="24" label="物料/产品" path="itemOrProduct">
          <n-radio-group v-model:value="formModel.itemOrProduct">
            <n-radio v-for="item in itemOrProductOptions" :key="item.value" :value="item.value">
              {{ item.label }}
            </n-radio>
          </n-radio-group>
        </n-form-item-grid-item>
        <n-form-item-grid-item :span="24" label="是否启用" path="enableFlag">
          <n-radio-group v-model:value="formModel.enableFlag">
            <n-radio v-for="item in enableFlagOptions" :key="item.value" :value="item.value">
              {{ item.label }}
            </n-radio>
          </n-radio-group>
        </n-form-item-grid-item>
      </n-grid>
      <n-space class="w-full pt-16px" :size="24" justify="end">
        <n-button class="w-72px" @click="closeModal">取消</n-button>
        <n-button class="w-72px" type="primary" @click="handleSubmit">确定</n-button>
      </n-space>
    </n-form>
  </n-modal>
</template>
<script setup lang="ts">
import { ref, computed, reactive, watch } from 'vue';
import type { FormInst, FormItemRule } from 'naive-ui';
import { enableFlagOptions } from '@/constants';
import { fetchItemTypeInfo, updateItem, addItem, getItemTypeList } from '@/service';
import { useAuthStore } from '@/store';
import { createRequiredFormRule, createRequiredFormRuleNull } from '@/utils';

const itemOrProductOptions = [
  { label: '物料', value: 'item' },
  { label: '产品', value: 'product' }
];
const auth = useAuthStore();
const parentItemTypeName = ref('');
const showParentType = ref(true);
const itemTypeOptions = ref({ options: [] });

export interface Props {
  /** 弹窗可见性 */
  visible: boolean;
  /**
   * 弹窗类型
   * add: 新增
   * edit: 编辑
   */
  type?: 'add' | 'edit';
  parentTypeId: number;
  parentFormValue: object;
  /** 编辑的表格行数据 */
  editData?: ApiItemManagement.Item | null;
}

export type ModalType = NonNullable<Props['type']>;

defineOptions({ name: 'ActionModal' });

const props = withDefaults(defineProps<Props>(), {
  type: 'add',
  parentTypeId: 0,
  editData: null
});

interface Emits {
  (e: 'update:visible', visible: boolean): void;
  (e: 'refreshTableData'): void;
}

const emit = defineEmits<Emits>();

const modalVisible = computed({
  get() {
    return props.visible;
  },
  set(visible) {
    emit('update:visible', visible);
  }
});
const closeModal = () => {
  modalVisible.value = false;
};

const title = computed(() => {
  const titles: Record<ModalType, string> = {
    add: '添加物料',
    edit: '编辑物料'
  };
  if (props.parentTypeId === 0) {
    return '添加主物料';
  }
  return titles[props.type];
});

const formRef = ref<HTMLElement & FormInst>();

type FormModelOrigin = Pick<
  ApiItemManagement.Item,
  | 'itemId'
  | 'itemName'
  | 'itemCode'
  | 'itemOrProduct'
  | 'specification'
  | 'itemTypeId'
  | 'itemTypeName'
  | 'itemTypeCode'
  // | 'minStock'
  // | 'maxStock'
  | 'enableFlag'
  | 'unitOfMeasure'
  | 'safeStockFlag'
>;
type FormModel = Partial<FormModelOrigin>;

const formModel = reactive<FormModel>(createDefaultFormModel());

const rules: Record<keyof FormModel, FormItemRule | FormItemRule[]> = {
  itemId: createRequiredFormRuleNull(''),
  itemName: createRequiredFormRule('请输入物料名'),
  itemCode: createRequiredFormRule('请输入物料编号'),
  itemTypeId: createRequiredFormRule('请输入物料分类Id'),
  itemTypeName: createRequiredFormRuleNull(''),
  itemTypeCode: createRequiredFormRuleNull(''),
  specification: createRequiredFormRule('请输入规格型号'),
  unitOfMeasure: createRequiredFormRule('请输入物料单位'),
  enableFlag: createRequiredFormRule('请选择是否启用'),
  itemOrProduct: createRequiredFormRule('请选择是物料还是产品'),
  safeStockFlag: createRequiredFormRuleNull('')
};

/**
 *  初始化新增窗口
 */
function createDefaultFormModel(): FormModel {
  return {
    itemName: '',
    itemCode: '',
    itemOrProduct: 'item',
    itemTypeId: undefined,
    itemTypeName: '',
    itemTypeCode: '',
    specification: '',
    unitOfMeasure: '',
    enableFlag: 'Y',
    safeStockFlag: 'N',
    itemId: undefined
  };
}

function handleUpdateFormModel(model: Partial<FormModel>) {
  Object.assign(formModel, model);
}

async function handleUpdateFormModelByModalType() {
  // 新建主物料，不显示父类input
  if (props.parentTypeId === 0) {
    showParentType.value = false;
  } else {
    showParentType.value = true;
  }

  const data = await fetchItemTypeInfo(props.parentTypeId);
  if (data.data) {
    parentItemTypeName.value = data.data.itemTypeName;
  } else {
    parentItemTypeName.value = '顶层基类';
  }
  const handlers: Record<ModalType, () => void> = {
    add: () => {
      const defaultFormModel = createDefaultFormModel();
      handleUpdateFormModel(defaultFormModel);
    },
    edit: () => {
      if (props.editData) {
        handleUpdateFormModel(props.editData);
      }
    }
  };

  handlers[props.type]();
}

async function handleSubmit() {
  await formRef.value?.validate();
  if (props.type === 'edit') {
    const updateBy = { updateBy: auth.userInfo.userName };
    Object.assign(formModel, updateBy);
    // formModel.updateBy = auth.userInfo.userName;
    const rs = await updateItem(formModel);
    if (rs.code === 200 && rs.msg === '操作成功') {
      window.$message?.success('编辑成功');
      emit('refreshTableData');
    } else {
      window.$message?.error(rs.msg);
    }
  }
  if (props.type === 'add') {
    interface NewItemInfo {
      [key: string]: any;
    }
    const newItemInfo: NewItemInfo = {};

    // newItemInfo.parentTypeId = props.parentTypeId;
    // if (props.parentTypeId === 0) {
    //   newItemInfo.ancestors = '0';
    // } else {
    //   newItemInfo.ancestors = `${formModel.ancestors},${formModel.itemTypeId}`;
    // }
    // // const createBy = { createBy: auth.userInfo.userName };
    // // Object.assign(newItemInfo, createBy);
    newItemInfo.createBy = auth.userInfo.userName;
    newItemInfo.itemTypeId = formModel.itemTypeId;
    newItemInfo.itemName = formModel.itemName;
    newItemInfo.itemCode = formModel.itemCode;
    newItemInfo.specification = formModel.specification;
    newItemInfo.itemOrProduct = formModel.itemOrProduct;
    newItemInfo.itemTypeName = formModel.itemTypeName;
    newItemInfo.unitOfMeasure = formModel.unitOfMeasure;
    newItemInfo.enableFlag = formModel.enableFlag;
    newItemInfo.safeStockFlag = formModel.safeStockFlag;
    const rs = await addItem(newItemInfo);
    if (rs.code === 200 && rs.data > 0) {
      window.$message?.success('添加成功');
      emit('refreshTableData');
    } else {
      window.$message?.error(rs.msg);
    }
  }

  closeModal();
}

watch(
  () => props.visible,
  newValue => {
    if (newValue) {
      handleUpdateFormModelByModalType();
    }
  }
);

function handleAdd() {
  // this.reset();
  // console.log(formModel)
  getItemTypeList().then(response => {
    // console.log(response);
    const ItemTypeOptions = response.data;
    ItemTypeOptions.forEach(item => {
      (itemTypeOptions.value.options as { label: string; value: string }[]).push({
        label: item.itemTypeName,
        value: item.itemTypeId
      });
    });
  });
}

function init() {
  handleAdd();
}
init();
</script>

<style scoped></style>
