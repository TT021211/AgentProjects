<!-- eslint-disable no-console -->
<template>
  <div class="weightAdmin">
    <n-card>
      <div class="box">
        <div class="left">
          <n-radio-group v-model:value="locale">
            <n-space style="display: flex; align-items: center; margin-bottom: 10px">
              <n-button type="info" size="small" @click="reset">重置信息</n-button>
              <n-button type="info" size="small" @click="lookStorageRecord">查看称重记录</n-button>
            </n-space>
          </n-radio-group>

          <n-form
            ref="semiFinProductRef"
            :model="semiFinProduct"
            label-placement="left"
            label-align="left"
            :label-width="120"
            :rules="semiFinProductRules"
          >
            <n-form-item label="客户简称">
              <n-select
                v-model:value="semiFinProduct.trader"
                placeholder="请选择客户简称"
                :filterable="true"
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
                :options="specList"
                :style="{ width: witeFormItemWidth }"
                @update:value="specChange"
              ></n-select>
            </n-form-item>
            <n-form-item label="物品编码" path="itemCode">
              <n-select
                v-model:value="semiFinProduct.itemCode"
                placeholder="请选择物品编码"
                :filterable="true"
                :options="itemCodeOptions"
                :style="{ width: witeFormItemWidth }"
                @update:value="itemCodeChange"
              ></n-select>
            </n-form-item>
            <n-form-item label="批号" path="batchNumber2">
              <n-select
                v-model:value="semiFinProduct.batchNumber2"
                placeholder="请选择批号"
                :filterable="true"
                :readonly="true"
                :options="batchNumberOptions"
                :style="{ width: witeFormItemWidth }"
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
            <div style="display: flex">
              <n-form-item label="轴标签" style="margin: 0 auto">
                <n-input-number
                  v-model:value="axisCount"
                  type="text"
                  :show-button="false"
                  :style="{ width: '80px', marginLeft: '-20px' }"
                >
                  <template #suffix>张</template>
                </n-input-number>
              </n-form-item>
              <n-form-item label="箱标签">
                <n-input-number
                  v-model:value="boxCount"
                  type="text"
                  :show-button="false"
                  :style="{ width: '80px', marginLeft: '-20px' }"
                >
                  <template #suffix>张</template>
                </n-input-number>
              </n-form-item>
            </div>
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
              :label-width="100"
              style="width: 720px"
              :model="selectPortForm"
              :rules="portRules"
            >
              <div style="display: flex; flex-direction: column; margin: 0 15px">
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

                <n-form-item label="轴模板">
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

                <n-form-item label="箱模板">
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
              </div>
              <div style="display: flex; flex-direction: column">
                <div style="display: flex; justify-content: space-between">
                  <n-form-item label="毛重" style="margin: 0 15px">
                    <n-input-number
                      v-model:value="grossWeight"
                      type="text"
                      :show-button="false"
                      placeholder="请输入毛重"
                      :style="{ width: '120px' }"
                      @update:value="changeGrossWeight"
                    >
                      <template #suffix>kg</template>
                    </n-input-number>
                  </n-form-item>
                  <n-form-item label="净重">
                    <n-space :style="{ width: '120px' }">
                      <span style="font-size: 24px">{{ parseFloat(netWeight.toFixed(retain)) }}&nbsp;&nbsp;kg</span>
                    </n-space>
                  </n-form-item>
                </div>
                <div style="display: flex; justify-content: space-between">
                  <n-form-item label="皮重" style="margin: 0 15px">
                    <n-input-number
                      v-model:value="semiFinProduct.axleLoad"
                      type="text"
                      placeholder="请输入皮重"
                      :show-button="false"
                      :style="{ width: '120px' }"
                      @update:value="changeTareWeight"
                    >
                      <template #suffix>kg</template>
                    </n-input-number>
                  </n-form-item>
                  <n-form-item label="每箱盘数">
                    <n-input-number
                      v-model:value="semiFinProduct.boxPerNumber"
                      type="text"
                      placeholder="每箱盘数"
                      :style="{ width: '120px' }"
                    />
                  </n-form-item>
                </div>
                <div style="display: flex; justify-content: space-between">
                  <n-form-item label="箱流水号" style="margin: 0 15px">
                    <n-input
                      v-model:value="semiFinProduct.boxSerialNumber"
                      type="text"
                      placeholder="箱流水号"
                      :style="{ width: '100px' }"
                    />
                  </n-form-item>
                  <n-form-item label="手动输入">
                    <n-switch
                      :style="{ width: '100px' }"
                      :checked-value="1"
                      :unchecked-value="2"
                      @update:value="handleUpdateSerial"
                    >
                      <template #checked>是</template>
                      <template #unchecked>否</template>
                    </n-switch>
                  </n-form-item>
                </div>
              </div>
            </n-form>
          </div>
          <div class="pub-box">
            <div style="width: 100%; display: flex; flex-direction: column; margin: 0 auto">
              <n-space>
                <n-button v-show="!isAutoSave" type="info" size="small" @click="manualSave">手动称重</n-button>
                <n-button type="info" :class="[isAutoSave ? 'open' : '']" size="small" @click="openOrCloneAutoSave">
                  自动称重
                  <span style="color: yellow">{{ isAutoSave ? '(已开启)' : '(已关闭)' }}</span>
                </n-button>
                <n-button
                  type="info"
                  :class="[isAutoSave ? 'open' : '']"
                  size="small"
                  @click="verificationStatus = true"
                >
                  <icon-tdesign-setting-1 class="mr-1px text-15px" />
                  重量检验
                </n-button>
                <n-switch :checked-value="3" :unchecked-value="2" @update:value="handleUpdateValue">
                  <template #checked>3位</template>
                  <template #unchecked>2位</template>
                </n-switch>
              </n-space>
            </div>
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
    <!-- <n-button @click="getPort">getPort</n-button> -->
  </div>
