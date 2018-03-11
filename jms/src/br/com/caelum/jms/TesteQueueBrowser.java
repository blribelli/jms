package br.com.caelum.jms;

import java.util.Enumeration;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class TesteQueueBrowser {

	public static void main(String[] args) throws NamingException, JMSException {

		InitialContext context = new InitialContext();

		// nome da connection factory definido na doc. do activemq
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection conn = factory.createConnection("user", "senha");
		conn.start();

		Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination fila = (Destination) context.lookup("financeiro");

		QueueBrowser browser = session.createBrowser((Queue) fila);
		Enumeration enumeration = browser.getEnumeration();

		while(enumeration.hasMoreElements()) {
			TextMessage textMessage = (TextMessage) enumeration.nextElement();
			System.out.println(textMessage.getText());

		}

		browser.close();
		session.close();
		conn.close();
		context.close();
	}

}
