<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form :model="searchForm" inline label-placement="top">
        <n-form-item label="成员名称" style="width: 180px">
          <n-input model:value="searchForm.memberName" placeholder="请输入成员名称" />
        </n-form-item>
        <n-form-item label="成员id">
          <n-input v-model:value="searchForm.memberId" placeholder="请输入成员id"></n-input>
        </n-form-item>
        <n-form-item>
          <n-button type="primary" class="mr-5" @click="handleSearch">
            <icon-tdesign-search class="mr-2" />
            搜索
          </n-button>
          <n-button @click="handleResert">
            <icon-tdesign-arrow-left-right-3 class="mr-8px text-15px" />
            重置
          </n-button>
        </n-form-item>
      </n-form>
    </my-card>
    <my-card title="班组成员">
      <template #right>
        <div class="flex-center">
          <cx-columns v-model:columns="columns" size="small"></cx-columns>
          <n-button type="primary" size="small" class="mr-5px ml-5px" @click="handleAddMember">
            <icon-ic-round-plus class="mr-3px text-15px" />
            添加组员
          </n-button>
          <n-button :disabled="!checkedList.length" type="error" size="small" class="mr-5px" @click="deleteList">
            <icon-tdesign-delete class="mr-3px text-15px" />
            批量删除
          </n-button>
          <n-button size="small" type="success" @click="goBack">
            <icon-tdesign-rollback class="mr-4px text-16px" />
            返回
          </n-button>
        </div>
      </template>
      <n-data-table
        :columns="columns"
        :row-key="row => row.id"
        :loading="loading"
        :data="data"
        :pagination="{
          itemCount: searchForm.total,
          pageSize: searchForm.pageSize,
          prefix: ({ itemCount }) => `共 ${itemCount} 条 `,
          pageSizes: [10, 20, 30, 40],
          showSizePicker: true,
          onUpdatePageSize: pageSize => {
            console.log(pageSize);
            searchForm.pageSize = pageSize;
          }
        }"
        @update:checked-row-keys="handleCheckedRowKeys"
      ></n-data-table>
      <!-- <n-pagination
        v-model:page="searchForm.pageNum"
        v-model:page-size="searchForm.pageSize"
        style="margin-top: 20px; float: right"
        :item-count="searchForm.total"
        @update:page="changePage"
      ></n-pagination> -->
    </my-card>
    <my-dialog
      v-model:show="addDialog"
      :sub-disable="!memberList.length"
      title="添加组员"
      width="900px"
      @cancel="
        async () => {
          addDialog = false;
          await addHandleResert();
          await init();
        }
      "
      @submit="
        async () => {
          addDialog = false;
          await memberAddList();
          addHandleResert();
        }
      "
    >
      <template #content>
        <div style="width: 100%; display: flex; flex-direction: column">
          <n-form :model="addSearchForm" inline label-placement="top">
            <n-form-item label="用户名称" style="width: 180px">
              <n-select
                v-model:value="addSearchForm.userName"
                clearable
                remote
                filterable
                placeholder="请输入用户名称"
                :options="UserOption"
                @search="searchUser"
              />
            </n-form-item>
            <n-form-item label="手机号">
              <n-input v-model:value="addSearchForm.phonenumber" placeholder="请输入电话号码"></n-input>
            </n-form-item>
            <n-form-item>
              <n-button type="primary" class="mr-5" @click="addHandleSearch">
                <icon-tdesign-search class="mr-2" />
                搜索
              </n-button>
              <n-button class="mr-5" @click="addHandleResert">
                <icon-tdesign-arrow-left-right-3 class="mr-8px text-15px" />
                重置
              </n-button>
              <!-- <n-button type="success" :disabled="memberList.length === 0" @click="memberAddList">
                批量加入班组
              </n-button> -->
            </n-form-item>
          </n-form>

          <n-data-table
            style="margin-bottom: 30px"
            :row-key="row => row.userId"
            :data="addData"
            :columns="addColumns"
            :loading="addLoading"
            @update:checked-row-keys="handleCheckedMemberKeys"
          ></n-data-table>

          <my-pagination v-model:search-form="addSearchForm" @init="initAddData"></my-pagination>
        </div>
      </template>
    </my-dialog>
  </div>
