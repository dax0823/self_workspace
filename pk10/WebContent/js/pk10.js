/**
 * 常量
 */
//是否去重（开关）
var IS_DUPLICATE = true;
//var IS_DUPLICATE = false;
// 请求地址
//var SERVER_URL = "http://www.pk10we.com/Pk10/ajax?ajaxhandler=GetNewestRecord&t=" + new Date();
var SERVER_URL = "http://www.97pkw.com/Pk10/ajax?ajaxhandler=GetNewestRecord&t=" + new Date();
// 自动请求时间间隔
 var MINUTE = 5;
//var MINUTE = 1;
// 中奖倍数
var TIMES = 0.92;
// 冠亚军和值大小的阈值：
// [2, 10]：小
// [11, 20]：大
var IS_SMALL_THRESHOLD = 11;
//规则三：“小”映射为
var RULE3_SMALL = 1;
//规则三：“大”映射为
var RULE3_BIG = 2;
//规则四：“单”映射为
var RULE4_ODD = 1;
//规则四：“双”映射为
var RULE4_EVEN = 2;

//可配置规则，初始总额
var CONFIG_ORIGINAL_SUM = 5000;
//var CONFIG_ORIGINAL_SUM = 10000;
//可配置规则，中奖系数
var CONFIG_LOTTERY_TIMES = 10;
//可配置规则，初始下注金额
var CONFIG_BACK_MONEY = 100;

/**
 * 变量
 */
// 历史数据
var histroyDataArr = new Array();
// 实时数据（含历史数据）
var dataArr = new Array();
//大量数据（所有数据和）
var bigDataArr = [];

// 下注配置数据
var backConfigArr = new Array();
// 已下注数据
//三维数组：backArr[规则][赛道][金额]
var backArr = null;
// 收支总金额，初始为 0
var income = 0;
//每日（每次打开页面）下注笔数限制
var backNum = 20;

// （普通）可配置规则数据
var configRulesArr = new Array();
// 当前应用中可配置规则数据
var currConfigRule = new Array();
// （首尾）可配置规则数据
var headTailConfigRulesArr = new Array();
//连中时，下注次数上限为
var configRulesTimes = 3;
//当前显示的规则（默认为 1）
//固定规则：1，2，3，4
//可配置规则：5（普通），6（首尾），7（冠亚）
var currRulesNum = 1;
//当本次猜中后，是否要将此号从追车队列中暂时剔除（默认剔除）
var isDeleteFollowCar = true;
//汇总数据，将每日最后结果进行汇总
//countPerDayArr[时间][结束金额][完全连中次数][……]
var  countPerDayArr = [];

/**
* 根据本次冠军&亚军来判断其的和值的大小
* first：冠军值
* second：亚军值
*/
var getRecordIsSmall = function(first, second) {
	if ((parseInt(first) + parseInt(second)) > IS_SMALL_THRESHOLD) return false;
	else return true;
}

/**
* 根据本次冠军&亚军来判断其的和值的单双数
* first：冠军值
* second：亚军值
*/
var getRecordIsOdd = function(first, second) {
	if ((parseInt(first) + parseInt(second)) % 2 == 0) return false;
	else return true;
}

/**
 * 规则三（和值+大【X】小【Y】 => 单双 => 大小）
 */
var getRecordIsOddRule3 = function(first, second) {
	return getRecordIsOdd(parseInt(first) + parseInt(second), getRecordIsSmall(first, second) ? RULE3_SMALL : RULE3_BIG);
}

/**
 * 规则三（和值+单【A】双【B】 => 大小 => 单双）
 */
var getRecordIsSmallRule4 = function(first, second) {
	return getRecordIsSmall(parseInt(first) + parseInt(second), getRecordIsOdd(first, second) ? RULE4_ODD : RULE4_EVEN);
}

/**
 * 录入历史数据
 */
var inputHistroyData = function() {
	//格式化历史数据
	var data = $("#histroyRecordTextArea").val();	//全量历史数据字符串
	var rowsDataArr = data.split("\n");	//拆分成每行的字符串数组
	if (rowsDataArr) {
		for (var i=0; i<rowsDataArr.length; i++) {
			if (rowsDataArr[i] && rowsDataArr[i].length > 0) {
				var rowData = rowsDataArr[i].split("	");		//分隔开期数&每道数据
				var numbersArr = rowData[1].split(",");
//				histroyDataArr.unshift([rowData[0], numbersArr, rowData[2]]);
				bigDataArr.unshift([rowData[0], numbersArr, rowData[2]]);
			}
		}
	}
	
	bigDataArr.sort(function(a, b) {
		// a-b：升序
		// b-a：降序
		return b[0] - a[0];
	});
	//数据格式为：“595163	06,03,10,01,08,05,02,09,07,04	2017/01/01 09:12”
	//按天分页
	var timee = "";
	for (var i=bigDataArr.length-1; i>=0; i--) {
		var dat = bigDataArr[i][2].split(" ");
		if (timee == "") {
			timee = dat[0];
//			histroyDataArr.push(bigDataArr[i]);
		} else if (timee == dat[0]) {	//判断是否为同一天数据，如“2017/01/01”
//			histroyDataArr.push(bigDataArr[i]);
		} else break;
		
		histroyDataArr.push(bigDataArr[i]);
	}
	
	//调整历史数据的顺序
	histroyDataArr.sort(function(a, b) {
		// a-b：升序
		// b-a：降序
		return b[0] - a[0];
	});
	
	//div 处理
	$("#histroyRecordDiv").hide();
	$("#contentDiv").show();
	
	//将历史数据压入实时数据中
	dataArr = histroyDataArr;
	
	//历史数据页面展示
	setHistroyDataTableRule1($("#recordTableRule1 tbody"));
	setHistroyDataTableRule2($("#recordTableRule2 tbody"));
	setHistroyDataTableRule3($("#recordTableRule3 tbody"));
	setHistroyDataTableRule4($("#recordTableRule4 tbody"));
}

/**
 * 历史数据填入页面表格：规则一（大小=>单双）
 * obj：页面表格对象
 */
var setHistroyDataTableRule1 = function(obj) {
	//将历史数据渲染后显示
	var trHtmlArr = new Array();
	for (var j=histroyDataArr.length-1; j>=0; j--) {
		var numbersArr = histroyDataArr[j][1];
		// 根据上一次和值的大小，来判断本次那些号码中奖
		// 如果冠亚军和值大于11，则为“大”，本次双数中奖
		// 如果冠亚军和值小于等于11，则为“小”，则本次单数中奖
		var numbersHtml = "";
		for (var k=0; k<numbersArr.length; k++) {
			if (j+1 == histroyDataArr.length) {	//历史第一条数据不做渲染
				numbersHtml += "<td><span class='numberBGColor'>" + numbersArr[k] + "</span></td>";
			} else {
				var numbersArr2 = histroyDataArr[j+1][1];
				if (!getRecordIsSmall(numbersArr2[0], numbersArr2[1]) && parseInt(numbersArr[k]) % 2 == 0)
					numbersHtml += "<td><span class='winBGColor'>" + numbersArr[k] + "</span></td>";
				else if (getRecordIsSmall(numbersArr2[0], numbersArr2[1]) && parseInt(numbersArr[k]) % 2 != 0) 
					numbersHtml += "<td><span class='winBGColor'>" + numbersArr[k] + "</span></td>";
				else numbersHtml += "<td><span class='numberBGColor'>" + numbersArr[k] + "</span></td>";
			}
		}
		
		var trHtml = "<tr class='histroy'>" +
				"<td>"  + histroyDataArr[j][2] +"</td>" +
				"<td><span class='time'>"  + histroyDataArr[j][0] +"<span></td>" +
				numbersHtml +
				"<td>" + (!getRecordIsSmall(numbersArr[0], numbersArr[1]) ? "<span class='big'>大</span>" : "<span class='small'>小</span>") + "</td>" +
				"<td></td>" +
			"</tr>";
		trHtmlArr.unshift(trHtml);
	}
	$(obj).append(trHtmlArr.toString());
	
	//显示下次“冠亚军和”的内容
	if (dataArr && dataArr.length > 0)
		$(obj).parent().find("td.sum").html(getRecordIsSmall(dataArr[0][1][0], dataArr[0][1][1]) ? "<span class='small'>单</span>" : "<span class='big'>双</span>" );
}

/**
 * 历史数据填入页面表格：规则二（单双=>大小）
 * obj：页面表格对象
 */
var setHistroyDataTableRule2 = function(obj) {
	//将历史数据渲染后显示
	var trHtmlArr = new Array();
	for (var j=histroyDataArr.length-1; j>=0; j--) {
		var numbersArr = histroyDataArr[j][1];
		// 根据上一次和值的单双，来判断本次那些号码中奖
		// 如果冠亚军和值为单数，则本次小号（1~5）中奖
		// 如果冠亚军和值为双数，则本次大号（6~10）中奖
		var numbersHtml = "";
		for (var k=0; k<numbersArr.length; k++) {
			if (j+1 == histroyDataArr.length) {	//历史第一条数据不做渲染
				numbersHtml += "<td><span class='numberBGColor'>" + numbersArr[k] + "</span></td>";
			} else {
				var numbersArr2 = histroyDataArr[j+1][1];
				if (getRecordIsOdd(numbersArr2[0], numbersArr2[1]) && parseInt(numbersArr[k]) <=5 )
					numbersHtml += "<td><span class='winBGColor'>" + numbersArr[k] + "</span></td>";
				else if (!getRecordIsOdd(numbersArr2[0], numbersArr2[1]) && parseInt(numbersArr[k]) >=6) 
					numbersHtml += "<td><span class='winBGColor'>" + numbersArr[k] + "</span></td>";
				else numbersHtml += "<td><span class='numberBGColor'>" + numbersArr[k] + "</span></td>";
			}
		}
		
		var trHtml = "<tr class='histroy'>" +
				"<td>"  + histroyDataArr[j][2] +"</td>" +
				"<td><span class='time'>"  + histroyDataArr[j][0] +"<span></td>" +
				numbersHtml +
				"<td>" + (getRecordIsOdd(numbersArr[0], numbersArr[1]) ? "<span class='small'>单</span>" : "<span class='big'>双</span>" ) + "</td>" +
				"<td></td>" +
			"</tr>";
		trHtmlArr.unshift(trHtml);
	}
	$(obj).append(trHtmlArr.toString());
	
	//显示下次“冠亚军和”的内容
	if (dataArr && dataArr.length > 0)
		$(obj).parent().find("td.sum").html(getRecordIsOdd(dataArr[0][1][0], dataArr[0][1][1]) ? "<span class='small'>小</span>" : "<span class='big'>大</span>" );
}


/**
 * 历史数据填入页面表格：规则三（和值+大【2】小【1】 => 单双 => 大小）
 * obj：页面表格对象
 */
