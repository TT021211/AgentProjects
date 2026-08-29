import { request } from '../../request';

// 获取自动生成代码规则列表
export function fetchAutoCodeRuleList(data) {
  return request.get('/system/system/autocode/rule/list', { params: data });
}

// 新增自动生成代码规范列表
export function addAutoCodeRule(data) {
  return request.post('/system/system/autocode/rule', data);
}
// 编辑自动生成代码规范列表
export function editAutoCodeRule(data) {
  return request.put('/system/system/autocode/rule', data);
}

// 删除自动生成代码规范列表
export function deleteAutoCodeRule(data: any) {
  return request.delete(`/system/system/autocode/rule/${data}`);
}
