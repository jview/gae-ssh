package org.esblink.module.basedata.dao;

import java.util.List;

import org.esblink.common.base.gae.IBaseDAO;
import org.esblink.module.basedata.domain.Parameter;

//@Service("parameterDao")
public interface IParameterDao extends IBaseDAO<Parameter> {
	public void save(List<Parameter> entitys) throws Exception;

	public List<Parameter> findAll();

	public Parameter findById(Long id);

	public boolean deleteById(Long id);

}
