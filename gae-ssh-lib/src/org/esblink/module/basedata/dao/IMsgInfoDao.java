package org.esblink.module.basedata.dao;

import java.util.List;

import org.esblink.common.base.gae.IBaseDAO;
import org.esblink.module.basedata.domain.MsgInfo;

//@Service("msgInfoDao")
public interface IMsgInfoDao extends  IBaseDAO<MsgInfo> {
	public void save(List<MsgInfo> entitys) throws Exception;

//	public List<MsgInfo> findAll();

	public MsgInfo findById(Long id);

	public boolean deleteById(Long id);

}
