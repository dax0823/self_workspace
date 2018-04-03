Ext.define('Ext.ux.CheckCombo',
{
	extend: 'Ext.form.field.ComboBox',
	alias: 'widget.checkcombo',
	multiSelect: true,
	allSelector: false,
	addAllSelector: false,
	allText: 'All',
	createPicker: function() {
	   var me = this,
	       picker,
	       menuCls = Ext.baseCSSPrefix + 'menu',
	       opts = Ext.apply({
	           pickerField: me,
	           selModel: {
	               mode: me.multiSelect ? 'SIMPLE' : 'SINGLE'
	           },
	           floating: true,
	           hidden: true,
	           ownerCt: me.ownerCt,
	           cls: me.el.up('.' + menuCls) ? menuCls : '',
	           store: me.store,
	           displayField: me.displayField,
	           focusOnToFront: false,
	           pageSize: me.pageSize,
	           tpl: 
			[
				'<ul><tpl for=".">',
					'<li role="option" class="' + Ext.baseCSSPrefix + 'boundlist-item"><span class="x-combo-checker">&nbsp;</span> {' + me.displayField + '}</li>',
				'</tpl></ul>'
			]
	       }, me.listConfig, me.defaultListConfig);

	   picker = me.picker = Ext.create('Ext.view.BoundList', opts);
	   if (me.pageSize) {
	       picker.pagingToolbar.on('beforechange', me.onPageChange, me);
	   }		

	   me.mon(picker, {
	       itemclick: me.onItemClick,
	       refresh: me.onListRefresh,
	       scope: me
	   });

	   me.mon(picker.getSelectionModel(), {
	       'beforeselect': me.onBeforeSelect,
	       'beforedeselect': me.onBeforeDeselect,
	       'selectionchange': me.onListSelectionChange,
	       scope: me
	   });

	   return picker;
    },
    getValue: function()
    {
    		return this.value.join(',');
    },
    getSubmitValue: function()
	{
		return this.getValue();
	},
    expand: function()
    {
	   var me = this,
	       bodyEl, picker, collapseIf;

	   if (me.rendered && !me.isExpanded && !me.isDestroyed) {
	       bodyEl = me.bodyEl;
	       picker = me.getPicker();
	       collapseIf = me.collapseIf;

	       // show the picker and set isExpanded flag
	       picker.show();
	       me.isExpanded = true;
	       me.alignPicker();
	       bodyEl.addCls(me.openCls);

			if(me.addAllSelector == true && me.allSelector == false)
			{
				me.allSelector = picker.getEl().insertHtml('afterBegin', '<div class="x-boundlist-item" role="option"><span class="x-combo-checker">&nbsp;</span> '+me.allText+'</div>', true);
				me.allSelector.on('click', function(e)
				{
					if(me.allSelector.hasCls('x-boundlist-selected'))
					{
						me.allSelector.removeCls('x-boundlist-selected');
						me.setValue('');
						me.fireEvent('select', me, []);
					}
					else
					{
						var records = [];
						me.store.each(function(record)
						{
							records.push(record);
						});
						me.allSelector.addCls('x-boundlist-selected');
						me.select(records);
						me.fireEvent('select', me, records); 
					}
				});
			}
	       // monitor clicking and mousewheel
	       me.mon(Ext.getDoc(), {
	           mousewheel: collapseIf,
	           mousedown: collapseIf,
	           scope: me
	       });
	       Ext.EventManager.onWindowResize(me.alignPicker, me);
	       me.fireEvent('expand', me);
	       me.onExpand();
	   }
    },
    onListSelectionChange: function(list, selectedRecords) 
    {
	   var me = this,
	       isMulti = me.multiSelect,
	       hasRecords = selectedRecords.length > 0;
	   // Only react to selection if it is not called from setValue, and if our list is
	   // expanded (ignores changes to the selection model triggered elsewhere)
	   if (me.isExpanded) {
	       if (!isMulti) {
	           Ext.defer(me.collapse, 1, me);
	       }
	       /*
	        * Only set the value here if we're in multi selection mode or we have
	        * a selection. Otherwise setValue will be called with an empty value
	        * which will cause the change event to fire twice.
	        */
	
	       if (isMulti || hasRecords) {
	           me.setValue(selectedRecords, false);
	       }
	       if (hasRecords) {
	           me.fireEvent('select', me, selectedRecords);
	       }
	       me.inputEl.focus();
	   }
	   
		if(me.addAllSelector == true && me.allSelector != false)
		{
			if(selectedRecords.length == me.store.getTotalCount()) me.allSelector.addCls('x-boundlist-selected');
			else me.allSelector.removeCls('x-boundlist-selected'); 
		}    
    }
});

//@ sourceURL=/ux/CheckCombo.js


