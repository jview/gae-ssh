package org.esblink.ws_basedata.domain;

import org.esblink.module.basedata.domain.Sysconfig;


/**
 * 系统参数
 * @author chenjh
 *
 */
public class SysconfigVO {
	public SysconfigVO(){
		
	}
	public SysconfigVO(Sysconfig sysConfig, String[] props){
		if(props==null){
			props=new String[0];
		}
		if(props.length>=1 && !props[0].isEmpty()){
			for(String prop:props){
				if (prop.equalsIgnoreCase("id")) {
					this.id=sysConfig.getId();
				}
				else if (prop.equalsIgnoreCase("code")) {
					this.code=sysConfig.getCode();
				}
				else if (prop.equalsIgnoreCase("name")) {
					this.name=sysConfig.getName();
				}
				else if (prop.equalsIgnoreCase("value")) {
					this.value=sysConfig.getValue();
				}
				else if (prop.equalsIgnoreCase("valueDefault")) {
					this.valueDefault=sysConfig.getValueDefault();
				}
				else if (prop.equalsIgnoreCase("status")) {
					this.status=sysConfig.getStatus();
				}
				else if (prop.equalsIgnoreCase("remark")) {
					this.remark=sysConfig.getRemark();
				}
			}
		}
		else{
			this.id=sysConfig.getId();
			this.code=sysConfig.getCode();
			this.name=sysConfig.getName();
			this.value=sysConfig.getValue();
			this.valueDefault=sysConfig.getValueDefault();
			this.status=sysConfig.getStatus();
			this.remark=sysConfig.getRemark();
		}
	}
	public SysconfigVO(Sysconfig sysConfig){
		this(sysConfig, null);
	}
	// id
	private Long id;
	// 编码
	private String code;
	// 名称
	private String name;
	// 值
	private String value;
	// 默认值
	private String valueDefault;
	// 状态
	private Long status;
	// 备注
	private String remark;
	// 扩展属性xml
	private String optXml="";
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getValueDefault() {
		return valueDefault;
	}
	public void setValueDefault(String valueDefault) {
		this.valueDefault = valueDefault;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getOptXml() {
		return optXml;
	}
	public void setOptXml(String optXml) {
		this.optXml = optXml;
	}
	
	

}
