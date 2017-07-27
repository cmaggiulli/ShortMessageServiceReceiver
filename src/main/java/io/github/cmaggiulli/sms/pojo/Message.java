package io.github.cmaggiulli.sms.pojo;

import java.util.Set;
import java.util.UUID;

import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

public class Message {
	private Set<PhoneNumber> to;
	private String body;
	private String id;
	
	public Message(Set<PhoneNumber> to, String body) {
		this.to = to;
		this.body = body;
		this.id = UUID.randomUUID().toString();
	}
	
	public Set<PhoneNumber> getTo() {
		return to;
	}
	
	public void setTo(Set<PhoneNumber> to) {
		this.to = to;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}	
	
	public String getId() {
		return id;
	}	
	
	public void setId() {
		this.id = UUID.randomUUID().toString();
	}
	
}
