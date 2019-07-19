package ram.jms;

import ram.jms.MessageSender;
import java.lang.Exception;

public class main {

	public static void main(String[] args) {
		MessageSender msgsender = new MessageSender("tcp://localhost:61616","MESSAGE_QUEUE");
		try {
			msgsender.send("Hi Henry");
		}
		catch(Exception e){
			System.out.println("Failed to send message");
		}
	}

}