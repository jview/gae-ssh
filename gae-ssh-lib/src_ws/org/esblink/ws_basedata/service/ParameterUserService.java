package org.esblink.ws_basedata.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.esblink.common.base.resteasy.BeansCacheRest;
import org.esblink.module.basedata.biz.IParameterUserBiz;
import org.esblink.module.basedata.domain.ParameterUser;
import org.esblink.ws_basedata.util.Constants;

import com.esblink.dev.type.Heard;
import com.esblink.dev.type.ReturnResult;

@Path(Constants.REST_PATH+"/basedata/parameterUser")
public class ParameterUserService implements IParameterUser {
//	@Resource(name="parameterUserBiz")
	private IParameterUserBiz parameterUserBiz;

	@Override
	 @GET  
	 @Path("/findByParamCode")
	public ReturnResult<ParameterUser> findParameterUserByCode(Long userId,
			String paramCode, Heard heard) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	 @GET  
	 @Path("/findByUserId")
	public ReturnResult<ParameterUser> findParameterUserByUser(Long userId,
			Heard heard) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	 @GET  
	 @Path("/findByUserParamValue")
	public ReturnResult<Long> saveParameterUserValue(Long userId,
			String paramCode, String value, Heard heard) {
		// TODO Auto-generated method stub
		return null;
	}

	public IParameterUserBiz getParameterUserBiz() {
		if(this.parameterUserBiz==null){
    		this.parameterUserBiz=(IParameterUserBiz)BeansCacheRest.getBean(this.getClass(), IParameterUserBiz.class.getName());
    	}
		return parameterUserBiz;
	}

	public void setParameterUserBiz(IParameterUserBiz parameterUserBiz) {
		this.parameterUserBiz = parameterUserBiz;
		BeansCacheRest.putBean(this.getClass(), IParameterUserBiz.class.getName(), parameterUserBiz);
	}

	
}
