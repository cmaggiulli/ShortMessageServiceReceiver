package io.github.cmaggiulli.sms.two;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;


public class TwoSMSClient {
	private static final Logger LOG = (Logger) LogManager.getLogger(TwoSMSClient.class);

	private static TwoSMSClient instance = null;

	private TwoSMSClient() {
		
	}

	public static TwoSMSClient getInstance() {
		if (instance == null) {
			instance = new TwoSMSClient();
		}
		
		return instance;
	}
	
	public void send(String template) throws Exception {
		URL url = new URL("http://www.2sms.com/xml/xml.jsp");
		
        URLConnection connection = url.openConnection();
        connection.setDoOutput(true);
        
        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
        writer.write(template);
        writer.flush();
        writer.close();

        response(connection);
	}
	
	private void response(URLConnection connection) throws ParserConfigurationException, SAXException, IOException {
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(connection.getInputStream());
        
        LOG.info(document.toString());
	}
}
