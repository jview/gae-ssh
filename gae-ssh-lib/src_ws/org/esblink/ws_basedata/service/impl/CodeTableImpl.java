package org.esblink.ws_basedata.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import org.esblink.module.basedata.biz.ICodeTableBiz;
import org.esblink.module.basedata.domain.CodeTable;
import org.esblink.ws_basedata.domain.CodeTableVO;
import org.esblink.ws_basedata.service.ICodeTable;

import com.esblink.dev.type.Heard;
import com.esblink.dev.type.ReturnResult;

@WebService(name="codeTableService")
@SOAPBinding(style = Style.DOCUMENT)
public class CodeTableImpl  {
	@Resource(name="codeTableBiz")
	private ICodeTableBiz codeTableBiz;
//	@Override
	@WebMethod
	public ReturnResult<CodeTableVO> findCodeTableByCodeType(String codeType,
			String propNames, String lastTime, Heard heard) {
		// TODO Auto-generated method stub
		ReturnResult<CodeTableVO> ret = new ReturnResult<CodeTableVO>();
		ret.setReturnFlag("0");
		List<CodeTable> list=this.codeTableBiz.codeTableAll();
		List<CodeTableVO> voList = new ArrayList<CodeTableVO>();
		for(CodeTable ct:list){
			voList.add(new CodeTableVO(ct));
		}
		ret.setDataList(voList);
		return ret;
		
	}

//	@Override
	@WebMethod
	public ReturnResult<CodeTableVO> findCodeTable(Long id, Heard heard) {
		// TODO Auto-generated method stub
//		return null;
		ReturnResult<CodeTableVO> ret = new ReturnResult<CodeTableVO>();
		ret.setReturnFlag("0");
		CodeTable codeTable= this.codeTableBiz.findCodeTable(id);
		
		if(codeTable!=null){
			ret.setData(new CodeTableVO(codeTable));
		}
		
		return ret;
	}

//	@Override
	@WebMethod
	public ReturnResult<CodeTableVO> findCodeTableByDataId(String codeType,
			Long dataId, Heard heard) {
		// TODO Auto-generated method stub
		ReturnResult<CodeTableVO> ret = new ReturnResult<CodeTableVO>();
		ret.setReturnFlag("0");
		String[] types=new String[1];
		types[0]=codeType;
		List<CodeTable> list=this.codeTableBiz.findByTypes(types);
		for(CodeTable ct:list){
			if(ct.getDataId().longValue()==dataId.longValue()){
				ret.setData(new CodeTableVO(ct));
				break;
			}
		}
		return ret;
	}

}
