package org.esblink.module.customer.dao;

import java.util.Collection;

import org.esblink.common.base.IPage;
import org.esblink.common.base.QueryObj;
import org.esblink.common.base.gae.IBaseDAO;
import org.esblink.module.customer.domain.ContactInfo;

/**
 * <pre>
 * *********************************************
 * Copyright esblink.net.
 * All rights reserved.
 * Description: ContactInfo DAO接口定义
 * HISTORY
 * *********************************************
 *  ID   DATE           PERSON          REASON
 *  1    2013.04.29     CodeGen         Create
 * 
 * *********************************************
 * </pre>
 */
public interface IContactInfoDao extends IBaseDAO<ContactInfo> {

	public Collection<ContactInfo> findBy(QueryObj queryObj);

	public IPage<ContactInfo> findPageBy(QueryObj queryObj);

}
