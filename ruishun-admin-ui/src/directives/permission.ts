import type { App, Directive } from 'vue';
import { usePermission } from '@/composables';
import { isArray } from '../utils';

export default function setupPermissionDirective(app: App) {
  const { hasPermission } = usePermission();

  function updateElVisible(el: HTMLElement, permission: Auth.RoleType | Auth.RoleType[]) {
    // console.log(permission, '=====');
    if (isArray(permission) && permission.length === 0) {
      // console.log(`need roles: like v-permission="'admin'", v-permission="['admin', 'test]"`);
    } else if (!hasPermission(permission)) {
      el.parentElement?.removeChild(el);
    }
  }

  const permissionDirective: Directive<HTMLElement, Auth.RoleType | Auth.RoleType[]> = {
    mounted(el, binding) {
      updateElVisible(el, binding.value);
    },
    beforeUpdate(el, binding) {
      updateElVisible(el, binding.value);
    }
  };

  app.directive('permission', permissionDirective);
}
