/**
 * 常量
 */
//中奖系数
var LOTTERY_TIMES = 10;
//每日数据列表中，显示主道之前N天的数据
var PER_DAY_N = 10;
//全部追车队列
var CARS_LIST = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];;
//向前比较数据的天数
var COMPARE_DAY_NUM = 30;
//var COMPARE_DAY_NUM = 15;

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
//全部猜错的车号数据
//mistakeCars[时间, [车号]]
var mistakeCars = [];

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
		
		if (perDayDataArr.length < 3) {
			alert("请输入超过三天的数据。");
			return;
		}
		
		//将数据中的日期填入下拉框选中
//		if (perDayDataArr && perDayDataArr.length>0) {
		if (perDayDataArr) {
			var optionsHtml = "";
			for (var b=0; b<perDayDataArr.length; b++) {
				optionsHtml += "<option value='" + perDayDataArr[b][0] + "'>" + perDayDataArr[b][0] + "</option>";
			}
			$("select.mainDayDateSelect").html("").html(optionsHtml).change(function() {	//日期下拉框
				//重置错误车号
				mistakeCars = [];
				
				$("#compareTable tbody").html("");
				$("#compareTable span.mainDay").text($(this).val());
				
				initPerDayTable(perDayDataArr[0][0], $(this).val());
				initCompareTable($(this).val());
			});
		}
	}
	
	//显示第一天和最后一天数据
//	$("#compareTable span.compareDay").text(perDayDataArr[0][0]);
	$("#compareTable span.mainDay").text(perDayDataArr[perDayDataArr.length-1][0]);
	initPerDayTable(perDayDataArr[0][0], perDayDataArr[perDayDataArr.length-1][0]);
//	initCompareTable(perDayDataArr[0][0], perDayDataArr[perDayDataArr.length-1][0]);
	initCompareTable(perDayDataArr[perDayDataArr.length-1][0]);
}

/**
 * 初始化每日统计数据表格
 * startDay：开始日期，格式为：YYYY/MM/DD，如“2017/01/01”
 * endDay：结束日期，格式为：YYYY/MM/DD，如“2017/01/01”
 */
