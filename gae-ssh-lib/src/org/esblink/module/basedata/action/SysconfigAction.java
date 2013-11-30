package org.esblink.module.basedata.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.helpers.Loader;
import org.apache.struts2.ServletActionContext;
import org.esblink.common.base.BaseDomain;
import org.esblink.common.base.IPage;
import org.esblink.common.base.QueryObj;
import org.esblink.common.base.action.BaseGridAction;
import org.esblink.common.base.domain.UserStatus;
import org.esblink.module.auth.biz.IUserBiz;
import org.esblink.module.auth.domain.User;
import org.esblink.module.auth.util.ConfigUtil;
import org.esblink.module.basedata.biz.ILogDbBiz;
import org.esblink.module.basedata.biz.ISysconfigBiz;
import org.esblink.module.basedata.domain.LogDb;
import org.esblink.module.basedata.domain.Sysconfig;
import org.esblink.module.basedata.util.Sysconfigs;
import org.springframework.stereotype.Controller;

import com.esblink.dev.util.CommMethod;


/**
 * <pre>
 * *********************************************
 * Copyright esblink.net.
 * All rights reserved.
 * Description: Sysconfig Action
 * HISTORY
 * *********************************************
 *  ID   DATE           PERSON          REASON
 *  1    2012.04.18     CodeGen         Create
 * 
 * *********************************************
 * </pre>
 */
@SuppressWarnings("serial")
@Controller("sysconfigAction")
public class SysconfigAction extends BaseGridAction<Sysconfig> {
	private String moduleCode="sysconfig";
	private String modelCode = getClass().getSimpleName();
	@Resource(name="logDbBiz")
	private ILogDbBiz logDbBiz;
	private LogDb logDb=new LogDb(moduleCode, modelCode);;
	public void setLogDbBiz(ILogDbBiz logDbBiz) {
		this.logDbBiz = logDbBiz;
	}
	@Resource(name="userBiz")
	private IUserBiz userBiz;
	@Resource(name="sysconfigBiz")
	private ISysconfigBiz sysconfigBiz;
	private Sysconfig sysconfig;
	private String ids;
	private String msg;
	
	private Collection<Sysconfig> sysconfigs;
	private int total;

	public String saveSysconfig() {
		try {
			if(sysconfig.getCreateUser()==null){
				sysconfig.setCreateUser(super.getCurrentUser().getId());
			}
			sysconfig.setModifyUser(super.getCurrentUser().getId());
			sysconfigBiz.saveSysconfig(sysconfig);
			//保存附加字段信息
//			TableColumnDatas.saveTableOptData(ServletActionContext.getRequest().getParameterMap(), null, sysconfig.getId(), null);
			this.logDbBiz.info(this.getCurrentUser(), logDb, "saveSysconfig", "id="+sysconfig.getId()+" code="+sysconfig.getCode(),"",SUCCESS,sysconfig);
		} catch (Exception e) {
			msg = e.toString();
			log.error("error", e);
			this.logDbBiz.error(this.getCurrentUser(), logDb, "saveSysconfig", "id="+sysconfig.getId()+" code="+sysconfig.getCode(),FAILURE+":"+e.getMessage(),FAILURE, sysconfig);
		}
		return SUCCESS;
	}
	
	public String changeConfig(){
		String password=ServletActionContext.getRequest().getParameter("password");
		String runTypeSelect=ServletActionContext.getRequest().getParameter("runTypeSelect");
		String refreshLog4j=ServletActionContext.getRequest().getParameter("refreshLog4j");
		boolean isTest=CommMethod.getBoolean(ConfigUtil.getConfigValue(ConfigUtil.config_key.SYS_IF_TEST));
		if(this.getCurrentUser().getStatus()!=UserStatus.ROOT){
			msg="权限不足";
			this.logDbBiz.error(this.getCurrentUser(), logDb, "changeConfig", "isTest="+isTest+" newIsTest="+runTypeSelect+" refreshLog4j="+refreshLog4j,FAILURE+":Permission prohibited!",FAILURE,null);
			return SUCCESS;
		}		
		if(!CommMethod.isEmpty(password)&&password.equals(ConfigUtil.getConfigValue(ConfigUtil.config_key.SYS_PASSWORD))){			
			isTest=Boolean.parseBoolean(runTypeSelect);
			ConfigUtil.setConfigValue(ConfigUtil.config_key.SYS_IF_TEST, ""+isTest);
			boolean refreshLog = Boolean.parseBoolean(refreshLog4j);
			if(refreshLog){
				PropertyConfigurator.configure(Loader.getResource("log4j.properties"));
//				BasicConfigurator.resetConfiguration();
			}			
//			System.out.println("-----"+runTypeSelect+"---"+ConfigUtil.isTest);
			this.logDbBiz.info(this.getCurrentUser(), logDb, "changeConfig", "isTest="+isTest+" newIsTest="+runTypeSelect+" refreshLog4j="+refreshLog4j,"",SUCCESS,null);
			msg="ok";
		}
		else{
			msg="密码错误!";
			this.logDbBiz.error(this.getCurrentUser(), logDb, "changeConfig", "isTest="+isTest+" newIsTest="+runTypeSelect+" refreshLog4j="+refreshLog4j,FAILURE+":Invalid password!",FAILURE,null);
		}		
		return SUCCESS;
	}

