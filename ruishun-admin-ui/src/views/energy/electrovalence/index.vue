<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline :model="search">
        <n-form-item label="名称">
          <n-input v-model:value="search.name" placeholder="请输入峰谷电价名称"></n-input>
        </n-form-item>
        <n-form-item>
          <component
            :is="
              useSearchBtn(() => {
                refresh(search);
              }, reset)
            "
          ></component>
        </n-form-item>
      </n-form>
    </my-card>
    <my-card title="峰谷电价列表">
      <template #right>
        <div>
          <component
            :is="
              customBtn({
                fun: () => {
                  editFlag = false;
                  showDialog = true;
                  addForm = {
                    name: '',
                    month: [['0', '0']],
                    startTime: undefined,
                    endTime: undefined,
                    electricityPrice: undefined
                  };
                },
                color: '#646cff',
                text: '新增峰谷电价',
                icon: 'AddOutline'
              })
            "
          ></component>
        </div>
      </template>
      <n-data-table
        :loading="loading"
        :data="data"
        :columns="columns"
        :max-height="dataTableConfig.maxHeight"
        :scroll-x="dataTableConfig.scrollWidth(columns)"
      ></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="refresh"></my-pagination>
    </my-card>
    <my-dialog
      v-model:show="showDialog"
      :title="editFlag ? '编辑梯度电价' : '新增梯度电价'"
      width="500px"
      @cancel="showDialog = false"
      @submit="submit"
    >
      <template #content>
        <div class="w-full">
          <n-form label-width="100" :model="addForm" label-placement="left">
            <n-form-item label="名称">
              <n-input v-model:value="addForm.name" placeholder="请输入名称"></n-input>
            </n-form-item>
            <n-form-item label="月份">
              <div class="flex-col">
                <div v-for="(arr, index) of addForm.month" :key="index" class="w-full flex mb-3px">
                  <n-input
                    v-model:value="addForm.month[index]"
                    style="width: 245px"
                    :placeholder="['开始月份', '结束月份']"
                    separator="-"
                    pair
                  ></n-input>
                  <div v-if="index === addForm.month.length - 1" class="flex ml-3px">
                    <div style="border: 1px solid gainsboro; padding: 8px; border-radius: 2px" @click="addMonth">
                      <svg-icon icon="ant-design:plus-outlined"></svg-icon>
                    </div>
                    <div style="border: 1px solid gainsboro; padding: 8px; border-radius: 2px" @click="delMonth(index)">
                      <svg-icon icon="material-symbols:remove"></svg-icon>
                    </div>
                  </div>
                </div>
              </div>
            </n-form-item>
            <n-form-item label="开始时段">
              <n-time-picker
                v-model:value="addForm.startTime"
                class="w-full"
                type="datetime"
                placeholder="请选择开始时段"
              ></n-time-picker>
            </n-form-item>
            <n-form-item label="结束时段">
              <n-time-picker
                v-model:value="addForm.endTime"
                class="w-full"
                type="date"
                placeholder="请选择结束时段"
              ></n-time-picker>
            </n-form-item>
            <n-form-item label="电价">
              <n-input v-model:value="addForm.electricityPrice" placeholder="请输入电价"></n-input>
            </n-form-item>
          </n-form>
        </div>
      </template>
    </my-dialog>
  </div>
</template>

<script setup lang="tsx">
import type { Ref } from 'vue';
import { ref } from 'vue';
import type { DataTableColumns } from 'naive-ui';
import dayjs from 'dayjs';
import { useTable } from '@/hooks';
import {
  getElectrovalenceList,
  addElectrovalence,
  delElectrovalence,
  editElectrovalence
} from '@/service/api/ecm/electrovalence';
import { dataTableConfig } from '@/config/dataTableConfig';
import { customBtn, useSearchBtn } from '@/hooks/common/useBtn';
import { deepClone } from '~/src/utils';

const { searchForm, data, refresh, reset, loading } = useTable<Energy.Electrovalence.Columns>(getElectrovalenceList);
const showDialog = ref(false);
const search = ref<{
  name: string | undefined;
}>({ name: undefined });

const editFlag = ref<boolean>(false);
const addForm = ref<{
  stepTariffId?: number;
  name: string | undefined;
  month: [string, string][];
  startTime: number | undefined; // 开始时段
  endTime: number | undefined; // 结束时段
  electricityPrice: string | undefined; // 电价
}>({
  name: '',
  month: [['0', '0']],
  startTime: undefined,
  endTime: undefined,
  electricityPrice: undefined
});
function addMonth() {
  addForm.value.month.push(['0', '0']);
}
function delMonth(index: number) {
  if (!index) return;
  addForm.value.month.splice(index, 1);
}
function submit() {
  const temp = addForm.value.month
    .map(item => {
      return `${item[0]}-${item[1]}`;
    })
    .join(',');
  if (addForm.value.stepTariffId) {
    editElectrovalence({
      ...addForm.value,
      month: temp,
      startTime: dayjs(addForm.value.startTime).format('HH:mm'),
      endTime: dayjs(addForm.value.endTime).format('HH:mm')
    }).then(_res => {
      if (_res.code === 200) {
        window.$message?.success('编辑成功');
        refresh(search.value);
        showDialog.value = false;
      }
    });
  } else {
    addElectrovalence({
      ...addForm.value,
      month: temp,
      startTime: dayjs(addForm.value.startTime).format('HH:mm'),
      endTime: dayjs(addForm.value.endTime).format('HH:mm')
    }).then(_res => {
      if (_res.code === 200) {
        window.$message?.success('新增成功');
        refresh(search.value);
        showDialog.value = false;
      }
    });
  }
}
const columns: Ref<DataTableColumns<Energy.Electrovalence.Columns>> = ref([
  {
    title: '名称',
    align: 'center',
    key: 'name'
  },
  {
    title: '月份',
    align: 'center',
    key: 'month'
  },
  {
    title: '开始时段',
    align: 'center',
    key: 'startTime'
  },
  {
    title: '结束时段',
    align: 'center',
    key: 'endTime'
  },
  {
    title: '电价',
    align: 'center',
    key: 'electricityPrice'
  },
  {
    title: '操作',
    key: 'action',
    align: 'center',
    render: _row => {
      if (_row.stepTariffId === 11) {
        return [];
      }
      return [
        customBtn({
          fun: () => {
            const row = deepClone(_row);
            // 获取今天的日
            const day = dayjs(new Date()).format('YYYY-MM-DD');
            // console.log(String(day) + _row.startTime);
            addForm.value = row as any;
            addForm.value.month = row.month.split(',').map(item => item.split('-')) as any;
            addForm.value.startTime = new Date(`${String(day)} ${row.startTime}`).getTime() as any;
            addForm.value.endTime = new Date(`${String(day)} ${row.endTime}`).getTime() as any;
            editFlag.value = true;
            showDialog.value = true;
          },
          text: '编辑',
          type: 'success',
          size: 'small'
        }),
        customBtn({
          fun: () => {
            delElectrovalence(_row.stepTariffId).then(res => {
              if (res.code === 200) {
                refresh(search.value);
              }
            });
          },
          text: '删除',
          type: 'error',
          size: 'small'
        })
      ];
    }
  }
]);
</script>

<style scoped></style>
