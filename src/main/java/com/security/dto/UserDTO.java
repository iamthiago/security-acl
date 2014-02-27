package com.security.dto;

import java.io.Serializable;

/**
 * @author Thiago
 *
 */

public class UserDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6281393951918291479L;

	private String username;
	private String password;
	private boolean enabled;
	private long[] menus;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public long[] getMenus() {
		return menus;
	}
	public void setMenus(long[] menus) {
		this.menus = menus;
	}
}
