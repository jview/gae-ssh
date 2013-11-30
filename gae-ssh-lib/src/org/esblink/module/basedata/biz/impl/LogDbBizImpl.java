package org.esblink.module.basedata.biz.impl;

import java.io.InputStream;
import java.util.Collection;
import java.util.Date;

import javax.annotation.Resource;

import org.esblink.common.base.IPage;
import org.esblink.common.base.QueryObj;
import org.esblink.common.base.domain.IUser;
import org.esblink.common.base.domain.LogInfo;
import org.esblink.common.base.gae.BaseBIZ;
import org.esblink.common.util.BeanUtils;
import org.esblink.common.util.JsonInfo;
import org.esblink.module.basedata.biz.ILogDbBiz;
import org.esblink.module.basedata.dao.ILogDbDao;
import org.esblink.module.basedata.domain.LogDb;
import org.springframework.stereotype.Service;

import com.esblink.dev.util.CommMethod;
import com.esblink.dev.util.ConfigUtil;

/**
 *
 */
@Service("logDbBiz")
public class LogDbBizImpl extends BaseBIZ implements ILogDbBiz {

	// logDbDao
	@Resource(name="logDbDao")
	private ILogDbDao logDbDao;
	private final static Long DEBUG = 1l, INFO = 2l, WARN = 3l, ERROR = 4l,
			FATAL = 5l;
	private JsonInfo jsonInfo = new JsonInfo();
	private LogInfo logInfo = LogInfo.getLogger(LogDbBizImpl.class);

	public void setLogDbDao(ILogDbDao logDbDao) {
		this.logDbDao = logDbDao;
	}

	public void saveLogDb(LogDb logDb) {
		logDb.setUserId(super.getCurrentUser().getId());
		logDb.setOperTime(new Date());
		// save logDb
		this.logDbDao.save(logDb);
	}

	
	public void write(Long level, IUser user, LogDb logDb, String title, String action, String desc, String retValue, Object obj){
		try{
			if(user!=null){
				if(null!=user.getEmpCode()){
					logDb.setUserCode(user.getEmpCode());
				}
				else{
					logDb.setUserCode(""+user.getId());			
				}
				if(user.getEmployee()!=null){
					logDb.setUserName(user.getEmployee().getName());
				}
				else{
					logDb.setUserName(user.getUsername());
				}
				logDb.setUserId(user.getId());
			}
			else{
				logDb.setUserId(1l);
				logDb.setUserCode("amp_sys");
				logDb.setUserName("amp_sys");
			}
			
			logDb.setOperTime(new Date());		
			logDb.setActions(action);
			logDb.setDescription(desc);
			logDb.setTitle(title);
			logDb.setLevels(level);
			logDb.setRetValue(retValue);
			if(obj!=null){				
				logDb.setJsonValue(this.jsonInfo.bean2jsonCfg(obj));
				if(logDb.getJsonValue()!=null){
					logDb.setJsonValue(logDb.getJsonValue().replaceAll(",", ",\n"));
				}				
			}
//			System.out.println("--------save logDb="+logDb);
			this.logDbDao.save(logDb);
		}catch(Exception e){
			if(CommMethod.getBoolean(ConfigUtil.getConfigValue(ConfigUtil.config_key.SYS_IF_TEST))){
				e.printStackTrace();
			}
			this.logInfo.logger.error("logDb error:"+e.getMessage());
//			this.logInfo.info(" logDb error:", user);
			Long user_id = logDb.getUserId();
			this.logInfo.error("logDb error:levels="+level+" useId="+user_id+" modelCode="+logDb.getModelCode()+" title="+title+" action="+action+"error:"+e.getMessage(), obj);
		}
		
	}
	
	public void write(Long level, IUser user, String moduleCode, String modelCode, String title, String action, String desc, String retValue, Object obj){
		LogDb logDb = new LogDb(moduleCode, modelCode);
		this.write(level, user, logDb, title, action, desc, retValue, obj);
	}

