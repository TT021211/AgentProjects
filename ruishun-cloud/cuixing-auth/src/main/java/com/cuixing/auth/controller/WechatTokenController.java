package com.cuixing.auth.controller;

import com.cuixing.auth.config.WechatLoginConfig;
import com.cuixing.auth.service.SysLoginService;
import com.cuixing.auth.service.SysWechatLoginService;
import com.cuixing.common.security.service.TokenService;
import com.cuixing.system.api.model.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URISyntaxException;
import java.util.Map;

/**
 * @author zhan
 * @create 2024/3/1 11:08
 */
@Controller
public class WechatTokenController {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private SysWechatLoginService sysWechatLoginService;
    @Autowired
    private WechatLoginConfig wechatLoginConfig;
    /**
     * 企业微信登录
     * @param code 企业微信的密钥
     * @return
     * @throws URISyntaxException
     */
    @GetMapping("wechatLogin")
    public String wechatLogin(@RequestParam("code")String code, @RequestParam("appid") String appid) throws URISyntaxException {
        LoginUser loginUser = sysWechatLoginService.wxLogin(code);
//        return R.ok(tokenService.createToken(loginUser));
        Map<String, Object> token = tokenService.createToken(loginUser);
        return "redirect:"+ wechatLoginConfig.getLoginUrl() +"/?access_token="+token.get("access_token")+"&"+"expires_in="+token.get("expires_in");
    }
}
