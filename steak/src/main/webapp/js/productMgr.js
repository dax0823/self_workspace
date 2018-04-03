/**
 * 商品流转记录窗口
 */
Ext.define('GoodsTransfer', {
	extend : 'Ext.data.Model',
	fields : [ 'id', 'createTime', 'type', 'source', 'target' ]
});

//商品流转记录数据源
var productTransferGridStore = new Ext.data.Store({//创建Store，在Grid中用到  
	proxy : {
		type: 'ajax'
//		, url : '/steak/goods/queryList'
	},
	autoDestroy: true,
	model : 'GoodsTransfer'
});

//商品流转记录
var productTransferGrid = Ext.create('Ext.grid.Panel', {
//	frame : true,
	store : productTransferGridStore,
	autoScroll : true,
	columns : [ {
		header : '时间',
		dataIndex : 'createTime',
		renderer : function(value, meta, record) {
			return (new Date(value)).pattern("yyyy-MM-dd HH:mm:ss");
//			return new Date(value);
		},
		flex : 1
	}, {
		header : '类别',
		dataIndex : 'type',
		flex : 1,
		renderer : function(value, meta, record) {
			if (value == 0) return '其他';
			else if (value == 1) return '入库';
			else if (value == 2) return '转移';
			else if (value == 3) return '出库';
			else if (value == 4) return '退还';
			else return '其他';
		},
		flex : 1
	}, {
		header : '原所在地',
		dataIndex : 'source',
		flex : 1
	}, {
		header : '现所在地',
		dataIndex : 'target',
		flex : 1
	} ]
});

//商品流转记录窗口
var productTransferDialog = Ext.create("Ext.window.Window", {
    width: 600,
    height: 400,
	closeAction : 'hide', // 窗口关闭的方式：hide/close
//	title : '流转记录',
	resizable : true,
	closable : true, // 是否可以关闭
	modal : true, // 是否为模态窗口
	autoScroll : true,
	items : productTransferGrid
});

/**
 * 商品类别窗口
 */
// 商品类别数据源
var productInfoFormComboTreeStore = Ext.create('Ext.data.TreeStore', {
	proxy : {
		type : 'ajax'
	}
//	 , fields: ["id", "name"]
});

// 商品类别树
var productInfoFormComboTree = Ext.create('Ext.tree.Panel', {
	useArrows: true,
	rootVisible: false,
	animate:true,
	store: productInfoFormComboTreeStore,
	columns : [ {
		xtype : 'treecolumn',
		dataIndex : 'name',
		sortable : false
		, width : 300
	} ],
	listeners : {
		beforeitemexpand : function(node, optd) { // 展开节点之前触发
			productInfoFormComboTreeStore.setProxy({
				type : 'ajax',
				url : '/steak/bizType/queryBizTypeByParentId',
				extraParams : {
					parentId : node.data.id
				}
			});
		},
		itemclick : function( node, event ) {
			if (event.childNodes.length == 0) {
	    		//填入类别信息
		    	productInfoForm.getForm().findField("typeId").setValue(event.data.id),
		    	productInfoForm.getForm().findField("typeName").setValue(event.parentNode.data.name + "->" + event.data.name),
		    	//填入系数信息
		    	productInfoForm.getForm().findField("coefficient").setValue(event.data.coefficient == null ? 100 : event.data.coefficient),
		    	
		    	//触发计算价格操作
		    	productInfoForm.getForm().findField("price").setValue(
		    			//(系数 * 标准价格 * 商品重量) / 500g
		    			((event.data.coefficient == null ? 100 : event.data.coefficient) / 100 * (event.parentNode.data.price == null ? 0 : event.parentNode.data.price)) 
		    				* (productInfoForm.getForm().findField("weight").getValue() == null ? 0 : productInfoForm.getForm().findField("weight").getValue()) / 500
		    	);
		    	
		    	//将上级节点价格填入表单，以备后用
		    	productInfoForm.getForm().findField("parentNodePrice").setValue(event.parentNode.data.price);
		    	
		    	//关闭窗口
				productInfoFormComboTreeDialog.hide();
	    	} 
//			else Ext.Msg.alert('提示', '只有最低级分类可以选择。');
		}
	}
});

//选择商品类别窗口
var productInfoFormComboTreeDialog = Ext.create("Ext.window.Window", {
    width: 330,
    height: 450,
	closeAction : 'hide', // 窗口关闭的方式：hide/close
	title : '商品类别',
	resizable : true,
	closable : true, // 是否可以关闭
	modal : true, // 是否为模态窗口
	autoScroll : true,
	items : productInfoFormComboTree
});

/**
 * 商品详情窗口
 */
