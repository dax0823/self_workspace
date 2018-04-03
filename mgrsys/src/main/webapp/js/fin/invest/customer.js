/**
 * 财务模块-客户信息页
 */
var PDD = PDD || {};
PDD.Fin = PDD.Fin || {};

/**
 * 显示客户信息页面
 */
PDD.Fin.showInvestCustomer = function() {
	//隐藏客户详情 div
	$("#finInvestCustomerInfoDiv").hide();
	
	//初始化时间范围查询条件组件
	$("#finInvestCustomerDiv input.duringDate").daterangepicker({
			format : 'YYYY-MM-DD'
			, startDate : '2014-06-01'
		},
		function(start, end, label) {
		//to do something
	}).css("width", "200px");
	//初始化清理按钮
	$("#finInvestCustomerDiv input.clear").click(function(){
		$("#finInvestCustomerDiv input.duringDate").val("");
	});
	
	// 初始化总览表格
	PDD.Var.finInvestCustomerTable = $('#finInvestCustomerTable').dataTable(PDD.Fin.finInvestCustomerTable);
	
	//初始化查询按钮
	$("#finInvestCustomerQueryBtn").click(function() {
		var dates = $("#finInvestCustomerDiv input.duringDate").val().split(" - ");
		var userName = $("#finInvestCustomerDiv input.userName").val();
		var realName = $("#finInvestCustomerDiv input.realName").val();
		var recommendName = $("#finInvestCustomerDiv input.recommendName").val();
		
		//参数
		var param = {
				startDate: dates[0],	
				endDate: dates[1],
				userName: userName,
				realName: realName,
				recommendName: recommendName,
		};
		
		//请求
		$.getJSON("./showFinInvestCustomer.do", param, function(result) {
			if (result.code == 0) {
				//清理上次结果
				PDD.Var.finInvestCustomerTable.fnClearTable();
				if (result.data && result.data.length > 0) {	// 当查到数据时，才填充
					PDD.Var.finInvestCustomerTable.fnAddData(result.data);
					
					PDD.Var.finInvestCustomerTable.$("tr").click(function() {
						var userId = PDD.Var.finInvestCustomerTable.fnGetData(this).id;
						PDD.Fin.finInvestCustomerInfoDivShow(userId);
						//展开详情 div
						$("#finInvestCustomerInfoDiv").show();
					}).css("cursor", "pointer");
				}
			}
		});

		//每次查询完毕，都收齐下方的信息div
		$("#finInvestCustomerInfoDiv").hide();
	});

	// 饼状图查看按钮
	$("#finInvestCustomerInfoTable span.pieCharts").click(function() {
//		if (typeof Highcharts != "undefined") {	//已初始化
		//展开图标div
		$("#finInvestCustomerInfoTable tr.pieChartsTr").show();
		
		if (PDD.Var.finInvestCustomerInvestPieCharts != null) {
			//图形重绘
			PDD.Var.finInvestCustomerInvestPieCharts = $("#finInvestCustomerInfoTable div.pieChartsDiv").highcharts();
			if (PDD.Var.finInvestCustomerInvestPieCharts && PDD.Var.finInvestCustomerInvestPieCharts.series) {
				//清除以前的图形
				while (PDD.Var.finInvestCustomerInvestPieCharts.series.length > 0) {
					PDD.Var.finInvestCustomerInvestPieCharts.series[0].remove(false);
				}
				
				//将新数据存入
				PDD.Var.finInvestCustomerInvestPieCharts.addSeries({
	                data: PDD.Var.finInvestCustomerInvestPieChartsData
	            });
				
				//重绘
				PDD.Var.finInvestCustomerInvestPieCharts.redraw();
			}
		} else {	//未初始化
			//（异步）动态加载图标 js
//			$.getScript("./framework/highcharts/highcharts.js", function() {
//				$.getScript("./framework/highcharts/highcharts-3d.js", function() {
////			展开图标div
//			$("#finInvestCustomerInfoTable tr.pieChartsTr").show();
					//初始化收起按钮
					$("#finInvestCustomerInfoTable input[type=button][class=btn]").click(function() {
						$("#finInvestCustomerInfoTable tr.pieChartsTr").hide();
					});
					
					//当有实际数据时才绘图
					if (PDD.Var.finInvestCustomerInvestPieChartsData != null && PDD.Var.finInvestCustomerInvestPieChartsData.length > 0) {
						$("#finInvestCustomerInfoTable div.pieChartsDiv").highcharts( {
							chart : {
								type : 'pie',
								options3d: {
					                enabled: true,
					                alpha: 45,
					                beta: 0
					            },
	//							backgroundColor: '#FCFFC5',
	//							backgroundColor: {
	//				                linearGradient: [0, 0, 500, 500],
	//				                stops: [
	//				                    [0, 'rgb(255, 255, 255)'],
	//				                    [1, 'rgb(200, 200, 255)']
	//				                ]
	//				            },
//								renderTo: "finInvestCustomerInfoTable div.pieChartsDiv",
								plotBackgroundColor : null,
								plotBorderWidth : null,
								plotShadow : false
							},
							credits: {
								enabled: false	//去掉右下角商标
							},
							exporting: {
					            enabled: true	//右上角导出按钮
							},
							title : {
								text : '个人待收（月份）饼状图'
							},
							tooltip : {
								pointFormat : '{series.name}: <b>{point.percentage:.1f}%</b>'
							},
							plotOptions : {
								pie : {
									allowPointSelect : true,
									cursor : 'pointer',
									depth: 35,
									dataLabels : {
										enabled : true,
										color : '#000000',
										connectorColor : '#000000',
										format : '<b>{point.name}</b>: {point.percentage:.1f} %'
									}
								}
							},
							series : [ {
	//							type : 'pie',
								name : "占待收总额",
								data : PDD.Var.finInvestCustomerInvestPieChartsData
							} ]
							, colors:[
								// 'red',//第一个颜色，欢迎加入Highcharts学习交流群294191384
								// 'blue',//第二个颜色
								// 'yellow',//第三个颜色
								'#77a1e5',
								'#c42525',
								'#a6c96a',
								'#1aadce',
								'#492970',
								'#f28f43'
							]
						});
					}
//				});
//			});
		}
	});
};

