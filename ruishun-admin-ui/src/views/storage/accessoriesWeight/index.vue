<!-- eslint-disable no-console -->
<!-- http://localhost:3200/storage/accessoriesWeight -->
<template>
  <div class="weightAdmin">
    <n-card>
      <div class="box">
        <div class="left">
          <n-form
            ref="formModel"
            :model="addForm"
            label-placement="left"
            label-align="left"
            :label-width="120"
            :rules="rules"
          >
            <n-form-item label="点击扫码">
              <n-input
                ref="inputValue"
                v-model:value="scanCode"
                placeholder="点击后扫码"
                :style="{ width: witeFormItemWidth }"
              />
            </n-form-item>
            <n-form-item label="辅料类型" path="productTypeName">
              <n-input
                v-model:value="addForm.productTypeName"
                placeholder="请输入辅料类型"
                :disabled="true"
                :style="{ width: witeFormItemWidth }"
              />
            </n-form-item>
            <n-form-item label="品名" path="itemName">
              <n-input
                v-model:value="addForm.itemName"
                placeholder="请输入品名"
                :disabled="true"
                :style="{ width: witeFormItemWidth }"
              />
            </n-form-item>
            <n-form-item label="批号" path="productBatchCode">
              <n-input
                v-model:value="addForm.productBatchCode"
                placeholder="请输入批号"
                :disabled="true"
                :style="{ width: witeFormItemWidth }"
              />
            </n-form-item>
            <n-form-item label="料号" path="partNumber">
              <n-input
                v-model:value="addForm.partNumber"
                placeholder="请输入料号"
                :disabled="true"
                :style="{ width: witeFormItemWidth }"
              />
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

              <n-form-item label="模板">
                <n-select
                  v-model:value="selectPortForm.printMaterialsTemplate"
                  placeholder="选择打印模板"
                  :clearable="true"
                  :filterable="true"
                  :options="MaterialsPrintTemplateDataList"
                  :style="{ width: formItemWidth }"
                  @update-value="changeMaterialsPrintTemplate"
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
              <n-form-item label="净重">
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
              <n-form-item label="皮重">
                <n-input-number
                  v-model:value="axleLoad"
                  type="text"
                  placeholder="请输入皮重"
                  :show-button="false"
                  :style="{ width: witeFormItemWidth }"
                  @update:value="changeTareWeight"
                >
                  <template #suffix>kg</template>
                </n-input-number>
              </n-form-item>
            </n-form>
          </div>
          <div class="pub-box">
            <n-space>
              <n-button v-show="!isAutoSave" type="info" size="small" @click="handleDeleteItemType">手动称重</n-button>
              <n-button type="info" :class="[isAutoSave ? 'open' : '']" size="small" @click="openOrCloneAutoSave">
                自动称重
                <span style="color: yellow">{{ isAutoSave ? '(已开启)' : '(已关闭)' }}</span>
              </n-button>
            </n-space>
          </div>
        </div>
      </div>
    </n-card>
  </div>
</template>

<script setup>
import { ref, onMounted, watch, onUnmounted, nextTick, onActivated, onDeactivated } from 'vue';
import { useMessage, useDialog } from 'naive-ui';
import { hiprint } from 'vue-plugin-hiprint';
import { createRequiredFormRule } from '@/utils';
import { getWeighMachineSelectAll } from '@/service/api/md/workbench/weighMachine';
import { getStorageManageList, addRealStock } from '@/service/api/wms/stockControl';
import { getPrintTemplateOne } from '~/src/service/api/hiprint/index';
// 消息框
const message = useMessage();
// 对话框
const dialog = useDialog();
// 辅料新增对象
const addForm = ref({});
// 净重
const netWeight = ref(0);
// 毛重
const grossWeight = ref(0);
// 皮重
const axleLoad = ref(0);
// 原料模板
const MaterialsTemp = ref(null);
// 自动保存
const isSaveStatus = ref(false);
// 扫码框聚焦
const inputValue = ref();
// 设备模板选择
const selectPortForm = ref({
  equipmentId: null,
  machineType: null,
  printMaterialsTemplate: null
});
const rules = {
  productTypeName: createRequiredFormRule('请输入辅料类型'),
  itemName: createRequiredFormRule('请输入品名'),
  productBatchCode: createRequiredFormRule('请输入批号'),
  partNumber: createRequiredFormRule('请输入料号')
};
let scanTimeout = null;
// 二维码编号
const scanCode = ref('');
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
        getStorageManageList({ itemIdentifier: scanCode.value }).then(res => {
          if (res.code === 200 && res.rows.length > 0) {
            addForm.value = {
              ...res.rows[0],
              codeSn: '',
              createTime: ''
            };
          } else {
            scanCode.value = '';
            message.error('二维码编号不存在，请扫新的二维码');
          }
        });
      }, 300);
    }
  }
);

