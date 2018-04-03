/**
 * 财务模块-投资总览页
 */
var PDD = PDD || {};
PDD.Fin = PDD.Fin || {};

/**
 * 显示总览页面
 */
PDD.Fin.showInvestHome = function() {
	// 页面加载时，先将下方的明细列表隐藏
	$("#finInvestHomeDetailDiv").hide();
	$("#finInvestHomeSumInterestDiv").hide();
	
	// 初始化时间选择控件
	$("#finInvestHomeDiv input.duringDate").daterangepicker({
		format : 'YYYY-MM-DD',
		startDate : '2014-06-25'
	}, function(start, end, label) {
	}).css("width", "200px");
	// 初始化清理按钮
	$("#finInvestHomeDiv input.clear").click(function() {
		$("#finInvestHomeDiv input.duringDate").val("");
	});
	
	// 初始化总览表格
	PDD.Var.finInvestHomeTable = $('#finInvestHomeTable').dataTable(PDD.Fin.finInvestHomeTable);
	PDD.Var.finInvestHomeDetailTable = $('#finInvestHomeDetailTable').dataTable(PDD.Fin.finInvestHomeDetailTable);
//	PDD.Var.finInvestHomeSumInterestTable = $('#finInvestHomeSumInterestTable').dataTable(PDD.Fin.finInvestHomeSumInterestTable);
	
	//初始化查询按钮
	$("#finInvestHomeQueryBtn").click(function() {
//		"2014-06-25 - 2014-08-01"
		var dates = $("#finInvestHomeDiv input.duringDate").val().split(" - ");
		var param = {
				startDate: dates[0],	
				endDate: dates[1]
		};
		
		$.getJSON("./showFinInvestHome.do", param, function(result) {
			if (result.code == 0) {
				//清理上次结果
				PDD.Var.finInvestHomeTable.fnClearTable();
				if (result.data && result.data.length > 0) {	// 当查到数据时，才填充
					PDD.Var.finInvestHomeTable.fnAddData(result.data);
					PDD.Var.finInvestHomeTable.$("tr").click(function() {
						PDD.Var.finInvestHomeTrClick(PDD.Var.finInvestHomeTable, this, null, PDD.Var.finInvestHomeDetailTable);
					}).css("cursor", "pointer");
				}
			} else {
			}

			//将滚动条自动移动到页面底部
//			$('div.ui-layout-center').scrollTop( $('div.ui-layout-center')[0].scrollHeight, "slow");
		});
	});
	
	// 初始化总览导出按钮
	$("#finInvestHomeExportBtn").click(function() {
		var url = "./exportInvestHome.do?rand=" + Math.random();
		if ($("#finInvestHomeDiv input.duringDate").val().length > 0) {
			dates = $("#finInvestHomeDiv input.duringDate").val().split(" - ");
			url += "startDate=" + dates[0] + "&endDate=" + dates[1];
		}
		window.open(url);
	});
	
	//初始化投标明细导出按钮
	$("#finInvestHomeDetailExportBtn").click(function() {
		var id = $(this).attr("ref");
		if (id && $.trim(id).length > 0) {
			window.open("./exportInvestHomeDetail.do?rand=" + Math.random() + "&id=" + id);
		}
	});
	
	//初始化“显示利息返还详情”按钮事件
	$("#finInvestHomeDetailDiv input.interestBtn").click(function() {
		var status = $("#finInvestHomeSumInterestDiv").css("display");
		if (status == "none") {
			$("#finInvestHomeSumInterestDiv").show();
			$(this).attr("value", "收起利息返还详情");

			var param = {
					//获取投标 id
					id: $("#finInvestHomeDetailExportBtn").attr("ref")
			};
			
			$.getJSON("./showFinInvestHomeSumInterest.do", param, function(result) {
				if (result.code == 0) {
					if (result.data && result.data.length > 0) {
						//根据实际投标的月份，添加列数
						var n = result.data[0].borrowDuration;
						var arr = [];
						//将模板填充
						for (var j=0; j<PDD.Fin.finInvestHomeSumInterestTableCol.length; j++) {
							arr.push(PDD.Fin.finInvestHomeSumInterestTableCol[j]);
						}
						//将动态列填充
						for (var i=0; i<n; i++) {
							var obj = {
								"mData" : "month" + (i + 1),
								"sTitle" : "第 " + (i + 1) + " 月（单位：元）",
								"bSearchable" : false,
								'mRender' : function(data, type, row) {
									return data.toFixed(2);
								}
							};
							arr.push(obj);
						}
						PDD.Fin.finInvestHomeSumInterestTable.aoColumns = arr;
					}

					if (PDD.Var.finInvestHomeSumInterestTable != null) {
						PDD.Var.finInvestHomeSumInterestTable.fnClearTable();
						PDD.Var.finInvestHomeSumInterestTable.fnDestroy();
						$("#finInvestHomeSumInterestTable thead").empty();
					}

					PDD.Var.finInvestHomeSumInterestTable = $('#finInvestHomeSumInterestTable').dataTable(PDD.Fin.finInvestHomeSumInterestTable);
					PDD.Var.finInvestHomeSumInterestTable.fnDraw();
					//数据填充
					PDD.Var.finInvestHomeSumInterestTable.fnAddData(result.data);
				}
				//将滚动条自动移动到页面底部
				$('div.ui-layout-center').scrollTop( $('div.ui-layout-center')[0].scrollHeight, "slow");
			});
		} else {
			$("#finInvestHomeSumInterestDiv").hide();
			$(this).attr("value", "显示利息返还详情");
//			PDD.Var.finInvestHomeSumInterestTable.fnDestroy();
		}
	});
	
	// 初始化利息返还详情按钮
	$("#finInvestHomeSumInterestExportBtn").click(function() {
		var url = "./exportInvestHomeSumInterest.do?rand=" + Math.random() + "&id=" + $("#finInvestHomeDetailExportBtn").attr("ref");
		window.open(url);
	});
};

