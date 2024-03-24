package com.jardvcode.business.uitl;

import java.util.Date;

import com.jardvcode.business.configuration.ConstantsTest;
import com.jardvcode.model.entity.AddressEntity;
import com.jardvcode.model.entity.BookEntity;
import com.jardvcode.model.entity.BookExemplaryEnum;
import com.jardvcode.model.entity.ExemplarEntity;
import com.jardvcode.model.entity.FinalizationEnum;
import com.jardvcode.model.entity.HistoricalStateEnum;
import com.jardvcode.model.entity.LocationEnum;
import com.jardvcode.model.entity.PenaltyEntity;
import com.jardvcode.model.entity.ReservationEntity;
import com.jardvcode.model.entity.StudentEntity;
import com.jardvcode.model.entity.TeacherEntity;
import com.jardvcode.model.entity.UserEntity;
import com.jardvcode.model.entity.UserStateEnum;
import com.jardvcode.model.util.DateUtil;

public final class DataTest {
	
	public static final AddressEntity ADDRESS = new AddressEntity("20 DE NOVIEMBRE", "SN", "SN", "68430");
	public static final UserEntity STUDENT_USER = createStudentUser();
	public static final UserEntity TEACHER_USER = createTeacherUser();
	public static final UserEntity STUDENT_PENALIZED_USER = new StudentEntity("ADELA ACOSTA MOTA", "gabrielmar", "12345678", "mail1@example.com", "GABRIEL", "HERNANDEZ", "MARTINEZ", ADDRESS, UserStateEnum.ACTIVE);
	public static final UserEntity TEACHER_PENALIZED_USER = new TeacherEntity("COMPUTER SCIENCE", "adelamot", "12345678", "mail4@example.com", "ADELA", "ACOSTA", "MOTA", ADDRESS, UserStateEnum.ACTIVE);
	
	public static final BookEntity BOOK = createBook();
	
	public static final ExemplarEntity A_BOOK_EXEMPLAR = getExemplary("A");
	
	public static final ExemplarEntity STUDENT_A_BOOK_EXEMPLAR_TO_FIND = createExemplaryToFind("A", createStudentToFind());
	public static final ExemplarEntity STUDENT_B_BOOK_EXEMPLAR_TO_FIND = createExemplaryToFind("B", createStudentToFind());
	public static final ExemplarEntity TEACHER_A_BOOK_EXEMPLAR_TO_FIND = createExemplaryToFind("A", createTeacherToFind());
	public static final ExemplarEntity TEACHER_B_BOOK_EXEMPLAR_TO_FIND = createExemplaryToFind("B", createTeacherToFind());
	
	public static final ExemplarEntity STUDENT_A_BOOK_EXEMPLAR_NOT_BORROWED = createExemplaryNotBorrowed("A");
	public static final ExemplarEntity STUDENT_B_BOOK_EXEMPLAR_NOT_BORROWED = createExemplaryNotBorrowed("B");
	public static final ExemplarEntity TEACHER_A_BOOK_EXEMPLAR_NOT_BORROWED = createExemplaryNotBorrowed("A");
	public static final ExemplarEntity TEACHER_B_BOOK_EXEMPLAR_NOT_BORROWED = createExemplaryNotBorrowed("B");
	
	public static final ExemplarEntity STUDENT_A_BOOK_EXEMPLAR_BORROWED = createExemplaryBorrowed("A", new Date(), DateUtil.addDays(new Date(), ConstantsTest.STUDENT_BOOK_LOAN_DAYS), createStudentUser());
	public static final ExemplarEntity STUDENT_B_BOOK_EXEMPLAR_BORROWED = createExemplaryBorrowed("B", new Date(), DateUtil.addDays(new Date(), ConstantsTest.STUDENT_BOOK_LOAN_DAYS), createStudentUser());
	public static final ExemplarEntity TEACHER_A_BOOK_EXEMPLAR_BORROWED = createExemplaryBorrowed("A", new Date(), DateUtil.addDays(new Date(), ConstantsTest.TEACHER_BOOK_LOAN_DAYS), createTeacherUser());
	public static final ExemplarEntity TEACHER_B_BOOK_EXEMPLAR_BORROWED = createExemplaryBorrowed("B", new Date(), DateUtil.addDays(new Date(), ConstantsTest.TEACHER_BOOK_LOAN_DAYS), createTeacherUser());
	public static final ExemplarEntity STUDENT_A_BOOK_EXEMPLAR_BORROWED_WITH_LATE_DELIVERY = createExemplaryBorrowed("A", DateUtil.parseDate("2023-10-07"), DateUtil.parseDate("2023-10-14"), createStudentUser());
	public static final ExemplarEntity STUDENT_B_BOOK_EXEMPLAR_BORROWED_WITH_LATE_DELIVERY = createExemplaryBorrowed("B", DateUtil.parseDate("2023-10-07"), DateUtil.parseDate("2023-10-14"), createStudentUser());
	public static final ExemplarEntity TEACHER_A_BOOK_EXEMPLAR_BORROWED_WITH_LATE_DELIVERY = createExemplaryBorrowed("A", DateUtil.parseDate("2023-10-07"), DateUtil.parseDate("2023-10-14"), createTeacherUser());
	public static final ExemplarEntity TEACHER_B_BOOK_EXEMPLAR_BORROWED_WITH_LATE_DELIVERY = createExemplaryBorrowed("B", DateUtil.parseDate("2023-10-07"), DateUtil.parseDate("2023-10-14"), createTeacherUser());
	
