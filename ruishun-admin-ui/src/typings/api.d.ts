// 后端接口返回的数据类型

/** 后端返回的用户权益相关类型 */
declare namespace ApiAuth {
  /** 返回的token和刷新token */
  interface Token {
    access_token: any;
    // refreshToken: string;
  }
  /** 返回的用户信息 */
  type UserInfo = Auth.UserInfo;
}

/** 后端返回的路由相关类型 */
declare namespace ApiRoute {
  /** 后端返回的路由数据类型 */
  interface Route {
    /** 动态路由 */
    routes: AuthRoute.Route[];
    /** 路由首页对应的key */
    home: AuthRoute.AllRouteKey;
  }
}

declare namespace ApiUserManagement {
  interface User {
    id: number;
    name: string | null;
    nickName: string | null;
    department: string | null;
    createTime: string | null;
    phone: string;
    status: string;
    jobNumber: string | null;
    sex: string;
  }
}

/**
 * 物料分类管理
 */
declare namespace ApiItemTypeManagement {
  interface ItemType {
    /** 分类id */
    id: number;
    /** 分类名称 */
    itemTypeName: string | null;
    /** 分类编码 */
    itemTypeCode: string | null;
    /**
     * 分类状态
     * - 1: 启用
     * - 0: 未启用
     */
    ifEnable: string | number;
    /** 创建时间 */
    createTime: string;
    /** 排序 */
    orderNum: number;
    /** 父分类ID */
    parentTypeId: number;
    /** 树形结构 */
    ancestors: string;
  }
}

/**
 * 物料管理
 */
declare namespace ApiItemManagement {
  interface Item {
    /** 物料id */
    itemId: number;
    /** 物料名称 */
    itemName: string | null;
    /** 物料编码 */
    itemCode: string | null;
    /** 规格型号 */
    specification: string;
    /** 单位 */
    unitOfMeasure: string;
    /** 产品or物料 */
    itemOrProduct: string;
    /** 物料类型ID */
    itemTypeId: number;
    /** 物料类型名称 */
    itemTypeName: string;
    /** 物料类型编码 */
    itemTypeCode: string;
    /** 最低库存量 */
    minStock: number;
    /** 最大库存量 */
    maxStock: number;
    /**
     * 物料状态
     * - Y: 启用
     * - N: 未启用
     */
    enableFlag: string;
    /**
     * 设置安全库存状态
     * - Y: 启用
     * - N: 未启用
     */
    safeStockFlag: string;
    /** 创建时间 */
    createTime: string;
    /** 创建者 */
    createBy: string;
    /** 更新时间 */
    updateTime: string;
    /** 更新者 */
    updateBy: string;
    /** 备注 */
    remark: string;
  }
}

/**
 * 角色管理
 */
declare namespace ApiRoleManagement {
  interface RoleType {
    roleId: number;
    roleName: string | null;
    roleKey: string;
    roleSort: number;
    createTime: string | null;
    status: string;
  }
}

/**
 * 分配用户权限管理
 */
declare namespace ApiUserAuthManagement {
  interface UserAuthType {
    userId: number;
    userName: string | null;
    nickName: string | null;
    email: string | null;
    phonenumber: string | null;
    createTime: string | null;
    status: string;
  }
}

/**
 * 分配角色权限管理
 */
declare namespace ApiRoleAuthManagement {
  interface RoleAuthType {
    roleId: number;
    roleName: string | null;
    roleKey: string;
    createTime: string | null;
    status: string;
  }
}

/**
 * 部门管理
 */
declare namespace ApiDeptManagement {
  interface DeptType {
    deptId: number;
    deptName: string | null;
    orderNum: number;
    createTime: string | null;
    status: string;
  }
}

/**
 * 岗位管理
 */
declare namespace ApiPostManagement {
  interface PostType {
    postId: number;
    postCode: string;
    postName: string;
    postSort: number;
    createTime: string | null;
    status: string;
  }
}

/**
 * 菜单管理
 */
declare namespace ApiMenuManagement {
  interface MenuType {
    menuId: number;
    icon: string;
    perms: string;
    component: string;
    menuName: string;
    createTime: string | null;
    status: string;
  }
}
/**
 * 登录管理
 */
declare namespace ApiLoginManagement {
  interface LoginType {
    index: number;
    tokenId: string;
    userName: string;
    ipaddr: string;
    loginTime: string | null;
    sortOrder: boolean;
    status: number;
  }
}

/**
 * 操作日志管理
 */
declare namespace ApiOperaManagement {
  interface OperaType {
    operId: number;
    title: string;
    businessType: number;
    requestMethod: string;
    operName: string;
    operIp: string;
    costTime: number;
    operTime: string | null;
    status: number;
  }
}