</template>

<script setup>
import { ref, onMounted, watch, onUnmounted, onBeforeMount, onActivated, onDeactivated } from 'vue';
import { useMessage, useDialog } from 'naive-ui';
import { hiprint } from 'vue-plugin-hiprint';
// import { SerialPort } from 'serialport';
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
import { getBoxReacprding, addBoxReacprding } from '@/service/api/storage/storageBox/index';
import {
  addRecording,
  getRecoordList,
  getYesterday,
  getBoxSerial,
  getBoxSerialToUser
} from '~/src/service/api/storage/storageRecording/index';
import { selectMaxValues, getSpecList } from '~/src/service/api/storage/weightAdmin/index';
import { useWarning } from '~/src/hooks';
import { getPrintTemplateOne } from '~/src/service/api/hiprint/index';
import rawMaterial from './component/index.vue';
const authStore = useAuthStore();
const dialog = useDialog();
const warning = useWarning('提示', '存在未装箱数据请先删除数据后重试', false);
const message = useMessage();
const verificationStatus = ref(false);
const formItemWidth = ref('280px');
// const formModel = ref(null);
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
const deviceMachineCode = ref('');

const boxPrintTemplateDataList = ref([]);
const axisPrintTemplateDataList = ref([]);
const MaterialsPrintTemplateDataList = ref([]);
const netWeight = ref(0);
const grossWeight = ref(0);
const retain = ref(2);
// 保留小数位
function handleUpdateValue(value) {
  retain.value = value;
}

// 子组件的ref
const rawMaterialRef = ref(null);
function recordReload() {
  getNowSerialNum();
  getRecoordData();
  getSumWeight();
  getYesterDayData();
  getMaxValues();
}
// 累计净重
const sumWeight = ref(0);
// 最大最小重量
const minWeight = ref(0);
const maxWeight = ref(100);

const semiFinProductRef = ref(null);
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

// 是否手动输入箱流水号
const manualSerial = ref(2);
function handleUpdateSerial(value) {
  manualSerial.value = value;
  if (value === 2) getNowSerialNum();
}
// 称重记录入库
const sleep2 = () =>
  new Promise(resolve => {
    addRecording({ ...semiFinProduct.value, decimalPlaces: retain.value }).then(async res => {
      resolve(res);
    });
  });
