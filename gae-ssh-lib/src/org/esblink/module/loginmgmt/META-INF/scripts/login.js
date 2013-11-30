	var initx ;
	
	initx = screen.availWidth/2 - 200;
	
	//初始化页面登陆框位置
	Ext.getCmp('mainPanel').showAt(initx , 100);
	
	function refresh(e, obj) {
		obj.src = "../imageServlet?"+Math.random();    
	}
	
	function getCookie(Name){
		var search = Name + "=";
		if(document.cookie.length > 0){
			var offset = document.cookie.indexOf(search);
			if(offset != -1){
				offset += search.length;
				var end = document.cookie.indexOf(";", offset);
				if(end == -1)
					end = document.cookie.length;
				return unescape(document.cookie.substring(offset, end));
			}else return null;
		}
	}
	
	var locale = getCookie('locale');
	if(locale == null || locale == undefined){
		window.location = "login_locale.action?language=zh&country=cn";
	}else{
		locale = locale.replace(/-/,'_').toLowerCase();
	}
	
	var localeRadio = Ext.getCmp('locale_radiogroup');
	localeRadio.setValue({lo: [locale]});
	
	var isChecked = true;
	function switchLocale(t){
		if(isChecked){
			isChecked = false;
			var lc = t.inputValue.split('_');
			window.location = "login_locale.action?language="+lc[0]+"&country="+lc[1];
		}
	}		
	
	var checkActionSending = false;
				
	function check(){
		var form = loginForm.getForm();
		if(form.isValid()){
			var username = Ext.getCmp('username').getValue();
			var password = Ext.getCmp('password').getValue();
			
			var wb = Ext.Msg.wait("${app:i18n_def('msg.wait.show','Please waiting ......')}");
	
			if(checkActionSending)
				return;
			checkActionSending = true;
			Ext.Ajax.request({
				url:'checkPassword.action',
				params:{
					username:username,
					password:password
				},
				callback:function(o,s,r){
					checkActionSending = false;
					wb.hide();
					if(s){
						var msg = Ext.decode(r.responseText).msg;					
						if (msg != '0') {
							failure(msg);
						}else{
							login();
						}
					}else{
						Ext.MessageBox.alert("${app:i18n('prompt')}","${app:i18n('login.timeout')}");
						form.findField('password').setValue('');
					}
				}
			});
		}else focus();
	}
	function login(){
		var form = loginForm.getForm();
		if(form.isValid()){
			var username = Ext.getCmp('username').getValue();
			var password = Ext.getCmp('password').getValue();
			var randomCode = Ext.getCmp('randomCode').getValue();
			var wb = Ext.Msg.wait("${app:i18n_def('msg.wait.show','Please waiting ......')}");
			Ext.Ajax.request({
				url:'login.action',
				params:{
					username:username,
					password:password,
					randomCode:randomCode
				},
				callback:function(o,s,r){
					wb.hide();
					if(s){
						var msg = Ext.decode(r.responseText).msg;
						if (msg != '0') {
							failure(msg);
						}else{
							Ext.Msg.wait("${app:i18n_def('msg.wait.show','Please waiting ......')}");
							//window.location='frame.action';
							if(window.name == 'pms'){
								window.location='frame.action';
							}else{
								window.open("","_self","");
								window.opener=null;
								var sys = window.open('frame.action', 'pms', 'channelmode=1,resizable=1,status=1');
								if(sys){
									sys.focus();
									window.close();
								}else{
									window.location.href="frame.action";
								}
							}
						}
					}else{
						Ext.MessageBox.alert("${app:i18n('prompt')}","${app:i18n('login.timeout')}");
						Ext.getCmp('password').setValue('');
					}
				}
			});
		}else focus();
	}
	function failure(msg){
		if(msg=='1'){
			Ext.MessageBox.alert("${app:i18n('prompt')}","${app:i18n('Authorize.not.exist.user')}");
		}else if(msg=='2'){
			Ext.MessageBox.alert("${app:i18n('prompt')}","${app:i18n('Authorize.invalid.user')}");
		}else if(msg=='3'){
			Ext.MessageBox.alert("${app:i18n('prompt')}","${app:i18n('Authorize.illegal.username')}");
		}else if(msg=='4'){
			Ext.MessageBox.alert("${app:i18n('prompt')}","${app:i18n('Authorize.illegal.password')}");
		}else if(msg=='5'){
			Ext.MessageBox.alert("${app:i18n('prompt')}","${app:i18n('Authorize.error.password')}");
		}else if(msg=='6'){
			Ext.MessageBox.alert("${app:i18n('prompt')}","${app:i18n('Authorize.error')}");
		}else if(msg == '7'){
			Ext.MessageBox.alert("${app:i18n('prompt')}","${app:i18n('Authorize.not.accredit')}");
		}else if(msg == '8'){
			Ext.MessageBox.alert("${app:i18n('prompt')}","${app:i18n('Authorize.illegal.randomCodeException')}");
		}
		Ext.getCmp('password').setValue('');
	}
	function focus(t,e){
		var username = Ext.getCmp('username').getValue();
		if(!username){
			Ext.getCmp('username').focus();
			return;
		}
		if(e.getKey() == e.ENTER)
			Ext.getCmp('password').focus();
	}

	Ext.getCmp('username').focus();
	
	function enterKey(t,e){
		if(e.getKey() == e.ENTER && t.getValue()) check();
	}