package com.jardvcode.business.uitl;

import java.util.Date;

import com.jardvcode.model.entity.FinalizationEnum;
import com.jardvcode.model.entity.HistoricalStateEnum;
import com.jardvcode.model.entity.ReservationEntity;
import com.jardvcode.util.BookBuilder;
import com.jardvcode.util.ReservationBuilder;
import com.jardvcode.util.UserBuilder;

public class ReservationDataTest {
	
	public static ReservationEntity RESERVATION_SAVED = ReservationBuilder.createReservation(
			1L, new Date(), new Date(),
			FinalizationEnum.DONE, HistoricalStateEnum.HISTORICAL, 
			BookBuilder.createBook(1L), UserBuilder.createStudentUser(1L)
	);

}