// 箱入库
const sleep3 = axleLoad =>
  new Promise(resolve => {
    addBoxReacprding({ ...semiFinProduct.value, axleLoad, decimalPlaces: retain.value }).then(res => {
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
      weight = parseFloat(weightData).toFixed(3) / 10;
      break;
    case 'B':
      weight = parseFloat(weightData).toFixed(3) / 1000;
      break;
    case 'C':
      weight = parseFloat(weightData).toFixed(3);
      break;
    default:
      break;
  }
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
// const num = ref(1);
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
        netWeight.value = grossWeight.value - semiFinProduct.value.axleLoad;
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
watch(
  () => [semiFinProduct.value.productName, semiFinProduct.value.wmsSpec],
  (newVal, oldVal) => {
    if (newVal[0] !== oldVal[0]) {
      semiFinProduct.value.wmsSpec = null;
      semiFinProduct.value.itemCode = null;
      semiFinProduct.value.batchNumber2 = null;
      semiFinProduct.value.materialCode = null;
    }
    if (newVal[1] !== oldVal[1]) {
      semiFinProduct.value.itemCode = null;
      semiFinProduct.value.batchNumber2 = null;
      semiFinProduct.value.materialCode = null;
    }
  }
);

watch(
  () => netWeight.value,
  newVal => {
    netWeight.value = grossWeight.value - semiFinProduct.value.axleLoad;
    if (isAutoSave.value) {
      if (netWeight.value <= 0 || netWeight.value === 'null') {
        if (stabilityTimeOut.value) clearTimeout(stabilityTimeOut.value);
        stabilityTimeOut.value = setTimeout(() => {
          if (newVal === netWeight.value) {
            const isWeightSaveStatus = JSON.parse(JSON.stringify(weightDataLsit.value)).filter(item => {
              return Number(item.value) <= 0 || item.value === 'null';
            });
            if (isWeightSaveStatus.length > 0) {
              isSaveStatus.value = true;
            }
          }
        }, 500);
      } else {
        if (stabilityTimeOut.value) clearTimeout(stabilityTimeOut.value);
        stabilityTimeOut.value = setTimeout(() => {
          if (newVal === netWeight.value) {
            const isWeightSaveStatus = JSON.parse(JSON.stringify(weightDataLsit.value)).filter(
              item => item.value > '0' && item.value === newVal
            );
            const status = isWeightSaveStatus.every(n => n.value === newVal);
            if (status && isSaveStatus.value) {
              saveListData();
            }
          }
        }, 1000);
      }
    }
  }
);
// 开启自动保存
function openOrCloneAutoSave() {
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
  // if (selectPortForm.value.printMaterialsTemplate && !MaterialsTemp.value) {
  //   message.warning('请配置原料打印模板内容');
  //   return;
  // }
  // if (selectPortForm.value.printMaterialsTemplate && !MaterialslivePrint.value) {
  //   message.warning('请配置原料打印机');
  //   return;
  // }
  if (!semiFinProductRef.value) return;
  semiFinProductRef.value.validate(async errors => {
    if (!errors) {
      isSaveStatus.value = !isSaveStatus.value;
      isAutoSave.value = !isAutoSave.value;
    }
  });
}
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
        getNowSerialNum();
        getSumWeight();
      }
    }
  }
);
// 查询当前批次号流水号
function getNowSerialNum() {
  getBoxSerial({ ...semiFinProduct.value, installationStatus: 'OUT' }).then(res => {
    if (res.data) {
      const serialNumber = parseInt(res.data.boxSerialNumber, 10) + 1;
      sessionStorage.setItem('boxSerialNum', serialNumber);
      semiFinProduct.value.boxSerialNumber =
        '0000'.substring(0, '0000'.length - serialNumber.toString().length) + serialNumber.toString();
    } else {
      sessionStorage.setItem('boxSerialNum', 1);
      semiFinProduct.value.boxSerialNumber = '0001';
    }
  });
  getMaxValues();
}

