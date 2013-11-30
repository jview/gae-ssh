package org.esblink.module.auth.biz;

import java.util.Collection;
import java.util.List;

import org.esblink.common.base.IPage;
import org.esblink.common.base.QueryObj;
import org.esblink.common.base.gae.IBaseBIZ;
import org.esblink.module.auth.domain.Module;


public interface IModuleBiz extends IBaseBIZ {
	public void saveModule(Module module);

	public Module findModule(Long id);
	
	/**
	 * 根据idList查moduleList
	 * @param idList
	 * @return
	 */
	public List<Module> findModules(final List<Long> idList);

	public Collection<Module> findBy(Module module);

	public IPage<Module> findPageBy(QueryObj queryObj);
	
	public List<Module> findModules(final String moduleName) ;
	
	public List<Module> findModulesByCode(final String moduleCode);
	
	public List<Module> findModuleByRoleId(Long roleId);

	public void deleteModules(String ids);
	public Collection<Module> findModulesByParentId(Long parentId);
	public List<Module> queryModuleList(Module module, int pageSize, int pageNum, long rootModuleId) ;
	public StringBuffer queryModuleJson(Module module, int pageSize, int pageNum, long rootTasksId) ;
//	public PaginatedList queryModuleList(Module func, int pageSize, int pageNum);
	/**
	 * 动态改名
	 * @param id
	 * @param title
	 * @return
	 */
	public Boolean ajaxUpdateTitle(Long id,String title);
	public void ajaxRemoveNode(Long id);
	public void ajaxMoveNode(long id, long oldParentId, long newParentId, long nodeIndex);
	
	public void downNode(long parentId, long minIndex, long maxIndex);
	public void upNode(long parentId, long minIndex, long maxIndex);
	
//	/**
//	 * 按模块自动将包中所有接口类的方法与模块关联写对数据库形成每个类对应其下的相应方法表
//	 * @param packageName
//	 * @param auth_module
//	 * @return
//	 */
//	public String updateOperAsModule(String packageName, String auth_module, Long moduleId);
}
