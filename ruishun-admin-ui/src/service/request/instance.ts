// import { useMessage } from 'naive-ui';
// import { useRouter } from 'vue-router';
import axios from 'axios';
import type { AxiosResponse, AxiosError, AxiosInstance, AxiosRequestConfig } from 'axios';
import { REFRESH_TOKEN_CODE } from '@/config';
import { useAuthStore, useAppStore } from '@/store';
import {
  localStg,
  handleAxiosError,
  handleBackendError,
  handleResponseError,
  handleServiceResult,
  transformRequestData,
  tansParams
} from '@/utils';
type RefreshRequestQueue = (config: AxiosRequestConfig) => void;

// const router = useRouter();
/**
 * 封装axios请求类
 * @author Soybean<honghuangdc@gmail.com>
 */
export default class CustomAxiosInstance {
  instance: AxiosInstance;

  backendConfig: Service.BackendResultConfig;

  isRefreshing: boolean;

  retryQueues: RefreshRequestQueue[];

  /**
   *
   * @param axiosConfig - axios配置
   * @param backendConfig - 后端返回的数据配置
   */
  constructor(
    axiosConfig: AxiosRequestConfig,

    backendConfig: any = {
      code: 'code',
      data: 'data',
      msg: 'msg',
      successCode: 200
    }
  ) {
    this.backendConfig = backendConfig;
    this.instance = axios.create(axiosConfig);
    this.setInterceptor();
    this.isRefreshing = false;
    this.retryQueues = [];
  }

  /** 设置请求拦截器 */
  setInterceptor() {
    this.instance.interceptors.request.use(
      async config => {
        const handleConfig = { ...config };
        if (handleConfig.headers) {
          // 数据转换
          const contentType = handleConfig.headers['Content-Type'] as UnionKey.ContentType;
          handleConfig.data = await transformRequestData(handleConfig.data, contentType);
          // 设置token
          handleConfig.headers.Authorization = localStg.get('token') || '';
        }
        // get参数拼接
        if (handleConfig.method === 'get' && handleConfig.params) {
          let url = `${handleConfig.url}?${tansParams(handleConfig.params)}`;
          url = url.slice(0, -1);
          handleConfig.params = {};
          handleConfig.url = url;
          // console.log(config.url)
        }
        const app = useAppStore();
        if (handleConfig.method !== 'get') {
          app.isLoading = true;
        }

        return handleConfig;
      },
      (axiosError: AxiosError) => {
        const error = handleAxiosError(axiosError);
        return handleServiceResult(error, null);
      }
    );
    // 响应数据拦截
    this.instance.interceptors.response.use(
      (async response => {
        // const message = useMessage();
        // console.log(response, 'response');
        const { status } = response;
        const auth = useAuthStore();
        const app = useAppStore();
        app.isLoading = false;
        if (status === 200 || status < 300 || status === 304) {
          const backend = response.data;

          // const { code, data, successCode } = this.backendConfig;
          // console.log(code, data, successCode);
          if (
            backend.code === this.backendConfig.successCode ||
            response.request.responseType === 'blob' ||
            response.request.responseType === 'arraybuffer'
          ) {
            return backend;
          }
          if (backend.code === 401) {
            // localStorage.clear();
            localStorage.removeItem('token');
            localStorage.removeItem('userInfo');
            // auth.resetAuthStore();
            location.reload();
            // location.href="/login";
            const error = handleBackendError(backend, this.backendConfig);
            return handleServiceResult(error, backend.msg);
          }
          if (backend.code !== 200) {
            const error = handleBackendError(backend, this.backendConfig);
            return handleServiceResult(error, backend.msg);
          }

          // token失效, 刷新token
          if (REFRESH_TOKEN_CODE.includes(backend.code)) {
            auth.resetAuthStore();
            location.href = '/';
            const error = handleBackendError(backend, this.backendConfig);
            return handleServiceResult(error, null);
          }
          const error = handleBackendError(backend, this.backendConfig);
          return handleServiceResult(error, null);
        }
        const error = handleResponseError(response);
        return handleServiceResult(error, null);
      }) as (response: AxiosResponse<any, any>) => Promise<AxiosResponse<any, any>>,
      (axiosError: AxiosError) => {
        const app = useAppStore();
        app.isLoading = false;
        const error = handleAxiosError(axiosError);
        return handleServiceResult(error, null);
      }
    );
  }
}

// eslint-disable-next-line max-params