// 获取累计净重
function getSumWeight() {
  getBoxSerialToUser({ ...semiFinProduct.value, createBy: authStore.userInfo.userName }).then(rs => {
    if (rs.code === 200) {
      sumWeight.value = rs.data.totalNetWeight;
    }
  });
}
async function closePort() {
  try {
    // await reader.value.cancel();
    await reader.value.closed();
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
}
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
      closePort();
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
          .then(() => {
            isOpen.value = true;
            statusMessage.value = '串口已打开';
            isOpening.value = false;
            isJoin.value = true;
            // 启动读取数据的操作
            readData(port.value);
          });
      } catch (openError) {
        statusMessage.value = `串口打开失败: ${openError.message}`;
        closePort();
      }
    }
  } catch (error) {
    closePort();
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
        port.value = null;
        if (!navigator?.serial) return;
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
const axisCount = ref(1);
const boxCount = ref(1);
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
    if (!isConfrim.value) {
      lookRecordShow.value = true;
      message.error('存在未装箱数据，删除后重试!');
      return;
    }
    if (netWeight.value <= minWeight.value || netWeight.value >= maxWeight.value) {
      message.error('保存的数据不符合范围，请重新保存数据!');
      return;
    }
    const batchNum = parseInt(sessionStorage.getItem('batchNum'), 10);
    const firstUser = authStore.userInfo.userName.substring(0, 1).toUpperCase();
    // 箱编号
    const boxStrCode = `XM${firstUser}${dateFormatter(new Date())}0000`;
    semiFinProduct.value.boxCode =
      boxStrCode.substring(0, boxStrCode.length - batchNum.toString().length) + batchNum.toString();
    isSaveStatus.value = false;
    // 判断箱编码是否存在
    getBoxReacprding({ boxCode: semiFinProduct.value.boxCode }).then(reslA => {
      if (reslA.rows.length > 0) {
        if (reslA.rows[0].boxPerNumber === reslA.rows.length) {
          selectMaxValues().then(res => {
            if (res.code === 200) {
              if (res.data) {
                hour.value = res.data.boxCode.substring(6, 8);
                sessionStorage.setItem(
                  'batchNum',
                  parseInt(res.data.boxCode.substring(9, res.data.boxCode.length), 10) + 1
                );
                sessionStorage.setItem(
                  'axisSerialNum',
                  parseInt(res.data.axisSerialNumber.substring(7, res.data.axisSerialNumber.length), 10) + 1
                );
                addWeightRecord2(resolve);
              } else {
                getYesterDayData();
                sessionStorage.setItem('batchNum', 1);
                sessionStorage.setItem('axisSerialNum', 1);
                addWeightRecord2(resolve);
              }
            }
          });
        } else {
          addWeightRecord2(resolve);
        }
      } else {
        addWeightRecord2(resolve);
      }
    });
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
  if (grossWeight.value < minWeight.value || grossWeight.value > maxWeight.value) {
    message.error('保存的数据不符合范围，请重新保存数据!');
    return;
  }
  if (!isConfrim.value) {
    lookRecordShow.value = true;
    message.error('存在未装箱数据，删除后重试!');
    return;
  }
  const d = dialog.warning({
    title: '入库',
    content: `当前重量为${parseFloat(parseFloat(netWeight.value).toFixed(retain.value))}kg,确定要入库吗?`,
    positiveText: '确定',
    negativeText: '取消',
    onPositiveClick: () => {
      d.loading = true;
      return new Promise(resolve => {
        const batchNum = parseInt(sessionStorage.getItem('batchNum'), 10);
        const firstUser = authStore.userInfo.userName.substring(0, 1).toUpperCase();
        // 箱编号
        const boxStrCode = `XM${firstUser}${dateFormatter(new Date())}0000`;
        semiFinProduct.value.boxCode =
          boxStrCode.substring(0, boxStrCode.length - batchNum.toString().length) + batchNum.toString();

        // 判断箱编码是否存在
        getBoxReacprding({ boxCode: semiFinProduct.value.boxCode }).then(reslA => {
          if (reslA.rows.length > 0) {
            selectMaxValues().then(res => {
              if (res.code === 200) {
                if (res.data) {
                  sessionStorage.setItem(
                    'batchNum',
                    parseInt(res.data.boxCode.substring(9, res.data.boxCode.length), 10) + 1
                  );
                  // sessionStorage.setItem('boxSerialNum', parseInt(res.data.boxSerialNumber, 10) + 1);
                  sessionStorage.setItem(
                    'axisSerialNum',
                    parseInt(res.data.axisSerialNumber.substring(7, res.data.axisSerialNumber.length), 10) + 1
                  );
                  addWeightRecord(resolve);
                } else {
                  getYesterDayData();
                  sessionStorage.setItem('batchNum', 1);
                  // sessionStorage.setItem('boxSerialNum', 1);
                  sessionStorage.setItem('axisSerialNum', 1);
                  addWeightRecord(resolve);
                }
              }
            });
          } else {
            addWeightRecord(resolve);
          }
        });
      });
    }
  });
}
// 新增称重记录
function addWeightRecord(resolve) {
  hour.value = new Date().getDate();
  const batchNum = parseInt(sessionStorage.getItem('batchNum'), 10);
  let boxSerialNum = parseInt(sessionStorage.getItem('boxSerialNum'), 10);
  let axisSerialNum = parseInt(sessionStorage.getItem('axisSerialNum'), 10);
  const firstUser = authStore.userInfo.userName.substring(0, 1).toUpperCase();
  // 箱编号
  const boxStrCode = `XM${firstUser}${dateFormatter(new Date())}0000`;
  // 轴流水号
  const axisSerialStrCode = `Z${dateFormatter(new Date())}0000`;
  semiFinProduct.value.boxCode =
    boxStrCode.substring(0, boxStrCode.length - batchNum.toString().length) + batchNum.toString();
  if (manualSerial.value === 2) {
    semiFinProduct.value.boxSerialNumber =
      '0000'.substring(0, '0000'.length - boxSerialNum.toString().length) + boxSerialNum.toString();
  }
  semiFinProduct.value.axisSerialNumber =
    axisSerialStrCode.substring(0, axisSerialStrCode.length - axisSerialNum.toString().length) +
    axisSerialNum.toString();
  // semiFinProduct.value.netWeight = parseFloat(parseFloat(netWeight.value).toFixed(retain.value)); // 净重
  semiFinProduct.value.grossWeight = parseFloat(grossWeight.value); // 毛重
  semiFinProduct.value.artt2 = '0';
  sleep2().then(async res => {
    if (res.code === 200) {
      message.success('成品入库成功');
      axisSerialNum++;
      lookRecordShow.value = true;
      sessionStorage.setItem('axisSerialNum', axisSerialNum);
      res.data.snidCode = `https://dms.idfactory.cn/whois/ui/searchResult?identity=88.389.00054/${res.data.boxCode}`;
      for (let i = 1; i <= axisCount.value; i++) {
        initPrint(res.data, 0);
      }
      getSumWeight();
      if (rawMaterialRef.value) {
        rawMaterialRef.value.init();
      }
      getRecoordList({
        pageSize: 100,
        boxCode: semiFinProduct.value.boxCode
      }).then(resl => {
        if (resl.total === semiFinProduct.value.boxPerNumber || semiFinProduct.value.boxPerNumber === 1) {
          // const totalNetWeight = parseFloat(
          //   resl.rows.reduce((prevValue, row) => prevValue + row.netWeight, 0).toFixed(retain.value)
          // );
          const totalGrossWeight = null;
          const axleLoad = null;
          semiFinProduct.value.netWeight = null;
          semiFinProduct.value.grossWeight = totalGrossWeight;
          semiFinProduct.value.materialNumber = semiFinProduct.value.materialManufacturer;
          semiFinProduct.value.artt2 = '1';
          sleep3(axleLoad).then(rs => {
            if (rs.code === 200) {
              // num.value = 1;
              message.success('箱入库成功');
              if (rawMaterialRef.value) {
                rawMaterialRef.value.init();
              }
              sessionStorage.setItem(
                'batchNum',
                parseInt(rs.data.boxCode.substring(9, rs.data.boxCode.length), 10) + 1
              );
              boxSerialNum++;
              sessionStorage.setItem('boxSerialNum', boxSerialNum);
              rs.data.snidCode = `https://dms.idfactory.cn/whois/ui/searchResult?identity=88.389.00054/${rs.data.boxCode}`;
              for (let i = 1; i <= boxCount.value; i++) {
                initPrint(rs.data, 1);
              }
            }
            resolve(rs);
          });
        }
      });
    }
    resolve(res);
  });
}
// 自动新增称重记录
function addWeightRecord2(resolve) {
  hour.value = new Date().getDate();
  const batchNum = parseInt(sessionStorage.getItem('batchNum'), 10);
  let boxSerialNum = parseInt(sessionStorage.getItem('boxSerialNum'), 10);
  let axisSerialNum = parseInt(sessionStorage.getItem('axisSerialNum'), 10);
  const firstUser = authStore.userInfo.userName.substring(0, 1).toUpperCase();
  // 箱编号
  const boxStrCode = `XM${firstUser}${dateFormatter(new Date())}0000`;
  // 轴流水号
  const axisSerialStrCode = `Z${dateFormatter(new Date())}0000`;
  semiFinProduct.value.boxCode =
    boxStrCode.substring(0, boxStrCode.length - batchNum.toString().length) + batchNum.toString();

  if (manualSerial.value === 2) {
    semiFinProduct.value.boxSerialNumber =
      '0000'.substring(0, '0000'.length - boxSerialNum.toString().length) + boxSerialNum.toString();
  }
  semiFinProduct.value.axisSerialNumber =
    axisSerialStrCode.substring(0, axisSerialStrCode.length - axisSerialNum.toString().length) +
    axisSerialNum.toString();
  // semiFinProduct.value.netWeight = parseFloat(parseFloat(netWeight.value).toFixed(retain.value)); // 净重
  semiFinProduct.value.grossWeight = parseFloat(grossWeight.value); // 毛重
  semiFinProduct.value.artt2 = '0';
  sleep2().then(async res => {
    if (res.code === 200) {
      message.success('成品入库成功');
      axisSerialNum++;
      isSaveStatus.value = false;
      lookRecordShow.value = true;
      sessionStorage.setItem('axisSerialNum', axisSerialNum);
      res.data.snidCode = `https://dms.idfactory.cn/whois/ui/searchResult?identity=88.389.00054/${res.data.boxCode}`;
      for (let i = 1; i <= axisCount.value; i++) {
        initPrint(res.data, 0);
      }
      getSumWeight();
      if (rawMaterialRef.value) {
        rawMaterialRef.value.init();
      }
      getRecoordList({
        pageSize: 100,
        boxCode: semiFinProduct.value.boxCode
      }).then(resl => {
        if (resl.total === semiFinProduct.value.boxPerNumber || semiFinProduct.value.boxPerNumber === 1) {
          const totalGrossWeight = null;
          const axleLoad = null;
          semiFinProduct.value.netWeight = null;
          semiFinProduct.value.grossWeight = totalGrossWeight;
          semiFinProduct.value.materialNumber = semiFinProduct.value.materialManufacturer;
          semiFinProduct.value.artt2 = '1';
          sleep3(axleLoad).then(rs => {
            if (rs.code === 200) {
              // num.value = 1;
              message.success('箱入库成功');
              if (rawMaterialRef.value) {
                rawMaterialRef.value.init();
              }
              sessionStorage.setItem(
                'batchNum',
                parseInt(rs.data.boxCode.substring(9, rs.data.boxCode.length), 10) + 1
              );
              boxSerialNum++;
              sessionStorage.setItem('boxSerialNum', boxSerialNum);
              rs.data.snidCode = `https://dms.idfactory.cn/whois/ui/searchResult?identity=88.389.00054/${rs.data.boxCode}`;
              for (let i = 1; i <= boxCount.value; i++) {
                initPrint(rs.data, 1);
              }
            }
            resolve(rs);
          });
        }
      });
    }
    resolve(res);
  });
}
// 修改成品毛重
function changeGrossWeight(e) {
  if (e !== null) {
    netWeight.value = parseFloat((e - semiFinProduct.value.axleLoad).toFixed(retain.value));
  }
}
// 修改成品皮重
function changeTareWeight(e) {
  if (e !== null) {
    netWeight.value = parseFloat((grossWeight.value - e).toFixed(retain.value));
  }
}
// 打印
async function initPrint(printValueData, type) {
  const printValueDatas = JSON.parse(JSON.stringify(printValueData));
  printValueDatas.grossWeight = parseFloat(printValueData.grossWeight.toFixed(retain.value));
  printValueDatas.netWeight = parseFloat(printValueData.netWeight.toFixed(retain.value));
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
// 轴打印模板
function changeAxisPrintTemplate(val, options) {
  if (val) {
    semiFinProduct.value.batchNumber = options.templateId;
    axisTemp.value = JSON.parse(options.content);
    axislivePrint.value = axisTemp.value.panels[0].livePrint;
  }
}
// 箱打印模板
function changeBoxPrintTemplate(val, options) {
  if (val) {
    semiFinProduct.value.materialManufacturer = options.templateId;
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
    itemCodeOptions.value.push({ label: batchNumberData.value[label][n][0], code: n, value: index });
  });

  // 排序
  itemCodeOptions.value = itemCodeOptions.value.sort((a, b) => {
    if (a.label < b.label) {
      return -1;
    }
    if (a.label > b.label) {
      return 1;
    }
    return 0;
  });
  // batchNumberData.value[label]?
}
function itemCodeChange(_val, item) {
  semiFinProduct.value.itemCode = item.code;
  batchNumberOptions.value = [];
  // 获取物料代码
  semiFinProduct.value.materialCode = materialData.value[item.code] ? materialData.value[item.code] : null;
  itemCodeData.value[item.code].forEach((n, _index) => {
    semiFinProduct.value.batchNumber2 = n;
  });
}
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
      axisPrintTemplateDataList.value = [];
      res.rows.forEach(item => {
        if (item.createBy === '1') {
          if (authStore.userInfo.userName === 'elck' && item.templateName.includes('二楼')) {
            boxPrintTemplateDataList.value.push({ label: item.templateName, value: item.templateId, ...item });
          } else if (authStore.userInfo.userName === 'slck' && !item.templateName.includes('二楼')) {
            boxPrintTemplateDataList.value.push({ label: item.templateName, value: item.templateId, ...item });
          }
        } else if (item.createBy === '3') {
          MaterialsPrintTemplateDataList.value.push({ label: item.templateName, value: item.templateId, ...item });
        } else if (item.createBy === '0') {
          if (authStore.userInfo.userName === 'elck' && item.templateName.includes('二楼')) {
            axisPrintTemplateDataList.value.push({ label: item.templateName, value: item.templateId, ...item });
          } else if (authStore.userInfo.userName === 'slck' && !item.templateName.includes('二楼')) {
            axisPrintTemplateDataList.value.push({ label: item.templateName, value: item.templateId, ...item });
          }
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
        if (item.warehouseName === '成品仓')
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
        sessionStorage.setItem('batchNum', parseInt(res.data.boxCode.substring(9, res.data.boxCode.length), 10) + 1);
        // sessionStorage.setItem('boxSerialNum', parseInt(res.data.boxSerialNumber, 10) + 1);
        sessionStorage.setItem(
          'axisSerialNum',
          parseInt(res.data.axisSerialNumber.substring(7, res.data.axisSerialNumber.length), 10) + 1
        );
      } else {
        getYesterDayData();
        sessionStorage.setItem('batchNum', 1);
        // sessionStorage.setItem('boxSerialNum', 1);
        sessionStorage.setItem('axisSerialNum', 1);
      }
    }
  });
}
// 查询未装箱数据
function getRecoordData() {
  getRecoordList({
    artt2: '0',
    createBy: authStore.userInfo.userName
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
  getNowSerialNum();
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
  getNowSerialNum();
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
  margin-right: 20px;
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
