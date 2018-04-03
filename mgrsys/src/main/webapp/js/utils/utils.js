/**
 * 工具 js
 */
var PDD = PDD || {};
PDD.Utils = PDD.Utils || {};

/**
 * 请求测试
 */
PDD.Utils.login = function(id, name) {
	var url = "./login.do";
	var param = {
		username : username,	// 丁丁
		pwd : pwd,	// 54e23552b007adf95506be46ee25defb （md5 加密）
		userword : userword	// 丁丁
	};
	var callBack = function(result) {
	};
	// $.getJSON(url, param, callBack);
};

/**
 * 将页面 A 的内容引入页面 B 的 div 中
 * 
 * @param {string}
 *            htmlUrl 页面 A
 * @param {string}
 *            divId 页面 B 的 div
 * @param {string}
 *            func 方法名称
 */
PDD.Utils.includePage = function(htmlUrl, divObj, func) {
	// 若无链接地址，则不操作
	if (!(htmlUrl && $.trim(htmlUrl) != "" && divObj != null))
		return;
	// 首先清空该 div 中的内容
	if ($(divObj).length > 0)
		$(divObj).html("");
	// 以同步方式导入 html 脚本
	$.ajax({
		url : htmlUrl,
		async : false,
		dataType : "html",
		success : function(html) {
			$(divObj).html(html);
			if (func) {
				if (typeof func == "function") 
					func();
				else if (typeof func == "string")
					eval(func);
			}
		}
	});
};

/**
 * 获得上个月的日期
 * date：YYYY-MM-DD
 */
PDD.Utils.getPrevMonth = function(date) {
	var dateTmp = new Date(Date.parse(date.replace(/-/g,   "/"))); //转换成Data();
	var month = "";
	var year = "";
	if ((dateTmp.getMonth() + 1) == 1) {
		month = "12";
		year = dateTmp.getFullYear() - 1;
	} else {
		month = (dateTmp.getMonth()) < 10 ? "0" + dateTmp.getMonth() : dateTmp.getMonth();
		year = dateTmp.getFullYear();
	}
	
	//当月份变更时，每月中的总天数不同，需要自动变化
	var datee = dateTmp.getDate();
	var prevMonthMaxDays = new Date(year, month, 0).getDate();
	if (datee > prevMonthMaxDays)
		datee = prevMonthMaxDays;
	
	datee = datee < 10 ? "0" + datee : datee;
	return year + "-" + month + "-" + datee;
};

/**
 * 获得下个月的日期
 * date：YYYY-MM-DD
 */
PDD.Utils.getNextMonth = function(date) {
	var dateTmp = new Date(Date.parse(date.replace(/-/g,   "/"))); //转换成Data();
	var month = "";
	var year = "";
	if ((dateTmp.getMonth() + 1) == 12) {
		month = "01";
		year = dateTmp.getFullYear() + 1;
	} else {
		month = (dateTmp.getMonth() + 2) < 10 ? "0" + (dateTmp.getMonth() + 2) : (dateTmp.getMonth() + 2);
		year = dateTmp.getFullYear();
	}
	
	//当月份变更时，每月中的总天数不同，需要自动变化
	var datee = dateTmp.getDate();
	var nextMonthMaxDays = new Date(year, month, 0).getDate();
	if (datee > nextMonthMaxDays)
		datee = nextMonthMaxDays;
	
	datee = datee < 10 ? "0" + datee : datee;
	return year + "-" + month + "-" + datee;
};



/**
 * 返回当前日期
 * type：
 * 1：return YYYY-MM-DD HH:mm:ss
 * 2：return YYYY-MM-DD HH:mm:ss.sss
 * 0：return YYYY-MM-DD
 * 3：return YYYY-MM
 * null && else：return YYYY-MM-DD
 */
