package com.jardvcode.model.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "book_exemplary")
public class ExemplarEntity extends CommonEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "exemplary_id")
	private String exemplaryId;

	@Temporal(TemporalType.DATE)
	@Column(name = "acquisition_date")
	private Date acquisitionDate;

	@Column
	private String observation;

	@Temporal(TemporalType.DATE)
	@Column(name = "lend_date")
	private Date lendDate;

	@Temporal(TemporalType.DATE)
	@Column(name = "return_date")
	private Date returnDate;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, columnDefinition = "enum('AVAILABLE', 'LENT')")
	private BookExemplaryEnum state;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, columnDefinition = "enum('SITTING_ROOM', 'HOME', 'DEPARMENT')")
	private LocationEnum location;

	@ManyToOne
	@JoinColumn(name = "book_id", referencedColumnName = "id", nullable = false)
	private BookEntity book;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exemplaryBook")
	private Set<LoanHistoricalEntity> lends = new HashSet<>();

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private UserEntity user;

	public ExemplarEntity() {
		super();
	}

	public ExemplarEntity(String exemplaryId, Date acquisitionDate, String observation, Date lendDate,
			Date returnDate, BookExemplaryEnum state, LocationEnum location, BookEntity book, UserEntity user) {
		super();
		this.exemplaryId = exemplaryId;
		this.acquisitionDate = acquisitionDate;
		this.observation = observation;
		this.lendDate = lendDate;
		this.returnDate = returnDate;
		this.state = state;
		this.location = location;
		this.book = book;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getExemplaryId() {
		return exemplaryId;
	}

	public void setExemplaryId(String exemplaryId) {
		this.exemplaryId = exemplaryId;
	}

	public Date getAcquisitionDate() {
		return acquisitionDate;
	}

	public void setAcquisitionDate(Date acquisitionDate) {
		this.acquisitionDate = acquisitionDate;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public Date getLendDate() {
		return lendDate;
	}

	public void setLendDate(Date lendDate) {
		this.lendDate = lendDate;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public BookEntity getBook() {
		return book;
	}

	public void setBook(BookEntity book) {
		this.book = book;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public Set<LoanHistoricalEntity> getLends() {
		return lends;
	}

	public void setLends(Set<LoanHistoricalEntity> lends) {
		this.lends = lends;
	}

	public BookExemplaryEnum getState() {
		return state;
	}

	public void setState(BookExemplaryEnum state) {
		this.state = state;
	}

	public LocationEnum getLocation() {
		return location;
	}

	public void setLocation(LocationEnum location) {
		this.location = location;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(exemplaryId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExemplarEntity other = (ExemplarEntity) obj;
		return Objects.equals(book, other.book) && Objects.equals(exemplaryId, other.exemplaryId);
	}

}
