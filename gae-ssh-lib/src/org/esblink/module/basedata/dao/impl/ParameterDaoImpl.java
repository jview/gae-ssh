package org.esblink.module.basedata.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.Query;

import org.esblink.common.base.gae.BaseDAO;
import org.esblink.module.basedata.dao.IParameterDao;
import org.esblink.module.basedata.domain.Parameter;
import org.springframework.stereotype.Service;

@Service("parameterDao")
public class ParameterDaoImpl extends BaseDAO<Parameter> implements IParameterDao{
	private static final Logger	logger	= Logger.getLogger(ParameterDaoImpl.class.getName());
	public void save(List<Parameter> entitys) throws Exception{
		for(Parameter entity : entitys)
		{
			entity.setModifyDate(new Date());
			this.save(entity);
		}
		
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Parameter> findAll()
	{
		Query q = em.createQuery("select from " + Parameter.class.getName()+" o ");
		return q.getResultList();
		
	}

	

	@Override
	public Parameter findById(Long id)
	{
		Parameter parameter = (Parameter)this.find(Parameter.class, id);
		return parameter;
	}

	@Override
	public boolean deleteById(Long id)
	{
		Parameter d=this.findById(id);
		if(d!=null){
			d.setModifyDate(new Date());
			this.update(d);
		}
		return true;
	}

	
}
