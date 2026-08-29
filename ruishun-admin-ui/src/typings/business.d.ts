/** 用户相关模块 */
declare namespace Auth {
  /**
   * 用户角色类型(前端静态路由用角色类型进行路由权限的控制)
   * - super: 超级管理员(该权限具有所有路由数据)
   * - admin: 管理员
   * - user: 用户
   */
  // type RoleType = 'super' | 'admin' | 'user';
  type RoleType = 'common' | 'admin';
  /** 用户信息 */
  interface UserInfo {
    /** 用户id */
    userId: string;
    /** 用户名 */
    userName: string;
    /** 用户角色类型 */
    userRole: RoleType;
    /** 用户头像 */
    avatar: '';
    nickName?: string;
    roles: [];
    // 用户权限
    permissions: [];
    sex: string;
    status: string;
  }
}

declare namespace UserManagement {
  interface User extends ApiUserManagement.User {
    /** 序号 */
    index: number;
    /** 表格的key（id） */
    key: string;
  }

  /**
   * 用户性别
   * - 0: 女
   * - 1: 男
   */
  type GenderKey = NonNullable<User['sex']>;

  /**
   * 用户状态
   * - 0: 启用
   * - 1: 禁用

   */
  type UserStatusKey = NonNullable<User['status']>;
}

/**
 * 物料分类管理
 */
declare namespace ItemTypeManagement {
  interface ItemType extends ApiItemTypeManagement.ItemType {
    /** 序号 */
    index: number;
    /** 表格的key（id） */
    key: string;
    /** 父分类名称 */
    parentTypeName: string;
    children: ItemType[];
  }

  // /**
  //  * 物料分类
  //  * - item: 物料
  //  * - product: 产品
  //  */
  // type ItemOrProductKey = NonNullable<ItemType['itemOrProduct']>;

  /**
   * 状态
   * - 1: 启用
   * - 0: 禁用
   */
  type EnableFlagKey = NonNullable<ItemType['ifEnable']>;
}

/**
 * 物料管理
 */
declare namespace ItemManagement {
  interface Item extends ApiItemManagement.Item {
    /** 序号 */
    index: number;
    /** 表格的key（id） */
    key: string;
  }

  /**
   * 物料分类
   * - item: 物料
   * - product: 产品
   */
  type ItemOrProductKey = NonNullable<Item['itemOrProduct']>;

  /**
   * 状态
   * - Y: 启用
   * - N: 禁用
   */
  type EnableFlagKey = NonNullable<Item['enableFlag']>;

  /**
   * 是否设置安全库存
   * - Y: 启用
   * - N: 禁用
   */
  type safeStockFlagKey = NonNullable<Item['safeStockFlag']>;
}

// 角色管理
declare namespace RoleManagement {
  interface RoleType extends ApiRoleManagement.RoleType {
    /** 序号 */
    index: number;
    /** 表格的key（id） */
    key: string;
  }
  type RoleStatusKey = NonNullable<RoleType['status']>;
}
// 分配用户权限管理
declare namespace UserAuthManagement {
  interface UserAuthType extends ApiUserAuthManagement.UserAuthType {
    /** 序号 */
    index: number;
    /** 表格的key（id） */
    key: string;
  }
  type UserAuthStatusKey = NonNullable<UserAuthType['status']>;
}

// 分配角色权限管理
declare namespace RoleAuthManagement {
  interface RoleAuthType extends ApiRoleAuthManagement.RoleAuthType {
    /** 序号 */
    index: number;
    /** 表格的key（id） */
    key: string;
  }
  type RoleAuthStatusKey = NonNullable<RoleAuthType['status']>;
}

// 部门管理
declare namespace DeptManagement {
  interface DeptType extends ApiDeptManagement.DeptType {
    /** 序号 */
    index: number;
    /** 表格的key（id） */
    key: string;
  }
  type DeptStatusKey = NonNullable<DeptType['status']>;
}

// 岗位管理
declare namespace PostManagement {
  interface PostType extends ApiPostManagement.PostType {
    /** 序号 */
    index: number;
    /** 表格的key（id） */
    key: string;
  }
  type PostStatusKey = NonNullable<PostType['status']>;
}

// 菜单管理
declare namespace MenuManagement {
  interface MenuType extends ApiMenuManagement.MenuType {
    /** 序号 */
    index: number;
    /** 表格的key（id） */
    key: string;
    menuType: string;
  }
  type MenuStatusKey = NonNullable<MenuType['status']>;
}

// 登录管理
declare namespace LoginManagement {
  interface LoginType extends ApiLoginManagement.LoginType {
    /** 序号 */
    index: number;
    /** 表格的key（id） */
    key: string;
  }
  type LoginStatusKey = NonNullable<LoginType['status']>;
}

// 操作日志
declare namespace OperaManagement {
  interface OperaType extends ApiOperaManagement.OperaType {
    /** 序号 */
    index: number;
    /** 表格的key（id） */
    key: string;
  }
  type OperaStatusKey = NonNullable<OperaType['status']>;
  type OperaBusinessTypeKey = NonNullable<OperaType['businessType']>;
}
