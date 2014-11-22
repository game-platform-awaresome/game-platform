package com.future.gameplatform.account.monitor;


import com.future.gameplatform.account.resource.UserOpenResource;

public class UserAccountJMX implements UserAccountJMXMBean {

	@Override
	public long getRegisterRequestCount() {
		return UserOpenResource.getRegisterCount(false);
	}

	@Override
	public long getAuthRequestCount() {
		return UserOpenResource.getAuthCount(false);
	}

	@Override
	public long resetRegisterRequestCount() {
		return UserOpenResource.getRegisterCount(true);
	}

	@Override
	public long resetAuthRequestCount() {
		return UserOpenResource.getAuthCount(true);
	}

	@Override
	public long getRegisterSucceedCount() {
		return UserOpenResource.getRegisterSucceedCount(false);
	}

	@Override
	public long getAuthSucceedCount() {
		return UserOpenResource.getAuthSucceedCount(false);
	}

	@Override
	public long resetRegisterSucceedCount() {
		return UserOpenResource.getRegisterSucceedCount(true);
	}

	@Override
	public long resetAuthSucceedCount() {
		return UserOpenResource.getAuthSucceedCount(true);
	}
}