</template>

<script setup lang="tsx">
defineOptions({
  name: 'WorkgroupOrganization'
});
import type { Ref } from 'vue';
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useMessage } from 'naive-ui';
import type { DataTableColumns } from 'naive-ui';
import {
  // getAllUserList,
  getAllUserListAll,
  deleteWorkgroupUser,
  getNotJoinUserList,
  getNotJoinWorkgroupUserList,
  memberAddWorkgroup
} from '@/service/api/md/workgroup';
import { useLoading, useWarning } from '~/src/hooks';

const message = useMessage();
const dialog = useWarning();
const { loading, startLoading, endLoading } = useLoading();
const { loading: addLoading, startLoading: addStartLoading, endLoading: addEndLoading } = useLoading();
const addDialog = ref<boolean>(false);
const addData = ref([]);
const addSearchForm = ref<Md.workUser>({
  userName: null,
  phonenumber: '',
  pageNum: 1,
  pageSize: 6,
  total: 0
});
async function handleAddMember() {
  addDialog.value = true;
  initAddData();
}
const UserOption = ref<{ label: string; value: string }[]>([]);
function initAddData() {
  addStartLoading();
  // addSearchForm.value.total = undefined;
  const data = getUserIdList() as string;
  getNotJoinWorkgroupUserList(data, addSearchForm.value).then((res: any) => {
    if (res.code === 200) {
      addData.value = res.rows;
      addSearchForm.value.total = res.total;
      addEndLoading();
      res.rows.forEach((item: any) => {
        UserOption.value.push({
          label: item.userName,
          value: item.userName
        });
      });
    }
  });
}

function searchUser(query: string) {
  const data = getUserIdList() as string;
  const form = {
    userName: query,
    phonenumber: '',
    pageNum: 1,
    pageSize: 999
  };
  UserOption.value.splice(0, UserOption.value.length);
  getNotJoinWorkgroupUserList(data, form).then((res: any) => {
    if (res.code === 200) {
      res.rows.forEach((item: any) => {
        UserOption.value.push({
          label: item.userName,
          value: item.userName
        });
      });
    }
  });
}
function addHandleSearch() {
  initAddData();
}
function addHandleResert() {
  addSearchForm.value = {
    userName: '',
    phonenumber: '',
    pageNum: 1,
    pageSize: 6,
    total: 0
  };
  initAddData();
}
// function addChangePage(page: number) {
//   addSearchForm.value.pageNum = page;
//   initAddData();
// }
const searchForm = ref<Md.WorkGroupSearch>({
  memberName: '',
  memberId: '',
  pageNum: 1,
  pageSize: 10,
  total: 0
});
function handleResert() {
  searchForm.value = {
    memberName: '',
    memberId: '',
    pageNum: 1,
    pageSize: 10,
    total: 0
  };
  init();
}
function handleSearch() {
  searchForm.value.pageNum = 1;
  init();
}
// function changePage(page: number) {
//   searchForm.value.pageNum = page;
//   init();
// }
const route = useRoute();
const router = useRouter();
function goBack() {
  router.go(-1);
}
const checkedList = ref<any[]>([]);
function handleCheckedRowKeys(rowKeys: string[]) {
  checkedList.value = rowKeys;
}
const addColumns: DataTableColumns<Md.WorkGroupMember[]> = [
  {
    type: 'selection'
  },
  {
    title: '用户编号',
    key: 'userId',
    align: 'center'
  },
  {
    title: '用户名称',
    key: 'userName',
    align: 'center'
  },

  {
    title: '用户昵称',
    key: 'nickName',
    align: 'center'
  },
  {
    title: '邮箱',
    key: 'email',
    align: 'center'
  },

  {
    title: '手机号码',
    key: 'phonenumber',
    align: 'center'
  }
  // ,
  // {
  //   title: '操作',
  //   key: 'active',
  //   width: 120,
  //   render(row) {
  //     return (
  //       <div>
  //         <n-button type="success" size="small" onClick={() => handleAdd(row)}>
  //           <icon-ic-round-plus class="mr-3px text-15px" />
  //           加入班组
  //         </n-button>
  //       </div>
  //     );
  //   }
  // }
];
const columns: Ref<DataTableColumns<Md.WorkGroupMember[]>> = ref([
  {
    type: 'selection'
  },
  {
    title: '成员名称',
    align: 'center',
    key: 'memberName'
  },
  {
    title: '成员id',
    align: 'center',
    key: 'memberId'
  },
  {
    title: '班组名称',
    align: 'center',
    key: 'workgroupName'
  },
  {
    title: '班组id',
    align: 'center',
    key: 'workgroupId'
  },
  {
    title: '班组编码',
    align: 'center',
    key: 'workgroupCode'
  },
  {
    title: '操作',
    align: 'center',
    key: 'active',
    width: 120,
    render(row) {
      return (
        <div>
          <n-button type="error" size="small" onClick={() => handleDelete(row)}>
            <icon-tdesign-delete class="mr-3px text-15px " />
            删除成员
          </n-button>
        </div>
      );
    }
  }
]);

