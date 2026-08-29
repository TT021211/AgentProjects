<!-- eslint-disable no-console -->
<template>
  <div class="weightAdmin">
    <n-card>
      <div class="box">
        <div class="left">
          <n-radio-group v-model:value="locale">
            <n-space style="display: flex; align-items: center; margin-bottom: 30px">
              <n-radio label="原材料" value="0" />
              <n-radio label="成品" value="1" />
              <n-button type="info" size="small" @click="reset">重置信息</n-button>
              <n-button type="info" size="small" @click="lookStorageRecord">查看称重记录</n-button>
            </n-space>
          </n-radio-group>
          <n-form
            v-show="locale === '0'"
            ref="formModel"
            :model="addForm"
            label-placement="left"
            label-align="left"
            :label-width="120"
            :rules="rules"
          >
            <!-- <n-form-item label="贸易商" path="ingredientsId">
              <n-select
                v-model:value="addForm.ingredientsId"
                placeholder="请选择贸易商"
                :filterable="true"
                :options="traderList"
                :style="{ width: witeFormItemWidth }"
                @update:value="
                  (val, item) => {
                    addForm.ingredientsName = item.label;
                  }
                "
              ></n-select>
            </n-form-item> -->
            <n-form-item label="材料厂家" path="manufacturerId">
              <n-select
                v-model:value="addForm.manufacturerId"
                placeholder="选择材料厂家"
                :filterable="true"
                :options="manufacturerList"
                :style="{ width: witeFormItemWidth }"
                @update:value="
                  (val, item) => {
                    addForm.manufacturerName = item.label;
                    addForm.manufacturerCode = item.manufacturerCode;
                    getLocalSpecList(item.label).then(r => {
                      if (r.data.length > 0) {
                        const lotNumber = r.data[0].lotNumber;
                        everyDayNum = Number(
                          lotNumber.substring(r.data[0].lotNumber.length - 3, r.data[0].lotNumber.length)
                        );
                        everyDayNum++;
                        addForm.partNumber =
                          dateFormatters +
                          item.manufacturerCode +
                          'L' +
                          (everyDayNum < 10 ? '00' + everyDayNum : everyDayNum < 100 ? '0' + everyDayNum : everyDayNum);
                        addForm.lotNumber =
                          dateFormatters +
                          item.manufacturerCode +
                          'P' +
                          (everyDayNum < 10 ? '00' + everyDayNum : everyDayNum < 100 ? '0' + everyDayNum : everyDayNum);
                      } else {
                        addForm.partNumber = dateFormatters + item.manufacturerCode + 'L001';
                        addForm.lotNumber = dateFormatters + item.manufacturerCode + 'P001';
                      }
                    });
                  }
                "
              ></n-select>
            </n-form-item>
            <n-form-item label="料号" path="partNumber">
              <n-input
                v-model:value="addForm.partNumber"
                type="text"
                placeholder="料号"
                :style="{ width: witeFormItemWidth }"
              />
            </n-form-item>
            <n-form-item label="批号" path="lotNumber">
              <n-input
                v-model:value="addForm.lotNumber"
                type="text"
                placeholder="批号"
                :style="{ width: witeFormItemWidth }"
              />
            </n-form-item>
            <n-form-item label="品名" path="productId">
              <n-select
                v-model:value="addForm.productId"
                placeholder="选择品名"
                :filterable="true"
                :options="materialsList"
                :style="{ width: witeFormItemWidth }"
                @update:value="
                  (val, item) => {
                    if (item.label.includes('锡')) {
                      addForm.attr2 = 1;
                    } else {
                      addForm.attr2 = 0;
                    }
                    addForm.productName = item.label;
                  }
                "
              ></n-select>
            </n-form-item>
            <n-form-item label="规格" path="itemId">
              <n-select
                v-model:value="addForm.itemId"
                placeholder="选择规格"
                :filterable="true"
                :options="specificationList"
                :style="{ width: witeFormItemWidth }"
                @update:value="handleUpdateValue"
              ></n-select>
            </n-form-item>

            <!-- <n-form-item label="点击扫码" v-if="locale === '1'">
            <n-input v-model:value="scanCode" ref="inputValue" placeholder="点击后扫码"
              :style="{ width: witeFormItemWidth }"  @keydown="handleKeydown" />
          </n-form-item> -->
          </n-form>

          <n-form
            v-show="locale === '1'"
            ref="semiFinProductRef"
            :model="semiFinProduct"
            label-placement="left"
            label-align="left"
            :label-width="120"
            :rules="semiFinProductRules"
          >
            <n-form-item label="点击扫码">
              <n-input
                ref="inputValue"
                v-model:value="scanCode"
                placeholder="点击后扫码"
                :style="{ width: witeFormItemWidth }"
              />
            </n-form-item>
            <n-form-item label="客户简称">
              <n-select
                v-model:value="semiFinProduct.trader"
                placeholder="请选择客户简称"
                :filterable="true"
                :disabled="true"
                :options="saleUserList"
                :style="{ width: witeFormItemWidth }"
                @update:value="
                  (val, item) => {
                    semiFinProduct.trader = item.label;
                  }
                "
              ></n-select>
            </n-form-item>
            <n-form-item label="仓库" path="artt1">
              <n-select
                v-model:value="semiFinProduct.artt1"
                placeholder="请选择仓库"
                :filterable="true"
                :disabled="true"
                :options="warehouseOptions"
                :style="{ width: witeFormItemWidth }"
                @update:value="
                  (val, item) => {
                    semiFinProduct.artt1 = item.label;
                  }
                "
              ></n-select>
            </n-form-item>
            <n-form-item label="品名" path="productName">
              <n-select
                v-model:value="semiFinProduct.productName"
                placeholder="请选择产品名称"
                :filterable="true"
                :disabled="true"
                :options="productList"
                :style="{ width: witeFormItemWidth }"
                @update:value="getSpecData"
              ></n-select>
            </n-form-item>
            <n-form-item label="规格" path="wmsSpec">
              <n-select
                v-model:value="semiFinProduct.wmsSpec"
                placeholder="请选择规格"
                :filterable="true"
                :disabled="true"
                :options="specList"
                :style="{ width: witeFormItemWidth }"
                @update:value="specChange"
              ></n-select>
            </n-form-item>
            <!-- <n-form-item label="物品编码" path="itemCode">
              <n-select
                v-model:value="semiFinProduct.itemCode"
                placeholder="请选择物品编码"
                :filterable="true"
                :options="itemCodeOptions"
                :style="{ width: witeFormItemWidth }"
                @update:value="itemCodeChange"
              ></n-select>
            </n-form-item> -->
            <n-form-item label="批号" path="batchNumber2">
              <n-select
                v-model:value="semiFinProduct.batchNumber2"
                placeholder="请选择批号"
                :filterable="true"
                :disabled="true"
                :options="batchNumberOptions"
                :style="{ width: witeFormItemWidth }"
                @update:value="
                  (_val, item) => {
                    semiFinProduct.batchNumber2 = item.label;
                  }
                "
              ></n-select>
            </n-form-item>
            <n-form-item label="物料代码">
              <n-input
                v-model:value="semiFinProduct.materialCode"
                type="text"
                :disabled="true"
                placeholder="请选择物品编码"
                :style="{ width: witeFormItemWidth }"
                @change="materialCodeChange"
              />
            </n-form-item>

            <n-form-item label="累计净重">
              <n-space style="font-size: 25px !important; margin-right: 10px">{{ sumWeight + ' kg' }}</n-space>
            </n-form-item>
          </n-form>
        </div>

        <div class="right-box">
          <div class="pub-box">
            <n-space style="font-size: 25px !important; margin-right: 10px">净重 :</n-space>
            <n-space class="weight">{{ parseFloat(netWeight).toFixed(3) }}</n-space>
            <n-space>(单位 : kg)</n-space>
          </div>
          <div class="pub-box">
            <n-tag :bordered="false" type="info">
              {{ statusMessage }}
            </n-tag>
            <div class="radio-box">
              <span>通信{{ isJoin ? '(已连接)' : '(已关闭)' }}</span>
              <span class="radio" :class="[isJoin ? 'green' : 'red']"></span>
            </div>
          </div>
          <div class="pub-box">
            <n-form
              ref="portFormModel"
              label-align="left"
              label-placement="left"
              :label-width="120"
              style="width: 700px"
              :model="selectPortForm"
              :rules="portRules"
            >
              <n-form-item label="设备">
                <n-select
                  v-model:value="selectPortForm.equipmentId"
                  placeholder="选择设备"
                  :clearable="true"
                  :filterable="true"
                  :options="equipmentList"
                  :style="{ width: formItemWidth }"
                  @update-value="handleUpdateValueDevice"
                ></n-select>
              </n-form-item>

              <n-form-item v-if="locale === '0'" label="原料模板">
                <n-select
                  v-model:value="selectPortForm.printMaterialsTemplate"
                  placeholder="选择原料打印模板"
                  :clearable="true"
                  :filterable="true"
                  :options="MaterialsPrintTemplateDataList"
                  :style="{ width: formItemWidth }"
                  @update-value="changeMaterialsPrintTemplate"
                ></n-select>
              </n-form-item>
              <n-form-item v-if="locale === '1'" label="轴模板">
                <n-select
                  v-model:value="selectPortForm.printAxisTemplate"
                  placeholder="选择轴打印模板"
                  :clearable="true"
                  :filterable="true"
                  :options="axisPrintTemplateDataList"
                  :style="{ width: formItemWidth }"
                  @update-value="changeAxisPrintTemplate"
                ></n-select>
              </n-form-item>

              <n-form-item v-if="locale === '1'" label="箱模板">
                <n-select
                  v-model:value="selectPortForm.printBoxTemplate"
                  placeholder="选择箱打印模板"
                  :clearable="true"
                  :filterable="true"
                  :options="boxPrintTemplateDataList"
                  :style="{ width: formItemWidth }"
                  @update-value="changeBoxPrintTemplate"
                ></n-select>
              </n-form-item>
              <n-form-item label="毛重">
                <n-input-number
                  v-model:value="grossWeight"
                  type="text"
                  :show-button="false"
                  placeholder="请输入毛重"
                  :style="{ width: witeFormItemWidth }"
                >
                  <template #suffix>kg</template>
                </n-input-number>
              </n-form-item>
              <n-form-item v-if="locale === '1'" label="净重">
                <n-input-number
                  v-model:value="netWeight"
                  type="text"
                  :show-button="false"
                  placeholder="请输入净重"
                  :style="{ width: witeFormItemWidth }"
                >
                  <template #suffix>kg</template>
                </n-input-number>
              </n-form-item>
              <n-form-item v-if="locale === '1'" label="皮重">
                <n-input-number
                  v-model:value="semiFinProduct.axleLoad"
                  type="text"
                  placeholder="请输入皮重"
                  :show-button="false"
                  :style="{ width: witeFormItemWidth }"
                  @update:value="changeTareWeight"
                >
                  <template #suffix>kg</template>
                </n-input-number>
              </n-form-item>
              <n-form-item v-if="locale === '0'" label="皮重">
                <n-input-number
                  v-model:value="tareWeight"
                  type="text"
                  placeholder="请输入皮重"
                  :show-button="false"
                  :style="{ width: witeFormItemWidth }"
                  @update:value="changeYCTareWeight"
                >
                  <template #suffix>kg</template>
                </n-input-number>
              </n-form-item>
              <n-form-item v-if="locale === '1'" label="每箱盘数">
                <n-input-number
                  v-model:value="semiFinProduct.boxPerNumber"
                  type="text"
                  placeholder="每箱盘数"
                  :style="{ width: witeFormItemWidth }"
                />
              </n-form-item>
            </n-form>
          </div>
          <div class="pub-box">
            <n-space>
              <n-button v-show="!isAutoSave" type="info" size="small" @click="manualSave">手动称重</n-button>
              <n-button type="info" :class="[isAutoSave ? 'open' : '']" size="small" @click="openOrCloneAutoSave">
                自动称重
                <span style="color: yellow">{{ isAutoSave ? '(已开启)' : '(已关闭)' }}</span>
              </n-button>
              <n-button type="info" :class="[isAutoSave ? 'open' : '']" size="small" @click="verificationStatus = true">
                <icon-tdesign-setting-1 class="mr-1px text-15px" />
                重量检验
              </n-button>
            </n-space>
          </div>
        </div>
      </div>
    </n-card>
    <rawMaterial v-if="lookRecordShow" ref="rawMaterialRef" @reload-record="recordReload"></rawMaterial>
    <my-dialog
      :show="verificationStatus"
      width="450px"
      :title="'重量校验'"
      :show-cancel="false"
      @cancel="verificationStatus = false"
      @submit="verificationStatus = false"
    >
      <template #content>
        <div>
          <n-form label-placement="left" label-align="left" :label-width="120">
            <n-form-item label="最小重量" path="minWeight">
              <n-input-number
                v-model:value="minWeight"
                type="text"
                placeholder="请输入最小重量"
                :show-button="false"
                :style="{ width: witeFormItemWidth }"
              >
                <template #suffix>kg</template>
              </n-input-number>
            </n-form-item>
            <n-form-item label="最大重量" path="maxWeight">
              <n-input-number
                v-model:value="maxWeight"
                type="text"
                placeholder="请输入最大重量"
                :show-button="false"
                :style="{ width: witeFormItemWidth }"
              >
                <template #suffix>kg</template>
              </n-input-number>
            </n-form-item>
          </n-form>
        </div>
      </template>
    </my-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted, watch, onUnmounted, nextTick, onBeforeMount, onActivated, onDeactivated } from 'vue';
