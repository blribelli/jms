package br.com.caelum.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import br.com.caelum.modelo.Pedido;
import br.com.caelum.modelo.PedidoFactory;

public class TesteProdutorTopico {

	public static void main(String[] args) throws NamingException, JMSException {

		InitialContext context = new InitialContext();

		// nome da connection factory definido na doc. do activemq
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection conn = factory.createConnection("admin", "admin");
		conn.start();

		Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination topico = (Destination) context.lookup("loja");
		
		MessageProducer producer = session.createProducer(topico);
		Message message;
		
		Pedido pedido = new PedidoFactory().geraPedidoComValores();

		for (int i = 0; i < 100; i++) {
			message = session.createObjectMessage(pedido);
			message.setBooleanProperty("ebook", true);
			producer.send(message);
		}
		
		session.close();
		conn.close();
		context.close();
	}

}
