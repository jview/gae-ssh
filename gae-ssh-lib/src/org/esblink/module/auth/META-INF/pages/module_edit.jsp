<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>


<html>   
<head>  
  <title><s:property value="%{getText('module.title')}"/></title>  
  <style type="text/css">
  	td{padding:0;margin:0;font-size:12px}
  </style>  
  <script type="text/javascript">  
     	function getCheckList(checkListName){
     		 var   element; 
             var   i   =   0; 
             var   n   =   0; 
             var   resultArray   =   new   Array();
             //alert(checkListName   +   "-"   +   (i++));
             i++;
             while (element   =   document.getElementById( checkListName   +   "-"   +   (i++))) 
             { 
                if(element.checked) 
                     resultArray[n++]   =   element.value; 
             } 
             return  resultArray; 

     	}
     	  
        function checkForm(form){  
        	funcname = document.getElementById("funcname").value; 
        	func_buttons = getCheckList('module.buttons'); 
        	//alert(func_buttons);
            if(func_buttons.length==0 && funcname == ""){   
                alert("错误提示:标题不能为空！");   
                return false;   
            }   
            funccode = document.getElementById("funccode").value;
            if(func_buttons.length==0 && funccode == ""){   
                alert("错误提示:编码不能为空！");   
                return false;   
            }  

      	  return submitForm(form);
        }   
        
        function submitForm(form){
        	var title = document.getElementById("funcname").value;
        	var funccode = document.getElementById("funccode").value;
        	var leaf_value = document.getElementById("funccode").value;
        	if(leaf_value==1){
        		leaf_value=true;
        	}
        	else{
        		leaf_value=false;
        	}
        	
        	try{
        		
        		form.form.submit();
        		window.parent.FormEditWin.reloadNavNode(title, leaf_value, funccode);
        	}catch(error){
        		alert(error);
        	}
        	      	
        	///window.parent.FormEditWin.close();
        	
         
        }
    </script>  
     
    
</head>  
<body style="background-color: white">  
    <form id="funcForm" action="../auth/saveModule.action" method="post" >  
       
        <table>  
         <tr>
         	<td width="100"><s:property value="%{getText('module.parentName')}"/>：</td>  
            <td> <s:text name="module.parentName" id="module.parentName" /> 
				 <s:textfield id="funcparentName"  name="module.parentName" size="20"  theme="simple" disabled="true"/>
            	
                </td>
          </tr>  
             <s:hidden id="module_id"  name="module.id" />
             <s:hidden id="module_parentId"  name="module.parentId" />
             <s:hidden id="module_status"  name="module.status" />
             <s:hidden id="module_sort"  name="module.sort" />      
           	<s:hidden id="module_applyTypeFlag"  name="module.applyTypeFlag" />
             <s:hidden id="module_createUser"  name="module.createUser" />
             <s:hidden id="module_createDate"  name="module.createDate" />
            <tr>
              <td width="60"><s:property value="%{getText('module.name')}"/>:</td>  
                <td>   <s:textfield id="funcname"  name="module.name" size="20"  theme="simple"/>
                </td>
             </tr> 
            
            <tr><td><s:property value="%{getText('module.code')}"/>:</td>  
                <td><s:textfield id="funccode"  name="module.code" size="20"  theme="simple"/>
                </td>
              </tr>  
              <tr><td><s:property value="%{getText('module.action')}"/>:</td>  
                <td><s:textfield id="funcaction"  name="module.action" size="35"  theme="simple"/>
                </td>
              </tr>
              <tr><td><s:property value="%{getText('module.icon')}"/>:</td>  
                <td><s:textfield id="module_icon"  name="module.icon" size="35"  theme="simple"/>
                </td>
              </tr>
              <tr><td><s:property value="%{getText('module.helpUrl')}"/>:</td>  
                <td><s:textfield id="module_helpUrl"  name="module.helpUrl" size="35"  theme="simple"/>
                </td>
              </tr>
              <s:if test="module.typeFlag>=4">
              	 <tr><td><s:property value="%{getText('module.typeFlag')}"/>:</td>  
	                <td><s:select id="module_typeFlag" 
			            tooltip="模块类型"
			            list="#{4:'Menu', 5:'Bundle', 6:'Panel', 7:'Widget', 8:'IOS'}"
			            name="module.typeFlag"
			            emptyOption="false"
			            theme="simple"
			            />
	                </td>
	              </tr>
              </s:if>
              <tr><td><s:property value="%{getText('module.leafValue')}"/>:</td>  
                <td>               
                <s:select id="module_leafValue" 
			            list="#{1:'是', 0:'否'}"
			            name="module.leafValue"
			            emptyOption="false"
			            theme="simple"
			            />
                
                </td>
              </tr> 
              <tr><td><s:property value="%{getText('module.showType')}"/>:</td>  
                <td>               
                <s:select id="module_showType" 
			            tooltip="是否在菜单中显示"
			            list="#{1:'是', 0:'否'}"
			            name="module.showType"
			            emptyOption="false"
			            theme="simple"
			            />
                
                </td>
              </tr> 
              <tr><td><s:property value="%{getText('module.ifThird')}"/>:</td>  
                <td>               
                <s:select id="module_ifThird" 
			            tooltip="是否第三方程序功能"
			            list="#{0:'否', 1:'是'}"
			            name="module.ifThird"
			            emptyOption="false"
			            theme="simple"
			            />
                
                </td>
              </tr> 
               
              <s:if test="module.leafValue==1">
              	 <tr><td><s:property value="%{getText('module.permitValue')}"/>:</td>  
	                <td><s:textarea id="module_permitValue"  name="module.permitValue" cols="30" rows="2"  theme="simple"/>
	                </td>
	              </tr>
              </s:if>
              <s:if test="module.leafValue==1&&saveType">
              	<tr>
	              <td width="60"><s:property value="%{getText('module.buttons')}"/>:</td>  
	                <td> 
	                  <s:checkboxlist name="module.buttons" list="#{ 1:'Search', 2: 'Add', 3: 'Modify', 4: 'Delete'}"
	                		listKey="key" listValue="value" value="" theme="simple"
	                		/>
	                </td>
	             </tr> 
              </s:if> 
              <tr>
              <td width="60"><s:property value="%{getText('module.description')}"/>:</td>  
                <td>   <s:textarea  id="description" cols="30" rows="2" name="module.description"   theme="simple"/>
                </td>
             </tr> 
            
           
             
            <tr><td colspan="2" align="center">  
                    <br/>  
                    <input type="button" name="button1" value="<s:property value="%{getText('msg.save')}"/>" onclick="checkForm(this)"/>  
                    &nbsp;&nbsp;   
                    <input type="reset" name="reset" value="<s:property value="%{getText('msg.reset')}"/>"/>  
                    &nbsp;&nbsp;   
                    <input type="button" name="button2" value="<s:property value="%{getText('msg.cancel')}"/>" onclick="window.parent.FormEditWin.close();">  
                </td></tr>  
        </table>  
    </form>  
   
</body>  
</html>  