	public static final ReservationEntity STUDENT_ACTIVE_BOOK_RESERVATION = createReservation(createStudentUser());
	public static final ReservationEntity TEACHER_ACTIVE_BOOK_RESERVATION = createReservation(createTeacherUser());
	
	public static final ReservationEntity STUDENT_ACTIVE_BOOK_RESERVATION_TO_FIND = createReservation(createStudentUser());

	public static final PenaltyEntity STUDENT_ACTIVE_PENALTY = createActivePenalty(new Date(), DateUtil.addDays(new Date(), 5), createStudentUser());
	public static final PenaltyEntity TEACHER_ACTIVE_PENALTY = createActivePenalty(new Date(), DateUtil.addDays(new Date(), 5), createTeacherUser());
	public static final PenaltyEntity STUDENT_ACTIVE_PENALTY_EXPIRED = createActivePenalty(DateUtil.parseDate("2023-10-07"), DateUtil.parseDate("2023-10-14"), createStudentUser());
	public static final PenaltyEntity TEACHER_ACTIVE_PENALTY_EXPIRED = createActivePenalty(DateUtil.parseDate("2023-10-07"), DateUtil.parseDate("2023-10-14"), createTeacherUser());
	
	private DataTest() {
		throw new RuntimeException("DataTest can not be instantiated");
	}
	
	private static PenaltyEntity createActivePenalty(Date startDate, Date endData, UserEntity user) {
		startDate = DateUtil.format("yyyy-MM-dd", startDate);
		endData = DateUtil.format("yyyy-MM-dd", endData);
		PenaltyEntity penalty = new PenaltyEntity(startDate, endData, HistoricalStateEnum.ACTIVE, user);
		return penalty;
	}
	
	private static ReservationEntity createReservation(UserEntity user) {
		BookEntity book = createBook();
		ReservationEntity reservation = new ReservationEntity(DateUtil.parseDate("2023-10-15"), null, FinalizationEnum.DONE, HistoricalStateEnum.ACTIVE, book, user);
		reservation.setId(1L);
		return reservation;
	}
	
	private static ExemplarEntity createExemplaryBorrowed(String exemplaryId, Date borrowDate, Date deliveryDate, UserEntity user) {
		ExemplarEntity exemplary = createExemplaryNotBorrowed(exemplaryId);
		exemplary.getBook().setStock(1);
		exemplary.setLendDate(DateUtil.format("yyyy-MM-dd", borrowDate));
		exemplary.setReturnDate(DateUtil.format("yyyy-MM-dd", deliveryDate));
		exemplary.setUser(user);
		exemplary.setState(BookExemplaryEnum.LENT);
		exemplary.setLocation(LocationEnum.DEPARTAMENT);
		return exemplary;
	}
	
	private static ExemplarEntity createExemplaryNotBorrowed(String exemplaryId) {		
		BookEntity book = createBook();
		ExemplarEntity exemplary = new ExemplarEntity(exemplaryId, DateUtil.parseDate("2023-10-15"), "NEW BOOK", null, null, BookExemplaryEnum.AVAILABLE, LocationEnum.HOME, book, null);
		exemplary.setId(1L);
		return exemplary;
	}
	
	private static ExemplarEntity createExemplaryToFind(String exemplaryId, UserEntity user) {
		BookEntity book = new BookEntity();
		book.setIsbn("9780132350884");
		ExemplarEntity exemplary = new ExemplarEntity(exemplaryId, DateUtil.parseDate("2023-10-15"), "NEW BOOK", null, null, BookExemplaryEnum.AVAILABLE, LocationEnum.HOME, book, user);
		return exemplary;
	}
	
	private static ExemplarEntity getExemplary(String exemplaryId) {
		BookEntity book = new BookEntity();
		book.setIsbn("9780132350884");
		
		ExemplarEntity exemplary = new ExemplarEntity(exemplaryId, DateUtil.parseDate("2023-10-15"), "NEW BOOK", null, null, BookExemplaryEnum.AVAILABLE, LocationEnum.HOME, book, null);
		
		return exemplary;
	}
	
	private static BookEntity createBook() {
		BookEntity book = new BookEntity("9780132350884", "Clean Code", "Robert C. Martin", 464, DateUtil.parseDate("2008-08-01"), 2);
		book.setId(1L);
		return book;
	}
	
	private static StudentEntity createStudentUser() {
		StudentEntity user = new StudentEntity("ADELA ACOSTA MOTA", "gabrielmar", "12345678", "mail1@example.com", "GABRIEL", "HERNANDEZ", "MARTINEZ", ADDRESS, UserStateEnum.ACTIVE);
		user.setId(1L);
		return user;
	}
	
	private static TeacherEntity createTeacherUser() {
		TeacherEntity user = new TeacherEntity("COMPUTER SCIENCE", "adelamot", "12345678", "mail4@example.com", "ADELA", "ACOSTA", "MOTA", ADDRESS, UserStateEnum.ACTIVE);
		user.setId(2L);
		return user;
	}
	
	private static UserEntity createStudentToFind() {
		UserEntity user = new StudentEntity();
		user.setNickname("gabrielmar");
		return user;
	}
	
	private static UserEntity createTeacherToFind() {
		UserEntity user = new TeacherEntity();
		user.setNickname("adelamot");
		return user;
	}

}
