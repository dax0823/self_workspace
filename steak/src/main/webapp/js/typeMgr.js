//商品类别-添加类别表单
var productTypeCoefficientForm = Ext.create("Ext.form.Panel", {
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
		xtype : "hidden",
		name : 'typeId',
	}, {
		xtype : 'textfield',
		fieldLabel : '级别名称',
		name : 'levelName',
		disabled : true
	}, {
		xtype : 'numberfield',
		fieldLabel : '系数',
		name : 'coefficient'
		, allowDecimals: false // 允许小数点
		, allowNegative: false // 允许负数 
	} ]
  , buttons : [ {
		xtype : "button",
		text : "保存", 
		handler : function() {
			var url = '/steak/coefficient/addCoefficientInfo';	//默认为添加
			if (productTypeCoefficientForm.getForm().findField("id").getValue() != '' && productTypeCoefficientForm.getForm().findField("id").getValue() != null) {
				//当表单中id有值时，说明是“编辑”操作
				url = '/steak/coefficient/modifyCoefficientInfo';
			}
			
			Ext.Ajax.request({
				url : url,
				params : {
					id : productTypeCoefficientForm.getForm().findField("id").getValue(),
					typeId : productTypeCoefficientForm.getForm().findField("typeId").getValue(),
					coefficient : productTypeCoefficientForm.getForm().findField("coefficient").getValue()
				},
				success : function(resp, opts) {
					productTypeCoefficientForm.getForm().findField("id").setValue('');
					Ext.Msg.alert('成功', '操作成功！');
					productTypeTreeRefresh();
					productTypeCoefficientDialog.hide();
				},
				failure : function(resp, opts) {
					productTypeCoefficientForm.getForm().findField("id").setValue('');
					Ext.Msg.alert('错误', '操作失败。');
				}
			});
		}
	}, {
		xtype : "button",
		text : "取消", 
		handler : function() {
			productTypeCoefficientDialog.hide();
		}
	} ]
    , buttonAlign : "center"
});

//修改商品系数窗口
var productTypeCoefficientDialog = Ext.create("Ext.window.Window", {
	closeAction : 'hide', // 窗口关闭的方式：hide/close
	resizable : true,
	closable : true, // 是否可以关闭
	modal : true, // 是否为模态窗口
	items : productTypeCoefficientForm
});

