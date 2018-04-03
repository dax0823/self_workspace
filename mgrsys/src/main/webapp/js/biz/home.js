/**
 * 财务模块-充值总览页
 */
var PDD = PDD || {};
PDD.Biz = PDD.Biz || {};

/**
 * 显示总览页面
 */
PDD.Biz.showHome = function() {
	$("#bizHomeDetailDiv").hide();
	$("#bizHomeBalanceDiv").hide();
	$("#bizHomeCustomerDiv").hide();
	
	//总览信息
	$.getJSON("./showBizHome.do?rand=" + Math.random(), null, function(result) {
		if (result.code == 0) {
			if (result.obj) {
//				$("#bizHomeTable span.tdAllMoney").text(result.obj.allMoney);
				$("#bizHomeTable span.tdBorrowMoney").text(result.obj.borrowMoney.toFixed(2));
				$("#bizHomeTable span.tdUsableMoney").text(result.obj.usableMoney.toFixed(2));
				$("#bizHomeTable span.tdAutoNum").text(result.obj.autoNum);
				$("#bizHomeTable span.tdAutoMoney").text(result.obj.autoMoney.toFixed(2));
				$("#bizHomeTable span.tdInpourMoney").text(result.obj.inpourMoney.toFixed(2));
				$("#bizHomeTable span.tdWithdrawMoney").text(result.obj.withdrawMoney.toFixed(2));
				$("#bizHomeTable span.tdTodayBalance").text(result.obj.currDayBalance.toFixed(2));
				$("#bizHomeTable span.tdYesterdayBalance").text(result.obj.lastDayBalance.toFixed(2));
				$("#bizHomeTable span.tdThisWeekBalance").text(result.obj.currWeekBalance.toFixed(2));
				$("#bizHomeTable span.tdLastWeekBalance").text(result.obj.lastWeekBalance.toFixed(2));
			}
		}
	});

	//初始化所有收起按钮
	$("#bizHomeDiv input[type=button][class=btn]").click(function() {
		$(this).parent().hide();
	});
	
	//初始化自动投标排队触发链接
	$("#bizHomeTable tr.trAuto").click(function() {
		if (PDD.Var.bizHomeDetailTable == null) {
			PDD.Var.bizHomeDetailTable = $('#bizHomeAutoDetailTable').dataTable(PDD.Fin.bizHomeAutoDetailTable);
		}
		$("#bizHomeDetailDiv").show();
	});
	
	//初始化各类进出明细触发链接
	//当日进出
	$("#bizHomeTable span[class=tdTodayBalance]").parent().click(function() {
		PDD.Fin.bizHomeBalanceContent("today");
	});
	//昨日进出
	$("#bizHomeTable span[class=tdYesterdayBalance]").parent().click(function() {
		PDD.Fin.bizHomeBalanceContent("yesterday");
	});
	//本周进出
	$("#bizHomeTable span[class=tdThisWeekBalance]").parent().click(function() {
		PDD.Fin.bizHomeBalanceContent("currWeek");
	});
	//上周进出
	$("#bizHomeTable span[class=tdLastWeekBalance]").parent().click(function() {
		PDD.Fin.bizHomeBalanceContent("lastWeek");
	});
	
	//初始化用户个人详细信息内容
//	$("#bizHomeDiv input[type=button][value=temp]").click(function() {
//		var userId = 44;
//	});
	
	//初始化 tab 标签页点击效果
	$("#bizHomeOtherTab span").click(function() {
		var type = $(this).attr("ref").replace("other", "");
		// 1. 将所有 div 的表格隐藏
		$("#bizHomeOtherContentDiv div.div").each(function(i) {
			$(this).removeClass("on");
		});
		// 2. 将选中的表格展开
		$("#bizHomeOtherContentDiv div[id*=" + type + "]").addClass("on");
		// 3. 修改 tab 页样式
		$("#bizHomeOtherTab span").each(function() {
			$(this).removeClass("on");
		});
		$(this).addClass("on");
		
		//将滚动条自动移动到页面底部
		$('div.ui-layout-center').scrollTop( $('div.ui-layout-center')[0].scrollHeight, "slow");
	});
};

/**
 * 根据点击情况，显示对应用户的详细个人相关信息
 */
