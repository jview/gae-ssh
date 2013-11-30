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
public class Sysconfig extends BaseDomain {

	// 名称
	@Basic
	private String name;
	// 值
	@Basic
	private String value;
	// 默认值
	@Basic
	@Column(name="value_default")
	private String valueDefault;
	// 状态
	@Basic
	private Long status;
	// 备注
	@Basic
	private String remark;
	// 修改人
	@Column(name="modify_user")
	private Long modifyUser;
	// 修改时间
	@Column(name="modify_date")
	private Date modifyDate;
	// 是否删除
	@Basic
	@Column(name="if_del")
	private Long ifDel;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValueDefault() {
		return this.valueDefault;
	}

	public void setValueDefault(String valueDefault) {
		this.valueDefault = valueDefault;
	}

	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
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

	public Long getIfDel() {
		return this.ifDel;
	}

	public void setIfDel(Long ifDel) {
		this.ifDel = ifDel;
	}
}
