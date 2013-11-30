package org.esblink.module.basedata.domain;

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
public class LinkRecently extends BaseDomain {

	// 用户
	@Basic
	@Column(name="user_id")
	private Long userId;
	// 模块
	@Basic
	@Column(name="module_id")
	private Long moduleId;
	// 模块名
	@Basic
	@Column(name="module_name")
	private String moduleName;
	// 请求url
	@Basic
	@Column(name="action_url")
	private String actionUrl;
	// 次数
	@Basic
	private Long count;
	// 修改人
//	@Basic
//	@Column(name="modify_user")
//	private Long modifyUser;
//	// 修改时间
//	@Basic
//	@Column(name="modify_date")
//	private Date modifyDate;
	// 参数中较重要的id,如特定类型
	@Basic
	@Column(name="param_type_id")
	private String paramTypeId;
	// 参数值，完整的参数信息
	@Basic
	@Column(name="param_value")
	private String paramValue;
	
	// 图标
	@Basic
	private String icon;
	@Basic
	@Column(name="order_count")
	private Long orderCount;
	@Transient
	private boolean expanded;
	//编号
	@Transient
	private Long num;
	@Transient
	private String tempTm;
	@Transient
	private Long parentId;
	@Transient
	private boolean leaf;
	@Transient
	private String qtip;

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

	public Long getCount() {
		return this.count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

//	public Long getModifyUser() {
//		return this.modifyUser;
//	}
//
//	public void setModifyUser(Long modifyUser) {
//		this.modifyUser = modifyUser;
//	}
//
//	public Date getModifyDate() {
//		return this.modifyDate;
//	}
//
//	public void setModifyDate(Date modifyDate) {
//		this.modifyDate = modifyDate;
//	}

	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Long getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(Long orderCount) {
		this.orderCount = orderCount;
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

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public String getQtip() {
		return qtip;
	}

	public void setQtip(String qtip) {
		this.qtip = qtip;
	}
	public String getText() {
		return this.getModuleName();
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
