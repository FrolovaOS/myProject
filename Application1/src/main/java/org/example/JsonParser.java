package org.example;


import java.util.logging.Logger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class JsonParser {

    private static Logger log;

    private ObjectMapper objectMapper = new ObjectMapper();

    public User getUser(String response)  {
        User  user = null;
        try {
            user = objectMapper.readValue(response, User.class);
        } catch (NullPointerException e) {
            log = Logger.getLogger(JsonParser.class.getName());
            log.info("Invalid dataa");
            log.info(e.getMessage());
        }
        catch( JsonProcessingException u){
            log = Logger.getLogger(JsonParser.class.getName());
            log.info("Invalid dataa");
            log.info(u.getMessage());
        }
        return  user;
    }

    public String getJson(User user){
        String  message = null;
        try {
            message = objectMapper.writeValueAsString(user);
        }catch (NullPointerException e) {
            log = Logger.getLogger(JsonParser.class.getName());
            log.info("Invalid data");
            log.info(e.getMessage());

        }
        catch( JsonProcessingException u){
            log = Logger.getLogger(JsonParser.class.getName());
            log.info("Invalid data");
            log.info(u.getMessage());
        }
        return message;
    }
}
