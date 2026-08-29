<template>
  <a-card>
    <n-button
      style="position: absolute; top: 10px; right: 10px"
      type="primary"
      @click="
        () => {
          $router.go(-1);
        }
      "
    >
      返回
    </n-button>
    <div style="display: flex; flex-direction: column">
      <a-space style="margin-bottom: 10px">
        <a-button-group>
          <template v-for="(value, type) in paperTypes" :key="type">
            <a-button :type="curPaperType === type ? 'primary' : 'info'" @click="setPaper(type, value)">
              {{ type }}
            </a-button>
          </template>
          <a-popover v-model="paperPopVisible" title="设置纸张宽高(mm)" trigger="click">
            <template #content>
              <div>
                <a-input-group compact style="margin: 10px 10px">
                  <n-input
                    v-model:value="paperWidth"
                    type="number"
                    style="width: 100px; text-align: center"
                    placeholder="宽(mm)"
                  />
                  <n-input
                    style="width: 30px; border-left: 0; pointer-events: none; background-color: #fff"
                    placeholder="~"
                    disabled
                  />
                  <n-input
                    v-model:value="paperHeight"
                    type="number"
                    style="width: 100px; text-align: center; border-left: 0"
                    placeholder="高(mm)"
                  />
                </a-input-group>
                <a-button type="primary" style="width: 100%" @click="otherPaper">确定</a-button>
              </div>
            </template>
            <a-button :type="'other' == curPaperType ? 'primary' : ''">自定义纸张</a-button>
          </a-popover>
        </a-button-group>
        <div class="print-header-center">
          <div class="print-header-center-I">
            <span style="line-height: 32px">默认打印机:</span>
            <a-select
              v-model:value="livePrint"
              show-search
              placeholder="选择打印机"
              style="width: 200px"
              :options="printerList"
              :filter-option="filterOption"
            ></a-select>
          </div>
        </div>

        <!--        <a-button type="text" icon="zoom-out" @click="changeScale(false)"></a-button>-->
        <!--        <a-input-number-->
        <!--          :value="scaleValue"-->
        <!--          :min="scaleMin"-->
        <!--          :max="scaleMax"-->
        <!--          :step="0.1"-->
        <!--          disabled-->
        <!--          style="width: 70px"-->
        <!--          :formatter="value => `${(value * 100).toFixed(0)}%`"-->
        <!--          :parser="value => value.replace('%', '')"-->
        <!--        />-->
        <!--        <a-button type="text" icon="zoom-in" @click="changeScale(true)"></a-button>-->
        <!--        <a-button type="primary" icon="redo" @click="rotatePaper()">旋转</a-button>-->
        <a-button type="primary" @click="preView">预览</a-button>
        <a-button type="primary" @click="print">直接打印</a-button>
        <a-button type="primary" @click="downloadPlugin">下载打印插件</a-button>
        <a-button type="primary" @click="submitTempalte">保存</a-button>

        <!--        <a-button type="primary" @click="onlyPrint">Api单独打印</a-button>-->
        <!--        <a-button type="primary" @click="onlyPrint2">Api单独直接打印</a-button>-->
        <a-popconfirm title="是否确认清空?" ok-type="danger" ok-text="确定清空" @confirm="clearPaper">
          <a-button type="danger">清空</a-button>
        </a-popconfirm>
        <!--        <json-view :template="template"/>-->
      </a-space>
      <a-space style="margin-bottom: 10px"></a-space>
      <a-space style="margin-bottom: 10px"></a-space>
    </div>
    <a-row :gutter="[8, 0]">
      <a-col :span="4">
        <a-card style="height: 100vh">
          <a-row>
            <a-col :span="24" class="rect-printElement-types hiprintEpContainer">
              <div v-for="(draw, index) in drawItemList" :key="index">
                <a-row class="drag_item_title">{{ draw.title }}</a-row>
                <a-row style="min-height: 50px">
                  <a-col v-for="item in draw.listName" :key="item.id" :span="12" class="drag_item_box">
                    <div>
                      <a class="ep-draggable-item" :tid="item.tid" style>
                        <span class="glyphicon glyphicon-text-width" aria-hidden="true"></span>
                        <p class="glyphicon-class">{{ item.text }}</p>
                      </a>
                    </div>
                  </a-col>
                </a-row>
              </div>
            </a-col>
          </a-row>
        </a-card>
      </a-col>
      <a-col :span="15">
        <a-card class="card-design">
          <div id="hiprint-printTemplate" class="hiprint-printTemplate"></div>
        </a-card>
      </a-col>
      <a-col :span="5" class="params_setting_container">
        <a-card>
          <a-row class="hinnn-layout-sider">
            <div id="PrintElementOptionSetting"></div>
          </a-row>
        </a-card>
      </a-col>
    </a-row>

    <print-preview ref="preView" />

    <my-dialog
      ref="addFormRef"
      v-model:show="save"
      :model="saveForm"
      :rules="rules"
      title="保存打印模板"
      @cancel="save = false"
      @submit="submit"
    >
      <template #content>
        <div class="w-full">
          <n-form label-width="90px" label-placement="left">
            <n-form-item label="打印类型" path="createBy">
              <n-select
                v-model:value="saveForm.createBy"
                :options="[
                  { value: '0', label: '轴模板' },
                  { value: '1', label: '箱模板' },
                  { value: '2', label: '托模板' },
                  { value: '3', label: '原料模板' },
                  { value: '4', label: '报工模板' }
                ]"
                placeholder="请选择打印类型"
              ></n-select>
            </n-form-item>
            <n-form-item label="模板类型">
              <n-select v-model:value="saveForm.templateType" disabled></n-select>
            </n-form-item>
            <n-form-item label="模板名称">
              <n-input v-model:value="saveForm.templateName"></n-input>
            </n-form-item>
            <n-form-item label="所属客户">
              <n-select
                v-model:value="saveForm.clientId"
                filterable
                :options="saleUserList"
                @update:value="selectClient"
              ></n-select>
            </n-form-item>
            <n-form-item label="所属用户">
              <n-select
                v-model:value="saveForm.userId"
                :options="userList"
                :filterable="true"
                :clearable="true"
                @update:value="
                  (val, item) => {
                    saveForm.userNickName = item.label;
                  }
                "
              ></n-select>
            </n-form-item>
            <n-form-item label="默认打印机">
              <n-select v-model:value="saveForm.livePrint" :options="printerList" style="position: relative"></n-select>
              <n-gradient-text
                size="10"
                style="font-weight: bold; position: absolute; left: 10px; top: 35px"
                type="error"
              >
                注：需要本地运行打印客户端程序才能获取打印机列表
              </n-gradient-text>
            </n-form-item>
          </n-form>
        </div>
      </template>
    </my-dialog>
  </a-card>
