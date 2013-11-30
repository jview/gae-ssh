	//修改密码--打开窗口
    function modifyPasswordHdl(){
    	var arrCur = userListGrid.getSelectionModel().getSelection();
    	if(arrCur==null||arrCur.length!=1){
    		Ext.MessageBox.alert('${app:i18n('prompt')}','${app:i18n('onlyOneUser')}');
    		return;
    	}
    	modPassFormVar.down('#modUserIdVar').setValue(arrCur[0].data.empId);
    	modPassFormVar.down('#modUserNameVar').setValue(arrCur[0].data.empCode);
    	modPasswordWindow.show();
    }
    //修改密码－－保存
    function saveUserPasswordHdl(){
      var newPasswordVarValue = modPassFormVar.down('#modPass1Var').getValue();
      var newPasswordConfirmVarValue = modPassFormVar.down('#modPass2Var').getValue();
    
      //对密码和确认密码的检验，包括长度为八位，字符不重复
      if(!checkPassword(newPasswordVarValue,newPasswordConfirmVarValue)){
        return ;
      }
    
      var curUserId = modPassFormVar.down('#modUserIdVar').getValue();
      var curUserName = modPassFormVar.down('#modUserNameVar').getValue();
      //alert(curUserId+curUserName);
      Ext.Ajax.request({params: {empCode:curUserName,selectUserId:curUserId,modPassword:newPasswordVarValue},url:"modUserPassword.action",
        success:function(response){
          var result = Ext.decode(response.responseText);
          if(result.msg == "true"){
            Ext.MessageBox.alert('${app:i18n('prompt')}','${app:i18n('modPassSuccess')}');
            modPasswordWindow.hide();
            modReset();
          }else{
            Ext.MessageBox.alert('${app:i18n('prompt')}','${app:i18n('saveFail')}');
          }
        }});
    }
    
    //对密码和确认密码的检验，包括长度为八位，字符不重复
    function checkPassword(password,confirmPassword){
    
      if(password != confirmPassword){
        Ext.MessageBox.alert('${app:i18n('prompt')}','${app:i18n('error.notEqu')}');
        return;
      }
    
      //alert("pass:--"+password.length);
      if(password.length != 8 || confirmPassword.length != 8){
        Ext.MessageBox.alert('${app:i18n('prompt')}','${app:i18n('checkPasswordLength')}');
        return;
      }
    
      //判断是否字符重复
      for(var i =0;i< password.length;i++){
        for(var j=i+1;j< confirmPassword.length;j++){
          if(password.charAt(i) == confirmPassword.charAt(j)){
            Ext.MessageBox.alert('${app:i18n('prompt')}','${app:i18n('checkPasswordChar')}');
            return;
          }
        }
      }
    
      //判断是否数字和英文组成
      var numFlag = 'false';
      var charFlag = 'false';
      for(var i =0;i< password.length;i++){
        if( !(password.charAt(i) >= '0' && password.charAt(i) <= '9') ){
          if( !( password.charAt(i) >= 'A' && password.charAt(i) <= 'Z' ) ){
            if( !( password.charAt(i) >= 'a' && password.charAt(i) <= 'z' )){
              Ext.MessageBox.alert('${app:i18n('prompt')}','${app:i18n('checkPasswordBuild')}');
              return;
            }
          }
        }
    
        if(password.charAt(i) >= '0' && password.charAt(i) <= '9'){
          numFlag = 'true';
        }
        if( ( password.charAt(i) >= 'A' && password.charAt(i) <= 'Z' ) || ( password.charAt(i) >= 'a' && password.charAt(i) <= 'z' )){
          charFlag = 'true';
        }
      }
    
      if(charFlag != 'true' || numFlag != 'true'){
        Ext.MessageBox.alert('${app:i18n('prompt')}','${app:i18n('checkPasswordBuild')}');
        return;
      }
      return true;
    }
    
    
    function modReset(){
      modPassFormVar.down('#modPass1Var').setValue("");
      modPassFormVar.down('#modPass2Var').setValue("");
    }
    
	function navigateTreeSelectDept(view, node){
		if (typeof (node.get("deptCode")) == "undefined" || node.get("deptCode") == "") {
			userListGrid.getStore().removeAll();
			userQueryForm.down('#deptCode').setValue("");
			return;
		}else{
			userQueryForm.down('#deptCode').setValue(node.get("deptCode"));
			onUserSearch();
		}
	}
	
	function beforeUserListStoreLoad(store){
		if (userQueryForm.down('#empCode').getValue() == "" && userQueryForm.down('#empName').getValue() == ""
			&& userQueryForm.down('#deptCode').getValue() == "" ) {
			Ext.Msg.alert('${app:i18n('prompt')}',"${app:i18n('user.queryCondition.mini')}");
			return false;
		}
		store.proxy.extraParams = userQueryForm.getForm().getValues();		
	}
	
	function onUserSearch(){
		userListGrid.getStore().removeAll();
		userListGrid.getStore().load();				
	}
	
	function onRegister(){
		var arrUes = userListGrid.getSelectionModel().getSelection();
		if (arrUes == null || arrUes.length < 1) {
			Ext.Msg.alert('${app:i18n('prompt')}',"${app:i18n('user.register.select')}");
			return;
		}
		var empCodes = "";
		for(var i = 0; i < arrUes.length; ++i){
			var ue = arrUes[i];
			if(ue != null && !ue.get('isValid')){
				if(empCodes.length > 0){
					empCodes += ',';
				}
				empCodes += ue.get('empCode');
			}
		}
		if(empCodes == ""){
			Ext.Msg.alert('${app:i18n('prompt')}','${app:i18n('user.register.noneed')}');
			return;			
		}
		var wb = Ext.Msg.wait('${app:i18n('waitingMessage')}');
		Ext.Ajax.request({
			url : 'addUser.action',
			params:{
				empCode : empCodes
			},
			callback:function(o,s,r){
				wb.hide();
				if(s){
					onUserSearch();
				}else
					Ext.Msg.alert('${app:i18n('prompt')}','${app:i18n('user.register.failed')}');
			}
		});
	}
	
	function showAddLdapWin(){
		addLdapUserWin.show();
	}
	
	function addLdapUsers(){
		var empCodes=addLdapUser.down("#empCode").getValue();
		var deptCode=userQueryForm.down('#deptCode').getValue();
		if(empCodes == ""){
			Ext.Msg.alert('${app:i18n('prompt')}','${app:i18n('user.addLdapUser.empCodeNeed')}');
			return;			
		}
		var wb = Ext.Msg.wait('${app:i18n('waitingMessage')}');
		Ext.Ajax.request({
			url : 'addLdapUsers.action',
			params:{
				empCode : empCodes, deptCode:deptCode
			},
			callback:function(o,s,r){
				wb.hide();
				if(s){
					var msg = Ext.decode(r.responseText).msg;
					if(msg == 'ok'){
						Ext.Msg.alert('${app:i18n('prompt')}', '${app:i18n('saveSuccess')}');
						onUserSearch();
					}else{
						Ext.Msg.alert('${app:i18n('prompt')}', '${app:i18n('user.addLdapUser.exist')}'+msg);
					}
					addLdapUserWin.hide();
				}else
					Ext.Msg.alert('${app:i18n('prompt')}','${app:i18n('user.addLdapUser.failed')}');
			}
		});
	}
	
	function onUnregister(){
		var arrUes = userListGrid.getSelectionModel().getSelection();
		if (arrUes == null || arrUes.length < 1) {
			Ext.Msg.alert('${app:i18n('prompt')}','${app:i18n('user.unregister.select')}');
			return;
		}
		var empCodes = "";
		for(var i = 0; i < arrUes.length; ++i){
			var ue = arrUes[i];
			if(ue != null && ue.get('isValid')){
				if(empCodes.length > 0){
					empCodes += ',';
				}
				empCodes += ue.get('empCode');
			}
		}
		if(empCodes == ""){
			Ext.Msg.alert('${app:i18n('prompt')}','${app:i18n('user.unregister.noneed')}');
			return;			
		}
		
		Ext.Msg.confirm('${app:i18n('prompt')}','${app:i18n('user.unregister.confirm')}',function(e){
			if(e == 'yes'){
				var wb = Ext.Msg.wait('${app:i18n('waitingMessage')}');
				Ext.Ajax.request({
					url : 'deleteUser.action',
					params:{
						empCode : empCodes
					},
					callback:function(o,s,r){
						wb.hide();
						if(s){
							onUserSearch();
						}else
							Ext.Msg.alert('${app:i18n('prompt')}','${app:i18n('user.unregister.failed')}');
					}
				});
			}
		});
	}
	
	function onUserEdit(){
		var arrUes = userListGrid.getSelectionModel().getSelection();
		if (arrUes == null || arrUes.length != 1) {
			Ext.Msg.alert('${app:i18n('prompt')}','${app:i18n('user.edit.select')}');
			return;
		}else if(!arrUes[0].get('isValid')){
			Ext.Msg.alert('${app:i18n('prompt')}','${app:i18n('user.edit.select')}');
			return;
		}
		modUserWin.user = arrUes[0];
		modUserWin.show();
		reloadUserRole();
		modUserWin.role = null;
		roleDeptPanel.down('#roleSelect').setValue('');
		userRoleDeptStore.removeAll();
		modUserWin.isRoleDeptEditing = false;
	}
	
	function beforeUserRoleStoreLoad(store){
		if(modUserWin.user == null){
			return false;
		}
		store.proxy.extraParams.empCode = modUserWin.user.get('empCode');
		return true;
		
	}
	
	function reloadUserRole(){
		userRoleStore.removeAll();
		userRoleStore.load();
	}

	function roleSelectWinShow() {
		roleSelectStore.suspendEvents(false);
		roleSelectStore.load();
	}
	
	function onAddRole(){
		roleSelectWin.isEditingRole = false;
		roleSelectWin.setTitle('${app:i18n('user.role.add')}');
		roleSelectWin.show();
		roleSelectForm.getForm().reset();
		roleSelectForm.down('#roleId').setReadOnly(false);
	}
	
	function onEditRole(grid, rowIndex, colIndex){
		var role = grid.getStore().getAt(rowIndex);
		roleSelectWin.isEditingRole = true;
		roleSelectWin.setTitle('${app:i18n('user.role.edit')}');
		roleSelectWin.show();
		roleSelectForm.getForm().reset();
		roleSelectForm.down('#roleId').setValue(role.get('id'));
		roleSelectForm.down('#roleId').setReadOnly(true);
		roleSelectForm.down('#unusedTm').setValue(role.get('unusedTm'));
	}
	
	function onDeleteRole(grid, rowIndex, colIndex){
		var role = grid.getStore().getAt(rowIndex);
		Ext.Msg.confirm('${app:i18n('prompt')}', "${app:i18n('user.role.delete.confirm')}", function(e){
			if(e == 'yes'){
				var roleId = role.get('id');
				if(modUserWin.role != null && modUserWin.role.get('id') == roleId){
					modUserWin.role = null;
					roleDeptPanel.down('#roleSelect').setValue('');
					userRoleDeptStore.removeAll();
					modUserWin.isRoleDeptEditing = false;
				}
				var roleName = role.get('name');
				var wb = Ext.Msg.wait('${app:i18n('waitingMessage')}');
				Ext.Ajax.request({
					url:'deleteUserRole.action',
					params:{
						empCode: modUserWin.user.get('empCode'),
						roleId: roleId
					},
					callback:function(o,s,r){
						wb.hide();
						if(s){
							reloadUserRole();
						}else
							Ext.Msg.alert('${app:i18n('prompt')}','${app:i18n('user.role.delete.failed')}');
					}
				});
			}
		});
	}
	
	function onSetRoleAsDefault(grid, rowIndex, colIndex){
		var userRole = grid.getStore().getAt(rowIndex);
		if(userRole.get("isDefault") == 1){
			Ext.Msg.alert('${app:i18n('prompt')}','${app:i18n('user.role.setDefault.noneed')}');
			return;
		}
		var userId = userRole.get('userId');
		var roleId = userRole.get('id');

		var wb = Ext.Msg.wait('${app:i18n('waitingMessage')}');
		Ext.Ajax.request({
			url : 'saveUserRoleAsDefault.action',
			params:{
				userId : userId,
				roleId : roleId
			},
			callback:function(o,s,r){
				wb.hide();
				if(s){
					reloadUserRole();
					Ext.Msg.alert('${app:i18n('prompt')}','${app:i18n('user.role.setDefault.success')}');
				}else{
					Ext.Msg.alert('${app:i18n('prompt')}','${app:i18n('user.role.setDefault.failed')}');
				}
			}
		});		
	}
	
	function onSelectRoleOk(){
	    if(!roleSelectForm.getForm().isValid()){
	    	return;
	    }
	    var roleId = roleSelectForm.down('#roleId').getValue();
	    var unusedTm = roleSelectForm.down('#unusedTm').getValue();
		var curDate = new Date();
		if (unusedTm != null && unusedTm.getTime() < curDate.getTime()) {
			roleSelectForm.down('#unusedTm').markInvalid('${app:i18n('role.unusedTm.invalid')}');
			Ext.Msg.alert('${app:i18n('prompt')}','${app:i18n('role.unusedTm.invalid')}');
			return;
		}
					
		if( !roleSelectWin.isEditingRole && -1 != userRoleGrid.getStore().findExact("id", roleId)){
			Ext.Msg.alert('${app:i18n('prompt')}','${app:i18n('user.role.add.alreadyExist')}');
			return;
		}
		
		var wb = Ext.Msg.wait('${app:i18n('waitingMessage')}');
		Ext.Ajax.request({
			url : 'addUserRole.action',
			params:{
				empCode : modUserWin.user.get('empCode'),
				roleId : roleId,
				unusedTm : unusedTm
			},
			callback:function(o,s,r){
				wb.hide();
				if(s){
					reloadUserRole();
					roleSelectWin.hide();
				}else
					Ext.Msg.alert('${app:i18n('prompt')}','${app:i18n('user.role.add.failed')}');
			}
		});
	}
	
	function userRoleDeptStoreDataChanged(store){
		modUserWin.isRoleDeptEditing = true;
	}
	
	function onResetRoleDept(){
		if(modUserWin.role == null){
			Ext.Msg.alert('${app:i18n('prompt')}',"${app:i18n('user.role.pleaseSelect')}");
			return;	
		}
		modUserWin.isRoleDeptEditing = false;
		userRoleDeptStore.removeAll();
		userRoleDeptStore.proxy.extraParams = {roleId : modUserWin.role.get('id'), empCode : modUserWin.user.get('empCode')}; 
		userRoleDeptStore.load();
	}
	
	function onSaveRoleDept(){
		if(modUserWin.role == null){
			Ext.Msg.alert('${app:i18n('prompt')}',"${app:i18n('user.role.pleaseSelect')}");
			return;	
		}
		if(!modUserWin.isRoleDeptEditing){
			Ext.Msg.alert('${app:i18n('prompt')}',"${app:i18n('user.role.dept.save.noneed')}");
			return;
		}
		modUserWin.isRoleDeptEditing = false;
		
		var roleDepts = new Array();
		userRoleDeptStore.each(function(record){
			roleDepts.push(record.data);
		});
		
		var wb = Ext.Msg.wait('${app:i18n('waitingMessage')}');
		Ext.Ajax.request({
			url: 'saveUserRoleDept.action',
			params:{
				userId : modUserWin.user.get('userId'),
				roleId : modUserWin.role.get('id'),
				userRoleDeptStr : Ext.JSON.encode(roleDepts)
			},
			callback:function(o,s,r){
				wb.hide();
				if(s){
					var msg = Ext.decode(r.responseText).msg;
					if(msg == 'ok'){
						Ext.Msg.alert('${app:i18n('prompt')}', '${app:i18n('saveSuccess')}');
						onResetRoleDept();
					}else{
						Ext.Msg.alert('${app:i18n('prompt')}', "${app:i18n('user.role.dept.save.failed')}");
					}
				}else{
					Ext.Msg.alert('${app:i18n('prompt')}', "${app:i18n('user.role.dept.save.failed')}");
				}
			}
		});
	}
	
	function onDeleteRoleDept(grid, rowIndex, colIndex){
		grid.getStore().removeAt(rowIndex);
		modUserWin.isRoleDeptEditing = true;
	}
	
	function onEditRoleDept(grid, rowIndex, colIndex){
		var role = grid.getStore().getAt(rowIndex);
	    if(modUserWin.role != null && modUserWin.isRoleDeptEditing){
			Ext.Msg.confirm('${app:i18n('prompt')}',"${app:i18n('user.role.dept.save.prompt')}",function(e){
				if(e == 'yes'){
					onSaveRoleDept();
				}
				modUserWin.role = role;
				roleDeptPanel.down('#roleSelect').setValue(role.get('name'));
				onResetRoleDept();
			});	    	
		}else{
			modUserWin.role = role;
			roleDeptPanel.down('#roleSelect').setValue(role.get('name'));
			onResetRoleDept();
		}
	}
	
	function roleDeptSelect(view, node){
		if(modUserWin.role == null){
			Ext.Msg.alert('${app:i18n('prompt')}',"${app:i18n('user.role.pleaseSelect')}");
			return;	
		}
		var deptCode = node.get('deptCode');
		if( -1 != userRoleDeptStore.findExact("deptCode", deptCode)){
			return;			
		}
		
		var text = node.get('text');
		var deptName = text.substr(deptCode.length + 1);
		
		userRoleDeptStore.add({
			deptCode : deptCode,
			deptName : deptName,
			inherited : 0
			});
		modUserWin.isRoleDeptEditing = true;
	}
	
	function userRoleRender(val, m, rec, r, c, s, v){
		if(rec.get("isDefault") == 1){
			return "<b>" + val + "</b>";
		}
		return val;
	}
				    	 
	function deptColumnRender(val, m, rec){
		return val + '/' + rec.get('deptName');
	}
	
	function onCopyUser(){
		var arrUes = userListGrid.getSelectionModel().getSelection();
		if (arrUes == null || arrUes.length != 1) {
			Ext.Msg.alert('${app:i18n('prompt')}',"${app:i18n('user.authorization.copy.select')}");
			return;
		}else if(!arrUes[0].get('isValid')){
			Ext.Msg.alert('${app:i18n('prompt')}',"${app:i18n('user.authorization.copy.select')}");
			return;
		}
		copyUserWin.user = arrUes[0];
		copyUserWin.show();
		copyUserQueryForm.getForm().reset();
		copyUserListStore.removeAll();
		copyTargetUserStore.removeAll();
		
	}
	
	function beforeCopyUserListStoreLoad(store){
		if (copyUserQueryForm.down('#empCode').getValue() == "" && copyUserQueryForm.down('#empName').getValue() == ""
			&& copyUserQueryForm.down('#deptCode').getValue() == "" ) {
			Ext.Msg.alert('${app:i18n('prompt')}',"${app:i18n('user.queryCondition.mini')}");
			return false;
		}
		store.proxy.extraParams = copyUserQueryForm.getForm().getValues();		
	}
	
	function onCopyUserOk(){
		if(copyTargetUserStore.getCount() == 0){
			Ext.Msg.alert('${app:i18n('prompt')}','${app:i18n('user.authorization.select')}');
			return;
		}
		var empCodes = "";
		copyTargetUserStore.each(function(record){
			empCodes += record.get("empCode") + ",";
		});
		
		var wb = Ext.Msg.wait('${app:i18n('waitingMessage')}');
		Ext.Ajax.request({
			url : 'pastAuth.action',
			params:{
				empCode : copyUserWin.user.get("empCode"),
				targetEmpCodes : empCodes
			},
			callback:function(o,s,r){
				wb.hide();
				if(s){
					copyUserWin.hide();
					Ext.Msg.alert('${app:i18n('prompt')}','${app:i18n('saveSuccess')}');
					onUserSearch();
				}else{
					Ext.Msg.alert('${app:i18n('prompt')}','${app:i18n('user.authorization.copy.failed')}');
				}
			}
		});		
	}
	
	function copyUserDeptSelect(view, node){
		if (typeof (node.get("deptCode")) == "undefined" || node.get("deptCode") == "") {
			copyUserListStore.removeAll();
			copyUserQueryForm.down('#deptCode').setValue("");
			return;
		}else{
			copyUserQueryForm.down('#deptCode').setValue(node.get("deptCode"));
			copyUserListStore.load();
		}
	}
	
	function onCopyUserSearch(){
		copyUserListStore.removeAll();
		copyUserListStore.load();
	}
	
	function onCopyUserTargetAdd(grid, rowIndex, colIndex){
		var empCode = grid.getStore().getAt(rowIndex).get("empCode");
		if( -1 != copyTargetUserStore.findExact("empCode", empCode)){
			return;			
		}	    
		copyTargetUserStore.add(grid.getStore().getAt(rowIndex));
	}
	
	function onCopyUserTargetDelete(grid, rowIndex, colIndex){
		grid.getStore().removeAt(rowIndex);
	}	