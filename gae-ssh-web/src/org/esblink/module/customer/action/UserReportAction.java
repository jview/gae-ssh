package org.esblink.module.customer.action;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.esblink.common.base.IPage;
import org.esblink.common.base.QueryObj;
import org.esblink.common.base.action.BaseGridAction;
import org.esblink.module.customer.biz.IUserReportBiz;
import org.esblink.module.customer.domain.UserReport;

/**
 * <pre>
 * *********************************************
 * Copyright esblink.net.
 * All rights reserved.
 * Description: UserReport Action
 * HISTORY
 * *********************************************
 *  ID   DATE           PERSON          REASON
 *  1    2013.04.29     CodeGen         Create
 * 
 * *********************************************
 * </pre>
 */
@SuppressWarnings("serial")
public class UserReportAction extends BaseGridAction<UserReport> {

	private IUserReportBiz userReportBiz;
	private UserReport userReport;
	private String ids;
	private String msg;
	private Collection<UserReport> userReports;
	private int total;
	private InputStream stream;

	public String saveUserReport() {
		try {
			if (userReport.getCreateUser()==null && super.getCurrentUser()!=null) {
				userReport.setCreateUser(super.getCurrentUser().getId());
			}
			if (super.getCurrentUser()!=null) {
				userReport.setModifyUser(super.getCurrentUser().getId());
			}
			userReportBiz.saveUserReport(userReport);
		} catch (Exception e) {
			msg = e.toString();
			log.error("error", e);
		}
		return SUCCESS;
	}
	
	/*
	 * 
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
			e.printStackTrace();
		}
	}
	
	public String saveUserReportJsp(){
		String remark=ServletActionContext.getRequest().getParameter("remark");
		String reportType=ServletActionContext.getRequest().getParameter("reportType");
		String userName=ServletActionContext.getRequest().getParameter("userName");
		String businessName=ServletActionContext.getRequest().getParameter("businessName");
		String comment=ServletActionContext.getRequest().getParameter("comment");
		String action=ServletActionContext.getRequest().getParameter("action");
		String contactInfo=ServletActionContext.getRequest().getParameter("contactInfo");
		String sysType=ServletActionContext.getRequest().getParameter("interestType");
		
//		UserReport userReport = new UserReport();
		if(this.userReport==null){
			this.userReport=new UserReport();
		}
		userReport.setReportType(reportType);
		userReport.setUserName(userName);
		userReport.setBusinessName(businessName);
		userReport.setComment(comment);
		userReport.setAction(action);
		userReport.setSysType(sysType);
		userReport.setContactInfo(contactInfo);
		userReport.setRemark(remark);
		try {
			this.saveUserReport();
			outJson("Thanks for interest EsbLink, We have receive your request."
					+ "<br/><a href='http://www.esblink.net'>Home</a>&nbsp;&nbsp;<a href='http://www.esblink.net/userReport.html'>Back</a>");
		} catch (Exception e) {
			msg = e.toString();
			log.error("error", e);
		}
		return SUCCESS;
	}

	public String findUserReport() {
		userReport = userReportBiz.findUserReport(userReport.getId());
		return SUCCESS;
	}

	public String findByUserReport() {
		userReports = userReportBiz.findBy(userReport);
		return SUCCESS;
	}

	public String deleteUserReports() {
		try {
			userReportBiz.deleteUserReports(ids);
		} catch (Exception e) {
			msg = e.toString();
			log.error("error", e);
		}
		return SUCCESS;
	}

	public String findPageByUserReport() {
		QueryObj queryObj = QueryObj.parseQueryObj(this);
		queryObj.setQueryObject("ifDel", 0l);
		IPage<UserReport> page = userReportBiz.findPageBy(queryObj);
		userReports = page.getData();
		total = (int) page.getTotalSize();
		return SUCCESS;
	}

	public String exportUserReport() {
		QueryObj queryObj = QueryObj.parseQueryObj(this);
		stream = userReportBiz.exportUserReport(queryObj);
		return SUCCESS;
	}

	public boolean isSuccess() {
		return true;
	}

	public void setUserReportBiz(IUserReportBiz userReportBiz) {
		this.userReportBiz = userReportBiz;
	}

	public UserReport getUserReport() {
		return this.userReport;
	}

	public void setUserReport(UserReport userReport) {
		this.userReport = userReport;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getMsg() {
		return this.msg;
	}

	public Collection<UserReport> getUserReports() {
		return this.userReports;
	}
	
	public Collection<UserReport> getRows() {
		return this.userReports;
	}
	public void setRows(int rows){
		this.total=rows;
	}
	public int getTotal() {
		return this.total;
	}

	public InputStream getStream() {
		return this.stream;
	}
}
