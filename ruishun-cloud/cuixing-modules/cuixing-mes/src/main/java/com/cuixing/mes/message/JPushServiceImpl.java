package com.cuixing.mes.message;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.cuixing.common.core.constant.SecurityConstants;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.mes.entity.MesAnnealingWorkorder;
import com.cuixing.mes.message.vo.Message;
import com.cuixing.mes.message.vo.ProductMessage;
import com.cuixing.system.api.RemoteSystemService;
import com.cuixing.system.api.RemoteUserService;
import com.cuixing.system.api.domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JPushServiceImpl implements JPushService {

    private static final String appKey= "77e31d81c6b07b87f6f2dc82";
    private static final String masterSecret= "636969646ab06f238f5bef4d";

    private final JPushClient jPushClient;

    public JPushServiceImpl() {
        // 使用 appKey 和 masterSecret 初始化 JPushClient
        this.jPushClient = new JPushClient(masterSecret, appKey);
    }

    @Autowired
    private RemoteUserService remoteUserService;

    /**
     * 推送消息给指定用户
     * @param message
     * @param registrationIds
     * @return
     */
    @Override
    public boolean sendPush(Message message, List<String> registrationIds) {
        // 创建推送对象
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.android()) // 选择推送平台
                .setAudience(Audience.registrationId(registrationIds)) // 根据 registrationIds 推送
                .setNotification(Notification.android(message.getContent(), message.getTitle(), null))
                .build();

        try {
            // 执行推送
            jPushClient.sendPush(payload);
            return true;
        } catch (APIConnectionException | APIRequestException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public AjaxResult sendPushToRegistrationIds(ProductMessage message) {
        //处理退火通知
        if (message.getMesAnnealingWorkorder() != null) {
            MesAnnealingWorkorder mesAnnealingWorkorder = message.getMesAnnealingWorkorder();
            if (mesAnnealingWorkorder.getDeviceId()!=null){
                List<SysUser> userToDeviceId = remoteUserService.getUserToDeviceId(mesAnnealingWorkorder.getDeviceId(), SecurityConstants.INNER);
                if (!userToDeviceId.isEmpty()){
                    //拿到每个用户的手机唯一标识
                    List<String> registrationIds = userToDeviceId.stream()
                            .map(SysUser::getRegistrationId)   // 提取 registrationId
                            .distinct()                         // 去重
                            .collect(Collectors.toList());      // 收集为 List                    //发送通知到退火人员手机
                    sendPush(mesAnnealingWorkorder.getMessage(), registrationIds);
                }
            }
        }
        //处理拉丝通知
        if (message.getMesWiredrawingWorkorder() != null){
            if (message.getMesWiredrawingWorkorder().getDeviceId()!=null){
                List<SysUser> userToDeviceId = remoteUserService.getUserToDeviceId(message.getMesWiredrawingWorkorder().getDeviceId(), SecurityConstants.INNER);
                if (!userToDeviceId.isEmpty()){
                    //拿到每个用户的手机唯一标识
                    List<String> registrationIds = userToDeviceId.stream()
                            .map(SysUser::getRegistrationId)   // 提取 registrationId
                            .distinct()                         // 去重
                            .collect(Collectors.toList());      // 收集为 List
                    sendPush(message.getMesWiredrawingWorkorder().getMessage(), registrationIds);
                }
            }
        }
        //处理并丝通知
        if (message.getMesDoublewireWorkorder() != null){
            if (message.getMesDoublewireWorkorder().getDeviceId()!=null){
                List<SysUser> userToDeviceId = remoteUserService.getUserToDeviceId(message.getMesDoublewireWorkorder().getDeviceId(), SecurityConstants.INNER);
                if (!userToDeviceId.isEmpty()){
                    //拿到每个用户的手机唯一标识
                    List<String> registrationIds = userToDeviceId.stream()
                            .map(SysUser::getRegistrationId)   // 提取 registrationId
                            .distinct()                         // 去重
                            .collect(Collectors.toList());      // 收集为 List
                    sendPush(message.getMesDoublewireWorkorder().getMessage(), registrationIds);
                }
            }
        }
        //处理绞线通知
        if (message.getMesStrandedwireWorkorder() != null){
            if (message.getMesStrandedwireWorkorder().getDeviceId()!=null){
                List<SysUser> userToDeviceId = remoteUserService.getUserToDeviceId(message.getMesStrandedwireWorkorder().getDeviceId(), SecurityConstants.INNER);
                if (!userToDeviceId.isEmpty()){
                    //拿到每个用户的手机唯一标识
                    List<String> registrationIds = userToDeviceId.stream()
                            .map(SysUser::getRegistrationId)   // 提取 registrationId
                            .distinct()                         // 去重
                            .collect(Collectors.toList());      // 收集为 List
                    sendPush(message.getMesStrandedwireWorkorder().getMessage(), registrationIds);
                }
            }
        }
        //处理镀锡通知
        if (message.getMesTinningWorkorder() != null){
            if (message.getMesTinningWorkorder().getDeviceId()!=null){
                List<SysUser> userToDeviceId = remoteUserService.getUserToDeviceId(message.getMesTinningWorkorder().getDeviceId(), SecurityConstants.INNER);
                if (!userToDeviceId.isEmpty()){
                    //拿到每个用户的手机唯一标识
                    List<String> registrationIds = userToDeviceId.stream()
                            .map(SysUser::getRegistrationId)   // 提取 registrationId
                            .distinct()                         // 去重
                            .collect(Collectors.toList());      // 收集为 List
                    sendPush(message.getMesTinningWorkorder().getMessage(), registrationIds);
                }
            }
        }
        return AjaxResult.success("消息发送成功！");
    }

    /**
     * 推送广播消息
     * @param messageTitle
     * @param messageContent
     * @return
     */
    @Override
    public AjaxResult sendBroadcast(String messageTitle, String messageContent) {
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
            jPushClient.sendPush(payload);
            return AjaxResult.success("消息发送成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxResult.error("消息发送失败！");
    }
}
