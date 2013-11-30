package org.esblink.module.auth.dao;

import java.util.List;

import org.esblink.common.base.IPage;
import org.esblink.common.base.QueryObj;
import org.esblink.common.base.gae.IBaseDAO;
import org.esblink.module.auth.action.dto.ModuleDto;
import org.esblink.module.auth.domain.Module;

public interface IModuleDao extends IBaseDAO<Module> {

	/**
	 * 找到指定模块下的子模块
	 * 
	 * @param modulesId
	 * @return
	 */
	public List<Module> findChildModules(long modulesId);

	/**
	 * 找到一个应用下面全部的模块列表
	 * 
	 * @param code
	 * @return
	 */
	List<Module> getModulesByCode(final String code);

	/**
	 * 得到系统中全部的应用模块列表
	 * 
	 * @return
	 */
	List<Module> getApplicationTypeModules();

	List<Long> loadModuleIdByRoleId(final long roleId);
	
	List<ModuleDto> loadDtoAll();
	
	public Module getModuleByUrl(final String url);
	
	public IPage<Module> findPageBy(QueryObj queryObj) ;
	
	/**
	 * 保存或修改
	 * @param entity
	 */
	public void saveOrUpdate(Object[] objs);
	
	/**
	 * 修改
	 * @param entity
	 */
	public void update(Object[] objs);
	

	
	public List<Module> findModulesByName(final String moduleName) ;
	
	public List<Module> findModulesByCode(final String moduleCode);
	
	/**
	 * 根据idList查moduleList
	 * @param idList
	 * @return
	 */
	public List<Module> findModules(final List<Long> idList);

}
