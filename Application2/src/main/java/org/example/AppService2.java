package org.example;


import org.example.db.UserDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;


@Service
public class AppService2 implements MessageHandler {
    private User user;

    @Autowired
    private UserDaoImpl userDao;

    @Autowired
    private JsonParser jsonParser;

    public void handleMessage(Message<?> message) throws MessagingException {
        this.user = jsonParser.getUser(message.getPayload().toString());
        if (user != null) {
            userDao.insertStatics(user);
        }
    }
}