import { useMessage, useDialog } from 'naive-ui';
import { hiprint } from 'vue-plugin-hiprint';
import { useAuthStore } from '@/store';
import { createRequiredFormRule } from '@/utils';
import { getWeighMachineSelectAll } from '@/service/api/md/workbench/weighMachine';
import { getMaterialList } from '@/service/api/md/itemlist/index';
import { getIngredientsManufacturerList } from '@/service/api/md/ingredients/manufacturer';
import { getUserList } from '@/service/api/sale/userManage';
import { list } from '@/service/api/wms/warehouseSet';
import { getAllClassifyList } from '@/service/api/md/classify';
import { getIngredientsTradersList } from '@/service/api/md/ingredients/traders';
import { getIngredientsProductNameList } from '@/service/api/md/ingredients/productName';
import { getStorageManageList } from '@/service/api/wms/stockControl';
import { addIngredientsLog } from '~/src/service/api/wms/rawMaterial';
import {
  addRecording,
  getRecoordList,
  getYesterday,
  getBoxSerial,
  getBoxSerialToUser
} from '~/src/service/api/storage/storageRecording/index';
import { addBoxReacprding } from '~/src/service/api/storage/storageBox/index';
import { selectMaxValues, getSpecList, getLocalSpecList } from '~/src/service/api/storage/weightAdmin/index';
import { useWarning } from '~/src/hooks';
import { getPrintTemplateOne } from '~/src/service/api/hiprint/index';
import rawMaterial from '~/src/views/storage/weightAdmin/component/index.vue';

const dateFormatters = ref(
  String(
    String(new Date().getFullYear()).substring(2, 4) +
      (new Date().getMonth() + 1 < 10 ? `0${new Date().getMonth() + 1}` : new Date().getMonth() + 1)
  ) + (new Date().getDate() < 10 ? `0${new Date().getDate()}` : new Date().getDate())
);
const everyDayNum = ref(1);
const dialog = useDialog();
const warning = useWarning('提示', '存在未装箱数据请先删除数据后重试', false);
const message = useMessage();
const verificationStatus = ref(false);
const formItemWidth = ref('280px');
const formModel = ref(null);
const portFormModel = ref(null);
const locale = ref('0');
const equipmentList = ref([]);
const equipmentListData = ref([]);
const specificationList = ref([]);
const manufacturerList = ref([]);
const productList = ref([]);
const traderList = ref([]);
const materialsList = ref([]);
const specList = ref([]);
const itemCodeData = ref([]);
const itemCodeOptions = ref([]);
const batchNumberData = ref([]);
const batchNumberOptions = ref([{ label: 'test', value: '1' }]);
const warehouseOptions = ref([]);
const materialData = ref({});
const saleUserList = ref([]);
const lookRecordShow = ref(false);
// const printerList = ref([]);
const boxhiprintTemplate = ref(null);
const axishiprintTemplate = ref(null);
const hiprintMaterialsTemplate = ref();
const isJoin = ref(false);
const witeFormItemWidth = ref('260px');
// 轴模板
const axisTemp = ref(null);
const BoxTemp = ref(null);
const MaterialsTemp = ref(null);
const stableData = ref([]);
const scanCode = ref('');
const deviceMachineCode = ref('');
const inputValue = ref();

