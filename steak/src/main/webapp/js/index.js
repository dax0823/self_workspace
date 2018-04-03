	
//左侧导航树
//点击事件
var leftTreeClick = function(node, event) {
	if (event.childNodes.length == 0) {	//判断是否叶子节点
		var tabPanel = Ext.getCmp('centerPanel_' + event.data.id);
		if (!tabPanel) {	//判断节点是否已存在
			if (event.data.id == 'typeMgr') {
				Ext.getCmp("centerPanel").add({
					id: 'centerPanel_' + event.data.id,
					title : event.parentNode.data.text + "_" + event.data.text
//					closable : true,
					, items: productTypeTree
				});
				productTypeTree.expandAll();
			} else if (event.data.id == 'productMgr') {
				Ext.getCmp("centerPanel").add({
					id: 'centerPanel_' + event.data.id,
					title: event.parentNode.data.text + "_" + event.data.text
					, items : productGrid
				});
				productGridStore.load();
			} else if (event.data.id == 'productStatistics') {
				Ext.getCmp("centerPanel").add({
					id: 'centerPanel_' + event.data.id,
					title: event.data.text
					, html: '<h2>功能建设中...</h2>'
				});
			}
			Ext.getCmp('centerPanel').setActiveTab('centerPanel_' + event.data.id);
		} else Ext.getCmp('centerPanel').setActiveTab(tabPanel);
	}
};
//构建导航树
var leftTreePanel = Ext.create('Ext.tree.Panel', {
//	title : '导航栏',
	root : {
		text : '大牛排',
		expanded : true,
		children : [ {
			text : '商品类别',
//			id : 'typeMgr',
			expanded : true
			, children : [ {
				text : '管理',
				id : 'typeMgr',
				leaf : true
			} ]
		}, {
			text : '商品信息',
			expanded : true,
			children : [ {
				text : '管理',
				id : 'productMgr',
				leaf : true
			}, {
				text : '统计',
				id : 'productStatistics',
				leaf : true
			} ]
		} ]
	},
	listeners : {
		itemclick : leftTreeClick
	}
});

// 初始化
Ext.onReady(function() {
	// 界面主窗口
	Ext.create('Ext.container.Viewport', {
		layout : 'border',
		items : [ {
			region : 'north',
			height : 100,
			html : "<center>超级大牛排</center>",
			autoScroll : true,
			split : true
		}, {
			region : 'west',
			title : '导航栏',
			id : 'west-region',
			width : 230,
			collapsible : true,	//折叠窗口
			split : true
			, items: leftTreePanel
		}, {
			region : 'center',
			xtype: 'tabpanel',
			id: 'centerPanel'
//			, autoDestroy:false
			, items: [{
				title: 'welcome',
				closable : true,
				html : '<h1>欢迎使用大牛排数据辅助系统！<h1>'
			}]
		} ]
	});
});
