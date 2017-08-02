package io.github.cmaggiulli.sms.template;
import java.io.StringWriter;

import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.json.JSONObject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

public class TemplateEngine {
	private static final Logger LOG = (Logger) LogManager.getLogger(TemplateEngine.class);
	
	static {
		init();
	}
	
	private static void init() {
		Velocity.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		Velocity.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		
		try {
			Velocity.init();
		} catch (Exception e) {
			LOG.error("Error creating velocity context");
			System.exit(1);
		}
	}
	
	public static String getTemplate(String json) throws ResourceNotFoundException, ParseErrorException, Exception {
		VelocityContext context = new VelocityContext();
		StringWriter writer = new StringWriter();
		
		context.put("body", new JSONObject(json).get("body"));
		Template template = Velocity.getTemplate("templates/message.vm");
		
		template.merge(context, writer);
		
		return writer.toString();
	}
}

