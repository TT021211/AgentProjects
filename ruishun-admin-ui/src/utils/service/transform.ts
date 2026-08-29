import { stringify } from 'qs';
import FormData from 'form-data';
import { isArray, isFile } from '../common';

/**
 * 请求数据的转换
 * @param requestData - 请求数据
 * @param contentType - 请求头的Content-Type
 */
export async function transformRequestData(requestData: any, contentType?: UnionKey.ContentType) {
  // application/json类型不处理
  let data = requestData;
  // form类型转换
  if (contentType === 'application/x-www-form-urlencoded') {
    data = stringify(requestData);
  }
  // form-data类型转换
  if (contentType === 'multipart/form-data') {
    data = await handleFormData(requestData);
  }

  return data;
}

async function handleFormData(data: Record<string, any>) {
  const formData = new FormData();
  const entries = Object.entries(data);

  entries.forEach(async ([key, value]) => {
    const isFileType = isFile(value) || (isArray(value) && value.length && isFile(value[0]));

    if (isFileType) {
      await transformFile(formData, key, value);
    } else {
      formData.append(key, value);
    }
  });

  return formData;
}

/**
 * 接口为上传文件的类型时数据转换
 * @param key - 文件的属性名
 * @param file - 单文件或多文件
 */
async function transformFile(formData: FormData, key: string, file: File[] | File) {
  if (isArray(file)) {
    // 多文件
    await Promise.all(
      (file as File[]).map(item => {
        formData.append(key, item);
        return true;
      })
    );
  } else {
    // 单文件
    formData.append(key, file);
  }
}
// get请求传递参数拼接
export function tansParams(params) {
  // console.log(params)
  let result = '';
  for (const propName of Object.keys(params)) {
    const value = params[propName];
    const part = `${encodeURIComponent(propName)}=`;
    if (value !== null && value !== '' && typeof value !== 'undefined') {
      if (typeof value === 'object') {
        for (const key of Object.keys(value)) {
          // eslint-disable-next-line max-depth
          if (value[key] !== null && value[key] !== '' && typeof value[key] !== 'undefined') {
            const param = `${propName}[${key}]`;
            const subPart = `${encodeURIComponent(param)}=`;
            result += `${subPart + encodeURIComponent(value[key])}&`;
          }
        }
      } else {
        result += `${part + encodeURIComponent(value)}&`;
      }
    }
  }

  return result;
}
// 添加日期范围
export function addDateRange(params, dateRange, propName) {
  const search = params;
  search.params =
    typeof search.params === 'object' && search.params !== null && !Array.isArray(search.params) ? search.params : {};
  // eslint-disable-next-line no-param-reassign
  dateRange = Array.isArray(dateRange) ? dateRange : [];
  if (typeof propName === 'undefined') {
    search.params.beginTime = dateRange[0];
    search.params.endTime = dateRange[1];
  } else {
    search.params[`begin${propName}`] = dateRange[0];
    search.params[`end${propName}`] = dateRange[1];
  }
  return search;
}