PDD.Fin.bizHomeShowCustomerInfo = function(userId) {
	// 用户基础信息
	$.getJSON("./showBizHomeCustomerInfoBase.do?rand=" + Math.random() + "&userId=" + userId, null, function(result) {
		if (result.code == 0) {
			if (result.obj) {
				$("#bizHomeCustomerTable span.tdUserName").text(result.obj.userName);
				$("#bizHomeCustomerTable span.tdUserId").text(result.obj.id);
				$("#bizHomeCustomerTable span.tdRealName").text(result.obj.realName);
				$("#bizHomeCustomerTable span.tdSex").text(result.obj.sex);
				$("#bizHomeCustomerTable span.tdRecommendUser").text(result.obj.recommendName);
				$("#bizHomeCustomerTable span.tdCustomerName").text(result.obj.customerName);
//				$("#bizHomeCustomerTable span.tdIsFreeze").text();
				$("#bizHomeCustomerTable span.tdIDCard").text(result.obj.idcard);
				$("#bizHomeCustomerTable span.tdMobile").text(result.obj.userPhone);
				$("#bizHomeCustomerTable span.tdEmail").text(result.obj.userEmail);
				$("#bizHomeCustomerTable span.tdAddress").text(result.obj.address);
			}
		}
	});
	
	//用户资金信息
	$.getJSON("./showBizHomeCustomerInfoMoney.do?rand=" + Math.random() + "&userId=" + userId, null, function(result) {
		if (result.code == 0) {
			if (result.obj) {
				$("#bizHomeCustomerMoneyTable span.tdAccountSum").text(result.obj.accountSum.toFixed(2));
				$("#bizHomeCustomerMoneyTable span.tdCollectSum").text(result.obj.collectMoney.toFixed(2));
				$("#bizHomeCustomerMoneyTable span.tdUsableMoney").text(result.obj.usableMoney.toFixed(2));
				$("#bizHomeCustomerMoneyTable span.tdFreezeMoney").text(result.obj.freezeMoney.toFixed(2));
			}
		}
	});
	
	//用户充值信息
	$.getJSON("./showBizHomeCustomerInfoInpour.do?rand=" + Math.random() + "&userId=" + userId, null, function(result) {
		if (result.code == 0) {
			if (result.obj) {
				$("#bizHomeCustomerMoneyTable span.tdInpourNum").text(result.obj.num);
				$("#bizHomeCustomerMoneyTable span.tdInpourSum").text(result.obj.money.toFixed(2));
			}
		}
	});
	
	//用户提现信息
	$.getJSON("./showBizHomeCustomerInfoWithdraw.do?rand=" + Math.random() + "&userId=" + userId, null, function(result) {
		if (result.code == 0) {
			if (result.obj) {
				$("#bizHomeCustomerMoneyTable span.tdWithdrawNum").text(result.obj.num);
				$("#bizHomeCustomerMoneyTable span.tdWithdrawSum").text(result.obj.money.toFixed(2));
			}
		}
	});
	
	//用户投标信息
	if (PDD.Var.bizHomeInvestTable != null) {
		PDD.Var.bizHomeInvestTable.fnClearTable();
		PDD.Var.bizHomeInvestTable.fnDestroy();
	}
	PDD.Fin.bizHomeInvestTable.sAjaxSource = "./showBizHomeCustomerInfoInvest.do?rand=" + Math.random() + "&userId=" + userId;
	PDD.Var.bizHomeInvestTable = $('#bizHomeInvestTable').dataTable(PDD.Fin.bizHomeInvestTable);
	
	//用户身份证图片
	$.getJSON("./showBizHomeCustomerInfoIDCardPath.do?rand=" + Math.random() + "&userId=" + userId, null, function(result) {
		if (result.code == 0) {
			if (result.obj) {
				$("#bizHomeOtherIdCardDiv img").attr("src", result.obj);
			}
		} else {
			$("#bizHomeOtherIdCardDiv img").attr("src", "");
		}
	});
	
	//用户资金变动记录
	if (PDD.Var.bizHomeMoneyLogTable != null) {
		PDD.Var.bizHomeMoneyLogTable.fnClearTable();
		PDD.Var.bizHomeMoneyLogTable.fnDestroy();
	}
	PDD.Fin.bizHomeMoneyLogTable.sAjaxSource = "./showBizHomeCustomerInfoMoneyLog.do?rand=" + Math.random() + "&userId=" + userId;
	PDD.Var.bizHomeMoneyLogTable = $('#bizHomeMoneyLogTable').dataTable(PDD.Fin.bizHomeMoneyLogTable);
	
	$("#bizHomeCustomerDiv").show();
};