	public void info(IUser user, String moduleCode, String modelCode, String title, String action, String desc, String retValue, Object obj){
		this.write(INFO, user, moduleCode, modelCode, title, action, desc, retValue, obj);
		
	}
	
	public void error(IUser user, String moduleCode, String modelCode, String title, String action, String desc,String retValue, Object obj){
		this.write(ERROR, user, moduleCode, modelCode, title, action, desc, retValue, obj);
	}
	
	public void debug(IUser user, String moduleCode, String modelCode, String title, String action, String desc, String retValue, Object obj){
		this.write(DEBUG, user, moduleCode, modelCode, title, action, desc, retValue, obj);
	}
	
	public void warn(IUser user, String moduleCode, String modelCode, String title, String action, String desc, String retValue, Object obj){
		this.write(WARN, user, moduleCode, modelCode, title, action, desc, retValue, obj);
	}
	
	public void fatal(IUser user, String moduleCode, String modelCode, String title, String action, String desc, String retValue, Object obj){
		this.write(FATAL, user, moduleCode, modelCode, title, action, desc, retValue, obj);
	}
	
	public void info(IUser user, LogDb logDb, String title, String action, String desc, String retValue, Object obj){
		this.write(INFO, user, logDb, title, action,  desc, retValue, obj);
		
	}
	public void error(IUser user, LogDb logDb, String title, String action, String desc, String retValue, Object obj){
		this.write(ERROR, user, logDb, title, action,  desc, retValue, obj);
		
	}
	public void debug(IUser user, LogDb logDb, String title, String action, String desc, String retValue, Object obj){
		this.write(DEBUG, user, logDb, title, action,  desc, retValue, obj);
		
	}
	public void warn(IUser user, LogDb logDb, String title, String action, String desc, String retValue, Object obj){
		this.write(WARN, user, logDb, title, action,  desc, retValue, obj);
		
	}
	public void fatal(IUser user, LogDb logDb, String title, String action, String desc, String retValue, Object obj){
		this.write(FATAL, user, logDb, title, action,  desc, retValue, obj);
		
	}

	public LogDb findLogDb(Long id) {
		// load logDb
		LogDb logDb = this.logDbDao.findById(id);

		return logDb;
	}

	public Collection<LogDb> findBy(LogDb logDb) {
		if (logDb == null) {
			logDb = new LogDb();
		} else {
			BeanUtils.clearEmptyProperty(logDb);
		}
		return this.logDbDao.findBy(logDb);
	}

	public IPage<LogDb> findPageBy(QueryObj queryObj) {
		return this.logDbDao.findPageBy(LogDb.class, queryObj);
	}

	public void deleteLogDbs(String ids) {
		String[] idArray = ids.split(",");
		for (String sid : idArray) {
			Long id = Long.parseLong(sid);
			// delete LogDb
//			this.logDbDao.remove(id);
		}
	}
	
	public InputStream exportExcel(QueryObj queryObj) {
		try {
			for (String key : queryObj.getQueryObjects()) {
				String value = new String((""+queryObj.getQueryObject(key)).getBytes("ISO-8859-1"), "UTF-8");
				queryObj.setQueryObject(key, value);
			}
//			queryObj.setPageSize(Sysconfigs.getInt(Sysconfigs.EXPORT_EXCEL_MAX_SIXE));
//			queryObj.setPageIndex(0);
//			Collection<LogDb> data = logDbDao.findPageBy(queryObj);
//			TableDefine table = new TableDefine(getMessage("logDb"));
//			table.addColumn("userId", getMessage("logDb.userName"), 0);
//			table.addColumn("userCode", getMessage("logDb.userCode"), 0);
//			table.addColumn("title", getMessage("logDb.title"), 4000);
//			table.addColumn("levels", getMessage("logDb.levels"), 0);
//			table.addColumn("actions", getMessage("logDb.actions"), 8000);
//			table.addColumn("retValue", getMessage("logDb.retValue"), 4000);
//			table.addColumn("operTime", getMessage("logDb.operTime"), 5000);
//			return new ExcelExport(table).export(data);
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private String getMessage(String key) {
		return getMessageSource().getMessage(key, key);
	}
}
