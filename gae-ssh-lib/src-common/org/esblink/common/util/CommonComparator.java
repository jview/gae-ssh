package org.esblink.common.util;

import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.Date;

/**
 * 支持get的属性，如name, getName
 * 如果是boolean属性，则方法全名，如isDel, isDel();
 * 调用方式：
 * CommonComparator comparator = new CommonComparator();   
   comparator.setFields(new String[]{ "codeType", "dataId"});   
   Collections.sort(list, comparator);
 * @author chenjh
 * 2012-5-8
 */
public class CommonComparator implements Comparator {
	/***
	 * @param 比较对象的属性用String[]的形式传过来 比较的对象一定要符合javaBean，即要有Set,Get方法
	 * */
	private String[] fields = null;
	private boolean asc=true;
	private String objName;

	public String[] getFields() {
		return fields;
	}

	public void setFields(String[] fields) {
		this.fields = fields;
	}
	
	/**
	 * sort=true正序,sort=false(反序),default=true
	 * @param sort
	 */
	public CommonComparator(){
		this.asc=true;
	}
	
	/**
	 * sort=true正序,sort=false(反序),default=true
	 * @param sort
	 */
	public CommonComparator(boolean asc){
		this.asc=asc;
	}
	
	/**
	 * 按fields中的属性排序 {"name","age"}
	 * @param fields
	 */
	public CommonComparator(String[] fields){
		this.asc=true;
		this.fields=fields;
	}
	
	
	/**
	 * 按fields中的属性排序 {"name","age"}
	 * sort=true正序,sort=false(反序),default=true
	 * @param sort
	 */
	public CommonComparator(String[] fields, boolean asc){
		this.asc=asc;
		this.fields=fields;
	}

	/**
	 * 定义排序规则 如果按照不止一个属性进行排序 这按照属性的顺序进行排序,类是sql order by 即只要比较出同位置的属性就停止
	 */
	public int compare(Object obj1, Object obj2) {
		if(this.objName==null&&obj1!=null){
			objName = obj1.getClass().getSimpleName();
		}
		// 没有属性，则不排序
		if (fields == null || fields.length == 0) {
			return 2;// 不比较
		}
		int value = 0;
		if(obj1==null&&obj2==null) 
			value=0; 
		else if(obj1==null) 
			value=1; 
		else if(obj2==null) 
			value=-1;
		else{
			for (int i = 0; i < fields.length; i++) {
				value= (compareField(obj1, obj2, fields[i])) ;
				if(value==0){
					continue;
				}
				return value;
			}
		}

		return 0;
		
	}

	/**
	 * @param fieldName
	 *            根据属性名排序
	 */
	private  int compareField(Object o1, Object o2, String fieldName) {
		int v=0;
		try {
			Object obj1 = getFieldValueByName(fieldName, o1);
			Object obj2 = getFieldValueByName(fieldName, o2);
			if(obj1==null&&obj2==null) 
				v=0;
			else if(obj1==null) 
				v=1; 
			else if(obj2==null) 
				v=-1;
			else if(obj1 instanceof Date){
				Date date1 = (Date)obj1;
				Date date2 = (Date)obj2;
				v = date1.compareTo(date2);
			}
			else if(obj1 instanceof Number){
				Number n1 = (Number)obj1;
				Number n2 = (Number)obj2;
				if(n1.floatValue()>n2.floatValue()){
					v=1;
				}
				else if(n1.floatValue()<n2.floatValue()){
					v=-1;
				}
				else{
					v=0;
				}
			}
			else{
				String value1 = ""+obj1;
				String value2 = ""+obj2;
				v = value1.compareTo(value2);		
			}
			
		} catch (Exception e) {
//			System.out.println("-----------------------------------------------");
//			System.out.println("------该属性不存在或者不允许在此安全级别上反射该属性，详情请查阅JAVA DOC--------");
//			System.out.println("-----------------------------------------------");
			e.printStackTrace();
		}
		if(!asc){
			v = -v;
		}
		return v;
	}

	/**
	 * @param fieldName
	 *            属性名 obj 对象 反射获得该属性的值
	 */
	private  Object getFieldValueByName(String fieldName, Object obj) {
		try {
			String methodStr = fieldName;
			if(!fieldName.startsWith("is")){
				String Letter = fieldName.substring(0, 1).toUpperCase();
				methodStr = "get" + Letter + fieldName.substring(1);
			}			
			Method method = obj.getClass().getMethod(methodStr, new Class[] {});

			Object value = method.invoke(obj, new Object[] {});
			return value;
		} catch (Exception e) {
			System.out.println("--------CommonComparator-"+objName+"该" + fieldName	+ "属性不存在---------------");
			
			return null;
		}
	}
}
