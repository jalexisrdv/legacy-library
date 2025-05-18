package com.jardvcode.util;

import java.util.Date;

import com.jardvcode.model.entity.HistoricalStateEnum;
import com.jardvcode.model.entity.PenaltyEntity;
import com.jardvcode.model.entity.UserEntity;

public final class PenaltyBuilder {
	
	private PenaltyBuilder() {
		throw new RuntimeException("Can not be instantiated");
	}
	
	public static PenaltyEntity createPenalty(Long id) {
		PenaltyEntity penalty = new PenaltyEntity();
		penalty.setId(id);
		return penalty;
	}
	
	public static PenaltyEntity createPenalty(Long id, Date startDate, Date endDate, HistoricalStateEnum state, UserEntity user) {
		PenaltyEntity penalty = createPenalty(startDate, endDate, state, user);
		penalty.setId(id);
		return penalty;
	}
	
	public static PenaltyEntity createPenalty(Date startDate, Date endDate, HistoricalStateEnum state, UserEntity user) {
		PenaltyEntity penalty = new PenaltyEntity();
		penalty.setStartDate(startDate);
		penalty.setEndDate(endDate);
		penalty.setState(state);
		penalty.setUser(user);
		return penalty;
	}

}
