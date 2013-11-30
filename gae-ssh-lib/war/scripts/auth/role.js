	var selectedRole;
	/**
	 *	显示对象所有属性
	 */
	function showAttr(obj){
		var str='';
		for(var tmp in obj){
			str=str+tmp+",	";
		}
		alert(str);
	}
	var listenerSearch= function(field, ex) {
        if (ex.getKey() == ex.ENTER) {
             searchRole();
        }
    }
    
    var listenerSearchNode= function(field, ex) {
        if (ex.getKey() == ex.ENTER) {
             searchNode();
        }
    }
    
	queryView.down('#roleNameQuery').addListener('specialkey', listenerSearch);
	queryModule.down('#moduleNameQuery').addListener('specialkey', listenerSearchNode);
	function onAdd() {
		editView.setTitle("${app:i18n('role.newRole')}");
		editForm.getForm().reset();
		editView.show();
		copyAddBut.hide();
  		saveAddBut.show();
	}
	
	function moduleNameFocus(){
    	queryModule.down('[name=moduleName]').focus();
    }
    
    moduleTree.disable();
    moduleTree.on("checkchange",checkedChooseNode);
	function checkedChooseNode(node,checked){
		alert('---checkedChooseNode='+node.id);
	}
	
	function chooseNode(node){
    	//alert("您已经拥有2: " + getSelectMoudles());
        alert('----chooseNode='+node.id);
        moduleTree.getSelectionModel().select(node);
    }
	
	// 查找树节点
	function searchNode() {
		//showAttr(moduleTree);
	     var queryValue = queryModule.down('[name=moduleName]').getValue();
	      if(queryValue!=''){
	          Ext.Ajax.request({
	               url: '../auth/moduleTreeSearch.action',
	               params:{moduleName:queryValue},
	               success:function(response){
	                   var o = Ext.decode(response.responseText);
	                   var path=o.ids;
	                   if(path!=null && !(path=='null')){
	                   		moduleTree.expandPath(path);
	                   		moduleTree.expandPath(path, 'id', "/", onExpandPathComplete);
	                   }	
	                   else{
	                   		Ext.MessageBox.alert("${app:i18n('prompt')}","没有找到匹配的记录", moduleNameFocus);	                   		
	                   }	                  
	               },
	               failure:function(response){
	               },
	               scope:this
	         });
	      }
	 }
	 
	 function onExpandPathComplete(bSuccess, oLastNode) {
	 	//alert('----'+bSuccess+' '+oLastNode+' '+oLastNode.id+' '+oLastNode.isNode);
	  	if(!bSuccess) 
	  		 return;
	  	
	  	 moduleTree.getSelectionModel().select(oLastNode);
	  	 moduleNameFocus();
	 }

	function onEdit() {
		var role = getSelectRole();
		if (role) {
			editView.setTitle("${app:i18n('role.modifyRole')}");
			loadEditRole(role);
			editView.show();
			copyAddBut.hide();
  			saveAddBut.show();
		}
	}

	function getSelectRole() {
		var selected = gridView.getSelectionModel().getSelection();
		selectedRole=selected;
		if (selected.length < 1) {
			Ext.MessageBox.alert("${app:i18n('prompt')}", "${app:i18n('role.newRole')}");
			return null;
		}
		return selected[0];
	}
	
	function onDelete() {
		var role = getSelectRole();
		if (role) {
			Ext.MessageBox.confirm("${app:i18n('prompt')}","${app:i18n('role.confirmDelete')}" + role.get('name') + "?", deleteRecord);
		}
	}

	function deleteRecord(result) {
		if (result == 'yes') {
			var role = getSelectRole();

			Ext.Ajax.request({url: "deleteRole.action",
				params: {id: role.get('id')},
				callback: function(o,s,r) {
					if(s) {
						refreshRole();
					} else {
						Ext.Msg.alert("${app:i18n('prompt')}","${app:i18n('role.deleteFail')}");
					}
				}});
		}
	}
	
	function searchRole(){
		//gridView.getStore().removeAll();
  		//gridView.getStore().baseParams = queryView.getForm().getValues();
  		gridView.getStore().load();
	}
	
	function refreshRole() {
		store_role.loadData([]);
		store_role.load();
	}

	function onReset() {
		if (editForm.down('[name=role.id]').getValue()) {
			var role = gridView.getSelectionModel().getSelection()[0];
			loadEditRole(role);
		} else {
			editForm.getForm().reset();
		}
	}

	function loadEditRole(role) {
		editForm.down('[name=role.id]').setValue(role.get('id'));
		editForm.down('[name=role.name]').setValue(role.get('name'));
		editForm.down('[name=role.description]').setValue(role.get('description'));
		editForm.down('[name=role.updateTm]').setValue(role.get('updateTm'));
		editForm.down('[name=role.createEmpCode]').setValue(role.get('createEmpCode'));
		editForm.down('[name=role.createTm]').setValue(role.get('createTm'));
		editForm.down('[name=role.updateEmpCode]').setValue(role.get('updateEmpCode'));
	}
	
	function copyRole(){
      if(!currentRole){
        Ext.MessageBox.alert("${app:i18n('prompt')}",'请选择角色');
        return;
      }
      editView.show();
      //alert(currentRole+'----'+currentRole.id);
      editForm.down('[name=role.id]').setValue(currentRole.id);
      editForm.down('[name=role.name]').setValue(currentRole.name);
      editForm.down('[name=role.description]').setValue(currentRole.description);
    
      saveAddBut.hide();
      copyAddBut.show();
    }
    
	function onSaveRoleModule() {
		var modules = moduleTree.getChecked();
		var ids = '';
		for(var i = 0; i < modules.length; i++) {
			if (ids == '') {
				ids = modules[i].data.id;
			} else {
				ids += ',' + modules[i].data.id;
			}
		}

		var role = gridView.getSelectionModel().getSelection()[0];
		loadEditRole(role);
		editForm.down('[name=ids]').setValue(ids);

		var wb = Ext.Msg.wait("${app:i18n('waitingMessage')}");
		Ext.Ajax.request({url: "saveRoleModule.action",
				params: editForm.getForm().getValues(),
				callback: function(o,s,r) {
					wb.hide();
					var resp = Ext.JSON.decode(r.responseText);
					if (resp.success) {
						role.set('updateTm', resp.role.updateTm);
					} else {
						Ext.Msg.alert("${app:i18n('prompt')}","${app:i18n('role.saveRoleModuleFail')}");
					}
				}});
	}

	function onModuleTreeLoad() {
		moduleTree.getRootNode().expandChildren();
		loadCurrentRoleModule();
	}

	function onRoleLoad() {
		if (this.getCount() > 0) {
			gridView.getView().select(0);
			if (currentRole == null) {
				currentRole = gridView.getSelectionModel().getSelection()[0].data;
				loadCurrentRoleModule();
			}
		}
	}

	function onRoleChange(v, selections) {
		if (selections.length > 0) {
			currentRole = selections[0].data;
			loadCurrentRoleModule();
		}
	}

	function loadCurrentRoleModule() {
		moduleTree.getView().node.cascadeBy(function(rec) {
        	if (rec.get('checked')) {
            	rec.set('checked', false);
        	}
    	});
		
		if (currentRole) {
			moduleTree.enable();
			Ext.Ajax.request({url: "loadRoleModuleIds.action",
				params: {id:currentRole.id},
				callback: function(o,s,r) {
					if (s && (r.responseText != "")) {
						var ids = r.responseText.substr(1, r.responseText.length - 2).split(",");
						for(var i = 0; i < ids.length; i++) {
							var node = moduleTree.getStore().getNodeById(ids[i]);
							if (node) {
								node.set('checked', true);
							}
						}
					}
				}});
		}
	}