const boxPrintTemplateDataList = ref([]);
const axisPrintTemplateDataList = ref([]);
const MaterialsPrintTemplateDataList = ref([]);
const netWeight = ref(0);
const grossWeight = ref(0);
const tareWeight = ref(0);
// 子组件的ref
const rawMaterialRef = ref(null);
function recordReload() {
  getRecoordData();
  getSumWeight();
  getMaxValues();
}
// 累计净重
const sumWeight = ref(0);
const authStore = useAuthStore();
// 最大最小重量
const minWeight = ref(0);
const maxWeight = ref(100);

const semiFinProductRef = ref(null);

const addForm = ref({
  ingredientsName: '',
  manufacturerName: '',
  partNumber: '',
  lotNumber: '',
  productName: '',
  itemSpecification: '',
  itemId: null,
  status: '0',
  netWeight: 0,
  grossWeight: 0,
  tareWeight: 0,
  measureId: 18,
  measureName: 'kg',
  codeSn: '',
  manufacturerId: null,
  manufacturerCode: '',
  ingredientsId: null,
  productId: null,
  attr2: 0
});

const selectPortForm = ref({
  equipmentId: null,
  machineType: null,
  printAxisTemplate: null,
  printBoxTemplate: null,
  printMaterialsTemplate: null
});
const semiFinProduct = ref({
  boxPerNumber: 1,
  axleLoad: 0,
  productName: null,
  materialNumber: '',
  batchNumber2: null,
  batchNumber: null,
  itemCode: null,
  wmsSpec: null,
  artt3: 1, // 盘数
  artt2: '0', // 装箱状态
  artt1: null, // 仓库
  artt4: 1, // 发货数量
  boxNumber: 1, // 箱数
  productUniqueCode: '', // 二维码编号
  wmsUnit: 'kg',
  trader: null,
  materialManufacturer: null,
  materialCode: '',
  installationStatus: 'OUT'
});

const rules = {
  ingredientsId: createRequiredFormRule('请选择贸易商'),
  manufacturerId: createRequiredFormRule('请选择材料厂家'),
  partNumber: createRequiredFormRule('请填写料号'),
  lotNumber: createRequiredFormRule('请填写批号'),
  itemId: createRequiredFormRule('请选择规格'),
  productId: createRequiredFormRule('请选择品名')
};
const semiFinProductRules = {
  artt1: createRequiredFormRule('请选择仓库'),
  trader: createRequiredFormRule('请选择客户简称'),
  materialManufacturer: createRequiredFormRule('请选择材料厂家'),
  materialNumber: createRequiredFormRule('请填写料号'),
  batchNumber2: createRequiredFormRule('请填写批号'),
  batchNumber: createRequiredFormRule('请填写批次号'),
  wmsSpec: createRequiredFormRule('请选择规格'),
  productName: createRequiredFormRule('请选择品名'),
  axleLoad: createRequiredFormRule('请输入皮重'),
  itemCode: createRequiredFormRule('请选择物品编码')
};

const portRules = {
  minWeight: createRequiredFormRule('请输入最小重量'),
  maxWeight: createRequiredFormRule('请输入最大重量')
};

const baudRate = ref(9600);
const dataBits = ref(8);
const stopBits = ref(1);
const parity = ref('none');
const bufferSize = ref(1024);
const flowControl = ref('none');
const isOpen = ref(false);
const isOpening = ref(false);
const boxlivePrint = ref(null);
const axislivePrint = ref(null);
const MaterialslivePrint = ref(null);
const statusMessage = ref('未选择串口');
const latestData = ref(0); // 最新接收到的数据
const savedData = ref(''); // 保存稳定数据
const reader = ref(null); // 串口数据读取器
const port = ref(null); // 串口端口
const parsedData = ref([]);

// 原料入库
const sleep = () =>
  new Promise(resolve => {
    addIngredientsLog(addForm.value).then(res => {
      resolve(res);
    });
  });
// 称重记录入库
const sleep2 = () =>
  new Promise(resolve => {
    addRecording(semiFinProduct.value).then(async res => {
      resolve(res);
    });
  });
// 箱入库
const sleep3 = axleLoad =>
  new Promise(resolve => {
    addBoxReacprding({ ...semiFinProduct.value, axleLoad }).then(res => {
      resolve(res);
    });
  });
// 将 Uint8Array 转换为 ASCII 字符串
const uint8ArrayToAscii = data => {
  let asciiStr = '';

  data.forEach(code => {
    if (code > 31 && code < 127) {
      // 只转换可打印字符
      asciiStr += String.fromCharCode(code);
    }
  });
  return asciiStr;
};
// 解析 ASCII 数据
const parseTuoliduoAsciiData = asciiData => {
  // 提取设备标识符 "DP"
  const deviceId = asciiData.slice(0, 2);

  // 提取状态字和重量数据
  const statusCode = asciiData[2]; // 状态字节
  const weightData = asciiData.slice(3); // 提取重量数据
  if (!statusCode) {
    return false;
  }
  // 解析状态字节
  const statusBits = statusCode.charCodeAt(0).toString(2).padStart(8, '0'); // 转换为二进制字符串

  // 解析重量数据
  let weight = null;
  switch (deviceMachineCode.value) {
    case 'A':
      weight = parseFloat(weightData).toFixed(2) / 10;
      break;
    case 'B':
      weight = parseFloat(weightData).toFixed(2) / 1000;
      break;
    case 'C':
      weight = parseFloat(weightData).toFixed(2);
      break;
    default:
      break;
  }
  // locale.value === '0' ? parseFloat(weightData).toFixed(2) / 10 : parseFloat(weightData).toFixed(2) / 1000;
  // 根据状态字节解析附加信息
  const isGrossWeight = statusBits[7] === '0'; // 最后一位0：毛重，1：净重
  const isNegative = statusBits[6] === '1'; // 倒数第二位1：负数，0：正数
  const isOutOfRange = statusBits[5] === '1'; // 倒数第三位1：超出范围，0：未超出范围
  const isStable = statusBits[4] === '0'; // 倒数第四位0：称重稳定，1：动态
  const isKg = statusBits[3] === '1'; // 第四位1：单位为KG，0：lb

  return {
    deviceId,
    weight,
    isGrossWeight,
    isNegative,
    isOutOfRange,
    isStable,
    isKg
  };
};

// 解析称重数据
const parseScaleData = data => {
  // 确保数据格式正确
  if (!data.startsWith('=') || data.length !== 8) {
    return { value: null };
  }

  // 提取重量数据
  const weightData = data.slice(1, 7); // 提取六位数据
  const sign = data[7]; // 提取符号位
  // 将字符串转换为数组，反转数组并转换回字符串
  const reversedWeightData = weightData.split('').reverse().join('');
  const weight = parseFloat(reversedWeightData);

  const finalWeight = sign === '-' ? -weight : weight; // 根据符号位调整重量

  return {
    weight: finalWeight,
    unit: 'kg', // 假设单位为公斤
    sign: sign === '-' ? '负' : '正'
  };
};

