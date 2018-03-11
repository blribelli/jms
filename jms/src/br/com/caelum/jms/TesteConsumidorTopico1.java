package br.com.caelum.jms;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import br.com.caelum.modelo.Pedido;

public class TesteConsumidorTopico1 {

	public static void main(String[] args) throws NamingException, JMSException {

		InitialContext context = new InitialContext();

		// nome da connection factory definido na doc. do activemq
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection conn = factory.createConnection("user", "senha");
		conn.setClientID("estoque");
		conn.start();

		Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Topic topico = (Topic) context.lookup("loja");

		MessageConsumer consumer = session.createDurableSubscriber(topico, "assinatura1");
		consumer.setMessageListener(new MessageListener() {

			@Override
			public void onMessage(Message message) {

				ObjectMessage objectMessage = (ObjectMessage) message;
				try {
					Pedido pedido = (Pedido) objectMessage.getObject();
					System.out.println(pedido.getCodigo());
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});

		new Scanner(System.in).nextLine();

		session.close();
		conn.close();
		context.close();
	}

}
