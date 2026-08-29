<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline label-placement="top">
        <n-form-item label="工艺路线名称">
          <n-input v-model:value="searchForm.routingName" placeholder="请输入工艺路线名称" />
        </n-form-item>
        <n-form-item label="工艺路线编码">
          <n-input v-model:value="searchForm.routingCode" placeholder="请输入工艺路线编码" />
        </n-form-item>
        <n-form-item>
          <component
            :is="
              useSearchBtn(
                () => {
                  search();
                },
                () => {
                  reset();
                }
              )
            "
          ></component>
        </n-form-item>
      </n-form>
    </my-card>
    <my-card title="工艺路线列表">
      <template #right>
        <div class="flex-center">
          <component
            :is="
              useAddBtn(() => {
                flag = true;
              })
            "
          />
          <cx-columns v-model:columns="columns" size="small"></cx-columns>
        </div>
      </template>
      <n-data-table :data="data" :loading="loading" :columns="columns"></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="init" />
    </my-card>
    <my-dialog
      v-model:show="flag"
      :title="form.id ? '编辑工艺路线' : ' 添加工艺路线'"
      @submit="submit"
      @cancel="cancel"
    >
      <template #content>
        <div>
          <n-form ref="formRef" label-placement="left" :rules="rules" :model="form" label-width="80">
            <n-form-item label="路线名称" path="routingName">
              <n-input v-model:value="form.routingName" placeholder="请输入工艺路线名称" />
            </n-form-item>
            <n-form-item label="路线编码" path="routingCode">
              <n-input v-model:value="form.routingCode" placeholder="请输入工艺路线编码" />
            </n-form-item>
            <n-form-item label="备注" path="remark">
              <n-input
                v-model:value="form.remark"
                type="textarea"
                ::autosize="{
                  minRows: 2,
                  maxRows: 5
                }"
                placeholder="请输入备注"
              />
            </n-form-item>
          </n-form>
        </div>
      </template>
    </my-dialog>
    <my-dialog
      v-model:show="TFlag"
      title="编辑工序"
      :show-cancel="false"
      @cancel="
        () => {
          init();
          TFlag = false;
        }
      "
      @submit="
        () => {
          init();
          TFlag = false;
        }
      "
    >
      <template #content>
        <div style="display: flex; flex-direction: column">
          <n-form
            ref="processFormRef"
            :model="processForm"
            :rules="processRules"
            inline
            class="w-full"
            label-placement="left"
          >
            <n-form-item label="工序名称" path="workstageId">
              <n-select
                v-model:value="processForm.workstageId"
                size="small"
                style="width: 180px"
                placeholder="请选择工序"
                :options="lineOptions"
              ></n-select>
            </n-form-item>
            <n-form-item>
              <component
                :is="
                  useAddBtn(() => {
                    addProcess();
                  })
                "
              ></component>
            </n-form-item>
          </n-form>
          <n-empty v-if="!processData.length" description="该线路暂无工序"></n-empty>
          <div class="w-full flex-center">
            <n-steps size="small" vertical style="width: 300px">
              <n-step
                v-for="(item, index) in processData"
                :key="item.id"
                :title="item.workstageName"
                :description="item.remark"
              >
                <component
                  :is="
                    useDelBtn(() => {
                      console.log(item);
                      fetchDeleteProcess(item.id).then(res => {
                        if (res.code == 200) {
                          processData.splice(index, 1);
                        }
                      });
                    }, 'tiny')
                  "
                  v-if="index === processData.length - 1"
                  style="position: absolute; right: 0px; top: -1px"
                ></component>
              </n-step>
            </n-steps>
          </div>
        </div>
      </template>
    </my-dialog>
  </div>
</template>

<script setup lang="tsx">
import type { Ref } from 'vue';
import { ref, onMounted } from 'vue';
import { NSteps, NStep, useMessage } from 'naive-ui';
import type { DataTableColumns, FormInst } from 'naive-ui';
import { createRequiredFormRule, deepClone } from '~/src/utils';
import { useLoading } from '~/src/hooks';
import { useEditBtn, useDelBtn, useInfoBtn, useAddBtn, useSearchBtn } from '~/src/hooks/common/useBtn';
import {
  fetchDeleteProcess,
  getProcessList,
  fetchAddProcess,
  getAllProcessList,
  getAllTechnologyList,
  fetchAddTechnology,
  fetchDeleteTechnology,
  fetchEditTechnology
} from '~/src/service/api/technology/technologyLine/index';

const message = useMessage();
const processFormRef = ref<FormInst | null>(null);
const processRules = {
  workstageId: createRequiredFormRule('工序名称不能为空')
};
const tempLineNumber = ref<number>(0);
const lineOptions = ref<
  {
    label: string;
    value: number;
  }[]
