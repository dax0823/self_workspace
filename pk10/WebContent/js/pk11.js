/**
 * 常量
 */
//初始总额
//var ORIGINAL_SUM = 5000;
//var ORIGINAL_SUM = 10000;
//中奖系数
var LOTTERY_TIMES = 10;
//初始下注金额
//var BACK_MONEY = 100;
//每日数据列表中，显示主道之前N天的数据
var PER_DAY_N = 10;
//全部追车队列
var CARS_LIST = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];

/**
 * 变量
 */
// 历史数据
var histroyDataArr = new Array();
//实时数据（含历史数据）
var dataArr = new Array();
//大量数据（所有数据）
var bigDataArr = [];
//大量数据（每日数据）
var perDayDataArr = [];

//*****************************************************************************************************************

/**
 * 录入历史数据
 */
var inputHistroyData = function() {
	//格式化历史数据
	var data = $("#histroyRecordTextArea").val();	//全量历史数据字符串
	
	if($.trim(data).length == 0) alert("请录入历史数据");
	
	var rowsDataArr = data.split("\n");	//拆分成每行的字符串数组
	if (rowsDataArr) {
		for (var i=0; i<rowsDataArr.length; i++) {
			if (rowsDataArr[i] && rowsDataArr[i].length > 0) {
				var rowData = rowsDataArr[i].split("	");		//分隔开期数&每道数据
				var numbersArr = rowData[1].split(",");
				//[期数][车道数字][时间]
				bigDataArr.unshift([rowData[0], numbersArr, rowData[2]]);
			}
		}
		bigDataArr.sort(function(a, b) {
			// a-b：升序
			// b-a：降序
			return b[0] - a[0];
		});
		
		//数据格式为：“595163	06,03,10,01,08,05,02,09,07,04	2017/01/01 09:12”
		//按天分割数据，重新压入一个数组中
		var timee = "";
		var perDay = [];
		for (var a=bigDataArr.length-1; a>=0; a--) {
			var dat = bigDataArr[a][2].split(" ");
			if (timee == "") {	//第一条数据
				timee = dat[0];
				perDay.push(bigDataArr[a]);
			} else if (timee == dat[0]) {	//判断是否为同一天数据，如“2017/01/01”
				perDay.push(bigDataArr[a]);
			} else {
				perDayDataArr.push([timee, perDay]);	//将上一天的数据压入数组
				
				timee = dat[0];
				perDay = [];
				perDay.push(bigDataArr[a]);
			}
		}
		perDayDataArr.push([timee, perDay]);	//最后一天的数据压入数组
		
		//将数据中的日期填入下拉框选中
		if (perDayDataArr && perDayDataArr.length>0) {
//			<option value="1">1</option>
			var optionsHtml = "";
			for (var b=0; b<perDayDataArr.length; b++) {
				optionsHtml += "<option value='" + perDayDataArr[b][0] + "'>" + perDayDataArr[b][0] + "</option>";
			}
			$("select.mainDayDateSelect").html("").html(optionsHtml).change(function() {	//日期下拉框
				$("#compareTable tbody").html("");
//				var startDay = "2017/01/01";
//				var endDay = "2017/01/02";
//				initCompareTable(startDay, endDay);
				$("#compareTable span.mainDay").text($(this).val());
				initCompareTable($("#compareTable span.compareDay").text(), $(this).val());
				initPerDayTable(perDayDataArr[0][0], $(this).val());
			});
		}
	}
	
	//显示第一天和最后一天数据
	$("#compareTable span.compareDay").text(perDayDataArr[0][0]);
	$("#compareTable span.mainDay").text(perDayDataArr[perDayDataArr.length-1][0]);
	initPerDayTable(perDayDataArr[0][0], perDayDataArr[perDayDataArr.length-1][0]);
	initCompareTable(perDayDataArr[0][0], perDayDataArr[perDayDataArr.length-1][0]);
}

/**
 * 初始化每日统计数据表格
 * startDay：开始日期，格式为：YYYY/MM/DD，如“2017/01/01”
 * endDay：结束日期，格式为：YYYY/MM/DD，如“2017/01/01”
 */
