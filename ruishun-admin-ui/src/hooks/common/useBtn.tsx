// import { useRoute } from 'vue-router';
import { ref } from 'vue';
import { NButton, useDialog, NIcon } from 'naive-ui';
import type { Size } from 'naive-ui/es/select/src/interface';
import {
  EyeOutline,
  Send,
  TrashOutline,
  Search,
  RefreshSharp,
  ReturnUpBack,
  AddOutline,
  DocumentTextOutline,
  CreateOutline,
  SettingsOutline,
  LockClosedOutline,
  LockOpenOutline,
  CloudDownloadOutline,
  CheckmarkSharp,
  CloseSharp
} from '@vicons/ionicons5';
// import * from '@vicons/ionicons5'
// type BtnType = 'del' | 'add' | 'back' | 'edit' | 'info';

// function debounce(func, delay) {
//   let timerId;

//   // eslint-disable-next-line func-names
//   return function (...args) {
//     clearTimeout(timerId);

//     timerId = setTimeout(() => {
//       // eslint-disable-next-line no-invalid-this
//       func.apply(this, args);
//     }, delay);
//   };
// }

const downFlag = ref<boolean>(false);
function throttle(func, delay) {
  let isThrottled = false;

  // eslint-disable-next-line func-names
  return function (...args) {
    if (!isThrottled) {
      // eslint-disable-next-line no-invalid-this
      func.apply(this, args);
      isThrottled = true;

      setTimeout(() => {
        isThrottled = false;
      }, delay);
    }
  };
}
// eslint-disable-next-line max-params
export function useAddBtn(
  fun: () => void = () => {
    // eslint-disable-next-line no-console
    console.log('新增');
  },
  size: undefined | Size = 'small',
  value: string = '新增'
) {
  return (
    <NButton type="primary" size={size} onClick={throttle(fun, 500)} class="mr-5px">
      <NIcon size="15" component={AddOutline} class="mr-5px" />
      {value}
    </NButton>
  );
}
// eslint-disable-next-line max-params
export function useDownBtn(
  fun: () => void = () => {
    // eslint-disable-next-line no-console
    console.log('下载');
  },
  size: undefined | Size = 'small',
  value: string = '下载',
  hasPermi: string = '',
  permission: string[] = []
) {
  async function click() {
    downFlag.value = true;
    await fun();
    setTimeout(() => {
      downFlag.value = false;
    }, 1000);
  }
  return (
    <NButton
      v-hasPermi={[hasPermi]}
      v-permission={permission}
      loading={downFlag.value}
      type="success"
      size={size}
      onClick={throttle(click, 500)}
      class="mr-5px"
    >
      <NIcon size="15" component={CloudDownloadOutline} class="mr-5px" />
      {value}
    </NButton>
  );
}
// eslint-disable-next-line max-params
export function useLockBtn(
  fun: () => void = () => {
    // eslint-disable-next-line no-console
    console.log('冻结');
  },
  size: undefined | Size = 'small',
  content: string = '确定要冻结吗？',
  value: string = '冻结',
  hasPermi: string = '',
  permission: string[] = []
) {
  const dialog = useDialog();
  function delDialog() {
    return dialog.warning({
      title: '提示',
      content,
      style: {
        borderRadius: '10px'
      },
      positiveText: '确定',
      negativeText: '取消',
      maskClosable: false,
      onClose: () => {
        // eslint-disable-next-line no-console
        console.log(1);
      },
      onPositiveClick: () => {
        fun();
      },
      onNegativeClick: () => {
        // eslint-disable-next-line no-console
        console.log(1);
      }
    });
  }
  return (
    <NButton
      v-hasPermi={[hasPermi]}
      v-permission={permission}
      type="error"
      size={size}
      onClick={throttle(delDialog, 500)}
      class="mr-5px"
    >
      <NIcon size="15" component={LockClosedOutline} class="mr-5px" />
      {value}
    </NButton>
  );
}
// eslint-disable-next-line max-params
export function useUnlockBtn(
  fun: () => void = () => {
    // eslint-disable-next-line no-console
    console.log('冻结');
  },
  size: undefined | Size = 'small',
  content: string = '确定要解除冻结吗？',
  value: string = '解冻',
  hasPermi: string = '',
  permission: string[] = []
) {
  const dialog = useDialog();
  function delDialog() {
    return dialog.warning({
      title: '提示',
      content,
      style: {
        borderRadius: '10px'
      },
      positiveText: '确定',
      negativeText: '取消',
      maskClosable: false,
      onClose: () => {
        // eslint-disable-next-line no-console
        console.log(1);
      },
      onPositiveClick: () => {
        fun();
      },
      onNegativeClick: () => {
        // eslint-disable-next-line no-console
        console.log(1);
      }
    });
  }
  return (
    <NButton
      v-hasPermi={[hasPermi]}
      v-permission={permission}
      type="primary"
      size={size}
      onClick={throttle(delDialog, 500)}
      class="mr-5px"
    >
      <NIcon size="15" component={LockOpenOutline} class="mr-5px" />
      {value}
    </NButton>
  );
}
// eslint-disable-next-line max-params
export function useDelBtn(
  fun: () => void = () => {
    // eslint-disable-next-line no-console
    console.log('删除');
  },
  size: undefined | Size = 'small',
  content: string = '确定要删除吗？',
  disabled: boolean = false,
  value: string = '删除'
) {
  const dialog = useDialog();
  function delDialog() {
    return dialog.warning({
      title: '提示',
      content,
      style: {
        borderRadius: '10px'
      },
      positiveText: '确定',
      negativeText: '取消',
      maskClosable: false,
      onClose: () => {
        // eslint-disable-next-line no-console
        console.log(1);
      },
      onPositiveClick: () => {
        fun();
      },
      onNegativeClick: () => {
        // eslint-disable-next-line no-console
        console.log(1);
      }
    });
  }
  return (
    <NButton type="error" disabled={disabled} size={size} onClick={throttle(delDialog, 500)} class="mr-5px">
      <NIcon size="15" component={TrashOutline} class="mr-5px" />
      {value}
    </NButton>
  );
}
// 编辑按钮
// eslint-disable-next-line max-params
export function useEditBtn(
  fun: () => void = () => {
    // eslint-disable-next-line no-console
    console.log('编辑');
  },
  size: undefined | Size = 'small',
  value: string = '编辑',
  color: string | undefined = undefined
) {
  return (
    <NButton type="success" color={color} size={size} onClick={throttle(fun, 500)} class="mr-5px">
      <NIcon size="15" component={CreateOutline} class="mr-5px" />
      {value}
    </NButton>
  );
}
// 明细按钮
// eslint-disable-next-line max-params
export function useInfoBtn(
  fun: () => void = () => {
    // eslint-disable-next-line no-console
    console.log('明细');
  },
  size: undefined | Size = 'small',
  value: string = '明细',
  color: string | undefined = undefined
) {
  return (
    <NButton size={size} onClick={throttle(fun, 500)} type="tertiary" color={color} class="mr-5px  ">
      <NIcon size="15" component={DocumentTextOutline} class="mr-5px" />
      {value}
    </NButton>
  );
}
// eslint-disable-next-line max-params
export function useBtn(
  fun: () => void = () => {
    // eslint-disable-next-line no-console
    console.log('下发');
  },
  type: 'add' | 'edit' | 'det' | 'send' | 'info' | 'get' | 'reject' | 'lock' = 'info',
  size: undefined | Size = 'small',
  value: string = '下发',
  color: string | undefined = '#FFD700',
  disabled: boolean = false,
  hasPermi: string = '',
  permission: string[] = []
) {
  const typeS = {
    add: AddOutline,
    edit: CreateOutline,
    det: DocumentTextOutline,
    send: Send,
    get: CheckmarkSharp,
    reject: CloseSharp,
    lock: LockClosedOutline
  };
  return (
    <NButton
      v-hasPermi={[hasPermi]}
      v-permission={permission}
      size={size}
      disabled={disabled}
      onClick={throttle(fun, 500)}
      type="tertiary"
      color={color}
      class="mr-5px  "
    >
      <NIcon size="15" component={typeS[type]} class="mr-5px" />
      {value}
    </NButton>
  );
}
// 设置按钮
// eslint-disable-next-line max-params
export function useSetBtn(
  fun: () => void = () => {
    // eslint-disable-next-line no-console
    console.log('设置');
  },
  size: undefined | Size = 'small',
  value: string = '设置',
  color: string | undefined = undefined,
  hasPermi: string = '',
  permission: string[] = []
) {
  return (
    <NButton
      v-permission={permission}
      v-hasPermi={[hasPermi]}
      size={size}
      onClick={throttle(fun, 500)}
      color={color}
      class="mr-5px"
    >
      <NIcon size="15" component={SettingsOutline} class="mr-5px" />
      {value}
    </NButton>
  );
}
// 搜索框搜索和重置
export function useSearchBtn(
  search: () => void = () => {
    // eslint-disable-next-line no-console
    console.log('搜索');
  },
  reset: () => void = () => {
    // eslint-disable-next-line no-console
    console.log('重置');
  },
  size: undefined | Size = 'medium'
) {
  return (
    <div class="flex justify-end">
      <NButton size={size} type="primary" onClick={throttle(search, 500)} class="mr-5px">
        <NIcon size="15" component={Search} class="mr-5px" />
        搜索
      </NButton>
      <NButton size={size} onClick={throttle(reset, 500)}>
        <NIcon size="15" component={RefreshSharp} class="mr-5px" />
        重置
      </NButton>
    </div>
  );
}

