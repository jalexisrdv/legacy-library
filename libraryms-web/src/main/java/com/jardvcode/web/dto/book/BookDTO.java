package com.jardvcode.web.dto.book;

public class BookDTO {
	
	private String isbn;
	private String exemplarNumber;
	private String title;
	private String deliveryDate;
	private String observations;
	
	public String getIsbn() {
		return isbn;
	}
	
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	
	public String getExemplarNumber() {
		return exemplarNumber;
	}
	
	public void setExemplarNumber(String exemplarNumber) {
		this.exemplarNumber = exemplarNumber;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDeliveryDate() {
		return deliveryDate;
	}
	
	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	
	public String getObservations() {
		return observations;
	}
	
	public void setObservations(String observations) {
		this.observations = observations;
	}

}
