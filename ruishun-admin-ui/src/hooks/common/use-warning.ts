import { useDialog } from 'naive-ui';

export default function useWarning(
  title: string = '警告',
  content: string = '确定要删除吗？',
  showCance: boolean = true
) {
  const dialog = useDialog();

  return {
    warn(
      callback: () => void,
      cancel: () => void = () => {
        // eslint-disable-next-line no-console
        console.log('取消');
      }
    ) {
      return dialog.warning({
        title,
        content,
        style: {
          borderRadius: '10px'
        },
        positiveText: '确定',
        negativeText: showCance ? '取消' : '',
        maskClosable: false,
        onClose: () => {
          cancel();
        },
        onPositiveClick: () => {
          callback();
        },
        onNegativeClick: () => {
          cancel();
        }
      });
    }
  };
}
