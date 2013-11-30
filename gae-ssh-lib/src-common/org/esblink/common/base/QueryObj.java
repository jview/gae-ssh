package org.esblink.common.base;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.ServletActionContext;
import org.esblink.common.base.action.BaseGridAction;

/**
 * <pre>
 * *********************************************
 * Copyright esblink.net.
 * All rights reserved. 
 * Description: 查询对象
 * </pre>
 */
public class QueryObj {

	// 查询字段、查询值
//	private Map<String, String> values = new HashMap<String, String>();
	private Map valueMap = new HashMap(); 
	// 每页记录数
	private int pageSize;
	// 查询第几页
	private int pageIndex;
	// 排序字段
	private String sortField;
	private List<SortField> sortFields = new ArrayList();
	// 是否升序排序
	private boolean asc;
	public QueryObj(int pageIndex, int pageSize, String sortField, boolean isAsc){
		this.pageIndex=pageIndex;
		this.pageSize=pageSize;
		this.sortField=sortField;
		this.asc=isAsc;
	}
	private QueryObj(BaseGridAction<?> action) {
		this.pageSize = action.getPageSize();
		this.pageIndex = action.getPageIndex();
		this.sortField = action.getSortField();
		this.asc = "ASC".equalsIgnoreCase(action.getSortDirection());
		// get query field、value
		parseQueryValues();
	}

	public static QueryObj parseQueryObj(BaseGridAction<?> action) {
		return new QueryObj(action);
	}
	
	public QueryObj(Map<?, ?> params) {
        if(params != null){
//            this.pagingParams = new PagingParams(params);
            parseQueryValues(params);
        }
	}
    
    private void parseQueryValues(Map<?, ?> params) {
        Iterator<?> paramIt = params.keySet().iterator();
        while (paramIt.hasNext()) {
            Object paramKey = paramIt.next();
            if(String.class.isInstance(paramKey)){
                String val = null;
                Object tmp = params.get(paramKey);
                if(String.class.isInstance(tmp)){
                    val = String.class.cast(tmp);
                }else if(String[].class.isInstance(tmp)){
                    String[] tmpArray = String[].class.cast(tmp);
                    if(tmpArray.length > 0){
                        val = tmpArray[0];
                    }
                }
                if (val != null && !"".equals(val.trim()) && !("rows".equals(paramKey)||"page".equals(paramKey))) {
                    valueMap.put(String.valueOf(paramKey), val);
                }
            }
        }
    }

//	public Set<String> getQueryFields() {
//		return values.keySet();
//	}
	
	public Set<String> getQueryObjects() {
		return valueMap.keySet();
	}
	
	public String getQueryValues(){
		Object[] objs = this.valueMap.keySet().toArray();
		String sqls="";
		for(Object obj:objs){
			sqls = sqls+obj+":"+this.valueMap.get(obj)+",";
		}
		return sqls;
	}

//	public String getQueryValue(String queryField) {
//		return values.get(queryField);
//	}

//	public void setQueryValue(String queryField, String queryValue) {
//		values.put(queryField, queryValue);
//	}
	
	public Object getQueryObject(String queryField) {
		return this.valueMap.get(queryField);
	}
	
	public void setQueryObject(String queryField, Object queryValue) {
		valueMap.put(queryField, queryValue);
	}
	
	public boolean containsQueryField(String queryField) {
		return valueMap.containsKey(queryField);
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public boolean isAsc() {
		return asc;
	}

	public void setAsc(boolean asc) {
		this.asc = asc;
	}

	private void parseQueryValues() {
		this.sortFields.clear();
		Enumeration<?> params = ServletActionContext.getRequest().getParameterNames();
		while (params.hasMoreElements()) {
			Object param = params.nextElement();
			if (param instanceof String) {
				String queryField = (String) param;
				if (queryField.startsWith("query.")) {
					String queryValue = ServletActionContext.getRequest().getParameter(queryField);
					if ((queryValue != null) && (!"".equals(queryValue.trim()))) {
						queryField = queryField.substring(6);
						valueMap.put(queryField, queryValue);
					}
				}
			}
		}
		
		if(sortField!=null && sortField.startsWith("[")){
			String sortStr = sortField.substring(1, sortField.length()-1);
			String[] sorts = sortStr.split("},");
			SortField sField = null;
			for(String sort:sorts){
				if(!sort.endsWith("}")){
					sort = sort+"}";
				}
				sField = new SortField(sort);
				this.sortFields.add(sField);
			}		
			if(this.sortFields.size()>0){
				sField = this.sortFields.get(0);
				System.out.println("----"+sField.getProperty()+" "+sField.getDirection()+" "+sField.isAsc());
				this.sortField=sField.getProperty();				
				this.asc=sField.isAsc();
			}
			else{
				this.sortField="";
			}			
		}
	}	
}
