package com.jardvcode.business.service;

import com.jardvcode.model.entity.ExemplarEntity;

public interface ExemplarService extends CrudService<ExemplarEntity, Long> {

	public ExemplarEntity findExemplarBorrowedByBookIdAndUserId(Long bookId, Long userId);

	public ExemplarEntity findExemplarByBookIdAndExemplarId(Long bookId, String exemplarId);

}
