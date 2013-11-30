package org.esblink.common.util;

import com.esblink.dev.util.CommMethod;

/**
 * 对参数进行编码，
 * @author chenjh
 * 2012-3-5
 */
public class ParaEncode {
	private static final int keyValue = 100;
	public static final int HEX_START=432;
	/**
	 * 编码
	 * @param str
	 * @return
	 */
	public static String encode(String str){
		if(str==null){
			return null;
		}
		if(str.length()>1000){
			return "Out of max length:1000";
		}
		byte[] bytes = str.getBytes();
		String lengthStr = CommMethod.formatNumber(bytes.length, "0000");
		int var = 0;
		int k_value = HEX_START+keyValue;
		int count_var=0;		
		StringBuffer sb = new StringBuffer();
		for (byte b : bytes) {
			count_var++;
			var = b+k_value+count_var;			
			sb.append(Integer.toHexString(var));
		}
		return lengthStr+sb.toString();
	}
	/**
	 * 解码
	 * @param str
	 * @return
	 */
	public static String decode(String str){
		if(str==null){
			return null;
		}
		String lengthStr = str.substring(0, 4);
		str = str.substring(4);
		int count_var=0;	
		int var = 0;
		int value;
		int k_value = HEX_START+keyValue;
		byte[] bytes = new byte[Integer.parseInt(lengthStr)];
		int k=0;
		
		for(int j=0; j<str.length(); j=j+3){
			count_var++;
			value = hexToInteger(str.substring(j, j+3));		
			var = value-k_value-count_var;
			bytes[k]=(byte) var;
			k++;
		}
		String decode=null;
		try {
			decode = new String(bytes);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return decode;
	}	
	/**
	 * 十六进制转十进制
	 * @param hex
	 * @return
	 */
	private static int hexToInteger(String hex){		
		return  Integer.parseInt(hex, 16);
	}
	
	
}
