package com.future.gameplatform.account.game.dao.impl;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.mongodb.Mongo;
import com.mongodb.MongoOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicDaoImpl {

	protected final Logger logger = LoggerFactory
	        .getLogger(this.getClass());

	protected final Morphia morphia;
	protected Mongo mongo;
	protected Datastore datastore;
	protected String mongoDomain;

	public BasicDaoImpl(String mongoDomain) {
		this.mongoDomain = mongoDomain;
		morphia = new Morphia();
		try {
			MongoOptions mongoOptions = new MongoOptions();
			mongoOptions.threadsAllowedToBlockForConnectionMultiplier = 120;
			mongoOptions.connectionsPerHost = 50;
			mongoOptions.autoConnectRetry = true;
			mongoOptions.socketKeepAlive = true;

			mongo = new Mongo(mongoDomain, mongoOptions);
			morphia.mapPackage("com.future.gameplatform.account.com.future.gameplatform.account.game.entity");
		} catch (Exception e) {
			logger.error("[BasicDao] init failed : " + e.getMessage());
		}
	}
}
