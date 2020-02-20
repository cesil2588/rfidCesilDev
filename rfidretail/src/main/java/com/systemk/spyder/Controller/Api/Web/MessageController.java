package com.systemk.spyder.Controller.Api.Web;

import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {
	
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public String greeting(String message) throws Exception {
        Thread.sleep(100); // simulated delay
        return new String(message);
    }

    @SendToUser("/queue/notiReceive")
    public String notiReceive(String message) throws Exception {
        Thread.sleep(100); // simulated delay
        return new String(message);
    }
    
    @SendTo("/topic/gateReceive")
    public String gateReceive(String message) throws Exception {
        Thread.sleep(100); // simulated delay
        return new String(message);
    }
    
    @MessageExceptionHandler
    @SendToUser("/queue/errors")
    public String handleException(Throwable exception) {
        return exception.getMessage();
    }
    
}