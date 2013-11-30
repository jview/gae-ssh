package org.esblink.ws_basedata.service;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import com.esblink.dev.domain.MsgInfoVO;
import com.esblink.dev.type.Heard;
import com.esblink.dev.type.ReturnResult;

@WebService
@SOAPBinding(style = Style.DOCUMENT)
public interface IMsgInfo {
	public ReturnResult<MsgInfoVO> findMsgInfo(@WebParam(name = "id")  Long id
			, @WebParam(name = "heard") Heard heard);
	
	public ReturnResult<MsgInfoVO> findMsgInfoBySys(@WebParam(name = "sysType")  String sysType
			, @WebParam(name = "propNames") String propNames
			, @WebParam(name = "heard") Heard heard);
	
	public ReturnResult<MsgInfoVO> findMsgInfoBySysAndAll(@WebParam(name = "sysType")  String sysType
			, @WebParam(name = "propNames") String propNames
			, @WebParam(name = "heard") Heard heard);
	
	public ReturnResult<MsgInfoVO> findMsgInfoByCode(@WebParam(name = "sysType")  String sysType
			, @WebParam(name = "code")  String code
			, @WebParam(name = "heard") Heard heard);
	
	public ReturnResult<MsgInfoVO> refresh(@WebParam(name = "sysType")  String sysType
			, @WebParam(name = "heard") Heard heard);
}
