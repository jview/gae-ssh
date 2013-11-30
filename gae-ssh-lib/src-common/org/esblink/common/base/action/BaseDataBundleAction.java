package org.esblink.common.base.action;

import org.esblink.common.base.DataBundle;

public abstract class BaseDataBundleAction extends BaseAction implements
		IDataBundleAction {
	private static final long serialVersionUID = 4702170484488548396L;
	private DataBundle requestDataBundle;
	private DataBundle responseDataBundle;

	public DataBundle getRequestDataBundle() {
		return this.requestDataBundle;
	}

	public void setResponseDataBundle(DataBundle responseDataBundle) {
		this.responseDataBundle = responseDataBundle;
	}

	public void setRequestDataBundle(DataBundle requestDataBundle) {
		this.requestDataBundle = requestDataBundle;
	}

	public DataBundle getResponseDataBundle() {
		return this.responseDataBundle;
	}
}
