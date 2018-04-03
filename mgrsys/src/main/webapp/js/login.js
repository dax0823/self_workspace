/**
 * 登录
 */
var PDD = PDD || {};
PDD.Login = PDD.Login || {};

/**
 * 页面初始化
 */
$(function() {//若未登录，则跳回登录页面
	if (PDD.Utils.getCookieValue(PDD.Cons.COOKIE_USERID)) {
		window.location.href = "./index.html";
	}
	
	//初始化登录按钮
	$("div.loginForm .loginSubmit").click(function() {
		var username = $("div.loginForm .username").val();
		var pwd = $("div.loginForm .pwd").val();
		var userword = $("div.loginForm .userword").val();
		var captcha = $("div.loginForm .captcha").val();
		
		if ($.trim(username).length == 0) {
			alert("请输入登录名。");
			return;
		} else if ($.trim(pwd).length == 0) {
			alert("请输入密码。");
			return;
		} else if ($.trim(userword).length == 0) {
			alert("请输入登录口令。");
			return;
		} else if ($.trim(captcha).length == 0) {
			alert("请输入验证码。");
			return;
		}
		
		PDD.Login.login(username, pwd, userword, captcha);
	});
	
	//初始化重置按钮
	$("div.loginForm .loginClear").click(function() {
		$("div.loginForm .username").val("");
		$("div.loginForm .pwd").val("");
		$("div.loginForm .userword").val("");
		$("div.loginForm .captcha").val("");
	});
	
	
	//调试 session
	$("div.loginForm .loginTest").click(function() {
		var url = "./loginTest.do";
		var param = {};
		var callBack = function(result) {
			if (result) {
				if (result.code != null && result.code == 0) {
					var obj = result.obj;
					if (obj && obj.id) {
					}
				} else {
					alert(result.msg);
				}
			}
		};
		$.getJSON(url, param, callBack);
	});
	
	
	//初始化验证码按钮
	$("div.loginForm img").click(function() {
		$(this).attr("src", "./gainCaptcha.do?t=" + new Date());
	});

	//添加键盘相应事件
	$(document).keydown(function(event){
		// 判断当event.keyCode 为37时（即左方面键），执行函数to_left();
		// 判断当event.keyCode 为39时（即右方面键），执行函数to_right();
		if (event.keyCode == 37) {
			// do somethings;
		} else if (event.keyCode == 39) {
			// do somethings;
		} else if (event.keyCode == 39) {

		} else if (event.keyCode == 27) { // 按 Esc
			
		} else if (event.keyCode == 113) { // 按 F2
			
		} else if (event.keyCode == 13) { // enter 键
			$("div.loginForm .loginSubmit").click();
		}
	}); 
});

/**
 * 用户登录
 */
PDD.Login.login = function(username, pwd, userword, captcha) {
	var url = "./login.do";
	var param = {
		username : username,	// 丁丁
		pwd : pwd,	// 54e23552b007adf95506be46ee25defb （md5 加密）
		userword : userword,	// 丁丁
		captcha: captcha
	};
	var callBack = function(result) {
		if (result) {
			if (result.code != null && result.code == 0) {
				var obj = result.obj;
				if (obj && obj.id) {
					PDD.Utils.addCookie(PDD.Cons.COOKIE_USERID, obj.id);
					PDD.Utils.addCookie(PDD.Cons.COOKIE_USERNAME, obj.username);
					PDD.Utils.addCookie(PDD.Cons.COOKIE_ISKF, obj.isKf);
					PDD.Utils.addCookie(PDD.Cons.COOKIE_ROLE, obj.uGroupId);
					
					window.location.href = "./index.html";
				}
			} else {
				alert(result.msg);
			}
		}
	};
	$.getJSON(url, param, callBack);
};