// 锡水入库
const sleep = () =>
  new Promise(resolve => {
    addRealStock(addForm.value).then(res => {
      resolve(res);
    });
  });
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
  if (selectPortForm.value.printMaterialsTemplate && !MaterialsTemp.value) {
    message.warning('请配置原料打印模板内容');
    return;
  }
  if (!scanCode.value) {
    message.warning('请扫码!');
    return;
  }
  getFocus();
  const d = dialog.warning({
    title: '入库',
    content: `当前重量为${parseFloat(parseFloat(netWeight.value).toFixed(3))}kg,确定要入库吗?`,
    positiveText: '确定',
    negativeText: '取消',
    onPositiveClick: () => {
      d.loading = true;
      return new Promise(resolve => {
        addForm.value.tareWeight = axleLoad.value;
        addForm.value.totalWeight = netWeight.value;
        addForm.value.weight = grossWeight.value;
        addForm.value.itemIdentifier = '';
        sleep().then(res => {
          if (res.code === 200) {
            message.success('原料入库成功');
            isSaveStatus.value = false;
            initPrint(res.data);
          }
          resolve(res);
        });
      });
    }
  });
}
// 串口是否连接
const isOpen = ref(false);
// 输入验证
const formModel = ref(null);
// 是否开启自动保存
const isAutoSave = ref(false);
// 开启自动保存
function openOrCloneAutoSave() {
  if (selectPortForm.value.printMaterialsTemplate && !MaterialsTemp.value) {
    message.warning('请配置原料打印模板内容');
    return;
  }
  if (!formModel.value) return;
  isSaveStatus.value = !isSaveStatus.value;
  isAutoSave.value = !isAutoSave.value;
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
  if (!scanCode.value) {
    message.warning('请扫码!');
    return;
  }
  autoSavePromise.value = new Promise(resolve => {
    addForm.value.tareWeight = axleLoad.value;
    addForm.value.totalWeight = netWeight.value;
    addForm.value.weight = grossWeight.value;
    addForm.value.itemIdentifier = '';
    sleep().then(res => {
      if (res.code === 200) {
        message.success('原料入库成功');
        getFocus();
        isSaveStatus.value = false;
        initPrint(res.data);
      }
      resolve(res);
    });
  });
}
// 打印模板
const hiprintMaterialsTemplate = ref();
const MaterialslivePrint = ref(null);
// 打印
async function initPrint(printValueData) {
  const printValueDatas = JSON.parse(JSON.stringify(printValueData));
  printValueDatas.grossWeight = parseFloat(printValueData.weight.toFixed(3));
  printValueDatas.netWeight = parseFloat(printValueData.totalWeight.toFixed(3));
  hiprint.PrintElementTypeManager.buildByHtml($('.ep-draggable-item'));
  $('#hiprint-printTemplate').empty();
  if (selectPortForm.value.printMaterialsTemplate) {
    hiprintMaterialsTemplate.value = await new hiprint.PrintTemplate({
      template: MaterialsTemp.value
    });
    hiprintMaterialsTemplate.value.print2(printValueDatas, {
      printer: MaterialslivePrint.value,
      title: 'hiprint测试打印'
    });
  }
}
const formItemWidth = ref('280px');
const portFormModel = ref(null);
const locale = ref('0');
const equipmentList = ref([]);
const equipmentListData = ref([]);
const isJoin = ref(false);
const witeFormItemWidth = ref('260px');
const stableData = ref([]);
const deviceMachineCode = ref('');

const MaterialsPrintTemplateDataList = ref([]);

const semiFinProductRef = ref(null);

const baudRate = ref(9600);
const dataBits = ref(8);
const stopBits = ref(1);
const parity = ref('none');
const bufferSize = ref(1024);
const flowControl = ref('none');
const isOpening = ref(false);
const statusMessage = ref('未选择串口');
const latestData = ref(0); // 最新接收到的数据
const savedData = ref(''); // 保存稳定数据
// bug问题
const reader = ref(null); // 串口数据读取器
const port = ref(null); // 串口端口
const parsedData = ref([]);

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
  // 根据不同设备计算重量
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
    netWeight.value = grossWeight.value - axleLoad.value;
  }
);

// 修改成品皮重
function changeTareWeight(e) {
  if (e !== null) {
    netWeight.value = grossWeight.value - e;
  }
}

// 选择打印模板
function changeMaterialsPrintTemplate(_val, options) {
  if (_val) {
    MaterialsTemp.value = JSON.parse(options.content);
    MaterialslivePrint.value = MaterialsTemp.value.panels[0].livePrint;
  }
}
// 重量解析模式
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
// 获取下拉框数据
async function getList() {
  getWeighMachineSelectAll({ pageSize: 999 }).then(res => {
    if (res.code === 200) {
      equipmentListData.value = res.rows;
      res.rows.forEach(item => {
        equipmentList.value.push({ label: item.machineName, value: item.id, ...item });
      });
    }
  });

  getPrintTemplateOne({ pageSize: 999 }).then(res => {
    if (res.code === 200) {
      res.rows.forEach(item => {
        if (item.createBy === '3') {
          MaterialsPrintTemplateDataList.value.push({ label: item.templateName, value: item.templateId, ...item });
        }
      });
    }
  });
}
// 获取焦点
function getFocus() {
  nextTick(() => {
    scanCode.value = '';
    inputValue.value.focus();
  });
}
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
      // 处理数据
      const asciiData = uint8ArrayToAscii(data);
      const obj = getParseData(selectPortForm.value.machineType);
      if (obj.func !== null) {
        parsedData.value = obj.func(asciiData);
      }
      if (!parsedData.value) {
        read();
        return;
      }
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
        netWeight.value = grossWeight.value - axleLoad.value;
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
// 关闭串口
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
// 组件进入
onActivated(async () => {
  getFocus();
  if (reader.value) {
    openOrClosePort(selectPortForm.value.equipmentId);
  }
});
// 组件离开
onDeactivated(() => {
  if (reader.value) {
    openOrClosePort(selectPortForm.value.equipmentId);
  }
});
// 组件挂载
onMounted(() => {
  getList();
  getFocus();
});
// 组件卸载
onUnmounted(() => {
  if (reader.value) {
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
