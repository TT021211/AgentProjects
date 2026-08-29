
<template>
	<div class="screen-wrapper" id="screen-wrapper">
		<div class="screen" id="screen">
			<div class="title" >瑞顺工业互联网平台</div>

			<!--			// 左侧区域-->
			<div class="left_area left_top_area">
				<div class="left_top_div">
					<div class="alltitle" v-show="!loading1">今日质检数量</div>
					<dv-loading style="" v-show="loading1"></dv-loading>
					<div class="left_cycle">
						<left-top
							v-show="!loading1"
							ref="leftTop"
							:detectionDayNum="detectionDayNum"
							:year="null"
							:height="'300px'"
						/>
					</div>
				</div>
			</div>
			<!-- // 左侧区域
			<div class="left_area left_top_area">
				<div class="left_top_div" >
					<div class="alltitle">产线生态优势</div>
							<dv-loading v-show="loading1">Loading...</dv-loading>
							<div class="left_cycle" >
								<left-top v-show="!loading1" ref="leftTop" :year="year"  @endLoading="endLoading"/>
							</div>
				</div>>
			</div> -->

			<div class="left_area left_mid_area" @click="toDetail('/produce/workOrderArrange')">
				<div class="left_top_div" >
					<div class="alltitle">生产工单管理</div>
					<div class="right_select">
						<div class="active">当日产量</div>
						<div>当月产量</div>
					</div>
					<div class="bot_cycle"></div>
					<div class="bar_area" style="margin-top: 40px">
						<dv-loading v-show="loading2">Loading...</dv-loading>
						<left-middle
							v-show="!loading2"
							ref="leftMiddle"
							:year="year"
							:productionData="productionData"
							width="450px"
							height="210px"
						/>
					</div>
				</div>
			</div>

			<div class="left_area left_bot_area" @click="toDetail('/quality/rawMaterialSummary')">
				<div class="top_title" >
					<span>质量数据</span>
				</div>
				<div class="bar_area" style="margin-top: 10px">
					<dv-loading v-show="loading3"></dv-loading>
					<left-bot
						v-show="!loading3"
						ref="leftBot"
						:year="year"
						width="480px"
						height="290px"
						:realData="leftBotData"
					/>
				</div>
			</div>

			<div class="left_allow">

			</div>
			<div class="right_allow">

			</div>
			<div class="mid_area mid_top_area">

				<div class="top_title" @click="toDetail('/plan/produceOrder')">
					<span>生产计划与进度</span>
				</div>
				<div class="bar_area">
           <div class="left">
						 <div class="yxz">
							 <div class="bar_title">本周排产总数</div>
							 <div class="content">
								 <div class="progress">
									 45
								 </div>
							 </div>
						 </div>
					 </div>
					<div class="tips" style="position: absolute;
						top: 47px;
						right: 300px;
						color: #fff;
						font-size: 12px;">
						本周计划完成量
					</div>
           <div class="mid">
						<div class="area2"  v-for="item in productionData.wiredrawingNumWeek" :key="item.date">
							<mid-top-other
								v-show="!loading2"
								:year="year"
								:chart-data="item"
								width="70px"
								height="215px"
							/>
						</div>
					 </div>
           <!-- <div class="right">
						 <div class="yxz2">
							 <div class="content">
								 <dv-loading v-show="loading9">Loading...</dv-loading>
								 <mid-top-right v-show="!loading9"  :year="year"  @endLoading="endLoading" width="218px" height="210px"/>
							 </div>
						 </div>
					 </div> -->
				</div>
			</div>

			<div class="mid_area mid_mid_area">
				<div class="left_area" style="margin-top: 20px;">
					<div class="content_area">
						<div class="ls_content" >
							<div class="left-side">
							</div>
							<div class="right-side">
								<div class="upper-content"><span style="font-size: 12px">排产数量</span></div>
								<div class="upper-content"><span style="font-size:16px;margin-left:3px;color:#00A2CB;font-weight: 400">{{productionData.completeWorkorder}}个</span></div>
							</div>
						</div>
						<div class="bar_area">
							<dv-loading v-show="loading10">Loading...</dv-loading>
							<mid-mid-first v-show="!loading10"  :year="year"  @endLoading="endLoading" width="375px" height="70px"/>

						</div>
					</div>
					<div class="content_area">
						<div class="ls_content" >
							<div class="left-side">
							</div>
							<div class="right-side">
								<div class="upper-content"><span style="font-size: 12px">生产数量</span></div>
								<div class="upper-content"><span style="font-size:16px;margin-left:3px;color:#D6B514;font-weight: 400">{{productionData.schedulingWorkorder}}个</span></div>
							</div>
						</div>
						<div class="bar_area">
							<dv-loading v-show="loading10">Loading...</dv-loading>
							<mid-mid-first v-show="!loading10"  :year="year"  @endLoading="endLoading" width="375px" height="70px"/>
						</div>
					</div>
					<div class="content_area">
						<div class="ls_content" >
							<div class="left-side">
							</div>
							<div class="right-side">
								<div class="upper-content"><span style="font-size: 12px">完工数量</span></div>
								<div class="upper-content"><span style="font-size:16px;margin-left:3px;color:#00A2CB;font-weight: 400">{{productionData.productionWorkorder}}个</span></div>
							</div>
						</div>
						<div class="bar_area">
							<dv-loading v-show="loading10">Loading...</dv-loading>
							<mid-mid-first v-show="!loading10"  :year="year"  @endLoading="endLoading" width="375px" height="70px"/>
						</div>
					</div>

				</div>

				<div class="right_area" style="margin-top: 30px;">
					<div class="yxz2">
						<div class="content">
							<dv-loading v-show="loading9">Loading...</dv-loading>
							<mid-top-right
								v-show="!loading9"
								:year="year"
								:production-data="productionData"
								@endLoading="endLoading"
								width="268px"
								height="240px"
							/>
						</div>
					</div>
				</div>
			</div>
			<div class="mid_area mid_bot_area">
				<dv-loading v-show="loading11">Loading...</dv-loading>
				<mid-mid-bot v-show="!loading11"  :year="year"  @endLoading="endLoading" width="900px" height="220px"/>
			</div>

			<!-- <div class="mid_area mid_bot_country_area" >
				<div class="total_count"></div>
				<div class="cunt_count">0</div>
				<div class="cunt_count">0</div>

				<div class="cunt_count">0</div>
				<div class="douhao"></div>
				<div class="cunt_count">0</div>
				<div class="cunt_count">5</div>

				<div class="cunt_count">5</div>
				<div class="douhao"></div>
				<div class="cunt_count">2</div>
				<div class="cunt_count">4</div>
				<div class="cunt_count">4</div>
				<div style="color:#86C3FF;font-size: 20px;font-weight: 400;padding-top: 36px;">单位：吨</div>
				<div class="unit"></div>
			</div> -->

			<div class="mid_area mid_bot_end_area">
				<div class="item">
					<div class="top">
						<span style="font-size: 22px;
    font-weight: bolder;
    color: #fff;">25吨</span>
					</div>
					<div class="bottom">
						<div class="left">

						</div>
						<div class="bot_text">今日产量</div>
						<div class="right"></div>
					</div>
				</div>
				<div class="item">
					<div class="top">
						<span style="font-size: 22px;
    font-weight: bolder;
    color: #fff;">86%</span>
					</div>
					<div class="bottom">
						<div class="left">

						</div>
						<div class="bot_text">设备在线率</div>
						<div class="right"></div>
					</div>
				</div>
				<div class="item">
					<div class="top">
						<dv-loading v-show="loading6">Loading...</dv-loading>
						<span v-show="!loading6" style="font-size: 22px; font-weight: bolder; color: #fff"
							>{{energyConsumptionToday}}kw/H</span
						>
					</div>
					<div class="bottom">
						<div class="left"></div>
						<div class="bot_text">今日能耗</div>
						<div class="right"></div>
					</div>
				</div>
			</div>




