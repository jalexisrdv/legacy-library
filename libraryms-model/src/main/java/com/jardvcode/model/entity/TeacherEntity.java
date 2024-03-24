package com.jardvcode.model.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = UserEnum.TEACHER)
public class TeacherEntity extends UserEntity {
	
	@Column
	private String department;

	public TeacherEntity() {
		super();
	}

	public TeacherEntity(String department, String nickname, String password, String mail, String name, String firstName, String lastName,
			AddressEntity address, UserStateEnum state) {
		super(nickname, password, mail, name, firstName, lastName, address, state);
		this.department = department;
	}
	
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	
}
