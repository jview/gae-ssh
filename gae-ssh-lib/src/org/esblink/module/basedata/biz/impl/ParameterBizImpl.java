package org.esblink.module.basedata.biz.impl;


import java.util.Collection;
import java.util.Date;

import javax.annotation.Resource;

import org.esblink.common.base.IPage;
import org.esblink.common.base.QueryObj;
import org.esblink.common.base.gae.BaseBIZ;
import org.esblink.common.util.BeanUtils;
import org.esblink.module.basedata.biz.IParameterBiz;
import org.esblink.module.basedata.dao.IParameterDao;
import org.esblink.module.basedata.domain.Parameter;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service("parameterBiz")
public class ParameterBizImpl extends BaseBIZ implements IParameterBiz {

	// parameterDao
	@Resource(name="parameterDao")
	private IParameterDao parameterDao;

	public void setParameterDao(IParameterDao parameterDao) {
		this.parameterDao = parameterDao;
	}

	public void saveParameter(Parameter parameter) {
		if (parameter.getCreateUser()==null) {
			parameter.setCreateUser(super.getCurrentUser().getId());
			parameter.setCreateDate(new Date());
		}
		
		parameter.setModifyUser(super.getCurrentUser().getId());
		parameter.setModifyDate(new Date());
		
		if(parameter.getIfDel()==null){
			parameter.setIfDel(0l);
		}
		
		// save parameter
		this.parameterDao.save(parameter);
	}

	public Parameter findParameter(Long id) {
		// load parameter
		Parameter parameter = this.parameterDao.findById(id);

		return parameter;
	}

	public Collection<Parameter> findBy(Parameter parameter) {
		if (parameter == null) {
			parameter = new Parameter();
		} else {
			BeanUtils.clearEmptyProperty(parameter);
		}
		return this.parameterDao.findBy(parameter);
	}

	public IPage<Parameter> findPageBy(QueryObj queryObj) {
		return this.parameterDao.findPageBy(Parameter.class, queryObj);
	}

	public void deleteParameters(String ids) {
		String[] idArray = ids.split(",");
		Parameter d = null;
		for (String sid : idArray) {
			Long id = Long.parseLong(sid);
			// delete Parameter
//			this.parameterDao.remove(id);
			d = this.findParameter(id);
			d.setIfDel(1l);
			this.saveParameter(d);
		}
	}
}
