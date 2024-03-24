package com.jardvcode.business.service;

import com.jardvcode.model.entity.UserEntity;

public interface UserService extends CrudService<UserEntity, Long> {
	
	public UserEntity findByNickname(String nickname);

	public Integer countOperationsByUserId(Long userId);

}
