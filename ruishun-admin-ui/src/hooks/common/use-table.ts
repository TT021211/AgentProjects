import type { UnwrapRef, Ref } from 'vue';
import { ref } from 'vue';
import useLoading from './use-loading';
interface response<T> {
  rows: T[];
  total: number;
  msg: string;
  code: number;
}

interface pages {
  pageNum: number;
  pageSize: number;
  total: number;
}
interface searchForm extends pages {
  [key: string]: string | number | null | undefined;
}
export default function useTable<T>(dataFetch: (params: searchForm) => Promise<response<T>>): {
  searchForm: Ref<searchForm>;
  data: Ref<UnwrapRef<T[]>>;
  refresh: (params?: { [key: string | number]: string | number | null | undefined }) => Promise<void>;
  reset: () => Promise<void>;
  loading: Ref<boolean>;
} {
  const { loading, startLoading, endLoading } = useLoading();
  const searchForm = ref({
    pageNum: 1,
    pageSize: 10,
    total: 0
  });
  const data = ref<T[]>([]);
  const refresh = async (params?: { [key: string | number]: string | number | null | undefined }) => {
    startLoading();
    if (params) searchForm.value = { ...searchForm.value, ...params };
    dataFetch(searchForm.value).then(res => {
      endLoading();
      if (!(res.code === 200)) return;
      data.value = res.rows as UnwrapRef<T[]>;
      searchForm.value.total = res.total;
    });
  };
  refresh();
  const reset = async () => {
    searchForm.value = {
      pageNum: 1,
      pageSize: 10,
      total: 0
    };
    dataFetch(searchForm.value).then(res => {
      endLoading();
      if (!(res.code === 200)) return;
      data.value = res.rows as UnwrapRef<T[]>;
      searchForm.value.total = res.total;
    });
  };

  return {
    // 分页数据
    searchForm,
    // 表格数据
    data,
    // 加载状态
    loading,
    // 刷新函数
    refresh,
    // 重置函数
    reset
  };
}
