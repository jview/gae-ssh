package org.esblink.common.base;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 查询参数类，和QueryObj类的功能类似，可以自由创建
 */
public class QueryParam {

	// 查询字段、查询值
	private Map<String, String> values = new HashMap<String, String>();
	
	private PagingParams pagingParams = null;
	
	public QueryParam(Map<?, ?> params) {
        if(params != null){
            this.pagingParams = new PagingParams(params);
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
                if (val != null && !"".equals(val.trim())) {
                    values.put(String.valueOf(paramKey), val);
                }
            }
        }
    }

	public Set<String> getQueryFields() {
		return values.keySet();
	}

	public String getQueryValue(String queryField) {
		return values.get(queryField);
	}

	public boolean containsQueryField(String queryField){
	    return values.containsKey(queryField);
	}
	
	public void setQueryValue(String queryField, String queryValue) {
		values.put(queryField, queryValue);
	}

	public int getPageIndex() {
        return pagingParams.getPageIndex();
    }

    public int getPageSize() {
        return pagingParams.getPageSize();
    }

    public int getPageStart() {
        return pagingParams.getPageStart();
    }

    public String getSortField() {
        return pagingParams.getSortField();
    }

    public boolean isAsc() {
        return pagingParams.isAsc();
    }
}
