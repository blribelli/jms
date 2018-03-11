package br.com.caelum.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class TesteProdutorFila {

	public static void main(String[] args) throws NamingException, JMSException {

		InitialContext context = new InitialContext();

		// nome da connection factory definido na doc. do activemq
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection conn = factory.createConnection("user", "senha");
		conn.start();

		Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination fila = (Destination) context.lookup("financeiro");

		MessageProducer producer = session.createProducer(fila);
		Message message;

		for (int i = 0; i < 1000; i++) {
			message = session.createTextMessage("<pedido><id>" + i + "</id></pedido>");
			producer.send(message);
			//producer.send(message, DeliveryMode.NON_PERSISTENT, 1, 5000);
		}

		session.close();
		conn.close();
		context.close();
	}

}
