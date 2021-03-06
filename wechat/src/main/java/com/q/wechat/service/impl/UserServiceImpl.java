package com.q.wechat.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.q.wechat.dao.IUserDao;
import com.q.wechat.entity.meten.UserBean;
import com.q.wechat.service.IUserService;

@Service
@Transactional
public class UserServiceImpl implements IUserService{
	
	@Resource
	private IUserDao userDao;
	
	@Override
	public int addUser(UserBean user) {
		// TODO Auto-generated method stub
		return userDao.addUser(user);
	}

	@Override
	public UserBean selectById(Integer id) {
		// TODO Auto-generated method stub
		return userDao.selectById(id);
	}

	@Override
	public UserBean selectByOpenid(String openid) {
		// TODO Auto-generated method stub
		return userDao.selectByOpenid(openid);
	}

	@Override
	public int updateUser(UserBean user) {
		// TODO Auto-generated method stub
		return userDao.updateUser(user);
	}

}
