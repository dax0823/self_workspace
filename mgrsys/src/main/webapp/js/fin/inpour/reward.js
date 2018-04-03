/**
 * 财务模块-充值奖励页
 */
var PDD = PDD || {};
PDD.Fin = PDD.Fin || {};

/**
 * 显示线下奖励内容
 */
PDD.Fin.showInpourReward = function() {
	//初始化时间范围查询条件组件
	$("#finInpourRewardDiv input.duringDate").daterangepicker({
			format : 'YYYY-MM-DD'
			, startDate : '2014-06-25'
		},
		function(start, end, label) {
		//to do something
	}).css("width", "200px");
	//初始化清理按钮
	$("#finInpourRewardDiv input.clear").click(function(){
		$("#finInpourRewardDiv input.duringDate").val("");
	});

	//初始化奖励列表
	PDD.Var.finInpourRewardTable = $('#finInpourRewardTable').dataTable(PDD.Fin.finInpourRewardTable);
	PDD.Var.finInpourRewardCusTable = $('#finInpourRewardCusTable').dataTable(PDD.Fin.finInpourRewardCusTable);
	PDD.Var.finInpourRewardCusTable.parent().hide();
	
	//初始化查询按钮
	$("#finInpourRewardQueryBtn").click(function() {
//		"2014-06-25 - 2014-08-01"
		var dates = $("#finInpourRewardDiv input.duringDate").val().split(" - ");
		var param = {
				startDate: dates[0],	
				endDate: dates[1]
		};
		
		$.getJSON("./showFinInpourReward.do", param, function(result) {
			if (result.code == 0) {
				//清理上次结果
				PDD.Var.finInpourRewardTable.fnClearTable();
				if (result.data && result.data.length > 0) {	// 当查到数据时，才填充
					PDD.Var.finInpourRewardTable.fnAddData(result.data);
					//当数据重新生成时需要命名触发事件
					PDD.Var.finInpourRewardTable.$("tr").click(function() {
						PDD.Var.finInpourRewardTrClick(PDD.Var.finInpourRewardTable, this, null, PDD.Var.finInpourRewardCusTable);
					}).css("cursor", "pointer");
				}
			}
		});
	});
	
	// 初始化明细导出按钮
	$("#finInpourRewardExportBtn").click(function() {
		var url = "./exportFinInpourReward.do?rand=" + Math.random();
		if ($("#finInpourRewardDiv input.duringDate").val().length > 0) {
			dates = $("#finInpourRewardDiv input.duringDate").val().split(" - ");
			url += "&startDate=" + dates[0] + "&endDate=" + dates[1];
		}
//		url += "&way=" + $("#finInpourHomeTab select").val();
		window.open(url);
	});
};

/**
 * 点击行数据触发事件
 */
PDD.Var.finInpourRewardTrClick = function(varTable, varTrData, way, varCusTable) {
	var sData = varTable.fnGetData(varTrData);
	var id = sData.userId;
	var param = {
			id: id,
	};
	
	$.getJSON("./showFinInpourRewardCus.do?rand=" + Math.random(), param, function(result) {
		if (result.code == 0) {
			if (varCusTable != null) {
				//清理上次结果
				varCusTable.fnClearTable();
				if (result.data && result.data.length > 0) {	// 当查到数据时，才填充
					varCusTable.fnAddData(result.data);
				}
				varCusTable.parent().show();
			} 
//			else {
////				varCusTable = $('#finInpourRewardCusTable').dataTable(PDD.Fin.finInpourRewardCusTable);
//				varCusTable.fnAddData(result.data);
//			}
		}
			
		
		//将滚动条自动移动到页面底部
		$('div.ui-layout-center').scrollTop( $('div.ui-layout-center')[0].scrollHeight, "slow");
	});
};

/**
 * 线下充值奖励，月统计表
 */
PDD.Fin.finInpourRewardTable = {
	"bInfo" : false, // 开关，是否显示表格的一些信息
	"bJQueryUI" : false,
	"bServerSide" : false, // 指定从服务器端获取数据
	"sAjaxSource" : "./showFinInpourReward.do?rand=" + Math.random(), // mvc后台ajax调用接口。
	'bPaginate' : true, // 是否分页
	'sPaginationType' : 'full_numbers', // 分页样式
	"bProcessing" : true, // 当datatable获取数据时候是否显示正在处理提示信息。
	'bFilter' : false, // 是否使用内置的过滤功能。
	'bLengthChange' : false, // 是否允许用户自定义每页显示条数。
	"bAutoWidth" : true, // 自适应宽度
	bSort : true,
	order: [[ 0, "desc" ]],
	"aoColumns" : [ {
		"mData" : "rewardMonth",
		"sTitle" : "月份",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "userName",
		"sTitle" : "客户名称",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "rewardSum",
		"sTitle" : "奖励金额（单位：元）",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			return data.toFixed(2);
		}
	}, {
		"mData" : "moneySum",
		"sTitle" : "充值金额（单位：元）",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			return data.toFixed(2);
		}
	} ],
	"oLanguage" : PDD.Cons.DATATABLE_OLANGUAGE,
	fnInitComplete : function(obj) { // 表格加载完成后执行
		PDD.Var.finInpourRewardTable.$("tr").click(function() {
			PDD.Var.finInpourRewardTrClick(PDD.Var.finInpourRewardTable, this, null, PDD.Var.finInpourRewardCusTable);
		}).css("cursor", "pointer");
	}
};

/**
 * 单个客户线下充值奖励明细
 */
PDD.Fin.finInpourRewardCusTable = {
	"bInfo" : false, // 开关，是否显示表格的一些信息
	"bJQueryUI" : false,
	"bServerSide" : false, // 指定从服务器端获取数据
//	"sAjaxSource" : "./showFinInpourRewardCus.do?rand=" + Math.random(), // mvc后台ajax调用接口。
	'bPaginate' : true, // 是否分页
	'sPaginationType' : 'full_numbers', // 分页样式
	"bProcessing" : true, // 当datatable获取数据时候是否显示正在处理提示信息。
	'bFilter' : false, // 是否使用内置的过滤功能。
	'bLengthChange' : false, // 是否允许用户自定义每页显示条数。
	"bAutoWidth" : true, // 自适应宽度
	bSort : true,
	order: [[ 0, "desc" ]],
	"aoColumns" : [ {
		"mData" : "rewardTime",
		"sTitle" : "奖励时间",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "userName",
		"sTitle" : "客户名称",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "rewardMoney",
		"sTitle" : "奖励金额（单位：元）",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			return data.toFixed(2);
		}
	}, {
		"mData" : "rate",
		"sTitle" : "奖励比率（%）",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "money",
		"sTitle" : "充值金额（单位：元）",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			return data.toFixed(2);
		}
	} ],
	"oLanguage" : PDD.Cons.DATATABLE_OLANGUAGE
};