PDD.Utils.getNowDate = function(type) {
	var nowDate = new Date();
//	nowDate.getYear();        //获取当前年份(2位)
//	nowDate.getFullYear();    //获取完整的年份(4位,1970-????)
//	nowDate.getMonth();       //获取当前月份(0-11,0代表1月)
//	nowDate.getDate();        //获取当前日(1-31)
//	nowDate.getDay();         //获取当前星期X(0-6,0代表星期天)
//	nowDate.getTime();        //获取当前时间(从1970.1.1开始的毫秒数)
//	nowDate.getHours();       //获取当前小时数(0-23)
//	nowDate.getMinutes();     //获取当前分钟数(0-59)
//	nowDate.getSeconds();     //获取当前秒数(0-59)
//	nowDate.getMilliseconds();    //获取当前毫秒数(0-999)
//	nowDate.toLocaleDateString();     //获取当前日期
//	var mytime=nowDate.toLocaleTimeString();     //获取当前时间
//	nowDate.toLocaleString( );        //获取日期与时间
	
	var month = (nowDate.getMonth() + 1) < 10 ? "0" + (nowDate.getMonth() + 1) : (nowDate.getMonth() + 1);
	var date = nowDate.getDate() < 10 ? "0" + nowDate.getDate() : nowDate.getDate();
	var hours = nowDate.getHours() < 10 ? "0" + nowDate.getHours() : nowDate.getHours();
	var mins = nowDate.getMinutes() < 10 ? "0" + nowDate.getMinutes() : nowDate.getMinutes();
	var secs = nowDate.getSeconds() < 10 ? "0" + nowDate.getSeconds() : nowDate.getSeconds();
	
	if (type) {
		if (type == "1") {
			return nowDate.getFullYear() + "-" + month + "-" + date + " " + hours + ":" + mins + ":" + secs;
		} else if (type == "2"){
			return nowDate.getFullYear() + "-" + month + "-" + date + " " + hours + ":" + mins + ":" + secs + "." + nowDate.getMilliseconds();
		} else if (type =="0") {
			return nowDate.getFullYear() + "-" + month + "-" + date;
		} else if (type =="3") {
			return nowDate.getFullYear() + "-" + month;
		}
	}
	
	//默认返回 YYYY-MM-DD
	return nowDate.getFullYear() + "-" + month + "-" + date;
};

/**
 * 获取某月的最大天数
 * @param year
 * @param month
 * @returns
 */
PDD.Utils.getDaysInMonth = function(year, month){
    month = parseInt(month, 10)+1;
    var temp = new Date(year+"/"+month+"/0");
    return temp.getDate();
};

/**
 * 获取当月的最大天数
 */
PDD.Utils.getDaysInCurrMonth = function(){
	var nowDate = new Date();
	var year = nowDate.getFullYear();    //获取完整的年份(4位,1970-????)
	var month = nowDate.getMonth() + 1;       //获取当前月份(0-11,0代表1月)
	return PDD.Utils.getDaysInMonth(year, month);
};

/**
 * 拼接文件名
 * prefix + “_” +YYYYMMDDHHMMSS + “.” + suffix
 * 如：财务总览_20140823173402.csv
 * @param prefix
 * @param suffix
 * @returns {String}
 */
PDD.Utils.getFileName = function(prefix, suffix) {
	var nowDate = new Date();
	var month = (nowDate.getMonth() + 1) < 10 ? "0" + (nowDate.getMonth() + 1) : (nowDate.getMonth() + 1);
	var date = nowDate.getDate() < 10 ? "0" + nowDate.getDate() : nowDate.getDate();
	var hours = nowDate.getHours() < 10 ? "0" + nowDate.getHours() : nowDate.getHours();
	var mins = nowDate.getMinutes() < 10 ? "0" + nowDate.getMinutes() : nowDate.getMinutes();
	var secs = nowDate.getSeconds() < 10 ? "0" + nowDate.getSeconds() : nowDate.getSeconds();
	return prefix + "_" + nowDate.getFullYear() + month + date
		+ hours + mins + secs + "." + suffix;
};

/**
 * 使用 js 脚本将数据生成文件
 * @param targetFolderPath
 * @param fileName
 * @param data
 * @returns {Boolean}
 */
