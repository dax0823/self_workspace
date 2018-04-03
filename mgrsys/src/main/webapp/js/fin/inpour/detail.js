/**
 * 财务模块-时间段内查询页
 */
var PDD = PDD || {};
PDD.Fin = PDD.Fin || {};

/**
 * 显示时间段内统计页面
 */
PDD.Fin.showInpourDetail = function() {
	// 初始化充值 tab 按钮
	$("#finInpourDetailTab span").click(function() {
		var type = $(this).attr("ref").replace("type", "");
		// 1. 将所有 div 的表格隐藏
		$("#finInpourDetailContentDiv div.div").each(function(i) {
			$(this).removeClass("on");
		});
		// 2. 将选中的表格展开
		$("#finInpourDetailContentDiv div[id=finInpourDetail" + type + "Div]").addClass("on");
		// 3. 修改 tab 页样式
		$("#finInpourDetailTab span").each(function() {
			$(this).removeClass("on");
		});
		$(this).addClass("on");

		//将滚动条自动移动到页面底部
		$('div.ui-layout-center').scrollTop( $('div.ui-layout-center')[0].scrollHeight, "slow");
	});
	
	//初始化四个时间段查询表格
	PDD.Var.finInpourDetailAllTable = $('#finInpourDetailAllTable').dataTable(PDD.Fin.finInpourDetailAllTable);
	PDD.Var.finInpourDetailBaofooTable = $('#finInpourDetailBaofooTable').dataTable(PDD.Fin.finInpourDetailBaofooTable);
	PDD.Var.finInpourDetailEasypayTable = $('#finInpourDetailEasypayTable').dataTable(PDD.Fin.finInpourDetailEasypayTable);
	PDD.Var.finInpourDetailOffTable = $('#finInpourDetailOffTable').dataTable(PDD.Fin.finInpourDetailOffTable);

	PDD.Var.finInpourDetailAllCusTable = $('#finInpourDetailAllCustomerTable').dataTable(PDD.Fin.finInpourDetailAllCusTable);
	PDD.Var.finInpourDetailAllCusTable.parent().hide();
	PDD.Var.finInpourDetailBaofooCusTable = $('#finInpourDetailBaofooCustomerTable').dataTable(PDD.Fin.finInpourDetailBaofooCusTable);
	PDD.Var.finInpourDetailBaofooCusTable.parent().hide();
	PDD.Var.finInpourDetailEasypayCusTable = $('#finInpourDetailEasypayCustomerTable').dataTable(PDD.Fin.finInpourDetailEasypayCusTable);
	PDD.Var.finInpourDetailEasypayCusTable.parent().hide();
	PDD.Var.finInpourDetailOffCusTable = $('#finInpourDetailOffCustomerTable').dataTable(PDD.Fin.finInpourDetailOffCusTable);
	PDD.Var.finInpourDetailOffCusTable.parent().hide();
	
	//初始化时间范围查询条件组件
	$("#finInpourDetailTab input.duringDate").daterangepicker({
			format : 'YYYY-MM-DD'
//			, startDate : '2014-06-25'
//			, endDate : '2013-12-31'
		},
		function(start, end, label) {
//			alert('A date range was chosen: ' + start.format('YYYY-MM-DD') + ' to ' + end.format('YYYY-MM-DD'));
	}).css("width", "200px");
	//初始化清理按钮
	$("#finInpourDetailTab input.clear").click(function(){
		$("#finInpourDetailTab input.duringDate").val("");
	});
	
	//初始化查询按钮
	$("#finInpourDetailQueryBtn").click(function() {
//		"2014-06-25 - 2014-08-01"
		var dates = $("#finInpourDetailTab input.duringDate").val().split(" - ");
		var param = {
				startDate: dates[0],	
				endDate: dates[1]
		};
		
		//点击按钮后，将四个列表的值同时查出
		//此策略可变更
		$.getJSON("./showFinInpourDetailAll.do", param, function(result) {
			if (result.code == 0) {
				//清理上次结果
				PDD.Var.finInpourDetailAllTable.fnClearTable();
				if (result.data && result.data.length > 0) {	// 当查到数据时，才填充
					PDD.Var.finInpourDetailAllTable.fnAddData(result.data);
					//当数据重新生成时需要命名触发事件
					PDD.Var.finInpourDetailAllTable.$("tr").click(function() {
						PDD.Var.finInpourDetailTrClick (PDD.Var.finInpourDetailAllTable, this, null, PDD.Var.finInpourDetailAllCusTable);
					}).css("cursor", "pointer");
				}
			}
		});
		
		$.getJSON("./showFinInpourDetailBaofoo.do", param, function(result) {
			if (result.code == 0) {
				PDD.Var.finInpourDetailBaofooTable.fnClearTable();
				if (result.data && result.data.length > 0) {
					PDD.Var.finInpourDetailBaofooTable.fnAddData(result.data);
					PDD.Var.finInpourDetailBaofooTable.$("tr").click(function() {
						PDD.Var.finInpourDetailTrClick (PDD.Var.finInpourDetailBaofooTable, this, null, PDD.Var.finInpourDetailBaofooCusTable);
					}).css("cursor", "pointer");
				}
			}
		});
		
		$.getJSON("./showFinInpourDetailEasypay.do", param, function(result) {
			if (result.code == 0) {
				PDD.Var.finInpourDetailEasypayTable.fnClearTable();
				if (result.data && result.data.length > 0) {
					PDD.Var.finInpourDetailEasypayTable.fnAddData(result.data);
					PDD.Var.finInpourDetailEasypayTable.$("tr").click(function() {
						PDD.Var.finInpourDetailTrClick (PDD.Var.finInpourDetailEasypayTable, this, null, PDD.Var.finInpourDetailEasypayCusTable);
					}).css("cursor", "pointer");
				}
			}
		});
		
		$.getJSON("./showFinInpourDetailOff.do", param, function(result) {
			if (result.code == 0) {
				PDD.Var.finInpourDetailOffTable.fnClearTable();
				if (result.data && result.data.length > 0) {
					PDD.Var.finInpourDetailOffTable.fnAddData(result.data);
					PDD.Var.finInpourDetailOffTable.$("tr").click(function() {
						PDD.Var.finInpourDetailTrClick (PDD.Var.finInpourDetailOffTable, this, null, PDD.Var.finInpourDetailOffCusTable);
					}).css("cursor", "pointer");
				}
			}
		});
		
		//隐藏充值明细 div
		PDD.Var.finInpourDetailAllCusTable.parent().hide();
	});
	
	// 初始化明细导出按钮
	$("#finInpourDetailExportBtn").click(function() {
		var url = "./exportFinInpourDetail.do?rand=" + Math.random();
		if ($("#finInpourDetailDiv input.duringDate").val().length > 0) {
			var dates = $("#finInpourDetailDiv input.duringDate").val().split(" - ");
			url += "&startDate=" + dates[0] + "&endDate=" + dates[1];
		}
		
		window.open(url);
	});
};

