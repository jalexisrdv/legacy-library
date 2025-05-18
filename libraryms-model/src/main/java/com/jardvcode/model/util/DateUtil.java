package com.jardvcode.model.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public final class DateUtil {

	private DateUtil() {
		throw new RuntimeException("can not be instantiated");
	}

	public static Date toFormatYearMonthDay(Date date) {
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			return simpleDateFormat.parse(simpleDateFormat.format(date));
		} catch (ParseException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	public static Date format(String format, Date date) {
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
			return simpleDateFormat.parse(simpleDateFormat.format(date));
		} catch (ParseException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public static Date addDays(Date date, int days) {
		days = Math.abs(days);
		return add(date, days);
	}

	public static Date substractDays(Date date, int days) {
		days = Math.abs(days);
		return add(date, days * -1);
	}

	private static Date add(Date date, int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, days);
		return format("yyyy-MM-dd", calendar.getTime());
	}

	public static int calculateDifferenceInDays(Date startDate, Date endDate) {
		startDate = format("yyyy-MM-dd", startDate);
		endDate = format("yyyy-MM-dd", endDate);

		long diffInMillies = Math.abs(endDate.getTime() - startDate.getTime());
		long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

		int days = (int) diff;

		return days;
	}

	public static Date parseDate(String date) {
		try {
			return new SimpleDateFormat("yyyy-MM-dd").parse(date);
		} catch (ParseException e) {
			return null;
		}
	}

}
