package com.zexi.bean;


/**
 * 返回消息，所有请求的返回消息统一一种
 * 
 */
public class ResponseMessage {
	// 请求的状态，默认为1，代表成功
	private int status;
	private String message;
	private Object data;

	public ResponseMessage() {
		status = 1;
		message = "success";
//		data = new JSONObject();
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