/**
 * 当用户点击客户列表时，将客户详情 div 展开
 */
PDD.Fin.finInvestCustomerInfoDivShow = function(userId) {
	//查询客户详情
	var param = {
		userId: userId
	};
	$.getJSON("./showFinInvestCustomerInfo.do?rand=" + Math.random(), param, function(result) {
		if (result.code == 0) {
			if (result.obj) {
				$("#finInvestCustomerInfoTable span.tdUserName").text(result.obj.userName);
				$("#finInvestCustomerInfoTable span.tdRealName").text(result.obj.realName);
				$("#finInvestCustomerInfoTable span.tdAccountMoney").text(result.obj.accountMoney.toFixed(2));
				$("#finInvestCustomerInfoTable span.tdSumInvest").text(result.obj.sumInvest);
				$("#finInvestCustomerInfoTable span.tdIDCard").text(result.obj.idCard);
				$("#finInvestCustomerInfoTable span.tdBankNum").text(result.obj.bankNum);
				$("#finInvestCustomerInfoTable span.tdBankName").text(result.obj.bankName);
				$("#finInvestCustomerInfoTable span.tdBankAddr").text(result.obj.bankAddress);
				$("#finInvestCustomerInfoTable span.tdRecommendName").text(result.obj.recommendName);
				$("#finInvestCustomerInfoTable span.tdSumUncollected").text(result.obj.sumUncollected);
				$("#finInvestCustomerInfoTable span.tdInvitedNames").text(result.obj.invitedName.join(", "));
			}
		}
	});
	
	//查询客户待收详情
	if (PDD.Var.finInvestCustomerUncollectedDetailTable != null) {
		PDD.Var.finInvestCustomerUncollectedDetailTable.fnClearTable();
		PDD.Var.finInvestCustomerUncollectedDetailTable.fnDestroy();
	}
	PDD.Fin.finInvestCustomerUncollectedDetailTable.sAjaxSource = "./showFinInvestCustomerUncollectedDetail.do?rand=" + Math.random() + "&userId=" + userId;
	PDD.Var.finInvestCustomerUncollectedDetailTable = $('#finInvestCustomerUncollectedDetailTable').dataTable(PDD.Fin.finInvestCustomerUncollectedDetailTable);

	$("#finInvestCustomerInfoTable tr.pieChartsTr").hide();
	
	//查询客户充值记录
	if (PDD.Var.finInvestCustomerInpourTable != null) {
		PDD.Var.finInvestCustomerInpourTable.fnClearTable();
		PDD.Var.finInvestCustomerInpourTable.fnDestroy();
	}
	PDD.Fin.finInvestCustomerInpourTable.sAjaxSource = "./showFinInvestCustomerInpour.do?rand=" + Math.random() + "&userId=" + userId;
	PDD.Var.finInvestCustomerInpourTable = $('#finInvestCustomerInpourTable').dataTable(PDD.Fin.finInvestCustomerInpourTable);
	
	//查询客户投资记录
	if (PDD.Var.finInvestCustomerInvestTable != null) {
		PDD.Var.finInvestCustomerInvestTable.fnClearTable();
		PDD.Var.finInvestCustomerInvestTable.fnDestroy();
	}
	PDD.Fin.finInvestCustomerInvestTable.sAjaxSource = "./showFinInvestCustomerInvest.do?rand=" + Math.random() + "&userId=" + userId;
	PDD.Var.finInvestCustomerInvestTable = $('#finInvestCustomerInvestTable').dataTable(PDD.Fin.finInvestCustomerInvestTable);
	
	//查询客户利息返还记录
	if (PDD.Var.finInvestCustomerInterestTable != null) {
		PDD.Var.finInvestCustomerInterestTable.fnClearTable();
		PDD.Var.finInvestCustomerInterestTable.fnDestroy();
	}
	PDD.Fin.finInvestCustomerInterestTable.sAjaxSource = "./showFinInvestCustomerInterest.do?rand=" + Math.random() + "&userId=" + userId;
	PDD.Var.finInvestCustomerInterestTable = $('#finInvestCustomerInterestTable').dataTable(PDD.Fin.finInvestCustomerInterestTable);
	
	//查询客户回款记录
	if (PDD.Var.finInvestCustomerBackCapitalTable != null) {
		PDD.Var.finInvestCustomerBackCapitalTable.fnClearTable();
		PDD.Var.finInvestCustomerBackCapitalTable.fnDestroy();
	}
	PDD.Fin.finInvestCustomerBackCapitalTable.sAjaxSource = "./showFinInvestCustomerBackCapital.do?rand=" + Math.random() + "&userId=" + userId;
	PDD.Var.finInvestCustomerBackCapitalTable = $('#finInvestCustomerBackCapitalTable').dataTable(PDD.Fin.finInvestCustomerBackCapitalTable);
};

