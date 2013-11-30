package org.esblink.module.basedata.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.esblink.common.util.AutoTool;
import org.esblink.common.util.CommonComparator;
import org.esblink.module.basedata.dao.IMsgInfoDao;
import org.esblink.module.basedata.domain.MsgInfo;
import org.esblink.module.basedata.domain.MsgInfoVO;

import com.esblink.dev.support.SpringDAOUtil;
import com.esblink.dev.util.CommMethod;

public class MsgInfos {
	public final static String DEFAULT_LANG="CN";
	public final static String DEFAULT_SYS_TYPE="ESB";
	//这里的name仅供参考，以数据库中的内容为主
	public static enum msgs_ios{
		ERR_DATA_INPUT            ("ERR_DATA_INPUT", "输入数据有误"),
		ERR_DATA_INVALID_ID       ("ERR_DATA_INVALID_ID", "无数数据id"),
		ERR_DATA_REPEAT           ("ERR_DATA_REPEAT", "数据重复"),
		ERR_DATA_UNEXISTED        ("ERR_DATA_UNEXISTED", "数据不存在"),
		ERR_DATA_EXISTED          ("ERR_DATA_EXISTED", "数据已经存在"),
		ERR_DATA_NOT_FOUND          ("ERR_DATA_NOT_FOUND", "数据未找到"),
		ERR_DATA_MAX_LENGTH          ("ERR_DATA_MAX_LENGTH", "超出数据最大长度"),
		ERR_DELETE_ERROR          ("ERR_DELETE_ERROR", "删除数据失败,请先删除其相关的数据"),
		ERR_LOGIN_INVALID_ESB ("ERR_LOGIN_INVALID_ESB", "ESB帐户或密码无效"),
		ERR_LOGIN_INVALID_ACCOUNT ("ERR_LOGIN_INVALID_ACCOUNT", "帐号不存在"),
		ERR_LOGIN_INVALID_PASSWORD("ERR_LOGIN_INVALID_PASSWORD", "密码错误"),
		ERR_PERMISSION_DENIED     ("ERR_PERMISSION_DENIED", "权限不足"),
		ERR_SERVER_CONNECTED      ("ERR_SERVER_CONNECTED", "系统连接失败，请联系管理员"),
		ERR_UNKNOWN                ("ERR_UNKNOWN", "未知错误"),
		SUCC_SUCCESSFUL           ("SUCC_SUCCESSFUL", "操作成功");
		private String code;
		private String name;
		
		msgs_ios(String code, String name){
			this.code=code;
			this.name=name;
		}
		public String getCode(){
			return this.code;
		}
		public String getName(){
			return this.name;
		}
		public static String getName(String code){
			msgs_ios[] list=msgs_ios.values();
			for(msgs_ios msg:list){
				if(msg.code.equals(code)){
					return msg.name;
				}
			}
			return null;
		}
	}
	
	private static final Log log = LogFactory.getLog(MsgInfos.class);
//	private static IMsgInfoDao msgInfoDao;
	private static HashMap<String, List> msgInfoListMap = null;
	public static void refresh(String sysType){
		log.info("============refresh()============");
		MsgInfo search = new MsgInfo();
		search.setStatus(1l);
		search.setIfDel(0l);
		if(sysType!=null)
			search.setSysType(sysType);
		String beanName="msgInfoDao";
		IMsgInfoDao msgInfoDao=(IMsgInfoDao)SpringDAOUtil.getBean(beanName);
		if(msgInfoDao==null){
			msgInfoListMap = new HashMap<String, List>();
			log.error("beans.xml not found beanName:"+beanName);
			return;
		}
		java.util.Collection<MsgInfo>pl= msgInfoDao.findBy(search);
		List<MsgInfo> plist=new ArrayList<MsgInfo>();
		plist.addAll(pl);
		
		CommonComparator comparator = new CommonComparator();   
		comparator.setFields(new String[]{ "sysType", "orderCount"});   
		Collections.sort(plist, comparator);
		if(msgInfoListMap==null){
			msgInfoListMap = new HashMap<String, List>();
		}
		MsgInfoVO vo = null;
		List list = new ArrayList();
		AutoTool at = new AutoTool();
		String pCode="";
		for(MsgInfo p: plist){
			vo = new MsgInfoVO(p);
			at.addMapCount(p.getSysType());
			if(at.getMapCount(p.getSysType())==1){
				if(list.size()>0){
					msgInfoListMap.put(pCode, list);
				}
				pCode=p.getSysType();
				list = new ArrayList();
			}
			list.add(vo);
//			msgInfoMap.put(vo.getCodeType()+"__"+vo.getDataId(), vo);
			//log4.info("key:"+p.getKey()+",value:"+ p.getValue());
		}
		msgInfoListMap.put(pCode, list);
	}
	


	public static MsgInfoVO getMsgInfo(String sysType, String code){
		if(msgInfoListMap==null){			
			refresh(null);			
		}
		if(msgInfoListMap==null)
			return null;
		List<MsgInfoVO> list= msgInfoListMap.get(sysType);
		MsgInfoVO msgInfo = null;
		if(list!=null){
			for(MsgInfoVO ct:list){
				if(ct.getCode().equals(code)){
					msgInfo=ct;
					break;
				}
			}
		}
		if(msgInfo==null){
			list= msgInfoListMap.get("ALL");
			for(MsgInfoVO ct:list){
				if(ct.getCode().equals(code)){
					msgInfo=ct;
					break;
				}
			}
		}
		return msgInfo;
	}
	
	public static List<MsgInfoVO> getMsgInfosByType(String sysType){
		if(msgInfoListMap==null){
			refresh(sysType);
		}
		if(msgInfoListMap==null)
			return null;
		log.info("------getMsgInfosByType--"+sysType);
		return msgInfoListMap.get(sysType);
	}
	
	/**
	 * 如果value为空则取valueDefault
	 */
	public static String getNameCn(String codeType, String code){
		MsgInfoVO p = getMsgInfo(codeType, code);
		if(p==null){
			return null;
		}
		else{
			String value = p.getNameCn();
			return value;
		}
	}
	
	/**
	 * 如果value为空则取valueDefault
	 */
	public static String getName(String sysType, String lang, String code){
		if(CommMethod.isEmpty(lang)){
			lang=DEFAULT_LANG;
		}
		if(CommMethod.isEmpty(sysType)){
			sysType=DEFAULT_SYS_TYPE;
		}
			
		MsgInfoVO p = getMsgInfo(sysType, code);
		if(p==null){
			return msgs_ios.getName(code);
		}
		else{
			String value = null;
			if(lang.equalsIgnoreCase("cn")){
				value = p.getNameCn();
			}
			else if(lang.equalsIgnoreCase("en")){
				value = p.getNameEn();
			}
			else if(lang.equalsIgnoreCase("tw")){
				value = p.getNameTw();
			}
			if(value==null){
				value = msgs_ios.getName(code);
			}
			return value;
		}
	}
	
	


	public static HashMap<String, List> getMsgInfoListMap() {
		return msgInfoListMap;
	}



}
