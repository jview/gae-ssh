package org.esblink.ws_basedata.service;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import org.esblink.module.basedata.domain.ParameterUser;

import com.esblink.dev.type.Heard;
import com.esblink.dev.type.ReturnResult;

@WebService
@SOAPBinding(style = Style.DOCUMENT)
public interface IParameterUser {
	public ReturnResult<ParameterUser> findParameterUserByCode(@WebParam(name = "userId") Long userId
			,@WebParam(name = "paramCode")  String paramCode
			,@WebParam(name = "heard")  Heard heard);
	
	public ReturnResult<ParameterUser> findParameterUserByUser(@WebParam(name = "userId") Long userId
			,@WebParam(name = "heard")  Heard heard);
	
	public ReturnResult<Long> saveParameterUserValue(@WebParam(name = "userId") Long userId
			,@WebParam(name = "paramCode") String paramCode
			,@WebParam(name = "value") String value
			,@WebParam(name = "heard") Heard heard);
}
