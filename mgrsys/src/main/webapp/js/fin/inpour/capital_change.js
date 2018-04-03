/**
 * 财务模块-客户资金变更信息页
 */
var PDD = PDD || {};
PDD.Fin = PDD.Fin || {};

/**
 * 显示客户信息页面
 */
PDD.Fin.showInpourCapitalChange = function() {
	
	//初始化时间范围查询条件组件
	$("#finInpourCapitalChangeDiv input.duringDate").daterangepicker({
			format : 'YYYY-MM-DD'
			, startDate : '2014-06-01'
		},
		function(start, end, label) {
		//to do something
	}).css("width", "200px");
	//初始化清理按钮
	$("#finInpourCapitalChangeDiv input.clear").click(function(){
		$("#finInpourCapitalChangeDiv input.duringDate").val("");
	});
	
	// 初始化总览表格
	PDD.Var.finInpourCapitalChangeTable = $('#finInpourCapitalChangeTable').dataTable(PDD.Fin.finInpourCapitalChangeTable);
	
	//初始化查询按钮
	$("#finInpourCapitalChangeQueryBtn").click(function() {
		var dates = $("#finInpourCapitalChangeDiv input.duringDate").val().split(" - ");
		var userName = $("#finInpourCapitalChangeDiv input.userName").val();
		var realName = $("#finInpourCapitalChangeDiv input.realName").val();
		
		//参数
		var param = {
				startDate: dates[0],	
				endDate: dates[1],
				userName: userName,
				realName: realName,
		};
		
		//请求
		$.getJSON("./showFinInpourCapitalChange.do", param, function(result) {
			if (result.code == 0) {
				//清理上次结果
				PDD.Var.finInpourCapitalChangeTable.fnClearTable();
				if (result.data && result.data.length > 0) {	// 当查到数据时，才填充
					PDD.Var.finInpourCapitalChangeTable.fnAddData(result.data);
				}
			}
		});
	});
};

/**
 * 总览表格
 */
PDD.Fin.finInpourCapitalChangeTable = {
	"bInfo" : false, // 开关，是否显示表格的一些信息
	"bJQueryUI" : false,
	"bServerSide" : false, // 指定从服务器端获取数据
	"sAjaxSource" : "./showFinInpourCapitalChange.do?rand=" + Math.random(), // mvc后台ajax调用接口
	'bPaginate' : true, // 是否分页
	'sPaginationType' : 'full_numbers', // 分页样式
	"bProcessing" : true, // 当datatable获取数据时候是否显示正在处理提示信息。
	'bFilter' : false, // 是否使用内置的过滤功能。
	'bLengthChange' : false, // 是否允许用户自定义每页显示条数。
	"bAutoWidth" : true, // 自适应宽度
	bSort : true,
	order: [[ 0, "desc" ]],
	"aoColumns" : [ {
		"mData" : "time",
		"sTitle" : "变更时间",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "userName",
		"sTitle" : "用户名",
		"sClass" : "center",
		"bSearchable" : false
//	}, {
//		"mData" : "realName",
//		"sTitle" : "真实姓名",
//		"sClass" : "center",
//		"bSearchable" : false
	}, {
		"mData" : "typeName",
		"sTitle" : "类型",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			//row.type == 7 ：管理员操作
			if (row.type == 7)
				return "<span style='color: #003472'><b>" + data + "</b></span>";
			else return data;
		}
	}, {
		"mData" : "affectMoney",
		"sTitle" : "变更金额（单位：元）",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			//row.type == 7 ：管理员操作
			if (row.type == 7 && (data >= 1000 || data <= -5000))
				return "<span style='color: red'><b>" + data.toFixed(2) + "</b></span>";
			else return data.toFixed(2);
		}
	}, {
		"mData" : "accountMoney",
		"sTitle" : "可用金额（单位：元）",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			return data.toFixed(2);
		}
	}, {
		"mData" : "freezeMoney",
		"sTitle" : "冻结金额（单位：元）",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			return data.toFixed(2);
		}
	} ],
	"oLanguage" : PDD.Cons.DATATABLE_OLANGUAGE
};