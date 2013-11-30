package org.esblink.module.basedata.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.esblink.common.base.QueryObj;
import org.esblink.common.base.gae.BaseDAO;
import org.esblink.module.basedata.dao.ILinkRecentlyDao;
import org.esblink.module.basedata.domain.LinkRecently;
import org.springframework.stereotype.Service;

@Service("linkRecentlyDao")
public class LinkRecentlyDaoImpl extends BaseDAO<LinkRecently> implements ILinkRecentlyDao{
	private static final Logger	logger	= Logger.getLogger(LinkRecentlyDaoImpl.class.getName());
	public void save(List<LinkRecently> entitys) throws Exception{
		for(LinkRecently entity : entitys)
		{
			entity.setModifyDate(new Date());
			this.save(entity);
		}
		
	}
	

	@Override
	public LinkRecently findById(Long id)
	{
		LinkRecently linkRecently = (LinkRecently)this.find(LinkRecently.class, id);
		return linkRecently;
	}

	@Override
	public boolean deleteById(Long id)
	{
		LinkRecently d=this.findById(id);
		if(d!=null){
			d.setModifyDate(new Date());
			this.update(d);
		}
		return true;
	}

	/**
	 * 根据用户ID查找最近访问
	 * @param userId,count
	 * @return
	 */
	public List<LinkRecently> findRecently(final Long userId,final int count){
		QueryObj queryObj = new QueryObj(0, count, "modifyDate", false);
		queryObj.setQueryObject("userId", userId);
		Collection list=this.findBy(queryObj);
		List<LinkRecently> res = new ArrayList();
		res.addAll(list);
		
		return res;
	}
	
	/**
	 * 根据用户ID删除最近访问
	 * @param userId
	 */
	
	public void deleteLinkRecentlys(Long synUserId) throws Exception {
		final String syncSQL = "delete from ts_link_recently where user_id=" + synUserId;
//		getHibernateTemplate().execute(new HibernateCallback(){
//			public Object doInHibernate(Session session){
//				Query query = session.createSQLQuery(syncSQL);
//				query.executeUpdate();
//				return null;
//			}
//		});
	}
	
}
