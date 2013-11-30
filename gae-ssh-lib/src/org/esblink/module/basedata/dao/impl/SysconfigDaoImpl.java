package org.esblink.module.basedata.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.Query;

import org.esblink.common.base.gae.BaseDAO;
import org.esblink.module.basedata.dao.ISysconfigDao;
import org.esblink.module.basedata.domain.Sysconfig;
import org.springframework.stereotype.Service;

@Service("sysconfigDao")
public class SysconfigDaoImpl extends BaseDAO<Sysconfig> implements ISysconfigDao{
	private static final Logger	logger	= Logger.getLogger(SysconfigDaoImpl.class.getName());
	public void save(List<Sysconfig> entitys) throws Exception{
		for(Sysconfig entity : entitys)
		{
			entity.setModifyDate(new Date());
			this.save(entity);
		}
		
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Sysconfig> findAll()
	{
		Query q = em.createQuery("select from " + Sysconfig.class.getName()+" o ");
		return q.getResultList();
		
	}

	

	@Override
	public Sysconfig findById(Long id)
	{
		Sysconfig sysconfig = (Sysconfig)this.find(Sysconfig.class, id);
		return sysconfig;
	}

	@Override
	public boolean deleteById(Long id)
	{
		Sysconfig d=this.findById(id);
		if(d!=null){
			d.setModifyDate(new Date());
			this.update(d);
		}
		return true;
	}

	
}
