<template>
  <n-modal v-model:show="modalVisible" preset="card" :title="title" class="w-700px">
    <n-form ref="formRef" label-placement="left" :label-width="80" :model="formModel" :rules="rules">
      <n-grid :cols="24" :x-gap="18">
        <n-form-item-grid-item :span="12" label="用户昵称" path="nickName">
          <n-input v-model:value="formModel.nickName" />
        </n-form-item-grid-item>
        <n-form-item-grid-item :span="12" label="所属部门" path="deptId">
          <n-tree-select v-model:value="formModel.deptId" :options="treeData" key-field="id" label-field="label" />
        </n-form-item-grid-item>

        <n-form-item-grid-item :span="12" label="邮箱" path="email">
          <n-input v-model:value="formModel.email" />
        </n-form-item-grid-item>
        <n-form-item-grid-item :span="12" label="性别">
          <n-select v-model:value="formModel.sex" :options="sexOptions.options" style="width: 200px" />
        </n-form-item-grid-item>
        <n-form-item-grid-item :span="12" label="状态">
          <n-select v-model:value="formModel.status" :options="statusOptions.options" />
        </n-form-item-grid-item>
        <n-form-item-grid-item :span="12" label="岗位" path="postIds">
          <n-select v-model:value="formModel.postIds" multiple :options="postOptions.options" />
        </n-form-item-grid-item>
        <n-form-item-grid-item :span="12" label="工号" path="jobNumber">
          <n-input v-model:value="formModel.jobNumber" />
        </n-form-item-grid-item>
        <n-form-item-grid-item :span="12" label="角色" path="roleIds">
          <n-select v-model:value="formModel.roleIds" :options="roleOptions.options" multiple />
        </n-form-item-grid-item>
        <n-form-item-grid-item v-if="formModel.userId == undefined" :span="12" label="用户名称" path="userName">
          <n-input v-model:value="formModel.userName" />
        </n-form-item-grid-item>
        <n-form-item-grid-item v-if="formModel.userId == undefined" :span="12" label="用户密码" path="password">
          <n-input v-model:value="formModel.password" />
        </n-form-item-grid-item>

        <n-form-item-grid-item :span="12" label="手机号" path="phonenumber">
          <n-input v-model:value="formModel.phonenumber" />
        </n-form-item-grid-item>
        <n-form-item-grid-item :span="24" label="机台分配">
          <n-select
            v-model:value="formModel.deviceIds"
            :options="deviceOptions"
            filterable
            multiple
            @update:value="
              (value: any) => {
                formModel.deviceId = value.join(',');
              }
            "
          />
        </n-form-item-grid-item>
        <n-form-item-grid-item :span="12" label="备注">
          <n-input v-model:value="formModel.remark" type="textarea" placeholder="自动调整尺寸" />
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
import type { FormInst } from 'naive-ui';
import { getUser, updateUser, addUser } from '@/service';
import { createRequiredFormRule, formRules } from '@/utils';
import { getEquipmentList } from '~/src/service/api/device/equipmentLedger';

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
  getList;
}
// const sex = ref('');
// const status = ref('');
// const role = ref('');
// const post = ref('');
const deviceOptions: any = ref([]);
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
const sexOptions = ref({
  options: [
    {
      label: '男',
      value: '0'
    },
    {
      label: '女',
      value: '1'
    }
  ]
});
// const form=ref({});
const postOptions = ref({ options: [] });
const roleOptions = ref({ options: [] });

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
    add: '添加用户',
    edit: '编辑用户'
  };
  return titles[props.type];
});

const formRef = ref<HTMLElement & FormInst>();

type FormModel = {
  phonenumber: string;
  nickName: string;
  userName: string;
  password: string;
  deptId: string;
  email: string;
  sex: string;
  status: string;
  postIds: string;
  roleIds: string;
  remark: string;
  userId: number;
  jobNumber: string;
  deviceId: any;
  deviceIds: any;
};

const formModel = reactive(createDefaultFormModel());

const rules = {
  phonenumber: formRules.phone,
  email: formRules.email,
  nickName: createRequiredFormRule('请输入昵称'),
  jobNumber: createRequiredFormRule('请输入工号'),
  postIds: createRequiredFormRule('请选择岗位'),
  roleIds: createRequiredFormRule('请选择角色'),
  userName: createRequiredFormRule('请输入用户名'),
  password: createRequiredFormRule('请输入密码'),
  deptId: createRequiredFormRule('请选择所在部门')
};

function createDefaultFormModel() {
  return {
    phonenumber: '',
    nickName: '',
    deptId: '',
    email: '',
    sex: '',
    status: '',
    postIds: '',
    roleIds: '',
    remark: '',
    userName: '',
    password: '',
    jobNumber: '',
    deviceId: '',
    deviceIds: null,
    userId: undefined
  };
}

function handleUpdateFormModel(model: Partial<FormModel>) {
  // console.log(formModel,model)
  model.deviceIds = model.deviceId ? model.deviceId.map(n => parseInt(n, 10)) : null;
  model.deviceId = model.deviceIds ? model.deviceIds.join(',') : null;
  Object.assign(formModel, model);
}

function handleUpdateFormModelByModalType() {
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
function handleAdd() {
  // this.reset();

  getUser(formModel.userId).then(response => {
    const PostOptions = response.posts;
    PostOptions.forEach(item => {
      (postOptions.value.options as { label: string; value: number }[]).push({
        label: item.postName,
        value: item.postId
      });
    });
    //  console.log(PostOptions)
    const RoleOptions = response.roles;
    RoleOptions.forEach(item => {
      (roleOptions.value.options as { label: string; value: number }[]).push({
        label: item.roleName,
        value: item.roleId
      });
    });
    //  console.log(RoleOptions)
    // this.roleOptions = response.roles;
    // this.open = true;
    // this.title = "添加用户";
    // this.form.password = this.initPassword;
  });
  deviceOptions.value = [];
  getEquipmentList({ pageSize: 999 }).then(res => {
    res.rows.forEach((item: any) => {
      deviceOptions.value.push({
        value: item.id,
        label: item.equipmentName
      });
    });
  });
}

async function handleSubmit() {
  await formRef.value?.validate(vaild => {
    if (!vaild) {
      if (formModel.userId !== undefined) {
        // console.log(formModel.dept.deptId)
        updateUser(formModel).then(() => {
          props.getList();
          closeModal();
          window.$message?.success('修改成功!');
        });
      } else {
        addUser(formModel).then(() => {
          props.getList();
          closeModal();
          window.$message?.success('新增成功!');
        });
      }
    }
  });
}

watch(
  () => props.visible,
  newValue => {
    if (newValue) {
      handleUpdateFormModelByModalType();
    }
  }
);
function init() {
  handleAdd();
}
init();
</script>

<style scoped></style>
