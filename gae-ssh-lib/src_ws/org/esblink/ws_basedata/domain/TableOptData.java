package org.esblink.ws_basedata.domain;

import java.util.List;

public class TableOptData {
	private Long dataId;
	private Long id;
	private Long index;
	private List<TableOptColumn> optColumnList;
	
	public Long getIndex() {
		return index;
	}
	public void setIndex(Long index) {
		this.index = index;
	}
	public Long getDataId() {
		return dataId;
	}
	public void setDataId(Long dataId) {
		this.dataId = dataId;
	}
	public List<TableOptColumn> getOptColumnList() {
		return optColumnList;
	}
	public void setOptColumnList(List<TableOptColumn> optColumnList) {
		this.optColumnList = optColumnList;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	
	
}
