<template>
  <n-config-provider
    :theme="theme.naiveTheme"
    :theme-overrides="theme.naiveThemeOverrides"
    :locale="zhCN"
    :date-locale="dateZhCN"
    class="h-full"
  >
    <naive-provider>
      <router-view />
    </naive-provider>
  </n-config-provider>
  <loadingAwait v-show="app.isLoading" />
</template>

<script setup>
import { ref, watch, onMounted } from 'vue';
import { dateZhCN, zhCN } from 'naive-ui';
import { hiprint } from 'vue-plugin-hiprint';
import { subscribeStore, useThemeStore, useAppStore } from '@/store';
import { useGlobalEvents } from '@/composables';
import { getPrintTemplateList } from '@/service/api/md/printTemplate';
import { useSocketStore } from '@/store/modules/socket/index';
import { formatDate } from '~/src/utils/form/rule';
const app = useAppStore();
const socketStore = useSocketStore();

const theme = useThemeStore();
subscribeStore();
useGlobalEvents();

const temp = ref({});
const hiprintTemplate = ref(null);
const livePrint = ref(null);

function print(printData, title) {
  if (window.hiwebSocket.opened && window.hiwebSocket.printerList.length) {
    hiprintTemplate.value.print2(printData, { printer: livePrint.value, title });
  }
}

function isValidJSON(data) {
  try {
    JSON.parse(data);
    return true;
  } catch (e) {
    return false;
  }
}

watch(
  () => socketStore.msg,
  async newValue => {
    if (!newValue) return;
    if (isValidJSON(newValue)) {
      const msgData = JSON.parse(newValue);
      const data = JSON.parse(JSON.stringify(msgData));
      socketStore.setMsg(null);

      getPrintTemplateList({ templateCode: data.templateType }).then(async res => {
        if (res.code === 200) {
          if (!res.rows[0].content) return;
          temp.value = JSON.parse(res.rows[0].content);

          // hiprint.PrintElementTypeManager.buildByHtml($('.ep-draggable-item'));
          // $('#hiprint-printTemplate').empty();
          hiprintTemplate.value = await new hiprint.PrintTemplate({
            template: temp.value
          });

          livePrint.value = temp.value.panels[0].livePrint;
          data.printValue.forEach(item => {
            if (data.templateType === 'enamelCoverReport') {
              item.machineBatchNumber = `${item.qbPrintValue}/${item.alexLot}`;
              if (item.createTime) {
                item.createTime = formatDate(new Date(item.createTime), 'yyyy-MM-dd');
              }
              item.groupStartName = `${item.workgroupValue}    ${item.standardName}`;
            }
            // 判断是否补打
            if (data.reprintData) {
              addReprintRecord(data.reprintData);
            }
            print(item, '测试打印');
          });
        }
      });
    }
  }
);

onMounted(() => {
  const socket = localStorage.getItem('socket');
  if (socket) {
    socketStore.setOpenSocket(socket);
  }
});
</script>
<style scoped></style>
