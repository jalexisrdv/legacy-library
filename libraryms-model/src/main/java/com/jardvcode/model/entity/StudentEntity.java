package com.jardvcode.model.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = UserEnum.STUDENT)
public class StudentEntity extends UserEntity {
	
	@Column
	private String tutor;
	
	public StudentEntity() {
		super();
	}

	public StudentEntity(String tutor, String nickname, String password, String mail, String name, String firstName, String lastName,
			AddressEntity address, UserStateEnum state) {
		super(nickname, password, mail, name, firstName, lastName, address, state);
		this.tutor = tutor;
	}

	public String getTutor() {
		return tutor;
	}

	public void setTutor(String tutor) {
		this.tutor = tutor;
	}

}