const memberList = ref<string[]>([]);
function handleCheckedMemberKeys(keys: string[]) {
  memberList.value = keys;
}

function getData() {
  const data = JSON.parse(route.query.groupForm as string);
  return {
    AddVersion: () => {
      data.version += 1;
    },
    GetData: () => {
      return data;
    },
    SetTotalMembers: value => {
      data.totalMembers = value;
    }
  };
}
const MyData = getData();
function memberAddList() {
  // const MyData = JSON.parse(route.query.groupForm as string);
  // MyData.totalMembers = searchForm.value.total;
  // MyData.version += 1;
  MyData.AddVersion();
  MyData.SetTotalMembers(searchForm.value.total);
  let ids = '/';
  memberList.value.forEach(item => {
    ids += `${item},`;
  });
  ids = ids.substring(0, ids.length - 1);
  memberAddWorkgroup(ids, MyData.GetData()).then(res => {
    if (res.code === 200) {
      message.success('新增成功');
      initAddData();
      init();
    }
  });
}
// function handleAdd(row: any) {
//   console.log(row);
//   const data = JSON.parse(route.query.groupForm);
//   const ids = `/${row.userId}`;
//   memberAddWorkgroup(ids, data).then(res => {
//     if (res.code === 200) {
//       message.success('新增成功');
//       initAddData();
//     }
//   });
// 成功后的操作
// addDialog.value = false;
// addHandleResert();
// }
function handleDelete(row: any) {
  dialog.warn(() => {
    deleteWorkgroupUser({ id: row.id }).then(res => {
      if (res.code === 200) {
        init();
        message.success('删除成功');
      }
    });
  });
}
function deleteList() {
  const data: any[] = [];
  checkedList.value.forEach(item => {
    data.push(item);
  });
  dialog.warn(() => {
    getNotJoinUserList(data).then(res => {
      if (res.code === 200) {
        init();
        message.success('删除成功');
        checkedList.value.splice(0, checkedList.value.length);
      }
    });
  });
}
const data = ref([]);
function getUserIdList() {
  let list = '/';
  if (data.value.length >= 1) {
    data.value.forEach((item: any) => {
      list += `${item.memberId},`;
    });
    list = list.substring(0, list.length - 1);
    return list;
  }
  return '/0';
}
function init() {
  startLoading();
  searchForm.value.workgroupId = route.query.id as string;
  // getAllUserList(searchForm.value).then(res => {
  //   console.log(res);
  //   if (res.code === 200) {
  //     data.value = res.rows;
  //     searchForm.value.total = res.total;
  //     endLoading();
  //   }
  // });
  getAllUserListAll(searchForm.value).then(res => {
    if (res.code === 200) {
      data.value = res.data;
      searchForm.value.total = res.data.length;
      endLoading();
    }
  });
}
onMounted(() => {
  init();
});
</script>

<style lang="scss" scoped></style>
