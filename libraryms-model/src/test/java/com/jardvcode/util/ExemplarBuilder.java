package com.jardvcode.util;

import java.util.Date;

import com.jardvcode.model.entity.BookEntity;
import com.jardvcode.model.entity.BookExemplaryEnum;
import com.jardvcode.model.entity.ExemplarEntity;
import com.jardvcode.model.entity.LocationEnum;
import com.jardvcode.model.entity.UserEntity;

public final class ExemplarBuilder {
	
	private ExemplarBuilder() {
		throw new RuntimeException("Can not be instantiated");
	}
	
	public static ExemplarEntity createExemplar(Long id) {
		ExemplarEntity exemplar = new ExemplarEntity();
		exemplar.setId(id);
		return exemplar;
	}

	public static ExemplarEntity createExemplar(Long id, String exemplarId, BookEntity book) {
		ExemplarEntity exemplar = new ExemplarEntity();
		exemplar.setId(id);
		exemplar.setExemplaryId(exemplarId);
		exemplar.setBook(book);
		return exemplar;
	}
	
	public static ExemplarEntity createExemplar(Long id, String exemplarId, Date adquisitionDate, String observation, Date loanDate, Date expectedDeliveryDate, BookExemplaryEnum state, LocationEnum location, BookEntity book, UserEntity user) {
		ExemplarEntity exemplar = createExemplar(exemplarId, adquisitionDate, observation, loanDate, expectedDeliveryDate, state, location, book, user);
		exemplar.setId(id);
		return exemplar;
	}
	
	public static ExemplarEntity createExemplar(String exemplarId, Date adquisitionDate, String observation, Date loanDate, Date expectedDeliveryDate, BookExemplaryEnum state, LocationEnum location, BookEntity book, UserEntity user) {
		ExemplarEntity exemplar = new ExemplarEntity();
		exemplar.setExemplaryId(exemplarId);
		exemplar.setAcquisitionDate(adquisitionDate);
		exemplar.setObservation(observation);
		exemplar.setLendDate(loanDate);
		exemplar.setReturnDate(expectedDeliveryDate);
		exemplar.setState(state);
		exemplar.setLocation(location);
		exemplar.setBook(book);
		exemplar.setUser(user);
		return exemplar;
	}

}
