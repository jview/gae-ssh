package org.esblink.ws_basedata.service;

import java.util.Collection;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.apache.log4j.Logger;
import org.esblink.common.base.resteasy.BeansCacheRest;
import org.esblink.module.basedata.biz.ISysconfigBiz;
import org.esblink.module.basedata.domain.Sysconfig;
import org.esblink.ws_basedata.domain.SysconfigVO;
import org.esblink.ws_basedata.util.Constants;

import com.esblink.dev.type.Heard;
import com.esblink.dev.type.ReturnResult;
import com.esblink.dev.util.CommMethod;
import com.esblink.dev.util.MsgInfos;

@Path(Constants.REST_PATH+"/basedata/sysconfig")
public class SysconfigService implements ISysconfig {
	private static Logger log4 = Logger.getLogger(SysconfigService.class);
//	@Resource(name="sysconfigBiz")
	private ISysconfigBiz sysconfigBiz;
	@Override
	 @GET  
	 @Path("/findByCode")
	public ReturnResult<SysconfigVO> findSysconfigByCode(String sysCode,
			String propNames, Heard heard) {
		// TODO Auto-generated method stub
		ReturnResult<SysconfigVO> ret = new ReturnResult<SysconfigVO>();
		ret.setReturnFlag("0");
		
		if(sysCode==null||sysCode.trim().length()==0){
			ret.initErrorInfo(heard, MsgInfos.msgs_ios.ERR_DATA_INPUT, "codeType="+sysCode);
			log4.info(ret.getMessageBody());
			return ret;
		}
		String[] props = CommMethod.splitPropNames(propNames);
		Sysconfig search = new Sysconfig();
		search.setIfDel(0l);
		search.setStatus(1l);
		search.setCode(sysCode);
		Collection<Sysconfig> sysConfigList=this.getSysconfigBiz().findBy(search);
		if(sysConfigList.size()==0){
			ret.initErrorInfo(heard, MsgInfos.msgs_ios.ERR_DATA_NOT_FOUND, "codeType="+sysCode);
			log4.info(ret.getMessageBody());
			return ret;
		}
		
		Sysconfig sysConfig = null;
		for(Sysconfig cur:sysConfigList){
			sysConfig=cur;
			break;
		}
		
		SysconfigVO vo = new SysconfigVO(sysConfig, props);
		
		String xmls="";
//		ReturnResult<String> retXml=this.tableColumnDataBiz.findColumnOptDataXml("crewReport", vo.getId(), true, heard);
//		if("0".equals(retXml.getReturnFlag())){
//			xmls+=retXml.getData();
//		}
		
//		xmls=this.tableColumnDataBiz.findOptDataXmlAllByTableCode("sysconfig", vo.getId(), true);
//		vo.setOptXml(xmls);
		
		
		ret.setData(vo);
		
		return ret;
	}

	@Override
	@GET  
	 @Path("/findByCodeAll")
	public ReturnResult<SysconfigVO> findSysconfigByCodeAll(Boolean ifShowOpt,
			Boolean ifShowNull, String propNames, Heard heard) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@GET  
	 @Path("/findByCode")
	public ReturnResult<Long> saveSysconfigByCode(Long operatorId,
			SysconfigVO sysconfig, Heard heard) {
		// TODO Auto-generated method stub
		return null;
	}

	public ISysconfigBiz getSysconfigBiz() {
		if(this.sysconfigBiz==null){
    		this.sysconfigBiz=(ISysconfigBiz)BeansCacheRest.getBean(this.getClass(), ISysconfigBiz.class.getName());
    	}
		return sysconfigBiz;
	}

	public void setSysconfigBiz(ISysconfigBiz sysconfigBiz) {
		this.sysconfigBiz = sysconfigBiz;
		BeansCacheRest.putBean(this.getClass(), ISysconfigBiz.class.getName(), sysconfigBiz);
	}

	
}
