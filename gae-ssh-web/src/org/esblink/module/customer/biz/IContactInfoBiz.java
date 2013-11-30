package org.esblink.module.customer.biz;

import java.io.InputStream;
import java.util.Collection;

import org.esblink.common.base.IPage;
import org.esblink.common.base.QueryObj;
import org.esblink.common.base.gae.IBaseBIZ;
import org.esblink.module.customer.domain.ContactInfo;

/**
 * <pre>
 * *********************************************
 * Copyright esblink.net.
 * All rights reserved.
 * Description: ContactInfo BIZ接口定义
 * HISTORY
 * *********************************************
 *  ID   DATE           PERSON          REASON
 *  1    2013.04.29     CodeGen         Create
 * 
 * *********************************************
 * </pre>
 */
public interface IContactInfoBiz extends IBaseBIZ {

	public void saveContactInfo(ContactInfo contactInfo);

	public ContactInfo findContactInfo(Long id);

	public Collection<ContactInfo> findBy(ContactInfo contactInfo);

	public IPage<ContactInfo> findPageBy(QueryObj queryObj);

	public void deleteContactInfos(String ids);

	public InputStream exportContactInfo(QueryObj queryObj);

}
