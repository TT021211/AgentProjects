import * as echarts from 'echarts';

let data = {
	one: 263,
	two: 291,
	three: 693,
	four: 1396,
};
// 复制代码
const chartData = [
	{
		value: 100,
		name: "",
		itemStyle: {
			shadowBlur: 20,
			shadowColor: "#3C4039",
			shadowOffsetx: 25,
			shadowOffsety: 20,
			color: "rgba(85,128,162, 1)",
		},
	},
	// {
	// 	value: data.two,
	// 	name: "二类",
	// 	itemStyle: {
	// 		shadowBlur: 20,
	// 		shadowColor: "#0C3E4E",
	// 		shadowOffsetx: 25,
	// 		shadowOffsety: 20,
	// 		color: "rgba(140,149,118, 1)",
	// 	},
	// },
	// {
	// 	value: data.three,
	// 	name: "三类",
	// 	itemStyle: {
	// 		shadowBlur: 20,
	// 		shadowColor: "#043E39",
	// 		shadowOffsetx: 25,
	// 		shadowOffsety: 20,
	// 		color: "rgba(85,139,175, 1)",
	// 	},
	// },
	// {
	// 	value: data.four,
	// 	name: "四类",
	// 	itemStyle: {
	// 		shadowBlur: 20,
	// 		shadowColor: "#043E39",
	// 		shadowOffsetx: 25,
	// 		shadowOffsety: 20,
	// 		color: "rgba(67,132,164, 1)",
	// 	},
	// },
];
const sum = chartData.reduce((per, cur) => per + cur.value, 0);
const gap = (1 * sum) / 100;
const pieData1 = [];
// const gapData = {
// 	name: "",
// 	value: gap,
// 	itemStyle: {
// 		color: "transparent",
// 	},
// };

//图标位置显示
// let total = 0;
// chartData.forEach((item) => {
//   total += item.value;
// });

for (let i = 0; i < chartData.length; i++) {
	// 第一圈数据
	pieData1.push({
		...chartData[i],
	});
	// pieData1.push(gapData);
}
// console.log('pieData1 ==>',pieData1);

export default  {
	backgroundColor: "transparent",
		title: {
	show: true,
		text: "总数",
		subtext: "5295",
		x: "43%",
		y: "43%",
		itemGap: 1,
		textStyle: {
		color: "#62809A",
			fontSize: 20,
			fontWeight: "400",
			lineHeight: 10
	},
	subtextStyle: {
		color: "#62809A",
			fontSize: 16,
			fontWeight: "400",
			lineHeight: 10
	},
	textAlign: "center",
},

	series: [
		{
			color: ["#163d59"],
			type: "gauge",
			center: ["45%", "50%"],
			startAngle: 150,
			endAngle: -209.999,
			splitNumber: 12,
			radius: "70%",
			pointer: {
				show: false,
			},
			progress: {
				show: false,
				roundCap: false,
				width: 1,
			},
			axisLine: {
				show: false,
				lineStyle: {
					width: 10,
					color: [
						[0, "#163d59"],
						[0.5, "#163d59"],
						[1, "#163d59"],
					],
				},
			},
			axisTick: {
				distance: -25,
				length: 10,
				splitNumber: 10,
				lineStyle: {
					width: 1,
					color: "#3771A1",
				},
			},
			splitLine: {
				distance: -35,
				length: 10,
				lineStyle: {
					width: 3,
					color: "#5594BF",
				},
			},
			axisLabel: {
				show: false,
			},
			anchor: {
				show: false,
			},
			title: {
				show: false,
			},
			detail: {
				show: false,
			},
			// data: this.data1,
		},
		{
			name: "",
			type: "pie",
			roundCap: true,
			radius: ["50%", "65%"],
			center: ["45%", "50%"],
			data: pieData1,
			labelLine: {
				length: 8,
				length2: 16,
				lineStyle: {
					width: 1,
				},
			},
			// label: {
			// 	show: true,
			// 	fontFamily: "ysbth",
			// 	position: "outside",
			// 	padding: [0, -4, 0, -4],
			// 	formatter(params) {
			// 		if (params.name === "") {
			// 			return "";
			// 		}
			// 		return `${params.percent.toFixed(0)}%`;
			// 	},
			// 	color: "#fff",
			// 	fontSize: "14px",
			// 	lineHeight: 10,
			// },
		},
		{
			type: "pie",
			radius: ["32%", "34%"],
			center: ["45%", "50%"],
			animation: false,
			hoverAnimation: false,
			// data: [
			// 	{
			// 		value: 100,
			// 	},
			// ],
			label: {
				show: false,
			},
			itemStyle: {
				normal: {
					color: "#3BC5EF",
				},
			},
		},
		{
			name: "",
			type: "pie",
			startAngle: 90,
			radius: "32%",
			animation: false,
			hoverAnimation: false,
			center: ["45%", "50%"],
			itemStyle: {
				normal: {
					labelLine: {
						show: false,
					},
					color: new echarts.graphic.RadialGradient(0.5, 0.5, 1, [
						{
							offset: 1,
							color: "rgba(50,171,241, 0)",
						},
						{
							offset: 0.5,
							color: "rgba(50,171,241, .4)",
						},
						{
							offset: 0,
							color: "rgba(55,70,130, 0)",
						},
					]),
					shadowBlur: 60,
				},
			},
			data: [
				{
					value: 100,
				},
			],
		},
	],
};

