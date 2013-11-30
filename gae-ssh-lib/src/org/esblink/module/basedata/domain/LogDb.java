package org.esblink.module.basedata.domain;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;

import org.esblink.common.base.BaseDomain;

import com.google.appengine.api.datastore.Text;

/**
 * esblink
 */
@SuppressWarnings("serial")
@Entity
public class LogDb extends BaseDomain {
	public LogDb(){
		
	}
	public LogDb(String moduleCode, String modelCode){
		this.moduleCode=moduleCode;
		this.modelCode=modelCode;
	}

	// 操作人id
	@Basic
	@Column(name="user_id")
	private Long userId;
	// 操作人编码
	@Basic
	@Column(name="user_code")
	private String userCode;
	// 操作人姓名
	@Basic
	@Column(name="user_name")
	private String userName;
	// 模块编码(主要是表名或特定业务编码，用于在不同记录可以反映相关性)
	@Column(name="model_code")
	private String modelCode;
	// 标题或模块名
	@Basic
	private String title;
	// 操作级别
	@Basic
	private Long levels;
	// 执行动作
	@Basic
	private String actions;
	// 动简介
	@Basic
	private String description;
	//结果信息
	@Basic
	@Column(name="ret_value")
	private String retValue;
	// 数据结果记录
	@Basic
	@Column(name="json_value")
	private Text jsonValue;
	// 操作时间
	@Basic
	@Column(name="oper_time")
	private Date operTime;
	// 备注
	@Basic
	private Text remark;
	// moduleCode
	@Basic
	@Column(name="module_code")
	private String moduleCode;

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserCode() {
		return this.userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getModelCode() {
		return this.modelCode;
	}

	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getLevels() {
		return this.levels;
	}

	public void setLevels(Long levels) {
		this.levels = levels;
	}

	public String getActions() {
		return this.actions;
	}

	public void setActions(String actions) {
		this.actions = actions;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getJsonValue() {
		return this.jsonValue.getValue();
	}

	public void setJsonValue(String jsonValue) {
		this.jsonValue = new Text(jsonValue);
	}

	public Date getOperTime() {
		return this.operTime;
	}

	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}

	public String getRemark() {
		return this.remark.getValue();
	}

	public void setRemark(String remark) {
		this.remark = new Text(remark);
	}

	public String getModuleCode() {
		return this.moduleCode;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

	public String getRetValue() {
		return retValue;
	}

	public void setRetValue(String retValue) {
		this.retValue = retValue;
	}
	
	
}