/**
 * 点击行数据触发事件
 */
PDD.Var.finInpourDetailTrClick  = function(varTable, varTrData, way, varCusTable) {
	var sData = varTable.fnGetData(varTrData);
	var id = sData.userId;
	var inpourTime = sData.inpourTime.split(" ")[0];
//	var dates = $("#finInpourDetailTab input.duringDate").val().split(" - ");
	var param = {
			id: id,
//			startDate: dates[0],	
//			endDate: dates[1],
			inpourTime: inpourTime,
			way: way
	};
	
	$.getJSON("./showFinInpourDetailCus.do?rand=" + Math.random(), param, function(result) {
		if (result.code == 0 && varCusTable != null) {
			//清理上次结果
			varCusTable.fnClearTable();
			if (result.data && result.data.length > 0) {	// 当查到数据时，才填充
				varCusTable.fnAddData(result.data);
			}
//			varCusTable.parents("div.cusDiv").addClass("on");
			varCusTable.parent().show();
		}
		
		//将滚动条自动移动到页面底部
		$('div.ui-layout-center').scrollTop( $('div.ui-layout-center')[0].scrollHeight, "slow");
	});
};

/**
 * 时间段查询-all
 */
PDD.Fin.finInpourDetailAllTable = {
	"bInfo" : false, // 开关，是否显示表格的一些信息
	"bJQueryUI" : false,
	"bServerSide" : false, // 指定从服务器端获取数据
	"sAjaxSource" : "./showFinInpourDetailAll.do?rand=" + Math.random(), // mvc后台ajax调用接口。
	'bPaginate' : true, // 是否分页
	'sPaginationType' : 'full_numbers', // 分页样式
	"bProcessing" : true, // 当datatable获取数据时候是否显示正在处理提示信息。
	'bFilter' : false, // 是否使用内置的过滤功能。
	'bLengthChange' : false, // 是否允许用户自定义每页显示条数。
	"bAutoWidth" : true, // 自适应宽度
	bSort : true,
	order: [[ 0, "desc" ]],
	"aoColumns" : [ {
		"mData" : "inpourTime",
		"sTitle" : "时间",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "userName",
		"sTitle" : "客户名称",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "summy",
		"sTitle" : "金额（单位：元）",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			// 修正 JSONObject 转对象时造成的小数点后位数过多问题
			return data.toFixed(2);
		}
	}, {
		"mData" : "way",
		"sTitle" : "充值方式",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			var way = "线下";
			if (data == "baofoo") way = "宝付";
			if (data == "easypay") way = "易生支付";
			return way;
		}
	} ],
	"oLanguage" : PDD.Cons.DATATABLE_OLANGUAGE,
	fnInitComplete : function(obj) { // 表格加载完成后执行
		PDD.Var.finInpourDetailAllTable.$("tr").click(function() {
			PDD.Var.finInpourDetailTrClick (PDD.Var.finInpourDetailAllTable, this, null, PDD.Var.finInpourDetailAllCusTable);
		}).css("cursor", "pointer");
	}
};

