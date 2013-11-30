package org.esblink.module.basedata.biz;

import java.util.Collection;

import org.esblink.common.base.IPage;
import org.esblink.common.base.QueryObj;
import org.esblink.module.basedata.domain.Sysconfig;

public interface ISysconfigBiz {
	public void saveSysconfig(Sysconfig sysconfig);

	public Sysconfig findSysconfig(Long id);

	public Collection<Sysconfig> findBy(Sysconfig sysconfig);

	public IPage<Sysconfig> findPageBy(QueryObj queryObj);

	public void deleteSysconfigs(String ids);
}
