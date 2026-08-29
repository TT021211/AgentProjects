<template>
  <div>
    <my-card title="客户标准">
      <template #right>
        <n-button class="mr-5px ml-5px" type="info" size="small" @click="uploadShow = true">
          <ArrowUpCircleOutline class="mr-2px text-25px w-20px" />
          导入标准
        </n-button>
        <n-button :loading="exportLoading" size="small" type="warning" class="mr-5px ml-5px" @click="exportList">
          <ArrowDownCircleOutline class="mr-2px text-25px w-20px" />
          导出标准
        </n-button>
      </template>
      <n-data-table :data="data" :columns="columns"></n-data-table>
    </my-card>

    <my-dialog
      :show="uploadShow"
      :title="'导入客户标准'"
      width="400px"
      @cancel="uploadShow = false"
      @submit="submitUpload"
    >
      <template #content>
        <div>
          <n-form ref="formModel" style="width: 600px" label-placement="left" label-align="left" :label-width="130">
            <n-form-item class="mr-5px ml-190px">
              <n-upload
                :show-file-list="true"
                :style="{ width: formItemWidth }"
                :default-upload="true"
                :accept="'.xls,.xlsx'"
                :directory-dnd="true"
                :max="5"
                @change="changeUpload"
              >
                <n-button :style="{ width: formItemWidth }">选择文件</n-button>
                <n-upload-dragger>
                  <div style="margin-bottom: 1px">
                    <n-icon size="48" :depth="3">
                      <ArchiveOutline />
                    </n-icon>
                  </div>
                  <n-text style="font-size: 16px">点击或者拖动文件到该区域来上传</n-text>
                  <n-p depth="3" style="margin: 8px 0 0 0">提示：仅允许导入“xls”或“xlsx”格式文件！</n-p>
                </n-upload-dragger>
              </n-upload>
            </n-form-item>
            <n-button class="ml-260px mt-10px" type="primary" dashed @click="importTemplate()">下载模板</n-button>
          </n-form>
        </div>
      </template>
    </my-dialog>
  </div>
</template>

<script setup lang="ts">
import type { Ref } from 'vue';
import { ref, onMounted } from 'vue';
import type { DataTableColumns } from 'naive-ui';
import { useMessage } from 'naive-ui';
import { ArrowUpCircleOutline, ArrowDownCircleOutline, ArchiveOutline } from '@vicons/ionicons5';
import { download } from '@/service/request/helpers';
import { getClientStandard, uploadClientStandard } from '@/service/api/technology/technologyStandard';

