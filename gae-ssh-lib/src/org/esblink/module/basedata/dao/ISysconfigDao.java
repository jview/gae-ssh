package org.esblink.module.basedata.dao;

import java.util.List;

import org.esblink.common.base.gae.IBaseDAO;
import org.esblink.module.basedata.domain.Sysconfig;

//@Service("sysconfigDao")
public interface ISysconfigDao extends IBaseDAO<Sysconfig> {
	public void save(List<Sysconfig> entitys) throws Exception;

	public List<Sysconfig> findAll();

	public Sysconfig findById(Long id);

	public boolean deleteById(Long id);

}
