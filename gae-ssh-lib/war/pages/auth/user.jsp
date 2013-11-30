<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Basic CRUD Application - jQuery EasyUI CRUD Demo</title>
    <link rel="stylesheet" type="text/css" href="../scripts/lib/easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="../scripts/lib/easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="../scripts/lib/easyui/demo/demo.css">
    <script type="text/javascript" src="../scripts/lib/jquery/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="../scripts/lib/easyui/jquery.easyui.min.js"></script>
</head>
<body>
    <h2>Basic CRUD Application</h2>
    <div class="demo-info" style="margin-bottom:10px">
        <div class="demo-tip icon-tip">&nbsp;</div>
        <div>Click the buttons on datagrid toolbar to do crud actions.</div>
    </div>
    
    <table id="dg" title="My Users" class="easyui-datagrid" style="width:700px;height:250px"
            url="loadUserEmp.action"
            toolbar="#toolbar" pagination="true"
            rownumbers="true" fitColumns="true" singleSelect="true">
        <thead>
            <tr>
                <th field="empCode" width="50">Emp code</th>
                <th field="empName" width="50">Emp Name</th>
                <th field="dutyName" width="50">Duty Name</th>
                <th field="deptCode" width="50">Dept code</th>
                <th field="isValid" width="50">Is valid</th>
            </tr>
        </thead>
    </table>
    <div id="toolbar">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openUser()">Open account</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="modifyPassword()">Change password</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyUser()">Remove User</a>
    </div>
    
    <div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
            closed="true" buttons="#dlg-buttons">
        <div class="ftitle">User Information</div>
        <form id="fm" method="post" novalidate>
            <div class="fitem">
                <label>Password new:</label>
                <input id="password" type="password" name="password" class="easyui-validatebox" required="true">
            </div>
            <div class="fitem">
                <label>Password confirm:</label>
                <input id="password_confirm"  type="password" name="password_confirm" class="easyui-validatebox" required="true">
            </div>
            
        </form>
    </div>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="chanagePw()">Save</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">Cancel</a>
    </div>
    <script type="text/javascript">
        var url;
        var empCode;
        function modifyPassword(){
            var row = $('#dg').datagrid('getSelected');
            if (row){
            	empCode=row.empCode;
            	
            	//alert(obj.'employee.empCode');
                $('#dlg').dialog('open').dialog('setTitle','Change password');
            }
           
        }
        function chanagePw(){
           
           	var password=$('#password').val();
           	var password_confirm=$('#password_confirm').val();
           	if(password!=password_confirm){
           		alert('Password not equal to confirm');
           		return;
           	}
           	$.post('modUserPassword.action',{empCode:empCode,modPassword:password},function(result){
                   if (result.msg){
                      // $('#dg').datagrid('reload');    // reload the user data
                      $('#dlg').dialog('close');   
                      alert('Operate success');
                   } else {
                       $.messager.show({    // show error message
                           title: 'Error',
                           msg: result.msg
                       });
                   }
               },'json');
            
        }
        function getRowData(entityName, row){
        	var tmp;
        	var obj={}
        	obj[entityName]={};
        	for(i in row){
        		tmp = row[i];
        		obj[entityName+'.'+i]=tmp;
        	}
        	return obj;
        }
        function openUser(){
            var row = $('#dg').datagrid('getSelected');
            if (row){
            	$.post('addUser.action',{empCode:row.empCode},function(result){
                    if (result.success){
                        $('#dg').datagrid('reload');    // reload the user data
                    } else {
                    	var info;
                    	if(result.msg != null){
                    		info=result.msg;
                    	}
                    	else{
                    		info='Open account success';
                    		
                    	}
                    	$.messager.show({    // show error message
                            title: 'INFO',
                            msg: info
                        });
                        
                    }
                },'json');
            }
        }
        function saveUser(){
            $('#fm').form('submit',{
                url: url,
                onSubmit: function(){
                    return $(this).form('validate');
                },
                success: function(result){
                    var result = eval('('+result+')');
                    if (result.errorMsg){
                        $.messager.show({
                            title: 'Error',
                            msg: result.errorMsg
                        });
                    } else {
                        $('#dlg').dialog('close');        // close the dialog
                        $('#dg').datagrid('reload');    // reload the user data
                    }
                }
            });
        }
        function destroyUser(){
            var row = $('#dg').datagrid('getSelected');
            if (row){
                $.messager.confirm('Confirm','Are you sure you want to destroy this user?',function(r){
                    if (r){
                        $.post('destroy_user.php',{id:row.id},function(result){
                            if (result.success){
                                $('#dg').datagrid('reload');    // reload the user data
                            } else {
                                $.messager.show({    // show error message
                                    title: 'Error',
                                    msg: result.errorMsg
                                });
                            }
                        },'json');
                    }
                });
            }
        }
    </script>
    <style type="text/css">
        #fm{
            margin:0;
            padding:10px 30px;
        }
        .ftitle{
            font-size:14px;
            font-weight:bold;
            padding:5px 0;
            margin-bottom:10px;
            border-bottom:1px solid #ccc;
        }
        .fitem{
            margin-bottom:5px;
        }
        .fitem label{
            display:inline-block;
            width:80px;
        }
    </style>
</body>
</html>