const weightDataLsit = ref([]);
const isSaveStatus = ref(false);
const isAutoSave = ref(false);
const isConfrim = ref(true);
const hour = ref(0);
// 确认入库
const num = ref(1);
// 读取数据
const readData = async ports => {
  reader.value = ports.readable.getReader();
  let done = null;
  function read() {
    reader.value.read().then(res => {
      const value = res.value;
      done = res.done;
      if (done) {
        reader.value.releaseLock(); // 确保在结束时释放锁
        return;
      }
      // value 是 Uint8Array 类型
      const data = new Uint8Array(value);
      // equipment.value = equipmentListData.value.find(item => item.id === equipmentId);
      // 处理数据
      const asciiData = uint8ArrayToAscii(data);
      // const obj = getParseData(equipment?.machineType);
      const obj = getParseData(selectPortForm.value.machineType);
      if (obj.func !== null) {
        parsedData.value = obj.func(asciiData);
      }
      if (!parsedData.value) {
        read();
        return;
      }
      // console.log('parsedData.value ==> ',parsedData.value );

      // console.log('parsedData.value.weight ==> ', parsedData.value.weight);

      if (parsedData.value.weight || parsedData.value.weight === 0) {
        if (latestData.value === parsedData.value.weight.toString() && !isSaveStatus.value) {
          if (stableData.value.includes(latestData.value)) {
            stableData.value.push(latestData.value);
          } else {
            stableData.value = [];
          }
        }
        latestData.value = parsedData.value.weight;
        grossWeight.value = parseFloat(latestData.value);
        weightDataLsit.value.push({ value: latestData.value, date: new Date().valueOf() });
        weightDataLsit.value = weightDataLsit.value.filter(it => new Date().valueOf() - it.date < 1000);
        if (locale.value === '1') {
          netWeight.value = parseFloat(latestData.value) - semiFinProduct.value.axleLoad;
        } else {
          netWeight.value = grossWeight.value - tareWeight.value;
        }
      }
      read();
    });
  }
  try {
    read();
  } catch (error) {
    // console.error('Error:', error);
  }
};

const canSaveData = parsedDatas => {
  // 判断是否为有效数据
  if (parsedDatas.isOutOfRange) {
    return false; // 如果数据超出范围，则不保存
  }

  if (!parsedDatas.isStable) {
    return false; // 如果数据不稳定，则不保存
  }
  // 其他判断条件
  // 可以根据需要添加更多条件，例如:
  if (!parsedDatas.isNegative) {
    return false; // 负数是否需要保存
  }

  // 如果所有条件都符合，则允许保存数据
  return true;
};

// 保存当前稳定数据
const saveStableData = () => {
  if (canSaveData(parsedData.value)) {
    savedData.value = parsedData.value.weight;
    sessionStorage.setItem('stableData', parsedData.value.weight);
    // statusMessage.value = `数据已保存: ${savedData.value}`;
  } else {
    // statusMessage.value = '数据不符合保存条件';
  }
};

function debounce(func, delay) {
  let timeoutId;
  return function setNewTimeout(...args) {
    // 清除之前的定时器
    if (timeoutId) {
      clearTimeout(timeoutId);
    }

    // 设置新的定时器
    timeoutId = setTimeout(() => {
      func.apply(args);
    }, delay);
  };
}

const save = debounce(saveStableData, 2000);

const watchStartOrStop = ref(false);
const stabilityTimeOut = ref(null);
watch(
  () => latestData.value,
  () => {
    if (watchStartOrStop.value) {
      save();
    }
  }
);

let scanTimeout = null;
// 监听扫码
watch(
  () => scanCode.value,
  async newVal => {
    if (newVal) {
      if (scanTimeout) {
        clearTimeout(scanTimeout);
      }
      // 设置一个新的延时器;
      scanTimeout = setTimeout(() => {
        if (!scanCode.value) return;
        semiFinProduct.value.productUniqueCode = '';
        getStorageManageList({ itemIdentifier: scanCode.value }).then(res => {
          if (res.code === 200 && res.rows.length > 0) {
            semiFinProduct.value.trader = res.rows[0].ingredientsName;
            semiFinProduct.value.artt1 = res.rows[0].warehouseName;
            semiFinProduct.value.productUniqueCode = scanCode.value;
            semiFinProduct.value.productName = res.rows[0].productTypeName;
            semiFinProduct.value.wmsSpec = res.rows[0].specification;
            semiFinProduct.value.itemCode = res.rows[0].itemCode;
            semiFinProduct.value.batchNumber2 = res.rows[0].productBatchCode;
            semiFinProduct.value.materialCode = res.rows[0].materialCode;
            getSumWeight();
          } else {
            scanCode.value = '';
            message.error('二维码编号不存在，请扫新的二维码');
          }
        });
      }, 300);
    }
  }
);
watch(
  () => grossWeight.value,
  newVal => {
    if (isAutoSave.value) {
      if (grossWeight.value <= 0 || grossWeight.value === 'null') {
        if (stabilityTimeOut.value) clearTimeout(stabilityTimeOut.value);
        stabilityTimeOut.value = setTimeout(() => {
          if (newVal === grossWeight.value) {
            const isWeightSaveStatus = JSON.parse(JSON.stringify(weightDataLsit.value)).filter(item => {
              return Number(item.value) <= 0 || item.value === 'null';
            });
            if (isWeightSaveStatus.length > 0) {
              isSaveStatus.value = true;
            }
          }
        }, 1000);
      } else {
        if (stabilityTimeOut.value) clearTimeout(stabilityTimeOut.value);
        stabilityTimeOut.value = setTimeout(() => {
          if (newVal === grossWeight.value) {
            const isWeightSaveStatus = JSON.parse(JSON.stringify(weightDataLsit.value)).filter(
              item => item.value > '0' && item.value === newVal
            );
            const status = isWeightSaveStatus.every(n => n.value === newVal);
            if (status && isSaveStatus.value) {
              if (locale.value === '1') {
                semiFinProductRef.value.validate(async errors => {
                  if (!errors) {
                    saveListData();
                  }
                });
              } else {
                formModel.value.validate(async errors => {
                  if (!errors) {
                    saveListData();
                  }
                });
              }
            }
          }
        }, 1000);
      }
    }
    if (grossWeight.value && locale.value === '1') {
      netWeight.value = grossWeight.value - semiFinProduct.value.axleLoad;
    }
    if (grossWeight.value && locale.value === '0') {
      netWeight.value = grossWeight.value - tareWeight.value;
    }
  }
);
// 开启自动保存
function openOrCloneAutoSave() {
  if (locale.value === '0') {
    if (selectPortForm.value.printMaterialsTemplate && !MaterialsTemp.value) {
      message.warning('请配置原料打印模板内容');
      return;
    }
    if (!formModel.value) return;
    // formModel.value.validate(async errors => {
    //   if (!errors) {
    isSaveStatus.value = !isSaveStatus.value;
    isAutoSave.value = !isAutoSave.value;
    //   }
    // });
  } else {
    if (selectPortForm.value.printAxisTemplate && !axisTemp.value) {
      message.warning('请配置轴打印模板内容');
      return;
    }
    if (selectPortForm.value.printAxisTemplate && !axislivePrint.value) {
      message.warning('请配置轴打印机');
      return;
    }
    if (selectPortForm.value.printBoxTemplate && !BoxTemp.value) {
      message.warning('请配置箱打印模板内容');
      return;
    }
    if (selectPortForm.value.printBoxTemplate && !boxlivePrint.value) {
      message.warning('请配置箱打印机');
      return;
    }
    if (selectPortForm.value.printMaterialsTemplate && !MaterialsTemp.value) {
      message.warning('请配置原料打印模板内容');
      return;
    }
    if (selectPortForm.value.printMaterialsTemplate && !MaterialslivePrint.value) {
      message.warning('请配置原料打印机');
      return;
    }
    if (!semiFinProductRef.value) return;
    // semiFinProductRef.value.validate(async errors => {
    //   if (!errors) {
    isSaveStatus.value = !isSaveStatus.value;
    isAutoSave.value = !isAutoSave.value;
    // }
    // });
  }
}
// 可以监听
watch(locale, async () => {
  reset();
  if (locale.value === '1') {
    await nextTick(() => {
      inputValue.value.focus();
    });
  }
});
watch(
  () => semiFinProduct.value.boxPerNumber,
  () => {
    getRecoordData();
  }
);
// 查询产品流水号
watch(
  () => [
    semiFinProduct.value.productName,
    semiFinProduct.value.wmsSpec,
    semiFinProduct.value.itemCode,
    semiFinProduct.value.batchNumber2,
    semiFinProduct.value.trader
  ],
  (newVal, oldVal) => {
    if (
      newVal[0] !== oldVal[0] ||
      newVal[1] !== oldVal[1] ||
      newVal[2] !== oldVal[2] ||
      newVal[3] !== oldVal[3] ||
      newVal[4] !== oldVal[4]
    ) {
      if (!newVal.includes(null)) {
        getBoxSerial({ ...semiFinProduct.value, installationStatus: 'OUT' }).then(res => {
          if (res.data) {
            sessionStorage.setItem('boxSerialNum', parseInt(res.data.boxSerialNumber, 10) + 1);
          } else {
            sessionStorage.setItem('boxSerialNum', 1);
          }
        });
        getSumWeight();
      }
    }
  }
);

