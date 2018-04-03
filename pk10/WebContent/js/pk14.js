/**
 * 常量
 */
//中奖系数
var LOTTERY_TIMES = 10;
//全部追车队列
var CARS_LIST = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];;

/**
 * 变量
 */
//大量数据（所有数据）
var bigDataArr = [];
//大量数据（每日数据）
var perDayDataArr = [];
//当前生效规则（默认为对子）
//1. 对子（追车号）；2. 三连；3. 对子（追车道）
//var currRule = 1;
//var currRule = 2;
var currRule = 3;
//当前生效天数（默认为3天）
var currRulesDays = 3;
//var currRulesDays = 5;
//全部猜错的车号数据
//mistakeCars[时间, [车号]]
var mistakeCars = [];
//需下注的车号数据
//currRule = 1&2；backCars[时间, [车号]]
//currRule = 3；backCars[时间, [车道, 车号]]
var backCars = [];


//*****************************************************************************************************************

/**
 * 录入历史数据
 */
var inputHistroyData = function() {
	//清理旧数据
	bigDataArr = [];
	perDayDataArr = [];
	mistakeCars = [];
	backCars = [];
	
	//格式化历史数据
	var data = $("#histroyRecordTextArea").val();	//全量历史数据字符串
	
	if($.trim(data).length == 0) {
		alert("请录入历史数据");
		return;
	}
	
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
				
				if (currRule == 1) {	//对子（追车号）
					initDoubleNumPerDayTable(perDayDataArr[0][0], $(this).val());
					initDoubleNumCompareTable($(this).val());
				} else if (currRule == 2) {	//三连
					initTriplePerDayTable(perDayDataArr[0][0], $(this).val());
					initTripleCompareTable($(this).val());
				} else if (currRule == 3) {	//对子（追车道）
					initDoubleRoadPerDayTable(perDayDataArr[0][0], $(this).val());
					initDoubleRoadCompareTable($(this).val());
				} else {
					
				}
			}).val(perDayDataArr[perDayDataArr.length-1][0]);
			
			//前一天
			$("#getBeforeDayButton").click(function() {
				var day = $("select.mainDayDateSelect").val();
				for (var c=0; c<perDayDataArr.length; c++) {
					if (day == perDayDataArr[c][0]) {
						if (perDayDataArr[c-1] && perDayDataArr[c-1].length > 0) {
							$("#compareTable span.mainDay").text(perDayDataArr[c-1][0]);
							$("select.mainDayDateSelect").val(perDayDataArr[c-1][0]);
							
							if (currRule == 1) {	//对子（追车号）
								initDoubleNumPerDayTable(perDayDataArr[0][0], perDayDataArr[c-1][0]);
								initDoubleNumCompareTable(perDayDataArr[c-1][0]);
							} else if (currRule == 2) {	//三连
								initTriplePerDayTable(perDayDataArr[0][0], perDayDataArr[c-1][0]);
								initTripleCompareTable(perDayDataArr[c-1][0]);
							} else if (currRule == 3) {	//对子（追车道）
								initDoubleRoadPerDayTable(perDayDataArr[0][0], perDayDataArr[c-1][0]);
								initDoubleRoadCompareTable(perDayDataArr[c-1][0]);
							} else {
								
							}
							break;
						} else {
							alert("没有更早的数据了。");
							return;
						}
					}
				}
			});
			
			//后一天
			$("#getNextDayButton").click(function() {
				var day = $("select.mainDayDateSelect").val();
				for (var c=0; c<perDayDataArr.length; c++) {
					if (day == perDayDataArr[c][0]) {
						if (perDayDataArr[c+1] && perDayDataArr[c+1].length > 0) {
							$("#compareTable span.mainDay").text(perDayDataArr[c+1][0]);
							$("select.mainDayDateSelect").val(perDayDataArr[c+1][0]);

							if (currRule == 1) {	//对子（追车号）
								initDoubleNumPerDayTable(perDayDataArr[0][0], perDayDataArr[c+1][0]);
								initDoubleNumCompareTable(perDayDataArr[c+1][0]);
							} else if (currRule == 2) {	//三连
								initTriplePerDayTable(perDayDataArr[0][0], perDayDataArr[c+1][0]);
								initTripleCompareTable(perDayDataArr[c+1][0]);
							} else if (currRule == 3) {	//对子（追车道）
								initDoubleRoadPerDayTable(perDayDataArr[0][0], perDayDataArr[c+1][0]);
								initDoubleRoadCompareTable(perDayDataArr[c+1][0]);
							} else {
								
							}
							break;
						} else {
							alert("没有更新的数据了。");
							return;
						}
					}
				}
			});
		}
	}
	
	//显示第一天和最后一天数据
	$("#compareTable span.mainDay").text(perDayDataArr[perDayDataArr.length-1][0]);
	$("select.mainDayDateSelect").val(perDayDataArr[perDayDataArr.length-1][0]);
	if (currRule == 1) {	//对子（追车号）
		initDoubleNumPerDayTable(perDayDataArr[0][0], perDayDataArr[perDayDataArr.length-1][0]);
		initDoubleNumCompareTable(perDayDataArr[perDayDataArr.length-1][0]);
	} else if (currRule == 2) {	//三连
		initTriplePerDayTable(perDayDataArr[0][0], perDayDataArr[perDayDataArr.length-1][0]);
		initTripleCompareTable(perDayDataArr[perDayDataArr.length-1][0]);
	} else if (currRule == 3) {	//对子（追车道）
		initDoubleRoadPerDayTable(perDayDataArr[0][0], perDayDataArr[perDayDataArr.length-1][0]);
		initDoubleRoadCompareTable(perDayDataArr[perDayDataArr.length-1][0]);
	} else {
		;
	}
}

