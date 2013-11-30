package org.esblink.module.basedata.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.esblink.common.base.gae.BaseDAO;
import org.esblink.module.basedata.dao.ILinkHistoryDao;
import org.esblink.module.basedata.domain.LinkHistory;
import org.esblink.module.basedata.domain.LinkHistoryCount;
import org.springframework.stereotype.Service;

@Service("linkHistoryDao")
public class LinkHistoryDaoImpl extends BaseDAO<LinkHistory> implements ILinkHistoryDao{
	private static final Logger	logger	= Logger.getLogger(LinkHistoryDaoImpl.class.getName());
	public void save(List<LinkHistory> entitys) throws Exception{
		for(LinkHistory entity : entitys)
		{
			entity.setModifyDate(new Date());
			this.save(entity);
		}
		
	}

	

	@Override
	public LinkHistory findById(Long id)
	{
		LinkHistory linkHistory = (LinkHistory)this.find(LinkHistory.class, id);
		return linkHistory;
	}

	@Override
	public boolean deleteById(Long id)
	{
		LinkHistory d=this.findById(id);
		if(d!=null){
			d.setModifyDate(new Date());
			this.update(d);
		}
		return true;
	}


	/**
	 * 根据用户统计各模块访问量
	 * @param userId,为空表示统计所有用户
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LinkHistoryCount> calVisitByUser(final Long userId) {
//		return (List<LinkHistoryCount>)getHibernateTemplate().execute(new HibernateCallback(){
//			public Object doInHibernate(Session session){
//				//增加ename
//				String conSql="";
//				if(userId!=null){
//					 conSql="where user_id="+userId;
//				}
//				String sql = "select  module_id, count(*) from ts_link_history "+conSql+" group by module_id";
////				log.info(sql);
//				Query query = session.createSQLQuery(sql);
//				List list= query.list();
//				List<LinkHistoryCount> tmpList = new ArrayList<LinkHistoryCount>();
//				Object[] objs;
//				LinkHistoryCount tmp = null;
//				for(Object obj:list){
//					objs=(Object[])obj;
//					tmp = new LinkHistoryCount();
//					tmp.setParamTypeId((String)objs[0]);
//					tmp.setCount(Long.parseLong(""+objs[3]));
//					tmpList.add(tmp);
//				}
//				return tmpList;
//				
//			}
//		});
		return null;
	}
	
	/**
	 * 根据模块统计访问量
	 * @param moduleId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LinkHistoryCount> calVisitByModule(final Long moduleId) {
//		return (List<LinkHistoryCount>)getHibernateTemplate().execute(new HibernateCallback(){
//			public Object doInHibernate(Session session){
//				//增加ename
//				String sql = "select  param_type_id, count(*) from ts_link_history where module_id="+moduleId+" group by param_type_id";
////				log.info(sql);
//				Query query = session.createSQLQuery(sql);
//				List list= query.list();
//				List<LinkHistoryCount> tmpList = new ArrayList<LinkHistoryCount>();
//				Object[] objs;
//				LinkHistoryCount tmp = null;
//				for(Object obj:list){
//					objs=(Object[])obj;
//					tmp = new LinkHistoryCount();
//					tmp.setParamTypeId((String)objs[0]);
//					tmp.setCount(Long.parseLong(""+objs[1]));
//					tmpList.add(tmp);
//				}
//				return tmpList;
//				
//			}
//		});
		return null;
	}
}