/**
 * 点击列表行，触发动作
 */
PDD.Var.finInvestHomeTrClick = function(varTable, varTrData, way, varDetailTable) {
	var sData = varTable.fnGetData(varTrData);
	var id = sData.id;
	var param = {
			id: id
	};
	
	$.getJSON("./showFinInvestHomeDetail.do?rand=" + Math.random(), param, function(result) {
		if (result.code == 0 && varDetailTable != null) {
			//清理上次结果
			varDetailTable.fnClearTable();
			if (result.data && result.data.length > 0) {	// 当查到数据时，才填充
				if (varDetailTable != null && varDetailTable.length > 0) {
					varDetailTable.fnAddData(result.data);
				}
			}
			varDetailTable.parent().show();
		}
	});

	$("#finInvestHomeDetailDiv").show();
	
	//将 id 保存到按钮中，以备后面的导出使用
	$("#finInvestHomeDetailExportBtn").attr("ref", id);
	
	//将下方的利息返还列表隐藏
	$("#finInvestHomeSumInterestDiv").hide();
	$("#finInvestHomeDetailDiv input.interestBtn").attr("value", "显示利息返还详情");
};

/**
 * 总览表格
 */
PDD.Fin.finInvestHomeTable = {
	"bInfo" : false, // 开关，是否显示表格的一些信息
	"bJQueryUI" : false,
	"bServerSide" : false, // 指定从服务器端获取数据
	"sAjaxSource" : "./showFinInvestHome.do?rand=" + Math.random(), // mvc后台ajax调用接口
	'bPaginate' : true, // 是否分页
	'sPaginationType' : 'full_numbers', // 分页样式
	"bProcessing" : true, // 当datatable获取数据时候是否显示正在处理提示信息。
	'bFilter' : false, // 是否使用内置的过滤功能。
	'bLengthChange' : false, // 是否允许用户自定义每页显示条数。
	"bAutoWidth" : true, // 自适应宽度
	bSort : true,
	order: [[ 6, "desc" ]],
	"aoColumns" : [ {
		"mData" : "borrowName",
		"sTitle" : "名称",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "borrowMoney",
		"sTitle" : "金额（单位：元）",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			return data.toFixed(2);
		}
	}, {
		"mData" : "borrowInterest",
		"sTitle" : "利息（单位：元）",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			return data.toFixed(2);
		}
	}, {
		"mData" : "borrowInterestRate",
		"sTitle" : "利率（%）",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			return data.toFixed(2);
		}
	}, {
		"mData" : "rewardNum",
		"sTitle" : "奖励利率（%）",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			return data.toFixed(2);
		}
	}, {
		"mData" : "borrowDuration",
		"sTitle" : "期限（月）",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "deadline",
		"sTitle" : "到期日",
		"sClass" : "center",
		"bSearchable" : false
	}],
	"oLanguage" : PDD.Cons.DATATABLE_OLANGUAGE,
	fnInitComplete : function(obj) { // 表格加载完成后执行
		PDD.Var.finInvestHomeTable.$("tr").click(function() {
			PDD.Var.finInvestHomeTrClick(PDD.Var.finInvestHomeTable, this, null, PDD.Var.finInvestHomeDetailTable);
			$("#finInvestHomeDetailDiv").show();
		}).css("cursor", "pointer");
	}
};

