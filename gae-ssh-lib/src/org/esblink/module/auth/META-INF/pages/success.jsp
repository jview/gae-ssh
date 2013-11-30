<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">  
<html>  
<head>  
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">  
<title>信息更新成功</title>
<%
//System.out.println("======4=====success.jsp============");
String jsonString = (String)request.getAttribute("jsonData");
String funcname=(String)request.getAttribute("module.name");
Long funcid=(Long)request.getAttribute("module.id");
 %>  
<script type="text/javascript">  
    // 信息保存成功后，刷新父节点   
    var jsonString =(<%=jsonString%>);
	//eval("var json='"+jsonString+"');
	//alert(json.success);
	//json = Ext.decode(json.trim());
	/**/
    if(jsonString.success){    	
    	 //window.parent.FormEditWin.reloadNavNode('<%=funcname%>', <%=funcid%>);
    	 window.parent.FormEditWin.reloadNavNode(jsonString.name, jsonString.code, jsonString.leafValue, jsonString.id);
   	     window.parent.FormEditWin.close();
    }
    else{
    	alert('操作失败:'+json.msg);
    	javascript:history.go(-1);
    }
	
   
</script> 
</head>   
<body>   

    <center>        
   <s:property  value="jsonString"/>
    </center>  
</body>  
 
</html>  