/**
 * 初始化三连每日统计数据表格
 * startDay：开始日期，格式为：YYYY/MM/DD，如“2017/01/01”
 * endDay：结束日期，格式为：YYYY/MM/DD，如“2017/01/01”
 */
var initTriplePerDayTable = function(startDay, endDay) {
//	if (!($.trim(startDay).length > 0 && $.trim(endDay).length > 0)) alert("initPerDayTable 日期参数有误。");

	$("#perDayTable tbody").html("");
	mistakeCars = [];	//清理
	backCars = [];
	
	var compareDayIndex = -1;		//配道数据序号
	var mainDayIndex = -1;	//主道数据序号
	//找到主道、配道数据
	for(var i=perDayDataArr.length-1; i>=0; i--) {
		if (endDay == perDayDataArr[i][0]) mainDayIndex = i;	//用倒数第一天作为比较基准
		if (startDay == perDayDataArr[i][0]) {
			compareDayIndex = i;
			if (compareDayIndex <= mainDayIndex - currRulesDays) {	//仅统计截止时间前N天的数据
				compareDayIndex = mainDayIndex - currRulesDays;
			}
		}
		
		if (compareDayIndex > 0 && mainDayIndex > 0) break;
	}
	
	//开始逐天对比
	for (var j=compareDayIndex; j<=mainDayIndex; j++) {
		//主道、配道比对
		var trHtml = "";
		if (j == mainDayIndex) {	//主道
			trHtml += "<tr><td>" + perDayDataArr[j][0] + "</td><td>主道</td><td>-</td><td>-</td><td>-</td><td>-</td></tr>";
		} else {	//以前几天数据
			//主道、配道比对
			var isBegin = false;
			var mistakeNum = 0;	// 错误次数
			var mn = 0;	//当前连错次数
			var mi = 0;	//
			var maxCorrectMistakeNum = 0;	//出现最大连中次数之前的连错次数
			var maxCorrectMistakeIssue = 0;	//出现最大连中次数之前的连错最后一条的期号
			var correctNum = 0;	// 猜对次数
			var maxCorrectNum = 0;	//最大连中次数
			var maxCorrectIssue = 0;	//最大连中次数的最后一次期号
			var cn = 0;	//当前连中次数
			var nextPairIndex = -1;	//二连下注位置
			var thirdPairIndex = -1;	//三连下注位置
			//三连下注行，可能在三条数据中同时出现多个下注点
			//thirdPairRowArr[行数][车号]
			var thirdPairRowArr = [];	
			var carsList = [];	//当前追车队列
			var perDayMistakeCars = [];	//猜错的车号队列
			var perDayBackCars = [];	//需下注的车号队列
			
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
							//将需下注的车号压入数组
							//perDayBackCars[时间（HH:MM）, 车号]
							perDayBackCars.push([perDayDataArr[j][1][k+2][2].split(" ")[1], numbersArr[x]]);
							
							break;	// 只处理每条数据的第一个三连
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
							correctNum++;
							cn++;
							if (maxCorrectNum < cn) {
								maxCorrectNum = cn;
								maxCorrectIssue = perDayDataArr[j][1][k][0];
								
								maxCorrectMistakeNum = mn;
								maxCorrectMistakeIssue = mi;
							}
//							mn = 0;
						} else {
							isCorrect = false;
							mistakeNum++;
							if (cn > 0) mn = 0;
							cn = 0;
							mn++;
							mi = perDayDataArr[j][1][k][0];
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
				
				//将每日的需下注车号填入
				for (var q=0; q<perDayBackCars.length; q++) {	//遍历每日需下注车号
					var flagg = false;
					for (var w=0; w<backCars.length; w++) {
						if (backCars[w][0] == perDayBackCars[q][0]) {	//时间相同
							flagg = true;
							var flag = false;
							for (var e=0; e<backCars[w][1].length; e++) {
								if (backCars[w][1][e] == perDayBackCars[q][1]) {
									flag = true;
									break;
								}
							}
							if (!flag) backCars[w][1].push(perDayBackCars[q][1]);
							
							break;
						}
					}
					if (!flagg) backCars.push([perDayBackCars[q][0], [perDayBackCars[q][1]]]);
				}
			}
			
			var mistakeCarsHtml = "";
			for (var r=0; r<perDayMistakeCars.length; r++) {
				mistakeCarsHtml += "【" + perDayMistakeCars[r][0] + "】-[" + perDayMistakeCars[r][1] + "]; "
			}
			
			var backCarsHtml = "";
			for (var r=0; r<perDayBackCars.length; r++) {
				backCarsHtml += "【" + perDayBackCars[r][0] + "】-[" + perDayBackCars[r][1] + "]; "
			}
			
			trHtml = "<tr><td class='date'>"  + perDayDataArr[j][0] +"</td>" 
					+ "<td>"  + correctNum +"</td>"
					+ "<td>"  + mistakeNum +"</td>"
					+ "<td>"  + maxCorrectNum + "【" + maxCorrectIssue + "】</td>"
					+ "<td>"  + maxCorrectMistakeNum + "【" + maxCorrectMistakeIssue + "】</td>"
//					+ "<td>"  + mistakeCarsHtml +"</td></tr>";
					+ "<td>"  + backCarsHtml +"</td></tr>";
		}
		
		if ($("#perDayTable tbody tr").length > 0) {
			$("#perDayTable tbody tr:eq(0)").before(trHtml);
		} else {
			$("#perDayTable tbody").append(trHtml);
		}
	}
};

