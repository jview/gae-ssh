package org.esblink.module.basedata.domain;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;

import org.esblink.common.base.BaseDomain;

/**
 * esblink
 */
@SuppressWarnings("serial")
@Entity
public class LinkRelation extends BaseDomain {

	// 模块
	@Basic
	@Column(name="module_id")
	private Long moduleId;
	// 模块名称
	@Basic
	@Column(name="rel_module_name")
	private String relModuleName;
	// url
	@Basic
	@Column(name="rel_action_url")
	private String relActionUrl;
	// 顺序号
	@Basic
	@Column(name="order_count")
	private Long orderCount;
	// l新窗口打开，2弹出窗口打开
	@Basic
	private String types;
	// 备注
	@Basic
	private String remark;
//	// 创建人
//	@Basic
//	@Column(name="create_user")
//	private Long createUser;
//	// 创建时间
//	@Basic
//	@Column(name="create_date")
//	private Date createDate;
//	// 修改人
//	@Basic
//	@Column(name="modify_user")
//	private Long modifyUser;
//	// 修改时间
//	@Basic
//	@Column(name="modify_date")
//	private Date modifyDate;
	// 模块ID
	@Basic
	@Column(name="rel_module_id")
	private Long relModuleId;
	// 图标
	@Basic
	@Column(name="rel_icon")
	private String relIcon;
	// 参数中较重要的id,如特定类型
	@Basic
	@Column(name="param_type_id")
	private String paramTypeId;
	// 参数值，完整的参数信息
	@Basic
	@Column(name="param_value")
	private String paramValue;

	public Long getModuleId() {
		return this.moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public String getRelModuleName() {
		return this.relModuleName;
	}

	public void setRelModuleName(String relModuleName) {
		this.relModuleName = relModuleName;
	}

	public String getRelActionUrl() {
		return this.relActionUrl;
	}

	public void setRelActionUrl(String relActionUrl) {
		this.relActionUrl = relActionUrl;
	}

	public Long getOrderCount() {
		return this.orderCount;
	}

	public void setOrderCount(Long orderCount) {
		this.orderCount = orderCount;
	}

	public String getTypes() {
		return this.types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

//	public Long getCreateUser() {
//		return this.createUser;
//	}
//
//	public void setCreateUser(Long createUser) {
//		this.createUser = createUser;
//	}
//
//	public Date getCreateDate() {
//		return this.createDate;
//	}
//
//	public void setCreateDate(Date createDate) {
//		this.createDate = createDate;
//	}
//
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

	public Long getRelModuleId() {
		return this.relModuleId;
	}

	public void setRelModuleId(Long relModuleId) {
		this.relModuleId = relModuleId;
	}

	public String getRelIcon() {
		return this.relIcon;
	}

	public void setRelIcon(String relIcon) {
		this.relIcon = relIcon;
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
