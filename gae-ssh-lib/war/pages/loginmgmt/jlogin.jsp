<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="app" uri="/app-tags"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" type="text/css" href="../scripts/lib/jquery/easyui/themes/default/easyui.css">
	<script type="text/javascript" src="../scripts/lib/jquery/jquery-1.8.0.min.js"></script> 
	<script type="text/javascript" src="../scripts/lib/jquery/easyui/jquery.easyui.min.js" ></script>
	
	<title>${app:i18n('title')}</title>
</head>
<body>
	<table align="center">
		<tr>
			<td style="text-align: right;">
				<div class="easyui-panel" title="EsbLink" style="width:400px;margin-right: 50px;">
					<div style="padding:10px 0 10px 60px">
				    <form id="loginForm" method="post">
				    	<table>
				    		<tr>
								<td>${app:i18n('username')}</td><td><input class="easyui-validatebox" id="username" name="username" value="admin" data-options="required:true" missingMessage="${app:i18n('msg.input.required')}"/></td>
							</tr>
							<tr>
								<td>${app:i18n('password')}</td><td><input class="easyui-validatebox" type="password" id="password" name="password" value="esblink123" data-options="required:true" missingMessage="${app:i18n('msg.input.required')}"/></td>
							</tr>
							<tr>
								<td>${app:i18n('validCode')}</td><td>
								<input class="easyui-validatebox" type="randomCode" id="randomCode" name="randomCode" value="" data-options="required:true" missingMessage=" "/>
								<img title="Change on click" id="randomCodeValid" name="randomCodeValid" onclick="javascript:refresh();" src="../imageServlet">
								</td>
							</tr>
							<tr>
								<td colspan="2">
								<s:radio list="#{'zh_cn':'中文简体','zh_tw':'中文繁體','en_us':'English'}" name="language" value="zh_cn"  onchange="switchLocale"/>								
								</td>
							</tr>
				    	</table>
				    </form>
				    </div>
				    <div style="text-align:center;padding:5px">
				    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">${app:i18n('login.btn.text')}</a>
				    	<!--<a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">Clear</a>-->
				    </div>
				</div>
			</td>
		</tr>
	</table>
	
	<script>
	$(document).ready(function () 
	        { 
	            $("input[name='language']").change(function () 
	            { 
	               var localeRadio=$("input[name='language']:checked").val();
	            	switchLocale(localeRadio);
	            }); 
	            
	            $("input[name='password']").keydown(function(e){ 
								var curKey = e.which; 
								if(curKey == 13){ 
									submitForm();
								return false; 
								}
							}); 
							
							 $("input[name='randomCode']").keydown(function(e){ 
								var curKey = e.which; 
								if(curKey == 13){ 
									submitForm();
								return false; 
								}
							}); 
	 }); 


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
		window.location = "loginUI_locale.action?language=zh&country=cn";
	}else{
		locale = locale.replace(/-/,'_').toLowerCase();
	}
	var localeRadio = $("input[name='language']").val();
	$("input[name='language'][value='"+locale+"']").attr("checked",true);
	
	var isChecked = true;
	function switchLocale(t){
		if(isChecked){
			isChecked = false;
			var lc = t.split('_');
			window.location = "loginUI_locale.action?language="+lc[0]+"&country="+lc[1];
		}
	}	
	
	function refresh() {
		$("#randomCodeValid").attr("src", "../imageServlet?"+Math.random());
		
	}
		function submitForm(){
			$.ajax({
 				type : "post",
 				url : "login.action",
 				cache:false,
 				async:false,
 				dataType : "text",
 				data:$('#loginForm').serialize(),
 				beforeSend : function(XMLHttpRequest) {	},
 				success : function(data, textStatus) {
 					eval("var resp="+data);
 					if(resp.msg==0) {
 						if(window.name != 'esblink'){
 								if (document.getElementById) {
 									window.location.href="jframe.action";
 									if(window.event){
 										window.event.returnValue = false;
 									}
 								}
 								else{
 									window.location="frame.action";
 								}
								
							}else{
								window.open("","_self","");
								window.opener=null;
								var sys = window.open('jframe.action', 'esblink', 'channelmode=1,resizable=1,status=1');
								if(sys){
									sys.focus();
									window.close();
								}else{
									window.location.href="frame.action";
								}
							}
 					} else if(resp.msg==1) {
 						alert("${app:i18n('Authorize.not.exist.user')}");
 					} else if(resp.msg==2) {
 						alert("${app:i18n('Authorize.invalid.user')}");
 					} else if(resp.msg==3) {
 						alert("${app:i18n('Authorize.illegal.username')}");
 					} else if(resp.msg==4) {
 						alert("${app:i18n('Authorize.illegal.password')}");
 					} else if(resp.msg==5) {
 						alert("${app:i18n('Authorize.error.password')}");
 					} else if(resp.msg==6) {
 						alert("${app:i18n('Authorize.error')}");
 					} else if(resp.msg==7) {
 						alert("${app:i18n('Authorize.not.accredit')}");
 					} else if(resp.msg==8){
 						alert("${app:i18n('Authorize.illegal.randomCodeException')}");
 						refresh();
 					}
 					else {
 						alert(resp.msg)
 					}
 				},
 				complete : function(XMLHttpRequest, textStatus) {
 					//HideLoading();
 				},
 				error : function() {
 					//请求出错处理
 					alert('error');
 				}
 			});
		}
		function clearForm(){
			$('#ff').form('clear');
		}
	</script>
</body>
</html>