//商品类别-类别表单
var productTypeForm = Ext.create("Ext.form.Panel", {
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
		xtype : "hidden",
		name : 'parentId',
	}, {
		xtype : 'textfield',
		fieldLabel : '上级类别名称',
		name : 'parentName',
		disabled : true
	}, {
		xtype : 'textfield',
		fieldLabel : '类别名称',
		name : 'name'
	}, {
		xtype : 'textfield',
		fieldLabel : '基础价格（￥）',
		name : 'price'
	}, {
		xtype : 'combobox',
		fieldLabel : '状态',
		id: 'productTypeFormStatusCombo',
		name : 'status',
		editable : false,
		valueField : 'value',
		displayField : 'text',
		store : new Ext.data.SimpleStore({
			fields : [ 'value', 'text' ],
			data : [ [ '0',  '正常' ], [ '1', '禁用' ] ]
		})
	} ]
  , buttons : [ {
		xtype : "button",
		text : "保存", 
		handler : function() {
			if (productTypeForm.getForm().findField("name").getValue() == '' || productTypeForm.getForm().findField("name").getValue() == null) {
				Ext.Msg.alert('错误', '类别名称不可为空。');
				return;
			}
			var url = '/steak/bizType/addBizTypeInfo';	//默认为添加
			if (productTypeForm.getForm().findField("id").getValue() != '' && productTypeForm.getForm().findField("id").getValue() != null) {
				//当表单中id有值时，说明是“编辑”操作
				url = '/steak/bizType/modifyBizTypeInfo';
			}
			
			Ext.Ajax.request({
				url : url,
				params : {
					id : productTypeForm.getForm().findField("id").getValue(),
					parentId : productTypeForm.getForm().findField("parentId").getValue(),
					status : Ext.getCmp('productTypeFormStatusCombo').getValue(),
					price : productTypeForm.getForm().findField("price").getValue(),
					name : productTypeForm.getForm().findField("name").getValue()
				},
				success : function(resp, opts) {
					Ext.Msg.alert('成功', '操作成功！');
					productTypeTreeRefresh();
					productTypeDialog.hide();
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
			productTypeDialog.hide();
		}
	} ]
    , buttonAlign : "center"
});

// 添加同级窗口、添加子级类别窗口、编辑类别窗口
var productTypeDialog = Ext.create("Ext.window.Window", {
	closeAction : 'hide', // 窗口关闭的方式：hide/close
	resizable : true,
	closable : true, // 是否可以关闭
	modal : true, // 是否为模态窗口
	items : productTypeForm
});

var productTypeTreeRefresh = function() {
	//将本次选中的信息保存后，再刷新
//	lastSelectParentNode = productTypeTree.getSelectionModel().getSelection()[0].parentNode;
//	lastSelectChildNodesSize = productTypeTree.getSelectionModel().getSelection()[0].childNodes.length;
	//清空之前选中的信息
	productTypeTree.getSelectionModel().deselectAll();
	// 重载前，将根节点重置
	productTypeTreeStore.proxy.extraParams.parentId = 'root';
	// 重载
	productTypeTreeStore.load();
	productTypeTree.expandAll();
}

// 商品类别工具条
var productTypeTreeToolBar = Ext.create("Ext.toolbar.Toolbar", {
	items : [ {
		text : '添加类别',
		handler : function() {
			productTypeDialog.setTitle("添加类别");
			
			//清理旧数据
			productTypeForm.getForm().findField("id").setValue('');
			productTypeForm.getForm().findField("price").setValue('0');
			productTypeForm.getForm().findField("price").enable();
			productTypeForm.getForm().findField("name").setValue('');
			productTypeForm.getForm().findField("parentId").setValue('0');
			productTypeForm.getForm().findField("parentName").setValue('无');
			
			Ext.getCmp('productTypeFormStatusCombo').setValue('0');
			Ext.getCmp('productTypeFormStatusCombo').disable();
			
			productTypeDialog.show();
		}
	}, {
		text : '添加同级类别',
		handler : function() {
			productTypeDialog.setTitle("添加同级类别");
			
			//将选中的数据填入表单
			var record = productTypeTree.getSelectionModel().getSelection();
			if (!record || record.length <= 0) {
				Ext.Msg.alert('提示', '请先选择您所需要操作的类别。');
				return;
			}
			productTypeForm.getForm().findField("id").setValue('');
			productTypeForm.getForm().findField("name").setValue('');
			productTypeForm.getForm().findField("price").setValue(record[0].data["price"]);
			productTypeForm.getForm().findField("price").enable();
			
			//上级类别id会用于保存操作：同级窗口set(parentId)，窗口set(id)
			productTypeForm.getForm().findField("parentId").setValue(record[0].data["parentId"]);
			//上级类别名称仅展示使用
//			var parentNode = null;
//			if (record[0].parentNode) 
//				parentNode = record[0].parentNode;
//			else  {
//				parentNode = lastSelectParentNode;
//			}
//			productTypeForm.getForm().findField("parentName").setValue(parentNode.data.name == null ? '无' : parentNode.data.name);
			productTypeForm.getForm().findField("parentName").setValue(record[0].parentNode.data.name == null ? '无' : record[0].parentNode.data.name);
			
			//如果 ext 组件有单独方法支持此操作，可修改
//			Ext.getCmp('productTypeFormStatusCombo').setValue('1');
//			var comboStoreData = Ext.getCmp('productTypeFormStatusCombo').getStore().data.items;
//			for (var i=0; i<comboStoreData.length; i++) {
//				if (comboStoreData[i].data.value == record[0].data["status"] + '') {
//					Ext.getCmp('productTypeFormStatusCombo').setValue(comboStoreData[i].data.value);
//					Ext.getCmp('productTypeFormStatusCombo').setRawValue(comboStoreData[i].data.text);
//					break;
//				}
//			}
			Ext.getCmp('productTypeFormStatusCombo').setValue('0');
			Ext.getCmp('productTypeFormStatusCombo').disable();
			
			productTypeDialog.show();
		}
	}, {
		text : '添加子级类别',
		handler : function() {
			productTypeDialog.setTitle("添加子级类别");
			
			//将选中的数据填入表单
			var record = productTypeTree.getSelectionModel().getSelection();
			if (!record || record.length <= 0) {
				Ext.Msg.alert('提示', '请先选择您所需要操作的类别。');
				return;
			}
			productTypeForm.getForm().findField("id").setValue('');
			productTypeForm.getForm().findField("name").setValue('');
			productTypeForm.getForm().findField("price").setValue(record[0].data["price"]);
			productTypeForm.getForm().findField("price").enable();
			
			//上级类别id会用于保存操作：同级窗口set(parentId)，子级窗口set(id)
			productTypeForm.getForm().findField("parentId").setValue(record[0].data["id"]);
			//上级类别名称仅展示使用
			productTypeForm.getForm().findField("parentName").setValue(record[0].data.name == null ? '无' : record[0].data.name);
			Ext.getCmp('productTypeFormStatusCombo').setValue('0');
			Ext.getCmp('productTypeFormStatusCombo').disable();

			
			productTypeDialog.show();
		}
	}, {
		text : '删除',
		handler : function() {
			//将选中的数据填入表单
			var record = productTypeTree.getSelectionModel().getSelection();
			if (!record || record.length <= 0) {
				Ext.Msg.alert('提示', '请先选择您所需要的类别。');
				return;
			}

			Ext.Msg.confirm("提示!", "删除后，该类别下的子级类别将不可见。\n确定要删除类别“" + record[0].data.name + "”吗?", function(btn) {
				if (btn == "yes") {
					Ext.Ajax.request({
						url : '/steak/bizType/deleteBizTypeInfo',
						params : {
							id : record[0].data.id
						},
						success : function(resp, opts) {
							Ext.Msg.alert('成功', '类别“' + record[0].data.name + '”删除成功！');
							productTypeTreeRefresh();
						},
						failure : function(resp, opts) {
							Ext.Msg.alert('错误', '类别“' + record[0].data.name + '”删除失败。');
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
			productTypeDialog.setTitle("编辑");
			//将选中的数据填入表单
			var record = productTypeTree.getSelectionModel().getSelection();
			if (!record || record.length <= 0) {
				Ext.Msg.alert('提示', '请先选择您所需要操作的类别。');
				return;
			}

			productTypeForm.getForm().findField("id").setValue(record[0].data.id);
			productTypeForm.getForm().findField("name").setValue(record[0].data["name"]);
			productTypeForm.getForm().findField("price").setValue(record[0].data["price"]);
			//当该节点非叶子节点时，价格不可编辑
//			if (record[0].childNodes.length == 0) productTypeForm.getForm().findField("price").disable();
//			else productTypeForm.getForm().findField("price").enable();

			productTypeForm.getForm().findField("parentId").setValue(record[0].data["parentId"]);
			//上级类别名称仅展示使用
			productTypeForm.getForm().findField("parentName").setValue(record[0].parentNode.data.name == null ? '无' : record[0].parentNode.data.name);
			
			Ext.getCmp('productTypeFormStatusCombo').setValue(record[0].data["status"] + '');
			Ext.getCmp('productTypeFormStatusCombo').enable();
			
			productTypeDialog.show();
		}
	}, {
		text : '刷新',
		handler : function() {
			productTypeTreeRefresh();
		}
	}, {
		text : '修改级别系数',
		handler : function() {
			productTypeDialog.setTitle("修改级别系数");
			//将选中的数据填入表单
			var record = productTypeTree.getSelectionModel().getSelection();
			if (!record || record.length <= 0) {
				Ext.Msg.alert('提示', '请先选择您所需要操作的类别。');
				return;
			}

			productTypeCoefficientForm.getForm().findField("id").setValue(record[0].data.coefficientId);
			productTypeCoefficientForm.getForm().findField("typeId").setValue(record[0].data.id);
			productTypeCoefficientForm.getForm().findField("levelName").setValue(record[0].data["name"]);
			productTypeCoefficientForm.getForm().findField("coefficient").setValue(record[0].data["coefficient"] == null ? 100 : record[0].data["coefficient"]);
			
			//当该节点非叶子节点时，系数不可编辑
			if (record[0].childNodes.length == 0) productTypeCoefficientForm.getForm().findField("coefficient").enable();
			else productTypeCoefficientForm.getForm().findField("coefficient").disable();
			
			productTypeCoefficientDialog.show();
		}
	} ]
});

// 商品类别数据源
var productTypeTreeStore = Ext.create('Ext.data.TreeStore', {
    proxy: {
        type: 'ajax'
    }
//    , fields: ["id", "name", "status", "parentId", "price", "remarks", "createTime", "modifyTime"]
});

//商品类别树
var productTypeTree = Ext.create('Ext.tree.Panel', {
	tbar: productTypeTreeToolBar,
//	frame : true,
	useArrows: true,
	rootVisible: false,
	store: productTypeTreeStore,
	columns : [ {
		xtype : 'treecolumn',
		text : '商品类别',
		dataIndex : 'name',
		width : 300
//		,sortable : true
	}, {
		text : '状态',
		dataIndex : 'status',
		flex : 1
		, renderer: function (value, meta, record) {
			if (value == 0) return '<span style="color:green;" >正常</span>';
			else return '<span style="color:red;" >禁用</span>';
		}
	}, {
		text : '基础价格（元/500g）',
		dataIndex : 'price',
		flex : 1
		, renderer: function (value, meta, record) {
			//叶子节点，不显示价格信息
			if (record.childNodes.length == 0) 
				return '';
			else return value;
		}
	}, {
		text : '系数（%）',
		dataIndex : 'coefficient',
		flex : 1,
		sortable : true
		, renderer: function (value, meta, record) {
			//非叶子节点，不显示价格信息
			if (record.childNodes.length > 0) 
				return '';
			else {
				if (value && value != '')
					return value;
				else return '100';
			}
		}
	}, {
		text : '级别价格（元/500g）',
		dataIndex : 'levelPrice',
		flex : 1,
		sortable : true
		, renderer: function (value, meta, record) {
			//非叶子节点，不显示价格信息
			if (record.childNodes.length > 0) 
				return '';
			else {
				if (record.parentNode && record.parentNode.data && record.parentNode.data.price)
					return record.parentNode.data.price * (record.data.coefficient == null ? 100 : record.data.coefficient) / 100;
			}
		}
	} ]
	, listeners : {
		beforeitemexpand : function(node, optd) { // 展开节点之前触发
			productTypeTreeStore.setProxy({
				type : 'ajax',
				url : '/steak/bizType/queryBizTypeByParentId',
				extraParams : {
					parentId : node.data.id
				}
			});
		}
	}
});
