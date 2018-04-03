/**
 * 财务模块-利息提醒页
 */
var PDD = PDD || {};
PDD.Fin = PDD.Fin || {};

/**
 * 显示利息提醒页面
 */
PDD.Fin.showInvestRemind = function() {
	//清空日历变量
	PDD.Var.finInvestRemindCalendar = null;
//	PDD.Var.finInvestRemindCalendarArr = [];

	//初始化日历组件
	PDD.Fin.finInvestRemindCalendarTruning();
	//日历中的时间为当前日期
	PDD.Var.finInvestRemindCalendarDate = PDD.Utils.getNowDate();
	
	//先隐藏本金、利息归还 div
	$("#finInvestRemindCapitalDiv").hide();
	$("#finInvestRemindInterestDiv").hide();
	
	//初始化所有收起按钮
	$("#finInvestRemindDiv input[type=button][class=btn]").click(function() {
		$(this).parent().hide();
	});
	
	//改变日历中鼠标样式
//	$("#finInvestRemindCalendarDiv div.fc-content").css("cursor", "pointer");
	$("#finInvestRemindCalendarDiv div.fc-content").delay(2000).css("cursor", "pointer");
};

/**
 * 明细列表中用户详细信息 html
 */
PDD.Fin.finInvestRemindDetailCustomerTableHtml = "<tr><td colspan='7'><table id='###TableId'><thead></thead><tbody></tbody></table>"
		+ "<div class='remindDetailCustomerDiv'><b>&nbsp;&nbsp;&nbsp;客户详细信息</b>"
		+"<table id='###CusTableId' class='remindDetailCustomerTable' >                "
		+"	<tr>                                                     "
		+"		<td>用户名称：<span class='tdUserName'></span></td>    "
		+"		<td>真实姓名：<span class='tdRealName'></span></td>    "
		+"	</tr>                                                    "
		+"	<tr>                                                     "
		+"		<td>待收金额：<span class='tdCollectMoney'></span></td>"
		+"		<td>身份证号：<span class='tdIDCard'></span></td>      "
		+"	</tr>                                                    "
		+"	<tr>                                                     "
		+"		<td>可用余额：<span class='tdUsableMoney'></span></td>      "
		+"		<td>手机号码：<span class='tdMobile'></span></td>     "
		+"	</tr>                                                    "
		+"	<tr>                                                     "
		+"		<td>注册时间：<span class='tdRegTime'></span></td>      "
		+"		<td>电子邮箱：<span class='tdEmail'></span></td>       "
		+"	</tr>                                                    "
		+"</table></div></td></tr>";

/**
 * 翻页（上月、下月）按钮
 */
