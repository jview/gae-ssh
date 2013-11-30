package org.esblink.module.basedata.biz;

import java.util.Collection;
import java.util.List;

import org.esblink.common.base.IPage;
import org.esblink.common.base.QueryObj;
import org.esblink.module.basedata.domain.CodeTable;

//@Service("codeTableBiz")
public interface ICodeTableBiz {

	public IPage<CodeTable> findPageBy(QueryObj queryObj);
	
	public Collection<CodeTable> findBy(CodeTable codeTable);
	
	public void saveCodeTable(CodeTable codeTable) ;

	public CodeTable findCodeTable(Long id);

	


	public void deleteCodeTables(String ids);
	
	
	public Collection<CodeTable> codeTableTree();
	
	/**
	 * codeTableAll
	 * @return
	 */
	public List<CodeTable> codeTableAll();
	
//	/**
//	 * 找出codeTableId重复的数据
//	 * @return
//	 */
//	public List<String> codeTableIdRepeat();
	
	public List<CodeTable> findByTypes(String[] types);
	/**
	 * 根据type类型查找
	 * @param type
	 * @return
	 */
	public List<CodeTable> codeTableByType(String type);
	
	
	
}