/**
 * 总览表格
 */
PDD.Fin.finInvestCustomerTable = {
	"bInfo" : false, // 开关，是否显示表格的一些信息
	"bJQueryUI" : false,
	"bServerSide" : false, // 指定从服务器端获取数据
	"sAjaxSource" : "./showFinInvestCustomer.do?rand=" + Math.random(), // mvc后台ajax调用接口
	'bPaginate' : true, // 是否分页
	'sPaginationType' : 'full_numbers', // 分页样式
	"bProcessing" : true, // 当datatable获取数据时候是否显示正在处理提示信息。
	'bFilter' : false, // 是否使用内置的过滤功能。
	'bLengthChange' : false, // 是否允许用户自定义每页显示条数。
	"bAutoWidth" : true, // 自适应宽度
	bSort : true,
//	order: [[ 1, "desc" ]],
	"aoColumns" : [ {
		"mData" : "userName",
		"sTitle" : "投资人",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "sumRecommendReward",
		"sTitle" : "推荐奖励（单位：元）",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			return data.toFixed(2);
		}
	}, {
		"mData" : "sumFee",
		"sTitle" : "利息服务费（单位：元）",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			return data.toFixed(2);
		}
	}, {
		"mData" : "sumInvest",
		"sTitle" : "投资金额（单位：元）",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			return data.toFixed(2);
		}
	}, {
		"mData" : "sumInpour",
		"sTitle" : "充值金额（单位：元）",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			return data.toFixed(2);
		}
	}, {
		"mData" : "accountMoney",
		"sTitle" : "可用金额（单位：元）",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			return data.toFixed(2);
		}
	}, {
		"mData" : "aUserName",
		"sTitle" : "客服",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "sumAuserReward",
		"sTitle" : "客服奖励（单位：元）",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			return data.toFixed(2);
		}
	} ],
	"oLanguage" : PDD.Cons.DATATABLE_OLANGUAGE,
	fnInitComplete : function(obj) { // 表格加载完成后执行
		PDD.Var.finInvestCustomerTable.$("tr").click(function() {
			var userId = PDD.Var.finInvestCustomerTable.fnGetData(this).id;
			PDD.Fin.finInvestCustomerInfoDivShow(userId);
			//展开详情 div
			$("#finInvestCustomerInfoDiv").show();
		}).css("cursor", "pointer");
	}
};

