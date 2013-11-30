package org.esblink.module.customer.biz;

import java.io.InputStream;
import java.util.Collection;
import java.util.Date;

import org.esblink.common.base.IPage;
import org.esblink.common.base.QueryObj;
import org.esblink.common.base.gae.BaseBIZ;
import org.esblink.common.util.BeanUtils;
import org.esblink.module.customer.dao.IUserReportDao;
import org.esblink.module.customer.domain.UserReport;

/**
 * <pre>
 * *********************************************
 * Copyright esblink.net.
 * All rights reserved.
 * Description: UserReport BIZ接口实现
 * HISTORY
 * *********************************************
 *  ID   DATE           PERSON          REASON
 *  1    2013.04.29     CodeGen         Create
 * 
 * *********************************************
 * </pre>
 */
public class UserReportBiz extends BaseBIZ implements IUserReportBiz {

	// userReportDao
	private IUserReportDao userReportDao;

	public void setUserReportDao(IUserReportDao userReportDao) {
		this.userReportDao = userReportDao;
	}

	public void saveUserReport(UserReport userReport) {
		if (userReport.getCreateUser()==null) {
//			userReport.setCreateUser(super.getCurrentUser().getId());
			userReport.setCreateDate(new Date());
		}
//		userReport.setModifyUser(super.getCurrentUser().getId());
		userReport.setModifyDate(new Date());
		if(userReport.getStatus()==null){
			userReport.setStatus(1l);
		}
		if(userReport.getIfDel()==null){
			userReport.setIfDel(0l);
		}
		
		// save userReport
		if(userReport.getId()!=null&& userReport.getId()>0){
			this.userReportDao.update(userReport);
		}
		else{
			this.userReportDao.save(userReport);
		}
	}

	public UserReport findUserReport(Long id) {
		// load userReport
		UserReport userReport = this.userReportDao.load(id);

		return userReport;
	}

	public Collection<UserReport> findBy(UserReport userReport) {
		if (userReport == null) {
			userReport = new UserReport();
		} else {
			BeanUtils.clearEmptyProperty(userReport);
		}
		return this.userReportDao.findBy(userReport);
	}

	public IPage<UserReport> findPageBy(QueryObj queryObj) {
		return this.userReportDao.findPageBy(queryObj);
	}

	public void deleteUserReports(String ids) {
		String[] idArray = ids.split(",");
		UserReport d= null;
		for (String sid : idArray) {
			Long id = Long.parseLong(sid);
			d = this.findUserReport(id);
			d.setIfDel(1l);
			this.saveUserReport(d);
			// delete UserReport
//			this.userReportDao.remove(id);
		}
	}

	public InputStream exportUserReport(QueryObj queryObj) {
		try {
//			for (String key : queryObj.getQueryFields()) {
//				String value = new String(queryObj.getQueryValue(key).getBytes("ISO-8859-1"), "UTF-8");
//				queryObj.setQueryValue(key, value);
//			}
//			Collection<UserReport> data = userReportDao.findBy(queryObj);
//			TableDefine table = new TableDefine(getMessage("userReport"));
//			table.addColumn("id", getMessage("userReport.id"), 0);
//			table.addColumn("sysType", getMessage("userReport.sysType"), 0);
//			table.addColumn("userName", getMessage("userReport.userName"), 0);
//			table.addColumn("businessName", getMessage("userReport.businessName"), 0);
//			table.addColumn("email", getMessage("userReport.email"), 0);
//			table.addColumn("phone", getMessage("userReport.phone"), 0);
//			table.addColumn("mobile", getMessage("userReport.mobile"), 0);
//			table.addColumn("country", getMessage("userReport.country"), 0);
//			table.addColumn("contactType", getMessage("userReport.contactType"), 0);
//			table.addColumn("comment", getMessage("userReport.comment"), 0);
//			table.addColumn("ifDel", getMessage("userReport.ifDel"), 0);
//			table.addColumn("status", getMessage("userReport.status"), 0);
//			table.addColumn("createUser", getMessage("userReport.createUser"), 0);
//			table.addColumn("createDate", getMessage("userReport.createDate"), 0);
//			table.addColumn("modifyUser", getMessage("userReport.modifyUser"), 0);
//			table.addColumn("modifyDate", getMessage("userReport.modifyDate"), 0);
//			table.addColumn("remark", getMessage("userReport.remark"), 0);
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
