package com.testtask.clinic.database.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PATIENTS")
public class Patient {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	private String uuid;
    private String name;
    private LocalDate date_of_birth;
    
	public Patient() {
	}
	
	public Patient(long id, String uuid, String name, LocalDate date_of_birth) {
		super();
		this.id = id;
		this.uuid = uuid;
		this.name = name;
		this.date_of_birth = date_of_birth;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalDate getDate_of_birth() {
		return date_of_birth;
	}

	public void setDate_of_birth(LocalDate date_of_birth) {
		this.date_of_birth = date_of_birth;
	}
}
