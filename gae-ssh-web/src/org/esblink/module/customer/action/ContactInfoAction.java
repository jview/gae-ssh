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
import org.esblink.module.customer.biz.IContactInfoBiz;
import org.esblink.module.customer.domain.ContactInfo;

/**
 * <pre>
 * *********************************************
 * Copyright esblink.net.
 * All rights reserved.
 * Description: ContactInfo Action
 * HISTORY
 * *********************************************
 *  ID   DATE           PERSON          REASON
 *  1    2013.04.29     CodeGen         Create
 * 
 * *********************************************
 * </pre>
 */
@SuppressWarnings("serial")
public class ContactInfoAction extends BaseGridAction<ContactInfo> {

	private IContactInfoBiz contactInfoBiz;
	private ContactInfo contactInfo;
	private String ids;
	private String msg;
	private Collection<ContactInfo> contactInfos;
	private int total;
	private InputStream stream;

	public String saveContactInfo() {
		try {
			if (contactInfo.getCreateUser()==null && super.getCurrentUser()!=null) {
				contactInfo.setCreateUser(super.getCurrentUser().getId());
			}
			if(super.getCurrentUser()!=null){
				contactInfo.setModifyUser(super.getCurrentUser().getId());
			}
			contactInfoBiz.saveContactInfo(contactInfo);
		} catch (Exception e) {
			msg = e.toString();
			log.error("error", e);
		}
		return SUCCESS;
	}
	
	/**
	 * 构建自定义String，通过HttpServletResponse发送给浏览器
	 * 可由转向后的页面进行输出
	 * @param json  
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
			e.printStackTrace();
		}
	}
	
	public String saveContactInfoJsp(){
		String email=ServletActionContext.getRequest().getParameter("email");
		String mobile=ServletActionContext.getRequest().getParameter("cell");
		String userName=ServletActionContext.getRequest().getParameter("userName");
		String businessName=ServletActionContext.getRequest().getParameter("businessName");
		String comment=ServletActionContext.getRequest().getParameter("comment");
		String phone=ServletActionContext.getRequest().getParameter("phone");
		String country=ServletActionContext.getRequest().getParameter("country");
		String contactType=ServletActionContext.getRequest().getParameter("contactType");
		String sysType=ServletActionContext.getRequest().getParameter("interestType");
		
		if(this.contactInfo==null){
			contactInfo = new ContactInfo();
		}
		contactInfo.setEmail(email);
		contactInfo.setMobile(mobile);
		contactInfo.setUserName(userName);
		contactInfo.setBusinessName(businessName);
		contactInfo.setComment(comment);
		contactInfo.setPhone(phone);
		contactInfo.setCountry(country);
		contactInfo.setSysType(sysType);
		contactInfo.setContactType(contactType);
		try {
			this.saveContactInfo();
			outJson("Thanks for interest EsbLink, We have receive your request."
					+ "<br/><a href='http://www.esblink.net'>Home</a>&nbsp;&nbsp;<a href='http://www.esblink.net/contact.html'>Back</a>");
		} catch (Exception e) {
			msg = e.toString();
			log.error("error", e);
		}
		return SUCCESS;
	}

	public String findContactInfo() {
		contactInfo = contactInfoBiz.findContactInfo(contactInfo.getId());
		return SUCCESS;
	}

	public String findByContactInfo() {
		contactInfos = contactInfoBiz.findBy(contactInfo);
		return SUCCESS;
	}

	public String deleteContactInfos() {
		try {
			contactInfoBiz.deleteContactInfos(ids);
		} catch (Exception e) {
			msg = e.toString();
			log.error("error", e);
		}
		return SUCCESS;
	}

	public String findPageByContactInfo() {
		QueryObj queryObj = QueryObj.parseQueryObj(this);
		queryObj.setQueryObject("ifDel", 0l);
		IPage<ContactInfo> page = contactInfoBiz.findPageBy(queryObj);
		contactInfos = page.getData();
		total = (int) page.getTotalSize();
		return SUCCESS;
	}

	public String exportContactInfo() {
		QueryObj queryObj = QueryObj.parseQueryObj(this);
		stream = contactInfoBiz.exportContactInfo(queryObj);
		return SUCCESS;
	}

	public boolean isSuccess() {
		return true;
	}

	public void setContactInfoBiz(IContactInfoBiz contactInfoBiz) {
		this.contactInfoBiz = contactInfoBiz;
	}

	public ContactInfo getContactInfo() {
		return this.contactInfo;
	}

	public void setContactInfo(ContactInfo contactInfo) {
		this.contactInfo = contactInfo;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getMsg() {
		return this.msg;
	}

	public Collection<ContactInfo> getContactInfos() {
		return this.contactInfos;
	}
	
	public Collection<ContactInfo> getRows() {
		return this.contactInfos;
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
