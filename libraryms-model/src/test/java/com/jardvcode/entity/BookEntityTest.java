package com.jardvcode.entity;

import static org.junit.Assert.*;

import org.junit.Test;

import com.jardvcode.model.entity.BookEntity;
import com.jardvcode.model.entity.ExemplarEntity;
import com.jardvcode.model.entity.ReservationEntity;
import com.jardvcode.util.BookBuilder;
import com.jardvcode.util.ExemplarBuilder;
import com.jardvcode.util.ReservationBuilder;

public class BookEntityTest {

	@Test
	public void book_should_be_equal_when_isbn_is_the_same() {
		BookEntity bookEntity1 = BookBuilder.createBook(1L, "123456789");
		BookEntity bookEntity2 = BookBuilder.createBook(2L, "123456789");
		BookEntity bookEntity3 = BookBuilder.createBook(3L, "987654321");
		
		assertTrue(bookEntity1.equals(bookEntity2));
		assertFalse(bookEntity1.equals(bookEntity3));
	}

	@Test
	public void book_should_find_examplar_by_isbn_and_exemplaryId() {
		BookEntity bookEntity = BookBuilder.createBook(1L, "123456789");
		ExemplarEntity exemplarEntity = ExemplarBuilder.createExemplar(1L, "A", bookEntity);

		bookEntity.getExemplaries().add(exemplarEntity);

		BookEntity bookEntityToFind = BookBuilder.createBook(1L, "123456789");
		ExemplarEntity exemplarEntityToFind = ExemplarBuilder.createExemplar(1L, "A", bookEntityToFind);
		
		assertTrue(bookEntity.getExemplaries().contains(exemplarEntityToFind));
		exemplarEntityToFind.setExemplaryId("B");
		assertFalse(bookEntity.getExemplaries().contains(exemplarEntityToFind));
	}
	
	@Test
	public void book_should_not_duplicate_exemplar_with_same_isbn_and_exemplaryId() {
		BookEntity bookEntity1 = BookBuilder.createBook(1L, "123456789");
		BookEntity bookEntity2 = BookBuilder.createBook(2L, "987654321");
		ExemplarEntity  exemplarEntity1 = ExemplarBuilder.createExemplar(1L, "A", bookEntity1);
		ExemplarEntity  exemplarEntity2 = ExemplarBuilder.createExemplar(2L, "A", bookEntity2);
		ExemplarEntity  exemplarEntity3 = ExemplarBuilder.createExemplar(3L, "A", bookEntity2);;

		BookEntity bookEntity3 = new BookEntity();
		bookEntity3.getExemplaries().add(exemplarEntity1);
		bookEntity3.getExemplaries().add(exemplarEntity2);
		bookEntity3.getExemplaries().add(exemplarEntity3);
		
		assertTrue(bookEntity3.getExemplaries().size() == 2);
	}
	
	@Test
	public void book_should_find_reservation_by_id() {
		BookEntity bookEntity = BookBuilder.createBook(1L, "123456789");
		ReservationEntity reservationEntity = ReservationBuilder.createReservation(1L);
		
		bookEntity.getReservations().add(reservationEntity);
		
		ReservationEntity reservationEntityToFind = ReservationBuilder.createReservation(1L);
		
		assertTrue(bookEntity.getReservations().contains(reservationEntityToFind));
		reservationEntityToFind.setId(2L);
		assertFalse(bookEntity.getReservations().contains(reservationEntityToFind));
	}
	
	@Test
	public void book_should_not_duplicate_reservation_with_same_id() {
		BookEntity bookEntity = BookBuilder.createBook(1L, "123456789");
		ReservationEntity reservationEntity1 = ReservationBuilder.createReservation(1L);
		ReservationEntity reservationEntity2 = ReservationBuilder.createReservation(1L);
		ReservationEntity reservationEntity3 = ReservationBuilder.createReservation(3L);
		
		bookEntity.getReservations().add(reservationEntity1);
		bookEntity.getReservations().add(reservationEntity2);
		bookEntity.getReservations().add(reservationEntity3);
		
		assertTrue(bookEntity.getReservations().size() == 2);
	}

}
