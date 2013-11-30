var navEditor;
var leafMenu;
var dirMenu;
var expandAll = false;
var removeFlag = false;
var nodeSelected;
var b64 = new Base64();
i18n.set({  lang: getCookie('locale'),   path: '../scripts/lib/i18n/'});

/**
 * 构造新的节点
 * @param {} text
 * @param {} type
 * @return {}
 */
function getAddedNode(text, id, type, code) {
	var node;
	if (!type) {
		node = {
			id : id,
			text : text,
			code : code,
			leaf : type,
			cls : 'feeds-node'
		};
	} else {
		node = {
			id : id,
			text : text,
			code : code,
			leaf : type,
			iconCls : 'table_gear',
			flag : 'iframe'
		};
	}

	return node;
}

function onTextChange(node, newText, oldText) {
	alert(node + '----' + newText + '----' + oldText);
	if (!titleChangeFlag && newText != oldText) {
		if (newText != null && newText != 'null' && newText != '') {
			//alert("newText="+newText+"   oldText="+oldText+" newType="+typeof(newText));
			Ext.Ajax.request({
						url : '../auth/ajaxUpdateTitle.action',
						success : function(response) {
							var json = Ext.decode(response.responseText);
							if (json.success) {
								//alert('修改成功!');
								//alert('修改成功'+json);
							} else {
								top.Ext.Msg.alert('错误', '修改失败:' + json.msg);
							}
						},
						failure : function() {
							Ext.Msg.show({
										title : "操作失败！",
										msg : "菜单修改失败！",
										buttons : Ext.Msg.OK,
										icon : Ext.MessageBox.ERROR
									});
						},
						params : "ids=" + node.id + "&title=" + newText
								+ "&oldTitle=" + oldText
					});

		}
	}
}

/**
 *节点移动事件
 */
function treeItemMove(node, oldParent, newParent, index) {
	Ext.Ajax.request({
				url : '../auth/ajaxMoveNode.action',
				success : function(response) {
					var json = Ext.decode(response.responseText);
					if (json.success) {
						//alert('修改成功!'+json);
					} else {
						top.Ext.Msg.alert('错误', '移动失败:' + json.msg);
					}
				},
				failure : function() {
					Ext.Msg.show({
								title : "操作失败！",
								msg : "菜单修改失败！",
								buttons : Ext.Msg.OK,
								icon : Ext.MessageBox.ERROR
							});
				},
				params : "ids=" + node.data.id + "&title=" + node.data.text	+ "&oldParentIds=" + oldParent.data.id
						+ "&oldParentTitle=" + oldParent.data.text			+ "&newParentIds=" + newParent.data.id
						+ "&newParentTitle=" + newParent.data.text			+ "&nodeIndexs=" + index
			});
}
/**
 *节点删除事件
 */
function treeItemRemove(parentNode, node) {
	if (removeFlag) {
		Ext.Ajax.request({
					url : '../auth/ajaxRemoveNode.action',
					success : function(response) {
						var json = Ext.decode(response.responseText);
						if (json.success) {
							//alert('修改成功!');
						} else {
							top.Ext.Msg.alert('错误', '删除失败:' + json.msg);
						}
					},
					failure : function() {
						top.Ext.Msg.show({
									title : "操作失败！",
									msg : "菜单修改失败！",
									buttons : Ext.Msg.OK,
									icon : Ext.MessageBox.ERROR
								});
					},
					params : "ids=" + node.data.id + "&title=" + node.data.text
				});
	}
}

function showTreeAll() {
	if (expandAll == false) {
		expandAll = true;
	} else {
		expandAll = false;
	}

	if (expandAll) {
		moduleTreeVar.expandAll();
	} else {
		moduleTreeVar.collapseAll();
	}

}

function setDirMenu() {
	// 设置目录菜单   
	if (!dirMenu) {
		/*
		{   
		        text : "修改标题",   
		        handler : function() {   
		            navEditor.triggerEdit(nodeSelected);   
		        }   
		    }, "-", 
		 */
		dirMenu = new Ext.menu.Menu({
					items : [{
								text : i18n._('Edit'),
								handler : function() {
									FormEditWin.showEditDirWin(nodeSelected);
								}
							}, "-", {
								text : i18n._('Add leaf node'),
								handler : function() {
									FormEditWin.showAddLeafWin(nodeSelected);
								}
							}, "-", {
								text : i18n._('Add directory node'),
								handler : function() {
									FormEditWin.showAddDirWin(nodeSelected);
								}
							}, "-", {
								text : i18n._('Delete'),
								handler : delTreeItemComfirm
							}]
				});
	}
}

function setLeafMenu() {
	// 设置叶子菜单   
	if (!leafMenu) {
		/*
		 {   
		        text : "修改标题",   
		        handler : function() {   
		            navEditor.triggerEdit(nodeSelected);   
		        }   
		    }, "-", 
		 */
		leafMenu = new Ext.menu.Menu({
					items : [{
								text : i18n._('Edit'),
								handler : function() {
									FormEditWin.showEditLeafWin(nodeSelected);
								}
							}, "-", {
								text : i18n._('Delete'),
								handler : delTreeItemComfirm
							}]
				});
	}
}

function setNavEditor() {
	if (!navEditor) {
		/*   
		navEditor = new Ext.tree.TreeEditor(moduleTreeVar, {   
		    allowBlank : false,   
		    ignoreNoChange : true,   
		    blankText : '标题不能为空',   
		    selectOnFocus : true  
		});
		 */
	}
}