<!--			// 右侧区域-->

			<div class="right_area right_top_area">
				<div class="top_title" @click="toDetail('/storage/storageRecording')">
					<span>仓储详情</span>
				</div>
				<div class="right_top_bar">
					<dv-loading v-show="loading4">Loading...</dv-loading>
					<right-top
						v-show="!loading4"
						ref="leftBot"
						:year="year"
						width="650px"
						:height="'300px'"
						:realData="rightTopData"
					/>
				</div>
			</div>
			<div class="right_area right_mid_top_area">
				<div class="box">
					<div class="inner-box">
						<div class="image image1"></div>
						<div class="text">
							<div class="weight">28405<span style="font-size: 18px;">箱</span></div>
							<div class="product">成品总箱数</div>
						</div>
					</div>
				</div>
				<div class="box">
					<div class="inner-box">
						<div class="image image2"></div>
						<div class="text">
							<div class="weight">625.40<span style="font-size: 18px;">吨</span></div>
							<div class="product">成品</div>
						</div>
					</div>
				</div>
				<div class="box">
					<div class="inner-box">
						<div class="image image3"></div>
						<div class="text">
							<div class="weight">68<span style="font-size: 18px;">吨</span></div>
							<div class="product">原材料</div>
						</div>
					</div>
				</div>
				<div class="box">
					<div class="inner-box">
						<div class="image image4"></div>
						<div class="text">
							<div class="weight">448.15<span style="font-size: 18px;">吨</span></div>
							<div class="product">铜丝半成品</div>
						</div>
					</div>
				</div>
			</div>

			<div class="right_area right_mid_bot_area">
				<div class="top_title" @click="toDetail('/device/equipmentLedger')">
					<span>设备状态</span>
				</div>
				<div class="bar_area">
					<div class="yxz">
						<div class="bar_title">运行中</div>
						<div class="content">
							<dv-loading v-show="loading5">Loading...</dv-loading>
							<right-middle
								v-show="!loading5"
								ref="leftBot"
								:year="year"
								@endLoading="endLoading"
								width="200px"
								:deviceCount="102"
								height="160px"
							/>
						</div>
					</div>
					<div class="djz">
						<div class="bar_title">待机中</div>
						<div class="content">
							<dv-loading v-show="loading5">Loading...</dv-loading>
							<right-middle
								v-show="!loading5"
								ref="leftBot"
								:deviceCount="40"
								:year="year"
								@endLoading="endLoading"
								width="200px"
								height="160px"
							/>
						</div>
					</div>
					<!-- <div class="gxz">
						<div class="bar_title">更新中</div>
						<div class="content">
							<dv-loading v-show="loading5">Loading...</dv-loading>
							<right-middle v-show="!loading5" ref="leftBot" :year="year"  @endLoading="endLoading" width="200px" height="160px"/>
						</div>
					</div> -->
				</div>
			</div>
			<div class="right_area right_bot_area">
				<div class="top_title"  @click="toDetail('/energy/electricity/dosage')">
					<span>能耗管理</span>
				</div>
				<div class="bar_area">
					<div
						class="line_area"
						style="width: 290px; height: 195px; position: absolute; left: 5px"
					>
						<div class="alltitle" style="font-size: 14px; top: 0px; left: 0px">
							能耗统计
						</div>
						<div class="bot_cycle"></div>
						<dv-loading v-show="loading6">Loading...</dv-loading>
						<right-bottom
							v-show="!loading6"
							ref="leftBot"
							:year="year"
							width="320px"
							height="160px"
							:energyData="energyData"
						/>
					</div>
					<div
						class="cj_area"
						style="
							width: 288px;
							height: 195px;
							position: absolute;
							display: grid;
							grid-template-rows: 1fr 1fr;
							right: 20px;
						"
					>
					<dv-loading v-show="loading6">Loading...</dv-loading>
						<div class="ls_area" 	v-show="!loading6">
							<div
								class="alltitle"
								style="font-size: 16px; top: 0px; left: 0px"
							>
								拉丝车间
							</div>
							<div class="ls_content">
								<div class="left-side"></div>
								<div class="right-side">
									<div class="upper-content">
										<span style="font-size: 12px">年用电总量</span
										><span
											style="
												font-size: 14px;
												margin-left: 3px;
												color: #00a2cb;
												font-weight: 400;
											"
											>{{energyData.LSYear}}KWH</span
										>
									</div>
									<div class="text-middle"></div>
									<div class="upper-content">
										<span style="font-size: 12px">月用电总量</span
										><span
											style="
												font-size: 14px;
												margin-left: 3px;
												color: #00a2cb;
												font-weight: 400;
											"
											>{{energyData.LSMonth}}KWH</span
										>
									</div>
								</div>
							</div>
						</div>
						<div class="qb_area" 	v-show="!loading6">
							<div
								class="alltitle"
								style="font-size: 14px; top: 0px; left: 0px"
							>
								工艺车间
							</div>
							<div class="ls_content">
								<div class="left-side"></div>
								<div class="right-side">
									<div class="upper-content">
										<span style="font-size: 12px">年用电总量</span
										><span
											style="
												font-size: 14px;
												margin-left: 3px;
												color: #00a2cb;
												font-weight: 400;
											"
											>{{energyData.QBYear}}KWH</span
										>
									</div>
									<div class="text-middle"></div>
									<div class="upper-content">
										<span style="font-size: 12px">月用电总量</span
										><span
											style="
												font-size: 14px;
												margin-left: 3px;
												color: #00a2cb;
												font-weight: 400;
											"
											>{{energyData.QBMoth}}KWH</span
										>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>

		</div>
	</div>
