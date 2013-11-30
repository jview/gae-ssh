package org.esblink.common.util;

import java.util.Calendar;
import java.util.Random;

public class Encrypt {
	public static final int HEX_START=432;
	private static final String KEY_DEFAULT="cs123456";
	private int keyValue = 100;
	private String keys="123";
	
	/**
	 * 十六进制转十进制
	 * @param hex
	 * @return
	 */
	private int hexToInteger(String hex){		
		return Integer.parseInt(hex, 16);
	}
	/**
	 * key转成数值
	 * @param key
	 * @return
	 */
	private int getKeyValue(String key){
		if(key==null){
			key = KEY_DEFAULT;
		}
		key = key.trim();
		byte[] bytes = key.getBytes();
		int bb = 0;
		for(byte b:bytes){
			bb = bb+b;
		}
		return this.getKeyLimit(bb+bytes.length);
	}
	
	/**
	 * 控制数值范围
	 * @param keyValue
	 * @return
	 */
	private int getKeyLimit(int keyVal){
		if(keyVal>3000){
			return keyVal/5;
		}
		else if(keyVal<0){
			return keyVal+100;
		}
		return keyVal;
	}
	
	public String encode(String str){
		String result=null;
		
		this.keyValue=this.getKeyValue(keys);
		int k_value = HEX_START+keyValue;
		
		int count_var=0;
		int var = 0;
		
		StringBuffer sb = new StringBuffer();
		
		for(byte b : str.getBytes()){
			count_var++;
			var = b+k_value+count_var;
			sb.append(Integer.toHexString(var));
		}
		result = sb.toString();
		return result;
	}
	
	public String decode(String str){
		String result=null;
		
		this.keyValue=this.getKeyValue(keys);
		
		int k_value = HEX_START+keyValue;
		
		int count_var=0;
		int var = 0;
		int b=0;
		StringBuffer sb = new StringBuffer();
		for(int j=0; j<str.length(); j=j+3){
			count_var++;
			b = this.hexToInteger(str.substring(j, j+3));
			var = b-k_value-count_var;
			sb.append((char)var);
		}
		result = sb.toString();
		return result;
	}
	
	
	
	public void setKeys(String keys) {
		this.keys = keys;
	}
	
	/**
	 * 获取时间信息
	 * @param type
	 * @return
	 */
	private String getDateInfo(int type){
		Calendar cal = Calendar.getInstance();
		String dateInfo = null;
		//日期，小时，分钟
		if(type<60){
			Integer minute = (cal.get(Calendar.MINUTE)/type);
			dateInfo=""+cal.get(Calendar.DATE)+":"+cal.get(Calendar.HOUR)+"-"+minute;
		}
		else{
			dateInfo=""+cal.get(Calendar.DATE)+":"+cal.get(Calendar.HOUR)+"-"+type;
		}
		return dateInfo;
	}
	
	/**
	 * 工号加密
	 * @param sysType
	 * @param workNo
	 * @param type
	 * @return
	 */
	public String getUserEncode(String sysType, String workNo, int type){
		this.setKeys(sysType+"-"+workNo);
		return this.encode(workNo+"_"+this.getDateInfo(type));
	}
	
	/**
	 * 工号解密
	 * @param sysType
	 * @param workNo
	 * @param value
	 * @param type
	 * @return
	 */
	public String getUserDecode(String sysType, String workNo, String value, int type){
		Random rnd = new Random();
		Float rnd_value = rnd.nextFloat()*10000;
		this.setKeys(sysType+"-"+workNo);
		value = this.decode(value)+"_"+rnd_value.intValue();
		if(value.startsWith(workNo+"_"+this.getDateInfo(type))){
			return workNo;
		}
		return null;
	}
	
	public static void main(String[] args)throws Exception {
		Encrypt en = new Encrypt();
//		en.setKeys("test");
//		String v = "sms123456";
		String workNo = "338444";
		String r = en.getUserEncode("sms", workNo, 10);
		String d = en.getUserDecode("sms", workNo, r, 10);
//		System.out.println(v);
		System.out.println(r);
		System.out.println(d);
//		System.out.println("value="+v+" encode"+r);
//		System.out.println(en.getMinute(30, 10));
//		System.out.println(en.getMinute(31, 10));
//		System.out.println(en.getMinute(32, 10));
//		System.out.println(en.getMinute(33, 10));
//		System.out.println(en.getMinute(35, 10));
//		System.out.println(en.getMinute(36, 10));
//		System.out.println(en.getMinute(37, 10));
		
//		String zipR = ZipUtil.compress(r);
		
//		System.out.println("v="+v+" r="+r+" zipR="+zipR);
		
//		System.out.println("decode="+en.decode(r));
		
		
	}
}
