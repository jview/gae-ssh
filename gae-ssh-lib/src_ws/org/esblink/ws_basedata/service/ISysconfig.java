package org.esblink.ws_basedata.service;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import org.esblink.ws_basedata.domain.SysconfigVO;

import com.esblink.dev.type.Heard;
import com.esblink.dev.type.ReturnResult;

@WebService
@SOAPBinding(style = Style.DOCUMENT)
public interface ISysconfig {
	public ReturnResult<SysconfigVO> findSysconfigByCode(@WebParam(name = "sysCode") String sysCode
			,@WebParam(name = "propNames") String propNames
			,@WebParam(name = "heard") Heard heard);
	
	public ReturnResult<SysconfigVO> findSysconfigByCodeAll(@WebParam(name = "ifShowOpt")Boolean ifShowOpt
			,@WebParam(name = "ifShowNull")Boolean ifShowNull
			,@WebParam(name = "propNames")String propNames
			,@WebParam(name = "heard") Heard heard);
	
	public ReturnResult<Long> saveSysconfigByCode(@WebParam(name = "operatorId")Long operatorId
			,@WebParam(name = "sysconfig") SysconfigVO sysconfig
			,@WebParam(name = "heard") Heard heard);
}