// 获取累计净重
function getSumWeight() {
  getBoxSerialToUser({ ...semiFinProduct.value, createBy: authStore.userInfo.userName }).then(rs => {
    if (rs.code === 200) {
      sumWeight.value = rs.data.totalNetWeight;
    }
  });
}
watch(
  () => hour.value,
  newVal => {
    if (parseInt(newVal, 10) !== new Date().getDate()) {
      getYesterDayData();
      sessionStorage.setItem('batchNum', 1);
      sessionStorage.setItem('boxSerialNum', 1);
      sessionStorage.setItem('axisSerialNum', 1);
    }
  }
);

// 打开或关闭串口
const openOrClosePort = async val => {
  if (!port.value) {
    statusMessage.value = '请先选择串口';
    message.warning(statusMessage.value);
  }

  if (!val) {
    message.warning('窗口配置未选择完全 ，请继续选择设备');
    return;
  }
  isOpening.value = true;
  try {
    // 释放读取器的锁
    if (reader.value) {
      try {
        await reader.value.cancel();
      } catch (cancelError) {
        await reader.value.cancel();
      } finally {
        reader.value.releaseLock();
        reader.value = null;
      }
      // 关闭串口
      try {
        port.value
          .close()
          .then(() => {
            isOpen.value = false;
            statusMessage.value = '串口已关闭';
            selectPortForm.value.equipmentId = null;
            isJoin.value = false;
          })
          .catch(closeError => {
            statusMessage.value = `串口关闭失败: ${closeError.message}`;
            isJoin.value = false;
          });
      } catch (closeError) {
        isJoin.value = false;
      }
    } else {
      // 打开串口
      try {
        port.value
          .open({
            baudRate: baudRate.value,
            dataBits: dataBits.value,
            stopBits: stopBits.value,
            parity: parity.value,
            bufferSize: bufferSize.value,
            flowControl: flowControl.value
          })
          .then(async () => {
            isOpen.value = true;
            statusMessage.value = '串口已打开';
            isOpening.value = false;
            isJoin.value = true;
            // 启动读取数据的操作
            await readData(port.value);
          });
      } catch (openError) {
        statusMessage.value = `串口打开失败: ${openError.message}`;
      }
    }
  } catch (error) {
    // eslint-disable-next-line no-console
    console.log(error, '连接异常');
  } finally {
    isOpening.value = false;
  }
};
// 选择设备
async function handleUpdateValueDevice(val, options) {
  if (val) {
    deviceMachineCode.value = options.machineCode;
    isOpening.value = false;
    // 选择串口
    try {
      port.value = null;
      if (!navigator?.serial) return;
      const ports = await navigator?.serial.getPorts();
      if (ports.length > 0) {
        ports.forEach(item => {
          if (item.getInfo().usbProductId) {
            port.value = item;
            statusMessage.value = `已选择串口: ${port.value.getInfo().usbProductId}`;
            selectPortForm.value.equipmentId = val;
            selectPortForm.value.machineType = options.machineType;
            openOrClosePort(val);
          }
        });
      } else {
        port.value = await navigator.serial.requestPort();
        if (port.value) {
          statusMessage.value = `已选择串口: ${port.value.getInfo().usbProductId}`;
          selectPortForm.value.equipmentId = val;
          selectPortForm.value.machineType = options.machineType;
          openOrClosePort(val);
        }
      }
    } catch (error) {
      // eslint-disable-next-line no-console
      console.log(error, '串口连接错误');
      statusMessage.value = '未选择串口';
    }
  } else {
    openOrClosePort(val);
  }
}
// 固定编号格式
function dateFormatter(date) {
  const year = date.getFullYear().toString().substring(2, date.getFullYear().length);
  const month = date.getMonth() + 1 < 10 ? `0${date.getMonth() + 1}` : date.getMonth() + 1;
  const day = date.getDate() < 10 ? `0${date.getDate()}` : date.getDate();
  return year + month + day;
}
// 自动保存
const autoSavePromise = ref(null);
function saveListData() {
  if (!window.hiwebSocket.printerList) {
    window.$message.error('客户端未连接,无法直接打印');
    return;
  }
  if (!window.hiwebSocket.opened && window.hiwebSocket.printerList.length) {
    window.$message.error('客户端未连接,无法直接打印');
    return;
  }
  if (!isOpen.value) {
    window.$message.error('串口未连接，无法保存');
    return;
  }
  autoSavePromise.value = new Promise(resolve => {
    if (locale.value === '0') {
      addForm.value.netWeight = netWeight.value;
      addForm.value.tareWeight = tareWeight.value;
      addForm.value.grossWeight = grossWeight.value;
      sleep().then(res => {
        if (res.code === 200) {
          message.success('原料入库成功');

          getLocalSpecList(addForm.value.manufacturerName).then(r => {
            if (r.data.length > 0) {
              const lotNumber = r.data[0].lotNumber;
              everyDayNum.value = Number(
                lotNumber.substring(r.data[0].lotNumber.length - 3, r.data[0].lotNumber.length)
              );
              everyDayNum.value++;
              dateFormatters.value =
                String(
                  String(new Date().getFullYear()).substring(2, 4) +
                    (new Date().getMonth() + 1 < 10 ? `0${new Date().getMonth() + 1}` : new Date().getMonth() + 1)
                ) + (new Date().getDate() < 10 ? `0${new Date().getDate()}` : new Date().getDate());
              addForm.value.partNumber = `${dateFormatters.value + addForm.value.manufacturerCode}L${
                // eslint-disable-next-line no-nested-ternary
                everyDayNum.value < 10
                  ? `00${everyDayNum.value}`
                  : everyDayNum.value < 100
                  ? `0${everyDayNum.value}`
                  : everyDayNum.value
              }`;
              addForm.value.lotNumber = `${dateFormatters.value + addForm.value.manufacturerCode}P${
                // eslint-disable-next-line no-nested-ternary
                everyDayNum.value < 10
                  ? `00${everyDayNum.value}`
                  : everyDayNum.value < 100
                  ? `0${everyDayNum.value}`
                  : everyDayNum.value
              }`;
              addForm.value.codeSn = res.data.codeSn;
              addForm.value.createTime = res.data.createTime;
              isSaveStatus.value = false;
              initPrint(addForm.value, 2);
            } else {
              addForm.value.partNumber = `${dateFormatters.value + addForm.value.manufacturerCode}L001`;
              addForm.value.lotNumber = `${dateFormatters.value + addForm.value.manufacturerCode}P001`;
            }
          });
        }
        resolve(res);
      });
    } else {
      if (!isConfrim.value) {
        lookRecordShow.value = true;
        message.error('存在未装箱数据，删除后重试!');
        return;
      }
      if (grossWeight.value <= minWeight.value && grossWeight.value >= maxWeight.value) {
        message.error('保存的数据不符合范围，请重新保存数据!');
        return;
      }
      if (!scanCode.value) {
        lookRecordShow.value = true;
        message.error('未扫码，请重新扫码！！！');
        return;
      }
      hour.value = new Date().getDate();
      let batchNum = parseInt(sessionStorage.getItem('batchNum'), 10);
      let boxSerialNum = parseInt(sessionStorage.getItem('boxSerialNum'), 10);
      let axisSerialNum = parseInt(sessionStorage.getItem('axisSerialNum'), 10);
      // 箱编号
      const boxStrCode = `XM${dateFormatter(new Date())}0000`;
      // 轴流水号
      const axisSerialStrCode = `Z${dateFormatter(new Date())}0000`;
      semiFinProduct.value.boxCode =
        boxStrCode.substring(0, boxStrCode.length - batchNum.toString().length) + batchNum.toString();
      semiFinProduct.value.boxSerialNumber =
        '0000'.substring(0, '0000'.length - boxSerialNum.toString().length) + boxSerialNum.toString();
      semiFinProduct.value.axisSerialNumber =
        axisSerialStrCode.substring(0, axisSerialStrCode.length - axisSerialNum.toString().length) +
        axisSerialNum.toString();
      semiFinProduct.value.netWeight = parseFloat(parseFloat(netWeight.value).toFixed(3)); // 净重
      semiFinProduct.value.grossWeight = parseFloat(parseFloat(grossWeight.value).toFixed(3)); // 毛重
      semiFinProduct.value.artt2 = '0';
      // semiFinProduct.value.tareWeight = tareWeight.value;
      sleep2().then(async res => {
        if (res.code === 200) {
          scanCode.value = '';
          await nextTick();
          inputValue.value.focus();
          message.success('成品入库成功');
          // scanCode.value = '';
          // inputValue.value.focus();
          axisSerialNum++;
          sessionStorage.setItem('axisSerialNum', axisSerialNum);
          isSaveStatus.value = false;
          lookRecordShow.value = true;
          res.data.snidCode = `https://dms.idfactory.cn/whois/ui/searchResult?identity=88.389.00054/${res.data.boxCode}`;
          initPrint(res.data, 0);
          getSumWeight();
          if (rawMaterialRef.value) rawMaterialRef.value.init();
          if (semiFinProduct.value.boxPerNumber === num.value || semiFinProduct.value.boxPerNumber === 1) {
            getRecoordList({
              boxCode: semiFinProduct.value.boxCode
            }).then(resl => {
              if (resl.rows.length === semiFinProduct.value.boxPerNumber) {
                const totalNetWeight = parseFloat(
                  resl.rows.reduce((prevValue, row) => prevValue + row.netWeight, 0).toFixed(3)
                );
                const totalGrossWeight = parseFloat(
                  resl.rows.reduce((prevValue, row) => prevValue + row.grossWeight, 0).toFixed(3)
                );
                const axleLoad = parseFloat(
                  resl.rows.reduce((prevValue, row) => prevValue + row.axleLoad, 0).toFixed(3)
                );
                semiFinProduct.value.netWeight = totalNetWeight;
                semiFinProduct.value.grossWeight = totalGrossWeight;
                semiFinProduct.value.materialNumber = semiFinProduct.value.materialManufacturer;
                semiFinProduct.value.artt2 = '1';
                sleep3(axleLoad).then(rs => {
                  if (rs.code === 200) {
                    num.value = 1;
                    isSaveStatus.value = false;
                    message.success('箱入库成功');
                    batchNum++;
                    boxSerialNum++;
                    sessionStorage.setItem('batchNum', batchNum);
                    sessionStorage.setItem('boxSerialNum', boxSerialNum);
                    rs.data.snidCode = `https://dms.idfactory.cn/whois/ui/searchResult?identity=88.389.00054/${rs.data.boxCode}`;
                    initPrint(rs.data, 1);
                  }
                  resolve(rs);
                });
              }
            });
          } else {
            num.value++;
          }
        }
        resolve(res);
      });
    }
  });
}
// 手动称重
async function handleDeleteItemType() {
  if (!window.hiwebSocket.printerList) {
    window.$message.error('客户端未连接,无法直接打印');
    return;
  }
  if (!window.hiwebSocket.opened && window.hiwebSocket.printerList.length) {
    window.$message.error('客户端未连接,无法直接打印');
    return;
  }
  if ((grossWeight.value < minWeight.value || grossWeight.value > maxWeight.value) && locale.value !== '0') {
    message.error('保存的数据不符合范围，请重新保存数据!');
    return;
  }
  // if (!isOpen.value) {
  //   window.$message.error('串口未连接，无法保存');
  //   return;
  // }
  if (!isConfrim.value) {
    lookRecordShow.value = true;
    message.error('存在未装箱数据，删除后重试!');
    return;
  }
  // if (!scanCode.value) {
  //   lookRecordShow.value = true;
  //   message.error('未扫码，请重新扫码！！！');
  //   return;
  // }
  scanCode.value = '';
  inputValue.value.focus();
  const d = dialog.warning({
    title: '入库',
    content: `当前重量为${parseFloat(parseFloat(netWeight.value).toFixed(3))}kg,确定要入库吗?`,
    positiveText: '确定',
    negativeText: '取消',
    onPositiveClick: () => {
      d.loading = true;
      return new Promise(resolve => {
        if (locale.value === '0') {
          addForm.value.netWeight = netWeight.value;
          addForm.value.tareWeight = tareWeight.value;
          addForm.value.grossWeight = grossWeight.value;
          sleep().then(res => {
            if (res.code === 200) {
              message.success('原料入库成功');

              getLocalSpecList(addForm.value.manufacturerName).then(r => {
                if (r.data.length > 0) {
                  const lotNumber = r.data[0].lotNumber;
                  everyDayNum.value = Number(
                    lotNumber.substring(r.data[0].lotNumber.length - 3, r.data[0].lotNumber.length)
                  );
                  everyDayNum.value++;
                  dateFormatters.value =
                    String(
                      String(new Date().getFullYear()).substring(2, 4) +
                        (new Date().getMonth() + 1 < 10 ? `0${new Date().getMonth() + 1}` : new Date().getMonth() + 1)
                    ) + (new Date().getDate() < 10 ? `0${new Date().getDate()}` : new Date().getDate());
                  addForm.value.partNumber = `${dateFormatters.value + addForm.value.manufacturerCode}L${
                    // eslint-disable-next-line no-nested-ternary
                    everyDayNum.value < 10
                      ? `00${everyDayNum.value}`
                      : everyDayNum.value < 100
                      ? `0${everyDayNum.value}`
                      : everyDayNum.value
                  }`;
                  addForm.value.lotNumber = `${dateFormatters.value + addForm.value.manufacturerCode}P${
                    // eslint-disable-next-line no-nested-ternary
                    everyDayNum.value < 10
                      ? `00${everyDayNum.value}`
                      : everyDayNum.value < 100
                      ? `0${everyDayNum.value}`
                      : everyDayNum.value
                  }`;
                  addForm.value.codeSn = res.data.codeSn;
                  addForm.value.createTime = res.data.createTime;
                  isSaveStatus.value = false;
                  initPrint(addForm.value, 2);
                } else {
                  addForm.value.partNumber = `${dateFormatters.value + addForm.value.manufacturerCode}L001`;
                  addForm.value.lotNumber = `${dateFormatters.value + addForm.value.manufacturerCode}P001`;
                }
              });
            }
            resolve(res);
          });
        } else {
          hour.value = new Date().getDate();
          let batchNum = parseInt(sessionStorage.getItem('batchNum'), 10);
          let boxSerialNum = parseInt(sessionStorage.getItem('boxSerialNum'), 10);
          let axisSerialNum = parseInt(sessionStorage.getItem('axisSerialNum'), 10);
          // 箱编号
          const boxStrCode = `XM${dateFormatter(new Date())}0000`;
          // 轴流水号
          const axisSerialStrCode = `Z${dateFormatter(new Date())}0000`;
          semiFinProduct.value.boxCode =
            boxStrCode.substring(0, boxStrCode.length - batchNum.toString().length) + batchNum.toString();
          semiFinProduct.value.boxSerialNumber =
            '0000'.substring(0, '0000'.length - boxSerialNum.toString().length) + boxSerialNum.toString();
          semiFinProduct.value.axisSerialNumber =
            axisSerialStrCode.substring(0, axisSerialStrCode.length - axisSerialNum.toString().length) +
            axisSerialNum.toString();
          semiFinProduct.value.netWeight = parseFloat(parseFloat(netWeight.value).toFixed(3)); // 净重
          semiFinProduct.value.grossWeight = parseFloat(parseFloat(grossWeight.value).toFixed(3)); // 毛重
          semiFinProduct.value.artt2 = '0';
          // semiFinProduct.value.tareWeight = tareWeight.value;
          sleep2().then(async res => {
            if (res.code === 200) {
              message.success('成品入库成功');
              axisSerialNum++;
              lookRecordShow.value = true;
              sessionStorage.setItem('axisSerialNum', axisSerialNum);
              res.data.snidCode = `https://dms.idfactory.cn/whois/ui/searchResult?identity=88.389.00054/${res.data.boxCode}`;
              initPrint(res.data, 0);
              getSumWeight();
              if (rawMaterialRef.value) rawMaterialRef.value.init();
              if (semiFinProduct.value.boxPerNumber === num.value || semiFinProduct.value.boxPerNumber === 1) {
                getRecoordList({
                  boxCode: semiFinProduct.value.boxCode
                }).then(resl => {
                  if (resl.rows.length === semiFinProduct.value.boxPerNumber) {
                    const totalNetWeight = parseFloat(
                      resl.rows.reduce((prevValue, row) => prevValue + row.netWeight, 0).toFixed(3)
                    );
                    const totalGrossWeight = parseFloat(
                      resl.rows.reduce((prevValue, row) => prevValue + row.grossWeight, 0).toFixed(3)
                    );
                    const axleLoad = parseFloat(
                      resl.rows.reduce((prevValue, row) => prevValue + row.axleLoad, 0).toFixed(3)
                    );
                    semiFinProduct.value.netWeight = totalNetWeight;
                    semiFinProduct.value.grossWeight = totalGrossWeight;
                    semiFinProduct.value.materialNumber = semiFinProduct.value.materialManufacturer;
                    semiFinProduct.value.artt2 = '1';
                    sleep3(axleLoad).then(rs => {
                      if (rs.code === 200) {
                        num.value = 1;
                        message.success('箱入库成功');
                        batchNum++;
                        boxSerialNum++;
                        sessionStorage.setItem('batchNum', batchNum);
                        sessionStorage.setItem('boxSerialNum', boxSerialNum);
                        rs.data.snidCode = `https://dms.idfactory.cn/whois/ui/searchResult?identity=88.389.00054/${rs.data.boxCode}`;
                        initPrint(rs.data, 1);
                      }
                      resolve(rs);
                    });
                  }
                });
              } else {
                num.value++;
              }
            }
            resolve(res);
          });
        }
      });
    }
  });
}
// 修改成品皮重
function changeTareWeight(e) {
  if (e !== null) {
    netWeight.value = grossWeight.value - e;
    semiFinProduct.value.boxPerNumber = 1;
  }
}

