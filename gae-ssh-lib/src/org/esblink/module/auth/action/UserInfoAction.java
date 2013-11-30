package org.esblink.module.auth.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.esblink.common.base.QueryObj;
import org.esblink.common.base.QueryParam;
import org.esblink.common.base.action.BaseGridAction;
import org.esblink.module.auth.biz.IUserBiz;
import org.esblink.module.auth.domain.User;
import org.esblink.module.auth.domain.UserEmp;
import org.esblink.module.basedata.biz.ILogDbBiz;
import org.esblink.module.basedata.domain.LogDb;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.esblink.dev.util.CommMethod;

/**
 ************************************
 *与UserAction.java区别是，正常操作都在UserAction.java
 *这里只是提供一个findUserInfoByQuery方法，用于联想输入时选择用户处理
 * </pre>
 */
@SuppressWarnings("serial")
//@Controller("userInfoAction")
//@Scope("prototype")
public class UserInfoAction extends BaseGridAction<User> {
	private String moduleCode="user";
	private String modelCode = getClass().getSimpleName();
//	@Resource(name="logDbBiz")
	private ILogDbBiz logDbBiz;
	private LogDb logDb=new LogDb(moduleCode, modelCode);;
	public void setLogDbBiz(ILogDbBiz logDbBiz) {
		this.logDbBiz = logDbBiz;
	}
	
//	@Resource(name="userBiz")
	private IUserBiz userBiz;
	private User user;
	private String ids;
	private String msg;
	private String query;
	private String userIds;
	
	private Collection<User> users;
	private int total;
	private Collection<UserEmp> userEmpList;

	public String saveUser() {
//		try {
//			userBiz.saveUser(user);
//			this.logDbBiz.info(this.getCurrentUser(), logDb, "saveUser", "id="+user.getId()+" code="+user.getCode(),"",SUCCESS,user);
//		} catch (Exception e) {
//			msg = e.toString();
//			log.error("error", e);
//			this.logDbBiz.error(this.getCurrentUser(), logDb, "saveUser", "id="+user.getId()+" code="+user.getCode(),FAILURE+":"+e.getMessage(),FAILURE, user);
//		}
		return SUCCESS;
	}
	
	

	public String findUser() {
//		user = userBiz.findUser(user.getId());
		return SUCCESS;
	}

	public String findByUser() {
//		users = userBiz.findBy(user);
		return SUCCESS;
	}

	public String deleteUsers() {
//		try {
//			userBiz.deleteUsers(ids);
//			this.logDbBiz.info(this.getCurrentUser(), logDb, "deleteUsers", "ids="+ids,"delete id for:"+ids, SUCCESS, null);
//		} catch (Exception e) {
//			msg = e.toString();
//			log.error("error", e);
//			this.logDbBiz.error(this.getCurrentUser(), logDb, "deleteUsers", "ids="+ids,FAILURE+":"+e.getMessage(),FAILURE,null);
//		}
		return SUCCESS;
	}

	public String findPageByUser() {
		QueryObj queryObj = QueryObj.parseQueryObj(this);
		queryObj.setQueryObject("ifDel", 0l);
		
//		IPage<User> page = userBiz.findPageBy(queryObj);
//		users = page.getData();		
//		total = (int) page.getTotalSize();
		return SUCCESS;
	}
	
	/**
	 * 用于联想输入
	 * @return
	 */
	public String findUserInfoByQuery() {	
		String view = ServletActionContext.getRequest().getParameter("view");
		Map mapPara = new HashMap();
		mapPara.putAll(ServletActionContext.getRequest().getParameterMap());
		mapPara.put("limit", ""+1000);
		mapPara.put("start", "0");
		QueryObj queryObj = new QueryObj(mapPara);
		log.info("=======findUserInfoByQuery--userId="+queryObj.getQueryObject("userId"));
		
		
		List<UserEmp> queryList = userBiz.findByQuery(queryObj, query);
		
		List<UserEmp> userInfos = new ArrayList<UserEmp>();
		if(CommMethod.isEmpty(query) && view!=null&&view.equals("all")){
			UserEmp all = new UserEmp();
			all.setEmpName("全部");
			all.setEmpCode("All");
			userInfos.add(all);
		}
		
		this.total= queryList.size();
		if(userIds==null){
			int max = (this.getPageIndex() + 1) * this.getPageSize() > this.total ? this.total : (this.getPageIndex() + 1) * this.getPageSize();		
			for (int i = (this.getPageSize() * (this.getPageIndex())); i < max; i++) {			
				userInfos.add(queryList.get(i));
			}
		}else{  
			Integer count=0;
			for (int i = 0; i < queryList.size(); i++) {
				UserEmp userInfo=queryList.get(i);
				if(userInfo.getId().longValue()==Long.parseLong(userIds)){
					count=i;
				} 
			}
			//判断当前的值是第几页就加载第几页
			Integer countPageIndex=count /this.getPageSize(); 
			int max = (countPageIndex + 1) * this.getPageSize() > this.total ? this.total : (countPageIndex+ 1) * this.getPageSize();	
			for (int i = (this.getPageSize() * countPageIndex); i < max; i++) {			
				userInfos.add(queryList.get(i));
			} 
		}
		this.userEmpList=userInfos;
		return SUCCESS;
	}

	public boolean isSuccess() {
		return true;
	}

	public void setUserBiz(IUserBiz userBiz) {
		this.userBiz = userBiz;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getMsg() {
		return this.msg;
	}

	public Collection<User> getRows() {
		return this.users;
	}

	public int getTotal() {
		return this.total;
	}



	public void setQuery(String query) {
		this.query = query;
	}



	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}



	public Collection<UserEmp> getUserEmpList() {
		return userEmpList;
	}



	public void setUserEmpList(Collection<UserEmp> userEmpList) {
		this.userEmpList = userEmpList;
	}
	
	
}