var setHistroyDataTableRule3 = function(obj) {
	//将历史数据渲染后显示
	var trHtmlArr = new Array();
	for (var j=histroyDataArr.length-1; j>=0; j--) {
		var numbersArr = histroyDataArr[j][1];
		// 根据上一次和值的单双，来判断本次那些号码中奖
		// 如果冠亚军和值为单数，则本次小号（1~5）中奖
		// 如果冠亚军和值为双数，则本次大号（6~10）中奖
		var numbersHtml = "";
		for (var k=0; k<numbersArr.length; k++) {
			if (j+1 == histroyDataArr.length) {	//历史第一条数据不做渲染
				numbersHtml += "<td><span class='numberBGColor'>" + numbersArr[k] + "</span></td>";
			} else {
				var numbersArr2 = histroyDataArr[j+1][1];
				if (getRecordIsOddRule3(numbersArr2[0], numbersArr2[1]) && parseInt(numbersArr[k]) <=5 )
					numbersHtml += "<td><span class='winBGColor'>" + numbersArr[k] + "</span></td>";
				else if (!getRecordIsOddRule3(numbersArr2[0], numbersArr2[1]) && parseInt(numbersArr[k]) >=6) 
					numbersHtml += "<td><span class='winBGColor'>" + numbersArr[k] + "</span></td>";
				else numbersHtml += "<td><span class='numberBGColor'>" + numbersArr[k] + "</span></td>";
			}
		}
		
		var trHtml = "<tr class='histroy'>" +
				"<td>"  + histroyDataArr[j][2] +"</td>" +
				"<td><span class='time'>"  + histroyDataArr[j][0] +"<span></td>" +
				numbersHtml +
				"<td>" + (getRecordIsOddRule3(numbersArr[0], numbersArr[1]) ? "<span class='small'>单</span>" : "<span class='big'>双</span>" ) + "</td>" +
				"<td>" + (parseInt(numbersArr[0]) + parseInt(numbersArr[1])) + " | " + (getRecordIsSmall(numbersArr[0], numbersArr[1]) ? "<span class='small'>小</span>【" + RULE3_SMALL + "】" : "<span class='big'>大</span>【" + RULE3_BIG + "】" )  + "</td>" +
				"<td></td>" +
			"</tr>";
		trHtmlArr.unshift(trHtml);
	}
	$(obj).append(trHtmlArr.toString());
	
	//显示下次“冠亚军和”的内容
	if (dataArr && dataArr.length > 0)
		$(obj).parent().find("td.sum").html(getRecordIsOddRule3(dataArr[0][1][0], dataArr[0][1][1]) ? "<span class='small'>小</span>" : "<span class='big'>大</span>" );
}

/**
 * 历史数据填入页面表格：规则四（和值+单【-1】双【1】 => 大小 => 单双）
 * obj：页面表格对象
 */
var setHistroyDataTableRule4 = function(obj) {
	//将历史数据渲染后显示
	var trHtmlArr = new Array();
	for (var j=histroyDataArr.length-1; j>=0; j--) {
		var numbersArr = histroyDataArr[j][1];
		// 根据上一次和值的大小，来判断本次那些号码中奖
		// 如果冠亚军和值大于11，则为“大”，本次双数中奖
		// 如果冠亚军和值小于等于11，则为“小”，则本次单数中奖
		var numbersHtml = "";
		for (var k=0; k<numbersArr.length; k++) {
			if (j+1 == histroyDataArr.length) {	//历史第一条数据不做渲染
				numbersHtml += "<td><span class='numberBGColor'>" + numbersArr[k] + "</span></td>";
			} else {
				var numbersArr2 = histroyDataArr[j+1][1];
				if (!getRecordIsSmallRule4(numbersArr2[0], numbersArr2[1]) && parseInt(numbersArr[k]) % 2 == 0)
					numbersHtml += "<td><span class='winBGColor'>" + numbersArr[k] + "</span></td>";
				else if (getRecordIsSmallRule4(numbersArr2[0], numbersArr2[1]) && parseInt(numbersArr[k]) % 2 != 0) 
					numbersHtml += "<td><span class='winBGColor'>" + numbersArr[k] + "</span></td>";
				else numbersHtml += "<td><span class='numberBGColor'>" + numbersArr[k] + "</span></td>";
			}
		}
		
		var trHtml = "<tr class='histroy'>" +
				"<td>"  + histroyDataArr[j][2] +"</td>" +
				"<td><span class='time'>"  + histroyDataArr[j][0] +"<span></td>" +
				numbersHtml +
				"<td>" + (!getRecordIsSmallRule4(numbersArr[0], numbersArr[1]) ? "<span class='big'>大</span>" : "<span class='small'>小</span>") + "</td>" +
				"<td>" + (parseInt(numbersArr[0]) + parseInt(numbersArr[1])) + " | " + (getRecordIsOdd(numbersArr[0], numbersArr[1]) ? "<span class='small'>单</span>【" + RULE4_ODD + "】" : "<span class='big'>双</span>【" + RULE4_EVEN + "】" )  + "</td>" +
				"<td></td>" + 
			"</tr>";
		trHtmlArr.unshift(trHtml);
	}
	$(obj).append(trHtmlArr.toString());
	
	//显示下次“冠亚军和”的内容
	if (dataArr && dataArr.length > 0)
		$(obj).parent().find("td.sum").html(getRecordIsSmallRule4(dataArr[0][1][0], dataArr[0][1][1]) ? "<span class='small'>单</span>" : "<span class='big'>双</span>" );
}

/**
 * 获取实时最新数据
 */
var getNewestRecord = function() {
	var url = SERVER_URL;
	var param = {};
	var callBack = function(result) {
		// 真实返回结果
//		{"period":582348,"drawingTime":"10-21 15:17","numbers":"6,4,10,3,5,8,9,1,2,7","guanyahe":10,"guanyahedx":"\u5c0f","guanyaheds":"\u53cc","longhu":["\u864e","\u9f8d","\u9f8d","\u864e","\u864e"]}
		if (result) {
			// 重复数据不再进行操作
			if (IS_DUPLICATE) {
//				if (dataArr && dataArr.length > 0 && dataArr[0][0] == result.period) {
				if (bigDataArr && bigDataArr.length > 0 && bigDataArr[0][0] == result.period) {
//					$(".isNewest").html("（" + result.period + "是最新一期）")
					return;
				}
			}
			
			//将所获取数据储存
			var numbers = result.numbers.split(",");
//			dataArr.unshift([result.period, numbers, result.drawingTime]);	// 最新的数据放在最上面
			bigDataArr.unshift([result.period, numbers, result.drawingTime]);	// 最新的数据放在最上面
			
			//将实时数据逐条填入表格
			setNewestDataTableRule1($("#recordTableRule1 tbody"), result.period, numbers, result.drawingTime);
			setNewestDataTableRule2($("#recordTableRule2 tbody"), result.period, numbers, result.drawingTime);
			setNewestDataTableRule3($("#recordTableRule3 tbody"), result.period, numbers, result.drawingTime);
			setNewestDataTableRule4($("#recordTableRule4 tbody"), result.period, numbers, result.drawingTime);
			
			//清理下注数据
//			backArr = new Array();
			backArr = new Array($(".ruleDivClass").length);
			$(".backShow").html("");
			
			//将收支金额显示到页面上
			$("#income").html(income);
			
			//显示下次期号
			$("td.period").html("<span class='time'>" + (parseInt(result.period) + 1) + "</span><br>已下注");
			
			//清理其他显示内容
//			$(".isNewest").html("");

			if (!(currConfigRule && currConfigRule.length > 0)) return;
			//currConfigRule[主车][追车队列][类型]
			//类型：1->普通；2->首尾；3->冠亚
			if (currConfigRule[2].toString() == "2")
				initHeadTailConfigRuleTable();
			else if (currConfigRule[2].toString() == "3") 
				initChampionConfigRuleTable();
			else initConfigRuleTable();
		}
	};
	
	$.ajax({
		url : url,
		type: "GET",
		dataType: 'JSONP',
		success : callBack
	});
}

/**
 * 实时数据填入页面表格：规则一
 * obj：页面表格对象
 * period：期号
 * numbers：本次号码数组
 * drawingTime：发布时间
 */
var setNewestDataTableRule1 = function(obj, period, numbers, drawingTime) {
	//将本次数据填入表格
	var trBGColor = 0;	// 行底色
	var incomeHtml = ""; //拼写下注收支情况
	var numbersHtml = "";
	for (var i=0; i<numbers.length; i++) {
		// 颜色显示规则：
		// 绿色：未下注，但实际已中奖
		// 紫色：已下注，且实际也中奖
		// 红色：已下注，但实际未中奖
		if (dataArr && dataArr.length > 1 && !getRecordIsSmall(dataArr[1][1][0], dataArr[1][1][1]) && numbers[i] % 2 == 0
				&& parseInt(period) - 1 == parseInt(dataArr[1][0])) {	// 中奖：前一次开“大”
			var flag = false;
			if (backArr[0]) {
				for (var bi=0; bi<backArr[0].length; bi++) {
					if (backArr[0][bi][0] == i+1) {
						flag = true;
						incomeHtml += backArr[0][bi][0] + "道下注￥" + backArr[0][bi][1] + "，<span class='incomeLucky'>赢利￥" + backArr[0][bi][1] * TIMES +"</span>;<br>";
						income += backArr[0][bi][1] * TIMES;
						break;
					}
				}
			}
			
			if (flag) numbersHtml += "<td><span class='luckyBGColor'>" + numbers[i] + "</span></td>";
			else numbersHtml += "<td><span class='winBGColor'>" + numbers[i] + "</span></td>";
		} else if (dataArr && dataArr.length > 1 &&  getRecordIsSmall(dataArr[1][1][0], dataArr[1][1][1])  && numbers[i] % 2 != 0
				&& parseInt(period) - 1 == parseInt(dataArr[1][0])) {	// 中奖：前一次开“小”
			var flag = false;
			if (backArr[0]) {
				for (var bi=0; bi<backArr[0].length; bi++) {
					if (backArr[0][bi][0] == i+1) {
						flag = true;
						incomeHtml += backArr[0][bi][0] + "道下注￥" + backArr[0][bi][1] + "，<span class='incomeLucky'>赢利￥" + backArr[0][bi][1] * TIMES +"</span>;<br>";
						income += backArr[0][bi][1] * TIMES;
						break;
					}
				}
			}
			
			if (flag) numbersHtml += "<td><span class='luckyBGColor'>" + numbers[i] + "</span></td>";
			else numbersHtml += "<td><span class='winBGColor'>" + numbers[i] + "</span></td>";
		} else {	// 未中奖
			var flag = false;
			if (backArr[0]) {
				for (var bi=0; bi<backArr[0].length; bi++) {
					if (backArr[0][bi][0] == i+1) {
						flag = true;
						incomeHtml += backArr[0][bi][0] + "道下注￥" + backArr[0][bi][1] + "，<span class='incomeError'>损失￥" + backArr[0][bi][1] +"</span>;<br>";
						income -= backArr[0][bi][1];
						break;
					}
				}
			}
			
			if (flag) numbersHtml += "<td><span class='errorBGColor'>" + numbers[i] + "</span></td>";
			else numbersHtml += "<td><span class='numberBGColor'>" + numbers[i] + "</span></td>";
		}
	}

	var trHtml = "<tr class='" + (trBGColor%2 == 0 ? "" : "trBGColor") + "'>" +
		"<td>"  + drawingTime +"</td>" +
		"<td><span class='time'>"  + period +"<span></td>" +
		numbersHtml +
		"<td>" + (!getRecordIsSmall(numbers[0], numbers[1]) ? "<span class='big'>大</span>" : "<span class='small'>小</span>" ) + "</td>" +
		"<td>"  + incomeHtml + "</td>" +
	"</tr>";
	
	if ($(obj).find("tr").length > 0) {
		$(obj).find("tr:eq(0)").before(trHtml);
	} else {
		$(obj).append(trHtml);
	}
	trBGColor++;

	//显示下次“冠亚军和”的内容
	$(obj).parent().find("td.sum").html((!getRecordIsSmall(numbers[0], numbers[1])  ? "<span class='big'>双</span>" : "<span class='small'>单</span>" ));
}

/**
 * 实时数据填入页面表格：规则二
 * obj：页面表格对象
 * period：期号
 * numbers：本次号码数组
 * drawingTime：发布时间
 */
