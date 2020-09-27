package org.example;


import java.util.logging.Logger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class JsonParser {

    private static Logger log = Logger.getLogger(JsonParser.class.getName());;

    private ObjectMapper objectMapper = new ObjectMapper();

    public User getUser(String response)  {
        User  user = null;
        try {
            user = objectMapper.readValue(response, User.class);
        } catch (NullPointerException e) {
            log.info("Invalid dataa");
            log.info(e.getMessage());
        }
        catch( JsonProcessingException u){
            log.info("Invalid dataa");
            log.info(u.getMessage());
        }
        return  user;
    }

    public String getJson(User user){
        String  json = null;
        try {
            json = objectMapper.writeValueAsString(user);
        }catch (NullPointerException e) {
            log.info("Invalid data");
            log.info(e.getMessage());

        }
        catch( JsonProcessingException u){
            log.info("Invalid data");
            log.info(u.getMessage());
        }
        return json;
    }
}