var initPerDayTable = function(startDay, endDay) {
	if (!($.trim(startDay).length > 0 && $.trim(endDay).length > 0)) alert("initPerDayTable 日期参数有误。");

	$("#perDayTable tbody").html("");
	
	var compareDayIndex = -1;		//配道数据序号
	var mainDayIndex = -1;	//主道数据序号
	//找到主道、配道数据
	for(var i=perDayDataArr.length-1; i>=0; i--) {
		if (endDay == perDayDataArr[i][0]) mainDayIndex = i - 1;	//用倒数第二天作为比较基准
		if (startDay == perDayDataArr[i][0]) {
			compareDayIndex = i;
			if (compareDayIndex <= mainDayIndex - COMPARE_DAY_NUM) {	//仅统计截止时间前N天的数据
				compareDayIndex = mainDayIndex - COMPARE_DAY_NUM;
			}
		}
		
		if (compareDayIndex > 0 && mainDayIndex > 0) break;
	}
	
	//开始逐天对比
	for (var j=compareDayIndex; j<=mainDayIndex+1; j++) {
		//主道、配道比对
		var trHtml = "";
		if (j == mainDayIndex + 1) {	//主道
			trHtml += "<tr><td>" + perDayDataArr[j][0] + "</td><td>主道</td><td>-</td></tr>";
		} else if (j == mainDayIndex) {
			trHtml += "<tr><td>" + perDayDataArr[j][0] + "</td><td>对比基准</td><td>-</td></tr>";
		} else {	//以前几天数据
			//主道、配道比对
			var isBegin = false;
			var mistakeNum = 0;	// 错误次数
			var nextPairIndex = -1;	//二连下注位置
			var thirdPairIndex = -1;	//三连下注位置
			//三连下注行，可能在三条数据中同时出现多个下注点
			//thirdPairRowArr[行数][车号]
			var thirdPairRowArr = [];	
			var carsList = [];	//当前追车队列
			var perDayMistakeCars = [];	//猜错的车号队列
			
			for (var k=0; k<perDayDataArr[j][1].length; k++) {	// 当天所有数据
				var pairIndex = -1;	//对子所在位置
				var carsList = [];	//当前追车队列
				var isCorrect = false;	//主道是否猜对
				
				if (k <= perDayDataArr[j][1].length - 3) {	//只处理到倒数第三条
					var numbersArr = perDayDataArr[j][1][k][1];
					for (var x=0; x<numbersArr.length; x++) {
						if (numbersArr[x] == perDayDataArr[j][1][k+1][1][x] && numbersArr[x] == perDayDataArr[j][1][k+2][1][x]) {	// 出现三连
							pairIndex = x;
							thirdPairRowArr.push([k + 2, numbersArr[x]]);
							
							break;	// 只处理每条数据的第一个对子
						}
					}
				}
				if (isBegin) {
					if (thirdPairIndex > -1) {
						//从追车队列中除去
						carsList = deepClone(CARS_LIST);
						var ind = -1;
						for (var a=0; a<carsList.length; a++) {
							if (thirdPairRowArr[0][1] == carsList[a]) {
								ind = a;
							}
						}
						carsList.splice(ind, 1);
						
						var inde = -1;
						for (var b=0; b<carsList.length; b++) {
							if (perDayDataArr[mainDayIndex][1][k][1][thirdPairIndex] == carsList[b]) {
								inde = b;
							}
						}
						if (inde > -1) {	// 查找主道对应位置是否出现追车队列中的车号
							isCorrect = true;
						} else {
							isCorrect = false;
							mistakeNum++;
							//将猜错的车号压入每日的数组
							//perDayMistakeCars[时间（HH:MM）, 车号]
							perDayMistakeCars.push([perDayDataArr[j][1][k][2].split(" ")[1], thirdPairRowArr[0][1]]);
						}
					}
				} else isBegin = true;
				
				for (var f=0; f<perDayDataArr[mainDayIndex][1][k][1].length; f++) {
					if (f == thirdPairIndex && isBegin && ind > -1)	{	//当前三连所在位置
						thirdPairRowArr.splice(0, 1);
					}
				}
				
				if (nextPairIndex > -1) {
					thirdPairIndex = nextPairIndex;
				} else {
					thirdPairIndex = -1;
				}
				
				if (pairIndex > -1) {
					nextPairIndex = pairIndex;
				} else {
					nextPairIndex = -1;
				}
				
				//将每日的错误车号填入
				for (var q=0; q<perDayMistakeCars.length; q++) {	//遍历每日错误车号
					var flagg = false;
					for (var w=0; w<mistakeCars.length; w++) {
						if (mistakeCars[w][0] == perDayMistakeCars[q][0]) {	//时间相同
							flagg = true;
							var flag = false;
							for (var e=0; e<mistakeCars[w][1].length; e++) {
								if (mistakeCars[w][1][e] == perDayMistakeCars[q][1]) {
									flag = true;
									break;
								}
							}
							if (!flag) mistakeCars[w][1].push(perDayMistakeCars[q][1]);
							
							break;
						}
					}
					if (!flagg) mistakeCars.push([perDayMistakeCars[q][0], [perDayMistakeCars[q][1]]]);
				}
			}
			
			var mistakeCarsHtml = "";
			for (var r=0; r<perDayMistakeCars.length; r++) {
				mistakeCarsHtml += "【" + perDayMistakeCars[r][0] + "】-[" + perDayMistakeCars[r][1] + "]; "
			}
			
			trHtml = "<tr><td class='date'>"  + perDayDataArr[j][0] +"</td>" 
					+ "<td>"  + mistakeNum +"</td>"
					+ "<td>"  + mistakeCarsHtml +"</td></tr>";
		}
		
		if ($("#perDayTable tbody tr").length > 0) {
			$("#perDayTable tbody tr:eq(0)").before(trHtml);
		} else {
			$("#perDayTable tbody").append(trHtml);
		}
	}
};

/**
 * 初始化对比数据表格
 * endDay：结束日期，格式为：YYYY/MM/DD，如“2017/01/01”
 */
