package org.esblink.module.auth.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.esblink.common.base.action.BaseAction;
import org.esblink.common.util.AutoTool;
import org.esblink.module.auth.action.dto.ModuleDto;
import org.esblink.module.auth.action.dto.UserRoleDto;
import org.esblink.module.auth.biz.IModuleBiz;
import org.esblink.module.auth.biz.IRoleBiz;
import org.esblink.module.auth.domain.Module;
import org.esblink.module.auth.domain.ModuleCheckVO;
import org.esblink.module.auth.domain.ModuleVO;
import org.esblink.module.auth.domain.Role;
import org.esblink.module.basedata.biz.ILogDbBiz;
import org.esblink.module.basedata.domain.LogDb;

//@Controller("roleAction")
//@Scope("prototype")
public class RoleAction extends BaseAction {
	private static final long serialVersionUID = 4155193901591626953L;
	private String moduleCode="role";
	private String modelCode = getClass().getSimpleName();
//	@Resource(name="logDbBiz")
	private ILogDbBiz logDbBiz;
	private LogDb logDb = new LogDb(moduleCode, modelCode);
	public void setLogDbBiz(ILogDbBiz logDbBiz) {
		this.logDbBiz = logDbBiz;
	}
	
//	@Resource(name="roleBiz")
	private IRoleBiz roleBiz;
//	@Resource(name="moduleBiz")
	private IModuleBiz moduleBiz;
	private Collection rows;
	private List<Role> roles;
	private List<UserRoleDto> userRoleList;
	private List<ModuleDto> moduleList;
	private List<Module> allModule;
	private List<ModuleVO> allModuleVO;
	private List<ModuleCheckVO> allModuleCheckVO;
	private Role role;
	private String ids;
	private String msg;
	private Boolean success = true;
	private String empCode;
	private String username;
	private Boolean existRoleFlag;
	// 角色查询条件
	private String roleFil;

