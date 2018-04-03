/**
 * 财务模块-报表-发标列表
 */
var PDD = PDD || {};
PDD.Fin = PDD.Fin || {};

/**
 * 显示管理员信息页面
 */
PDD.Fin.showReportFiling = function() {
	$("#finReportBorrowInfoTable").hide();
	$("#finReportFilingTable").hide();
	
	//初始化时间范围查询条件组件
	$("#finReportFilingDiv input.duringDate").daterangepicker({
			format : 'YYYY-MM-DD'
			, startDate : '2014-06-01'
		},
		function(start, end, label) {
		//to do something
	}).css("width", "200px");
	//初始化清理按钮
	$("#finReportFilingDiv input.clear").click(function(){
		$("#finReportFilingDiv input.duringDate").val("");
	});
	
	//初始化发标情况列表
	PDD.Var.finReportBorrowListTable = $('#finReportBorrowListTable').dataTable(PDD.Fin.finReportBorrowListTable);

	//初始化查询按钮
	$("#finReportFilingQueryBtn").click(function() {
		var cls = $("#finReportFilingDiv input[name='bList']:checked").attr("class");
		//根据查询条件的不同，使用不同的请求 url
		var source = "";
		if (cls != null && cls == "bId") {
			var id = $("#finReportFilingDiv input.borrowId").val();
			if (id == null || $.trim(id).length <= 0) {
				alert("请输入发标id。");
				return;
			}
			source = "./showReportBorrowListById.do?rand=" + Math.random() + "&id=" + id;
		} else {
			var dates = $("#finReportFilingDiv input.duringDate").val().split(" - ");
			source = "./showReportBorrowListByCondition.do?rand=" + Math.random() + "&userName=" + $("#finReportFilingDiv input.userName").val()
				 + "&borrowName=" + $("#finReportFilingDiv input.borrowName").val()
				 + "&startDate=" + (dates[0] != null ? dates[0] : "")
				 + "&endDate=" + (dates[1] != null ? dates[1] : "");
		}
		
		//查询客户待收详情
		if (PDD.Var.finReportBorrowListTable != null) {
			PDD.Var.finReportBorrowListTable.fnClearTable();
			PDD.Var.finReportBorrowListTable.fnDestroy();
		}
		PDD.Fin.finReportBorrowListTable.sAjaxSource = source;
		PDD.Var.finReportBorrowListTable = $('#finReportBorrowListTable').dataTable(PDD.Fin.finReportBorrowListTable);
		
		//重新搜索时，将明细内容隐藏
		$("#finReportBorrowInfoTable").hide();
		$("#finReportFilingTable").hide();
	});
	
	// 初始化明细导出按钮
	$("#finReportFilingExportBtn").click(function() {
		if (PDD.Var.finReportFilingId == null || $.trim(PDD.Var.finReportFilingId).length <= 0) {
			alert("还未点击对应的投标。");
			return;
		}
		var url = "./exportFinReportFiling.do?rand=" + Math.random() + "&id=" + PDD.Var.finReportFilingId;
		window.open(url);
	});
};

/**
 * 查询发标信息列表
 */
PDD.Fin.finReportBorrowListTable = {
	"bInfo" : false, // 开关，是否显示表格的一些信息
	"bJQueryUI" : false,
	"bServerSide" : false, // 指定从服务器端获取数据
	"sAjaxSource" : "./showReportBorrowListByCondition.do?rand=" + Math.random(), // mvc后台ajax调用接口
	'bPaginate' : true, // 是否分页
	'sPaginationType' : 'full_numbers', // 分页样式
	"bProcessing" : true, // 当datatable获取数据时候是否显示正在处理提示信息。
	'bFilter' : false, // 是否使用内置的过滤功能。
	'bLengthChange' : false, // 是否允许用户自定义每页显示条数。
	"bAutoWidth" : true, // 自适应宽度
	bSort : true,
	order: [[ 0, "desc" ]],
	"aoColumns" : [ {
		"mData" : "id",
		"sTitle" : "发标id",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "userName",
		"sTitle" : "借款人用户名",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "borrowName",
		"sTitle" : "发标名称",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "time",
		"sTitle" : "发标时间",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "money",
		"sTitle" : "金额（单位：元）",
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
	} ],
	"oLanguage" : PDD.Cons.DATATABLE_OLANGUAGE
	, fnInitComplete : function(obj) { // 表格加载完成后执行
		//显示用户个人信息
		PDD.Var.finReportBorrowListTable.$("tr").click(function() {
			//当点击发标信息时，将该行数据填写到下方表单中
			$("#finReportBorrowInfoTable span.tdBorrowName").text(PDD.Var.finReportBorrowListTable.fnGetData(this).borrowName);
			$("#finReportBorrowInfoTable span.tdMoney").text(PDD.Var.finReportBorrowListTable.fnGetData(this).money);
			$("#finReportBorrowInfoTable span.tdInterestRate").text(PDD.Var.finReportBorrowListTable.fnGetData(this).interestRate);
			$("#finReportBorrowInfoTable span.tdRewardRate").text(PDD.Var.finReportBorrowListTable.fnGetData(this).rewardRate);
			$("#finReportBorrowInfoTable span.tdTime").text(PDD.Var.finReportBorrowListTable.fnGetData(this).time);
			
			//该投标中投资人相关明细
			if (PDD.Var.finReportFilingTable  != null) {
				PDD.Var.finReportFilingTable.fnClearTable();
				PDD.Var.finReportFilingTable.fnDestroy();
			}
			PDD.Fin.finReportFilingTable.sAjaxSource = "./showReportFiling.do?rand=" + Math.random() + "&id=" + PDD.Var.finReportBorrowListTable.fnGetData(this).id;
			PDD.Var.finReportFilingTable = $("#finReportFilingTable").dataTable(PDD.Fin.finReportFilingTable);
			
			//将单个发标明细隐藏的部分显示出来
			$("#finReportBorrowInfoTable").show();
			$("#finReportFilingTable").show();
			
			//记录当前点击的投标的id
			PDD.Var.finReportFilingId = PDD.Var.finReportBorrowListTable.fnGetData(this).id;
		}).css("cursor", "pointer");
	}
};

/**
 * 查询某个标中投资人相关信息
 */
PDD.Fin.finReportFilingTable = {
	"bInfo" : false, // 开关，是否显示表格的一些信息
	"bJQueryUI" : false,
	"bServerSide" : false, // 指定从服务器端获取数据
//	"sAjaxSource" : "./showReportFiling.do?rand=" + Math.random(), // mvc后台ajax调用接口
	'bPaginate' : true, // 是否分页
	'sPaginationType' : 'full_numbers', // 分页样式
	"bProcessing" : true, // 当datatable获取数据时候是否显示正在处理提示信息。
	'bFilter' : false, // 是否使用内置的过滤功能。
	'bLengthChange' : false, // 是否允许用户自定义每页显示条数。
	"bAutoWidth" : true, // 自适应宽度
	bSort : true,
//	order: [[ 0, "desc" ]],
	"aoColumns" : [ {
		"mData" : "userName",
		"sTitle" : "投资人用户名",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "realName",
		"sTitle" : "真实姓名",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "IDcard",
		"sTitle" : "身份证号",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "userPhone",
		"sTitle" : "电话",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "capital",
		"sTitle" : "投资金额（单位：元）",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "interest",
		"sTitle" : "利息（单位：元）",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "bankName",
		"sTitle" : "银行名称",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "bankNum",
		"sTitle" : "银行账号",
		"sClass" : "center",
		"bSearchable" : false		
	} ],
	"oLanguage" : PDD.Cons.DATATABLE_OLANGUAGE
};