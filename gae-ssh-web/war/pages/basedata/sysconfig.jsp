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
 
    
    <table id="dg" title="My Sysconfigs" class="easyui-datagrid" style="width:700px;height:250px"
            url="findPageBySysconfig.action" 
            toolbar="#toolbar" pagination="true" onClickRow="editContact" onDblClickRow="editUser()"
            rownumbers="true" fitColumns="true" singleSelect="true">
        <thead>
            <tr >
            	<th field="code" width="50" sortable="true">code</th>
                <th field="name" width="50" sortable="true">name</th>
                <th field="value" width="50">value</th>
                <th field="valueDefault" width="50" sortable="true">valueDefault</th>
                <th field="status" width="50" sortable="true">status</th>
                <th field="create_disp" width="50" sortable="true">Create user</th>
                <th field="createDate" width="50" sortable="true">Create date</th>
                <th field="modify_disp" width="50" sortable="true">Modify user</th>
                <th field="modifyDate" width="50" sortable="true">Modify date</th>
                <th field="remark" width="50">remark</th>
               
            </tr>
        </thead>
    </table>
    <div id="toolbar">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newUser()">New</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editUser()">Edit</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyUser()">Remove</a>
    </div>
    
    <div id="dlg" class="easyui-dialog" style="width:400px;height:340px;padding:10px 20px"
            closed="true" buttons="#dlg-buttons">
        <div class="ftitle">User Information</div>
        <form id="fm" method="post" novalidate>
            <div class="fitem">
                <label>code:</label>
                <input name="sysconfig.code" class="easyui-validatebox" required="true">
            </div>
            <div class="fitem">
                <label>Name:</label>
                <input name="sysconfig.name" class="easyui-validatebox" required="true">
            </div>
            <div class="fitem">
                <label>value:</label>
                <input name="sysconfig.value" class="easyui-validatebox" required="true">
            </div>
            <div class="fitem">
                <label>valueDefault:</label>
                <input name="sysconfig.valueDefault" class="easyui-validatebox" required="true">
            </div>
            <div class="fitem">
                <label>Status:</label>
                <input name="sysconfig.status" class="easyui-validatebox" required="true">
            </div>
            <div class="fitem">
                <label>remark:</label>
                <textarea name="sysconfig.remark" style="height:60px;"></textarea>
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
            $('#dlg').dialog('open').dialog('setTitle','New Sysconfig');
            $('#fm').form('clear');
            url = 'saveSysconfig.action';
        }
        $('#dg').on("rowdblclick", function (sender, record) {
        	editUser();
        });
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
            	var entityName='sysconfig';
            	var obj = getRowData(entityName, row);
            	//alert(obj.'sysconfig.empCode');
                $('#dlg').dialog('open').dialog('setTitle','Edit Sysconfig');
                $('#fm').form('load',obj);
                url = 'saveSysconfig.action?'+entityName+'.id='+row.id;
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
                            msg: result.msg
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
                        $.post('deleteSysconfigs.action',{empIds:row.id},function(result){
                            if (result.success){
                                $('#dg').datagrid('reload');    // reload the user data
                            } else {
                                $.messager.show({    // show error message
                                    title: 'Error',
                                    msg: result.msg
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