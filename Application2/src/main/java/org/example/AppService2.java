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

    public void handleMessage(Message<?> message) throws MessagingException {
        JsonParser users = new JsonParser();
        this.user = users.getUser(message.getPayload().toString());
        if (user != null) {
            userDao.insertStatics(user);
        }
    }
}