</template>

<script>
import  leftTop  from './compents/leftTop.vue'
import  leftMiddle  from './compents/leftMiddle.vue'
import  leftBot  from './compents/leftBot.vue'
import  rightMiddle  from './compents/rightMiddle.vue'
import  rightTop  from './compents/rightTop.vue'
import  leftBottom  from './compents/leftBotton.vue'
import  rightBottom  from './compents/rightBottom.vue'
import  midTopFirst  from './compents/midTopFirst.vue'
import  midTopOther  from './compents/midTopOther.vue'
import  midTopRight  from './compents/midTopRight.vue'
import  midMidFirst  from './compents/midMidFirst.vue'
import  midMidBot  from './compents/midMidBot.vue'
// import {defineComponent} from "@vue/composition-api";
import { defineComponent } from 'vue';

import { getWmsKanban, getQcKanbanPage,getEcmElectricityConsumption,getMesProductWorkorder} from "@/service/api/board/kanBan";
import {debounce} from './compents/dashboard/mixins/resize';
export default defineComponent({

	name: 'HomeView',
	components: {
		leftTop, leftMiddle,leftBot,rightTop,rightMiddle, leftBottom,rightBottom,midTopFirst,midTopOther, midTopRight,midMidFirst,midMidBot
	},
	data() {
		return {
			year: null,
			totalCount: 0,
			totalWeight: 0,
			weightUnit: '',
			loading1: true,
			loading2: true,
			loading3: true,
			loading4: true,
			loading5: true,
			loading6: true,
			loading7: true,
			loading8: true,
			loading9: true,
			loading10: true,
			loading11: true,
			loading12: true,
			loading13: true,
			loading14: true,
			loading15: true,
			dataForCount: [],
			dataForWeight: [],
			rightTopCountIn: [],
			rightTopCountOut: [],
			rightTopWeightIn: [],
			rightTopWeightOut: [],
			dataLegend: [],
			yData: [],

			lbNames:[],
			lbCount: [],
			lbWeight: [],

			rbNames:[],
			rbCount: [],
			rbWeight: [],

			wmsData: {
				ingredientsNum: 0,
				semiFinishedCopperNum: 0,
				finishedProductNum: 0,
				finishedProductWeight: 0,
			},
			rightTopData: {
				monthData: [],
				outData: [],
				inData: [],
			},
			leftBotData: {
				seriesData: [],
			},
			detectionDayNum: 0,
			debouncedHandleResize: null,
			energyData : {
				LSMonth : 0,
				GYMoth : 0,
				LSYear : 0,
				GYYear : 0,
				xAxisData : [],
				LSSeriesData : [],
				GYSeriesData : []
			},
			productionData : {},
			// totalTransport : 0,
			energyConsumptionToday  : 0
		}
	},
	mounted() {
		this.initData();
		this.handleScreenAuto();
		// 绑定自适应函数   ---防止浏览器栏变化后不再适配
		// window.onresize = () => debounce(this.handleScreenAuto());
		// 使用防抖函数包装 handleScreenAuto
		this.debouncedHandleResize = debounce(this.handleScreenAuto, 200);

		// 绑定自适应函数
		window.addEventListener("resize", this.debouncedHandleResize);

	},
	beforeDestroy() {
    // 页面离开时移除监听
    if (this.debouncedHandleResize) {
      window.removeEventListener('resize', this.debouncedHandleResize);
    }
  },
	methods: {
		toDetail(path){
			this.$router.push({path})
		},
		// 防抖函数
		debounce(func, wait = 100) {
			let timeout;
			return function (...args) {
				clearTimeout(timeout);
				timeout = setTimeout(() => {
					func.apply(this, args);
				}, wait);
			};
		},
		handleScreenAuto() {
			const designDraftWidth = 2280; //设计稿的宽度
			const designDraftHeight = 1080; //设计稿的高度
			// 根据屏幕的变化适配的比例
			const scale =
				document.documentElement.clientWidth /
				document.documentElement.clientHeight <
				designDraftWidth / designDraftHeight
					? document.documentElement.clientWidth / designDraftWidth
					: document.documentElement.clientHeight / designDraftHeight;
			// 缩放比例
			document.querySelector('#screen').style.transform = `scale(${scale}) translate(-50%,-50%)`;
		},
		dateFormatter(times){
			let newTime = new Date()
			let time = new Date(newTime.getTime()-times)
			let month = time.getMonth()+1 > 10 ? (time.getMonth()+1):'0' + (time.getMonth()+1)
			let day = time.getDate() > 10 ? time.getDate().toString():'0' + time.getDate()
			return month + '-' + day
		},
		initData() {
			this.loading12 = true;
			this.loading4 = true;
			// getWmsKanban().then((res) => {
				this.loading12 = false;
				this.loading4 = false;
			// 	if (res.code === 200) {
			// 		for (let key in this.wmsData) {
			// 			if (res.data[key] && key !== "finishedProductNum") {
			// 				res.data[key] = Number(res.data[key]).toFixed(2);
			// 			}
			// 			this.wmsData[key] = res.data[key];
			// 		}
			let wmsIngredientsLog12Vos = [{"month":"1","totalInboundWeight":473947.5,"totalInboundNum":491,"totalOutboundWeight":780917,"totalOutboundNum":491},{"month":"2","totalInboundWeight":1122875,"totalInboundNum":747,"totalOutboundWeight":1073647,"totalOutboundNum":747},{"month":"3","totalInboundWeight":1534750,"totalInboundNum":1154,"totalOutboundWeight":1445396.5,"totalOutboundNum":1154},{"month":"4","totalInboundWeight":61595.5,"totalInboundNum":38,"totalOutboundWeight":65764.5,"totalOutboundNum":38}]
					wmsIngredientsLog12Vos?.forEach((item) => {
						this.rightTopData.monthData.push(item.month + "月");
						this.rightTopData.outData.push(item.totalOutboundWeight);
						this.rightTopData.inData.push(item.totalInboundWeight);
					});
					let length = wmsIngredientsLog12Vos?.length || 0;
					if (length < 12) {
						for (let index = length; index < 12; index++) {
							this.rightTopData.outData.push(0);
							this.rightTopData.inData.push(0);
						}
					}
			// 	}
			// });
			// this.loading3 = true;
			// this.loading1 = true;
			getQcKanbanPage().then((res) => {
				if (res.code === 200) {
					this.loading3 = false;
					this.loading1 = false;
					this.detectionDayNum = res.data.detectionDayNum || 35;
					const qcPageResults = res.data || [];
					for (const item of qcPageResults) {
						this.leftBotData.seriesData.push(Number(item.qualifiedRate).toFixed(2));
					}
					let length = res.data?.length || 0;
					if (length < 12) {
						for (let index = length; index < 12; index++) {
							this.leftBotData.seriesData.push(0);
						}
					}
				}
			});


			this.loading6 = true;
			getEcmElectricityConsumption().then(res => {
				if (res.code === 200) {
					this.loading6 = false;
					this.energyData.LSMonth = Number(res.data.monthElectricQuantity[0].electricQuantity / 1000)?.toFixed(0);
					this.energyData.QBMoth = Number(res.data.monthElectricQuantity[1].electricQuantity / 1000)?.toFixed(0);
					this.energyData.LSYear = Number(res.data.yearElectricQuantity[0].electricQuantity / 1000)?.toFixed(0);
					this.energyData.QBYear = Number(res.data.yearElectricQuantity[1].electricQuantity / 1000)?.toFixed(0);


					let lsData = res.data.weeksElectricQuantity.filter(item => item.workshopType === '中拉车间');
					let qbData = res.data.weeksElectricQuantity.filter(item => item.workshopType === '小拉车间');
					let wlData = res.data.weeksElectricQuantity.filter(item => item.workshopType === '微拉车间');
					lsData.forEach(item => {
						this.energyData.xAxisData.push(item.recordDay);
						this.energyData.LSSeriesData.push(Number(item.totalUsage / 1000).toFixed(0))
					})
					qbData.forEach(item => {
						this.energyData.GYSeriesData.push(Number(item.totalUsage / 1000).toFixed(0))
					})
					if (lsData.length === 7 && qbData.length === 7 && wlData.length === 7) {
							this.energyConsumptionToday = (lsData[6].totalUsage / 1000 + qbData[6].totalUsage / 1000 + wlData[6].totalUsage / 1000).toFixed(0)
					}
				}
			})
			this.loading2 = true;
			getMesProductWorkorder().then(res => {
				if (res.code === 200) {
					this.loading2 = false;
					this.productionData = {...res.data};
					this.productionData.wiredrawingNumWeek = [{
						"date":this.dateFormatter(24*60*60*1000*6),
						"week":"周一",
						"schedulingNum":null,
						"completedNum":null,
						"putNum":83,
						"outNum":227,
						"carryNum":96},{
							"date":this.dateFormatter(24*60*60*1000*5),
							"week":"周二",
							"schedulingNum":null,
							"completedNum":null,
							"putNum":127,
							"outNum":318,
							"carryNum":50},{
								"date":this.dateFormatter(24*60*60*1000*4),
								"week":"周三",
								"schedulingNum":null,
								"completedNum":null,
								"putNum":110,
								"outNum":338,
								"carryNum":142},{
									"date":this.dateFormatter(24*60*60*1000*3),
									"week":"周四",
									"schedulingNum":null,
									"completedNum":null,
									"putNum":116,
									"outNum":340,
									"carryNum":143},{
										"date":this.dateFormatter(24*60*60*1000*2),
										"week":"周五",
										"schedulingNum":null,
										"completedNum":null,
										"putNum":55,
										"outNum":204,
										"carryNum":121},{
											"date":this.dateFormatter(24*60*60*1000),
											"week":"周六",
											"schedulingNum":null,
											"completedNum":null,
											"putNum":127,
											"outNum":320,
											"carryNum":123},{
												"date": this.dateFormatter(0),
												"week":"周日",
												"schedulingNum":null,
												"completedNum":null,
												"putNum":60,
												"outNum":190,
												"carryNum":102}]
							// this.totalTransport = res.data.mesProductWorkorderWeek.reduce((sum, item) => sum + (Number(item.carryNum) || 0), 0);
				}
			})
		},
		endLoading(num){
			console.log(num)
			this[`loading${num}`] = false
		},

	}
});
</script>

