/**
 * 累加器，用于显示页面上每一条记录的序号
 */
package org.esblink.common.util;

import java.util.HashMap;
import java.util.Map;

import com.esblink.dev.util.CommMethod;


//import javax.faces.model.SelectItem;

/**
 * 
 * @author tyler
 *
 */

public class AutoTool {

	private int rowCount=1;
	private int sunInt=0;
	private float sunFloat=0;
	//用于处理在commandLink链接的信息前后加上三个空格

//	private List intItem;
//	private List longItem;
//	private List floatItem;
//	private List doubleItem;
//	private List boolItem;
//	private int itemCount = 5;
//	private int step = 1;



	public int getRowCount() {
		return rowCount++;
	}

	
	public void setRowCount(int rowCount) {
		this.rowCount=rowCount;
	}
	
	
	

	public float getSunFloat() {
		return sunFloat;
	}

	
	public void setSunFloat(float sunFloat) {
		this.sunFloat = sunFloat;
	}

	
	public int getSunInt() {
		return sunInt;
	}

	
	public void setSunInt(int sunInt) {
		this.sunInt = sunInt;
	}
	
	
	/**
	 * 动态累加器
	 */
	private Map map = new HashMap();
	private int mapCount;
	public void clear(){
		this.map.clear();
	}
	
	private double sumValue;
	public double getSumValue(String keys){
		if(this.map.get(keys)!=null && !CommMethod.isEmpty(""+this.map.get(keys))){			
			this.sumValue = Double.parseDouble(""+this.map.get(keys));
		}	
		else{
			this.sumValue = 0;
		}
		return sumValue;
	}
	
	
	/**
	 * 动态加法器
	 * @param keys
	 * @param value
	 */
	public void addSumValue(String keys, double value){
		try{
		if(this.map.get(keys)==null){
			this.map.put(keys, ""+value);			
		}	
		else{
			this.map.put(keys, (((double)this.getSumValue(keys))+value));
		}	
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	public int getMapCount(String value) {
		if(this.map.get(value)!=null && !CommMethod.isEmpty(""+this.map.get(value))){			
			Double d  = Double.parseDouble(""+this.map.get(value));
			this.mapCount = d.intValue();
		}	
		else{
			this.mapCount = 0;
		}
		return mapCount;
	}
	
	
	/**
	 * 动态计数器
	 * @param value
	 */
	public void addMapCount(String value) {
		if(this.map.get(value)==null){
			this.map.put(value, 1);
		}	
		else{
			this.map.put(value, (this.getMapCount(value)+1));
		}		
	}
	public void setMapCount(String value, int count){
		this.map.put(value, count);
	}


//	/**
//	 * @return the intItem
//	 */
//	public List getIntItem() {
//		if(intItem==null){
//			int value = 0;
//			intItem=new LinkedList<SelectItem>();
//			for(int i=0; i<this.itemCount; i++){	
//				value = i+1;
//				intItem.add(new SelectItem(value, ""+value));
//			}
//		}
//		return intItem;
//	}
//
//
//	/**
//	 * @param intItem the intItem to set
//	 */
//	public void setIntItem(List intItem) {
//		this.intItem = intItem;
//	}
//
//
//	/**
//	 * @return the longItem
//	 */
//	public List getLongItem() {
//		if(longItem==null){
//			long value = 0l;
//			longItem=new LinkedList<SelectItem>();
//			for(int i=0; i<this.itemCount; i++){	
//				value = i+1;
//				longItem.add(new SelectItem(value, ""+value));
//			}
//		}
//		return longItem;
//	}
//
//
//	/**
//	 * @param longItem the longItem to set
//	 */
//	public void setLongItem(List longItem) {
//		this.longItem = longItem;
//	}
//
//
//	/**
//	 * @return the floatItem
//	 */
//	public List getFloatItem() {
//		if(floatItem==null){
//			float value = 0f;
//			floatItem=new LinkedList<SelectItem>();
//			for(int i=0; i<this.itemCount; i++){	
//				value = i+1;
//				floatItem.add(new SelectItem(value, ""+value));
//			}
//		}
//		return floatItem;
//	}
//
//
//	/**
//	 * @param floatItem the floatItem to set
//	 */
//	public void setFloatItem(List floatItem) {
//		this.floatItem = floatItem;
//	}
//
//
//	/**
//	 * @return the doubleItem
//	 */
//	public List getDoubleItem() {
//		if(doubleItem==null){
//			double value = 0d;
//			doubleItem=new LinkedList<SelectItem>();
//			for(int i=0; i<this.itemCount; i++){	
//				value = i+1;
//				doubleItem.add(new SelectItem(value, ""+value));
//			}
//		}
//		return doubleItem;
//	}
//
//
//	/**
//	 * @param doubleItem the doubleItem to set
//	 */
//	public void setDoubleItem(List doubleItem) {
//		this.doubleItem = doubleItem;
//	}
//
//
//	/**
//	 * @return the boolItem
//	 */
//	public List getBoolItem() {
//		if(boolItem==null){
//			
//			boolItem=new LinkedList<SelectItem>();
//			
//			boolItem.add(new SelectItem(true, "true"));
//			boolItem.add(new SelectItem(false, "false"));
//			
//		}
//		return boolItem;
//	}
//
//
//	/**
//	 * @param boolItem the boolItem to set
//	 */
//	public void setBoolItem(List boolItem) {
//		this.boolItem = boolItem;
//	}
//	
	
	
	
}
