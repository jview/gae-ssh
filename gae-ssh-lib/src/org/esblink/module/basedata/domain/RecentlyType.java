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
public class RecentlyType extends BaseDomain {

	// 模块
	@Basic
	@Column(name="module_id")
	private Long moduleId;
	// 名称
	@Basic
	private String name;
	// 属性名
	@Basic
	@Column(name="prop_id")
	private String propName;
	// 限制类型0天数, 1访问频次
	@Basic
	@Column(name="limit_type")
	private Long limitType;
	@Basic
	@Column(name="limit_value")
	private String limitValue;
	// 显示方式 0按日期， 1按次数
	@Basic
	@Column(name="show_type")
	private Long showType;
	// 显示排序方式，0 asc, 1 desc
	@Basic
	@Column(name="show_order")
	private Long showOrder;
	
	// 限制次数
	@Basic
	@Column(name="show_row")
	private Integer showRow;
	@Basic
	@Column(name="clear_type")
	private Long clearType;
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

	public Long getModuleId() {
		return this.moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPropName() {
		return this.propName;
	}

	public void setPropName(String propName) {
		this.propName = propName;
	}

	public Long getLimitType() {
		return this.limitType;
	}

	public void setLimitType(Long limitType) {
		this.limitType = limitType;
	}
	
	

	public String getLimitValue() {
		return limitValue;
	}

	public void setLimitValue(String limitValue) {
		this.limitValue = limitValue;
	}


	public Long getShowOrder() {
		return showOrder;
	}

	public void setShowOrder(Long showOrder) {
		this.showOrder = showOrder;
	}

	public Long getShowType() {
		return showType;
	}

	public void setShowType(Long showType) {
		this.showType = showType;
	}

	
	public Integer getShowRow() {
		return showRow;
	}

	public void setShowRow(Integer showRow) {
		this.showRow = showRow;
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

	public Long getClearType() {
		return clearType;
	}

	public void setClearType(Long clearType) {
		this.clearType = clearType;
	}
	
	
}