// 商品详情-详情表单
var productInfoForm = Ext.create("Ext.form.Panel", {
    margin: 20,
    defaults: {
        anchor: '100%',
    },
    fieldDefaults: {
        labelWidth: 100,
        labelAlign: "right",
        flex: 1
    },
	items : [ {
		xtype : "hidden",
		name : 'id',
	}, {
		xtype : 'textfield',
		fieldLabel : '商品名称',
		name : 'name'
		, allowBlank: false
	}, {
		xtype : 'textfield',
		fieldLabel : '批次号',
		name : 'batch'
	}, {
		xtype : 'textfield',
		fieldLabel : '（业务）编号',
		name : 'serial'
	}, {
		 xtype : "hidden",
		 name : 'parentNodePrice',
	 }, {
		 xtype : "hidden",
		 name : 'typeId',
//	 }, {
//		 xtype : 'textfield',
//		 fieldLabel : '类别名称',
//		 name : 'typeName'
//		 , disabled : true
//	}, 	productInfoFormComboTree, 	{
	}, {
		xtype: 'fieldset',
		border: false,
//	    style: 'margin-top:20px',
	    padding:'0 0 0 0',
	    layout: 'column',
	    items:[{
	    	xtype : 'textfield',
			 fieldLabel : '类别名称',
			 name : 'typeName'
			 , disabled : true
			 , allowBlank: false
	    }, {
	      xtype: 'button',
	      text: '选择类别',
	      handler: function(button) {
	    	  productInfoFormComboTreeDialog.show();
	    	  productInfoFormComboTree.expandAll();
	      }
	    }] 
	}, {
		 xtype : "textfield",
		 fieldLabel : '系数（%）',
		 name : 'coefficient'
		 , disabled : true
	}, {
		xtype : 'numberfield',
		fieldLabel : '重量（g）',
		name : 'weight'
		, allowDecimals: false // 允许小数点
		, allowNegative: false // 允许负数
		, allowBlank: false
		, listeners : {
			change : function(field, newValue, oldValue){
				//触发计算价格操作
		    	productInfoForm.getForm().findField("price").setValue(
		    			//(系数 * 标准价格 * 商品重量) / 500g
		    			((productInfoForm.getForm().findField("coefficient").getValue() == null ? 100 : productInfoForm.getForm().findField("coefficient").getValue()) / 100 
		    					* (productInfoForm.getForm().findField("parentNodePrice").getValue() == null ? 0 : productInfoForm.getForm().findField("parentNodePrice").getValue())) 
		    					* (newValue == null ? 0 : newValue) / 500
		    	);
	    	}
		}
	}, {
		xtype : 'numberfield',
		fieldLabel : '价格（￥）',
		name : 'price'
		, allowDecimals: true // 允许小数点
		, allowNegative: false // 允许负数
		, allowBlank: false
		 , disabled : true
//	}, {
//		xtype : 'textfield',
//		fieldLabel : '所在地',
//		name : 'address'
	}, {
		xtype : 'combobox',
		fieldLabel : '所在地',
		id: 'productInfoFormAddressCombo',
		name : 'address',
//		editable : false,
//		valueField : 'value',
//		displayField : 'text',
		store : new Ext.data.SimpleStore({
			fields : [ 'value', 'text' ],
			data : [ [ '0',  '屠宰场冷库' ], [ '1', '公司冷库' ], [ '2', '公司冰柜' ], [ '3', '发货途中' ], [ '4', '店铺' ], [ '5', '餐厅' ], [ '6', '客户' ], [ '7', '退货途中' ], [ '8', '退回公司冰柜' ], [ '9', '退回公司冷库' ] ]
		})
	}, {
		xtype : 'textfield',
		fieldLabel : '库存',
		name : 'stock'
		, allowDecimals: false // 允许小数点
		, allowNegative: false // 允许负数
		, allowBlank: false
	}, {
		xtype : 'textfield',
		fieldLabel : '总量',
		name : 'sum'
		, allowDecimals: false // 允许小数点
		, allowNegative: false // 允许负数
		, allowBlank: false
	}, {
		xtype : 'textarea',
		fieldLabel : '描述',
		name : 'description'
	} ]
  , buttons : [ {
		xtype : "button",
		text : "保存", 
		handler : function() {
			if (productInfoForm.getForm().findField("name").getValue() == '' || productInfoForm.getForm().findField("name").getValue() == null) {
				Ext.Msg.alert('错误', '商品名称不可为空。');
				return;
			}
			var url = '/steak/goods/addGoodsInfo';	// 默认为添加
			if (productInfoForm.getForm().findField("id").getValue() != '' && productInfoForm.getForm().findField("id").getValue() != null) {
				// 当表单中id有值时，说明是“编辑”操作
				url = '/steak/goods/modifyGoodsInfo';
			}
			
			Ext.Ajax.request({
				url : url,
				params : {
					id : productInfoForm.getForm().findField("id").getValue(),
					name : productInfoForm.getForm().findField("name").getValue(),
					batch : productInfoForm.getForm().findField("batch").getValue(),
					serial : productInfoForm.getForm().findField("serial").getValue(),
					typeId : productInfoForm.getForm().findField("typeId").getValue(),
					weight : productInfoForm.getForm().findField("weight").getValue(),
					price : productInfoForm.getForm().findField("price").getValue(),
					address : productInfoForm.getForm().findField("address").getValue(),
					stock : productInfoForm.getForm().findField("stock").getValue(),
					sum : productInfoForm.getForm().findField("sum").getValue()
					, description : productInfoForm.getForm().findField("description").getValue()
				},
				success : function(resp, opts) {
					Ext.Msg.alert('成功', '操作成功！');
					productGridStore.load();
					productInfoDialog.hide();
				},
				failure : function(resp, opts) {
					Ext.Msg.alert('错误', '操作失败。');
				}
			});
		}
	}, {
		xtype : "button",
		text : "取消", 
		handler : function() {
			productInfoDialog.hide();
		}
	} ]
    , buttonAlign : "center"
});

