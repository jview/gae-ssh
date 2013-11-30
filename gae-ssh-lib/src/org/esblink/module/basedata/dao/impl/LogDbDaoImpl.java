package org.esblink.module.basedata.dao.impl;

import java.util.List;
import java.util.logging.Logger;

import org.esblink.common.base.gae.BaseDAO;
import org.esblink.module.basedata.dao.ILogDbDao;
import org.esblink.module.basedata.domain.LogDb;
import org.springframework.stereotype.Service;

@Service("logDbDao")
public class LogDbDaoImpl extends BaseDAO<LogDb> implements ILogDbDao{
	private static final Logger	logger	= Logger.getLogger(LogDbDaoImpl.class.getName());
	public void save(List<LogDb> entitys) throws Exception{
		for(LogDb entity : entitys)
		{
//			entity.setModifyDate(new Date());
			this.save(entity);
		}
		
	}

	

	@Override
	public LogDb findById(Long id)
	{
		LogDb logDb = (LogDb)this.find(LogDb.class, id);
		return logDb;
	}

	@Override
	public boolean deleteById(Long id)
	{
//		LogDb d=this.findById(id);
//		if(d!=null){
//			d.setModifyDate(new Date());
//			this.update(d);
//		}
		return true;
	}

	
}
