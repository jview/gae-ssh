package org.esblink.module.basedata.dao;

import java.util.List;

import org.esblink.common.base.gae.IBaseDAO;
import org.esblink.module.basedata.domain.LogDb;

//@Service("logDbDao")
public interface ILogDbDao extends IBaseDAO<LogDb> {
	public void save(List<LogDb> entitys) throws Exception;

//	public List<LogDb> findAll();

	public LogDb findById(Long id);

	public boolean deleteById(Long id);

}