//修改商品详情窗口
var productInfoDialog = Ext.create("Ext.window.Window", {
	closeAction : 'hide', // 窗口关闭的方式：hide/close
	resizable : true,
	closable : true, // 是否可以关闭
	modal : true, // 是否为模态窗口
	items : productInfoForm
});

/**
 * 商品数据列表
 */
//商品类别工具条
var productInfoToolBar = Ext.create("Ext.toolbar.Toolbar", {
	items : [ {
		text : '添加',
		handler : function() {
			productInfoDialog.setTitle("添加");
			//清理旧数据
			productInfoForm.getForm().findField("id").setValue('');
//			productInfoForm.getForm().findField("name").setValue('');
//			productInfoForm.getForm().findField("batch").setValue('');
//			productInfoForm.getForm().findField("serial").setValue('');
////			productInfoForm.getForm().findField("typeId").setValue(record[0].data["typeId"]);
//			productInfoForm.getForm().findField("typeId").setValue(27);
//			productInfoForm.getForm().findField("typeName").setValue('');
//			productInfoForm.getForm().findField("weight").setValue(0);
//			productInfoForm.getForm().findField("price").setValue(0);
//			productInfoForm.getForm().findField("address").setValue('');
//			productInfoForm.getForm().findField("stock").setValue(1);
//			productInfoForm.getForm().findField("sum").setValue(1);
//			productInfoForm.getForm().findField("description").setValue('');
			productInfoForm.getForm().findField("name").setValue('临时调试');
			productInfoForm.getForm().findField("batch").setValue('调试2016');
			productInfoForm.getForm().findField("serial").setValue('#test');
//			productInfoForm.getForm().findField("typeId").setValue(record[0].data["typeId"]);
//			productInfoForm.getForm().findField("typeId").setValue(27);
//			productInfoForm.getForm().findField("typeName").setValue('调试');
			productInfoForm.getForm().findField("weight").setValue(110);
			productInfoForm.getForm().findField("price").setValue(0);
			productInfoForm.getForm().findField("address").setValue('公司');
			productInfoForm.getForm().findField("stock").setValue(1);
			productInfoForm.getForm().findField("sum").setValue(1);
			productInfoForm.getForm().findField("description").setValue('描述');
			productInfoDialog.show();
			
//			productGridRowEditing.cancelEdit();
//            var rowInfo = Ext.create('Goods', {
//                name: '请输入...',
//                batch: '请输入...',
//                serial: '请输入...',
//                typeId: '27',
//                typeName: 'etst',
//                weight: '0',
//                price: '0',
//                address: '请输入...',
//                stock: '1',
//                sum: '1',
//                description: ''
//            });
//            productGridStore.insert(0, rowInfo);
//            productGridRowEditing.startEdit(0, 0);
		}
	}, {
		text : '删除',
		handler : function() {
			//将选中的数据填入表单
			var record = productGrid.getSelectionModel().getSelection();
			if (!record || record.length <= 0) {
				Ext.Msg.alert('提示', '请先选择您所需要操作的商品。');
				return;
			}

			Ext.Msg.confirm("提示!", "确定要删除商品“" + record[0].data.name + "”吗?", function(btn) {
				if (btn == "yes") {
					Ext.Ajax.request({
						url : '/steak/goods/deleteGoodsInfo',
						params : {
							id : record[0].data.id
						},
						success : function(resp, opts) {
							Ext.Msg.alert('成功', '商品“' + record[0].data.name + '”删除成功！');
							productGridStore.load();
						},
						failure : function(resp, opts) {
							Ext.Msg.alert('错误', '商品“' + record[0].data.name + '”删除失败。');
						}
					});
				} else {
					return;
				}
			});
		}
	}, {
		text : '编辑',
		handler : function() {
			productInfoDialog.setTitle("编辑");
			//将选中的数据填入表单
			var record = productGrid.getSelectionModel().getSelection();
			if (!record || record.length <= 0) {
				Ext.Msg.alert('提示', '请先选择您所需要操作的商品。');
				return;
			}

			productInfoForm.getForm().findField("id").setValue(record[0].data.id);
			productInfoForm.getForm().findField("name").setValue(record[0].data["name"]);
			productInfoForm.getForm().findField("batch").setValue(record[0].data["batch"]);
			productInfoForm.getForm().findField("serial").setValue(record[0].data["serial"]);
//			productInfoForm.getForm().findField("typeId").setValue(record[0].data["typeId"]);
			productInfoForm.getForm().findField("typeId").setValue(27);
			productInfoForm.getForm().findField("typeName").setValue(record[0].data["typeName"]);
			productInfoForm.getForm().findField("weight").setValue(record[0].data["weight"]);
			productInfoForm.getForm().findField("price").setValue(record[0].data["price"]);
			productInfoForm.getForm().findField("address").setValue(record[0].data["address"]);
			productInfoForm.getForm().findField("stock").setValue(record[0].data["stock"]);
			productInfoForm.getForm().findField("sum").setValue(record[0].data["sum"]);
			productInfoForm.getForm().findField("description").setValue(record[0].data["description"]);
			
			productInfoDialog.show();
		}
	}, {
		text : '刷新',
		handler : function() {
			productGridStore.load();
		}
	}, {
		text : '流转记录',
		handler : function() {
			//将选中的数据填入表单
			var record = productGrid.getSelectionModel().getSelection();
			if (!record || record.length <= 0) {
				Ext.Msg.alert('提示', '请先选择您所需要操作的商品。');
				return;
			}
			productTransferDialog.setTitle('"' + record[0].data["name"] + '"-流转记录');
			productTransferGridStore.proxy.url = '/steak/goodsTransfer/queryListByGoodsId?goodsId=' + record[0].data["id"];
			productTransferGridStore.load();
			productTransferDialog.show();
		}
	}	]
});

