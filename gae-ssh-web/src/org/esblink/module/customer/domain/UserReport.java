package org.esblink.module.customer.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;

import org.esblink.common.base.BaseDomain;

import com.google.appengine.api.datastore.Text;

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
public class UserReport extends BaseDomain {

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
	// 报告类型
	@Basic
	@Column(name="report_type")
	private String reportType;
	
	// 问题所在的url
	@Basic
	@Column(name="action")
	private String action;
	
	// 联系方式
	@Basic
	@Column(name="contact_info")
	private String contactInfo;
	// 备注说明
	@Basic
	private Text comment;
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
	private Text remark;

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

	

	public String getComment() {
		return this.comment.getValue();
	}

	public void setComment(String comment) {
		this.comment = new Text(comment);
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
		return this.remark.getValue();
	}

	public void setRemark(String remark) {
		this.remark = new Text(remark);
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(String contactInfo) {
		this.contactInfo = contactInfo;
	}
	
	
}
