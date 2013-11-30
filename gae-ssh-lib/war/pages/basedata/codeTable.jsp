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
 
    
    <table id="dg" title="My CodeTables" class="easyui-datagrid" style="width:700px;height:450px"
            url="findPageByCodeTable.action" 
            toolbar="#toolbar" pagination="true" onClickRow="editContact" onDblClickRow="editUser()"
            rownumbers="true" fitColumns="true" singleSelect="true">
        <thead>
            <tr >
            <th style="width: 30px" field="id">id</th>
	        <th style="width: 50px" field="code">Code</th>
	        <th style="width: 50px" field="codeType">Code type</th>
	        <th style="width: 50px" field="cname">cname</th>   
	        <th style="width: 50px" field="ename">ename</th>
	        <th style="width: 50px" field="dataId">dataId</th>
	        <th style="width: 50px" field="showValues">Show values</th>
	        <th style="width: 50px" field="showValuesEn">Show values en</th>
	        <th style="width: 40px" field="status">status</th>
	        <th style="width: 50px" field="remark">remark</th>
                
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
        <div class="ftitle">Code table Information</div>
        <form id="fm" method="post" novalidate>
            <div class="fitem">
                <label>Code:</label>
                <input name="codeTable.code" class="easyui-validatebox" required="true">
            </div>
            <div class="fitem">
                <label>Code type:</label>
                <input name="codeTable.codeType" class="easyui-validatebox" required="true">
            </div>
            <div class="fitem">
                <label>Cname:</label>
                <input name="codeTable.cname" class="easyui-validatebox" required="true">
            </div>
            <div class="fitem">
                <label>Ename:</label>
                <input name="codeTable.ename" class="easyui-validatebox" >
            </div>
            <div class="fitem">
                <label>dataId:</label>
                <input name="codeTable.dataId" class="easyui-validatebox" >
            </div>
            <div class="fitem">
                <label>Show value:</label>
                <input name="codeTable.showValues">
            </div>
            <div class="fitem">
                <label>Show value en:</label>
                <input name="codeTable.showValuesEn">
            </div>
            <div class="fitem">
                <label>status:</label>
                <input name="codeTable.status" class="easyui-validatebox" required="true">
            </div>
            <div class="fitem">
                <label>remark:</label>
                <textarea name="codeTable.remark" style="height:60px;"></textarea>
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
            $('#dlg').dialog('open').dialog('setTitle','New CodeTable');
            $('#fm').form('clear');
            url = 'saveCodeTable.action';
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
            	var entityName='codeTable';
            	var obj = getRowData(entityName, row);
            	//alert(obj.'codeTable.empCode');
                $('#dlg').dialog('open').dialog('setTitle','Edit CodeTable');
                $('#fm').form('load',obj);
                url = 'saveCodeTable.action?'+entityName+'.id='+row.id;
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
                        $.post('deleteCodeTables.action',{empIds:row.id},function(result){
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