package com.jardvcode.model.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "book")
@NamedQuery(name = "BookEntity.findAll", query = "SELECT e FROM BookEntity e")
public class BookEntity extends CommonEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column
	private String isbn;
	
	@Column
	private String title;
	
	@Column
	private String author;
	
	@Column(name = "page_number")
	private Integer pageNumber;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "register_date")
	private Date registerDate;
	
	@Column
	private Integer stock;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "book", cascade = CascadeType.ALL)
	private Set<ExemplarEntity> exemplaries = new HashSet<>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "book")
	private Set<ReservationEntity> reservations = new HashSet<>();

	public BookEntity() {
		super();
	}

	public BookEntity(String isbn, String title, String author, Integer pageNumber, Date registerDate,
			Integer stock) {
		super();
		this.isbn = isbn;
		this.title = title;
		this.author = author;
		this.pageNumber = pageNumber;
		this.registerDate = registerDate;
		this.stock = stock;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitles(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Set<ExemplarEntity> getExemplaries() {
		return exemplaries;
	}

	public void setExemplaries(Set<ExemplarEntity> exemplaries) {
		this.exemplaries = exemplaries;
	}

	public Set<ReservationEntity> getReservations() {
		return reservations;
	}

	public void setReservations(Set<ReservationEntity> reservations) {
		this.reservations = reservations;
	}

	@Override
	public int hashCode() {
		return Objects.hash(isbn);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BookEntity other = (BookEntity) obj;
		return Objects.equals(isbn, other.isbn);
	}

	@Override
	public String toString() {
		return "BookEntity [id=" + id + ", isbn=" + isbn + ", title=" + title + ", author=" + author + ", pageNumber="
				+ pageNumber + ", registerDate=" + registerDate + ", stock=" + stock + ", exemplaries=" + exemplaries
				+ ", reservations=" + reservations + "]";
	}

}
