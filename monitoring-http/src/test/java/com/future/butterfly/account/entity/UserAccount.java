package com.future.butterfly.account.entity;

import java.util.Date;
import java.util.Map;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;

@Entity(value = "account", noClassnameStored = true)
public class UserAccount {

	@Id
	private String akid;
	private String passwd;

	@Indexed
	private String status;

	private Date createDate;

	@Embedded(concreteClass = java.util.HashMap.class)
	private Map<String, Object> extra;

	public UserAccount() {
		super();
	}

	public UserAccount(String akid) {
		this.akid = akid;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public Map<String, Object> getExtra() {
		return extra;
	}

	public String getAkid() {
		return akid;
	}

	public String getStatus() {
		return status;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setExtra(Map<String, Object> extra) {
		this.extra = extra;
	}

	public void setakid(String akid) {
		this.akid = akid;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getPasswd() {
		return passwd;
	}
}
