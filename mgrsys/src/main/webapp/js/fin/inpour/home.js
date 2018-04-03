/**
 * 财务模块-充值总览页
 */
var PDD = PDD || {};
PDD.Fin = PDD.Fin || {};

/**
 * 显示总览页面
 */
PDD.Fin.showInpourHome = function() {
	// 页面加载时，先隐藏明细信息
	$("#finInpourHomeDetailDiv").hide();

	// 初始化总览表格
	PDD.Var.finInpourHomeTable = $('#finInpourHomeTable').dataTable(PDD.Fin.finInpourHomeTable);
//	 .$("tr:odd").css("backgroundColor", "#F5F5DC"); // 间隔行底色区分
//	 .finInpourd("tr.odd").css("backgroundColor", "red"); // 被插件自带的颜色顶掉了
	
	// 初始化充值 tab 按钮
	$("#finInpourHomeInpourTab span").click(function() {
		var type = $(this).attr("ref").replace("inpour", "");
		// 1. 将所有 div 的表格隐藏
		$("#finInpourHomeInpourDiv div.div").each(function(i) {
			$(this).removeClass("on");
		});
		// 2. 将选中的表格展开
		$("#finInpourHomeInpourDiv div[id*=" + type + "]").addClass("on");
		// 3. 修改 tab 页样式
		$("#finInpourHomeInpourTab span").each(function() {
			$(this).removeClass("on");
		});
		$(this).addClass("on");
	});
	//初始化充值详细内容
	PDD.Var.finInpourHomeInpourCurrDateTable = $('#finInpourHomeInpourCurrDateTable').dataTable(PDD.Fin.finInpourHomeInpourCurrDateTable);
	PDD.Var.finInpourHomeInpourLast7DayTable = $('#finInpourHomeInpourLast7DayTable').dataTable(PDD.Fin.finInpourHomeInpourLast7DayTable);
	PDD.Var.finInpourHomeInpourLastMonthTable = $('#finInpourHomeInpourLastMonthTable').dataTable(PDD.Fin.finInpourHomeInpourLastMonthTable);
	
	// 初始化“查看明细”按钮
	$("#showFinInpourHomeDetailBtn").click(function() {
		var status = $("#finInpourHomeDetailDiv").css("display");
		if (status == "none") {
			$("#finInpourHomeDetailDiv").show();
			$(this).attr("value", "收起导出明细");

			// 初始化明细信息-天统计
			PDD.Var.finInpourHomeEveryDayTable = $('#finInpourHomeEveryDayTable').dataTable(
						PDD.Fin.finInpourHomeEveryDayTable);
			// 初始化明细信息-月统计，默认不显示
			PDD.Var.finInpourHomeEveryMonthTable = $('#finInpourHomeEveryMonthTable').dataTable(
						PDD.Fin.finInpourHomeEveryMonthTable);
		} else {
			$("#finInpourHomeDetailDiv").hide();
			$(this).attr("value", "查看导出明细");
			//清理掉统计列表
			PDD.Var.finInpourHomeEveryDayTable.fnDestroy();
			PDD.Var.finInpourHomeEveryMonthTable.fnDestroy();
		}
	});

	// 初始化 tab 按钮
	$("#finInpourHomeTab span").click(function() {
		var type = $(this).attr("ref").replace("every", "");
		// 1. 将所有 div 的表格隐藏
		$("#finInpourHomeContentDiv div.div").each(function(i) {
//			$(this).hide();
			$(this).removeClass("on");
		});
		// 2. 将选中的表格展开
		$("#finInpourHomeContentDiv div[id*=Every" + type + "]").addClass("on");
		// 3. 修改 tab 页样式
		$("#finInpourHomeTab span").each(function() {
			$(this).removeClass("on");
		});
		$(this).addClass("on");
		
		//将滚动条自动移动到页面底部
		$('div.ui-layout-center').scrollTop( $('div.ui-layout-center')[0].scrollHeight, "slow");
	});

	//初始化时间选择控件
	$("#finInpourHomeTab input.duringDate").daterangepicker({
			format : 'YYYY-MM-DD'
			, startDate : '2014-06-25'
//			, endDate : '2013-12-31'
		},
		function(start, end, label) {
//			alert('A date range was chosen: ' + start.format('YYYY-MM-DD') + ' to ' + end.format('YYYY-MM-DD'));
	}).css("width", "200px");
	//初始化清理按钮
	$("#finInpourHomeTab input.clear").click(function(){
		$("#finInpourHomeTab input.duringDate").val("");
	});
	
	//初始化查询按钮
	$("#finInpourHomeDetailQueryBtn").click(function() {
//		"2014-06-25 - 2014-08-01"
		var dates = $("#finInpourHomeTab input.duringDate").val().split(" - ");
		var param = {
				startDate: dates[0],	
				endDate: dates[1],
				way: $("#finInpourHomeTab select").val()
		};
		
		//天统计
		$.getJSON("./showFinInpourHomeEveryDay.do", param, function(result) {
			if (result.code == 0) {
				//清理上次结果
				PDD.Var.finInpourHomeEveryDayTable.fnClearTable();
				if (result.data && result.data.length > 0) {	// 当查到数据时，才填充
					PDD.Var.finInpourHomeEveryDayTable.fnAddData(result.data);
				}
			} else {
			}

			//将滚动条自动移动到页面底部
			$('div.ui-layout-center').scrollTop( $('div.ui-layout-center')[0].scrollHeight, "slow");
		});
		
		//月统计
		$.getJSON("./showFinInpourHomeEveryMonth.do", param, function(result) {
			if (result.code == 0) {
				PDD.Var.finInpourHomeEveryMonthTable.fnClearTable();
				if (result.data && result.data.length > 0) {
					PDD.Var.finInpourHomeEveryMonthTable.fnAddData(result.data);
				}
			}

			//将滚动条自动移动到页面底部
			$('div.ui-layout-center').scrollTop( $('div.ui-layout-center')[0].scrollHeight, "slow");
		});
	});
	
	// 初始化明细导出按钮
	$("#finInpourHomeDetailExportBtn").click(function() {
		var url = "./";
		var ref = $("#finInpourHomeTab span.on").attr("ref");
		if (ref.indexOf("everyDay") > -1) {
			url += "exportFinInpourHomeDetailDays.do?";
		} else if (ref.indexOf("everyMonth") > -1) {
			url += "exportFinInpourHomeDetailMonths.do?";
		} else {
			//error
		}
		if ($("#finInpourHomeTab input.duringDate").val().length > 0) {
			dates = $("#finInpourHomeTab input.duringDate").val().split(" - ");
			url += "startDate=" + dates[0] + "&endDate=" + dates[1];
		}
		url += "&way=" + $("#finInpourHomeTab select").val();
		window.open(url);
	});
};

