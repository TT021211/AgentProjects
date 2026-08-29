// 深度克隆
export function deepClone<T>(obj: T): T {
  return JSON.parse(JSON.stringify(obj));
}

// // 将type列表转化为树状图
// export function listToTree<
//   T extends {
//     children: T[];
//     id: string;
//     parentId: string | null;
//   }
// >(list: T[], id = 'id', parentId = 'parentId'): T[] {
//   // 使用对象而不是数组来存储节点，以提高查找效率，并避免改变原数组
//   const nodes: { [key: string]: T } = {};
//   const tree: T[] = [];

//   // 遍历列表，将每个节点存储到nodes对象中
//   list.forEach(item => {
//     nodes[item[id]] = item;
//     item.children = [];
//   });

//   // 构建树结构
//   list.forEach(item => {
//     const parentIdValue = item[parentId];
//     if (parentIdValue !== 0 && parentIdValue !== null) {
//       // 通过nodes对象快速找到父节点
//       const parent = nodes[parentIdValue];
//       if (parent) {
//         parent.children?.push(item);
//       }
//     } else {
//       tree.push(item);
//     }
//   });

//   return tree;
// }

// 将一个list转换成树形结构
export function listToTree<T>(list: T[], id = 'id', parentId = 'parentId'): T[] {
  const map = new Map();
  const tree: T[] = [];

  // 将所有的节点存入map中
  for (const item of list) {
    map.set(item[id], item);
  }

  for (const item of list) {
    const parentIdValue = item[parentId];
    if (parentIdValue === 0 || parentIdValue === null) {
      tree.push(item);
    } else {
      const parent = map.get(parentIdValue);
      if (!parent) {
        console.log(item);
        tree.push(item);
      }
      if (parent) {
        if (!parent.children) {
          parent.children = [];
        }
        parent.children.push(item);
      }
    }
  }

  return tree;
}
