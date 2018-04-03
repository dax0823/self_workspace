/**
 * 财务模块-续投奖励记录页
 */
var PDD = PDD || {};
PDD.Adj = PDD.Adj || {};

/**
 * 显示线下奖励内容
 */
PDD.Adj.showIntegral = function() {
	$("#adjIntegralLogDiv").hide();
	
	//初始化奖励列表
	PDD.Var.adjIntegralCustomerTable = $('#adjIntegralCustomerTable').dataTable(PDD.Adj.adjIntegralCustomerTable);
	
	//初始化时间范围查询条件组件
	$("#adjIntegralDiv input.duringDate").daterangepicker({
			format : 'YYYY-MM-DD'
			, startDate : '2014-06-01'
		},
		function(start, end, label) {
		//to do something
	}).css("width", "200px");
	//初始化清理按钮
	$("#adjIntegralDiv input.clear").click(function(){
		$("#adjIntegralDiv input.duringDate").val("");
	});
	
	//初始化查询按钮
	$("#adjIntegralCustomerQueryBtn").click(function() {
		PDD.Adj.queryCustomerList();
	});
	
	//初始化修改按钮
	$("#adjIntegralLogDiv input.btn").click(function() {
		if (!confirm("确定要修改此用户积分？")) {
			return;
		}
		
		var obj = $("#adjIntegralLogDiv input[type=radio]:checked");
		var val = $(obj).next("input[type=text]").val();
		var userId = $("#adjIntegralLogDiv input[type=hidden][class=userId]").val();
		var description = $("#adjIntegralLogDiv input.description").val();
		
		//数据合法性验证
		if (!PDD.Utils.isInteger(val)) {
			alert("请输入正整数。");
			return;
		}
		
		if ($(obj).attr("class").indexOf("addtion") > -1) {
			//增加
		} else {
			//减少
			//积分减少时的逻辑合法性判断
			var integral = $("#adjIntegralLogDiv input[type=hidden][class=integral]").val();
			var activeIntegral = $("#adjIntegralLogDiv input[type=hidden][class=activeIntegral]").val();
			if (parseInt(val) > parseInt(integral)) {
				alert("填写的“减少积分”，请不要超过用户的总积分。");
				return;
			}
			if (parseInt(val) > parseInt(activeIntegral)) {
				alert("填写的“减少积分”，请不要超过用户的活跃积分。");
				return;
			}
			
			val = val * -1;
		}
		
		//请求
		var param = {
				userId: userId,	
				adjNum: val,
//				description: encodeURI(description)
				description: description
		};
		$.getJSON("./modifyIntegral.do", param, function(result) {
			if (result.code == 0) {
				alert("该用户积分修改完毕！");
				$("#adjIntegralLogDiv").hide();
			} else {
				alert("该用户积分修改失败。");
			}
			
			//刷新用户积分列表
			PDD.Adj.queryCustomerList();
//			if (PDD.Var.adjIntegralCustomerTable != null) {
//				PDD.Var.adjIntegralCustomerTable.fnClearTable();
//				PDD.Var.adjIntegralCustomerTable.fnDestroy();
//			}
//			PDD.Adj.adjIntegralCustomerTable.sAjaxSource = "./showAdjIntegralCustomer.do?rand=" + Math.random();
//			PDD.Var.adjIntegralCustomerTable = $('#adjIntegralCustomerTable').dataTable(PDD.Adj.adjIntegralCustomerTable);
			
			//刷新积分日志列表
			if (PDD.Var.adjIntegralLogTable != null) {
				PDD.Var.adjIntegralLogTable.fnClearTable();
				PDD.Var.adjIntegralLogTable.fnDestroy();
			}
			PDD.Adj.adjIntegralLogTable.sAjaxSource = "./showAdjIntegralLog.do?rand=" + Math.random() + "&userId=" + userId;
			PDD.Var.adjIntegralLogTable = $('#adjIntegralLogTable').dataTable(PDD.Adj.adjIntegralLogTable);
		});
	});
};

/**
 * 查询客户积分列表
 */
PDD.Adj.queryCustomerList = function(userName) {
	var userName = $("#adjIntegralDiv input.userName").val();
	var dates = $("#adjIntegralDiv input.duringDate").val().split(" - ");
	
	//重新加载客户积分列表
	if (PDD.Var.adjIntegralCustomerTable != null) {
		PDD.Var.adjIntegralCustomerTable.fnClearTable();
		PDD.Var.adjIntegralCustomerTable.fnDestroy();
	}
	PDD.Adj.adjIntegralCustomerTable.sAjaxSource = "./showAdjIntegralCustomer.do?rand=" + Math.random() 
		+ "&userName=" + userName + "&startDate=" + dates[0] + "&startDate=" + dates[1];
	PDD.Var.adjIntegralCustomerTable = $('#adjIntegralCustomerTable').dataTable(PDD.Adj.adjIntegralCustomerTable);
};

/**
 * 客户积分列表
 */
