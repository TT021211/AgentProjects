import * as echarts from 'echarts';

export let option= {
	//你的代码
	tooltip: {
		backgroundColor: 'rgba(0, 13, 26, 0.80)',
		borderColor: 'rgba(143, 225, 252, 0)',
		padding: 8,
		textStyle: {
			color: '#fff',
		},
		trigger: 'axis',
		axisPointer: {
			type: 'shadow',
		},
		formatter: function (params) {
			var str = "";
			for (var i = 0; i < params.length; i++) {
				if (params[i].seriesName !== "") {
					str +=
						params[i].seriesName + "：" +
						params[i].value + 'kg'
					"<br/>";
				}
			}
			return str;
		}
	},
	legend: {
		data: ['成品入库', '成品出库'],
		icon: 'rect',
		itemWidth: 14,
		itemHeight: 14,
		right: 25,
		top:10,
		textStyle: {
			fontSize: 14,
			color: '#FFFFFF'
		}
	},
	xAxis: [
		{
			type: 'category',
			data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月'],
			axisPointer: {
				type: 'shadow'
			},
			axisLabel: {
				textStyle: {
					color: '#F5F5F5',  //更改坐标轴文字颜色
					fontSize: 12     //更改坐标轴文字大小
				}
			},
		}
	],
	yAxis: [
		{
			type: 'value',
			name: '',
			max: 1000000,
			minInterval: 1000,
			axisLabel: {
				textStyle: {
					color: '#F5F5F5',  //更改坐标轴文字颜色
					fontSize: 12     //更改坐标轴文字大小
				},
				formatter: '{value}'
			},
			splitLine: {
				//网格
				show: true,
				lineStyle: {
					color: '#dadde4',
					type: 'dashed'
				}
			}
		},

	],
	series: [
		{
			name: '成品入库',
			type: 'bar',
			itemStyle: {
				borderWidth: 1,
				opacity: 1, // 这个是 透明度
				// normal: {
				// 	color: 'rgba(54,164,255,0.35)',
				// 	borderColor: '#1578D8',
				// 	borderWidth: 1
				// },
				color: new echarts.graphic.LinearGradient(
					0,
					1,
					0,
					0,
					[
						{
							offset: 0,
							color: 'rgba(0,168,251, 0)' // 0% 处的颜色
						},
						{
							offset: 1,
							color: 'rgba(9,61,107, 1)' // 100% 处的颜色
						}
					],
					false
				)
			},
			// 实现数字展示在柱状图
			label: {
				show: false,
				position: 'top',
				fontSize: 12,
				color: '#F5F5F5',
				offset: [0, -10],
				formatter: '{c}' //添加单位
			},
			data: []
		},
		{
			name: '成品出库',
			type: 'bar',
			itemStyle: {
				borderWidth: 1,
				// lenged文本
				opacity: 1, // 这个是 透明度
				// normal: {
				// 	color: 'rgba(220,185,25, 1)',
				// 	borderColor: '#837734',
				// 	borderWidth: 1
				// },
				color: new echarts.graphic.LinearGradient(
					0,
					1,
					0,
					0,
					[
						{
							offset: 0,
							color: 'rgba(220,185,25, 1)' // 100% 处的颜色
						},
						{
							offset: 1,

							color: 'rgba(39,76,85, 1)' // 0% 处的颜色
						}
					],
					false
				)
			},
			label: {
				show: false,
				position: 'top',
				fontSize: 12,
				color: '#F5F5F5',
				offset: [0, -10],
				formatter: '{c}' //添加单位
			},
			data: []
		},
		// {
		// 	name: '剩余库存',
		// 	type: 'bar',
		// 	itemStyle: {
		// 		// lenged文本
		// 		opacity: 1, // 这个是 透明度
		// 		// normal: {
		// 		// 	color: 'rgba(220,185,25, 1)',
		// 		// 	borderColor: '#837734',
		// 		// 	borderWidth: 1
		// 		// },
		// 		color: new echarts.graphic.LinearGradient(
		// 			0,
		// 			1,
		// 			0,
		// 			0,
		// 			[
		// 				{
		// 					offset: 0,
		// 					color: 'rgba(18,212,227,0.9)' //
		// 				},
		// 				{
		// 					offset: 1,

		// 					color: 'rgba(10,59,100, 0.3)' //
		// 				}
		// 			],
		// 			false
		// 		)
		// 	},
		// 	// 实现数字展示在柱状图
		// 	label: {
		// 		show: false,
		// 		position: 'top',
		// 		fontSize: 12,
		// 		color: '#F5F5F5',
		// 		offset: [0, -10],
		// 		formatter: '{c}' //添加单位
		// 	},
		// 	data: [68, 17, 82, 77, 37, 66, 92, 72]
		// }
	]
};


