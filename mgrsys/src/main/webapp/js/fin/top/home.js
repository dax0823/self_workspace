/**
 * 财务模块-充值总览页
 */
var PDD = PDD || {};
PDD.Fin = PDD.Fin || {};

/**
 * 显示总览页面
 */
PDD.Fin.showTopHome = function() {

	// 初始化各排行榜
	PDD.Var.showTopHomeUncollectedTable = $('#finTopHomeUncollectedTable').dataTable(PDD.Fin.showTopHomeUncollectedTable);
	PDD.Var.showTopHomeInvestedTable = $('#finTopHomeInvestedTable').dataTable(PDD.Fin.showTopHomeInvestedTable);
	PDD.Var.showTopHomeInpourTable = $('#finTopHomeInpourTable').dataTable(PDD.Fin.showTopHomeInpourTable);
	PDD.Var.showTopHomeWithdrawTable = $('#finTopHomeWithdrawTable').dataTable(PDD.Fin.showTopHomeWithdrawTable);
	PDD.Var.showTopHomeBorrowTable = $('#finTopHomeBorrowTable').dataTable(PDD.Fin.showTopHomeBorrowTable);
	PDD.Var.showTopHomeIntegralTable = $('#finTopHomeIntegralTable').dataTable(PDD.Fin.showTopHomeIntegralTable);
};

/**
 * 用户总待收排行
 */
PDD.Fin.showTopHomeUncollectedTable = {
	"bInfo" : false, // 开关，是否显示表格的一些信息
	"bJQueryUI" : false,
	"bServerSide" : false, // 指定从服务器端获取数据
	"sAjaxSource" : "./showTopHomeUncollected.do?rankNum=10&rand=" + Math.random(), // mvc后台ajax调用接口
	'bPaginate' : false, // 是否分页
//	'sPaginationType' : 'full_numbers', // 分页样式
	"bProcessing" : true, // 当datatable获取数据时候是否显示正在处理提示信息。
	'bFilter' : false, // 是否使用内置的过滤功能。
	'bLengthChange' : false, // 是否允许用户自定义每页显示条数。
	"bAutoWidth" : true, // 自适应宽度
	bSort : true,
	order: [[ 0, "asc" ]],
	"aoColumns" : [ {
		"mData" : "rank",
		"sTitle" : "排名",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "userName",
		"sTitle" : "用户名",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "realName",
		"sTitle" : "真实姓名",
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
	}],
	"oLanguage" : PDD.Cons.DATATABLE_OLANGUAGE
};

/**
 * 用户累积投资排行
 */
PDD.Fin.showTopHomeInvestedTable = {
	"bInfo" : false, // 开关，是否显示表格的一些信息
	"bJQueryUI" : false,
	"bServerSide" : false, // 指定从服务器端获取数据
	"sAjaxSource" : "./showTopHomeInvested.do?rankNum=10&rand=" + Math.random(), // mvc后台ajax调用接口
	'bPaginate' : false, // 是否分页
//	'sPaginationType' : 'full_numbers', // 分页样式
	"bProcessing" : true, // 当datatable获取数据时候是否显示正在处理提示信息。
	'bFilter' : false, // 是否使用内置的过滤功能。
	'bLengthChange' : false, // 是否允许用户自定义每页显示条数。
	"bAutoWidth" : true, // 自适应宽度
	bSort : true,
	order: [[ 0, "asc" ]],
	"aoColumns" : [ {
		"mData" : "rank",
		"sTitle" : "排名",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "userName",
		"sTitle" : "用户名",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "realName",
		"sTitle" : "真实姓名",
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
	}],
	"oLanguage" : PDD.Cons.DATATABLE_OLANGUAGE
};

/**
 * 用户累积充值排行
 */
