package com.q.wechat.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.q.wechat.entity.meten.UserBean;
import com.q.wechat.service.IUserService;

public class UserRealm extends AuthorizingRealm {
	@Autowired
	private IUserService userServiceImpl;
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("1");
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UserBean user = new UserBean();
		user.setNickname("qiao");
		String nickname = (String)token.getPrincipal();
		if(user.getNickname().equals(nickname)) {
			SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user.getNickname(),"","");
			return authenticationInfo;
		} else {
			throw new UnknownAccountException();
		}
	}

}
