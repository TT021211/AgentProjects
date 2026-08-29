import * as echarts from 'echarts';

let data = [
	{ value: 160, name: '运行中' },
	{ value: 3, name: '待机中' },
	// { value: 165, name: '总数' },
];
const colorList = [
	new echarts.graphic.LinearGradient(0, 0, 1, 0, [{
		offset: 0,
		color: "rgba(0,9,49,1)"
	},
		{
			offset: 1,
			color: "rgba(49,253,186,0.8)"
		}
	]),
	new echarts.graphic.LinearGradient(1, 0, 0, 0, [{
		offset: 0,
		color: "rgba(80,81,46,0.2)"
	},
		{
			offset: 1,
			color: "rgba(201,164,40,0.8)"
		}
	]),
	new echarts.graphic.LinearGradient(0, 1, 0, 0, [{
		offset: 0,
		color: "rgba(3,19,62,0.2)"
	},
		{
			offset: 1,
			color: "rgba(47,159,250,0.8)"
		}
	])
];
export let option =  {
	// color: color,
	tooltip: {
		trigger: 'item',
		backgroundColor: 'rgba(0,0,0,0.5)',
		padding: [8, 16],
		textStyle: {
			color: '#fff',
			fontSize: 16
		},

		formatter: function (params) {
			return (
				params.marker +
				'<span style="color:' +
				params.color +
				'">' +
				params.data['name'] +
				'\n' +
				params.data['value'] +
				'</span>'
			);
		}
	},
	title: {
		text: '160',
		subtext: `总数`,
		top: '36%',
		left: 'center',
		textStyle: {
			color: '#fff',
			fontSize: 20,
			fontWeight: 400
		},
		subtextStyle: {
			color: '#fff',
			fontSize: 16,
			fontWeight: 400
		}
	},
	series: [
		{
			name: "",
			type: 'gauge',
			splitNumber: 60, //刻度数量
			min: 0,
			max: 100,
			radius: '120%', //图表尺寸
			center: ['50%', '50%'],
			startAngle: 90,
			endAngle: -270,
			axisLine: {
				show: false
			},
			axisTick: {
				show: false
			},
			splitLine: {
				show: true,
				// length: 30,
				length: '8%',
				lineStyle: {
					color: '#0dc2fe',
					width: 1
				}
			},
			axisLabel: {
				show: false
			},
			pointer: { //仪表盘指针
				show: 0,
			},
			detail: {
				show: 0,
			},
		},
		{
			name: '',
			type: 'gauge',
			splitNumber: 30, //刻度数量
			min: 0,
			max: 100,
			radius: '120%', //图表尺寸
			center:['50%', '50%'],
			startAngle: 90,
			endAngle: -270,
			axisLine: {
				show: false,
			},
			axisTick: {
				show: true,
				lineStyle: {
					color: {
						type: 'linear',
						x: 0,
						y: 0,
						x2: 0,
						y2: 1,
						colorStops: [{
							offset: 0,
							color: '#0dc2fe' // 0% 处的颜色
						}, {
							offset: 1,
							color: '#051349' // 100% 处的颜色
						}],
						global: false // 缺省为 false
					},
					// color: '#0dc2fe',
					width: 2
				},
				// length: 15,
				length: '5%',
				splitNumber: 5
			},
			splitLine: {
				show: false,
			},
			axisLabel: {
				show: false
			},
			pointer: { //仪表盘指针
				show: 0,
			},
		},
		{
			name: '',
			type: 'pie',
			radius: ['79%', '50%'],
			center: ['50%', '50%'], // 修改为居中
			avoidLabelOverlap: true,
			label: {
				show: false
			},
			itemStyle: {
				color: params => {
					return colorList[params.dataIndex];
				}
			},
			labelLine: {
				show: false
			},
			data: data
		},

		{
			name: '',
			type: 'gauge',
			splitNumber: 30, //刻度数量
			min: 0,
			max: 100,
			radius: '70%', //图表尺寸
			center:['50%', '50%'],
			startAngle: 90,
			endAngle: -270,
			axisLine: {
				show: false,
			},
			axisTick: {
				show: true,
				lineStyle: {
					color: {
						type: 'linear',
						x: 0,
						y: 0,
						x2: 0,
						y2: 1,
						colorStops: [{
							offset: 0,
							color: '#0dc2fe' // 0% 处的颜色
						}, {
							offset: 1,
							color: '#051349' // 100% 处的颜色
						}],
						global: false // 缺省为 false
					},
					// color: '#0dc2fe',
					width: 2
				},
				// length: 15,
				length: '5%',
				splitNumber: 5
			},
			splitLine: {
				show: false,
			},
			axisLabel: {
				show: false
			},
			pointer: { //仪表盘指针
				show: 0,
			},
		},

	]
};


