package com.jardvcode.business.configuration;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jardvcode.business.exception.LibraryException;
import com.jardvcode.model.entity.StudentEntity;
import com.jardvcode.model.entity.TeacherEntity;
import com.jardvcode.model.entity.UserEntity;
import com.jardvcode.model.util.DateUtil;

public class LibraryConfiguration {

	private int studentBookLoanDays = 7;
	private int teacherBookLoanDays = 30;
	private int studentBookLoanLimit = 5;
	private int teacherBookLoanLimit = 8;

	private static Log logger = LogFactory.getLog(LibraryConfiguration.class);

	private static LibraryConfiguration me = new LibraryConfiguration();

	private LibraryConfiguration() {
		logger.debug("Instance created of " + LibraryConfiguration.class);
	}

	public static LibraryConfiguration getInstance() {
		return me;
	}

	public Integer getBookLoanDaysOf(UserEntity user) throws LibraryException {
		if (user instanceof StudentEntity) {
			return studentBookLoanDays;
		} else if (user instanceof TeacherEntity) {
			return teacherBookLoanDays;
		} else {
			String msg = "Only students and teachers can request loans";
			logger.error(msg);
			throw new LibraryException(msg);
		}
	}

	public void checkBookLoanLimitOf(UserEntity user, int loanNumber) throws LibraryException {
		String msg;
		if (!(user instanceof StudentEntity) && !(user instanceof TeacherEntity)) {
			msg = "Only students and teachers can have borrow books";
			logger.error(msg);
			throw new LibraryException(msg);
		}
		if ((user instanceof StudentEntity && loanNumber > studentBookLoanLimit)
				|| (user instanceof TeacherEntity && loanNumber > teacherBookLoanLimit)) {
			msg = "The limit of possible operations is full";
			logger.error(msg);
			throw new LibraryException(msg);
		}
	}

	public Integer getBookLoanLimitOf(UserEntity user) throws LibraryException {
		if (user instanceof StudentEntity)
			return studentBookLoanLimit;
		else if (user instanceof TeacherEntity)
			return teacherBookLoanLimit;
		else {
			String msg = "Only students and teachers can have borrow books";
			logger.error(msg);
			throw new LibraryException(msg);
		}
	}
	
	public Date calculateBookReturnDate(Date date, UserEntity user) {
		if (user instanceof StudentEntity) {
			return DateUtil.addDays(date, studentBookLoanDays);
		} else if (user instanceof TeacherEntity) {
			return DateUtil.addDays(date, teacherBookLoanDays);
		} else {
			String msg = "Only students and teachers can request loans";
			logger.error(msg);
			throw new LibraryException(msg);
		}
	}
	
	public Date calculatePenaltyEndDate(Date startDate, Date endDate) {
		int delayDays = DateUtil.calculateDifferenceInDays(startDate, endDate);
		return DateUtil.addDays(new Date(), delayDays * 2);
	}

}
