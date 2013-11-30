package org.esblink.module.loginmgmt.actionLoginFilter;

final class SessionDatePaire {

	private String sessionId;
	private long lastRequestTm;

	SessionDatePaire(String sessionId, long lastRequestTm) {
		this.sessionId = sessionId;
		this.lastRequestTm = lastRequestTm;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public long getLastRequestTm() {
		return lastRequestTm;
	}

	public void setLastRequestTm(long lastRequestTm) {
		this.lastRequestTm = lastRequestTm;
	}

}
