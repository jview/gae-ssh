package org.esblink.module.framework.cache;

import org.esblink.common.base.cache.ICacheDataProvider;
import org.esblink.module.auth.dao.IUserDao;
import org.esblink.module.auth.domain.User;
import org.esblink.module.framework.biz.EmployeeCacheBiz;
import org.esblink.module.framework.domain.FlyweightCacheUser;
import org.esblink.module.organization.domain.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlyweightUserCacheProvider implements ICacheDataProvider {

	private static Logger logger = LoggerFactory
			.getLogger(FlyweightUserCacheProvider.class);

	private IUserDao userDao;

	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	public Object getData(Object key) {
		if (key == null) {
			logger.debug("Can not get User from FlyweightUserCacheProvider without assign a userId");
			return null;
		}

		Long userId = (Long) key;
		User user = userDao.findById(userId);
		if (null == user)
			throw new RuntimeException(
					"Can not find user["
							+ key.toString()
							+ "], if this record exists in database, please check if there an employee exists with its emp_id.");
		Employee e = EmployeeCacheBiz.getEmployee(user.getEmpCode());
		if (e == null || e.getValidFlg()==null || e.getValidFlg().longValue()==0)//因为EMPLOYEE表中字段名为对象，修改为属性后，更改。
		{
			logger.info("User validFlg is not valid on employee!");
			return null;
		}
		FlyweightCacheUser cu = new FlyweightCacheUser();
		cu.setId(user.getId());
		cu.setStatus(user.getStatus());
		cu.setUsername(user.getUsername());
		cu.setDeptCode(user.getDeptCode());
		cu.setEmpCode(user.getEmpCode());
		cu.setEmpEmail(e.getEmpEmail());
		cu.setEmpName(e.getName());
		return cu;
	}

}