var setNewestDataTableRule2 = function(obj, period, numbers, drawingTime) {
	//将本次数据填入表格
	var trBGColor = 0;	// 行底色
	var incomeHtml = ""; //拼写下注收支情况
	var numbersHtml = "";
	for (var i=0; i<numbers.length; i++) {
		// 颜色显示规则：
		// 绿色：未下注，但实际已中奖
		// 紫色：已下注，且实际也中奖
		// 红色：已下注，但实际未中奖
		if (dataArr && dataArr.length > 1 && getRecordIsOdd(dataArr[1][1][0], dataArr[1][1][1]) && numbers[i] <= 5
				&& parseInt(period) - 1 == parseInt(dataArr[1][0])) {	// 中奖：前一次开“大”
			var flag = false;
			if (backArr[1]) {
				for (var bi=0; bi<backArr[1].length; bi++) {
					if (backArr[1][bi][0] == i+1) {
						flag = true;
						incomeHtml += backArr[1][bi][0] + "道下注￥" + backArr[1][bi][1] + "，<span class='incomeLucky'>赢利￥" + backArr[1][bi][1] * TIMES +"</span>;<br>";
						income += backArr[1][bi][1] * TIMES;
						break;
					}
				}
			}
			
			if (flag) numbersHtml += "<td><span class='luckyBGColor'>" + numbers[i] + "</span></td>";
			else numbersHtml += "<td><span class='winBGColor'>" + numbers[i] + "</span></td>";
		} else if (dataArr && dataArr.length > 1 &&  !getRecordIsOdd(dataArr[1][1][0], dataArr[1][1][1])  && numbers[i] >= 6
				&& parseInt(period) - 1 == parseInt(dataArr[1][0])) {	// 中奖：前一次开“小”
			var flag = false;
			if (backArr[1]) {
				for (var bi=0; bi<backArr[1].length; bi++) {
					if (backArr[1][bi][0] == i+1) {
						flag = true;
						incomeHtml += backArr[1][bi][0] + "道下注￥" + backArr[1][bi][1] + "，<span class='incomeLucky'>赢利￥" + backArr[1][bi][1] * TIMES +"</span>;<br>";
						income += backArr[1][bi][1] * TIMES;
						break;
					}
				}
			}
			
			if (flag) numbersHtml += "<td><span class='luckyBGColor'>" + numbers[i] + "</span></td>";
			else numbersHtml += "<td><span class='winBGColor'>" + numbers[i] + "</span></td>";
		} else {	// 未中奖
			var flag = false;
			if (backArr[1]) {
				for (var bi=0; bi<backArr[1].length; bi++) {
					if (backArr[1][bi][0] == i+1) {
						flag = true;
						incomeHtml += backArr[1][bi][0] + "道下注￥" + backArr[1][bi][1] + "，<span class='incomeError'>损失￥" + backArr[1][bi][1] +"</span>;<br>";
						income -= backArr[1][bi][1];
						break;
					}
				}
			}
			
			if (flag) numbersHtml += "<td><span class='errorBGColor'>" + numbers[i] + "</span></td>";
			else numbersHtml += "<td><span class='numberBGColor'>" + numbers[i] + "</span></td>";
		}
	}

	var trHtml = "<tr class='" + (trBGColor % 2 == 0 ? "" : "trBGColor") + "'>" +
		"<td>"  + drawingTime +"</td>" +
		"<td><span class='time'>"  + period +"<span></td>" +
		numbersHtml +
		"<td>" + (getRecordIsOdd(numbers[0], numbers[1]) ? "<span class='small'>单</span>" : "<span class='big'>双</span>" ) + "</td>" +
		"<td>"  + incomeHtml + "</td>" +
	"</tr>";
	
	if ($(obj).find("tr").length > 0) {
		$(obj).find("tr:eq(0)").before(trHtml);
	} else {
		$(obj).append(trHtml);
	}
	trBGColor++;

	//显示下次“冠亚军和”的内容
	$(obj).parent().find("td.sum").html(getRecordIsOdd(numbers[0], numbers[1]) ? "<span class='small'>小</span>" : "<span class='big'>大</span>" );
}

/**
 * 实时数据填入页面表格：规则三
 * obj：页面表格对象
 * period：期号
 * numbers：本次号码数组
 * drawingTime：发布时间
 */
var setNewestDataTableRule3 = function(obj, period, numbers, drawingTime) {
	//将本次数据填入表格
	var trBGColor = 0;	// 行底色
	var incomeHtml = ""; //拼写下注收支情况
	var numbersHtml = "";
	for (var i=0; i<numbers.length; i++) {
		// 颜色显示规则：
		// 绿色：未下注，但实际已中奖
		// 紫色：已下注，且实际也中奖
		// 红色：已下注，但实际未中奖
		if (dataArr && dataArr.length > 1 && getRecordIsOddRule3(dataArr[1][1][0], dataArr[1][1][1]) && numbers[i] <= 5
				&& parseInt(period) - 1 == parseInt(dataArr[1][0])) {	// 中奖：前一次开“大”
			var flag = false;
			if (backArr[2]) {
				for (var bi=0; bi<backArr[2].length; bi++) {
					if (backArr[2][bi][0] == i+1) {
						flag = true;
						incomeHtml += backArr[2][bi][0] + "道下注￥" + backArr[2][bi][1] + "，<span class='incomeLucky'>赢利￥" + backArr[2][bi][1] * TIMES +"</span>;<br>";
						income += backArr[2][bi][1] * TIMES;
						break;
					}
				}
			}
			
			if (flag) numbersHtml += "<td><span class='luckyBGColor'>" + numbers[i] + "</span></td>";
			else numbersHtml += "<td><span class='winBGColor'>" + numbers[i] + "</span></td>";
		} else if (dataArr && dataArr.length > 1 &&  !getRecordIsOddRule3(dataArr[1][1][0], dataArr[1][1][1])  && numbers[i] >= 6
				&& parseInt(period) - 1 == parseInt(dataArr[1][0])) {	// 中奖：前一次开“小”
			var flag = false;
			if (backArr[2]) {
				for (var bi=0; bi<backArr[2].length; bi++) {
					if (backArr[2][bi][0] == i+1) {
						flag = true;
						incomeHtml += backArr[2][bi][0] + "道下注￥" + backArr[2][bi][1] + "，<span class='incomeLucky'>赢利￥" + backArr[2][bi][1] * TIMES +"</span>;<br>";
						income += backArr[2][bi][1] * TIMES;
						break;
					}
				}
			}
			
			if (flag) numbersHtml += "<td><span class='luckyBGColor'>" + numbers[i] + "</span></td>";
			else numbersHtml += "<td><span class='winBGColor'>" + numbers[i] + "</span></td>";
		} else {	// 未中奖
			var flag = false;
			if (backArr[2]) {
				for (var bi=0; bi<backArr[2].length; bi++) {
					if (backArr[2][bi][0] == i+1) {
						flag = true;
						incomeHtml += backArr[2][bi][0] + "道下注￥" + backArr[2][bi][1] + "，<span class='incomeError'>损失￥" + backArr[2][bi][1] +"</span>;<br>";
						income -= backArr[2][bi][1];
						break;
					}
				}
			}
			
			if (flag) numbersHtml += "<td><span class='errorBGColor'>" + numbers[i] + "</span></td>";
			else numbersHtml += "<td><span class='numberBGColor'>" + numbers[i] + "</span></td>";
		}
	}

	var trHtml = "<tr class='" + (trBGColor % 2 == 0 ? "" : "trBGColor") + "'>" +
		"<td>"  + drawingTime +"</td>" +
		"<td><span class='time'>"  + period +"<span></td>" +
		numbersHtml +
		"<td>" + (getRecordIsOddRule3(numbers[0], numbers[1]) ? "<span class='small'>单</span>" : "<span class='big'>双</span>" ) + "</td>" +
		"<td>" + (parseInt(numbers[0]) + parseInt(numbers[1])) + " | " + (getRecordIsSmall(numbers[0], numbers[1]) ? "<span class='small'>小</span>【" + RULE3_SMALL + "】" : "<span class='big'>大</span>【" + RULE3_BIG+ "】" )  + "</td>" +
		"<td>"  + incomeHtml + "</td>" + 
	"</tr>";
	
	if ($(obj).find("tr").length > 0) {
		$(obj).find("tr:eq(0)").before(trHtml);
	} else {
		$(obj).append(trHtml);
	}
	trBGColor++;

	//显示下次“冠亚军和”的内容
	$(obj).parent().find("td.sum").html(getRecordIsOddRule3(numbers[0], numbers[1]) ? "<span class='small'>小</span>" : "<span class='big'>大</span>" );
}

/**
 * 实时数据填入页面表格：规则四
 * obj：页面表格对象
 * period：期号
 * numbers：本次号码数组
 * drawingTime：发布时间
 */
var setNewestDataTableRule4 = function(obj, period, numbers, drawingTime) {
	//将本次数据填入表格
	var trBGColor = 0;	// 行底色
	var incomeHtml = ""; //拼写下注收支情况
	var numbersHtml = "";
	for (var i=0; i<numbers.length; i++) {
		// 颜色显示规则：
		// 绿色：未下注，但实际已中奖
		// 紫色：已下注，且实际也中奖
		// 红色：已下注，但实际未中奖
		if (dataArr && dataArr.length > 1 && !getRecordIsSmallRule4(dataArr[1][1][0], dataArr[1][1][1]) && numbers[i] % 2 == 0
				&& parseInt(period) - 1 == parseInt(dataArr[1][0])) {	// 中奖：前一次开“大”
			var flag = false;
			if (backArr[3]) {
				for (var bi=0; bi<backArr[3].length; bi++) {
					if (backArr[3][bi][0] == i+1) {
						flag = true;
						incomeHtml += backArr[3][bi][0] + "道下注￥" + backArr[3][bi][1] + "，<span class='incomeLucky'>赢利￥" + backArr[3][bi][1] * TIMES +"</span>;<br>";
						income += backArr[3][bi][1] * TIMES;
						break;
					}
				}
			}
			
			if (flag) numbersHtml += "<td><span class='luckyBGColor'>" + numbers[i] + "</span></td>";
			else numbersHtml += "<td><span class='winBGColor'>" + numbers[i] + "</span></td>";
		} else if (dataArr && dataArr.length > 1 &&  getRecordIsSmallRule4(dataArr[1][1][0], dataArr[1][1][1])  && numbers[i] % 2 != 0
				&& parseInt(period) - 1 == parseInt(dataArr[1][0])) {	// 中奖：前一次开“小”
			var flag = false;
			if (backArr[3]) {
				for (var bi=0; bi<backArr[3].length; bi++) {
					if (backArr[3][bi][0] == i+1) {
						flag = true;
						incomeHtml += backArr[3][bi][0] + "道下注￥" + backArr[3][bi][1] + "，<span class='incomeLucky'>赢利￥" + backArr[3][bi][1] * TIMES +"</span>;<br>";
						income += backArr[3][bi][1] * TIMES;
						break;
					}
				}
			}
			
			if (flag) numbersHtml += "<td><span class='luckyBGColor'>" + numbers[i] + "</span></td>";
			else numbersHtml += "<td><span class='winBGColor'>" + numbers[i] + "</span></td>";
		} else {	// 未中奖
			var flag = false;
			if (backArr[3]) {
				for (var bi=0; bi<backArr[3].length; bi++) {
					if (backArr[3][bi][0] == i+1) {
						flag = true;
						incomeHtml += backArr[3][bi][0] + "道下注￥" + backArr[3][bi][1] + "，<span class='incomeError'>损失￥" + backArr[3][bi][1] +"</span>;<br>";
						income -= backArr[3][bi][1];
						break;
					}
				}
			}
			
			if (flag) numbersHtml += "<td><span class='errorBGColor'>" + numbers[i] + "</span></td>";
			else numbersHtml += "<td><span class='numberBGColor'>" + numbers[i] + "</span></td>";
		}
	}

	var trHtml = "<tr class='" + (trBGColor%2 == 0 ? "" : "trBGColor") + "'>" +
		"<td>"  + drawingTime +"</td>" +
		"<td><span class='time'>"  + period +"<span></td>" +
		numbersHtml +
		"<td>" + (!getRecordIsSmallRule4(numbers[0], numbers[1]) ? "<span class='big'>大</span>" : "<span class='small'>小</span>" ) + "</td>" +
		"<td>" + (parseInt(numbers[0]) + parseInt(numbers[1])) + " | " + (getRecordIsOdd(numbers[0], numbers[1]) ? "<span class='small'>单</span>【" + RULE4_ODD + "】" : "<span class='big'>双</span>【" + RULE4_EVEN + "】" )  + "</td>" +
		"<td>"  + incomeHtml + "</td>" +
	"</tr>";
	
	if ($(obj).find("tr").length > 0) {
		$(obj).find("tr:eq(0)").before(trHtml);
	} else {
		$(obj).append(trHtml);
	}
	trBGColor++;

	//显示下次“冠亚军和”的内容
	$(obj).parent().find("td.sum").html((!getRecordIsSmallRule4(numbers[0], numbers[1])  ? "<span class='big'>双</span>" : "<span class='small'>单</span>" ));
}

