import { $t } from '@/locales';
import { transformObjectToOption } from './_shared';

export const loginModuleLabels: Record<UnionKey.LoginModule, string> = {
  'pwd-login': $t('page.login.pwdLogin.title'),
  'code-login': $t('page.login.codeLogin.title'),
  register: $t('page.login.register.title'),
  'reset-pwd': $t('page.login.resetPwd.title'),
  'bind-wechat': $t('page.login.bindWeChat.title'),
  'company-login': $t('page.login.companyLogin.title')
};

export const userRoleLabels: Record<Auth.RoleType, string> = {
  // super: $t('page.login.pwdLogin.superAdmin'),
  admin: $t('page.login.pwdLogin.admin'),
  common: $t('page.login.pwdLogin.user')
};
export const userRoleOptions = transformObjectToOption(userRoleLabels);

/** 用户性别 */
export const genderLabels: Record<UserManagement.GenderKey, string> = {
  0: '女',
  1: '男'
};
export const genderOptions = transformObjectToOption(genderLabels);

/** 用户状态 */
export const userStatusLabels: Record<UserManagement.UserStatusKey, string> = {
  0: '启用',
  1: '禁用'
};
export const userStatusOptions = transformObjectToOption(userStatusLabels);

// /** 物料OR产品 */
// export const itemOrProductLabels: Record<ItemTypeManagement.ItemOrProductKey, string> = {
//   item: '物料',
//   product: '产品'
// };

// export const itemOrProductOptions = transformObjectToOption(itemOrProductLabels);

/** 是否启用 */
export const enableFlagLabels: Record<ItemTypeManagement.EnableFlagKey, string> = {
  1: '是',
  0: '否'
};

export const enableFlagOptions = transformObjectToOption(enableFlagLabels);

/** 角色状态 */
export const roleStatusLabels: Record<RoleManagement.RoleStatusKey, string> = {
  0: '启用',
  1: '禁用'
};
export const roleStatusOptions = transformObjectToOption(roleStatusLabels);

/** 用户权限状态 */
export const userAuthStatusLabels: Record<UserAuthManagement.UserAuthStatusKey, string> = {
  0: '正常',
  1: '已禁用'
};
export const userAuthStatusOptions = transformObjectToOption(userAuthStatusLabels);

/** 角色权限状态 */
export const roleAuthStatusLabels: Record<RoleAuthManagement.RoleAuthStatusKey, string> = {
  0: '正常',
  1: '已禁用'
};
export const roleAuthStatusOptions = transformObjectToOption(roleAuthStatusLabels);

/** 部门状态 */
export const deptStatusLabels: Record<DeptManagement.DeptStatusKey, string> = {
  0: '正常',
  1: '已禁用'
};
export const deptStatusOptions = transformObjectToOption(deptStatusLabels);

/** 岗位状态 */
export const postStatusLabels: Record<PostManagement.PostStatusKey, string> = {
  0: '正常',
  1: '已禁用'
};
export const postStatusOptions = transformObjectToOption(postStatusLabels);

/** 菜单状态 */
export const menuStatusLabels: Record<MenuManagement.MenuStatusKey, string> = {
  0: '正常',
  1: '已禁用'
};
export const menuStatusOptions = transformObjectToOption(menuStatusLabels);

/** 操作状态 */
export const operaStatusLabels: Record<OperaManagement.OperaStatusKey, string> = {
  0: '成功',
  1: '失败'
};
export const operaStatusOptions = transformObjectToOption(operaStatusLabels);

/** 操作类型 */
export const operaBusinessLabels: Record<OperaManagement.OperaBusinessTypeKey, string> = {
  0: '其他',
  1: '新增',
  2: '修改',
  3: '删除',
  4: '授权',
  5: '导出',
  6: '导入',
  7: '退出',
  9: '清空数据'
};
export const operaBusinessOptions = transformObjectToOption(operaBusinessLabels);

/** 登录状态 */
export const loginStatusLabels: Record<LoginManagement.LoginStatusKey, string> = {
  0: '成功',
  1: '失败'
};
export const loginStatusOptions = transformObjectToOption(loginStatusLabels);
