package org.esblink.module.basedata.domain;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

import org.esblink.common.base.BaseDomain;

/**
 * esblink
 */
@SuppressWarnings("serial")
@Entity  
public class LinkFavorite extends BaseDomain {

	// 用户
	@Basic
	@Column(name="user_id")
	private Long userId;
	// 模块
	@Basic
	@Column(name="module_id")
	private Long moduleId;
	// 模块名称
	@Basic
	@Column(name="module_name")
	private String moduleName;
	// url
	@Basic
	@Column(name="action_url")
	private String actionUrl;
	// 顺序号
	@Basic
	@Column(name="order_count")
	private Long orderCount;
	// 备注
	@Basic
	private String remark;
	// 修改人
	@Basic
	@Column(name="modify_user")
	private Long modifyUser;
	// 修改时间
	@Basic
	@Column(name="modify_date")
	private Date modifyDate;
	// 图标
	@Basic
	private String icon;
	// 参数中较重要的id,如特定类型
	@Basic
	@Column(name="param_type_id")
	private String paramTypeId;
	// 参数值，完整的参数信息
	@Basic
	@Column(name="param_value")
	private String paramValue;
	
	@Transient
	private boolean leaf;
	@Transient
	private boolean expanded;
	//编号
	@Transient
	private Long num;
	@Transient
	private String tempTm;
	@Transient
	private String text;
	@Transient
	private String qtip;
	@Transient
	private Long parentId;
	private String getHref(){
		return this.getActionUrl();
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getModuleId() {
		return this.moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public String getModuleName() {
		return this.moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getActionUrl() {
		return this.actionUrl;
	}

	public void setActionUrl(String actionUrl) {
		this.actionUrl = actionUrl;
	}

	public Long getOrderCount() {
		return this.orderCount;
	}

	public void setOrderCount(Long orderCount) {
		this.orderCount = orderCount;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getModifyUser() {
		return this.modifyUser;
	}

	public void setModifyUser(Long modifyUser) {
		this.modifyUser = modifyUser;
	}

	public Date getModifyDate() {
		return this.modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
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

	public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
	}

	public String getTempTm() {
		return tempTm;
	}

	public void setTempTm(String tempTm) {
		this.tempTm = tempTm;
	}

	public String getText() {
		return this.getModuleName();
	}

	public String getQtip() {
		return qtip;
	}

	public void setQtip(String qtip) {
		this.qtip = qtip;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
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
