<template>
  <n-modal v-model:show="modalVisible" :on-close="close" preset="card" :title="title" class="w-700px">
    <n-form ref="formRef" label-placement="left" :label-width="80" :model="formModel" :rules="rules">
      <n-grid :cols="24" :x-gap="18">
        <n-form-item-grid-item :span="12" label="角色名称" path="roleName">
          <n-input v-model:value="formModel.roleName" placeholder="输入角色名" />
        </n-form-item-grid-item>
        <n-form-item-grid-item :span="12" label="权限字符" path="roleKey">
          <n-input v-model:value="formModel.roleKey" placeholder="输入权限字符" />
        </n-form-item-grid-item>
        <n-form-item-grid-item :span="12" label="角色顺序">
          <n-input-number v-model:value="formModel.roleSort" />
        </n-form-item-grid-item>
        <n-form-item-grid-item :span="12" label="状态" path="status">
          <n-select v-model:value="formModel.status" :options="statusOptions.options" />
        </n-form-item-grid-item>
        <n-form-item-grid-item :span="12" label="菜单权限">
          <n-space>
            <n-switch :rail-style="expand">
              <template #checked>折叠</template>
              <template #unchecked>展开</template>
            </n-switch>
            <n-switch v-model:value="selectedSwitchshow" @update:value="handleSwitchChange">
              <template #checked>全选</template>
              <template #unchecked>不选</template>
            </n-switch>
            <n-tree
              block-line
              :cascade="true"
              checkable
              cancelable
              virtual-scroll
              style="height: 200px; width: 200px"
              key-field="id"
              :selectable="true"
              :data="treeData"
              :default-expand-all="isExpand"
              :checked-keys="CheckedKeys"
              @update:indeterminate-keys="getIndeterminateData"
              @update:checked-keys="updateCheckedKeys"
            />
          </n-space>
        </n-form-item-grid-item>

        <n-form-item-grid-item :span="12" label="备注">
          <n-input
            v-model:value="formModel.remark"
            type="textarea"
            placeholder="自动调整尺寸"
            :autosize="{ minRows: 3 }"
          />
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
import type { CSSProperties } from 'vue';
import { ref, computed, reactive, watch } from 'vue';
import type { FormInst, TreeOption } from 'naive-ui';
import { updateRole, addRole } from '@/service';
import { createRequiredFormRule, formRules } from '@/utils';

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
  treeData?: any;
  checkedKeys?: any;
  getList;
}
type treeType = {
  id: number;
  label: string;
  children?: treeType[];
};
// const sex = ref('');
// const status = ref('');
// const role = ref('');
// const post = ref('');

const statusOptions = ref({
  value: null,
  options: [
    {
      label: '启用',
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
let menuId: number[] = [];
const modalVisible = computed({
  get() {
    return props.visible;
  },
  set(visible) {
    emit('update:visible', visible);
  }
});

const title = computed(() => {
  const titles: Record<ModalType, string> = {
    add: '添加角色',
    edit: '编辑角色'
  };
  return titles[props.type];
});

const formRef = ref<HTMLElement & FormInst>();
const isExpand = ref(false);
const selectedSwitchshow = ref(false);

// const defaultCheckedKeys = ref();
const CheckedKeys = ref();
type FormModel = {
  roleName: string;
  roleKey: string;
  roleSort: number;
  status: string;
  remark: string;
  roleId: number;
};
const close = () => {
  CheckedKeys.value = [];
};
const formModel = reactive(createDefaultFormModel());

const rules = {
  phonenumber: formRules.phone,
  email: formRules.email,
  roleName: createRequiredFormRule('请输入昵称'),
  roleKey: createRequiredFormRule('请输入权限字符'),
  roleSort: createRequiredFormRule(''),
  status: createRequiredFormRule('请选择状态'),
  password: createRequiredFormRule('请输入密码'),
  deptId: createRequiredFormRule('请选择所在部门')
};
const expand = ({ checked }: { checked: boolean }) => {
  const style: CSSProperties = {};
  if (checked) {
    style.background = '#d03050';
    isExpand.value = checked;
  } else {
    style.background = '#2080f0';
    isExpand.value = checked;
  }
  return style;
};
function reset() {
  for (const key in formModel) {
    if (key) {
      formModel[key] = undefined;
    }
  }
}
function findId(obj: treeType) {
  if (obj) {
    menuId.push(obj.id);
    if (obj.children) {
      obj.children.forEach(item => findId(item));
    }
  }
}
function handleSwitchChange(value) {
  if (value) {
    props.treeData.forEach(item => findId(item));
    CheckedKeys.value = menuId;
  } else {
    CheckedKeys.value = [];
  }
}

const closeModal = () => {
  modalVisible.value = false;
  CheckedKeys.value = [];
};
function createDefaultFormModel() {
  return {
    roleName: '',
    roleKey: '',
    roleSort: 0,
    status: '',
    remark: '',
    roleId: undefined
  };
}
const treeRole = ref<number[]>([]);
function getIndeterminateData(keys: Array<string | number>, options: Array<TreeOption | null>) {
  treeRole.value = keys as number[];
  // CheckedKeys.value.push(...(keys as number[]));
  console.log('getIndeterminateData', keys, options);
}
function updateCheckedKeys(
  keys: Array<string | number>,
  options: Array<TreeOption | null>,
  meta: {
    node: TreeOption | null;
    action: 'check' | 'uncheck';
  }
) {
  CheckedKeys.value = keys as number[];
  console.log('updateCheckedKeys', keys, options, meta);
}

function handleUpdateFormModel(model: Partial<FormModel>) {
  // console.log(formModel,model)
  Object.assign(formModel, model);
  console.log(formModel);
}

function handleUpdateFormModelByModalType() {
  const handlers: Record<ModalType, () => void> = {
    add: () => {
      reset();
      const defaultFormModel = createDefaultFormModel();
      handleUpdateFormModel(defaultFormModel);
      menuId = [];
      if (props.treeData) {
        props.treeData.forEach(item => {
          findId(item);
        });
        console.log(menuId);
      }
    },
    edit: () => {
      if (props.editData) {
        reset();
        handleUpdateFormModel(props.editData);
      }
      if (props.checkedKeys) {
        CheckedKeys.value = props.checkedKeys;
        // defaultCheckedKeys.value = props.checkedKeys;
      }
    }
  };

  handlers[props.type]();
}

async function handleSubmit() {
  await formRef.value?.validate(vaild => {
    const checkdata: number[] = [];
    checkdata.push(...CheckedKeys.value);
    if (CheckedKeys.value.length > 0) {
      checkdata.push(...treeRole.value);
    }
    console.log(checkdata, '12');
    // const test: number[] = [];
    // props.treeData.value.forEach((item: any) => {
    //   if (checkdata.includes(item.id)) {
    //     test.push(item.id);
    //   } else if (item.children.length > 0) {
    //     item.children.forEach((item1: any) => {
    //       if (checkdata.includes(item1.id)) {
    //         test.push(item1.id);
    //       }
    //     });
    //   }
    // });
    console.log(CheckedKeys.value, '12');
    if (!vaild) {
      if (formModel.roleId !== undefined) {
        Reflect.set(formModel, 'menuIds', [...CheckedKeys.value]);
        updateRole(formModel).then(() => {
          props.getList();
          window.$message?.success('修改成功!');
        });
      } else {
        Reflect.set(formModel, 'menuIds', [...CheckedKeys.value]);
        addRole(formModel).then(() => {
          props.getList();
          window.$message?.success('新增成功!');
        });
      }
    }
  });

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
// function init() {}
// init();
</script>

<style scoped></style>
