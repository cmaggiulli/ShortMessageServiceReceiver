package io.github.cmaggiulli.sms.pojo;

import java.io.File;
import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import io.github.cmaggiulli.sms.util.Properties;

public class Request {
	
	public static Message getMessage(String body) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		
		Message message = (Message) mapper.readValue(body, Message.class);
		message.setId();
		
		return message;
	}
	
	public static String getMessageRequest(Message message) {
		File file = new File("src/main/resources/RequestSendMessage.xml");
		
		return populate(file.toString(), message);
	}
	
	private static String populate(String request, Message message) {
		return request.replaceAll("${user}", Properties.getTwoSMSUser()).replaceAll("${password}", 
				Properties.getTwoSMSPassword()).replaceAll("${to}", message.getTo().toString()).replaceAll("${body}", message.getBody());
	}
}
