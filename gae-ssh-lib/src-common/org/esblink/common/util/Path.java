package org.esblink.common.util;

public class Path extends cn.kpifa.paras.util.Path {
	public static String getSuffix(String fileName){
		String suffix=null;
		int len= fileName.lastIndexOf(".");
		if(len>0){
			suffix = fileName.substring(len+1);
		}
		return suffix;
	}
	
//	public String getFileName(String path){
//		path = path.replaceAll("\\\\", "/");
//		String fileName = null;
//		int len = path.lastIndexOf("/");
//		if(len>0){
//			fileName = path.substring(len);
//		}
//		else{
//			fileName = path;
//		}
//		return fileName;
//	}

}
