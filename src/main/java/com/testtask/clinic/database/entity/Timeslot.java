package com.testtask.clinic.database.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TIMESLOTS")
public class Timeslot {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	
	@Column(name = "doctor_id")
	private long doctorId;
	@Column(name = "patient_id")
	private Long patientId;
    private LocalTime starttime;
    private LocalDate visitdate;
    
    public Timeslot() {
	}

	public Timeslot(long id, long doctorId, Long patientId, LocalTime starttime, LocalDate visitdate) {
		super();
		this.id = id;
		this.doctorId = doctorId;
		this.patientId = patientId;
		this.starttime = starttime;
		this.visitdate = visitdate;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(long doctorId) {
		this.doctorId = doctorId;
	}

	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

	public LocalTime getStarttime() {
		return starttime;
	}

	public void setStarttime(LocalTime starttime) {
		this.starttime = starttime;
	}

	public LocalDate getVisitdate() {
		return visitdate;
	}

	public void setVisitdate(LocalDate visitdate) {
		this.visitdate = visitdate;
	}

	@Override
	public String toString() {
		return "Timeslot [id=" + id + ", doctorId=" + doctorId + ", patientId=" + patientId + ", starttime="
				+ starttime + ", visitdate=" + visitdate + "]";
	}
	
}
