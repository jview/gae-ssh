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
 
    
    <table id="dg" title="My Roles" class="easyui-datagrid" style="width:700px;height:250px"
            url="loadAllRole.action" 
            toolbar="#toolbar" pagination="true" onClickRow="editRole" onDblClickRow="editRole()"
            rownumbers="true" fitColumns="true" singleSelect="true">
        <thead>
            <tr >
                <th field="code" width="50" sortable="true">Code</th>
                <th field="name" width="50">Name</th>
               
                <th field="description" width="50">description</th>
            </tr>
        </thead>
    </table>
    <div id="toolbar">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newRole()">New Role</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editRole()">Edit Role</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyRole()">Remove Role</a>
    </div>
    
    <div id="dlg" class="easyui-dialog" style="width:400px;height:340px;padding:10px 20px"
            closed="true" buttons="#dlg-buttons">
        <div class="ftitle">Role Information</div>
        <form id="fm" method="post" novalidate>
            <div class="fitem">
                <label>Code:</label>
                <input name="role.code" class="easyui-validatebox" required="true">
            </div>
            <div class="fitem">
                <label>Name:</label>
                <input name="role.name" class="easyui-validatebox" required="true">
            </div>
           
            
            <div class="fitem">
                <label>Desc:</label>
                <textarea name="role.description" style="height:60px;"></textarea>
            </div>
        </form>
    </div>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveRole()">Save</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">Cancel</a>
    </div>
    <script type="text/javascript">
        var url;
        function newRole(){
            $('#dlg').dialog('open').dialog('setTitle','New Role');
            $('#fm').form('clear');
            url = 'saveOrUpdateRole.action';
        }
        $('#dg').on("rowdblclick", function (sender, record) {
        	editRole();
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
        function editRole(){
            var row = $('#dg').datagrid('getSelected');
            if (row){
            	var entityName='role';
            	var obj = getRowData(entityName, row);
            	//alert(obj.'role.empCode');
                $('#dlg').dialog('open').dialog('setTitle','Edit Role');
                $('#fm').form('load',obj);
                url = 'saveOrUpdateRole.action?'+entityName+'.id='+row.id;
            }
           
        }
        function saveRole(){
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
                        $('#dg').datagrid('reload');    // reload the role data
                    }
                }
            });
        }
        function destroyRole(){
            var row = $('#dg').datagrid('getSelected');
            if (row){
                $.messager.confirm('Confirm','Are you sure you want to destroy this role?',function(r){
                    if (r){
                        $.post('destroy_role.php',{id:row.id},function(result){
                            if (result.success){
                                $('#dg').datagrid('reload');    // reload the role data
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