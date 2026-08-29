import { useMessage } from 'naive-ui';
import type { AxiosRequestConfig } from 'axios';
import { saveAs } from 'file-saver';
import { useAuthStore } from '@/store';
import { localStg, blobValidate } from '@/utils';
import { fetchUpdateToken } from '../api/system/system';
import errorCode from './errorCode';
import { request } from './index';

/**
 * 刷新token
 * @param axiosConfig - token失效时的请求配置
 */
export async function handleRefreshToken(axiosConfig: AxiosRequestConfig) {
  const { resetAuthStore } = useAuthStore();
  const refreshToken = localStg.get('refreshToken') || '';
  const { data } = await fetchUpdateToken(refreshToken);
  if (data) {
    localStg.set('token', data.token);
    // localStg.set('refreshToken', data.refreshToken);

    const config = { ...axiosConfig };
    if (config.headers) {
      config.headers.Authorization = data.token;
    }
    return config;
  }

  resetAuthStore();
  return null;
}

const message = useMessage();
// eslint-disable-next-line max-params
export async function download(url, params, filename, config) {
  // eslint-disable-next-line no-console
  console.log(url, params, filename);
  return request
    .post(url, params, {
      transformRequest: [
        params2 => {
          return params2;
        }
      ],
      headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
      responseType: 'blob',
      ...config
    })
    .then(async data => {
      const isBlob = blobValidate(data);
      if (isBlob) {
        const blob = new Blob([data]);

        saveAs(blob, filename);
      } else {
        const resText = await data.text();
        const rspObj = JSON.parse(resText);
        const errMsg = errorCode[rspObj.code] || rspObj.msg || errorCode.default;
        message.error(errMsg);
      }
    })
    .catch(() => {
      message.error('下载文件出现错误，请联系管理员！');
    });
}
// 导出
// eslint-disable-next-line max-params
export async function downloadList(url, params) {
  return request
    .post(url, params, {
      responseType: 'blob',
      headers: {
        'Content-Type': 'application/json',
        Accept: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
      }
    })
    .then(async response => {
      const fileName = '装托表数据.xlsx';
      // const disposition = response.headers['content-disposition'];
      // if (disposition && disposition.includes('filename=')) {
      //   fileName = decodeURIComponent(disposition.split('filename=')[1].replace(/"/g, ''));
      // }
      const blob = new Blob([response.data], {
        type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
      });
      const urls = window.URL.createObjectURL(blob);
      const link = document.createElement('a');
      link.href = urls;
      link.setAttribute('download', fileName);
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
      window.URL.revokeObjectURL(url);
      window.$message?.success('文件下载成功');
    })
    .catch(() => {
      message.error('下载文件出现错误，请联系管理员！');
    });
}
