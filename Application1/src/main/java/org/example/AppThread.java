package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.kafka.dsl.Kafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.sql.Timestamp;
import java.util.Map;
import java.util.concurrent.*;
import java.util.logging.Logger;

@Configuration
@EnableIntegration
@EnableAutoConfiguration
public class AppThread  implements MessageHandler {

    private DirectChannel sendStatics;

    private ObjectMapper objectMapper = new ObjectMapper();

    private Timestamp startinterval = new Timestamp(System.currentTimeMillis());

    private static ConcurrentMap<Integer,Integer> statics =new ConcurrentHashMap<>(1);

    @Autowired
    private ConfigProperties properties;

    @Autowired
    private ThreadPoolTaskExecutor executor;

    @Bean
    public ThreadPoolTaskExecutor initExecutor(){
        executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(1);
        executor.setMaxPoolSize(1);
        executor.setQueueCapacity(25);
        executor.initialize();
        return executor;
    }

    @Scheduled(cron = "* */2 * * * *")
    public void interval() {
        startinterval = new Timestamp(System.currentTimeMillis());
    }

    @Bean
    public void sendStatics(){ sendStatics = new DirectChannel();}

    @Scheduled(cron = "* */2 * * * *")
    public void showStatics(){
        Logger log;
        log = Logger.getLogger(AppThread.class.getName());
        executor.execute(() -> {
            for(Map.Entry<Integer,Integer> entry : statics.entrySet())
            {
                Counter count = new Counter(entry.getKey(),entry.getValue(),startinterval.getTime());

                String info = null;
                try {
                    info = objectMapper.writeValueAsString(count);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    log.info("Invalid data");
                    log.info(e.getMessage());
                }

                Message record = MessageBuilder.withPayload(info).build();
                sendStatics.send(record);
            }
            statics.clear();
        });
    }

    @Bean
    public IntegrationFlow staticsToKafka() {
        return IntegrationFlows
                .from(sendStatics)
                .handle(Kafka.outboundChannelAdapter(new DefaultKafkaProducerFactory<>(properties.getProperties().buildProducerProperties())).topic(properties.getStaticstopic()))
                .get();
    }

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {

        Integer id =  (Integer) message.getPayload();
        Integer oldValue=statics.get(id);

        if(oldValue!=null)
        {
            statics.replace(id,oldValue+1);
        }
        else statics.put(id,1);

    }
}