/**
 * 总览表格
 */
PDD.Fin.finInpourHomeTable = {
	"bInfo" : false, // 开关，是否显示表格的一些信息
	"bJQueryUI" : false,
	"bServerSide" : false, // 指定从服务器端获取数据
	"sAjaxSource" : "./showFinInpourHome.do?rand=" + Math.random(), // mvc后台ajax调用接口。
	// "aaData" : "./showFinInpourHome.do",
	'bPaginate' : false, // 是否分页。
	"bProcessing" : true, // 当datatable获取数据时候是否显示正在处理提示信息。
	'bFilter' : false, // 是否使用内置的过滤功能。
	'bLengthChange' : false, // 是否允许用户自定义每页显示条数。
	'sPaginationType' : 'full_numbers', // 分页样式
	"bAutoWidth" : true, // 自适应宽度
	bSort : false,
//	order: [[ 0, "desc" ]],
	"aoColumns" : [ {
		"mData" : "during",
		"sTitle" : "时间范围",
		"sClass" : "center",
		"bSearchable" : false
//		"bSortable" : true
	// "fnRender" : function(oObj) {
	// return '<a href=\"Details/' + oObj.aData[0] + '\">View</a>';
	// }
	}, {
		"mData" : "baofoo",
		"sTitle" : "宝付（单位：元）",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			// 修正 JSONObject 转对象时造成的小数点后位数过多问题
			return data.toFixed(2);
		}
	}, {
		"mData" : "easypay",
		"sTitle" : "易生支付（单位：元）",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			return data.toFixed(2);
		}
	}, {
		"mData" : "off",
		"sTitle" : "线下（单位：元）",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			return data.toFixed(2);
		}
	}, {
		"mData" : "total",
		"sTitle" : "充值总额（单位：元）",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			return data.toFixed(2);
		}
	} ],
	"oLanguage" : PDD.Cons.DATATABLE_OLANGUAGE,
	fnInitComplete : function(obj) { // 表格加载完成后执行
		//初始化当点击“总览列表”对应行时，将下面的充值详细列表切换动作
		PDD.Var.finInpourHomeTable.$("tr").each(function(i) {
			$(this).click(function() {
//				var sData = PDD.Var.finInpourHomeTable.fnGetData(this);
				if (i == 0) { // 当天
					$("#finInpourHomeInpourTab span[ref=inpourCurrDate]").click();
				} else if (i == 1) {	//最近 7 天
					$("#finInpourHomeInpourTab span[ref=inpourLast7Day]").click();
				} else if (i == 2) {	//最近一个月
					$("#finInpourHomeInpourTab span[ref=inpourLastMonth]").click();
				} else {
					//error
				}
			});
		}).css("cursor", "pointer");
	}
};

