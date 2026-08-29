<template>
  <div>
    <my-card title="个人详情">
      <n-form label-placement="left" style="min-height: calc(100vh - 200px)" class="m-10px">
        <n-grid :cols="3" :x-gap="18">
          <n-form-item-grid-item label="账号：" :span="1">
            <n-input :value="userinfo.userName" disabled></n-input>
          </n-form-item-grid-item>
          <n-form-item-grid-item label="姓名：" :span="1">
            <n-input :value="userinfo.nickName" disabled></n-input>
          </n-form-item-grid-item>
          <n-form-item-grid-item>
            <n-button type="primary" :span="1" @click="showChangePwd = true">修改密码</n-button>
          </n-form-item-grid-item>
          <n-form-item-grid-item :span="1" label="部门：">
            <div class="flex-row-reverse">
              <div v-for="item in userinfo.depts" :key="item" style="line-height: 32px">{{ item }}</div>
            </div>
          </n-form-item-grid-item>
          <n-form-item-grid-item :span="1" label="角色：">
            <div v-if="userinfo.rolesName?.length > 0" class="flex-row-reverse">
              <div v-for="item in userinfo.rolesName" :key="item" style="line-height: 32px">{{ item }}</div>
            </div>
            <div v-else class="flex-row-reverse">
              <div style="line-height: 32px">暂无角色</div>
            </div>
          </n-form-item-grid-item>
          <n-form-item-grid-item :span="3" label="权限：">
            <n-tree block-line selectable :data="data" :default-expand-all="false" />
          </n-form-item-grid-item>
        </n-grid>
      </n-form>
    </my-card>
    <my-dialog height="100px" title="修改密码" :show="showChangePwd" @cancel="cancel" @submit="pwdSubmit">
      <template #content>
        <div>
          <n-form
            ref="pwdRef"
            :rules="rules"
            :model="changePwd"
            label-placement="left"
            label-width="100px"
            label-align="left"
          >
            <n-form-item label="新密码：" path="newpwd">
              <n-input v-model:value="changePwd.newpwd" type="password"></n-input>
            </n-form-item>
            <n-form-item label="确认密码：" path="confirmpwd">
              <n-input v-model:value="changePwd.confirmpwd" type="password"></n-input>
            </n-form-item>
          </n-form>
        </div>
      </template>
    </my-dialog>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue';
import { useRoute } from 'vue-router';
import type { FormInst, FormRules, FormItemRule } from 'naive-ui';
import { useMessage } from 'naive-ui';
import { resetUserPwd, rolePermission, getUserInfo } from '@/service';
import { formRules } from '@/utils';
const message = useMessage();
const route = useRoute();
type User = {
  userName: string;
  nickName: string;
  roles: { [key: string]: string };
  depts: string;
  rolesName: string[];
};
const userinfo = ref<User>({
  userName: '',
  nickName: '',
  roles: {},
  depts: '',
  rolesName: ['']
});

const data = ref<any[]>([]);
const clear = (formValue: { [key: string]: any }) => {
  Object.keys(formValue).forEach(key => {
    formValue[key] = '';
  });
};

// 修改密码
const changePwd = ref({
  newpwd: '',
  confirmpwd: ''
});

const rules: FormRules = {
  newpwd: [
    {
      required: true,
      message: '请输入新密码',
      trigger: ['input', 'blur']
    }
  ],
  confirmpwd: [
    { required: true, message: '请输入新密码', trigger: ['input', 'blur'] },
    {
      validator: validatePasswordStartWith,
      message: '两次密码输入不一致',
      trigger: ['input']
    },
    ...formRules.password
  ]
};

function validatePasswordStartWith(rule: FormItemRule, value: string): boolean {
  // eslint-disable-next-line no-console
  console.log(rule);
  return Boolean(value === changePwd.value.newpwd);
}
const pwdRef = ref<FormInst | null>(null);

const showChangePwd = ref<boolean>(false);
const cancel = () => {
  showChangePwd.value = false;
  // 清除对象中的值
  clear(changePwd.value);
};
const pwdSubmit = () => {
  pwdRef.value?.validate(errors => {
    if (!errors) {
      resetUserPwd(route.query.id, changePwd.value.newpwd).then(res => {
        if (res.code === 200) {
          message.success('修改成功');
        }
        // 关闭弹窗
        cancel();
      });
    } else {
      message.error('校验失败');
    }
  });
};
// 一个函数将数组对象中的id转换成key有children继续下一层循环结束跳出
function transformTree(arr: any[]) {
  return arr.map(item => {
    item.key = item.id;
    if (item.children && item.children.length) {
      item.children = transformTree(item.children);
    }
    return item;
  });
}

onMounted(async () => {
  let keylist = '';
  await getUserInfo().then(res => {
    userinfo.value = res.userInfo;
    Object.keys(userinfo.value.roles).forEach(key => {
      keylist += `${key},`;
      if (userinfo.value.rolesName) {
        userinfo.value.rolesName.push(userinfo.value.roles[key]);
      } else {
        userinfo.value.rolesName = [];
        userinfo.value.rolesName.push(userinfo.value.roles[key]);
      }
    });
    if (keylist.length > 0) {
      keylist = keylist.substring(0, keylist.length - 1);
      rolePermission(keylist).then(Response => {
        if (Response.code === 200) {
          data.value = transformTree(Response.menus);
          // eslint-disable-next-line no-console
          console.log(data.value, '432');
        }
      });
    }
  });
});
</script>

<style scoped>
:deep(.n-input) {
  width: 200px;
}
</style>
