package org.esblink.module.customer.dao;

import java.util.Collection;

import org.esblink.common.base.IPage;
import org.esblink.common.base.QueryObj;
import org.esblink.common.base.gae.IBaseDAO;
import org.esblink.module.customer.domain.UserReport;

/**
 * <pre>
 * *********************************************
 * Copyright esblink.net.
 * All rights reserved.
 * Description: UserReport DAO接口定义
 * HISTORY
 * *********************************************
 *  ID   DATE           PERSON          REASON
 *  1    2013.04.29     CodeGen         Create
 * 
 * *********************************************
 * </pre>
 */
public interface IUserReportDao extends IBaseDAO<UserReport> {

	public Collection<UserReport> findBy(QueryObj queryObj);

	public IPage<UserReport> findPageBy(QueryObj queryObj);

}
