/**
 * 财务模块-管理员信息页
 */
var PDD = PDD || {};
PDD.Fin = PDD.Fin || {};

/**
 * 显示管理员信息页面
 */
PDD.Fin.showInvestBorrowor = function() {
	//隐藏客户详情 div
	$("#finInvestBorroworDetailDiv").hide();
	
	// 初始化管理员表格
	PDD.Var.finInvestBorroworTable = $('#finInvestBorroworTable').dataTable(PDD.Fin.finInvestBorroworTable);
};

/**
 * 展开借款人明细 div
 */
PDD.Fin.finInvestBorroworDetailDivShow = function(userId) {
	//查询客户充值记录
	if (PDD.Var.finInvestBorroworDetailTable != null) {
		PDD.Var.finInvestBorroworDetailTable.fnClearTable();
		PDD.Var.finInvestBorroworDetailTable.fnDestroy();
	}
	PDD.Fin.finInvestBorroworDetailTable.sAjaxSource = "./showFinInvestBorroworDetail.do?rand=" + Math.random() + "&userId=" + userId;
	PDD.Var.finInvestBorroworDetailTable = $('#finInvestBorroworDetailTable').dataTable(PDD.Fin.finInvestBorroworDetailTable);
};

/**
 * 汇总表格
 */
PDD.Fin.finInvestBorroworTable = {
	"bInfo" : false, // 开关，是否显示表格的一些信息
	"bJQueryUI" : false,
	"bServerSide" : false, // 指定从服务器端获取数据
	"sAjaxSource" : "./showFinInvestBorrowor.do?rand=" + Math.random(), // mvc后台ajax调用接口
	'bPaginate' : true, // 是否分页
	'sPaginationType' : 'full_numbers', // 分页样式
	"bProcessing" : true, // 当datatable获取数据时候是否显示正在处理提示信息。
	'bFilter' : false, // 是否使用内置的过滤功能。
	'bLengthChange' : false, // 是否允许用户自定义每页显示条数。
	"bAutoWidth" : true, // 自适应宽度
	bSort : true,
//	order: [[ 1, "desc" ]],
	"aoColumns" : [ {
		"mData" : "userName",
		"sTitle" : "借款人",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "sumBorrow",
		"sTitle" : "借款额度（单位：元）",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			return data.toFixed(2);
		}
//	}, {
//		"mData" : "sumReward",
//		"sTitle" : "总奖励（单位：元）",
//		"sClass" : "center",
//		"bSearchable" : false,
//		'mRender' : function(data, type, row) {
//			return data.toFixed(2);
//		}
	}, {
		"mData" : "sumInterest",
		"sTitle" : "利息（单位：元）",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			return data.toFixed(2);
		}
	}, {
		"mData" : "sumFee",
		"sTitle" : "借款管理费（单位：元）",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			return data.toFixed(2);
		}
	} ],
	"oLanguage" : PDD.Cons.DATATABLE_OLANGUAGE,
	fnInitComplete : function(obj) { // 表格加载完成后执行
		PDD.Var.finInvestBorroworTable.$("tr").click(function() {
			var userId = PDD.Var.finInvestBorroworTable.fnGetData(this).id;
			PDD.Fin.finInvestBorroworDetailDivShow(userId);
			//展开详情 div
			$("#finInvestBorroworDetailDiv").show();
		}).css("cursor", "pointer");
		
		//计算三列数据合计值
		var lst = obj.aoData;
		var sumCapital = 0;
		var sumInterest = 0;
		var sumFee = 0;
		if (lst != null) {
			for (var i=0; i<lst.length; i++) {
				var vo = lst[i]._aData;
				sumCapital += vo.sumBorrow;
				sumInterest += vo.sumInterest;
				sumFee += vo.sumFee;
			}
		}
		$("#finInvestBorroworDiv div.sum span.tdSumCapital").text(sumCapital);
		$("#finInvestBorroworDiv div.sum span.tdSumInterest").text(sumInterest.toFixed(2));
		$("#finInvestBorroworDiv div.sum span.tdSumFee").text(sumFee.toFixed(2));
	}
};

/**
 * 明细表格
 */
PDD.Fin.finInvestBorroworDetailTable = {
	"bInfo" : false, // 开关，是否显示表格的一些信息
	"bJQueryUI" : false,
	"bServerSide" : false, // 指定从服务器端获取数据
	"sAjaxSource" : "./showFinInvestBorroworDetail.do?rand=" + Math.random(), // mvc后台ajax调用接口
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
		"sTitle" : "借款人",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "borrowName",
		"sTitle" : "发标名称",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "duration",
		"sTitle" : "期限",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "borrowTime",
		"sTitle" : "发标时间",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "borrowInterest",
		"sTitle" : "利息（单位：元）",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			return data.toFixed(2);
		}
	}, {
		"mData" : "borrowFee",
		"sTitle" : "管理费（单位：元）",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			return data.toFixed(2);
		}
	}, {
		"mData" : "recentlyTime",
		"sTitle" : "最近一次利息发放时间",
		"sClass" : "center",
		"bSearchable" : false
	} ],
	"oLanguage" : PDD.Cons.DATATABLE_OLANGUAGE
};