package org.esblink.ws_basedata.domain;

import org.esblink.module.basedata.domain.MsgInfo;

public class MsgInfoVO {
	public MsgInfoVO(){
		
	}
	
	public MsgInfoVO(MsgInfo mInfo, String[] props){
		if(props==null){
			props=new String[0];
		}
		if(props.length>=1 && !props[0].isEmpty()){
			for(String prop:props){
				if (prop.equalsIgnoreCase("id")) {
					this.id = mInfo.getId();
				} else if (prop.equalsIgnoreCase("sysType")) {
					this.sysType=mInfo.getSysType();
				}
				else if (prop.equalsIgnoreCase("code")) {
					this.code=mInfo.getCode();
				}
				else if (prop.equalsIgnoreCase("nameCn")) {
					this.nameCn=mInfo.getNameCn();
				}
				else if (prop.equalsIgnoreCase("nameEn")) {
					this.nameEn=mInfo.getNameEn();
				}
				else if (prop.equalsIgnoreCase("nameTw")) {
					this.nameTw=mInfo.getNameTw();
				}
				else if (prop.equalsIgnoreCase("descs")) {
					this.descs=mInfo.getDescs();
				}
			}
		}
		else{
			this.id=mInfo.getId();
			this.sysType=mInfo.getSysType();
			this.code=mInfo.getCode();
			this.nameCn=mInfo.getNameCn();
			this.nameEn=mInfo.getNameEn();
			this.nameTw=mInfo.getNameTw();
			this.descs=mInfo.getDescs();
		}
	}
	
	public MsgInfoVO(MsgInfo mInfo){
		this(mInfo, null);
	}
	private Long id;
	private Integer index;
	// 系统类型
	private String sysType;
	// 中文名称
	private String nameCn;
	// 英文名称
	private String nameEn;
	// 繁体名称
	private String nameTw;
	// 编码
	private String code;
	// 顺序号
	private Long orderCount;
	// 描述
	private String descs;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getSysType() {
		return sysType;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(Long orderCount) {
		this.orderCount = orderCount;
	}

	public String getDescs() {
		return descs;
	}

	public void setDescs(String descs) {
		this.descs = descs;
	}
	
	
}
