<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Lazy Load Tree Nodes - jQuery EasyUI Demo</title>
     <link rel="stylesheet" type="text/css" href="../scripts/lib/easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="../scripts/lib/easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="../scripts/lib/easyui/demo/demo.css">
    <script type="text/javascript" src="../scripts/lib/jquery/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="../scripts/lib/easyui/jquery.easyui.min.js"></script>
 
</head>
<body>
    <h2>Lazy Load Tree Nodes</h2>
    <div class="demo-info">
        <div class="demo-tip icon-tip"></div>
        <div>Get full hierarchical tree data but lazy load nodes level by level.</div>
    </div>
    <div style="margin:10px 0;"></div>
    <ul class="easyui-tree" data-options="url:'queryModuleList.action',method:'get',loadFilter:myLoadFilter"></ul>
    <script>
        function myLoadFilter(data, parent){
            var state = $.data(this, 'tree');
            
            function setData(){
                var serno = 1;
                var todo = [];
                for(var i=0; i<data.length; i++){
                    todo.push(data[i]);
                }
                while(todo.length){
                    var node = todo.shift();
                    if (node.id == undefined){
                        node.id = '_node_' + (serno++);
                    }
                    if (node.children){
                        node.state = 'closed';
                        node.children1 = node.children;
                        node.children = undefined;
                        todo = todo.concat(node.children1);
                    }
                }
                state.data = data;
            }
            function find(id){
                var data = state.data;
                var cc = [data];
                while(cc.length){
                    var c = cc.shift();
                    for(var i=0; i<c.length; i++){
                        var node = c[i];
                        if (node.id == id){
                            return node;
                        } else if (node.children1){
                            cc.push(node.children1);
                        }
                    }
                }
                return null;
            }
            
            setData();
            
            var t = $(this);
            var opts = t.tree('options');
            opts.onBeforeExpand = function(node){
                var n = find(node.id);
                if (n.children && n.children.length){return}
                if (n.children1){
                    var filter = opts.loadFilter;
                    opts.loadFilter = function(data){return data;};
                    t.tree('append',{
                        parent:node.target,
                        data:n.children1
                    });
                    opts.loadFilter = filter;
                    n.children = n.children1;
                }
            };
            return data;
        }
    </script>
</body>
</html>