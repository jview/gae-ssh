package org.esblink.common.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;

/**
 * 创建人修改人处理，每个表如果有创建人，修改人，统一由这里定义，实体继承此类
 * @author chenjh
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) 
public class BaseDomain extends BaseEntity {
	private static final long serialVersionUID = 1L;
	@Transient
	private String create_disp;//显示创建人信息
	@Transient
	private String modify_disp;//显示修改人信息
	
	@Basic
	@Column(name="create_user")
	private Long createUser;//创建人
	@Basic
	@Column(name="create_date")
	private Date createDate;//创建时间
	@Basic
	@Column(name="modify_user")
	private Long modifyUser;//修改人
	@Basic
	@Column(name="modify_date")
	private Date modifyDate;//修改时间
	
	
	
	public String getModify_disp() {
		return modify_disp;
	}
	public void setModify_disp(String modify_disp) {
		this.modify_disp = modify_disp;
	}
	public String getCreate_disp() {
		return create_disp;
	}
	public void setCreate_disp(String create_disp) {
		this.create_disp = create_disp;
	}
	
	/**
	 * 创建人
	 * @return
	 */
	public Long getCreateUser() {
		return createUser;
	}
	public void setCreateUser(Long createUser) {
		this.createUser = createUser;
	}
	/**
	 * 创建时间
	 * @return
	 */
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * 修改人
	 * @return
	 */
	public Long getModifyUser() {
		return modifyUser;
	}
	public void setModifyUser(Long modifyUser) {
		this.modifyUser = modifyUser;
	}
	/**
	 * 修改时间
	 * @return
	 */
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	/**
	 * 取得创建人，修改人id
	 * @param domainList
	 * @return
	 */
	public static List<Long> getOperIds(Collection<BaseDomain> domainList){		
		List<Long> idList = new ArrayList<Long>();
		for(BaseDomain domain:domainList){
			if(domain.getCreateUser()!=null){
				idList.add(domain.getCreateUser());	
			}			
			if(domain.getModifyUser()!=null){
				idList.add(domain.getModifyUser());
			}			
		}
		return idList;
	}

}
