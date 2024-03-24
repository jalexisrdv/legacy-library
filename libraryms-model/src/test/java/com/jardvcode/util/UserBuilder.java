package com.jardvcode.util;

import com.jardvcode.model.entity.AddressEntity;
import com.jardvcode.model.entity.StudentEntity;
import com.jardvcode.model.entity.TeacherEntity;
import com.jardvcode.model.entity.UserEntity;
import com.jardvcode.model.entity.UserStateEnum;

public final class UserBuilder {
	
	private UserBuilder() {
		throw new RuntimeException("Can not be instantiated");
	}
	
	public static UserEntity createStudentUser(Long id) {
		return createUser(id, null, new StudentEntity());
	}
	
	public static UserEntity createTeacherUser(Long id) {
		return createUser(id, null, new TeacherEntity());
	}
	
	public static UserEntity createStudentUser(Long id, String nickname) {
		return createUser(id, nickname, new StudentEntity());
	}
	
	public static UserEntity createTeacherUser(Long id, String nickname) {
		return createUser(id, nickname, new TeacherEntity());
	}
	
	public static UserEntity createStudentUser(Long id, String nickname, String password, String email, String name, String firstName, String lastName, UserStateEnum state, AddressEntity addressEntity) {
		return createUser(id, nickname, password, email, name, firstName, lastName, state, addressEntity, new StudentEntity());
	}
	
	public static UserEntity createTeacherUser(Long id, String nickname, String password, String email, String name, String firstName, String lastName, UserStateEnum state, AddressEntity addressEntity) {
		return createUser(id, nickname, password, email, name, firstName, lastName, state, addressEntity,new TeacherEntity());
	}
	
	public static UserEntity createStudentUser(String nickname, String password, String email, String name, String firstName, String lastName, UserStateEnum state, AddressEntity addressEntity) {
		return createUser(nickname, password, email, name, firstName, lastName, state, addressEntity, new StudentEntity());
	}
	
	public static UserEntity createTeacherUser(String nickname, String password, String email, String name, String firstName, String lastName, UserStateEnum state, AddressEntity addressEntity) {
		return createUser(nickname, password, email, name, firstName, lastName, state, addressEntity,new TeacherEntity());
	}
	
	public static UserEntity createUser(Long id, String nickname, UserEntity user) {
		user.setId(id);
		user.setNickname(nickname);
		return user;
	}
	
	public static UserEntity createUser(Long id, String nickname, String password, String email, String name, String firstName, String lastName, UserStateEnum state, AddressEntity addressEntity, UserEntity user) {
		user = createTeacherUser(nickname, password, email, name, firstName, lastName, state, addressEntity);
		user.setId(id);
		return user;
	}
	
	public static UserEntity createUser(String nickname, String password, String email, String name, String firstName, String lastName, UserStateEnum state, AddressEntity addressEntity, UserEntity user) {
		user.setNickname(nickname);
		user.setPassword(password);
		user.setMail(email);
		user.setName(name);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setState(state);
		user.setAddress(addressEntity);
		return user;
	}

}
