package io.github.cmaggiulli.sms;

import io.github.cmaggiulli.sms.template.TemplateEngine;
import io.github.cmaggiulli.sms.two.TwoSMSClient;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.apache.velocity.app.Velocity;

import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;

/**
 * 
 * @author CMaggi1
 */
public class ShortMessageServiceConsumer extends DefaultConsumer {
	private static final Logger LOG = (Logger) LogManager.getLogger(ShortMessageServiceConsumer.class);

	public ShortMessageServiceConsumer(Channel channel) {
		super(channel);
	}

	@Override
	public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body) throws IOException {

		try {
			TwoSMSClient client = TwoSMSClient.getInstance();
			client.send(TemplateEngine.getTemplate(new String(body)));
		
			getChannel().basicAck(envelope.getDeliveryTag(), true);
		} catch(Exception e) {
			getChannel().basicAck(envelope.getDeliveryTag(), false);
		}
	}
}
