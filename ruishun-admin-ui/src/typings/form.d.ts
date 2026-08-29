declare namespace deptManagement {
  interface Dept {
    /** 用户id */
    index: string;
    /** 用户名 */
    code: string | null;
    /** 用户年龄 */
    name: string | null;
    /**
     * 用户性别
     * - 0: 女
     * - 1: 男
     */
    sort: number | null;
    /** 用户手机号码 */
    createTime: string | null;

    /**
     * 用户状态
     * - 1: 正常
     * - 2: 禁用

     */
    status: '0' | '1' | null;
  }
}