var initPerDayTable = function(startDay, endDay) {
	if (!($.trim(startDay).length > 0 && $.trim(startDay).length > 0)) alert("initPerDayTable 日期参数有误。");

	$("#perDayTable tbody").html("");
	
	var compareDayIndex = -1;		//配道数据序号
	var mainDayIndex = -1;	//主道数据序号
	//找到主道、配道数据
	for(var i=0; i<perDayDataArr.length; i++) {
		if (startDay == perDayDataArr[i][0]) compareDayIndex = i;
		if (endDay == perDayDataArr[i][0]) mainDayIndex = i;
		
		if (compareDayIndex > 0 && mainDayIndex > 0) break;
	}
	
	//开始逐天对比
//	var n = 0;	//显示几天数据
//	for (var j=mainDayIndex, n=0; j>=compareDayIndex && n<=PER_DAY_N; j--, n++) {
	for (var j=compareDayIndex; j<=mainDayIndex; j++) {
//	for (var j=mainDayIndex; j>=compareDayIndex; j--) {
		//主道、配道比对
		var trHtml = "";
		if (j == mainDayIndex) {	//主道
			trHtml += "<td>" + perDayDataArr[j][0] + "</td><td>主道</td><td>0</td><td>0</td><td></td>";
//		} else if () {	//此为当天最后一条数据
			
		} else {	//以前几天数据
			//主道、配道比对
			var sumMoney = $("#originalSumMoneySpan").text();
			var backMoney = 0;
			var backDropNum = $("#backDropNumSpan").text();
			var backNum = $("#backNumSpan").text();
			var profit = 0;
			var isBegin = false;
			var correctNum = 0;	// 正确次数
			var mistakeNum = 0;	// 错误次数
			var nextPairIndex = -1;	//下一条对子所在位置
			var nextCarsList = [];	//当前追车队列
//			var t = 0;	//配道对子出现次数
			
			for (var k=0; k<perDayDataArr[j][1].length; k++) {	// 当天所有数据
				var pairIndex = -1;	//对子所在位置
				var carsList = [];	//当前追车队列
				var isCorrect = false;	//主道是否猜对
				
				if (k <= perDayDataArr[j][1].length - 2) {	//只处理到倒数第二条
					var numbersArr = perDayDataArr[j][1][k][1];
					for (var x=0; x<numbersArr.length; x++) {
						if (numbersArr[x] == perDayDataArr[j][1][k+1][1][x]) {	// 出现对子
//							t++;
							pairIndex = x;

							// 将配道出现对子的车号从追车队列中除去
							carsList = deepClone(CARS_LIST);
							var ind = -1;
							for (var a=0; a<carsList.length; a++) {
								if (numbersArr[x] == carsList[a]) {
									ind = a;
								}
							}
							carsList.splice(ind, 1);
							
							break;	// 只处理每条数据的第一个对子
						}
					}
				}
				if (isBegin) {
					if (nextPairIndex > -1) {
						backMoney = $("#backMoneySpan").text();
						sumMoney -= backMoney;	// 扣除下注金额
						
						var inde = -1;
						for (var b=0; b<nextCarsList.length; b++) {
							if (perDayDataArr[mainDayIndex][1][k][1][nextPairIndex] == nextCarsList[b]) {
								inde = b;
							}
						}
						if (inde > -1) {	// 查找主道对应位置是否出现追车队列中的车号
							profit = parseInt(backMoney*1 / nextCarsList.length) * LOTTERY_TIMES;
							sumMoney += profit;
							isCorrect = true;
							correctNum++;
						} else {
							profit = 0;
							isCorrect = false;
							mistakeNum++;
						}
					}
				} else isBegin = true;
				
				if (pairIndex > -1) {
					nextPairIndex = pairIndex;
					nextCarsList = deepClone(carsList);
				} else {
					nextPairIndex = -1;
					nextCarsList = [];
				}
			}
			
			trHtml = "<tr><td class='date'>"  + perDayDataArr[j][0] +"</td>" 
					+ "<td>"  + sumMoney +"</td>" 
					+ "<td>"  + correctNum +"</td>" 
					+ "<td>"  + mistakeNum +"</td>"
					+ "<td><button class='choose'>选择</button></td></tr>";
		}
		
		if ($("#perDayTable tbody tr").length > 0) {
			$("#perDayTable tbody tr:eq(0)").before(trHtml);
		} else {
			$("#perDayTable tbody").append(trHtml);
		}
	}
	
	//初始化各“选择”按钮
	$("#perDayTable button.choose").each(function() {
		var _self = this;
		$(_self).click(function() {
			var date = $(this).closest("tr").find("td.date").text();
			$("#compareTable span.compareDay").text(date);
			initCompareTable(date, $("#compareTable span.mainDay").text());
		});
	});
//	perDayTable
};

