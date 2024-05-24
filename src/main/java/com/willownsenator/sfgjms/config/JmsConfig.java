package com.willownsenator.sfgjms.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.jms.JMSException;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Configuration
@EnableJms
public class JmsConfig {
    public final static String MY_QUEUE = "hello-world-queue";
    public final static String MY_SEND_RECEIVE_QUEUE = "reply-back-to-me";

    @Bean
    public MessageConverter messageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }

    @Bean
    public JmsTemplate jmsTemplate() throws JMSException {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory());
        jmsTemplate.setMessageConverter(messageConverter());
        return jmsTemplate;
    }

    @Bean
    public ActiveMQConnectionFactory connectionFactory() throws JMSException {
        var connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL("tcp://192.168.0.18:61616");
        connectionFactory.setUser("artemis");
        connectionFactory.setPassword("simetraehcapa");
        return connectionFactory;
    }

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }

}
