package io.github.cmaggiulli.sms.util;

import java.io.File;

import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

public class Properties {
	private static final Logger LOG = (Logger) LogManager.getLogger(Properties.class);
	
	private static final Configurations configurations;
	private static PropertiesConfiguration propertiesConfiguration;
	
	static {
		configurations = new Configurations();
		
		try {
			propertiesConfiguration = configurations.properties(new File("configuration.properties"));
		} catch (ConfigurationException e) {
			LOG.error("Cannot read from configuration.properties file");
			System.exit(0);
		}
	}
	public static String test = "";
	public static final String getRabbitUser() {
		return propertiesConfiguration.getString("mq.user");
	}
	
	public static final String getRabbitPassword() {
		return propertiesConfiguration.getString("mq.pass");
	}
	
	public static final String getRabbitHost() {
		return propertiesConfiguration.getString("mq.host");
	}
	
	public static final String getTwoSMSUser() {
		return propertiesConfiguration.getString("sms.user");
	}
	
	public static final String getTwoSMSPassword() {
		return propertiesConfiguration.getString("sms.pass");
	}
	
}
