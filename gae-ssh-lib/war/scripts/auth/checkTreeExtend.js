function treeCascadeControl(e, t){
    var item = e.getTarget(this.getItemSelector(), this  
                    .getTargetEl()), record;  
    var treePanel = this.panel;
    if (item) {  
        record = this.getRecord(item);  
        var check = !record.get('checked');  
        var isCascade = treePanel.cascade || false;        
		var isCascadeDown = treePanel.cascadeDown || false;
		if (record.isRoot()) {
			record.set('checked', false);
			if (!record.isExpanded()) {
				record.expand(false);
			}
			return;
		}
		record.set('checked', check);
		if (isCascade) {
			record.cascadeBy(function(node) {
				if (!node.isLeaf() && !node.isExpanded()) {
					treePanel.setLoading(true);
					node.expand(true, function() {
						node.cascadeBy(function(childNode) {
							childNode.set('checked', check);
						});
						treePanel.setLoading(false);
					});
				}
				node.set('checked', check);
			});
	
			if (check && !isCascadeDown) {
				record.bubble(function(parentNode) {
					if (!parentNode.isRoot()) {
						parentNode.set('checked', true);
					}
				});
			}
		}
    }
}

Ext.override(Ext.layout.component.AbstractDock, {
	afterRemove : function(item) {
		this.callParent(arguments);
		if (item.el != "undefined" && this.itemCls) {
			item.el.removeCls(this.itemCls + '-' + item.dock);
		}
		var dom = item.el.dom;

		if (!item.destroying && dom && dom.parentNode != null) {
			dom.parentNode.removeChild(dom);
		}
		this.childrenChanged = true;
	}
});
