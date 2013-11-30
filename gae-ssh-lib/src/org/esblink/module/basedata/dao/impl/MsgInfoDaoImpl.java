package org.esblink.module.basedata.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.esblink.common.base.gae.BaseDAO;
import org.esblink.module.basedata.dao.IMsgInfoDao;
import org.esblink.module.basedata.domain.MsgInfo;
import org.springframework.stereotype.Service;

@Service("msgInfoDao")
public class MsgInfoDaoImpl extends BaseDAO<MsgInfo> implements IMsgInfoDao{
	private static final Logger	logger	= Logger.getLogger(MsgInfoDaoImpl.class.getName());
	public void save(List<MsgInfo> entitys) throws Exception{
		for(MsgInfo entity : entitys)
		{
			entity.setModifyDate(new Date());
			this.save(entity);
		}
		
	}


	

	@Override
	public MsgInfo findById(Long id)
	{
		MsgInfo msgInfo = (MsgInfo)this.find(MsgInfo.class, id);
		return msgInfo;
	}

	@Override
	public boolean deleteById(Long id)
	{
		MsgInfo d=this.findById(id);
		if(d!=null){
			d.setModifyDate(new Date());
			this.update(d);
		}
		return true;
	}

	
}
