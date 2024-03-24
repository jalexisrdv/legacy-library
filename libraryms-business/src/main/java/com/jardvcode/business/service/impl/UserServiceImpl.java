package com.jardvcode.business.service.impl;

import javax.persistence.EntityManagerFactory;

import com.jardvcode.business.service.UserService;
import com.jardvcode.model.dao.UserDao;
import com.jardvcode.model.entity.UserEntity;

public class UserServiceImpl implements UserService {

	private EntityManagerFactory entityManagerFactory;
	private UserDao userDao;
	
	public UserServiceImpl(EntityManagerFactory entityManagerFactory, UserDao userDao) {
		this.entityManagerFactory = entityManagerFactory;
		this.userDao = userDao;
	}

	@Override
	public UserEntity create(UserEntity entity) {
		return null;
	}

	@Override
	public UserEntity findById(Long id) {
		return null;
	}

	@Override
	public UserEntity updateById(UserEntity entity) {
		return null;
	}

	@Override
	public void deleteById(Long id) {
		
	}

	@Override
	public UserEntity findByNickname(String nickname) {
		return null;
	}

	@Override
	public Integer countOperationsByUserId(Long userId) {
		return null;
	}

}
