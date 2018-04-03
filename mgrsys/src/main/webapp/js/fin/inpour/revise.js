/**
 * 财务模块-修改记录页
 */
var PDD = PDD || {};
PDD.Fin = PDD.Fin || {};

/**
 * 显示线下奖励内容
 */
PDD.Fin.showInpourRevise = function() {
	//初始化时间范围查询条件组件
	$("#finInpourReviseDiv input.duringDate").daterangepicker({
			format : 'YYYY-MM-DD'
			, startDate : '2014-06-25'
		},
		function(start, end, label) {
		//to do something
	}).css("width", "200px");
	//初始化清理按钮
	$("#finInpourReviseDiv input.clear").click(function(){
		$("#finInpourReviseDiv input.duringDate").val("");
	});

	//初始化奖励列表
	PDD.Var.finInpourReviseTable = $('#finInpourReviseTable').dataTable(PDD.Fin.finInpourReviseTable);
	
	//初始化查询按钮
	$("#finInpourReviseQueryBtn").click(function() {
		var dates = $("#finInpourReviseDiv input.duringDate").val().split(" - ");
		var param = {
				startDate: dates[0],	
				endDate: dates[1]
		};
		
		$.getJSON("./showFinInpourRevise.do", param, function(result) {
			if (result.code == 0) {
				//清理上次结果
				PDD.Var.finInpourReviseTable.fnClearTable();
				if (result.data && result.data.length > 0) {	// 当查到数据时，才填充
					PDD.Var.finInpourReviseTable.fnAddData(result.data);
				}
			}
		});
	});
};

/**
 * 线下充值奖励，月统计表
 */
PDD.Fin.finInpourReviseTable = {
	"bInfo" : false, // 开关，是否显示表格的一些信息
	"bJQueryUI" : false,
	"bServerSide" : false, // 指定从服务器端获取数据
	"sAjaxSource" : "./showFinInpourRevise.do?rand=" + Math.random(), // mvc后台ajax调用接口。
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
		"sTitle" : "客户名称",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "realName",
		"sTitle" : "真实姓名",
		"sClass" : "center",
		"bSearchable" : false
	},  {
		"mData" : "reviseTime",
		"sTitle" : "修改时间",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "reviseMoney",
		"sTitle" : "修改金额（单位：元）",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			return data.toFixed(2);
		}
	}, {
		"mData" : "reason",
		"sTitle" : "原因",
		"sClass" : "center",
		"bSearchable" : false
	} ],
	"oLanguage" : PDD.Cons.DATATABLE_OLANGUAGE
};
