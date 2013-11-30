package org.esblink.module.basedata.dao;

import java.util.List;

import org.esblink.common.base.gae.IBaseDAO;
import org.esblink.module.basedata.domain.ParameterUser;

//@Service("parameterUserDao")
public interface IParameterUserDao extends  IBaseDAO<ParameterUser> {
//	public void save(List<ParameterUser> entitys) throws Exception;

	public List<ParameterUser> findAll();

	public ParameterUser findById(Long id);

//	public boolean deleteById(Long id);

}
