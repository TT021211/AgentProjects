declare namespace search {
  class public {
    pageNum: number;

    pageSize: number;

    total: number;
  }
}

// 定时任务
declare namespace job {
  interface searchForm extends search.public {
    jobName: string;
    jobGroup: string | null;
    status: string | null;
  }

  interface addForm {
    concurrent: string; // 0 允许 ；1禁止
    cronExpression: string;
    misfirePolicy: string; // 执行策略
    jobName: string;
    invokeTarget: string;
    jobGroup: string | null;
    jobId: number | null;
    status: string;
  }

  interface FormModel {
    index: number; // 0 允许 ；1禁止
    jobGroup: string;
    jobName: string;
    createTime: string;
    cronExpression: string;
    nextValidTime: string;
    invokeTarget: string;
    status: string;
    concurrent: string;
    misfirePolicy: string;
  }

  interface TableList {
    jobId: number;
    concurrent: string; // 0 允许 ；1禁止
    cronExpression: string;
    misfirePolicy: string; // 执行策略
    jobName: string;
    invokeTarget: string;
    jobGroup: string | null;
    status: string;
  }
}

// 调度日志
declare namespace jobLog {
  interface searchForm extends search.public {
    jobName: string;
    jobGroup: string | null;
    status: string | null;
    'params[beginTime]': string | null;
    'params[endTime]': string | null;
  }

  interface addForm {
    concurrent: string; // 0 允许 ；1禁止
    cronExpression: string;
    misfirePolicy: string; // 执行策略
    jobName: string;
    invokeTarget: string;
    jobGroup: string | null;
    jobId: number | null;
    status: string;
  }

  interface FormModel {
    index: number; // 0 允许 ；1禁止
    jobGroup: string;
    jobName: string;
    createTime: string;
    cronExpression: string;
    nextValidTime: string;
    invokeTarget: string;
    status: string;
    concurrent: string;
    misfirePolicy: string;
  }

  interface TableList {
    jobLogId: number;
    jobName: string;
    invokeTarget: string;
    jobMessage: string;
    jobGroup: string | null;
    status: string;
    createTime: string;
  }
}

// 调度日志
declare namespace connectSocket {
  interface searchForm extends search.public {
    dictLabel: string;
    dictValue: string;
    dictType: string;
  }

  interface TableList {
    dictLabel: string;
    dictValue: string;
  }
}
