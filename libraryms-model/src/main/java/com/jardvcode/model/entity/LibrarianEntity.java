package com.jardvcode.model.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "librarian")
@NamedQuery(name = "LibrarianEntity.findAll", query = "SELECT e FROM LibrarianEntity e")
public class LibrarianEntity extends CommonEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column
	private String nickname;
	
	@Column
	private String password;
	
	@Column
	private String nif;

	public LibrarianEntity() {
		super();
	}

	public LibrarianEntity(String nickname, String password, String nif) {
		super();
		this.nickname = nickname;
		this.password = password;
		this.nif = nif;
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

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
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
		LibrarianEntity other = (LibrarianEntity) obj;
		return Objects.equals(nif, other.nif);
	}
	
}
