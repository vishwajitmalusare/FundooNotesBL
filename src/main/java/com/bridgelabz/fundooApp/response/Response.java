package com.bridgelabz.fundooApp.response;

public class Response {

	private int statuscode;
	private String statusMessage;
	private Object data;

	public Response() {}

	public Response(int statuscode, String statusMessage, Object data) {
		
		this.statuscode = statuscode;
		this.statusMessage = statusMessage;
		this.data = data;
	}

	public int getStatuscode() {
		return statuscode;
	}

	public void setStatuscode(int statuscode) {
		this.statuscode = statuscode;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public Object getData() {
		return data;
	}
	
	public void setData(Object data) {
		this.data = data;
	}
	
	
}