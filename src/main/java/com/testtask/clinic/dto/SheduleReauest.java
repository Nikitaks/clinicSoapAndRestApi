package com.testtask.clinic.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class SheduleReauest {
	private long doctor_id;
	private LocalDate date;
	private LocalTime  startTime;
	private LocalTime  finishTime;
	private int intervalTimeSlotMinutes;
	private int timeSlotsNumber;
	
	public SheduleReauest() {
		super();
	}

	@Override
	public String toString() {
		return "SheduleReauest [doctor_id=" + doctor_id + ", date=" + date + ", startTime=" + startTime
				+ ", finishTime=" + finishTime + ", intervalTimeSlotMinutes=" + intervalTimeSlotMinutes
				+ ", timeSlotsNumber=" + timeSlotsNumber + "]";
	}

	public SheduleReauest(long doctor_id, LocalDate date, LocalTime startTime, LocalTime finishTime,
			int intervalTimeSlotMinutes, int timeSlotsNumber) {
		super();
		this.doctor_id = doctor_id;
		this.date = date;
		this.startTime = startTime;
		this.finishTime = finishTime;
		this.intervalTimeSlotMinutes = intervalTimeSlotMinutes;
		this.timeSlotsNumber = timeSlotsNumber;
	}

	public long getDoctor_id() {
		return doctor_id;
	}
	public void setDoctor_id(long doctor_id) {
		this.doctor_id = doctor_id;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public LocalTime getStartTime() {
		return startTime;
	}
	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}
	public LocalTime getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(LocalTime finishTime) {
		this.finishTime = finishTime;
	}
	public int getTimeSlotsNumber() {
		return timeSlotsNumber;
	}
	public void setTimeSlotsNumber(int timeSlotsNumber) {
		this.timeSlotsNumber = timeSlotsNumber;
	}

	public int getIntervalTimeSlotMinutes() {
		return intervalTimeSlotMinutes;
	}

	public void setIntervalTimeSlotMinutes(int intervalTimeSlotMinutes) {
		this.intervalTimeSlotMinutes = intervalTimeSlotMinutes;
	}
}