/**
 * 按天统计表格
 */
PDD.Fin.finInpourHomeEveryDayTable = {
	"bInfo" : false, // 开关，是否显示表格的一些信息
	"bJQueryUI" : false,
	"bServerSide" : false, // 指定从服务器端获取数据
	// "sAjaxSource" : "./showFinInpourHomeEveryDay.do", // mvc后台ajax调用接口。
	"ajax" : "./showFinInpourHomeEveryDay.do?rand=" + Math.random(),
	'bPaginate' : true, // 是否分页
	'sPaginationType' : 'full_numbers', // 分页样式
	"bProcessing" : true, // 当datatable获取数据时候是否显示正在处理提示信息。
	'bFilter' : false, // 是否使用内置的过滤功能。
	'bLengthChange' : false, // 是否允许用户自定义每页显示条数。
	"bAutoWidth" : true, // 自适应宽度
	bSort : true,
	order: [[ 0, "desc" ]],
	"aoColumns" : [ {
		"mData" : "during",
		"sTitle" : "日期",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "baofoo",
		"sTitle" : "宝付（单位：元）",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			// 修正 JSONObject 转对象时造成的小数点后位数过多问题
			return data.toFixed(2);
		}
	}, {
		"mData" : "easypay",
		"sTitle" : "易生支付（单位：元）",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			return data.toFixed(2);
		}
	}, {
		"mData" : "off",
		"sTitle" : "线下（单位：元）",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			return data.toFixed(2);
		}
	}, {
		"mData" : "total",
		"sTitle" : "总额（单位：元）",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			return data.toFixed(2);
		}
	} ],
	"oLanguage" : PDD.Cons.DATATABLE_OLANGUAGE,
	fnInitComplete : function(obj) { // 表格加载完成后执行
		//将滚动条自动移动到页面底部
		$('div.ui-layout-center').scrollTop( $('div.ui-layout-center')[0].scrollHeight, "slow");
	}
};

/**
 * 按月统计表格
 */
PDD.Fin.finInpourHomeEveryMonthTable = {
	"bInfo" : false, // 开关，是否显示表格的一些信息
	"bJQueryUI" : false,
	"bServerSide" : false, // 指定从服务器端获取数据
	"sAjaxSource" : "./showFinInpourHomeEveryMonth.do?rand=" + Math.random(), // mvc后台ajax调用接口。
	'bPaginate' : true, // 是否分页
	'sPaginationType' : 'full_numbers', // 分页样式
	"bProcessing" : true, // 当datatable获取数据时候是否显示正在处理提示信息。
	'bFilter' : false, // 是否使用内置的过滤功能。
	'bLengthChange' : false, // 是否允许用户自定义每页显示条数。
	"bAutoWidth" : true, // 自适应宽度
	bSort : true,
	order: [[ 0, "desc" ]],
	"aoColumns" : [ {
		"mData" : "during",
		"sTitle" : "月份",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "baofoo",
		"sTitle" : "宝付（单位：元）",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			// 修正 JSONObject 转对象时造成的小数点后位数过多问题
			return data.toFixed(2);
		}
	}, {
		"mData" : "easypay",
		"sTitle" : "易生支付（单位：元）",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			return data.toFixed(2);
		}
	}, {
		"mData" : "off",
		"sTitle" : "线下（单位：元）",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			return data.toFixed(2);
		}
	}, {
		"mData" : "total",
		"sTitle" : "总额（单位：元）",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			return data.toFixed(2);
		}
	} ],
	"oLanguage" : PDD.Cons.DATATABLE_OLANGUAGE
};

/**
 * 当天充值详细
 */
