package org.esblink.module.basedata.action;

import java.io.InputStream;
import java.util.Collection;

import javax.annotation.Resource;

import org.esblink.common.base.IPage;
import org.esblink.common.base.QueryObj;
import org.esblink.common.base.action.BaseGridAction;
import org.esblink.module.basedata.biz.ILogDbBiz;
import org.esblink.module.basedata.domain.LogDb;
import org.springframework.stereotype.Controller;

/**
 * <pre>
 * *********************************************
 * Copyright esblink.net.
 * All rights reserved.
 * Description: LogDb Action
 * HISTORY
 * *********************************************
 *  ID   DATE           PERSON          REASON
 *  1    2012.04.18     CodeGen         Create
 * 
 * *********************************************
 * </pre>
 */
@SuppressWarnings("serial")
@Controller("logDbAction")
public class LogDbAction extends BaseGridAction<LogDb> {
	private String moduleCode="logDb";
	private String modelCode = getClass().getSimpleName();
	@Resource(name="logDbBiz")
	private ILogDbBiz logDbBiz;
	private LogDb logDb;
	private String ids;
	private String msg;
	private Collection<LogDb> logDbs;
	private int total;
	private InputStream stream;

	public String saveLogDb() {
		try {
//			logDbBiz.saveLogDb(logDb);
			msg="error";
		} catch (Exception e) {
			msg = e.toString();
			log.error("error", e);
		}
		return SUCCESS;
	}

	public String findLogDb() {
		logDb = logDbBiz.findLogDb(logDb.getId());
		return SUCCESS;
	}

	public String findByLogDb() {
		logDbs = logDbBiz.findBy(logDb);
		return SUCCESS;
	}

	public String deleteLogDbs() {
		try {
//			logDbBiz.deleteLogDbs(ids);
			msg="error";
		} catch (Exception e) {
			msg = e.toString();
			log.error("error", e);
		}
		return SUCCESS;
	}

	public String findPageByLogDb() {
		QueryObj queryObj = QueryObj.parseQueryObj(this);
		IPage<LogDb> page = logDbBiz.findPageBy(queryObj);
		logDbs = page.getData();
		total = (int) page.getTotalSize();
		return SUCCESS;
	}
	
	public String exportExcel() {
		QueryObj queryObj = QueryObj.parseQueryObj(this);
		String queryInfo = queryObj.toString();
		stream = logDbBiz.exportExcel(queryObj);
		this.logDbBiz.info(this.getCurrentUser(), this.moduleCode, modelCode, "exportExcel", "queryInfo="+queryInfo, "", SUCCESS,logDb);
		return SUCCESS;
	} 

	public boolean isSuccess() {
		return true;
	}

	public void setLogDbBiz(ILogDbBiz logDbBiz) {
		this.logDbBiz = logDbBiz;
	}

	public LogDb getLogDb() {
		return this.logDb;
	}

	public void setLogDb(LogDb logDb) {
		this.logDb = logDb;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getMsg() {
		return this.msg;
	}

	public Collection<LogDb> getLogDbs() {
		return this.logDbs;
	}
	
	public Collection<LogDb> getRows() {
		return this.logDbs;
	}
	
	public void setRows(int rows){
		this.total=rows;
	}

	public int getTotal() {
		return this.total;
	}
	
	public InputStream getStream() {
		return stream;
	}
}
