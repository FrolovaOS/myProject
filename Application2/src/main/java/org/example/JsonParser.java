package org.example;

import java.util.logging.Logger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonParser {

    public User getUser(String response)  {
        ObjectMapper objectMapper = new ObjectMapper();
        User  user = null;

        try {
            user = objectMapper.readValue(response, User.class);
        } catch (NullPointerException e) {
            Logger log = Logger.getLogger(JsonParser.class.getName());
            log.info("Invalid dataaa");
            log.info(e.getMessage());
        }
        catch( JsonProcessingException u){
            Logger log = Logger.getLogger(JsonParser.class.getName());
            log.info("Invalid dataaa");
            log.info(u.getMessage());
        }
        return  user;
    }


}
