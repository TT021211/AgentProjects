import { ref, toRefs } from 'vue';
import { fetchDictData } from '@/service/api/system/dictData';
import useDictStore from '@/store/modules/dict';
const useDict = (...args) => {
  const res = ref({});
  const store = useDictStore();
  return (() => {
    args.forEach(async dictType => {
      res.value[dictType] = [];
      const dicts = await store.getDict(dictType);
      if (dicts) {
        res.value[dictType] = dicts;
      } else {
        await fetchDictData(dictType).then(resp => {
          resp.data.forEach(item => {
            item.label = item.dictLabel;
            item.value = item.dictValue;
          });
          res.value[dictType] = resp.data;
          store.setDict(dictType, res.value[dictType]);
        });
      }
    });
    return toRefs(res.value);
  })();
};

export default useDict;
