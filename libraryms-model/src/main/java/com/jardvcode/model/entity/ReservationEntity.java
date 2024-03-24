package com.jardvcode.model.entity;

import java.util.Date;
import java.util.Objects;

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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "reservation")
public class ReservationEntity extends CommonEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "start_date")
	private Date startDate;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "end_date")
	private Date endDate;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "finalization_state", nullable = false, columnDefinition = "enum('CANCELED', 'DONE')")
	private FinalizationEnum finalizationState;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "historical_state", nullable = false, columnDefinition = "enum('ACTIVE', 'HISTORICAL')")
	private HistoricalStateEnum historicalState;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name ="book_id", referencedColumnName = "id", nullable = false)
	private BookEntity book;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name ="user_id", referencedColumnName = "id", nullable = false)
	private UserEntity user;

	public ReservationEntity() {
		super();
	}

	public ReservationEntity(Date startDate, Date endDate, FinalizationEnum finalizationState,
			HistoricalStateEnum historicalState, BookEntity book, UserEntity user) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.finalizationState = finalizationState;
		this.historicalState = historicalState;
		this.book = book;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public FinalizationEnum getFinalizationState() {
		return finalizationState;
	}

	public void setFinalizationState(FinalizationEnum finalizationState) {
		this.finalizationState = finalizationState;
	}

	public HistoricalStateEnum getHistoricalState() {
		return historicalState;
	}

	public void setHistoricalState(HistoricalStateEnum historicalState) {
		this.historicalState = historicalState;
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
		ReservationEntity other = (ReservationEntity) obj;
		return Objects.equals(id, other.id);
	}
	
}
