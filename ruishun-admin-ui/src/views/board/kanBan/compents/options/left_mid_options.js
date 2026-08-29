

let xjcs = [35, 22, 41, 33, 28, 46, 30];
let xjgls = [35, 22, 41, 33, 28, 46, 30];
let myData1 = [
	"周一",
	"周二",
	"周三",
	"周四",
	"周五",
	"周六",
	"周日",
];
export let option =    {
	tooltip: {
		trigger: "axis",
		axisPointer: {
			type: "shadow",
		},
		textStyle: {
			color: "#fff",
			fontSize: 12,
		},
		confine: true, // 超出范围
		backgroundColor: "rgba(17,95,182,0.5)", //设置背景颜色
		//   borderColor: "rgba(255, 255, 255, .8)",
		formatter: "{b}<br>{a}：{c}/个<br>{a1}：{c1}/个",
	},
	legend: {
		top: "4%",
		right: "14%",
		itemWidth: 18,
		itemHeight: 10,
		itemGap: 30,
		textStyle: {
			fontSize: 12,
			color: "#fff",
			padding: [0, 0, 0, 10],
		},
	},
	grid: {
		top: "18%",
		left: "4%",
		right: "3%",
		bottom: "2%",
		containLabel: true,
	},
	xAxis: [
		{
			type: "category",
			data: myData1,
			axisTick: {
				show: false,
				alignWithLabel: true,
			},
			axisLine: {
				lineStyle: {
					color: "#1C82C5",
				},
			},
			axisLabel: {
				interval: 0,
				margin: 10,
				color: "#05D5FF",
				textStyle: {
					fontSize: 14,
					color: "#fff",
				},
			},
		},
	],
	yAxis: [
		{
			type: "value",
			name: "/个",
			splitNumber: 5,
			nameTextStyle: {
				color: "#fff",
				fontSize: 12,
				align: "center",
				padding: [0, 28, 0, 0],
			},
			axisLabel: {
				formatter: "{value}",
				color: "rgba(95, 187, 235, 1)",
				textStyle: {
					fontSize: 14,
					color: "#fff",
					lineHeight: 16,
				},
			},
			axisTick: {
				show: false,
			},
			splitLine: {
				show: true,
				lineStyle: {
					color: "rgba(28, 130, 197, .3)",
					type: "dashed",
				},
			},
		},
		{
			name: "/个",
			splitNumber: 5,
			type: "value",
			nameTextStyle: {
				color: "#fff",
				fontSize: 12,
				align: "center",
				padding: [0, 0, 0, 25],
			},
			axisLabel: {
				show: true,
				fontSize: 12,
				color: "#fff",
			},
			axisLine: {
				show: false,
			},
			axisTick: {
				show: false,
			},
			splitLine: {
				show: false,
			},
		},
	],
	series: [
		{
			name: "已排产",
			type: "bar",
			barWidth: "12",
			data: xjcs,
			itemStyle: {
				normal: {
					color: {
						x: 0,
						y: 0,
						x2: 0,
						y2: 1,
						colorStops: [
							{
								offset: 0,
								color: "rgba(9, 63, 109, 1)",
							},
							{
								offset: 1,
								color: "rgba(1, 166, 252, 1)",
							},
						],
					},
					borderColor: "#3681FF",
				},
				// borderRadius: [2, 2, 0, 0],
			},
		},
		{
			name: "已完成",
			type: "line",
			yAxisIndex: 1, // 与第二个 y 轴关联
			showSymbol: true,
			symbol: 'rect',
			symbolSize: 8,
			lineStyle: {
				normal: {
					color: "#E6D28B",
				},
			},
			itemStyle: {
				color: "#E6D28B",
				borderColor: "#E6D28B",
				borderWidth: 1,
			},
			label: {
				show: true,
				position: "top",
				textStyle: {
					fontSize: 14,
					color: "#91CDFE",
				},
			},
			data: xjgls, // 折线图的数据
		},
	],
};

