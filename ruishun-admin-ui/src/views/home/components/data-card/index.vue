<template>
  <n-card :bordered="false" class="h-full rounded-8px shadow-sm">
    <n-grid cols="s:1 m:2 l:4" responsive="screen" :x-gap="16" :y-gap="16">
      <n-grid-item v-for="item in cardData" :key="item.id">
        <gradient-bg class="h-100px" :start-color="item.colors[0]" :end-color="item.colors[1]">
          <h3 class="text-16px">{{ item.title }}</h3>
          <div class="flex justify-between pt-12px">
            <svg-icon :icon="item.icon" class="text-32px" />
            <count-to
              :prefix="item.unit"
              :start-value="1"
              :end-value="item.value"
              class="text-30px text-white dark:text-dark"
            />
          </div>
        </gradient-bg>
      </n-grid-item>
    </n-grid>
  </n-card>
</template>

<script setup lang="ts">
import { GradientBg } from './components';

defineOptions({ name: 'DashboardAnalysisDataCard' });

interface CardData {
  id: string;
  title: string;
  value: number;
  unit: string;
  colors: [string, string];
  icon: string;
}

const cardData: CardData[] = [
  {
    id: 'visit',
    title: '设备总数',
    value: 160,
    unit: '',
    colors: ['#ec4786', '#b955a4'],
    icon: 'ant-design:bar-chart-outlined'
  },
  {
    id: 'amount',
    title: '运行中',
    value: 130,
    unit: '$',
    colors: ['#865ec0', '#5144b4'],
    icon: 'ant-design:money-collect-outlined'
  },
  {
    id: 'download',
    title: '待机',
    value: 30,
    unit: '',
    colors: ['#56cdf3', '#719de3'],
    icon: 'carbon:document-download'
  },
  {
    id: 'trade',
    title: '故障',
    value: 0,
    unit: '',
    colors: ['#fcbc25', '#f68057'],
    icon: 'ant-design:trademark-circle-outlined'
  }
];
</script>

<style scoped></style>
