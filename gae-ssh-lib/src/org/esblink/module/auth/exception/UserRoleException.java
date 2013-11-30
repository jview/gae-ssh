package org.esblink.module.auth.exception;

public class UserRoleException extends AuthorizeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2122255412662371851L;
	
	private static final String userRoleExceptionMsg = "Authorize.illegal.userRole";
	
	public UserRoleException(){
		super(userRoleExceptionMsg);
	}

}
