//package com.cuixing.mes.message;
//
//import com.cuixing.common.core.web.domain.AjaxResult;
//import com.cuixing.mes.message.vo.Message;
//import com.cuixing.mes.message.vo.ProductMessage;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
///**
// * 极光推送接口
// *
// * @author 阿江
// */
//@Api(tags = "极光推送接口")
//@RestController
//@RequestMapping("/jpush")
//public class JPushController {
//
//    @Autowired
//    private JPushService jPushService;
//
//    /**
//     * 推送给指定用户
//     *
//     * @param message
//     * @return
//     */
//   /* @ApiOperation("推送给指定用户")
//    @PostMapping("/sendToRegistrationIds")
//    public AjaxResult sendPushToUsers2(@RequestBody Message message) {
//        AjaxResult ajaxResult = jPushService.sendPushToRegistrationIds2(message.getMessage(), message.getRegistrationIds());
//        return AjaxResult.success(ajaxResult);
//    }*/
//
//    /**
//     * 推送给指定用户
//     *
//     * @param message
//     * @return
//     */
//    @ApiOperation("推送给指定用户")
//    @PostMapping("/sendToRegistrationIds")
//    public AjaxResult sendPushToUsers(@RequestBody ProductMessage message) {
//        AjaxResult ajaxResult = jPushService.sendPushToRegistrationIds(message);
//        return AjaxResult.success(ajaxResult);
//    }
//
//    /**
//     * 推送给所有用户
//     * @param messageTitle
//     * @param messageContent
//     * @return
//     */
//    @RequestMapping("/sendBroadcast")
//    public AjaxResult sendBroadcast(String messageTitle, String messageContent) {
//        AjaxResult ajaxResult = jPushService.sendBroadcast(messageTitle, messageContent);
//        return AjaxResult.success(ajaxResult);
//    }
//}
