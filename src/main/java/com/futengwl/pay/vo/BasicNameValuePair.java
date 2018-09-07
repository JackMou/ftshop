package com.futengwl.pay.vo;



public class BasicNameValuePair {
	
	private String name;
	private String value;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public BasicNameValuePair(String name, String value) {
		this.name = name;
		this.value = value;
	}
	
	
}
