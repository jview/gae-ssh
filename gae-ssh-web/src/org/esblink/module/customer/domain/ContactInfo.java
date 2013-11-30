package org.esblink.module.customer.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;

import org.esblink.common.base.BaseDomain;

/**
 * <pre>
 * *********************************************
 * Copyright esblink.net.
 * All rights reserved.
 * Description: tc_contact_info
 * HISTORY
 * *********************************************
 *  ID   DATE           PERSON          REASON
 *  1    2013.04.29     CodeGen         Create
 * 
 * *********************************************
 * </pre>
 */
@SuppressWarnings("serial")
@Entity
public class ContactInfo extends BaseDomain {

	// 系统类型
	@Basic
	@Column(name="sys_type")
	private String sysType;
	// 名称
	@Basic
	@Column(name="user_name")
	private String userName;
	// 单位
	@Basic
	@Column(name="business_name")
	private String businessName;
	// 邮箱
	@Basic
	@Column(name="email")
	private String email;
	// 电话
	@Basic
	@Column(name="phone")
	private String phone;
	// 手机
	@Basic
	private String mobile;
	// 国家
	@Basic
	private String country;
	// 联系方式
	@Basic
	@Column(name="contact_type")
	private String contactType;
	// 备注说明
	@Basic
	private String comment;
	// 是否删除
	@Basic
	@Column(name="if_del")
	private Long ifDel;
	// 状态
	@Basic
	private Long status;
//	// 创建人
//	private Long createUser;
//	// 创建日期
//	private Date createDate;
//	// 修改人
//	private Long modifyUser;
//	// 修改日期
//	private Date modifyDate;
	// 备注
	@Basic
	private String remark;

	public String getSysType() {
		return this.sysType;
	}

	public void setSysType(String sysType) {
		this.sysType = sysType;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getBusinessName() {
		return this.businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getContactType() {
		return this.contactType;
	}

	public void setContactType(String contactType) {
		this.contactType = contactType;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Long getIfDel() {
		return this.ifDel;
	}

	public void setIfDel(Long ifDel) {
		this.ifDel = ifDel;
	}

	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
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

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