// 扩展窗体   
FormEditWin = function() {
	var curFormWin;
	return {
		width : 400,
		height : 370,
		showAddDirWin : function(parentNode) {
			// 显示添加子目录窗口         
			var number = parentNode.indexOf(parentNode.lastChild) + 1;
			var editpage = "../auth/prepare4AddModule.action?parentId="+ parentNode.data.id + "&leafValue=0&number=" + number
					+ "&parentText=" + b64.encode64(parentNode.data.text)+"&parentCode="+ parentNode.data.code;

			var window = this.createWin("windirnew", i18n._('Add directory node'), editpage,
					function(text, code, leafValue, id) {
						// var root_node = getRootNode(parentNode);            
						// root_node.reload();
						
						if(id){
							var addedNode = getAddedNode(text, id, false, code);
							parentNode.appendChild(addedNode);
						}
					});
			window.show();
		},
		showAddLeafWin : function(parentNode) {
			// 显示添加子叶子节点窗口           	        	
			var number = parentNode.indexOf(parentNode.lastChild) + 1;
			var editpage = "../auth/prepare4AddModule.action?parentId="+ parentNode.data.id + "&leafValue=1&number=" + number
					+ "&parentText=" + b64.encode64(parentNode.data.text)+"&parentCode="+ parentNode.data.code;
			var window = this.createWin("winleafnew", i18n._('Add leaf node'), editpage,
					function(text, code, leafValue, id) {
						if(id){
							var addedNode = getAddedNode(text, 0, true, code);
							parentNode.appendChild(addedNode);
						}
					});
			window.show();
		},
		showEditDirWin : function(node) {
			if (node.parentNode == undefined || node.parentNode == null) {
				alert('不能修改根节点工作项');
				return;
			}
			// 显示目录编辑窗口   
			var editpage = "../auth/prepare4EditModule.action?ids="+ node.data.id + "&parentId=" + node.parentNode.data.id
					+ "&title=" + b64.encode64(node.data.text) + "&funccode=" + node.data.code+"&parentText="+ b64.encode64(node.parentNode.data.text)+"&parentCode="+ node.parentNode.data.code;
			var window = this.createWin("win" + node.data.id,
					node.parentNode.data.text, editpage, function(text, code, leafValue, id) {
						if(id){
							node.set("id", id);
							node.set("text", text);
							node.set("code", code);
							var isLeaf=false;
							if(leafValue==1){
								isLeaf=true;
							}
							node.set("leaf", isLeaf);
						}
						

					});
			window.show();
		},
		showEditLeafWin : function(node) {
			// 显示叶子节点编辑窗口   
			var editpage = "../auth/prepare4EditModule.action?ids="+ node.data.id + "&parentId=" + node.parentNode.data.id+"&ifLeaf=1"
					+ "&title=" + b64.encode64(node.data.text) + "&funccode=" + node.data.code +"&parentText="+ b64.encode64(node.parentNode.data.text)+"&parentCode="+ node.parentNode.data.code;
			var window = this.createWin("win" + node.data.id,
					node.parentNode.data.text, editpage, function(text, code, leafValue, id) {
						if(id){
							node.set("id", id);
							node.set("text", text);
							node.set("code", code);
							var isLeaf=false;
							if(leafValue==1){
								isLeaf=true;
							}
							node.set("leaf", isLeaf);
						}
					});
			window.show();
		},
		createWin : function(winId, winTitle, iframePage, closeFun) {
			// 供各类型窗口创建时调用   
			var win = Ext.getCmp(winId);
			if (!win) {
				win = new Ext.Window({
					id : winId,
					title : i18n._('Edit')+"-" + winTitle,
					width : this.width,
					height : this.height,
					maximizable : true,
					modal : true,
					html : "<iframe width='100%' height='100%' frameborder='0' src='"
							+ iframePage + "'></iframe>"
				});
				this.reloadNavNode = closeFun;
			}
			curFormWin = win;
			return win;
		},
		createWin2 : function(winId, winTitle, iframePage, closeFun) {
			// 供各类型窗口创建时调用   
			var win = Ext.getCmp(winId);
			if (!win) {
				win = new Ext.Window({
					id : winId,
					title : winTitle,
					width : 400,
					height : 320,
					maximizable : true,
					modal : true,
					html : "<iframe width='100%' height='100%' frameborder='0' src='"
							+ iframePage + "'></iframe>"
				});
				//this.reloadNavNode = closeFun;   
			}
			curFormWin = win;
			return win;
		},
		reloadNavNode : function(text, code, leafValue, id) {

		},
		close : function() {
			if (curFormWin) {
				curFormWin.close();
			}
		}
	}
}();

function delTreeItem() {
	if (nodeSelected != moduleTreeVar.getRootNode()) {
		removeFlag = true;
		nodeSelected.remove();
		removeFlag = false;
	} else {
		Ext.Msg.alert("警告", "不能删除树的根节点！");
	}
}

function delTreeItemComfirm() {
	Ext.Msg.confirm("确认删除", "确定要删除所选节点吗？", function(btn) {
				if (btn == "yes") {
					delTreeItem();
				}
			});
}

function showTreeMenu(view, node, rec, index, e) {
	//alert('--------showTreeMenu-----'+view+'---'+rec+'---'+node+'--'+index+'--'+e);
	nodeSelected = node;
	moduleTreeVar.getSelectionModel().select(nodeSelected);
	e.stopEvent();
	if (node.data.leaf) {
		// 显示叶子节点菜单   
		leafMenu.showAt(e.getXY());
	} else {
		// 显示目录节点菜单   
		dirMenu.showAt(e.getXY());
	}
	return false;
}
setNavEditor();
setLeafMenu();
setDirMenu();