/**
 * 时间段查询-baofoo
 */
PDD.Fin.finInpourDetailBaofooTable = {
	"bInfo" : false, // 开关，是否显示表格的一些信息
	"bJQueryUI" : false,
	"bServerSide" : false, // 指定从服务器端获取数据
	"sAjaxSource" : "./showFinInpourDetailBaofoo.do?rand=" + Math.random(), // mvc后台ajax调用接口。
	'bPaginate' : true, // 是否分页
	'sPaginationType' : 'full_numbers', // 分页样式
	"bProcessing" : true, // 当datatable获取数据时候是否显示正在处理提示信息。
	'bFilter' : false, // 是否使用内置的过滤功能。
	'bLengthChange' : false, // 是否允许用户自定义每页显示条数。
	"bAutoWidth" : true, // 自适应宽度
	bSort : true,
	order: [[ 0, "desc" ]],
	"aoColumns" : [ {
		"mData" : "inpourTime",
		"sTitle" : "时间",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "userName",
		"sTitle" : "客户名称",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "summy",
		"sTitle" : "金额（单位：元）",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			// 修正 JSONObject 转对象时造成的小数点后位数过多问题
			return data.toFixed(2);
		}
	} ],
	"oLanguage" : PDD.Cons.DATATABLE_OLANGUAGE,
	fnInitComplete : function(obj) { // 表格加载完成后执行
		PDD.Var.finInpourDetailBaofooTable.$("tr").click(function() {
			PDD.Var.finInpourDetailTrClick (PDD.Var.finInpourDetailBaofooTable, this, "baofoo", PDD.Var.finInpourDetailBaofooCusTable);
		}).css("cursor", "pointer");
	}
};


/**
 * 时间段查询-easypay
 */