/**
 * 初始化对比数据表格
 * startDay：开始日期，格式为：YYYY/MM/DD，如“2017/01/01”
 * endDay：结束日期，格式为：YYYY/MM/DD，如“2017/01/01”
 */
var initCompareTable = function(startDay, endDay) {
	if (!($.trim(startDay).length > 0 && $.trim(startDay).length > 0)) alert("initCompareTable 日期参数有误。");
	
	$("#compareTable tbody").html("");
	
	var compareDay = [];		//配道数据
	var mainDay = [];	//主道数据
	//找到主道、配道数据
	for(var i=0; i<perDayDataArr.length; i++) {
		if (startDay == perDayDataArr[i][0]) compareDay = perDayDataArr[i];
		if (endDay == perDayDataArr[i][0]) mainDay = perDayDataArr[i];
		
		if (compareDay.length > 0 && mainDay.length > 0) break;
	}
	
	//主道、配道比对
// perDay：[rowData[0], numbersArr, rowData[2]]
//	var perDay = perDayDataArr[j];
	var sumMoney = $("#originalSumMoneySpan").text();
	var backMoney = 0;
	var backDropNum = $("#backDropNumSpan").text();
	var backNum = $("#backNumSpan").text();
	var profit = 0;
	var isBegin = false;
//	var correctNum = 0;	// 正确次数
//	var mistakeNum = 0;	// 错误次数
	var bgColor = "";
	var nextPairIndex = -1;	//下一条对子所在位置
	var nextCarsList = [];	//当前追车队列
	var t = 0;	//配道对子出现次数
	
	for (var k=0; k<compareDay[1].length; k++) {	// 当天所有数据
		var pairIndex = -1;	//对子所在位置
		var carsList = [];	//当前追车队列
//		var mainCorrectIndex = -1;	//主道猜对位置
		var isCorrect = false;	//主道是否猜对
		
		if (k <= compareDay[1].length - 2) {	//只处理到倒数第二条
			var numbersArr = compareDay[1][k][1];
			for (var x=0; x<numbersArr.length; x++) {
				if (numbersArr[x] == compareDay[1][k+1][1][x]) {	// 出现对子
					t++;
					pairIndex = x;

					// 将配道出现对子的车号从追车队列中除去
					carsList = deepClone(CARS_LIST);
					var ind = -1;
					for (var a=0; a<carsList.length; a++) {
						if (numbersArr[x] == carsList[a]) {
							ind = a;
						}
					}
					carsList.splice(ind, 1);
					
					break;	// 只处理每条数据的第一个对子
				}
			}
		}

//		if (isBegin && nextPairIndex > -1 && t > 1 && t <= backNum) {
		if (isBegin) {
			if (nextPairIndex > -1) {
				backMoney = $("#backMoneySpan").text();
				sumMoney -= backMoney;	// 扣除下注金额
				
				var inde = -1;
				for (var b=0; b<nextCarsList.length; b++) {
					if (mainDay[1][k][1][nextPairIndex] == nextCarsList[b]) {
						inde = b;
					}
				}
				if (inde > -1) {	// 查找主道对应位置是否出现追车队列中的车号
					profit = parseInt(backMoney*1 / nextCarsList.length) * LOTTERY_TIMES;
					sumMoney += profit;
					isCorrect = true;
//					correctNum++;
				} else {
					profit = 0;
					isCorrect = false;
//					mistakeNum++;
				}
			} else {
				backMoney = 0;
				profit = 0;
			}
		} else isBegin = true;
		
		//拼接页面效果
		var numbersHtml = "";
		for (var d=0; d<compareDay[1][k][1].length; d++) {
			var htm = "<td><span class='numberBGColor'>" + compareDay[1][k][1][d] + "</span></td>";
			if (d == pairIndex)	//当前对子所在位置
				htm = "<td><span class='mainCarBGColor'>" + compareDay[1][k][1][d] + "</span></td>";
			if (d == nextPairIndex)	//当前对子所在位置
				htm = "<td><span class='mainCarBGColor'>" + compareDay[1][k][1][d] + "</span></td>";
			numbersHtml += htm;
		}
		for (var f=0; f<mainDay[1][k][1].length; f++) {
			var htm = "<td><span class='mainNumberBGColor'>" + mainDay[1][k][1][f] + "</span></td>";
//			if (f == mainCorrectIndex)	//当前对子所在位置
			if (f == nextPairIndex && isBegin)	{	//当前对子所在位置
				if (isCorrect)
					htm = "<td><span class='winBGColor'>" + mainDay[1][k][1][f] + "</span></td>";
				else htm = "<td><span class='errorBGColor'>" + mainDay[1][k][1][f] + "</span></td>";
			}
				
			numbersHtml += htm;
		}
		if (pairIndex > -1) {
			nextPairIndex = pairIndex;
			nextCarsList = deepClone(carsList);
		} else {
			nextPairIndex = -1;
			nextCarsList = [];
		}
		
		var trHtml = "<tr>"
				+ "<td>"  + compareDay[1][k][0] +"</td>" 
				+ "<td><span class='time'>"  + compareDay[1][k][2].split(" ")[1] +"<span></td>" 
				+ numbersHtml
				+ "<td>"  + mainDay[1][k][0] +"</td>" 
				+ "<td class='" + bgColor + "'>" + backMoney + "</td>" 
				+ "<td class='" + bgColor + "'>" + profit + "</td>"
				+ "<td class='" + bgColor + "'>" + sumMoney + "</td>" + 
			"</tr>";
		if ($("#compareTable tbody tr").length > 0) {
			$("#compareTable tbody tr:eq(0)").before(trHtml);
		} else {
			$("#compareTable tbody").append(trHtml);
		}
	}
};