/**
 * 投标明细
 */
PDD.Fin.finInvestHomeDetailTable = {
	"bInfo" : false, // 开关，是否显示表格的一些信息
	"bJQueryUI" : false,
	"bServerSide" : false, // 指定从服务器端获取数据
//	"sAjaxSource" : "./showFinInvestHome.do?rand=" + Math.random(), // mvc后台ajax调用接口
	'bPaginate' : true, // 是否分页
	'sPaginationType' : 'full_numbers', // 分页样式
	"bProcessing" : true, // 当datatable获取数据时候是否显示正在处理提示信息。
	'bFilter' : false, // 是否使用内置的过滤功能。
	'bLengthChange' : false, // 是否允许用户自定义每页显示条数。
	"bAutoWidth" : true, // 自适应宽度
	bSort : true,
	order: [[ 1, "desc" ]],
	"aoColumns" : [ {
		"mData" : "userName",
		"sTitle" : "投资人",
		"sClass" : "userBankInfo",
		"bSearchable" : false
	}, {
		"mData" : "investTime",
		"sTitle" : "时间",
		"bSearchable" : false
	}, {
		"mData" : "investorCapital",
		"sTitle" : "金额（单位：元）",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			return data.toFixed(2);
		}
	}, {
		"mData" : "borrowName",
		"sTitle" : "投标名称",
		"bSearchable" : false
	}, {
		"mData" : "borrowDuration",
		"sTitle" : "周期（月）",
		"bSearchable" : false
	}, {
		"mData" : "borrowInterestRate",
		"sTitle" : "利率（%）",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			return data.toFixed(2);
		}
	}, {
		"mData" : "investorInterest",
		"sTitle" : "利息（单位：元）",
		"sClass" : "userInterest ",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			return data.toFixed(2);
		}
	} ],
	"oLanguage" : PDD.Cons.DATATABLE_OLANGUAGE
};

/**
 * 投标利息返还情况
 */
//PDD.Fin.finInvestHomeSumInterestTable = {
//	"bInfo" : false, // 开关，是否显示表格的一些信息
//	"bJQueryUI" : false,
//	"bServerSide" : false, // 指定从服务器端获取数据
////	"sAjaxSource" : "./showFinInvestHomeSumInterest.do?rand=" + Math.random() + "&id=" + $("#finInvestHomeDetailExportBtn").attr("ref"),
//	'bPaginate' : true, // 是否分页
//	'sPaginationType' : 'full_numbers', // 分页样式
//	"bProcessing" : true, // 当datatable获取数据时候是否显示正在处理提示信息。
//	'bFilter' : false, // 是否使用内置的过滤功能。
//	'bLengthChange' : false, // 是否允许用户自定义每页显示条数。
//	"bAutoWidth" : true, // 自适应宽度
//	bRetrieve: true,	//重新检索
//	bDestroy: true,
//	bSort : true,
////	order: [[ 0, "desc" ]],
//	"aoColumns" : [ {
//		"mData" : "userName",
//		"sTitle" : "投资人",
//		"bSearchable" : false
//	},  {
//		"mData" : "sumInvest",
//		"sTitle" : "金额（单位：元）",
//		"bSearchable" : false,
//		'mRender' : function(data, type, row) {
//			return data.toFixed(2);
//		}
//	},  {
//		"mData" : "sumReward",
//		"sTitle" : "奖励（单位：元）",
//		"bSearchable" : false,
//		'mRender' : function(data, type, row) {
//			return data.toFixed(2);
//		}
////	}, {
////		"mData" : "borrowDuration",
////		"sTitle" : "周期（月）",
////		"bSearchable" : false
//	}, {
//		"mData" : "borrowInterestRate",
//		"sTitle" : "利率（%）",
//		"bSearchable" : false,
//		'mRender' : function(data, type, row) {
//			return data.toFixed(2);
//		}
//	}, {
//		"mData" : "sumInterest",
//		"sTitle" : "利息（单位：元）",
//		"bSearchable" : false,
//		'mRender' : function(data, type, row) {
//			return data.toFixed(2);
//		}
//	} ],
//	"oLanguage" : PDD.Cons.DATATABLE_OLANGUAGE
//};