/**
 * 将各规则的下注信息显示出来
 * str：连接词（单、双、大、小等）
 */
var printBackInfo = function() {
	var backHtml = "";
	if (backArr) {
		for (var i=0; i<backArr.length; i++) {
			if (backArr[i]) {
				backHtml += "<br>规则 " + backArr[i][0][3] + "：";
				for (var j=0; j<backArr[i].length; j++) {
					backHtml += backArr[i][j][0] + backArr[i][j][2] + backArr[i][j][1] + "，";
				}
				backHtml = backHtml.substring(0, backHtml.length - 1);
			}
		}
	}
	$("p span.backShow").html(backHtml);
}

/**
 * 下注按钮：规则一
 * obj：页面表格对象
 */
var backButtonClickRule1 = function(obj) {
	$(obj).find(".trWay td").each(function() {
		var _self = this;
		var css = $(_self).attr("class");
		var html = css + " 道";
		
		if (backConfigArr && backConfigArr.length > 0) {
			var optionsHtml = "";
			for (var c=0; c<backConfigArr.length; c++) {
				optionsHtml += "<option value='" + backConfigArr[c][1] + "'>" + backConfigArr[c][0] + "</option>";
			}
			html += "<br><select>" +
				optionsHtml +
				"</select><br><button>下注</button>";
		}
		$(_self).html(html);
		
		//初始化下注按钮
		$(this).find("button").click(function() {
			if (!backArr[0])  backArr[0] = new Array();
			for (var d=0; d<backArr[0].length; d++) {
				if (backArr[0][d][0] == css) {
					alert("请勿重复下注");
					return;
				}
			}
			
			if (backNum > 0) {
				backNum--;	//次数减一
				$("#backNum").html(backNum);
				
				//将下注信息显示在页面上
				var backHtml = "";
				var odd = "道";
				if (dataArr && dataArr.length > 0) {
					odd = (getRecordIsSmall(dataArr[0][1][0], dataArr[0][1][1]) ? "单" : "双");
				}
				
				backArr[0].push([css, $(_self).find("select").val(), odd, 1]);
				printBackInfo();
				
				$(obj).find(".trBackWay td." + css).html("<span class='backShow'>￥" + $(_self).find("select").val() + "</span>");
			}
		});
	});
}

/**
 * 下注按钮：规则二
 * obj：页面表格对象
 */
var backButtonClickRule2 = function(obj) {
	$(obj).find(".trWay td").each(function() {
		var _self = this;
		var css = $(_self).attr("class");
		var html = css + " 道";
		
		if (backConfigArr && backConfigArr.length > 0) {
			var optionsHtml = "";
			for (var c=0; c<backConfigArr.length; c++) {
				optionsHtml += "<option value='" + backConfigArr[c][1] + "'>" + backConfigArr[c][0] + "</option>";
			}
			html += "<br><select>" +
				optionsHtml +
				"</select><br><button>下注</button>";
		}
		$(_self).html(html);
		
		//初始化下注按钮
		$(this).find("button").click(function() {
			if (!backArr[1])  backArr[1] = new Array();
			for (var d=0; d<backArr[1].length; d++) {
				if (backArr[1][d][0] == css) {
					alert("请勿重复下注");
					return;
				}
			}
			
			if (backNum > 0) {
				backNum--;	//次数减一
				$("#backNum").html(backNum);
				
				//将下注信息显示在页面上
				var backHtml = "";
				var small = "道";
				if (dataArr && dataArr.length > 0) {
					small = (getRecordIsOdd(dataArr[0][1][0], dataArr[0][1][1]) ? "小" : "大");
				}
				
				backArr[1].push([css, $(_self).find("select").val(), small, 2]);
				printBackInfo();
				
				$(obj).find(".trBackWay td." + css).html("<span class='backShow'>￥" + $(_self).find("select").val() + "</span>");
			} else alert("很遗憾，当前可下注次数已不足。");
		});
	});
}

/**
 * 下注按钮：规则三
 * obj：页面表格对象
 */
var backButtonClickRule3 = function(obj) {
	$(obj).find(".trWay td").each(function() {
		var _self = this;
		var css = $(_self).attr("class");
		var html = css + " 道";
		
		if (backConfigArr && backConfigArr.length > 0) {
			var optionsHtml = "";
			for (var c=0; c<backConfigArr.length; c++) {
				optionsHtml += "<option value='" + backConfigArr[c][1] + "'>" + backConfigArr[c][0] + "</option>";
			}
			html += "<br><select>" +
				optionsHtml +
				"</select><br><button>下注</button>";
		}
		$(_self).html(html);
		
		//初始化下注按钮
		$(this).find("button").click(function() {
			if (!backArr[2])  backArr[2] = new Array();
			for (var d=0; d<backArr[2].length; d++) {
				if (backArr[2][d][0] == css) {
					alert("请勿重复下注");
					return;
				}
			}
			
			if (backNum > 0) {
				backNum--;	//次数减一
				$("#backNum").html(backNum);
				
				//将下注信息显示在页面上
				var backHtml = "";
				var small = "道";
				if (dataArr && dataArr.length > 0) {
					small = (getRecordIsOddRule3(dataArr[0][1][0], dataArr[0][1][1]) ? "小" : "大");
				}
				
				backArr[2].push([css, $(_self).find("select").val(), small, 3]);
				printBackInfo();
				
				$(obj).find(".trBackWay td." + css).html("<span class='backShow'>￥" + $(_self).find("select").val() + "</span>");
			} else alert("很遗憾，当前可下注次数已不足。");
		});
	});
}

/**
 * 下注按钮：规则四
 * obj：页面表格对象
 */
var backButtonClickRule4 = function(obj) {
	$(obj).find(".trWay td").each(function() {
		var _self = this;
		var css = $(_self).attr("class");
		var html = css + " 道";
		
		if (backConfigArr && backConfigArr.length > 0) {
			var optionsHtml = "";
			for (var c=0; c<backConfigArr.length; c++) {
				optionsHtml += "<option value='" + backConfigArr[c][1] + "'>" + backConfigArr[c][0] + "</option>";
			}
			html += "<br><select>" +
				optionsHtml +
				"</select><br><button>下注</button>";
		}
		$(_self).html(html);
		
		//初始化下注按钮
		$(this).find("button").click(function() {
			if (!backArr[3])  backArr[3] = new Array();
			for (var d=0; d<backArr[3].length; d++) {
				if (backArr[3][d][0] == css) {
					alert("请勿重复下注");
					return;
				}
			}
			
			if (backNum > 0) {
				backNum--;	//次数减一
				$("#backNum").html(backNum);
				
				//将下注信息显示在页面上
				var backHtml = "";
				var odd = "道";
				if (dataArr && dataArr.length > 0) {
					odd = (getRecordIsSmallRule4(dataArr[0][1][0], dataArr[0][1][1]) ? "单" : "双");
				}
				
				backArr[3].push([css, $(_self).find("select").val(), odd, 4]);
				printBackInfo();
				
				$(obj).find(".trBackWay td." + css).html("<span class='backShow'>￥" + $(_self).find("select").val() + "</span>");
			} else alert("很遗憾，当前可下注次数已不足。");
		});
	});
}

/**
 * 初始化（普通）可配置规则表格
 */
var initConfigRuleTable = function() {
	if (!(currConfigRule && currConfigRule.length > 0)) return;
	
	// 渲染可配置规则表格
	var html = "当前为可配置规则：主车【" + currConfigRule[0] + "】，追车队列【" + currConfigRule[1].toString() + "】<br>"
			+ "初始总金额为：" + CONFIG_ORIGINAL_SUM +  "元。<br>"
			+ "初始下注金额为：" + CONFIG_BACK_MONEY +  "元。<br>"
			+ "<table><thead><tr>"
				+ "<td rowspan='2'>时间</td>"
				+ "<td rowspan='2'>期号</td>"
				+ "<td colspan='10'>开奖号码</td>"
				+ "<td rowspan='2'>下注（元）</td>"
				+ "<td rowspan='2'>盈亏（元）</td>"
				+ "<td rowspan='2'>合计（元）</td></tr>"
			+ "<tr class='trWay'>"
				+ "<td class='1'>1 道</td>"
				+ "<td class='2'>2 道</td>"
				+ "<td class='3'>3 道</td>"
				+ "<td class='4'>4 道</td>"
				+ "<td class='5'>5 道</td>"
				+ "<td class='6'>6 道</td>"
				+ "<td class='7'>7 道</td>"
				+ "<td class='8'>8 道</td>"
				+ "<td class='9'>9 道</td>"
				+ "<td class='10'>10 道</td></tr>"
				+ "</thead>"
			+ "<tbody></tbody></table>";
	$("#recordDivConfigRule").html(html);
	
	//将数据动态填入表格
	var sumMoney = CONFIG_ORIGINAL_SUM;// 当前总金额
	//连中次数
	//当配置的规则猜中一次的后一次，开始下注
	var t = 0;
	var bt = 1; //当前连中下注次数
	var isBegin = false;		//下注开关
	var mainCarIndex = -1;	//当前主车位置
	var lastMainCarIndex = -1;	//上一条主车位置
	var profit = 0;	//	当前盈亏金额
	
	for (var a=dataArr.length-1; a>=0; a--) {
		var numbersArr = dataArr[a][1];
		
		//	下注
		var backMoney = 0;// 当前下注金额
		if (isBegin) {
			if (profit == 0) {
				backMoney = CONFIG_BACK_MONEY;
				bt = 1;
			}
			else if (bt >= configRulesTimes)	{
				backMoney = CONFIG_BACK_MONEY;	//达到连中下注次数上限
				bt = 1;
			} else {
				backMoney = profit;
				bt++;
			}
			sumMoney -= backMoney;
			profit = 0;
		}
		
		var flag = false;	//本条是否猜中
		var num = -1;	//本条猜中的号码
		var isWin = false;	//上一条数据是否猜中
		var bgColor = "";
		if (mainCarIndex > -1) {
//			if ($.inArray(parseInt(numbersArr[mainCarIndex]).toString(), currConfigRule[1]) > 0) flag = true;
			for (var c=0; c<currConfigRule[1].length; c++) {
				if (numbersArr[mainCarIndex]*1 == currConfigRule[1][c]*1) {	//猜中
					flag = true;
					num = currConfigRule[1][c];
					
					bgColor = "configBGcolor configWinBGColor";
					if (!isBegin) {
						isBegin = true;
						bgColor = "";
					}
					
					//本条中奖盈利计算
					var single = parseInt(backMoney / currConfigRule[1].length);
					profit = single * CONFIG_LOTTERY_TIMES;
					sumMoney += profit;
					t++;
					
					//当连中次数达到追车队列总数时，换底色
					if (bt >= configRulesTimes) {
						bgColor = "configBGcolor configAllWinBGColor";
						//将之前连中的数据一同换色
						for (var y=1; y<configRulesTimes; y++) {
							$("#recordDivConfigRule span:contains('" + dataArr[a+y][0] + "')").closest("tr").find("td.configBGcolor").attr("class", "configBGcolor configAllWinBGColor");
						}
					}
				}
			}
			if (!flag) {	//猜错
				t = 0;	//清空连中次数
			}
			isWin = flag;
		}
		
		lastMainCarIndex = mainCarIndex;
		//重新设置主车位置
		for (var b=0; b<numbersArr.length; b++) {
			if (parseInt(numbersArr[b]) == parseInt(currConfigRule[0])) {	
				mainCarIndex = b;
				break;
			}
		}

//		if (flag) {	//当猜中后，将该追车从追车队列中移除
		if (flag && isDeleteFollowCar) {	//当猜中后，将该追车从追车队列中移除
			for (var x=0; x<configRulesArr.length; x++) {
				if (configRulesArr[x][0] == currConfigRule[0]) {
					currConfigRule = deepClone(configRulesArr[x]);
//					currConfigRule[1].splice($.inArray(num.toString(), currConfigRule[1]), 1);
					var index = -1;
					for (var y=0; y<currConfigRule[1].length; y++) {
						if (num.toString() == currConfigRule[1][y]) {
							index = y;
							break;
						}
					}
					currConfigRule[1].splice(index, 1);
					break;
				}
			}
		}
		
		//拼接页面效果
		var numbersHtml = "";
		for (var d=0; d<numbersArr.length; d++) {
			var htm = "<td><span class='numberBGColor'>" + numbersArr[d] + "</span></td>";
			if (d == mainCarIndex)	//当前主车位置
				htm = "<td><span class='mainCarBGColor'>" + numbersArr[d] + "</span></td>";
			if (numbersArr[lastMainCarIndex]*1 == num && lastMainCarIndex == d)	//上一条主车位置，判断是否猜中
				htm = "<td><span class='winBGColor'>" + numbersArr[d] + "</span></td>";
				
			numbersHtml += htm;
		}
		
		var trHtml = "<tr>"
				+ "<td>"  + dataArr[a][2] +"</td>" 
				+ "<td><span class='time'>"  + dataArr[a][0] +"<span></td>" + numbersHtml 
				+ "<td class='" + bgColor + "'>" + backMoney + "</td>" 
				+ "<td class='" + bgColor + "'>" + profit + "</td>"
				+ "<td class='" + bgColor + "'>" + sumMoney + "</td>" + 
			"</tr>";
		if ($("#recordDivConfigRule tbody tr").length > 0) {
			$("#recordDivConfigRule tbody tr:eq(0)").before(trHtml);
		} else {
			$("#recordDivConfigRule tbody").append(trHtml);
		}
	}
}