	public List<ModuleDto> getModuleList() {
		return moduleList;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public Boolean getSuccess() {
		return success;
	}

	public String getMsg() {
		return msg;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getIds() {
		return ids;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public void setRoleBiz(IRoleBiz roleBiz) {
		this.roleBiz = roleBiz;
	}

	public void setModuleBiz(IModuleBiz moduleBiz) {
		this.moduleBiz = moduleBiz;
	}

	public String loadAllRole() {
		roles = roleBiz.loadAllRole();
		List rList = new ArrayList();
		if(this.roleFil!=null){
			for(Role role:roles){
				if(role.getName().indexOf(this.roleFil)>=0){
					rList.add(role);
				}
			}
			roles=rList;
		}
		this.rows=roles;
		return SUCCESS;
	}
	
    public String loadRoleByUsername() {
        userRoleList = roleBiz.loadRoleByUsername(username);
        return SUCCESS;
    }
    
	public String loadMyRole() {
	    userRoleList = roleBiz.loadMyRole();
		return SUCCESS;
	}

	public String loadUserRole() {
	    userRoleList = roleBiz.loadUserRole(empCode);
		return SUCCESS;
	}
	
	/*
	 * 得到所有Module的Action 由于一直不能传角色参数， 所以通过页面script方法替换掉clsField参数，保存相关角色过滤参数。
	 */
	@SuppressWarnings("unchecked")
	public String moduleCheckList() throws Exception {
		Map<String, Object> parames = (Map<String, Object>) ServletActionContext
				.getContext().getParameters();
		String[] nodeIds = (String[]) parames.get("node");
		Long nodeIdLong = new Long(nodeIds[0].toString());
		Long roleIdLong=0l;
		log.info("  id="+super.getId()+" nodeId="+nodeIdLong+" roleId="+roleIdLong);
		allModuleCheckVO = roleBiz.findRoleModulesByModuleAndRole(nodeIdLong, roleIdLong);
		for(ModuleCheckVO m:allModuleCheckVO){
			if(nodeIdLong.longValue()==1){
				m.setExpanded(true);
			}
		}
		log.info("--moduleCheckList()----------getId()="+super.getId()+" roleIds="+roleIdLong+" allModule size="+allModuleCheckVO.size());
		return SUCCESS;
	}

	public String loadRoleModule() {
		String nodeId=ServletActionContext.getRequest().getParameter("node");
		System.out.println("---loadRoleModule id="+this.getId()+" nodeId="+nodeId);
		
		ModuleDto moduleRoot = roleBiz.loadRoleModule(getId());
		if (moduleRoot != null) {
			moduleList = new ArrayList<ModuleDto>(0);
			moduleList.add(moduleRoot);
		}
		
		return SUCCESS;
	}
	
	private String moduleNameAtKey=this.getClass().getSimpleName()+"_moduleNameAt";
	private String moduleListKey=this.getClass().getSimpleName()+"_moduleList";
	/**
	 * 返回找到用户所在的层级
	 * @return
	 */
	public String moduleTreeSearch(){
		String moduleName = ServletActionContext.getRequest().getParameter("moduleName");
		log.info("-------moduleTreeSearch--moduleName="+moduleName);
		String keys = this.getCurrentUser().getId()+"__"+moduleName;
		HttpSession session = ServletActionContext.getRequest().getSession();
		
		AutoTool moduleNameAt=(AutoTool)session.getAttribute(moduleNameAtKey);
		if(moduleNameAt==null){
			moduleNameAt = new AutoTool();
			session.setAttribute(moduleNameAtKey, moduleNameAt);
		}
		moduleNameAt.addMapCount(keys);
		
		List<Module> moduleList = this.moduleBiz.findModules(moduleName);
		
		int count = moduleNameAt.getMapCount(keys);
		Module dist=null;				
		if(moduleList.size()>=count){
			int tmp_count=0;
			for(Module tmp:moduleList){
				dist = tmp;
				tmp_count++;	
				if(count==tmp_count){
					break;
				}
			}
			String ids = this.getTreePath(dist);
			
			ids = ""+ids+"/"+dist.getId();
//			log.info("=======moduleTreeSearch=="+ids);
			this.ids=ids;
			if(moduleList.size()==count){
				moduleNameAt.setMapCount(keys, 0);
			}
//			System.out.println("=================="+keys+"---------"+count+" "+dist.getId());
		}
		return SUCCESS;
	}
	
	/**
	 * 递归取得用户所在部门层级
	 * @param dist
	 * @return
	 */
	private String getTreePath(Module dist){
		HttpSession session = ServletActionContext.getRequest().getSession();
		List<Module> moduleList = (List<Module>)session.getAttribute(moduleListKey);
		if(moduleList==null){
			moduleList = this.moduleBiz.findModules("");
			session.setAttribute(moduleListKey, moduleList);
		}
		Module parent = null;		
		String ids = "";
		
		if(dist!=null&&dist.getParentId()!=null&&dist.getParentId()>0){
//			parent= this.moduleBiz.findModule(dist.getParentId());
			for(Module module:moduleList){
				if(module.getId().longValue()==dist.getParentId().longValue()){
					parent=module;
					break;
				}
			}
			return getTreePath(parent)+"/"+dist.getParentId();
		}
		else{
			return "";
		}
//		return parent;
	}

	public String loadRoleModuleIds() {
		this.ids = roleBiz.loadRoleModuleId(getId());
		return SUCCESS;
	}

	public String saveOrUpdateRole() {
		try {
			this.roleBiz.saveOrUpdateRole(role);
			this.logDbBiz.info(this.getCurrentUser(), logDb, "saveOrUpdateRole", "role_id="+role.getId(),"", SUCCESS, role);
		} catch (Exception e) {
			success = false;
			msg = e.toString();
			log.error("save role error, role name:" + role.getName(), e);
			this.logDbBiz.error(this.getCurrentUser(), logDb, "saveOrUpdateRole", "role_id="+role.getId(),FAILURE+":"+msg,FAILURE, role);
		}
		return SUCCESS;
	}
	
	/*
	 * 复制角色动作
	 */
	public String copyRole() throws Exception {
		try {
			
			int size=this.roleBiz.findRoleByName(role.getName()).size();
			if(size>0){
				msg="角色名已存在";
				return SUCCESS;
			}
			roleBiz.copyRole(role);			
			success = true;
			this.logDbBiz.info(this.getCurrentUser(), logDb, "copyRole", "role_id="+roleFil,"",SUCCESS, role);
		} catch (Exception e) {
			msg=e.getMessage();
			this.logDbBiz.error(this.getCurrentUser(), logDb, "copyRole", "role_id="+role.getId(),FAILURE+":"+e.getMessage(),FAILURE,role);
			success = false;
		}
		return SUCCESS;
	}
	
	/*
	 * 判断角色是否存在
	 */
	public String existRole() throws Exception {
		existRoleFlag = this.roleBiz.findRoleByName(role.getName()).size() > 0;
		return SUCCESS;
	}

	public String deleteRole() {
		try {
			this.roleBiz.deleteRole(super.getId());
			this.logDbBiz.info(this.getCurrentUser(), logDb, "deleteRole", "role_id="+super.getId(),"", SUCCESS, null);
		} catch (Exception e) {
			success = false;
			msg = e.toString();
			log.error("delete role error, role id:" + super.getId(), e);
			this.logDbBiz.error(this.getCurrentUser(), logDb, "deleteRole", "role_id="+super.getId(),FAILURE+":"+msg,SUCCESS, null);
		}
		return SUCCESS;
	}

	public String saveRoleModule() {
		try {
			List<Long> moduleIds = new ArrayList<Long>();
			if (ids != null) {
				String[] is = ids.split(",");
				for (String i : is) {
					if (i.length() > 0)
						moduleIds.add(Long.valueOf(i));
				}
			}

			this.roleBiz.saveRoleModule(role, moduleIds);
			this.logDbBiz.info(this.getCurrentUser(), logDb, "saveRoleModule", "role_id="+role.getId()+" moduleIds="+ids, "", SUCCESS, null);
		} catch (Exception e) {
			success = false;
			msg = e.toString();
			log.error("save role module error, role id:" + super.getId(), e);
			this.logDbBiz.error(this.getCurrentUser(), logDb, "saveRoleModule", "role_id="+role.getId()+" moduleIds="+ids, FAILURE+":"+msg,FAILURE, null);
		}
		return SUCCESS;
	}

	public List<Role> getRoles() {
		return roles;
	}

    public List<UserRoleDto> getUserRoleList() {
        return userRoleList;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

	public String getRoleFil() {
		return roleFil;
	}
	
	public List<Module> getAllModule() {
		return allModule;
	}	

	public List<ModuleCheckVO> getAllModuleCheckVO() {
		return allModuleCheckVO;
	}
	public List<ModuleVO> getAllModuleVO() {
		return allModuleVO;
	}

	public void setRoleFil(String roleFil) {
		this.roleFil = roleFil;
	}

	public Boolean getExistRoleFlag() {
		return existRoleFlag;
	}

	public Collection getRows() {
		return rows;
	}
    
    
}
