<template>
  <my-dialog v-model:show="show" title="修改信息" @cancel="cancel" @submit="submit">
    <template #content>
      <div class="w-full">
        <n-form v-if="data" ref="addFormRef" label-placement="left" :model="data" :rules="rules" label-width="100">
          <n-form-item label="打印类型" path="createBy">
            <n-select
              v-model:value="data.createBy"
              :options="[
                { value: '0', label: '轴模板' },
                { value: '1', label: '箱模板' },
                { value: '2', label: '托模板' },
                { value: '3', label: '原料模板' },
                { value: '4', label: '报工模板' },
                { value: '5', label: '废丝模板' }
              ]"
              placeholder="请选择打印类型"
            ></n-select>
          </n-form-item>
          <n-form-item label="模板类型">
            <n-select
              v-model:value="data.templateCode"
              :options="printTypeList"
              placeholder="请选择模板类型"
              @update:value="(val, item: any) => {
              data.code = item.value;
              console.log('data.templateName ==? ', data.templateName);
            }"
            ></n-select>
          </n-form-item>
          <n-form-item label="模板名称">
            <n-input v-model:value="data.templateName"></n-input>
          </n-form-item>
          <!-- <n-form-item label="模板名称">
            <n-input v-model:value="data.templateName" placeholder="请输入模板名称"></n-input>
          </n-form-item> -->
          <n-form-item label="所属客户">
            <n-select
              v-model:value="data.clientId"
              :options="clientOptions"
              :filterable="true"
              :clearable="true"
              placeholder="请选择客户"
              @change="selectClient"
            ></n-select>
          </n-form-item>
          <n-form-item label="所属用户">
            <n-select
              v-model:value="data.userId"
              :options="userList"
              :filterable="true"
              :clearable="true"
              placeholder="请选择用户"
              @update:value="
                (val, item: any) => {
                  data.userNickName = item.label;
                }
              "
            ></n-select>
          </n-form-item>
          <n-form-item label="默认打印机">
            <n-input v-model:value="data.defaultPrinterName" placeholder="请输入客户"></n-input>
          </n-form-item>
        </n-form>
      </div>
    </template>
  </my-dialog>
</template>

<script setup lang="tsx">
import { watch, ref } from 'vue';
import type { FormInst } from 'naive-ui';
import { getPrintTemplateInfo, updatePrintTemplate } from '@/service/api/md/printTemplate';
import { createRequiredFormRule } from '~/src/utils';
// const { proxy } = getCurrentInstance() as any;
// const { print_template_type } = proxy.useDict('print_template_type');
const show = defineModel('show', { type: Boolean, default: false });
function cancel() {
  show.value = false;
}
interface Emits {
  (e: 'init'): void;
  /** 点击协议 */
}
const rules = {
  createBy: createRequiredFormRule('请输入字典标签')
};
const addFormRef = ref<FormInst | null>(null);
const emit = defineEmits<Emits>();
const props = withDefaults(
  defineProps<{
    id: string | number;
    clientOptions: any[];
    printTypeList: any[];
    userList: any[];
  }>(),
  {
    id: '',
    clientOptions: () => [],
    printTypeList: () => []
  }
);
const data = ref<any>(null);
function selectClient(_val, item) {
  if (!data.value) return;
  data.value.clientId = item.id;
  data.value.clientCode = item.code;
  data.value.clientName = item.label;
}
function submit() {
  addFormRef.value?.validate(errors => {
    if (!errors) {
      if (!data.value.userId) data.value.userNickName = '';
      updatePrintTemplate(data.value).then(res => {
        if (res.code === 200) {
          window.$message?.success('修改成功');
          emit('init');
          cancel();
        }
      });
    }
  });
}
watch(
  () => props.id,
  () => {
    getPrintTemplateInfo(props.id).then(res => {
      data.value = res.data;
    });
  }
);
</script>

<style scoped></style>
