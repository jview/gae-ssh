package org.esblink.module.customer.biz;

import java.io.InputStream;
import java.util.Collection;

import org.esblink.common.base.IPage;
import org.esblink.common.base.QueryObj;
import org.esblink.common.base.gae.IBaseBIZ;
import org.esblink.module.customer.domain.UserReport;

/**
 * <pre>
 * *********************************************
 * Copyright esblink.net.
 * All rights reserved.
 * Description: UserReport BIZ接口定义
 * HISTORY
 * *********************************************
 *  ID   DATE           PERSON          REASON
 *  1    2013.04.29     CodeGen         Create
 * 
 * *********************************************
 * </pre>
 */
public interface IUserReportBiz extends IBaseBIZ {

	public void saveUserReport(UserReport contactInfo);

	public UserReport findUserReport(Long id);

	public Collection<UserReport> findBy(UserReport contactInfo);

	public IPage<UserReport> findPageBy(QueryObj queryObj);

	public void deleteUserReports(String ids);

	public InputStream exportUserReport(QueryObj queryObj);

}
