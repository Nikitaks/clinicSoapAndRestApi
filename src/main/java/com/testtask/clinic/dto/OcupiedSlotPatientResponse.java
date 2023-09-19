package com.testtask.clinic.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.testtask.clinic.database.entity.Timeslot;

public class OcupiedSlotPatientResponse {

	private long timeslotId;    
	private long doctorId;
    private LocalTime starttime;
    private LocalDate visitdate;
    
	public OcupiedSlotPatientResponse() {
		super();
	}

	public OcupiedSlotPatientResponse(long timeslotId, long doctorId, LocalTime starttime, LocalDate visitdate) {
		super();
		this.timeslotId = timeslotId;
		this.doctorId = doctorId;
		this.starttime = starttime;
		this.visitdate = visitdate;
	}
	
	public OcupiedSlotPatientResponse(Timeslot timeslot) {
		super();
		this.timeslotId = timeslot.getId();
		this.doctorId = timeslot.getDoctorId();
		this.starttime = timeslot.getStarttime();
		this.visitdate = timeslot.getVisitdate();
	}

	public long getTimeslotId() {
		return timeslotId;
	}

	public void setTimeslotId(long timeslotId) {
		this.timeslotId = timeslotId;
	}

	public long getDoctorId() {
		return doctorId;
	}

	public void setDoctorTimeslotId(long doctorId) {
		this.doctorId = doctorId;
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

}
