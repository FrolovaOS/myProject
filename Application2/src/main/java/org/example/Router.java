package org.example;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;

import org.springframework.integration.kafka.dsl.Kafka;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;


@Configuration
@EnableKafka
@EnableIntegration
public class Router {


    @Autowired
    private ConfigProperties properties;

    @Autowired
    private AppService1 service1;

    @Autowired
    private AppService2 service2;


    @Bean
    public IntegrationFlow readFromKafka1() {
        return IntegrationFlows
                .from(Kafka.messageDrivenChannelAdapter( new DefaultKafkaConsumerFactory<>(properties.getProperties().buildConsumerProperties()),properties.getUsertopic()))
                .handle(service1)
                .get();
    }


    @Bean
    public IntegrationFlow readFromKafka2() {
        return IntegrationFlows
                .from(Kafka.messageDrivenChannelAdapter( new DefaultKafkaConsumerFactory<>(properties.getProperties().buildConsumerProperties()),properties.getStaticstopic()))
                .handle(service2)
                .get();
    }
}
