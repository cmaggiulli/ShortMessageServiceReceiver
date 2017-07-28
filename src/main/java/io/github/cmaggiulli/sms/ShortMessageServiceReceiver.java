package io.github.cmaggiulli.sms;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import io.github.cmaggiulli.sms.util.Properties;

/**
 * 
 * @author CMaggi1
 *
 */
public class ShortMessageServiceReceiver {
	private static final Logger LOG = (Logger) LogManager.getLogger(ShortMessageServiceReceiver.class);
	private static final String QUEUE = "ShortMessageService";

	/**
	 * 
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		Channel channel = ShortMessageServiceReceiver.connection().createChannel();
		channel.queueDeclare(QUEUE, true, false, false, null);

		Velocity.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		Velocity.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		Velocity.init();
		
		channel.basicConsume(QUEUE, false, new ShortMessageServiceConsumer(channel));
	}

	/**
	 * 
	 * @return
	 * @throws IOException
	 * @throws TimeoutException
	 * @throws ConfigurationException
	 */
	public static Connection connection() throws IOException, TimeoutException, ConfigurationException {		
		final ConnectionFactory factory = new ConnectionFactory();
		
		factory.setUsername(Properties.getRabbitUser());
		factory.setPassword(Properties.getRabbitPassword());
		factory.setHost(Properties.getRabbitHost());

		return factory.newConnection();
	}
}
