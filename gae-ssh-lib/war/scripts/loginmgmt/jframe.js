
var height;
var heightCurrent=0;
function autoheight() {
	if(document.documentElement){
		var heightTotal=document.documentElement.clientHeight;
		var height=heightTotal-$('#menu').height()-28;
		
		if(height!=heightCurrent){
			$('#tabs').height(height);
			$('.tabs-panels').height(height-32);
			heightCurrent=height;
		}
	}
}

function showAttr(obj){
	var str='';
	for(var tmp in obj){
		str=str+tmp+",	";
	}
	alert(str);
}
function openPage(id, url, title, icon, paramTypeId, paramValue, fav) {
	//alert(url);
	//$("#main",parent.document.body).attr("src","../"+url);
	var tabId=title;
	var tab = $('#tabs').tabs('exists', tabId);
	if (tab) {
		$('#tabs').tabs('select', tabId);
		return;
	}
	
	if(!fav){
		url='../'+url;
	}
	url=url.replace('//','/');
	//alert(url);
	var content = '<iframe scrolling="no" frameborder="0"'+
          				 'src="'+url+'" style="width:100%;height:100%"></iframe>';
        			  // $("#main",parent.document.body).attr("src",url);
        			   $('#tabs').tabs('add',{
        			   		id:tabId,
						  title:''+title,
						  //href:'../basedata/findFavoriteByUserIdJson.action',
						  content:content,
						  closable:true
						 });
	 
	var params={
		       	 	synicon:icon,
		       	 	synmoduleId:id,
		         	synmoduleName:title,
		         	'linkRecently.paramTypeId':paramTypeId,
		         	'linkRecently.paramValue':paramValue,
		          	synactionUrl:url};
	$.ajax({			
			url: '../basedata/saveLinkRecently.action',
			type : "post",
			cache:false,
			async:false,
			//dataType : "text",
			data:params,
			error : function() {
				alert("Error loading basedata/saveLinkRecently.action");
			},
			success : function(text) {
				
			}
		});	
}
function logout() {
	$.messager.confirm("${app:i18n('logout')}", "${app:i18n('logout.confirm')}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;", logoutConfirmed);
}
function logoutConfirmed(r) {
	if (r) {
		window.location.href='jlogout.action';
	}
}
function startWith(str, exp){     
	  var reg=new RegExp("^"+exp);     
	  return reg.test(str);        
	}  

function showFav(){
	$('#dlg').dialog('open');
	//$('#dlg').dialog({ closeOnEscape: true }); 
	$('#treeFav').tree({   
           checkbox: false,   
           url: '../basedata/findFavoriteByUserIdJson.action',   
           onBeforeExpand:function(node,param){
               //$('#tt2').tree('options').url = "../basedata/findFavoriteByUserIdJson.action?id=" + node.id;// change the url                       
           },               
           onClick:function(node){
        	   if(node.attributes){
        		   if(node.attributes.actionUrl){
        			   var url=node.attributes.actionUrl;
        			   var module_name=node.attributes.moduleName;
        			   var icon=node.attributes.icon;
        			   var paramTypeId=node.attributes.paramTypeId;
        			   var paramValue=node.attributes.paramValue;
        			    openPage(node.attributes.moduleId, url, module_name, icon, paramTypeId, paramValue, true);
						$('#dlg').dialog('close');
        		   }
        	   }
           }  
			});
	timeoutId2=setTimeout((function() {
			return function() {
			$('#treeFav').tree('expandAll');
		}
	})(this), 300);
	
}
$(window).resize(function() {
	location = location;
});