>([]);
const processForm = ref({
  workstageId: null
});
const processData = ref<Tline.processDataType[]>([]);
function addProcess() {
  // eslint-disable-next-line no-console
  console.log(processForm.value.workstageId);
  processFormRef.value?.validate(errors => {
    if (!errors) {
      const tempData: Tline.addProcessType = {
        routingId: tempLineNumber.value,
        workstageId: processForm.value.workstageId,
        orderNum: processData.value.length + 1
      };
      // processData.value.push(tempData);
      fetchAddProcess(tempData).then(res => {
        if (res.code === 200) {
          message.success('添加成功');
          processForm.value.workstageId = null;
          getProcessList(tempLineNumber.value).then(response => {
            if (res.code === 200) {
              processData.value = response.data;
            } else {
              processData.value = [];
            }
          });
        }
      });
    }
  });
}
const TFlag = ref<boolean>(false);

const { loading, startLoading, endLoading } = useLoading();
const flag = ref<boolean>(false);
const form = ref<Tline.formData>({
  routingName: '',
  routingCode: '',
  remark: ''
});
const data = ref<Tline.columns[]>([]);
const rules = {
  routingName: createRequiredFormRule('请输入工艺路线名称'),
  routingCode: createRequiredFormRule('请输入工艺路线编码')
};
const formRef = ref<FormInst | null>(null);

const columns: Ref<DataTableColumns<Tline.columns>> = ref([
  {
    title: '路线名称',
    align: 'center',
    titleAlign: 'center',
    key: 'routingName',
    width: 200
  },
  {
    title: '路线编码',
    align: 'center',
    titleAlign: 'center',
    key: 'routingCode',
    width: 150
  },
  {
    title: '工序列表',
    key: 'routingWorkstages',
    align: 'center',
    titleAlign: 'center',
    render: (row: any) => {
      return (
        <NSteps size="small">
          {row.routingWorkstages.map((item: any) => {
            return <NStep title={item.lable} />;
          })}
        </NSteps>
      );
    }
  },
  {
    title: () => {
      return '备注';
    },
    align: 'center',
    titleAlign: 'center',
    key: 'remark',
    width: 100
  },
  {
    title: '操作',
    key: 'actions',
    width: 270,
    align: 'center',
    titleAlign: 'center',
    fixed: 'right',
    render: row => {
      return [
        useEditBtn(() => {
          editTLine(row);
        }, 'tiny'),
        useDelBtn(() => {
          deleteTline(row.id);
        }, 'tiny'),
        useInfoBtn(
          () => {
            TFlag.value = true;
            tempLineNumber.value = row.id;
            getProcessList(tempLineNumber.value).then(res => {
              if (res.code === 200) {
                processData.value = res.data;
              } else {
                processData.value = [];
              }
            });
          },
          'tiny',
          '编辑工序'
        )
      ];
    }
  }
]);
function submit() {
  formRef.value?.validate(errors => {
    if (!errors) {
      if (!form.value.id) {
        fetchAddTechnology(form.value).then(res => {
          if (res.code === 200) {
            message.success('添加成功');
            flag.value = false;
            init();
            cancel();
          }
        });
      } else {
        fetchEditTechnology(form.value).then(res => {
          if (res.code === 200) {
            message.success('修改成功');
            flag.value = false;
            init();
            cancel();
          }
        });
      }
    }
  });
}
function cancel() {
  flag.value = false;
  form.value = {
    routingName: '',
    routingCode: '',
    remark: ''
  };
}
function editTLine(row) {
  form.value = deepClone(row);
  flag.value = true;
}
function deleteTline(id: number) {
  fetchDeleteTechnology(id).then(res => {
    if (res.code === 200) {
      message.success('删除成功');
      init();
    }
  });
}
const searchForm = ref<Tline.searchForm>({
  routingName: '',
  routingCode: '',
  pageNum: 1,
  pageSize: 10,
  total: 0
});
function search() {
  searchForm.value.pageNum = 1;
  init();
}
function reset() {
  searchForm.value = {
    routingName: '',
    routingCode: '',
    pageNum: 1,
    pageSize: 10,
    total: 0
  };
  init();
}
function init() {
  startLoading();
  data.value = [];
  getAllTechnologyList(searchForm.value).then(res => {
    if (res.code === 200) {
      data.value = res.rows;
      searchForm.value.total = res.total;
    }
  });
  endLoading();
}
onMounted(() => {
  init();
  getAllProcessList().then(res => {
    if (res.code === 200) {
      res.data.forEach(item => {
        lineOptions.value.push({
          label: item.workstageName,
          value: item.id
        });
      });
    }
  });
});
</script>

<style scoped lang="scss"></style>
