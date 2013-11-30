package org.esblink.module.auth.domain;


public class ModuleVO {
	public ModuleVO(){
		
	}
	public ModuleVO(Module module){
		this.id=module.getId();
		this.parent=module.getParent();
		this.name=module.getName();
		this.description=module.getDescription();
		this.icon=module.getIcon();
		this.code=module.getCode();
		this.helpUrl=module.getHelpUrl();
		this.action=module.getAction();
		this.parentId=module.getParentId();
		this.parentName=module.getParentName();
		this.leaf=module.isLeaf();
		this.ifThird=module.getIfThird();
	}
	private String text;
	private Long id;
	/**
	 * 上级功能
	 */
	private Module parent;

	/**
	 * 功能名称
	 */
	private String name;

	/**
	 * 功能描述
	 */
	private String description;

	/**
	 * 功能图标
	 */
	private String icon;

	/**
	 * 功能编号
	 */
	private String code;

	/**
	 * 功能所对应URL
	 */
	private String action;

	/**
	 * 排序号
	 */
	private Integer sort;

	/**
	 * 功能类型(MENU/BUTTON)
	 */
	private Integer typeFlag;

	/**
	 * 功能应用类型(GUI/WEB)
	 */
	private Integer applyTypeFlag;

	/**
	 * 功能对应的帮助Url
	 */
	private String helpUrl;
	
	// 是否有效
	private Long status;
	
	private Long parentId;
	private String parentName;
	private Integer leafValue;//false:0, true:1
	private Integer ifThird;
	private boolean checked;
	private boolean leaf;
	private boolean expanded;
	
	public String getText() {
		return this.getName();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Module getParent() {
		return parent;
	}
	public void setParent(Module parent) {
		this.parent = parent;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public Integer getTypeFlag() {
		return typeFlag;
	}
	public void setTypeFlag(Integer typeFlag) {
		this.typeFlag = typeFlag;
	}
	public Integer getApplyTypeFlag() {
		return applyTypeFlag;
	}
	public void setApplyTypeFlag(Integer applyTypeFlag) {
		this.applyTypeFlag = applyTypeFlag;
	}
	public String getHelpUrl() {
		return helpUrl;
	}
	public void setHelpUrl(String helpUrl) {
		this.helpUrl = helpUrl;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public Integer getLeafValue() {
		return leafValue;
	}
	public void setLeafValue(Integer leafValue) {
		this.leafValue = leafValue;
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
	public Integer getIfThird() {
		return ifThird;
	}

	public void setIfThird(Integer ifThird) {
		this.ifThird = ifThird;
	}
}