/**
 * 替换XX进出表格内容
 */
PDD.Fin.bizHomeBalanceContent = function(type) {
	if (PDD.Var.bizHomeBalanceInpourTable != null) {
		PDD.Var.bizHomeBalanceInpourTable.fnClearTable();
		PDD.Var.bizHomeBalanceInpourTable.fnDestroy();
	}
	PDD.Fin.bizHomeBalanceInpourTable.sAjaxSource = "./showBizHomeInpourMoneyDetail.do?rand=" + Math.random() + "&type=" + type;
	PDD.Var.bizHomeBalanceInpourTable = $('#bizHomeBalanceInpourTable').dataTable(PDD.Fin.bizHomeBalanceInpourTable);
	
	if (PDD.Var.bizHomeBalanceWithdrawTable != null) {
		PDD.Var.bizHomeBalanceWithdrawTable.fnClearTable();
		PDD.Var.bizHomeBalanceWithdrawTable.fnDestroy();
	}
	PDD.Fin.bizHomeBalanceWithdrawTable.sAjaxSource = "./showBizHomeWithdrawMoneyDetail.do?rand=" + Math.random() + "&type=" + type;
	PDD.Var.bizHomeBalanceWithdrawTable = $('#bizHomeBalanceWithdrawTable').dataTable(PDD.Fin.bizHomeBalanceWithdrawTable);
	
	var perfix = "当日";
	switch(type) {
		case "yesterday" :
			perfix = "昨日";
			break;
		case "currWeek" :
			perfix = "本周";
			break;
		case "lastWeek" :
			perfix = "上周";
			break;
		default: perfix = "当日";
	}
	
	$("#bizHomeBalanceDiv span.prefixBalance").text(perfix);
	
	$("#bizHomeBalanceDiv").show();
};

/**
 * 自动投标排队情况
 */
PDD.Fin.bizHomeAutoDetailTable = {
	"bInfo" : false, // 开关，是否显示表格的一些信息
	"bJQueryUI" : false,
	"bServerSide" : false, // 指定从服务器端获取数据
	"sAjaxSource" : "./showBizHomeAutoDetail.do?rand=" + Math.random(),
	'bPaginate' : true, // 是否分页
	'sPaginationType' : 'full_numbers', // 分页样式
	"bProcessing" : true, // 当datatable获取数据时候是否显示正在处理提示信息。
	'bFilter' : false, // 是否使用内置的过滤功能。
	'bLengthChange' : false, // 是否允许用户自定义每页显示条数。
	"bAutoWidth" : true, // 自适应宽度
	bSort : true,
//	order: [[ 7, "asc" ]],
	"aoColumns" : [ {
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
		"mData" : "usableMoney",
		"sTitle" : "可用额度（单位：元）",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			return data.toFixed(2);
		}
	}, {
		"mData" : "collectMoney",
		"sTitle" : "待收金额（单位：元）",
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
	}, {
		"mData" : "recommendUser",
		"sTitle" : "推荐人",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "customerName",
		"sTitle" : "专属客服",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "regTime",
		"sTitle" : "注册时间",
		"sClass" : "center",
		"bSearchable" : false
	} ],
	"oLanguage" : PDD.Cons.DATATABLE_OLANGUAGE
};

/**
 * XX进出充值明细
 */
PDD.Fin.bizHomeBalanceInpourTable = {
	"bInfo" : false, // 开关，是否显示表格的一些信息
	"bJQueryUI" : false,
	"bServerSide" : false, // 指定从服务器端获取数据
//	"sAjaxSource" : "./showBizHomeAutoDetail.do?rand=" + Math.random(),
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
		"sTitle" : "用户名",
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
	}, {
		"mData" : "time",
		"sTitle" : "时间",
		"sClass" : "center",
		"bSearchable" : false
	} ],
	"oLanguage" : PDD.Cons.DATATABLE_OLANGUAGE,
	fnInitComplete : function(obj) { // 表格加载完成后执行
		//充值小计
		var data = PDD.Var.bizHomeBalanceInpourTable.fnGetData();
		var sum = 0;
		for (var i=0; i<data.length; i++) {
			sum += data[i].money;
		}
		$("#bizHomeBalanceInpourTable").closest("div.halfBalance").find("span.sum").text(sum.toFixed(2));
		
		//显示用户个人信息
		PDD.Var.bizHomeBalanceInpourTable.$("tr").click(function() {
			var userId = PDD.Var.bizHomeBalanceInpourTable.fnGetData(this).id;
			PDD.Fin.bizHomeShowCustomerInfo(userId);
		}).css("cursor", "pointer");
	}
};

