package com.future.gameplatform.warehouse.dao.impl;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.mongodb.Mongo;
import com.mongodb.MongoOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicDaoImpl {

	private final static Logger logger = LoggerFactory
	        .getLogger(BasicDaoImpl.class);

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
            morphia.mapPackage("com.future.gameplateform.warehouse.entity");
		} catch (Exception e) {
			logger.error("[AppBasicDao] init failed : " + e.getMessage());
		}
	}
}
