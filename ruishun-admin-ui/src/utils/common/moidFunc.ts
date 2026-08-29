import { getDictDataList } from '@/service/api/system/dictData';
let typeData = [];
getDictDataList({ dictType: 'mold_status_list', pageSize: 999 }).then(res => {
  typeData = res.rows;
});
export function getMoidStatus(status: string) {
  const items: any = typeData.find((item: any) => item.dictValue === status);
  let statusType = '';
  let text = '';
  if (items) {
    statusType = items.listClass;
    text = items.dictLabel;
  }
  return {
    text,
    statusType
  };
}
