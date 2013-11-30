<jsp:directive.page contentType="text/html;charset=utf-8" />
<%@page import="java.util.List" %>
<%@page import="java.io.File" %>

*** login info ***<br/>
<%
String permit_codes = null;
out.println("sessionId:"+session.getId()+"<br/>");
out.println("<br/>");
Runtime lRuntime = Runtime.getRuntime();
out.println("*** BEGIN MEMORY STATISTICS ***<br/>");
out.println("Free  Memory: "+lRuntime.freeMemory()+"<br/>");
out.println("Total Memory: "+lRuntime.totalMemory()+"<br/>");
double used = (lRuntime.totalMemory()-lRuntime.freeMemory());
out.println("Used  Memory: "+(lRuntime.totalMemory()-lRuntime.freeMemory())+"----"+Math.round(used/lRuntime.totalMemory()*100)+"%<br/> ");
out.println("Max   Memory: "+lRuntime.maxMemory()+"<br/>");

out.println("Available Processors : "+lRuntime.availableProcessors()+"<br/>");
out.println("*** END MEMORY STATISTICS ***<br/>");


%>



<BR/>
<BR/>

<%
	String info = request.getContentType();
	out.println("ContentType: "+info+"<br/>");
	info = request.getContextPath();
	out.println("ContextPath: "+info+"<br/>");
	info = request.getPathInfo();
	out.println("PathInfo: "+info+"<br/>");
	info = request.getRequestURI();
	out.println("RequestURI: "+info+"<br/>");
	info = request.getCharacterEncoding();
	out.println("CharacterEncoding: "+info+"<br/>");
	info = request.getServletPath();
	out.println("ServletPath: "+info+"<br/>");
	info = request.getServerName();
	out.println("ServerName: "+info+"<br/>");
	info = request.getHeader("referer");
	out.println("referer: "+info+"<br/>");	
	info = request.getRequestURL().toString();
	out.println("RequestURL: "+info+"<br/>");
	info = request.getRequestedSessionId();
	out.println("RequestedSessionId: "+info+"<br/>");
	info = request.getQueryString();
	out.println("QueryString: "+info+"<br/>");
	info = request.getMethod();
	out.println("Method: "+info+"<br/>");
	info = null;
	if(request.getUserPrincipal()!=null){
		info = request.getUserPrincipal().getName();
	}
	out.println("UserPrincipal: "+info+"<br/>");
	Object vInfo=null;
	String[] values=request.getSession().getValueNames();
	for(String v:values){
		out.println(v+"<br/>\n");
	}
	
	String path=request.getParameter("path");
	if(path!=null){
		out.println(path+"<br/>\n");
		File file = new File(path);
		out.println(file.getAbsolutePath()+"<br/>\n");
		String[] fNames=file.list();
		for(String fName:fNames){
			out.println(fName+"<br/>\n");
		}
	}
	
%>

<BR/>
<BR/>


session_username=<%= request.getSession().getAttribute("login_userId")%>
<br/>


