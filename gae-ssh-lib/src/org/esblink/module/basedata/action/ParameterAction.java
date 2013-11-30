package org.esblink.module.basedata.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.esblink.common.base.BaseDomain;
import org.esblink.common.base.IPage;
import org.esblink.common.base.QueryObj;
import org.esblink.common.base.action.BaseGridAction;
import org.esblink.module.auth.biz.IUserBiz;
import org.esblink.module.auth.domain.User;
import org.esblink.module.basedata.biz.ILogDbBiz;
import org.esblink.module.basedata.biz.IParameterBiz;
import org.esblink.module.basedata.domain.Parameter;
import org.springframework.stereotype.Controller;

import com.esblink.dev.util.CommMethod;

/**
 * <pre>
 * *********************************************
 * Copyright esblink.net.
 * All rights reserved.
 * Description: Parameter Action
 * HISTORY
 * *********************************************
 *  ID   DATE           PERSON          REASON
 *  1    2012.04.18     CodeGen         Create
 * 
 * *********************************************
 * </pre>
 */
@SuppressWarnings("serial")
@Controller("parameterAction")
public class ParameterAction extends BaseGridAction<Parameter> {
	private String moduleCode="parameter";
	private String modelCode = getClass().getSimpleName();
	@Resource(name="logDbBiz")
	private ILogDbBiz logDbBiz;
	public void setLogDbBiz(ILogDbBiz logDbBiz) {
		this.logDbBiz = logDbBiz;
	}
	@Resource(name="userBiz")
	private IUserBiz userBiz;
	@Resource(name="parameterBiz")
	private IParameterBiz parameterBiz;
	private Parameter parameter;
	private String ids;
	private String msg;
	private Collection<Parameter> parameters;
	private int total;

	public String saveParameter() {
		try {
			parameterBiz.saveParameter(parameter);
//			this.msg="ok";
			this.logDbBiz.info(this.getCurrentUser(), this.moduleCode, modelCode, "save"+modelCode, "id="+parameter.getId()+" code="+parameter.getCode(),"", SUCCESS, parameter);
		} catch (Exception e) {
			msg = e.toString();
			log.error("error", e);
			this.logDbBiz.error(this.getCurrentUser(), this.moduleCode, modelCode, "save"+modelCode, "id="+parameter.getId()+" code="+parameter.getCode(),FAILURE+":"+e.getMessage(), FAILURE, parameter);
		}
		return SUCCESS;
	}

	public String findParameter() {
		parameter = parameterBiz.findParameter(parameter.getId());
		return SUCCESS;
	}

	public String findByParameter() {
		parameters = parameterBiz.findBy(parameter);
		return SUCCESS;
	}

	public String deleteParameters() {
		try {
			parameterBiz.deleteParameters(ids);
			this.msg="ok";
			this.logDbBiz.info(this.getCurrentUser(), this.moduleCode, modelCode, "delete"+modelCode+"s", "ids="+ids, "delete ids for:"+ids, SUCCESS, null);
		} catch (Exception e) {
			msg = e.toString();
			log.error("error", e);
			this.logDbBiz.error(this.getCurrentUser(), this.moduleCode, modelCode, "delete"+modelCode+"s", "ids="+ids,FAILURE+":"+e.getMessage(), FAILURE, null);
		}
		return SUCCESS;
	}

	public String findPageByParameter() {
		QueryObj queryObj = QueryObj.parseQueryObj(this);
		queryObj.setQueryObject("ifDel", 0l);
		
		IPage<Parameter> page = parameterBiz.findPageBy(queryObj);
		parameters = page.getData();
		
		//begin修改人，创建人转换
		try{
			Collection<BaseDomain> domainList = new ArrayList<BaseDomain>();			
			for(Parameter dist:parameters){				
				domainList.add(dist);
			}
	
	//		如果数据库创建人修改人用的是id的话
			List<Long> idList = BaseDomain.getOperIds(domainList);
			log.info(idList.toString());
			idList = CommMethod.sortLong(idList);
			idList = CommMethod.distLong(idList);
			List<User> userList = this.userBiz.findUserByIds(idList, true);
	
			
			int i=1;
			
			for(Parameter dist:parameters){
				for(User user:userList){
					if(null!=dist.getCreateUser() && dist.getCreateUser().longValue()==user.getId().longValue()){
						dist.setCreate_disp(user.getUsername());
					}
					if(null!=dist.getModifyUser() && dist.getModifyUser().longValue()==user.getId().longValue()){
						dist.setModify_disp(user.getUsername());
					}
				}
				i++;
			}
		}catch(Exception e){
			log.error("---dataError dispShow--createUser/modifyUser", e);
		}
		//end修改人，创建人转换
		
		total = (int) page.getTotalSize();
		return SUCCESS;
	}

	public boolean isSuccess() {
		return true;
	}

	public void setParameterBiz(IParameterBiz parameterBiz) {
		this.parameterBiz = parameterBiz;
	}

	public Parameter getParameter() {
		return this.parameter;
	}

	public void setParameter(Parameter parameter) {
		this.parameter = parameter;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getMsg() {
		return this.msg;
	}

	public Collection<Parameter> getParameters() {
		return this.parameters;
	}
	
	public Collection<Parameter> getRows() {
		return this.parameters;
	}
	
	public void setRows(int rows){
		this.total=rows;
	}

	public int getTotal() {
		return this.total;
	}

	public void setUserBiz(IUserBiz userBiz) {
		this.userBiz = userBiz;
	}
	
	
}