PDD.Fin.finInvestHomeSumInterestTable = {
	"bInfo" : false, // 开关，是否显示表格的一些信息
	"bJQueryUI" : false,
	"bServerSide" : false, // 指定从服务器端获取数据
//	"ajax" : "./showFinInpourHomeEveryDay.do?rand=" + Math.random(),
	'bPaginate' : true, // 是否分页
	'sPaginationType' : 'full_numbers', // 分页样式
	"bProcessing" : true, // 当datatable获取数据时候是否显示正在处理提示信息。
	'bFilter' : false, // 是否使用内置的过滤功能。
	'bLengthChange' : false, // 是否允许用户自定义每页显示条数。
	"bAutoWidth" : true, // 自适应宽度
	bDestroy: true,
	bRetrieve: true,	//重新检索
	bSort : true,
	order: [[ 0, "desc" ]],
//	"aoColumns" : PDD.Fin.finInvestHomeSumInterestTableCol,
//		[ {
//		"mData" : "userName",
//		"sClass": "center",
//		"sTitle" : "投资人",
//		"bSearchable" : false,
//		'mRender' : function(data, type, row) {
//			return data;
//		}
//	},  {
//		"mData" : "sumInvest",
//		"sClass": "center",
//		"sTitle" : "金额（单位：元）",
//		"bSearchable" : false,
//		'mRender' : function(data, type, row) {
//			return data.toFixed(2);
//		}
//	},  {
//		"mData" : "sumReward",
//		"sClass": "center",
//		"sTitle" : "奖励（单位：元）",
//		"bSearchable" : false,
//		'mRender' : function(data, type, row) {
//			return data.toFixed(2);
//		}
//	}, {
//		"mData" : "borrowInterestRate",
//		"sClass": "center",
//		"sTitle" : "利率（%）",
//		"bSearchable" : false,
//		'mRender' : function(data, type, row) {
//			return data.toFixed(2);
//		}
//	}, {
//		"mData" : "sumInterest",
//		"sClass": "center",
//		"sTitle" : "利息（单位：元）",
//		"bSearchable" : false,
//		'mRender' : function(data, type, row) {
//			return data.toFixed(2);
//		}
//	} ],
	"oLanguage" : PDD.Cons.DATATABLE_OLANGUAGE,
	fnInitComplete : function(obj) { // 表格加载完成后执行
		//将滚动条自动移动到页面底部
		$('div.ui-layout-center').scrollTop( $('div.ui-layout-center')[0].scrollHeight, "slow");
	}
};

PDD.Fin.finInvestHomeSumInterestTableCol = [{
	"mData" : "userName",
	"sClass": "center",
	"sTitle" : "投资人",
	"bSearchable" : false,
	'mRender' : function(data, type, row) {
		return data;
	}
},  {
	"mData" : "sumInvest",
	"sClass": "center",
	"sTitle" : "金额（单位：元）",
	"bSearchable" : false,
	'mRender' : function(data, type, row) {
		return data.toFixed(2);
	}
},  {
	"mData" : "sumReward",
	"sClass": "center",
	"sTitle" : "奖励（单位：元）",
	"bSearchable" : false,
	'mRender' : function(data, type, row) {
		return data.toFixed(2);
	}
}, {
	"mData" : "borrowInterestRate",
	"sClass": "center",
	"sTitle" : "利率（%）",
	"bSearchable" : false,
	'mRender' : function(data, type, row) {
		return data.toFixed(2);
	}
}, {
	"mData" : "sumInterest",
	"sClass": "center",
	"sTitle" : "利息（单位：元）",
	"bSearchable" : false,
	'mRender' : function(data, type, row) {
		return data.toFixed(2);
	}
}];