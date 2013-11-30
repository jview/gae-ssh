package org.esblink.module.basedata.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

public class RecentlyInputVO {
	public RecentlyInputVO(){
		
	}
	public RecentlyInputVO(RecentlyInput reInput){
		this.userId=reInput.getUserId();
		this.recentlyTypeId=reInput.getRecentlyTypeId();
		this.inputValue=reInput.getInputValue();
		this.operDate=reInput.getOperDate();
	}
	// 用户
	private Long userId;
	// 最近类型
	private Long recentlyTypeId;
	// 输入值
	private String inputValue;
	// 操作时间
	private Date operDate;
	
	private Long count;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getRecentlyTypeId() {
		return recentlyTypeId;
	}
	public void setRecentlyTypeId(Long recentlyTypeId) {
		this.recentlyTypeId = recentlyTypeId;
	}
	public String getInputValue() {
		return inputValue;
	}
	public void setInputValue(String inputValue) {
		this.inputValue = inputValue;
	}
	public Date getOperDate() {
		return operDate;
	}
	public void setOperDate(Date operDate) {
		this.operDate = operDate;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	
	

}
