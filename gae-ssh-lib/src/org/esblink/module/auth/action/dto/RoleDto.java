package org.esblink.module.auth.action.dto;

import java.util.Date;

public class RoleDto {

	private Long id;
	private String name;
	private String description;
	private Date unusedTm;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Date getUnusedTm() {
		return unusedTm;
	}

	public void setUnusedTm(Date unusedTm) {
		this.unusedTm = unusedTm;
	}
}
