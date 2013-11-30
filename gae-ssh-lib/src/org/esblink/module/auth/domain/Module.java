package org.esblink.module.auth.domain;

import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

import org.esblink.common.base.BaseDomain;
import org.esblink.common.base.domain.IModule;
import org.esblink.common.base.domain.ModuleApplyType;
import org.esblink.common.base.domain.ModuleType;

/**
 * 功能资源实体
 *
 *
 */
/**
 * esblink
 */
@SuppressWarnings("serial")
@Entity
public class Module extends BaseDomain implements IModule  {

	private static final long serialVersionUID = 1L;

	/**
	 * 上级功能
	 */
	@Transient
	private Module parent;


	/**
	 * 功能名称
	 */
	@Basic
	private String name;

	/**
	 * 功能描述
	 */
	@Basic
	private String description;

	/**
	 * 功能图标
	 */
	@Basic
	private String icon;

	/**
	 * 功能编号
	 */
	@Basic
	private String code;

	/**
	 * 功能所对应URL
	 */
	@Basic
	private String action;

	/**
	 * 排序号
	 */
	@Basic
	private Integer sort;

	/**
	 * 功能类型(MENU/BUTTON)
	 */
	@Basic
	@Column(name="type_flag")
	private Integer typeFlag;

	/**
	 * 功能应用类型(GUI/WEB)
	 */
	@Basic
	@Column(name="apply_type_flag")
	private Integer applyTypeFlag;

	/**
	 * 功能对应的帮助Url
	 */
	@Basic
	@Column(name="help_url")
	private String helpUrl;
	
	// 是否有效
	@Basic
	private Long status;
	@Basic
	@Column(name="parent_id")
	private Long parentId;
	@Basic
	@Column(name="leaf_value")
	private Integer leafValue;//false:0, true:1
	@Basic
	@Column(name="show_type")
	private Integer showType;//false:0, true:1 0不显示，1显示
	@Basic
	@Column(name="class_name")
	private String className;
	@Basic
	@Column(name="permit_value")
	private String permitValue;
	// 参数中较重要的id,如特定类型
	@Basic
	@Column(name="param_type_id")
	private String paramTypeId;
	// 参数值，完整的参数信息
	@Basic
	@Column(name="param_value")
	private String paramValue;
	// 是否第三方程序
	@Basic
	@Column(name="if_third")
	private Integer ifThird;
	
	@Transient
	private String[] buttons;
	@Transient
	private String parentName;
	@Transient
	private boolean leaf;
	

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

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
	  public final Collection getChildren()
	    {
	        return children;
	    }

	    public final void setChildren(Collection children)
	    {
	        this.children = children;
	    }
	    @Transient
	    private Collection children;

	public String getUrl() {
		if (action == null || action.length() == 0)
			return "";
		ModuleType type = getType();
		if (type != ModuleType.APPLICATION) {
			String url = action;
			if (! url.endsWith(".action"))
				url = url + ".action";
			if (url.charAt(0) != '/')
				url = "/" + url;
			return url;
		}
		return action;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
		super.setCode(code);
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

	public ModuleType getType() {
		if(getTypeFlag()==null)
			return ModuleType.NONE;
		else if (getTypeFlag() == 1)
			return ModuleType.ROOT;
		else if (getTypeFlag() == 2)
			return ModuleType.APPLICATION;
		else if (getTypeFlag() == 4)
			return ModuleType.MENU;
		else if (getTypeFlag() == 5)
			return ModuleType.BUNDLE;
		else if (getTypeFlag() == 6)
			return ModuleType.PANEL;
		else if (getTypeFlag() == 7)
			return ModuleType.WIDGET;
		else
			return ModuleType.NONE;
	}

	public void setType(ModuleType type) {
		if (type == ModuleType.ROOT)
			setTypeFlag(1);
		else if (type == ModuleType.APPLICATION)
			setTypeFlag(2);
		else if (type == ModuleType.MENU)
			setTypeFlag(4);
		else if (type == ModuleType.BUNDLE)
			setTypeFlag(5);
		else if (type == ModuleType.PANEL)
			setTypeFlag(6);
		else if (type == ModuleType.WIDGET)
			setTypeFlag(7);
		else
			setTypeFlag(0);
	}

	public ModuleApplyType getApplyType() {
		if(getApplyTypeFlag()==null)
			return ModuleApplyType.NONE;
		else if (getApplyTypeFlag() == 1)
			return ModuleApplyType.WEB;
		else if (getApplyTypeFlag() == 2)
			return ModuleApplyType.GUI;
		else
			return ModuleApplyType.NONE;
	}

	public void setApplyType(ModuleApplyType applyType) {
		if (applyType == ModuleApplyType.WEB)
			setApplyTypeFlag(1);
		else if (applyType == ModuleApplyType.GUI)
			setApplyTypeFlag(2);
		else
			setApplyTypeFlag(0);
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
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
		if(this.parentId==null&&this.getParent()!=null){
			this.parentId= this.getParent().getId();
		}
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
		if(null==this.leafValue||this.leafValue==0){
			return false;
		}
		else{
			return true;
		}
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public Integer getShowType() {
		return showType;
	}

	public void setShowType(Integer showType) {
		this.showType = showType;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String[] getButtons() {
		return buttons;
	}

	public void setButtons(String[] buttons) {
		this.buttons = buttons;
	}

	public String getPermitValue() {
		return permitValue;
	}

	public void setPermitValue(String permitValue) {
		this.permitValue = permitValue;
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

	public Integer getIfThird() {
		return ifThird;
	}

	public void setIfThird(Integer ifThird) {
		this.ifThird = ifThird;
	}
	
	

}
