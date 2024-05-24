package com.willownsenator.sfgjms.listener;

import com.willownsenator.sfgjms.config.JmsConfig;
import com.willownsenator.sfgjms.model.HelloWorldMessage;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class HelloMessageListener {
    private final JmsTemplate jmsTemplate;

    public HelloMessageListener(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @JmsListener(destination = JmsConfig.MY_QUEUE)
    public void listen(@Payload HelloWorldMessage helloWorldMessage) {
        System.out.println("I got a message!!!!");
        System.out.println(helloWorldMessage);
    }

    @JmsListener(destination = JmsConfig.MY_SEND_RECEIVE_QUEUE)
    public void listenForHello(@Payload HelloWorldMessage helloWorldMessage, Message message) throws JMSException {
        System.out.println("I got a message!!!!");
        System.out.println(helloWorldMessage);
        var msg = new HelloWorldMessage(UUID.randomUUID(),"World!!!");
        jmsTemplate.convertAndSend(message.getJMSReplyTo(), msg);
        System.out.println("Reply sent");
    }
}
