<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="app" uri="/app-tags"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Basic CRUD Application - jQuery EasyUI CRUD Demo </title>
    <link rel="stylesheet" type="text/css" href="../scripts/lib/easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="../scripts/lib/easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="../scripts/lib/easyui/demo/demo.css">
    <script type="text/javascript" src="../scripts/lib/jquery/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="../scripts/lib/easyui/jquery.easyui.min.js"></script>
</head>
<body>
    <h2>Basic CRUD Application ${app:i18n('module.title')} aaa</h2>
 
    
    <table id="dg" title="My Modules" class="easyui-datagrid" style="width:900px;height:450px"
            url="findPageByModule.action" 
            toolbar="#toolbar" pagination="true" onClickRow="editModule" onDblClickRow="editModule()"
            rownumbers="true" fitColumns="true" singleSelect="true">
        <thead>
            <tr >
            	<th field="id" width="50" sortable="true">Id</th>
                <th field="code" width="50" sortable="true">Code</th>
                <th field="name" width="50">Name</th>
                <th field="parentId" width="50">parentId</th>
                <th field="leafValue" width="50">Leaf value</th>
                <th field="icon" width="50">Icon</th>
                <th field="action" width="50">Action</th>
                <th field="sort" width="50">Sort</th>
                <th field="typeFlag" width="50">Type flag</th>
                <th field="applyTypeFlag" width="50">Apply type flag</th>
                <th field="status" width="50">Status</th>
                <th field="description" width="50">description</th>
            </tr>
        </thead>
    </table>
    <div id="toolbar">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newModule()">New Module</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editModule()">Edit Module</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyModule()">Remove Module</a>
    </div>
    
    <div id="dlg" class="easyui-dialog" style="width:400px;height:340px;padding:10px 20px"
            closed="true" buttons="#dlg-buttons">
        <div class="ftitle">Module Information</div>
        <form id="fm" method="post" novalidate>
            <div class="fitem">
                <label>Code:</label>
                <input name="module.code" class="easyui-validatebox" required="true">
            </div>
            <div class="fitem">
                <label>Name:</label>
                <input name="module.name" class="easyui-validatebox" required="true">
            </div>
            <div class="fitem">
                <label>Parent id:</label>
                <input name="module.parentId" class="easyui-validatebox" required="true">
            </div>
            <div class="fitem">
                <label>Show type:</label>
                <input name="module.showType" class="easyui-validatebox" required="true">
            </div>
           <div class="fitem">
                <label>Leaf value:</label>
                <input name="module.leafValue" class="easyui-validatebox" required="true">
            </div>
            <div class="fitem">
                <label>Icon:</label>
                <input name="module.icon" >
            </div>
            <div class="fitem">
                <label>Action:</label>
                <input name="module.action">
            </div>
            <div class="fitem">
                <label>Type flag:</label>
                <input name="module.typeFlag" class="easyui-validatebox" required="true">
            </div>
            <div class="fitem">
                <label>apply type flag:</label>
                <input name="module.applyTypeFlag" class="easyui-validatebox" required="true">
            </div>
            <div class="fitem">
                <label>Status:</label>
                <input name="module.status" class="easyui-validatebox" required="true">
            </div>
            <div class="fitem">
                <label>Desc:</label>
                <textarea name="module.description" style="height:60px;"></textarea>
            </div>
        </form>
    </div>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveModule()">Save</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">Cancel</a>
    </div>
    <script type="text/javascript">
        var url;
        function newModule(){
            $('#dlg').dialog('open').dialog('setTitle','New Module');
            $('#fm').form('clear');
            url = 'saveOrUpdateModule.action';
        }
        $('#dg').on("rowdblclick", function (sender, record) {
        	editModule();
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
        function editModule(){
            var row = $('#dg').datagrid('getSelected');
            if (row){
            	var entityName='module';
            	var obj = getRowData(entityName, row);
            	//alert(obj.'module.empCode');
                $('#dlg').dialog('open').dialog('setTitle','Edit Module');
                $('#fm').form('load',obj);
                url = 'saveOrUpdateModule.action?'+entityName+'.id='+row.id;
            }
           
        }
        function saveModule(){
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
                        $('#dg').datagrid('reload');    // reload the module data
                    }
                }
            });
        }
        function destroyModule(){
            var row = $('#dg').datagrid('getSelected');
            if (row){
                $.messager.confirm('Confirm','Are you sure you want to destroy this module?',function(r){
                    if (r){
                        $.post('destroy_module.php',{id:row.id},function(result){
                            if (result.success){
                                $('#dg').datagrid('reload');    // reload the module data
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