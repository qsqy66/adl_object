<script setup lang="ts">
import * as echarts from 'echarts'
import type { EChartsOption } from 'echarts'
import { nextTick, onBeforeUnmount, onMounted, ref, watch } from 'vue'
const props = withDefaults(defineProps<{ option: EChartsOption; height?: string }>(), { height: '300px' }); const el = ref<HTMLElement>(); let chart: echarts.ECharts | undefined
function render() { if (!chart) return; chart.setOption(props.option, true) }
onMounted(async () => { await nextTick(); if (el.value) chart = echarts.init(el.value); render(); window.addEventListener('resize', () => chart?.resize()) })
watch(() => props.option, render, { deep: true }); onBeforeUnmount(() => { chart?.dispose(); window.removeEventListener('resize', () => chart?.resize()) })
</script>
<template><div ref="el" :style="{ height, width: '100%' }"></div></template>