/*
Tree combo
Use with 'Ext.data.TreeStore'

If store root note has 'checked' property tree combo becomes multiselect combo (tree store must have records with 'checked' property)

Has event 'itemclick' that can be used to capture click

Options:
selectChildren - if set true and if store isn't multiselect, clicking on an non-leaf node selects all it's children
canSelectFolders - if set true and store isn't multiselect clicking on a folder selects that folder also as a value

Use:

single leaf node selector:
selectChildren: false
canSelectFolders: false
- this will select only leaf nodes and will not allow selecting non-leaf nodes

single node selector (can select leaf and non-leaf nodes)
selectChildren: false
canSelectFolders: true
- this will select single value either leaf or non-leaf

children selector:
selectChildren: true
canSelectFolders: true
- clicking on a node will select it's children and node, clicking on a leaf node will select only that node

This config:
selectChildren: true
canSelectFolders: false
- is invalid, you cannot select children without node

*/
Ext.define('Ext.ux.TreeCombo',
{
	extend: 'Ext.form.field.Picker',
	alias: 'widget.treecombo',
	tree: false,
	constructor: function(config)
	{
//		this.addEvents(
//		{
//			"itemclick" : true
//		});

		this.listeners = config.listeners;
		this.callParent(arguments);
	},
	records: [],
	recursiveRecords: [],
	selectChildren: true,
	canSelectFolders: true,
	multiselect: false,
	displayField: 'text',
	valueField: 'id',
	recursivePush: function(node)
	{
		var	me = this;
		me.recursiveRecords.push(node);
		
		node.eachChild(function(nodesingle)
		{
			if(nodesingle.hasChildNodes() == true)
			{
				me.recursivePush(nodesingle);
			}
			else me.recursiveRecords.push(nodesingle);
		});
	},
	recursiveUnPush: function(node)
	{
		var	me = this;
		Ext.Array.remove(me.records, node);
		
		node.eachChild(function(nodesingle)
		{
			if(nodesingle.hasChildNodes() == true)
			{
				me.recursiveUnPush(nodesingle);
			}
			else Ext.Array.remove(me.records, nodesingle);
		});
	},
	afterLoadSetValue: false,
	setValue: function(valueInit)
	{
		if(typeof valueInit == 'undefined') return;
		
		var	me = this,
			tree = this.tree,
			value = valueInit.split(',');
			
		inputEl = me.inputEl;

		if(tree.store.isLoading())
		{
			me.afterLoadSetValue = valueInit;
		}

		if(inputEl && me.emptyText && !Ext.isEmpty(value))
		{
			inputEl.removeCls(me.emptyCls);
		}

		if(tree == false) return false;

		var node = tree.getRootNode();
		if(node == null) return false;
		
		me.recursiveRecords = [];
		me.recursivePush(node);
		
		var valueFin = [];
		var idsFin = [];
		
		if(me.multiselect == true)
		{
			Ext.each(me.recursiveRecords, function(record)
			{
				record.set('checked', false);
			});
		}
		
		me.records = [];
		Ext.each(me.recursiveRecords, function(record)
		{
			var data = record.get(me.valueField);
			Ext.each(value, function(val)
			{
				if(data == val) 
				{
					valueFin.push(record.get(me.displayField));
					idsFin.push(data);
					if(me.multiselect == true) record.set('checked', true);
					me.records.push(record);
				}
			});
		});

		me.value = valueInit;
		me.setRawValue(valueFin.join(', '));
		
		me.checkChange();
		me.applyEmptyText();
		return me;
	},
	getValue: function() 
	{
		return this.value;
	},
	getSubmitValue: function()
	{
		return this.value;
	},
	checkParentNodes: function(node)
	{
		if(node == null) return;
		
		var	me = this,
			checkedAll = true,
			ids = [];
			
		Ext.each(me.records, function(value)
		{
			ids.push(value.get(me.valueField));
		});

		node.eachChild(function(nodesingle)
		{
			if(!Ext.Array.contains(ids, nodesingle.get(me.valueField))) checkedAll = false;
		});
		
		if(checkedAll == true)
		{
			me.records.push(node);
			me.checkParentNodes(node.parentNode);
		}
		else
		{
			Ext.Array.remove(me.records, node);
			me.checkParentNodes(node.parentNode);
		}
	},
	initComponent: function() 
	{
		var	me = this;
		
		me.tree = Ext.create('Ext.tree.Panel',
		{
			alias: 'widget.assetstree',
			hidden: true,
			minHeight: 300,
			rootVisible: (typeof me.rootVisible != 'undefined') ? me.rootVisible : true,
			floating: true,
			useArrows: true,
			store: me.store,
			listeners:
			{
				load: function(store, records)
				{
					if(me.afterLoadSetValue != false)
					{
						me.setValue(me.afterLoadSetValue);
					}
				},
				itemclick: function(view, record, item, index, e, eOpts)
				{
					var values = [];
					
					var node = me.tree.getRootNode().findChild('id', record.get(me.valueField), true);
					if(node == null) 
					{
						if(me.tree.getRootNode().get(me.valueField) == record.get(me.valueField)) node = me.tree.getRootNode();
						else return false;
					}
					
					if(me.multiselect == false) me.records = [];
						
					if(me.canSelectFolders == false && record.get('leaf') == false) return false;
					if(record.get('leaf') == true || me.selectChildren == false) 
					{
						if(me.multiselect == false) me.records.push(record);
						else
						{
							if(record.get('checked') == false) me.records.push(record);
							else Ext.Array.remove(me.records, record);
						}
					}
					else
					{						
						me.recursiveRecords = [];
						if(me.multiselect == false || record.get('checked') == false)
						{
							me.recursivePush(node);
							Ext.each(me.recursiveRecords, function(value)
							{
								if(!Ext.Array.contains(me.records, value)) me.records.push(value);
							});
						}
						else if(record.get('checked') == true)
						{
							me.recursiveUnPush(node);
						}
					}
					
					if(me.canSelectFolders == true) me.checkParentNodes(node.parentNode);
		
					Ext.each(me.records, function(record)
					{
						values.push(record.get(me.valueField));
					});

					me.setValue(values.join(','));
		
					me.fireEvent('itemclick', me, record, item, index, e, eOpts, me.records, values);

					if(me.multiselect == false) me.onTriggerClick();
				}
			}
		});
		
		if(me.tree.getRootNode().get('checked') != null) me.multiselect = true;
		
		this.createPicker = function()
		{
			var	me = this;
			return me.tree;
		};
		
		this.callParent(arguments);
	}	
});

//@ sourceURL=/ux/TreeCombo.js