package org.example;

import org.example.db.UserDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.integration.kafka.dsl.Kafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.support.MessageBuilder;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Logger;

@Configuration
@EnableIntegration
@EnableAutoConfiguration
public class Service extends MessageProducerSupport implements MessageHandler {


    private static ConcurrentMap<Integer, User> users;

    private static Integer id;

    private DirectChannel sendUser;

    @Autowired
    private UserDaoImpl userDao;

    @Autowired
    private ConfigProperties properties;

    @Autowired
    private JsonParser jsonParser;

    @Bean
    public void sendUser(){ sendUser = new DirectChannel();}

    @Bean
    public void loadUsers()
    {
       users= userDao.loadAllUser();
    }


    @Bean
    public IntegrationFlow userToKafka() {
    return IntegrationFlows
            .from(sendUser)
            .handle(Kafka.outboundChannelAdapter(new DefaultKafkaProducerFactory<>(properties.getProperties().buildProducerProperties())).topic(properties.getUsertopic()))
            .get();
    }


    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        User user = jsonParser.getUser(message.getPayload().toString());

        Message msgForStatics = null;
        if(users.containsValue(user)) {
            for(Map.Entry<Integer,User> entry : users.entrySet())
            {
                if(entry.getValue().equals(user) )
                {
                    id=entry.getKey();
                }
            }
            msgForStatics = MessageBuilder.withPayload(id).setHeader(KafkaHeaders.TOPIC, properties.getUsertopic()).build();
        }
        else {
            id=users.size()+1;
            users.put(id,user);

            Message uniqUser = MessageBuilder.withPayload(jsonParser.getJson(user)).setHeader(KafkaHeaders.TOPIC, properties.getUsertopic()).setHeader("idUser",id).build();
            sendUser.send(uniqUser);

            msgForStatics = MessageBuilder.withPayload(id).setHeader(KafkaHeaders.TOPIC, properties.getUsertopic()).build();
        }

        sendMessage(msgForStatics);
    }
}
