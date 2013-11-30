/**
 * TODO
 */
package org.esblink.common.util;

import java.util.*;
/**
 * 告警消息容器
 * @author chenjh
 *
 */
public class AlertMessage {

	private List<String> messages;
	
	/**
	 * @return the messages
	 */
	public List<String> getMessages() {
		if(this.messages==null){
			messages = new LinkedList<String>();
		}
		return messages;
	}
	
	public void addMessage(String message){		
//		log4.info("message="+message+" messages size="+messages.size());
		this.getMessages();
		
		messages.add(message);			
	}
	
//	public void addMessage(int errorCode){		
//		String msg = ErrorCode.getErrorMessage(errorCode);
//		this.addMessage(msg);		
//	}
//	
//	public void addMessage(ErrorCode.errors errorCode){		
//		String msg = ErrorCode.getErrorMessage(errorCode.getCode());
//		this.addMessage(msg);		
//	}

	public void clear(){
		
//		log4.info("--clear--");
		this.messages=null;
	}
	
	public void print(){
		if(this.messages!=null)
		for(String str:this.messages){
			System.out.println(str);
		}
	}
	
	
}
