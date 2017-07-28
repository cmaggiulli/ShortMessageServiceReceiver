# About
**ShortMessageServiceReceiver** is an RabbitMQ messaging consumer written in Java.  When a executable jar is created and ran as a process this application will consume messages from the *ShortMessageService* queue.  Once the application's consumer pops a message off the queue it will then populate and post an XML Velocity template to the 2sms service which will trigger an SMS to be sent. This consumer is a module component of a larger system.  In the full system a Liferay OSGi module first publishes a message to the *ShortMessageService* using Spring AMQP before being consumed by this receiver.

# Environment
This application was developed for the following environment
1. Java SE 8u111
2. Erlang OTP 19.3.x
3. RabbitMQ 3.6.1
4. RHEL 7

However the minimum requirements can safely be assumed to be
1. Java SE 6
2. Erlange OTP 16.3.x
3. RabbitMQ 3.4.x
4. Any recent Windows, Ubuntu, Debian, RHEL, CentOS, Mac OS


> I would also recommend downloading [PerfTools](https://github.com/rabbitmq/rabbitmq-perf-test) for testing, configure the management plugin, and [jps](http://docs.oracle.com/javase/7/docs/technotes/tools/share/jps.html)

# Building
You will need to modify the properties in configuration.properties to represent your RabbitMQ credentials (default is guest:guest) and your 2sms credentials.  We utilize the *maven-assembly-plugin* to build this program as an executable jar.  Simply run a maven build and specify package as your goal.  The jar will be placed in the /target directory.

### Dependencies
This application uses Maven for build and dependency automation.  The following dependencies are used for the following purposes.
1. *amqp-client* is used because it provides the programmatic API necessary to consume messages from the Rabbit queue.
2. *log4j-core* is used for logging
3. *commons-configuration2* is used for external properties and xml files
4. *libphonenumber* is used for phone number formatting and validation
5. *velocity-tools* is used for the XML templates that get posted to 2sms

# Deploying
To deploy this application you must first install Erlang OTP 16+ and RabbitMQ 3.6+ on your Linux box.  Afterwards transfer your jar file onto the server using ftp, scp, jenkins, etc and run the following shell command.

    sudo java -jar ShortMessageServiceReceiver.jar
    
If you wish you can specify your Log4J appender that this time

    sudo java -jar -Dlog4j.configurationFile=file:///your/file/appender.xml ShortMessageServiceReceiver.jar
    
After running the command you can use the following commands to verify it is running and check the process id.

    jps -m
    
or

    ps aux | grep TextMessageconsumer
    
# Testing
To test this consumer I recommend you download [PerfTools](https://github.com/rabbitmq/rabbitmq-perf-test) and also enable the management plugin for RabbitMQ which provides a nifty web GUI to view information about your RabbitMQ installation and message statistics.  You can also use the rabbitmqctl command in Linux to get this information from shell. 

You need to publish a message to the SMS queue in order to test this consumer.  To publish a message you can use the web GUI, the rabbitmqctl tool, perftools, the http api, or you can write a light weight producer in python/groovy/etc.  You need to have a json object in the following form

    {
       "body":"hello world",
       "to":["15188598588"]
    }

### Warning:  If you are running this against your live SMS service make sure you put in safe guards against accidentally firing 400000 texts


