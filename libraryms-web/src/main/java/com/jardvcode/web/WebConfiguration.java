package com.jardvcode.web;

public class WebConfiguration {
	
	public final static Integer PAGINATION_SIZE = 2;
	
	public static Long getPaginationSize(Long itemNumber) {
		return (long) Math.ceil(Double.valueOf(itemNumber) / Double.valueOf(WebConfiguration.PAGINATION_SIZE));
	}
	
}
