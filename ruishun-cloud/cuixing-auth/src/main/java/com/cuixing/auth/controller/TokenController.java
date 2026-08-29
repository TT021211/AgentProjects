package com.cuixing.auth.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import com.cuixing.auth.form.LoginBody;
import com.cuixing.auth.form.RegisterBody;
import com.cuixing.auth.service.SysLoginService;
import com.cuixing.common.core.domain.R;
import com.cuixing.common.core.utils.JwtUtils;
import com.cuixing.common.core.utils.StringUtils;
import com.cuixing.common.security.auth.AuthUtil;
import com.cuixing.common.security.service.TokenService;
import com.cuixing.common.security.utils.SecurityUtils;
import com.cuixing.system.api.model.LoginUser;


/**
 * token 控制
 * 
 * @author cuixing
 */
@RestController
public class TokenController
{
    @Autowired
    private TokenService tokenService;

    @Autowired
    private SysLoginService sysLoginService;


    @PostMapping("login")
    public R<?> login(@RequestBody LoginBody form)
    {
        System.out.println(form);
        // 用户登录
        LoginUser userInfo = sysLoginService.login(form.getUsername(), form.getPassword());
        // 获取登录token
        return R.ok(tokenService.createToken(userInfo));
    }

//    /**
//     * 企业微信登录
//     * @param code 企业微信的密钥
//     * @return
//     * @throws URISyntaxException
//     */
//    @GetMapping("wechatLogin")
//    public String wechatLogin(@RequestParam("code")String code,@RequestParam("appid") String appid) throws URISyntaxException {
//        LoginUser loginUser = sysWechatLoginService.wxLogin(code);
////        return R.ok(tokenService.createToken(loginUser));
//        Map<String, Object> token = tokenService.createToken(loginUser);
//        return "redirect:"+ WechatLoginConfig. +"/?access_token="+token.get("access_token")+"&"+"expires_in="+token.get("expires_in");
//    }

//    /**
//     * 企业微信登录
//     * @param code 企业微信的密钥
//     * @return
//     * @throws URISyntaxException
//     */
//    @GetMapping("wechatLogin")
//    public R<?> wechatLogin(@RequestParam("code")String code,@RequestParam("appid") String appid) throws URISyntaxException {
//        LoginUser loginUser = sysWechatLoginService.wxLogin(code);
//        return R.ok(tokenService.createToken(loginUser));
//    }

    @DeleteMapping("logout")
    public R<?> logout(HttpServletRequest request)
    {
        String token = SecurityUtils.getToken(request);
        if (StringUtils.isNotEmpty(token))
        {
            String username = JwtUtils.getUserName(token);
            // 删除用户缓存记录
            AuthUtil.logoutByToken(token);
            // 记录用户退出日志
            sysLoginService.logout(username);
        }
        return R.ok();
    }

    @PostMapping("refresh")
    public R<?> refresh(HttpServletRequest request)
    {
        LoginUser loginUser = tokenService.getLoginUser(request);
        if (StringUtils.isNotNull(loginUser))
        {
            // 刷新令牌有效期
            tokenService.refreshToken(loginUser);
            return R.ok();
        }
        return R.ok();
    }

    @PostMapping("register")
    public R<?> register(@RequestBody RegisterBody registerBody)
    {
        // 用户注册
        sysLoginService.register(registerBody.getUsername(), registerBody.getPassword());
        return R.ok();
    }


}
