package org.esblink.module.basedata.dao.impl;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.esblink.common.base.IPage;
import org.esblink.common.base.QueryObj;
import org.esblink.common.base.gae.BaseDAO;
import org.esblink.module.basedata.dao.ICodeTableDao;
import org.esblink.module.basedata.domain.CodeTable;
import org.springframework.stereotype.Service;

@Service("codeTableDao")
public class CodeTableDaoImpl extends BaseDAO<CodeTable> implements ICodeTableDao{
	private static final Logger	logger	= Logger.getLogger(CodeTableDaoImpl.class.getName());
	
	public Collection<CodeTable> findBy(QueryObj queryObj) {
		return super.findBy(queryObj);
	}

	public IPage<CodeTable> findPageBy(QueryObj queryObj) {
		IPage<CodeTable> result=null;
		try {
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			for(String queryField:queryObj.getQueryObjects()){
				paramsMap.put(queryField, queryObj.getQueryObject(queryField));
			}
//			System.out.println("------paramsMap="+paramsMap+" queryField="+queryObj.getQueryObjects());
			result=super.findPageBy(CodeTable.class, paramsMap, queryObj.getPageSize(), queryObj.getPageIndex(),  queryObj.getSortField(), queryObj.isAsc());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.warning("-----findPageBy--"+queryObj.getQueryValues()+", exception:"+e.getMessage());
		}
		return result;
		
	}
	
	@Override
	public void save(List<CodeTable> entitys) throws Exception{
		for(CodeTable entity : entitys)
		{
			entity.setModifyDate(new Date());
			this.save(entity);
		}
		
	}

	

	@Override
	public CodeTable findById(Long id)
	{
		CodeTable codeTable = (CodeTable)this.find(CodeTable.class, id);
		return codeTable;
	}

	@Override
	public boolean deleteById(Long id)
	{
		CodeTable d=this.findById(id);
		if(d!=null){
			d.setModifyDate(new Date());
			this.update(d);
		}
		return true;
	}

	
}
