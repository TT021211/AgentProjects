import { request } from '../../request';

type ResultData = {
  data: ItemTypeManagement.ItemType[];
  allParentIDArr: string[];
};

function adapterOfFetchItemTypeList(data: ApiItemTypeManagement.ItemType[] | null): ResultData {
  const allItemTypeID: number[] = [];
  const result: ResultData = { data: [], allParentIDArr: [] };
  if (!data) return result;

  const itemMap = new Map();

  data.forEach((item, index) => {
    const itemtype: ItemTypeManagement.ItemType = {
      index: index + 1,
      key: item.id.toString(),
      ...item,
      parentTypeName: '',
      children: []
    };
    itemMap.set(item.id, itemtype);
    allItemTypeID.push(item.id);
    /** 所有父节点去重，得到默认展开的行key */
    if (item.parentTypeId !== 0 && !result.allParentIDArr.includes(item.parentTypeId.toString())) {
      result.allParentIDArr.push(item.parentTypeId.toString());
    }
  });

  for (const [key] of itemMap) {
    const pID = itemMap.get(key).parentTypeId;

    /** 父节点是否存在 */
    if (allItemTypeID.includes(pID)) {
      const parent = itemMap.get(pID);
      parent.children.push(itemMap.get(key));
      /** 更新排序 */
      parent.children.sort((a: ItemTypeManagement.ItemType, b: ItemTypeManagement.ItemType) => {
        if (a.orderNum > b.orderNum) {
          return 1;
        }
        if (a.orderNum < b.orderNum) {
          return -1;
        }
        return 0;
      });
    } else {
      /** 根节点放入result */
      result.data.push(itemMap.get(key));
      /** 更新排序 */
      result.data.sort((a: ItemTypeManagement.ItemType, b: ItemTypeManagement.ItemType) => {
        if (a.orderNum > b.orderNum) {
          return 1;
        }
        if (a.orderNum < b.orderNum) {
          return -1;
        }
        return 0;
      });
    }
  }

  return result;
}

/**
 * 物料分类列表
 */
export async function fetchItemTypeList(reqBody: any) {
  let params = '';
  const arr = Object.keys(reqBody);
  if (arr.length !== 0) {
    params += '?';
    for (const key in reqBody) {
      if (reqBody[key] !== null) {
        params += `${key}=${reqBody[key]}&`;
      }
    }
  }
  const { data } = await request.get<ApiAuth.Token>(`/md/mdItemType/selectall${params}`, reqBody);
  const rs = adapterOfFetchItemTypeList(data);
  return rs;
}

/**
 * 物料分类列表
 */
// export async function fetchItemTypeList(reqBody: any) {
//   let params = '';
//   const arr = Object.keys(reqBody);
//   if (arr.length !== 0) {
//     params += '?';
//     for (const key in reqBody) {
//       if (reqBody[key]) {
//         params += `${key}=${reqBody[key]}&`;
//       }
//     }
//   }
//   const data = await request.get<ApiAuth.Token>(`/md/itemtype/list${params}`, reqBody);
//   const rs = adapterOfFetchItemTypeList(data);
//   return rs;
// }

// function adapterOfFetchItemTypeInfo(data: ApiItemTypeManagement.ItemType): ApiItemTypeManagement.ItemType {
//   return data;
// }

/**
 * 物料分类详情
 */
export async function fetchItemTypeInfo(itemTypeId: number) {
  const rows = await request.get<ApiAuth.Token>(`/md/mdItemType/${itemTypeId}`, {});
  return rows;
}

/**
 * 物料分类更新
 */
export async function updateItemType(reqBody: object) {
  const data = await request.put<ApiAuth.Token>(`/md/mdItemType`, reqBody);
  return data;
}

/**
 * 物料分类删除
 */
export async function deleteItemType(itemTypeId: number) {
  const data = await request.delete<ApiAuth.Token>(`/md/mdItemType/${itemTypeId}`, {});
  return data;
}

/**
 * 物料分类新增
 */
export async function addItemType(reqBody: object) {
  const data = await request.post<ApiAuth.Token>(`/md/mdItemType`, reqBody);
  return data;
}

/**
 * 物料列表
 */
export async function fetchItemList(reqBody: any) {
  let params = '';
  const arr = Object.keys(reqBody);
  if (arr.length !== 0) {
    params += '?';
    for (const key in reqBody) {
      if (reqBody[key]) {
        params += `${key}=${reqBody[key]}&`;
      }
    }
  }
  const data = await request.get<ApiAuth.Token>(`/md/mdItem/list${params}`, reqBody);
  return data;
}

/**
 * 物料详情
 */
export async function fetchItemInfo(itemId: number) {
  const data = await request.get<ApiAuth.Token>(`/md/mdItem/${itemId}`, {});
  return data;
}

/**
 * 物料更新
 */
export async function updateItem(reqBody: object) {
  const data = await request.put<ApiAuth.Token>(`/md/mdItem`, reqBody);
  return data;
}

/**
 * 物料删除
 */
export async function deleteItem(itemId: number) {
  const data = await request.delete<ApiAuth.Token>(`/md/mdItem/${itemId}`, {});
  return data;
}

/**
 * 物料新增
 */
export async function addItem(reqBody: object) {
  const data = await request.post<ApiAuth.Token>(`/md/mdItem`, reqBody);
  return data;
}

/**
 * 获取物料分类列表下拉框
 */
export async function getItemTypeList() {
  const data = await request.get<ApiAuth.Token>(`/md/mdItemType/queryall`, {});
  return data;
}
