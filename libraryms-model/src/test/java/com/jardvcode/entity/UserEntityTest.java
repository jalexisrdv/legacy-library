package com.jardvcode.entity;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.jardvcode.model.entity.PenaltyEntity;
import com.jardvcode.model.entity.UserEntity;
import com.jardvcode.util.PenaltyBuilder;
import com.jardvcode.util.UserBuilder;

public class UserEntityTest {

	@Test
	public void user_should_be_equal_when_nickname_is_the_same() {
		UserEntity userEntity1 = UserBuilder.createStudentUser(1L, "alexis");
		UserEntity userEntity2 = UserBuilder.createStudentUser(1L, "alexis");
		UserEntity userEntity3 = UserBuilder.createStudentUser(1L, "jose");
		
		assertTrue(userEntity1.equals(userEntity2));
		assertFalse(userEntity1.equals(userEntity3));
	}
	
	@Test
	public void user_should_find_penalty_by_id() {
		UserEntity userEntity = UserBuilder.createStudentUser(1L, "alexis");
		PenaltyEntity penaltyEntity = PenaltyBuilder.createPenalty(1L);
		
		userEntity.getPenalties().add(penaltyEntity);
		
		PenaltyEntity penaltyEntityToFind = PenaltyBuilder.createPenalty(1L);
		
		assertTrue(userEntity.getPenalties().contains(penaltyEntityToFind));
		penaltyEntityToFind.setId(2L);
		assertFalse(userEntity.getPenalties().contains(penaltyEntityToFind));
	}
	
	@Test
	public void user_should_not_duplicate_penalty_with_same_id() {
		UserEntity userEntity = UserBuilder.createStudentUser(1L, "alexis");
		
		PenaltyEntity penaltyEntity1 = PenaltyBuilder.createPenalty(1L);
		PenaltyEntity penaltyEntity2 = PenaltyBuilder.createPenalty(1L);
		penaltyEntity2.setUser(userEntity);
		PenaltyEntity penaltyEntity3 = PenaltyBuilder.createPenalty(3L);
		
		userEntity.getPenalties().add(penaltyEntity1);
		userEntity.getPenalties().add(penaltyEntity2);
		userEntity.getPenalties().add(penaltyEntity3);
		
		assertTrue(userEntity.getPenalties().size() == 2);
	}

}
