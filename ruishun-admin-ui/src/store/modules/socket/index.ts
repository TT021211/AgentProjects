import { defineStore } from 'pinia';

interface socketState {
  socket: string | null;
  ws: any;
  connected: boolean;
  wsUrl: string;
  msg: any;
  heartbeatInterval: any;
  heartbeatTimeout: number;
}
let wsUrl = `ws://`;
if (typeof window !== 'undefined') {
  const protocol = window.location.protocol;
  if (protocol === 'https:') {
    wsUrl = `wss://`;
  }
}
export const useSocketStore = defineStore('socket-store', {
  state: (): socketState => ({
    socket: localStorage.getItem('socket') || null,
    ws: null,
    connected: false,
    wsUrl,
    msg: {},
    heartbeatInterval: null,
    heartbeatTimeout: 30000 // 30秒的心跳间隔
  }),
  actions: {
    setOpenSocket(socket: string) {
      // const url = `${wsUrl + socket}?userId=${socket}`;
      const url = `${wsUrl + socket}`;
      this.ws = new WebSocket(url);
      this.ws.onopen = () => {
        this.connected = true;
        this.socket = socket;
        localStorage.setItem('socket', socket);
        console.log('连接服务器端成功');
        this.ws.onmessage = res => {
          console.log('res ==> ', res);
          this.msg = res.data;
        };

        this.ws.onclose = () => {
          console.log('WebSocket connection closed. Reconnecting...');
          this.stopHeartbeat();
          this.reconnect();
        };
      };
    },
    setCloseScoket() {
      if (this.ws) {
        this.ws.close();
        this.ws = null;
        this.connected = false;
        this.socket = '';
        localStorage.removeItem('socket');
        this.messageCount = 0;
        console.log('关闭服务器端成功');
      }
    },

    setMsg(data) {
      console.log('data setMsg ==> ', data);
      this.msg = data;
    },

    startHeartbeat() {
      this.heartbeatInterval = setInterval(() => {
        if (this.ws.readyState === WebSocket.OPEN) {
          this.ws.send('ping'); // 发送一个心跳包
        }
      }, this.heartbeatTimeout);
    },

    stopHeartbeat() {
      clearInterval(this.heartbeatInterval);
    },

    reconnect() {
      setTimeout(() => {
        this.setOpenSocket(this.socket); // 重新绑定事件处理
      }, 5000); // 5秒后尝试重连
    }
  }
});
