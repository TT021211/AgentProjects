<template>
  <div id="login_container" class="pt-24px"></div>
</template>

<script setup lang="ts">
import { onMounted } from 'vue';
// import { useRoute } from 'vue-router';
// import { useDialog } from 'naive-ui';
// import { useAuthStore } from '@/store/index';

// const dialog = useDialog();
// const router = useRouter();
// onBeforeRouteUpdate((to) => {
//         // console.log('onBeforeRouteUpdate',to.path);
//  });
// const route = useRoute();
// qywx.24yt.com
// 初始化企业微信
// const initCode = () => {
//   return new WwLogin({
//     id: 'login_container',
//     appid: 'ww63d3ad0bbe336bd3',
//     agentid: '1000004',
//     redirect_uri: 'http://yh-api.24yt.com/auth/wechatLogin',
//     state: '',
//     href: '',
//     lang: 'zh'
//   });
// };

// watch(
//   () => router.currentRoute.value.fullPath,
//   (newValue, oldValue) => {
//     console.log(newValue, oldValue);
//   }
// async () => {
// 	console.log(route.query.code)
//   if (route.query.code) {
//     try {
//       decodeURIComponent(route.query.code as string);
//       // 直接调用自己登录接口（注意交互 加个loading 啥的)
//       // const res = await getUserInfo({ code: code });
//       router.push({ name: 'home' });
//     } catch (err) {
//       // 自己的登录接口失败（可能过期啥的）直接重新进入登录页
//       console.log(err);
//       router.replace({
//         name: 'login'
//       });
//       initCode();
//     }
//   }
// }
// );
// const errMap = {
//   '401': '登录用户不存在',
//   '402': '该用户已被删除',
//   '403': '该账号已被停用'
// };
// const { handleActionAfterLogin } = useAuthStore();

onMounted(() => {
  // initCode();
});
</script>

<style scoped>
#login_container {
  width: 100%;
  height: 400px;
  display: flex;
  justify-content: center;
}
</style>
