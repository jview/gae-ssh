package org.esblink.module.auth.action.dto;

import java.util.ArrayList;
import java.util.List;

public class ModuleDto {

	private long id;
	private Long parentId;
	private String text;
	private boolean checked = false;
	private boolean leaf = false;
	private Long type;
	private List<ModuleDto> children = new ArrayList<ModuleDto>();

	public void addChild(ModuleDto module) {
		children.add(module);
	}

	public List<ModuleDto> getChildren() {
		return children;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