/**
 * 初始化（首尾）可配置规则表格
 */
var initHeadTailConfigRuleTable = function() {
	if (!(currConfigRule && currConfigRule.length > 0)) return;
	
	// 渲染可配置规则表格
	var html = "当前为可配置规则：主车【" + currConfigRule[0] + "】，追车队列【首尾】<br>"
			+ "初始总金额为：" + CONFIG_ORIGINAL_SUM +  "元。<br>"
			+ "初始下注金额为：" + CONFIG_BACK_MONEY +  "元。<br>"
			+ "<table><thead><tr>"
				+ "<td rowspan='2'>时间</td>"
				+ "<td rowspan='2'>期号</td>"
				+ "<td colspan='10'>开奖号码</td>"
				+ "<td rowspan='2'>下注（元）</td>"
				+ "<td rowspan='2'>盈亏（元）</td>"
				+ "<td rowspan='2'>合计（元）</td></tr>"
			+ "<tr class='trWay'>"
				+ "<td class='1'>1 道</td>"
				+ "<td class='2'>2 道</td>"
				+ "<td class='3'>3 道</td>"
				+ "<td class='4'>4 道</td>"
				+ "<td class='5'>5 道</td>"
				+ "<td class='6'>6 道</td>"
				+ "<td class='7'>7 道</td>"
				+ "<td class='8'>8 道</td>"
				+ "<td class='9'>9 道</td>"
				+ "<td class='10'>10 道</td></tr>"
				+ "</thead>"
			+ "<tbody></tbody></table>";
	$("#recordDivConfigRule").html(html);
	
	//将数据动态填入表格
	var sumMoney = CONFIG_ORIGINAL_SUM;// 当前总金额
	//连中次数
	//当配置的规则猜中一次的后一次，开始下注
	var t = 0;
	var bt = 1; //当前连中下注次数
	var isBegin = false;	
	var mainCarIndex = -1;	//当前主车位置
	var lastMainCarIndex = -1;	//上一条主车位置
	var profit = 0;	//	当前盈亏金额
	
	for (var a=dataArr.length-1; a>=0; a--) {
		var numbersArr = dataArr[a][1];
		
		//	下注
		var backMoney = 0;// 当前下注金额
		if (isBegin) {
			if (profit == 0) {
				backMoney = CONFIG_BACK_MONEY;
				bt = 1;
			}
			else if (bt >= configRulesTimes)	{
				backMoney = CONFIG_BACK_MONEY;	//达到连中下注次数上限
				bt = 1;
			} else {
				backMoney = profit;
				bt++;
			}
			sumMoney -= backMoney;
			profit = 0;
		}
		
		var flag = false;	//本条是否猜中
		var num = -1;	//本条猜中的号码
		var isWin = false;	//上一条数据是否猜中
		var bgColor = "";
		if (mainCarIndex > -1) {
			for (var c=0; c<currConfigRule[1].length; c++) {
				if (numbersArr[mainCarIndex]*1 == currConfigRule[1][c]*1) {	//猜中
					flag = true;
					num = currConfigRule[1][c];

//					if (!isBegin) isBegin = true;
					bgColor = "configBGcolor configWinBGColor";
					if (!isBegin) {
						isBegin = true;
						bgColor = "";
					}
					
					//本条中奖盈利计算
					var single = parseInt(backMoney / currConfigRule[1].length);
					profit = single * CONFIG_LOTTERY_TIMES;
					sumMoney += profit;
					t++;

					//当连中次数达到追车队列总数时，换底色
					if (bt >= configRulesTimes) {
						bgColor = "configBGcolor configAllWinBGColor";
						//将之前连中的数据一同换色
						for (var y=1; y<configRulesTimes; y++) {
							$("#recordDivConfigRule span:contains('" + dataArr[a+y][0] + "')").closest("tr").find("td.configBGcolor").attr("class", "configBGcolor configAllWinBGColor");
						}
					}
				}
			}
			if (!flag) {	//猜错
				t = 0;	//清空连中次数
			}
			isWin = flag;
		}

		lastMainCarIndex = mainCarIndex;
		//重新设置主车位置
		for (var b=0; b<numbersArr.length; b++) {
			if (parseInt(numbersArr[b]) == parseInt(currConfigRule[0])) {	
				mainCarIndex = b;

				//计算首尾和
				var su = numbersArr[0]*1 + numbersArr[numbersArr.length-1]*1;
				var carsList = [];
				if (su <= 10) {	//和值小于等于10，由当前数个位的下一个号开始追
					for (var i=1; i<5; i++) {
						carsList.push(((su % 10 + i) % 10) == 0 ? 10 : ((su % 10 + i) % 10));
					}
				} else {	//和值大于10，由当前数个位的号开始追
					for (var i=0; i<4; i++) {
						carsList.push(((su % 10 + i) % 10) == 0 ? 10 : ((su % 10 + i) % 10));
					}
				}
				currConfigRule[1] = carsList;
				
				break;
			}
		}

//		if (flag) {	//当猜中后，将该追车从追车队列中移除
		if (flag && isDeleteFollowCar) {	//当猜中后，将该追车从追车队列中移除
			for (var x=0; x<configRulesArr.length; x++) {
				if (configRulesArr[x][0] == currConfigRule[0]) {
					currConfigRule = deepClone(configRulesArr[x]);
//					currConfigRule[1].splice($.inArray(num.toString(), currConfigRule[1]), 1);
					var index = -1;
					for (var y=0; y<currConfigRule[1].length; y++) {
						if (num.toString() == currConfigRule[1][y]) {
							index = y;
							break;
						}
					}
					currConfigRule[1].splice(index, 1);
					break;
				}
			}
		}
		
		//拼接页面效果
		var numbersHtml = "";
		for (var d=0; d<numbersArr.length; d++) {
			var htm = "<td><span class='numberBGColor'>" + numbersArr[d] + "</span></td>";
			if (d == mainCarIndex)	//当前主车位置
				htm = "<td><span class='mainCarBGColor'>" + numbersArr[d] + "</span></td>";
			if (numbersArr[lastMainCarIndex]*1 == num && lastMainCarIndex == d)	//上一条主车位置，判断是否猜中
				htm = "<td><span class='winBGColor'>" + numbersArr[d] + "</span></td>";
				
			numbersHtml += htm;
		}
		
		var trHtml = "<tr>"
				+ "<td>"  + dataArr[a][2] +"</td>" 
				+ "<td><span class='time'>"  + dataArr[a][0] +"<span></td>" + numbersHtml 
				+ "<td class='" + bgColor + "'>" + backMoney + "</td>" 
				+ "<td class='" + bgColor + "'>" + profit + "</td>"
				+ "<td class='" + bgColor + "'>" + sumMoney + "</td>" + 
			"</tr>";
		if ($("#recordDivConfigRule tbody tr").length > 0) {
			$("#recordDivConfigRule tbody tr:eq(0)").before(trHtml);
		} else {
			$("#recordDivConfigRule tbody").append(trHtml);
		}
	}
}


/**
 * 初始化（冠亚）可配置规则表格
 */