PDD.Fin.finInvestRemindCalendarTruning = function(date) {
	if (date == null) {
		date = PDD.Utils.getNowDate();
	}
	
	//若数据已经查询过，则不在重复查找
//	if (PDD.Var.finInvestRemindCalendarArr && PDD.Var.finInvestRemindCalendarArr.length > 0) {
//		var flag = false;
//		for (var i=0; i<PDD.Var.finInvestRemindCalendarArr.length; i++) {
//			if (date == PDD.Var.finInvestRemindCalendarArr[i]) {
//				flag = true;
//				break;
//			}
//		}
//		if (flag) {
//			$('#finInvestRemindCalendarDiv').fullCalendar('rerenderEvents');
//			return ;
//		}
//	}
	
	//动态获取提醒内容
	var param = {
			date: date
	};
	$.getJSON("./showFinInvestRemind.do?rand=" + Math.random(), param, function(result) {
		PDD.Var.finInvestRemindCalendarArr.push(date);
		var arr = [];
		if (result.code == 0) {
			var reminds = result.obj;
			if (reminds && PDD.Fin.finInvestRemindCalendar) {
				//曲线图所需x轴时间数据
				PDD.Var.finInvestRemindLineChartsDays = [];	//数组重新初始化
				PDD.Var.finInvestRemindLineChartsCapital = [];
				PDD.Var.finInvestRemindLineChartsInterest = [];
				var tmpDate = new Date(date.replace(new RegExp(/(-)/g), "/"));
				var year = tmpDate.getFullYear();
				var month = tmpDate.getMonth() + 1;
//				for (var i=0; i<PDD.Utils.getDaysInCurrMonth(); i++) {
				for (var i=0; i<PDD.Utils.getDaysInMonth(year, month); i++) {
					PDD.Var.finInvestRemindLineChartsDays.push(year + "-" + (month<10?"0"+month:month) + "-" + ((i+1)<10?"0"+(i+1):(i+1)));
				}
				var x = 0;	//x轴相对于实际数据的修正下标
				
				for (var i=0; i<reminds.length; i++) {
					//当日本金总和
					if (reminds[i].sumCapital > 0) {
						arr.push({
							id: "capital," + reminds[i].remindTime,
//							title : "本金还款：" + reminds[i].sumCapital.toFixed(2),
							title : "本金还款：" + PDD.Utils.commafy(reminds[i].sumCapital),
							backgroundColor : "#FF8C00",
							start : reminds[i].remindTime
						});
					}
					
					//当日利息总和
					if (reminds[i].sumInterest > 0) {
						arr.push({
							id: "interest," + reminds[i].remindTime,
//							title : "利息还款：" + reminds[i].sumInterest.toFixed(2),
							title : "利息还款：" + PDD.Utils.commafy(reminds[i].sumInterest),
							backgroundColor : "#6495ED",
							start : reminds[i].remindTime
						});
					}

					//根据返回的数据，配置曲线图所需数据
					for (; i<PDD.Var.finInvestRemindLineChartsDays.length-x; ) {
						if (reminds[i].remindTime == PDD.Var.finInvestRemindLineChartsDays[i+x]) {
							PDD.Var.finInvestRemindLineChartsCapital.push(reminds[i].sumCapital==0? 0:parseInt(reminds[i].sumCapital.toFixed(2)));
							PDD.Var.finInvestRemindLineChartsInterest.push(reminds[i].sumInterest==0? 0:parseInt(reminds[i].sumInterest.toFixed(2)));
							break;
						} else {
							PDD.Var.finInvestRemindLineChartsCapital.push(0);
							PDD.Var.finInvestRemindLineChartsInterest.push(0);
							x++;
						}
					}
				}
				PDD.Fin.finInvestRemindCalendar.events = arr;
			}
		}

		//移除当前日历中所有已有项
		$('#finInvestRemindCalendarDiv').fullCalendar( 'removeEvents');
		
		//填充日历内容
		if (PDD.Var.finInvestRemindCalendar == null) {
			$('#finInvestRemindCalendarDiv').empty();
			PDD.Var.finInvestRemindCalendar = $('#finInvestRemindCalendarDiv').fullCalendar(PDD.Fin.finInvestRemindCalendar);

			//上个月按钮事件
			$("div.fc-button-group button.fc-prev-button").click(function() {
				//日期指向上个月
				PDD.Var.finInvestRemindCalendarDate = PDD.Utils.getPrevMonth(PDD.Var.finInvestRemindCalendarDate);
				//重画日历
				PDD.Fin.finInvestRemindCalendarTruning(PDD.Var.finInvestRemindCalendarDate);
			});
			
			//下个月按钮事件
			$("div.fc-button-group button.fc-next-button").click(function() {
				//日期指向下个月
				PDD.Var.finInvestRemindCalendarDate = PDD.Utils.getNextMonth(PDD.Var.finInvestRemindCalendarDate);
				PDD.Fin.finInvestRemindCalendarTruning(PDD.Var.finInvestRemindCalendarDate);
			});
		} else {
			for (var j=0; j<arr.length; j++) {
				$('#finInvestRemindCalendarDiv').fullCalendar('renderEvent', arr[j], false);
			}
		}
		
		//（异步）动态加载图标 js
//		if (typeof Highcharts == "undefined") {	//已加载
//			$.getScript("./framework/highcharts/highcharts.js", function() {
//				$.getScript("./framework/highcharts/highcharts-3d.js", function() {
					//当有实际数据时才绘图
					if (PDD.Var.finInvestRemindLineChartsCapital != null && PDD.Var.finInvestRemindLineChartsCapital.length > 0) {
						$("#finInvestRemindLineChartsDiv").highcharts( {
							title: {
					            text: '还款日程曲线图',
					            x: -20 //center
					        },
//					        subtitle: {
//					            text: 'Source: WorldClimate.com',
//					            x: -20
//					        },
					        xAxis: {
					            categories: PDD.Var.finInvestRemindLineChartsDays
//					            	['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
					        },
					        credits: {
								enabled: false	//去掉右下角商标
							},
					        yAxis: {
					            title: {
					                text: '金额 (单位：元)'
					            },
					            plotLines: [{
					                value: 0,
					                width: 1,
					                color: '#808080'
					            }]
					        },
					        tooltip: {
					            valueSuffix: '元'
					        },
					        legend: {
					            layout: 'vertical',
					            align: 'right',
					            verticalAlign: 'middle',
					            borderWidth: 0
					        },
					        series: [{
					            name: '本金',
					            data: PDD.Var.finInvestRemindLineChartsCapital
					        }, {
					            name: '利息',
					            data: PDD.Var.finInvestRemindLineChartsInterest
					        }]
							, colors:[	//线段颜色与日历表中的统一
								'#FF8C00',
								'#6495ED'
							]
						});
//					}
//				});
//			});
		} else {
			//曲线图重绘
			PDD.Var.finInvestRemindLineCharts = $("#finInvestRemindLineChartsDiv").highcharts();
			if (PDD.Var.finInvestRemindLineCharts && PDD.Var.finInvestRemindLineCharts.series) {
				//清除以前的图形
				while (PDD.Var.finInvestRemindLineCharts.series.length > 0) {
					PDD.Var.finInvestRemindLineCharts.series[0].remove(false);
				}
				
				//将新数据存入
				PDD.Var.finInvestRemindLineCharts.addSeries({
					name: '本金',
		            data: PDD.Var.finInvestRemindLineChartsCapital
	            }, false);
				PDD.Var.finInvestRemindLineCharts.addSeries({
					name: '利息',
		            data: PDD.Var.finInvestRemindLineChartsInterest
	            }, false);
				
				//重绘
				PDD.Var.finInvestRemindLineCharts.redraw();
			}
		}
	});
};