/**
 * 客户当前待收详情
 */
PDD.Fin.finInvestCustomerUncollectedDetailTable = {
	"bInfo" : false, // 开关，是否显示表格的一些信息
	"bJQueryUI" : false,
	"bServerSide" : false, // 指定从服务器端获取数据
//	"sAjaxSource" : "./showFinInvestCustomerInpour.do?rand=" + Math.random(), // mvc后台ajax调用接口
	'bPaginate' : true, // 是否分页
	'sPaginationType' : 'full_numbers', // 分页样式
	"bProcessing" : true, // 当datatable获取数据时候是否显示正在处理提示信息。
	'bFilter' : false, // 是否使用内置的过滤功能。
	'bLengthChange' : false, // 是否允许用户自定义每页显示条数。
	"bAutoWidth" : true, // 自适应宽度
	bSort : true,
	order: [[ 0, "desc" ]],
	"aoColumns" : [ {
		"mData" : "borrowId",
		"sTitle" : "投标id",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "borrowId",
		"sTitle" : "链接",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			return "<a href='"  + PDD.Cons.INVEST_REMIND_LINK_STR.replace("##NUM", data) + "' target='_blank'>打开</a>";
		}
	}, {
		"mData" : "borrowName",
		"sTitle" : "投标名称",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "borrowMoney",
		"sTitle" : "发标额度（单位：元）",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			return data.toFixed(2);
		}
	}, {
		"mData" : "duration",
		"sTitle" : "期限",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "capital",
		"sTitle" : "投资金额（单位：元）",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			return data.toFixed(2);
		}
	}, {
		"mData" : "investTime",
		"sTitle" : "投资时间",
		"sClass" : "center",
		"bSearchable" : false
	} ],
	"oLanguage" : PDD.Cons.DATATABLE_OLANGUAGE,
	fnInitComplete : function(obj) {
		//将该表格数据进行加工，在下方以饼状图形式显示出来
		var res = obj.aoData;
		PDD.Var.finInvestCustomerInvestPieChartsData = new Array();
		for (var i=0; i<res.length; i++) {
			var dur = res[i]._aData.duration;
			var cap= res[i]._aData.capital;
			//与已有的数组对比，若月份相同，则将其金额加入其中，否则新插入一个月份对象
			var flag = false;
			for (var j=0; j<PDD.Var.finInvestCustomerInvestPieChartsData.length; j++) {
				if ((dur + "月") == PDD.Var.finInvestCustomerInvestPieChartsData[j][0]) {
					PDD.Var.finInvestCustomerInvestPieChartsData[j][1] += cap;
					flag = true;
					break;
				}
			}
			if (!flag) {
				PDD.Var.finInvestCustomerInvestPieChartsData.push(new Array((dur + "月"), cap));
			}
		}
	}
};

/**
 * 客户充值记录
 */
PDD.Fin.finInvestCustomerInpourTable = {
	"bInfo" : false, // 开关，是否显示表格的一些信息
	"bJQueryUI" : false,
	"bServerSide" : false, // 指定从服务器端获取数据
//	"sAjaxSource" : "./showFinInvestCustomerInpour.do?rand=" + Math.random(), // mvc后台ajax调用接口
	'bPaginate' : true, // 是否分页
	'sPaginationType' : 'full_numbers', // 分页样式
	"bProcessing" : true, // 当datatable获取数据时候是否显示正在处理提示信息。
	'bFilter' : false, // 是否使用内置的过滤功能。
	'bLengthChange' : false, // 是否允许用户自定义每页显示条数。
	"bAutoWidth" : true, // 自适应宽度
	bSort : true,
	order: [[ 0, "desc" ]],
	"aoColumns" : [ {
		"mData" : "inpourTime",
		"sTitle" : "充值时间",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "money",
		"sTitle" : "金额（单位：元）",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			return data.toFixed(2);
		}
	}, {
		"mData" : "way",
		"sTitle" : "充值方式",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			var way = "线下";
			if (data == "baofoo") way = "宝付";
			if (data == "easypay") way = "易生支付";
			return way;
		}
	} ],
	"oLanguage" : PDD.Cons.DATATABLE_OLANGUAGE
};

