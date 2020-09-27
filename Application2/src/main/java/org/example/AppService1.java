package org.example;


import org.example.db.UserDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;


@Service
public class AppService1 implements MessageHandler {

    @Autowired
    private JsonParser jsonParser;

    @Autowired
    private UserDaoImpl userDao;

    public void handleMessage(Message<?> message) throws MessagingException {
        User user = jsonParser.getUser(message.getPayload().toString());
        if (user != null) {
            userDao.insert(user, Integer.parseInt(message.getHeaders().get(KafkaHeaders.RECEIVED_MESSAGE_KEY).toString()));
        }
    }
}