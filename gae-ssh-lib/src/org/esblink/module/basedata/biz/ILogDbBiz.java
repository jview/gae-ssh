package org.esblink.module.basedata.biz;

import java.io.InputStream;
import java.util.Collection;

import org.esblink.common.base.IPage;
import org.esblink.common.base.QueryObj;
import org.esblink.common.base.domain.IUser;
import org.esblink.common.base.gae.IBaseBIZ;
import org.esblink.module.basedata.domain.LogDb;

/**
 * <pre>
 * *********************************************
 * Copyright esblink.net.
 * All rights reserved.
 * Description: LogDb BIZ接口定义
 * HISTORY
 * *********************************************
 *  ID   DATE           PERSON          REASON
 *  1    2012.04.18     CodeGen         Create
 * 
 * *********************************************
 * </pre>
 */
public interface ILogDbBiz extends IBaseBIZ {

	public void saveLogDb(LogDb logDb);
	
	public void write(Long level, IUser user, String moduleCode,  String modelCode, String title, String action, String desc, String retValue, Object obj);
	
	public void debug(IUser user, String moduleCode, String modelCode, String title, String action, String desc, String retValue, Object obj);
	
	public void warn(IUser user, String moduleCode, String modelCode, String title, String action, String desc, String retValue, Object obj);
	
	public void info(IUser user, String moduleCode, String modelCode, String title, String action, String desc, String retValue, Object obj);
	
	public void error(IUser user, String moduleCode, String modelCode, String title, String action, String desc,String retValue, Object obj);
	
	public void fatal(IUser user, String moduleCode, String modelCode, String title, String action, String desc,String retValue, Object obj);

	public void info(IUser user, LogDb logDb, String title, String action, String desc, String retValue, Object obj);
	
	public void debug(IUser user, LogDb logDb, String title, String action, String desc, String retValue, Object obj);
	
	public void warn(IUser user, LogDb logDb, String title, String action, String desc, String retValue, Object obj);
	
	public void error(IUser user, LogDb logDb, String title, String action, String desc, String retValue, Object obj);
	
	public void fatal(IUser user, LogDb logDb, String title, String action, String desc, String retValue, Object obj);

	public LogDb findLogDb(Long id);

	public Collection<LogDb> findBy(LogDb logDb);

	public IPage<LogDb> findPageBy(QueryObj queryObj);

	public void deleteLogDbs(String ids);
	
	public InputStream exportExcel(QueryObj queryObj);

}
