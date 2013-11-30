package org.esblink.module.basedata.biz;

import java.util.Collection;

import org.esblink.common.base.IPage;
import org.esblink.common.base.QueryObj;
import org.esblink.common.base.gae.IBaseBIZ;
import org.esblink.module.basedata.domain.Parameter;

/**
 * <pre>
 * *********************************************
 * Copyright esblink.net.
 * All rights reserved.
 * Description: Parameter BIZ接口定义
 * HISTORY
 * *********************************************
 *  ID   DATE           PERSON          REASON
 *  1    2012.04.18     CodeGen         Create
 * 
 * *********************************************
 * </pre>
 */
public interface IParameterBiz extends IBaseBIZ {

	public void saveParameter(Parameter parameter);

	public Parameter findParameter(Long id);

	public Collection<Parameter> findBy(Parameter parameter);

	public IPage<Parameter> findPageBy(QueryObj queryObj);

	public void deleteParameters(String ids);

}
