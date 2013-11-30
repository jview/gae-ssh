package org.esblink.module.auth.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.esblink.common.base.IPage;
import org.esblink.common.base.QueryObj;
import org.esblink.common.base.context.ApplicationContext;
import org.esblink.common.base.gae.BaseDAO;
import org.esblink.module.auth.action.dto.ModuleDto;
import org.esblink.module.auth.domain.Module;

//@Service("moduleDao")
public class ModuleDao extends BaseDAO<Module> implements IModuleDao {
	public IPage<Module> findPageBy(QueryObj queryObj) {
		return super.findPageBy(queryObj);
	}
	
	
	public void saveOrUpdate(Object[] objs) {		
		for (Object obj : objs)
			super.save(obj);
		
	}

	
	public void update(Object[] objs) {
		for (Object obj : objs)
			super.update(obj);
		
	}
	
	@Override
	public Module getModuleByUrl(final String url) {
		// TODO Auto-generated method stub
		Module search = new Module();
		search.setStatus(1l);
		search.setAction(url);
		Collection<Module> list=this.findBy(search, null, false);
		Module module = null;
		for(Module m:list){
			module = m;
			break;
		}
		return module;
	}
	
	// 通过权限Id找到子权限
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Module> findChildModules(final long modulesId) {
		Module search = new Module();
		search.setStatus(1l);
		search.setParentId(modulesId);
		Collection<Module> list=this.findBy(search, "sort", true);
		List<Module> mList = new ArrayList<Module>();
		mList.addAll(list);
		
		return mList;
	}

	public List<Module> getModulesByCode(final String code) {
		QueryObj queryObj = new QueryObj(-1, -1, "sort", true);
		queryObj.setQueryObject("code", code);
		queryObj.setQueryObject("status", 1l);
		Collection<Module> list=this.findBy(queryObj);
		
		List<Module> res = new ArrayList();
//		Module module = new Module();
//		module.setStatus(1l);
//		module.setCode(code);;
//		Collection<Module> list=this.findBy(module, "sort", true);
		res.addAll(list);
		
		
		for(Module m:list){
			res.addAll(getModulesByPId(m.getId()));
		}
		return res;
	}

	private List<Module> getModulesByPId(final long pid) {
//		Module search = new Module();
//		search.setStatus(1l);
//		search.setParentId(pid);
//		Collection<Module> list =this.findBy(search, "sort", true);
		QueryObj queryObj = new QueryObj(-1, -1, "sort", true);
		queryObj.setQueryObject("parentId", pid);
		queryObj.setQueryObject("status", 1l);
		Collection<Module> list=this.findBy(queryObj);
//		System.out.println("-----------module--list="+list.size());
		List<Module> res = new ArrayList();
		res.addAll(list);
		for (Module m : list) {
			res.addAll(getModulesByPId(m.getId()));
		}
		
		return res;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Module> getApplicationTypeModules() {
//		Module search = new Module();
//		search.setStatus(1l);
//		search.setTypeFlag(2);
//		Collection list=this.findBy(search);
		QueryObj queryObj = new QueryObj(-1, -1, "sort", true);
		queryObj.setQueryObject("typeFlag", 2);
		queryObj.setQueryObject("status", 1l);
		Collection<Module> list=this.findBy(queryObj);
		List<Module> res = new ArrayList();
		res.addAll(list);
		return res;
	}

	@SuppressWarnings("unchecked")
	public List<Long> loadModuleIdByRoleId(final long roleId) {

//		return (List<Long>) getHibernateTemplate().execute(
//				new HibernateCallback() {
//					public Object doInHibernate(Session session)
//							throws HibernateException, SQLException {
//						//status=1排除无效的信息
//						SQLQuery sqlQuery = session
//								.createSQLQuery("select rm.module_id from ts_role_module rm, ts_module m where rm.module_id=m.module_id and m.status=1 and rm.role_id =?");
//						sqlQuery.setLong(0, roleId);
//						sqlQuery.addScalar("module_id", Hibernate.LONG);
//						return sqlQuery.list();
//					}
//				});
		return null;
	}
	
	public List<Module> findModulesByName(final String moduleName) {
		QueryObj queryObj = new QueryObj(-1, -1, "sort", true);
		queryObj.setQueryObject(SQL_PRE_LIKE+"name", moduleName);
		Collection<Module> list=this.findBy(queryObj);
		List<Module> mList=new ArrayList<Module>();
		mList.addAll(list);
		return mList;
	}
	
	public List<Module> findModulesByCode(final String moduleCode) {
		QueryObj queryObj = new QueryObj(-1, -1, "sort", true);
		queryObj.setQueryObject(SQL_PRE_LIKE+"code", moduleCode);
		Collection<Module> list=this.findBy(queryObj);
		List<Module> mList=new ArrayList<Module>();
		mList.addAll(list);
		return mList;
	}
	
	/**
	 * 根据idList查moduleList
	 * @param idList
	 * @return
	 */
	public List<Module> findModules(final List<Long> idList) {
		if (idList == null || idList.size() == 0) {
			return new ArrayList();
		}

		QueryObj queryObj = new QueryObj(-1, -1, null, false);
		queryObj.setQueryObject(SQL_PRE_IN+"id", idList);
		Collection<Module> list=this.findBy(queryObj);
		List<Module> mList=new ArrayList<Module>();
		mList.addAll(list);
		return mList;
	}


	public List<ModuleDto> loadDtoAll() {
		List<Module> moduleList = getModulesByCode(ApplicationContext
				.getContext().getApplication().getName());
		List<ModuleDto> dtoList = new ArrayList<ModuleDto>(moduleList.size());
		for (Module m : moduleList) {
			ModuleDto dto = new ModuleDto();
			dto.setId(m.getId());
			dto.setParentId(m.getParent() == null ? null : m.getParent().getId());
			dto.setText(m.getName());
			if(m.getTypeFlag()!=null)
				dto.setType(m.getTypeFlag().longValue());
			dtoList.add(dto);
		}
		return dtoList;
	}
}