	public String findSysconfig() {
		sysconfig = sysconfigBiz.findSysconfig(sysconfig.getId());
		return SUCCESS;
	}

	public String findBySysconfig() {
		sysconfigs = sysconfigBiz.findBy(sysconfig);
		return SUCCESS;
	}

	public String deleteSysconfigs() {
		try {
			sysconfigBiz.deleteSysconfigs(ids);
			//跟着删除附加字段信息
//			TableColumnDatas.deleteTableOptData(ServletActionContext.getRequest().getParameterMap(), null, ids, null);
			this.logDbBiz.info(this.getCurrentUser(), logDb, "deleteSysconfigs", "ids="+ids,"delete id for:"+ids, SUCCESS, null);
		} catch (Exception e) {
			msg = e.toString();
			log.error("error", e);
			this.logDbBiz.error(this.getCurrentUser(), logDb, "deleteSysconfigs", "ids="+ids,FAILURE+":"+e.getMessage(),FAILURE,null);
		}
		return SUCCESS;
	}
	
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
			PrintWriter out = null;			
			out = response.getWriter();			
			out.write(xmlStr);
			out.flush();
			out.close();
		} catch (IOException e) {
//			e.printStackTrace();
		}
	}
	
	/**
	 * 系统参数值
	 * @return
	 */
	public String findSysconfigValue(){		
		String sysconfigCode = ServletActionContext.getRequest().getParameter("sysconfigCode");
		log.info("======sysconfigCode=="+sysconfigCode);
		String value = null;
		if(sysconfigCode.equals("isTest")){
			value = "{sysconfigValue:'"+CommMethod.getBoolean(ConfigUtil.getConfigValue(ConfigUtil.config_key.SYS_IF_TEST))+"'}";
		}
		else{
			value = "{sysconfigValue:'"+Sysconfigs.getValue(sysconfigCode)+"'}";
		}
		outJson(value); 
		return null;
	}

	public String findPageBySysconfig() {
		QueryObj queryObj = QueryObj.parseQueryObj(this);
		queryObj.setQueryObject("ifDel", 0l);
		
		IPage<Sysconfig> page = sysconfigBiz.findPageBy(queryObj);
		sysconfigs = page.getData();
		
		//begin修改人，创建人转换
		try{
			Collection<BaseDomain> domainList = new ArrayList<BaseDomain>();			
			for(Sysconfig dist:sysconfigs){				
				domainList.add(dist);
			}
	
	//		如果数据库创建人修改人用的是id的话
			List<Long> idList = BaseDomain.getOperIds(domainList);
			log.info(idList.toString());
			idList = CommMethod.sortLong(idList);
			idList = CommMethod.distLong(idList);
			List<User> userList = this.userBiz.findUserByIds(idList, true);
	
			
			int i=1;
			
			for(Sysconfig dist:sysconfigs){
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
			log.error("---dataError dispShow--modifyUser", e);
		}
		//end修改人，创建人转换
				
		total = (int) page.getTotalSize();
		return SUCCESS;
	}

	public boolean isSuccess() {
		return true;
	}

	public void setSysconfigBiz(ISysconfigBiz sysconfigBiz) {
		this.sysconfigBiz = sysconfigBiz;
	}

	public Sysconfig getSysconfig() {
		return this.sysconfig;
	}

	public void setSysconfig(Sysconfig sysconfig) {
		this.sysconfig = sysconfig;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getMsg() {
		return this.msg;
	}

	public Collection<Sysconfig> getSysconfigs() {
		return this.sysconfigs;
	}
	
	public Collection<Sysconfig> getRows() {
		return this.sysconfigs;
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
