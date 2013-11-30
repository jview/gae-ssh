package org.esblink.ws_basedata.service;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.esblink.dev.type.Heard;


@WebService
public class ICodeTable {
	@WebMethod
	public Heard findCodeTableByCodeType( String codeType
			,String propNames
			,String lastTime){
		return null;
	}
	@WebMethod
	public String findCodeTable(Long id){
				return null;
			}
	@WebMethod
	public String findCodeTableByDataId(String codeType
			, Long dataId){
				return null;
			}

}
