package com.jardvcode.business.uitl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.jardvcode.model.entity.BookExemplaryEnum;
import com.jardvcode.model.entity.ExemplarEntity;
import com.jardvcode.model.entity.LocationEnum;
import com.jardvcode.util.BookBuilder;
import com.jardvcode.util.ExemplarBuilder;
import com.jardvcode.util.UserBuilder;

public class ExemplarDataTest {
	
	public static final ExemplarEntity EXEMPLAR_SAVED = ExemplarBuilder.createExemplar(
			1L, "C", new Date(), "New Book",
			new Date(), new Date(),
			BookExemplaryEnum.AVAILABLE, LocationEnum.SITTING_ROOM, 
			BookBuilder.createBook(1L), UserBuilder.createStudentUser(1L)
	);
	
	public static final List<ExemplarEntity> EXEMPLARES = Arrays.asList(EXEMPLAR_SAVED);

}
