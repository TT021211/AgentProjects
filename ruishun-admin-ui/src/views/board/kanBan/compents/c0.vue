<template>
  <div>
    <div>
      <div  class="main_container">
        <div class="title">
          <span class="first">时间</span>
          <span class="second">产品</span>
          <span class="four">数量</span>
          <span class="third">类型</span>
        </div>
        <div ref="scrollContainer" class="scroll_container">
          <div v-for="(item, index) in listData" :key="item.id" class="scroll_item"
               :class="{ 'success-row': index % 2 == 0, 'warning-row': index % 2 != 0 }">
            <span class="first">{{ item.time }}</span>
            <span class="second">{{ item.subject }}</span>
            <span class="four">{{ item.number }}</span>
            <span class="third"
                  :style="{ color: item.type === '出库' ? '#FF5333FF' : item.type === '入库' ? '#FFF000FF' : '#35F43BFF' }">{{
                item.type }}</span>
          </div>
          <div id="empty" class="empty"><span>暂无数据</span></div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { EventSourcePolyfill } from 'event-source-polyfill';

export default {
  name: 'c0',
  props: {
    enterprise: {
      type: String,
      required: true
    },
    sseUrlSuffix: {
      type: String,
      required: true
    },
    module: {
      type: String,
      required: true
    },
    token: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      // 组件功能描述
      description: '实时展示出入库数据，获取最新的20条出入库明细',
      // 数据类型
      business: 'getInAndOutStock',
      // 展示数据列表
      listData: [],
      // sse对象
      sse: null,
      sseUrl: ''
    };
  },
  mounted() {
    // 数据接口地址
    this.sseUrl = `${this.sseUrlSuffix}/${this.module}/${this.business}`;
    this.sse = new EventSourcePolyfill(this.sseUrl, {
      headers: {
        'Tenant': this.enterprise,
        'Authorization': 'Bearer ' + this.token
      }
    })
    console.log('start listener')
    this.addListener();
  },
  methods: {
    addListener() {
      // 监听连接打开事件
      this.sse.addEventListener("open", function(e) {
        console.log("SSE连接建立");
      });
      // 监听连接,更新数据
      this.sse.addEventListener(`${this.module}/${this.business}/${this.enterprise}`, (e) => {
        const newData = JSON.parse(e.data);
        this.listData = newData;
        // 遍历新数据数组
        // newData.forEach(item => {
        //   // 检查每个新元素是否已经存在于listData中
        //   const existingIndex = this.listData.findIndex(existingItem => JSON.stringify(existingItem) === JSON.stringify(item));
        //   if (existingIndex === -1) { // 如果不存在，则添加到数组中
        //     this.listData.push(item);
        //   }
        // });
        // // 确保数组长度不超过20
        // while (this.listData.length > 20) {
        //   this.listData.shift(); // 移除数组的第一个元素
        // }
        console.log("收到新数据", this.listData);
      });
      // 监听连接失败，重拨
      this.sse.addEventListener("error", function(err) {
        if (this.sse.readyState === EventSource.CLOSED) {
          // 如果连接已经关闭，那么尝试重新连接
          console.log("5秒后尝试重新连接...");
          setTimeout(() => {
            this.sse = new EventSource(this.sseUrl);
            this.addListener();
          }, 5000);
        }
      });
    }
  },
  beforeDestroy() {
    // 组件销毁前关闭 SSE 连接
    if (this.sse) {
      this.sse.close();
    }
  }
};
</script>

<style scoped lang="scss">

.main_container {
  width: 50vw;
  height: 245px;
  cursor: default
}

.empty {
  span {
    color: #ffffff66;
    margin-top: 115px;
    margin-left: 4px;
    display: block;
  }

  display: none;
  position: absolute;
  top: 20%;
  left: 50%;
  width: 100px;
  height: 100px;
}

.scroll_container {
  width: 100%;
  height: 200px;
  overflow: hidden;
  color: #fff;
}

.title {
  font-size: 20px;
  color: #90B8DDFF;
  background-color: transparent;
  line-height: 40px;
  height: 40px;
}

.scroll_item {
  height: 40px;
  line-height: 40px;
  display: flex;
}

.first {
  width: 20%;
  display: inline-block;
  text-align: center;
}

.second {
  width: 30%;
  color: #90B8DDFF;
  display: inline-block;
  text-align: center;
}

.second {
  width: 30%;
  display: inline-block;
  text-align: center;
}

.four {
  width: 30%;
  display: inline-block;
  text-align: center;
}

.third {
  width: 20%;
  display: inline-block;
  text-align: center;
}


.scroll_container .second {
  cursor: pointer
}


.warning-row {
  background: #021E32FF;

  td {
    color: rgba(255, 255, 255, 1);
  }
}

.success-row {
  background: #003B5100;

  td {
    color: rgba(255, 195, 0, 1);
  }
}
</style>
