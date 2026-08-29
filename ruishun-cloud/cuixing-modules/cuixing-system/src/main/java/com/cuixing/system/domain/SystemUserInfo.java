package com.cuixing.system.domain;

import java.util.List;
import java.util.Map;

public class SystemUserInfo {

	private String userName;
	private String nickName;
	private List<String> depts;
	private Map<Long,String> roles;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public List<String> getDepts() {
		return depts;
	}

	public void setDepts(List<String> depts) {
		this.depts = depts;
	}

	public Map<Long, String> getRoles() {
		return roles;
	}

	public void setRoles(Map<Long, String> roles) {
		this.roles = roles;
	}

}
