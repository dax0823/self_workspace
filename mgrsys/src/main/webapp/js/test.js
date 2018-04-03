/**
 * 测试 js
 */
var PDD = PDD || {};
PDD.Test = {};

/**
 * 请求测试
 */
//PDD.Test.submit = function(id, name) {
//	var url = "./test.do";
//	var param = {
//		id: id,
//		name: name
//	};
//	var callBack = function(result) {
//		alert(123);
//		$("#label").text(result);
//	};
//	
////	$.getJSON(url, param, callBack);
//	$.ajax({
//		url: url,
//		type: "GET",
//		data: param,
//		dataType: "text",
//        success:  callBack,
//        error:  function (XMLHttpRequest, textStatus, errorThrown) {
//        	alert(errorThrown);
//        } 
//	});
//};

PDD.Test.login = function(username, pwd, userword) {
	var url = "./login.do";
	var param = {
			username: username,	//丁丁
			pwd: pwd,	//54e23552b007adf95506be46ee25defb	（md5 加密）
			userword: userword	//丁丁
	};
	var callFunc = function(result) {
		if (result.code == 0) {
			$("#label").text(result.msg);
		} else {
			$("#label").text(result.msg);
		}
	};
	$.getJSON(url, param, callFunc);
//	$.ajax({
//		url: url,
//		type: "GET",
//		data: param,
//		dataType: "text",
//        success:  function(result) {
//        	//if ()
//    		$("#label").text("登录成功");
//    	},
//        error:  function (XMLHttpRequest, textStatus, errorThrown) {
//    		$("#label").text("登录失败");
//    	} 
//	});
};

/**
 * 页面组件初始化
 */
$(function() {
//	$("#username").focus(function() {
//		var val = $(this).val();
//		if(val == "enter id") {
//			$(this).val("");
//		}
//	}).blur(function() {
//		var val = $(this).val();
//		if($.trim(val) == "") {
//			$(this).val("enter id");
//		}
//	});
//	
//	$("#pwd").focus(function() {
//		var val = $(this).val();
//		if(val == "enter name") {
//			$(this).val("");
//		}
//	}).blur(function() {
//		var val = $(this).val();
//		if($.trim(val) == "") {
//			$(this).val("enter name");
//		}
//	});
//	
//	$("#userword").focus(function() {
//		var val = $(this).val();
//		if(val == "enter name") {
//			$(this).val("");
//		}
//	}).blur(function() {
//		var val = $(this).val();
//		if($.trim(val) == "") {
//			$(this).val("enter name");
//		}
//	});
//	userword
	
	$("#btn").click(function() {
//		var id = $("#inputId").val();
//		var name = $("#inputName").val();
//		PDD.Test.submit(id, name);
		var username = $("#username").val();
		var pwd = $("#pwd").val();
		var userword = $("#userword").val();
		PDD.Test.login(username, pwd, userword);
	});
});


Ext.require([
             'Ext.form.*',
             'Ext.layout.container.Absolute',
             'Ext.window.Window'
         ]);
          
         Ext.onReady(function() {
//             var form = Ext.create('Ext.form.Panel', {
//                 layout: 'absolute',
////                 url: 'save-form.php',
//                 defaultType: 'textfield',
////                 border: false,
//                 items: [{
//                     fieldLabel: 'Send To',
//                     fieldWidth: 60,
//                     msgTarget: 'side',
//                     allowBlank: false,
//                     vtype: 'email',
//                     x: 5,
//                     y: 5,
//                     name: 'to',
//                     anchor: '-5'  // anchor width by right offset
//                 }, {
//                     fieldLabel: 'Subject',
//                     fieldWidth: 60,
//                     x: 5,
//                     y: 35,
//                     name: 'subject',
//                     anchor: '-5'  // anchor width by right offset
//                 }, {
//                     x:5,
//                     y: 65,
//                     xtype: 'textarea',
//                     style: 'margin:0',
//                     hideLabel: true,
//                     name: 'msg',
//                     anchor: '-5 -5'  // anchor by right anf bottom offset
//                 }]
//             });
          
         	var form = Ext.create('Ext.form.Panel', {
                 layout: 'border',
                 items: [{  
                     region:'west',  
                     width:200,  
                     layout:'accordion',  
                     layoutConfig:{  
                         titleCollapse:true,  
                         animate:true,  
                         activeOnTop:false  
                     },  
                     items:[{  
                         title:'第一栏',  
                         html:'第一栏'  
                     },{  
                        title:'第二栏',  
                         html:'第二栏'   
                     },{  
                        title:'第三栏',  
                         html:'第三栏'   
                     }]  
                 },{  
                     region:'center',  
                     split:true,  
                     border:true
                 }]
             });
             var win = Ext.create('Ext.window.Window', {
                 autoShow: true,
//                 title: 'Resize Me',
//                 width: 500,
//                 height: 300,
//                 minWidth: 300,
//                 minHeight: 200,
                 layout: 'fit',
                 plain:true,
                 items: form,
          
//                 buttons: [{
//                     text: 'Send'
//                 },{
//                     text: 'Cancel'
//                 }]
             });
         });