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
public class RecentlyInput extends BaseDomain {

	// 用户
	@Basic
	@Column(name="user_id")
	private Long userId;
	// 最近类型
	@Basic
	@Column(name="recently_type_id")
	private Long recentlyTypeId;
	// 输入值
	@Basic
	@Column(name="input_value")
	private String inputValue;
	// 操作时间
	@Basic
	@Column(name="oper_date")
	private Date operDate;

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRecentlyTypeId() {
		return this.recentlyTypeId;
	}

	public void setRecentlyTypeId(Long recentlyTypeId) {
		this.recentlyTypeId = recentlyTypeId;
	}

	public String getInputValue() {
		return this.inputValue;
	}

	public void setInputValue(String inputValue) {
		this.inputValue = inputValue;
	}

	public Date getOperDate() {
		return this.operDate;
	}

	public void setOperDate(Date operDate) {
		this.operDate = operDate;
	}
}
