package org.esblink.module.loginmgmt.actionLoginFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimerTask;

public class RemoveCacheTask extends TimerTask {

	private long lifeTm;

	public void setLifeTm(long lifeTm) {
		this.lifeTm = lifeTm;
	}

	@Override
	public synchronized void run() {
		if (lifeTm > 0) {
			Map<Long, SessionDatePaire> map = LoginSessionContainer.getMap();
			List<Long> toRemove = new ArrayList<Long>();
			long currentTimeMillis = System.currentTimeMillis();
			for (Entry<Long, SessionDatePaire> e : map.entrySet()) {
				SessionDatePaire sdp = e.getValue();
				if (currentTimeMillis - sdp.getLastRequestTm() >= lifeTm)
					toRemove.add(e.getKey());
			}
			for (Long userId : toRemove)
				map.remove(userId);
		}
	}

}
