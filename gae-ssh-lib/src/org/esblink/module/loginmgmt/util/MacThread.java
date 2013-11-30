/**
 * TODO
 */
package org.esblink.module.loginmgmt.util;

import org.apache.log4j.Logger;
import org.esblink.module.basedata.biz.ILogDbBiz;
import org.esblink.module.basedata.domain.LogDb;
import org.esblink.module.framework.domain.CacheUser;
import org.esblink.module.framework.domain.FlyweightCacheUser;

import com.esblink.dev.support.SpringDAOUtil;



/**
 * 通过udp取主机名或mac地址
 * @author chenjh
 * 2011-12-15
 */
public class MacThread implements Runnable{
	private Logger log = Logger.getLogger(MacThread.class);
	private String moduleCode="login";
	private String modelCode = getClass().getSimpleName();
	
	private String login=null;
	private String username=null;
	private String ipAddress;	
	private UdpGetClientMacAddr udpMac;

	public MacThread(String login, String username, String ip){
		this.login = login;
		this.username=username;
		this.ipAddress=ip;
	}
	public MacThread(){
		
	}

	public void run(){
		if(login==null){
			return;
		}
		try {
			udpMac = new UdpGetClientMacAddr(this.ipAddress);
			String macs = udpMac.getRemoteMacAddr();
			String hostName = udpMac.getHostName();
			log.info("------run getMacip="+this.ipAddress+" macs="+macs);
			String beanName="logDbBiz";
			ILogDbBiz logDbBiz=(ILogDbBiz)SpringDAOUtil.getBean(beanName);
			if(logDbBiz==null){
				log.error("beans.xml not found beanName:"+beanName);
				return;
			}
			if(logDbBiz!=null){				
				CacheUser user = new CacheUser();
				FlyweightCacheUser flyUser = new FlyweightCacheUser();
				flyUser.setEmpCode(login);
				flyUser.setUsername(login);
				flyUser.setEmpName(username);
				user.setCu(flyUser);
				LogDb logDb = new LogDb(moduleCode, modelCode);
				logDbBiz.info(user, logDb, "getMac", "username="+this.login, "ips:"+this.ipAddress+" macs:"+macs+" hostName="+hostName,"info", null);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e.getMessage());
		}
		
	}
	
//	private static  ILogDbBiz logDbBiz;
//
//
//	public void setLogDbBiz(ILogDbBiz logDbBiz) {
//		this.logDbBiz = logDbBiz;
//	}

}
