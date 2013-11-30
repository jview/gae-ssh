package org.esblink.ws_basedata.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.esblink.ws_basedata.util.Constants;

import com.esblink.dev.domain.MsgInfoVO;
import com.esblink.dev.type.Heard;
import com.esblink.dev.type.ReturnResult;

@Path(Constants.REST_PATH+"/basedata/msgInfo")
public class MsgInfoService implements IMsgInfo {
//	private IMsgInfoBiz msgInfoBiz;
	@Override
	 @GET
	 @Produces({MediaType.APPLICATION_JSON })
	 @Path("/findById/{id}")
	public ReturnResult<MsgInfoVO> findMsgInfo(@PathParam("codeType") Long id, Heard heard) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	 @GET
	 @Produces({MediaType.APPLICATION_JSON })
	 @Path("/findBySysType/{sysType}")
	public ReturnResult<MsgInfoVO> findMsgInfoBySys(@PathParam("codeType")String sysType,
			String propNames, Heard heard) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReturnResult<MsgInfoVO> findMsgInfoBySysAndAll(String sysType,
			String propNames, Heard heard) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReturnResult<MsgInfoVO> findMsgInfoByCode(String sysType,
			String code, Heard heard) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReturnResult<MsgInfoVO> refresh(String sysType, Heard heard) {
		// TODO Auto-generated method stub
		return null;
	}

//	public IMsgInfoBiz getMsgInfoBiz() {
//		return msgInfoBiz;
//	}
//
//	public void setMsgInfoBiz(IMsgInfoBiz msgInfoBiz) {
//		this.msgInfoBiz = msgInfoBiz;
//	}

	
}
