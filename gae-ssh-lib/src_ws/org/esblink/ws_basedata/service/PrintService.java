package org.esblink.ws_basedata.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.esblink.common.base.resteasy.BeansCacheRest;
import org.esblink.module.basedata.util.MsgInfos;
import org.esblink.module.organization.biz.IEmployeeBiz;
import org.esblink.module.organization.domain.Employee;
import org.esblink.ws_basedata.util.Constants;
import org.jboss.resteasy.annotations.Form;

import cn.kpifa.paras.util.CommMethod;

import com.esblink.dev.type.Heard;
import com.esblink.dev.type.ReturnResult;
    
@Path(Constants.REST_PATH+"/customer")
public class PrintService {   
//	@Resource(name="employeeBiz")
    private IEmployeeBiz employeeBiz;
    
    @GET  
    @Path("/print")   
    public Response printMessage() {
    	if(this.employeeBiz==null){
    		this.employeeBiz=(IEmployeeBiz)BeansCacheRest.getBean(this.getClass(), IEmployeeBiz.class.getName());
    	}
//    	System.out.println("------employeeBiz="+this.employeeBiz);
    	Collection<Employee> empList=employeeBiz.findBy(new Employee());
    	String code="";
    	for(Employee emp:empList){
    		code+=emp.getEmpCode()+",";
    	}
        String result = code;
//    	
//    	String result="test";
    
        return Response.status(200).entity(result).build();   
    
    }
    
    @GET
    @Produces({MediaType.APPLICATION_JSON })
    @Path("/list")   
    public Collection<Employee> list() {
    	if(this.employeeBiz==null){
    		this.employeeBiz=(IEmployeeBiz)BeansCacheRest.getBean(this.getClass(), IEmployeeBiz.class.getName());
    	}
//    	System.out.println("------employeeBiz="+this.employeeBiz);
    	Collection<Employee> empList=employeeBiz.findBy(new Employee());
    	return empList;
    
    }
    
    @GET
    @Produces({MediaType.APPLICATION_JSON })
    @Path("/findAll")   
    public ReturnResult<Employee> findAll() {
    	if(this.employeeBiz==null){
    		this.employeeBiz=(IEmployeeBiz)BeansCacheRest.getBean(this.getClass(), IEmployeeBiz.class.getName());
    	}
//    	System.out.println("------employeeBiz="+this.employeeBiz);
    	Collection<Employee> empList=employeeBiz.findBy(new Employee());
    	ReturnResult<Employee> ret = new ReturnResult<Employee>();
    	ret.setReturnFlag("0");
    	ret.setReturnTime(CommMethod.getCurrentDateStr());
    	List<Employee> list=new ArrayList();
    	list.addAll(empList);
    	ret.setDataList(list);
    	return ret;
    
    }
    
    @GET
    @POST
    @Produces({MediaType.APPLICATION_JSON })
    @Path("/findById/{id}")   
    public ReturnResult<Employee> findById(@PathParam("id") Long id) {
    	if(this.employeeBiz==null){
    		this.employeeBiz=(IEmployeeBiz)BeansCacheRest.getBean(this.getClass(), IEmployeeBiz.class.getName());
    	}
//    	System.out.println("------employeeBiz="+this.employeeBiz);
    	Employee emp=employeeBiz.findEmployee(id);
    	ReturnResult<Employee> ret = new ReturnResult<Employee>();
    	ret.setReturnFlag("0");
    	ret.setData(emp);
    	return ret;
    
    }
    
    @GET
    @POST
    @Produces({MediaType.APPLICATION_JSON })
    @Path("/findById3")
    public ReturnResult<Employee> findById3(@QueryParam("id") Long id) {
    	if(this.employeeBiz==null){
    		this.employeeBiz=(IEmployeeBiz)BeansCacheRest.getBean(this.getClass(), IEmployeeBiz.class.getName());
    	}
//    	System.out.println("------employeeBiz="+this.employeeBiz);
    	Employee emp=employeeBiz.findEmployee(id);
    	ReturnResult<Employee> ret = new ReturnResult<Employee>();
    	ret.setReturnFlag("0");
    	ret.setData(emp);
    	return ret;
    
    }
    
    @GET
    @Produces({MediaType.APPLICATION_JSON })
    @Path("/findByIdGet")   
    public ReturnResult<Employee> findByIdGet(@QueryParam("id") Long id, @Context HttpServletRequest request) {
    	if(this.employeeBiz==null){
    		this.employeeBiz=(IEmployeeBiz)BeansCacheRest.getBean(this.getClass(), IEmployeeBiz.class.getName());
    	}
    	
    	Heard heard = new Heard(request, null);
//    	System.out.println("------employeeBiz="+this.employeeBiz);
    	if(heard!=null)
    		System.out.println("-------heard.operatorCode="+heard.getOperator_code());
    	
    	ReturnResult<Employee> ret = new ReturnResult<Employee>();
    	ret.setReturnFlag("0");
    	if(id==null){
    		ret.setReturnFlag("1");
    		ret.setMessageInfor(MsgInfos.msgs_ios.ERR_DATA_INPUT.getCode());
    		ret.setMessageBody("id="+id+", heard.operator="+heard.getOperator_code());
    		return ret;
    	}
//    	System.out.println("----------"+heard.getOperator_code());
    	Employee emp=employeeBiz.findEmployee(id);
    	
    	ret.setData(emp);
    	return ret;
    
    }
    