<style>
.screen-wrapper{
	width: 100vw;
	height: 100vh;
}
.screen {
	width: 2280px;
	height: 1080px;
	transform-origin: 0 0;
	position: relative;
	background: url(../../../assets/img/pageBg.png) no-repeat;
	background-size: cover;
	left: 50%;
	top: 50%;
}


.title{
	text-align: center;
	letter-spacing: 16px;
	text-indent:30px;
	font-size: 54px;
	text-indent:30px;
	font-weight: bolder;
	margin-bottom: 12px;
	background-image: -webkit-linear-gradient(top, #FFFEFe,#C0EAFF,#7CD0FE,#219FF4);
	background-clip: text;
	-webkit-background-clip: text;
	-webkit-text-fill-color: transparent;
}

.left_area{
	margin-left: 40px;
	position: absolute;

}

.left_top_area{
	width: 500px;
	height: 230px;
	background: url(../../../assets/img/board/left_top_bg.png) no-repeat center center;
	background-size: cover;
	top: 40px;
}

.left_top_div{
	position: absolute;
	width: 100%;
}

.left_top_div .bot_cycle{
	position: absolute;
	top: 43px;
	left: 97px;
	width: 320px;
	height: 5px;
	background: url(../../../assets/img/board/produce_line.png) no-repeat center center;
}

.right_bot_area .bot_cycle{
	position: absolute;
	top: 26px;
	left: -65px;
	width: 350px;
	height: 5px;
	background: url(/src/assets/img/board/produce_line.png) no-repeat center center;
}

.right_select{
	display: flex;
	align-items: center;
	width: 130px;
	justify-content: space-around;
	position: absolute;
	top: 12px;
	right: 30px;

}

.right_select div{
	font-size: 14px;
	color:#91AFC8;
	cursor: pointer;
}

.right_select div.active{
	border: 1px solid #8ECDF2;
	border-radius: 2px;
	color: #8ECDF2;
	padding: 2px;
}

.left_mid_area{
	width: 450px;
	height: 285px;
	background: url(../../../assets/img/board/left_mid_bg.png) no-repeat center center;
	background-size: cover;
	top: 360px;
}
.alltitle {
	font-size: 18px;
	color: #fff;
	line-height: 20px;
	padding-left: 12px;
	position: relative;
	top: 15px;
	left: 16px;
}

.left_bot_area{
	width: 505px;
	height: 300px;
	top: 700px;
}

.left_bot_area .top_title,.right_top_area .top_title,.right_mid_bot_area .top_title, .right_bot_area .top_title{
	width: 100%;
	height: 45px;
	background: url(../../../assets/img/board/common_text_bg.png) no-repeat center center;
	background-size: cover;
}

.mid_top_area .top_title{
	width: 100%;
	height: 45px;
	background: url(../../../assets/img/board/mid_text_bg.png) no-repeat center center;
	background-size: cover;
}

.mid_top_area .top_title span{
	text-align: center;
	letter-spacing: 6px;
	text-indent:30px;
	line-height: 26px;
	font-size: 26px;
	font-weight: bolder;
	background-image: -webkit-linear-gradient(top, #FFFEFe,#C0EAFF,#7CD0FE,#219FF4);
	background-clip: text;
	-webkit-background-clip: text;
	-webkit-text-fill-color: transparent;
	font-family: Impact, Haettenschweiler, Arial Narrow Bold, sans-serif;
}

.left_bot_area .top_title span, .right_top_area .top_title span, .right_mid_bot_area .top_title span, .right_bot_area .top_title span{
	text-align: center;
	letter-spacing: 6px;
	text-indent:30px;
	line-height: 26px;
	font-size: 26px;
	font-weight: bolder;
	background-image: -webkit-linear-gradient(top, #FFFEFe,#C0EAFF,#7CD0FE,#219FF4);
	background-clip: text;
	-webkit-background-clip: text;
	-webkit-text-fill-color: transparent;
	font-family: Impact, Haettenschweiler, Arial Narrow Bold, sans-serif;
}

.right_area{
	position: absolute;
	margin-right: 40px;
	right: 0px;
}
.right_top_area{
	width: 525px;
	height: 260px;
	top: 80px;
}

.right_top_bar{
	width: 610px;
	height: calc(100% - 45px);
	position: absolute;
	right: 0px;
}
.right_mid_top_area{
	width: 560px;
	height: 230px;
	top: 365px;
}
.right_mid_top_area{
	display: flex; /* 使用 Flexbox 布局 */
	flex-wrap: wrap; /* 允许子元素换行 */
	height: 180px; /* 容器高度，根据需要调整 */
	justify-content: space-between; /* 水平方向平均分配 */
	align-content: space-between; /* 垂直方向平均分配 */
}
.right_mid_top_area .box{
	width: 50%; /* 小 div 宽度，占据父容器的一半 */
	height: 50%; /* 小 div 高度，占据父容器的一半 */
	display: flex;
	justify-content: center;
	align-items: center;
	font-size: 24px; /* 文字大小，可以根据需要调整 */
}

.inner-box {
	display: flex;
	justify-content: center;
	align-items: center;
	width: 100%;
}

.inner-box  .image {
	width: 66px;
	height: 66px;
	background-color: #ccc; /* 仅为了可视化，实际应使用图片 */
	display: flex;
	justify-content: center;
	align-items: center;
}

.inner-box  .image1 {
	background: url(../../../assets/img/board/cp.png) no-repeat center center;

}

.inner-box  .image2 {
	background: url(../../../assets/img/board/bcp.png) no-repeat center center;
}
.inner-box  .image3 {
	background: url(../../../assets/img/board/ycl.png) no-repeat center center;
}
.inner-box  .image4 {
	background: url(../../../assets/img/board/qtcl.png) no-repeat center center;
}

.text {
	display: flex;
	flex-direction: column;
	justify-content: space-around;
}
.text div{
	text-align: center;
	letter-spacing: 2px;

	font-weight: bolder;
	background-image: -webkit-linear-gradient(top, #FFFEFe,#C0EAFF,#7CD0FE,#219FF4);
	background-clip: text;
	-webkit-background-clip: text;
	-webkit-text-fill-color: transparent;
	font-family: Impact, Haettenschweiler, Arial Narrow Bold, sans-serif;
}

.text .weight{
	font-size: 30px;
	line-height: 30px;
}
.text .product{
	font-size: 18px;
	line-height: 18px;
}

.right_mid_bot_area{
	width: 500px;
	height: 230px;
	top: 540px;
}
.right_mid_bot_area .bar_area{
	width: 565px;
	height: calc(100% - 23px);
	position: absolute;
	right: 0px;
	top: 45px;
	background: url(../../../assets/img/board/equment_bg.png) no-repeat ;
	background-size: cover;
	display: flex;
	justify-content: space-between;
	align-items: center;
}

.mid_top_area .bar_area .yxz2{
	top: 0px;
	padding: 0px;
	position: relative;
}
.mid_top_area .bar_area .yxz2 .content{
	top: 0px;
	width: 230px;
	height: 210px;
}
.right_bot_area .bar_area{
	width: 600px;
	height: calc(100% - 12px);
	position: absolute;
	right: 0px;
	top: 35px;
	background: url(../../../assets/img/board/equment_bg.png) no-repeat ;
	display: flex;
	justify-content: space-between;
	align-items: center;
}

.bar_area .yxz, .bar_area .djz, .bar_area .gxz {
	flex: 1; /* 每个子元素占据相等的空间 */
	display: flex; /* 启用 Flexbox */
	flex-direction: column; /* 子元素垂直排列 */
	justify-content: space-between; /* 子元素垂直分布 */
	align-items: center; /* 水平居中 */
	height: 200px; /* 根据需要调整高度 */
	margin: 5px; /* 根据需要调整间距 */
	padding: 10px; /* 根据需要调整内边距 */
}
.right_mid_bot_area .bar_area .bar_title{
	height: 20px;
	width: 80px;
	font-size: 16px; /* 根据需要调整字体大小 */
	font-weight: bold; /* 标题加粗 */
	text-align: center;
	font-weight: bolder;
	margin-bottom: 12px;
	color:#fff;
	background: url(../../../assets/img/board/text_bg.png) no-repeat;
	background-size: cover;
}
/* 标题样式 *

/* 内容样式 */
.content {
	position: absolute;
	width: 190px;
	height: 190px;
	top: 39px;
}

.right_bot_area{
	width: 600px;
	height: 230px;
	top: 805px;
}

.mid_area{
	position: absolute;

}
.mid_bot_end_area{
	width: 932px;
	height: 160px;
	bottom: 32px;
	left: 615px;
	display: flex;
	justify-content: space-between;
}
.mid_bot_end_area .item{
	flex: 1; /* 每个div平均分配空间 */
	position: relative;
}



.mid_bot_end_area .item .top{
	background: url(../../../assets/img/board/bottom_count.png) no-repeat;
	height: 72px;
	position: absolute;
	top: 30px;
	left: 50px;
	width: 190px;
	text-align: center;
	padding-top: 10px;
}

.mid_bot_end_area .item .bottom{
	position: absolute;
	top: 90px;
	left: 65px;
	width: 160px;
	height: 25px;
	display: flex;
	justify-content: space-around;
}
.mid_bot_end_area .item .bottom .left{
	width: 15px;
	height: 25px;
	background: url(../../../assets/img/board/three_left.png) no-repeat;
}
.mid_bot_end_area .item .bottom .right{
	width: 15px;
	height: 25px;
	background: url(../../../assets/img/board/three_right.png) no-repeat;
}

.mid_bot_end_area .item .bottom .bot_text{
	width: 100px;
	height: 25px;
	text-align: center;
	color: #fff;
}
.mid_top_area{
	width: 870px;
	height: 300px;
	left: 657px;
	top: 140px;
}

.mid_top_area .bar_area{
	width: 870px;
	height: calc(100% - 85px);
	display: flex;
}
.mid_top_area .bar_area .left{
	width: 210px;
	height: 100%;
	margin-right: 20px;
}
.mid_top_area .bar_area .left  .bar_title{
	height: 25px;
	width: 125px;
	font-size: 16px;
	font-weight: bold;
	text-align: center;
	font-weight: bolder;
	margin-bottom: 12px;
	color: #fff;
	background: url(/src/assets/img/board/text_bg.png) no-repeat;
	background-size: cover;
}

.mid_top_area .bar_area .left  .content{
	background: url(/src/assets/img/board/process.png) no-repeat;
	top: 130px;
	left: 20px;
}

.mid_top_area .bar_area .left  .content .progress{
	color: #fff;
	font-size: 30px;
	font-weight: 900;
	margin-left: 60px;
	margin-top: -30px;
}

.mid_top_area .bar_area .mid{
	width: 400px;
	height: 100%;
	margin-right: 20px;
	padding: 0 10px;
	display: flex;
}

.mid_top_area .bar_area .mid div{
	width: 70px;
	height: 100%;
}

.mid_top_area .bar_area .right{
	width: 218px;
	height: 100%;
}

.mid_mid_area{
	width: 1030px;
	height: 300px;
	left: 580px;
	top: 355px;
}

.mid_mid_area .right_area{
	width: 250px;
	height: 100%;
}

.mid_mid_area .left_area{
	width: 620px;
	height: 220px;
	background: url(/src/assets/img/board/jiagong_bg.png) no-repeat center center;
	top: 45px;
	display: grid;
	grid-template-rows: 1fr 1fr 1fr;
}

.mid_mid_area .left_area .content_area{
	position: relative;
}

.mid_bot_area{
	width: 900px;
	height: 180px;
	left: 630px;
	top: 680px;
	background: url(../../../assets/img/board/left_mid_bg.png) no-repeat center center;
	background-size: cover;
}

.left_allow{
	position: absolute;
	width: 170px;
	height: 718px;
	left: 500px;
	top: 150px;
	background: url(../../../assets/img/board/mid-left.png) no-repeat center center;
	background-size: cover;
}
.right_allow{
	position: absolute;
	width: 170px;
	height: 718px;
	left: 1510px;
	top: 150px;
	background: url(../../../assets/img/board/mid-right.png) no-repeat center center;
	background-size: cover;
}

.ls_content{
	position: absolute;
	width: 200px;
	height: 65px;
	left: 42px;
	margin-top: 10px;
	background: url(../../../assets/img/board/ls_bg.png) no-repeat center center;
	display: flex; /* 启用 Flexbox */
	justify-content: center; /* 水平居中 */
	align-items: center; /* 垂直居中 */
}
.mid_mid_area .left_area  .ls_content, .mid_mid_area .left_area .content_area  .text-middle{
	background: none;
}

.mid_mid_area .left_area .content_area .left-side{
	background: url(../../../assets/img/board/lighting.png) no-repeat center center; /* 设置背景图片 */
	width: 30%; /* 占据父容器的一半宽度 */
	height: 100%; /* 与父容器等高 */
}

.left-side {
	background: url(../../../assets/img/board/ls_text_bg.png) no-repeat center center; /* 设置背景图片 */
	width: 30%; /* 占据父容器的一半宽度 */
	height: 100%; /* 与父容器等高 */
}

.mid_mid_area .left_area  .ls_content .right-side{
	width: 40%;
}

.mid_mid_area .left_area .content_area .bar_area{
	height: 100%;
	position: relative;
	left: 210px;
	width: 390px;
	top: -7px;
}

.right-side {
	width: 50%;
	position: relative;
	top: -5px;
	width: 90%;
}

.right-side  .text-middle{
	width: 100%;
	height: 3px;
	background: url(../../../assets/img/board/ls_line.png) no-repeat center center;
}

.upper-content, .lower-content {
color: #fff;
}


.alltitle:before {
	position: absolute;
	height: 12px;
	width: 4px;
	background: #49bcf7;
	border-radius: 5px;
	content: "";
	left: 0;
	top: 50%;
	margin-top: -6px;
}

.left_cycle{
	position: absolute;
	left: 135px;
}

.mid_bot_country_area{
	top: 810px;
	left: 650px;
	width: 870px;
	height: 85px;
	display: flex;
	justify-content: space-between;
}

.mid_bot_country_area .cunt_count{
	width: 45px;
	height: 85px;
	background: url(../../../assets/img/board/count_bg.png) no-repeat center center;
	display: grid;
	place-items: center;
	color: #fff;
	font-size: 30px;
	font-weight: bold;
}
.mid_bot_country_area .douhao{
	width: 15px;
	height: 135px;
	margin: 0 -20px;
	background: url(../../../assets/img/board/,.png) no-repeat center center;

}
.mid_bot_country_area .total_count{
	width: 95px;
	height: 85px;
	background: url(../../../assets/img/board/total_count.png) no-repeat center center;
}

.mid_bot_country_area .unit{
	width: 95px;
	height: 85px;
	background: url(../../../assets/img/board/unit_bg.png) no-repeat center center;
}

.clearfix:after, .clearfix:before {
	display: table;
	content: " ";
}


.row>li {
	float: left;
	height: 100%;
}

li {
	list-style-type: none;
}
.col-6 {
	width: 50%;
}

.sqzs {
	margin-right: .2rem;
}

.sqzs h1 span {
	font-size: 60px;
	font-family: Impact, Haettenschweiler, Arial Narrow Bold, sans-serif;
}


</style>