//日历组件配置
PDD.Fin.finInvestRemindCalendar = {
	defaultDate: PDD.Utils.getNowDate(),
	editable: false,
	eventLimit : true, // allow "more" link when too many events
//	theme : true,
	height: 700,
	buttonText : {
//		month : '月',
//		week : '周',
//		day : '日',
		today : '今天',
//		prev : '昨天',
//		next : '明天',
		prev : '《',
		next : '》',
//		prevYear : '去年',
//		nextYear : '明年'
	},
	dayNamesShort : ["日", "一", "二", "三", "四", "五", "六"],
//	monthNamesShort : ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
	monthNames : ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
	eventClick: function(calEvent, jsEvent, view) {
//	        alert('Event: ' + calEvent.title + "; id = " + calEvent.id);
//	        alert('Coordinates: ' + jsEvent.pageX + ',' + jsEvent.pageY);
//	        alert('View: ' + view.name);
//	        $(this).css('border-color', 'red');
		var ids = calEvent.id.split(",");
		var type = ids[0];
		var remindTime = ids[1];
		if (type == "capital") {
			PDD.Fin.finInvestRemindCapitalDivShow(remindTime);
		} else if (type == "interest") {
			PDD.Fin.finInvestRemindInterestDivShow(remindTime);
		} else {
			alert("remind type is error.");
		}
    },
    dayClick: function(date, allDay, jsEvent, view) {
//    	alert(123);
//    	PDD.Fin.finInvestRemindCalendar.css("background-color", "#fff");
//    	$(this).css("background-color", "#E9967A");
    }
};

