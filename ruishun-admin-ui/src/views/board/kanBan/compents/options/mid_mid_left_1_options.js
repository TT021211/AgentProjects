import * as echarts from 'echarts';

let lineColor = "#5AC2DB"
let axisData = [  "1",
	"2",
	"3",
	"4",
	"5",
	"6",
	"7",
	"8",
	"9",
	"10",]
let seriesData = [
	[410, 411, 418, 420, 421, 420, 423, 424, 420, 425, 427, 428],
]

export let option = {
	grid: {
		left: '1%',
		right: '1%',
		top: '20%',
		bottom: '1%',
		containLabel: true
	},
	xAxis: {
		type: "category",
		data: axisData,
		boundaryGap: true,
		splitLine: {
			show: false
		},
		axisTick: {
			show: false
		},
		// type: 'dashed',
		axisLine: {
			lineStyle: {
				type: 'dashed',
				color: "#2557B4"
			}
		},
		axisLabel: {
			margin: 10,
			textStyle: {
				fontSize: 14,
				color: "#C0C4CC"
			}
		}
	},
	yAxis: [
		{
			type: "value",
			position: "right",
			name: '',

			nameTextStyle: {
				padding: [0, 80, 0, 0],
				color: '#C0C4CC',
				fontSize: 13,
				fontWeight: 'bold',
				// lineHeight: 40,
			},
			splitLine: {
				show: false,
				lineStyle: {
					type: 'dashed',
					color: "#C0C4CC"
				}
			},
			axisLine: {
				show: false,
				lineStyle: {
					color: "rgba(255,255,255,0.3)"
				}
			},

			axisLabel: {
				show: false,
				margin: 2,
				textStyle: {
					fontSize: 12,
					color: "#DEEBFF"
				}
			}
		},],
	series: [
		{
			name: "流出",
			type: "line",
			smooth: true,
			showSymbol: false,
			symbol: 'rect',
			// showSymbol: true,
			// symbol: 'circle',
			data: seriesData[0],
			itemStyle: {
				normal: {
					color: '#287CE8'
				}
			},
			lineStyle: {
				normal: {
					width: 3
				}
			},
			areaStyle: {
				normal: {
					color: new echarts.graphic.LinearGradient(
						0,
						0,
						0,
						1,
						[
							{
								offset: 0,
								color: "rgba(9, 69, 123, 1)",
							},
							{
								offset: 1,
								color: "rgba(1, 84, 161, 0)",
							},
						],
						false
					),
				},
			},
			markLine: {
				name: '',
				symbol: 'rect',
				symbolSize: [0, 0],
				lineStyle: {
					type: 'solid', // 线条类型
					color: new echarts.graphic.LinearGradient(0, 0, 1, 0, [{
						offset: 0, color: 'rgba(255,255,255,0)' // 渐变开始的颜色
					}, {
						offset: 1, color: lineColor // 渐变结束的颜色
					}]), // 线条颜色
					width: 10, // 线条宽度
					borderWidth: 10,

				},
				data: [
					[{
						coord: [3, 0]
					}, {
						coord: [3, 420]
					}
					],
				]
			},
		}
	]
}

