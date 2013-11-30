package org.esblink.module.loginmgmt.actionLoginFilter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class LoginSessionContainer {

	private static Map<Long, SessionDatePaire> map;

	static {
		map = new ConcurrentHashMap<Long, SessionDatePaire>();
	}

	private LoginSessionContainer() {
	}

	private static void put(long userId, String sessionId, long lastRequestTm) {
		SessionDatePaire sdp = new SessionDatePaire(sessionId, lastRequestTm);
		map.put(userId, sdp);
	}
	
	public static void login(long userId, String sessionId){
		put(userId, sessionId, System.currentTimeMillis());
	}

	public static boolean checkUserSession(Long userId, String sessionId) {
		// 如果用户ID为空，则目前尚未登录，所以不做登录校验，直接通过。(structs2会对未登录的action做校验)
		if (userId == null)
			return true;
		// 如果sessionId为空，则是非法的，正常的请求不会为空，所以不通过。
		if (sessionId == null)
			return false;
		SessionDatePaire sdp = map.get(userId);
		if (sdp == null) {
			// 如果map中没有这个用户的数据，则说明本次之后也最多有一个人用这个ID登录，所以加入这个数据，同时通过校验。
			put(userId, sessionId, System.currentTimeMillis());
			return true;
		} else if (sessionId.equals(sdp.getSessionId())) {
			// 如果sessionId与map中的数据一样，则说明不是另一个登录用户，所以通过校验。
			sdp.setLastRequestTm(System.currentTimeMillis());
			return true;
		}
		return false;
	}

	static Map<Long, SessionDatePaire> getMap() {
		return map;
	}

}
