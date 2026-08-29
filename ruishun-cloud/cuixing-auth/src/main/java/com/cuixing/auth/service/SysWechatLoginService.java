package com.cuixing.auth.service;

import com.cuixing.auth.config.WechatLoginConfig;
import com.cuixing.auth.exception.WechatLoginException;
import com.cuixing.auth.form.AccessTokenBody;
import com.cuixing.auth.form.EnterpriseWechatIdBody;
import com.cuixing.common.core.constant.Constants;
import com.cuixing.common.core.constant.SecurityConstants;
import com.cuixing.common.core.domain.R;
import com.cuixing.common.core.enums.UserStatus;
import com.cuixing.common.core.exception.ServiceException;
import com.cuixing.common.core.utils.StringUtils;
import com.cuixing.system.api.RemoteUserService;
import com.cuixing.system.api.domain.SysUser;
import com.cuixing.system.api.model.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author zhan
 * @create 2023/12/13 17:07
 */
@Service
public class SysWechatLoginService {
    @Autowired
    private WechatLoginConfig wechatLoginConfig;
    @Autowired
    private RemoteUserService remoteUserService;

    @Autowired
    private SysRecordLogService recordLogService;

    /**
     * 通过企业微信扫码传入code登录
     * @param code
     * @return
     * @throws URISyntaxException
     */
    public LoginUser wxLogin(String code) throws URISyntaxException {

        RestTemplate restTemplate = new RestTemplate();
        String access = wechatLoginConfig.getAccessUrl();
        System.out.println(access);

        ResponseEntity<AccessTokenBody> TokenResult = restTemplate.getForEntity(new URI(wechatLoginConfig.getAccessUrl()), AccessTokenBody.class);
        String access_token = TokenResult.getBody().getAccess_token();
        EnterpriseWechatIdBody enterpriseWechatIdBody = restTemplate.getForObject(new URI(wechatLoginConfig.getCodeUrl(access_token, code)), EnterpriseWechatIdBody.class);
        System.out.println(wechatLoginConfig.getCodeUrl(access_token, code));
        System.out.println(enterpriseWechatIdBody);
        //获得企业微信id
        String wechatUserId = enterpriseWechatIdBody.getUserid();
        //其他相关信息
        Object forObject = restTemplate.getForObject(new URI(wechatLoginConfig.getUserData(access_token, wechatUserId)), Object.class);
       //这里后期需要添加判断
        System.out.println(wechatUserId);
        return  loginByUserId(wechatUserId);
    }

    /**
     * 通过wechatUserId登录
     * @param wechatUserId
     * @return
     */
    public LoginUser loginByUserId(String wechatUserId) {
        System.out.println(wechatUserId);
        R<LoginUser> userResult = remoteUserService.getInfoByEnterpriseWechatId(wechatUserId, SecurityConstants.INNER);

        if (StringUtils.isNull(userResult) || StringUtils.isNull(userResult.getData())) {

            recordLogService.recordLogininfor(wechatUserId, Constants.LOGIN_FAIL, "登录用户不存在");
            throw new WechatLoginException("登录用户：" + wechatUserId + " 不存在",401);
        }
        LoginUser userInfo = userResult.getData();
        // System.out.println(userInfo.getUsername());
        SysUser user = userResult.getData().getSysUser();
        if (UserStatus.DELETED.getCode().equals(user.getDelFlag())) {
            recordLogService.recordLogininfor(wechatUserId, Constants.LOGIN_FAIL, "对不起，您的账号已被删除");
            throw new WechatLoginException("对不起，您的账号：" + wechatUserId + " 已被删除",402);
        }
        if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
            recordLogService.recordLogininfor(wechatUserId, Constants.LOGIN_FAIL, "用户已停用，请联系管理员");
            throw new WechatLoginException("对不起，您的账号：" + wechatUserId + " 已停用",403);
        }
        recordLogService.recordLogininfor(wechatUserId, Constants.LOGIN_SUCCESS, "登录成功");
        return userInfo;
    }
}