/**
 * 展开本金归还 div
 * date: "YYYY-MM-dd"
 */
PDD.Fin.finInvestRemindCapitalDivShow = function(date) {
	$("#finInvestRemindCapitalDiv").show();
	$("#finInvestRemindCapitalDiv span.reamindCapital").text(date);
	
	//查询客户充值记录
	if (PDD.Var.finInvestRemindCapitalTable != null) {
		PDD.Var.finInvestRemindCapitalTable.fnClearTable();
		PDD.Var.finInvestRemindCapitalTable.fnDestroy();
	}
	PDD.Fin.finInvestRemindCapitalTable.sAjaxSource = "./showFinInvestRemindCapitalOneDay.do?rand=" + Math.random() + "&date=" + date;
	PDD.Var.finInvestRemindCapitalTable = $('#finInvestRemindCapitalTable').dataTable(PDD.Fin.finInvestRemindCapitalTable);
	
	//将滚动条自动移动到页面底部
	$('div.ui-layout-center').scrollTop( $('div.ui-layout-center')[0].scrollHeight, "slow");
};

/**
 * 展开利息归还 div
 * date: "YYYY-MM-dd"
 */
PDD.Fin.finInvestRemindInterestDivShow = function(date) {
	$("#finInvestRemindInterestDiv").show();
	$("#finInvestRemindInterestDiv span.reamindInterest").text(date);
	
	//查询客户充值记录
	if (PDD.Var.finInvestRemindInterestTable != null) {
		PDD.Var.finInvestRemindInterestTable.fnClearTable();
		PDD.Var.finInvestRemindInterestTable.fnDestroy();
	}
	PDD.Fin.finInvestRemindInterestTable.sAjaxSource = "./showFinInvestRemindInterestOneDay.do?rand=" + Math.random() + "&date=" + date;
	PDD.Var.finInvestRemindInterestTable = $('#finInvestRemindInterestTable').dataTable(PDD.Fin.finInvestRemindInterestTable);
	
	//将滚动条自动移动到页面底部
	$('div.ui-layout-center').scrollTop( $('div.ui-layout-center')[0].scrollHeight, "slow");
};

/**
 * 某天内所有投标的本金回款信息列表
 */
