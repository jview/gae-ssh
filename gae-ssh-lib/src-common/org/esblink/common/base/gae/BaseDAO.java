package org.esblink.common.base.gae;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.esblink.common.base.IEntity;
import org.esblink.common.base.IPage;
import org.esblink.common.base.Page;
import org.esblink.common.base.QueryObj;
import org.esblink.common.base.QueryResult;
import org.esblink.common.util.ClassUtils;
import org.esblink.module.auth.domain.User;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esblink.dev.util.CommMethod;




@Transactional
public  class BaseDAO<T extends IEntity> implements IBaseDAO<T>{
	protected static final Logger log = Logger.getLogger(BaseDAO.class.getName());
	protected final Class<T> entityClass;
	   public BaseDAO()
	   {
	     try
	     {
//	    	 System.out.println("------------getClass()="+getClass());
	    	 if(this.getClass().getName().indexOf("EnhancerByCGLIB")<0){
//	    		 System.out.println("------------getClass().getGenericSuperclass()="+getClass().getGenericSuperclass());
//		    	 System.out.println("------------getClass().getGenericSuperclass().getActualTypeArguments()="+((java.lang.reflect.ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments());
		    	 this.entityClass = ((Class)((java.lang.reflect.ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0]);	 
	    	 }
	    	 else{
	    		 this.entityClass=null;
	    		 
	    	 }
	    	
	     } catch (Exception e) {
	       String clsName = getClass().getSimpleName();
	       log.warning(this.getClass().getName());
//	       e.printStackTrace();
	       throw new RuntimeException(getClass().getCanonicalName() + "未定义泛型! 继承于:" + BaseDAO.class.getCanonicalName() + "的类都必需声明所操作实体的泛型, 如:\npublic class " + clsName + " extends " + BaseDAO.class.getSimpleName() + "<" + clsName.substring(0, clsName.length() - 3) + "> implements I" + clsName + "{\n\t...\n}");
	       
	     }
	   }
	
	@PersistenceContext protected EntityManager em;
	protected final String SQL_PRE_IN="_in_";
	protected final String SQL_PRE_LIKE="_like_";
	
	public void clear(){
		em.clear();
	}

	public <T> void delete(Class<T> entityClass,Object entityid) {
		delete(entityClass, new Object[]{entityid});
	}

	public <T> void delete(Class<T> entityClass,Object[] entityids) {
		for(Object id : entityids){
			em.remove(em.getReference(entityClass, id));
		}
	}
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public <T> T find(Class<T> entityClass, Object entityId) {
		return em.find(entityClass, entityId);
	}

	public void save(Object entity) {
		em.persist(entity);
	}
	
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public <T> long getCount(Class<T> entityClass) {
		return (Long)em.createQuery("select count("+ getCountField(entityClass) +") from "+ getEntityName(entityClass)+ " o").getSingleResult();
	}
	
	public void update(Object entity) {
		em.merge(entity);
	}
	
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public <T> QueryResult<T> getScrollData(Class<T> entityClass,
			int firstindex, int maxresult, LinkedHashMap<String, String> orderby) {
		return getScrollData(entityClass,firstindex,maxresult,null,null,orderby);
	}
	
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public <T> QueryResult<T> getScrollData(Class<T> entityClass,
			int firstindex, int maxresult, String wherejpql, Object[] queryParams) {
		return getScrollData(entityClass,firstindex,maxresult,wherejpql,queryParams,null);
	}
	
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public <T> QueryResult<T> getScrollData(Class<T> entityClass, int firstindex, int maxresult) {
		return getScrollData(entityClass,firstindex,maxresult,null,null,null);
	}
	
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public <T> QueryResult<T> getScrollData(Class<T> entityClass) {
		return getScrollData(entityClass, -1, -1);
	}
	
	
	public <T> Collection<T> findBy(final T entity, String sortField, boolean isAsc){
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		
		List<Field> fieldList = ClassUtils.getClassFields(entity.getClass(), "private");
		Object obj = null;
		List<String> igList=new ArrayList<String>();
		igList.add("jdoFieldFlags");
		igList.add("jdoFieldTypes");
		igList.add("jdoFieldNames");
		igList.add("jdoInheritedFieldCount");
		igList.add("serialVersionUID");
		igList.add("jdoPersistenceCapableSuperclass");
	
		IPage page=null;
		try {
			boolean isIgnore=false;
			String fieldStr="";
			for(Field field:fieldList){
				 isIgnore=false;
				for(String ig:igList){
					if(ig.equals(field.getName())){
						isIgnore=true;
						break;
					}
				}
				if(isIgnore){
					continue;
				}
				field.setAccessible(true);
				obj = field.get(entity);
				if(obj!=null){
					fieldStr+=field.getName()+",";
					
					paramsMap.put(field.getName(), obj);
				}
			}
			log.finer("----find fieldName="+fieldStr+" paramsMap="+paramsMap);
			page = this.findPageBy(entity.getClass(), paramsMap, -1, -1, sortField, isAsc);
			return page.getData();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public <T> IPage<T> findBy(Class<T> entityClass, QueryObj queryObj){
		IPage<T> result=null;
		try {
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			for(String queryField:queryObj.getQueryObjects()){
				paramsMap.put(queryField, queryObj.getQueryObject(queryField));
			}
//			System.out.println("------paramsMap="+paramsMap+" queryField="+queryObj.getQueryObjects());
			result=this.findPageBy(entityClass, paramsMap, queryObj.getPageSize(), queryObj.getPageIndex(),  queryObj.getSortField(), queryObj.isAsc());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public <T> IPage<T> findPageBy(Class<T> entityClass, QueryObj queryObj){
		IPage<T> result=null;
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		try {
			for(String queryField:queryObj.getQueryObjects()){
				paramsMap.put(queryField, queryObj.getQueryObject(queryField));
			}
//			System.out.println("------paramsMap="+paramsMap+" queryField="+queryObj.getQueryObjects());
			result=this.findPageBy(entityClass, paramsMap, queryObj.getPageSize(), queryObj.getPageIndex(),  queryObj.getSortField(), queryObj.isAsc());
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			log.log(Level.WARNING, "----findPageBy--"+queryObj.getQueryValues()+", exception:"+e.getMessage(), e);
		}
		return result;
	}
	
	public <T> IPage<T> findPageBy(final Class<T> entityClass, final int pageSize, final int pageIndex) throws Exception
	   {
//	     return (IPage)getHibernateTemplate().execute(new HibernateCallback() {
//	       public Object doInHibernate(Session session) throws HibernateException {
//	         Criteria criteria = session.createCriteria(entity.getClass());
//	         criteria.add(Example.create(entity));
//	 
//	         Integer total = Integer.valueOf(BaseEntityDao.this.getCachedCount(criteria, pageIndex));
//	         criteria.setFirstResult(pageSize * pageIndex);
//	         criteria.setMaxResults(pageSize);
//	         return new Page(criteria.list(), total.intValue(), pageSize, pageIndex);
//	       }
//	     });
		
		QueryResult result=this.getScrollData(entityClass, pageSize * pageIndex, pageSize);
		return new Page(result.getResultlist(), result.getTotalrecord(), pageSize, pageIndex);
	   }
	
	 public <T> IPage<T> findPageBy(final Class<T> entityClass, final Map<String, Object> paramsMap, final int pageSize, final int pageIndex, final String sortField, final boolean isAsc)
		     throws Exception
		   {
//		     return (IPage)getHibernateTemplate().execute(new HibernateCallback() {
//		       public Object doInHibernate(Session session) throws HibernateException {
//		         Criteria criteria = session.createCriteria(entity.getClass());
//		         criteria.add(Example.create(entity));
//		 
//		         Integer total = Integer.valueOf(BaseEntityDao.this.getCachedCount(criteria, pageIndex));
//		         if (!CommMethod.isEmpty(sortField)) {
//		           criteria.addOrder(isAsc ? Order.asc(sortField) : Order.desc(sortField));
//		         }
//		         criteria.setFirstResult(pageSize * pageIndex);
//		         criteria.setMaxResults(pageSize);
//		         return new Page(criteria.list(), total.intValue(), pageSize, pageIndex);
//		       }
//		     });
		 	LinkedHashMap<String, String> orderBy = new LinkedHashMap<String, String>();
		 	if(sortField!=null){
		 		orderBy.put(sortField, isAsc==true?"asc":"desc");
		 	}
		 	
		    try {
				QueryResult result=this.getScrollData(entityClass, pageSize * pageIndex, pageSize,paramsMap, orderBy);
				return new Page(result.getResultlist(), result.getTotalrecord(), pageSize, pageIndex);
			} catch (Exception e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
				log.log(Level.WARNING, "----findPageBy--"+paramsMap.size()+", exception:"+e.getMessage(), e);
				return null;
			}
		   }
	 
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public <T> QueryResult<T> getScrollData(Class<T> entityClass, int firstindex, int maxresult
			, String wherejpql, Object[] queryParams, LinkedHashMap<String, String> orderby) {
		QueryResult qr = new QueryResult<T>();
		if(wherejpql!=null && (wherejpql.equals("")||wherejpql.equals("null"))){
			wherejpql=null;
		}
		
		String entityname = getEntityName(entityClass);
		String sql = "select o from "+ entityname+ " o "+(wherejpql==null? "": "where "+ wherejpql)+ buildOrderby(orderby);
		log.finer("-----jpql(select)="+sql);
//		System.out.println("-----------entityname="+entityname+" wherejpql="+wherejpql+" queryParams="+queryParams);
		Query query = em.createQuery(sql);
		setQueryParams(query, queryParams);
		if(firstindex==0 && maxresult==0){
			firstindex=-1;
			maxresult=-1;
			log.warning("-------invalid firstindex=0 and maxresult=0 show all as default");
		}
		if(firstindex!=-1 && maxresult!=-1) 
			query.setFirstResult(firstindex).setMaxResults(maxresult);
		qr.setResultlist(query.getResultList());
		 sql = "select count("+ getCountField(entityClass)+ ") from "+ entityname+ " o "+(wherejpql==null? "": "where "+ wherejpql);
//			System.out.println("-------sql2="+sql);
		 log.finer("-----jpql(count)="+sql);
		query = em.createQuery(sql);
		setQueryParams(query, queryParams);
		qr.setTotalrecord((Long)query.getSingleResult());
		return qr;
	}
	
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public <T> QueryResult<T> getScrollData(Class<T> entityClass, int firstindex, int maxresult
			, Map<String, Object> paramsMap, LinkedHashMap<String, String> orderby) {
		QueryResult qr = new QueryResult<T>();
		String entityname = getEntityName(entityClass);
		String wherejpql="";
		Iterator itor = paramsMap.entrySet().iterator();
		Entry ent = null;
		int i=0;
		Object[] queryParams = new Object[paramsMap.size()];
		String key = null;
		while(itor.hasNext()){
			i++;
			ent = (Entry)itor.next();
			queryParams[i-1]=ent.getValue();
			key = (String)ent.getKey();
			if(key.startsWith(SQL_PRE_IN)){
				key=key.substring(SQL_PRE_IN.length());
				if(ent.getValue() instanceof List){
					wherejpql+=" o."+key+" in ("+CommMethod.getListIds((List)ent.getValue())+") and ";
				}
				else if(ent.getValue() instanceof String){
					wherejpql+=" o."+key+" in "+ent.getValue()+" and ";
				}
			}
			else if(key.startsWith(SQL_PRE_LIKE)){
				key=key.substring(SQL_PRE_LIKE.length());
				wherejpql+=" o."+key+" like ?"+i+" and ";
			}
			else{
				wherejpql+=" o."+key+"=?"+i+" and ";
			}
			
		}
		if(wherejpql.endsWith("and ")){
			wherejpql=wherejpql.substring(0, wherejpql.length()-"and ".length());
		}
//		System.out.println("--------paramsMap="+paramsMap+" wherejpql="+wherejpql);
		return this.getScrollData(entityClass, firstindex, maxresult, wherejpql,  queryParams, orderby);
//		Query query = em.createQuery("select o from "+ entityname+ " o "+(paramsMap.size()==0? "": "where "+ wherejpql)+ buildOrderby(orderby));
//		setQueryParams(query, queryParams);
//		if(firstindex!=-1 && maxresult!=-1) query.setFirstResult(firstindex).setMaxResults(maxresult);
//		qr.setResultlist(query.getResultList());
//		query = em.createQuery("select count("+ getCountField(entityClass)+ ") from "+ entityname+ " o "+(wherejpql==null? "": "where "+ wherejpql));
//		setQueryParams(query, queryParams);
//		qr.setTotalrecord((Long)query.getSingleResult());
//		return qr;
	}
	
	protected void setQueryParams(Query query, Object[] queryParams){
		if(queryParams!=null && queryParams.length>0){
			for(int i=0; i<queryParams.length; i++){
				query.setParameter(i+1, queryParams[i]);
			}
		}
	}
	/**
	 * 组装order by语句
	 * @param orderby
	 * @return
	 */
	protected String buildOrderby(LinkedHashMap<String, String> orderby){
		StringBuffer orderbyql = new StringBuffer("");
		if(orderby!=null && orderby.size()>0){
			orderbyql.append(" order by ");
			for(String key : orderby.keySet()){
				orderbyql.append("o.").append(key).append(" ").append(orderby.get(key)).append(",");
			}
			orderbyql.deleteCharAt(orderbyql.length()-1);
		}
		return orderbyql.toString();
	}
	/**
	 * 获取实体的名称
	 * @param <T>
	 * @param entityClass 实体类
	 * @return
	 */
	protected <T> String getEntityName(Class<T> entityClass){
		String entityname = entityClass.getSimpleName();
		Entity entity = entityClass.getAnnotation(Entity.class);
		if(entity!=null && entity.name()!=null && !"".equals(entity.name())){
			entityname = entity.name();
		}
		return entityname;
	}
	
	protected <T> String getCountField(Class<T> clazz){
		String out = "o";
		try {
			PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(clazz).getPropertyDescriptors();
			for(PropertyDescriptor propertydesc : propertyDescriptors){
				Method method = propertydesc.getReadMethod();
				if(method!=null && method.isAnnotationPresent(EmbeddedId.class)){					
					PropertyDescriptor[] ps = Introspector.getBeanInfo(propertydesc.getPropertyType()).getPropertyDescriptors();
					out = "o."+ propertydesc.getName()+ "." + (!ps[1].getName().equals("class")? ps[1].getName(): ps[0].getName());
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
        return out;
	}
	
	public List<T> findAll(){
		Query q = em.createQuery("select o from " +this.entityClass.getName()+" o ");
		return q.getResultList();
	}
	
	public IPage<T> findPageBy(QueryObj queryObj) {
		if(this.entityClass==null){
			log.warning("-----"+this.getClass().getName()+" entityClass=null");
			return null;
		}
//		DetachedCriteria criteria = generateCriteria(queryObj);
//		return super.findPageBy(criteria, queryObj.getPageSize(), queryObj.getPageIndex(), queryObj.getSortField(), queryObj.isAsc());
		IPage<T> result=null;
		try {
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			for(String queryField:queryObj.getQueryObjects()){
				paramsMap.put(queryField, queryObj.getQueryObject(queryField));
			}
//			System.out.println("------paramsMap="+paramsMap+" queryField="+queryObj.getQueryObjects());
			result=this.findPageBy(this.entityClass, paramsMap, queryObj.getPageSize(), queryObj.getPageIndex(),  queryObj.getSortField(), queryObj.isAsc());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
		
	}
	
	public Collection<T> findBy(QueryObj queryObj) {
		if(this.entityClass==null){
			log.warning("-----"+this.getClass().getName()+" entityClass=null");
			return null;
		}
		 IPage<T> page= this.findBy(this.entityClass, queryObj);
		 return page.getData();
	}
	
	public <T> Collection<T> findBy(T entity){
		return this.findBy(entity, null, false);
	}
	
	public T load(Long id)
	{
		if(this.entityClass==null){
			log.warning("-----"+this.getClass().getName()+" entityClass=null");
			return null;
		}
		return this.find(this.entityClass, id);
	}
}
