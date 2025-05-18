package com.jardvcode.model.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class AddressEntity {

	@Column
	private String street;
	
	@Column
	private String number;
	
	@Column
	private String flat;
	
	@Column(name = "postal_code")
	private String postalCode;
	
	public AddressEntity() {
		
	}

	public AddressEntity(String street, String number, String flat, String postalCode) {
		super();
		this.street = street;
		this.number = number;
		this.flat = flat;
		this.postalCode = postalCode;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getFlat() {
		return flat;
	}

	public void setFlat(String flat) {
		this.flat = flat;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	@Override
	public int hashCode() {
		return Objects.hash(flat, number, postalCode, street);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AddressEntity other = (AddressEntity) obj;
		return Objects.equals(flat, other.flat) && Objects.equals(number, other.number)
				&& Objects.equals(postalCode, other.postalCode) && Objects.equals(street, other.street);
	}
	
}
