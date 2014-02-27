package com.security.form;

import java.io.Serializable;

/**
 * @author Thiago
 *
 */
public class ResultMessage implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3061717715994345738L;
	
	private String key;
	private String value;
	
	public ResultMessage(){}
	
	public ResultMessage(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
