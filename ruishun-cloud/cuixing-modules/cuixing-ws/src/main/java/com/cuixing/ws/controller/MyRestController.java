package com.cuixing.ws.controller;

import com.cuixing.ws.config.MyWebSocketHandler;
import com.cuixing.ws.entity.WebSocket;
import org.springframework.web.bind.annotation.*;

import javax.websocket.OnMessage;

@RestController
@RequestMapping("/api")
public class MyRestController {

    private final MyWebSocketHandler webSocketHandler;

    public MyRestController(MyWebSocketHandler webSocketHandler) {
        this.webSocketHandler = webSocketHandler;
    }

    // 发送消息给所有连接的用户
    @GetMapping("/send")
    public String sendMessageToAll(@RequestParam String message) {
        try {
            webSocketHandler.sendMessageToAll(message);
            return "Message sent to all: " + message;
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to send message to all";
        }
    }

    // 发送消息给指定的用户
//    @CrossOrigin(origins = "*") // 允许来自任何来源的跨域请求
//    @GetMapping("/sendToUser")
//    public String sendMessageToUser(@RequestParam String userId, @RequestParam Object message) {
//        try {
//            webSocketHandler.sendMessageToUser(userId, message);
//            return "Message sent to " + userId + ": " + message;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "Failed to send message to " + userId;
//        }
//    }

    @CrossOrigin(origins = "*") // 允许来自任何来源的跨域请求
    @PostMapping("/sendToUser")
    public String sendMessageToUser(@RequestBody WebSocket webSocket) {
        try {
            webSocketHandler.sendMessageToUser(webSocket.getUserId(), webSocket.getMessage());

            return "Message sent to " + webSocket.getUserId() + ": " +  webSocket.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to send message to " + webSocket.getUserId();
        }
    }
}