PDD.Fin.finInvestRemindCapitalTable = {
	"bInfo" : false, // 开关，是否显示表格的一些信息
	"bJQueryUI" : false,
	"bServerSide" : false, // 指定从服务器端获取数据
//	"sAjaxSource" : "./showFinInvestRemindCapitalOneDay.do?rand=" + Math.random(), // mvc后台ajax调用接口
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
		"sTitle" : "投标名称",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "borrowUserName",
		"sTitle" : "借款人用户名",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "borrowMoney",
		"sTitle" : "金额（单位：元）",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
//			return data.toFixed(2);
			return PDD.Utils.commafy(data);
			
		}
	}, {
		"mData" : "borrowInterest",
		"sTitle" : "利息（单位：元）",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
//			return data.toFixed(2);
			return PDD.Utils.commafy(data);
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
		"mData" : "borrowDuration",
		"sTitle" : "期限（月）",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "sumReceiveCapital",
		"sTitle" : "需还本金（单位：元）",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
//			return data.toFixed(2);
			return PDD.Utils.commafy(data);
		}
	}, {
		"mData" : "id",
		"sTitle" : "投标链接",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			return "<a href='"  + PDD.Cons.INVEST_REMIND_LINK_STR.replace("##NUM", data) + "' target='_blank'>打开</a>";
		}
	}],
	"oLanguage" : PDD.Cons.DATATABLE_OLANGUAGE,
	fnInitComplete : function(obj) { // 表格加载完成后执行
		PDD.Var.finInvestRemindCapitalTable.$("tr").click(function() {
			var detailRowTable = $(this).next().find("table");	//明细行对象的下一行内的 table
			var date = $("#finInvestRemindCapitalDiv span.reamindCapital").text();	//时间
			var borrowId = PDD.Var.finInvestRemindCapitalTable.fnGetData(this).id;	//投标 id
			if (detailRowTable.length > 0 && detailRowTable.attr("id") == "captial_detail_" + borrowId) {
				$(this).next().slideToggle("fast");
			} else {
				//将客户详细信息的脚本插入主 table 中
				var detailHtml = PDD.Fin.finInvestRemindDetailCustomerTableHtml.replace("###TableId", "captial_detail_" + borrowId)
					.replace("###CusTableId", "captial_detail_" + borrowId + "_cus");
				$(this).after(detailHtml);
				
				PDD.Fin.finInvestRemindCapitalDetailTable.sAjaxSource = "./showFinInvestRemindCapitalDetailOneDay.do?rand=" 
					+ Math.random() + "&date=" + date + "&borrowId=" + borrowId;
				
				// detailRowTable 中是空的，需再次获取明细 table 对象
				var captialDetail = $(this).next().find("table[id=captial_detail_" + borrowId + "]");
				PDD.Var.finInvestRemindCapitalDetailTable = captialDetail.dataTable(PDD.Fin.finInvestRemindCapitalDetailTable);
				
				//修改客户列表显示样式
				var width = $("#finInvestRemindDiv").width() * 0.45;
				$(this).next().find("div[id=captial_detail_" + borrowId + "_wrapper]").width(width).css("float", "left");
				$(this).next().find("div[id=captial_detail_" + borrowId + "_cus]").width(width).css("float", "left");
			}
		}).css("cursor", "pointer");
	}
};

/**
 * 某天内所有投标的利息回款信息列表
 */
PDD.Fin.finInvestRemindInterestTable = {
	"bInfo" : false, // 开关，是否显示表格的一些信息
	"bJQueryUI" : false,
	"bServerSide" : false, // 指定从服务器端获取数据
//	"sAjaxSource" : "./showFinInvestRemindInterestOneDay.do?rand=" + Math.random(), // mvc后台ajax调用接口
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
		"sTitle" : "名称",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "borrowUserName",
		"sTitle" : "借款人用户名",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "borrowMoney",
		"sTitle" : "金额（单位：元）",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
//			return data.toFixed(2);
			return PDD.Utils.commafy(data);
		}
	}, {
		"mData" : "borrowInterest",
		"sTitle" : "利息（单位：元）",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
//			return data.toFixed(2);
			return PDD.Utils.commafy(data);
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
		"mData" : "borrowDuration",
		"sTitle" : "期限（月）",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "sumReceiveInterest",
		"sTitle" : "需还利息（单位：元）",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
//			return data.toFixed(2);
			return PDD.Utils.commafy(data);
		}
	}, {
		"mData" : "id",
		"sTitle" : "投标链接",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
			return "<a href='"  + PDD.Cons.INVEST_REMIND_LINK_STR.replace("##NUM", data) + "' target='_blank'>打开</a>";
		}
	}],
	"oLanguage" : PDD.Cons.DATATABLE_OLANGUAGE,
	fnInitComplete : function(obj) { // 表格加载完成后执行
		PDD.Var.finInvestRemindInterestTable.$("tr").click(function() {
			var detailRowTable = $(this).next().find("table");	//明细行对象的下一行内的 table
			var date = $("#finInvestRemindInterestDiv span.reamindInterest").text();	//时间
			var borrowId = PDD.Var.finInvestRemindInterestTable.fnGetData(this).id;	//投标 id
			if (detailRowTable.length > 0 && detailRowTable.attr("id") == "interest_detail_" + borrowId) {
				$(this).next().slideToggle("fast");
			} else {
				var detailHtml = PDD.Fin.finInvestRemindDetailCustomerTableHtml.replace("###TableId", "interest_detail_" + borrowId)
					.replace("###CusTableId", "interest_detail_" + borrowId + "_cus");
				$(this).after(detailHtml);
				PDD.Fin.finInvestRemindInterestDetailTable.sAjaxSource = "./showFinInvestRemindInterestDetailOneDay.do?rand=" 
					+ Math.random() + "&date=" + date + "&borrowId=" + borrowId;
				// detailRowTable 中是空的，需再次获取明细 table 对象
				var interestDetail = $(this).next().find("table[id=interest_detail_" + borrowId + "]");
				PDD.Var.finInvestRemindInterestDetailTable = interestDetail.dataTable(PDD.Fin.finInvestRemindInterestDetailTable);

				var width = $("#finInvestRemindDiv").width() * 0.45;
				$(this).next().find("div[id=interest_detail_" + borrowId + "_wrapper]").width(width).css("float", "left");
				$(this).next().find("div[id=interest_detail_" + borrowId + "_cus]").width(width).css("float", "left");
			}
		}).css("cursor", "pointer");
	}
};