// 修改原料皮重
function changeYCTareWeight(e) {
  if (e !== null) {
    netWeight.value = grossWeight.value - e;
  }
}
// 打印
async function initPrint(printValueData, type) {
  const printValueDatas = JSON.parse(JSON.stringify(printValueData));
  printValueDatas.grossWeight = parseFloat(printValueData.grossWeight.toFixed(3));
  printValueDatas.netWeight = parseFloat(printValueData.netWeight.toFixed(3));
  hiprint.PrintElementTypeManager.buildByHtml($('.ep-draggable-item'));
  $('#hiprint-printTemplate').empty();
  // 模板
  if (type === 0 && selectPortForm.value.printAxisTemplate) {
    axishiprintTemplate.value = await new hiprint.PrintTemplate({
      template: axisTemp.value
    });
    axishiprintTemplate.value.print2(printValueDatas, { printer: axislivePrint.value, title: 'hiprint测试打印' });
  } else if (type === 1 && selectPortForm.value.printBoxTemplate) {
    boxhiprintTemplate.value = await new hiprint.PrintTemplate({
      template: BoxTemp.value
    });
    boxhiprintTemplate.value.print2(printValueDatas, { printer: boxlivePrint.value, title: 'hiprint测试打印' });
  } else if (type === 2 && selectPortForm.value.printMaterialsTemplate) {
    hiprintMaterialsTemplate.value = await new hiprint.PrintTemplate({
      template: MaterialsTemp.value
    });
    hiprintMaterialsTemplate.value.print2(printValueDatas, {
      printer: MaterialslivePrint.value,
      title: 'hiprint测试打印'
    });
  }
}

