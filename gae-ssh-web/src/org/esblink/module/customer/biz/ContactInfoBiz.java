package org.esblink.module.customer.biz;

import java.io.InputStream;
import java.util.Collection;
import java.util.Date;

import org.esblink.common.base.IPage;
import org.esblink.common.base.QueryObj;
import org.esblink.common.base.gae.BaseBIZ;
import org.esblink.common.util.BeanUtils;
import org.esblink.module.customer.dao.IContactInfoDao;
import org.esblink.module.customer.domain.ContactInfo;

/**
 * <pre>
 * *********************************************
 * Copyright esblink.net.
 * All rights reserved.
 * Description: ContactInfo BIZ接口实现
 * HISTORY
 * *********************************************
 *  ID   DATE           PERSON          REASON
 *  1    2013.04.29     CodeGen         Create
 * 
 * *********************************************
 * </pre>
 */
public class ContactInfoBiz extends BaseBIZ implements IContactInfoBiz {

	// contactInfoDao
	private IContactInfoDao contactInfoDao;

	public void setContactInfoDao(IContactInfoDao contactInfoDao) {
		this.contactInfoDao = contactInfoDao;
	}

	public void saveContactInfo(ContactInfo contactInfo) {
		if (contactInfo.getCreateUser()==null) {
//			contactInfo.setCreateUser(super.getCurrentUser().getId());
			contactInfo.setCreateDate(new Date());
		}
//		contactInfo.setModifyUser(super.getCurrentUser().getId());
		contactInfo.setModifyDate(new Date());
		if(contactInfo.getStatus()==null){
			contactInfo.setStatus(1l);
		}
		if(contactInfo.getIfDel()==null){
			contactInfo.setIfDel(0l);
		}
		
		// save contactInfo
		if(contactInfo.getId()!=null&& contactInfo.getId()>0){
			this.contactInfoDao.update(contactInfo);
		}
		else{
			this.contactInfoDao.save(contactInfo);
		}
	}

	public ContactInfo findContactInfo(Long id) {
		// load contactInfo
		ContactInfo contactInfo = this.contactInfoDao.load(id);

		return contactInfo;
	}

	public Collection<ContactInfo> findBy(ContactInfo contactInfo) {
		if (contactInfo == null) {
			contactInfo = new ContactInfo();
		} else {
			BeanUtils.clearEmptyProperty(contactInfo);
		}
		return this.contactInfoDao.findBy(contactInfo);
	}

	public IPage<ContactInfo> findPageBy(QueryObj queryObj) {
		return this.contactInfoDao.findPageBy(queryObj);
	}

	public void deleteContactInfos(String ids) {
		String[] idArray = ids.split(",");
		ContactInfo d= null;
		for (String sid : idArray) {
			Long id = Long.parseLong(sid);
			d = this.findContactInfo(id);
			d.setIfDel(1l);
			this.saveContactInfo(d);
			// delete ContactInfo
//			this.contactInfoDao.remove(id);
		}
	}

	public InputStream exportContactInfo(QueryObj queryObj) {
		try {
//			for (String key : queryObj.getQueryFields()) {
//				String value = new String(queryObj.getQueryValue(key).getBytes("ISO-8859-1"), "UTF-8");
//				queryObj.setQueryValue(key, value);
//			}
//			Collection<ContactInfo> data = contactInfoDao.findBy(queryObj);
//			TableDefine table = new TableDefine(getMessage("contactInfo"));
//			table.addColumn("id", getMessage("contactInfo.id"), 0);
//			table.addColumn("sysType", getMessage("contactInfo.sysType"), 0);
//			table.addColumn("userName", getMessage("contactInfo.userName"), 0);
//			table.addColumn("businessName", getMessage("contactInfo.businessName"), 0);
//			table.addColumn("email", getMessage("contactInfo.email"), 0);
//			table.addColumn("phone", getMessage("contactInfo.phone"), 0);
//			table.addColumn("mobile", getMessage("contactInfo.mobile"), 0);
//			table.addColumn("country", getMessage("contactInfo.country"), 0);
//			table.addColumn("contactType", getMessage("contactInfo.contactType"), 0);
//			table.addColumn("comment", getMessage("contactInfo.comment"), 0);
//			table.addColumn("ifDel", getMessage("contactInfo.ifDel"), 0);
//			table.addColumn("status", getMessage("contactInfo.status"), 0);
//			table.addColumn("createUser", getMessage("contactInfo.createUser"), 0);
//			table.addColumn("createDate", getMessage("contactInfo.createDate"), 0);
//			table.addColumn("modifyUser", getMessage("contactInfo.modifyUser"), 0);
//			table.addColumn("modifyDate", getMessage("contactInfo.modifyDate"), 0);
//			table.addColumn("remark", getMessage("contactInfo.remark"), 0);
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
