package org.esblink.module.basedata.dao.impl;

import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.Query;

import org.esblink.common.base.gae.BaseDAO;
import org.esblink.module.basedata.dao.IParameterUserDao;
import org.esblink.module.basedata.domain.ParameterUser;
import org.springframework.stereotype.Service;

@Service("parameterUserDao")
public class ParameterUserDaoImpl extends BaseDAO<ParameterUser>  implements IParameterUserDao{
	private static final Logger	logger	= Logger.getLogger(ParameterUserDaoImpl.class.getName());

	@SuppressWarnings("unchecked")
	@Override
	public List<ParameterUser> findAll()
	{
		Query q = em.createQuery("select from " + ParameterUser.class.getName()+" o ");
		return q.getResultList();
		
	}

	

	@Override
	public ParameterUser findById(Long id)
	{
		ParameterUser parameterUser = (ParameterUser)this.find(ParameterUser.class, id);
		return parameterUser;
	}
	
	public Long saveParameterUserValue(final ParameterUser parameterUser){
		boolean isExist=false;
		if(parameterUser.getId()==null||parameterUser.getId().longValue()==0){
			ParameterUser search = new ParameterUser();
			search.setParameterId(parameterUser.getParameterId());
			search.setUserId(parameterUser.getUserId());
			Collection<ParameterUser> paraUserList = this.findBy(search);
			if(paraUserList.size()>0){
				isExist=true;
			}
		}
		else{
			isExist=true;
		}
		
		final boolean isUpdate=isExist;
//		return (Long)getHibernateTemplate().execute(new HibernateCallback(){
//			public Object doInHibernate(Session session){
//				String sql="";
//				
//				//update
//				if(isUpdate){
//					if(CommMethod.isEmpty(parameterUser.getRemark())){
//						parameterUser.setRemark("");
//					}
//					sql = "update ts_parameter_user set value='"+parameterUser.getValue()+"', remark='"+parameterUser.getRemark()+"'" +
//							" where parameter_id="+parameterUser.getParameterId()+" and user_id="+parameterUser.getUserId();
//				}
//				else{
//					String paraUserIds = "nextval('ts_parameter_user_id_seq')";
//					if(DbInfo.isOracle()){
//						paraUserIds="ts_parameter_user_id_seq.nextval";
//					}
//					else if(DbInfo.isHsqldb()){
//						paraUserIds = "next value for ts_parameter_user_id_seq";
//					}
//					sql = "insert into ts_parameter_user (id, user_id, parameter_id, value, remark)" +
//							" values (" +paraUserIds+
//							", "+parameterUser.getUserId()+"" +
//							", "+parameterUser.getParameterId()+"" +
//							", '"+parameterUser.getValue()+"'" +
//							", '"+parameterUser.getRemark()+"')";
//				}
//			  
//                SQLQuery sqlQuery = session.createSQLQuery(sql);
//                
//                sqlQuery.executeUpdate();
//                session.flush();
//                
//                //用于返回的id处理
//                if(isUpdate){
//                	return parameterUser.getId();
//                }
//                else{
//                	String sqlFind="select id from ts_parameter_user" +
//                			"  where parameter_id="+parameterUser.getParameterId()+" and user_id="+parameterUser.getUserId();
//    				
//    				Query query = session.createSQLQuery(sqlFind).addScalar("id", Hibernate.LONG);
//    				List<Long> idList = query.list();
//    				if(idList.size()>0){
//    					return idList.get(0);
//    				}
//                }
//                
//				return null;
//			}
//		});
		return null;
	}


	
}
