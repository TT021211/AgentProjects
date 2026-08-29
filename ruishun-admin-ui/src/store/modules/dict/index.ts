import { defineStore } from 'pinia';

const useDictStore = defineStore('dict-store', {
  state: () => ({
    dict: []
  }),
  actions: {
    // 获取字典
    getDict(_key: string) {
      if (_key === null && _key === '') {
        return null;
      }
      try {
        for (let i: number = 0; i < this.dict.length; i += 1) {
          if (this.dict[i].key === _key) {
            return this.dict[i].value;
          }
        }
      } catch (e) {
        return null;
      }
      return null;
    },
    // 设置字典
    setDict(_key, value) {
      if (_key !== null && _key !== '') {
        this.dict.push({
          key: _key,
          value
        });
      }
    },
    // 删除字典
    removeDict(_key) {
      let bln = false;
      console.log('删除对应的字典', _key);
      try {
        for (let i = 0; i < this.dict.length; i += 1) {
          if (this.dict[i].key === _key) {
            this.dict.splice(i, 1);
            return true;
          }
        }
      } catch (e) {
        bln = false;
      }
      return bln;
    },
    // 清空字典
    cleanDict() {
      this.dict = [];
    }
  }
});

export default useDictStore;
