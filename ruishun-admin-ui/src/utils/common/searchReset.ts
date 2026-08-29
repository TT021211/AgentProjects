import { ref } from 'vue';

function deepClone(value: any) {
  if (value === null || typeof value !== 'object') return value;
  return JSON.parse(JSON.stringify(value));
}

export function useResetSearch<T>(value: T) {
  const initialValue = deepClone(value);
  const searchForm = ref(value);
  const reset = () => {
    searchForm.value = deepClone(initialValue);
  };
  return { searchForm, reset };
}