/**
 * 验证是否为正整数
 * @param a：参数
 * @returns
 */
function isUnsignedInteger(a) {
	var reg =  /^[0-9]*[1-9][0-9]*$/;
//	var reg = /^-?[1-9]//d*$/;
	return reg.test(a);
}

//深度克隆
function deepClone(obj){
    var result,oClass=isClass(obj);
        //确定result的类型
    if(oClass==="Object"){
        result={};
    }else if(oClass==="Array"){
        result=[];
    }else{
        return obj;
    }
    for(key in obj){
        var copy=obj[key];
        if(isClass(copy)=="Object"){
            result[key]=arguments.callee(copy);//递归调用
        }else if(isClass(copy)=="Array"){
            result[key]=arguments.callee(copy);
        }else{
            result[key]=obj[key];
        }
    }
    return result;
}
function isClass(o){
    if(o===null) return "Null";
    if(o===undefined) return "Undefined";
    return Object.prototype.toString.call(o).slice(8,-1);
}

//*****************************************************************************************************************

/**
 * 页面初始化
 */
$(function() {
	
	//初始化导入历史数据按钮
	$("#histroyRecordButton").click(function() {
		inputHistroyData();
	});
	
	//初始化主道下拉框
//	$("select.mainDayDateSelect").change(function() {
//		var startDay = "2017/01/01";
//		var endDay = "2017/01/02";
//		initCompareTable(startDay, endDay);
//	});
	
	//初始化初始金额按钮
	$("#originalSumMoneyButton").click(function () {
		var money = $("#originalSumMoneyInput").val();
		if (isUnsignedInteger(money))
			$("#originalSumMoneySpan").text(money);
		else alert("请输入正整数。");
	});
	
	//初始化每局下注金额按钮
	$("#backMoneyButton").click(function () {
		var money = $("#backMoneyInput").val();
		if (isUnsignedInteger(money))
			$("#backMoneySpan").text(money);
		else alert("请输入正整数。");
	});
	
	//初始化空几手按钮
	$("#backDropNumButton").click(function () {
		var num = $("#backDropNumInput").val();
		if (isUnsignedInteger(num) || num == 0)
			$("#backDropNumSpan").text(num);
		else alert("请输入正整数或“0”。");
	});
	
	//初始化追几手按钮
	$("#backNumButton").click(function () {
		var num = $("#backNumInput").val();
		if (isUnsignedInteger(num))
			$("#backNumSpan").text(num);
		else alert("请输入正整数。");
	});
});