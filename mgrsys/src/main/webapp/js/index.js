/**
 * 页面初始化 js
 */
$(function() {
	//若未登录，则跳回登录页面
	if (!PDD.Utils.getCookieValue(PDD.Cons.COOKIE_USERID)) {
		window.location.href = "./login.html";
	}
	
	//填充登录的用户名
	$("span.username").text(PDD.Utils.getCookieValue(PDD.Cons.COOKIE_USERNAME));
	//初始化“退出按钮”
	$("a.logout").click(function() {
		PDD.Utils.deleteCookie(PDD.Cons.COOKIE_USERID);
		window.location.href = "./login.html";
	});
	
	// 主页布局初始化
	$("body").layout({
		applyDefaultStyles : true,
		// north__spacing_closed: 8
		// north__resizable: true,
		north__closable : false,
		west__closable : false,
		// resizerTip: "可调整大小"
		north__size : 130,
		west__size : 240,
		// slideTrigger_open: "click",
		// slideTrigger_close: "mouseout",
//		togglerTip_open : "合闭",// pane打开时，当鼠标移动到边框上按钮上，显示的提示语
//		togglerTip_closed : "展开",// pane关闭时，当鼠标移动到边框上按钮上，显示的提示语
//		togglerLength_open : 50,// pane打开时，边框按钮的长度
//		togglerLength_closed : 50,// pane关闭时，边框按钮的长度
//		togglerContent_open : "<",
//		togglerContent_closed : ">",// pane关闭时，同上。
//		enableCursorHotkey : true,// 启用快捷键CTRL或shift + 上下左右。
	});

	// 加载左侧功能树
	PDD.Var.leftTreeObj = $("#leftTree").jstree({
		"themes" : {
			"theme" : "classic",
			"dots" : true,
			"icons" : true
		},
		"core" : {
			"data" : PDD.Cons.LEFT_TREE_DATA
//			"data" : [{
//				id: 1,
//				text: "test"
//			}]
		},
		"plugins" : [ "contextmenu", "dnd", "search", "state", "types", "wholerow" ]
//		, types : {
//			"default" : {
//				clickable : true,
//				draggable : false	// 设置节点不可拖拽
//			},
//			"root" : {
//				valid_children : "folder",	// 设置下级节点类型，可是数组
//				icon : {
//					image : "root1.png"	// 设置当前节点icon图片,路径自己决定
//				}
//			},
//			"folder" : {
//				valid_children : "leaf",
//				icon : {
//					image : "folder1.png"
//				}
//			},
//			"leaf" : {
//				valid_children : "none",
//				icon : {
//					image : "leaf1.png"
//				}
//			}
//		}
	}).bind("select_node.jstree", function(node, data) {
		// 将 center 的内容填充为指定的 html
		if (data.node.original.openUrl && data.node.original.openUrl.length > 0) {
			PDD.Utils.includePage(data.node.original.openUrl, $(".ui-layout-center"), data.node.original.openUrlFunc);
		}
//	}).bind("create.jstree", function(e, data) {
//		$.post("./server.php", //请求的后台的url地址 
//		{
//			"operation" : "create_node", //操作参数 
//			"id" : data.rslt.parent.attr("id").replace("node_", ""),//id参数  
//			"position" : data.rslt.position,//位置参数 
//			"title" : data.rslt.name,//内容参数 
//			"type" : data.rslt.obj.attr("rel")
//		//关系参数 
//		}, function(r) {
//			if (r.status) {
//				$(data.rslt.obj).attr("id", "node_" + r.id);
//			} else {
//				$.jstree.rollback(data.rlbk);
//			}
//		});
//	}).bind("create_node.jstree", function(node, data) {
//		alert(data);
//		var role = PDD.Utils.getCookieValue(PDD.Cons.COOKIE_ROLE);
//		if (role != null && role == 5 && role == 20) {
//			// 仅超级管理员&管理员可见客服提成页面
//		}
	});
	
//	PDD.Var.leftTreeObj.jstree("select_node", null);
	//根据登录用户角色显示页面
//	$("#leftTree").jstree("create", null, "last", { "attr": { "rel": "test"} });
//	PDD.Var.leftTreeObj.jstree("create", function(e, data) {
//		$(data.rslt.obj).attr("id", "node_test").attr("text", "test1111");
//		alert(data);
//		var role = PDD.Utils.getCookieValue(PDD.Cons.COOKIE_ROLE);
//		if (role != null && role == 5 && role == 20) {
//			// 仅超级管理员&管理员可见客服提成页面
//		}
//	});
//	$("#leftTree").jstree("create", null, "last", { "attr": { "rel": "ttttest"} });
//	var js = {
//		attr : {
//			id : "test11111",
//			url : ""
//		},
//		data : {// showHtmlContent('topics/BSS角色场景map090819B1.doc','1282902377544')
//			attr : {
//				'class' : "li-a-b",
//				style : "cursor: pointer;",
//				onclick : "showHtmlContent(\'topics" + url + "\'" + ",\'" + id
//						+ "\')",
//				target : "cFrame_110",
//				unselectable : "on"
//			},
//			// title: "<span onclick=\"getHtmlContent(\'" + id +"\')\">" + name
//			// + "</span>"
//			title : name
//		}
//	}; 
//	$("#leftTree").jstree("create", null, "last", js, null, "test"); 
//	PDD.Var.leftTreeObj.create(checkNode,position,js,null,name); 
//	$("#leftTree").jstree("create", null, "last", {
//		
//        "attr": {
//            "id": "test111"
//        }
//    });
});