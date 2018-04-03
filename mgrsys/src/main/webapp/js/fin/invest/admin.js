/**
 * 财务模块-管理员信息页
 */
var PDD = PDD || {};
PDD.Fin = PDD.Fin || {};

/**
 * 显示管理员信息页面
 */
PDD.Fin.showInvestAdmin = function() {
	var role = PDD.Utils.getCookieValue(PDD.Cons.COOKIE_ROLE);
	if (role != null && (role == 5 || role == 20)) {
		// nothing
	} else {
		alert("sorry……此功能仅“管理员”可用。");
		PDD.Utils.includePage("./page/fin/invest/remind.html", $(".ui-layout-center"), "PDD.Fin.showInvestRemind()");
		return;
	}
	
	// 初始化时间选择控件
	$("#finInvestAdminDiv input.duringDate").daterangepicker({
		format : 'YYYY-MM-DD',
		startDate : '2014-06-25'
	}, function(start, end, label) {
	}).css("width", "200px");
	// 初始化清理按钮
	$("#finInvestAdminDiv input.clear").click(function() {
		$("#finInvestAdminDiv input.duringDate").val("");
	});
	
	// 初始化管理员表格
	PDD.Var.finInvestAdminTable = $('#finInvestAdminTable').dataTable(PDD.Fin.finInvestAdminTable);

	//初始化查询按钮
	$("#finInvestAdminQueryBtn").click(function() {
		var dates = $("#finInvestAdminDiv input.duringDate").val().split(" - ");
		var param = {
				startDate: dates[0],	
				endDate: dates[1]
		};
		$.getJSON("./showFinInvestAdminUser.do", param, function(result) {
			if (result.code == 0) {
				//清理上次结果
				PDD.Var.finInvestAdminTable.fnClearTable();
				if (result.data && result.data.length > 0) {
					PDD.Var.finInvestAdminTable.fnAddData(result.data);
				}
			}
		});
	});
	
	// 初始化管理员导出按钮
	$("#finInvestAdminExportBtn").click(function() {
		var url = "./exportInvestAdmin.do?rand=" + Math.random();
		if ($("#finInvestAdminDiv input.duringDate").val().length > 0) {
			dates = $("#finInvestAdminDiv input.duringDate").val().split(" - ");
			url += "startDate=" + dates[0] + "&endDate=" + dates[1];
		}
		window.open(url);
	});
};

/**
 * 总览表格
 */
PDD.Fin.finInvestAdminTable = {
	"bInfo" : false, // 开关，是否显示表格的一些信息
	"bJQueryUI" : false,
	"bServerSide" : false, // 指定从服务器端获取数据
	"sAjaxSource" : "./showFinInvestAdminUser.do?rand=" + Math.random(), // mvc后台ajax调用接口
	'bPaginate' : true, // 是否分页
	'sPaginationType' : 'full_numbers', // 分页样式
	"bProcessing" : true, // 当datatable获取数据时候是否显示正在处理提示信息。
	'bFilter' : false, // 是否使用内置的过滤功能。
	'bLengthChange' : false, // 是否允许用户自定义每页显示条数。
	"bAutoWidth" : true, // 自适应宽度
	bSort : false,
//	order: [[ 1, "desc" ]],
	"aoColumns" : [ {
		"mData" : "adminUserName",
		"sTitle" : "管理员名称",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "adminRealName",
		"sTitle" : "真实姓名",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "isKf",
		"sTitle" : "是否客服",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			return (data == "1" ? "是" : "否");
		}
	}, {
		"mData" : "sumReward",
		"sTitle" : "客服奖金（单位：元）",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			return data.toFixed(2);
		}
	} ],
	"oLanguage" : PDD.Cons.DATATABLE_OLANGUAGE
};