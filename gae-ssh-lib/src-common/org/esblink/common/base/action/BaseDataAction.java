package org.esblink.common.base.action;

public abstract class BaseDataAction extends BaseAction implements IDataAction {
	private static final long serialVersionUID = -4811944954396494625L;
	private Object requestData;
	private Object responseData;

	public Object getRequestData() {
		return this.requestData;
	}

	public void setResponseData(Object responseData) {
		this.responseData = responseData;
	}

	public void setRequestData(Object requestData) {
		this.requestData = requestData;
	}

	public Object getResponseData() {
		return this.responseData;
	}
}