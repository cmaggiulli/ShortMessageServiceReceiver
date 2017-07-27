package io.github.cmaggiulli.sms.util;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat;
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberType;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

public class Formatter {
	private static final String US = "US";
	
	public static PhoneNumber phone(String number) throws NumberParseException {
		PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
		PhoneNumber phoneNumber = phoneNumberUtil.parse(number, US);
		phoneNumberUtil.format(phoneNumber, PhoneNumberFormat.NATIONAL);
		
		return phoneNumber;
	}
}
