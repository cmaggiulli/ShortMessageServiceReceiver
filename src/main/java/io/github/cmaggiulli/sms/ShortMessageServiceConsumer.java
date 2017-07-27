package io.github.cmaggiulli.sms;

import io.github.cmaggiulli.sms.pojo.Message;
import io.github.cmaggiulli.sms.pojo.Request;
import io.github.cmaggiulli.sms.two.TwoSMSClient;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;

/**
 * 
 * @author CMaggi1
 *
 */
public class ShortMessageServiceConsumer extends DefaultConsumer {
	private static final Logger LOG = (Logger) LogManager.getLogger(ShortMessageServiceConsumer.class);

	public ShortMessageServiceConsumer(Channel channel) {
		super(channel);
	}

	@Override
	public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body) throws IOException {
		Message message = null;
		try {
			TwoSMSClient client = TwoSMSClient.getInstance();
			
			message = Request.getMessage(new String(body));
			client.send(Request.getMessageRequest(message), message.getId());
		
			getChannel().basicAck(envelope.getDeliveryTag(), true);
		} catch(Exception e) {
			LOG.error("Message " + message.getId() + " failed to deliver");
			getChannel().basicAck(envelope.getDeliveryTag(), false);
		}
	}
}
