package com.cuixing.system.api;

import com.cuixing.common.core.constant.SecurityConstants;
import com.cuixing.common.core.constant.ServiceNameConstants;
import com.cuixing.common.core.domain.R;
import com.cuixing.system.api.domain.SysUser;
import com.cuixing.system.api.factory.RemoteUserFallbackFactory;
import com.cuixing.system.api.model.LoginUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户服务
 *
 * @author cuixing
 */
@FeignClient(contextId = "remoteUserService", value = ServiceNameConstants.SYSTEM_SERVICE, fallbackFactory = RemoteUserFallbackFactory.class)
public interface RemoteUserService {

	/**
	 * 通过用户名查询用户信息
	 *
	 * @param username 用户名
	 * @param source   请求来源
	 * @return 结果
	 */
	@GetMapping("/user/info/{username}")
	public R<LoginUser> getUserInfo(@PathVariable("username") String username, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

	/**
	 * 注册用户信息
	 *
	 * @param sysUser 用户信息
	 * @param source  请求来源
	 * @return 结果
	 */
	@PostMapping("/user/register")
	public R<Boolean> registerUserInfo(@RequestBody SysUser sysUser, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

	/**
	 * 通过企业微信扫码登录
	 *
	 * @param wechatUserId
	 * @param source
	 * @return 结果
	 */
	@GetMapping("/user/info/wechatLogin/{wechatUserId}")
	public R<LoginUser> getInfoByEnterpriseWechatId(@PathVariable("wechatUserId") String wechatUserId, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

	@GetMapping("/user/getUserByIds/{userIds}")
	public R<List<SysUser>> getUserByIds(@PathVariable("userIds") Long[] userIds);

	@GetMapping("/user/getUserByDeviceId/{deviceIds}")
	public List<SysUser> getUserToDeviceId(@PathVariable("deviceIds") String deviceIds, @RequestHeader(SecurityConstants.FROM_SOURCE)String source);


}
