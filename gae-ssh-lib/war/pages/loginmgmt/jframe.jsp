<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="app" uri="/app-tags"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>${app:i18n_def(app:concat(app.code, '.name'), app.name)}</title>
	<link rel="stylesheet" type="text/css" href="../scripts/lib/jquery/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../scripts/lib/jquery/easyui/themes/icon.css">
	<script type="text/javascript" src="../scripts/lib/jquery/jquery-1.8.0.min.js" ></script>
	<script type="text/javascript" src="../scripts/lib/jquery/easyui/jquery.easyui.min.js" ></script>
	
	<script type="text/javascript">
	$(document).ready(function () 
	{ 
		autoheight();
		$('#dlg').dialog('close');
	});
	<%@include file="../../scripts/loginmgmt/jframe.js"%>
	openPage(0, 'menu.html', 'menu', '', 0, 0, false);
	</script>
	
</head>
<body>

	<div id="menu" style="padding:5px;border:1px solid #ddd">
		<s:iterator value="menus">
    		<a href="#" class="easyui-menubutton" data-options="menu:'#mm${id }'">${app:i18n_menu_def(app:concat(code, '.name'), name)}</a>
    	</s:iterator>
    	
    	<a href="#" class="easyui-menubutton" style="float:right;" data-options="menu:'#menuTools'">${app:i18n('tools')}</a>
    	<a href="#" class="easyui-menubutton" style="float:right;" data-options="menu:'#menuFav'" onclick="showFav();">Fav</a>
	</div>
	<div id="menuTools" style="width:150px;">
		<div onclick="logout();">${app:i18n('logout')}</div>
	</div>

	<s:iterator value="menus">
		<div id="mm${id }" style="width:150px;">
			<s:iterator value="%{children}">
				<s:if test="%{children == null}">
					<div style="background-image: url('${icon}');background-repeat:no-repeat;" onclick="openPage('${id}','${url}','${app:i18n_menu_def(app:concat(code, '.name'), name)}','${icon}','${paramTypeId}','${paramValue}');">${app:i18n_menu_def(app:concat(code, '.name'), name)}</div>
				</s:if>
				<s:else>
					<div style="background-image: url('${icon}');background-repeat:no-repeat;">
						<span>${app:i18n_menu_def(app:concat(code, '.name'), name)}</span>
						<div style="width:150px;">
							<s:iterator value="%{children}">
								<div onclick="openPage('${id}','${url}','${app:i18n_menu_def(app:concat(code, '.name'), name)}','${icon}','${paramTypeId}','${paramValue}');">${app:i18n_menu_def(app:concat(code, '.name'), name)}</div>
							</s:iterator>
						</div>
					</div>
				</s:else>
			</s:iterator>
		</div>
	</s:iterator>
	<div id="dlg" class="easyui-dialog" title="Basic Dialog" data-options="iconCls:'icon-save'" style="width:300px;height:450px;padding:10px;">
	       <ul id="treeFav" class="easyui-tree"        url="../basedata/findFavoriteByUserIdJson.action">    </ul>
	</div>
	<div id="main" style="width:100%;height:100%;">
		<!--${homeUrl}-->
		<div id="tabs" class="easyui-tabs" data-options="tools:'#tab-tools'" style="height:806px;">   
 			
 		</div>
	</div>
	</body>
</html>