package ru.pavlenty.chatBroadcast.web;


import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import ru.pavlenty.chatBroadcast.message.Message;
import ru.pavlenty.chatBroadcast.message.Response;

@Controller
@Slf4j
public class WebSocketController {

    private static final String token = "token12345";
    private final SimpMessagingTemplate simpMessagingTemplate;
    public WebSocketController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @RequestMapping(path="/")
    public String index() {
        return "index";
    }

    @Bean
    public ViewResolver getViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("templates/");
        return resolver;
    }

    @RequestMapping(path="/broadcast")
    public String broadcast() {
        return "broadcast";
    }

    @MessageMapping("/broadcast")
    @SendTo("/b")
    public Response broadcast(Message message, @Header(value = "authorization") String authorizationToken) {
        val response = new Response("Token check failed!");
        if (authorizationToken.equals(token)) {
            if(message.getType().equals("name")) {
                response.setResponse("Welcome, " + message.getMessage() + "!");
            } else {
                response.setResponse(message.getName() + ": " + message.getMessage());
            }
        } else {
            log.info(response.getResponse());
        }
        return response;
    }


}
