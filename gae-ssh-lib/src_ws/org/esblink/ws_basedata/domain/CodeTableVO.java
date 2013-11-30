package org.esblink.ws_basedata.domain;

import org.esblink.module.basedata.domain.CodeTable;

public class CodeTableVO {
	public CodeTableVO(){
		
	}
	public CodeTableVO(CodeTable codeTable, String[] props){
		if(props==null){
			props=new String[0];
		}
		if(props.length>=1 && !props[0].isEmpty()){
			for(String prop:props){
				if (prop.equalsIgnoreCase("id")) {
					this.id = codeTable.getId();
				} else if (prop.equalsIgnoreCase("codeType")) {
					this.codeType = codeTable.getCodeType();
				} else if (prop.equalsIgnoreCase("ename")) {
					this.ename = codeTable.getEname();
				} else if (prop.equalsIgnoreCase("cname")) {
					this.cname = codeTable.getCname();
				} else if (prop.equalsIgnoreCase("dataId")) {
					this.dataId = codeTable.getDataId();
				} else if (prop.equalsIgnoreCase("display")) {
					this.display = codeTable.getDisplay();
				} else if (prop.equalsIgnoreCase("showColor")) {
					this.showColor = codeTable.getShowColor();
				} else if (prop.equalsIgnoreCase("showValues")) {
					this.showValues = codeTable.getShowValues();
				} else if (prop.equalsIgnoreCase("showValuesEn")) {
					this.showValuesEn = codeTable.getShowValuesEn();
				}  else if (prop.equalsIgnoreCase("matchValues")) {
					this.matchValues = codeTable.getMatchValues();
				}
			}
		}
		else{
			this.id=codeTable.getId();
			this.codeType=codeTable.getCodeType();
			this.ename=codeTable.getEname();
			this.cname=codeTable.getCname();
			this.dataId=codeTable.getDataId();
			this.display=codeTable.getDisplay();
			this.showColor=codeTable.getShortCode();
			this.showValues=codeTable.getShowValues();
			this.showValuesEn=codeTable.getShowValuesEn();
			this.matchValues=codeTable.getMatchValues();
		}
	}
	
	public CodeTableVO(CodeTable codeTable){
		this(codeTable, null);
	}
	
	
	private Long id;
	// 代码类别标识
	private String codeType;
	// 英文名
	private String ename;
	// 中文名
	private String cname;
	// 编码
	private Long dataId;
	// 显示顺序
	private Long display;
	// 显示内容
	private String showValues;
	// 显示内容
	private String showValuesEn;
	//显示颜色
	private String showColor;
	// 对应值
	private String matchValues;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCodeType() {
		return codeType;
	}
	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public Long getDataId() {
		return dataId;
	}
	public void setDataId(Long dataId) {
		this.dataId = dataId;
	}
	public Long getDisplay() {
		return display;
	}
	public void setDisplay(Long display) {
		this.display = display;
	}
	public String getShowValues() {
		return showValues;
	}
	public void setShowValues(String showValues) {
		this.showValues = showValues;
	}
	
	public String getShowValuesEn() {
		return showValuesEn;
	}
	public void setShowValuesEn(String showValuesEn) {
		this.showValuesEn = showValuesEn;
	}
	public String getShowColor() {
		return showColor;
	}
	public void setShowColor(String showColor) {
		this.showColor = showColor;
	}
	public String getMatchValues() {
		return matchValues;
	}
	public void setMatchValues(String matchValues) {
		this.matchValues = matchValues;
	}
	
	


	
}
