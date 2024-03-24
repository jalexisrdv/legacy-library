package com.jardvcode.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import com.jardvcode.model.entity.PenaltyEntity;
import com.jardvcode.util.PenaltyBuilder;

public class PenaltyEntityTest {

	@Test
	public void penalty_should_be_equal_when_id_is_the_same() {
		PenaltyEntity penaltyEntity1 = PenaltyBuilder.createPenalty(1L);
		PenaltyEntity penaltyEntity2 = PenaltyBuilder.createPenalty(1L);
		PenaltyEntity penaltyEntity3 = PenaltyBuilder.createPenalty(3L);
		
		assertEquals(penaltyEntity1, penaltyEntity2);
		assertFalse(penaltyEntity1.equals(penaltyEntity3));
	}

}
