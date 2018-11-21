package com.q.wechat.service;

import com.q.wechat.entity.meten.UserBean;

public interface IUserService {
	int addUser(UserBean user);
	UserBean selectById(Integer id);
	UserBean selectByOpenid(String openid);
	int updateUser(UserBean user);
}
