package org.esblink.module.customer.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.esblink.module.customer.biz.IContactInfoBiz;
import org.esblink.module.customer.domain.ContactInfo;

public class ContactInfoServlet extends HttpServlet {
	private static IContactInfoBiz contactInfoBiz;

    public void doPost(HttpServletRequest req, HttpServletResponse resp)
                throws IOException {
//        UserService userService = UserServiceFactory.getUserService();
//        User user = userService.getCurrentUser();
//
//        String guestbookName = req.getParameter("guestbookName");
//        Key guestbookKey = KeyFactory.createKey("Guestbook", guestbookName);
//        String content = req.getParameter("content");
//        Date date = new Date();
//        Entity greeting = new Entity("Greeting", guestbookKey);
//        greeting.setProperty("user", user);
//        greeting.setProperty("date", date);
//        greeting.setProperty("content", content);
//
//        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
//        datastore.put(greeting);
//
//        resp.sendRedirect("/guestbook.jsp?guestbookName=" + guestbookName);
    	
    	
    	String email=req.getParameter("email");
		String mobile=req.getParameter("cell");
		String userName=req.getParameter("userName");
		String businessName=req.getParameter("businessName");
		String comment=req.getParameter("comment");
		String phone=req.getParameter("phone");
		String country=req.getParameter("country");
		String contactType=req.getParameter("contactType");
		String sysType=req.getParameter("interestType");
		
		ContactInfo contactInfo = new ContactInfo();
		contactInfo.setEmail(email);
		contactInfo.setMobile(mobile);
		contactInfo.setUserName(userName);
		contactInfo.setBusinessName(businessName);
		contactInfo.setComment(comment);
		contactInfo.setPhone(phone);
		contactInfo.setCountry(country);
		contactInfo.setSysType(sysType);
		contactInfo.setContactType(contactType);
		this.contactInfoBiz.saveContactInfo(contactInfo);
		
		resp.setContentType("text/html; charset=utf8");
        resp.getWriter().println("Thanks for interest ESBLink, We have receive your request.");
        resp.getWriter().println("<br/><a href='#' onclick='history.go(-1)'>back</a>");
		
		
    }

	public void setContactInfoBiz(IContactInfoBiz contactInfoBiz) {
		ContactInfoServlet.contactInfoBiz = contactInfoBiz;
	}
    
}
