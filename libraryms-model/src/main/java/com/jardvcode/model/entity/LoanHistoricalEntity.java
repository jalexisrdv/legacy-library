package com.jardvcode.model.entity;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "loan_historical")
public class LoanHistoricalEntity extends CommonEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "lend_date")
	private Date lendDate;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "return_date")
	private Date returnDate;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "real_return_date")
	private Date realReturnDate;
	
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
	private UserEntity user;
	
	@ManyToOne
	@JoinColumn(name = "book_exemplary_id", referencedColumnName = "id", nullable = false)
	private ExemplarEntity exemplaryBook;

	public LoanHistoricalEntity() {
		super();
	}

	public LoanHistoricalEntity(Date lendDate, Date returnDate, Date realReturnDate, UserEntity user,
			ExemplarEntity exemplaryBook) {
		super();
		this.lendDate = lendDate;
		this.returnDate = returnDate;
		this.realReturnDate = realReturnDate;
		this.user = user;
		this.exemplaryBook = exemplaryBook;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Date getRealReturnDate() {
		return realReturnDate;
	}

	public void setRealReturnDate(Date realReturnDate) {
		this.realReturnDate = realReturnDate;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public ExemplarEntity getExemplaryBook() {
		return exemplaryBook;
	}

	public void setExemplaryBook(ExemplarEntity exemplaryBook) {
		this.exemplaryBook = exemplaryBook;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LoanHistoricalEntity other = (LoanHistoricalEntity) obj;
		return Objects.equals(id, other.getId());
	}

}
