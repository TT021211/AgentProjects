package com.cuixing.messsage;

import cn.jpush.api.JPushClient;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.notification.Notification;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.Options;

import java.util.List;

public class JPushService {

    private static final String APP_KEY = "77e31d81c6b07b87f6f2dc82"; // 替换为你的 APP_KEY
    private static final String MASTER_SECRET = "636969646ab06f238f5bef4d"; // 替换为你的 MASTER_SECRET

    private JPushClient jpushClient;

    public JPushService() {
        jpushClient = new JPushClient(MASTER_SECRET, APP_KEY);
    }

    // 广播推送（推送给所有设备）
    public void sendBroadcast(String messageTitle, String messageContent) {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all()) // 所有平台
                .setAudience(Audience.all()) // 所有用户
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setAlert(messageContent)
                                .setTitle(messageTitle)
                                .build())
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setAlert(messageContent)
                                .setSound("default")
                                .build())
                        .build())
                .build();

        try {
            jpushClient.sendPush(payload);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 指定用户推送
    public void sendToSpecificUsers(List<String> userIds, String messageTitle, String messageContent) {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all()) // 所有平台
                .setAudience(Audience.registrationId(userIds)) // 指定用户
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setAlert(messageContent)
                                .setTitle(messageTitle)
                                .build())
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setAlert(messageContent)
                                .setSound("default")
                                .build())
                        .build())
                .build();

        try {
            jpushClient.sendPush(payload);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 向群组推送消息
    public void sendToGroup(String groupName, String messageTitle, String messageContent) {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all()) // 所有平台
                .setAudience(Audience.tag(groupName)) // 指定群组
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setAlert(messageContent)
                                .setTitle(messageTitle)
                                .build())
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setAlert(messageContent)
                                .setSound("default")
                                .build())
                        .build())
                .build();

        try {
            jpushClient.sendPush(payload);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 向用户发送自定义消息（非通知）
    public void sendMessageToUser(String userId, String messageContent) {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.registrationId(userId))
                .setMessage(Message.newBuilder()
                        .setMsgContent(messageContent)
                        .build())
                .build();

        try {
            jpushClient.sendPush(payload);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