PDD.Fin.showTopHomeInpourTable = {
	"bInfo" : false, // 开关，是否显示表格的一些信息
	"bJQueryUI" : false,
	"bServerSide" : false, // 指定从服务器端获取数据
	"sAjaxSource" : "./showTopHomeInpour.do?rankNum=10&rand=" + Math.random(), // mvc后台ajax调用接口
	'bPaginate' : false, // 是否分页
//	'sPaginationType' : 'full_numbers', // 分页样式
	"bProcessing" : true, // 当datatable获取数据时候是否显示正在处理提示信息。
	'bFilter' : false, // 是否使用内置的过滤功能。
	'bLengthChange' : false, // 是否允许用户自定义每页显示条数。
	"bAutoWidth" : true, // 自适应宽度
	bSort : true,
	order: [[ 0, "asc" ]],
	"aoColumns" : [ {
		"mData" : "rank",
		"sTitle" : "排名",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "userName",
		"sTitle" : "用户名",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "realName",
		"sTitle" : "真实姓名",
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
	}],
	"oLanguage" : PDD.Cons.DATATABLE_OLANGUAGE
};

/**
 * 用户累积提现排行
 */
PDD.Fin.showTopHomeWithdrawTable = {
	"bInfo" : false, // 开关，是否显示表格的一些信息
	"bJQueryUI" : false,
	"bServerSide" : false, // 指定从服务器端获取数据
	"sAjaxSource" : "./showTopHomeWithdraw.do?rankNum=10&rand=" + Math.random(), // mvc后台ajax调用接口
	'bPaginate' : false, // 是否分页
//	'sPaginationType' : 'full_numbers', // 分页样式
	"bProcessing" : true, // 当datatable获取数据时候是否显示正在处理提示信息。
	'bFilter' : false, // 是否使用内置的过滤功能。
	'bLengthChange' : false, // 是否允许用户自定义每页显示条数。
	"bAutoWidth" : true, // 自适应宽度
	bSort : true,
	order: [[ 0, "asc" ]],
	"aoColumns" : [ {
		"mData" : "rank",
		"sTitle" : "排名",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "userName",
		"sTitle" : "用户名",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "realName",
		"sTitle" : "真实姓名",
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
	}],
	"oLanguage" : PDD.Cons.DATATABLE_OLANGUAGE
};

/**
 * 用户累积借款排行
 */
PDD.Fin.showTopHomeBorrowTable = {
	"bInfo" : false, // 开关，是否显示表格的一些信息
	"bJQueryUI" : false,
	"bServerSide" : false, // 指定从服务器端获取数据
	"sAjaxSource" : "./showTopHomeBorrow.do?rankNum=10&rand=" + Math.random(), // mvc后台ajax调用接口
	'bPaginate' : false, // 是否分页
//	'sPaginationType' : 'full_numbers', // 分页样式
	"bProcessing" : true, // 当datatable获取数据时候是否显示正在处理提示信息。
	'bFilter' : false, // 是否使用内置的过滤功能。
	'bLengthChange' : false, // 是否允许用户自定义每页显示条数。
	"bAutoWidth" : true, // 自适应宽度
	bSort : true,
	order: [[ 0, "asc" ]],
	"aoColumns" : [ {
		"mData" : "rank",
		"sTitle" : "排名",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "userName",
		"sTitle" : "用户名",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "realName",
		"sTitle" : "真实姓名",
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
	}],
	"oLanguage" : PDD.Cons.DATATABLE_OLANGUAGE
};

/**
 * 用户可用积分排行
 */
PDD.Fin.showTopHomeIntegralTable = {
	"bInfo" : false, // 开关，是否显示表格的一些信息
	"bJQueryUI" : false,
	"bServerSide" : false, // 指定从服务器端获取数据
	"sAjaxSource" : "./showTopHomeIntegral.do?rankNum=10&rand=" + Math.random(), // mvc后台ajax调用接口
	'bPaginate' : false, // 是否分页
//	'sPaginationType' : 'full_numbers', // 分页样式
	"bProcessing" : true, // 当datatable获取数据时候是否显示正在处理提示信息。
	'bFilter' : false, // 是否使用内置的过滤功能。
	'bLengthChange' : false, // 是否允许用户自定义每页显示条数。
	"bAutoWidth" : true, // 自适应宽度
	bSort : true,
	order: [[ 0, "asc" ]],
	"aoColumns" : [ {
		"mData" : "rank",
		"sTitle" : "排名",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "userName",
		"sTitle" : "用户名",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "realName",
		"sTitle" : "真实姓名",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "num",
		"sTitle" : "可用积分",
		"sClass" : "center",
		"bSearchable" : false
	}],
	"oLanguage" : PDD.Cons.DATATABLE_OLANGUAGE
};