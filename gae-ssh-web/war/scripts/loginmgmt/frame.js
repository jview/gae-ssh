//初始化页面用户角色选项
	var roleCombox = Ext.getCmp('roleCombox');
	roleCombox.setValue('${roleId}');
	
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
	moudleSelectTree.on("load", function( treeStore, records, successful, operation){
		if(records.data.id==1){
	    	var id = 1; // This is the ID of the node that somehow you know in advance
	    	//var node = treeStore.getNodeById(id);
	    	//moudleSelectTree.expandPath(node.getPath());
	    }
	});
	var nodeFirst;
	//重新加载树的第一个节点的数据
		function showWorkWin (){ 
			var nodeFav = treeStore.getNodeById(1);
			nodeFav.removeAll(false);
			treeStore.load({
	                  node : nodeFav
	              });
		
			workWin.show();
		}
		
		var tmp_nodeLeaf;//是否叶子节点，用于校验是否可再加子节点
		var desc;
		var helpUrl;
		var url;
		var id;
		var text;
		function selectFavorite(view, node, rec){
			tmp_nodeLeaf = node.get('leaf');
			//alert(node.data.qtip+'----'+node.data.moduleId);
			
			if(tmp_nodeLeaf){
				id=node.data.moduleId;
				url = node.data.actionUrl;
				text=node.data.qtip;
				desc=node.data.qtip;
				if(url){
					openPage(id, text, desc, helpUrl, url, true,'', node.data.paramTypeId, node.data.paramValue);
					workWin.hide();
				}
			}
			
		}
		function onChange(){
			openPage('3010214', "${app:i18n('favorite.manage')}", desc, helpUrl, '../basedata/linkFavorite.action', true,'');
			workWin.hide();
		}
		function onClears(result){
			if(result=='yes'){
				//删除所有的最近访问数据
				  Ext.Ajax.request({
			        url:"../basedata/deleteAllRecently.action",
			        success:function(response){
			        var result = Ext.decode(response.responseText);
					  if(result.msg == "ok"){
						  
							Ext.MessageBox.alert("${app:i18n('prompt')}","${app:i18n('recently.clear.ok')}");
							treeStore.load();
						   
					    }else{
					        Ext.MessageBox.alert("${app:i18n('prompt')}","${app:i18n('recently.clear.fail')}");
				       	}
			     	}
			      });
			 }   
		}
		function onClear(){
			Ext.MessageBox.confirm("${app:i18n('prompt')}","${app:i18n('recently.clear.confirm')}", onClears);
		}
	function switchRole(c,r,i){
		var id = r.pop().get('id');
		if(id != '${roleId}'){
			Ext.Msg.wait("${app:i18n('switchRole.wait')}");
			Ext.Ajax.request({
				url:'switchRole.action',
				params:{
					roleId:id
				},
				callback:function(){
					window.location='frame.action';
				}
			});
		}
	}
	
	//是否已经开打选择风格窗口
	var isChecked = false;

	function pageStyle(){
		switchStyleWin.show();
		var ra = Ext.getCmp('radios');
		ra.setValue({radios: ['${style}']});
		isChecked = true;
	}

	function switchStyle(t, n, o){
		if(isChecked){
			isChecked = false;
			if(n != o){
				Ext.Msg.wait("${app:i18n('msg.wait.show')}");
	   			Ext.Ajax.request({
	   				url:'switchStyle.action',
	   				params:{
	   					style:t.inputValue
	   				},
	   				callback:function(o,s,r){
	   					if(s){
	   						var res = Ext.decode(r.responseText);
	   						if(res.style !== '')
	   							window.location='frame.action';
	   						else
	       						Ext.MessageBox.alert("${app:i18n('ts')}","${app:i18n('switchStyle.fail')}");
	   					}else
	   						Ext.MessageBox.alert("${app:i18n('ts')}","${app:i18n('switchStyle.fail')}");
	   				}
	   			});

			}
			switchStyleWin.hide();
		}
  	}
		
	function closeAllTab() {
		var panelsArray = Ext.ComponentQuery.query('panel');
	    for (i = 0; i < panelsArray.length; i ++) {
	    	if (panelsArray[i].id != 'home'
	               	&& panelsArray[i].id != 'homemain'
	               	&& panelsArray[i].id != 'homebar'
	               	&& panelsArray[i].id != 'homemsg'
	               	&& panelsArray[i].id != 'homebtn'
	               	&& panelsArray[i].id != 'homehelp') {
            	tabs.remove(panelsArray[i]);
           	}
        }
	}		

	function showHelp(helpUrl) {
		if (helpUrl && helpUrl.length > 0) {
			window.open("${helpUrlPrefix}" + helpUrl, '_blank', 'height=600,width=800,resizable=yes,scrollbars=yes');
		}
	}
	
	function saveChangePwd() {
			if(changePasswordForm.getForm().isValid()) {
				var oldPasswordVarValue = changePasswordForm.down('#oldPasswordVar').getValue();
				var newPasswordVarValue = changePasswordForm.down('#newPasswordVar').getValue();
				var newPasswordConfirmVarValue = changePasswordForm.down('#newPasswordConfirmVar').getValue();
				if (validatePassword(oldPasswordVarValue, newPasswordVarValue, newPasswordConfirmVarValue)){
					var wb = Ext.Msg.wait("${app:i18n('waitingMessage')}");
					Ext.Ajax.request({url: "change_password.action",
						params: changePasswordForm.getForm().getValues(),
						callback: function(o,s,r) {
							wb.hide();
							var resp = Ext.JSON.decode(r.responseText);
							if (resp.success) {
								Ext.Msg.alert("${app:i18n('prompt')}","${app:i18n('change.password.success.message')}");
							} else {
								Ext.Msg.alert("${app:i18n('prompt')}","${app:i18n('change.password.failure.message')}");
							}
						}});
				}
			}
	    }

		// 检验密码表单数据
		function validatePassword(oldPasswordVarValue, newPasswordVarValue, newPasswordConfirmVarValue) {
			if(! checkPassword(oldPasswordVarValue, "${app:i18n('old.password')}")) {
				return false;
			}
			if(! checkPassword(newPasswordVarValue, "${app:i18n('new.password')}")) {
				return false;
			}
			if(! checkPassword(newPasswordConfirmVarValue, "${app:i18n('comfirm.new.password')}")) {
				return false;
			}
			if(oldPasswordVarValue == newPasswordVarValue){
				Ext.MessageBox.alert("${app:i18n('ts')}", "${app:i18n('error.equ')}");
				return false;
			}
			if(newPasswordVarValue != newPasswordConfirmVarValue) {
				Ext.MessageBox.alert("${app:i18n('ts')}", "${app:i18n('error.notEqu')}");
				return false;
			}
			return true;
		}

		// 检验密码，包括长度为八位，字符不重复，必需为数据与字母混合等
		function checkPassword(password, name) {
		    if(password == null || password == ''){
		        Ext.MessageBox.alert("${app:i18n('ts')}", name + "${app:i18n('error.checkPasswordEmpty')}");
		        return false;
		    }
		    if(password.length < 8){
		        Ext.MessageBox.alert("${app:i18n('ts')}", name + "${app:i18n('error.checkPasswordLength')}");
		        return false;
		    }
		    //判断是否字符重复
		    for(var i = 0;i < password.length; i ++){
		        for(var j = i + 1; j < password.length; j ++){
		            if(password.charAt(i) == password.charAt(j)){
		                 Ext.MessageBox.alert("${app:i18n('ts')}", name + "${app:i18n('error.checkPasswordChar')}");
		                 return false;
		            }
		        }
		    }
		    //判断是否数字和英文组成
		    var numFlag = false;
		    var charFlag = false;
		    for(var i =0; i < password.length; i++){
		        if(!(password.charAt(i) >= '0' && password.charAt(i) <= '9') ){
		            if( !( password.charAt(i) >= 'A' && password.charAt(i) <= 'Z' ) ){
		                if( !( password.charAt(i) >= 'a' && password.charAt(i) <= 'z' )){
							Ext.MessageBox.alert("${app:i18n('ts')}", name + "${app:i18n('error.checkPasswordChar')}");
							return false;
		                }
		            }
		        }
		        if(password.charAt(i) >= '0' && password.charAt(i) <= '9'){
		            numFlag = true;
		        }
		        if( ( password.charAt(i) >= 'A' && password.charAt(i) <= 'Z' ) || ( password.charAt(i) >= 'a' && password.charAt(i) <= 'z' )){
		            charFlag = true;
		        }
		    }
		    if(! charFlag || ! numFlag ) {
		        Ext.MessageBox.alert("${app:i18n('ts')}", name + "${app:i18n('error.checkPasswordBuild')}");
				return false;
		    }
		    return true;
		}

		function changePasswordSuccess() {
			changePasswordWin.hide();
			changePasswordForm.getForm().reset();
			Ext.MessageBox.alert("${app:i18n('change.password.success.title')}", "${app:i18n('change.password.success.message')}");
		}

		function changePasswordFailure() {
			Ext.MessageBox.alert("${app:i18n('change.password.failure.title')}", "${app:i18n('change.password.failure.message')}");
		}

	function logoutConfirmed(s) {
		if (s == 'yes') {
			window.location.href='logout.action';
		}
	}
	function logout() {
		Ext.MessageBox.confirm("${app:i18n('logout')}", "${app:i18n('logout.confirm')}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;", logoutConfirmed);
	}

	function openMenu(item, url, desc, helpUrl, id, pTypeId, pValue) {
		openPage(id, item.text, desc, helpUrl, url, true, item.icon, pTypeId, pValue);
	}

	function openTab(id, text, desc, helpUrl, url, pTypeId, pValue) {
		openPage(id, text, desc, helpUrl, url, true, '', pTypeId, pValue);
	}

	function refreshTab(id, text, desc, helpUrl, url, pTypeId, pValue) {
		
		refreshPage(id, text, desc, helpUrl, url, true, pTypeId, pValue);
	}

	function openPage(id, text, desc, helpUrl, url, closable,icon, pTypeId, pValue) {
		var tabId='#panel-'+id;
		var urlVisit=url;
		if(pValue && pValue!='null'){
			urlVisit = urlVisit+"?synmoduleId="+id+"&"+pValue;
			tabId = tabId+'-'+pTypeId;
		}
		
		var tab = tabs.child(tabId);
		if (typeof(tab) == "undefined" || tab == null) {
			tabs.add(createTabPane(tabId, text, desc, helpUrl, urlVisit, closable)).show();
			//将这条数据保存到最近访问功能里
				  Ext.Ajax.request({
		       	 	params: {
		       	 	synicon:icon,
		       	 	synmoduleId:id,
		         	synmoduleName:text,
		         	'linkRecently.paramTypeId':pTypeId,
		         	'linkRecently.paramValue':pValue,
		          	synactionUrl:url},
				    url:"../basedata/saveLinkRecently.action",
				    success:function(response){
				  }});
        } else {
        	tabs.setActiveTab(tab);
        }
	}

	function refreshPage(id, text, desc, helpUrl, url, closable, pTypeId, pValue) {
		var tabId='#panel-'+id;
		var urlVisit=url;
		if(pValue && pValue!='null'){
			urlVisit = urlVisit+"?synmoduleId="+id+"&"+pValue;
			tabId = tabId+'-'+pTypeId;
		}
		var tab = tabs.child(tabId);
		if (typeof(tab) != "undefined" && tab != null) {
			tabs.remove(tab);
        }
        tabs.add(createTabPane(tabId, text, desc, helpUrl, urlVisit, closable)).show();
	}


	function createTabPane(tabId, text, desc, helpUrl, url, closable) {
		tabId = tabId.replace("#","");
		var tabPane = new Ext.panel.Panel({
				id: tabId,
				title: text,
				border: false,
				closable: closable,
				layout: "border",
				items: [
			        new Ext.panel.Panel({
						region: "center",
						border: false,
			            html: "<iframe name='frame_" + tabId + "' src='" + url + "' width='100%' height='100%' frameborder='0'></iframe>"
			        })
	        	]
	        });
		return tabPane;
	}

	var mask = null;

	// 传入的参数为不被遮罩的元素
	function showModal(win) {
		if (mask == null) {
			if (win && win.el && win.el.dom) {
				mask = framePane.container.createChild({cls:"ext-el-mask"}, win.el.dom);
			} else {
				userWin.show();
				userWin.hide();
				mask = framePane.container.createChild({cls:"ext-el-mask"}, userWin.el.dom);
			}
		}
		Ext.getBody().addClass("x-body-masked");
		mask.setSize(Ext.lib.Dom.getViewWidth(true), Ext.lib.Dom.getViewHeight(true));
		mask.show();
	}

	function hideModal() {
		mask.hide();
		Ext.getBody().removeClass("x-body-masked");
	}

	function requestBlank() {
		Ext.Ajax.request({
			url: 'blank.action',
			method: 'GET',
			disableCaching: true,
			success: function (result, request) {
				if (result.responseText.indexOf('okokokokokok') == -1) {
					window.location.href='logout.action';
				}
			}
		});
	}
	
	timeoutId = setInterval((function() {
			return function(){
				requestBlank();
			};
		})(this), sessionTimeout);