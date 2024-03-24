package com.jardvcode.business.uitl;

import java.util.Date;

import com.jardvcode.model.entity.HistoricalStateEnum;
import com.jardvcode.model.entity.PenaltyEntity;
import com.jardvcode.util.PenaltyBuilder;
import com.jardvcode.util.UserBuilder;

public class PenaltyDataTest {
	
	public static final PenaltyEntity PENALTY_SAVED = PenaltyBuilder.createPenalty(
			1L, new Date(), new Date(), 
			HistoricalStateEnum.ACTIVE, UserBuilder.createStudentUser(1L)
	);

}