PDD.Fin.finInpourHomeInpourCurrDateTable = {
	"bInfo" : false, // 开关，是否显示表格的一些信息
	"bJQueryUI" : false,
	"bServerSide" : false, // 指定从服务器端获取数据
	"sAjaxSource" : "./showFinInpourCurrDate.do?rand=" + Math.random(), // mvc后台ajax调用接口。
	'bPaginate' : true, // 是否分页
	'sPaginationType' : 'full_numbers', // 分页样式
	"bProcessing" : true, // 当datatable获取数据时候是否显示正在处理提示信息。
	'bFilter' : false, // 是否使用内置的过滤功能。
	'bLengthChange' : false, // 是否允许用户自定义每页显示条数。
	"bAutoWidth" : true, // 自适应宽度
	bSort : true,
	order: [[ 2, "desc" ]],
	"aoColumns" : [ {
		"mData" : "userName",
		"sTitle" : "今天充值客户",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "money",
		"sTitle" : "金额（单位：元）",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			// 修正 JSONObject 转对象时造成的小数点后位数过多问题
			return data.toFixed(2);
		}
	}, {
		"mData" : "inpourTime",
		"sTitle" : "时间",
		"sClass" : "center",
		"bSearchable" : false
//		,
//		'mRender' : function(data, type, row) {
////				return data.toFixed(2);
//		}
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
 * 最近7 天充值详细
 */
PDD.Fin.finInpourHomeInpourLast7DayTable = {
	"bInfo" : false, // 开关，是否显示表格的一些信息
	"bJQueryUI" : false,
	"bServerSide" : false, // 指定从服务器端获取数据
	"sAjaxSource" : "./showFinInpourLast7Day.do?rand=" + Math.random(), // mvc后台ajax调用接口。
	'bPaginate' : true, // 是否分页
	'sPaginationType' : 'full_numbers', // 分页样式
	"bProcessing" : true, // 当datatable获取数据时候是否显示正在处理提示信息。
	'bFilter' : false, // 是否使用内置的过滤功能。
	'bLengthChange' : false, // 是否允许用户自定义每页显示条数。
	"bAutoWidth" : true, // 自适应宽度
	bSort : true,
	order: [[ 2, "desc" ]],
	"aoColumns" : [ {
		"mData" : "userName",
		"sTitle" : "近7天充值客户",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "money",
		"sTitle" : "金额（单位：元）",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			// 修正 JSONObject 转对象时造成的小数点后位数过多问题
			return data.toFixed(2);
		}
	}, {
		"mData" : "inpourTime",
		"sTitle" : "时间",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "way",
		"sTitle" : "充值方式",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			var way = "线下";
			if (data == "baofoo") way = "宝付";
			else if (data == "easypay") way = "易生支付";
			return way;
		}
	} ],
	"oLanguage" : PDD.Cons.DATATABLE_OLANGUAGE
//	, fnInitComplete : function(obj) {
//		// 将该表格数据进行加工，在下方以饼状图形式显示出来
//		var res = obj.aoData;
//		PDD.Var.finInpourHomeInpourLast7DayPieChartsData = new Array();
//		PDD.Var.finInpourHomeInpourLast7DayPieChartsData_baofoo = new Array();
//		PDD.Var.finInpourHomeInpourLast7DayPieChartsData_easypay = new Array();
//		PDD.Var.finInpourHomeInpourLast7DayPieChartsData_off = new Array();
//		PDD.Var.finInpourHomeInpourLast7DayPieChartsData_all = new Array();
//		for (var i=0; i<res.length; i++) {
//			var way = res[i]._aData.way;
//			var inpourTime= res[i]._aData.inpourTime;
//			var money= res[i]._aData.money;
//			//与已有的数组对比，若月份相同，则将其金额加入其中，否则新插入一个月份对象
//			var flag = false;
//			if (way == "off") {
//				
//			} else if (way == "baofoo") {
//				
//			} else if (way == "easypay") {
//				
//			}
//			
//			for (var j=0; j<PDD.Var.finInpourHomeInpourLast7DayPieChartsData.length; j++) {
//				if ((dur + "月") == PDD.Var.finInpourHomeInpourLast7DayPieChartsData[j][0]) {
//					PDD.Var.finInpourHomeInpourLast7DayPieChartsData[j][1] += cap;
//					flag = true;
//					break;
//				}
//			}
//			if (!flag) {
//				PDD.Var.finInpourHomeInpourLast7DayPieChartsData.push(new Array((dur + "月"), cap));
//			}
//		}
//	}
};

/**
 * 最近一个月充值详细
 */
PDD.Fin.finInpourHomeInpourLastMonthTable = {
	"bInfo" : false, // 开关，是否显示表格的一些信息
	"bJQueryUI" : false,
	"bServerSide" : false, // 指定从服务器端获取数据
	"sAjaxSource" : "./showFinInpourLastMonth.do?rand=" + Math.random(), // mvc后台ajax调用接口。
	'bPaginate' : true, // 是否分页
	'sPaginationType' : 'full_numbers', // 分页样式
	"bProcessing" : true, // 当datatable获取数据时候是否显示正在处理提示信息。
	'bFilter' : false, // 是否使用内置的过滤功能。
	'bLengthChange' : false, // 是否允许用户自定义每页显示条数。
	"bAutoWidth" : true, // 自适应宽度
	bSort : true,
	order: [[ 2, "desc" ]],
	"aoColumns" : [ {
		"mData" : "userName",
		"sTitle" : "月内充值客户",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "money",
		"sTitle" : "金额（单位：元）",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			// 修正 JSONObject 转对象时造成的小数点后位数过多问题
			return data.toFixed(2);
		}
	}, {
		"mData" : "inpourTime",
		"sTitle" : "时间",
		"sClass" : "center",
		"bSearchable" : false
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