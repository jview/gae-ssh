package org.esblink.module.framework.basecache;

import net.sf.ehcache.store.MemoryStoreEvictionPolicy;

/**
 * <pre>
 * *********************************************
 * Copyright esblink.net.
 * All rights reserved. 
 * Description: 缺省的cache参数配置
 * </pre>
 */
public class DefaultConfig {

	private static DefaultConfig instance;

	private int maxElementsInMemory;
	private MemoryStoreEvictionPolicy memoryStoreEvictionPolicy;
	private boolean overflowToDisk;
	private String diskStorePath;
	private boolean eternal;
	private long timeToLiveSeconds;
	private long timeToIdleSeconds;
	private boolean diskPersistent;
	private long diskExpiryThreadIntervalSeconds;

	public DefaultConfig() {
		super();
		instance = this;
	}

	public static DefaultConfig getInstance() {
		return instance;
	}

	public int getMaxElementsInMemory() {
		return maxElementsInMemory;
	}

	public void setMaxElementsInMemory(int maxElementsInMemory) {
		this.maxElementsInMemory = maxElementsInMemory;
	}

	public MemoryStoreEvictionPolicy getMemoryStoreEvictionPolicy() {
		return memoryStoreEvictionPolicy;
	}

	public void setMemoryStoreEvictionPolicy(MemoryStoreEvictionPolicy memoryStoreEvictionPolicy) {
		this.memoryStoreEvictionPolicy = memoryStoreEvictionPolicy;
	}

	public boolean isOverflowToDisk() {
		return overflowToDisk;
	}

	public void setOverflowToDisk(boolean overflowToDisk) {
		this.overflowToDisk = overflowToDisk;
	}

	public String getDiskStorePath() {
		return diskStorePath;
	}

	public void setDiskStorePath(String diskStorePath) {
		this.diskStorePath = diskStorePath;
	}

	public boolean isEternal() {
		return eternal;
	}

	public void setEternal(boolean eternal) {
		this.eternal = eternal;
	}

	public long getTimeToLiveSeconds() {
		return timeToLiveSeconds;
	}

	public void setTimeToLiveSeconds(long timeToLiveSeconds) {
		this.timeToLiveSeconds = timeToLiveSeconds;
	}

	public long getTimeToIdleSeconds() {
		return timeToIdleSeconds;
	}

	public void setTimeToIdleSeconds(long timeToIdleSeconds) {
		this.timeToIdleSeconds = timeToIdleSeconds;
	}

	public boolean isDiskPersistent() {
		return diskPersistent;
	}

	public void setDiskPersistent(boolean diskPersistent) {
		this.diskPersistent = diskPersistent;
	}

	public long getDiskExpiryThreadIntervalSeconds() {
		return diskExpiryThreadIntervalSeconds;
	}

	public void setDiskExpiryThreadIntervalSeconds(long diskExpiryThreadIntervalSeconds) {
		this.diskExpiryThreadIntervalSeconds = diskExpiryThreadIntervalSeconds;
	}
}
