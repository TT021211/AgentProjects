<template>
  <n-space :vertical="true" :size="16">
    <data-card />
    <top-chart />
    <bottom-part />
    <!-- <Left />
    <Right /> -->
  </n-space>
</template>

<script lang="ts" setup>
import { DataCard, TopChart, BottomPart } from './components';
</script>

<style scoped></style>
