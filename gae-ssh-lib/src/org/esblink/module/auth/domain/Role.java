package org.esblink.module.auth.domain;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

import org.esblink.common.base.BaseEntity;
import org.esblink.common.base.domain.IRole;

/**
 * 角色实体
 * 
 * 
 */
/**
 * esblink
 */
@SuppressWarnings("serial")
@Entity
public class Role extends BaseEntity implements IRole {
	public Role(){
		
	}
	 @Transient
	   private Collection<Long> moduleIds = new HashSet();
	 
	   public final void addModule(Long moduleId) {
	     if (null != moduleId)
	       this.moduleIds.add(moduleId);
	   }
	 
	   public final Collection<Long> getModuleIds() {
	     return this.moduleIds;
	   }
	 
	   public final boolean constainsModule(Long moduleId) {
	     if (null == moduleId) {
	       return true;
	     }
	     return this.moduleIds.contains(moduleId);
	   }
	 
	   public final void setModuleIds(Collection<Long> modules) {
	     for (Long moduleId : modules)
	       this.moduleIds.add(moduleId);
	   }
	 
	   public final void addModuleId(Long moduleId)
	   {
	     this.moduleIds.add(moduleId);
	   }
	private static final long serialVersionUID = -4146005497987122891L;
	@Basic
	private String name;
	@Basic
	private String description;
	@Basic
	@Column(name="create_emp_code")
	private String createEmpCode;
	@Basic
	@Column(name="update_emp_code")
	private String updateEmpCode;
	@Basic
	@Column(name="create_tm")
	private Date createTm;
	@Basic
	@Column(name="update_tm")
	private Date updateTm;
	//赋予角色的功能权限
	@Transient
	private Set<Module> roleModules;
	@Transient
	private String uncheckIds;

	public String getCreateEmpCode() {
		return createEmpCode;
	}

	public void setCreateEmpCode(String createEmpCode) {
		this.createEmpCode = createEmpCode;
	}

	public String getUpdateEmpCode() {
		return updateEmpCode;
	}

	public void setUpdateEmpCode(String updateEmpCode) {
		this.updateEmpCode = updateEmpCode;
	}

	public Date getCreateTm() {
		return createTm;
	}

	public void setCreateTm(Date createTm) {
		this.createTm = createTm;
	}

	public Date getUpdateTm() {
		return updateTm;
	}

	public void setUpdateTm(Date updateTm) {
		this.updateTm = updateTm;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e.toString());
		}
	}

	public Set<Module> getRoleModules() {
		return roleModules;
	}

	public void setRoleModules(Set<Module> roleModules) {
		this.roleModules = roleModules;
	}
	public String getUncheckIds() {
		return uncheckIds;
	}

	public void setUncheckIds(String uncheckIds) {
		this.uncheckIds = uncheckIds;
	}
	
}