/**
 * XX进出提现明细
 */
PDD.Fin.bizHomeBalanceWithdrawTable = {
	"bInfo" : false, // 开关，是否显示表格的一些信息
	"bJQueryUI" : false,
	"bServerSide" : false, // 指定从服务器端获取数据
//	"sAjaxSource" : "./.do?rand=" + Math.random(),
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
		"sTitle" : "用户名",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "money",
		"sTitle" : "提现金额（单位：元）",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			return data.toFixed(2);
		}
	}, {
		"mData" : "time",
		"sTitle" : "时间",
		"sClass" : "center",
		"bSearchable" : false
	} ],
	"oLanguage" : PDD.Cons.DATATABLE_OLANGUAGE,
	fnInitComplete : function(obj) { // 表格加载完成后执行
		//提现小计
		var data = PDD.Var.bizHomeBalanceWithdrawTable.fnGetData();
		var sum = 0;
		for (var i=0; i<data.length; i++) {
			sum += data[i].money;
		}
		$("#bizHomeBalanceWithdrawTable").closest("div.halfBalance").find("span.sum").text(sum.toFixed(2));

		//显示用户个人信息
		PDD.Var.bizHomeBalanceWithdrawTable.$("tr").click(function() {
			var userId = PDD.Var.bizHomeBalanceWithdrawTable.fnGetData(this).id;
			PDD.Fin.bizHomeShowCustomerInfo(userId);
		}).css("cursor", "pointer");
	}
};

/**
 * 用户个人投标信息记录
 */
PDD.Fin.bizHomeInvestTable = {
	"bInfo" : false, // 开关，是否显示表格的一些信息
	"bJQueryUI" : false,
	"bServerSide" : false, // 指定从服务器端获取数据
//	"sAjaxSource" : "./.do?rand=" + Math.random(),
	'bPaginate' : true, // 是否分页
	'sPaginationType' : 'full_numbers', // 分页样式
	"bProcessing" : true, // 当datatable获取数据时候是否显示正在处理提示信息。
	'bFilter' : false, // 是否使用内置的过滤功能。
	'bLengthChange' : false, // 是否允许用户自定义每页显示条数。
	"bAutoWidth" : true, // 自适应宽度
	bSort : true,
	order: [[ 1, "desc" ]],
	"aoColumns" : [ {
		"mData" : "borrowName",
		"sTitle" : "标的名称",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "investTime",
		"sTitle" : "投标日期",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "investorCapital",
		"sTitle" : "投标金额（单位：元）",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			return data.toFixed(2);
		}
	}, {
		"mData" : "endTime",
		"sTitle" : "到期日期",
		"sClass" : "center",
		"bSearchable" : false
	} ],
	"oLanguage" : PDD.Cons.DATATABLE_OLANGUAGE
};

/**
 * 用户资金变动记录
 */
PDD.Fin.bizHomeMoneyLogTable = {
	"bInfo" : false, // 开关，是否显示表格的一些信息
	"bJQueryUI" : false,
	"bServerSide" : false, // 指定从服务器端获取数据
	'bPaginate' : true, // 是否分页
	'sPaginationType' : 'full_numbers', // 分页样式
	"bProcessing" : true, // 当datatable获取数据时候是否显示正在处理提示信息。
	'bFilter' : false, // 是否使用内置的过滤功能。
	'bLengthChange' : false, // 是否允许用户自定义每页显示条数。
	iDisplayLength: 15,	//每页显示行数
	"bAutoWidth" : true, // 自适应宽度
	bSort : true,
	order: [[ 0, "desc" ]],
	"aoColumns" : [ {
		"mData" : "logTime",
		"sTitle" : "变动时间",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "affectMoney",
		"sTitle" : "变动金额（单位：元）",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			return data.toFixed(2);
		}
	}, {
		"mData" : "info",
		"sTitle" : "说明",
		"sClass" : "center",
		"bSearchable" : false
	} ],
	"oLanguage" : PDD.Cons.DATATABLE_OLANGUAGE
};