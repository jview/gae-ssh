package org.esblink.ws_basedata.service;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.esblink.common.base.resteasy.BeansCacheRest;
import org.esblink.module.basedata.biz.ICodeTableBiz;
import org.esblink.module.basedata.domain.CodeTable;
import org.esblink.ws_basedata.domain.CodeTableVO;
import org.esblink.ws_basedata.util.Constants;

import com.esblink.dev.type.Heard;
import com.esblink.dev.type.ReturnResult;

//@WebService(name="codeTableService")
//@SOAPBinding(style = Style.DOCUMENT)
@Path(Constants.REST_PATH+"/basedata/codeTable")
public class CodeTableService  {
//	@Resource(name="codeTableBiz")
	private ICodeTableBiz codeTableBiz;
//	@Override
//	@WebMethod
	 @GET
	 @Produces({MediaType.APPLICATION_JSON })
	 @Path("/findByCodeType/{codeType}")
	public ReturnResult<CodeTableVO> findCodeTableByCodeType(@PathParam("codeType") String codeType,
			@QueryParam("propNames") String propNames, @QueryParam("lastTime") String lastTime) {
		// TODO Auto-generated method stub
		ReturnResult<CodeTableVO> ret = new ReturnResult<CodeTableVO>();
		ret.setReturnFlag("0");
		List<CodeTable> list=this.getCodeTableBiz().codeTableAll();
		List<CodeTableVO> voList = new ArrayList<CodeTableVO>();
		for(CodeTable ct:list){
			voList.add(new CodeTableVO(ct));
		}
		ret.setDataList(voList);
		return ret;
		
	}

//	@Override
//	@WebMethod
	 @GET  
	 @Produces({MediaType.APPLICATION_JSON })
	 @Path("/findById/{id}")
	public ReturnResult<CodeTableVO> findCodeTable(@PathParam("id") Long id) {
		// TODO Auto-generated method stub
//		return null;
		ReturnResult<CodeTableVO> ret = new ReturnResult<CodeTableVO>();
		ret.setReturnFlag("0");
		CodeTable codeTable= this.getCodeTableBiz().findCodeTable(id);
		
		if(codeTable!=null){
			ret.setData(new CodeTableVO(codeTable));
		}
		
		return ret;
	}

//	@Override
//	@WebMethod
	 @GET
	 @Produces({MediaType.APPLICATION_JSON })
	 @Path("/findByDataId/{codeType}/{dataId}")
	public ReturnResult<CodeTableVO> findCodeTableByDataId(@PathParam("codeType") String codeType,
			@PathParam("dataId") Long dataId, Heard heard) {
		// TODO Auto-generated method stub
		ReturnResult<CodeTableVO> ret = new ReturnResult<CodeTableVO>();
		ret.setReturnFlag("0");
		String[] types=new String[1];
		types[0]=codeType;
		List<CodeTable> list=this.getCodeTableBiz().findByTypes(types);
		for(CodeTable ct:list){
			if(ct.getDataId().longValue()==dataId.longValue()){
				ret.setData(new CodeTableVO(ct));
				break;
			}
		}
		return ret;
	}

	public ICodeTableBiz getCodeTableBiz() {
		if(this.codeTableBiz==null){
    		this.codeTableBiz=(ICodeTableBiz)BeansCacheRest.getBean(this.getClass(), ICodeTableBiz.class.getName());
    	}
		return codeTableBiz;
	}

	public void setCodeTableBiz(ICodeTableBiz codeTableBiz) {
		this.codeTableBiz = codeTableBiz;
		BeansCacheRest.putBean(this.getClass(), ICodeTableBiz.class.getName(), codeTableBiz);
	}

	 
}
