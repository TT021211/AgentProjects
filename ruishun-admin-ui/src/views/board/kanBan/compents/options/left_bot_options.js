import * as echarts from 'echarts';

export let option = {
	grid: {
		top: "10%",
		left: "5%",
		right: "5%",
		bottom: "10%",
		containLabel: true,
	},
	tooltip: {
		trigger: "axis",
		backgroundColor: "transparent",
		axisPointer: {
			lineStyle: {
				color: "#3763cd", //显示竖线颜色
				type: "solid",
			},
		},
		textStyle:{
			color:"#ffffff"
		},
		formatter: function (params) {
			// params 是一个包含当前悬浮点信息的对象
			// 这里假设你的数据是一维的，即只有一个系列的数据
			var result = '良品率 ';
			if (params instanceof Array) {
				// 如果是多系列，则遍历每个系列
				params.forEach(function (item) {
					const value = item.value === 0 ?  '暂无该月数据' : item.value + '%'
					result += item.marker + " ：" + value + '<br/>';
				});
			} else {
				// 单系列的情况
				result += params.marker + " ：" + params.value + '%';
			}


			return result;
		}
	},
	xAxis: [
		{
			type: "category",
			boundaryGap: false,
			axisLine: {
				//坐标轴轴线相关设置。数学上的x轴
				show: false,
				lineStyle: {
					color: "#233653",
				},
			},
			splitLine: {
				show: false,
				interval: 0,
				lineStyle: {
					color: "#304d81",
					type: "dashed",
				},
			},
			axisLabel: {
				show: true,
				interval: 0,
				textStyle: {
					color: "#b8c4d9",
					// padding: 10, //下方文字距离
					fontSize: 14,
				},
				formatter: function (value) {
					if (value === 0) {
						return value;
					}
					return value;
				},
			},
			axisTick: {
				show: false,
			},
			data: [
				'1月',
				'2月',
				'3月',
				'4月',
				'5月',
				'6月',
				'7月',
				'8月',
				'9月',
				'10月',
				'11月',
				'12月'
			],
		},
	],
	yAxis: [
		{
			// name: "良品率",
			nameTextStyle: {
				color: "#7ec7ff",
				fontSize: "13px",
				  // padding: [0, 20, 0, 0],
			},
			min: 85,
			max : 100,
			splitLine: {
				show: false,
				lineStyle: {
					color: "red",
				},
			},
			axisLine: {
				show: false,
				lineStyle: {
					color: "#315182",
				},
			},
			axisLabel: {
				show: true,
				interval: 10,
				textStyle: {
					color: "#b8c4d9",
					padding: 10,
					fontSize: 14,
				},
				formatter: function (value) {
					if (value === 0) {
						return value + '%';
					}
					return value + '%';
				},
			},
			axisTick: {
				show: false,
			},
		},
	],
	series: [
		{
			type: "line",
			// symbol: "circle", // 默认是空心圆（中间是白色的），改成实心圆
			smooth: true,
			showSymbol: true, //圆点显隐
			symbolSize: 10, //圆点大小
			lineStyle: {
				normal: {
					width: 4,
					color: "#22e1db", // 线条颜色
				},
			},
			itemStyle: {
				color: "#b1fffd", //圆角边框颜色
				borderWidth: 5,
			},

			areaStyle: {
				//区域填充样式
				normal: {
					//线性渐变，前4个参数分别是x0,y0,x2,y2(范围0~1);相当于图形包围盒中的百分比。如果最后一个参数是‘true’，则该四个值是绝对像素位置。
					color: new echarts.graphic.LinearGradient(
						0,
						0,
						0,
						1,
						[
							{
								offset: 0,
								color: "rgba(6,201,226,1)",
							},
							{
								offset: 1,
								color: "rgba(2,17,61,0)",
							},
						],
						false
					),
					shadowColor: "#0D7BA7", //阴影颜色
					shadowBlur: 10, //shadowBlur设图形阴影的模糊大小。配合shadowColor,shadowOffsetX/Y, 设置图形的阴影效果。
				},
			},
			label: {
				show: true,
				position: "top",
				textStyle: {
					fontSize: 14,
					color: "#91CDFE",
				},
				formatter: function (params) {
					// 自定义标签内容
					return params.value + '%';
				},
			},
			data: [],
		},

	],
};