/**
 * 客户投标记录
 */
PDD.Fin.finInvestCustomerInvestTable = {
	"bInfo" : false, // 开关，是否显示表格的一些信息
	"bJQueryUI" : false,
	"bServerSide" : false, // 指定从服务器端获取数据
	'bPaginate' : true, // 是否分页
	'sPaginationType' : 'full_numbers', // 分页样式
	"bProcessing" : true, // 当datatable获取数据时候是否显示正在处理提示信息。
	'bFilter' : false, // 是否使用内置的过滤功能。
	'bLengthChange' : false, // 是否允许用户自定义每页显示条数。
	"bAutoWidth" : true, // 自适应宽度
	bSort : true,
	order: [[ 4, "desc" ]],
	"aoColumns" : [ {
		"mData" : "borrowId",
		"sTitle" : "链接",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			return "<a href='"  + PDD.Cons.INVEST_REMIND_LINK_STR.replace("##NUM", data) + "' target='_blank'>打开</a>";
		}
	}, {
		"mData" : "borrowName",
		"sTitle" : "投标名称",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "duration",
		"sTitle" : "期限",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "investorCapital",
		"sTitle" : "金额（单位：元）",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			return data.toFixed(2);
		}
	}, {
		"mData" : "investTime",
		"sTitle" : "投标时间",
		"sClass" : "center",
		"bSearchable" : false
	} ],
	"oLanguage" : PDD.Cons.DATATABLE_OLANGUAGE
};

/**
 * 客户利息发放记录
 */
PDD.Fin.finInvestCustomerInterestTable = {
	"bInfo" : false, // 开关，是否显示表格的一些信息
	"bJQueryUI" : false,
	"bServerSide" : false, // 指定从服务器端获取数据
	'bPaginate' : true, // 是否分页
	'sPaginationType' : 'full_numbers', // 分页样式
	"bProcessing" : true, // 当datatable获取数据时候是否显示正在处理提示信息。
	'bFilter' : false, // 是否使用内置的过滤功能。
	'bLengthChange' : false, // 是否允许用户自定义每页显示条数。
	"bAutoWidth" : true, // 自适应宽度
	bSort : true,
	order: [[ 5, "desc" ]],
	"aoColumns" : [ {
		"mData" : "borrowId",
		"sTitle" : "链接",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			return "<a href='"  + PDD.Cons.INVEST_REMIND_LINK_STR.replace("##NUM", data) + "' target='_blank'>打开</a>";
		}
	}, {
		"mData" : "borrowName",
		"sTitle" : "投标名称",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "duration",
		"sTitle" : "期限",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "total",
		"sTitle" : "发放总次数",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "sortOrder",
		"sTitle" : "当前次数",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "interestTime",
		"sTitle" : "发放时间",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "interest",
		"sTitle" : "金额（单位：元）",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			return data.toFixed(2);
		}
	} ],
	"oLanguage" : PDD.Cons.DATATABLE_OLANGUAGE
};

/**
 * 客户回款记录
 */
PDD.Fin.finInvestCustomerBackCapitalTable = {
	"bInfo" : false, // 开关，是否显示表格的一些信息
	"bJQueryUI" : false,
	"bServerSide" : false, // 指定从服务器端获取数据
	'bPaginate' : true, // 是否分页
	'sPaginationType' : 'full_numbers', // 分页样式
	"bProcessing" : true, // 当datatable获取数据时候是否显示正在处理提示信息。
	'bFilter' : false, // 是否使用内置的过滤功能。
	'bLengthChange' : false, // 是否允许用户自定义每页显示条数。
	"bAutoWidth" : true, // 自适应宽度
	bSort : true,
	order: [[ 3, "desc" ]],
	"aoColumns" : [ {
		"mData" : "borrowId",
		"sTitle" : "链接",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			return "<a href='"  + PDD.Cons.INVEST_REMIND_LINK_STR.replace("##NUM", data) + "' target='_blank'>打开</a>";
		}
	},  {
		"mData" : "borrowName",
		"sTitle" : "投标名称",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "duration",
		"sTitle" : "期限",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "backTime",
		"sTitle" : "回款时间",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "capital",
		"sTitle" : "金额（单位：元）",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			return data.toFixed(2);
		}
	} ],
	"oLanguage" : PDD.Cons.DATATABLE_OLANGUAGE
};