//**********************************************************************************************************

/**
 * 某个标所有用户投资本金情况
 */
PDD.Fin.finInvestRemindCapitalDetailTable = {
	"bInfo" : false, // 开关，是否显示表格的一些信息
	"bJQueryUI" : false,
	"bServerSide" : false, // 指定从服务器端获取数据
	'bPaginate' : false, // 是否分页
//	iDisplayLength： 10000
//	'sPaginationType' : 'full_numbers', // 分页样式
	"bProcessing" : true, // 当datatable获取数据时候是否显示正在处理提示信息。
	'bFilter' : false, // 是否使用内置的过滤功能。
	'bLengthChange' : false, // 是否允许用户自定义每页显示条数。
	"bAutoWidth" : true, // 自适应宽度
	bSort : true,
//		order: [[ 1, "desc" ]],
	"aoColumns" : [ {
		"mData" : "userName",
		"sTitle" : "用户名",
		"sClass" : "center",
		"bSearchable" : false
//	}, {
//		"mData" : "deadline",
//		"sTitle" : "本金回款时间",
//		"sClass" : "center",
//		"bSearchable" : false
	}, {
		"mData" : "capital",
		"sTitle" : "金额（单位：元）",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
//			return data.toFixed(2);
			return PDD.Utils.commafy(data);
		}
	}],
	"oLanguage" : PDD.Cons.DATATABLE_OLANGUAGE,
	fnInitComplete : function(obj) {
		//改变子表格的显示样式
		$(this).removeClass().removeAttr().addClass("remindDetailTable");
		$(this).find("thead tr").addClass("remindDetailTableTheadTr");
		$(this).find("tbody").addClass("remindDetailTableTbody");
		var _table = $(this);
		var _varSelf = PDD.Var.finInvestRemindCapitalDetailTable;
		// 获取对应用户信息
		$(this).find("tbody tr").click(function() {
			var param = {
				userId: _varSelf.fnGetData(this).userId
			};
			$.getJSON("./showRemindBorrowDetailCustomerInfo.do?rand=" + Math.random(), param, function(result) {
				if (result.code == 0) {
					if (result.obj) {
						var remindDetailCustomerDiv = _table.parents("td").find("div.remindDetailCustomerDiv");
						remindDetailCustomerDiv.css("display", "block");
						
						// 填充客户相关字段
						var table = remindDetailCustomerDiv.find("table");
						table.find("span.tdUserName").text(result.obj.userName);
						table.find("span.tdRealName").text(result.obj.realName);
//						table.find("span.tdCollectMoney").text(result.obj.collectMoney.toFixed(2) + " 元");
						table.find("span.tdCollectMoney").text(PDD.Utils.commafy(result.obj.collectMoney) + " 元");
						table.find("span.tdIDCard").text(result.obj.IDCard);
//						table.find("span.tdUsableMoney").text(result.obj.usableMoney.toFixed(2) + " 元");
						table.find("span.tdUsableMoney").text(PDD.Utils.commafy(result.obj.usableMoney) + " 元");
						table.find("span.tdMobile").text(result.obj.mobile);
						table.find("span.tdRegTime").text(result.obj.regTime);
						table.find("span.tdEmail").text(result.obj.email);
					}
				}
			});
		}).css("cursor", "pointer");
	}
};

