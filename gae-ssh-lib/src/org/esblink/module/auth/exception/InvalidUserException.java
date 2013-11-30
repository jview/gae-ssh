package org.esblink.module.auth.exception;

public class InvalidUserException extends AuthorizeException {
	/**
	 *
	 */
	private static final long serialVersionUID = 1087398560990405530L;

	private static String InvalidUserKey = "Authorize.invalid.user";

	public InvalidUserException() {
		super(InvalidUserKey);
	}
}
