package org.esblink.common.base;

import java.util.Map;

/**
 * 处理分页参数
 * 
 * @since pms version 2011-10-28
 */
public class PagingParams {
    
    // 每页记录数
    private int pageSize;
    private int pageStart;
    // 排序字段
    private String sortField;
    // 是否升序排序
    private boolean asc;
    
    public PagingParams(Map<?, ?> values){
        if (values.containsKey("start")) {
            Object tmp = values.get("start");
            String val = null;
            if(String.class.isInstance(tmp)){
                val = String.class.cast(tmp);
            }else if(String[].class.isInstance(tmp)){
                String[] tmpArray = String[].class.cast(tmp);
                if(tmpArray.length > 0){
                    val = tmpArray[0];
                }
            }
            if(val != null && !"".equals(val.trim())){
                this.pageStart = Integer.parseInt(val);
            }
        }
        if (values.containsKey("limit")) {
            Object tmp = values.get("limit");
            String val = null;
            if (String.class.isInstance(tmp)) {
                val = String.class.cast(tmp);
            } else if (String[].class.isInstance(tmp)) {
                String[] tmpArray = String[].class.cast(tmp);
                if (tmpArray.length > 0) {
                    val = tmpArray[0];
                }
            }
            if (val != null && !"".equals(val.trim())) {
                this.pageSize = Integer.parseInt(val);
            } else {
                this.pageSize = getDefaultPageSize();
            }
        } else {
            this.pageSize = getDefaultPageSize();
        }
        
        if (values.containsKey("sort")) {
            Object tmp = values.get("sort");
            String val = null;
            if(String.class.isInstance(tmp)){
                val = String.class.cast(tmp);
            }else if(String[].class.isInstance(tmp)){
                String[] tmpArray = String[].class.cast(tmp);
                if(tmpArray.length > 0){
                    val = tmpArray[0];
                }
            }
            if(val != null && !"".equals(val.trim())){
                this.sortField = val;
            }
        }
        if (values.containsKey("dir")) {
            Object tmp = values.get("dir");
            String val = null;
            if(String.class.isInstance(tmp)){
                val = String.class.cast(tmp);
            }else if(String[].class.isInstance(tmp)){
                String[] tmpArray = String[].class.cast(tmp);
                if(tmpArray.length > 0){
                    val = tmpArray[0];
                }
            }         
            if(val != null && !"".equals(val.trim())){
                this.asc = "ASC".equalsIgnoreCase(val);
            }            
        }        
    }
    
    /**
     * 获取当前页码
     *
     * @return 当前页码
     */
    public int getPageIndex() {
        int start = getPageStart();
        int limit = getPageSize();
        if (start == 0 || limit == 0 || start < limit)
            return 0;
        return start / limit;
    }
    
    private int getDefaultPageSize() {
        return 20;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageStart() {
        return pageStart;
    }

    public void setPageStart(int pageStart) {
        this.pageStart = pageStart;
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
}