</template>

<script>
// import { defineComponent } from '@vue/composition-api';
import { defineComponent } from 'vue';
import { hiprint } from 'vue-plugin-hiprint';
import { getPrintTemplate, savePrintTemplate } from '@/service/api/hiprint/index.ts';
import { getAllUser as saleUser, getUserList } from '@/service/api/sale/userManage';
import { fetchUserList } from '~/src/service';
import printData from './data/print-data.js';
import printPreview from './preview.vue';
import { boxButtons, CommonButtons, auxiliaryButtons } from './data/buttons';
import providerFunc from './data/provider.js';

export default defineComponent({
  name: 'PrintDesign',
  components: { printPreview },
  data() {
    return {
      template: null,
      livePrint: {},
      printerList: [],
      temp: null,
      saleList: [],
      save: false,
      userList: [],
      saveForm: {
        templateType: '',
        templateName: '',
        clientId: '',
        userId: '',
        livePrint: '',
        createBy: ''
      },
      drawItemList: [
        {
          listName: boxButtons,
          title: '基础元素'
        },
        {
          listName: CommonButtons,
          title: '公共元素'
        },
        {
          listName: auxiliaryButtons,
          title: '辅助元素'
        }
      ],
      options: [],
      curPaper: {
        type: 'A4',
        width: 210,
        height: 296.6
      },
      paperTypes: {
        A3: {
          width: 420,
          height: 296.6
        },
        A4: {
          width: 210,
          height: 296.6
        },
        A5: {
          width: 210,
          height: 147.6
        },
        B3: {
          width: 500,
          height: 352.6
        },
        B4: {
          width: 250,
          height: 352.6
        },
        B5: {
          width: 250,
          height: 175.6
        }
      },
      // 自定义纸张
      paperPopVisible: false,
      paperWidth: '',
      paperHeight: '',
      // 缩放
      scaleValue: 1,
      scaleMax: 5,
      scaleMin: 0.5,
      // 导入导出json
      jsonIn: '',
      jsonOut: '',
      hiprintTemplate: null,
      saleUserList: []
    };
  },
  computed: {
    curPaperType() {
      let type = 'other';
      const types = this.paperTypes;
      // eslint-disable-next-line guard-for-in
      for (const key in types) {
        const item = types[key];
        const { width, height } = this.curPaper;
        if (item.width === width && item.height === height) {
          type = key;
        }
      }
      return type;
    }
  },

  watch: {
    hiprintTemplate: {
      handler(val) {
        if (val && !this.printerList.length) {
          this.getPrintList();
        }
      }
    }
  },

  async created() {
    const data = await fetchUserList({ params: { pageSize: 999 } });
    this.userList = [];
    data.rows.forEach(item => {
      this.userList.push({ label: item.userName, value: item.userId });
    });
    getPrintTemplate(this.$route.query.templateId).then(res => {
      this.saveForm = res.data;
      if (res.data.content) {
        this.temp = JSON.parse(res.data.content);
        this.livePrint = this.temp.panels[0].livePrint;
      }
      // console.log(this.temp, panel, '11');
      this.saveForm.templateName = res.data.templateName;
      this.saveForm.templateType = res.data.templateCode;
    });
    // console.log(this.$route.query.templateId, '---');
    $(document).ready(() => {
      if (hiwebSocket.hasIo() && window.autoConnect) {
        hiwebSocket.start();
      }
    });
  },

  mounted() {
    getUserList({ pageSize: 999 }).then(res => {
      if (res.code === 200) {
        this.saleUserList = [];
        res.rows.forEach(item => {
          this.saleUserList.push({ label: item.clientNick, value: item.id, clientCode: item.clientCode });
        });
      }
    });
    if (window.hiwebSocket.opened) {
      this.getPrintList();
    }
    setTimeout(() => {
      this.init();
    }, 500);

    hiprint.init({
      // eslint-disable-next-line new-cap
      providers: [new providerFunc(boxButtons)]
    });
  },
  methods: {
    submit() {
      const hiprintTemplateData = this.hiprintTemplate.getJson();

      hiprintTemplateData.panels[0].livePrint = this.livePrint;
      this.saveForm.content = JSON.stringify(hiprintTemplateData);
      if (!this.saveForm.userId) this.saveForm.userNickName = '';
      savePrintTemplate(this.saveForm).then(_res => {
        window.$message?.success('保存成功');
        this.save = false;
      });
    },
    selectUser(_val, row) {
      this.saveForm.userNickName = row.userName;
    },
    selectClient(_val, row) {
      this.saveForm.clientName = row.label;
      this.saveForm.clientCode = row.clientCode;
    },
    getPrintList() {
      // console.log('window.hiwebSocket', window.hiwebSocket);
      // console.log('window.hiwebSocket', window.hiwebSocket.opened);
      if (window.hiwebSocket.opened || window.hiwebSocket.printerList.length) {
        this.printerList = window.hiwebSocket.printerList.map(item => {
          return {
            label: item.name,
            value: item.name
          };
        });
        if (this.printerList && this.printerList.length) {
          this.livePrint = this.$ls.get('livePrint') || this.printerList[0].name;
          return;
        }
        window.$message?.error('客户端未连接,无法直接打印');
      }
    },

    filterOption(input, option) {
      return option.value.toLowerCase().indexOf(input.toLowerCase()) >= 0;
    },

    // changeLivePring() {
    //   if (this.livePrint) {
    //     this.$ls.set('livePrint', this.livePrint);
    //   }
    // },

    init() {
      // eslint-disable-next-line no-undef
      hiprint.PrintElementTypeManager.buildByHtml($('.ep-draggable-item'));
      $('#hiprint-printTemplate').empty();
      // eslint-disable-next-line no-multi-assign
      this.template = this.hiprintTemplate = new hiprint.PrintTemplate({
        template: this.temp,
        // 图片选择功能
        onImageChooseClick: target => {
          // 测试 3秒后修改图片地址值
          setTimeout(() => {
            // target.refresh(url,options,callback)
            // callback(el, width, height) // 原元素,宽,高
            // target.refresh(url,false,(el,width,height)=>{
            //   el.options.width = width;
            //   el.designTarget.css('width', width + "pt");
            //   el.designTarget.children('.resize-panel').trigger($.Event('click'));
            // })
            target.refresh(
              'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAtAAAAIIAQMAAAB99EudAAAABlBMVEUmf8vG2O41LStnAAABD0lEQVR42u3XQQqCQBSAYcWFS4/QUTpaHa2jdISWLUJjjMpclJoPGvq+1WsYfiJCZ4oCAAAAAAAAAAAAAAAAAHin6pL9c6H/fOzHbRrP0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0u/SY9LS0tLS0tLS0tLS0n+edm+UlpaWlpaWlpaWlpaW/tl0Ndyzbno7/+tPTJdd1wal69dNa6abx+Lq6TSeYtK7BX/Diek0XULSZZrakPRtV0i6Hu/KIt30q4fM0pvBqvR9mvsQkZaW9gyJT+f5lsnzjR54xAk8mAUeJyMPwYFH98ALx5Jr0kRLLndT7b64UX9QR/0eAAAAAAAAAAAAAAAAAAD/4gpryzr/bja4QgAAAABJRU5ErkJggg==',
              {
                // auto: true, // 根据图片宽高自动等比(宽>高?width:height)
                // width: true, // 按宽调整高
                // height: true, // 按高调整宽
                real: true // 根据图片实际尺寸调整(转pt)
              }
            );
          }, 3000);
          // target.getValue()
          // target.refresh(url)
        },
        // 自定义可选字体
        // 或者使用 hiprintTemplate.setFontList([])
        // 或元素中 options.fontList: []
        fontList: [
          { title: '微软雅黑', value: 'Microsoft YaHei' },
          { title: '黑体', value: 'STHeitiSC-Light' },
          { title: '思源黑体', value: 'SourceHanSansCN-Normal' },
          { title: '王羲之书法体', value: '王羲之书法体' },
          { title: '宋体', value: 'SimSun' },
          { title: '华为楷体', value: 'STKaiti' },
          { title: 'cursive', value: 'cursive' }
        ],
        dataMode: 1, // 1:getJson 其他：getJsonTid 默认1
        history: true, // 是否需要 撤销重做功能
        onDataChanged: (type, json) => {
          // eslint-disable-next-line no-console
          console.log(type); // 新增、移动、删除、修改(参数调整)、大小、旋转
          // eslint-disable-next-line no-console
          console.log(json); // 返回 template
        },
        onUpdateError: e => {
          // eslint-disable-next-line no-console
          console.log(e);
        },
        settingContainer: '#PrintElementOptionSetting',
        paginationContainer: '.hiprint-printPagination'
      });
      this.hiprintTemplate.design('#hiprint-printTemplate', { grid: true });
      // 获取当前放大比例, 当zoom时传true 才会有
      this.scaleValue = this.hiprintTemplate.editingPanel.scale || 1;
    },
    /**
     * 设置纸张大小
     * @param type [A3, A4, A5, B3, B4, B5, other]
     * @param value {width,height} mm
     */
    setPaper(type, value) {
      try {
        if (Object.keys(this.paperTypes).includes(type)) {
          this.curPaper = { type, width: value.width, height: value.height };
          this.hiprintTemplate.setPaper(value.width, value.height);
        } else {
          this.curPaper = { type: 'other', width: value.width, height: value.height };
          this.hiprintTemplate.setPaper(value.width, value.height);
        }
      } catch (error) {
        this.$message.error(`操作失败: ${error}`);
      }
    },
    otherPaper() {
      const value = {};
      value.width = this.paperWidth;
      value.height = this.paperHeight;
      this.paperPopVisible = false;
      this.setPaper('other', value);
    },
    changeScale(big) {
      let scaleValue = this.scaleValue;
      if (big) {
        scaleValue += 0.1;
        if (scaleValue > this.scaleMax) scaleValue = 5;
      } else {
        scaleValue -= 0.1;
        if (scaleValue < this.scaleMin) scaleValue = 0.5;
      }
      if (this.hiprintTemplate) {
        // scaleValue: 放大缩小值, false: 不保存(不传也一样), 如果传 true, 打印时也会放大
        this.hiprintTemplate.zoom(scaleValue);
        this.scaleValue = scaleValue;
      }
    },
    rotatePaper() {
      if (this.hiprintTemplate) {
        this.hiprintTemplate.rotatePaper();
      }
    },
    preView() {
      this.$refs.preView.show(this.hiprintTemplate, printData, '240');
    },

    // 下载打印插件
    downloadPlugin() {
      const a = document.createElement('a');
      a.href = '@/hiprint/hiprint_win_x64.exe';
      a.download = 'hiprint_win_x64.exe';
      a.target = '_blank';
      a.click();
    },

    submitTempalte() {
      saleUser().then(res => {
        this.saleList = res.data;
      });
      this.save = true;
      this.saveForm.livePrint = this.livePrint;
    },

    print() {
      if (window.hiwebSocket.opened) {
        // console.log('this.hiprintTemplate ==> ', this.hiprintTemplate);

        // const printerList = this.hiprintTemplate.getPrinterList();
        // console.log(printerList);
        this.hiprintTemplate.print2(printData, { printer: this.livePrint, title: 'hiprint测试打印' });
        return;
      }
      this.$message.error('客户端未连接,无法直接打印');
    },
    clearPaper() {
      try {
        this.hiprintTemplate.clear();
      } catch (error) {
        this.$message.error(`操作失败: ${error}`);
      }
    },
    ippPrintAttr() {
      // 不知道打印机 ipp 情况， 可通过 '客户端' 获取一下
      const printerList = this.hiprintTemplate.getPrinterList();
      if (!printerList.length) return;
      const p = printerList[0];
      // 系统不同， 参数可能不同
      const url = p.options['printer-uri-supported'];
      // 测试 获取 ipp打印 支持参数
      hiprint.ippPrint(
        {
          url,
          // 打印机参数： {version,uri,charset,language}
          opt: {},
          action: 'Get-Printer-Attributes', // 获取打印机支持参数
          // ipp参数
          message: null
        },
        res => {
          // 执行的ipp 任务回调 / 错误回调
          // eslint-disable-next-line no-console
          console.log(res);
        },
        printer => {
          // ipp连接成功 回调 打印机信息
          // eslint-disable-next-line no-console
          console.log(printer);
        }
      );
    },
    ippPrintTest() {
      // 不知道打印机 ipp 情况， 可通过 '客户端' 获取一下
      const printerList = this.hiprintTemplate.getPrinterList();
      if (!printerList.length) return;
      const p = printerList[0];
      // 系统不同， 参数可能不同
      const url = p.options['printer-uri-supported'];
      // 测试 打印文本
      hiprint.ippPrint(
        {
          url,
          // 打印机参数： {version,uri,charset,language}
          opt: {},
          action: 'Print-Job',
          // ipp参数
          message: {
            'operation-attributes-tag': {
              'requesting-user-name': 'hiPrint', // 用户名
              'job-name': 'ipp Test Job', // 任务名
              'document-format': 'text/plain' // 文档类型
            },
            // data 需为 Buffer (客户端简单处理了string 转 Buffer), 支持设置 encoding
            // data 需为 Buffer (客户端简单处理了string 转 Buffer), 支持设置 encoding
            // data 需为 Buffer (客户端简单处理了string 转 Buffer), 支持设置 encoding
            // 其他 Uint8Array/ArrayBuffer   默认仅 使用 Buffer.from(data)
            // 其他 Uint8Array/ArrayBuffer   默认仅 使用 Buffer.from(data)
            // 其他 Uint8Array/ArrayBuffer   默认仅 使用 Buffer.from(data)
            // 其他 Uint8Array/ArrayBuffer   默认仅 使用 Buffer.from(data)
            data: 'test test test test test test test',
            encoding: 'utf-8' // 默认可不传
          }
        },
        res => {
          // 执行的ipp 任务回调 / 错误回调
          // eslint-disable-next-line no-console
          console.log(res);
        },
        printer => {
          // ipp连接成功 回调 打印机信息
          // eslint-disable-next-line no-console
          console.log(printer);
        }
      );
    },
    // 自定义 ipp 请求
    ippRequestTest() {
      const printerList = this.hiprintTemplate.getPrinterList();
      if (!printerList.length) return;
      const p = printerList[0];
      // 系统不同， 参数可能不同
      const url = p.options['printer-uri-supported'];
      // 详见： https://www.npmjs.com/package/ipp
      hiprint.ippRequest(
        {
          url,
          // 传入的数据 ipp.serialize 后 未做任何处理  打印内容 需要 Buffer
          // 传入的数据 ipp.serialize 后 未做任何处理  打印内容 需要 Buffer
          // 传入的数据 ipp.serialize 后 未做任何处理  打印内容 需要 Buffer
          data: {
            operation: 'Get-Printer-Attributes',
            'operation-attributes-tag': {
              // 测试发现 Request下列3个必须要有
              'attributes-charset': 'utf-8',
              'attributes-natural-language': 'zh-cn',
              'printer-uri': url
            }
          }
        },
        res => {
          // 执行的ipp 任务回调 / 错误回调
          // eslint-disable-next-line no-console
          console.log(res);
        }
      );
    },
    ippRequestPrint() {
      const printerList = this.hiprintTemplate.getPrinterList();
      if (!printerList.length) return;
      const p = printerList[0];
      // 系统不同， 参数可能不同
      const url = p.options['printer-uri-supported'];
      const str = 'ippRequestPrint ippRequestPrint ippRequestPrint';
      const array = new Uint8Array(str.length);
      for (let i = 0; i < str.length; i += 1) {
        array[i] = str.charCodeAt(i);
      }
      const testData = array.buffer;
      // 详见： https://www.npmjs.com/package/ipp
      hiprint.ippRequest(
        {
          url,
          // 传入的数据 ipp.serialize 后 未做任何处理  打印内容 需要 Buffer
          // 传入的数据 ipp.serialize 后 未做任何处理  打印内容 需要 Buffer
          // 传入的数据 ipp.serialize 后 未做任何处理  打印内容 需要 Buffer
          data: {
            operation: 'Print-Job',
            'operation-attributes-tag': {
              // 测试发现 Request下列3个必须要有
              'attributes-charset': 'utf-8',
              'attributes-natural-language': 'zh-cn',
              'printer-uri': url,
              'requesting-user-name': 'hiPrint', // 用户名
              'job-name': 'ipp Request Job', // 任务名
              'document-format': 'text/plain' // 文档类型
            },
            data: testData
          }
        },
        res => {
          // 执行的ipp 任务回调 / 错误回调
          // eslint-disable-next-line no-console
          console.log(res);
        }
      );
    },
    updateJson() {
      if (this.hiprintTemplate) {
        try {
          this.hiprintTemplate.update(JSON.parse(this.jsonIn));
        } catch (e) {
          this.$message.error(`更新失败: ${e}`);
        }
      }
    },
    exportJson() {
      if (this.hiprintTemplate) {
        this.jsonOut = JSON.stringify(this.hiprintTemplate.getJson() || {});
      }
    },
    setElsAlign(e) {
      this.hiprintTemplate.setElsAlign(e);
    },
    setElsSpace(h) {
      this.hiprintTemplate.setElsSpace(10, h);
    },
    getSelectEls() {
      const els = this.hiprintTemplate.getSelectEls();
      // eslint-disable-next-line no-console
      console.log(els);
    },
    updateFontSize() {
      this.hiprintTemplate.updateOption('fontSize', 12);
    },
    updateFontWeight() {
      this.hiprintTemplate.updateOption('fontWeight', 'bolder');
    }
  }
});
</script>

