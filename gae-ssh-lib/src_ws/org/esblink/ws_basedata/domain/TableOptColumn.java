package org.esblink.ws_basedata.domain;

public class TableOptColumn {
	private Long id;
	private Long columnOptId;
	private String columnCode;
	private String columnName;
	private Long orderCount;
	private String columnValue;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getColumnCode() {
		return columnCode;
	}
	public void setColumnCode(String columnCode) {
		this.columnCode = columnCode;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getColumnValue() {
		return columnValue;
	}
	public void setColumnValue(String columnValue) {
		this.columnValue = columnValue;
	}
	public Long getOrderCount() {
		return orderCount;
	}
	public void setOrderCount(Long orderCount) {
		this.orderCount = orderCount;
	}
	public Long getColumnOptId() {
		return columnOptId;
	}
	public void setColumnOptId(Long columnOptId) {
		this.columnOptId = columnOptId;
	}
	
}
