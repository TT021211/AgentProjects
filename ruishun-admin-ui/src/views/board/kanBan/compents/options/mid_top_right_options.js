
export const data = [

	{
		name: '工单数量',
		value: 190,
	},
	// {
	// 	name: '未完成',
	// 	value: 110,
	// },

];
const color = [
	'#12CC9C',
	'#185D92',

];
const legendData = data.map((item, index) => {
	const tag = index % 6;
	return {
		'name': item.name,
		itemStyle: {
			color: color[tag] || '',
		},
		'textStyle': {
			'rich': {
				'title': {
					'color': '#CDCEDA',
					'fontSize': 14,
					'width': 70,
					'fontWeight': 'bold',
				},
				'p': {
					'fontSize': 14,
					'width': 55,
					'color': '#02D0C5',
					'shadowColor': '#000',
					'inactiveColor': '#000',
					'fontWeight': 'bold',
				},
			},
		},
	};
});

export let option = {
	// 'tooltip': {
	// 	'trigger': 'item',
	// 	backgroundColor: 'rgba(13,5,30,.6)',
	// 	borderWidth: 1,
	// 	borderColor: '#32A1FF',
	// 	padding: 5,
	// 	textStyle: {
	// 		color: '#fff'
	// 	},
	// 	formatter: data => {
	// 		const { name = '', value = '' } = data.data || {};
	// 		const name1 = name.length > 10 ? `${name.substring(0, 10)} <br /> ${name.substring(10, name.length)}` : name;
	// 		return `${name1} ${value}`;
	// 	},
	// },
	title: [
		{
			text: "{a|56.3}{b|%}",
			textStyle: {
				rich: {
					a: {
						color: "#fff",
						fontSize: 22,
						fontWeight: "bold",
						align: "55%",
					},
					b: {
						fontSize: 22,
						color: "#fff",
						fontWeight: "bold",
						align: "55%",
					}
				},

			},
			x: "42%",
			y: "36%",
		},
		{ // 副标题
			text: '工单总数量',     // '/n'代表换行
			textStyle: { // 标题样式
				fontSize: 16,
				color:'#fff',
			},
			top: '89%', // 位置
			left: '35%'
		}
	],
	'series': [
		{
			'name': '',
			'type': 'pie',
			'radius': [
				'45%',
				'70%',
			],
			'center': [
				'52%',
				'43%',
			],
			'startAngle': 90,
			'itemStyle': {
				'shadowBlur': 10,
				'shadowColor': 'rgba(0, 103, 255, 0.2)',
				'shadowOffsetX': -5,
				'shadowOffsetY': 5,

				color: function (params) {
					return color[params.dataIndex % 6];
				},
			},
			'label': {
				show: false,
			},
			'labelLine': {
			},
			data: data,
		},
		{
			name: '外边框',
			type: 'pie',
			clockwise: false, //顺时加载
			// silent: true,
			center: ['52%', '43%'],
			radius: ['80%', '80%'],
			label: { show: false },
			data: [{
				value: 9,
				name: '',
				itemStyle: {
					borderWidth: 3,
					borderColor: '#0b5263'
				}
			}]
		}

	],
};

