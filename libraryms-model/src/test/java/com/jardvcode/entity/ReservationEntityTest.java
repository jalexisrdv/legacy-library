package com.jardvcode.entity;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.jardvcode.model.entity.ReservationEntity;
import com.jardvcode.util.ReservationBuilder;

public class ReservationEntityTest {

	@Test
	public void reservation_should_be_equal_when_id_is_the_same() {
		ReservationEntity reservationEntity1 = ReservationBuilder.createReservation(1L);
		ReservationEntity reservationEntity2 = ReservationBuilder.createReservation(1L);
		ReservationEntity reservationEntity3 = ReservationBuilder.createReservation(3L);
		
		assertTrue(reservationEntity1.equals(reservationEntity2));
		assertFalse(reservationEntity1.equals(reservationEntity3));
	}

}