/**
 *  某个标所有用户所获利息情况
 */
PDD.Fin.finInvestRemindInterestDetailTable = {
	"bInfo" : false, // 开关，是否显示表格的一些信息
	"bJQueryUI" : false,
	"bServerSide" : false, // 指定从服务器端获取数据
	'bPaginate' : false, // 是否分页
//	'sPaginationType' : 'full_numbers', // 分页样式
	"bProcessing" : true, // 当datatable获取数据时候是否显示正在处理提示信息。
	'bFilter' : false, // 是否使用内置的过滤功能。
	'bLengthChange' : false, // 是否允许用户自定义每页显示条数。
	"bAutoWidth" : true, // 自适应宽度
	bSort : true,
//		order: [[ 1, "desc" ]],
	"aoColumns" : [ {
		"mData" : "userName",
		"sTitle" : "投标用户名",
		"sClass" : "center",
		"bSearchable" : false
//	}, {
//		"mData" : "deadline",
//		"sTitle" : "利息回款时间",
//		"sClass" : "center",
//		"bSearchable" : false
	}, {
		"mData" : "interest",
		"sTitle" : "金额（单位：元）",
		"sClass" : "center",
		"bSearchable" : false,
		'mRender' : function(data, type, row) {
//			return data.toFixed(2);
			return PDD.Utils.commafy(data);
		}
	}, {
		"mData" : "sortOrder",
		"sTitle" : "进行中期数",
		"sClass" : "center",
		"bSearchable" : false
	}, {
		"mData" : "total",
		"sTitle" : "总期数",
		"sClass" : "center",
		"bSearchable" : false
	}],
	"oLanguage" : PDD.Cons.DATATABLE_OLANGUAGE,
	fnInitComplete : function(obj) {
		//改变子表格的显示样式
		$(this).removeClass().removeAttr().addClass("remindDetailTable");
		$(this).find("thead tr").addClass("remindDetailTableTheadTr");
		$(this).find("tbody").addClass("remindDetailTableTbody");
		var _table = $(this);
		var _varSelf = PDD.Var.finInvestRemindInterestDetailTable;
		// 获取对应用户信息
		$(this).find("tbody tr").click(function() {
			var param = {
				userId: _varSelf.fnGetData(this).userId
			};
			$.getJSON("./showRemindBorrowDetailCustomerInfo.do?rand=" + Math.random(), param, function(result) {
				if (result.code == 0) {
					if (result.obj) {
						var remindDetailCustomerDiv = _table.parents("td").find("div.remindDetailCustomerDiv");
						remindDetailCustomerDiv.css("display", "block");
						
						var table = remindDetailCustomerDiv.find("table");
						table.find("span.tdUserName").text(result.obj.userName);
						table.find("span.tdRealName").text(result.obj.realName);
//						table.find("span.tdCollectMoney").text(result.obj.collectMoney.toFixed(2) + " 元");
						table.find("span.tdCollectMoney").text(PDD.Utils.commafy(result.obj.collectMoney) + " 元");
						table.find("span.tdIDCard").text(result.obj.IDCard);
//						table.find("span.tdUsableMoney").text(result.obj.usableMoney.toFixed(2) + " 元");
						table.find("span.tdUsableMoney").text(PDD.Utils.commafy(result.obj.usableMoney) + " 元");
						table.find("span.tdMobile").text(result.obj.mobile);
						table.find("span.tdRegTime").text(result.obj.regTime);
						table.find("span.tdEmail").text(result.obj.email);
					}
				}
			});
		}).css("cursor", "pointer");
	}
};