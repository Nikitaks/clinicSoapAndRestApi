package com.testtask.clinic.dto;

import java.time.LocalTime;

import com.testtask.clinic.database.entity.Timeslot;

public class FreeSlotDoctorDateResponse {

	private long id;
    private LocalTime starttime;
    
	public FreeSlotDoctorDateResponse() {
		super();
	}

	public FreeSlotDoctorDateResponse(long id, LocalTime starttime) {
		super();
		this.id = id;
		this.starttime = starttime;
	}
	
	public FreeSlotDoctorDateResponse(Timeslot timeslot) {
		super();
		this.id = timeslot.getId();
		this.starttime = timeslot.getStarttime();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalTime getStarttime() {
		return starttime;
	}

	public void setStarttime(LocalTime starttime) {
		this.starttime = starttime;
	}
}
