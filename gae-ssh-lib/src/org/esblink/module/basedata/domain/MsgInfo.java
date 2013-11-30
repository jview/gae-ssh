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
public class MsgInfo extends BaseDomain {

	// 系统类型
	@Basic
	@Column(name="sys_type")
	private String sysType;
	// 中文名称
	@Basic
	@Column(name="name_cn")
	private String nameCn;
	// 英文名称
	@Basic
	@Column(name="name_en")
	private String nameEn;
	// 繁体名称
	@Basic
	@Column(name="name_tw")
	private String nameTw;
	// 顺序号
	@Basic
	@Column(name="order_count")
	private Long orderCount;
	// 描述
	@Basic
	private String descs;
	// 是否删除
	@Basic
	@Column(name="if_del")
	private Long ifDel;
	// 状态
	@Basic
	private Long status;
	// 备注
	@Basic
	private String remark;
//	// 创建人
//	private Long createUser;
//	// 创建时间
//	private Date createDate;
//	// 修改人
//	private Long modifyUser;
//	// 修改时间
//	private Date modifyDate;

	public String getSysType() {
		return this.sysType;
	}

	public void setSysType(String sysType) {
		this.sysType = sysType;
	}

	

	public String getNameCn() {
		return nameCn;
	}

	public void setNameCn(String nameCn) {
		this.nameCn = nameCn;
	}

	public String getNameEn() {
		return nameEn;
	}

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	public String getNameTw() {
		return nameTw;
	}

	public void setNameTw(String nameTw) {
		this.nameTw = nameTw;
	}

	public String getDescs() {
		return this.descs;
	}

	public void setDescs(String descs) {
		this.descs = descs;
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

	public Long getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(Long orderCount) {
		this.orderCount = orderCount;
	}
	
	
}
