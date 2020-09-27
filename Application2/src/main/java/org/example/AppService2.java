package org.example;


import org.example.db.UserDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;


@Service
public class AppService2 implements MessageHandler {


    @Autowired
    private UserDaoImpl userDao;

    @Autowired
    private JsonParser jsonParser;

    public void handleMessage(Message<?> message) throws MessagingException {
        Counter count = jsonParser.getCounter(message.getPayload().toString());
        if (count != null) {
            userDao.insertStatics(count);
        }
    }
}
