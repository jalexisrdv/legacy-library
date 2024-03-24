package com.jardvcode.business.uitl;

import com.jardvcode.model.entity.AddressEntity;
import com.jardvcode.model.entity.UserEntity;
import com.jardvcode.model.entity.UserStateEnum;
import com.jardvcode.util.UserBuilder;

public class UserDataTest {
	
	public static final UserEntity USER_SAVED = UserBuilder.createStudentUser(
			1L,
			"elizabethmz", "12345678", "mail7@example.com", 
			"elizabeth", "evangelista", "ramirez", UserStateEnum.ACTIVE, 
			new AddressEntity("20 DE NOVIEMBRE", "SN", "SN", "68410")
	);

}
