package com.jardvcode.business.service;

import java.util.List;

import com.jardvcode.model.entity.ExemplarEntity;

public interface ExemplarService extends CrudService<ExemplarEntity, Long> {

	public ExemplarEntity findExemplarBorrowedByBookIdAndUserId(Long bookId, Long userId);

	public ExemplarEntity findExemplarByBookIdAndExemplarId(Long bookId, String exemplarId);
	
	public List<ExemplarEntity> findExemplaresByUserId(Long userId);

}
