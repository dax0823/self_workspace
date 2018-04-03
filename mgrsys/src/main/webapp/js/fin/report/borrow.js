/**
 * 财务模块-报表-发标列表
 */
var PDD = PDD || {};
PDD.Fin = PDD.Fin || {};

/**
 * 显示管理员信息页面
 */
PDD.Fin.showReportBorrow = function() {
	//初始化日期选择组件
	$('#finReportBorrowDiv input.month').Zebra_DatePicker({
		view: 'month',
		format: 'Y-m',
	    always_visible: $('#finReportBorrowDiv div.container')
	});
	
	// 初始化管理员表格
	PDD.Var.finReportBorrowTable = $('#finReportBorrowTable').dataTable(PDD.Fin.finReportBorrowTable);

	//初始化查询按钮
	$("#finReportBorrowBtn").click(function() {
		//查询客户待收详情
		if (PDD.Var.finReportBorrowTable != null) {
			PDD.Var.finReportBorrowTable.fnClearTable();
			PDD.Var.finReportBorrowTable.fnDestroy();
		}
		PDD.Fin.finReportBorrowTable.sAjaxSource = "./showReportBorrow.do?rand=" + Math.random() + "&month=" + $("#finReportBorrowDiv input.month").val();
		PDD.Var.finReportBorrowTable = $('#finReportBorrowTable').dataTable(PDD.Fin.finReportBorrowTable);
	});
	
	// 初始化明细导出按钮
	$("#finReportBorrowExportBtn").click(function() {
		var url = "./exportFinReportBorrow.do?rand=" + Math.random();
		if ($("#finReportBorrowDiv input.month").val().length > 0) {
			var month = $("#finReportBorrowDiv input.month").val();
			url += "&month=" + month;
		}
		
		window.open(url);
	});
};

PDD.Fin.finReportBorrowTable = {
		"bInfo" : false, // 开关，是否显示表格的一些信息
		"bJQueryUI" : false,
		"bServerSide" : false, // 指定从服务器端获取数据
		"sAjaxSource" : "./showReportBorrow.do?rand=" + Math.random(), // mvc后台ajax调用接口
		'bPaginate' : true, // 是否分页
		'sPaginationType' : 'full_numbers', // 分页样式
		"bProcessing" : true, // 当datatable获取数据时候是否显示正在处理提示信息。
		'bFilter' : false, // 是否使用内置的过滤功能。
		'bLengthChange' : false, // 是否允许用户自定义每页显示条数。
		"bAutoWidth" : true, // 自适应宽度
		bSort : true,
		order: [[ 1, "desc" ]],
		"aoColumns" : [ {
			"mData" : "name",
			"sTitle" : "标名",
			"sClass" : "center",
			"bSearchable" : false
		}, {
			"mData" : "time",
			"sTitle" : "发标时间",
			"sClass" : "center",
			"bSearchable" : false
		}, {
			"mData" : "money",
			"sTitle" : "借款额度（单位：元）",
			"sClass" : "center",
			"bSearchable" : false
		}, {
			"mData" : "duration",
			"sTitle" : "期限（月）",
			"sClass" : "center",
			"bSearchable" : false
		}, {
			"mData" : "interestRate",
			"sTitle" : "借款利率（%）",
			"sClass" : "center",
			"bSearchable" : false
		}, {
			"mData" : "rewardRate",
			"sTitle" : "奖励利率（%）",
			"sClass" : "center",
			"bSearchable" : false		
		}, {
			"mData" : "interest",
			"sTitle" : "利息（单位：元）",
			"sClass" : "center",
			"bSearchable" : false
		}, {
			"mData" : "borrowFee",
			"sTitle" : "管理费（单位：元）",
			"sClass" : "center",
			"bSearchable" : false
		}, {
			"mData" : "fee",
			"sTitle" : "投资手续费（单位：元）",
			"sClass" : "center",
			"bSearchable" : false
		}, {
			"mData" : "income",
			"sTitle" : "收入（单位：元）",
			"sClass" : "center",
			"bSearchable" : false
		}, {
			"mData" : "spreads",
			"sTitle" : "息差（单位：元）",
			"sClass" : "center",
			"bSearchable" : false
		} ],
		"oLanguage" : PDD.Cons.DATATABLE_OLANGUAGE
	};