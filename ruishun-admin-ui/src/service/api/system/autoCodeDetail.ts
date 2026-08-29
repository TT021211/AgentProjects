import { request } from '../../request';

// 获取自动代码详情
export function getAutoCodeDetail(data: any) {
  return request.get(`/system/system/autocode/part/list`, { params: data });
}

// 删除自动获取代码详情
export function deleteAutoCodeDetail(data: any) {
  return request.delete(`/system/system/autocode/part/${data}`);
}

// 新增自动获取代码详情
export function addAutoCodeDetail(data: any) {
  return request.post(`/system/system/autocode/part`, data);
}
// 获取自动获取代码详情
export function getAutoCodeDetailById(id: number) {
  return request.get(`/system/system/autocode/part/${id}`);
}

// 修改自动获取代码详情
export function updateAutoCodeDetail(data: any) {
  return request.put(`/system/system/autocode/part`, data);
}

// 获取选项字典列表
export function getOptionList(code: string) {
  return request.get(`/system/dict/data/type/${code}`);
}

// 生成编码
export function genCode(ruleCode: string) {
  return request.get(`/system/system/autocode/getByAjaxResult/${ruleCode}/${ruleCode}`);
}
