export function getStatusName(num: string | null) {
  const statusInfo = {
    statusName: '',
    bgColor: ''
  };
  switch (num) {
    case '0':
      statusInfo.statusName = '已排产';
      statusInfo.bgColor = 'info';
      break;
    case '1':
      statusInfo.statusName = '生产中';
      statusInfo.bgColor = 'info';
      break;
    case '2':
      statusInfo.statusName = '已完工';
      statusInfo.bgColor = 'success';
      break;
    case '3':
      statusInfo.statusName = '已作废';
      statusInfo.bgColor = 'error';
      break;
    default:
      break;
  }
  return statusInfo;
}

export function getPlanStatusName(num: string | null) {
  const statusInfo = {
    statusName: '',
    bgColor: ''
  };
  switch (num) {
    case '0':
      statusInfo.statusName = '未排产';
      statusInfo.bgColor = 'warning';
      break;
    case '1':
      statusInfo.statusName = '排产中';
      statusInfo.bgColor = 'info';
      break;
    case '2':
      statusInfo.statusName = '生产中';
      statusInfo.bgColor = 'info';
      break;
    case '3':
      statusInfo.statusName = '已完成';
      statusInfo.bgColor = 'success';
      break;
    case '4':
      statusInfo.statusName = '已作废';
      statusInfo.bgColor = 'error';
      break;
    default:
      statusInfo.statusName = '';
      break;
  }
  return statusInfo;
}

export default { getStatusName, getPlanStatusName };