PDD.Adj.adjIntegralCustomerTable = {
	"bInfo" : false, // 开关，是否显示表格的一些信息
	"bJQueryUI" : false,
	"bServerSide" : false, // 指定从服务器端获取数据
	"sAjaxSource" : "./showAdjIntegralCustomer.do?rand=" + Math.random(), // mvc后台ajax调用接口。
	'bPaginate' : true, // 是否分页
	'sPaginationType' : 'full_numbers', // 分页样式
	"bProcessing" : true, // 当datatable获取数据时候是否显示正在处理提示信息。
	'bFilter' : false, // 是否使用内置的过滤功能。
	'bLengthChange' : false, // 是否允许用户自定义每页显示条数。
	"bAutoWidth" : true, // 自适应宽度
	bSort : true,
	order: [[ 3, "desc" ]],
	"aoColumns" : [ {
		"mData" : "userName",
		"sTitle" : "客户名称",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "integral",
		"sTitle" : "总积分（可忽略）",
		"sClass" : "center",
		"bSearchable" : false
	},  {
		"mData" : "activeIntegral",
		"sTitle" : "活跃积分",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "regTime",
		"sTitle" : "注册时间",
		"sClass" : "center",
		"bSearchable" : false
	} ],
	"oLanguage" : PDD.Cons.DATATABLE_OLANGUAGE,
	fnInitComplete : function(obj) { // 表格加载完成后执行
		//显示用户个人信息
		PDD.Var.adjIntegralCustomerTable.$("tr").click(function() {
			var userId = PDD.Var.adjIntegralCustomerTable.fnGetData(this).id;
			var userName = PDD.Var.adjIntegralCustomerTable.fnGetData(this).userName;
			var integral = PDD.Var.adjIntegralCustomerTable.fnGetData(this).integral;
			var activeIntegral = PDD.Var.adjIntegralCustomerTable.fnGetData(this).activeIntegral;
			
			//修改当前日志列表上面的用户名
			$("#adjIntegralLogDiv span.customer").text(userName);
			//修改隐藏域的用户 id，以便后面的修改操作使用
			$("#adjIntegralLogDiv input[type=hidden][class=userId]").val(userId);
			$("#adjIntegralLogDiv input[type=hidden][class=integral]").val(integral);
			$("#adjIntegralLogDiv input[type=hidden][class=activeIntegral]").val(activeIntegral);
			
			//用户投标信息
			if (PDD.Var.adjIntegralLogTable != null) {
				PDD.Var.adjIntegralLogTable.fnClearTable();
				PDD.Var.adjIntegralLogTable.fnDestroy();
			}
			PDD.Adj.adjIntegralLogTable.sAjaxSource = "./showAdjIntegralLog.do?rand=" + Math.random() + "&userId=" + userId;
			PDD.Var.adjIntegralLogTable = $('#adjIntegralLogTable').dataTable(PDD.Adj.adjIntegralLogTable);
			
			$("#adjIntegralLogDiv").show();
		}).css("cursor", "pointer");
	}
};

/**
 * 客户积分日志
 */
PDD.Adj.adjIntegralLogTable = {
	"bInfo" : false, // 开关，是否显示表格的一些信息
	"bJQueryUI" : false,
	"bServerSide" : false, // 指定从服务器端获取数据
//	"sAjaxSource" : "./showAdjIntegralLog.do?rand=" + Math.random(), // mvc后台ajax调用接口。
	'bPaginate' : true, // 是否分页
	'sPaginationType' : 'full_numbers', // 分页样式
	"bProcessing" : true, // 当datatable获取数据时候是否显示正在处理提示信息。
	'bFilter' : false, // 是否使用内置的过滤功能。
	'bLengthChange' : false, // 是否允许用户自定义每页显示条数。
	iDisplayLength: 5,	//每页显示行数
	"bAutoWidth" : true, // 自适应宽度
	bSort : true,
	order: [[ 0, "desc" ]],
	"aoColumns" : [ {
		"mData" : "time",
		"sTitle" : "发生时间",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "type",
		"sTitle" : "类型",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			var str = "投资积分";
			if (data == "1") str = "还款积分";
			else if (data == "3") str = "消费积分";
			else if (data == "4") str = "其他积分";
			return str;
		}
	}, {
		"mData" : "affectIntegral",
		"sTitle" : "影响积分",
		"sClass" : "center",
		"bSearchable" : false,
	},  {
		"mData" : "activeIntegral",
		"sTitle" : "剩余活跃积分",
		"sClass" : "center",
		"bSearchable" : false
	},  {
		"mData" : "accountIntegral",
		"sTitle" : "总积分（可忽略）",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "info",
		"sTitle" : "说明",
		"sClass" : "center",
		"bSearchable" : false
	} ],
	"oLanguage" : PDD.Cons.DATATABLE_OLANGUAGE,
	fnInitComplete : function(obj) { // 表格加载完成后执行
		//将滚动条自动移动到页面底部
		$('div.ui-layout-center').scrollTop( $('div.ui-layout-center')[0].scrollHeight, "slow");
	}
};
