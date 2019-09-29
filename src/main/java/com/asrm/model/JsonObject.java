package com.asrm.model;

import java.util.List;

public class JsonObject {
	public String newempid;
	public List<Emp> emps;
	public String getNewempid() {
		return newempid;
	}
	public void setNewempid(String newempid) {
		this.newempid = newempid;
	}
	public List<Emp> getEmps() {
		return emps;
	}
	public void setEmps(List<Emp> emps) {
		this.emps = emps;
	}
}
