package org.esblink.module.basedata.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

import org.esblink.common.base.BaseDomain;

/**
 * esblink
 */
@SuppressWarnings("serial")
@Entity
public class CodeTable extends BaseDomain {
	public CodeTable(){
		
	}
	//类的属性名
	public final static String objFields="id,codeType,dataId,ename,cname,shortCode,display,showValues,showValuesEn,showColor,matchValues,status,ifDel,remark,canModify";
		
	public CodeTable(Object[] objs, boolean status){
		try{
			int i=0;		
			this.setId(Long.parseLong(""+objs[i]));i++;
			this.codeType=""+objs[i];i++;
			this.dataId=Long.parseLong(""+objs[i]);i++;
			this.ename=""+objs[i];i++;
			this.cname=""+objs[i];i++;
			this.shortCode=""+objs[i];i++;
			this.display=Long.parseLong(""+objs[i]);i++;
			this.showValues=""+objs[i];i++;
			this.showValuesEn=""+objs[i];i++;
			this.showColor=""+objs[i];i++;
			this.matchValues=""+objs[i];i++;
			this.status=Long.parseLong(""+objs[i]);i++;
			this.ifDel=Long.parseLong(""+objs[i]);i++;
			this.remark=""+objs[i];i++;
			this.canModify=Long.parseLong(""+objs[i]);i++;
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public Object[] toObjs(){
		int size = objFields.split(",").length;
		Object[] temp = new Object[size];
		int i=0;temp[i]=this.getId();
		i++;temp[i]=this.codeType;
		i++;temp[i]=this.dataId;
		i++;temp[i]=this.ename+"     ";
		i++;temp[i]=this.cname+"     ";
		i++;temp[i]=this.shortCode;
		i++;temp[i]=this.display;
		i++;temp[i]=this.showValues+"     ";
		i++;temp[i]=this.showValuesEn+"     ";
		i++;temp[i]=this.showColor+"     ";
		i++;temp[i]=this.matchValues;
		i++;temp[i]=this.status;
		i++;temp[i]=this.ifDel;
		i++;temp[i]=this.remark+"     ";
		i++;temp[i]=this.canModify;
		i++;temp[i]=this.getCreateUser();
		i++;temp[i]=this.getModifyUser();
		i++;temp[i]=this.getCreate_disp();
		i++;temp[i]=this.getModify_disp();
		return temp;
	}

	// 代码类别标识
	@Basic
	@Column(name="code_type")
	private String codeType;
	// 英文名
	@Basic
	private String ename;
	// 中文名
	@Basic
	private String cname;
	// 简码
	@Basic
	@Column(name="short_code")
	private String shortCode;
	// 编码
	@Basic
	@Column(name="data_id")
	private Long dataId;
	// 显示顺序
	@Basic
	private Long display;
	// 显示内容
	@Basic
	@Column(name="show_values")
	private String showValues;
	// 显示内容
	@Basic
	@Column(name="show_value_en")
	private String showValuesEn;
	//显示颜色
	@Basic
	@Column(name="show_color")
	private String showColor;
	// 对应值
	@Basic
	@Column(name="match_values")
	private String matchValues;
	//关联值
	@Basic
	@Column(name="refer_values")
	private String referValues;
	// 是否可修改
	@Basic
	@Column(name="can_modify")
	private Long canModify;
	// 状态
	@Basic
	private Long status;
	// 是否删除
	@Basic
	@Column(name="if_del")
	private Long ifDel;
	// 备注
	@Basic
	private String remark;
	
	//---------------------
	@Transient
	private int treeId;
    //子节点ID
	@Transient
	private String treeTpye;
	//是否下面还有子节点
	@Transient
	private Boolean leaf;
	//子节点名字
	@Transient
	private String childName;

	public String getCodeType() {
		return this.codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	public String getEname() {
		return this.ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public String getCname() {
		return this.cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getShortCode() {
		return this.shortCode;
	}

	public void setShortCode(String shortCode) {
		this.shortCode = shortCode;
	}

	public Long getDataId() {
		return this.dataId;
	}

	public void setDataId(Long dataId) {
		this.dataId = dataId;
	}

	public Long getDisplay() {
		return this.display;
	}

	public void setDisplay(Long display) {
		this.display = display;
	}

	public String getShowValues() {
		return this.showValues;
	}

	public void setShowValues(String showValues) {
		this.showValues = showValues;
	}
	
	

	public String getShowValuesEn() {
		return showValuesEn;
	}

	public void setShowValuesEn(String showValuesEn) {
		this.showValuesEn = showValuesEn;
	}

	public String getShowColor() {
		return showColor;
	}

	public void setShowColor(String showColor) {
		this.showColor = showColor;
	}

	public String getMatchValues() {
		return this.matchValues;
	}

	public void setMatchValues(String matchValues) {
		this.matchValues = matchValues;
	}
	
	
	public String getReferValues() {
		return referValues;
	}

	public void setReferValues(String referValues) {
		this.referValues = referValues;
	}

	public Long getCanModify() {
		return this.canModify;
	}

	public void setCanModify(Long canModify) {
		this.canModify = canModify;
	}

	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Long getIfDel() {
		return this.ifDel;
	}

	public void setIfDel(Long ifDel) {
		this.ifDel = ifDel;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getTreeId() {
		return treeId;
	}

	public void setTreeId(int treeId) {
		this.treeId = treeId;
	}

	public String getTreeTpye() {
		return treeTpye;
	}

	public void setTreeTpye(String treeTpye) {
		this.treeTpye = treeTpye;
	}

	public Boolean getLeaf() {
		return leaf;
	}

	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}

	public String getChildName() {
		return childName;
	}

	public void setChildName(String childName) {
		this.childName = childName;
	}

	
}
