package org.esblink.module.basedata.domain;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;

import org.esblink.common.base.BaseDomain;

/**
 * <pre>
 * *********************************************
 * Copyright esblink.net.
 * All rights reserved.
 * Description: ts_link_history
 * HISTORY
 * *********************************************
 *  ID   DATE           PERSON          REASON
 *  1    2012.07.24     CodeGen         Create
 * 
 * *********************************************
 * </pre>
 */
@SuppressWarnings("serial")
@Entity
public class LinkHistory extends BaseDomain {

	// 用户ID
	@Basic
	@Column(name="user_id")
	private Long userId;
	// 模块ID
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
	// 修改人
	@Basic
	@Column(name="modify_user")
	private Long modifyUser;
	// 修改时间
	@Basic
	@Column(name="modify_date")
	private Date modifyDate;
	// 参数中较重要的id,如特定类型
	@Basic
	@Column(name="param_type_id")
	private String paramTypeId;
	// 参数值，完整的参数信息
	@Basic
	@Column(name="param_value")
	private String paramValue;

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