// 返回按钮
// eslint-disable-next-line max-params
export function useBackBtn(
  fun: () => void = () => {
    window.history.go(-1);
  },
  size: undefined | Size = 'small',
  value: string = '返回',
  color: string | undefined = undefined
) {
  // const route = useRoute();
  // function back() {
  //   if (route.query.back) {
  //     window.history.go(-1);
  //   } else {
  //     fun();
  //   }
  // }
  return (
    <NButton type="success" size={size} color={color} onClick={throttle(fun, 500)} class="mr-5px">
      <NIcon size="15" component={ReturnUpBack} class="mr-5px" />
      {value}
    </NButton>
  );
}

const Icon = {
  EyeOutline,
  Send,
  TrashOutline,
  Search,
  RefreshSharp,
  ReturnUpBack,
  AddOutline,
  DocumentTextOutline,
  CreateOutline,
  SettingsOutline,
  LockClosedOutline,
  LockOpenOutline,
  CloudDownloadOutline,
  CheckmarkSharp,
  CloseSharp
};
interface btnType {
  fun: () => void;
  size?: Size;
  icon?: keyof typeof Icon;
  color?: string;
  text?: string;
  hasPermi?: string;
  permission?: string[];
  type?: 'primary' | 'success' | 'warning' | 'error';
}

