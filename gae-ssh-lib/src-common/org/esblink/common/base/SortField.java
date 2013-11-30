package org.esblink.common.base;

public class SortField {
	public SortField(){
		
	}
	/**
	 * {"property":"acType","direction":"ASC"}
	 * @param sortInfo
	 */
	public SortField(String sortInfo){
		if(sortInfo!=null){
			sortInfo=sortInfo.trim().replaceAll("\"", "");
			String[] infos = sortInfo.split(",");
			this.property=infos[0];
			this.property=this.property.substring(this.property.indexOf(":")+1);
			
			this.direction=infos[1];
			this.direction=this.direction.substring(this.direction.indexOf(":")+1);
			this.direction=this.direction.substring(0, this.direction.length()-1);
			
			if(this.direction.equalsIgnoreCase("asc")){
				this.asc=true;
			}			
		}
	}
	private String property;
	private String direction;
	private boolean asc;
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public boolean isAsc() {
		return asc;
	}
	public void setAsc(boolean asc) {
		this.asc = asc;
	}
	
}