// 弹出确认入库提示
function manualSave() {
  if (locale.value === '0') {
    if (selectPortForm.value.printMaterialsTemplate && !MaterialsTemp.value) {
      message.warning('请配置原料打印模板内容');
      return;
    }
    if (!formModel.value) return;
    formModel.value.validate(async errors => {
      if (!errors) {
        handleDeleteItemType();
      }
    });
  } else {
    if (selectPortForm.value.printAxisTemplate && !axisTemp.value) {
      message.warning('请配置轴打印模板内容');
      return;
    }
    if (selectPortForm.value.printAxisTemplate && !axislivePrint.value) {
      message.warning('请配置轴打印机');
      return;
    }
    if (selectPortForm.value.printBoxTemplate && !BoxTemp.value) {
      message.warning('请配置箱打印模板内容');
      return;
    }
    if (selectPortForm.value.printBoxTemplate && !boxlivePrint.value) {
      message.warning('请配置箱打印机');
      return;
    }
    if (!semiFinProductRef.value) return;
    semiFinProductRef.value.validate(async errors => {
      if (!errors) {
        handleDeleteItemType();
      }
    });
  }
}

function handleUpdateValue(val, options) {
  // eslint-disable-next-line no-console
  console.log('val ==> ', val);
  addForm.value.itemSpecification = options.label;
}
// 原料打印模板
// eslint-disable-next-line @typescript-eslint/no-unused-vars
function changeMaterialsPrintTemplate(val, options) {
  // eslint-disable-next-line no-console
  console.log('val changeMaterialsPrintTemplate ==> ', val);
  if (val) {
    MaterialsTemp.value = JSON.parse(options.content);
    MaterialslivePrint.value = MaterialsTemp.value.panels[0].livePrint;
  }
}
// 轴打印模板
function changeAxisPrintTemplate(val, options) {
  // eslint-disable-next-line no-console
  console.log('val changeAxisPrintTemplate ==> ', val);
  if (val) {
    axisTemp.value = JSON.parse(options.content);
    axislivePrint.value = axisTemp.value.panels[0].livePrint;
  }
}
// 箱打印模板
function changeBoxPrintTemplate(val, options) {
  // eslint-disable-next-line no-console
  console.log('val changeBoxPrintTemplate ==> ', val);
  if (val) {
    BoxTemp.value = JSON.parse(options.content);
    boxlivePrint.value = BoxTemp.value.panels[0].livePrint;
  }
}

function reset() {
  if (isOpen.value) {
    openOrClosePort(selectPortForm.value.equipmentId);
    selectPortForm.value = {
      equipmentId: null,
      machineType: null,
      printAxisTemplate: null,
      printBoxTemplate: null
    };
    semiFinProduct.value = {
      boxPerNumber: 1,
      axleLoad: 0,
      productName: null,
      materialNumber: '',
      batchNumber2: null,
      batchNumber: null,
      itemCode: null,
      wmsSpec: null,
      artt3: 1, // 盘数
      artt2: '0', // 装箱状态
      artt1: null, // 仓库
      artt4: 1, // 发货数量
      boxNumber: 1, // 箱数
      wmsUnit: 'kg',
      trader: null,
      materialManufacturer: null,
      materialCode: '',
      installationStatus: 'OUT'
    };
  } else {
    selectPortForm.value = {
      equipmentId: null,
      machineType: null,
      printAxisTemplate: null,
      printBoxTemplate: null
    };
    semiFinProduct.value = {
      boxPerNumber: 1,
      axleLoad: 0,
      productName: null,
      materialNumber: '',
      batchNumber2: null,
      batchNumber: null,
      itemCode: null,
      wmsSpec: null,
      artt3: 1, // 盘数
      artt2: '0', // 装箱状态
      artt1: null, // 仓库
      artt4: 1, // 发货数量
      boxNumber: 1, // 箱数
      wmsUnit: 'kg',
      trader: null,
      materialManufacturer: null,
      materialCode: '',
      installationStatus: 'OUT'
    };
  }
}

function lookStorageRecord() {
  lookRecordShow.value = !lookRecordShow.value;
}

function getParseData(machineType) {
  const obj = {
    func: null
  };
  switch (machineType) {
    case 'A':
      obj.func = parseTuoliduoAsciiData;
      break;
    case 'B':
      obj.func = parseScaleData;
      break;
    default:
      break;
  }
  return obj;
}

