<template>
  <n-form ref="formRef" class="pt-74px" :model="model" size="large" :show-label="false">
    <n-form-item path="userName" style="position: relative">
      <img
        src="@/assets/img/user.png"
        style="
          height: 20px;
          position: absolute;
          z-index: 999;
          left: 10px;
          padding-right: 10px;
          border-right: 2px solid #d9d9d9;
        "
        alt=""
      />
      <n-input
        v-model:value="model.username"
        class="input"
        style="width: 100%; padding-left: 30px; line-height: 50px"
        :placeholder="$t('page.login.common.userNamePlaceholder')"
      />
    </n-form-item>
    <n-form-item path="password" style="position: relative">
      <img
        src="@/assets/img/pwd.png"
        style="
          height: 20px;
          position: absolute;
          z-index: 999;
          left: 10px;
          padding-right: 10px;
          border-right: 2px solid #d9d9d9;
        "
        alt=""
      />
      <n-input
        v-model:value="model.password"
        class="input"
        style="width: 100%; padding-left: 30px"
        type="password"
        show-password-on="click"
      />
    </n-form-item>
    <n-form-item path="imgCode">
      <n-input
        v-model:value="model.code"
        :placeholder="$t('page.login.codeLogin.imageCodePlaceholder')"
        @keyup.enter="handleSubmit"
      />
      <div class="pl-8px">
        <!-- <image-verify v-model:code="imgCode" /> -->
        <img :src="codeUrl" class="login-code-img" style="height: 50px" @click="getCode" />
      </div>
    </n-form-item>
    <n-space :vertical="true" :size="24">
      <div class="flex-y-center justify-between">
        <n-checkbox v-model:checked="rememberMe">
          <span style="color: #d9d9d9">{{ $t('page.login.pwdLogin.rememberMe') }}</span>
        </n-checkbox>
        <n-button
          :text="true"
          style="color: #d9d9d9; display: flex; justify-content: center; align-items: center"
          @click="toLoginModule('company-login')"
        >
          <!-- <span>{{ $t('page.login.pwdLogin.forgetPassword') }}</span> -->
          <span>扫码登录</span>
          <img src="@/assets/img/question.png" style="height: 18px" />
        </n-button>
      </div>
      <n-button
        type="primary"
        style="background-color: #3863ba"
        size="large"
        :block="true"
        :loading="auth.loginLoading"
        @click="handleSubmit"
      >
        {{ $t('page.login.common.confirm') }}
      </n-button>
      <div class="flex-y-center justify-between">
        <!-- <n-button class="flex-1" :block="true" @click="toLoginModule('code-login')">
          {{ loginModuleLabels['code-login'] }}
        </n-button> -->
        <!-- <div class="w-12px"></div>
        <n-button class="flex-1" :block="true" @click="toLoginModule('register')">
          {{ loginModuleLabels.register }}
        </n-button> -->
      </div>
    </n-space>
    <!-- <other-account @login="handleLoginOtherAccount" /> -->
  </n-form>
</template>

<script setup lang="ts">
import { reactive, ref, onMounted } from 'vue';
import type { FormInst } from 'naive-ui';
// import { loginModuleLabels } from '@/constants';
import { getImageCode } from '@/service';
import { useAuthStore } from '@/store';
import { useRouterPush } from '@/composables';
// import { formRules } from '@/utils';
import { $t } from '~/src/locales';

// import { OtherAccount } from './components';

const auth = useAuthStore();
const { login } = useAuthStore();
const { toLoginModule } = useRouterPush();
const codeUrl = ref('');
const formRef = ref<HTMLElement & FormInst>();
const rememberMe = ref<boolean>(false);
const model = reactive({
  username: '',
  password: '',
  rememberMe: rememberMe.value,
  code: '',
  uuid: ''
  // rememberMe: rememberMe.value
});

const message = localStorage.getItem('message');
console.log('message ==>', message);

if (message) {
  const data = JSON.parse(message);
  model.username = data.username;
  model.password = data.password;
  rememberMe.value = Boolean(data.rememberMe);
}

// const rules: FormRules = {
//   password: formRules.password
// };
onMounted(() => {
  getCode();
});

// 获取验证码
async function getCode() {
  const result: any = await getImageCode();
  console.log(result.img, '图片路径');

  codeUrl.value = `data:image/gif;base64,${result.img}`;
  model.uuid = result.uuid;
}
// 登录
async function handleSubmit() {
  console.log(111111111111);

  // await formRef.value?.validate();
  const { username, password, code, uuid } = model;

  login(username, password, code, uuid, getCode).then(() => {
    if (rememberMe.value) {
      localStorage.setItem('message', JSON.stringify({ username, password, rememberMe: rememberMe.value }));
    } else {
      localStorage.removeItem('message');
    }
  });
}
// getCookie() {
//   const username = Cookies.get("username");
//   const password = Cookies.get("password");
//   const rememberMe = Cookies.get('rememberMe')
//   this.loginForm = {
//     username: username === undefined ? this.loginForm.username : username,
//     password: password === undefined ? this.loginForm.password : decrypt(password),
//     rememberMe: rememberMe === undefined ? false : Boolean(rememberMe)
//   };
// },
// handleLogin() {
//   this.$refs.loginForm.validate(valid => {
//     if (valid) {
//       this.loading = true;
//       if (this.loginForm.rememberMe) {
//         Cookies.set("username", this.loginForm.username, { expires: 30 });
//         Cookies.set("password", encrypt(this.loginForm.password), { expires: 30 });
//         Cookies.set('rememberMe', this.loginForm.rememberMe, { expires: 30 });
//       } else {
//         Cookies.remove("username");
//         Cookies.remove("password");
//         Cookies.remove('rememberMe');
//       }
//       this.$store.dispatch("Login", this.loginForm).then(() => {
//         this.$router.push({ path: this.redirect || "/" }).catch(()=>{});
//       }).catch(() => {
//         this.loading = false;
//         if (this.captchaEnabled) {
//           this.getCode();
//         }
//       });
//     }
//   });
// }
// 登录方法

// function handleLoginOtherAccount(param: { userName: string; password: string }) {
//   const { userName, password } = param;
//   login(userName, password);
// }
</script>

<style lang="scss" scoped>
.login-code {
  width: 33%;
  height: 38px;
  float: right;

  img {
    cursor: pointer;
    vertical-align: middle;
  }
}
:deep(.n-input) {
  width: 200px;
  height: 50px;
  line-height: 50px;
  font-size: 15px;
}
:deep(.n-input__input) {
  line-height: 40px;
  height: 50px;
  width: 200px;
}
:deep(.n-input__input-el) {
  height: 50px;
  line-height: 50px;
  font-size: 17px;
}
</style>