PDD.Utils.createDataFile = function(targetFolderPath, fileName, data) {
	//默认路径为桌面
	if (!(targetFolderPath && $.trim(targetFolderPath).length > 0))
		targetFolderPath = PDD.Cons.exportPath;
	
	var fso, tf;
	fso = new ActiveXObject("Scripting.FileSystemObject");
	// 创建新文件
//	tf = fso.CreateTextFile("c:\\testfile.txt", true);
	tf = fso.CreateTextFile(targetFolderPath + fileName, true);
	// 填写数据，并增加换行符
	tf.WriteLine(content);
	// 增加3个空行
//	tf.WriteBlankLines(3);
	// 填写一行，不带换行符
//	tf.Write("This is a test.");
	
	//遍历数据集，写入文件
	if (data) {
		for (var i=0; i<data.length; i++) {
			var obj = data[i];
			
			tf.WriteLine(obj);
		}
		return true;
	};
	// 关闭文件
	tf.Close();
	
	return false;
};

/**
 * 新增 cookie
 */
PDD.Utils.addCookie = function(name, value, hours, path){
	var name = escape(name);
	var value = escape(value);
	var expires = new Date();
	var time = (hours == null ? 0.5 : hours) * 3600 * 1000;
	expires.setTime(expires.getTime() + time);
	// path=/，表示cookie能在整个网站下使用，path=/temp，表示cookie只能在temp目录下使用
	path = (path == "" || path == null ? "/" : ";path=" + path);
	// GMT(Greenwich Mean Time)是格林尼治平时，现在的标准时间，协调世界时是UTC
	// 参数days只能是数字型
	var _expires = (typeof hours) == "string" ? "" : ";expires=" + expires.toUTCString();
	document.cookie = name + "=" + value + _expires + path;  
};

/**
 * 获取cookie的值，根据cookie的键获取值
 */
PDD.Utils.getCookieValue = function(name){
	// 用处理字符串的方式查找到key对应value
	var name = escape(name);
	// 读cookie属性，这将返回文档的所有cookie
	var allcookies = document.cookie;
	// 查找名为name的cookie的开始位置
	name += "=";
	var pos = allcookies.indexOf(name);
	// 如果找到了具有该名字的cookie，那么提取并使用它的值
	if (pos != -1) { // 如果pos值为-1则说明搜索"version="失败
		var start = pos + name.length; // cookie值开始的位置
		var end = allcookies.indexOf(";", start); // 从cookie值开始的位置起搜索第一个";"的位置,即cookie值结尾的位置
		if (end == -1)
			end = allcookies.length; // 如果end值为-1说明cookie列表里只有一个cookie
		var value = allcookies.substring(start, end); // 提取cookie的值
		return (unescape(value)); // 对它解码
	} else { // 搜索失败，返回空字符串
		return "";
	}  
};

/**
 * 根据cookie的键，删除cookie，其实就是设置其失效
 */
PDD.Utils.deleteCookie = function(name, path){ 
	var name = escape(name);
	var expires = new Date(0);
	path = path == "" ? "" : ";path=" + path;
	document.cookie = name + "=" + ";expires=" + expires.toUTCString() + path;  
};

/**
 * 验证是否为整数
 */
PDD.Utils.isInteger = function(str){
//	var regu = /^[-]{0,1}[0-9]{1,}$/;
	var regu = /^[0-9]*[1-9][0-9]*$/;
	return regu.test(parseInt(str));
};

/**
 * 千位分隔符：将普通数字转换为千位分隔符表示
 * 12345 -> 12,345
 * @param num
 * @returns
 */
PDD.Utils.commafy = function(num) {
	num = num.toFixed(2) +"";
	var re = /(-?\d+)(\d{3})/;
	
	while(re.test(num)){
		num=num.replace(re,"$1,$2");
	}
	return num;
} 

/**
 * 千位分隔符：将千位分隔符转换为普通数字表示
 * 12,345 -> 12345
 * @param num
 * @returns
 */
PDD.Utils.commafyback = function(num) {
	var x = num.split(',');
	return parseFloat(x.join(""));
}