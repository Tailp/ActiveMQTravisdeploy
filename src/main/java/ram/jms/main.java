package ram.jms;

import ram.jms.MessageSender;
import java.lang.Exception;

public class main {

	public static void main(String[] args) {
		MessageSender msgsender = new MessageSender();
		try {
			msgsender.send("4324525234");
		}
		catch(Exception e){
			System.out.println("Failed to send message");
		}

		System.out.println(msgsender.listen());
	}

}