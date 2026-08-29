<template>
  <n-modal v-model:show="modalVisible" preset="card" :title="title" class="w-700px">
    <n-form
      ref="formRef"
      label-placement="left"
      label-width="100px"
      label-align="left"
      :model="formModel"
      :rules="rules"
    >
      <n-grid :cols="24" :x-gap="18">
        <n-form-item-grid-item v-if="showParentType" :span="10" label="父分类" path="parentItemTypeName">
          <n-input v-model:value="parentItemTypeName" disabled />
        </n-form-item-grid-item>
        <n-form-item-grid-item :span="10" label="分类名称" path="itemTypeName">
          <n-input v-model:value="formModel.itemTypeName" />
        </n-form-item-grid-item>
        <n-form-item-grid-item :span="10" label="分类编号" path="itemTypeCode">
          <n-input v-model:value="formModel.itemTypeCode" />
        </n-form-item-grid-item>
        <n-form-item-grid-item :span="10" label="显示排序" path="orderNum">
          <n-input-number v-model:value="formModel.orderNum" clearable />
        </n-form-item-grid-item>
        <n-form-item-grid-item :span="10" label="是否启用" path="ifEnable">
          <!-- <n-switch v-model:value="formModel.ifEnable" :checked-value="1" :unchecked-value="0">
            <template #unchecked>
              <div>禁用</div>
            </template>
            <template #checked>
              <div>启用</div>
            </template>
          </n-switch> -->
          <n-radio-group v-model:value="formModel.ifEnable">
            <n-radio v-for="item in enableFlagOptions" :key="getInt(item.value)" :value="getInt(item.value)">
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
import { fetchItemTypeInfo, updateItemType, addItemType } from '@/service';
import { useAuthStore } from '@/store';
import { createRequiredFormRule } from '@/utils';

function getInt(str: string | number) {
  return parseInt(String(str), 10);
}
const auth = useAuthStore();
const parentItemTypeName = ref('');
const showParentType = ref(true);

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
  editData?: ApiItemTypeManagement.ItemType | null;
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
    add: '添加分类',
    edit: '编辑分类'
  };
  if (props.parentTypeId === 0 && props.type === 'add') {
    return '添加主分类';
  }
  return titles[props.type];
});

const formRef = ref<HTMLElement & FormInst>();

type FormModelOrigin = Pick<
  ApiItemTypeManagement.ItemType,
  'id' | 'itemTypeName' | 'itemTypeCode' | 'orderNum' | 'ifEnable' | 'ancestors'
>;
type FormModel = Partial<FormModelOrigin>;

const formModel = reactive<FormModel>(createDefaultFormModel());

const rules: Record<keyof FormModel, FormItemRule | FormItemRule[]> = {
  itemTypeName: createRequiredFormRule('请输入分类名'),
  itemTypeCode: createRequiredFormRule('请输入分类编号'),
  ifEnable: createRequiredFormRule('请选择是否启用'),
  orderNum: createRequiredFormRule('请输入排序号'),
  id: createRequiredFormRule('缺少id'),
  ancestors: createRequiredFormRule('缺少ancestors')
};

function createDefaultFormModel(): FormModel {
  return {
    itemTypeName: '',
    itemTypeCode: '',
    orderNum: 1,
    ifEnable: 1
  };
}

function handleUpdateFormModel(model: Partial<FormModel>) {
  Object.assign(formModel, model);
}

async function handleUpdateFormModelByModalType() {
  // 新建主分类，不显示父类input
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
    const rs = await updateItemType(formModel);
    if (rs.code === 200) {
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

    newItemInfo.parentTypeId = props.parentTypeId;
    if (props.parentTypeId === 0) {
      newItemInfo.ancestors = '0';
    } else {
      newItemInfo.ancestors = `${formModel.ancestors},${formModel.id}`;
    }
    // const createBy = { createBy: auth.userInfo.userName };
    // Object.assign(newItemInfo, createBy);
    newItemInfo.createBy = auth.userInfo.userName;
    newItemInfo.itemTypeName = formModel.itemTypeName;
    newItemInfo.itemTypeCode = formModel.itemTypeCode;
    newItemInfo.orderNum = formModel.orderNum;
    newItemInfo.ifEnable = formModel.ifEnable;
    const rs = await addItemType(newItemInfo);
    if (rs.code === 200) {
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
</script>

<style scoped></style>
