package org.esblink.common.base.gae;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.esblink.common.base.IEntity;
import org.esblink.common.base.IPage;
import org.esblink.common.base.QueryObj;
import org.esblink.common.base.QueryResult;

public interface IBaseDAO<T extends IEntity> {
	/**
	 * 获取记录总数
	 * @param entityClass 实体类
	 * @return
	 */
	public <T> long getCount(Class<T> entityClass);
	/**
	 * 清除一级缓存的数据
	 */
	public void clear();
	/**
	 * 保存实体
	 * @param entity 实体id
	 */
	public void save(Object entity);
	/**
	 * 更新实体
	 * @param entity 实体id
	 */
	public void update(Object entity);
	/**
	 * 删除实体
	 * @param entityClass 实体类
	 * @param entityid 实体id
	 */
	public <T> void delete(Class<T> entityClass, Object entityid);
	/**
	 * 删除实体
	 * @param entityClass 实体类
	 * @param entityids 实体id数组
	 */
	public <T> void delete(Class<T> entityClass, Object[] entityids);
	/**
	 * 获取实体
	 * @param <T>
	 * @param entityClass 实体类
	 * @param entityId 实体id
	 * @return
	 */
	public <T> T find(Class<T> entityClass, Object entityId);
	
	public <T> Collection<T> findBy(T entity, String sortField, boolean isAsc);
	
	public <T> Collection<T> findBy(Class<T> entityClass, QueryObj queryObj);
	
	public <T> IPage<T> findPageBy(Class<T> entityClass, QueryObj queryObj);
	
	public <T> IPage<T> findPageBy(Class<T> entityClass, int pageSize, int pageIndex) throws Exception;
	
	public <T> IPage<T> findPageBy(Class<T> entityClass, Map<String, Object> paramsMap, int pageSize, int pageIndex, String sortField, boolean isAsc) throws Exception;
	/**
	 * 获取分页数据
	 * @param <T>
	 * @param entityClass 实体类
	 * @param firstindex 开始索引
	 * @param maxresult 需要获取的记录数
	 * @return
	 */
	public <T> QueryResult<T> getScrollData(Class<T> entityClass, int firstindex, int maxresult
			, String wherejpql, Object[] queryParams,LinkedHashMap<String, String> orderby);
	
	public <T> QueryResult<T> getScrollData(Class<T> entityClass, int firstindex, int maxresult
			, String wherejpql, Object[] queryParams);
	
	public <T> QueryResult<T> getScrollData(Class<T> entityClass, int firstindex, int maxresult
			, LinkedHashMap<String, String> orderby);
	
	public <T> QueryResult<T> getScrollData(Class<T> entityClass, int firstindex, int maxresult);
	
	public <T> QueryResult<T> getScrollData(Class<T> entityClass);
	
	public <T> List<T> findAll();
	public <T> Collection<T> findBy(QueryObj queryObj);
	public <T> Collection<T> findBy(T entity);
	public <T> IPage<T> findPageBy(QueryObj queryObj);
	public <T> T load(Long id);
}
