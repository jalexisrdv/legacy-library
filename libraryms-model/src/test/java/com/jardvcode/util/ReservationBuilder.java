package com.jardvcode.util;

import java.util.Date;

import com.jardvcode.model.entity.BookEntity;
import com.jardvcode.model.entity.FinalizationEnum;
import com.jardvcode.model.entity.HistoricalStateEnum;
import com.jardvcode.model.entity.ReservationEntity;
import com.jardvcode.model.entity.UserEntity;

public final class ReservationBuilder {
	
	private ReservationBuilder() {
		throw new RuntimeException("Can not be instantiated");
	}
	
	public static ReservationEntity createReservation(Long id) {
		ReservationEntity reservation = new ReservationEntity();
		reservation.setId(id);
		return reservation;
	}
	
	public static ReservationEntity createReservation(Long id, Date startDate, Date endDate, FinalizationEnum finalizationState, HistoricalStateEnum historicalState, BookEntity book, UserEntity user) {
		ReservationEntity reservation = createReservation(startDate, endDate, finalizationState, historicalState, book, user);
		reservation.setId(id);
		return reservation;
	}
	
	public static ReservationEntity createReservation(Date startDate, Date endDate, FinalizationEnum finalizationState, HistoricalStateEnum historicalState, BookEntity book, UserEntity user) {
		ReservationEntity reservation = new ReservationEntity();
		reservation.setStartDate(startDate);
		reservation.setEndDate(endDate);
		reservation.setFinalizationState(finalizationState);
		reservation.setHistoricalState(historicalState);
		reservation.setBook(book);
		reservation.setUser(user);
		return reservation;
	}

}
