package com.futengwl.activeMQ;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;

/**
 * Created by figo on 2/28/17.
 */

@Configuration
@EnableJms
public class ActiveMQConfig {

    @Value("${spring.activemq.broker-url}")
    private String activeMQUrl;

    @Bean
    public Queue orderSettlementQueue() {
        return new ActiveMQQueue(QueueName.ORDER_SETTLEMENT_QUEUE);
    }

    @Bean
    public Queue orderAfterSalesQueue() {
        return new ActiveMQQueue(QueueName.ORDER_AFTERSALES_QUEUE);
    }
    
    @Bean
    public Queue orderChangeStatusQueue() {
    	return new ActiveMQQueue(QueueName.ORDER_CHANGESTATUS_QUEUE);
    }


    @Bean
    public ConnectionFactory connectionFactory(){

        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(activeMQUrl);
        connectionFactory.setTrustAllPackages(true);
        return connectionFactory;
    }

    @Bean
    public JmsTemplate jmsTemplate(){
        return new JmsTemplate(connectionFactory());
    }

}