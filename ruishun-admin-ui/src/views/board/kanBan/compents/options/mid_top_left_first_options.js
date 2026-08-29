
export let option = {
	title: [
		{ // 标题
			text: '周一',
			borderColor: '#E5C418',
			left: 'center',
			borderWidth: 1, // 边框宽度（默认单位px）
			textStyle: { // 标题样式
				fontSize: 14,
				color:'#E5C418',
			},
			top: '13%' // 位置
		},
		{ // 副标题
			text: '78',     // '/n'代表换行
			borderColor: '#E5C418',
			left: 'center',
			textStyle: { // 标题样式
				fontSize: 14,
				color:'#E5C418',
			},
			top: '23%' // 位置
		}
	],
	grid: {
		top: '35%', // 将图表往下移动，你可以根据需要调整这个百分比
		left: '3%',
		right: '4%',
		bottom: '3%',
		containLabel: true
	},
	xAxis: {
		type: "category",
		boundaryGap: true,
		data: ["奉城镇"],
		axisLabel: {
			show: false
		},
		axisTick: {
			show: false
		},
		axisLine: {
			show: false
		},
		splitLine: {
			show: false,
			lineStyle: {
				color: "rgba(77, 128, 254, 0.2)"
			}
		},
		splitArea: {
			show: true,
			areaStyle: {
				color: ["rgba(255,255,255, 0.05)", "rgba(255,255,255, 0)"]
			}
		},
	},
	yAxis: [
		{
			type: "value",
			splitNumber: 6,
			axisLabel: {
				show: false
			},
			axisLine: {
				show: false
			},
			axisTick: {
				show: false
			},
			splitLine: {
				show: false
			},
			splitArea: {
				show: true,
				interval: '0',
				areaStyle: {
					barWidth: '100%',
					opacity: 1,
					color: ["rgba(71,60,6, 0.05)", "rgba(71,60,6, 0.04)","rgba(71,60,6, 0.03)","rgba(71,60,6, 0.02)","rgba(71,60,6, 0.01)"]
				}
			},
		}
	],
	series: [
		{
			name: '1月',
			type: "pictorialBar",
			barWidth: "100%",
			stack: "总量",
			label: {
				normal: {
					show: false
				}
			},
			itemStyle: {
				normal: {
					color: {
						type: "linear",
						x: 0,
						y: 0,
						x2: 0,
						y2: 1,
						colorStops: [
							{
								offset: 0,
								color: "rgba(128,106,24, 0.8)" // 0% 处的颜色
							},
							{
								offset: 1,
								color: "rgba(128,106,24, 1)" // 100% 处的颜色
							}
						],
						globalCoord: false
					},
					borderColor: 'rgba(214,158,32, 1)', // 边框颜色
					borderWidth: 6, // 边框宽度

				}
			},
			symbol: "path://M12.000,-0.000 C12.000,-0.000 16.074,60.121 22.731,60.121 C26.173,60.121 -3.234,60.121 0.511,60.121 C7.072,60.121 12.000,-0.000 12.000,-0.000 Z",
			data: [23]
		}
	]
};

