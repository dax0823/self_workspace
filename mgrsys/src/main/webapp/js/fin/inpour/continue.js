/**
 * 财务模块-续投奖励记录页
 */
var PDD = PDD || {};
PDD.Fin = PDD.Fin || {};

/**
 * 显示线下奖励内容
 */
PDD.Fin.showInpourContinue = function() {
	//初始化时间范围查询条件组件
	$("#finInpourContinueDiv input.duringDate").daterangepicker({
			format : 'YYYY-MM-DD'
			, startDate : '2014-06-25'
		},
		function(start, end, label) {
		//to do something
	}).css("width", "200px");
	//初始化清理按钮
	$("#finInpourContinueDiv input.clear").click(function(){
		$("#finInpourContinueDiv input.duringDate").val("");
	});

	//初始化奖励列表
	PDD.Var.finInpourContinueTable = $('#finInpourContinueTable').dataTable(PDD.Fin.finInpourContinueTable);
	
	//初始化查询按钮
	$("#finInpourContinueQueryBtn").click(function() {
		var dates = $("#finInpourContinueDiv input.duringDate").val().split(" - ");
		var param = {
				startDate: dates[0],	
				endDate: dates[1]
		};
		
		$.getJSON("./showFinInpourContinue.do", param, function(result) {
			if (result.code == 0) {
				//清理上次结果
				PDD.Var.finInpourContinueTable.fnClearTable();
				if (result.data && result.data.length > 0) {	// 当查到数据时，才填充
					PDD.Var.finInpourContinueTable.fnAddData(result.data);
				}
			}
		});
	});
};

/**
 * 线下充值奖励，月统计表
 */
PDD.Fin.finInpourContinueTable = {
	"bInfo" : false, // 开关，是否显示表格的一些信息
	"bJQueryUI" : false,
	"bServerSide" : false, // 指定从服务器端获取数据
	"sAjaxSource" : "./showFinInpourContinue.do?rand=" + Math.random(), // mvc后台ajax调用接口。
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
		"mData" : "continueTime",
		"sTitle" : "续投时间",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "investMoney",
		"sTitle" : "续投金额（单位：元）",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			return data.toFixed(2);
		}
	},  {
		"mData" : "rewardMoney",
		"sTitle" : "续投奖励（单位：元）",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			return data.toFixed(2);
		}
	} ],
	"oLanguage" : PDD.Cons.DATATABLE_OLANGUAGE
};
