<template>
  <!-- <icon-local-logo-fill v-if="fill" />
  <icon-local-logo v-else /> -->

  <!-- <icon-local-ak-logo v-if="fill" />
  <icon-local-ak-logo v-else /> -->

  <img src="../../assets/img/rsLogo.png" alt="" style="width: 200px; height: 70px" />
</template>

<script lang="ts" setup>
defineOptions({ name: 'SystemLogo' });

interface Props {
  /** logo是否填充 */
  fill?: boolean;
}

withDefaults(defineProps<Props>(), {
  fill: false
});
</script>

<style scoped></style>