/**
 * 初始化三连对比数据表格
 * endDay：结束日期，格式为：YYYY/MM/DD，如“2017/01/01”
 */
var initTripleCompareTable = function(endDay) {
//	if (!($.trim(endDay).length > 0)) alert("initCompareTable 日期参数有误。");
	
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
	var sumMoney = $("#originalSumMoneySpan").text();
	var bgColor = "";
	var carsList = [];	//当前追车队列
	
	for (var k=0; k<mainDay[1].length; k++) {	// 当天所有数据
		var pairIndex = -1;	//对子所在位置
		var isCorrect = false;	//主道是否猜对
		var profit = 0;	//每条获利
		var profitSum = 0;	//每条获利总和
		var backMoney = $("#backMoneySpan").text();	//每条下注
		var backMoneySum = 0;	//每条下注总和
		
		//errorCars[车号，车道位置]
		var errorCars = [];
		var flag = false;
//		for (var m=0; m<mistakeCars.length; m++) {
//			if (mistakeCars[m][0] == mainDay[1][k][2].split(" ")[1]) {	//比较时间
//				flag = true;
//				
//				for (var n=0; n<mistakeCars[m][1].length; n++) {	//遍历所有错误车号
//					for (var v=0; v<mainDay[1][k-1][1].length; v++) {	//查找上一条该车号所在车道
//						if (mainDay[1][k-1][1][v] == mistakeCars[m][1][n]) {
//							errorCars.push([mainDay[1][k-1][1][v], v]);
//							
//							// 将错误的车号从追车队列中除去
//							carsList = deepClone(CARS_LIST);
//							var ind = -1;
//							for (var a=0; a<carsList.length; a++) {
//								if (mainDay[1][k-1][1][v] == carsList[a]) {
//									ind = a;
//								}
//							}
//							carsList.splice(ind, 1);
//							
//							var inde = -1;
//							for (var c=0; c<carsList.length; c++) {
//								if (mainDay[1][k][1][v] == carsList[c]) {
//									inde = c;
//									break;
//								}
//							}
//							
//							backMoneySum += parseInt(backMoney);
//							sumMoney -= backMoney;	// 扣除下注金额
//							
//							if (inde > -1) {	//猜中
//								profit = parseInt(backMoney*1 / carsList.length) * LOTTERY_TIMES;
//								sumMoney += profit;
//								profitSum += profit;
//								
//								isCorrect = true;
//							} else {	//猜错
//								profit = 0;
//								isCorrect = false;
//							}
//							
//							break;
//						}
//					}
//				}
//			}
//		}
		for (var m=0; m<backCars.length; m++) {
			if (backCars[m][0] == mainDay[1][k][2].split(" ")[1]) {	//比较时间
				flag = true;
				
				for (var n=0; n<backCars[m][1].length; n++) {	//遍历所有错误车号
					for (var v=0; v<mainDay[1][k-1][1].length; v++) {	//查找上一条该车号所在车道
						if (mainDay[1][k-1][1][v] == backCars[m][1][n]) {
//							errorCars.push([mainDay[1][k-1][1][v], v]);
							
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
								
//								isCorrect = true;
								errorCars.push([mainDay[1][k-1][1][v], v, true]);
							} else {	//猜错
								profit = 0;
//								isCorrect = false;
								errorCars.push([mainDay[1][k-1][1][v], v, false]);
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
//					if (isCorrect)
					if (errorCars[g][2])
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
 * 初始化对子（追车号）每日统计数据表格
 * startDay：开始日期，格式为：YYYY/MM/DD，如“2017/01/01”
 * endDay：结束日期，格式为：YYYY/MM/DD，如“2017/01/01”
 */
var initDoubleNumPerDayTable = function(startDay, endDay) {
	$("#perDayTable tbody").html("");
	mistakeCars = [];	//清理
	backCars = [];
	
	var compareDayIndex = -1;		//配道数据序号
	var mainDayIndex = -1;	//主道数据序号
	//找到主道、配道数据
	for(var i=perDayDataArr.length-1; i>=0; i--) {
		if (endDay == perDayDataArr[i][0]) mainDayIndex = i;	//用倒数第一天作为比较基准
		if (startDay == perDayDataArr[i][0]) {
			compareDayIndex = i;
			if (compareDayIndex <= mainDayIndex - currRulesDays) {	//仅统计截止时间前N天的数据
				compareDayIndex = mainDayIndex - currRulesDays;
			}
		}
		
		if (compareDayIndex > 0 && mainDayIndex > 0) break;
	}
	
	//开始逐天对比
	for (var j=compareDayIndex; j<=mainDayIndex; j++) {
		//主道、配道比对
		var trHtml = "";
		if (j == mainDayIndex) {	//主道
			trHtml += "<tr><td>" + perDayDataArr[j][0] + "</td><td>主道</td><td>-</td><td>-</td><td>-</td><td>-</td></tr>";
		} else {	//以前几天数据
			//主道、配道比对
			var isBegin = false;
			var mistakeNum = 0;	// 错误次数
			var mn = 0;	//当前连错次数
			var mi = 0;	//
			var maxCorrectMistakeNum = 0;	//出现最大连中次数之前的连错次数
			var maxCorrectMistakeIssue = 0;	//出现最大连中次数之前的连错最后一条的期号
			var correctNum = 0;	// 猜对次数
			var maxCorrectNum = 0;	//最大连中次数
			var maxCorrectIssue = 0;	//最大连中次数的最后一次期号
			var cn = 0;	//当前连中次数
			var nextPairIndex = -1;	//二连下注位置
			var carsList = [];	//当前追车队列
			var perDayMistakeCars = [];	//猜错的车号队列
			var perDayBackCars = [];
			
			for (var k=0; k<perDayDataArr[j][1].length; k++) {	// 当天所有数据
				var pairIndex = -1;	//对子所在位置
				var carsList = [];	//当前追车队列
				var isCorrect = false;	//主道是否猜对
				
				if (k <= perDayDataArr[j][1].length - 2) {	//只处理到倒数第二条
					var numbersArr = perDayDataArr[j][1][k][1];
					for (var x=0; x<numbersArr.length; x++) {
						if (numbersArr[x] == perDayDataArr[j][1][k+1][1][x]) {	// 出现对子
							pairIndex = x;
							perDayBackCars.push([perDayDataArr[j][1][k+1][2].split(" ")[1], numbersArr[x]]);
							
							break;	// 只处理每条数据的第一个三连
						}
					}
				}
				if (isBegin) {
					if (nextPairIndex > -1) {
						//从追车队列中除去
						carsList = deepClone(CARS_LIST);
						var ind = -1;
						for (var a=0; a<carsList.length; a++) {
							if (perDayDataArr[j][1][k][1][nextPairIndex] == carsList[a]) {
								ind = a;
							}
						}
						carsList.splice(ind, 1);
						
						var inde = -1;
						for (var b=0; b<carsList.length; b++) {
							if (perDayDataArr[mainDayIndex][1][k][1][nextPairIndex] == carsList[b]) {
								inde = b;
							}
						}
						
						if (inde > -1) {	// 查找主道对应位置是否出现追车队列中的车号
							isCorrect = true;
							correctNum++;
							cn++;
							if (maxCorrectNum < cn) {
								maxCorrectNum = cn;
								maxCorrectIssue = perDayDataArr[j][1][k][0];
								
								maxCorrectMistakeNum = mn;
								maxCorrectMistakeIssue = mi;
							}
//							mn = 0;
						} else {
							isCorrect = false;
							mistakeNum++;
							if (cn > 0) mn = 0;
							cn = 0;
							mn++;
							mi = perDayDataArr[j][1][k][0];
							//将猜错的车号压入每日的数组
							//perDayMistakeCars[时间（HH:MM）, 车号]
							perDayMistakeCars.push([perDayDataArr[j][1][k][2].split(" ")[1], perDayDataArr[mainDayIndex][1][k][1][nextPairIndex]]);
						}
					}
				} else isBegin = true;
				
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
				

				//将每日的需下注车号填入
				for (var q=0; q<perDayBackCars.length; q++) {	//遍历每日需下注车号
					var flagg = false;
					for (var w=0; w<backCars.length; w++) {
						if (backCars[w][0] == perDayBackCars[q][0]) {	//时间相同
							flagg = true;
							var flag = false;
							for (var e=0; e<backCars[w][1].length; e++) {
								if (backCars[w][1][e] == perDayBackCars[q][1]) {
									flag = true;
									break;
								}
							}
							if (!flag) backCars[w][1].push(perDayBackCars[q][1]);
							
							break;
						}
					}
					if (!flagg) backCars.push([perDayBackCars[q][0], [perDayBackCars[q][1]]]);
				}
			}
			
			var mistakeCarsHtml = "";
			for (var r=0; r<perDayMistakeCars.length; r++) {
				mistakeCarsHtml += "【" + perDayMistakeCars[r][0] + "】-[" + perDayMistakeCars[r][1] + "]; "
			}

			var backCarsHtml = "";
			for (var r=0; r<perDayBackCars.length; r++) {
				backCarsHtml += "【" + perDayBackCars[r][0] + "】-[" + perDayBackCars[r][1] + "]; "
			}
			
			trHtml = "<tr><td class='date'>"  + perDayDataArr[j][0] +"</td>" 
					+ "<td>"  + correctNum +"</td>"
					+ "<td>"  + mistakeNum +"</td>"
					+ "<td>"  + maxCorrectNum + "【" + maxCorrectIssue + "】</td>"
					+ "<td>"  + maxCorrectMistakeNum + "【" + maxCorrectMistakeIssue + "】</td>"
//					+ "<td>"  + mistakeCarsHtml +"</td></tr>";
					+ "<td>"  + backCarsHtml +"</td></tr>";
		}
		
		if ($("#perDayTable tbody tr").length > 0) {
			$("#perDayTable tbody tr:eq(0)").before(trHtml);
		} else {
			$("#perDayTable tbody").append(trHtml);
		}
	}
};

/**
 * 初始化对子（追车号）对比数据表格
 * endDay：结束日期，格式为：YYYY/MM/DD，如“2017/01/01”
 */
var initDoubleNumCompareTable = function(endDay) {
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
	var sumMoney = $("#originalSumMoneySpan").text();
	var bgColor = "";
	var carsList = [];	//当前追车队列
	
	for (var k=0; k<mainDay[1].length; k++) {	// 当天所有数据
		var pairIndex = -1;	//对子所在位置
//		var isCorrect = false;	//主道是否猜对
		var profit = 0;	//每条获利
		var profitSum = 0;	//每条获利总和
		var backMoney = $("#backMoneySpan").text();	//每条下注
		var backMoneySum = 0;	//每条下注总和
		
		//errorCars[车号,车道位置,对错]
		var errorCars = [];
		var flag = false;
//		for (var m=0; m<mistakeCars.length; m++) {
//			if (mistakeCars[m][0] == mainDay[1][k][2].split(" ")[1]) {	//比较时间
//				flag = true;
//				
//				for (var n=0; n<mistakeCars[m][1].length; n++) {	//遍历所有错误车号
//					for (var v=0; v<mainDay[1][k-1][1].length; v++) {	//查找上一条该车号所在车道
//						if (mainDay[1][k-1][1][v] == mistakeCars[m][1][n]) {
//							errorCars.push([mainDay[1][k-1][1][v], v]);
//							
//							// 将错误的车号从追车队列中除去
//							carsList = deepClone(CARS_LIST);
//							var ind = -1;
//							for (var a=0; a<carsList.length; a++) {
//								if (mainDay[1][k-1][1][v] == carsList[a]) {
//									ind = a;
//								}
//							}
//							carsList.splice(ind, 1);
//							
//							var inde = -1;
//							for (var c=0; c<carsList.length; c++) {
//								if (mainDay[1][k][1][v] == carsList[c]) {
//									inde = c;
//									break;
//								}
//							}
//							
//							backMoneySum += parseInt(backMoney);
//							sumMoney -= backMoney;	// 扣除下注金额
//							
//							if (inde > -1) {	//猜中
//								profit = parseInt(backMoney*1 / carsList.length) * LOTTERY_TIMES;
//								sumMoney += profit;
//								profitSum += profit;
//								
//								isCorrect = true;
//							} else {	//猜错
//								profit = 0;
//								isCorrect = false;
//							}
//							
//							break;
//						}
//					}
//				}
//			}
//		}
		for (var m=0; m<backCars.length; m++) {
			if (backCars[m][0] == mainDay[1][k][2].split(" ")[1]) {	//比较时间
				flag = true;
				
				for (var n=0; n<backCars[m][1].length; n++) {	//遍历所有错误车号
					for (var v=0; v<mainDay[1][k-1][1].length; v++) {	//查找上一条该车号所在车道
						if (mainDay[1][k-1][1][v] == backCars[m][1][n]) {
//							errorCars.push([mainDay[1][k-1][1][v], v]);
							
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
								
//								isCorrect = true;
								errorCars.push([mainDay[1][k-1][1][v], v, true]);
							} else {	//猜错
								profit = 0;
//								isCorrect = false;
								errorCars.push([mainDay[1][k-1][1][v], v, false]);
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
//					if (isCorrect)
					if (errorCars[g][2])
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
 * 初始化对子（追车道）每日统计数据表格
 * startDay：开始日期，格式为：YYYY/MM/DD，如“2017/01/01”
 * endDay：结束日期，格式为：YYYY/MM/DD，如“2017/01/01”
 */
var initDoubleRoadPerDayTable = function(startDay, endDay) {
	$("#perDayTable tbody").html("");
	mistakeCars = [];	//清理
	backCars = [];
	
	var compareDayIndex = -1;		//配道数据序号
	var mainDayIndex = -1;	//主道数据序号
	//找到主道、配道数据
	for(var i=perDayDataArr.length-1; i>=0; i--) {
		if (endDay == perDayDataArr[i][0]) mainDayIndex = i;	//用倒数第一天作为比较基准
		if (startDay == perDayDataArr[i][0]) {
			compareDayIndex = i;
			if (compareDayIndex <= mainDayIndex - currRulesDays) {	//仅统计截止时间前N天的数据
				compareDayIndex = mainDayIndex - currRulesDays;
			}
		}
		
		if (compareDayIndex > 0 && mainDayIndex > 0) break;
	}
	
	//开始逐天对比
	for (var j=compareDayIndex; j<=mainDayIndex; j++) {
		//主道、配道比对
		var trHtml = "";
		if (j == mainDayIndex) {	//主道
			trHtml += "<tr><td>" + perDayDataArr[j][0] + "</td><td>主道</td><td>-</td><td>-</td><td>-</td><td>-</td></tr>";
		} else {	//以前几天数据
			//主道、配道比对
			var isBegin = false;
			var mistakeNum = 0;	// 错误次数
			var mn = 0;	//当前连错次数
			var mi = 0;	//
			var maxCorrectMistakeNum = 0;	//出现最大连中次数之前的连错次数
			var maxCorrectMistakeIssue = 0;	//出现最大连中次数之前的连错最后一条的期号
			var correctNum = 0;	// 猜对次数
			var maxCorrectNum = 0;	//最大连中次数
			var maxCorrectIssue = 0;	//最大连中次数的最后一次期号
			var cn = 0;	//当前连中次数
			var nextPairIndex = -1;	//二连下注位置
			var carsList = [];	//当前追车队列
			var perDayMistakeCars = [];	//猜错的车号队列
			var perDayBackCars = [];
			
			for (var k=0; k<perDayDataArr[j][1].length; k++) {	// 当天所有数据
				var pairIndex = -1;	//对子所在位置
				var carsList = [];	//当前追车队列
				var isCorrect = false;	//主道是否猜对
				
				if (k <= perDayDataArr[j][1].length - 2) {	//只处理到倒数第二条
					var numbersArr = perDayDataArr[j][1][k][1];
					for (var x=0; x<numbersArr.length; x++) {
						if (numbersArr[x] == perDayDataArr[j][1][k+1][1][x]) {	// 出现对子
							pairIndex = x;
//							perDayBackCars.push([perDayDataArr[j][1][k+1][2].split(" ")[1], numbersArr[x]]);
							perDayBackCars.push([perDayDataArr[j][1][k+1][2].split(" ")[1], x+1, numbersArr[x]]);
							
							break;	// 只处理每条数据的第一个三连
						}
					}
				}
				if (isBegin) {
					if (nextPairIndex > -1) {
						//从追车队列中除去
						carsList = deepClone(CARS_LIST);
						var ind = -1;
						for (var a=0; a<carsList.length; a++) {
							if (perDayDataArr[j][1][k][1][nextPairIndex] == carsList[a]) {
								ind = a;
							}
						}
						carsList.splice(ind, 1);
						
						var inde = -1;
						for (var b=0; b<carsList.length; b++) {
							if (perDayDataArr[mainDayIndex][1][k][1][nextPairIndex] == carsList[b]) {
								inde = b;
							}
						}
						
						if (inde > -1) {	// 查找主道对应位置是否出现追车队列中的车号
							isCorrect = true;
							correctNum++;
							cn++;
							if (maxCorrectNum < cn) {
								maxCorrectNum = cn;
								maxCorrectIssue = perDayDataArr[j][1][k][0];
								
								maxCorrectMistakeNum = mn;
								maxCorrectMistakeIssue = mi;
							}
//							mn = 0;
						} else {
							isCorrect = false;
							mistakeNum++;
							if (cn > 0) mn = 0;
							cn = 0;
							mn++;
							mi = perDayDataArr[j][1][k][0];
							//将猜错的车号压入每日的数组
							//perDayMistakeCars[时间（HH:MM）, 车号]
							perDayMistakeCars.push([perDayDataArr[j][1][k][2].split(" ")[1], perDayDataArr[mainDayIndex][1][k][1][nextPairIndex]]);
						}
					}
				} else isBegin = true;
				
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
				

				//将每日的需下注车道&车号填入
				for (var q=0; q<perDayBackCars.length; q++) {	//遍历每日需下注车道
					var flagg = false;
					for (var w=0; w<backCars.length; w++) {
						if (backCars[w][0] == perDayBackCars[q][0]) {	//时间相同
							flagg = true;
							var flag = false;
							for (var e=0; e<backCars[w][1].length; e++) {
//								if (backCars[w][1][e] == perDayBackCars[q][1]) {	//当已有该车道数据时，不再压入（仅取最早一个数据）
								if (backCars[w][1][e][0] == perDayBackCars[q][1]) {	//当已有该车道数据时，不再压入（仅取最早一个数据）
									flag = true;
									break;
								}
							}
//							if (!flag) backCars[w][1].push(perDayBackCars[q][1]);
							if (!flag) backCars[w][1].push([perDayBackCars[q][1], perDayBackCars[q][2]]);
							
							break;
						}
					}
//					if (!flagg) backCars.push([perDayBackCars[q][0], [perDayBackCars[q][1]]]);
					if (!flagg) backCars.push([perDayBackCars[q][0], [[perDayBackCars[q][1], perDayBackCars[q][2]]]]);
				}
			}
			
			var mistakeCarsHtml = "";
			for (var r=0; r<perDayMistakeCars.length; r++) {
				mistakeCarsHtml += "【" + perDayMistakeCars[r][0] + "】-[" + perDayMistakeCars[r][1] + "]; "
			}

			var backCarsHtml = "";
			for (var r=0; r<perDayBackCars.length; r++) {
//				backCarsHtml += "【" + perDayBackCars[r][0] + "】-[" + perDayBackCars[r][2] + "]-(" + perDayBackCars[r][1] + "); "
				backCarsHtml += "【" + perDayBackCars[r][0] + "】-" + perDayBackCars[r][1] + "道" + perDayBackCars[r][2] + "; "
			}
			
			trHtml = "<tr><td class='date'>"  + perDayDataArr[j][0] +"</td>" 
					+ "<td>"  + correctNum +"</td>"
					+ "<td>"  + mistakeNum +"</td>"
					+ "<td>"  + maxCorrectNum + "【" + maxCorrectIssue + "】</td>"
					+ "<td>"  + maxCorrectMistakeNum + "【" + maxCorrectMistakeIssue + "】</td>"
//					+ "<td>"  + mistakeCarsHtml +"</td></tr>";
					+ "<td>"  + backCarsHtml +"</td></tr>";
		}
		
		if ($("#perDayTable tbody tr").length > 0) {
			$("#perDayTable tbody tr:eq(0)").before(trHtml);
		} else {
			$("#perDayTable tbody").append(trHtml);
		}
	}
};

/**
 * 初始化对子（追车道）对比数据表格
 * endDay：结束日期，格式为：YYYY/MM/DD，如“2017/01/01”
 */
var initDoubleRoadCompareTable = function(endDay) {
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
	var sumMoney = $("#originalSumMoneySpan").text();
	var bgColor = "";
	var carsList = [];	//当前追车队列
	
	for (var k=0; k<mainDay[1].length; k++) {	// 当天所有数据
		var pairIndex = -1;	//对子所在位置
//		var isCorrect = false;	//主道是否猜对
		var profit = 0;	//每条获利
		var profitSum = 0;	//每条获利总和
		var backMoney = $("#backMoneySpan").text();	//每条下注
		var backMoneySum = 0;	//每条下注总和
		
		//errorCars[车号,车道位置,对错]
		var errorCars = [];
		var flag = false;
//		for (var m=0; m<mistakeCars.length; m++) {
//			if (mistakeCars[m][0] == mainDay[1][k][2].split(" ")[1]) {	//比较时间
//				flag = true;
//				
//				for (var n=0; n<mistakeCars[m][1].length; n++) {	//遍历所有错误车号
//					for (var v=0; v<mainDay[1][k-1][1].length; v++) {	//查找上一条该车号所在车道
//						if (mainDay[1][k-1][1][v] == mistakeCars[m][1][n]) {
//							errorCars.push([mainDay[1][k-1][1][v], v]);
//							
//							// 将错误的车号从追车队列中除去
//							carsList = deepClone(CARS_LIST);
//							var ind = -1;
//							for (var a=0; a<carsList.length; a++) {
//								if (mainDay[1][k-1][1][v] == carsList[a]) {
//									ind = a;
//								}
//							}
//							carsList.splice(ind, 1);
//							
//							var inde = -1;
//							for (var c=0; c<carsList.length; c++) {
//								if (mainDay[1][k][1][v] == carsList[c]) {
//									inde = c;
//									break;
//								}
//							}
//							
//							backMoneySum += parseInt(backMoney);
//							sumMoney -= backMoney;	// 扣除下注金额
//							
//							if (inde > -1) {	//猜中
//								profit = parseInt(backMoney*1 / carsList.length) * LOTTERY_TIMES;
//								sumMoney += profit;
//								profitSum += profit;
//								
//								isCorrect = true;
//							} else {	//猜错
//								profit = 0;
//								isCorrect = false;
//							}
//							
//							break;
//						}
//					}
//				}
//			}
//		}
		for (var m=0; m<backCars.length; m++) {
			if (backCars[m][0] == mainDay[1][k][2].split(" ")[1]) {	//比较时间
				flag = true;
				
				for (var n=0; n<backCars[m][1].length; n++) {	//遍历所有错误车号
					for (var v=0; v<mainDay[1][k-1][1].length; v++) {	//匹配车道
//						if (mainDay[1][k-1][1][v] == backCars[m][1][n]) {
						if (v == (backCars[m][1][n][0] - 1)) {
//							errorCars.push([mainDay[1][k-1][1][v], v]);
							
							// 将错误的车号从追车队列中除去
							carsList = deepClone(CARS_LIST);
							var ind = -1;
							for (var a=0; a<carsList.length; a++) {
//								if (mainDay[1][k-1][1][v] == carsList[a]) {
								if (backCars[m][1][n][1] == carsList[a]) {
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
								
//								isCorrect = true;
								errorCars.push([mainDay[1][k-1][1][v], v, true]);
							} else {	//猜错
								profit = 0;
//								isCorrect = false;
								errorCars.push([mainDay[1][k-1][1][v], v, false]);
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
//					if (isCorrect)
					if (errorCars[g][2])
						htm = "<td><span class='winBGColor'>" + mainDay[1][k][1][f] + "</span></td>";
					else htm = "<td><span class='errorBGColor'>" + mainDay[1][k][1][f] + "</span></td>";
				}
			}
			numbersHtml += htm;
		}
		
		var errorCarsHtml = "";
		for (var h=0; h<errorCars.length; h++) {
//			errorCarsHtml += "[" + errorCars[h][0] + "]车-" + (errorCars[h][1]*1+1) + "道;"
			errorCarsHtml += (errorCars[h][1]*1+1) + "道" + + errorCars[h][0] + ";";
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
	
	//初始化对子（追车号）按钮
	$("#rulesDoubleNumButton").click(function () {
		var _self = this;
		if (currRule != 1) {
			$("#currRuleSpan").text($(_self).text());
			currRule = 1;
		}
	});
	
	//初始化三连按钮
	$("#rulesTripleButton").click(function () {
		var _self = this;
		if (currRule != 2) {
			$("#currRuleSpan").text($(_self).text());
			currRule = 2;
		}
	});

	//初始化对子（追车道）按钮
	$("#rulesDoubleRoadButton").click(function () {
		var _self = this;
		if (currRule != 3) {
			$("#currRuleSpan").text($(_self).text());
			currRule = 3;
		}
	});
	
	//初始化天数按钮
	$("#rulesDaysButton").click(function () {
		var num = $("#rulesDaysInput").val();
		if (isUnsignedInteger(num)) {
			$("#currRulesDaysSpan").text(num);
			currRulesDays = num;
		} else alert("请输入正整数。");
	});
	
	//根据配置的参数重新加载下放的数据表格
	$("#reloadDataTableButton").click(function() {
		$("#compareTable span.mainDay").text(perDayDataArr[perDayDataArr.length-1][0]);
		$("select.mainDayDateSelect").val(perDayDataArr[perDayDataArr.length-1][0]);
		
		if (currRule == 1) {	//对子（追车号）
			initDoubleNumPerDayTable(perDayDataArr[0][0], perDayDataArr[perDayDataArr.length-1][0]);
			initDoubleNumCompareTable(perDayDataArr[perDayDataArr.length-1][0]);
		} else if (currRule == 2) {	//三连
			initTriplePerDayTable(perDayDataArr[0][0], perDayDataArr[perDayDataArr.length-1][0]);
			initTripleCompareTable(perDayDataArr[perDayDataArr.length-1][0]);
		} else if (currRule == 3) {	//对子（追车道）
			initDoubleRoadPerDayTable(perDayDataArr[0][0], perDayDataArr[perDayDataArr.length-1][0]);
			initDoubleRoadCompareTable(perDayDataArr[perDayDataArr.length-1][0]);
		} else {
			;
		}
	});
});