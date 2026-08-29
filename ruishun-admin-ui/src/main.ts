import { createApp } from 'vue';
import Antd from 'ant-design-vue';
import Storage from 'vue-ls';
import { hiPrintPlugin, autoConnect } from 'vue-plugin-hiprint';
import DataV from '@kjgl77/datav-vue3';
import MyDialog from '@/components/MyDialog/index.vue';
import MyCard from '@/components/MyCard/index.vue';
import MyPagination from '@/components/MyPagination/index.vue';
import CxColumns from '@/components/CXColumnsSetting/index.vue';
import MySwitch from '@/components/MySwitch/index.vue';
import coverData from '@/components/moldData/coverData.vue';
// eslint-disable-next-line import/no-duplicates
import nestingView from '@/components/moldData/nestingView.vue';
import ledgerData from '@/components/moldData/ledgerData.vue';
// eslint-disable-next-line import/no-duplicates
import ledgerList from '@/components/moldData/nestingView.vue';
import loadingAwait from '@/components/loading/index.vue';
import useDict from '@/utils/common/useDict';
import DictTag from '@/components/DictTag/index.vue';
import App from './App.vue';
import AppLoading from './components/common/app-loading.vue';
import { setupDirectives } from './directives';
import { setupRouter } from './router';
import { setupAssets } from './plugins';
import { setupStore } from './store';
import { setupI18n } from './locales';
import { dataTableConfig } from './config/dataTableConfig';

autoConnect();
async function setupApp() {
  // import assets: js、css
  setupAssets();

  // app loading
  const appLoading = createApp(AppLoading);

  appLoading.mount('#appLoading');

  const app = createApp(App);

  // store plugin: pinia
  setupStore(app);

  // vue custom directives
  setupDirectives(app);

  // vue router
  await setupRouter(app);

  setupI18n(app);

  appLoading.unmount();
  app.component('MyDialog', MyDialog);
  app.component('MyCard', MyCard);
  app.component('MyPagination', MyPagination);
  app.component('CxColumns', CxColumns);
  app.component('MySwitch', MySwitch);
  app.component('DictTag', DictTag);
  app.component('CoverData', coverData);
  app.component('NestingView', nestingView);
  app.component('LedgerData', ledgerData);
  app.component('LedgerList', ledgerList);
  app.component('LoadingAwait', loadingAwait);
  app.use(Antd);
  // 字典
  app.use(DataV);
  app.use(hiPrintPlugin);

  const options = {
    namespace: 'hiPrint-',
    name: 'ls',
    storage: 'local'
  };
  app.use(Storage, options);

  app.config.globalProperties.useDict = useDict;
  /**
   * @description: 全局变量
   */
  app.config.globalProperties.maxHeight = dataTableConfig.maxHeight;
  app.config.globalProperties.scrollWidth = dataTableConfig.scrollWidth;
  // mount app
  app.mount('#app');
}

setupApp();
