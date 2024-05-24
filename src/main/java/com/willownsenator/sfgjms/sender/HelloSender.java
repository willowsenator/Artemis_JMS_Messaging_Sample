package com.willownsenator.sfgjms.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.willownsenator.sfgjms.config.JmsConfig;
import com.willownsenator.sfgjms.model.HelloWorldMessage;
import jakarta.jms.JMSException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class HelloSender {
    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public HelloSender(JmsTemplate jmsTemplate, ObjectMapper objectMapper) {
        this.jmsTemplate = jmsTemplate;
        this.objectMapper = objectMapper;
    }

    @Scheduled(fixedRate = 2000)
    public void sendMessage() {
        var msg = new HelloWorldMessage(UUID.randomUUID(),"Hello World!!!");
        jmsTemplate.convertAndSend(JmsConfig.MY_QUEUE, msg);
    }

    @Scheduled(fixedRate = 2000)
    public void sendAndReceiveMessage() throws JMSException {
        var msg = new HelloWorldMessage(UUID.randomUUID(),"Hello");
        var receiveMessage = jmsTemplate.sendAndReceive(JmsConfig.MY_SEND_RECEIVE_QUEUE, session -> {
            try {
                var message = session.createTextMessage(objectMapper.writeValueAsString(msg));
                message.setStringProperty("_type", "com.willownsenator.sfgjms.model.HelloWorldMessage");
                return message;
            } catch (JsonProcessingException e) {
                throw new JMSException("boom");
            }
        });
        assert receiveMessage != null;
        System.out.println(receiveMessage.getBody(String.class));
    }
}
