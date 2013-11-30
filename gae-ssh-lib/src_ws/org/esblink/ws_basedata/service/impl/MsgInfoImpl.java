package org.esblink.ws_basedata.service.impl;

import org.esblink.ws_basedata.service.IMsgInfo;

import com.esblink.dev.domain.MsgInfoVO;
import com.esblink.dev.type.Heard;
import com.esblink.dev.type.ReturnResult;

public class MsgInfoImpl implements IMsgInfo {

	@Override
	public ReturnResult<MsgInfoVO> findMsgInfo(Long id, Heard heard) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReturnResult<MsgInfoVO> findMsgInfoBySys(String sysType,
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

}