<style lang="less" scoped>
// 拖拽
.drag_item_box {
  height: 100%;
  padding: 6px;
  margin-bottom: 5px;
}

.drag_item_box > div {
  height: 100%;
  width: 100%;
  background-color: #fff;
  display: flex;
  justify-content: center;
  align-items: center;
}

.drag_item_box > div > a {
  text-align: center;
  text-decoration-line: none;
}

.drag_item_box > div > a > span {
  font-size: 28px;
}

.drag_item_box > div > a > p {
  margin: 0;
}

.drag_item_title {
  font-size: 16px;
  padding: 12px 6px 0 6px;
  font-weight: bold;
  margin-bottom: 10px;
}

// 默认图片

// 辅助线样式
:deep(.toplineOfPosition) {
  border: 0;
  border-top: 1px dashed purple;
}

:deep(.bottomlineOfPosition) {
  border: 0;
  border-top: 1px dashed purple;
}

:deep(.leftlineOfPosition) {
  border: 0;
  border-left: 1px dashed purple;
}

:deep(.rightlineOfPosition) {
  border: 0;
  border-left: 1px dashed purple;
}

// 设计容器
.card-design {
  overflow: hidden;
  overflow-x: auto;
  overflow-y: auto;
}

.ant-btn {
  line-height: 1.499;
  position: relative;
  display: inline-block;
  font-weight: 400;
  white-space: nowrap;
  text-align: center;
  background-image: none;
  border: 1px solid transparent;
  -webkit-box-shadow: 0 2px 0 rgba(0, 0, 0, 0.015);
  box-shadow: 0 2px 0 rgba(0, 0, 0, 0.015);
  cursor: pointer;
  -webkit-transition: all 0.3s cubic-bezier(0.645, 0.045, 0.355, 1);
  transition: all 0.3s cubic-bezier(0.645, 0.045, 0.355, 1);
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
  -ms-touch-action: manipulation;
  touch-action: manipulation;
  height: 32px;
  padding: 0 15px;
  font-size: 14px;
  border-radius: 4px;
  color: rgba(0, 0, 0, 0.65);
  background-color: #fff;
  border-color: #d9d9d9;
}

.ant-btn-primary {
  color: #fff;
  background-color: #1890ff;
  border-color: #1890ff;
}

.print-left-button {
  width: 100%;
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  /* 创建两列的网格 */
  grid-gap: 5px;
  /* 网格之间和网格行之间的间距 */
}
</style>
