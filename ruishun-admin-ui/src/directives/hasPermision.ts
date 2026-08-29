import type { App, Directive } from 'vue';
import { getUserInfo } from '@/store/modules/auth/helpers';
export default function setupHasPermissionDirective(app: App) {
  function jugeHasPermission(el: HTMLElement, binding: any) {
    const hasPermision = binding;
    const all_permission = '*:*:*';
    const permissions = getUserInfo().permissions;
    // console.log(permissions, '------------'); v-hasPermi="['sys:user:add','sys:user:edit']"
    if (hasPermision) {
      const permissionFlag = hasPermision;
      const hasPermissions = permissions.some(permission => {
        return all_permission === permission || permissionFlag.includes(permission);
      });
      if (!hasPermissions) {
        // eslint-disable-next-line no-unused-expressions
        el.parentNode && el.parentNode.removeChild(el);
      }
    } else {
      // console.log(`请设置操作权限标签值`);
    }
  }
  const HasPermissionDirective: Directive<HTMLElement, Auth.RoleType | Auth.RoleType[]> = {
    mounted(el, binding) {
      jugeHasPermission(el, binding.value);
    },
    beforeUpdate(el, binding) {
      jugeHasPermission(el, binding.value);
    }
  };

  app.directive('hasPermi', HasPermissionDirective);
}
