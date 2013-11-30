package org.esblink.module.basedata.biz;


import java.util.Collection;
import java.util.List;

import org.esblink.common.base.IPage;
import org.esblink.common.base.QueryObj;
import org.esblink.common.base.gae.IBaseBIZ;
import org.esblink.module.basedata.domain.ParameterUser;

/**
 * <pre>
 * *********************************************
 * Copyright esblink.net.
 * All rights reserved.
 * Description: ParameterUser BIZ接口定义
 * HISTORY
 * *********************************************
 *  ID   DATE           PERSON          REASON
 *  1    2012.04.18     CodeGen         Create
 * 
 * *********************************************
 * </pre>
 */
public interface IParameterUserBiz extends IBaseBIZ {

	public void saveParameterUser(ParameterUser parameterUser);

	public ParameterUser findParameterUser(Long id);
	
	public List<ParameterUser> findParameterUserbyUser(Long userId);

	public Collection<ParameterUser> findBy(ParameterUser parameterUser);

	public IPage<ParameterUser> findPageBy(QueryObj queryObj);

	public void deleteParameterUsers(String ids);
	
	/**
	 * 根据编码及userId查ParameterUser,如果用户为设置取默认值
	 * @param param_code
	 * @param userId
	 * @return
	 */
	public Long saveParameterUserValue(String param_code, Long userId, String value) throws Exception;

}