PDD.Fin.finInpourDetailEasypayTable = {
	"bInfo" : false, // 开关，是否显示表格的一些信息
	"bJQueryUI" : false,
	"bServerSide" : false, // 指定从服务器端获取数据
	"sAjaxSource" : "./showFinInpourDetailEasypay.do?rand=" + Math.random(), // mvc后台ajax调用接口。
	'bPaginate' : true, // 是否分页
	'sPaginationType' : 'full_numbers', // 分页样式
	"bProcessing" : true, // 当datatable获取数据时候是否显示正在处理提示信息。
	'bFilter' : false, // 是否使用内置的过滤功能。
	'bLengthChange' : false, // 是否允许用户自定义每页显示条数。
	"bAutoWidth" : true, // 自适应宽度
	bSort : true,
	order: [[ 0, "desc" ]],
	"aoColumns" : [ {
		"mData" : "inpourTime",
		"sTitle" : "时间",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "userName",
		"sTitle" : "客户名称",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "summy",
		"sTitle" : "金额（单位：元）",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			// 修正 JSONObject 转对象时造成的小数点后位数过多问题
			return data.toFixed(2);
		}
	} ],
	"oLanguage" : PDD.Cons.DATATABLE_OLANGUAGE,
	fnInitComplete : function(obj) { // 表格加载完成后执行
		PDD.Var.finInpourDetailEasypayTable.$("tr").click(function() {
			PDD.Var.finInpourDetailTrClick (PDD.Var.finInpourDetailEasypayTable, this, "easypay", PDD.Var.finInpourDetailEasypayCusTable);
		}).css("cursor", "pointer");
	}
};


/**
 * 时间段查询-off
 */
PDD.Fin.finInpourDetailOffTable = {
	"bInfo" : false, // 开关，是否显示表格的一些信息
	"bJQueryUI" : false,
	"bServerSide" : false, // 指定从服务器端获取数据
	"sAjaxSource" : "./showFinInpourDetailOff.do?rand=" + Math.random(), // mvc后台ajax调用接口。
	'bPaginate' : true, // 是否分页
	'sPaginationType' : 'full_numbers', // 分页样式
	"bProcessing" : true, // 当datatable获取数据时候是否显示正在处理提示信息。
	'bFilter' : false, // 是否使用内置的过滤功能。
	'bLengthChange' : false, // 是否允许用户自定义每页显示条数。
	"bAutoWidth" : true, // 自适应宽度
	bSort : true,
	order: [[ 0, "desc" ]],
	"aoColumns" : [ {
		"mData" : "inpourTime",
		"sTitle" : "时间",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "userName",
		"sTitle" : "客户名称",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "summy",
		"sTitle" : "金额（单位：元）",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			// 修正 JSONObject 转对象时造成的小数点后位数过多问题
			return data.toFixed(2);
		}
	} ],
	"oLanguage" : PDD.Cons.DATATABLE_OLANGUAGE,
	fnInitComplete : function(obj) { // 表格加载完成后执行
		PDD.Var.finInpourDetailOffTable.$("tr").click(function() {
			PDD.Var.finInpourDetailTrClick (PDD.Var.finInpourDetailOffTable, this, "off", PDD.Var.finInpourDetailOffCusTable);
		}).css("cursor", "pointer");
	}
};

//**************************************************************************
//客户单笔明细-all
PDD.Fin.finInpourDetailAllCusTable = {
	"bInfo" : false, // 开关，是否显示表格的一些信息
	"bJQueryUI" : false,
	"bServerSide" : false, // 指定从服务器端获取数据
	"sAjaxSource" : "./showFinInpourDetailCus.do?rand=" + Math.random() + "",
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
		"sTitle" : "客户名称",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "inpourTime",
		"sTitle" : "时间",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "money",
		"sTitle" : "金额（单位：元）",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			// 修正 JSONObject 转对象时造成的小数点后位数过多问题
			return data.toFixed(2);
		}
	}, {
		"mData" : "way",
		"sTitle" : "充值方式",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			var way = "线下";
			if (data == "baofoo") way = "宝付";
			if (data == "easypay") way = "易生支付";
			return way;
		}
	} ],
	"oLanguage" : PDD.Cons.DATATABLE_OLANGUAGE
};

