package com.jardvcode.business.service;

import com.jardvcode.model.entity.PenaltyEntity;

public interface PenaltyService extends CrudService<PenaltyEntity, Long> {

	public PenaltyEntity findActivePenaltyByUserId(Long userId);

}
