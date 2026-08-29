<template>
  <n-modal v-model:show="modalVisible" preset="card" :title="title" class="w-700px">
    <n-form ref="formRef" label-placement="left" :label-width="80" :model="formModel" :rules="rules">
      <n-grid :cols="24" :x-gap="18">
        <n-form-item-grid-item :span="12" label="岗位编码" path="postCode">
          <n-input v-model:value="formModel.postCode" placeholder="输入权限字符" />
        </n-form-item-grid-item>
        <n-form-item-grid-item :span="12" label="岗位名称" path="postName">
          <n-input v-model:value="formModel.postName" placeholder="输入权限字符" />
        </n-form-item-grid-item>
        <n-form-item-grid-item :span="12" label="岗位顺序" path="postSort">
          <n-input-number v-model:value="formModel.postSort" />
        </n-form-item-grid-item>
        <n-form-item-grid-item :span="12" label="岗位状态">
          <n-select v-model:value="formModel.status" :options="statusOptions.options" />
        </n-form-item-grid-item>
        <n-form-item-grid-item :span="12" label="备注">
          <n-input
            v-model:value="formModel.remark"
            type="textarea"
            placeholder="自动调整尺寸"
            :autosize="{ minRows: 5 }"
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
import { ref, computed, reactive, watch } from 'vue';
import { useMessage } from 'naive-ui';
import type { FormInst } from 'naive-ui';
import { updatePost, addPost } from '@/service';
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
  deptId?: any;
  getList;
}

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
    add: '添加岗位',
    edit: '编辑岗位'
  };
  return titles[props.type];
});

const formRef = ref<HTMLElement & FormInst>();

type FormModel = {
  postCode: string;
  postName: string;
  postSort: number;
  status: string;
  remark: string;
  postId: number;
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
    postCode: '',
    postName: '',
    postSort: 0,
    status: '',
    remark: '',
    postId: undefined
  };
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
    },
    edit: () => {
      reset();
      if (props.editData) {
        handleUpdateFormModel(props.editData);
      }
    }
  };

  handlers[props.type]();
}

async function handleSubmit() {
  await formRef.value?.validate(vaild => {
    if (!vaild) {
      if (formModel.postId !== undefined) {
        updatePost(formModel)
          .then(() => {
            message.success('修改成功');
            closeModal();
            props.getList();
          })
          .catch(() => {
            message.success('修改失败');
          });
      } else {
        addPost(formModel)
          .then(() => {
            message.success('新增成功');
            props.getList();
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