Ext.define('Goods', {
    extend: 'Ext.data.Model',
    fields: [
		'id', 'name', 'nickname', 'batch', 'serial', 'typeId', 'typeName', 'pics', 'weight', 'price', 'color', 'style'
		, 'brand', 'address', 'description', 'stock', 'sum', 'status', "remarks", "createTime", "modifyTime"
//        { name: 'start', type: 'date', dateFormat: 'n/j/Y' },
//        { name: 'salary', type: 'float' },
//        { name: 'active', type: 'bool' }
    ]
});

//商品信息数据源
var productGridStore = new Ext.data.Store({//创建Store，在Grid中用到  
	proxy : {
		type: 'ajax'
		, url : '/steak/goods/queryList'
	},
//	fields: ['id', 'name', 'nickname', 'batch', 'serial', 'typeId', 'typeName', 'pics', 'weight', 'price', 'color', 'style'
//               , 'brand', 'address', 'description', 'stock', 'sum', 'status', "remarks", "createTime", "modifyTime"]
	autoDestroy: true,
	model : 'Goods'
});

// 商品信息列表
var productGrid = Ext.create('Ext.grid.Panel', {
	tbar : productInfoToolBar,
	// layout : 'fit',
	frame : true,
	store : productGridStore,
	autoScroll : true,
	columns : [ {
		header : '名称',
		dataIndex : 'name',
		flex : 1
	}, {
		header : '类别',
		dataIndex : 'typeName',
		flex : 1
	}, {
		header : '批次号',
		dataIndex : 'batch',
		flex : 1
	}, {
		header : '（业务）编号',
		dataIndex : 'serial',
		flex : 1
	}, {
		header : '重量（g）',
		dataIndex : 'weight',
		flex : 1
	}, {
		header : '价格（元）',
		dataIndex : 'price',
		flex : 1
	}, {
		header : '所在地',
		dataIndex : 'address',
		flex : 1
	}, {
		header : '描述',
		dataIndex : 'description',
		flex : 1
	}, {
		header : '库存',
		dataIndex : 'stock',
		flex : 1
	}, {
		header : '已售出',
		//		, dataIndex : 'sum'
		renderer : function(value, meta, record) {
			return record.data.sum - record.data.stock;
		},
		flex : 1
	} ]
//	, plugins: [productGridRowEditing]
	, listeners: {
	    'rowdblclick': function(view, records) {
			var record = productGrid.getSelectionModel().getSelection();
			productTransferDialog.setTitle('"' + record[0].data["name"] + '"-流转记录');
			productTransferGridStore.proxy.url = '/steak/goodsTransfer/queryListByGoodsId?goodsId=' + record[0].data["id"];
			productTransferGridStore.load();
			productTransferDialog.show();
	    }
	}
});