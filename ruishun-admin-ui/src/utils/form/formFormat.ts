/**
 * 构造树型结构数据
 * @param {*} data 数据源
 * @param {*} id id字段 默认 'id'
 * @param {*} parentId 父节点字段 默认 'parentId'
 * @param {*} children 孩子节点字段 默认 'children'
 */
// eslint-disable-next-line max-params
export function handleTree(data, id, parentId, children) {
  const config = {
    id: id || 'id',
    parentId: parentId || 'parentId',
    childrenList: children || 'children'
  };

  const childrenListMap = {};
  const nodeIds = {};
  const tree = [] as any;

  for (const d of data) {
    const parentId01 = d[config.parentId];

    if (!childrenListMap[parentId01]) {
      childrenListMap[parentId01] = [];
    }
    nodeIds[d[config.id]] = d;
    childrenListMap[parentId01].push(d);
  }

  for (const d of data) {
    const parentId02 = d[config.parentId];
    if (!nodeIds[parentId02]) {
      tree.push(d);
    }
  }

  for (const t of tree) {
    adaptToChildrenList(t);
  }

  function adaptToChildrenList(o) {
    if (childrenListMap[o[config.id]] !== null) {
      o[config.childrenList] = childrenListMap[o[config.id]];
    }
    if (o[config.childrenList]) {
      for (const c of o[config.childrenList]) {
        adaptToChildrenList(c);
      }
    }
  }

  return tree;
}
