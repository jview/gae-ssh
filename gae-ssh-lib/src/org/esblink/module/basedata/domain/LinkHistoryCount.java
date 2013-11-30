package org.esblink.module.basedata.domain;


public class LinkHistoryCount {
	private Long moduleId;
	private Long userId;
	private String paramTypeId;
	private Long count;
	public Long getModuleId() {
		return moduleId;
	}
	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getParamTypeId() {
		return paramTypeId;
	}
	public void setParamTypeId(String paramTypeId) {
		this.paramTypeId = paramTypeId;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	
}
