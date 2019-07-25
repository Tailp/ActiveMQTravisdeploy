package ram.jms;

/*For sender*/
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

/*For listener*/
import javax.jms.DeliveryMode;
import javax.jms.ExceptionListener;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/*
 * This class is used to send a text message to the queue.
 */
public class MessageSender
{
	private static String url = "tcp://localhost:61616";
	private static String queueName = "MESSAGE_QUEUE";

	public MessageSender(){}

	public MessageSender(String url_ln,String queueName_ln) {
		this.url = url_ln;
		this.queueName = queueName_ln;

	}

	public Boolean testConnection() {
        try {
            /*
             * Getting JMS connection from the JMS server and starting it
             */
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(this.url);
            Connection connection = connectionFactory.createConnection();
            connection.start();

            /*
             * Creating a non transactional session to send/receive JMS message.
             */
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            /*
             * The queue will be created automatically on the server.
             */
            Destination destination = session.createQueue("Con_Test");

            /*
             * Destination represents here our queue 'MESSAGE_QUEUE' on the JMS server.
             * 
             * MessageProducer is used for sending messages to the queue.
             */
            MessageProducer producer = session.createProducer(destination);
            TextMessage message = session.createTextMessage("Testing");

            producer.send(message);
            connection.close();
            System.out.println("Connection to activemq Succeeded"); 
            return true;
        }catch(Exception e){
            System.out.println("Connection to activemq failed, please double check the ActiveMQ server"); 
            return false;   
        }
    }

	public void send(String str_message) throws JMSException
	{
		System.out.println("url = " + url);

		/*
		 * Getting JMS connection from the JMS server and starting it
		 */
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
		Connection connection = connectionFactory.createConnection();
		connection.start();

		/*
		 * Creating a non transactional session to send/receive JMS message.
		 */
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		/*
		 * The queue will be created automatically on the server.
		 */
		Destination destination = session.createQueue(queueName);

		/*
		 * Destination represents here our queue 'MESSAGE_QUEUE' on the JMS server.
		 * 
		 * MessageProducer is used for sending messages to the queue.
		 */
		MessageProducer producer = session.createProducer(destination);
		TextMessage message = session.createTextMessage(str_message);

		/*
		 * Here we are sending our message!
		 */
		producer.send(message);

		System.out.println("Message '" + message.getText() + ", Sent Successfully to the Queue");
		connection.close();
	}

	public String listen() {
        try {
            // Create a ConnectionFactory
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(this.url);

            // Create a Connection
            Connection connection = connectionFactory.createConnection();
            connection.start();

            // Create a Session
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create the destination (Topic or Queue)
            Destination destination = session.createQueue(this.queueName);

            // Create a MessageConsumer from the Session to the Topic or Queue
            MessageConsumer consumer = session.createConsumer(destination);

            // Wait for a message
            Message message = consumer.receive(1000);

            TextMessage textMessage = (TextMessage) message;
            String text = textMessage.getText();

            consumer.close();
            session.close();
            connection.close();
            return text;
        } catch (Exception e) {
            return "Caught: " + e;
        }
    }
}
