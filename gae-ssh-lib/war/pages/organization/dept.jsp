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
 
    
    <table id="dg" title="My Departments" class="easyui-datagrid" style="width:700px;height:250px"
            url="findPageByDepartment.action"
            toolbar="#toolbar" pagination="true"
            rownumbers="true" fitColumns="true" singleSelect="true">
        <thead>
            <tr>
                <th field="parentCode" width="50">Parent code</th>
                <th field="deptName" width="50">Dept name</th>
                <th field="deptCode" width="50">Dept code</th>
                <th field="deleteFlg" width="50">deleteFlg</th>
                
                <th field="createTm" width="50">Create time</th>
            </tr>
        </thead>
    </table>
    <div id="toolbar">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newUser()">New Dept</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editUser()">Edit Dept</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyUser()">Remove Dept</a>
    </div>
    
    <div id="dlg" class="easyui-dialog" style="width:400px;height:340px;padding:10px 20px"
            closed="true" buttons="#dlg-buttons">
        <div class="ftitle">User Information</div>
        <form id="fm" method="post" novalidate>
        	 <div class="fitem">
                <label>Parent Code:</label>
                <input name="department.parentCode" class="easyui-validatebox">
            </div>
           <div class="fitem">
                <label>Dept code:</label>
                <input name="department.deptCode" class="easyui-validatebox" required="true">
            </div>
           
            <div class="fitem">
                <label>Name:</label>
                <input name="department.deptName" class="easyui-validatebox" required="true">
            </div>
            
            <div class="fitem">
                <label>deleteFlg:</label>
                <input name="department.deleteFlg">
            </div>
            <div class="fitem">
                <label>Desc:</label>
                <textarea name="department.descs" style="height:60px;"></textarea>
            </div>
        </form>
    </div>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveUser()">Save</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">Cancel</a>
    </div>
    <script type="text/javascript">
        var url;
        function newUser(){
            $('#dlg').dialog('open').dialog('setTitle','New Department');
            $('#fm').form('clear');
            url = 'saveDepartment.action';
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
        function editUser(){
            var row = $('#dg').datagrid('getSelected');
            if (row){
            	var entityName='department';
            	var obj = getRowData(entityName, row);
                $('#dlg').dialog('open').dialog('setTitle','Edit Department');
                $('#fm').form('load',obj);
                url = 'saveDepartment.action?'+entityName+'.id='+row.id;
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