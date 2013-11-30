package org.esblink.module.basedata.dao;

import java.util.List;

import org.esblink.common.base.gae.IBaseDAO;
import org.esblink.module.basedata.domain.LinkHistory;
import org.esblink.module.basedata.domain.LinkHistoryCount;

//@Service("linkHistoryDao")
public interface ILinkHistoryDao extends IBaseDAO<LinkHistory> {
	public void save(List<LinkHistory> entitys) throws Exception;

//	public List<LinkHistory> findAll();

	public LinkHistory findById(Long id);

	public boolean deleteById(Long id);
	/**
	 * 统计访问量
	 * @param moduleId
	 * @return
	 */
	public List<LinkHistoryCount> calVisitByModule(final Long moduleId);
	
	/**
	 * 根据用户统计各模块访问量
	 * @param userId,为空表示统计所有用户
	 * @return
	 */
	public List<LinkHistoryCount> calVisitByUser(final Long userId);
}
