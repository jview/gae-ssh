package org.esblink.module.basedata.dao;

import java.util.Collection;
import java.util.List;

import org.esblink.common.base.IPage;
import org.esblink.common.base.QueryObj;
import org.esblink.common.base.gae.IBaseDAO;
import org.esblink.module.basedata.domain.CodeTable;

//@Service("codeTableDao")
public interface ICodeTableDao extends IBaseDAO<CodeTable>{
	public Collection<CodeTable> findBy(QueryObj queryObj);

	public IPage<CodeTable> findPageBy(QueryObj queryObj);
	
	public void save(List<CodeTable> entitys) throws Exception;

//	public List<CodeTable> findAll();

	public CodeTable findById(Long id);

	public boolean deleteById(Long id);

}
