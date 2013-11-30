package org.esblink.module.basedata.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.esblink.common.base.IPage;
import org.esblink.common.base.QueryObj;
import org.esblink.common.base.action.BaseGridAction;
import org.esblink.module.basedata.biz.ILogDbBiz;
import org.esblink.module.basedata.biz.IParameterUserBiz;
import org.esblink.module.basedata.domain.ParameterUser;
import org.esblink.module.basedata.util.ParameterUsers;
import org.springframework.stereotype.Controller;

import com.esblink.dev.util.CommMethod;

/**
 * <pre>
 * *********************************************
 * Copyright esblink.net.
 * All rights reserved.
 * Description: ParameterUser Action
 * HISTORY
 * *********************************************
 *  ID   DATE           PERSON          REASON
 *  1    2012.04.18     CodeGen         Create
 * 
 * *********************************************
 * </pre>
 */
@SuppressWarnings("serial")
@Controller("parameterUserAction")
public class ParameterUserAction extends BaseGridAction<ParameterUser> {
	private String moduleCode="parameterUser";
	private String modelCode = getClass().getSimpleName();
	@Resource(name="logDbBiz")
	private ILogDbBiz logDbBiz;
	public void setLogDbBiz(ILogDbBiz logDbBiz) {
		this.logDbBiz = logDbBiz;
	}
	@Resource(name="parameterUserBiz")
	private IParameterUserBiz parameterUserBiz;
	private ParameterUser parameterUser;
	private String ids;
	private String msg;
	private Collection<ParameterUser> parameterUsers;
	private int total;
	protected String JSON="json";
	
	/**
	 * 构建自定义String，通过HttpServletResponse发送给浏览器
	 * 可对数据进行压缩
	 * 可由转向后的页面进行输出
	 * @param json  
	 * @author chenjh
	 * @version 1.0 
	 */
	protected  void outJson(String xmlStr) {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		try {
			PrintWriter out = response.getWriter();
			out.write(xmlStr);
			out.flush();
			out.close();
		} catch (IOException e) {
//			e.printStackTrace();
		}
	}

	public String saveParameterUser() {
		try {
			if(parameterUser.getUserId()==null){
				parameterUser.setUserId(super.getCurrentUser().getId());
			}
			parameterUserBiz.saveParameterUser(parameterUser);
			this.logDbBiz.info(this.getCurrentUser(), this.moduleCode, modelCode, "save"+modelCode, parameterUser.getParameterId()+" value="+parameterUser.getValue(),SUCCESS,SUCCESS, parameterUser);
//			this.msg="ok";
		} catch (Exception e) {
			msg = e.toString();
			log.error("error", e);
			this.logDbBiz.error(this.getCurrentUser(), this.moduleCode, modelCode, "save"+modelCode, parameterUser.getParameterId()+" value="+parameterUser.getValue(),FAILURE+":"+e.getMessage(),FAILURE, parameterUser);
		}
		return SUCCESS;
	}	

	public String findParameterUser() {
		parameterUser = parameterUserBiz.findParameterUser(parameterUser.getId());
		return SUCCESS;
	}

	public String findByParameterUser() {
		parameterUsers = parameterUserBiz.findBy(parameterUser);
		return SUCCESS;
	}

	public String deleteParameterUsers() {
		try {
			msg="禁止删除";
//			parameterUserBiz.deleteParameterUsers(ids);
		} catch (Exception e) {
			msg = e.toString();
			log.error("error", e);
		}
		return SUCCESS;
	}

	public String findPageByParameterUser() {
		QueryObj queryObj = QueryObj.parseQueryObj(this);
		queryObj.setQueryObject("userId", this.getCurrentUser().getId());
		IPage<ParameterUser> page = parameterUserBiz.findPageBy(queryObj);
		parameterUsers = page.getData();
		total = (int) page.getTotalSize();
		return SUCCESS;
	}
	
	/**
	 * 返回用户参数所设的值
	 * @return
	 */
	public String findUserParameterByCode(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String parameterCode=request.getParameter("parameterCode");
		log.info("========findUserParameterByCode--userId="+this.getCurrentUser().getId()+" parameterCode="+parameterCode);
		//String value = "{userId:"+this.getCurrentUser().getId()+", userName:'"+this.getCurrentUser().getUsername()+"',userNo:'"+this.getCurrentUser().getUserNo()+"'}";
		String value = null;
		String defaultValue=null;
		if(!CommMethod.isEmpty(parameterCode)){
//			ParameterUser parameterUser=parameterUserBiz.findParameterUser(parameterCode, this.getCurrentUser().getId());
//			log.info("------parameterUser="+parameterUser);		
//			if(parameterUser!=null){
//				value = parameterUser.getValue();
//				defaultValue = parameterUser.getDefaultValue();
//			}
			ParameterUser paraUser = ParameterUsers.getParameterUser(this.getCurrentUser().getId(), parameterCode);
			value = paraUser.getValue();
			defaultValue=paraUser.getDefaultValue();
		}
		String valueInfo = "{code:'"+parameterCode+"', value:'"+value+"', defaultValue:'"+defaultValue+"'}";
		outJson(valueInfo); 
		return JSON;
	}
	
	/**
	 * 保存用户参数值
	 * @return
	 */
	public String saveUserParameterByCode() {
		HttpServletRequest request=ServletActionContext.getRequest();
		String parameterCode=request.getParameter("parameterCode");
		String parameterValue = request.getParameter("parameterValue");
		log.info("========saveUserParameterByCode--userId="+this.getCurrentUser().getId()+" parameterCode="+parameterCode+" parameterValue="+parameterValue);
		boolean success=false;
		if(!CommMethod.isEmpty(parameterCode) && !CommMethod.isEmpty(parameterValue)){
			try {			
				parameterUserBiz.saveParameterUserValue(parameterCode, this.getCurrentUser().getId(), parameterValue);
				this.logDbBiz.info(this.getCurrentUser(), this.moduleCode, modelCode, "saveUserParameterByCode", parameterCode+" value="+parameterValue,SUCCESS,SUCCESS, null);
				this.msg="ok";
				success=true;
			} catch (Exception e) {
				msg = e.toString();
				log.error("error", e);
				this.logDbBiz.error(this.getCurrentUser(), this.moduleCode, modelCode, "saveUserParameterByCode", parameterCode+" value="+parameterValue,FAILURE+":"+e.getMessage(),FAILURE, null);
			}
		}
		else{
			this.msg="参数不能为空!";
		}
		String value =  "{success:"+success+", code:'"+parameterCode+"', status:'"+msg+"'}";
		outJson(value);
		return this.JSON;
	}

	public boolean isSuccess() {
		return true;
	}

	public void setParameterUserBiz(IParameterUserBiz parameterUserBiz) {
		this.parameterUserBiz = parameterUserBiz;
	}

	public ParameterUser getParameterUser() {
		return this.parameterUser;
	}

	public void setParameterUser(ParameterUser parameterUser) {
		this.parameterUser = parameterUser;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getMsg() {
		return this.msg;
	}

	public Collection<ParameterUser> getParameterUsers() {
		return this.parameterUsers;
	}

	public int getTotal() {
		return this.total;
	}
}
