package org.esblink.module.basedata.biz.impl;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.esblink.common.base.IPage;
import org.esblink.common.base.QueryObj;
import org.esblink.common.base.gae.BaseBIZ;
import org.esblink.common.util.BeanUtils;
import org.esblink.module.basedata.biz.IParameterBiz;
import org.esblink.module.basedata.biz.IParameterUserBiz;
import org.esblink.module.basedata.dao.IParameterUserDao;
import org.esblink.module.basedata.domain.Parameter;
import org.esblink.module.basedata.domain.ParameterUser;
import org.springframework.stereotype.Service;

/**
 * 
 */
@Service("parameterUserBiz")
public class ParameterUserBiz extends BaseBIZ implements IParameterUserBiz {

	// parameterUserDao
	@Resource(name="parameterUserDao")
	private IParameterUserDao parameterUserDao;
	@Resource(name="parameterBiz")
	private IParameterBiz parameterBiz;

	public void setParameterUserDao(IParameterUserDao parameterUserDao) {
		this.parameterUserDao = parameterUserDao;
	}

	public void saveParameterUser(ParameterUser parameterUser) {

		// save parameterUser
//		Long id=this.parameterUserDao.saveParameterUserValue(parameterUser);
	}

	public ParameterUser findParameterUser(Long id) {
		// load parameterUser
		ParameterUser parameterUser = this.parameterUserDao.findById(id);

		return parameterUser;
	}

	public Collection<ParameterUser> findBy(ParameterUser parameterUser) {
		if (parameterUser == null) {
			parameterUser = new ParameterUser();
		} else {
			BeanUtils.clearEmptyProperty(parameterUser);
		}
		return this.parameterUserDao.findBy(parameterUser);
	}
	
	public List<ParameterUser> findParameterUserbyUser(Long userId){
		Parameter search = new Parameter();
		search.setIfDel(0l);
		search.setStatus(1l);
		Collection<Parameter> pList=this.parameterBiz.findBy(search);
		
		List<ParameterUser> list = new ArrayList<ParameterUser>();
		
		ParameterUser searchPU =new ParameterUser();
		searchPU.setStatus(1l);
		Collection<ParameterUser> pUserList = this.findBy(searchPU);
		list.addAll(pUserList);
		
		ParameterUser pUserNew = null;
		boolean isExist=false;
		for(Parameter p:pList){
			isExist=false;
			for(ParameterUser pUser:pUserList){
				if(p.getId().longValue()==pUser.getParameterId().longValue()){
					isExist=true;
					break;
				}
			}
			if(!isExist){
				pUserNew = new ParameterUser(p);
				list.add(pUserNew);
			}
		}
		
		return list;
	}

	public IPage<ParameterUser> findPageBy(QueryObj queryObj) {
		Parameter search = new Parameter();
		search.setIfDel(0l);
		Collection<Parameter> pList=this.parameterBiz.findBy(search);
		
		IPage<ParameterUser> page= this.parameterUserDao.findPageBy(ParameterUser.class, queryObj);
		Collection<ParameterUser> pUserList = page.getData();
		List<ParameterUser> list = new ArrayList<ParameterUser>();
		ParameterUser pUserNew = null;
		boolean isExist=false;
		for(Parameter p:pList){
			isExist=false;
			for(ParameterUser pUser:pUserList){
				if(p.getId().longValue()==pUser.getParameterId().longValue()){
					isExist=true;
					break;
				}
			}
			if(!isExist){
				pUserNew = new ParameterUser(p);
				list.add(pUserNew);
			}
		}
		pUserList.addAll(list);
		
		return page;
	}

	public void deleteParameterUsers(String ids) {
		String[] idArray = ids.split(",");
		for (String sid : idArray) {
			Long id = Long.parseLong(sid);
			// delete ParameterUser
//			this.parameterUserDao.remove(id);
		}
	}
	
	/**
	 * 根据编码及userId查ParameterUser,如果用户为设置取默认值
	 * @param param_code
	 * @param userId
	 * @return
	 */
	public Long saveParameterUserValue(String param_code, Long userId, String value)throws Exception{
		Parameter parameter = new Parameter();
		parameter.setCode(param_code);
		parameter.setStatus(1l);
		Long v= this.saveParameterUserValue(parameter, userId, value);
		return v;
	}
	
	/**
	 * 根据param及userId查parameterUser
	 * @param param
	 * @param userId
	 * @return
	 */
	private Long saveParameterUserValue(Parameter param, Long userId, String value)throws Exception{
		Long id=null;
		ParameterUser parameterUser = this.findParameterUser(param, userId);
		if(parameterUser!=null){
			ParameterUser paramUserVO = new ParameterUser(parameterUser);
			paramUserVO.setValue(value);
//			id=this.parameterUserDao.saveParameterUserValue(paramUserVO);
//			ParameterUsers.refresh();
		}
		return id;
	}
	
	/**
	 * 根据param及userId查parameterUser
	 * 如果用户未设置取默认的parameter值，否则取用户设置的值
	 * @param param
	 * @param userId
	 * @return
	 */
	private ParameterUser findParameterUser(Parameter param, Long userId)  throws Exception{
		ParameterUser parameterUser = null;
		Parameter parameter =null;
		if(param.getId()!=null){
			parameter = this.parameterBiz.findParameter(param.getId());	
		}
		else{
			Collection<Parameter> paraList = this.parameterBiz.findBy(param);
			for(Parameter curParam:paraList){
				parameter = curParam;
				break;
			}
		}
		
		if(parameter==null){
			throw new Exception("Parameter not found!");
		}
				
		if(parameter!=null && parameter.getStatus().intValue()==1){
			ParameterUser search = new ParameterUser();
			search.setParameterId(parameter.getId());
			search.setUserId(userId);
			Collection<ParameterUser> paramUserList = this.findBy(search);
			for(ParameterUser curParamUser:paramUserList){
				parameterUser = curParamUser;
				break;
			}
			if(parameterUser!=null){
				parameterUser.setCode(parameter.getCode());
				parameterUser.setParameterId(parameter.getId());
				parameterUser.setName(parameter.getName());
				parameterUser.setDefaultValue(parameter.getValue());
				parameterUser.setStatus(parameter.getStatus());
			}
			else{
				parameterUser = new ParameterUser(parameter);
				parameterUser.setUserId(userId);
			}
		}
		

		return parameterUser;
	}

	public void setParameterBiz(IParameterBiz parameterBiz) {
		this.parameterBiz = parameterBiz;
	}
	
	
}
