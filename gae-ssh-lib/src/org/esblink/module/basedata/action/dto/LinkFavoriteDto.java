package org.esblink.module.basedata.action.dto;
import org.esblink.module.basedata.domain.LinkFavorite;
import org.esblink.module.basedata.domain.LinkRecently;


public class LinkFavoriteDto {
	public LinkFavoriteDto(){
		
	}
	
	public LinkFavoriteDto(LinkFavorite favorite){
		this.moduleId=favorite.getModuleId();
		this.actionUrl=favorite.getActionUrl();
		this.moduleName=favorite.getModuleName();
		this.leaf=favorite.isLeaf();
		this.expanded=favorite.isExpanded();
		this.icon=favorite.getIcon();
		this.qtip=favorite.getQtip();
		this.parentId=favorite.getParentId();
		this.id=favorite.getId();
		this.paramTypeId=favorite.getParamTypeId();
		this.paramValue=favorite.getParamValue();
		if(parentId==null){
			this.parentId=0l;
		}
		
	}
	public LinkFavoriteDto(LinkRecently favorite){
		this.moduleId=favorite.getModuleId();
		this.actionUrl=favorite.getActionUrl();
		this.moduleName=favorite.getModuleName();
		this.leaf=favorite.isLeaf();
		this.expanded=favorite.isExpanded();
		this.icon=favorite.getIcon();
		this.qtip=favorite.getQtip();
		this.parentId=favorite.getParentId();
		this.id=favorite.getId();
		this.paramTypeId=favorite.getParamTypeId();
		this.paramValue=favorite.getParamValue();
		if(parentId==null){
			this.parentId=0l;
		}
		
	}
	private Long id;
	private Long moduleId;
	private String moduleName;
	private String actionUrl;
	private boolean leaf;
	private boolean expanded;
	private String qtip;
	private String icon;
	private Long parentId;
	private String paramTypeId;
	private String paramValue;
	
	
	public Long getModuleId() {
		return moduleId;
	}
	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getActionUrl() {
		return actionUrl;
	}
	public void setActionUrl(String actionUrl) {
		this.actionUrl = actionUrl;
	}
	public boolean isLeaf() {
		return leaf;
	}
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
	public boolean isExpanded() {
		return expanded;
	}
	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}
	public String getQtip() {
		return qtip;
	}
	public void setQtip(String qtip) {
		this.qtip = qtip;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getParamTypeId() {
		return paramTypeId;
	}

	public void setParamTypeId(String paramTypeId) {
		this.paramTypeId = paramTypeId;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	
	
}
