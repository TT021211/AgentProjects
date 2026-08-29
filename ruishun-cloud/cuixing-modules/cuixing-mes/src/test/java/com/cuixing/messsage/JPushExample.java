package com.cuixing.messsage;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

public class JPushExample {
    @Test
    public  void test() {
        JPushService jPushService = new JPushService();

        // 广播推送
        jPushService.sendBroadcast("广播消息标题", "广播消息内容");

        // 向指定用户推送
        //List<String> userIds = List.of("user_id_1", "user_id_2");
        //jPushService.sendToSpecificUsers(userIds, "指定用户消息标题", "指定用户消息内容");

        // 向群组推送
        //jPushService.sendToGroup("group_name", "群组消息标题", "群组消息内容");

        // 向用户发送自定义消息
        //jPushService.sendMessageToUser("user_id_1", "自定义消息内容");
    }
}