var initCompareTable = function(endDay) {
	if (!($.trim(endDay).length > 0)) alert("initCompareTable 日期参数有误。");
	
	$("#compareTable tbody").html("");
	
	var mainDay = [];	//主道数据
	//找到主道数据
	for(var i=0; i<perDayDataArr.length; i++) {
		if (endDay == perDayDataArr[i][0]) {
			mainDay = perDayDataArr[i];
			break;
		}
	}
	
	//主道、配道比对
// perDay：[rowData[0], numbersArr, rowData[2]]
//	var perDay = perDayDataArr[j];
	var sumMoney = $("#originalSumMoneySpan").text();
	var backDropNum = $("#backDropNumSpan").text();
	var backNum = $("#backNumSpan").text();
	var bgColor = "";
	var carsList = [];	//当前追车队列
	
	for (var k=0; k<mainDay[1].length; k++) {	// 当天所有数据
		var pairIndex = -1;	//对子所在位置
		var isCorrect = false;	//主道是否猜对
		var profit = 0;	//每条获利
		var profitSum = 0;	//每条获利总和
		var backMoney = $("#backMoneySpan").text();	//每条下注
		var backMoneySum = 0;	//每条下注总和
//		backMoney = parseInt($("#backMoneySpan").text());
		
		if (598717 == mainDay[1][k][0]) 
			mainDay[1][k][0];
		
		//errorCars[车号，车道位置]
		var errorCars = [];
		var flag = false;
		for (var m=0; m<mistakeCars.length; m++) {
			if (mistakeCars[m][0] == mainDay[1][k][2].split(" ")[1]) {	//比较时间
				flag = true;
				
				for (var n=0; n<mistakeCars[m][1].length; n++) {	//遍历所有错误车号
					for (var v=0; v<mainDay[1][k-1][1].length; v++) {	//查找上一条该车号所在车道
						if (mainDay[1][k-1][1][v] == mistakeCars[m][1][n]) {
							errorCars.push([mainDay[1][k-1][1][v], v]);
							
							// 将错误的车号从追车队列中除去
							carsList = deepClone(CARS_LIST);
							var ind = -1;
							for (var a=0; a<carsList.length; a++) {
								if (mainDay[1][k-1][1][v] == carsList[a]) {
									ind = a;
								}
							}
							carsList.splice(ind, 1);
							
							var inde = -1;
							for (var c=0; c<carsList.length; c++) {
								if (mainDay[1][k][1][v] == carsList[c]) {
									inde = c;
									break;
								}
							}
							
							backMoneySum += parseInt(backMoney);
							sumMoney -= backMoney;	// 扣除下注金额
							
							if (inde > -1) {	//猜中
								profit = parseInt(backMoney*1 / carsList.length) * LOTTERY_TIMES;
								sumMoney += profit;
								profitSum += profit;
								
								isCorrect = true;
							} else {	//猜错
								profit = 0;
								isCorrect = false;
							}
							
							break;
						}
					}
				}
			}
		}
		if (!flag) {
			backMoney = 0;
			profit = 0;
		}
		
		//拼接页面效果
		var numbersHtml = "";
		for (var f=0; f<mainDay[1][k][1].length; f++) {
			var htm = "<td><span class='mainNumberBGColor'>" + mainDay[1][k][1][f] + "</span></td>";
			
			for (var g=0; g<errorCars.length; g++) {
				if (f == errorCars[g][1]) {	//车道匹配
					if (isCorrect)
						htm = "<td><span class='winBGColor'>" + mainDay[1][k][1][f] + "</span></td>";
					else htm = "<td><span class='errorBGColor'>" + mainDay[1][k][1][f] + "</span></td>";
				}
			}
			numbersHtml += htm;
		}
		
		var errorCarsHtml = "";
		for (var h=0; h<errorCars.length; h++) {
			errorCarsHtml += "[" + errorCars[h][0] + "]车-" + (errorCars[h][1]*1+1) + "道;"
		}
		
		var trHtml = "<tr>"
				+ "<td>"  + mainDay[1][k][0] +"</td>" 
				+ "<td><span class='time'>"  + mainDay[1][k][2].split(" ")[1] +"<span></td>" 
				+ numbersHtml
				+ "<td>"  + errorCarsHtml +"</td>" 
				+ "<td class='" + bgColor + "'>" + backMoneySum + "</td>" 
				+ "<td class='" + bgColor + "'>" + profitSum + "</td>"
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