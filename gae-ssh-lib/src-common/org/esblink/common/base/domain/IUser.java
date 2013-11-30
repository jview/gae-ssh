package org.esblink.common.base.domain;

import java.util.List;

import org.esblink.common.base.IEntity;

public abstract interface IUser extends IEntity
{
  public abstract String getUsername();

  public abstract String getPassword();

  public abstract String getDeptCode();

  public abstract UserStatus getStatus();

  public abstract Long getRoleId();

  public abstract List<String> getAuthDepartmentCodes();

  public abstract String getEmpCode();

  public abstract String getEmpName();

  public abstract String getEmpEmail();

  public abstract IEmployee getEmployee();
}


 
 
