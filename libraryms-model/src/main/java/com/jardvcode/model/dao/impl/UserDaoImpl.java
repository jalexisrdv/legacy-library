package com.jardvcode.model.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.jardvcode.model.dao.UserDao;
import com.jardvcode.model.entity.UserEntity;

public class UserDaoImpl extends CrudDaoImpl<UserEntity, Long> implements UserDao {

	public UserDaoImpl(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public UserEntity findById(Long id) {
		try {
			return entityManager.find(UserEntity.class, id);
		} catch(Exception e) {
			return null;
		}
	}
	
	public UserEntity findUserByNickname(String nickname) {
		try {
			Query query = entityManager.createQuery("SELECT u FROM UserEntity U WHERE u.nickname = :nickname");
			query.setParameter("nickname", nickname);
			return (UserEntity) query.getSingleResult();
		} catch(Exception e) {
			return null;
		}
	}

}
