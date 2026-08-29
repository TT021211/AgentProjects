import { h } from 'vue';
import type { DataTableColumns } from 'naive-ui';
export const dataTableConfig = {
  maxHeight: 380,
  scrollWidth<T>(columns: DataTableColumns<T>): number {
    if (!Array.isArray(columns)) {
      throw new Error('参数不匹配');
    }
    columns.forEach((item, index, ele) => {
      if (!Object.keys(item).includes('resizable')) {
        ele[index].resizable = true;
      }
    });
    if (columns.length === 0) return 0;
    return columns.reduce((sum, item) => sum + (Number(item.width) || Number(item.minWidth) || 100), 0);
  },

  // 汇总
  createSummary(summaryList: Array<string> | string, color?: string) {
    return pageData => {
      const obj = {};
      let summaryArray: Array<string> = [];
      if (!Array.isArray(summaryList)) {
        summaryArray.push(summaryList);
      } else {
        summaryArray = JSON.parse(JSON.stringify(summaryList));
      }
      for (let index = 0; index < summaryArray.length; index++) {
        const item = summaryArray[index];
        obj[item] = {
          value: h(
            'span',
            { style: { color: color || 'red' } },
            (pageData as unknown as any[])
              .reduce((prevValue, row) => prevValue + (Number(row[item]) || 0), 0)
              ?.toFixed(2)
          ),
          colSpan: 1
        };
      }

      return obj;
    };
  }
};
