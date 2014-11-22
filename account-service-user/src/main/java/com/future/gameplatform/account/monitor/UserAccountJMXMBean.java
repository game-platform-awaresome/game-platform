package com.future.gameplatform.account.monitor;

public interface UserAccountJMXMBean {
	long getRegisterRequestCount();

	long getAuthRequestCount();

	long resetRegisterRequestCount();

	long resetAuthRequestCount();

	long getRegisterSucceedCount();

	long getAuthSucceedCount();

	long resetRegisterSucceedCount();

	long resetAuthSucceedCount();
}
