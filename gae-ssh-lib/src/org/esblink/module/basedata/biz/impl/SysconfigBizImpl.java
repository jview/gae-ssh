package org.esblink.module.basedata.biz.impl;

import java.util.Collection;
import java.util.Date;

import javax.annotation.Resource;

import org.esblink.common.base.IPage;
import org.esblink.common.base.QueryObj;
import org.esblink.common.base.gae.BaseBIZ;
import org.esblink.common.util.BeanUtils;
import org.esblink.module.basedata.biz.ISysconfigBiz;
import org.esblink.module.basedata.dao.ISysconfigDao;
import org.esblink.module.basedata.domain.Sysconfig;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service("sysconfigBiz")
public class SysconfigBizImpl extends BaseBIZ implements ISysconfigBiz {

	// sysconfigDao
	@Resource(name="sysconfigDao")
	private ISysconfigDao sysconfigDao;

	public void setSysconfigDao(ISysconfigDao sysconfigDao) {
		this.sysconfigDao = sysconfigDao;
	}

	public void saveSysconfig(Sysconfig sysconfig) {
		if(sysconfig.getCreateUser()==null && sysconfig.getModifyUser()!=null){
			sysconfig.setCreateUser(sysconfig.getModifyUser());
		}
		if(sysconfig.getCreateDate()==null){
			sysconfig.setCreateDate(new Date());
		}
		sysconfig.setModifyDate(new Date());
		if(sysconfig.getStatus()==null){
			sysconfig.setStatus(1l);			
		}
		if(sysconfig.getIfDel()==null){
			sysconfig.setIfDel(0l);
		}
		// save sysconfig
		this.sysconfigDao.save(sysconfig);
	}

	public Sysconfig findSysconfig(Long id) {
		// load sysconfig
		Sysconfig sysconfig = this.sysconfigDao.findById(id);

		return sysconfig;
	}

	public Collection<Sysconfig> findBy(Sysconfig sysconfig) {
		if (sysconfig == null) {
			sysconfig = new Sysconfig();
		} else {
			BeanUtils.clearEmptyProperty(sysconfig);
		}
		return this.sysconfigDao.findBy(sysconfig);
	}

	public IPage<Sysconfig> findPageBy(QueryObj queryObj) {
		return this.sysconfigDao.findPageBy(Sysconfig.class, queryObj);
	}

	public void deleteSysconfigs(String ids) {
		String[] idArray = ids.split(",");
		Sysconfig d = null;
		for (String sid : idArray) {
			Long id = Long.parseLong(sid);
			// delete Sysconfig
//			this.sysconfigDao.remove(id);
			d = this.findSysconfig(id);
			d.setIfDel(1l);
			this.saveSysconfig(d);
		}
	}
}
