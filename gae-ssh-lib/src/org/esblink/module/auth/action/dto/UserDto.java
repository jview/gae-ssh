package org.esblink.module.auth.action.dto;

import org.esblink.module.auth.domain.User;

public class UserDto {
	public UserDto(){
		
	}
	public UserDto(User user){
		this.id=user.getId();
		this.username=user.getUsername();
		this.empCode=user.getEmpCode();
		this.empName=user.getEmpName();
	}

	private long id;
	private String username;
	private String empCode;
	private String empName;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

}
