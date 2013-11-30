package org.esblink.ws_basedata.service.impl;

import javax.annotation.Resource;

import org.esblink.module.basedata.biz.IParameterUserBiz;
import org.esblink.module.basedata.domain.ParameterUser;
import org.esblink.ws_basedata.service.IParameterUser;

import com.esblink.dev.type.Heard;
import com.esblink.dev.type.ReturnResult;

public class ParameterUserImpl implements IParameterUser {
	@Resource(name="parameterUserBiz")
	private IParameterUserBiz parameterUserBiz;

	@Override
	public ReturnResult<ParameterUser> findParameterUserByCode(Long userId,
			String paramCode, Heard heard) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReturnResult<ParameterUser> findParameterUserByUser(Long userId,
			Heard heard) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReturnResult<Long> saveParameterUserValue(Long userId,
			String paramCode, String value, Heard heard) {
		// TODO Auto-generated method stub
		return null;
	}

}