type DataType = {
  id?: number;
  plantName: string;
};
const message = useMessage();
const exportLoading = ref<boolean>(false);
const formItemWidth = ref<string>('220px');
const uploadFormData = ref<any | null>(null);
const uploadShow = ref<boolean>(false);
function exportList() {
  exportLoading.value = true;
  download('mes/mesProcessStandard/export', null, `客户标准.xlsx`, undefined)
    .then(() => {
      exportLoading.value = false;
    })
    .catch(() => {
      exportLoading.value = false;
    });
}
const data = ref<DataType[]>([]);
const columns: Ref<DataTableColumns<DataType>> = ref([
  {
    title: '序号',
    key: 'index',
    align: 'center',
    width: 80,
    render: (_row, index) => index + 1
  },
  {
    title: '标准名称',
    align: 'center',
    width: 80,
    key: 'standardName'
  },
  {
    title: '客户名称',
    align: 'center',
    width: 80,
    key: 'customerName'
  },
  {
    title: '导体标称直径',
    align: 'center',
    width: 80,
    key: 'standardDiameter'
  },
  // {
  //   title: '漆包线最大外径',
  //   key: 'sfas',
  //   width: 80,
  //   align: 'center',
  //   children: [
  //     {
  //       title: '薄漆膜',
  //       width: 80,
  //       key: 'wireFilmMax'
  //     },
  //     {
  //       title: '厚漆膜',
  //       width: 80,
  //       key: 'wireThickMax'
  //     }
  //   ]
  // },
  // {
  //   title: '最小漆膜厚度',
  //   align: 'center',
  //   width: 80,
  //   key: 'sfas',
  //   children: [
  //     {
  //       title: '薄漆膜',
  //       width: 80,
  //       key: 'wireFilmMin'
  //     },
  //     {
  //       title: '厚漆膜',
  //       width: 80,
  //       key: 'wireThickMin'
  //     }
  //   ]
  // },
  {
    title: '最大外径',
    align: 'center',
    width: 80,
    key: 'outsideDiameterMax'
  },
  {
    title: '导体偏差',
    align: 'center',
    width: 80,
    key: 'conductorDeviation'
  },
  {
    title: '导体f值',
    align: 'center',
    width: 80,
    key: 'conductorFNumber'
  },
  {
    title: '最小直流电阻(℃)',
    align: 'center',
    width: 80,
    key: 'dcResistanceMin'
  },
  {
    title: '最大直流电阻(℃)',
    align: 'center',
    width: 80,
    key: 'dcResistanceMax'
  },
  {
    title: '伸长率',
    align: 'center',
    width: 80,
    key: 'elongation'
  },
  {
    title: '回弹性',
    align: 'center',
    width: 80,
    key: 'resilience'
  },
  {
    title: '圆棒卷绕',
    align: 'center',
    width: 80,
    key: 'wind'
  },
  {
    title: '拉伸',
    width: 80,
    align: 'center',
    key: 'stretch'
  },
  {
    title: '急拉断',
    align: 'center',
    width: 80,
    key: 'snapBreak'
  },
  {
    title: '剥离扭绞',
    align: 'center',
    width: 80,
    key: 'strippingTwist'
  },
  {
    title: '热冲击',
    align: 'center',
    width: 80,
    key: 'thermalShock'
  },
  {
    title: '软化冲击',
    align: 'center',
    width: 80,
    key: 'softeningImpact'
  },
  {
    title: '软化击穿',
    align: 'center',
    width: 80,
    key: 'softeningBreakdown'
  },
  // {
  //   title: '刮漆1',
  //   width: 80,
  //   align: 'center',
  //   key: 'scraperPaintingOne'
  // },
  // {
  //   title: '刮漆2',
  //   align: 'center',
  //   width: 80,
  //   key: 'scraperPaintingTwo'
  // },
  {
    title: '耐溶剂',
    width: 80,
    align: 'center',
    key: 'solventResistance'
  },
  {
    title: '室温击穿电压',
    align: 'center',
    width: 80,
    key: 'roomtempBreakdown'
  },
  {
    title: '高压针孔',
    align: 'center',
    width: 80,
    key: 'highPressurePinhole'
  },
  {
    title: '盐水针孔',
    align: 'center',
    width: 80,
    key: 'brinePinhole'
  },
  {
    title: '焊锡性',
    width: 80,
    align: 'center',
    key: 'solderability'
  },
  {
    title: '外观',
    width: 80,
    align: 'center',
    key: 'appearance'
  },
  {
    title: '包装',
    width: 80,
    align: 'center',
    key: 'package'
  },
  {
    title: '环保材料',
    width: 80,
    align: 'center',
    key: 'envMaterial'
  },
  {
    title: '其他要求',
    width: 80,
    align: 'center',
    key: 'otherRequirements'
  }
]);

function importTemplate() {
  exportLoading.value = true;
  download('mes/mesProcessStandard/importTemplate', null, `标准模板.xlsx`, undefined)
    .then(() => {
      exportLoading.value = false;
    })
    .catch(() => {
      exportLoading.value = false;
    });
}
function submitUpload() {
  if (uploadFormData.value === null) {
    message.warning('请选择文件进行上传');
    return;
  }
  uploadClientStandard(uploadFormData.value, {
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  }).then(res => {
    if (res.code === 200) {
      message.success('上传成功');
      uploadShow.value = false;
      init();
    }
  });
}
function changeUpload(files) {
  if (files.fileList.length === 0) {
    uploadFormData.value = null;
    return;
  }
  uploadFormData.value = files.file;
}
function init() {
  getClientStandard().then(res => {
    data.value = res.data;
  });
}
onMounted(() => {
  init();
});
</script>

<style scoped></style>
