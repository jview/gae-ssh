package org.esblink.module.auth.exception;

import org.esblink.common.exception.WithoutPermissionException;


public class AuthorizeException extends WithoutPermissionException {

	private static final long serialVersionUID = 7862756769053963983L;

	public AuthorizeException() {
		super("error.cms.authorize");
	}

	public AuthorizeException(String key) {
		super(key);
	}

}
