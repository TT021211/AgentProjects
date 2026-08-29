<template>
  <div class="report-container">
    <iframe :src="reportUrl" frameborder="0" width="100%" height="100%"></iframe>
  </div>
</template>

<script setup lang="tsx">
import { ref } from 'vue';
import { serviceEnv } from '~/.env-config';
const reportUrl = ref<string>(serviceEnv[import.meta.env.VITE_SERVICE_ENV as string].reportServerUrl);
</script>

<style scoped>
.report-container {
  width: 100%;
  height: 100vh;
}
</style>
