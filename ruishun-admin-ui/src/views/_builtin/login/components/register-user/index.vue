<template>
  <n-form ref="formRef" :model="model" :rules="rules" size="large" :show-label="false">
    <!-- <n-form-item path="phone">
      <n-input v-model:value="model.phone" :placeholder="$t('page.login.common.phonePlaceholder')" />
    </n-form-item> -->
    <!-- <n-form-item path="code">
      <div class="flex-y-center w-full">
        <n-input v-model:value="model.code" :placeholder="$t('page.login.common.codePlaceholder')" />
        <div class="w-18px"></div>
        <n-button size="large" :disabled="isCounting" :loading="smsLoading" @click="handleSmsCode">
          {{ label }}
        </n-button>
      </div>
    </n-form-item> -->
    <n-form-item path="userName">
      <n-input v-model:value="model.username" :placeholder="$t('page.login.common.userNamePlaceholder')" />
    </n-form-item>
    <n-form-item path="pwd">
      <n-input
        v-model:value="model.password"
        type="password"
        show-password-on="click"
        :placeholder="$t('page.login.common.passwordPlaceholder')"
      />
    </n-form-item>
    <n-form-item path="confirmPwd">
      <n-input
        v-model:value="model.confirmPassword"
        type="password"
        show-password-on="click"
        :placeholder="$t('page.login.common.confirmPasswordPlaceholder')"
      />
    </n-form-item>
    <n-form-item path="imgCode">
      <n-input v-model:value="model.code" :placeholder="$t('page.login.codeLogin.imageCodePlaceholder')" />
      <div class="pl-8px">
        <!-- <image-verify v-model:code="imgCode" /> -->
        <img :src="codeUrl" class="login-code-img" @click="getCode" />
      </div>
    </n-form-item>
    <n-space :vertical="true" :size="18">
      <!-- <login-agreement v-model:value="agreement" /> -->
      <n-button type="primary" size="large" :block="true" :round="true" @click="handleSubmit">
        {{ $t('page.login.common.confirm') }}
      </n-button>
      <n-button size="large" :block="true" :round="true" @click="toLoginModule('pwd-login')">
        {{ $t('page.login.common.back') }}
      </n-button>
    </n-space>
  </n-form>
</template>

<script lang="ts" setup>
import { reactive, ref, toRefs, onMounted } from 'vue';
import type { FormInst, FormRules } from 'naive-ui';
import { getImageCode } from '@/service';
import { useRouterPush } from '@/composables';
// import { useSmsCode } from '@/hooks';
import { formRules, getConfirmPwdRule } from '@/utils';
import { $t } from '@/locales';

const { toLoginModule } = useRouterPush();
// const { label, isCounting, loading: smsLoading, start } = useSmsCode();
const codeUrl = ref('');
const formRef = ref<HTMLElement & FormInst>();

const model = reactive({
  code: '',
  username: '',
  password: '',
  confirmPassword: '',
  uuid: ''
});

const rules: FormRules = {
  // phone: formRules.phone,
  code: formRules.code,
  password: formRules.password,
  confirmPwd: getConfirmPwdRule(toRefs(model).password)
};

// const agreement = ref(false);

// function handleSmsCode() {
//   start();
// }
onMounted(() => {
  getCode();
});
async function getCode() {
  const result: any = await getImageCode();
  codeUrl.value = `data:image/gif;base64,${result.img}`;
  model.uuid = result.uuid;
}
async function handleSubmit() {
  await formRef.value?.validate();
  window.$message?.success($t('page.login.common.validateSuccess'));
  console.log('Sdfsd');
}
</script>

<style scoped></style>