var initChampionConfigRuleTable = function() {
	if (!(currConfigRule && currConfigRule.length > 0)) return;
	
	// 渲染可配置规则表格
	var html = "当前为可配置规则：主车【" + currConfigRule[0] + "】，追车队列【冠亚】<br>"
			+ "初始总金额为：" + CONFIG_ORIGINAL_SUM +  "元。<br>"
			+ "初始下注金额为：" + CONFIG_BACK_MONEY +  "元。<br>"
			+ "<table><thead><tr>"
				+ "<td rowspan='2'>时间</td>"
				+ "<td rowspan='2'>期号</td>"
				+ "<td colspan='10'>开奖号码</td>"
				+ "<td rowspan='2'>下注（元）</td>"
				+ "<td rowspan='2'>盈亏（元）</td>"
				+ "<td rowspan='2'>合计（元）</td></tr>"
			+ "<tr class='trWay'>"
				+ "<td class='1'>1 道</td>"
				+ "<td class='2'>2 道</td>"
				+ "<td class='3'>3 道</td>"
				+ "<td class='4'>4 道</td>"
				+ "<td class='5'>5 道</td>"
				+ "<td class='6'>6 道</td>"
				+ "<td class='7'>7 道</td>"
				+ "<td class='8'>8 道</td>"
				+ "<td class='9'>9 道</td>"
				+ "<td class='10'>10 道</td></tr>"
				+ "</thead>"
			+ "<tbody></tbody></table>";
	$("#recordDivConfigRule").html(html);
	
	//将数据动态填入表格
	var sumMoney = CONFIG_ORIGINAL_SUM;// 当前总金额
	//连中次数
	//当配置的规则猜中一次的后一次，开始下注
	var t = 0;
	var bt = 1; //当前连中下注次数
	var isBegin = false;	
	var mainCarIndex = -1;	//当前主车位置
	var lastMainCarIndex = -1;	//上一条主车位置
	var profit = 0;	//	当前盈亏金额
	
	for (var a=dataArr.length-1; a>=0; a--) {
		var numbersArr = dataArr[a][1];

		//	下注
		var backMoney = 0;// 当前下注金额
		if (isBegin) {
			if (profit == 0) {
				backMoney = CONFIG_BACK_MONEY;
				bt = 1;
			}
			else if (bt >= configRulesTimes)	{
				backMoney = CONFIG_BACK_MONEY;	//达到连中下注次数上限
				bt = 1;
			} else {
				backMoney = profit;
				bt++;
			}
			sumMoney -= backMoney;
			profit = 0;
		}
		
		var flag = false;	//本条是否猜中
		var num = -1;	//本条猜中的号码
		var isWin = false;	//上一条数据是否猜中
		var bgColor = "";
		if (mainCarIndex > -1) {
			for (var c=0; c<currConfigRule[1].length; c++) {
				if (numbersArr[mainCarIndex]*1 == currConfigRule[1][c]*1) {	//猜中
					flag = true;
					num = currConfigRule[1][c];

//					if (!isBegin) isBegin = true;
					bgColor = "configBGcolor configWinBGColor";
					if (!isBegin) {
						isBegin = true;
						bgColor = "";
					}
					//本条中奖盈利计算
					var single = parseInt(backMoney / currConfigRule[1].length);
					profit = single * CONFIG_LOTTERY_TIMES;
					sumMoney += profit;
					t++;

					//当连中次数达到追车队列总数时，换底色
					if (bt >= configRulesTimes) {
						bgColor = "configBGcolor configAllWinBGColor";
						//将之前连中的数据一同换色
						for (var y=1; y<configRulesTimes; y++) {
							$("#recordDivConfigRule span:contains('" + dataArr[a+y][0] + "')").closest("tr").find("td.configBGcolor").attr("class", "configBGcolor configAllWinBGColor");
						}
					}
					
				}
			}
			if (!flag) {	//猜错
				t = 0;	//清空连中次数
			}
			isWin = flag;
		}

		lastMainCarIndex = mainCarIndex;
		//重新设置主车位置
		for (var b=0; b<numbersArr.length; b++) {
			if (parseInt(numbersArr[b]) == parseInt(currConfigRule[0])) {	
				mainCarIndex = b;

				//计算冠亚和
				var su = numbersArr[0]*1 + numbersArr[1]*1;
				var carsList = [];
				if (su <= 10) {	//和值小于等于10，由当前数个位的下一个号开始追
					for (var i=1; i<5; i++) {
						carsList.push(((su % 10 + i) % 10) == 0 ? 10 : ((su % 10 + i) % 10));
					}
				} else {	//和值大于10，由当前数个位的号开始追
					for (var i=0; i<4; i++) {
						carsList.push(((su % 10 + i) % 10) == 0 ? 10 : ((su % 10 + i) % 10));
					}
				}
				currConfigRule[1] = carsList;
				
				break;
			}
		}

//		if (flag) {	//当猜中后，将该追车从追车队列中移除
		if (flag && isDeleteFollowCar) {	//当猜中后，将该追车从追车队列中移除
			for (var x=0; x<configRulesArr.length; x++) {
				if (configRulesArr[x][0] == currConfigRule[0] && configRulesArr[x][2] == currConfigRule[2]) {
					currConfigRule = deepClone(configRulesArr[x]);
//						currConfigRule[1].splice($.inArray(num.toString(), currConfigRule[1]), 1);
					var index = -1;
					for (var y=0; y<currConfigRule[1].length; y++) {
						if (num.toString() == currConfigRule[1][y]) {
							index = y;
							break;
						}
					}
					currConfigRule[1].splice(index, 1);
					break;
				}
			}
		}
		
		//拼接页面效果
		var numbersHtml = "";
		for (var d=0; d<numbersArr.length; d++) {
			var htm = "<td><span class='numberBGColor'>" + numbersArr[d] + "</span></td>";
			if (d == mainCarIndex)	//当前主车位置
				htm = "<td><span class='mainCarBGColor'>" + numbersArr[d] + "</span></td>";
			if (numbersArr[lastMainCarIndex]*1 == num && lastMainCarIndex == d)	//上一条主车位置，判断是否猜中
				htm = "<td><span class='winBGColor'>" + numbersArr[d] + "</span></td>";
				
			numbersHtml += htm;
		}
		
		var trHtml = "<tr>"
				+ "<td>"  + dataArr[a][2] +"</td>" 
				+ "<td><span class='time'>"  + dataArr[a][0] +"<span></td>" + numbersHtml 
				+ "<td class='" + bgColor + "'>" + backMoney + "</td>" 
				+ "<td class='" + bgColor + "'>" + profit + "</td>"
				+ "<td class='" + bgColor + "'>" + sumMoney + "</td>" + 
			"</tr>";
		if ($("#recordDivConfigRule tbody tr").length > 0) {
			$("#recordDivConfigRule tbody tr:eq(0)").before(trHtml);
		} else {
			$("#recordDivConfigRule tbody").append(trHtml);
		}
	}
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

//统计【普通】可配置规则的每日数据
var countConfigRulePerDay = function() {
	countPerDayArr = [];
	if (!(currConfigRule && currConfigRule.length > 0)) return;

	//将数据动态填入表格
	var sumMoney = CONFIG_ORIGINAL_SUM;// 当前总金额
	var complete = 0;	//每日完成连中的次数
	//连中次数
	//当配置的规则猜中一次的后一次，开始下注
	var t = 0;	//连中次数
	var bt = 1; //当前连中下注次数
	var isBegin = false;		//下注开关
	var mainCarIndex = -1;	//当前主车位置
	var lastMainCarIndex = -1;	//上一条主车位置
	var profit = 0;	//	当前盈亏金额
	var max = CONFIG_ORIGINAL_SUM;	//当日最大金额
	var min = CONFIG_ORIGINAL_SUM;	//当日最小金额
	var timee = "";
	for (var i=bigDataArr.length-1; i>=0; i--) {
		var dat = bigDataArr[i][2].split(" ");
		if (timee == "") {	//第一条
			timee = dat[0];
		} else if (timee == dat[0]) {	//判断是否为同一天数据，如“2017/01/01”
			
		} else {
			//统计，数据压入队列
			countPerDayArr.push([timee, sumMoney, complete, max, min]);
			
			timee = dat[0];
			//处理不同日数据时，将变量初始化
			sumMoney = CONFIG_ORIGINAL_SUM;
			t = 0;
			bt = 1;
			isBegin = false;
			mainCarIndex = -1;
			lastMainCarIndex = -1;
			profit = 0;
			complete = 0;
			max = 0;
			min = CONFIG_ORIGINAL_SUM;
		}

		var numbersArr = bigDataArr[i][1];
		//	下注
		var backMoney = 0;// 当前下注金额
		if (isBegin) {
			if (profit == 0) {
				backMoney = CONFIG_BACK_MONEY;
				bt = 1;
			}
			else if (bt >= configRulesTimes)	{
				backMoney = CONFIG_BACK_MONEY;	//达到连中下注次数上限
				bt = 1;
				complete++;
			} else {
				backMoney = profit;
				bt++;
			}
			sumMoney -= backMoney;
			profit = 0;
		}
		
		var flag = false;	//本条是否猜中
		var num = -1;	//本条猜中的号码
		var isWin = false;	//上一条数据是否猜中
		var bgColor = "";
		if (mainCarIndex > -1) {
			for (var c=0; c<currConfigRule[1].length; c++) {
				if (numbersArr[mainCarIndex]*1 == currConfigRule[1][c]*1) {	//猜中
					flag = true;
					num = currConfigRule[1][c];
					
					if (!isBegin) {
						isBegin = true;
					}
					
					//本条中奖盈利计算
					var single = parseInt(backMoney / currConfigRule[1].length);
					profit = single * CONFIG_LOTTERY_TIMES;
					sumMoney += profit;
					t++;
					//当日持有最大金额
					if (max < sumMoney) max = sumMoney;
				}
			}
			if (!flag) {	//猜错
				t = 0;	//清空连中次数
				
				//当日持有最小金额
				if (min > sumMoney) min = sumMoney;
			}
			isWin = flag;
		}
		
		lastMainCarIndex = mainCarIndex;
		//重新设置主车位置
		for (var b=0; b<numbersArr.length; b++) {
			if (parseInt(numbersArr[b]) == parseInt(currConfigRule[0])) {	
				mainCarIndex = b;
				break;
			}
		}

		if (flag && isDeleteFollowCar) {	//当猜中后，将该追车从追车队列中移除
			for (var x=0; x<configRulesArr.length; x++) {
				if (configRulesArr[x][0] == currConfigRule[0]) {
					currConfigRule = deepClone(configRulesArr[x]);
					var index = -1;
					for (var y=0; y<currConfigRule[1].length; y++) {
						if (num.toString() == currConfigRule[1][y]) {
							index = y;
							break;
						}
					}
					currConfigRule[1].splice(index, 1);
					break;
				}
			}
		}
	}
	//统计，最后一天数据压入队列
	countPerDayArr.push([timee, sumMoney, complete, max, min]);

	//开始拼写统计结果表格
	var html = "" ;
	for (var d=0; d<countPerDayArr.length; d++) {
		html += "<tr><td>" + countPerDayArr[d][0] + "</td><td>" + countPerDayArr[d][1] + "</td><td>" + countPerDayArr[d][2] + "</td>"
			+"<td>" + countPerDayArr[d][3] + "</td><td>" + countPerDayArr[d][4] + "</td></tr>";
	}
	$("#countPerDayTable tbody").html(html);
};

//统计【首尾】可配置规则的每日数据
var countHeadTailConfigRulePerDay = function() {
	countPerDayArr = [];
	if (!(currConfigRule && currConfigRule.length > 0)) return;

	//将数据动态填入表格
	var sumMoney = CONFIG_ORIGINAL_SUM;// 当前总金额
	var complete = 0;	//每日完成连中的次数
	//连中次数
	//当配置的规则猜中一次的后一次，开始下注
	var t = 0;	//连中次数
	var bt = 1; //当前连中下注次数
	var isBegin = false;		//下注开关
	var mainCarIndex = -1;	//当前主车位置
	var lastMainCarIndex = -1;	//上一条主车位置
	var profit = 0;	//	当前盈亏金额
	var max = CONFIG_ORIGINAL_SUM;	//当日最大金额
	var min = CONFIG_ORIGINAL_SUM;	//当日最小金额
	var timee = "";
	for (var i=bigDataArr.length-1; i>=0; i--) {
		var dat = bigDataArr[i][2].split(" ");
		if (timee == "") {	//第一条
			timee = dat[0];
		} else if (timee == dat[0]) {	//判断是否为同一天数据，如“2017/01/01”
			
		} else {
			//统计，数据压入队列
			countPerDayArr.push([timee, sumMoney, complete, max, min]);
			
			timee = dat[0];
			//处理不同日数据时，将变量初始化
			sumMoney = CONFIG_ORIGINAL_SUM;
			t = 0;
			bt = 1;
			isBegin = false;
			mainCarIndex = -1;
			lastMainCarIndex = -1;
			profit = 0;
			complete = 0;
			max = 0;
			min = CONFIG_ORIGINAL_SUM;
		}

		var numbersArr = bigDataArr[i][1];
		//	下注
		var backMoney = 0;// 当前下注金额
		if (isBegin) {
			if (profit == 0) {
				backMoney = CONFIG_BACK_MONEY;
				bt = 1;
			}
			else if (bt >= configRulesTimes)	{
				backMoney = CONFIG_BACK_MONEY;	//达到连中下注次数上限
				bt = 1;
				complete++;
			} else {
				backMoney = profit;
				bt++;
			}
			sumMoney -= backMoney;
			profit = 0;
		}
		
		var flag = false;	//本条是否猜中
		var num = -1;	//本条猜中的号码
		var isWin = false;	//上一条数据是否猜中
		var bgColor = "";
		if (mainCarIndex > -1) {
			for (var c=0; c<currConfigRule[1].length; c++) {
				if (numbersArr[mainCarIndex]*1 == currConfigRule[1][c]*1) {	//猜中
					flag = true;
					num = currConfigRule[1][c];
					
					if (!isBegin) {
						isBegin = true;
					}
					
					//本条中奖盈利计算
					var single = parseInt(backMoney / currConfigRule[1].length);
					profit = single * CONFIG_LOTTERY_TIMES;
					sumMoney += profit;
					t++;
					//当日持有最大金额
					if (max < sumMoney) max = sumMoney;
				}
			}
			if (!flag) {	//猜错
				t = 0;	//清空连中次数
				
				//当日持有最小金额
				if (min > sumMoney) min = sumMoney;
			}
			isWin = flag;
		}
		
		lastMainCarIndex = mainCarIndex;
		//重新设置主车位置
		for (var b=0; b<numbersArr.length; b++) {
			if (parseInt(numbersArr[b]) == parseInt(currConfigRule[0])) {
				mainCarIndex = b;

				//计算首尾和
				var su = numbersArr[0]*1 + numbersArr[numbersArr.length-1]*1;
				var carsList = [];
				if (su <= 10) {	//和值小于等于10，由当前数个位的下一个号开始追
					for (var x=1; x<5; x++) {
						carsList.push(((su % 10 + x) % 10) == 0 ? 10 : ((su % 10 + x) % 10));
					}
				} else {	//和值大于10，由当前数个位的号开始追
					for (var z=0; z<4; z++) {
						carsList.push(((su % 10 + z) % 10) == 0 ? 10 : ((su % 10 + z) % 10));
					}
				}
				currConfigRule[1] = carsList;

				break;
			}
		}

		if (flag && isDeleteFollowCar) {	//当猜中后，将该追车从追车队列中移除
			for (var x=0; x<configRulesArr.length; x++) {
				if (configRulesArr[x][0] == currConfigRule[0]) {
					currConfigRule = deepClone(configRulesArr[x]);
					var index = -1;
					for (var y=0; y<currConfigRule[1].length; y++) {
						if (num.toString() == currConfigRule[1][y]) {
							index = y;
							break;
						}
					}
					currConfigRule[1].splice(index, 1);
					break;
				}
			}
		}
	}
	//统计，最后一天数据压入队列
	countPerDayArr.push([timee, sumMoney, complete, max, min]);

	//开始拼写统计结果表格
	var html = "" ;
	for (var d=0; d<countPerDayArr.length; d++) {
		html += "<tr><td>" + countPerDayArr[d][0] + "</td><td>" + countPerDayArr[d][1] + "</td><td>" + countPerDayArr[d][2] + "</td>"
			+"<td>" + countPerDayArr[d][3] + "</td><td>" + countPerDayArr[d][4] + "</td></tr>";
	}
	$("#countPerDayTable tbody").html(html);
};

//统计【冠亚】可配置规则的每日数据
var countChampionConfigRulePerDay = function() {
	countPerDayArr = [];
	if (!(currConfigRule && currConfigRule.length > 0)) return;

	//将数据动态填入表格
	var sumMoney = CONFIG_ORIGINAL_SUM;// 当前总金额
	var complete = 0;	//每日完成连中的次数
	//连中次数
	//当配置的规则猜中一次的后一次，开始下注
	var t = 0;	//连中次数
	var bt = 1; //当前连中下注次数
	var isBegin = false;		//下注开关
	var mainCarIndex = -1;	//当前主车位置
	var lastMainCarIndex = -1;	//上一条主车位置
	var profit = 0;	//	当前盈亏金额
	var max = CONFIG_ORIGINAL_SUM;	//当日最大金额
	var min = CONFIG_ORIGINAL_SUM;	//当日最小金额
	var timee = "";
	for (var i=bigDataArr.length-1; i>=0; i--) {
		var dat = bigDataArr[i][2].split(" ");
		if (timee == "") {	//第一条
			timee = dat[0];
		} else if (timee == dat[0]) {	//判断是否为同一天数据，如“2017/01/01”
			
		} else {
			//统计，数据压入队列
			countPerDayArr.push([timee, sumMoney, complete, max, min]);
			
			timee = dat[0];
			//处理不同日数据时，将变量初始化
			sumMoney = CONFIG_ORIGINAL_SUM;
			t = 0;
			bt = 1;
			isBegin = false;
			mainCarIndex = -1;
			lastMainCarIndex = -1;
			profit = 0;
			complete = 0;
			max = 0;
			min = CONFIG_ORIGINAL_SUM;
		}

		var numbersArr = bigDataArr[i][1];
		//	下注
		var backMoney = 0;// 当前下注金额
		if (isBegin) {
			if (profit == 0) {
				backMoney = CONFIG_BACK_MONEY;
				bt = 1;
			}
			else if (bt >= configRulesTimes)	{
				backMoney = CONFIG_BACK_MONEY;	//达到连中下注次数上限
				bt = 1;
				complete++;
			} else {
				backMoney = profit;
				bt++;
			}
			sumMoney -= backMoney;
			profit = 0;
		}
		
		var flag = false;	//本条是否猜中
		var num = -1;	//本条猜中的号码
		var isWin = false;	//上一条数据是否猜中
		var bgColor = "";
		if (mainCarIndex > -1) {
			for (var c=0; c<currConfigRule[1].length; c++) {
				if (numbersArr[mainCarIndex]*1 == currConfigRule[1][c]*1) {	//猜中
					flag = true;
					num = currConfigRule[1][c];
					
					if (!isBegin) {
						isBegin = true;
					}
					
					//本条中奖盈利计算
					var single = parseInt(backMoney / currConfigRule[1].length);
					profit = single * CONFIG_LOTTERY_TIMES;
					sumMoney += profit;
					t++;
					//当日持有最大金额
					if (max < sumMoney) max = sumMoney;
				}
			}
			if (!flag) {	//猜错
				t = 0;	//清空连中次数
				
				//当日持有最小金额
				if (min > sumMoney) min = sumMoney;
			}
			isWin = flag;
		}
		
		lastMainCarIndex = mainCarIndex;
		//重新设置主车位置
		for (var b=0; b<numbersArr.length; b++) {
			if (parseInt(numbersArr[b]) == parseInt(currConfigRule[0])) {	
				mainCarIndex = b;

				//计算冠亚和
				var su = numbersArr[0]*1 + numbersArr[1]*1;
				var carsList = [];
				if (su <= 10) {	//和值小于等于10，由当前数个位的下一个号开始追
					for (var x=1; x<5; x++) {
						carsList.push(((su % 10 + x) % 10) == 0 ? 10 : ((su % 10 + x) % 10));
					}
				} else {	//和值大于10，由当前数个位的号开始追
					for (var z=0; z<4; z++) {
						carsList.push(((su % 10 + z) % 10) == 0 ? 10 : ((su % 10 + z) % 10));
					}
				}
				currConfigRule[1] = carsList;
				
				break;
			}
		}

		if (flag && isDeleteFollowCar) {	//当猜中后，将该追车从追车队列中移除
			for (var x=0; x<configRulesArr.length; x++) {
				if (configRulesArr[x][0] == currConfigRule[0]) {
					currConfigRule = deepClone(configRulesArr[x]);
					var index = -1;
					for (var y=0; y<currConfigRule[1].length; y++) {
						if (num.toString() == currConfigRule[1][y]) {
							index = y;
							break;
						}
					}
					currConfigRule[1].splice(index, 1);
					break;
				}
			}
		}
	}
	//统计，最后一天数据压入队列
	countPerDayArr.push([timee, sumMoney, complete, max, min]);

	//开始拼写统计结果表格
	var html = "" ;
	for (var d=0; d<countPerDayArr.length; d++) {
		html += "<tr><td>" + countPerDayArr[d][0] + "</td><td>" + countPerDayArr[d][1] + "</td><td>" + countPerDayArr[d][2] + "</td>"
			+"<td>" + countPerDayArr[d][3] + "</td><td>" + countPerDayArr[d][4] + "</td></tr>";
	}
	$("#countPerDayTable tbody").html(html);
};

/**
 * 页面初始化
 */
$(function() {
	//默认显示规则一表格
	$("#recordDivRule1").show();
	
	//初始化下注数组
	backArr = new Array($(".ruleDivClass").length);
	
	//显示规则相关参数
	$(".rule3Big").html(RULE3_BIG);
	$(".rule3Small").html(RULE3_SMALL);
	$(".rule4Odd").html(RULE4_ODD);
	$(".rule4Even").html(RULE4_EVEN);
	
	//显示可下注笔数
	$("#backNum").html(backNum);
	
	//初始化导入历史数据按钮
	$("#histroyRecordButton").click(function() {
		inputHistroyData();
	});
	
	//初始化下注配置信息的添加按钮
	$("#addConfigButton").click(function() {
		var _self = this;
		var name = $(_self).parent().parent().find("td.name input").val();
		var money = $(_self).parent().parent().find("td.money input").val();
		for (var a=0; a<backConfigArr.length; a++) {
			if (backConfigArr[a][0] == name) {
				alert("“下注名称”不可重复，请重新填写。");
				return;
			}
		}
		backConfigArr.push([name, money]);
		
		//将本条数据显示在页面上
		var trHtml = "<tr>" +
			"<td>"  + name +"</td>" +
			"<td>"  + money +"</td>" +
			"<td><button class='removeConfigButton" + name + "'>删除</button></td>" +
		"</tr>";
		if ($("#configTable tbody tr").length > 0) {
			$("#configTable tbody tr:eq(0)").before(trHtml);
		} else {
			$("#configTable tbody").append(trHtml);
		}
		//初始化下注配置信息的删除按钮
		$(".removeConfigButton" + name).click(function() {
			//数组中删除
			var removeName = $(this).parent().parent().find("td.name").text();
			backConfigArr.splice($.inArray(removeName, backConfigArr), 1);
			
			//页面上删除
			$(this).parent().parent().remove();
		});
	});
	
	//初始化（普通）可配置规则-1追34567
	$("#addConfigRules34567Button").click(function() {
		var _self = this;
		var mainCar = 1;
		var followCar = [3, 4, 5, 6, 7];
		
		var carsList = new Array();
		var mainFlag = false;
		for (var a=0; a<configRulesArr.length; a++) {	//查找主车
			if (configRulesArr[a][0] == mainCar && configRulesArr[a][2].toString() == "1") {
				$("button[class='removeConfigRulesButton']").click();
				break;
			}
		}
		configRulesArr.push([mainCar, followCar, "1"]);
		
		initRulesClickButton();
	});
	
	//初始化（普通）可配置规则-1追345678
	$("#addConfigRules345678Button").click(function() {
		var _self = this;
		var mainCar = 1;
		var followCar = [3, 4, 5, 6, 7, 8];
		
		var carsList = new Array();
		var mainFlag = false;
		for (var a=0; a<configRulesArr.length; a++) {	//查找主车
			if (configRulesArr[a][0] == mainCar && configRulesArr[a][2].toString() == "1") {
				$("button[class='removeConfigRulesButton']").click();
				break;
			}
		}
		configRulesArr.push([mainCar, followCar, "1"]);
		
		initRulesClickButton();
	});
	
	//初始化（普通）可配置规则
	$("#addConfigRulesButton").click(function() {
		var _self = this;
		var mainCar = $(_self).parent().parent().find("td.mainCar select").val();
		var followCar = $(_self).parent().parent().find("td.followCar select").val();
		
		var carsList = new Array();
		var mainFlag = false;
		for (var a=0; a<configRulesArr.length; a++) {	//查找主车
			if (configRulesArr[a][0] == mainCar && configRulesArr[a][2].toString() == "1") {
				mainFlag = true;
				carsList = configRulesArr[a][1];
				var followFlag = false;
				for (var b=0; b<carsList.length; b++) {	//查找追车
					if (carsList[b] == followCar) {
						followFlag = true;
						alert("请勿重复添加“所需追车”。");
						return;
					}
				}
				if (!followFlag) {
					configRulesArr[a][1].push(followCar);
					configRulesArr[a][1].sort(function(a, b) {
						// a-b：升序
						// b-a：降序
						return a[0] - b[0];
					});
				}
			}
		}
		if (!mainFlag) {
			carsList.push(followCar);
			configRulesArr.push([mainCar, carsList, "1"]);
		}
		
		initRulesClickButton();
	});
	
	//初始化（首尾）可配置规则
	$("#addHeadTailConfigRulesButton").click(function() {
		var _self = this;
		var mainCar = $(_self).parent().parent().find("td.mainCar select").val();
		
		var carsList = new Array();
		var mainFlag = false;
		for (var a=0; a<configRulesArr.length; a++) {	//查找主车
			if (configRulesArr[a][0] == mainCar && configRulesArr[a][2].toString() == "2") {
				mainFlag = true;
				alert("请勿重复添加“主车”。");
				return;
			}
		}
		if (!mainFlag) {
			configRulesArr.push([mainCar, [], "2"]);
		}
		
		initRulesClickButton();
	});

	//初始化（冠亚）可配置规则
	$("#addChampionConfigRulesButton").click(function() {
		var _self = this;
		var mainCar = $(_self).parent().parent().find("td.mainCar select").val();
		
		var carsList = new Array();
		var mainFlag = false;
		for (var a=0; a<configRulesArr.length; a++) {	//查找主车
			if (configRulesArr[a][0] == mainCar && configRulesArr[a][2].toString() == "3") {
				mainFlag = true;
				alert("请勿重复添加“主车”。");
				return;
			}
		}
		if (!mainFlag) {
			configRulesArr.push([mainCar, [], "3"]);
		}
		
		initRulesClickButton();
	});
	
	/**
	 * 初始化配置规则的点击按钮
	 */
	var initRulesClickButton = function() {
		var html = "";
		for (var i=0; i<configRulesArr.length; i++) {
			var btn = "";
			var cars = "";
			if (configRulesArr[i][2].toString() == "2") {
				btn = "<td><button class='removeHeadTailConfigRulesButton'>删除</button> | <button class='useHeadTailConfigRulesButton'>应用</button></td>";
				cars = "首尾"; 
			} else if (configRulesArr[i][2].toString() == "3") {
				btn = "<td><button class='removeChampionConfigRulesButton'>删除</button> | <button class='useChampionConfigRulesButton'>应用</button></td>";
				cars = "冠亚"; 
			} else {	//普通
				btn = "<td><button class='removeConfigRulesButton'>删除</button> | <button class='useConfigRulesButton'>应用</button></td>";
				cars = configRulesArr[i][1].toString(); 
			}
			
			html += "<tr><td class='mainCar'>"  + configRulesArr[i][0] +"</td>"
					+ "<td>"  + cars + "</td>"
					+ btn + "</tr>";
		}
		$("#rulesTable tbody").html(html);
		
		//初始化下注配置信息的（普通）删除按钮
		$("button[class='removeConfigRulesButton']").each(function(){
			var _self = this;
			$(_self).click(function() {
				var mainCar = $(this).closest("tr").find("td.mainCar").text();
				//数组中删除
//				configRulesArr.splice($.inArray(mainCar, configRulesArr), 1);
				for (var i=0; i<configRulesArr.length; i++) {
					if (configRulesArr[i][0] == mainCar && configRulesArr[i][2].toString() == "1")
						configRulesArr.splice(i, 1);
				}
				
				//页面上删除
				$(this).closest("tr").remove();
			});
		});
		
		//初始化（普通）应用按钮
		$("button[class='useConfigRulesButton']").each(function(){
			var _self = this;
			$(_self).click(function() {
				var mainCar = $(this).closest("tr").find("td.mainCar").text();
				var carsList = [];
				for (var i=0; i<configRulesArr.length; i++) {
					var tmp = "";
					if (configRulesArr[i][0] == mainCar && configRulesArr[i][2].toString() == "1") {
						carsList = configRulesArr[i][1];
					}
				}
				
				//将当前应用的规则存入变量
				currConfigRule = [mainCar, carsList, "1"];
				
				//初始化表格
				initConfigRuleTable();
				
				$("div[id*='recordDivRule']").each(function(){
					$(this).hide();
				});
				$("#recordDivConfigRule").show();
				
				$("#countPerDayTable").hide();
			});
			
			currRulesNum = 5;
		});
		
		//初始化下注配置信息的（首尾）删除按钮
		$("button[class='removeHeadTailConfigRulesButton']").each(function(){
			var _self = this;
			$(_self).click(function() {
				var mainCar = $(this).closest("tr").find("td.mainCar").text();
				//数组中删除
				for (var i=0; i<configRulesArr.length; i++) {
					if (configRulesArr[i][0] == mainCar && configRulesArr[i][2].toString() == "2")
						configRulesArr.splice(i, 1);
				}
				
				//页面上删除
				$(this).closest("tr").remove();
			});
		});
		
		//初始化（首尾）应用按钮
		$("button[class='useHeadTailConfigRulesButton']").each(function(){
			var _self = this;
			$(_self).click(function() {
				var mainCar = $(this).closest("tr").find("td.mainCar").text();
	//		$(".useHeadTailConfigRulesButton" + mainCar).click(function() {
				//将当前应用的规则存入变量
				currConfigRule = [mainCar, [], "2"];
				
				//初始化表格
				initHeadTailConfigRuleTable();
				
				$("div[id*='recordDivRule']").each(function(){
					$(this).hide();
				});
				$("#recordDivConfigRule").show();
				
				$("#countPerDayTable").hide();
			});
			
			currRulesNum = 6;
		});
		

		//初始化下注配置信息的（冠亚）删除按钮
		$("button[class='removeChampionConfigRulesButton']").each(function(){
			var _self = this;
			$(_self).click(function() {
				var mainCar = $(this).closest("tr").find("td.mainCar").text();
	//		$(".removeChampionConfigRulesButton" + mainCar).click(function() {
				//数组中删除
	//			configRulesArr.splice($.inArray([mainCar, [], "2"], configRulesArr), 1);
				for (var i=0; i<configRulesArr.length; i++) {
					if (configRulesArr[i][0] == mainCar && configRulesArr[i][2].toString() == "3")
						configRulesArr.splice(i, 1);
				}
				
				//页面上删除
	//			$(objThis).parent().parent().remove();
				$(this).closest("tr").remove();
			});
		});
		
		//初始化（冠亚）应用按钮
		$("button[class='useChampionConfigRulesButton']").each(function(){
			var _self = this;
			$(_self).click(function() {
				var mainCar = $(this).closest("tr").find("td.mainCar").text();
	//		$(".useChampionConfigRulesButton" + mainCar).click(function() {
				//将当前应用的规则存入变量
				currConfigRule = [mainCar, [], "3"];
				
				//初始化表格
				initChampionConfigRuleTable();
				
				$("div[id*='recordDivRule']").each(function(){
					$(this).hide();
				});
				$("#recordDivConfigRule").show();
				
				$("#countPerDayTable").hide();
			});
			
			currRulesNum = 7;
		});
	}
	
	//导入各道的下注选项按钮
	$("button.importConfig").click(function() {
		backButtonClickRule1($("#recordTableRule1 thead"));
		backButtonClickRule2($("#recordTableRule2 thead"));
		backButtonClickRule3($("#recordTableRule3 thead"));
		backButtonClickRule4($("#recordTableRule4 thead"));
	});
	
	//初始化获取实时数据按钮
	$("#getRecordButton").click(function() {
		getNewestRecord();
//		alert("test = " + parseInt("08") + "; test2 = " + ("08" * 1) + ".");
	});
	
	//初始化可配置规则-连中的下注次数上限（默认三次）
	$("#configRulesTimesButton").click(function() {
		configRulesTimes = $("#configRulesTimesInput").val();
		$("span.configRulesTimesSpan").text(configRulesTimes);
	});
	
	
	//初始化“显示规则”单选框
	$("p.pRules").find("button").click(function() {
		var _self = this;
		var n = $(this).attr("class");
		$("div[id*='recordDivRule']").each(function(){
			$(this).hide();
		});
		$("#recordDivRule" + n).show();
		
		$("#recordDivConfigRule").hide();
		
		currRulesNum = n;
	});

	//初始化获取前一天数据按钮
	$("#getBeforeDataButton").click(function() {
		var dd = 0;	//期号
		if (dataArr && dataArr.length > 0) dd = dataArr[dataArr.length-1][0];
		var ind = -1;
		//找到本页第一条（时间最早）数据所在位置
		for (var a=0; a<bigDataArr.length; a++) {
			if (dd == bigDataArr[a][0]) {
				ind = a;
				break;
			}
		}
		if (bigDataArr[ind+1] && bigDataArr[ind+1].length > 0) {
			var myd = bigDataArr[ind+1][2].split(" ")[0];	//前一条的MMMM/yy/dd
			dataArr = [];
			for (var b=ind+1;b<bigDataArr.length;b++) {
				if (myd == bigDataArr[b][2].split(" ")[0]) {
					dataArr.push(bigDataArr[b]);
				} else break;
			}
		} else {
			alert("没有更早的数据。");
			return;
		}
		
		showRulesDiv();
	});
	
	//初始化获取后一天数据按钮
	$("#getNextDataButton").click(function() {
		var dd = dataArr[0][0];	//期号
		var ind = -1;
		//找到本页最后（时间最晚）数据所在位置
		for (var a=0; a<bigDataArr.length; a++) {
			if (dd == bigDataArr[a][0]) {
				ind = a;
				break;
			}				
		}
		if (bigDataArr[ind-1] && bigDataArr[ind-1].length > 0) {
			var myd = bigDataArr[ind-1][2].split(" ")[0];	//前一条的MMMM/yy/dd
			dataArr = [];
			for (var b=ind-1;b>=0;b--) {
				if (myd == bigDataArr[b][2].split(" ")[0]) {
					dataArr.push(bigDataArr[b]);
				} else break;
			}
		} else {
			alert("没有更晚的数据。");
			return;
		}
		
		showRulesDiv();
	});
	
	//重新显示当前规则表格
	var showRulesDiv = function() {
		dataArr.sort(function(a, b) {
			// a-b：升序
			// b-a：降序
			return b[0] - a[0];
		});
		
		if (currRulesNum == 1) {
			
		} else if (currRulesNum == 2) {
			
		} else if (currRulesNum == 3) {
			
		} else if (currRulesNum == 4) {
			
		} else if (currRulesNum == 5) {
			$("button[class='useConfigRulesButton']").click();
		} else if (currRulesNum == 6) {
			$("button[class='useHeadTailConfigRulesButton']").click();
		} else if (currRulesNum == 7) {
			$("button[class='useChampionConfigRulesButton']").click();
		} else ;
	};
	
	//初始化跳号按钮
	$("#deleteFollowCarButton").change(function() {
		if ($(this).get(0).checked) isDeleteFollowCar = true;
		else isDeleteFollowCar = false;
	});
	
	//初始化统计按钮
	$("#countPerDayButton").click(function() {
		$("#countPerDayTable").show();
		
		if (currRulesNum == 1) {
			
		} else if (currRulesNum == 2) {
			
		} else if (currRulesNum == 3) {
			
		} else if (currRulesNum == 4) {
			
		} else if (currRulesNum == 5) {
			countConfigRulePerDay();
		} else if (currRulesNum == 6) {
			countHeadTailConfigRulePerDay();
		} else if (currRulesNum == 7) {
			countChampionConfigRulePerDay();
		} else ;
	});
	
	//间隔五分钟重新获取最新数据
	setInterval("getNewestRecord()", MINUTE * 60 * 1000);
});