function getSpecData(_val, item) {
  semiFinProduct.value.productName = item.label;
  specList.value = [];
  materialData.value = {};
  batchNumberData.value = [];
  getSpecList(item.label).then(res => {
    res.data.specList.forEach((n, index) => {
      specList.value.push({ label: n, value: index });
    });
    materialData.value = res.data.materialCode;
    batchNumberData.value = res.data.batchNumberMap;
  });
}
function specChange(_val, item) {
  const label = item.label;
  itemCodeOptions.value = [];
  itemCodeData.value = [];
  semiFinProduct.value.wmsSpec = label;
  itemCodeData.value = batchNumberData.value[label];
  Object.keys(batchNumberData.value[label]).forEach((n, index) => {
    itemCodeOptions.value.push({ label: n, value: index });
  });
  // batchNumberData.value[label]?
}
// function itemCodeChange(val, item) {
//   // eslint-disable-next-line no-console
//   console.log('val==>', val);
//   // console.log(semiFinProduct.value.itemCode , item.label);
//   semiFinProduct.value.itemCode = item.label;
//   batchNumberOptions.value = [];
//   // 获取物料代码
//   semiFinProduct.value.materialCode = materialData.value[item.label] ? materialData.value[item.label] : null;
//   itemCodeData.value[item.label].forEach((n, index) => {
//     batchNumberOptions.value.push({ label: n, value: index });
//   });
// }
function materialCodeChange(e) {
  if (e !== null) {
    semiFinProduct.value.materialCode = e;
  }
}
async function getList() {
  getIngredientsProductNameList().then(res => {
    if (res.code === 200) {
      res.rows.forEach(item => {
        materialsList.value.push({ label: item.productName, value: item.id, ...item });
      });
    }
  });
  getIngredientsTradersList({ pageSize: 999 }).then(res => {
    if (res.code === 200) {
      res.rows.forEach(item => {
        traderList.value.push({ label: item.tradersName, value: item.id, ...item });
      });
    }
  });
  getWeighMachineSelectAll({ pageSize: 999 }).then(res => {
    if (res.code === 200) {
      equipmentListData.value = res.rows;
      res.rows.forEach(item => {
        equipmentList.value.push({ label: item.machineName, value: item.id, ...item });
      });
    }
  });

  getMaterialList({ pageSize: 999 }).then(res => {
    if (res.code === 200) {
      res.rows.forEach(item => {
        if (item.workstageName === ' ') {
          specificationList.value.push({ label: item.specification, value: item.id });
        }
      });
    }
  });

  getIngredientsManufacturerList({ pageSize: 999 }).then(res => {
    if (res.code === 200) {
      res.rows.forEach(item => {
        manufacturerList.value.push({
          label: `${item.manufacturerCode} (${item.manufacturerNick})`,
          value: item.id,
          ...item
        });
      });
    }
  });

  getPrintTemplateOne({ pageSize: 999 }).then(res => {
    if (res.code === 200) {
      res.rows.forEach(item => {
        if (item.createBy === '1') {
          boxPrintTemplateDataList.value.push({ label: item.templateName, value: item.templateId, ...item });
        } else if (item.createBy === '3') {
          MaterialsPrintTemplateDataList.value.push({ label: item.templateName, value: item.templateId, ...item });
        } else if (item.createBy === '0') {
          axisPrintTemplateDataList.value.push({ label: item.templateName, value: item.templateId, ...item });
        }
      });
    }
  });

  getUserList({ pageSize: 999 }).then(res => {
    if (res.code === 200) {
      res.rows.forEach(item => {
        saleUserList.value.push({ label: item.clientNick, value: item.id });
      });
    }
  });

  getAllClassifyList({ pageNum: 1 }).then(res => {
    res.data.forEach(item => {
      if (item.parentTypeId !== 0) {
        productList.value.push({
          id: item.id,
          value: item.id,
          key: item.id,
          label: item.typeName
        });
      }
    });
  });

  list({ pageSize: 999 }).then(res => {
    if (res.code === 200) {
      res.rows.forEach(item => {
        warehouseOptions.value.push({ label: item.warehouseName, value: item.warehouseId });
      });
    }
  });
}

// 是否存在未装箱数据
function getYesterDayData() {
  getYesterday().then(res => {
    if (res.data.length > 0) {
      warning.warn(() => {
        message.error('存在未装箱数据，删除后重试!');
        lookRecordShow.value = true;
        isConfrim.value = false;
      });
    }
  });
}
// 查询当天最大值
function getMaxValues() {
  selectMaxValues().then(res => {
    if (res.code === 200) {
      if (res.data) {
        hour.value = res.data.boxCode.substring(6, 8);
        sessionStorage.setItem('batchNum', parseInt(res.data.boxCode.substring(8, res.data.boxCode.length), 10) + 1);
        sessionStorage.setItem('boxSerialNum', parseInt(res.data.boxSerialNumber, 10) + 1);
        sessionStorage.setItem(
          'axisSerialNum',
          parseInt(res.data.axisSerialNumber.substring(7, res.data.axisSerialNumber.length), 10) + 1
        );
      } else {
        getYesterDayData();
        sessionStorage.setItem('batchNum', 1);
        sessionStorage.setItem('boxSerialNum', 1);
        sessionStorage.setItem('axisSerialNum', 1);
      }
    }
  });
}
// 查询未装箱数据
function getRecoordData() {
  getRecoordList({
    artt2: '0'
  }).then(res => {
    if (res.rows.length === 0) {
      isConfrim.value = true;
      lookRecordShow.value = false;
    } else {
      isConfrim.value = false;
      lookRecordShow.value = true;
      message.error('存在未装箱数据，删除后重试!');
    }
  });
}
onActivated(async () => {
  getMaxValues();
  getRecoordData();
  getSumWeight();
  if (isOpen.value) {
    openOrClosePort(selectPortForm.value.equipmentId);
  }
});
onDeactivated(() => {
  getMaxValues();
  if (isOpen.value) {
    openOrClosePort(selectPortForm.value.equipmentId);
  }
});
onBeforeMount(() => {
  getMaxValues();
});
onMounted(() => {
  reset();
  getList();
});

onUnmounted(() => {
  if (isOpen.value) {
    openOrClosePort(selectPortForm.value.equipmentId);
  }
});
</script>

<style scoped>
.n-data-table .n-data-table-th.n-data-table-th--fixed-right,
.n-data-table .n-data-table-td.n-data-table-td--fixed-right {
  box-shadow: -5px 2px 10px 0px rgb(198, 196, 196);
}
.n-form-item {
  display: block;
}

:deep(.n-form-item-label__text),
:deep(.n-input .n-input__input-el),
:deep(.n-base-selection-overlay__wrapper),
:deep(.n-radio__label) {
  font-size: 20px !important;
}

:deep(.n-form-item) {
  display: flex !important;
  margin-bottom: 25px !important;
}

.box {
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 0 50px;
  flex-wrap: nowrap;
}

.left {
  display: flex;
  justify-content: center;
  flex-direction: column;
  align-items: center;
  width: 50%;
  height: 80vh;
  box-shadow: 0 0 5px 1px rgba(0, 0, 0, 0.2);
  padding: 30px;
  min-width: 370px;
  margin-right: 40px;
}

.right-box {
  display: flex;
  justify-content: center;
  flex-direction: column;
  align-items: center;
  width: 50%;
  height: 80vh;
  box-shadow: 0 0 5px 1px rgba(0, 0, 0, 0.2);
  padding: 15px;
  min-width: 370px;
  margin-right: 50px;
}

.btn-box {
  display: flex;
  flex-direction: row-reverse;
  padding-right: 50px;
}

.pub-box {
  width: 80%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 20px;
}

.weight {
  font-size: 35px !important;
  margin-right: 10px;
}

.radio {
  width: 18px;
  height: 18px;
  border-radius: 50%;
  margin-left: 10px;
  display: inline-block;
}

.radio-box {
  display: flex;
  align-items: center;
  margin-left: 20px;
}

.green {
  background-color: lightgreen;
}

.red {
  background-color: red;
}
.open {
  background-color: lightgreen;
}
</style>
