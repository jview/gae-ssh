<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>



<html>   
<head>  
   <script src="../scripts/lib/jquery/jquery-1.8.0.min.js" type="text/javascript" ></script>
   <script src="../scripts/lib/AjaxData.js" type="text/javascript" ></script>
    <title>系统设置</title>  
     <script type="text/javascript">    
     function checkForm(form){ 
    	try{
    		var pwd = document.getElementById("password").value;
    		var runType = document.getElementById("runTypeSelect").value;
    		var refreshLog4j = document.getElementById("refreshLog4j").value;
    		var runTypeName;
    		if(pwd==null||pwd==''){
    			alert('密码不能为空');
    		}
    		if(pwd!=''&&runType!=''){
    			var status=changeConfig(runType,refreshLog4j,pwd);
    			if(status=='ok'){
    				document.getElementById("password").value='';
    				if(runType==true||runType=='true'){
    					runTypeName='测试模式';
    				}
    				else {
    					runTypeName='运行模式';	
    				}
    				//document.getElementById("runType").value=(runTypeName);
    				$("#runType").text(runTypeName);
    				alert('更改为'+runTypeName+'成功!');
    			}
    			else{
    				alert(status);
    			}
    		}
    		//alert(pwd+'---'+runType);
     		//form.form.submit();
     	}catch(error){
     		
     	}
     }
     
     function changeConfig(runType, refreshLog4j, pwd){
 	 	var status="fail";
 	 	 $.ajax({
 					type : "post",
 					url : "../basedata/changeConfig.action",
 					async:false,
 					data:{runTypeSelect:runType, refreshLog4j:refreshLog4j, password:pwd},
 					beforeSend : function(XMLHttpRequest) {
 						// ShowLoading();
 					},
 					success : function(data, textStatus) { 						
 						status= data.msg;
 						//alert(status); 						
 					},
 					complete : function(XMLHttpRequest, textStatus) {
 						//HideLoading();
 					},
 					error : function() {
 	
 						//请求出错处理
 					}
 				});
 		return status;
 	}
     </script>  
    
</head>  
<body style="background-color: white">  
    <form id="funcForm" method="post" >  
       
        <table align="center" width="300">  
        	<tr>
              <td width="120">当前模式：</td>  
                <td>   
               		<div id='runType'></div>
                </td>
             </tr> 
             
            <tr>
              <td width="120">模式选择：</td>  
                <td>   
                <s:select id="runTypeSelect" name="runTypeSelect" list="#{true:'测试模式',false:'运行模式'}" listKey="key" listValue="value"  headerKey="true" theme="simple">
				</s:select>
                </td>
             </tr> 
             <tr>
              <td width="120">刷新Log4j：</td>  
                <td>   
                <s:select id="refreshLog4j" name="refreshLog4j" list="#{false:'否', true:'是'}" listKey="key" listValue="value"  headerKey="true" theme="simple">
				</s:select>
                </td>
             </tr> 
            <tr>
              <td >密码：</td>  
                <td>   <s:password id="password"  name="password" size="20"  theme="simple"/>
                </td>
             </tr> 
     
           
             
            <tr><td colspan="2" align="center">  
                    <br/>  
                    <input type="button" name="button1" value="<s:property value="%{getText('msg.ok')}"/>" onclick="checkForm(this)"/>                     
                </td></tr>  
        </table>  
    </form>  
   <script type="text/javascript">
   var ajaxData = new AjaxData(false);
   var isTest = ajaxData.getSysconfigValue("isTest");
   var runType="运行模式";
   if(isTest==true){
	   runType="测试模式";
   }
   //alert(runType);
   $("#runType").text(runType);
   </script>
</body>  
</html>  