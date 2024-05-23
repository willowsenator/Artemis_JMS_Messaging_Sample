package com.willownsenator.sfgjms.sender;

import com.willownsenator.sfgjms.config.JmsConfig;
import com.willownsenator.sfgjms.model.HelloWorldMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class HelloSender {
    private final JmsTemplate jmsTemplate;

    @Autowired
    public HelloSender(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Scheduled(fixedRate = 2000)
    public void sayHello() {
        System.out.println("I am saying hello!!!");
        var msg = new HelloWorldMessage(UUID.randomUUID(),"Hello World!!!");
        jmsTemplate.convertAndSend(JmsConfig.QUEUE_NAME, msg);
        System.out.println("Message sent!!!");
    }
}
