
export let option = {
	title: [
		{ // 标题
			text: '周二',
			left: 'center',
			borderColor: '#fff',
			borderWidth: 1, // 边框宽度（默认单位px）
			textStyle: { // 标题样式
				fontSize: 14,
				color:'#fff',
			},
			top: '13%' // 位置
		},
		{ // 副标题
			text: '65',     // '/n'代表换行
			borderColor: '#fff',
			left: 'center',

			textStyle: { // 标题样式
				fontSize: 14,
				color:'#fff',
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
		data: ["进度"],
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
	},
	yAxis: [
		{
			type: "value",
			splitNumber: 3,
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
					barWidth: '10%',
					opacity: 1,
					color: ["rgba(6,70,121, 0.5)", "rgba(6,70,121, 0.4)","rgba(6,70,121, 0.3)","rgba(6,70,121, 0.2)","rgba(6,70,121, 0.1)"]
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
								color: "rgba(6,62,105, 0.8)" // 0% 处的颜色
							},
							{
								offset: 1,
								color: "rgba(6,62,105, 1)" // 100% 处的颜色
							}
						],
						globalCoord: false
					},
					borderColor: 'rgba(15,108,168, 1)', // 边框颜色
					borderWidth: 6, // 边框宽度

				}
			},
			symbol: "path://M12.000,-0.000 C12.000,-0.000 16.074,60.121 22.731,60.121 C26.173,60.121 -3.234,60.121 0.511,60.121 C7.072,60.121 12.000,-0.000 12.000,-0.000 Z",
			data: [23]
		}
	]
};

