<template>
  <n-modal v-model:show="modalVisible" preset="card" :title="title" class="w-700px">
    <n-form ref="formRef" label-placement="left" :label-width="80" :model="formModel" :rules="rules">
      <n-grid :cols="24" :x-gap="18">
        <n-form-item-grid-item :span="12" label="上级菜单" path="parentId">
          <n-tree-select
            v-model:value="formModel.parentId"
            :options="treeData"
            key-field="menuId"
            label-field="menuName"
          />
        </n-form-item-grid-item>
        <n-form-item-grid-item :span="12" label="菜单类型" path="menuType">
          <n-select v-model:value="formModel.menuType" :options="menuTypeOptions.options" />
        </n-form-item-grid-item>
        <n-form-item-grid-item v-if="formModel.menuType != 'F'" :span="12" label="菜单图标" path="icon">
          <icon-select v-model:value="formModel.icon" :icons="icons" />
        </n-form-item-grid-item>
        <n-form-item-grid-item :span="12" label="菜单名称">
          <n-input v-model:value="formModel.menuName" placeholder="输入菜单名称" />
        </n-form-item-grid-item>
        <n-form-item-grid-item :span="12" label="显示排序">
          <n-input-number v-model:value="formModel.orderNum" />
        </n-form-item-grid-item>
        <n-form-item-grid-item v-if="formModel.menuType != 'F'" :span="12" label="路由地址" path="path">
          <n-input v-model:value="formModel.path" placeholder="输入路由地址" />
        </n-form-item-grid-item>
        <n-form-item-grid-item v-if="formModel.menuType == 'C'" :span="12" label="组件路径" path="component">
          <n-input v-model:value="formModel.component" placeholder="输入组件路径" />
        </n-form-item-grid-item>
        <n-form-item-grid-item v-if="formModel.menuType != 'M'" :span="12" label=" 权限字符" path="perms">
          <n-input v-model:value="formModel.perms" placeholder="输入权限字符" />
        </n-form-item-grid-item>
        <n-form-item-grid-item v-if="formModel.menuType != 'F'" :span="12" label="是否显示">
          <n-switch v-model:value="expandedSwitchshowDailog" @update:value="handleSwitchChangeDailog">
            <template #checked>显示</template>
            <template #unchecked>隐藏</template>
          </n-switch>
        </n-form-item-grid-item>
        <n-form-item-grid-item v-if="formModel.menuType != 'F'" :span="12" label="菜单状态">
          <n-select v-model:value="formModel.status" :options="statusOptions.options" />
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
import { useMessage } from 'naive-ui';
import type { FormInst } from 'naive-ui';
import { updateMenu, addMenu } from '@/service';
import { createRequiredFormRule, formRules } from '@/utils';
import { icons } from './icons';

export interface Props {
  /** 弹窗可见性 */
  visible: boolean;
  /**
   * 弹窗类型
   * add: 新增
   * edit: 编辑
   */
  type?: 'add' | 'edit';
  /** 编辑的表格行数据 */
  editData?: any;
  // eslint-disable-next-line vue/require-default-prop
  treeData?: any;
  // eslint-disable-next-line vue/require-default-prop
  menuId?: any;
  getList;
}
const expandedSwitchshowDailog = ref(true);
const menuTypeOptions = ref({
  value: null,
  options: [
    {
      label: '目录',
      value: 'M'
    },
    {
      label: '菜单',
      value: 'C'
    },
    {
      label: '按钮',
      value: 'F'
    }
  ]
});

const statusOptions = ref({
  value: null,
  options: [
    {
      label: '正常',
      value: '0'
    },
    {
      label: '禁用',
      value: '1'
    }
  ]
});

export type ModalType = NonNullable<Props['type']>;

defineOptions({ name: 'TableActionModal' });

const props = withDefaults(defineProps<Props>(), {
  type: 'add',
  editData: null
});

interface Emits {
  (e: 'update:visible', visible: boolean): void;
}

const emit = defineEmits<Emits>();
const message = useMessage();
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
    add: '添加菜单',
    edit: '编辑菜单'
  };
  return titles[props.type];
});

const formRef = ref<HTMLElement & FormInst>();

type FormModel = {
  roleName: string;
  roleKey: string;
  roleSort: number;
  status: string;
  remark: string;
  roleId: number;
};

const formModel = reactive(createDefaultFormModel());

const rules = {
  phone: formRules.phone,
  email: formRules.email,
  parentId: createRequiredFormRule('请选择上级部门'),
  deptName: createRequiredFormRule('请输入部门名称'),
  orderNum: createRequiredFormRule('请定义排序')
};

function createDefaultFormModel() {
  return {
    parentId: '',
    menuType: '',
    orderNum: 0,
    status: '',
    icon: '',
    menuName: '',
    path: '',
    component: '',
    perms: '',
    visible: '',
    menuId: undefined
  };
}
// 是否显示菜单
function handleSwitchChangeDailog(isShow) {
  if (isShow) {
    formModel.visible = '0';
  } else {
    formModel.visible = '1';
  }
}
function handleUpdateFormModel(model: Partial<FormModel>) {
  // console.log(formModel,model)
  Object.assign(formModel, model);
}

function handleUpdateFormModelByModalType() {
  const handlers: Record<ModalType, () => void> = {
    add: () => {
      reset();

      const defaultFormModel = createDefaultFormModel();
      handleUpdateFormModel(defaultFormModel);
      formModel.parentId = props.menuId;
    },
    edit: () => {
      reset();
      if (props.editData) {
        handleUpdateFormModel(props.editData);
        changeShow(props.editData);
      }
    }
  };

  handlers[props.type]();
}

async function handleSubmit() {
  await formRef.value?.validate(vaild => {
    if (!vaild) {
      if (formModel.menuId !== undefined) {
        updateMenu(formModel)
          .then(() => {
            message.success('修改成功');
            props.getList();
            closeModal();
          })
          .catch(() => {
            message.success('修改失败');
          });
      } else {
        addMenu(formModel)
          .then(() => {
            message.success('新增成功');
            props.getList();
            // closeModal();
          })
          .catch(() => {
            message.success('修改失败');
          });
      }
    }
  });

  closeModal();
}
function reset() {
  for (const key in formModel) {
    if (key) {
      formModel[key] = undefined;
    }
  }
}
function changeShow(editData) {
  if (editData.visible === '1') {
    expandedSwitchshowDailog.value = false;
  } else {
    expandedSwitchshowDailog.value = true;
  }
}
watch(
  () => props.visible,
  newValue => {
    if (newValue) {
      handleUpdateFormModelByModalType();
    }
  }
);
// function init() {}
// init();
</script>

<style scoped></style>
