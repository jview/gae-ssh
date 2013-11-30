package org.esblink.module.basedata.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;

import org.esblink.common.base.BaseDomain;

/**
 * esblink
 */
@SuppressWarnings("serial")
@Entity
public class ParameterUser extends BaseDomain {
	public ParameterUser(){
		
	}
	public ParameterUser(Parameter parameter){
		if(parameter!=null){
			this.parameterId=parameter.getId();
			this.setCode(parameter.getCode());
			this.name=parameter.getName();
			this.value=parameter.getValue();
			this.defaultValue=parameter.getValue();		
			this.status=parameter.getStatus();
		}
	}
	
	public ParameterUser(ParameterUser parameterUser){
		this.setCode(parameterUser.getCode());
		this.setId(parameterUser.getId());
		this.setValue(parameterUser.getValue());
		this.setDefaultValue(parameterUser.getDefaultValue());
		this.setRemark(parameterUser.getRemark());
		this.setStatus(parameterUser.getStatus());
		this.setName(parameterUser.getName());
		this.setUserId(parameterUser.getUserId());
		this.setParameterId(parameterUser.getParameterId());
	}

	// 名称
	@Basic
	private String name;
	// 参数值
	@Basic
	@Column(name="default_value")
	private String defaultValue;
	// 用户
	@Basic
	@Column(name="user_id")
	private Long userId;
	// 自定义的参数项
	@Basic
	@Column(name="parameter_id")
	private Long parameterId;
	// 参数值
	@Basic
	private String value;
	@Basic
	private Long status;
	// 备注
	@Basic
	private String remark;

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getParameterId() {
		return this.parameterId;
	}

	public void setParameterId(Long parameterId) {
		this.parameterId = parameterId;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	
	
	
}