//客户单笔明细-baofoo
PDD.Fin.finInpourDetailBaofooCusTable = {
	"bInfo" : false, // 开关，是否显示表格的一些信息
	"bJQueryUI" : false,
	"bServerSide" : false, // 指定从服务器端获取数据
	"sAjaxSource" : "./showFinInpourDetailCus.do?rand=" + Math.random() + "&way=baofoo",
	'bPaginate' : true, // 是否分页
	'sPaginationType' : 'full_numbers', // 分页样式
	"bProcessing" : true, // 当datatable获取数据时候是否显示正在处理提示信息。
	'bFilter' : false, // 是否使用内置的过滤功能。
	'bLengthChange' : false, // 是否允许用户自定义每页显示条数。
	"bAutoWidth" : true, // 自适应宽度
	bSort : true,
	order: [[ 0, "desc" ]],
	"aoColumns" : [ {
		"mData" : "userName",
		"sTitle" : "客户名称",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "inpourTime",
		"sTitle" : "时间",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "money",
		"sTitle" : "金额（单位：元）",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			// 修正 JSONObject 转对象时造成的小数点后位数过多问题
			return data.toFixed(2);
		}
	} ],
	"oLanguage" : PDD.Cons.DATATABLE_OLANGUAGE
};

//客户单笔明细-easypay
PDD.Fin.finInpourDetailEasypayCusTable = {
	"bInfo" : false, // 开关，是否显示表格的一些信息
	"bJQueryUI" : false,
	"bServerSide" : false, // 指定从服务器端获取数据
	"sAjaxSource" : "./showFinInpourDetailCus.do?rand=" + Math.random() + "&way=easypay",
	'bPaginate' : true, // 是否分页
	'sPaginationType' : 'full_numbers', // 分页样式
	"bProcessing" : true, // 当datatable获取数据时候是否显示正在处理提示信息。
	'bFilter' : false, // 是否使用内置的过滤功能。
	'bLengthChange' : false, // 是否允许用户自定义每页显示条数。
	"bAutoWidth" : true, // 自适应宽度
	bSort : true,
	order: [[ 0, "desc" ]],
	"aoColumns" : [ {
		"mData" : "userName",
		"sTitle" : "客户名称",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "inpourTime",
		"sTitle" : "时间",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "money",
		"sTitle" : "金额（单位：元）",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			// 修正 JSONObject 转对象时造成的小数点后位数过多问题
			return data.toFixed(2);
		}
	} ],
	"oLanguage" : PDD.Cons.DATATABLE_OLANGUAGE
};

//客户单笔明细-off
PDD.Fin.finInpourDetailOffCusTable = {
	"bInfo" : false, // 开关，是否显示表格的一些信息
	"bJQueryUI" : false,
	"bServerSide" : false, // 指定从服务器端获取数据
	"sAjaxSource" : "./showFinInpourDetailCus.do?rand=" + Math.random() + "&way=off",
	'bPaginate' : true, // 是否分页
	'sPaginationType' : 'full_numbers', // 分页样式
	"bProcessing" : true, // 当datatable获取数据时候是否显示正在处理提示信息。
	'bFilter' : false, // 是否使用内置的过滤功能。
	'bLengthChange' : false, // 是否允许用户自定义每页显示条数。
	"bAutoWidth" : true, // 自适应宽度
	bSort : true,
	order: [[ 0, "desc" ]],
	"aoColumns" : [ {
		"mData" : "userName",
		"sTitle" : "客户名称",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "inpourTime",
		"sTitle" : "时间",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "money",
		"sTitle" : "金额（单位：元）",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			// 修正 JSONObject 转对象时造成的小数点后位数过多问题
			return data.toFixed(2);
		}
	} ],
	"oLanguage" : PDD.Cons.DATATABLE_OLANGUAGE
};