/**
 * 财务模块-提现记录页
 */
var PDD = PDD || {};
PDD.Fin = PDD.Fin || {};

/**
 * 显示已提现记录内容
 */
PDD.Fin.showInpourWithdraw = function() {
	//初始化时间范围查询条件组件
	$("#finInpourWithdrawDiv input.duringDate").daterangepicker({
			format : 'YYYY-MM-DD'
			, startDate : '2014-06-25'
		},
		function(start, end, label) {
		//to do something
	}).css("width", "200px");
	//初始化清理按钮
	$("#finInpourWithdrawDiv input.clear").click(function(){
		$("#finInpourWithdrawDiv input.duringDate").val("");
	});

	//初始化奖励列表
	PDD.Var.finInpourWithdrawTable = $('#finInpourWithdrawTable').dataTable(PDD.Fin.finInpourWithdrawTable);
	
	//初始化查询按钮
	$("#finInpourWithdrawQueryBtn").click(function() {
//		"2014-06-25 - 2014-08-01"
		var type = $("#finInpourWithdrawDiv input[type=radio]:checked").val();
		var dates = $("#finInpourWithdrawDiv input.duringDate").val().split(" - ");
		var param = {
				startDate: dates[0],	
				endDate: dates[1],
				type: type
		};
		
		$.getJSON("./showFinInpourWithdraw.do", param, function(result) {
			if (result.code == 0) {
				//清理上次结果
				PDD.Var.finInpourWithdrawTable.fnClearTable();
				if (result.data && result.data.length > 0) {	// 当查到数据时，才填充
					PDD.Var.finInpourWithdrawTable.fnAddData(result.data);
//					//当数据重新生成时需要命名触发事件
//					PDD.Var.finInpourWithdrawTable.$("tr").click(function() {
//						PDD.Var.finInpourWithdrawTrClick(PDD.Var.finInpourWithdrawTable, this, null, PDD.Var.finInpourWithdrawCusTable);
//					}).css("cursor", "pointer");
				}
			}
		});
	});
	
	// 初始化明细导出按钮
	$("#finInpourWithdrawExportBtn").click(function() {
		var url = "./exportFinInpourWithdraw.do?rand=" + Math.random();
		if ($("#finInpourWithdrawDiv input.duringDate").val().length > 0) {
			var dates = $("#finInpourWithdrawDiv input.duringDate").val().split(" - ");
			url += "&startDate=" + dates[0] + "&endDate=" + dates[1];
		}
		var type = $("#finInpourWithdrawDiv input[type=radio]:checked").val();
		url += "&type=" + type;
		
		window.open(url);
	});
};

/**
 * 线下充值奖励，月统计表
 */
PDD.Fin.finInpourWithdrawTable = {
	"bInfo" : false, // 开关，是否显示表格的一些信息
	"bJQueryUI" : false,
	"bServerSide" : false, // 指定从服务器端获取数据
	"sAjaxSource" : "./showFinInpourWithdraw.do?rand=" + Math.random(), // mvc后台ajax调用接口
	'bPaginate' : true, // 是否分页
	'sPaginationType' : 'full_numbers', // 分页样式
	"bProcessing" : true, // 当datatable获取数据时候是否显示正在处理提示信息。
	'bFilter' : false, // 是否使用内置的过滤功能。
	'bLengthChange' : false, // 是否允许用户自定义每页显示条数。
	"bAutoWidth" : true, // 自适应宽度
	bSort : false,
//	order: [[ 0, "desc" ]],
	"aoColumns" : [ {
		"mData" : "withdrawTime",
		"sTitle" : "申请时间",
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
		"mData" : "withdrawStatus",
		"sTitle" : "处理状态",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "bankNum",
		"sTitle" : "银行账号",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "bankProv",
		"sTitle" : "开户行省",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "bankCity",
		"sTitle" : "开户行市",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "bankAddr",
		"sTitle" : "开户行",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "bankName",
		"sTitle" : "银行名称",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "withdrawMoney",
		"sTitle" : "提现金额（单位：元）",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			return data.toFixed(2);
		}
	}, {
		"mData" : "successMoney",
		"sTitle" : "到账（单位：元）",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			return data.toFixed(2);
		}
	} ],
	"oLanguage" : PDD.Cons.DATATABLE_OLANGUAGE
};

//*********************************************************************

/**
 * 显示已提现记录内容
 */
PDD.Fin.showInpourPendingWithdraw = function() {
	//初始化列表
	PDD.Var.finInpourPendingWithdrawTable = $('#finInpourPendingWithdrawTable').dataTable(PDD.Fin.finInpourPendingWithdrawTable);
	
	//初始化查询按钮
	$("#finInpourPendingWithdrawRefrashBtn").click(function() {
		var param = {
		};
		$.getJSON("./showFinInpourPendingWithdraw.do", param, function(result) {
			if (result.code == 0) {
				//清理上次结果
				PDD.Var.finInpourPendingWithdrawTable.fnClearTable();
				if (result.data && result.data.length > 0) {	// 当查到数据时，才填充
					PDD.Var.finInpourPendingWithdrawTable.fnAddData(result.data);
				}
			}
		});
		
		// 变更刷新按钮右侧的时间，以体现刷新效果
		$("#finInpourPendingWithdrawDiv span.showTime").text(PDD.Utils.getNowDate(2));
	});
};

/**
 * 线下充值奖励，月统计表
 */
PDD.Fin.finInpourPendingWithdrawTable = {
	"bInfo" : false, // 开关，是否显示表格的一些信息
	"bJQueryUI" : false,
	"bServerSide" : false, // 指定从服务器端获取数据
	"sAjaxSource" : "./showFinInpourPendingWithdraw.do?rand=" + Math.random(), // mvc后台ajax调用接口
	'bPaginate' : true, // 是否分页
	'sPaginationType' : 'full_numbers', // 分页样式
	"bProcessing" : true, // 当datatable获取数据时候是否显示正在处理提示信息。
	'bFilter' : false, // 是否使用内置的过滤功能。
	'bLengthChange' : false, // 是否允许用户自定义每页显示条数。
	"bAutoWidth" : true, // 自适应宽度
	bSort : false,
	order: [[ 0, "desc" ]],
	"aoColumns" : [ {
		"mData" : "withdrawTime",
		"sTitle" : "申请时间",
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
		"mData" : "withdrawMoney",
		"sTitle" : "申请金额（单位：元）",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			return data.toFixed(2);
		}
	}, {
		"mData" : "accountMoney",
		"sTitle" : "账户余额（含冻结，单位：元）",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			return data.toFixed(2);
		}
	}, {
		"mData" : "inpourMoney",
		"sTitle" : "15天内充值金额（单位：元）",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			return data.toFixed(2);
		}
	}, {
		"mData" : "freeMoney",
		"sTitle" : "免手续费金额（单位：元）",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			return data.toFixed(2);
		}
	}, {
		"mData" : "feeMoney",
		"sTitle" : "手续费（单位：元）",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			return data.toFixed(2);
		}
	}, {
		"mData" : "finalMoney",
		"sTitle" : "实际提现（单位：元）",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			return data.toFixed(2);
		}
	} ],
	"oLanguage" : PDD.Cons.DATATABLE_OLANGUAGE,
	fnInitComplete : function(obj) { // 表格加载完成后执行
		$("#finInpourPendingWithdrawDiv span.showTime").text(PDD.Utils.getNowDate(2));
	}
};