export function customBtn(config: btnType) {
  const { fun, size, icon, color, text, hasPermi, permission, type } = config;

  // console.log(Icon[icon], 11, SettingsOutline);
  if (!hasPermi && !permission) {
    return (
      <NButton type={type ?? 'success'} size={size} color={color} onClick={throttle(fun, 500)} class="mr-5px">
        {icon ? <NIcon size="15" component={Icon[icon]} class="mr-5px" /> : ''}
        {text}
      </NButton>
    );
  } else if (hasPermi && permission) {
    return (
      <NButton
        type={type ?? 'success'}
        v-permission={permission}
        v-hasPermi={[hasPermi]}
        size={size}
        color={color}
        onClick={throttle(fun, 500)}
        class="mr-5px"
      >
        {icon ? <icon NIcon size="15" component={Icon[icon]} class="mr-5px" /> : ''}
        {text}
      </NButton>
    );
  } else if (hasPermi) {
    return (
      <NButton
        type={type ?? 'success'}
        v-hasPermi={[hasPermi]}
        size={size}
        color={color}
        onClick={throttle(fun, 500)}
        class="mr-5px"
      >
        {icon ? <icon NIcon size="15" component={Icon[icon]} class="mr-5px" /> : ''}
        {text}
      </NButton>
    );
  }
  return (
    <NButton
      type={type ?? 'success'}
      v-permission={permission}
      size={size}
      color={color}
      onClick={throttle(fun, 500)}
      class="mr-5px"
    >
      {icon ? <icon NIcon size="15" component={Icon[icon]} class="mr-5px" /> : ''}
      {text}
    </NButton>
  );
}
