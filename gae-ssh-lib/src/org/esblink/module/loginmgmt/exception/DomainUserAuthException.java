package org.esblink.module.loginmgmt.exception;

import org.esblink.module.auth.exception.AuthorizeException;

public class DomainUserAuthException extends AuthorizeException {
	/**
	 *
	 */
	private static final long serialVersionUID = 1087398560990405530L;

	private static String InvalidDomainUserKey = "Authorize.invalid.domainUser";

	public DomainUserAuthException() {
		super(InvalidDomainUserKey);
	}
}
