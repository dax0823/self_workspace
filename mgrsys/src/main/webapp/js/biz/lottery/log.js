/**
 * 财务模块-充值总览页
 */
var PDD = PDD || {};
PDD.Biz = PDD.Biz || {};

/**
 * 显示总览页面
 */
PDD.Biz.showLotteryLog = function() {
	//初始化时间范围查询条件组件
	$("#bizLotteryLogDiv input.duringDate").daterangepicker({
			format : 'YYYY-MM-DD'
			, startDate : '2014-06-25'
		},
		function(start, end, label) {
		//to do something
	}).css("width", "200px");
	//初始化清理按钮
	$("#bizLotteryLogDiv input.clear").click(function(){
		$("#bizLotteryLogDiv input.duringDate").val("");
	});

	//初始化奖励列表
	PDD.Var.bizLotteryLogTable = $('#bizLotteryLogTable').dataTable(PDD.Fin.bizLotteryLogTable);
	
	//初始化查询按钮
	$("#bizLotteryLogQueryBtn").click(function() {
//		"2014-06-25 - 2014-08-01"
		var dates = $("#bizLotteryLogDiv input.duringDate").val().split(" - ");
		var userName = $("#bizLotteryLogDiv input.userName").val();
		var realName = $("#bizLotteryLogDiv input.realName").val();
		var productName = $("#bizLotteryLogDiv input.productName").val();
		
		//参数
		var param = {
				startDate: dates[0],	
				endDate: dates[1],
				userName: userName,
				realName: realName,
				productName: productName,
		};
		
		//请求
		$.getJSON("./showBizLotteryLog.do", param, function(result) {
			if (result.code == 0) {
				//清理上次结果
				PDD.Var.bizLotteryLogTable.fnClearTable();
				if (result.data && result.data.length > 0) {	// 当查到数据时，才填充
					PDD.Var.bizLotteryLogTable.fnAddData(result.data);
				}
			}
		});
	});
	
	// 初始化明细导出按钮
	$("#bizLotteryLogExportBtn").click(function() {
		var url = "./exportBizLotteryLog.do?rand=" + Math.random();
		if ($("#bizLotteryLogDiv input.duringDate").val().length > 0) {
			dates = $("#bizLotteryLogDiv input.duringDate").val().split(" - ");
			url += "&startDate=" + dates[0] + "&endDate=" + dates[1];
		}
		if ($("#bizLotteryLogDiv input.userName").val().length > 0) {
			url += "&userName=" + $("#bizLotteryLogDiv input.userName").val();
		}
		if ($("#bizLotteryLogDiv input.realName").val().length > 0) {
			url += "&realName=" + $("#bizLotteryLogDiv input.realName").val();
		}
		if ($("#bizLotteryLogDiv input.productName").val().length > 0) {
			url += "&productName=" + $("#bizLotteryLogDiv input.productName").val();
		}
		
		window.open(url);
	});
};

/**
 * 线下充值奖励，月统计表
 */
PDD.Fin.bizLotteryLogTable = {
	"bInfo" : false, // 开关，是否显示表格的一些信息
	"bJQueryUI" : false,
	"bServerSide" : false, // 指定从服务器端获取数据
	"sAjaxSource" : "./showBizLotteryLog.do?rand=" + Math.random(), // mvc后台ajax调用接口。
	'bPaginate' : true, // 是否分页
	'sPaginationType' : 'full_numbers', // 分页样式
	"bProcessing" : true, // 当datatable获取数据时候是否显示正在处理提示信息。
	'bFilter' : false, // 是否使用内置的过滤功能。
	'bLengthChange' : false, // 是否允许用户自定义每页显示条数。
	"bAutoWidth" : true, // 自适应宽度
	bSort : true,
	order: [[ 0, "desc" ]],
	"aoColumns" : [ {
		"mData" : "lotteryTime",
		"sTitle" : "抽奖时间",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "userName",
		"sTitle" : "客户名称",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "realName",
		"sTitle" : "真实姓名",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "mobile",
		"sTitle" : "手机号",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "name",
		"sTitle" : "奖品",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "info",
		"sTitle" : "相关信息",
		"sClass" : "center",
		"bSearchable" : false
	} ],
	"oLanguage" : PDD.Cons.DATATABLE_OLANGUAGE
};