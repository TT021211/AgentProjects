import * as echarts from 'echarts';

const barData1 = [227, 318, 338, 340, 204, 320, 176]; // 报工
const barData2 = [83, 127, 110, 116, 55, 127, 54]; // 投料
// const lineData = [39, 50, 54, 49, 50, 46, 56, 54]; //折线图数据

function dateFormatter(times){
	let newTime = new Date()
	let time = new Date(newTime.getTime()-times)
	let year = time.getFullYear()
	let month = time.getMonth()+1 > 10 ? (time.getMonth()+1):'0' + (time.getMonth()+1)
	let day = time.getDate() > 10 ? time.getDate().toString():'0' + time.getDate()
	return year + '-' + month + '-' + day
}
export let option = {
	tooltip: {
		trigger: 'axis',
		axisPointer: {
			type: 'none',
			label: {
				show: false,
			},
		},
	},
	grid: {
		left: '4%',
		top: '5%',
		right: '5%',
		bottom: '20%',
	},
	legend: {
		padding: [0, 200],
		data: [
			{
				name: '拉丝投料数量',
				icon: 'circle',
				textStyle: { color: '#ffffff' },
			},
			{
				name: '拉丝报工数量',
				icon: 'circle',
				textStyle: { color: '#ffffff' },
			},
			// {
			// 	name: '损耗率',
			// 	icon: 'circle',
			// 	textStyle: { color: '#ffffff' },
			// },
		],
		top: '4%',
		right: '3%',
		textStyle: {
			color: '#1FC3CE',
			fontSize: 16,
		},
	},
	xAxis: {
		data: [
			dateFormatter(24*60*60*1000*6),
			dateFormatter(24*60*60*1000*5),
			dateFormatter(24*60*60*1000*4),
			dateFormatter(24*60*60*1000*3),
			dateFormatter(24*60*60*1000*2),
			dateFormatter(24*60*60*1000),
			dateFormatter(0)
		],
		axisLine: {
			show: false, //隐藏X轴轴线
		},
		axisTick: {
			show: false, //隐藏X轴刻度
			alignWithLabel: true,
		},
		axisLabel: {
			show: true,
			textStyle: {
				color: '#0b78d5', //X轴文字颜色
				fontSize: 12,
			},
			margin: 12,
			interval: 0
		},
	},
	yAxis: [
		{
			type: 'value',
			name: '单位：%',
			splitNumber: 8,
			nameTextStyle: {
				color: '#0b78d5',
				fontSize: 14,
			},
			splitLine: {
				show: true,
				lineStyle: {
					color: 'rgba(21, 78, 129, 0.5 )',
					type: 'solid',
				},
			},
			axisTick: {
				show: false,
			},
			axisLine: {
				show: false
			},
			axisLabel: {
				show: true,
				textStyle: {
					color: '#0b78d5',
					fontSize: 12,
				},
			},
		},
		{
			type: 'value',
			name: '单位：件',
			max: 100,
			splitNumber: 8,
			nameTextStyle: {
				color: '#0b78d5',
				fontSize: 14,
			},
			splitLine: {
				show: true,
				lineStyle: {
					color: 'rgba(92, 229, 192, 0.2)',
					type: 'dashed',
				},
			},
			axisLabel: {
				show: true,
				textStyle: {
					color: '#0b78d5',
					fontSize: 12,
				},
			},
			axisLine: {
				show: false,
			},
			axisTick: {
				show: false
			},
		},
	],
	series: [
		{
			name: '拉丝投料数量',
			type: 'bar',
			barWidth: 12,
			itemStyle: {
				normal: {
					color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
						{
							offset: 0,
							color: 'rgba(60, 211, 158, 1)',
						},
						{
							offset: 1,
							color: 'rgba(60, 211, 158, 0.1)',
						},
					]),
				},
			},
			data: barData2,
			label: {
				show: true,
				position: 'top',
				distance: 5,
				fontSize: 12,
				color: '#FFFFFF',
			},
		},
		{
			name: '拉丝报工数量',
			type: 'bar',
			barWidth: 12,
			itemStyle: {
				normal: {
					color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
						{
							offset: 0,
							color: 'rgba(218, 227, 108, 1)',
						},
						{
							offset: 1,
							color: 'rgba(218, 227, 108, 0.1)',
						},
					]),
				},
			},
			data: barData1,
			label: {
				show: true,
				position: 'top',
				distance: 5,
				fontSize: 12,
				color: '#FFFFFF',
			},
		},

		// {
		// 	name: '损耗率',
		// 	type: 'line',
		// 	smooth: true,
		// 	yAxisIndex: 1,
		// 	showAllSymbol: true, //显示所有图形。
		// 	symbol: 'rect', //标记的图形为实心圆
		// 	symbolSize: 8, //标记的大小
		// 	itemStyle: {
		// 		//折线拐点标志的样式
		// 		color: 'rgba(88, 222, 196, 1)',
		// 	},
		// 	lineStyle: {
		// 		color: 'rgba(55, 168, 192, 1)',
		// 		width: 2,
		// 		shadowBlur: 2,
		// 	},
		// 	areaStyle: {
		// 		normal: {
		// 			color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
		// 				{
		// 					offset: 0,
		// 					color: 'rgba(92, 106, 142, 0.4)'
		// 				},
		// 				{
		// 					offset: 1,
		// 					color: 'rgba(92, 106, 142, 0.4)'
		// 				}
		// 			], false),
		// 		}
		// 	},
		// 	data: lineData,
		// },
	],
};
