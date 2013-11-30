package org.esblink.module.loginmgmt.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.esblink.common.base.action.BaseAction;
import org.esblink.common.base.context.ApplicationContext;
import org.esblink.common.base.context.UserContext;
import org.esblink.common.base.domain.IModule;
import org.esblink.common.base.domain.IUser;
import org.esblink.common.base.security.ModuleContext;
import org.esblink.common.util.Encrypt;
import org.esblink.common.util.Monitor;
import org.esblink.common.util.ParaEncode;
import org.esblink.module.auth.util.ConfigUtil;
import org.esblink.module.framework.action.dto.DepartmentDto;
import org.esblink.module.framework.biz.DepartmentCacheBiz;

import com.esblink.dev.util.CommMethod;
import com.esblink.dev.util.Path;

/**
 * 菜单Action
 * 
 * 
 */
public class WebFrameAction extends BaseAction {

    private static final long serialVersionUID = 1L;

    private String helpUrlPrefix;

    private String homeUrl;

    private String style;

    private IUser user;

    private DepartmentDto department;

    public IModule app;

    public List<IModule> menus;

    public Long roleId;

    @Override
    public String execute() throws Exception {
        Monitor.beginAction();
        user = getCurrentUser();
        if (user != null) {
            department = DepartmentCacheBiz.convertToDto(DepartmentCacheBiz.getByCode(user.getDeptCode()));
            roleId = user.getRoleId();
        }

        String appName = ApplicationContext.getContext().getApplication().getName();
        log.info("-----webFrame--appName="+appName);;
        app = ModuleContext.getModuleByCode(appName);
        if (app != null && app.getUrl() != null && app.getUrl().endsWith(".action"))
            homeUrl = ".." + app.getUrl();
        else
            homeUrl = "#";
        menus = UserContext.getContext().getUserWebModule();
//        log.info("-----webFrame--appName="+appName+" menus size="+menus.size());
//        Collection<IModule> mList;
//        for(IModule m:menus){
//        	System.out.println(m.getName()+"---"+m.getParent());
//        	if(m.getChildren()!=null){
//        		mList = m.getChildren();
//        		 for(IModule mc:mList){
//        			 System.out.println(mc.getName()+"---"+mc.getParent());
//        		 }
//        	}
//        }
        helpUrlPrefix = ApplicationContext.getContext().getSystemConfig().getProperty("help");
        style = UserContext.getContext().getUserStyle();
        Monitor.endAction();
        return SUCCESS;
    }
    public boolean isLoginDomain(){
		return CommMethod.getBoolean(ConfigUtil.getConfigValue(ConfigUtil.config_key.SYS_LOGIN_DOMAIN));
	}

    public String getHomeUrl() {
        return homeUrl;
    }

    public String getHelpUrlPrefix() {
        return helpUrlPrefix;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public Long getRoleId() {
        return roleId;
    }

    public IUser getUser() {
        return user;
    }

    public DepartmentDto getDepartment() {
        return department;
    }

    public IModule getApp() {
        return app;
    }

    public List<IModule> getMenus() {
        return menus;
    }

    public long getSessionTimeout() {
        return ServletActionContext.getRequest().getSession().getMaxInactiveInterval() * 1000 + 1000;
    }
    
    /**
	 * 构建自定义String，通过HttpServletResponse发送给浏览器
	 * 可对数据进行压缩
	 * 可由转向后的页面进行输出
	 * @param json  
	 * @version 1.0 
	 */
	protected  void outJson(String xmlStr) {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		try {
			PrintWriter out = null;			
			out = response.getWriter();			
			out.write(xmlStr);
			out.flush();
			out.close();
		} catch (IOException e) {
//			e.printStackTrace();
		}
	}
	
	public Map<String, String> getVersion() {
		Map<String, String> version = new HashMap<String, String>();
		Properties props = ApplicationContext.getContext().getSystemConfig();
		Iterator itors = props.entrySet().iterator();
		Entry ent;
		String keys = null;
		while(itors.hasNext()){
			ent = (Entry)itors.next();
			keys = ""+ent.getKey();
			if(keys.startsWith("version.")){
				version.put(keys.substring("version.".length()), ""+ent.getValue());
			}
		}
		return version;
	}
	
	public String getChangeLog(){
		String webPath = Path.getWebPath();
		String changeFile = webPath+"/WEB-INF/changes.txt";
		File file = new File(changeFile);
		List<String> list;
		String infos = "";
		try {
			list = Path.readLineFile(file);			
			for(String line:list){
				infos+=(line+"<br/>");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			infos = e.getMessage();
		}
		
		return infos;
	}
	
    /**
	 * 检查操作的action是否用权限
	 * @author chenjh
	 * @return
	 */
	public String checkPermit(){
		String action = ServletActionContext.getRequest().getParameter("action");
		
//		boolean isPermit=SecurityUserHolder.checkPermit(action);
//		outJson("{userId:"+this.getCurrentUser().getId()+", action:"+action+", permit:"+isPermit+"}");
		return null;
	}
	
	/**
	 * 参数编码
	 * @return
	 */
	public String paraEncode(){
		log.info("======paraEncode==");
		String paras = ServletActionContext.getRequest().getParameter("paras");		
		String value = "{paraCode:'"+ParaEncode.encode(paras)+"'}";
		outJson(value); 
		return null;
	}
	
	/**
	 * 参数解码
	 * @return
	 */
	public String paraDecode(){
		log.info("======paraDecode==");
		String paras = ServletActionContext.getRequest().getParameter("paras");		
		String value = "{paraValue:'"+ParaEncode.decode(paras)+"'}";
		outJson(value); 
		return null;
	}
	
	/**
	 * 用户编码
	 * @return
	 */
	public String userEncodeStr(){
		Encrypt en = new Encrypt();
		String paras = null;
		//非域模式，不处理
		if(CommMethod.getBoolean(ConfigUtil.getConfigValue(ConfigUtil.config_key.SYS_LOGIN_DOMAIN))){
			paras=en.getUserEncode("sms", this.getCurrentUser().getUsername(), 10);
		}
		else{
			paras=en.getUserEncode("test", this.getCurrentUser().getUsername(), 10);;
		}
		String value = "{paraValue:'"+paras+"'}";
		outJson(value);
		return null;
	}
	
	/**
	 * 返回操作人信息
	 * @return
	 */
	public String findCurrentUserInfo(){
		log.info("======findCurrentUserInfo==");		
		String value = "{userId:"+this.getCurrentUser().getId()+", userName:'"+this.getCurrentUser().getUsername()+"', empName:'"+this.getCurrentUser().getEmpName()+"',userNo:'"+this.getCurrentUser().getEmpCode()+"'}";
		outJson(value); 
		return null;
	}
	
	/**
	 * 清除用户指定模块的缓存
	 * @return
	 */
	public String clearModuleCache(){
		log.info("======clearModuleCache==");
		String mmKey = ServletActionContext.getRequest().getParameter("mmKey");
		if(CommMethod.isEmpty(mmKey)){
			outJson("{success:false,paras:'Empty param for mmKey:"+mmKey+"!'}"); 
			return null;
		}
		ConfigUtil.clearCacheData(this.getCurrentUser().getId(), mmKey);
		outJson("{success:'true'}"); 
		return null;
	}
}