    @POST
    @Produces({MediaType.APPLICATION_JSON })
    @Path("/findByIdPost")   
    public ReturnResult<Employee> findByIdPost(@QueryParam("id") Long id, @Context HttpServletRequest request) {
    	if(this.employeeBiz==null){
    		this.employeeBiz=(IEmployeeBiz)BeansCacheRest.getBean(this.getClass(), IEmployeeBiz.class.getName());
    	}
    	
    	Heard heard = new Heard(request, null);
//    	System.out.println("------employeeBiz="+this.employeeBiz);
    	if(heard!=null)
    		System.out.println("-------heard.operatorCode="+heard.getOperator_code());
    	
    	ReturnResult<Employee> ret = new ReturnResult<Employee>();
    	ret.setReturnFlag("0");
    	if(id==null){
    		ret.setReturnFlag("1");
    		ret.setMessageInfor(MsgInfos.msgs_ios.ERR_DATA_INPUT.getCode());
    		ret.setMessageBody("id="+id+", heard.operator="+heard.getOperator_code());
    		return ret;
    	}
//    	System.out.println("----------"+heard.getOperator_code());
    	Employee emp=employeeBiz.findEmployee(id);
    	
    	ret.setData(emp);
    	return ret;
    
    }
    
    
    @POST
    @GET
    @Produces({MediaType.APPLICATION_JSON })
    @Path("/findByIdPostGet")   
    public ReturnResult<Employee> findByIdPostGet(@QueryParam("id") Long id, @Context HttpServletRequest request) {
    	if(this.employeeBiz==null){
    		this.employeeBiz=(IEmployeeBiz)BeansCacheRest.getBean(this.getClass(), IEmployeeBiz.class.getName());
    	}
    	
    	Heard heard = new Heard(request, null);
//    	System.out.println("------employeeBiz="+this.employeeBiz);
    	if(heard!=null)
    		System.out.println("-------heard.operatorCode="+heard.getOperator_code());
    	
    	ReturnResult<Employee> ret = new ReturnResult<Employee>();
    	ret.setReturnFlag("0");
    	if(id==null){
    		ret.setReturnFlag("1");
    		ret.setMessageInfor(MsgInfos.msgs_ios.ERR_DATA_INPUT.getCode());
    		ret.setMessageBody("id="+id+", heard.operator="+heard.getOperator_code());
    		return ret;
    	}
//    	System.out.println("----------"+heard.getOperator_code());
    	Employee emp=employeeBiz.findEmployee(id);
    	
    	ret.setData(emp);
    	return ret;
    
    }
    
    @Produces({MediaType.APPLICATION_JSON })
    @POST
    @GET
    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//    @Produces(MediaType.TEXT_HTML)
    @Path("/findById2")   
    public ReturnResult<Employee> findById2(@Form Employee employee, @QueryParam("id") Long id) {
    	if(this.employeeBiz==null){
    		this.employeeBiz=(IEmployeeBiz)BeansCacheRest.getBean(this.getClass(), IEmployeeBiz.class.getName());
    	}
    	
    	if(employee==null){
    		employee = new Employee();
    		employee.setId(1l);
    	}
    	if(employee.getId()==null){
    		employee.setId(1l);
    	}
    	System.out.println("------id="+employee.getId());
    	Employee emp=employeeBiz.findEmployee(employee.getId());
    	ReturnResult<Employee> ret = new ReturnResult<Employee>();
    	ret.setReturnFlag("0");
    	ret.setData(emp);
    	return ret;
    
    }
    
    @GET
    @Produces({MediaType.APPLICATION_XML })
    @Path("/listXml")   
    public Collection<Employee> listXml() {
    	if(this.employeeBiz==null){
    		this.employeeBiz=(IEmployeeBiz)BeansCacheRest.getBean(this.getClass(), IEmployeeBiz.class.getName());
    	}
//    	System.out.println("------employeeBiz="+this.employeeBiz);
    	Collection<Employee> empList=employeeBiz.findBy(new Employee());
    	return empList;
    
    }
    
    @GET
    @Produces({MediaType.APPLICATION_XHTML_XML })
    @Path("/listXhtmlXml")   
    public Collection<Employee> listXhtmlXml() {
    	if(this.employeeBiz==null){
    		this.employeeBiz=(IEmployeeBiz)BeansCacheRest.getBean(this.getClass(), IEmployeeBiz.class.getName());
    	}
//    	System.out.println("------employeeBiz="+this.employeeBiz);
    	Collection<Employee> empList=employeeBiz.findBy(new Employee());
    	return empList;
    
    }


	public void setEmployeeBiz(IEmployeeBiz employeeBiz) {
		
		this.employeeBiz = employeeBiz;
		BeansCacheRest.putBean(this.getClass(), IEmployeeBiz.class.getName(), employeeBiz);
//		System.out.println("-------employeeBiz="+this.employeeBiz+" employeeBiz="+employeeBiz);
	}   
    
    
}  