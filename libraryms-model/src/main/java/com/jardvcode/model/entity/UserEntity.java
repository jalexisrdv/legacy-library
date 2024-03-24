package com.jardvcode.model.entity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DiscriminatorOptions;

@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorOptions(force = true)
public abstract class UserEntity extends CommonEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(unique = true)
	private String nickname;
	
	@Column
	private String password;
	
	@Column(unique = true)
	private String mail;
	
	@Column
	private String name;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Embedded
	private AddressEntity address;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, columnDefinition = "enum('ACTIVE', 'DEFAULTED', 'PENALIZED')")
	private UserStateEnum state;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	private Set<LoanHistoricalEntity> lends = new HashSet<>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	private Set<ExemplarEntity> exemplaries = new HashSet<>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	private Set<ReservationEntity> reservations = new HashSet<>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	private Set<PenaltyEntity> penalties = new HashSet<>();

	public UserEntity() {
		super();
	}

	public UserEntity(String nickname, String password, String mail, String name, String firstName, String lastName,
			AddressEntity address, UserStateEnum state) {
		super();
		this.nickname = nickname;
		this.password = password;
		this.mail = mail;
		this.name = name;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.state = state;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public AddressEntity getAddress() {
		return address;
	}

	public void setAddress(AddressEntity address) {
		this.address = address;
	}

	public UserStateEnum getState() {
		return state;
	}

	public void setState(UserStateEnum state) {
		this.state = state;
	}

	public Set<LoanHistoricalEntity> getLends() {
		return lends;
	}

	public void setLends(Set<LoanHistoricalEntity> lends) {
		this.lends = lends;
	}

	public Set<ReservationEntity> getReservations() {
		return reservations;
	}

	public void setReservations(Set<ReservationEntity> reservations) {
		this.reservations = reservations;
	}

	public Set<PenaltyEntity> getPenalties() {
		return penalties;
	}

	public void setPenalties(Set<PenaltyEntity> penalties) {
		this.penalties = penalties;
	}

	public Set<ExemplarEntity> getExemplaries() {
		return exemplaries;
	}

	public void setExemplaries(Set<ExemplarEntity> exemplaries) {
		this.exemplaries = exemplaries;
	}

	@Override
	public int hashCode() {
		return Objects.hash(nickname);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserEntity other = (UserEntity) obj;
		return Objects.equals(nickname, other.nickname);
	}
	
}
