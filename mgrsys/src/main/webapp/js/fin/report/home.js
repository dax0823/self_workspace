/**
 * 财务模块-报表
 */
var PDD = PDD || {};
PDD.Fin = PDD.Fin || {};

/**
 * 显示总览页面
 */
PDD.Fin.showReportHome = function() {
	$("#finReportHomeInfoTable").show();
	$("#finReportHomeDiv div.error").hide();
	
	//默认显示上月数据
//	PDD.Fin.finReportHomeDivShow(PDD.Utils.getNowDate(3));
	PDD.Fin.finReportHomeDivShow();
	
	//初始化日期选择组件
	$('#finReportHomeInfoDiv input.month').Zebra_DatePicker({
		view: 'month',
		format: 'Y-m',
	    always_visible: $('#finReportHomeInfoDiv div.container')
	});
	
	//初始化查询按钮
	$("#finReportInfoBtn").click(function() {
//		var month = $("#finReportHomeInfoDiv input.month").val();
		PDD.Fin.finReportHomeDivShow($("#finReportHomeInfoDiv input.month").val());
	});
};

/**
 * 当用户点击客户列表时，将客户详情 div 展开
 */
PDD.Fin.finReportHomeDivShow = function(month) {
	$("#finReportHomeInfoTable span.tdMonth").text("");
	$("#finReportHomeInfoTable span.tdNumbers").text("");
	$("#finReportHomeInfoTable span.tdVolume").text("");
	$("#finReportHomeInfoTable span.tdCapitalBack").text("");
	$("#finReportHomeInfoTable span.tdInterestBack").text("");
	$("#finReportHomeInfoTable span.tdFee").text("");
	$("#finReportHomeInfoTable span.tdProfit").text("");
	$("#finReportHomeInfoTable span.tdCapitalUncollected").text("");
	$("#finReportHomeInfoTable span.tdInterestUncollected").text("");
	$("#finReportHomeInfoTable span.tdFeeUncollected").text("");
	$("#finReportHomeInfoTable span.tdSumUncollected").text("");
	$("#finReportHomeInfoTable span.tdAverageProfit").text("");
	$("#finReportHomeInfoTable span.tdRegisterNew").text("");
	$("#finReportHomeInfoTable span.tdRegisterSum").text("");
	
	//查询客户详情
	var param = {
			month: month
	};
	$.getJSON("./showReportHome.do?rand=" + Math.random(), param, function(result) {
		if (result.code == 0) {
			if (result.obj) {
				$("#finReportHomeInfoTable").show();
				$("#finReportHomeDiv div.error").hide();
				
				$("#finReportHomeInfoTable span.tdMonth").text(result.obj.month);
				$("#finReportHomeInfoTable span.tdNumbers").text(result.obj.numbers);
				$("#finReportHomeInfoTable span.tdVolume").text(result.obj.volume);
				$("#finReportHomeInfoTable span.tdCapitalBack").text(result.obj.capitalBack);
				$("#finReportHomeInfoTable span.tdInterestBack").text(result.obj.interestBack);
				$("#finReportHomeInfoTable span.tdFee").text(result.obj.fee);
				$("#finReportHomeInfoTable span.tdProfit").text(result.obj.profit);
				$("#finReportHomeInfoTable span.tdCapitalUncollected").text(result.obj.capitalUncollected);
				$("#finReportHomeInfoTable span.tdInterestUncollected").text(result.obj.interestUncollected);
				$("#finReportHomeInfoTable span.tdFeeUncollected").text(result.obj.feeUncollected);
				$("#finReportHomeInfoTable span.tdSumUncollected").text(result.obj.sumUncollected);
				$("#finReportHomeInfoTable span.tdAverageProfit").text(result.obj.averageProfit == null ? null : result.obj.averageProfit.toFixed(2));
				$("#finReportHomeInfoTable span.tdRegisterNew").text(result.obj.registerNew);
				$("#finReportHomeInfoTable span.tdRegisterSum").text(result.obj.registerSum);
			} else {
				$("#finReportHomeInfoTable").hide();
				$("#finReportHomeDiv div.error").show();
			}
		}
	});
};